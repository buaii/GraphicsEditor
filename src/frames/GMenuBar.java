package frames;
import javax.swing.JMenuBar;

import menus.GEditMenu;
import menus.GFileMenu;

public class GMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	public GFileMenu fileMenu;
	public GEditMenu editMenu;
	
	public GMenuBar() {
		this.fileMenu = new GFileMenu("File");
		this.add(this.fileMenu);
		
		this.editMenu = new GEditMenu("Edit");
		this.add(this.editMenu);
	}

}
