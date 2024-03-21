package shapeTools;
import java.awt.Graphics;

public class GLineTool extends GShapeTool {

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		g.drawLine(x, y, w, h);
	}

}
