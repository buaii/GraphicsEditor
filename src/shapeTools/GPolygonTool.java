package shapeTools;
import java.awt.Graphics;

public class GPolygonTool extends GShapeTool {
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		int[] arrX = {x, x+50, x+100, x+100, x+50, x};
		int[] arrY = {y, y-25, y, y+50, y+75, y+50};
		g.drawPolygon(arrX, arrY, 6);
	}

}
