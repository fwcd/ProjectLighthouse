package lighthouse.ui.board;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.Timer;

import lighthouse.model.Board;
import lighthouse.ui.ViewController;
import lighthouse.ui.board.controller.BoardPlayController;
import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.ui.board.controller.DelegateResponder;
import lighthouse.ui.board.input.BoardInput;
import lighthouse.ui.board.input.BoardKeyInput;
import lighthouse.ui.board.input.BoardMouseInput;
import lighthouse.ui.board.input.BoardXboxControllerInput;
import lighthouse.ui.board.overlay.Animation;
import lighthouse.ui.board.overlay.AnimationState;
import lighthouse.ui.board.transform.CoordinateMapper;
import lighthouse.ui.board.view.BoardView;
import lighthouse.ui.board.view.LighthouseView;
import lighthouse.ui.board.view.LocalBoardView;
import lighthouse.ui.board.viewmodel.BoardViewModel;
import lighthouse.ui.board.viewmodel.LighthouseViewModel;
import lighthouse.util.Updatable;

/**
 * Manages the different board views. It assembles the necessary inputs and
 * views, while still allowing the user of this class to hook custom views.
 */
public class BoardViewController implements ViewController {
	private final JComponent component;

	private final Updatable gameUpdater;
	private BoardViewModel viewModel;
	private LighthouseViewModel lighthouseViewModel;
	private int animationFPS = 60;

	private final List<LighthouseView> lhViews = new ArrayList<>();
	private final List<BoardView> boardViews = new ArrayList<>();
	private final LocalBoardView localView;

	private final DelegateResponder responder;

	public BoardViewController(Board model, CoordinateMapper coordinateMapper, Updatable gameUpdater) {
		this.gameUpdater = gameUpdater;

		viewModel = new BoardViewModel(model);
		lighthouseViewModel = new LighthouseViewModel(viewModel);
		responder = new DelegateResponder(new BoardPlayController(viewModel, gameUpdater));

		// Creates a local view and hooks up the Swing component
		localView = new LocalBoardView(coordinateMapper);
		localView.relayout(model.getColumns(), model.getRows());
		component = localView.getComponent();
		addBoardView(localView);

		// Adds mouse input
		BoardMouseInput mouseInput = new BoardMouseInput(coordinateMapper);
		mouseInput.addResponder(responder);
		localView.addMouseInput(mouseInput);

		// Adds keyboard input
		BoardKeyInput keyInput = new BoardKeyInput();
		keyInput.addResponder(responder);
		localView.addKeyInput(keyInput);

		// Adds controller input
		BoardInput xboxInput = new BoardXboxControllerInput();
		xboxInput.addResponder(responder);
	}

	/** Plays an animation in high and low resolution on the board views. */
	public void play(Animation animation) {
		AnimationState state = new AnimationState(animation);
		localView.addOverlay(state);
		lighthouseViewModel.addOverlay(state);
		
		Timer timer = new Timer(1000 / animationFPS, e -> {
			if (state.hasNextFrame()) {
				state.advance();
				gameUpdater.update();
			} else {
				localView.removeOverlay(state);
				lighthouseViewModel.removeOverlay(state);
				gameUpdater.update();
				((Timer) e.getSource()).stop();
			}
		});
		
		timer.setRepeats(true);
		timer.start();
	}
	
	public void updateModel(Board model) {
		viewModel = new BoardViewModel(model);
		lighthouseViewModel = new LighthouseViewModel(viewModel);
		responder.updateViewModel(viewModel);
	}
	
	public void render() {
		for (BoardView view : boardViews) {
			view.draw(viewModel);
		}
		lighthouseViewModel.renderOverlays();
		for (LighthouseView lhView : lhViews) {
			lhView.draw(lighthouseViewModel);
		}
	}
	
	public void setResponder(BoardResponder responder) {
		this.responder.setDelegate(responder);
	}
	
	public void reset() {
		responder.reset();
	}
	
	public void addLighthouseView(LighthouseView view) {
		lhViews.add(view);
	}
	
	public void addBoardView(BoardView view) {
		boardViews.add(view);
	}
	
	public BoardViewModel getViewModel() {
		return viewModel;
	}
	
	@Override
	public JComponent getComponent() {
		return component;
	}
}
