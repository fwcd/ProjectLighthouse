package lighthouse.ui.tickers;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import lighthouse.model.GameState;
import lighthouse.ui.board.floating.FloatingContext;
import lighthouse.ui.loop.Ticker;
import lighthouse.ui.util.Status;
import lighthouse.util.ColorUtils;

public class GameWinChecker implements Ticker {
	private final JComponent parent;
	private final GameState game;
	private final FloatingContext floatingCtx;
	private boolean alreadyWon = false;
	
	public GameWinChecker(JComponent parent, GameState game, FloatingContext floatingCtx) {
		this.parent = parent;
		this.game = game;
		this.floatingCtx = floatingCtx;
	}
	
	@Override
	public void tick() {
		GameState state = game.getState();
		if (!alreadyWon && state.isWon() && !state.getActiveBoard().isEmpty() && game.getLevelStage().isInGame()) {
			alreadyWon = true;
			floatingCtx.clear();
			
			String message = "GAME WON!";
			if (state.getLevel().isTooEasy()) {
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
