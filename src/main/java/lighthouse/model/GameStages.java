package lighthouse.model;

import java.util.Arrays;
import java.util.List;

/**
 * Holds the various stages of a game match.
 */
public class GameStages {
	public static final List<GameStage> STAGES = Arrays.asList(new Start(), new Current(), new End());
	
	public static class Start implements GameStage {
		@Override
		public int getIndex() { return 0; }
		
		@Override
		public String getName() { return "Start"; }
	}
	
	public static class Current implements GameStage {
		@Override
		public int getIndex() { return 1; }
		
		@Override
		public String getName() { return "Current"; }
	}
	
	public static class End implements GameStage {
		@Override
		public int getIndex() { return 2; }
		
		@Override
		public String getName() { return "End"; }
	}
}
