package shapeTools;
import java.awt.Graphics;

public class GOvalTool extends GShapeTool {

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		
		g.drawOval(x, y, w, h);
	}

}
