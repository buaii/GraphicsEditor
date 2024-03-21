package frames;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import shapeTools.GShapeTool;

public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private GShapeTool shapeTool;
	
	public GDrawingPanel() {
		this.setBackground(Color.gray);
		MouseEventHandler mouseEventHandler = new MouseEventHandler();
		this.addMouseListener(mouseEventHandler);
		this.addMouseMotionListener(mouseEventHandler);
	}
	
	public void paint(Graphics graphics) {
		
	}
	
	public void setShapeTool(GShapeTool shapeTool) {
		this.shapeTool = shapeTool;		
	}
	
	private void draw(int x, int y) {
		if (shapeTool != null) {
			this.shapeTool.draw(getGraphics(), x, y, 20, 20);
		}
		
		
	}
	
	private class MouseEventHandler implements MouseListener, MouseMotionListener {		
		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			draw(e.getX(), e.getY());
			System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			draw(e.getX(), e.getY());
			draw(e.getX(), e.getY()); 
			System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		}
		

		@Override
		public void mouseEntered(MouseEvent e) {
			System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		}

		@Override
		public void mouseExited(MouseEvent e) {
			System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		}
		
	}	
  
}
