package lighthouse.util;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

public class DoubleRectTest {
	@Test
	public void testContains() {
		DoubleRect a = new DoubleRect(1, 10, 10, 10);
		assertThat(a, rectContains(2, 11));
		assertThat(a, rectContains(1, 10));
	}
	
	@Test
	public void testDoubleersects() {
		DoubleRect a = new DoubleRect(1, 2, 2, 1);
		DoubleRect b = new DoubleRect(2, 2, 2, 1);
		DoubleRect c = new DoubleRect(1, 0, 1, -4);
		DoubleRect d = new DoubleRect(0, 1, -1, 1);
		assertThat(a, rectIntersects(b));
		assertThat(a, rectIntersects(c));
		assertThat(c, rectIntersects(a)); // symmetry
		assertThat(c, rectIntersects(d)); // include edges
		assertThat(d, not(rectIntersects(b)));
	}
	
	private Matcher<DoubleRect> rectContains(int x, int y) {
		return new BaseMatcher<DoubleRect>() {
			@Override
			public boolean matches(Object item) {
				return ((DoubleRect) item).contains(x, y);
			}
			
			@Override
			public void describeTo(Description description) {
				description.appendText("should contain " + x + ", " + y);
			}
		};
	}
	
	private Matcher<DoubleRect> rectIntersects(DoubleRect intersect) {
		return new BaseMatcher<DoubleRect>() {
			@Override
			public boolean matches(Object item) {
				return ((DoubleRect) item).intersects(intersect);
			}
			
			@Override
			public void describeTo(Description description) {
				description.appendText("should intersect ")
					.appendValue(intersect);
			}
		};
	}
}
