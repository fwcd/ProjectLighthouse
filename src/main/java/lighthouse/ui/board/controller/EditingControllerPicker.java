package lighthouse.ui.board.controller;

import lighthouse.model.Board;
import lighthouse.model.GameStageVisitor;
import lighthouse.model.GameStages.Current;
import lighthouse.model.GameStages.Goal;
import lighthouse.model.GameStages.Start;

/**
 * A GameStageVisitor that determines the editing
 * controller to pick based on a stage.
 */
public class EditingControllerPicker implements GameStageVisitor<BoardResponder> {
	private final Board board;
	
	public EditingControllerPicker(Board board) {
		this.board = board;
	}
	
	@Override
	public BoardResponder visitStart(Start stage) { return new BoardDrawController(board); }
	
	@Override
	public BoardResponder visitCurrent(Current stage) { return new BoardDrawController(board); }
	
	@Override
	public BoardResponder visitGoal(Goal stage) { return new BoardArrangeController(board); }
}
