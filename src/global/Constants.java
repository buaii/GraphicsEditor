package global;

import shapeTools.GLine;
import shapeTools.GOval;
import shapeTools.GPolygon;
import shapeTools.GRectangle;
import shapeTools.GShape;

public class Constants {
	public enum EShapeButtons {
		eRectangle("rectangle", new GRectangle()),
		eOval("oval", new GOval()),
		eLine("line", new GLine()),
		ePolygon("polygon", new GPolygon());
		
		private String text;
		private GShape shapeTool;
		private EShapeButtons(String text, GShape shapeTool){
			this.text = text;
			this.shapeTool = shapeTool;
		}
		
		public String getText() { return this.text; }
		public GShape getShapeTool() { return this.shapeTool; }
		
	}

	public static final int NUM_POINTS = 20;
}
