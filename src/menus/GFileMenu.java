package menus;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import frames.GDrawingPanel;
import global.Constants.EFileMenus;
import shapeTools.GShape;

public class GFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	private GDrawingPanel drawingPanel;
	
	public GFileMenu(String s) {
		super(s);
		
		for (EFileMenus eFileMenu : EFileMenus.values()) {
			MenuActionHandler menuActionHandler = new MenuActionHandler();
			JMenuItem jMenuItem = new JMenuItem(eFileMenu.getText());
			jMenuItem.setActionCommand(eFileMenu.toString());
			jMenuItem.addActionListener(menuActionHandler);
			this.add(jMenuItem);
		}
	}

	public void initialize() {
		
	}
	
	public void associate(GDrawingPanel gDrawingPanel) {
		this.drawingPanel = gDrawingPanel;
	}

	public void save() throws IOException {
		try {
            FileOutputStream fileOut = new FileOutputStream("shapes.ser");
            ObjectOutputStream out = new ObjectOutputStream(
            		new BufferedOutputStream(fileOut));
            out.writeObject(this.drawingPanel.getShape());
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in shapes.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@SuppressWarnings("unchecked")
	public void load() throws IOException {
        try {
            FileInputStream fileIn = new FileInputStream("shapes.ser");
            ObjectInputStream in = new ObjectInputStream(
            		new BufferedInputStream(fileIn));
            Object object = (Vector<GShape>) in.readObject();
            this.drawingPanel.setShape(object);
            in.close();
            fileIn.close();
            this.paint(getGraphics());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
	}
	
	
	public class MenuActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EFileMenus eFileMenu = EFileMenus.valueOf(e.getActionCommand());
			if (eFileMenu.ordinal() == 1) {
				try {
					load();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if (eFileMenu.ordinal() == 2) {
				try {
					save();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
