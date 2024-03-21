package shapeTools;
import java.awt.Graphics;

public class GRectangleTool extends GShapeTool {
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.drawRect(x, y, w, h);
	}
}
