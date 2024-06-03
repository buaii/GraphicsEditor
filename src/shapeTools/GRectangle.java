package shapeTools;

import java.awt.Rectangle;

public class GRectangle extends G2PShape {
	private static final long serialVersionUID = 1L;
	
	public GRectangle() {
		super(EDrawingStyle.e2PStyle, new Rectangle(0,0,1,1));
	}
	
	@Override
	public GRectangle clone() {
		return new GRectangle();
	}	
}

