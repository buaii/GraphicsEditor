package shapeTools;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.GeneralPath; 

public class GPolygon extends GNPShape {	
	private static final long serialVersionUID = 1L;

	public GPolygon() {
		super(EDrawingStyle.eNPStyle, new Polygon());
	}
	
	public GPolygon(Shape shape) {
		super(EDrawingStyle.eNPStyle, shape);
	}
	
	@Override
	public GPolygon clone() {
		if (shape == null) {
			return new GPolygon();
		} else {
			GPolygon gPolygon = new GPolygon(this.shape);
			this.shape = null;
			return gPolygon;
		}
	}
	@Override
	public void drag(Graphics g, Graphics dbGraphics, Image doubleBuffering) {
		Graphics2D graphics2D = (Graphics2D) dbGraphics;
		Polygon polygon = (Polygon)this.shape;
		GeneralPath polyLine = new GeneralPath();

	    if (polygon.npoints > 0) {
	        polyLine.moveTo(polygon.xpoints[0], polygon.ypoints[0]);
	    }

	    for (int i = 1; i < polygon.npoints; i++) {
	        polyLine.lineTo(polygon.xpoints[i], polygon.ypoints[i]);
	    }
		graphics2D.draw(polyLine);
		g.drawImage(doubleBuffering, 0, 0, null);
	}

	@Override
	public void setOrigin(int x, int y) {
		Polygon polygon = (Polygon)this.shape;
		polygon.reset();
	    polygon.addPoint(x, y);
	    polygon.addPoint(x,y);
	}
	
	@Override
	public void movePoint(int x, int y) {
		Polygon polygon = (Polygon)this.shape;
		polygon.xpoints[polygon.npoints-1] = x;
		polygon.ypoints[polygon.npoints-1] = y;
	}	
	
	@Override
	public void addPoint(int x, int y) {
	    Polygon polygon = (Polygon) this.shape;
	    polygon.addPoint(x,y);
	}
	
	@Override
	public void startMove(int x, int y) {
		ox2 = x;
		oy2 = y;		
	}
	
	@Override
	public void move(int x, int y) {
		Polygon polygon = (Polygon)this.shape;
		polygon.translate(x-ox2, y-oy2);
		moveAnchor();
		ox2 = x;
		oy2 = y;
		
	}
}
