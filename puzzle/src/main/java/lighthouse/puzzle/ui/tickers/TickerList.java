package lighthouse.puzzle.ui.tickers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lighthouse.puzzle.ui.modes.GameMode;
import lighthouse.puzzle.ui.perspectives.GamePerspective;

/**
 * A combined ticker.
 */
public class TickerList implements Ticker {
	private final List<Ticker> tickers;
	
	public TickerList() {
		tickers = new ArrayList<>();
	}
	
	public TickerList(Collection<? extends Ticker> tickers) {
		this.tickers = new ArrayList<>(tickers);
	}
	
	@Override
	public void tick(GameMode mode, GamePerspective perspective) {
		for (Ticker ticker : tickers) {
			ticker.tick(mode, perspective);
		}
	}
	
	public void add(Ticker ticker) { tickers.add(ticker); }
	
	public void remove(Ticker ticker) { tickers.remove(ticker); }
}
