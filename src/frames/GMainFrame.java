package frames;
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class GMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// components
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
		
		this.shapeToolBar = new GShapeToolBar();
		this.add(shapeToolBar, BorderLayout.NORTH);
		
		this.drawingPanel = new GDrawingPanel();
		this.add(drawingPanel, BorderLayout.CENTER);
		
		this.menubar = new GMenuBar();
		this.setJMenuBar(this.menubar);
		
		this.menubar.associate(this.drawingPanel);
		this.shapeToolBar.associate(this.drawingPanel);
	}
	
	// methods
	public void initialize() {
		this.menubar.initialize();
		this.shapeToolBar.initialize();
		this.drawingPanel.initialize();
	}
	
}
