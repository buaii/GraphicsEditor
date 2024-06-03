package shapeTools;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator; 

public class GPolygon extends GNPShape {	
	private static final long serialVersionUID = 1L;
	private int nx1;
	private int ny1;
	private int nx2;
	private int ny2;
	private int nox2;
	private int noy2;
	
	public GPolygon() {
		super(EDrawingStyle.eNPStyle, new Polygon());
	}
	
	public GPolygon(Shape shape) {
		super(EDrawingStyle.eNPStyle, shape);
	}
	
	@Override
	public GPolygon clone() {
		if (shape == null) {
			return new GPolygon();
		} else {
			GPolygon gPolygon = new GPolygon(this.shape);
			this.shape = null;
			return gPolygon;
		}
	}
	
	@Override 
	public void keepResize(int x, int y, boolean bool) {
		if (!bool) {
			movePoint(x, y);	
		} else {
			super.keepResize(x, y, true);
		}
		
//	    if (eSelectedAnchor == EAnchors.eSE) {
//	        Polygon polygon = (Polygon) this.shape;
//
//	        // 현재 다각형의 바운드 좌표와 크기
//	        int originalX = polygon.getBounds().x;
//	        int originalY = polygon.getBounds().y;
//	        int originalWidth = polygon.getBounds().width;
//	        int originalHeight = polygon.getBounds().height;
//
//	        // 마우스 이동으로 인해 변화된 새로운 우하단 모서리 좌표
//	        int newX = x;
//	        int newY = y;
//
//	        // 스케일 팩터 계산
//	        double scaleX = (newX - originalX) / (double) originalWidth;
//	        double scaleY = (newY - originalY) / (double) originalHeight;
//
//	        // 다각형의 좌상단 기준점
//	        int centerX = originalX;
//	        int centerY = originalY;
//
//	        // 각 꼭지점을 우하단 모서리 기준으로 스케일링
//	        for (int i = 0; i < polygon.npoints; i++) {
//	            polygon.xpoints[i] = (int) (centerX + (polygon.xpoints[i] - centerX) * scaleX);
//	            polygon.ypoints[i] = (int) (centerY + (polygon.ypoints[i] - centerY) * scaleY);
//	        }
//
//	        // 다각형을 다시 생성하여 위치 업데이트
//	        polygon.invalidate();
//
//	        // 이전 끝점 좌표 업데이트
//	        nox2 = x;
//	        noy2 = y;
//	    } else {
//	        movePoint(x, y);
//	    }
	}

	@Override
	public void drag(Graphics g, Graphics dbGraphics, Image doubleBuffering) {
		Graphics2D graphics2D = (Graphics2D) dbGraphics;
		if (shape instanceof Path2D) {
			// do nothing
		} else {
			Polygon polygon = (Polygon)this.shape;
			GeneralPath polyLine = new GeneralPath();

		    if (polygon.npoints > 0) {
		        polyLine.moveTo(polygon.xpoints[0], polygon.ypoints[0]);
		    }

		    for (int i = 1; i < polygon.npoints; i++) {
		        polyLine.lineTo(polygon.xpoints[i], polygon.ypoints[i]);
		    }
			graphics2D.draw(polyLine);
			g.drawImage(doubleBuffering, 0, 0, null);
	
		}
	}

	@Override
	public void setOrigin(int x, int y) {
		Polygon polygon = (Polygon)this.shape;
		polygon.reset();
	    polygon.addPoint(x, y);
	    polygon.addPoint(x,y);
	}
	
	@Override
	public void movePoint(int x, int y) {
		Polygon polygon = (Polygon)this.shape;
		polygon.xpoints[polygon.npoints-1] = x;
		polygon.ypoints[polygon.npoints-1] = y;
	}	
	
	@Override
	public void addPoint(int x, int y) {
	    Polygon polygon = (Polygon) this.shape;
	    polygon.addPoint(x,y);
	    this.nx1 = (int)this.shape.getBounds().getX();
	    this.ny1 = (int)this.shape.getBounds().getY();
	    this.nx2 = (int) (this.shape.getBounds().getWidth()+this.shape.getBounds().getX());
	    this.ny2 = (int) (this.shape.getBounds().getHeight()+this.shape.getBounds().getY());
	    this.nox2 = nx2;
		this.noy2 = ny2;
	}
	
	@Override
	public void startMove(int x, int y) {
		ox2 = x;
		oy2 = y;		
	}
}
