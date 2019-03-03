package lighthouse.ui.board.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Brick;
import lighthouse.model.Edge;
import lighthouse.model.GameBlock;
import lighthouse.ui.board.input.BoardKeyInput;
import lighthouse.ui.board.input.BoardMouseInput;
import lighthouse.ui.board.viewmodel.BoardEditState;
import lighthouse.ui.board.viewmodel.BoardViewModel;
import lighthouse.ui.board.viewmodel.overlay.Graphics2DOverlayRenderer;
import lighthouse.ui.board.viewmodel.overlay.Overlay;
import lighthouse.ui.board.viewmodel.overlay.OverlayShapeVisitor;
import lighthouse.util.ArrayUtils;
import lighthouse.util.IntVec;
import lighthouse.util.transform.DoubleVecBijection;

/**
 * A local high-resolution (Swing-based) view of the GameBoard.
 */
public class LocalBoardView implements BoardView {
	private static final Logger LOG = LoggerFactory.getLogger(LocalBoardView.class);
	
	private final Color background = Color.WHITE;
	private final Color gridLineColor = Color.LIGHT_GRAY;
	private final Color selectionColor = Color.LIGHT_GRAY;
	private final int gridDashLength = 3;
	private final int gridLineThickness = 1;
	private boolean drawGrid = true;
	private boolean drawBackground = true;
	private EdgeDrawMode edgeDrawMode = EdgeDrawMode.NONE;
	private double activeBrickScale = 0.6;
	private double placedBrickScale = 0.8;
	private double selectedBrickScale = 0.95;
	
	private final JComponent component;
	private final DoubleVecBijection gridToPixels;
	private BoardViewModel viewModel = null;
	
	public LocalBoardView(DoubleVecBijection gridToPixels) {
		this.gridToPixels = gridToPixels;
		
		component = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				render((Graphics2D) g, getSize());
			}
		};
	}
	
	public static enum EdgeDrawMode {
		NONE, HIGHLIGHTED_ONLY, ALL;
	}
	
	@Override
	public void draw(BoardViewModel viewModel) {
		this.viewModel = viewModel;
		// Redraw the component
		repaint();
	}
	
	public void repaint() {
		SwingUtilities.invokeLater(component::repaint);
	}
	
	public void relayout(int columns, int rows) {
		IntVec cellSize = gridToPixels.apply(IntVec.ONE_ONE).floor();
		int width = cellSize.getX() * columns;
		int height = cellSize.getY() * rows;
		// Add one to render the right and bottom grid borders
		component.setPreferredSize(new Dimension(width + 1, height + 1));
	}
	
	/** Renders the model grid to the Swing Graphics canvas. */
	private void render(Graphics2D g2d, Dimension canvasSize) {
		int canvasWidth = (int) canvasSize.getWidth();
		int canvasHeight = (int) canvasSize.getHeight();
		
		if (drawBackground) {
			g2d.setColor(background);
			g2d.fillRect(0, 0, canvasWidth, canvasHeight);
		}

		if (viewModel == null) {
			g2d.setFont(g2d.getFont().deriveFont(18F)); // Make font larger
			g2d.drawString("No Board model drawn", 30, 30);
		} else {
			IntVec cellSize = getCellSize();
			
			// Draw the background grid
			if (drawGrid) {
				float[] dash = {gridDashLength};
				g2d.setColor(gridLineColor);
				g2d.setStroke(new BasicStroke(gridLineThickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0));
				
				for (int y = 0; y < canvasHeight; y += cellSize.getY()) {
					g2d.drawLine(0, y, canvasWidth, y);
				}
				
				for (int x = 0; x < canvasWidth; x += cellSize.getX()) {
					g2d.drawLine(x, 0, x, canvasHeight);
				}
			}
			
			// Draw the board's bricks
			for (Brick brick : viewModel.getBricks()) {
				LOG.debug("Rendering {}", brick);
				Color color = brick.getColor();
				
				if (viewModel.isSelected(brick)) {
					renderBlock(g2d, brick, selectedBrickScale, selectionColor);
				}
				
				renderBlock(g2d, brick, placedBrickScale, color);
				
				if (edgeDrawMode != EdgeDrawMode.NONE) {
					renderEdges(g2d, gridToPixels.apply(brick.getPos()).floor(), brick.getEdges(), placedBrickScale);
				}
			}
			
			// Draw the editing state
			BoardEditState editState = viewModel.getEditState();
			GameBlock brickInProgress = editState.getBrickInProgress();
			
			if (brickInProgress != null) {
				renderBlock(g2d, brickInProgress, activeBrickScale);
			}
			
			// Draw the overlays
			g2d.setStroke(new BasicStroke(3.0F));
			OverlayShapeVisitor overlayRenderer = new Graphics2DOverlayRenderer(g2d, gridToPixels.floor());
			
			for (Overlay overlay : viewModel.getOverlays()) {
				overlay.acceptForAllShapes(overlayRenderer);
			}
		}
	}
	
	private void renderEdges(Graphics2D g2d, IntVec pixelPos, List<? extends Edge> edges, double blockScale) {
		renderEdges(g2d, pixelPos, edges, blockScale, Color.ORANGE, 2.0F);
	}
	
	private void renderEdges(Graphics2D g2d, IntVec pixelPos, List<? extends Edge> edges, double blockScale, Color color, float thickness) {
		for (Edge edge : edges) {
			if (edgeDrawMode == EdgeDrawMode.ALL || (edgeDrawMode == EdgeDrawMode.HIGHLIGHTED_ONLY && edge.isHighlighted())) {
				renderEdge(g2d, pixelPos, edge, blockScale, color, thickness);
			}
		}
	}
	
	private void renderEdge(Graphics2D g2d, IntVec pixelPos, Edge edge, double scale, Color color, float thickness) {
		g2d.setStroke(new BasicStroke(thickness));
		g2d.setColor(color);
		
		IntVec cellSize = getCellSize();
		IntVec scaledCellSize = cellSize.scale(scale).castToInt();
		IntVec cornerOffset = cellSize.sub(scaledCellSize).scale(0.5).castToInt();
		IntVec innerTopLeft = pixelPos.add(gridToPixels.apply(edge.getOff()).floor()).add(cornerOffset);
		IntVec innerBottomRight = innerTopLeft.add(scaledCellSize);
		
		switch (edge.getDir()) {
			case UP:
				g2d.drawLine(innerTopLeft.getX(), innerTopLeft.getY(), innerBottomRight.getX(), innerTopLeft.getY());
				break;
			case DOWN:
				g2d.drawLine(innerTopLeft.getX(), innerBottomRight.getY(), innerBottomRight.getX(), innerBottomRight.getY());
				break;
			case RIGHT:
				g2d.drawLine(innerBottomRight.getX(), innerTopLeft.getY(), innerBottomRight.getX(), innerBottomRight.getY());
				break;
			case LEFT:
				g2d.drawLine(innerTopLeft.getX(), innerTopLeft.getY(), innerTopLeft.getX(), innerBottomRight.getY());
				break;
			default: throw new IllegalArgumentException("Will never happen.");
		}
	}
	
	private void renderBlock(Graphics2D g2d, GameBlock block, double blockScale) {
		renderBlock(g2d, block, blockScale, block.getColor());
	}
	
	private void renderBlock(Graphics2D g2d, GameBlock block, double blockScale, Color color) {
		renderBlock(g2d, gridToPixels.apply(block.getPos()).floor(), block, blockScale, color);
	}
	
	private void renderBlock(Graphics2D g2d, IntVec pixelPos, GameBlock block, double blockScale, Color color) {
		IntVec cellSize = getCellSize();
		IntVec[][] fragments = block.to2DArray();
		
		g2d.setColor(color);
		
		for (int y = 0; y < fragments.length; y++) {
			for (int x = 0; x < fragments[y].length; x++) {
				if (fragments[y][x] != null) {
					renderCell(g2d, gridToPixels.apply(fragments[y][x]).floor().add(pixelPos), fragments, new IntVec(x, y), cellSize, blockScale);
				}
			}
		}
	}
	
	@SuppressWarnings("unused") // For debugging
	private void markPoint(Graphics2D g2d, IntVec pos, Color color) {
		Color tmpColor = g2d.getColor();
		g2d.setColor(color);
		g2d.fillOval(pos.getX() - 4, pos.getY() - 4, 8, 8);
		g2d.setColor(tmpColor);
	}
	
	private void renderCell(Graphics2D g2d, IntVec topLeft, IntVec[][] fragments, IntVec cellRelPos, IntVec cellSize, double scale) {
		int relY = cellRelPos.getY();
		int relX = cellRelPos.getX();
		
		if (Math.abs(scale - 1.0) < 0.01) {
			// Use simpler rendering algorithm if the scale is approximately one
			g2d.fillRect(topLeft.getX(), topLeft.getY(), cellSize.getX(), cellSize.getY());
		} else {
			IntVec bottomRight = topLeft.add(cellSize);
			IntVec scaledCellSize = cellSize.scale(scale).castToInt();
			IntVec cornerOffset = cellSize.sub(scaledCellSize).scale(0.5).castToInt();
			IntVec innerTopLeft = topLeft.add(cornerOffset);
			
			g2d.fillRect(innerTopLeft.getX(), innerTopLeft.getY(), scaledCellSize.getX(), scaledCellSize.getY());
			
			boolean neighborTopLeft = ArrayUtils.getOr(null, fragments, relY - 1, relX - 1) != null;
			boolean neighborAbove = ArrayUtils.getOr(null, fragments, relY - 1, relX) != null;
			boolean neighborTopRight = ArrayUtils.getOr(null, fragments, relY - 1, relX + 1) != null;
			boolean neighborLeft = ArrayUtils.getOr(null, fragments, relY, relX - 1) != null;
			boolean neighborRight = ArrayUtils.getOr(null, fragments, relY, relX + 1) != null;
			boolean neighborBottomLeft = ArrayUtils.getOr(null, fragments, relY + 1, relX - 1) != null;
			boolean neighborBelow = ArrayUtils.getOr(null, fragments, relY + 1, relX) != null;
			boolean neighborBottomRight = ArrayUtils.getOr(null, fragments, relY + 1, relX + 1) != null;
			
			// Draw corners
			if (neighborTopLeft && neighborAbove && neighborLeft)      g2d.fillRect(topLeft.getX(), topLeft.getY(), cornerOffset.getX(), cornerOffset.getY());
			if (neighborTopRight && neighborAbove && neighborRight)    g2d.fillRect(bottomRight.getX() - cornerOffset.getX(), topLeft.getY(), cornerOffset.getX(), cornerOffset.getY());
			if (neighborBottomLeft && neighborBelow && neighborLeft)   g2d.fillRect(topLeft.getX(), bottomRight.getY() - cornerOffset.getY(), cornerOffset.getX(), cornerOffset.getY());
			if (neighborBottomRight && neighborBelow && neighborRight) g2d.fillRect(bottomRight.getX() - cornerOffset.getX(), bottomRight.getY() - cornerOffset.getY(), cornerOffset.getX(), cornerOffset.getY());
			
			// Draw sides
			if (neighborAbove) g2d.fillRect(innerTopLeft.getX(), topLeft.getY(), scaledCellSize.getX(), cornerOffset.getY());
			if (neighborBelow) g2d.fillRect(innerTopLeft.getX(), bottomRight.getY() - cornerOffset.getY(), scaledCellSize.getX(), cornerOffset.getY());
			if (neighborLeft)  g2d.fillRect(topLeft.getX(), innerTopLeft.getY(), cornerOffset.getX(), scaledCellSize.getY());
			if (neighborRight) g2d.fillRect(bottomRight.getX() - cornerOffset.getX(), innerTopLeft.getY(), cornerOffset.getX(), scaledCellSize.getY());
		}
	}
	
	public void setDrawGrid(boolean drawGrid) { this.drawGrid = drawGrid; }
	
	public boolean doesDrawGrid() { return drawGrid; }
	
	public void setActiveBrickScale(double activeBrickScale) { this.activeBrickScale = activeBrickScale; }
	
	public double getActiveBrickScale() { return activeBrickScale; }
	
	public void setPlacedBrickScale(double placedBrickScale) { this.placedBrickScale = placedBrickScale; }
	
	public double getPlacedBrickScale() { return placedBrickScale; }
	
	public EdgeDrawMode getEdgeDrawMode() { return edgeDrawMode; }
	
	public void setEdgeDrawMode(EdgeDrawMode edgeDrawMode) { this.edgeDrawMode = edgeDrawMode; }
	
	public boolean doesDrawBackground() { return drawBackground; }
	
	public void setDrawBackground(boolean drawBackground) { this.drawBackground = drawBackground; }
	
	private IntVec getCellSize() { return gridToPixels.apply(IntVec.ONE_ONE).floor(); }
	
	public void addMouseInput(BoardMouseInput listener) {
		component.addMouseListener(listener);
		component.addMouseMotionListener(listener);
	}
	
	public void addKeyInput(BoardKeyInput listener) {
		// component.addKeyListener(listener);
		InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = component.getActionMap();
		
		for (Map.Entry<Integer, Runnable> binding : listener.getBindings().entrySet()) {
			Integer keyCode = binding.getKey();
			Runnable action = binding.getValue();
			
			inputMap.put(KeyStroke.getKeyStroke(keyCode, 0), keyCode);
			actionMap.put(keyCode, new AbstractAction() {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					component.requestFocus();
					action.run();
				}
			});
		}
	}
	
	public JComponent getComponent() {
		return component;
	}
}
