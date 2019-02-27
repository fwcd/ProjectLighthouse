package lighthouse.ui.tickers;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import lighthouse.model.Game;
import lighthouse.ui.board.floating.FloatingContext;
import lighthouse.ui.loop.Ticker;

public class GameWinChecker implements Ticker {
	private final JComponent parent;
	private final Game game;
	private final FloatingContext floatingCtx;
	private boolean alreadyWon = false;
	
	public GameWinChecker(JComponent parent, Game game, FloatingContext floatingCtx) {
		this.parent = parent;
		this.game = game;
		this.floatingCtx = floatingCtx;
	}
	
	@Override
	public void tick() {
		if (!alreadyWon && game.getState().isWon() && !game.getState().getActiveBoard().isEmpty() && game.getLevelStage().isInGame()) {
			alreadyWon = true;
			floatingCtx.clear();
			JOptionPane.showMessageDialog(parent, "GAME WON! Hooray!");
		}
	}
	
	public void reset() {
		alreadyWon = false;
	}
}
