package menus;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import frames.GMainFrame.MenuActionHandler;
import global.Constants.EFileMenus;

public class GMenuItem extends JMenu {
	private static final long serialVersionUID = 1L;
	
	public GMenuItem(String s, MenuActionHandler menuActionHandler) {
		super(s);
		
		for (EFileMenus eFileMenu : EFileMenus.values()) {
			JMenuItem jMenuItem = new JMenuItem(eFileMenu.getText());
			jMenuItem.setActionCommand(eFileMenu.toString());
			jMenuItem.addActionListener(menuActionHandler);
			this.add(jMenuItem);
		}
	}

}
