package lighthouse.ui.board.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import lighthouse.model.Board;
import lighthouse.model.BoardEditState;
import lighthouse.model.BrickBuilder;
import lighthouse.model.Direction;
import lighthouse.ui.board.CoordinateMapper;
import lighthouse.ui.board.input.BoardKeyInput;
import lighthouse.ui.board.input.BoardMouseInput;
import lighthouse.util.IntVec;

/**
 * A local high-resolution (Swing-based) view of the GameBoard.
 */
public class LocalBoardView implements BoardView {
	private final JComponent component;
	private Board model = null;
	private final CoordinateMapper coordinateMapper;
	
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
		component.setPreferredSize(new Dimension(width, height));
	}
	
	/** Renders the model grid to the Swing Graphics canvas. */
	private void render(Graphics2D g2d, Dimension canvasSize) {
		if (model == null) {
			g2d.setFont(g2d.getFont().deriveFont(18F)); // Make font larger
			g2d.drawString("No Board model drawn", 30, 30);
		} else {
			IntVec cellSize = coordinateMapper.toPixelCoordinate(IntVec.ONE_ONE);
			int cellWidth = cellSize.getX();
			int cellHeight = cellSize.getY();
			int cols = model.getColumns();
			int rows = model.getRows();
			
			// Draw the cell grid
			for (int y = 0; y < rows; y++) {
				for (int x = 0; x < cols; x++) {
					g2d.setColor(model.colorAt(x, y));
					g2d.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
				}
			}
			
			// Draw the editing state
			BoardEditState editState = model.getEditState();
			IntVec bipPos = editState.getStartPos();
			BrickBuilder brickInProgress = editState.getBrickInProgress();
			
			if (brickInProgress != null) {
				g2d.setColor(brickInProgress.getColor());
				g2d.fillRect(bipPos.getX() * cellWidth, bipPos.getY() * cellHeight, cellWidth, cellHeight);
				
				for (Direction dir : brickInProgress) {
					bipPos = bipPos.add(dir);
					g2d.fillRect(bipPos.getX() * cellWidth, bipPos.getY() * cellHeight, cellWidth, cellHeight);
				}
			}
		}
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
