package lighthouse.gameapi;

public class GameMenuEntry {
	private final String label;
	private final Runnable action;
	
	public GameMenuEntry(String label, Runnable action) {
		this.label = label;
		this.action = action;
	}
	
	public String getLabel() { return label; }
	
	public Runnable getAction() { return action; }
}
