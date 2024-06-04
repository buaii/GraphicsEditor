package menus;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import frames.GDrawingPanel;
import global.Constants.GMenubar.EFileMenuItem;
import shapeTools.GShape;

public class GFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	private GDrawingPanel drawingPanel;
	
	public GFileMenu(String s) {
		super(s);
		
		for (EFileMenuItem eFileMenu : EFileMenuItem.values()) {
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
	public void open() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\Buaii\\eclipse-workspace\\ClassProject"));


        int option = fileChooser.showOpenDialog(drawingPanel);
        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();

                if (!filePath.toLowerCase().endsWith(".ser")) {
                    JOptionPane.showMessageDialog(drawingPanel, "올바른 객체 직렬화 파일(.ser)을 선택해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (FileInputStream fileIn = new FileInputStream(filePath);
                     BufferedInputStream bufferedIn = new BufferedInputStream(fileIn);
                     ObjectInputStream objectIn = new ObjectInputStream(bufferedIn)) {

                    Object object = objectIn.readObject();
                    if (object instanceof Vector) {
                        Vector<GShape> shapes = (Vector<GShape>) object;
                        drawingPanel.setShape(shapes); // 가져온 도형 정보를 DrawingPanel에 설정합니다.
                        drawingPanel.setDB();
                        drawingPanel.paint(drawingPanel.getGraphics());
                        JOptionPane.showMessageDialog(drawingPanel, "파일에서 객체를 성공적으로 불러왔습니다.");
                    } else {
                        JOptionPane.showMessageDialog(drawingPanel, "잘못된 객체 유형입니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (ClassNotFoundException | IOException ex) {
                    JOptionPane.showMessageDialog(drawingPanel, "파일을 열 때 오류가 발생했습니다: " + ex.getMessage(),
                            "오류", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(drawingPanel, "파일을 선택하는 중 오류가 발생했습니다: " + ex.getMessage(),
                        "오류", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            // 사용자가 파일 선택을 취소한 경우
        	JOptionPane.showMessageDialog(drawingPanel, "파일을 선택하는 중 오류가 발생했습니다: ");
        }
	}
	
	public void clear() {
		this.drawingPanel.clear();
	}
	
	public void saveAs() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\Buaii\\eclipse-workspace\\ClassProject"));

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Serialized Objects (*.ser)", "ser");
        fileChooser.setFileFilter(filter);

        int option = fileChooser.showSaveDialog(drawingPanel);
        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = fileChooser.getSelectedFile();

                String filePath = selectedFile.getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".ser")) {
                    filePath += ".ser"; 
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath);
                     BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOut);
                     ObjectOutputStream objectOut = new ObjectOutputStream(bufferedOut)) {

                    objectOut.writeObject(this.drawingPanel.getShape());
                    JOptionPane.showMessageDialog(drawingPanel, "객체가 파일에 성공적으로 저장되었습니다.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(drawingPanel, "파일을 저장하는 중 오류가 발생했습니다: " + ex.getMessage(),
                            "오류", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(drawingPanel, "파일을 선택하는 중 오류가 발생했습니다: " + ex.getMessage(),
                        "오류", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
	}
	
	public class MenuActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EFileMenuItem eFileMenu = EFileMenuItem.valueOf(e.getActionCommand());
			if (eFileMenu.ordinal() == 0) {
				clear();
			} else if (eFileMenu.ordinal() == 1) {
				try {
					open();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (eFileMenu.ordinal() == 2) {
				try {
					save();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (eFileMenu.ordinal() == 3) {
				saveAs();
			}
		}
	}
}
