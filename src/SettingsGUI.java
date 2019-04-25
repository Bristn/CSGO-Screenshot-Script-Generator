package ScreenshotScript;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SettingsGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnApply;
//Suppres Warnings to reduce Warnings	
	@SuppressWarnings("rawtypes")
	private JList listSelectionColor;
//Suppres Warnings to reduce Warnings	
	@SuppressWarnings("rawtypes")
	private JList listSwitchingColor;
	private JRadioButton rdbtnLongDelay;
	private JRadioButton rdbtnDefaultDelay;
	private JRadioButton rdbtnShortDelay;
	private JRadioButton checkBoxMode1;
	private JRadioButton checkBoxMode2;
	private JRadioButton checkBoxMode3;

	private String aUnSelectedIcon = "\u25CF ";
	private int CONST_DefaultScreenWidth = 1920;
	private int CONST_DefaultScreenHeight = 1080;

	private Control theMainControl;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField txtDefaultX;
	private JTextField txtDefaultY;
	private JTextField txtDefaultZ;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();

//Suppres Warnings to reduce Warnings	
	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	public SettingsGUI(Control pControl) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double lScreenWidth = (double) screenSize.getWidth();
		double lScreenHeight = (double) screenSize.getHeight();

		double lWidthFactor = lScreenWidth / CONST_DefaultScreenWidth;
		double lHeightFactor = lScreenHeight / CONST_DefaultScreenHeight;
		double lFontFactor = lScreenHeight / CONST_DefaultScreenHeight;

		setIconImage(Toolkit.getDefaultToolkit().getImage(SettingsGUI.class.getResource("/settings.png")));
		setTitle("Settings");
		theMainControl = pControl;
		setResizable(false);
		setBounds((int) (100 * lWidthFactor), (int) (100 * lHeightFactor), (int) (630 * lWidthFactor),
				(int) (384 * lHeightFactor));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds((int) (10 * lWidthFactor), (int) (11 * lHeightFactor), (int) (120 * lWidthFactor),
				(int) (323 * lHeightFactor));
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Selection color:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		lblNewLabel.setBounds((int) (10 * lWidthFactor), (int) (10 * lHeightFactor), (int) (100 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel.add(lblNewLabel);

		listSelectionColor = new JList();
		listSelectionColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				String pColor = (String) listSelectionColor.getSelectedValue();
				theMainControl.selectionColorChangedTo(pColor);
			}
		});
		listSelectionColor.setBackground(SystemColor.control);
		listSelectionColor.setFixedCellHeight((int) (25 * lHeightFactor));
		listSelectionColor.setModel(new AbstractListModel() {
			String[] values = new String[] { aUnSelectedIcon + "red", aUnSelectedIcon + "pink",
					aUnSelectedIcon + "purple", aUnSelectedIcon + "blue", aUnSelectedIcon + "cyan",
					aUnSelectedIcon + "green", aUnSelectedIcon + "yellow", aUnSelectedIcon + "orange",
					aUnSelectedIcon + "white", aUnSelectedIcon + "grey", aUnSelectedIcon + "black" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		listSelectionColor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSelectionColor.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		listSelectionColor.setBounds((int) (10 * lWidthFactor), (int) (40 * lHeightFactor), (int) (100 * lWidthFactor),
				(int) (272 * lHeightFactor));
		panel.add(listSelectionColor);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setBounds((int) (488 * lWidthFactor), (int) (269 * lHeightFactor), (int) (116 * lWidthFactor),
				(int) (65 * lHeightFactor));
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theMainControl.clickedApplySettings();
			}
		});
		btnApply.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		btnApply.setBounds((int) (10 * lWidthFactor), (int) (11 * lHeightFactor), (int) (96 * lWidthFactor),
				(int) (43 * lHeightFactor));
		panel_1.add(btnApply);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_3.setBounds((int) (140 * lWidthFactor), (int) (11 * lHeightFactor), (int) (120 * lWidthFactor),
				(int) (323 * lHeightFactor));
		contentPane.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblDesiredScreenshotPrefix = new JLabel("Switching color");
		lblDesiredScreenshotPrefix.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		lblDesiredScreenshotPrefix.setBounds((int) (10 * lWidthFactor), (int) (11 * lHeightFactor),
				(int) (197 * lWidthFactor), (int) (30 * lHeightFactor));
		panel_3.add(lblDesiredScreenshotPrefix);

		listSwitchingColor = new JList();
		listSwitchingColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				String pColor = (String) listSwitchingColor.getSelectedValue();
				theMainControl.switchingColorChangedTo(pColor);
			}
		});
		listSwitchingColor.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		listSwitchingColor.setBackground(SystemColor.control);
		listSwitchingColor.setBounds((int) (10 * lWidthFactor), (int) (40 * lHeightFactor), (int) (100 * lWidthFactor),
				(int) (272 * lHeightFactor));
		listSwitchingColor.setFixedCellHeight((int) (25 * lHeightFactor));
		listSwitchingColor.setModel(new AbstractListModel() {
			String[] values = new String[] { aUnSelectedIcon + "red", aUnSelectedIcon + "pink",
					aUnSelectedIcon + "purple", aUnSelectedIcon + "blue", aUnSelectedIcon + "cyan",
					aUnSelectedIcon + "green", aUnSelectedIcon + "yellow", aUnSelectedIcon + "orange",
					aUnSelectedIcon + "white", aUnSelectedIcon + "grey", aUnSelectedIcon + "black" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		panel_3.add(listSwitchingColor);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds((int) (270 * lWidthFactor), (int) (11 * lHeightFactor), (int) (208 * lWidthFactor),
				(int) (323 * lHeightFactor));
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_4.setBounds((int) (0 * lWidthFactor), (int) (171 * lHeightFactor), (int) (208 * lWidthFactor),
				(int) (152 * lHeightFactor));
		panel_2.add(panel_4);
		panel_4.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Delay between screenshots:\r\n");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		lblNewLabel_1.setBounds((int) (10 * lWidthFactor), (int) (11 * lHeightFactor), (int) (189 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_4.add(lblNewLabel_1);

		rdbtnShortDelay = new JRadioButton("Short");
		rdbtnShortDelay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theMainControl.settingsScreenshotDelayChanged("Short");
			}
		});
		buttonGroup.add(rdbtnShortDelay);
		rdbtnShortDelay.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		rdbtnShortDelay.setBounds((int) (20 * lWidthFactor), (int) (44 * lHeightFactor), (int) (109 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_4.add(rdbtnShortDelay);

		rdbtnDefaultDelay = new JRadioButton("Default");
		rdbtnDefaultDelay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theMainControl.settingsScreenshotDelayChanged("Default");
			}
		});
		buttonGroup.add(rdbtnDefaultDelay);
		rdbtnDefaultDelay.setSelected(true);
		rdbtnDefaultDelay.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		rdbtnDefaultDelay.setBounds((int) (20 * lWidthFactor), (int) (77 * lHeightFactor), (int) (109 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_4.add(rdbtnDefaultDelay);

		rdbtnLongDelay = new JRadioButton("Long");
		rdbtnLongDelay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theMainControl.settingsScreenshotDelayChanged("Long");
			}
		});
		buttonGroup.add(rdbtnLongDelay);
		rdbtnLongDelay.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		rdbtnLongDelay.setBounds((int) (20 * lWidthFactor), (int) (110 * lHeightFactor), (int) (109 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_4.add(rdbtnLongDelay);

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_5.setBounds((int) (0 * lWidthFactor), (int) (0 * lHeightFactor), (int) (208 * lWidthFactor),
				(int) (152 * lHeightFactor));
		panel_2.add(panel_5);
		panel_5.setLayout(null);

		checkBoxMode1 = new JRadioButton("With same scheme");
		buttonGroup_1.add(checkBoxMode1);
		checkBoxMode1.setBounds((int) (20 * lWidthFactor), (int) (44 * lHeightFactor), (int) (171 * lWidthFactor),
				(int) (30 * lHeightFactor));
		checkBoxMode1.setSelected(true);
		checkBoxMode1.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		checkBoxMode1.setBackground(SystemColor.menu);
		panel_5.add(checkBoxMode1);

		checkBoxMode2 = new JRadioButton("All in subfolder");
		buttonGroup_1.add(checkBoxMode2);
		checkBoxMode2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theMainControl.settingsDeleteScreenshotsChanged(2);
			}
		});
		checkBoxMode2.setBounds((int) (20 * lWidthFactor), (int) (77 * lHeightFactor), (int) (163 * lWidthFactor),
				(int) (30 * lHeightFactor));
		checkBoxMode2.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		checkBoxMode2.setBackground(SystemColor.menu);
		panel_5.add(checkBoxMode2);

		checkBoxMode1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theMainControl.settingsDeleteScreenshotsChanged(1);
			}
		});
		JLabel lblDeletedScreenshots = new JLabel("Deleted screenshots");
		lblDeletedScreenshots.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		lblDeletedScreenshots.setBounds((int) (10 * lWidthFactor), (int) (11 * lHeightFactor),
				(int) (189 * lWidthFactor), (int) (30 * lHeightFactor));
		panel_5.add(lblDeletedScreenshots);

		checkBoxMode3 = new JRadioButton("None");
		checkBoxMode3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theMainControl.settingsDeleteScreenshotsChanged(0);
			}
		});
		buttonGroup_1.add(checkBoxMode3);
		checkBoxMode3.setFont(new Font("Arial", Font.PLAIN, 14));
		checkBoxMode3.setBackground(SystemColor.menu);
		checkBoxMode3.setBounds((int) (20 * lWidthFactor), (int) (110 * lHeightFactor), (int) (163 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_5.add(checkBoxMode3);

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_6.setLayout(null);
		panel_6.setBounds((int) (488 * lWidthFactor), (int) (11 * lHeightFactor), (int) (116 * lWidthFactor),
				(int) (169 * lHeightFactor));
		contentPane.add(panel_6);

		JLabel lblDefaultOffset = new JLabel("Default offset");
		lblDefaultOffset.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		lblDefaultOffset.setBounds((int) (10 * lWidthFactor), (int) (11 * lHeightFactor), (int) (95 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_6.add(lblDefaultOffset);

		txtDefaultX = new JTextField();
		txtDefaultX.setHorizontalAlignment(SwingConstants.CENTER);
		txtDefaultX.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		txtDefaultX.setBounds((int) (37 * lWidthFactor), (int) (44 * lHeightFactor), (int) (68 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_6.add(txtDefaultX);
		txtDefaultX.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("X:");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		lblNewLabel_2.setBounds((int) (20 * lWidthFactor), (int) (44 * lHeightFactor), (int) (17 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_6.add(lblNewLabel_2);

		JLabel lblY = new JLabel("Y:");
		lblY.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		lblY.setBounds((int) (20 * lWidthFactor), (int) (85 * lHeightFactor), (int) (17 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_6.add(lblY);

		txtDefaultY = new JTextField();
		txtDefaultY.setHorizontalAlignment(SwingConstants.CENTER);
		txtDefaultY.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		txtDefaultY.setColumns(10);
		txtDefaultY.setBounds((int) (37 * lWidthFactor), (int) (85 * lHeightFactor), (int) (68 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_6.add(txtDefaultY);

		JLabel lblZ = new JLabel("Z:");
		lblZ.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		lblZ.setBounds((int) (20 * lWidthFactor), (int) (126 * lHeightFactor), (int) (17 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_6.add(lblZ);

		txtDefaultZ = new JTextField();
		txtDefaultZ.setHorizontalAlignment(SwingConstants.CENTER);
		txtDefaultZ.setText("-64");
		txtDefaultZ.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		txtDefaultZ.setColumns(10);
		txtDefaultZ.setBounds((int) (37 * lWidthFactor), (int) (126 * lHeightFactor), (int) (68 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_6.add(txtDefaultZ);

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_7.setBounds((int) (488 * lWidthFactor), (int) (191 * lHeightFactor), (int) (116 * lWidthFactor),
				(int) (51 * lHeightFactor));
		contentPane.add(panel_7);
		panel_7.setLayout(null);

		JButton btnNewButton = new JButton("Uninstall");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theMainControl.clickedUninstall();
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		btnNewButton.setBounds((int) (10 * lWidthFactor), (int) (11 * lHeightFactor), (int) (96 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_7.add(btnNewButton);
	}

//Suppres Warnings to reduce Warnings	
	@SuppressWarnings("rawtypes")
	public JList getListSelectionColor() {
		return listSelectionColor;
	}

//Suppres Warnings to reduce Warnings	
	@SuppressWarnings("rawtypes")
	public JList getListSwitchingColor() {
		return listSwitchingColor;
	}

	public JRadioButton getRadioButtonDefaultDelay() {
		return rdbtnDefaultDelay;
	}

	public JRadioButton getRadioButtonLongDelay() {
		return rdbtnLongDelay;
	}

	public JRadioButton getRadioButtonShortDelay() {
		return rdbtnShortDelay;
	}

	public JRadioButton getCheckBoxMode1() {
		return checkBoxMode1;
	}

	public JRadioButton getCheckBoxMode2() {
		return checkBoxMode2;
	}

	public JTextField getTxtDefaultX() {
		return txtDefaultX;
	}

	public JTextField getTxtDefaultY() {
		return txtDefaultY;
	}

	public JTextField getTxtDefaultZ() {
		return txtDefaultZ;
	}

	public JRadioButton getCheckBoxMode3() {
		return checkBoxMode3;
	}
}
