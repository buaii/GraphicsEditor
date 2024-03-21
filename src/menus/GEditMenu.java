package menus;
import javax.swing.Action;
import javax.swing.JMenu;

public class GEditMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	public GEditMenu() {
		// TODO Auto-generated constructor stub
	}

	public GEditMenu(String s) {
		super(s);
		
		GMenu Cut = new GMenu("Cut");
		GMenu Copy = new GMenu("Copy");
		GMenu Paste = new GMenu("Paste");
		
		this.add(Cut);
		this.add(Copy);
		this.add(Paste);
		// TODO Auto-generated constructor stub
	}

	public GEditMenu(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public GEditMenu(String s, boolean b) {
		super(s, b);
		// TODO Auto-generated constructor stub
	}

}
