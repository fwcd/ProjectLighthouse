package lighthouse.ui.tickers;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import lighthouse.model.GameState;
import lighthouse.ui.GameContext;
import lighthouse.ui.board.viewmodel.BoardStatistics;
import lighthouse.ui.util.Status;
import lighthouse.util.ColorUtils;

public class GameWinChecker implements Ticker {
	private final JComponent parent;
	private final GameState game;
	private final GameContext context;
	private final BoardStatistics statistics;
	private boolean alreadyWon = false;
	
	public GameWinChecker(JComponent parent, GameState game, GameContext context, BoardStatistics statistics) {
		this.parent = parent;
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
			context.setStatus(new Status("Won", ColorUtils.LIGHT_VIOLET));
			JOptionPane.showMessageDialog(parent, message);
		}
	}
	
	public void reset() {
		alreadyWon = false;
	}
}
