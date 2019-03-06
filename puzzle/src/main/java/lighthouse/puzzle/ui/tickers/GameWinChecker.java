package lighthouse.puzzle.ui.tickers;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.gameapi.SceneInteractionFacade;
import lighthouse.puzzle.model.PuzzleGameState;
import lighthouse.puzzle.ui.board.viewmodel.BoardStatistics;
import lighthouse.puzzle.ui.modes.GameMode;
import lighthouse.puzzle.ui.perspectives.GamePerspective;
import lighthouse.ui.ObservableStatus;
import lighthouse.ui.scene.viewmodel.graphics.ConfettiAnimation;
import lighthouse.ui.util.Status;
import lighthouse.util.ColorUtils;

public class GameWinChecker implements Ticker {
	private static final Logger LOG = LoggerFactory.getLogger(GameWinChecker.class);
	private final JComponent parent;
	private final PuzzleGameState game;
	private final ObservableStatus status;
	private final BoardStatistics statistics;
	private final SceneInteractionFacade sceneFacade;
	private boolean alreadyWon = false;
	
	public GameWinChecker(
		JComponent parent,
		SceneInteractionFacade sceneFacade,
		PuzzleGameState game,
		ObservableStatus status,
		BoardStatistics statistics
	) {
		this.parent = parent;
		this.sceneFacade = sceneFacade;
		this.status = status;
		this.game = game;
		this.statistics = statistics;
	}
	
	@Override
	public void tick(GameMode mode, GamePerspective perspective) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("Not already won: {}, playing mode: {}, in-game perspective: {}, game won: {}, board not empty: {}",
				!alreadyWon, mode.isPlaying(), perspective.isInGame(), game.isWon(), !game.getBoard().isEmpty()
			);
		}
		
		if (!alreadyWon && mode.isPlaying() && perspective.isInGame() && game.isWon() && !game.getBoard().isEmpty()) {
			alreadyWon = true;
			
			String message = "FINISHED GAME in " + statistics.getMoveCount() + " moves!";
			if (game.getLevel().isTooEasy()) {
				message += " (Pretty easily though, since your goal animationRunner exactly matches your starting animationRunner)";
			} else {
				message += " Hooray!";
			}
			sceneFacade.play(new ConfettiAnimation());
			status.set(new Status("Won", ColorUtils.LIGHT_VIOLET));
			JOptionPane.showMessageDialog(parent, message);
		}
	}
	
	public void reset() {
		alreadyWon = false;
	}
}
