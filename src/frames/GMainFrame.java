package frames;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import global.Constants.EShapeButtons;


public class GMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private GMenuBar menubar;
	private GShapeToolBar shapeToolBar;
	private GDrawingPanel drawingPanel;
	
	// constructor
	public GMainFrame() {
		this.setSize(600, 800);
		
//		LayoutManager layoutManager = new FlowLayout();
//		LayoutManager layoutManager = new CardLayout();
//		LayoutManager layoutManager = new BorderLayout();
		BoxLayout layoutManager = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		this.setLayout(layoutManager);
		
		this.menubar = new GMenuBar();
		this.setJMenuBar(this.menubar);
		
		ShapeActionHandler shapeActionHandler = new ShapeActionHandler();
		this.shapeToolBar = new GShapeToolBar(shapeActionHandler);
		this.add(shapeToolBar);
		
		
		this.drawingPanel = new GDrawingPanel();
		this.add(drawingPanel);
		
	}
	
	public class ShapeActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EShapeButtons eShapeButton = EShapeButtons.valueOf(e.getActionCommand());
			drawingPanel.setShapeTool(eShapeButton.getShapeTool());						
		}
	}
	
	
}
