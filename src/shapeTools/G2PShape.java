package shapeTools;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class G2PShape extends GShape {
	private static final long serialVersionUID = 1L;

	public G2PShape(EDrawingStyle eDrawingStyle, Shape shape) {
		super(EDrawingStyle.e2PStyle, shape);
	}
	
	@Override
	public void drag(Graphics g, Graphics dbGraphics, BufferedImage doubleBuffering) {
		Graphics2D graphics2D = (Graphics2D) dbGraphics;
		graphics2D.draw(this.shape);
		g.drawImage(doubleBuffering, 0, 0, null);
	}
	
	@Override
	public void setOrigin(int x, int y) {
		this.x1 = x;
		this.y1 = y;
		
		this.ox2 = x+1;
		this.oy2 = y+1;
		this.x2 = x+1;
		this.y2 = y+1;
		
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.translate(x1,y1);
		this.shape = affineTransform.createTransformedShape(this.shape);
	}
	
	@Override
	public void startMove(int x, int y) {
		Rectangle rectangle = this.shape.getBounds();
		x1 = (int)rectangle.getX();
		y1 = (int)rectangle.getY();
		x2 = (int)rectangle.getWidth() + x1;
		y2 = (int)rectangle.getHeight() + y1;
		
		ox2 = x;
		oy2 = y;
	}
	
	@Override
	public void movePoint(int x, int y) {
		this.ox2 = this.x2;
		this.oy2 = this.y2;
		this.x2 = x;
		this.y2 = y;
	}
	@Override
	public void addPoint(int x, int y) {
		this.x2 = x;
		this.y2 = y;
	}
	
	@Override
	public GShape clone() {
		// TODO Auto-generated method stub
		return null;
	}	
}
