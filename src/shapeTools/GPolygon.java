package shapeTools;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class GPolygon extends GShape {
	private int[] arrX, arrY;
	private boolean isDrawing = false;
	private int vertex = 1;
	
	public GPolygon() {
		super(EDrawingStyle.eNPStyle);
	}
	public GPolygon(int[] arrX, int[] arrY, int vertex) {
		this.arrX = arrX;
		this.arrY = arrY;
		this.vertex = vertex;
	}
	
	public void setP(int x, int y) {
		this.x1 = x;
		this.y1 = y;
	}
	public void setP1(int x1, int y1) {
		if (this.x1 == 0) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x1;
			this.y2 = y1;
			this.ox2 = x1;
			this.oy2 = y1;
			
			int[] newArrX = new int[1];
			int[] newArrY = new int[1];
			
			newArrX[0] = x1;
			newArrY[0] = y1;
			
			this.arrX = newArrX;
			this.arrY = newArrY;
		} else {
			this.x1 = x1;
			this.y1 = y1;
		}
		
	}
	
	public void setP2(int x2, int y2) {
		this.ox2 = this.x2;
		this.oy2 = this.y2;
		this.x2 = x2;
		this.y2 = y2;
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
	
	@Override
	public void draw(Graphics g) {
		g.drawPolygon(arrX, arrY, vertex);
	}
	
	@Override
	public GPolygon clone() {
		return new GPolygon(arrX, arrY, vertex);
	}
	@Override
	public void setDrawing(boolean bool) {
		this.isDrawing = bool;
	}
	@Override
	public boolean isDrawing(int x, int y) {
		if (arrX == null)
			return this.isDrawing;
		if (x == arrX[arrX.length-1] && y == arrY[arrY.length-1])
			return true;
		return false;
	}
	@Override
	public void addPoint(int x, int y) {
		int[] newArrX = new int[arrX.length+1];
		int[] newArrY = new int[arrY.length+1];
		
		System.arraycopy(arrX, 0, newArrX, 0, arrX.length);
		System.arraycopy(arrY, 0, newArrY, 0, arrY.length);
		newArrX[arrX.length] = x2;
		newArrY[arrY.length] = y2;
		
		this.arrX = newArrX;
		this.arrY = newArrY;
		
		vertex += 1;
	}
}
