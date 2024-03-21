package frames;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import global.Constants.EShapeButtons;

public class GShapeToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	public GShapeToolBar(GMainFrame.ShapeActionHandler shapeActionHandler) {
		ButtonGroup buttonGroup = new ButtonGroup();
		
		for (EShapeButtons eShapeButtons : EShapeButtons.values()) {
			JRadioButton button = new JRadioButton(eShapeButtons.getText());
			button.addActionListener(shapeActionHandler);
			button.setActionCommand(eShapeButtons.toString());
			add(button);
			buttonGroup.add(button);
		}
	}
}
