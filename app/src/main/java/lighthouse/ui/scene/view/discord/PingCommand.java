package lighthouse.ui.scene.view.discord;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class PingCommand implements DiscordCommand {
	@Override
	public void invoke(String args, User author, MessageChannel channel) {
		channel.sendMessage("Pong!").queue();
	}
}
