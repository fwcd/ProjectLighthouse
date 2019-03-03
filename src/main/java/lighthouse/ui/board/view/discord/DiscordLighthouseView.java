package lighthouse.ui.board.view.discord;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.ui.board.view.LighthouseView;
import lighthouse.ui.board.viewmodel.LighthouseViewModel;
import lighthouse.util.Listener;
import lighthouse.util.ListenerList;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;

/**
 * A Lighthouse view that uses the Discord
 * API to present the current Lighthouse state.
 */
public class DiscordLighthouseView implements LighthouseView {
	private static final Logger LOG = LoggerFactory.getLogger(DiscordLighthouseView.class);
	
	private final Map<String, DiscordCommand> commands = new HashMap<>();
	private final ListenerList<Void> readyListeners = new ListenerList<>("DiscordLighthouseView.readyListeners");
	
	private final Pattern commandPattern;
	private JDA jda;
	
	public DiscordLighthouseView(String prefix) {
		commandPattern = Pattern.compile(Pattern.quote(prefix) + "(\\w+)(?:\\s+(.+))?");
		registerCommands();
	}
	
	private void registerCommands() {
		commands.put("ping", new PingCommand());
	}
	
	public void connect(String token) {
		try {
			jda = new JDABuilder(token)
				.addEventListeners((EventListener) this::onEvent)
				.build();
		} catch (LoginException e) {
			LOG.error("Error while connecting to discord:", e);
		}
	}
	
	private void onEvent(GenericEvent event) {
		if (event instanceof ReadyEvent) {
			readyListeners.fire();
		} else if (event instanceof MessageReceivedEvent) {
			onMessage((MessageReceivedEvent) event);
		}
	}
	
	private void onMessage(MessageReceivedEvent event) {
		Message msg = event.getMessage();
		String text = msg.getContentStripped();
		Matcher matcher = commandPattern.matcher(text);
		
		if (matcher.find()) {
			String command = matcher.group(1);
			String args = matcher.group(2);
			
			if (commands.containsKey(command)) {
				commands.get(command).invoke(args, msg.getAuthor(), msg.getChannel());
			} else {
				msg.getChannel().sendMessage("Sorry, I did not recognize the command `" + command + "`");
			}
		} else {
			LOG.debug("Received non-command message for Discord: {}", msg);
		}
	}
	
	public boolean isConnected() {
		return jda != null && jda.getStatus().equals(JDA.Status.CONNECTED);
	}
	
	public void addReadyListener(Listener<Void> listener) {
		readyListeners.add(listener);
	}
	
	public void removeReadyListener(Listener<Void> listener) {
		readyListeners.remove(listener);
	}
	
	@Override
	public void draw(LighthouseViewModel viewModel) {
		// TODO
	}
}
