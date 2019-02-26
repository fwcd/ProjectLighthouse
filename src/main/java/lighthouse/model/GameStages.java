package lighthouse.model;

import java.util.Arrays;
import java.util.List;

/**
 * Holds the various stages of a game match.
 */
public class GameStages {
	public static final List<GameStage> STAGES = Arrays.asList(new Start(), new Current(), new Goal());
	
	public static class Start implements GameStage {
		@Override
		public int getIndex() { return 0; }
		
		@Override
		public String getName() { return "Start"; }
		
		@Override
		public void navigateToIn(Game game) {
			game.backupBoard();
			game.setBoard(game.getLevel().getStart());
		}
	}
	
	public static class Current implements GameStage {
		@Override
		public int getIndex() { return 1; }
		
		@Override
		public String getName() { return "Current"; }
		
		@Override
		public void navigateToIn(Game game) {
			game.revertToBackupBoardOr(Board::new);
		}
	}
	
	public static class Goal implements GameStage {
		@Override
		public int getIndex() { return 2; }
		
		@Override
		public String getName() { return "Goal"; }
		
		@Override
		public void navigateToIn(Game game) {
			game.backupBoard();
			game.setBoard(game.getLevel().getGoal());
		}
	}
}
