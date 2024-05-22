package shapeTools;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Shape;
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
	
	public abstract void setOrigin(int x1, int y1); 
	public abstract void movePoint(int x2, int y2);	
	public abstract void addPoint(int x2, int y2);
	
	public abstract void drag(Graphics g, Graphics dbGraphics, Image doubleBuffering);
	public abstract void draw(Graphics g);	
	public abstract GShape clone();
	public int[] getX() {
		return null;
	}
	
	public boolean onShape(int x, int y) {
		return false;
	}
	
	public abstract void startMove(int x, int y);
	public abstract void move(int x, int y);
	
	
}
