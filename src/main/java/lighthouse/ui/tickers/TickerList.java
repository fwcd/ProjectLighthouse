package lighthouse.ui.tickers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	public void tick() {
		for (Ticker ticker : tickers) {
			ticker.tick();
		}
	}
	
	public void add(Ticker ticker) { tickers.add(ticker); }
	
	public void remove(Ticker ticker) { tickers.remove(ticker); }
}
