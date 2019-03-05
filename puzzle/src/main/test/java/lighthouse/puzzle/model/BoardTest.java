package lighthouse.puzzle.model;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import lighthouse.util.Direction;
import lighthouse.util.IntVec;

public class BoardTest {
	@Test
	@SuppressWarnings("unchecked")
	public void testBoard() {
		// +----+
		// |  XX|
		// |L X |
		// |LL  |
		// |    |
		// |    |
		// |    |
		// +----+
		Board board = boardOf(
			brickOf(new IntVec(2, 1), Direction.UP, Direction.RIGHT),
			brickOf(new IntVec(0, 1), Direction.DOWN, Direction.RIGHT)
		);
		assertThat(board.getBricks(), containsInAnyOrder(
			matchesBrick(new IntVec(2, 0), new IntVec(3, 0), new IntVec(2, 1)),
			matchesBrick(new IntVec(0, 1), new IntVec(0, 2), new IntVec(1, 2))
		));
		
		Board patternA = new Board(2, 2);
		patternA.add(brickOf(new IntVec(0, 0), Direction.DOWN, Direction.RIGHT));
		
		assertTrue("Board should contain bottom-left pattern brick", board.containsPattern(patternA));
		
		Board patternB = new Board(2, 3);
		patternB.add(brickOf(new IntVec(0, 1), Direction.UP, Direction.RIGHT));
		
		assertTrue("Board should contain top-right pattern brick", board.containsPattern(patternB));
	}
	
	private Board boardOf(Brick... bricks) {
		Board board = new Board();
		for (Brick brick : bricks) {
			board.add(brick);
		}
		return board;
	}
	
	private Brick brickOf(IntVec pos, Direction... dirs) {
		return new Brick(pos, Arrays.asList(dirs));
	}
	
	private Matcher<Brick> matchesBrick(IntVec... expectedPositions) {
		return new BaseMatcher<Brick>() {
			@Override
			public boolean matches(Object item) {
				Brick actual = (Brick) item;
				return actual.getAllPositions().equals(Arrays.stream(expectedPositions).collect(Collectors.toSet()));
			}
			
			@Override
			public void describeTo(Description description) {
				description.appendText("expected ")
					.appendValue(Arrays.toString(expectedPositions));
			}
		};
	}
}
