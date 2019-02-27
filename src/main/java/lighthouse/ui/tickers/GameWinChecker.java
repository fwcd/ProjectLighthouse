package lighthouse.ui.tickers;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import lighthouse.model.GameState;
import lighthouse.ui.loop.Ticker;
import lighthouse.ui.util.Status;
import lighthouse.util.ColorUtils;

public class GameWinChecker implements Ticker {
	private final JComponent parent;
	private final GameState game;
	private boolean alreadyWon = false;
	
	public GameWinChecker(JComponent parent, GameState game) {
		this.parent = parent;
		this.game = game;
	}
	
	@Override
	public void tick() {
		if (!alreadyWon && game.isWon() && !game.getBoard().isEmpty()) {
			alreadyWon = true;
			floatingCtx.clear();
			
			String message = "GAME WON!";
			if (game.getLevel().isTooEasy()) {
				message += " (Pretty easily though, since your goal board exactly matches your starting board)";
			} else {
				message += " Hooray!";
			}
			game.setStatus(new Status("Won", ColorUtils.LIGHT_VIOLET));
			JOptionPane.showMessageDialog(parent, message);
		}
	}
	
	public void reset() {
		alreadyWon = false;
	}
}
