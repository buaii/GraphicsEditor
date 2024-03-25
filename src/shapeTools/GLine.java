package shapeTools;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class GLine extends GShape {
	
	public GLine() {
	}
	
	public GLine(int x1, int y1, int x2, int y2) {
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
		g.drawLine(x1, y1, ox2, oy2);
		// draw new shape
		g.drawLine(x1, y1, x2, y2);
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawLine(x1, y1, x2, y2);
	}




	@Override
	public GShape getShape() {
		return new GLine(this.x1, this.y1, this.x2, this.y2);
	}

}
