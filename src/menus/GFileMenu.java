package menus;
import javax.swing.JMenu;

public class GFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	public GFileMenu(String s) {
		super(s);
		
		GMenu New = new GMenu("New");
		GMenu Save = new GMenu("Save");
		GMenu SaveAs = new GMenu("Save As");
		
		this.add(New);
		this.add(Save);
		this.add(SaveAs);
	}

}
