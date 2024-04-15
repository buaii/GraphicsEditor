package frames;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import shapeTools.GShape;
import shapeTools.GShape.EDrawingStyle;

public class GDrawingPanel extends JPanel {
	// attributes
	private static final long serialVersionUID = 1L;
	private enum EDrawingState {
		eIdle,
		e2PState,
		eNPState
	} 
	private EDrawingState eDrawingState;
	
	// components
	private Vector<GShape> shapes;
	private GShape shapeTool;
	private GShape currentShape;
	private Image doubleBuffering;
	private Graphics dbGraphics;
	
	// constructors
	public GDrawingPanel() {
		MouseEventHandler mouseEventHandler = new MouseEventHandler();
		this.addMouseListener(mouseEventHandler);
		this.addMouseMotionListener(mouseEventHandler);
		
		this.eDrawingState = EDrawingState.eIdle;
		this.shapes = new Vector<GShape>();
	}
	
	public void initialize() {
		this.doubleBuffering = createImage(getWidth(), getHeight());
		this.dbGraphics = this.doubleBuffering.getGraphics();
	}	
	
	// setters and getters
	public void setDB() {
		this.initialize();
		this.paint(getGraphics());
	}
	
	public void setShapeTool(GShape shapeTool) {
		this.shapeTool = shapeTool;		
	}
	
	// methods
	public void save() throws IOException {
        // 파일 저장 다이얼로그 생성
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml");
            }

            public String getDescription() {
                return "PNG 파일(*.png)";
            }
        });
        // 다이얼로그를 보여주고 사용자가 파일을 저장할 경로를 선택하도록 함
        int result = fileChooser.showSaveDialog(null);

        // 사용자가 확인 버튼을 눌렀을 때
        if (result == JFileChooser.APPROVE_OPTION) {
            // 선택한 파일 가져오기
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            // 확장자가 .png인지 확인
            if (!filePath.toLowerCase().endsWith(".png")) {
                filePath += ".png";
            }

            // 이미지로 객체 저장
            BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            this.paint(g);
            g.dispose();

            // 이미지를 파일로 저장
            File file = new File(filePath);
            ImageIO.write(image, "png", file);

            JOptionPane.showMessageDialog(null, "파일이 저장되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
        } else {
        	JOptionPane.showMessageDialog(null, "파일저장이 취소되었습니다.", "취소", JOptionPane.INFORMATION_MESSAGE);
        }
	}
	
	public void paint(Graphics graphics) {	
		if (!shapes.isEmpty()) {
			for (GShape g : shapes) {
				g.draw(dbGraphics);
			}
		}		
		graphics.drawImage(doubleBuffering, 0, 0, null);
	}
	
	private void startDrawing(int x, int y) {
		currentShape = shapeTool.clone();
		currentShape.setOrigin(x, y);
	}
	
	private void keepDrawing(int x, int y) {
		currentShape.movePoint(x, y);
		setDB();
		currentShape.drag(getGraphics(), dbGraphics, doubleBuffering);
	}
	
	private void continueDrawing(int x, int y) {
		currentShape.addPoint(x, y);
	}
	
	private void stopDrawing(int x, int y) {
		currentShape.addPoint(x, y);
		currentShape.draw(getGraphics());
		shapes.add(currentShape.clone());
		setDB();
	}
	
	private class MouseEventHandler implements MouseListener, MouseMotionListener {		
		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				if (shapeTool.getEDrawingStyle() == EDrawingStyle.e2PStyle) {
					startDrawing(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2PState;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PState) {
				keepDrawing(e.getX(), e.getY());
				eDrawingState = EDrawingState.e2PState;
			}
			
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PState){
				stopDrawing(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if (shapeTool.getEDrawingStyle() == EDrawingStyle.eNPStyle) {
				if (e.getClickCount() == 1) {
					if (eDrawingState == EDrawingState.eIdle) {
						startDrawing(e.getX(), e.getY());
						eDrawingState = EDrawingState.eNPState;
					} else if (eDrawingState == EDrawingState.eNPState) {
						continueDrawing(e.getX(), e.getY());
		            	eDrawingState = EDrawingState.eNPState;
					}
				} else if (e.getClickCount() == 2) {
					if (eDrawingState == EDrawingState.eNPState) {
						stopDrawing(e.getX(), e.getY());
		            	eDrawingState = EDrawingState.eIdle;
					}
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPState) {
				keepDrawing(e.getX(), e.getY());
				eDrawingState = EDrawingState.eNPState;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		
	}  
}
