package lighthouse.ui.board.view.discord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.ui.board.input.BoardKeyInput;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class KeyCommand implements DiscordCommand {
	private static final Logger LOG = LoggerFactory.getLogger(KeyCommand.class);
	private final int keyCode;
	private final BoardKeyInput input;
	
	public KeyCommand(int keyCode, BoardKeyInput input) {
		this.keyCode = keyCode;
		this.input = input;
	}
	
	@Override
	public void invoke(String args, User author, MessageChannel channel) {
		int times = 1;
		
		if (args.length() > 1) {
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
