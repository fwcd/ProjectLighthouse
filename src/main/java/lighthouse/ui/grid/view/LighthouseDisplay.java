package lighthouse.ui.grid.view;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import javax.management.InvalidAttributeValueException;

import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class wraps the network communication with the lighthouse in a simple
 * interface. The network connection is configured upon object creation but
 * needs to manually connect. Afterwards data can be sent to the lighthouse.
 */
public class LighthouseDisplay implements AutoCloseable {
	private static final Logger LOG = LoggerFactory.getLogger(LighthouseDisplay.class);
	private String username;
	private String token;
	private LighthouseDisplayHandler handler;
	private WebSocketClient client;

	/**
	 * Creates a new LighthouseDisplay with given user-name and access token and
	 * sets weather connect and disconnect messages should be printed in stdOut
	 */
	public LighthouseDisplay(String username, String token) {
		handler = new LighthouseDisplayHandler(this);
		this.username = username;
		this.token = token;
	}

	/**
	 * Connects to the lighthouse server with the default web-socket address
	 * "wss://lighthouse.uni-kiel.de/user/<username>/model" and Certificate checking
	 * disabled (root CA unknown)
	 * 
	 * @throws InvalidAttributeValueException
	 *             if "username" is invalid for creating a connection
	 * @throws IOException
	 *             if there is an error while connecting or constructing the
	 *             web-socket
	 * @throws Exception
	 *             if there is an error constructing a web-socket-client
	 */
	public void connect() throws Exception {
		try {
			connect("wss://lighthouse.uni-kiel.de/user/" + username + "/model", true);
		} catch (URISyntaxException e) {
			throw new InvalidAttributeValueException("Given username is invalid for a connection");
		}
	}

	/**
	 * Connects to the lighthouse server at the given web-socket address
	 * 
	 * @throws URISyntaxException
	 *             if destUri contains errors
	 * @throws IOException
	 *             if there is an error while connecting or constructing the
	 *             web-socket
	 * @throws Exception
	 *             if there is an error constructing a web-socket-client
	 */
	public void connect(String destUri) throws Exception {
		connect(destUri, false);
	}

	/**
	 * Connects to the lighthouse server at the given web-socket address and
	 * disables certificate validation if selfSigned is true. Connection is
	 * finalized asynchronous
	 * 
	 * @throws URISyntaxException
	 *             if destUri contains errors
	 * @throws IOException
	 *             if there is an error while connecting or constructing the
	 *             web-socket
	 * @throws Exception
	 *             if there is an error constructing a web-socket-client
	 */
	public void connect(String destUri, boolean selfSigned) throws Exception {
		if (selfSigned) {
			// Since we use a self-signed certificate, we can't check the
			// validity of the certificate (and we have to disable this check)
			SslContextFactory sec = new SslContextFactory(true);
			client = new WebSocketClient(sec);
		} else {
			client = new WebSocketClient();
		}

		URI targetUri = new URI(destUri);
		ClientUpgradeRequest upgrade = new ClientUpgradeRequest();

		client.start();
		client.connect(handler, targetUri, upgrade);
		LOG.info("Connecting to: {}", targetUri);
	}

	/**
	 * getter for the configured username
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * getter for the configured token
	 * 
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sends a packet of data to the lighthouse server. Usually the data should be a
	 * byte array consisting of 1176 bytes. The first three bytes are the red, green
	 * and blue color values of the first window. The windows start at the top-left
	 * corner. If less bytes are sent, only the first windows are updated. The next
	 * transmission starts at the first window again.
	 *
	 * @param data
	 *            The data to send
	 * @throws IOException
	 *             if some error occurs during sending of the data.
	 */
	public void send(byte[] data) throws IOException {
		handler.send(data, 0, data.length);
	}

	/**
	 * returns if there is currently a connection open note: connection is
	 * established asynchronous so this value might be false after a call of
	 * connect()
	 * 
	 * @return if the connection is open
	 */
	public boolean isConnected() {
		return handler.isConnected();
	}
	
	@Override
	public void close() {
		handler.close();
		try {
			if (client != null) {
				client.stop();
			}
		} catch (Exception e) {
			LOG.error("An exception occurred while closing the LighthouseDisplay: ", e);
		}
	}

	/**
	 * private class for handling the web-socket
	 */
	@WebSocket(maxTextMessageSize = 64 * 1024, maxBinaryMessageSize = 64 * 1024)
	public class LighthouseDisplayHandler {
		private LighthouseDisplay parent;
		private Session session;
		private boolean connected = false;

		private LighthouseDisplayHandler(LighthouseDisplay parent) {
			this.parent = parent;
		}

		/**
		 * this method sends the given data as a lighthouse request to the server
		 * 
		 * @param data
		 *            the data to send
		 * @param offset
		 *            the offset to start in the data
		 * @param length
		 *            the length to send
		 * @throws IOException
		 *             on errors while transmitting the data
		 */
		public void send(byte[] data, int offset, int length) throws IOException {
			if (isConnected()) {
				// Lighthouse request (as JSON/Type mix):
				// {
				// "VERB" => String // (GET, PUT, STREAM)
				// "PATH" => [String] // (["user",<username>,"model"])
				// "AUTH" => {"USER" => String, "TOKEN" => String}
				// "META" => {* => *}
				// "PAYL" => *
				// "REID" => Int // Request-ID
				// }
				MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
				packer.packMapHeader(6);
				{
					packer.packString("REID");
					packer.packInt(0);

					packer.packString("VERB");
					packer.packString("PUT");

					packer.packString("PATH");
					packer.packArrayHeader(3);
					{
						packer.packString("user");
						packer.packString(parent.getUsername());
						packer.packString("model");
					}

					packer.packString("AUTH");
					packer.packMapHeader(2);
					{
						packer.packString("USER");
						packer.packString(parent.getUsername());

						packer.packString("TOKEN");
						packer.packString(parent.getToken());
					}

					packer.packString("META");
					packer.packMapHeader(0);

					packer.packString("PAYL");
					packer.packBinaryHeader(length);
					packer.addPayload(data, offset, length);
				}
				RemoteEndpoint endpoint = session.getRemote();
				endpoint.sendBytes(ByteBuffer.wrap(packer.toByteArray()));
				endpoint.flush();
				LOG.debug("Sent {} bytes", length);
			}
		}

		/**
		 * this method sends the close notification to close this connection
		 */
		public void close() {
			connected = false;
			if (session != null) {
				session.close(StatusCode.NORMAL, "end of data");
			}
		}

		/**
		 * this method tells if a connection is established
		 * 
		 * @return if connection is established
		 */
		public boolean isConnected() {
			return connected && session != null && session.isOpen();
		}

		/**
		 * event target for the web-socket close event
		 */
		@OnWebSocketClose
		public void onClose(int statusCode, String reason) {
			connected = false;
			LOG.info("Connection closed [{}]: {}", statusCode, reason);
		}

		/**
		 * event target for the web-socket connect event
		 */
		@OnWebSocketConnect
		public void onConnect(Session session) {
			// save session for usage in communication
			this.session = session;
			connected = true;
			if (LOG.isDebugEnabled()) {
				LOG.debug("Got connection: {}", session);
			} else {
				LOG.info("Connected");
			}
		}

		@OnWebSocketMessage
		public void onMessage(String msg) {
			LOG.debug("Got text Message: {}", msg);
		}

		@OnWebSocketMessage
		public void methodName(byte buf[], int offset, int length) {
			if (LOG.isDebugEnabled()) {
				StringBuilder str = new StringBuilder("Got binary Message: ");
				for (int i = 0; i < length; i++) {
					str.append(String.format("%02X", buf[offset + i] & 0xFF));
				}
				LOG.debug(str.toString());
			}
		}

		/**
		 * event target for the web-socket error event
		 */
		@OnWebSocketError
		public void onError(Session session, Throwable error) {
			LOG.error("Session: ", session);
			LOG.error("Lighthouse web-socket error: ", error);
		}
	}
}
