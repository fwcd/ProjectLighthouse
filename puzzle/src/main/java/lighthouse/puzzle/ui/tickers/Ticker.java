package lighthouse.puzzle.ui.tickers;

import lighthouse.puzzle.ui.modes.GameMode;
import lighthouse.puzzle.ui.perspectives.GamePerspective;

public interface Ticker {
	void tick(GameMode mode, GamePerspective perspective);
}
