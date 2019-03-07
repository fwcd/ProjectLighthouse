package lighthouse.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseGameState implements GameState {
	private static final Logger LOG = LoggerFactory.getLogger(BaseGameState.class);
	
	@Override
	public void loadLevelFrom(InputStream stream) throws IOException {
		LOG.warn("Loading levels from an InputStream is not supported for {}", this);
	}
	
	@Override
	public void saveLevelTo(Path path) throws IOException {
		LOG.warn("Saving levels to a Path is not supported for {}", this);
	}
}
