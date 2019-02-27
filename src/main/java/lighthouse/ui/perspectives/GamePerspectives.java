package lighthouse.ui.perspectives;

import java.util.Arrays;
import java.util.List;

/**
 * Lists the default perspectives in their correct order.
 */
public class GamePerspectives {
	public static final List<GamePerspective> PERSPECTIVES = Arrays.asList(
		StartPerspective.INSTANCE,
		InGamePerspective.INSTANCE,
		GoalPerspective.INSTANCE
	);
	
	private GamePerspectives() {}
}
