package frames;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

import shapeTools.GShape;
import shapeTools.GShape.EAnchors;
import shapeTools.GShape.EDrawingStyle;

public class GDrawingPanel extends JPanel {
	// attributes
	private static final long serialVersionUID = 1L;
	private enum EDrawingState {
		eIdle,
		e2PState,
		eNPState,
		eTransformation	
	}
	private EDrawingState eDrawingState;
	
//	private enum ETransformation {
//		eDraw,
//		eMove,
//		eResize,
//		eRotate
//	}
//	private ETransformation eTransformation;
	
	// components
	private Vector<GShape> shapes;
	private GShape shapeTool;
	private GShape currentShape;
	private GShape onClicked;
	private Image doubleBuffering;
	private Graphics dbGraphics;
	
	// constructors
	public GDrawingPanel() {
		// attributes
		this.eDrawingState = EDrawingState.eIdle;
//		this.eTransformation = null;
		// components
		MouseEventHandler mouseEventHandler = new MouseEventHandler();
		this.addMouseListener(mouseEventHandler);
		this.addMouseMotionListener(mouseEventHandler);
		// dynamic components
		this.shapes = new Vector<GShape>();
	}
	
	public void initialize() {
		this.doubleBuffering = createImage(getWidth(), getHeight());
		this.dbGraphics = this.doubleBuffering.getGraphics();
	}	
	
	public void clear() {
		this.shapes = new Vector<GShape>();
		this.setDB();
		this.paint(getGraphics());
	}
	
	// setters and getters
	public void setDB() {
		this.initialize();
		this.paint(getGraphics());
	}
	
	public void setShapeTool(GShape shapeTool) {
		this.shapeTool = shapeTool;		
	}
	
	public Vector<GShape> getShape() {
		return this.shapes;
	}
	
	@SuppressWarnings("unchecked")
	public void setShape(Object object) {
		this.shapes = (Vector<GShape>) object;
	}
	
	public void paint(Graphics graphics) {	
		if (!shapes.isEmpty()) {
			for (GShape g : shapes) {
				g.draw(dbGraphics);
			}
		}
		graphics.drawImage(doubleBuffering, 0, 0, null);		
	}
	
	private void startDrawing(int x, int y) {
		currentShape = shapeTool.clone();
		currentShape.setOrigin(x, y);
		currentShape.drawAnchors((Graphics2D)getGraphics());
	}
	
	private void keepDrawing(int x, int y) {
		currentShape.keepResize(x, y, false);
		setDB();
		currentShape.drag(getGraphics(), dbGraphics, doubleBuffering);
	}
	
	private void continueDrawing(int x, int y) {
		currentShape.addPoint(x, y);
	}
	
	private void stopDrawing(int x, int y) {
		currentShape.draw(getGraphics());
		currentShape.setSelected(getGraphics(), x, y);
		shapes.add(currentShape);
		setDB();
	}
	
	private GShape onShape(int x, int y) {
		for (GShape shape : this.shapes) {
			boolean isOnShape = shape.onShape(x,y);
			if (isOnShape) {
				return shape;
			}
		}
		
		return null;
	}
	
	private void startMoving(int x, int y) {
		onClicked.startMove(x, y);
		setDB();
		onClicked.drag(getGraphics(), dbGraphics, doubleBuffering);
	}
	
	private void keepMoving(int x, int y) {
		onClicked.move(x, y);
		setDB();
		onClicked.drag(getGraphics(), dbGraphics, doubleBuffering);
	}
	
	private void stopMoving(int x, int y) {
		onClicked.move(x, y);
		onClicked.draw(getGraphics());
		setDB();
		
	}
	
	private void offAnchor() {
		for (GShape shape : this.shapes) {
			shape.offAnchor();
		}
	}
	
	private void changeCursor(int x, int y) {
		GShape shape = this.onShape(x, y);
		if (shape == null) {
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} else {
			this.setCursor(shape.getCursor());
		}
	}

	public void startRotate(int x, int y) {
		
		
	}  
	
	private class MouseEventHandler implements MouseListener, MouseMotionListener {		
		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				onClicked = onShape(e.getX(), e.getY());
				if (onClicked == null) {
					offAnchor();
					if (shapeTool.getEDrawingStyle() == EDrawingStyle.e2PStyle) {
						startDrawing(e.getX(), e.getY());
						eDrawingState = EDrawingState.e2PState;
					}
				} else {
					if (onClicked.getSelectedAnchor() == EAnchors.eMM) {
						// move
						startMoving(e.getX(), e.getY());
						onClicked.setSelected(getGraphics(), e.getX(), e.getY());
					} else if (onClicked.getSelectedAnchor() == EAnchors.eRR) {
						// rotate
						onClicked.startRotate(e.getX(), e.getY());
					} else {
						// resize
						onClicked.startResize(e.getX(), e.getY());
					}
					eDrawingState = EDrawingState.eTransformation;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {			
			if (eDrawingState == EDrawingState.e2PState) {
				keepDrawing(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eTransformation) {
				if (onClicked.getSelectedAnchor() == EAnchors.eMM) {
					// move
					keepMoving(e.getX(), e.getY());
				} else if (onClicked.getSelectedAnchor() == EAnchors.eRR) {
					// rotate
					onClicked.keepRotate(e.getX(), e.getY());
					setDB();
					onClicked.drag(getGraphics(), dbGraphics, doubleBuffering);
				} else {
					// resize
					onClicked.keepResize(e.getX(), e.getY(), true);
					setDB();
					onClicked.drag(getGraphics(), dbGraphics, doubleBuffering);
				}
			}			
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PState){
				stopDrawing(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			} else if (eDrawingState == EDrawingState.eTransformation) {
				if (onClicked.getSelectedAnchor() == EAnchors.eMM) {
					// move
					stopMoving(e.getX(), e.getY());
				} else if (onClicked.getSelectedAnchor() == EAnchors.eRR) {
					// rotate
					onClicked.stopRotate(e.getX(), e.getY());
				} else {
					// resize
					onClicked.stopResize(e.getX(), e.getY());
				}
				eDrawingState = EDrawingState.eIdle;
			} 
		}
		
		
		private void mouse1Clicked(MouseEvent e) {
			if (shapeTool.getEDrawingStyle() == EDrawingStyle.eNPStyle) {
				if (eDrawingState == EDrawingState.eIdle) {
					startDrawing(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPState;
				} else if (eDrawingState == EDrawingState.eNPState) {
					continueDrawing(e.getX(), e.getY());
	            	eDrawingState = EDrawingState.eNPState;
				}
			}
		}
		
		private void mouse2Clicked(MouseEvent e) {
			if (shapeTool.getEDrawingStyle() == EDrawingStyle.eNPStyle) {
				if (eDrawingState == EDrawingState.eNPState) {
					stopDrawing(e.getX(), e.getY());
	            	eDrawingState = EDrawingState.eIdle;
				}
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {	
			if (e.getClickCount() == 1) {
				mouse1Clicked(e);
			} else if (e.getClickCount() == 2) {
				mouse2Clicked(e);
			}	
		
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				changeCursor(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eNPState) {
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
