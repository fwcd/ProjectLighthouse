package lighthouse.ui.board.view.discord;

import lighthouse.ui.board.input.BoardKeyInput;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class KeyCommand implements DiscordCommand {
	private final int keyCode;
	private final BoardKeyInput input;
	
	public KeyCommand(int keyCode, BoardKeyInput input) {
		this.keyCode = keyCode;
		this.input = input;
	}
	
	@Override
	public void invoke(String args, User author, MessageChannel channel) {
		input.keyPressed(keyCode);
		input.keyReleased(keyCode);
	}
}
