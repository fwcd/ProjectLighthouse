package lighthouse.ui.stage;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lighthouse.model.Board;
import lighthouse.model.GameState;

/**
 * Holds the various stages of a game match.
 */
public class LevelStages {
	public static final LevelStage START = new Start();
	public static final LevelStage CURRENT = new Current();
	public static final LevelStage GOAL = new Goal();
	public static final List<LevelStage> STAGES = Arrays.asList(START, CURRENT, GOAL);
	
	private LevelStages() {}
	
	public static class Start implements LevelStage {
		Start() {}
		
		@Override
		public int getIndex() { return 0; }
		
		@Override
		public String getName() { return "Start"; }
		
		@Override
		public void transitionFrom(LevelStage lastStage, GameState state) {
			if (lastStage.isCurrent()) {
				state.backupBoard();
			}
			state.setBoard(state.getLevel().getStart());
		}
	
		public <T> T accept(LevelStageVisitor<T> visitor) { return visitor.visitStart(this); }
		
		@Override
		public Optional<Board> getBoardFrom(GameState state) { return Optional.of(state.getLevel().getStart()); }
	}
	
	public static class Current implements LevelStage {
		Current() {}
		
		@Override
		public int getIndex() { return 1; }
		
		@Override
		public String getName() { return "Current"; }
		
		@Override
		public void transitionFrom(LevelStage lastStage, GameState state) {
			if (!lastStage.isCurrent()) {
				state.revertToBackupBoardOr(Board::new);
			}
		}
		
		@Override
		public boolean isCurrent() { return true; }
	
		public <T> T accept(LevelStageVisitor<T> visitor) { return visitor.visitCurrent(this); }
		
		@Override
		public Optional<Board> getBoardFrom(GameState state) { return Optional.empty(); }
	}
	
	public static class Goal implements LevelStage {
		Goal() {}
		
		@Override
		public int getIndex() { return 2; }
		
		@Override
		public String getName() { return "Goal"; }
		
		@Override
		public void transitionFrom(LevelStage lastStage, GameState state) {
			if (lastStage.isCurrent()) {
				state.backupBoard();
			}
			state.setBoard(state.getLevel().getGoal());
		}
	
		public <T> T accept(LevelStageVisitor<T> visitor) { return visitor.visitGoal(this); }
		
		@Override
		public Optional<Board> getBoardFrom(GameState state) { return Optional.of(state.getLevel().getGoal()); }
	}
}
