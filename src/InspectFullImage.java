package ScreenshotScript;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.Toolkit;

public class InspectFullImage extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel contentPane;
	private JTable tblFullImage;
	private ImageIcon tblImageFull = new ImageIcon();
	private Control theControl;
	private Label lblFileName;	
	private int CONST_DefaultScreenWidth = 1920;
	private int CONST_DefaultScreenHeight = 1080;

	public InspectFullImage(Control pControl) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	double lScreenWidth = (double) screenSize.getWidth();
    	double lScreenHeight = (double) screenSize.getHeight();
		
		double lWidthFactor = lScreenWidth / CONST_DefaultScreenWidth;
		double lHeightFactor = lScreenHeight / CONST_DefaultScreenHeight;
		double lFontFactor = lScreenHeight / CONST_DefaultScreenHeight;
		
		theControl = pControl;
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, (int) (1920 * lWidthFactor), (int) (1080 * lHeightFactor));
		setUndecorated(true);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBounds(0, 0, (int) (1920 * lWidthFactor), (int) (1080 * lHeightFactor));

        String[] columnNames  = { ""};
        Object[][] data =
        {
        	{tblImageFull},
        };
        
//Suppres Warnings to reduce Warnings	
		@SuppressWarnings("serial")
		DefaultTableModel modelTbl = new DefaultTableModel(data, columnNames)
        {
//Suppres Warnings to reduce Warnings			
            @SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
            public boolean isCellEditable(int rowIndex, int colIndex)
			{   
				return false;   
			}
        };
        
        tblFullImage = new JTable(modelTbl);
        tblFullImage.setRowMargin(0);
        tblFullImage.setShowVerticalLines(false);
        tblFullImage.setShowHorizontalLines(false);
        tblFullImage.setShowGrid(false);
        tblFullImage.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseEntered(MouseEvent arg0) {
        		theControl.setInFullImageTable(true);
        	}
        	@Override
        	public void mouseExited(MouseEvent e) {
        		theControl.setInFullImageTable(false);
        	}
        });
        
        lblFileName = new Label("Filename:");
        lblFileName.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
        lblFileName.setBounds((int) (1440 * lWidthFactor), (int) (1053 * lHeightFactor), (int) (480 * lWidthFactor), (int) (27 * lHeightFactor));
        contentPane.add(lblFileName);
        contentPane.add(tblFullImage);
		tblFullImage.setBounds(0, 0, (int) (1920 * lWidthFactor), (int) (1080 * lHeightFactor));
		tblFullImage.setRowHeight((int) (1080 * lHeightFactor));
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		tblFullImage.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESC");
		tblFullImage.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "LEFT");
		tblFullImage.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "RIGHT");
		tblFullImage.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LEFT");
		tblFullImage.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RIGHT");
		tblFullImage.getActionMap().put("ESC", new ActionEscape());
		tblFullImage.getActionMap().put("LEFT", new ActionLeft());
		tblFullImage.getActionMap().put("RIGHT", new ActionRight());
	}
	
	public JTable getTableFullImage()
	{
		return tblFullImage;
	}
	
	public ImageIcon getFullImage()
	{
		return tblImageFull;
	}
	
	public Label getLabelFileName() {
		return lblFileName;
	}
	
	 private class ActionEscape extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			theControl.pressedEscFullImage();
		}
	 }
	 
	 private class ActionLeft extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			theControl.pressedLeftFullImage();
		}
	 }
	 
	 private class ActionRight extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			theControl.pressedRightFullImage();
		}
	}
}
