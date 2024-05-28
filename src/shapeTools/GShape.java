package shapeTools;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

public abstract class GShape implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public enum EDrawingStyle {
		e2PStyle,
		eNPStyle
	}
	private EDrawingStyle eDrawingStyle;
	
	public EDrawingStyle getEDrawingStyle() {
		return this.eDrawingStyle; 
	}
	
	public enum EAnchor {
		eMove,
		eResize,
		eRotate
	}
	
	// int x[], int y[];
	protected Shape shape;
	protected boolean onAnchor = false;
	protected Rectangle box;
	
	protected enum EAnchors {
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
	
	private EAnchors eSelectedAnchor;
	
	protected Ellipse2D.Float[] anchors;
	protected int x1, y1, x2, y2, ox2, oy2;
	protected boolean onClicked = false;
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
		this.eSelectedAnchor = null;
		this.box = shape.getBounds();
		
		this.x1 = 0;
		this.y1 = 0;
		this.x2 = 0;
		this.y2 = 0;
		this.ox2 = 0;
		this.oy2 = 0;
	}
	public GShape() {}
	public abstract GShape clone();
	
	public void draw(Graphics g) {
		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.draw(shape);
		if (this.anchors != null) {
			drawAnchors(graphics2D);
		} 
	}
	
	public abstract void drag(Graphics g, Graphics dbGraphics, Image doubleBuffering);
	
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
	
	public boolean onShape(int x, int y) {
		this.eSelectedAnchor = null;
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
	public abstract void move(int x, int y);
		
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
}
