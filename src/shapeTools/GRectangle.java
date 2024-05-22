package shapeTools;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;

//import shapeTools.GShape.EAnchor;

public class GRectangle extends G2PShape {
	private static final long serialVersionUID = 1L;
	
	public GRectangle() {
		super(EDrawingStyle.e2PStyle, new Rectangle());
	}
	
	public GRectangle(Shape shape) {
		super(EDrawingStyle.e2PStyle, shape);
	}

	@Override
	public GRectangle clone() {
		if (shape == null) {
			return new GRectangle();
		} else {
			GRectangle gRectangle = new GRectangle(this.shape);
			this.shape = null;
			return gRectangle;
		}
	}
	
	@Override
	public void drag(Graphics g, Graphics dbGraphics, Image doubleBuffering) {
		Graphics2D graphics2D = (Graphics2D) dbGraphics;
		Rectangle rectangle = (Rectangle)this.shape;
		System.out.println(this.shape);
		rectangle.setFrame(Math.min(x1,x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
		graphics2D.draw(rectangle);
		g.drawImage(doubleBuffering, 0, 0, null);
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D graphics2D = (Graphics2D) g; 
		Rectangle rectangle = (Rectangle)this.shape;
		
		graphics2D.draw(rectangle);
	}
	
	@Override
	public boolean onShape(int x, int y) {
		Rectangle rectangle = (Rectangle)this.shape;
		return rectangle.contains(x, y);
	}

	@Override
	public void startMove(int x, int y) {
		Rectangle rectangle = (Rectangle)this.shape;
		x1 = (int)rectangle.getX();
		y1 = (int)rectangle.getY();
		x2 = (int)rectangle.getWidth() + x1;
		y2 = (int)rectangle.getHeight() + y1;
		
		ox2 = x;
		oy2 = y;
	}
	
	@Override
	public void move(int x, int y) {
		Rectangle rectangle = (Rectangle)this.shape;
		x1 = (int)rectangle.getX() + x - ox2;
		y1 = (int)rectangle.getY() + y - oy2;
		x2 = (int)rectangle.getWidth() + x1;
		y2 = (int)rectangle.getHeight() + y1;
		
		ox2 = x;
		oy2 = y;
	}
	
	
}

