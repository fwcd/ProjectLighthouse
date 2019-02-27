package lighthouse.ui.tickers;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import lighthouse.model.Game;
import lighthouse.ui.loop.Ticker;

public class GameWinChecker implements Ticker {
	private final JComponent parent;
	private final Game game;
	private boolean alreadyWon = false;
	
	public GameWinChecker(JComponent parent, Game game) {
		this.parent = parent;
		this.game = game;
	}
	
	@Override
	public void tick() {
		if (!alreadyWon && game.getState().isWon() && !game.getState().getActiveBoard().isEmpty() && game.getLevelStage().isInGame()) {
			alreadyWon = true;
			JOptionPane.showMessageDialog(parent, "GAME WON! Hooray!");
		}
	}
	
	public void reset() {
		alreadyWon = false;
	}
}
