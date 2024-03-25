package frames;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import shapeTools.GShape;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private GShape gShape;
	private ArrayList<GShape> gShapeList;
	public GDrawingPanel() {
		MouseEventHandler mouseEventHandler = new MouseEventHandler();
		this.addMouseListener(mouseEventHandler);
		this.addMouseMotionListener(mouseEventHandler);
		this.gShapeList = new ArrayList<>();
	}
	
	public void paint(Graphics graphics) {	
		if (!gShapeList.isEmpty()) {
			for (GShape g : gShapeList) {
				g.draw(graphics);
			}
		}
		
	}
	
	public void setShapeTool(GShape shapeTool) {
		this.gShape = shapeTool;		
	}
	
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
	
	public void addShape() {
		if (gShape != null)
			this.gShapeList.add(this.gShape.getShape());
	}
	
	private class MouseEventHandler implements MouseListener, MouseMotionListener {		
		@Override
		public void mousePressed(MouseEvent e) {
			if (gShape != null)
				gShape.setP1(e.getX(), e.getY());
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (gShape != null) {
				gShape.drawing(getGraphics());
				gShape.setP2(e.getX(), e.getY());
			}
			
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			addShape();
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseMoved(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
	}	
  
}
