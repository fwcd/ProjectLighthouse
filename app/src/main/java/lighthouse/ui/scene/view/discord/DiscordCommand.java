package lighthouse.ui.scene.view.discord;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

@FunctionalInterface
public interface DiscordCommand {
	void invoke(String args, User author, MessageChannel channel);
	
	default DiscordCommand then(DiscordCommand next) {
		return (args, author, ch) -> {
			invoke(args, author, ch);
			next.invoke(args, author, ch);
		};
	}
}
