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
		currentShape.setP1(x, y);
	}
	
	private void keepDrawing(int x, int y) {
		currentShape.setP2(x, y);
		currentShape.drag(getGraphics());
	}
	
	private void stopDrawing(int x, int y) {
		currentShape.setP2(x, y);
		shapes.add(currentShape.clone());
	}
	
	private class MouseEventHandler implements MouseListener, MouseMotionListener {		
		private long lastClickTime = 0;
		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				if (shapeTool != null 
						&& shapeTool.getEDrawingStyle() == EDrawingStyle.e2PStyle) {
					startDrawing(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2PState;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PState) {
				keepDrawing(e.getX(), e.getY());
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
			long clickTime = System.currentTimeMillis();
            
			if (eDrawingState == EDrawingState.eNPState) {
				// 더블클릭 이벤트 처리를 위한 로직
				if (clickTime - lastClickTime <= 300) {
					currentShape.addPoint(e.getX(), e.getY());	
	            	shapes.set(shapes.size()-1, currentShape);
	            	shapes.get(shapes.size()-1).draw(getGraphics());
	            	eDrawingState = EDrawingState.eIdle;
	            } else {
	            	// 일반 클릭
	            	currentShape.addPoint(e.getX(), e.getY());	
	            	shapes.set(shapes.size()-1, currentShape);
	            	currentShape.setP(e.getX(), e.getY());
	            }
			} else if (eDrawingState == EDrawingState.eIdle) {
				if (shapeTool != null 
						&& shapeTool.getEDrawingStyle() == EDrawingStyle.eNPStyle) {
					currentShape = shapeTool.clone();
					shapes.add(currentShape);
					currentShape.setP1(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPState;
				}
			}
			lastClickTime = clickTime;
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPState) {
				currentShape.setP2(e.getX(), e.getY());
				currentShape.drag(getGraphics());
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
	}	
  
}
