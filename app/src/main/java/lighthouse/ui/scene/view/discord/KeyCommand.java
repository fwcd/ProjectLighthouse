package lighthouse.ui.scene.view.discord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.ui.scene.input.SceneKeyInput;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class KeyCommand implements DiscordCommand {
	private static final Logger LOG = LoggerFactory.getLogger(KeyCommand.class);
	private final int keyCode;
	private final SceneKeyInput input;
	
	public KeyCommand(int keyCode, SceneKeyInput input) {
		this.keyCode = keyCode;
		this.input = input;
	}
	
	@Override
	public void invoke(String args, User author, MessageChannel channel) {
		int times = 1;
		
		if (args != null && !args.isEmpty()) {
			try {
				times = Integer.parseInt(args);
			} catch (NumberFormatException e) {
				LOG.warn("Unrecognized KeyCommand argument: {}", args);
			}
		}
		
		for (int i = 0; i < times; i++) {
			input.keyPressed(keyCode);
			input.keyReleased(keyCode);
		}
	}
}
