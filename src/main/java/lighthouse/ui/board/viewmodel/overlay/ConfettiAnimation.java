package lighthouse.ui.board.viewmodel.overlay;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ConfettiAnimation implements Animation {

	Random r = new Random();
	
	List<List<OverlayShape>> rows = new ArrayList<>();
	{
		for (int i = 0; i < 6; i++){
			rows.add(new ArrayList<OverlayShape>());
		}
	}

	@Override
	public String getName() { return "Confetti"; }
	
	@Override
	public int getTotalFrames() { return 360; }
	
	@Override
	public List<OverlayShape> getShape(int frame) {
		int depth = frame/30;
		
		if (frame % 30 == 0){
			
			for (int i = 5; i > 0; i--){
				rows.set(i, rows.get(i - 1));
			}
			rows.set(0, Arrays.asList(
			new OverlayRect(r.nextInt(4), 0, 1, 1, new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()), OverlayShading.FILLED),
			new OverlayRect(r.nextInt(4), 0, 1, 1, new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()), OverlayShading.FILLED),
			new OverlayRect(r.nextInt(4), 0, 1, 1, new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()), OverlayShading.FILLED)));
		}
		
		
		
		return rows.stream().flatMap(x -> x.stream()).collect(Collectors.toList());
	}
}
