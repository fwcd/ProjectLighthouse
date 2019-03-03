package lighthouse.ui.board.view.discord;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

@FunctionalInterface
public interface DiscordCommand {
	void invoke(String args, User author, MessageChannel channel);
}
