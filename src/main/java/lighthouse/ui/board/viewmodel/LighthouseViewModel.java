package lighthouse.ui.board.viewmodel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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
 * A class that wraps the board preparing it for the big screen (the Lighthouse)
 * by scaling and translating the grid positions.
 */
public class LighthouseViewModel implements ColorGrid {
	private final int columns;
	private final int rows;
	private final BoardViewModel board;
	private final BufferedImage overlay;
	private final DoubleVecBijection lighthouseSizeToGrid = new Scaling(0.2, 0.5);
	private final DoubleVecBijection lighthousePosToGrid = new Translation(-4, -1).andThen(lighthouseSizeToGrid);

	public LighthouseViewModel(BoardViewModel board) {
		this(board, LhConstants.LIGHTHOUSE_COLS, LhConstants.LIGHTHOUSE_ROWS);
	}

	public LighthouseViewModel(BoardViewModel board, int columns, int rows) {
		this.board = board;
		this.columns = columns;
		this.rows = rows;
		overlay = new BufferedImage(columns, rows, BufferedImage.TYPE_INT_RGB);
	}

	/** Fetches the Lighthouse grid's columns. */
	public int getColumns() {
		return columns;
	}

	/** Fetches the Lighthouse grid's rows. */
	public int getRows() {
		return rows;
	}

	private void renderOverlays() {
		Graphics2D g2d = overlay.createGraphics();
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, columns, rows);
		
		OverlayShapeVisitor renderer = new Graphics2DOverlayRenderer(g2d,
			lighthousePosToGrid.inverse().floor(),
			lighthouseSizeToGrid.inverse().ceil()
		);
		
		for (Overlay overlay : board.getOverlays()) {
			overlay.acceptForAllShapes(renderer);
		}
		
		g2d.dispose();
	}
	
	@Override
	public Color getColorAt(IntVec gridPos) {
		renderOverlays();
		Color overlayColor = new Color(overlay.getRGB(gridPos.getX(), gridPos.getY()));
		return ((overlayColor.getRGB() & 0xFFFFFF) == 0)
			? board.getColorAt(lighthousePosToGrid.apply(gridPos).floor())
			: overlayColor;
	}
	
	@Override
	public Color getColorAt(int x, int y) {
		return getColorAt(new IntVec(x, y));
	}
}
