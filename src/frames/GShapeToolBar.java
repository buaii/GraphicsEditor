package frames;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import global.Constants.EShapeButtons;

public class GShapeToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	private GDrawingPanel drawingPanel;
	
	public GShapeToolBar() {
		ButtonGroup buttonGroup = new ButtonGroup();
		ShapeActionHandler shapeActionHandler = new ShapeActionHandler();
		
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
	
	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	private void setShapeTool(EShapeButtons eShapeButton) {
		this.drawingPanel.setShapeTool(eShapeButton.getShapeTool());		
	}
	
	private class ShapeActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EShapeButtons eShapeButton = EShapeButtons.valueOf(e.getActionCommand());
			setShapeTool(eShapeButton);
		}
	}
}
