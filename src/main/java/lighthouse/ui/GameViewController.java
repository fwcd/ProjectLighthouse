package lighthouse.ui;

import javax.swing.JComponent;

import lighthouse.model.Game;
import lighthouse.model.Level;
import lighthouse.model.Status;
import lighthouse.ui.board.BoardViewController;
import lighthouse.ui.board.controller.BoardPlayController;
import lighthouse.ui.board.controller.EditingControllerPicker;
import lighthouse.ui.stage.LevelStage;
import lighthouse.ui.stage.LevelStages;
import lighthouse.util.ColorUtils;
import lighthouse.util.Listener;
import lighthouse.util.ListenerList;

/**
 * Manages the game board view and the current game state.
 */
public class GameViewController implements ViewController {
	private final Game model;
	private final BoardViewController board;
	
	private LevelStage stage = LevelStages.CURRENT;
	
	private final ListenerList<LevelStage> stageListeners = new ListenerList<>();
	private final Listener<LevelStage> playControlListener;
	private final Listener<LevelStage> editControlListener;
	
	public GameViewController(Game model) {
		this.model = model;
		
		board = new BoardViewController(model.getState().getBoard());
		model.getState().getBoardListeners().add(board::updateModel);
		
		editControlListener = stage -> {
			board.setResponder(stage.accept(new EditingControllerPicker(model.getState().getBoard())));
		};
		playControlListener = stage -> {
			// TODO: Multiple play controllers
			board.setResponder(new BoardPlayController(model.getState().getBoard()));
		};
		
		model.getState().getLevelListeners().add(level -> {
			level.getGoal().bindToUpdates(level.getStart());
			stage.transitionFrom(stage, model.getState());
		});
		Level initialLevel = model.getState().getLevel();
		initialLevel.getGoal().bindToUpdates(initialLevel.getStart());
		
		// Initially enter game mode
		play();
	}
	
	/** Switches to playing mode. */
	public void play() {
		model.setStatus(new Status("Playing", ColorUtils.LIGHT_GREEN));
		board.setResponder(new BoardPlayController(model.getState().getBoard()));
		stageListeners.remove(editControlListener);
		stageListeners.add(playControlListener);
		playControlListener.on(stage);
	}
	
	/** Switches to editing mode. */
	public void edit() {
		model.setStatus(new Status("Editing", ColorUtils.LIGHT_ORANGE));
		stageListeners.add(editControlListener);
		stageListeners.remove(playControlListener);
		editControlListener.on(stage);
	}
	
	public void reset() {
		board.reset();
	}
	
	public void switchToStage(LevelStage newStage) {
		if (stage != null && newStage.getIndex() != stage.getIndex()) {
			newStage.transitionFrom(stage, model.getState());
		}
		stage = newStage;
		stageListeners.fire(newStage);
	}
	
	public BoardViewController getBoard() { return board; }
	
	/** Fetches the currently viewed stage. */
	public LevelStage getStage() { return stage; }
	
	public Game getModel() { return model; }
	
	public ListenerList<LevelStage> getStageListeners() { return stageListeners; }
	
	@Override
	public JComponent getComponent() { return board.getComponent(); }
}
