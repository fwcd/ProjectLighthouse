package lighthouse.puzzle.ui.perspectives;

import java.util.Arrays;
import java.util.List;

/**
 * Stores the default perspectives in their correct order.
 */
public class CommonPerspective {
	public static final List<GamePerspective> PERSPECTIVES = Arrays.asList(
		StartPerspective.INSTANCE,
		InGamePerspective.INSTANCE,
		GoalPerspective.INSTANCE
	);
	
	private CommonPerspective() {}
	
	public static GamePerspective byIndex(int index) { return PERSPECTIVES.get(index); }
}
