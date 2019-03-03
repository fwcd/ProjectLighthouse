package lighthouse.ui.board.view.discord;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.ui.board.view.LighthouseView;
import lighthouse.ui.board.viewmodel.LighthouseViewModel;
import lighthouse.util.Listener;
import lighthouse.util.ListenerList;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
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
	private final ListenerList<Void> readyListeners = new ListenerList<>("DiscordLighthouseView.readyListeners");
	
	private JDA jda;
	private String prefix;
	
	public DiscordLighthouseView(String prefix) {
		this.prefix = prefix;
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
			// TODO
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
