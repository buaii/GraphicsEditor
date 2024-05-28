package shapeTools;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;


public class GOval extends G2PShape {
	private static final long serialVersionUID = 1L;

	public GOval() {
		super(EDrawingStyle.e2PStyle, new Ellipse2D.Float());
	}
	
	public GOval(Shape shape) {
		super(EDrawingStyle.e2PStyle, shape);
	}

	@Override
	public GShape clone() {
		if (shape == null) {
			return new GOval(); 
		} else {
			GOval gOval = new GOval(this.shape);
			this.shape = null;
			return gOval;
		}
	}
	
	@Override
	public void drag(Graphics g, Graphics dbGraphics, Image doubleBuffering) {
		Graphics2D graphics2D = (Graphics2D) dbGraphics;
		Ellipse2D.Float oval = (Ellipse2D.Float)this.shape;
		oval.setFrame(Math.min(x1,x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
		drawAnchors(graphics2D);
		graphics2D.draw(oval);
		g.drawImage(doubleBuffering, 0, 0, null);
	}

	@Override
	public void startMove(int x, int y) {
		Ellipse2D.Float oval = (Ellipse2D.Float)this.shape;
		x1 = (int)oval.getX();
		y1 = (int)oval.getY();
		x2 = (int)oval.getWidth() + x1;
		y2 = (int)oval.getHeight() + y1;
		
		ox2 = x;
		oy2 = y;
	}
	
	@Override
	public void move(int x, int y) {
		Ellipse2D.Float oval = (Ellipse2D.Float)this.shape;
		x1 = (int)oval.getX() + x - ox2;
		y1 = (int)oval.getY() + y - oy2;
		x2 = (int)oval.getWidth() + x1;
		y2 = (int)oval.getHeight() + y1;
		ox2 = x;
		oy2 = y;
	}
	
}
