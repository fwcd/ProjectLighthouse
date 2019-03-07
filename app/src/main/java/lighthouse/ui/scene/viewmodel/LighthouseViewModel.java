package lighthouse.ui.scene.viewmodel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import lighthouse.model.grid.ColorGrid;
import lighthouse.ui.scene.viewmodel.graphics.Graphics2DSceneRenderer;
import lighthouse.ui.scene.viewmodel.graphics.SceneLayer;
import lighthouse.ui.scene.viewmodel.graphics.SceneShapeVisitor;
import lighthouse.ui.scene.viewmodel.graphics.SceneViewModel;
import lighthouse.util.IntVec;
import lighthouse.util.LighthouseConstants;
import lighthouse.util.transform.DoubleVecBijection;

/**
 * A class that wraps the grid preparing it for the big screen (the Lighthouse)
 * by scaling and translating the grid positions.
 */
public class LighthouseViewModel implements ColorGrid {
	private final int columns;
	private final int rows;
	private final SceneViewModel scene;
	private final BufferedImage image;
	// TODO: Move these to the puzzle module:
	private final DoubleVecBijection lighthouseSizeToGrid;
	private final DoubleVecBijection lighthousePosToGrid;
	private boolean antialiasingEnabled = true;
	
	public LighthouseViewModel(SceneViewModel scene, DoubleVecBijection lighthouseSizeToGrid, DoubleVecBijection lighthousePosToGrid) {
		this(scene, LighthouseConstants.COLS, LighthouseConstants.ROWS, lighthouseSizeToGrid, lighthousePosToGrid);
	}

	public LighthouseViewModel(SceneViewModel scene, int columns, int rows, DoubleVecBijection lighthouseSizeToGrid, DoubleVecBijection lighthousePosToGrid) {
		this.lighthousePosToGrid = lighthousePosToGrid;
		this.lighthouseSizeToGrid = lighthouseSizeToGrid;
		this.scene = scene;
		this.columns = columns;
		this.rows = rows;
		image = new BufferedImage(columns, rows, BufferedImage.TYPE_INT_RGB);
	}

	/** Fetches the Lighthouse grid's columns. */
	public int getColumns() {
		return columns;
	}

	/** Fetches the Lighthouse grid's rows. */
	public int getRows() {
		return rows;
	}

	public void render() {
		int[] imgBuffer = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				imgBuffer[(y * columns) + x] = 0;
			}
		}
		
		Graphics2D g2d = image.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antialiasingEnabled ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
		
		SceneShapeVisitor renderer = new Graphics2DSceneRenderer(g2d,
			lighthousePosToGrid.inverse().floor(),
			lighthouseSizeToGrid.inverse().ceil()
		);
		
		for (SceneLayer layer : scene) {
			if (layer.hasBackground()) {
				g2d.setColor(layer.getBackground());
				g2d.fillRect(0, 0, columns, rows);
			}
			
			layer.acceptForAllShapes(renderer);
		}
		
		g2d.dispose();
	}
	
	@Override
	public Color getColorAt(IntVec gridPos) {
		return new Color(image.getRGB(gridPos.getX(), gridPos.getY()));
	}
	
	@Override
	public Color getColorAt(int x, int y) {
		return getColorAt(new IntVec(x, y));
	}
	
	public BufferedImage getImage() {
		return image;
	}
}
