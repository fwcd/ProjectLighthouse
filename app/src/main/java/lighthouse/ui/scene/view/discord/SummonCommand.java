package lighthouse.ui.scene.view.discord;

import java.util.Set;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class SummonCommand implements DiscordCommand {
	private final Set<MessageChannel> activeChannels;
	
	public SummonCommand(Set<MessageChannel> activeChannels) {
		this.activeChannels = activeChannels;
	}
	
	@Override
	public void invoke(String args, User author, MessageChannel channel) {
		activeChannels.add(channel);
		channel.sendMessage("This channel will now continuously receive boards from me!").queue();
	}
}
