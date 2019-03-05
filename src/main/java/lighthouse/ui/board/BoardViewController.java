package lighthouse.ui.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.swing.JComponent;
import javax.swing.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Board;
import lighthouse.ui.ViewController;
import lighthouse.ui.board.controller.BoardPlayController;
import lighthouse.ui.board.controller.BoardResponder;
import lighthouse.ui.board.controller.DelegateResponder;
import lighthouse.ui.board.debug.AnimationTracker;
import lighthouse.ui.board.input.BoardKeyInput;
import lighthouse.ui.board.input.BoardMouseInput;
import lighthouse.ui.board.view.BoardView;
import lighthouse.ui.board.view.LighthouseView;
import lighthouse.ui.board.view.LocalBoardView;
import lighthouse.ui.board.viewmodel.BoardStatistics;
import lighthouse.ui.board.viewmodel.BoardViewModel;
import lighthouse.ui.board.viewmodel.LighthouseViewModel;
import lighthouse.ui.board.viewmodel.overlay.Animation;
import lighthouse.util.Updatable;
import lighthouse.util.transform.DoubleVecBijection;

/**
 * Manages the different board views. It assembles the necessary inputs and
 * views, while still allowing the user of this class to hook custom views.
 */
public class BoardViewController implements ViewController {
	private static final Logger LOG = LoggerFactory.getLogger(BoardViewController.class);
	private final JComponent component;

	private final Updatable gameUpdater;
	
	private BoardViewModel viewModel;
	private LighthouseViewModel lighthouseViewModel;
	
	private final List<LighthouseView> lhViews = new ArrayList<>();
	private final List<BoardView> boardViews = new ArrayList<>();
	private final LocalBoardView localView;
	private final DelegateResponder responder;
	
	private final int animationFPS = 60;
	private final BoardAnimationRunner animationRunner;
	private boolean hasRunningTransitionTimer = false;

	public BoardViewController(Board model, List<Board> blockedStates, DoubleVecBijection gridToPixels, Updatable gameUpdater) {
		this.gameUpdater = gameUpdater;

		viewModel = new BoardViewModel(model, blockedStates);
		lighthouseViewModel = new LighthouseViewModel(viewModel);
		animationRunner = new BoardAnimationRunner(viewModel, animationFPS, gameUpdater);
		responder = new DelegateResponder(new BoardPlayController(viewModel, gameUpdater, animationRunner));

		// Creates a local view and hooks up the Swing component
		localView = new LocalBoardView(gridToPixels);
		localView.relayout(model.getColumns(), model.getRows());
		component = localView.getComponent();
		addBoardView(localView);

		// Adds mouse input
		BoardMouseInput mouseInput = new BoardMouseInput(gridToPixels);
		mouseInput.addResponder(responder);
		localView.addMouseInput(mouseInput);

		// Adds keyboard input
		BoardKeyInput keyInput = new BoardKeyInput();
		keyInput.addResponder(responder);
		localView.addKeyInput(keyInput);
	}

	/** Plays an animation in high and low resolution on the board views. */
	public void play(Animation animation) {
		animationRunner.play(animation);
	}
	
	public CompletableFuture<Void> play(List<? extends Board> boards, int delayMs) {
		Iterator<? extends Board> iterator = boards.iterator();
		CompletableFuture<Void> future = new CompletableFuture<>();
		Timer timer = new Timer(delayMs, e -> {
			if (iterator.hasNext()) {
				LOG.trace("Updating");
				updateModel(iterator.next());
				gameUpdater.update();
			} else {
				((Timer) e.getSource()).stop();
				future.complete(null);
			}
		});
		
		timer.setRepeats(true);
		timer.start();
		return future;
	}
	
	public void updateModel(Board model) {
		// Preserve the statistics instance across viewmodel changes
		BoardStatistics statistics = (viewModel == null) ? new BoardStatistics() : viewModel.getStatistics();
		statistics.reset();
		viewModel.transitionTo(model);
		updateTransitionTimer();
	}
	
	public void render() {
		updateTransitionTimer();
		for (BoardView view : boardViews) {
			view.draw(viewModel);
		}
		for (LighthouseView lhView : lhViews) {
			lhView.draw(lighthouseViewModel);
		}
	}
	
	private void updateTransitionTimer() {
		if (!hasRunningTransitionTimer) {
			hasRunningTransitionTimer = true;
			
			Timer timer = new Timer(1000 / animationFPS, e -> {
				if (viewModel.hasNextTransitionFrame()) {
					LOG.trace("Rendering next transition frame");
					viewModel.nextTransitionFrame();
					render();
				} else {
					((Timer) e.getSource()).stop();
					hasRunningTransitionTimer = false;
				}
			});
			timer.setRepeats(true);
			timer.start();
		}
	}
	
	public void setResponder(BoardResponder responder) { this.responder.setDelegate(responder); }
	
	public BoardResponder getResponder() { return responder; }
	
	public void reset() { responder.reset(); }
	
	public void addLighthouseView(LighthouseView view) { lhViews.add(view); }
	
	public void addBoardView(BoardView view) { boardViews.add(view); }
	
	public BoardViewModel getViewModel() { return viewModel; }
	
	public AnimationTracker getAnimationTracker() { return animationRunner.getTracker(); }
	
	@Override
	public JComponent getComponent() { return component; }
}
