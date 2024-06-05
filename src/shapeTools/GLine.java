package shapeTools;

import java.awt.geom.Line2D;


public class GLine extends G2PShape {
	private static final long serialVersionUID = 1L;

	public GLine() {
		super(EDrawingStyle.e2PStyle, new Line2D.Float(0,0,1,1));
	}
	
	@Override
	public GShape clone() {
		return new GLine();
	}
}
