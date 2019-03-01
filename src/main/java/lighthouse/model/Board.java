package lighthouse.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.grid.ColorGrid;
import lighthouse.util.ColorUtils;
import lighthouse.util.IntVec;
import lighthouse.util.ListenerList;

/**
 * The game board model representing
 * the entire state of the "Schimmler"-game.
 */
public class Board implements Serializable, ColorGrid {
	private static final long serialVersionUID = 6367414981719952292L;
	private static final Logger LOG = LoggerFactory.getLogger(Board.class);
	private int columns;
	private int rows;
	private Set<Brick> bricks = new HashSet<>();
	
	private transient ListenerList<Void> lazyChangeListeners;
	
	/** Creates a new board with the default size of 4x6. */
	public Board() {
		this(4, 6);
	}
	
	/** Constructs a new board of the given size. */
	public Board(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
	}
	
	/** Fetches the board's column count. */
	public int getColumns() { return columns; }
	
	/** Fetches the board's row count. */
	public int getRows() { return rows; }
	
	/** Fetches all bricks on this board. */
	public Collection<Brick> getBricks() { return bricks; }
	
	/** Checks whether the board is empty. */
	public boolean isEmpty() { return bricks.isEmpty(); }
	
	/** Pushes a brick onto the board. */
	public void add(Brick brick) {
		bricks.add(brick);
		getChangeListeners().fire();
	}
	
	/** Replaces a brick. */
	public void replace(Brick oldBrick, Brick newBrick) {
		bricks.remove(oldBrick);
		bricks.add(newBrick);
		getChangeListeners().fire();
	}
	
	/** Removes and returns a brick at a certain position. */
	public Brick removeBrickAt(IntVec gridPos) {
		Iterator<Brick> iterator = bricks.iterator();
		while (iterator.hasNext()) {
			Brick brick = iterator.next();
			if (brick.contains(gridPos)) {
				iterator.remove();
				getChangeListeners().fire();
				return brick;
			}
		}
		return null;
	}
	
	/** Clears the board's contents. */
	public void clear() {
		bricks.clear();
		getChangeListeners().fire();
	}
	
	/** Fetches the cell's color at the specified position. */
	@Override
	public Color getColorAt(IntVec gridPos) {
		GameBlock block = locateBrick(gridPos);
		return (block == null) ? null : block.getColor();
	}
	
	@Override
	public Color getColorAt(int x, int y) {
		return getColorAt(new IntVec(x, y));
	}
	
	public Color getColorOrBlackAt(IntVec gridPos) {
		Color color = getColorAt(gridPos);
		return (color == null) ? Color.BLACK : color;
	}
	
	public Color getColorOrBlackAt(int x, int y) {
		Color color = getColorAt(x, y);
		return (color == null) ? Color.BLACK : color;
	}
	
	public boolean hasBrickAt(IntVec gridPos) {
		return bricks.stream().anyMatch(brick -> brick.contains(gridPos));
	}
	
	/** Finds a brick at a given position and returns null if there is none. */
	public Brick locateBrick(IntVec gridPos) {
		return bricks.stream()
			.filter(brick -> brick.contains(gridPos))
			.findFirst()
			.orElse(null);
	}
	
	/** Performs a given move. */
	public void perform(Move move) {
		replace(move.getOldBrick(), move.getNewBrick());
	}
	
	public Board childBoard(Move move) {
		Board child = copy();
		child.perform(move);
		return child;
	}
	
	/** Encodes this board as an array of columns * rows item.. */
	public double[] encode1D() {
		return IntStream.range(0, columns * rows)
			.mapToObj(i -> getColorOrBlackAt(i % columns, i / columns))
			.mapToDouble(ColorUtils::getBrightnessPercent)
			.toArray();
	}
	
	/** Encodes this board as a 2D array of columns * rows item.. */
	public double[][] encode2D() {
		return IntStream.range(0, columns)
			.mapToObj(x -> IntStream.range(0, rows)
				.mapToObj(y -> getColorOrBlackAt(x, y))
				.mapToDouble(ColorUtils::getBrightnessPercent)
				.toArray())
			.toArray(double[][]::new);
	}
	
	/** Streams the bricks on this board. */
	public Stream<Brick> streamBricks() { return bricks.stream(); }
	
	/** Fetches all possible moves. */
	public Stream<Move> streamPossibleMoves() {
		return bricks.stream()
			.flatMap(this::streamPossibleMovesFor);
	}
	
	/** Fetches possible moves for a single moves. */
	public Stream<Move> streamPossibleMovesFor(Brick brick) {
		return getLimitsFor(brick).entrySet().stream()
			.flatMap(entry -> movesIntoDirection(brick, entry.getKey(), entry.getValue()));
	}
	
	/** Computes the possible moves of a brick into a certain direction. */
	private Stream<Move> movesIntoDirection(Brick brick, Direction dir, int limit) {
		return IntStream.range(0, limit)
			.mapToObj(i -> new Move(brick, brick.movedBy(new IntVec(dir.getDx(), dir.getDy()).scale(limit))));
	}
	
    /** Fetches the limits into each direction for a brick. */
    public Map<Direction, Integer> getLimitsFor(Brick brick) {
        Map<Direction, Integer> limits = new HashMap<>();
        
		limits.put(Direction.UP, Integer.MAX_VALUE);
		limits.put(Direction.DOWN, Integer.MAX_VALUE);
		limits.put(Direction.RIGHT, Integer.MAX_VALUE);
        limits.put(Direction.LEFT, Integer.MAX_VALUE);
        
        List<Edge> edgeList = brick.getEdges();
		for (Direction dir : Direction.values()) {
			edgeList.stream().filter(edge -> edge.getDir().getIndex() == dir.getIndex()).forEach(edge -> {
				IntVec face = brick.getPos().add(edge.getOff()).add(dir);
				int limit = 0;
				while (!hasBrickAt(face) && face.xIn(0, columns) && face.yIn(0, rows)) {
					limit += 1;
					face = face.add(dir);
				}
				if (limits.get(dir) > limit) {
					LOG.debug("Looking {} I found a smaller limit than {}: {}", dir, limits.get(dir), limit);
					limits.put(dir, limit);
				}
				if (limit > 0) {
					edge.setHighlighted(true);
				}
			});
		}
        LOG.debug("Limits: {}", limits);
        
        return limits;
    }
	
	/** Synchronizes this board's bricks with another board. */
	public void bindToUpdates(Board other) {
		other.getChangeListeners().add(v -> {
			bricks.clear();
			bricks.addAll(other.bricks);
			getChangeListeners().fire();
		});
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Board)) return false; 
		Board board = (Board) obj;
		return board.bricks.equals(bricks);
	}
	
	/** Deeply copies this board. */
	public Board copy() {
		Board copied = new Board(columns, rows);
		copied.bricks.addAll(bricks);
		return copied;
	}
	
	/** Fetches the lazily loaded change listeners.  */
	public ListenerList<Void> getChangeListeners() {
		if (lazyChangeListeners == null) {
			// Lazy initialization/reinitalization after deserialization
			lazyChangeListeners = new ListenerList<>("Board.changeListeners");
		}
		return lazyChangeListeners;
	}
}
