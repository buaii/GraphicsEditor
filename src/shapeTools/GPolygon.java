package shapeTools;
import java.awt.Graphics;

import global.Constants;


public class GPolygon extends GNPShape {	
	private static final long serialVersionUID = 1L;

	public GPolygon() {
		super(EDrawingStyle.eNPStyle);
		this.xPoints = new int[Constants.NUM_POINTS];
		this.yPoints = new int[Constants.NUM_POINTS];
		this.nPoints = 0;
	}
	
	public GPolygon(int xP[], int yP[], int nP) {
		super(xP, yP, nP);
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawPolygon(xPoints, yPoints, nPoints);
	}
	
	@Override
	public GPolygon clone() {
		return new GPolygon(this.xPoints, this.yPoints, nPoints);
	}
}
