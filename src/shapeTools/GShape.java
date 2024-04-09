package shapeTools;
import java.awt.Graphics;

public abstract class GShape {
	public enum EDrawingStyle {
		e2PStyle,
		eNPStyle
	}
	private EDrawingStyle eDrawingStyle;
	protected int x1, y1, x2, y2, ox2, oy2;
	
	public EDrawingStyle getEDrawingStyle() {
		return this.eDrawingStyle;
	}
	public GShape(EDrawingStyle eDrawingStyle) {
		this.eDrawingStyle = eDrawingStyle;
		
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
	
	public abstract void drag(Graphics g);
	public abstract void draw(Graphics g);	
	public abstract GShape clone();
	public int[] getX() {
		return null;
	}
	
	
	
	
}
