package shapeTools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class GShape implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	public enum EDrawingStyle {
		e2PStyle,
		eNPStyle
	}
	private EDrawingStyle eDrawingStyle;
	
	public EDrawingStyle getEDrawingStyle() {
		return this.eDrawingStyle; 
	}
	
	protected Shape shape;
	
	public enum EAnchors {
		eRR(new Cursor(Cursor.HAND_CURSOR)),
		eNN(new Cursor(Cursor.N_RESIZE_CURSOR)),
		eSS(new Cursor(Cursor.S_RESIZE_CURSOR)),
		eEE(new Cursor(Cursor.E_RESIZE_CURSOR)),
		eWW(new Cursor(Cursor.W_RESIZE_CURSOR)),
		eNE(new Cursor(Cursor.NE_RESIZE_CURSOR)),
		eSE(new Cursor(Cursor.SE_RESIZE_CURSOR)),
		eNW(new Cursor(Cursor.NW_RESIZE_CURSOR)),
		eSW(new Cursor(Cursor.SW_RESIZE_CURSOR)),
		eMM(new Cursor(Cursor.CROSSHAIR_CURSOR));
		
		private Cursor cursor;
		private EAnchors(Cursor cursor) {
			this.cursor = cursor;
		}
		public Cursor getCursor() {
			return this.cursor;
		}
	}
	
	protected EAnchors eSelectedAnchor;
	
	protected double sx, sy;
	protected double dx, dy;
	protected double rx, ry = 0;
	protected double previousAngle;
	protected double accumulatedAngle;
	protected Ellipse2D.Float[] anchors;
	protected Color fillColor;
	protected Color lineColor;
	protected int x1, y1, x2, y2, ox2, oy2;
	protected boolean onClicked;
	// setters and getters
	public void setSelected(Graphics graphics, int x, int y) {
		Graphics2D graphics2D = (Graphics2D) graphics;
		onClicked = true;
		this.drawAnchors(graphics2D);		
	}
	
	public void clearSelected() {
		this.anchors = null;
	}
	
	public EAnchors getSelectedAnchor() {
		return this.eSelectedAnchor;
	}
	
	public Cursor getCursor() {
		return this.eSelectedAnchor.getCursor();
	}
		
	public GShape(EDrawingStyle eDrawingStyle, Shape shape) {
		this.eDrawingStyle = eDrawingStyle;
		this.shape = shape;
		this.anchors = null;
		this.eSelectedAnchor = EAnchors.eSE;
		this.onClicked = true; 
		this.previousAngle = 0;
		this.fillColor = new Color(0, 0, 0, 0);
		this.lineColor = Color.BLACK;
		
		this.x1 = 0;
		this.y1 = 0;
		this.x2 = 0;
		this.y2 = 0;
		this.ox2 = 0;
		this.oy2 = 0;
	}
	public GShape() {}
	public abstract GShape clone();
	
	public GShape copy() throws CloneNotSupportedException {
		if (onClicked) {
			GShape clone = (GShape)super.clone();
			AffineTransform affineTransform = new AffineTransform();
			affineTransform.setToTranslation(50, 50);
			clone.shape = affineTransform.createTransformedShape(clone.shape);
			return clone;
		} else {
			return null;
		}
	}
	
	public void draw(Graphics g) {
		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.setColor(fillColor);
		graphics2D.fill(shape);
		graphics2D.setColor(lineColor);
		graphics2D.draw(shape);
		if (this.anchors != null) {
			drawAnchors(graphics2D);
		} 
	}
	
	public abstract void drag(Graphics g, Graphics dbGraphics, BufferedImage doubleBuffering);
	
	public void drawAnchors(Graphics2D graphics2D) {
		if (onClicked) {
			Rectangle rectangle = this.shape.getBounds();
			int x = rectangle.x;
			int y = rectangle.y;
			int w = rectangle.width;
			int h = rectangle.height;
			int ANCHOR_WIDTH = 10;
			int ANCHOR_HEIGHT = 10;
			this.anchors = new Ellipse2D.Float[EAnchors.values().length-1];
			this.anchors[EAnchors.eRR.ordinal()] = new Ellipse2D.Float(x+w/2-ANCHOR_WIDTH/2, y-50-ANCHOR_HEIGHT/2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
			this.anchors[EAnchors.eNN.ordinal()] = new Ellipse2D.Float(x+w/2-ANCHOR_WIDTH/2, y-ANCHOR_HEIGHT/2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
			this.anchors[EAnchors.eSS.ordinal()] = new Ellipse2D.Float(x+w/2-ANCHOR_WIDTH/2, y+h-ANCHOR_HEIGHT/2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
			this.anchors[EAnchors.eEE.ordinal()] = new Ellipse2D.Float(x+w-ANCHOR_WIDTH/2, y+h/2-ANCHOR_HEIGHT/2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
			this.anchors[EAnchors.eWW.ordinal()] = new Ellipse2D.Float(x-ANCHOR_WIDTH/2, y+h/2-ANCHOR_HEIGHT/2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
			this.anchors[EAnchors.eNW.ordinal()] = new Ellipse2D.Float(x-ANCHOR_WIDTH/2, y-ANCHOR_HEIGHT/2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
			this.anchors[EAnchors.eSW.ordinal()] = new Ellipse2D.Float(x-ANCHOR_WIDTH/2, y+h-ANCHOR_HEIGHT/2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
			this.anchors[EAnchors.eNE.ordinal()] = new Ellipse2D.Float(x+w-ANCHOR_WIDTH/2, y-ANCHOR_HEIGHT/2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
			this.anchors[EAnchors.eSE.ordinal()] = new Ellipse2D.Float(x+w-ANCHOR_WIDTH/2, y+h-ANCHOR_HEIGHT/2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
			
			
			// draw boundary box
			Stroke originalStroke = graphics2D.getStroke();
			graphics2D.setColor(Color.GRAY);
			graphics2D.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3}, 0));
			graphics2D.draw(rectangle);
			int lx = (int)this.anchors[EAnchors.eRR.ordinal()].x ;
			int ly = (int)this.anchors[EAnchors.eRR.ordinal()].y ;
			int lw = (int)this.anchors[EAnchors.eNN.ordinal()].x ;
			int lh = (int)this.anchors[EAnchors.eNN.ordinal()].y ;
			graphics2D.drawLine(lx+5, ly, lw+5, lh);
			graphics2D.setStroke(originalStroke);
			// draw anchors
			for (Ellipse2D.Float anchor : this.anchors) {
				graphics2D.setColor(Color.WHITE);
	        	graphics2D.fill(anchor);
	        	graphics2D.setColor(Color.BLACK);
	        	graphics2D.draw(anchor);
			}
		}
	}
	
	public void setFillColor(Color color) {
		if (onClicked) {
			this.fillColor = color;
		}		 
	}
	
	public void setLineColor(Color color) {
		if (onClicked) {
			this.lineColor = color;
		} 
	}
	
	public boolean onShape(int x, int y) {
		this.eSelectedAnchor = EAnchors.eSE;
		if (this.anchors != null) {
			for (int i = 0; i < EAnchors.values().length-1; i++) {
				if (anchors[i].contains(x, y)) {
					this.eSelectedAnchor = EAnchors.values()[i];
					return true;
				}
			}
		}
		
		boolean isOnShape = this.shape.getBounds().contains(x, y);
		if (isOnShape) {
			this.eSelectedAnchor = EAnchors.eMM;
		}
		return isOnShape;
	}

	public abstract void setOrigin(int x1, int y1); 
	public abstract void movePoint(int x2, int y2);	
	public abstract void addPoint(int x2, int y2);
	
	public abstract void startMove(int x, int y);
	public void move(int x, int y) {
		Rectangle rectangle = (Rectangle)this.shape.getBounds();
		x1 = (int)rectangle.getX() + x - ox2;
		y1 = (int)rectangle.getY() + y - oy2;
		x2 = (int)rectangle.getWidth() + x1;
		y2 = (int)rectangle.getHeight() + y1;
		
		int dx = x - ox2;
		int dy = y - oy2;
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setToTranslation(dx, dy);
		this.shape = affineTransform.createTransformedShape(this.shape);
		
		ox2 = x;
		oy2 = y;
	}
	
	public void moveAnchor() {
		if (onClicked) {
			Rectangle rectangle = this.shape.getBounds();
			int x = rectangle.x;
			int y = rectangle.y;
			int w = rectangle.width;
			int h = rectangle.height;
			int ANCHOR_WIDTH = 10;
			int ANCHOR_HEIGHT = 10;
			this.anchors = new Ellipse2D.Float[EAnchors.values().length-1];
			this.anchors[EAnchors.eRR.ordinal()] = new Ellipse2D.Float(x+w/2-ANCHOR_WIDTH/2, y-50-ANCHOR_HEIGHT/2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
			this.anchors[EAnchors.eNN.ordinal()] = new Ellipse2D.Float(x+w/2-ANCHOR_WIDTH/2, y-ANCHOR_HEIGHT/2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
			this.anchors[EAnchors.eSS.ordinal()] = new Ellipse2D.Float(x+w/2-ANCHOR_WIDTH/2, y+h-ANCHOR_HEIGHT/2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
			this.anchors[EAnchors.eEE.ordinal()] = new Ellipse2D.Float(x+w-ANCHOR_WIDTH/2, y+h/2-ANCHOR_HEIGHT/2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
			this.anchors[EAnchors.eWW.ordinal()] = new Ellipse2D.Float(x-ANCHOR_WIDTH/2, y+h/2-ANCHOR_HEIGHT/2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
			this.anchors[EAnchors.eNW.ordinal()] = new Ellipse2D.Float(x-ANCHOR_WIDTH/2, y-ANCHOR_HEIGHT/2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
			this.anchors[EAnchors.eSW.ordinal()] = new Ellipse2D.Float(x-ANCHOR_WIDTH/2, y+h-ANCHOR_HEIGHT/2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
			this.anchors[EAnchors.eNE.ordinal()] = new Ellipse2D.Float(x+w-ANCHOR_WIDTH/2, y-ANCHOR_HEIGHT/2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
			this.anchors[EAnchors.eSE.ordinal()] = new Ellipse2D.Float(x+w-ANCHOR_WIDTH/2, y+h-ANCHOR_HEIGHT/2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
		}	
	}

	public void offAnchor() {
		this.onClicked = false;
	}

	public void startResize(int x, int y) {
		this.ox2 = x2;
		this.oy2 = y2;
		this.x2 = x;
		this.y2 = y;
	}
	
	private Point2D getResizeFactor() {
		sx = 1;
		sy = 1;
		dx = 0;
		dy = 0;
		double cx = 0;
		double cy = 0;
		double w = this.shape.getBounds().getWidth();
		double h = this.shape.getBounds().getHeight();
		
		switch(this.eSelectedAnchor) {
		case eNW:
			cx = this.anchors[EAnchors.eSE.ordinal()].getCenterX();
			cy = this.anchors[EAnchors.eSE.ordinal()].getCenterY();
			sx = (w+ox2-x2)/w;
			dx = cx - cx * sx;
			sy = (h+oy2-y2)/h;
			dy = cy - cy * sy;
			break;
		case eSE:
			cx = this.anchors[EAnchors.eNW.ordinal()].getCenterX();
			cy = this.anchors[EAnchors.eNW.ordinal()].getCenterY();
			sx = (w+x2-ox2)/w;
			dx = cx - cx * sx;
			sy = (h+y2-oy2)/h;
			dy = cy - cy * sy;
			break;
		case eEE:
			cx = this.anchors[EAnchors.eWW.ordinal()].getCenterX();
			sx = (w+x2-ox2)/w;
			dx = cx - cx * sx;
			break;
		case eWW:
			cx = this.anchors[EAnchors.eEE.ordinal()].getCenterX();
			sx = (w+ox2-x2)/w;
			dx = cx - cx * sx;
			break;
		case eSS:
			cy = this.anchors[EAnchors.eNN.ordinal()].getCenterY();
			sy = (h+y2-oy2)/h; 
			dy = cy - cy * sy;
			break;
		case eNN: 
			cy = this.anchors[EAnchors.eSS.ordinal()].getCenterY();
			sy = (h+oy2-y2)/h;
			dy = cy - cy * sy;
			break;
		case eSW:
			cx = this.anchors[EAnchors.eNE.ordinal()].getCenterX();
			cy = this.anchors[EAnchors.eNE.ordinal()].getCenterY();
			sx = (w+ox2-x2)/w;
			dx = cx - cx * sx;
			sy = (h+y2-oy2)/h;
			dy = cy - cy * sy;
			break;
		case eNE:
			cx = this.anchors[EAnchors.eSW.ordinal()].getCenterX();
			cy = this.anchors[EAnchors.eSW.ordinal()].getCenterY();
			sx = (w+x2-ox2)/w;
			dx = cx - cx * sx;
			sy = (h+oy2-y2)/h;
			dy = cy - cy * sy;
			break;
		default:
			break;
		
		}
		return new Point2D.Double(sx, sy);
	}
	
	public void keepResize(int x, int y, boolean bool) {
		if (Math.abs(x-x2) > 1 && Math.abs(y-y2) > 1) {
			this.ox2 = x2;
			this.oy2 = y2;
			this.x2 = x;
			this.y2 = y;
			
			
			Point2D resizeFactor = getResizeFactor();
			AffineTransform affineTransform = new AffineTransform();
		
			affineTransform.setToScale(resizeFactor.getX(), resizeFactor.getY());
			this.shape = affineTransform.createTransformedShape(this.shape);
			AffineTransform affineTransform2 = new AffineTransform();
			affineTransform2.setToTranslation(dx, dy);
			this.shape = affineTransform2.createTransformedShape(this.shape);
		}
	}

	public void stopResize(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	public void startRotate(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	private double getRotateFactor(int x, int y) {
        double centerX = this.anchors[EAnchors.eRR.ordinal()].getCenterX();
        double centerY = this.anchors[EAnchors.eRR.ordinal()].getCenterY();
        if (rx == 0 || ry == 0) {
        	rx = this.anchors[EAnchors.eRR.ordinal()].getCenterX();
            ry = this.anchors[EAnchors.eRR.ordinal()].getCenterY();
        } 
        
        double v1X = rx - centerX;
        double v1Y = ry - centerY;
        double v2X = x - centerX;
        double v2Y = y - centerY;
        
        double angle1 = Math.atan2(v1Y, v1X);
        double angle2 = Math.atan2(v2Y, v2X);
        
        double angleDiff = angle2 - angle1;
        if (angleDiff < 0) {
            angleDiff += 2 * Math.PI;
        }
        rx = x;
        ry = y;
        return angleDiff;
	}
	
	public void keepRotate(int x, int y) {
	    double angleDiff = getRotateFactor(x, y);
	    
	    Rectangle2D bounds = this.shape.getBounds2D();
	    double centerX = bounds.getCenterX();
	    double centerY = bounds.getCenterY();
	    
	    double rotationAngle = Math.toDegrees(angleDiff);
	    
	    AffineTransform affineTransform = new AffineTransform();
	    affineTransform.rotate(Math.toRadians(rotationAngle), centerX, centerY);
	    
	    this.shape = affineTransform.createTransformedShape(this.shape);
	}

	public void stopRotate(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	public boolean isSelected() {
		return onClicked;
	}
}
