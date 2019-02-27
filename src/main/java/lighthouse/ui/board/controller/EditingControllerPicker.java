package lighthouse.ui.board.controller;

import lighthouse.model.Board;
import lighthouse.ui.stage.GameStageVisitor;
import lighthouse.ui.stage.GameStages;

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
	public BoardResponder visitStart(GameStages.Start stage) { return new BoardDrawController(board); }
	
	@Override
	public BoardResponder visitCurrent(GameStages.Current stage) { return new BoardDrawController(board); }
	
	@Override
	public BoardResponder visitGoal(GameStages.Goal stage) { return new BoardArrangeController(board); }
}
