package shapeTools;

import java.awt.Graphics;

public abstract class G2PShape extends GShape {
	
	public G2PShape(EDrawingStyle eDrawingStyle) {
		super(EDrawingStyle.e2PStyle);
	}
	public G2PShape(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public abstract void drag(Graphics g);
	public abstract void draw(Graphics g);	
	
	@Override
	public void setOrigin(int x, int y) {
		this.x1 = x;
		this.y1 = y;
		
		this.ox2 = x;
		this.oy2 = y;
		this.x2 = x;
		this.y2 = y;
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
