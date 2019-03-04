package lighthouse.ui.board.viewmodel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import lighthouse.model.GameBlock;
import lighthouse.model.grid.ColorGrid;
import lighthouse.ui.board.viewmodel.overlay.Graphics2DOverlayRenderer;
import lighthouse.ui.board.viewmodel.overlay.Overlay;
import lighthouse.ui.board.viewmodel.overlay.OverlayShapeVisitor;
import lighthouse.util.IntVec;
import lighthouse.util.LhConstants;
import lighthouse.util.transform.DoubleVecBijection;
import lighthouse.util.transform.Scaling;
import lighthouse.util.transform.Translation;

/**
 * A class that wraps the grid preparing it for the big screen (the Lighthouse)
 * by scaling and translating the grid positions.
 */
public class LighthouseViewModel implements ColorGrid {
	private final int columns;
	private final int rows;
	private final BoardViewModel board;
	private final BufferedImage image;
	private final DoubleVecBijection lighthouseSizeToGrid = new Scaling(0.2, 0.5);
	private final DoubleVecBijection lighthousePosToGrid = new Translation(-4, -1).andThen(lighthouseSizeToGrid);
	private boolean antialiasingEnabled = true;
	
	public LighthouseViewModel(BoardViewModel board) {
		this(board, LhConstants.LIGHTHOUSE_COLS, LhConstants.LIGHTHOUSE_ROWS);
	}

	public LighthouseViewModel(BoardViewModel board, int columns, int rows) {
		this.board = board;
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
				GameBlock block = board.locateBlock(lighthousePosToGrid.apply(new IntVec(x, y)));
				Color color = Color.BLACK;
				
				if (block != null) {
					color = block.getColor();
					if (board.isSelected(block)) {
						color = color.brighter().brighter();
					}
				}
				
				imgBuffer[(y * columns) + x] = color.getRGB();
			}
		}
		
		Graphics2D g2d = image.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antialiasingEnabled ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
		
		OverlayShapeVisitor renderer = new Graphics2DOverlayRenderer(g2d,
			lighthousePosToGrid.inverse().floor(),
			lighthouseSizeToGrid.inverse().ceil()
		);
		
		for (Overlay rendered : board.getOverlays()) {
			rendered.acceptForAllShapes(renderer);
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
	
	public BoardViewModel getBoard() {
		return board;
	}
}
