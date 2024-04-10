package frames;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import global.Constants.EShapeButtons;

public class GMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private GMenuBar menubar;
	private GShapeToolBar shapeToolBar;
	private GDrawingPanel drawingPanel;
	
	// constructor
	public GMainFrame() {
		// set attribute 
		this.setSize(600, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// create components
//		LayoutManager layoutManager = new FlowLayout();
//		LayoutManager layoutManager = new CardLayout();
		BorderLayout layoutManager = new BorderLayout();
//		BoxLayout layoutManager = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		this.setLayout(layoutManager);
		
		this.menubar = new GMenuBar();
		this.setJMenuBar(this.menubar);
		
		ShapeActionHandler shapeActionHandler = new ShapeActionHandler();
		this.shapeToolBar = new GShapeToolBar(shapeActionHandler);
		this.add(shapeToolBar, BorderLayout.NORTH);
		
		
		this.drawingPanel = new GDrawingPanel();
		this.add(drawingPanel, BorderLayout.CENTER);
		
	}
	
	public void initialize() {
		this.menubar.initialize();
		this.shapeToolBar.initialize();
		this.drawingPanel.initialize();
	}
	
	public class ShapeActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EShapeButtons eShapeButton = EShapeButtons.valueOf(e.getActionCommand());
			drawingPanel.setShapeTool(eShapeButton.getShapeTool());						
		}
	}
	
}
