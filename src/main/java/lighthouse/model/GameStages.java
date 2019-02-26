package lighthouse.model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Holds the various stages of a game match.
 */
public class GameStages {
	public static final GameStage START = new Start();
	public static final GameStage CURRENT = new Current();
	public static final GameStage GOAL = new Goal();
	public static final List<GameStage> STAGES = Arrays.asList(START, CURRENT, GOAL);
	
	private GameStages() {}
	
	public static class Start implements GameStage {
		Start() {}
		
		@Override
		public int getIndex() { return 0; }
		
		@Override
		public String getName() { return "Start"; }
		
		@Override
		public void transitionFrom(GameStage lastStage, GameState state) {
			if (lastStage.isCurrent()) {
				state.backupBoard();
			}
			state.setBoard(state.getLevel().getStart());
		}
	
		public <T> T accept(GameStageVisitor<T> visitor) { return visitor.visitStart(this); }
		
		@Override
		public Optional<Board> getBoardFrom(GameState state) { return Optional.of(state.getLevel().getStart()); }
	}
	
	public static class Current implements GameStage {
		Current() {}
		
		@Override
		public int getIndex() { return 1; }
		
		@Override
		public String getName() { return "Current"; }
		
		@Override
		public void transitionFrom(GameStage lastStage, GameState state) {
			if (!lastStage.isCurrent()) {
				state.revertToBackupBoardOr(Board::new);
			}
		}
		
		@Override
		public boolean isCurrent() { return true; }
	
		public <T> T accept(GameStageVisitor<T> visitor) { return visitor.visitCurrent(this); }
		
		@Override
		public Optional<Board> getBoardFrom(GameState state) { return Optional.empty(); }
	}
	
	public static class Goal implements GameStage {
		Goal() {}
		
		@Override
		public int getIndex() { return 2; }
		
		@Override
		public String getName() { return "Goal"; }
		
		@Override
		public void transitionFrom(GameStage lastStage, GameState state) {
			if (lastStage.isCurrent()) {
				state.backupBoard();
			}
			state.setBoard(state.getLevel().getGoal());
		}
	
		public <T> T accept(GameStageVisitor<T> visitor) { return visitor.visitGoal(this); }
		
		@Override
		public Optional<Board> getBoardFrom(GameState state) { return Optional.of(state.getLevel().getGoal()); }
	}
}
