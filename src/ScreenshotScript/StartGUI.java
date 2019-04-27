package ScreenshotScript;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.SystemColor;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Color;

public class StartGUI extends JFrame {
	private int CONST_DefaultScreenWidth = 1920;
	private int CONST_DefaultScreenHeight = 1080;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtSteamAppsPath;
	private JTextField txtSubfolder;
	private JTextField txtScreenshotPostfix;
	private JTextField txtScreenshotPrefix;
	private JCheckBox checkBoxSteamAppsPath;
	private JButton btnEdit;
	private JButton btnConvert;

	private Control theMainControl;
	private JTextField txtVscriptPath;
	private JTextField txtScreenshotPath;
//Suppres Warnings to reduce Warnings	
	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxGame;
	private JLabel lblDropHere;
	private JPanel panel_2;
	private JLabel label;
	private JTextField txtShortVersion;
	private JLabel label_1;
	private JTextField txtCustomGame;
	private int aPreventCrashComoboBox = 0;

	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//Suppres Warnings to reduce Warnings	
					@SuppressWarnings("unused")
					StartGUI frame = new StartGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//Suppres Warnings to reduce Warnings	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public StartGUI() {
		
		
		setTitle("ScriptGenerator");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double lScreenWidth = (double) screenSize.getWidth();
		double lScreenHeight = (double) screenSize.getHeight();

		double lWidthFactor = lScreenWidth / CONST_DefaultScreenWidth;
		double lHeightFactor = lScreenHeight / CONST_DefaultScreenHeight;
		double lFontFactor = lScreenHeight / CONST_DefaultScreenHeight;

		setIconImage(Toolkit.getDefaultToolkit().getImage(StartGUI.class.getResource("/icon.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((int) ((lScreenWidth / 2) - (850 * lWidthFactor / 2)),
				(int) ((lScreenHeight / 2) - (330 * lHeightFactor)), (int) (860 * lWidthFactor),
				(int) (340 * lHeightFactor));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds((int) (10 * lWidthFactor), (int) (11 * lHeightFactor), (int) (824 * lWidthFactor),
				(int) (177 * lHeightFactor));
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("SteamApps folder:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		lblNewLabel.setBounds((int) (10 * lWidthFactor), (int) (10 * lHeightFactor), (int) (121 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel.add(lblNewLabel);

		txtSteamAppsPath = new JTextField();
		txtSteamAppsPath.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				theMainControl.pathToSteamAppsChanged();
			}
		});

		txtSteamAppsPath.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		txtSteamAppsPath.setText("F:\\Program Files (x86)\\Steam\\SteamApps");
		txtSteamAppsPath.setBounds((int) (141 * lWidthFactor), (int) (11 * lHeightFactor), (int) (588 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel.add(txtSteamAppsPath);
		txtSteamAppsPath.setColumns(10);

		checkBoxSteamAppsPath = new JCheckBox("Default");
		checkBoxSteamAppsPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theMainControl.checkedDefaultScript(checkBoxSteamAppsPath.isSelected());
			}
		});

		checkBoxSteamAppsPath.setFont(new Font("Tahoma", Font.PLAIN, (int) (14 * lFontFactor)));
		checkBoxSteamAppsPath.setSelected(true);
		checkBoxSteamAppsPath.setBounds((int) (735 * lWidthFactor), (int) (9 * lHeightFactor),
				(int) (80 * lWidthFactor), (int) (30 * lHeightFactor));
		panel.add(checkBoxSteamAppsPath);

		JLabel lblDesiredSubfolder = new JLabel("Desired subfolder:");
		lblDesiredSubfolder.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		lblDesiredSubfolder.setBounds((int) (428 * lWidthFactor), (int) (51 * lHeightFactor),
				(int) (121 * lWidthFactor), (int) (30 * lHeightFactor));
		panel.add(lblDesiredSubfolder);

		txtSubfolder = new JTextField();
		txtSubfolder.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				theMainControl.updateSubfolder();
			}
		});
		txtSubfolder.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		txtSubfolder.setColumns(10);
		txtSubfolder.setBounds((int) (559 * lWidthFactor), (int) (52 * lHeightFactor), (int) (256 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel.add(txtSubfolder);

		comboBoxGame = new JComboBox();
		comboBoxGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (aPreventCrashComoboBox != 0) {
					theMainControl.selectedGame();
				} else {
					aPreventCrashComoboBox++;
				}
			}
		});
		comboBoxGame.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		comboBoxGame.setModel(new DefaultComboBoxModel(new String[] { "Alien Swarm", "Counter-Strike Global Offensive",
				"Left 4 Dead 2", "Portal 2", "Custom game configuration" }));
		comboBoxGame.setSelectedIndex(0);
		comboBoxGame.setBounds((int) (10 * lWidthFactor), (int) (52 * lHeightFactor), (int) (406 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel.add(comboBoxGame);

		JLabel lblVscriptFolder = new JLabel("Vscript folder:");
		lblVscriptFolder.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		lblVscriptFolder.setBounds((int) (10 * lWidthFactor), (int) (95 * lHeightFactor), (int) (121 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel.add(lblVscriptFolder);

		txtVscriptPath = new JTextField();
		txtVscriptPath.setEditable(false);
		txtVscriptPath.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		txtVscriptPath.setColumns(10);
		txtVscriptPath.setBounds((int) (141 * lWidthFactor), (int) (92 * lHeightFactor), (int) (674 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel.add(txtVscriptPath);

		JLabel lblScreenshotFolder = new JLabel("Screenshot folder:");
		lblScreenshotFolder.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		lblScreenshotFolder.setBounds((int) (10 * lWidthFactor), (int) (136 * lHeightFactor),
				(int) (121 * lWidthFactor), (int) (30 * lHeightFactor));
		panel.add(lblScreenshotFolder);

		txtScreenshotPath = new JTextField();
		txtScreenshotPath.setEditable(false);

		txtScreenshotPath.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		txtScreenshotPath.setColumns(10);
		txtScreenshotPath.setBounds((int) (141 * lWidthFactor), (int) (136 * lHeightFactor), (int) (674 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel.add(txtScreenshotPath);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setBounds((int) (301 * lWidthFactor), (int) (199 * lHeightFactor), (int) (533 * lWidthFactor),
				(int) (94 * lHeightFactor));
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		btnEdit = new JButton("Edit script");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theMainControl.clickedMainButton();
				theMainControl.clickedEditScript();
			}
		});
		btnEdit.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		btnEdit.setBounds((int) (329 * lWidthFactor), (int) (11 * lHeightFactor), (int) (135 * lWidthFactor),
				(int) (70 * lHeightFactor));
		panel_1.add(btnEdit);

		btnConvert = new JButton("Convert .txt file");
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theMainControl.clickedMainButton();
				theMainControl.clickedConvertStartGUI();

			}
		});
		btnConvert.setEnabled(false);
		btnConvert.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		btnConvert.setBounds((int) (184 * lWidthFactor), (int) (10 * lHeightFactor), (int) (135 * lWidthFactor),
				(int) (72 * lHeightFactor));
		panel_1.add(btnConvert);

		lblDropHere = new JLabel("Drop .txt here");
		lblDropHere.setBackground(SystemColor.window);
		lblDropHere.setHorizontalAlignment(SwingConstants.CENTER);
		lblDropHere.setIcon(new ImageIcon(StartGUI.class.getResource("/DnD.png")));

		lblDropHere.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		lblDropHere.setBounds((int) (10 * lWidthFactor), (int) (11 * lHeightFactor), (int) (164 * lWidthFactor),
				(int) (70 * lHeightFactor));
		lblDropHere.setBorder(LineBorder.createBlackLineBorder());
		panel_1.add(lblDropHere);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_3.setBounds((int) (10 * lWidthFactor), (int) (199 * lHeightFactor), (int) (281 * lWidthFactor),
				(int) (94 * lHeightFactor));
		contentPane.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblDesiredScreenshotPostfix = new JLabel("Screenshot postfix:");
		lblDesiredScreenshotPostfix.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		lblDesiredScreenshotPostfix.setBounds((int) (10 * lWidthFactor), (int) (52 * lHeightFactor),
				(int) (122 * lWidthFactor), (int) (30 * lHeightFactor));
		panel_3.add(lblDesiredScreenshotPostfix);

		JLabel lblDesiredScreenshotPrefix = new JLabel("Screenshot prefix:");
		lblDesiredScreenshotPrefix.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		lblDesiredScreenshotPrefix.setBounds((int) (10 * lWidthFactor), (int) (11 * lHeightFactor),
				(int) (122 * lWidthFactor), (int) (30 * lHeightFactor));
		panel_3.add(lblDesiredScreenshotPrefix);

		txtScreenshotPostfix = new JTextField();
		txtScreenshotPostfix.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		txtScreenshotPostfix.setColumns(10);
		txtScreenshotPostfix.setBounds((int) (142 * lWidthFactor), (int) (52 * lHeightFactor),
				(int) (128 * lWidthFactor), (int) (30 * lHeightFactor));
		panel_3.add(txtScreenshotPostfix);

		txtScreenshotPrefix = new JTextField();
		txtScreenshotPrefix.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		txtScreenshotPrefix.setColumns(10);
		txtScreenshotPrefix.setBounds((int) (142 * lWidthFactor), (int) (11 * lHeightFactor),
				(int) (128 * lWidthFactor), (int) (30 * lHeightFactor));
		panel_3.add(txtScreenshotPrefix);

		new FileDrop(lblDropHere, new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				try {
					String lPath = files[0].getCanonicalPath();
					theMainControl.droppedFileOntoStartGUI(lPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		JButton btnSettings = new JButton("");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theMainControl.clickedSettingsButton();
			}
		});
		btnSettings.setBounds((int) (474 * lWidthFactor), (int) (22 * lHeightFactor), (int) (48 * lWidthFactor),
				(int) (48 * lHeightFactor));
		btnSettings.setIcon(new ImageIcon(StartGUI.class.getResource("/settings.png")));
		panel_1.add(btnSettings);

		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_2.setBounds((int) (10 * lWidthFactor), (int) (304 * lHeightFactor), (int) (647 * lWidthFactor),
				(int) (52 * lHeightFactor));
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		label = new JLabel("Short version:");
		label.setBounds((int) (380 * lWidthFactor), (int) (10 * lHeightFactor), (int) (100 * lWidthFactor),
				(int) (30 * lHeightFactor));
		label.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		panel_2.add(label);

		txtShortVersion = new JTextField();
		txtShortVersion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				theMainControl.customShortVersionChanged();
			}
		});
		txtShortVersion.setBounds((int) (486 * lWidthFactor), (int) (10 * lHeightFactor), (int) (150 * lWidthFactor),
				(int) (30 * lHeightFactor));
		txtShortVersion.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		txtShortVersion.setColumns(10);
		panel_2.add(txtShortVersion);

		label_1 = new JLabel("Custom game:");
		label_1.setBounds((int) (10 * lWidthFactor), (int) (10 * lHeightFactor), (int) (100 * lWidthFactor),
				(int) (30 * lHeightFactor));
		label_1.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		panel_2.add(label_1);

		txtCustomGame = new JTextField();
		txtCustomGame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				theMainControl.customSelectedGameChanged();
			}
		});
		txtCustomGame.setBounds((int) (120 * lWidthFactor), (int) (10 * lHeightFactor), (int) (250 * lWidthFactor),
				(int) (30 * lHeightFactor));
		txtCustomGame.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		txtCustomGame.setColumns(10);
		panel_2.add(txtCustomGame);

		theMainControl = new Control(this);
		setVisible(true);
		theMainControl.readLastSessionCfg();
		
	}

//Suppres Warnings to reduce Warnings		
	@SuppressWarnings("rawtypes")
	public JComboBox getComboBoxGame() {
		return comboBoxGame;
	}

	public JTextField getTxtPathToVscript() {
		return txtVscriptPath;
	}

	public JTextField getTxtPathToSteamApps() {
		return txtSteamAppsPath;
	}

	public JTextField getTxtPathToScreenshots() {
		return txtScreenshotPath;
	}

	public JTextField getTxtDesiredSubfolder() {
		return txtSubfolder;
	}

	public JTextField getTxtDesiredPrefix() {
		return txtScreenshotPrefix;
	}

	public JTextField getTxtDesiredPostfix() {
		return txtScreenshotPostfix;
	}

	public JCheckBox getCheckBoxScript() {
		return checkBoxSteamAppsPath;
	}

	public JButton getBtnConvert() {
		return btnConvert;
	}

	public JButton getBtnEdit() {
		return btnEdit;
	}

	public JTextField getTxtCustomGame() {
		return txtCustomGame;
	}

	public JTextField getTxtShortVersion() {
		return txtShortVersion;
	}

	public JLabel getLblDropFile() {
		return lblDropHere;
	}
}
