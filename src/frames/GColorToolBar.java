package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import global.Constants.EColorButtons;

public class GColorToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	private GDrawingPanel drawingPanel;
	
	public GColorToolBar() {
		ShapeActionHandler shapeActionHandler = new ShapeActionHandler();
		
		for (EColorButtons eColorButtons : EColorButtons.values()) {
			JRadioButton button = new JRadioButton(eColorButtons.getText());
			button.addActionListener(shapeActionHandler);
			button.setActionCommand(eColorButtons.toString());
			add(button);
		}
		
	}

	public void initialize() {
		
	}
	
	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	private void setColor(EColorButtons eColorButton) {
		this.drawingPanel.setColor(eColorButton.getText());
	}
	
	private class ShapeActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EColorButtons eColorButton = EColorButtons.valueOf(e.getActionCommand());
			setColor(eColorButton);
		}
	}
}
