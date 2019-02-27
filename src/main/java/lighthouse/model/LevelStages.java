package lighthouse.model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Holds the various stages of a game match.
 */
public class LevelStages {
	public static final LevelStage START = new Start();
	public static final LevelStage IN_GAME = new InGame();
	public static final LevelStage GOAL = new Goal();
	public static final List<LevelStage> STAGES = Arrays.asList(START, IN_GAME, GOAL);
	
	private LevelStages() {}
	
	public static class Start implements LevelStage {
		Start() {}
		
		@Override
		public int getIndex() { return 0; }
		
		@Override
		public String getName() { return "Start"; }
		
		@Override
		public void transitionFrom(LevelStage lastStage, GameState state) {
			if (lastStage.isInGame()) {
				state.backupBoard();
			}
			state.setBoard(state.getLevel().getStart());
		}
	
		public <T> T accept(LevelStageVisitor<T> visitor) { return visitor.visitStart(this); }
		
		@Override
		public Optional<Board> getBoardFrom(GameState state) { return Optional.of(state.getLevel().getStart()); }
	}
	
	public static class InGame implements LevelStage {
		InGame() {}
		
		@Override
		public int getIndex() { return 1; }
		
		@Override
		public String getName() { return "InGame"; }
		
		@Override
		public void transitionFrom(LevelStage lastStage, GameState state) {
			if (!lastStage.isInGame()) {
				state.revertToBackupBoardOr(Board::new);
			}
		}
		
		@Override
		public boolean isInGame() { return true; }
	
		public <T> T accept(LevelStageVisitor<T> visitor) { return visitor.visitInGame(this); }
		
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
			if (lastStage.isInGame()) {
				state.backupBoard();
			}
			state.setBoard(state.getLevel().getGoal());
		}
	
		public <T> T accept(LevelStageVisitor<T> visitor) { return visitor.visitGoal(this); }
		
		@Override
		public Optional<Board> getBoardFrom(GameState state) { return Optional.of(state.getLevel().getGoal()); }
	}
}
