package shapeTools;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class GRectangle extends GShape {
	
	public GRectangle() {
	}
	public GRectangle(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	@Override
	public void drawing(Graphics g) {
		Graphics2D graphics2D = (Graphics2D) g;
		// 그림이 없으면 그리고 있으면 지우는 도구
		graphics2D.setXORMode(graphics2D.getBackground());
		// erase old shape
		g.drawRect(Math.min(x1, ox2), Math.min(y1, oy2), Math.abs(ox2-x1), Math.abs(oy2-y1));
		// draw new shape
		g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2-x1), Math.abs(y2-y1));
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2-x1), Math.abs(y2-y1));
	}
	
	@Override
	public GShape getShape() {
		return new GRectangle(this.x1, this.y1, this.x2, this.y2);
	}
}
