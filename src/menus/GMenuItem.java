package menus;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import frames.GDrawingPanel;
import global.Constants.EFileMenus;

public class GMenuItem extends JMenu {
	private static final long serialVersionUID = 1L;
	
	private GDrawingPanel gDrawingPanel;
	
	public GMenuItem(String s) {
		super(s);
		
		for (EFileMenus eFileMenu : EFileMenus.values()) {
			MenuActionHandler menuActionHandler = new MenuActionHandler();
			JMenuItem jMenuItem = new JMenuItem(eFileMenu.getText());
			jMenuItem.setActionCommand(eFileMenu.toString());
			jMenuItem.addActionListener(menuActionHandler);
			this.add(jMenuItem);
		}
	}

	public void initialize(GDrawingPanel gDrawingPanel) {
		this.gDrawingPanel = gDrawingPanel;
	}

	public class MenuActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EFileMenus eFileMenu = EFileMenus.valueOf(e.getActionCommand());
			if (eFileMenu.ordinal() == 1) {
				try {
					gDrawingPanel.load();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if (eFileMenu.ordinal() == 2) {
				try {
					gDrawingPanel.save();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
