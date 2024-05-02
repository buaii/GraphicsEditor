package frames;
import javax.swing.JMenuBar;

import menus.GMenuItem;

public class GMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	public GMenuItem fileMenu;
	public GMenuItem editMenu;
	
	public GMenuBar() {
		this.fileMenu = new GMenuItem("File");
		this.add(this.fileMenu);
		
		this.editMenu = new GMenuItem("Edit");
		this.add(this.editMenu);
	}

	public void initialize(GDrawingPanel gDrawingPanel) {
		this.fileMenu.initialize(gDrawingPanel);
		this.editMenu.initialize(gDrawingPanel);
	}

}
