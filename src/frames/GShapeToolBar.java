package frames;

import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import global.Constants.EShapeButtons;

public class GShapeToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	public GShapeToolBar(GMainFrame.ShapeActionHandler shapeActionHandler) {
		ButtonGroup buttonGroup = new ButtonGroup();
		
		for (EShapeButtons eShapeButtons : EShapeButtons.values()) {
			ImageIcon icon = new ImageIcon(eShapeButtons.getImage());
			Image originImage = icon.getImage();
			Image resizedImage = originImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			Image selectedImage = originImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			ImageIcon resizedIcon = new ImageIcon(resizedImage);
			ImageIcon selectedIcon = new ImageIcon(selectedImage);
			JRadioButton button = new JRadioButton(resizedIcon);
			button.setSelectedIcon(selectedIcon);
			button.addActionListener(shapeActionHandler);
			button.setActionCommand(eShapeButtons.toString());
			add(button);
			buttonGroup.add(button);
		}
		
	}

	public void initialize() {
		JRadioButton defaultButton 
			= (JRadioButton) (this.getComponent(EShapeButtons.eRectangle.ordinal()));
		defaultButton.doClick();
	}
}
