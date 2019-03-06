package lighthouse.snake.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import lighthouse.model.GameState;
import lighthouse.util.IntVec;
import lighthouse.util.LhConstants;

public class SnakeGameState implements GameState {
	private final Snake snake;
	private final Set<IntVec> foods = new HashSet<>();
	private final int boardWidth = LhConstants.LIGHTHOUSE_COLS;
	private final int boardHeight = LhConstants.LIGHTHOUSE_ROWS;
	private int score = 0;
	private boolean lost = false;
	
	public SnakeGameState() {
		snake = new Snake(boardWidth, boardHeight);
		spawnFood();
	}
	
	public void spawnFood() {
		IntVec pos = randomPos();
		while (snake.contains(pos)) {
			pos = randomPos();
		}
		foods.add(pos);
	}
	
	public boolean hasLost() {
		return false;
	}
	
	public void move() {
		boolean ate = foods.contains(snake.nextHead());
		if (ate) {
			foods.remove(snake.nextHead());
			spawnFood();
			score++;
		}
		snake.move(!ate);
	}
	
	public int getScore() {
		return score;
	}
	
	private IntVec randomPos() {
		Random r = ThreadLocalRandom.current();
		return new IntVec(r.nextInt(boardWidth), r.nextInt(boardHeight));
	}
	
	public Set<IntVec> getFoods() {
		return foods;
	}
	
	public Snake getSnake() {
		return snake;
	}
	
	/** Saves a level as JSON to a file. */
	@Override
	public void saveLevelTo(Path path) throws IOException {
		// TODO
	}
	
	/** Loads a level from a JSON file. */
	@Override 
	public void loadLevelFrom(InputStream stream) throws IOException {
		// TODO
	}
	
	/** Returns the grid size in the game's own coordinate system. */
	@Override 
	public IntVec getGridSize() {
		return new IntVec(boardWidth, boardHeight);
	}
}
