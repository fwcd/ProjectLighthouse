package lighthouse.ui.discordrpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import lighthouse.util.ConfigFile;
import lighthouse.util.ResourceConfigFile;

public class DiscordRPCRunner {
	private static final Logger LOG = LoggerFactory.getLogger(DiscordRPCRunner.class);
	private final DiscordRPC lib = DiscordRPC.INSTANCE;
	private String details = "Lighthouse";
	private String largeImageKey = "lighthouseicon";
	private String state = "";
	private boolean updateSoon = false;
	
	public void start() {
		ConfigFile config = new ResourceConfigFile("/discordToken.txt");
		
		if (config.has("clientID")) {
			// Source: https://github.com/MinnDevelopment/java-discord-rpc#basics
			String appID = config.get("clientID");
			String steamID = "";
			
			LOG.info("Starting Discord RPC");
			
			DiscordEventHandlers handlers = new DiscordEventHandlers();
			handlers.ready = user -> LOG.info("Discord RPC is ready");
			handlers.errored = (err, msg) -> LOG.info("Discord RPC errored with code {}: {}", err, msg);
			handlers.disconnected = (err, msg) -> LOG.info("Discord RPC disconnected with code {}: {}", err, msg);
			lib.Discord_Initialize(appID, handlers, true, steamID);
			
			new Thread(this::runWorker, "Discord RPC").start();
		} else {
			LOG.info("Did not start Discord RPC since the config file did not contain a 'clientID'");
		}
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	public void updatePresenceSoon() {
		updateSoon = true;
	}
	
	private void updatePresenceNow() {
		DiscordRichPresence presence = new DiscordRichPresence();
		presence.details = details;
		presence.largeImageKey = largeImageKey;
		presence.state = state;
		lib.Discord_UpdatePresence(presence);
	}
	
	private void runWorker() {
		while (!Thread.currentThread().isInterrupted()) {
			if (updateSoon) {
				updatePresenceNow();
				updateSoon = false;
			}
			
			LOG.trace("Running Discord RPC callbacks");
			lib.Discord_RunCallbacks();
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ignored) {}
		}
	}
}
