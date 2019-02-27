package lighthouse.ui.board.controller;

import lighthouse.model.Board;
import lighthouse.model.LevelStageVisitor;
import lighthouse.model.LevelStages;
import lighthouse.ui.board.floating.FloatingContext;

/**
 * A GameStageVisitor that determines the playing
 * controller to pick based on a stage.
 */
public class PlayControllerPicker implements LevelStageVisitor<BoardResponder> {
	private final Board board;
	private final FloatingContext floating;
	
	public PlayControllerPicker(Board board, FloatingContext floating) {
		this.board = board;
		this.floating = floating;
	}
	
	@Override
	public BoardResponder visitStart(LevelStages.Start stage) { return NoResponder.INSTANCE; }
	
	@Override
	public BoardResponder visitInGame(LevelStages.InGame stage) { return new BoardPlayController(board, floating); }
	
	@Override
	public BoardResponder visitGoal(LevelStages.Goal stage) { return NoResponder.INSTANCE; }
}
