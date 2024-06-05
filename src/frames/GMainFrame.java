package frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import global.Constants;

public class GMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// components
	private GMenuBar menubar;
	private GShapeToolBar shapeToolBar;
	private GColorToolBar colorToolBar;
	private GDrawingPanel drawingPanel;
	
	// constructor
	public GMainFrame() {
		// set attribute 
		this.setSize(Constants.GMainFrame.WIDTH, Constants.GMainFrame.HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - Constants.GMainFrame.WIDTH) / 2;
        int y = (screenSize.height - Constants.GMainFrame.HEIGHT) / 2;
        this.setLocation(x, y);
        
		// create components
		BorderLayout layoutManager = new BorderLayout(); 
		this.setLayout(layoutManager);
		
		JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        
		this.shapeToolBar = new GShapeToolBar();
//		this.add(shapeToolBar, BorderLayout.NORTH);
		shapeToolBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, shapeToolBar.getPreferredSize().height));
		topPanel.add(shapeToolBar);
		
		this.colorToolBar = new GColorToolBar();
//		this.add(colorToolBar, BorderLayout.NORTH);
		colorToolBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, colorToolBar.getPreferredSize().height));
		topPanel.add(colorToolBar);
		this.add(topPanel, BorderLayout.NORTH);
		
		this.drawingPanel = new GDrawingPanel();
		this.add(drawingPanel, BorderLayout.CENTER);
		
		this.menubar = new GMenuBar();
		this.setJMenuBar(this.menubar);
		
		this.menubar.associate(this.drawingPanel);
		this.shapeToolBar.associate(this.drawingPanel);
		this.colorToolBar.associate(this.drawingPanel);
	}
	
	// methods
	public void initialize() {
		this.menubar.initialize();
		this.shapeToolBar.initialize();
		this.drawingPanel.initialize();
	}
	
}
