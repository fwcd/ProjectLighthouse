package lighthouse.ui.board.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import lighthouse.model.Board;
import lighthouse.model.BoardEditState;
import lighthouse.model.Brick;
import lighthouse.model.BrickBuilder;
import lighthouse.model.Direction;
import lighthouse.model.GameBlock;
import lighthouse.ui.board.CoordinateMapper;
import lighthouse.ui.board.input.BoardKeyInput;
import lighthouse.ui.board.input.BoardMouseInput;
import lighthouse.util.IntVec;

/**
 * A local high-resolution (Swing-based) view of the GameBoard.
 */
public class LocalBoardView implements BoardView {
	private final Color background = Color.WHITE;
	private final Color gridLineColor = Color.LIGHT_GRAY;
	private final int gridDashLength = 3;
	private final int gridLineThickness = 1;
	
	private final JComponent component;
	private final CoordinateMapper coordinateMapper;
	private Board model = null;
	
	public LocalBoardView(CoordinateMapper coordinateMapper) {
		this.coordinateMapper = coordinateMapper;
		component = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				render((Graphics2D) g, getSize());
			}
		};
	}
	
	@Override
	public void draw(Board model) {
		this.model = model;
		// Redraw the component
		SwingUtilities.invokeLater(component::repaint);
	}
	
	public void relayout(int columns, int rows) {
		IntVec cellSize = coordinateMapper.toPixelCoordinate(IntVec.ONE_ONE);
		int width = cellSize.getX() * columns;
		int height = cellSize.getY() * rows;
		// Add one to render the right and bottom grid borders
		component.setPreferredSize(new Dimension(width + 1, height + 1));
	}
	
	/** Renders the model grid to the Swing Graphics canvas. */
	private void render(Graphics2D g2d, Dimension canvasSize) {
		int canvasWidth = (int) canvasSize.getWidth();
		int canvasHeight = (int) canvasSize.getHeight();
		g2d.setColor(background);
		g2d.fillRect(0, 0, canvasWidth, canvasHeight);

		if (model == null) {
			g2d.setFont(g2d.getFont().deriveFont(18F)); // Make font larger
			g2d.drawString("No Board model drawn", 30, 30);
		} else {
			IntVec cellSize = getCellSize();
			
			// Draw the background grid
			float[] dash = {gridDashLength};
			g2d.setColor(gridLineColor);
			g2d.setStroke(new BasicStroke(gridLineThickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0));
			
			for (int y = 0; y < canvasHeight; y += cellSize.getY()) {
				g2d.drawLine(0, y, canvasWidth, y);
			}
			
			for (int x = 0; x < canvasWidth; x += cellSize.getX()) {
				g2d.drawLine(x, 0, x, canvasHeight);
			}
			
			// Draw the board's bricks
			for (Brick brick : model.getBricks()) {
				renderBlock(g2d, brick);
			}
			
			// Draw the editing state
			BoardEditState editState = model.getEditState();
			BrickBuilder brickInProgress = editState.getBrickInProgress();
			
			if (brickInProgress != null) {
				renderBlock(g2d, brickInProgress);
			}
		}
	}
	
	private void renderBlock(Graphics2D g2d, GameBlock block) {
		IntVec cellSize = getCellSize();
		IntVec currentPos = block.getPos();
		int cellWidth = cellSize.getX();
		int cellHeight = cellSize.getY();
		
		g2d.setColor(block.getColor());
		g2d.fillRect(currentPos.getX() * cellWidth, currentPos.getY() * cellHeight, cellWidth, cellHeight);
		
		for (Direction dir : block.getStructure()) {
			currentPos = currentPos.add(dir);
			g2d.fillRect(currentPos.getX() * cellWidth, currentPos.getY() * cellHeight, cellWidth, cellHeight);
		}
	}
	
	private IntVec getCellSize() {
		return coordinateMapper.toPixelCoordinate(IntVec.ONE_ONE);
	}
	
	public void addMouseInput(BoardMouseInput listener) {
		component.addMouseListener(listener);
		component.addMouseMotionListener(listener);
	}
	
	public void addKeyInput(BoardKeyInput listener) {
		component.addKeyListener(listener);
	}
	
	public JComponent getComponent() {
		return component;
	}
}
