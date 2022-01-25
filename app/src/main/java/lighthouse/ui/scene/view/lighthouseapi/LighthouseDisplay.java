package lighthouse.ui.scene.view.lighthouseapi;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.InvalidAttributeValueException;

import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.WriteCallback;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageStringCodingException;
import org.msgpack.core.MessageTypeCastException;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.value.Value;
import org.msgpack.value.impl.ImmutableStringValueImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.ui.scene.input.lighthouseapi.ILighthouseInputListener;
import lighthouse.util.Listener;
import lighthouse.util.ListenerList;

/**
 * This class wraps the network communication with the lighthouse in a simple
 * interface. The network connection is configured upon object creation but
 * needs to manually connect. Afterwards data can be sent to the lighthouse.
 */
public class LighthouseDisplay implements AutoCloseable {
	private static final Logger LOG = LoggerFactory.getLogger(LighthouseDisplay.class);
	private final String username;
	private final String token;
	private LighthouseDisplayHandler handler;
	private WebSocketClient client;
	
	private final Set<ILighthouseInputListener> observers = new HashSet<>();
	private final ListenerList<Void> connectListeners = new ListenerList<>("LighthouseDisplay.connectListeners");
	private final ListenerList<Void> disconnectListeners = new ListenerList<>("LighthouseDisplay.disconnectListeners");
	
	/**
	 * Creates a new LighthouseDisplay with given user-name and access token
	 */
	public LighthouseDisplay(String username, String token) {
		handler = new LighthouseDisplayHandler(this);
		this.username = username;
		this.token = token;
	}

	/**
	 * Connects to the lighthouse server with the default web-socket address
	 * "wss://lighthouse.uni-kiel.de/websocket" and Certificate checking
	 * disabled (root CA unknown)
	 * 
	 * @throws InvalidAttributeValueException if "username" is invalid for creating
	 *                                        a connection
	 * @throws IOException                    if there is an error while connecting
	 *                                        or constructing the web-socket
	 * @throws Exception                      if there is an error constructing a
	 *                                        web-socket-client
	 */
	public void connect() throws Exception {
		try {
			connect("wss://lighthouse.uni-kiel.de/websocket", true);
		} catch (URISyntaxException e) {
			throw new InvalidAttributeValueException("Given username is invalid for a connection");
		}
	}

	/**
	 * Connects to the lighthouse server at the given web-socket address
	 * 
	 * @throws URISyntaxException if destUri contains errors
	 * @throws IOException        if there is an error while connecting or
	 *                            constructing the web-socket
	 * @throws Exception          if there is an error constructing a
	 *                            web-socket-client
	 */
	public void connect(String destUri) throws Exception {
		connect(destUri, false);
	}

	/**
	 * Connects to the lighthouse server at the given web-socket address and
	 * disables certificate validation if selfSigned is true. Connection is
	 * finalized asynchronous
	 * 
	 * @throws URISyntaxException if destUri contains errors
	 * @throws IOException        if there is an error while connecting or
	 *                            constructing the web-socket
	 * @throws Exception          if there is an error constructing a
	 *                            web-socket-client
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
	 * @param data The data to send
	 * @throws IOException if some error occurs during sending of the data.
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

	public void close() {
		handler.close();
		try {
			client.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addButtonListener(ILighthouseInputListener listener) {
		observers.add(listener);
	}

	public void removeButtonListener(ILighthouseInputListener listener) {
		observers.remove(listener);
	}
	
	public void addConnectListener(Listener<Void> listener) {
		connectListeners.add(listener);
	}
	
	public void removeConnectListener(Listener<Void> listener) {
		connectListeners.remove(listener);
	}
	
	public void addDisconnectListener(Listener<Void> listener) {
		disconnectListeners.add(listener);
	}
	
	public void removeDisconnectListener(Listener<Void> listener) {
		disconnectListeners.remove(listener);
	}

	/**
	 * private class for handling the web-socket
	 */
	@WebSocket(maxTextMessageSize = 64 * 1024, maxBinaryMessageSize = 64 * 1024)
	public static class LighthouseDisplayHandler {
		private LighthouseDisplay parent;
		private Session session;
		private boolean connected = false;
		private RemoteEndpoint endpoint = null;

		private LighthouseDisplayHandler(LighthouseDisplay parent) {
			this.parent = parent;
		}

		/**
		 * this method sends the given data as a lighthouse request to the server
		 * 
		 * @param data   the data to send
		 * @param offset the offset to start in the data
		 * @param length the length to send
		 * @throws IOException on errors while transmitting the data
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

				endpoint.sendBytes(ByteBuffer.wrap(packer.toByteArray()), new WriteCallback() {
					@Override
					public void writeSuccess() {}
					@Override
					public void writeFailed(Throwable err) {
						LOG.error("Sending image failed: ", err);
					}
				});
				endpoint.flush();
			}
		}

		/**
		 * this method sends the close notification to close this connection
		 */
		public void close() {
			connected = false;
			parent.disconnectListeners.fire();
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
			parent.disconnectListeners.fire();
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
			LOG.debug("Got connection: {}", session);
			
			// request stream for controller input
			MessageBufferPacker packer2 = MessagePack.newDefaultBufferPacker();
			try {
				endpoint = session.getRemote();
				packer2.packMapHeader(6);
				{
					packer2.packString("REID");
					packer2.packInt(-1);
	
					packer2.packString("VERB");
					packer2.packString("STREAM");
	
					packer2.packString("PATH");
					packer2.packArrayHeader(3);
					{
						packer2.packString("user");
						packer2.packString(parent.getUsername());
						packer2.packString("model");
					}
	
					packer2.packString("AUTH");
					packer2.packMapHeader(2);
					{
						packer2.packString("USER");
						packer2.packString(parent.getUsername());
	
						packer2.packString("TOKEN");
						packer2.packString(parent.getToken());
					}
	
					packer2.packString("META");
					packer2.packMapHeader(0);
	
					packer2.packString("PAYL");
					packer2.packNil();
				}
	
				endpoint.sendBytes(ByteBuffer.wrap(packer2.toByteArray()));
				endpoint.flush();
				
				parent.connectListeners.fire();
			} catch (IOException e) {
				LOG.error("Error requesting controller input stream:", e);
			}
		}

		@OnWebSocketMessage
		public void onMessage(String msg) {
			LOG.trace("Got text Message: {}", msg);
		}

		@OnWebSocketMessage
		public void onMessage(byte buf[], int offset, int length) {
			if (LOG.isTraceEnabled()) {
				StringBuilder builder = new StringBuilder("got binary Message: ");
				for (int i = 0; i < length; i++) {
					builder.append(Integer.toHexString(buf[offset + i] & 0xFF));
				}
				LOG.trace(builder.toString());
			}
			MessageUnpacker unp = MessagePack.newDefaultUnpacker(buf, offset, length);
			try {
				Value v = unp.unpackValue();
				Map<Value,Value> vmap = v.asMapValue().map();
				int rnum = vmap.get(new ImmutableStringValueImpl("RNUM")).asIntegerValue().toInt();
				if (rnum == 200) {
					int reid = vmap.get(new ImmutableStringValueImpl("REID")).asIntegerValue().toInt();
					if (reid == -1) {
						Value payload = vmap.get(new ImmutableStringValueImpl("PAYL"));
						List<Value> entries; // list of event entries
						if (payload.isArrayValue()) {
							entries = payload.asArrayValue().list();
						} else {
							entries = new ArrayList<Value>(1);
							entries.add(payload);
						}
						for (Value entry : entries) {
							Map<Value,Value> payl = entry.asMapValue().map();
							
							boolean isKeyboard = true;
							Value btn = payl.get(new ImmutableStringValueImpl("key"));
							if (btn == null) {
								btn = payl.get(new ImmutableStringValueImpl("btn"));
								isKeyboard = false;
							}
							
							boolean pressed = payl.get(new ImmutableStringValueImpl("dwn")).asBooleanValue().getBoolean();
							int src = payl.get(new ImmutableStringValueImpl("src")).asIntegerValue().toInt();
							int button = btn.asIntegerValue().toInt();
							
							for (ILighthouseInputListener listener : parent.observers) {
								try {
									if (isKeyboard) {
										listener.keyboardEvent(src, button, pressed);
									} else {
										listener.controllerEvent(src, button, pressed);
									}
								} catch (Exception e) {
									LOG.error("Error while delegating ILighthouseInputListener event:", e);
								}
							}
						}
					}
				} else {
					Value responseValue = vmap.get(new ImmutableStringValueImpl("RESPONSE"));
					String response = "";
					if (responseValue.isStringValue()) {
						try {
							response = responseValue.asStringValue().asString();
						} catch (MessageStringCodingException ignored) {}
					}
					LOG.error("API Error: ({}) {}", rnum, response);
				}
			} catch (IOException e) {
				LOG.error("IOException while receiving message", e);
			} catch (NullPointerException ignored) { // in case of malformed message
				LOG.debug("Got malformed message (though this exception is ignored):", ignored);
			} catch (MessageTypeCastException ignored) { // in case of malformed message
				LOG.debug("Got malformed message (though this exception is ignored):", ignored);
			}
		}

		/**
		 * event target for the web-socket error event
		 */
		@OnWebSocketError
		public void onError(Session session, Throwable error) {
			LOG.error("WebSocket-Error", error);
		}
	}
}
