package frames;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

import shapeTools.GShape;
import shapeTools.GShape.EDrawingStyle;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private enum EDrawingState {
		eIdle,
		e2PState,
		eNPState
	} 
	private EDrawingState eDrawingState;
	
	private Vector<GShape> shapes;
	private GShape shapeTool;
	private GShape currentShape;
	
	public GDrawingPanel() {
		MouseEventHandler mouseEventHandler = new MouseEventHandler();
		this.addMouseListener(mouseEventHandler);
		this.addMouseMotionListener(mouseEventHandler);
		
		this.eDrawingState = EDrawingState.eIdle;
		this.shapes = new Vector<GShape>();
	}
	
	public void initialize() {
		
	}	
	
	public void paint(Graphics graphics) {	
		if (!shapes.isEmpty()) {
			for (GShape g : shapes) {
				g.draw(graphics);
			}
		}
		
	}
	
	public void setShapeTool(GShape shapeTool) {
		this.shapeTool = shapeTool;		
	}
	
	private void startDrawing(int x, int y) {
		currentShape = shapeTool.clone();
		currentShape.setOrigin(x, y);
	}
	
	private void keepDrawing(int x, int y) {
		currentShape.movePoint(x, y);
		currentShape.drag(getGraphics());
	}
	
	private void continueDrawing(int x, int y) {
		currentShape.addPoint(x, y);
	}
	
	private void stopDrawing(int x, int y) {
		currentShape.addPoint(x, y);
		currentShape.draw(getGraphics());
		shapes.add(currentShape.clone());
	}
	
	private class MouseEventHandler implements MouseListener, MouseMotionListener {		
		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				if (shapeTool.getEDrawingStyle() == EDrawingStyle.e2PStyle) {
					startDrawing(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2PState;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PState) {
				keepDrawing(e.getX(), e.getY());
				eDrawingState = EDrawingState.e2PState;
			}
			
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PState){
				stopDrawing(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if (shapeTool.getEDrawingStyle() == EDrawingStyle.eNPStyle) {
				if (e.getClickCount() == 1) {
					if (eDrawingState == EDrawingState.eIdle) {
						startDrawing(e.getX(), e.getY());
						eDrawingState = EDrawingState.eNPState;
					} else if (eDrawingState == EDrawingState.eNPState) {
						continueDrawing(e.getX(), e.getY());
		            	eDrawingState = EDrawingState.eNPState;
					}
				} else if (e.getClickCount() == 2) {
					if (eDrawingState == EDrawingState.eNPState) {
						stopDrawing(e.getX(), e.getY());
		            	eDrawingState = EDrawingState.eIdle;
					}
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPState) {
				keepDrawing(e.getX(), e.getY());
				eDrawingState = EDrawingState.eNPState;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
	}  
}
