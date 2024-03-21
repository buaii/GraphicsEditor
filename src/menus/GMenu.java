package menus;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

public class GMenu extends JMenuItem {
	private static final long serialVersionUID = 1L;

	public GMenu() {
		// TODO Auto-generated constructor stub
	}

	public GMenu(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public GMenu(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public GMenu(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public GMenu(String text, Icon icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}

	public GMenu(String text, int mnemonic) {
		super(text, mnemonic);
		// TODO Auto-generated constructor stub
	}

}
