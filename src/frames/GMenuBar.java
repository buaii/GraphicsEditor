package frames;
import javax.swing.JMenuBar;

import global.Constants;
import menus.GFileMenu;

public class GMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	public GFileMenu fileMenu;
	public GFileMenu editMenu;
	
	public GMenuBar() {
		this.fileMenu = new GFileMenu(Constants.GMenubar.EMenu.eFile.getText());
		this.add(this.fileMenu);
		
		this.editMenu = new GFileMenu(Constants.GMenubar.EMenu.eEdit.getText());
		this.add(this.editMenu);
	}

	public void initialize() {
		
	}

	public void associate(GDrawingPanel drawingPanel) {
		this.fileMenu.associate(drawingPanel);
		this.editMenu.associate(drawingPanel);
	}
}
