package lighthouse.ui.tickers;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import lighthouse.model.GameState;
import lighthouse.ui.GameContext;
import lighthouse.ui.board.BoardViewController;
import lighthouse.ui.board.viewmodel.BoardStatistics;
import lighthouse.ui.board.viewmodel.overlay.ConfettiAnimation;
import lighthouse.ui.util.Status;
import lighthouse.util.ColorUtils;

public class GameWinChecker implements Ticker {
	private final GameState game;
	private final GameContext context;
	private final BoardStatistics statistics;
	private final BoardViewController board;
	private boolean alreadyWon = false;
	
	public GameWinChecker(
		BoardViewController board,
		GameState game,
		GameContext context,
		BoardStatistics statistics
	) {
		this.board = board;
		this.context = context;
		this.game = game;
		this.statistics = statistics;
	}
	
	@Override
	public void tick() {
		if (!alreadyWon && game.isWon() && !game.getBoard().isEmpty()) {
			alreadyWon = true;
			
			String message = "FINISHED GAME in " + statistics.getMoveCount() + " moves!";
			if (game.getLevel().isTooEasy()) {
				message += " (Pretty easily though, since your goal board exactly matches your starting board)";
			} else {
				message += " Hooray!";
			}
			board.play(new ConfettiAnimation());
			context.setStatus(new Status("Won", ColorUtils.LIGHT_VIOLET));
			JOptionPane.showMessageDialog(board.getComponent(), message);
		}
	}
	
	public void reset() {
		alreadyWon = false;
	}
}
