package lighthouse.ui.scene.viewmodel.graphics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConfettiAnimation implements Animation {
	private final Random r = new Random();
	
	private final List<List<Color>> rows = new ArrayList<>();
	private final List<List<Integer>> rowsX = new ArrayList<>();

	@Override
	public String getName() { return "Confetti"; }
	
	@Override
	public int getTotalFrames() { return 360; }
	
	@Override
	public List<SceneShape> getShape(int frame) {
		// int depth = frame/30;
		List<SceneShape> shapes = new ArrayList<>();

		if (frame % 30 == 0) {
			rows.add(0, new ArrayList<>());
			rows.get(0).add(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()));
			rows.get(0).add(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()));
			rows.get(0).add(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()));
			rows.get(0).add(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()));
			rows.get(0).add(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()));
			rowsX.add(0, new ArrayList<>());
			rowsX.get(0).add(r.nextInt(4));
			rowsX.get(0).add(r.nextInt(4));
			rowsX.get(0).add(r.nextInt(4));
			rowsX.get(0).add(r.nextInt(4));
			rowsX.get(0).add(r.nextInt(4));
		}
		
		double dFrame = (double) frame;
		for (int i = 0; i < rows.size(); i++) {
			shapes.add(new SceneRect(rowsX.get(i).get(0) + 0.5, i + (dFrame%30)/30, 0.5, 0.5, rows.get(i).get(0), Shading.FILLED));
			shapes.add(new SceneRect(rowsX.get(i).get(1) + 0.5, i + (dFrame%30)/30, 0.5, 0.5, rows.get(i).get(1), Shading.FILLED));
			shapes.add(new SceneRect(rowsX.get(i).get(2) + 0.5, i + (dFrame%30)/30, 0.5, 0.5, rows.get(i).get(2), Shading.FILLED));
			shapes.add(new SceneRect(rowsX.get(i).get(3), i + (dFrame%30)/30, 0.5, 0.5, rows.get(i).get(3), Shading.FILLED));
			shapes.add(new SceneRect(rowsX.get(i).get(4), i + (dFrame%30)/30, 0.5, 0.5, rows.get(i).get(4), Shading.FILLED));
		}
		
		return shapes;
	}
}
