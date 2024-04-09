package shapeTools;

import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class GNPShape extends GShape {
	
	protected int xPoints[];
	protected int yPoints[];
	protected int nPoints;

	public GNPShape(EDrawingStyle enpstyle) {
		super(enpstyle);
	}
	public GNPShape(int xP[], int yP[], int nP) {
		this.xPoints = xP.clone();
		this.yPoints = yP.clone();
		this.nPoints = nP;
	}
	@Override
	public void setOrigin(int x, int y) {
		this.x1 = x;
		this.y1 = y;
		this.x2 = x;
		this.y2 = y;
		this.ox2 = x;
		this.oy2 = y;
		
		this.xPoints[nPoints] = x;
		this.yPoints[nPoints] = y;
		
		this.nPoints++;
		this.xPoints[nPoints] = x;
		this.yPoints[nPoints] = y;
	}

	@Override
	public void movePoint(int x, int y) {
		this.xPoints[nPoints] = x;
		this.yPoints[nPoints] = y;
		this.ox2 = this.x2;
		this.oy2 = this.y2;
		this.x2 = x;
		this.y2 = y;
	}

	@Override
	public void addPoint(int x, int y) {
		this.nPoints++;
		this.xPoints[nPoints] = x;
		this.yPoints[nPoints] = y;
		this.x1 = this.x2;
		this.y1 = this.y2;
	}

	@Override
	public void drag(Graphics g) {
		Graphics2D graphics2D = (Graphics2D) g;
		// 그림이 없으면 그리고 있으면 지우는 도구
		graphics2D.setXORMode(graphics2D.getBackground());
		// erase old shape
		graphics2D.drawLine(x1, y1, ox2, oy2);
		// draw new shape
		graphics2D.drawLine(x1, y1, x2, y2);
	}

	public abstract void draw(Graphics g);	

	@Override
	public GShape clone() {
		// TODO Auto-generated method stub
		return null;
	}
	public int[] getX() {
		return xPoints;
	}

}
