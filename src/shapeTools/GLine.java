package shapeTools;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Line2D;

public class GLine extends G2PShape {
	private static final long serialVersionUID = 1L;

	public GLine() {
		super(EDrawingStyle.e2PStyle, new Line2D.Float());
	}
	
	public GLine(Shape shape) {
		super(EDrawingStyle.e2PStyle, shape);
	}
	
	@Override
	public GShape clone() {
		if (shape == null) {
			return new GLine();
		} else {
			GLine gLine = new GLine(this.shape);
			this.shape = null;
			return gLine;
		}
	}

	@Override
	public void drag(Graphics g, Graphics dbGraphics, Image doubleBuffering) {
		Graphics2D graphics2D = (Graphics2D) dbGraphics;
		Line2D.Float line = (Line2D.Float)this.shape;
		line.setLine(x1, y1, x2, y2);
		graphics2D.draw(line); 
		g.drawImage(doubleBuffering, 0, 0, null);
		
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D graphics2D = (Graphics2D) g;
		Line2D.Float line = (Line2D.Float)this.shape;
		
		graphics2D.draw(line);
	}

	
	@Override
	public boolean onShape(int x, int y) {
		Line2D.Float line = (Line2D.Float)this.shape;
		return line.getBounds().contains(x, y);
	}

	@Override
	public void startMove(int x, int y) {
		Line2D.Float line = (Line2D.Float)this.shape;
		x1 = (int)line.getX1();
		y1 = (int)line.getY1();
		x2 = (int)line.getX2();
		y2 = (int)line.getY2();
		
		ox2 = x;
		oy2 = y;
	}
	
	@Override
	public void move(int x, int y) {
		Line2D.Float line = (Line2D.Float)this.shape;
		x1 = (int)line.getX1() + x - ox2;
		y1 = (int)line.getY1() + y - oy2;
		x2 = (int)line.getX2() + x - ox2;
		y2 = (int)line.getY2() + y - oy2;
		
		ox2 = x;
		oy2 = y;
	}

}
