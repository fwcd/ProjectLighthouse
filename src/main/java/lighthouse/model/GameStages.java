package lighthouse.model;

import java.util.Arrays;
import java.util.List;

/**
 * Holds the various stages of a game match.
 */
public class GameStages {
	public static final GameStage START = new Start();
	public static final GameStage CURRENT = new Current();
	public static final GameStage GOAL = new Goal();
	public static final List<GameStage> STAGES = Arrays.asList(START, CURRENT, GOAL);
	
	private GameStages() {}
	
	private static class Start implements GameStage {
		@Override
		public int getIndex() { return 0; }
		
		@Override
		public String getName() { return "Start"; }
		
		@Override
		public void transitionFrom(GameStage lastStage, Game game) {
			if (lastStage.isCurrent()) {
				game.backupBoard();
			}
			game.setBoard(game.getLevel().getStart());
		}
	}
	
	private static class Current implements GameStage {
		@Override
		public int getIndex() { return 1; }
		
		@Override
		public String getName() { return "Current"; }
		
		@Override
		public void transitionFrom(GameStage lastStage, Game game) {
			game.revertToBackupBoardOr(Board::new);
		}
		
		@Override
		public boolean isCurrent() { return true; }
	}
	
	private static class Goal implements GameStage {
		@Override
		public int getIndex() { return 2; }
		
		@Override
		public String getName() { return "Goal"; }
		
		@Override
		public void transitionFrom(GameStage lastStage, Game game) {
			if (lastStage.isCurrent()) {
				game.backupBoard();
			}
			game.setBoard(game.getLevel().getGoal());
		}
	}
}
