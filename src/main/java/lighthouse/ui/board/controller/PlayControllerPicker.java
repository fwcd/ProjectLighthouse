package lighthouse.ui.board.controller;

import lighthouse.model.Board;
import lighthouse.model.LevelStageVisitor;
import lighthouse.model.LevelStages;
import lighthouse.ui.board.CoordinateMapper;
import lighthouse.ui.board.floating.FloatingContext;

/**
 * A GameStageVisitor that determines the playing
 * controller to pick based on a stage.
 */
public class PlayControllerPicker implements LevelStageVisitor<BoardResponder> {
	private final Board board;
	private final FloatingContext floating;
	private final CoordinateMapper coordinateMapper;
	
	public PlayControllerPicker(Board board, FloatingContext floating, CoordinateMapper coordinateMapper) {
		this.board = board;
		this.floating = floating;
		this.coordinateMapper = coordinateMapper;
	}
	
	@Override
	public BoardResponder visitStart(LevelStages.Start stage) { return NoResponder.INSTANCE; }
	
	@Override
	public BoardResponder visitInGame(LevelStages.InGame stage) { return new BoardPlayController(board, floating, coordinateMapper); }
	
	@Override
	public BoardResponder visitGoal(LevelStages.Goal stage) { return NoResponder.INSTANCE; }
}
