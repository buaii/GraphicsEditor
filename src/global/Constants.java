package global;

import shapeTools.GLine;
import shapeTools.GOval;
import shapeTools.GPolygon;
import shapeTools.GRectangle;
import shapeTools.GShape;

public class Constants {
	public enum EShapeButtons {
		eRectangle("rectangle", new GRectangle(),"src/global/rect.png"),
		eOval("oval", new GOval(),"src/global/oval.png"),
		eLine("line", new GLine(),"src/global/line.png"),
		ePolygon("polygon", new GPolygon(),"src/global/polygon.png");
		
		private String text;
		private GShape shapeTool;
		private String imagePath;
		private EShapeButtons(String text, GShape shapeTool, String imagePath){
			this.text = text;
			this.shapeTool = shapeTool;
			this.imagePath = imagePath;
		}
		
		public String getText() { return this.text; }
		public GShape getShapeTool() { return this.shapeTool; }
		public String getImage() { return this.imagePath; }
		
	}
	
	public static final int NUM_POINTS = 20;
	
	public static class GMainFrame {
		public final static int WIDTH = 400;
		public final static int HEIGHT = 600;
	}
	
	public static class GMenubar {
		public enum EMenu {
			eFile("File"),
			eEdit("Edit");
			
			private String text;
			private EMenu(String text) {
				this.text = text;
			}
			public String getText() { return this.text; }
		}
		
		public enum EFileMenuItem {
			eNew("New"),
			eLoad("Open"),
			eSave("Save"),
			eSaveAs("Save As");
			
			private String text;
			private EFileMenuItem(String text) {
				this.text = text;
			}
			public String getText() { return this.text; }
			
		}
	}
}
