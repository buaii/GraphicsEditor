package shapeTools;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.io.Serializable;
import java.util.Vector;

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
	protected Vector<Rectangle> anchors;
	protected int x1, y1, x2, y2, ox2, oy2;
	
	public GShape(EDrawingStyle eDrawingStyle, Shape shape) {
		this.eDrawingStyle = eDrawingStyle;
		this.shape = shape;
		
		this.x1 = 0;
		this.y1 = 0;
		this.x2 = 0;
		this.y2 = 0;
		this.ox2 = 0;
		this.oy2 = 0;
	}
	public GShape() {}
	
	public void draw(Graphics g) {
		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.draw(shape);
		if (onAnchor) {
			drawAnchor(graphics2D);
		} 
	}
	
	public abstract void drag(Graphics g, Graphics dbGraphics, Image doubleBuffering);
	
	public abstract GShape clone();
	
	public boolean onShape(int x, int y) {
		return false;
	}

	public abstract void setOrigin(int x1, int y1); 
	public abstract void movePoint(int x2, int y2);	
	public abstract void addPoint(int x2, int y2);
	
	public abstract void startMove(int x, int y);
	public abstract void move(int x, int y);
	public void onAnchor() {
		onAnchor = true;
	}
	public void offAnchor() {
		onAnchor = false;
	}
	
	public void setAnchor() {
		box = shape.getBounds();
		anchors = new Vector<>();
        int size = 6;
        int halfSize = size / 2;

        anchors.add(new Rectangle(box.x - halfSize, box.y - halfSize, size, size));
        anchors.add(new Rectangle(box.x + box.width / 2 - halfSize, box.y - halfSize, size, size));
        anchors.add(new Rectangle(box.x + box.width - halfSize, box.y - halfSize, size, size));
        anchors.add(new Rectangle(box.x - halfSize, box.y + box.height / 2 - halfSize, size, size));
        anchors.add(new Rectangle(box.x + box.width - halfSize, box.y + box.height / 2 - halfSize, size, size));
        anchors.add(new Rectangle(box.x - halfSize, box.y + box.height - halfSize, size, size));
        anchors.add(new Rectangle(box.x + box.width / 2 - halfSize, box.y + box.height - halfSize, size, size));
        anchors.add(new Rectangle(box.x + box.width - halfSize, box.y + box.height - halfSize, size, size));
	}
	
	public void drawAnchor(Graphics2D graphics2D) {
		if (box == null || anchors == null) {
			setAnchor();
		} else {
			Stroke originalStroke = graphics2D.getStroke();
			graphics2D.setColor(Color.GRAY);
			graphics2D.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3}, 0));
			graphics2D.draw(box);
			graphics2D.setStroke(originalStroke);
			
            for (Rectangle anchor : anchors) {
            	graphics2D.setColor(Color.WHITE);
            	graphics2D.fill(anchor);
            	graphics2D.setColor(Color.BLACK);
            	graphics2D.draw(anchor);
            }
		}
	}
		
	public void moveAnchor(int x, int y) {
		box.setFrame(box.getX() + x - ox2, box.getY() + y - oy2, box.getWidth(), box.getHeight());
		for (Rectangle anchor : anchors) {
			anchor.setFrame(anchor.getX() + x - ox2, anchor.getY() + y - oy2, anchor.getWidth(), anchor.getHeight());
		}
		
	}
}
