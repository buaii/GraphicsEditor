package frames;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

import shapeTools.GPolygon;
import shapeTools.GShape;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private boolean bDrawing;
	private boolean polyDrawing;
	private Vector<GShape> shapes;
	private GShape shapeTool;
	private GShape currentShape;
	
	public GDrawingPanel() {
		MouseEventHandler mouseEventHandler = new MouseEventHandler();
		this.addMouseListener(mouseEventHandler);
		this.addMouseMotionListener(mouseEventHandler);
		
		this.bDrawing = false;
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
	/*
	private void draw() {
		Graphics graphics = this.getGraphics();
		if (gShape != null) {
			if (!gShapeList.isEmpty()) {
				for (GShape g : gShapeList) {
					g.draw(graphics);
				}
			}
			
			this.gShape.draw(getGraphics());
		}
		
		
	}
	
	private void move() {
	}
	*/
	
	private class MouseEventHandler implements MouseListener, MouseMotionListener {		
		@Override
		public void mousePressed(MouseEvent e) {
			if (shapeTool instanceof GPolygon) {
				if (currentShape == null || !currentShape.isDrawing(e.getX(), e.getY())) {
					
					currentShape = shapeTool.clone();
					shapes.add(currentShape);
					currentShape.setP1(e.getX(), e.getY());
				} else {
					currentShape.setP1(e.getX(), e.getY());
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (shapeTool instanceof GPolygon) {
				currentShape.setP2(e.getX(), e.getY());
				currentShape.drag(getGraphics());
			}
			
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if (shapeTool instanceof GPolygon) {
				currentShape.addPoint(e.getX(), e.getY());	
				shapes.set(shapes.size()-1, currentShape);
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if (shapeTool != null) {
				if (!bDrawing && !polyDrawing) {
					currentShape = shapeTool.clone();
					currentShape.setP1(e.getX(), e.getY());
					bDrawing = true;
				} else if (bDrawing){
					currentShape.setP2(e.getX(), e.getY());
					shapes.add(currentShape.clone());
					bDrawing = false;
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (shapeTool != null && bDrawing) {
				currentShape.setP2(e.getX(), e.getY());
				currentShape.drag(getGraphics());
			}
			if (shapes.size() != 0 && !shapes.get(shapes.size()-1).isDrawing(e.getX(), e.getY())) {
				shapes.get(shapes.size()-1).draw(getGraphics());
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
	}	
  
}
