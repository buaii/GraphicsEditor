package frames;
import javax.swing.JMenuBar;

import frames.GMainFrame.MenuActionHandler;
import menus.GMenuItem;

public class GMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	public GMenuItem fileMenu;
	public GMenuItem editMenu;
	
	public GMenuBar(MenuActionHandler menuActionHandler) {
		this.fileMenu = new GMenuItem("File", menuActionHandler);
		this.add(this.fileMenu);
		
		this.editMenu = new GMenuItem("Edit", menuActionHandler);
		this.add(this.editMenu);
	}

	public void initialize() {
		
	}

}
