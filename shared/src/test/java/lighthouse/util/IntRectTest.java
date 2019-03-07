package lighthouse.util;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

public class IntRectTest {
	@Test
	public void testContains() {
		IntRect a = new IntRect(1, 10, 10, 10);
		assertThat(a, rectContains(2, 11));
		assertThat(a, rectContains(1, 10));
	}
	
	@Test
	public void testIntersects() {
		IntRect a = new IntRect(1, 2, 2, 1);
		IntRect b = new IntRect(2, 2, 2, 1);
		IntRect c = new IntRect(1, 0, 1, -4);
		IntRect d = new IntRect(0, 1, -1, 1);
		assertThat(a, rectIntersects(b));
		assertThat(a, rectIntersects(c));
		assertThat(c, rectIntersects(a)); // symmetry
		assertThat(c, rectIntersects(d)); // include edges
		assertThat(d, not(rectIntersects(b)));
	}
	
	private Matcher<IntRect> rectContains(int x, int y) {
		return new BaseMatcher<IntRect>() {
			@Override
			public boolean matches(Object item) {
				return ((IntRect) item).contains(x, y);
			}
			
			@Override
			public void describeTo(Description description) {
				description.appendText("should contain " + x + ", " + y);
			}
		};
	}
	
	private Matcher<IntRect> rectIntersects(IntRect intersect) {
		return new BaseMatcher<IntRect>() {
			@Override
			public boolean matches(Object item) {
				return ((IntRect) item).intersects(intersect);
			}
			
			@Override
			public void describeTo(Description description) {
				description.appendText("should intersect ")
					.appendValue(intersect);
			}
		};
	}
}
