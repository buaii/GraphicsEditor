package shapeTools;

import java.awt.geom.Ellipse2D;


public class GOval extends G2PShape {
	private static final long serialVersionUID = 1L;

	public GOval() {
		super(EDrawingStyle.e2PStyle, new Ellipse2D.Float(0,0,1,1));
	}

	@Override
	public GShape clone() {
		return new GOval();
	}
}
