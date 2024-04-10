package shapeTools;

import java.awt.Graphics;
import java.awt.Image;

public class GLine extends G2PShape {
	
	public GLine() {
		super(EDrawingStyle.e2PStyle);
	}
	
	public GLine(int x1, int y1, int x2, int y2) {
		super(x1, y1, x2, y2);
	}

	@Override
	public void drag(Graphics g, Graphics dbGraphics, Image doubleBuffering) {
		/*Graphics2D graphics2D = (Graphics2D) g;
		// 그림이 없으면 그리고 있으면 지우는 도구
		graphics2D.setXORMode(graphics2D.getBackground());
		// erase old shape
		graphics2D.drawLine(x1, y1, ox2, oy2);
		// draw new shape
		graphics2D.drawLine(x1, y1, x2, y2);*/
		dbGraphics.drawLine(x1, y1, x2, y2);
		g.drawImage(doubleBuffering, 0, 0, null);
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawLine(x1, y1, x2, y2);
	}

	@Override
	public GShape clone() {
		return new GLine(this.x1, this.y1, this.x2, this.y2);
	}

	@Override
	public void addPoint(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
