package lighthouse.ui.scene.view.discord;

import java.util.Set;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class UnsummonCommand implements DiscordCommand {
	private final Set<MessageChannel> activeChannels;
	
	public UnsummonCommand(Set<MessageChannel> activeChannels) {
		this.activeChannels = activeChannels;
	}
	
	@Override
	public void invoke(String args, User author, MessageChannel channel) {
		activeChannels.remove(channel);
		channel.sendMessage("This channel will no longer receive boards from me!").queue();
	}
}
