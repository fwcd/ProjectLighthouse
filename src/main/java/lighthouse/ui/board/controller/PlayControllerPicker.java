package lighthouse.ui.board.controller;

import lighthouse.model.Board;
import lighthouse.ui.stage.LevelStageVisitor;
import lighthouse.ui.stage.LevelStages;

/**
 * A GameStageVisitor that determines the playing
 * controller to pick based on a stage.
 */
public class PlayControllerPicker implements LevelStageVisitor<BoardResponder> {
	private final Board board;
	
	public PlayControllerPicker(Board board) {
		this.board = board;
	}
	
	@Override
	public BoardResponder visitStart(LevelStages.Start stage) { return NoResponder.INSTANCE; }
	
	@Override
	public BoardResponder visitCurrent(LevelStages.Current stage) { return new BoardPlayController(board); }
	
	@Override
	public BoardResponder visitGoal(LevelStages.Goal stage) { return NoResponder.INSTANCE; }
}
