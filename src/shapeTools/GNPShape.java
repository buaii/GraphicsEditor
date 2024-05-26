package shapeTools;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Shape;

public abstract class GNPShape extends GShape {
	private static final long serialVersionUID = 1L;
	
	protected int xPoints[];
	protected int yPoints[];
	protected int nPoints;

	public GNPShape(EDrawingStyle eNpstyle, Shape shape) {
		super(EDrawingStyle.eNPStyle, shape);
	}
	public GNPShape(int xP[], int yP[], int nP) {
		this.xPoints = xP.clone(); 
		this.yPoints = yP.clone();
		this.nPoints = nP;
	}
	@Override
	public void setOrigin(int x, int y) {
		
	}

	@Override
	public void movePoint(int x, int y) {
		
	}

	@Override
	public void addPoint(int x, int y) {
		
	}

	@Override
	public void drag(Graphics g, Graphics dbGraphics, Image doubleBuffering) {
		
	}
	
	@Override
	public GShape clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
