package lighthouse.spaceinvaders.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import lighthouse.model.GameState;
import lighthouse.util.IntVec;
import lighthouse.util.LighthouseConstants;

public class SpaceInvadersGameState implements GameState {
    private final int boardWidth;
    private final int boardHeight;
    
    public SpaceInvadersGameState() {
        this(LighthouseConstants.COLS, LighthouseConstants.ROWS);
    }
    
    public SpaceInvadersGameState(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }

    @Override
    public IntVec getGridSize() { return new IntVec(boardWidth, boardHeight); }
    
    @Override
    public void loadLevelFrom(InputStream stream) throws IOException {
        // TODO
    }
    
    @Override
    public void saveLevelTo(Path path) throws IOException {
        // TODO
    }
}
