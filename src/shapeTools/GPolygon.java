package shapeTools;
import java.awt.Graphics;
import java.util.Arrays;

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

	@Override
	public void startMove(int x, int y) {
		ox2 = x;
		oy2 = y;		
	}

	@Override
	public boolean onClicked(int x, int y) {
		int[] arrX = Arrays.copyOfRange(xPoints, 0, nPoints);
		int[] arrY = Arrays.copyOfRange(yPoints, 0, nPoints);
		Arrays.sort(arrX);
		Arrays.sort(arrY);
		
		int minX = arrX[0];
		int maxX = arrX[nPoints-1];
		int minY = arrY[0];
		int maxY = arrY[nPoints-1];
		
        return minX < x && x < maxX && minY < y && y < maxY;
	}
	
	@Override
	public void move(int x, int y) {
		for (int i = 0; i < nPoints+1; i++) {
			xPoints[i] += x - ox2;
        }
		
		for (int i = 0; i < nPoints+1; i++) {
			yPoints[i] += y - oy2;
        }
		ox2 = x;
		oy2 = y;
	}
}
