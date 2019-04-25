package ScreenshotScript;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.border.LineBorder;
import java.awt.event.KeyAdapter;

public class MainGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private int CONST_MaxRow = 8;
	private int CONST_MaxColoum = 8;
	private int CONST_MaxCellNumber = CONST_MaxRow * CONST_MaxColoum;
	private int CONST_DefaultScreenWidth = 1920;
	private int CONST_DefaultScreenHeight = 1080;
	private ImageIcon tblImageMain[][] = new ImageIcon[CONST_MaxColoum][CONST_MaxRow];
	private ImageIcon tblImageDeleted[] = new ImageIcon[4];
	private ImageIcon tblImageCopied[] = new ImageIcon[2];
	private Control theMainControl;

	private JTable tableDeleted;
	private JTable tableCopied;
	private JTable tableMain;
	private JLabel lblCurrentImage;
	private JLabel lblCurrentPos;
	private JTextField txtCurrentPos;
	private JTextField txtGlobalXOffset;
	private JTextField txtGlobalYOffset;
	private JTextField txtGlobalZOffset;
	private JTextField txtIndividualXOffset;
	private JTextField txtIndividualYOffset;
	private JTextField txtIndividualZOffset;
	private JScrollPane scrollPane;
	private JTextField txtNewPrefix;
	private JTextField txtNewPostfix;
	private JTextField txtNewScreenshotSubfolder;
	private JButton btnMarkSwitchCells;
	private JButton btnSwitchCells;
	private JButton btnConvertMainGUI;
	private JLabel lblDropHere;

	public MainGUI(Control pControl) {
		setTitle("ScriptGenerator");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainGUI.class.getResource("/icon.png")));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double lScreenWidth = (double) screenSize.getWidth();
		double lScreenHeight = (double) screenSize.getHeight();

		double lWidthFactor = lScreenWidth / CONST_DefaultScreenWidth;
		double lHeightFactor = lScreenHeight / CONST_DefaultScreenHeight;
		double lFontFactor = lScreenHeight / CONST_DefaultScreenHeight;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theMainControl = pControl;
		initialiseTableImages();
		getContentPane().setBackground(SystemColor.control);
		setBounds(0, 0, (int) (1920 * lWidthFactor), (int) (1080 * lHeightFactor));

		String[] columnNamesMain = { "", "", "", "", "", "", "", "" };
		Object[][] dataMain = {
				{ tblImageMain[0][0], tblImageMain[0][1], tblImageMain[0][2], tblImageMain[0][3], tblImageMain[0][4],
						tblImageMain[0][5], tblImageMain[0][6], tblImageMain[0][7] },
				{ tblImageMain[1][0], tblImageMain[1][1], tblImageMain[1][2], tblImageMain[1][3], tblImageMain[1][4],
						tblImageMain[1][5], tblImageMain[1][6], tblImageMain[1][7] },
				{ tblImageMain[2][0], tblImageMain[2][1], tblImageMain[2][2], tblImageMain[2][3], tblImageMain[2][4],
						tblImageMain[2][5], tblImageMain[2][6], tblImageMain[2][7] },
				{ tblImageMain[3][0], tblImageMain[3][1], tblImageMain[3][2], tblImageMain[3][3], tblImageMain[3][4],
						tblImageMain[3][5], tblImageMain[3][6], tblImageMain[3][7] },
				{ tblImageMain[4][0], tblImageMain[4][1], tblImageMain[4][2], tblImageMain[4][3], tblImageMain[4][4],
						tblImageMain[4][5], tblImageMain[4][6], tblImageMain[4][7] },
				{ tblImageMain[5][0], tblImageMain[5][1], tblImageMain[5][2], tblImageMain[5][3], tblImageMain[5][4],
						tblImageMain[5][5], tblImageMain[5][6], tblImageMain[5][7] },
				{ tblImageMain[6][0], tblImageMain[6][1], tblImageMain[6][2], tblImageMain[6][3], tblImageMain[6][4],
						tblImageMain[6][5], tblImageMain[6][6], tblImageMain[6][7] },
				{ tblImageMain[7][0], tblImageMain[7][1], tblImageMain[7][2], tblImageMain[7][3], tblImageMain[7][4],
						tblImageMain[7][5], tblImageMain[7][6], tblImageMain[7][7] } };

//Suppress Warnings for sake of removing warnings
		@SuppressWarnings("serial")
		DefaultTableModel modelTblMain = new DefaultTableModel(dataMain, columnNamesMain) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}

			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		getContentPane().setLayout(null);
		tableMain = new JTable(modelTblMain);
		tableMain.setShowHorizontalLines(false);
		tableMain.setShowVerticalLines(false);
		tableMain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				theMainControl.setInMainTable(true);
				theMainControl.setInCopiedTable(false);
				theMainControl.setInDeletedTable(false);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				mousePressedOnTable();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				mousePressedOnTable();
			}
		});
		tableMain.setShowGrid(false);
		tableMain.setRowSelectionAllowed(false);
		tableMain.setRowHeight((int) (132 * lHeightFactor));
		tableMain.setPreferredScrollableViewportSize(tableMain.getPreferredSize());
		tableMain.setTableHeader(null);

		scrollPane = new JScrollPane(tableMain);
		scrollPane.setBounds((int) (10 * lWidthFactor), (int) (52 * lHeightFactor), (int) (1900 * lWidthFactor),
				(int) (659 * lHeightFactor));
		getContentPane().add(scrollPane);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBackground(SystemColor.control);
		panel.setBounds((int) (0 * lWidthFactor), (int) (722 * lHeightFactor), (int) (973 * lWidthFactor),
				(int) (295 * lHeightFactor));
		getContentPane().add(panel);
		panel.setLayout(null);

		String[] columnNamesDeleted = { "", "", "", "" };
		Object[][] dataDeleted = {
				{ tblImageDeleted[0], tblImageDeleted[1], tblImageDeleted[2], tblImageDeleted[3] }, };
//Suppress Warnings for sake of removing warnings
		@SuppressWarnings("serial")
		DefaultTableModel modelTblDeleted = new DefaultTableModel(dataDeleted, columnNamesDeleted) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}

			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		tableDeleted = new JTable(modelTblDeleted);
		tableDeleted.setToolTipText("Displays the last 3 deleted images/positions.");
		tableDeleted.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tableDeleted.setShowVerticalLines(false);
		tableDeleted.setShowHorizontalLines(false);
		tableDeleted.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				theMainControl.setInMainTable(false);
				theMainControl.setInCopiedTable(false);
				theMainControl.setInDeletedTable(true);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				mousePressedOnTable();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				mousePressedOnTable();
			}
		});
		;
		tableDeleted.setRowSelectionAllowed(false);
		tableDeleted.setBounds((int) (10 * lWidthFactor), (int) (11 * lHeightFactor), (int) (950 * lWidthFactor),
				(int) (132 * lHeightFactor));
		tableDeleted.setRowHeight((int) (132 * lHeightFactor));
		panel.add(tableDeleted);

		String[] columnNamesCopied = { "", "" };
		Object[][] dataCopied = { { tblImageCopied[0], tblImageCopied[1] }, };
//Suppress Warnings for sake of removing warnings
		@SuppressWarnings("serial")
		DefaultTableModel modelTblCopied = new DefaultTableModel(dataCopied, columnNamesCopied) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}

			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		tableCopied = new JTable(modelTblCopied);
		tableCopied.setToolTipText("Displays the copied image/position.");
		tableCopied.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tableCopied.setShowHorizontalLines(false);
		tableCopied.setShowVerticalLines(false);
		tableCopied.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				theMainControl.setInMainTable(false);
				theMainControl.setInCopiedTable(true);
				theMainControl.setInDeletedTable(false);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				mousePressedOnTable();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				mousePressedOnTable();
			}
		});
		tableCopied.setShowGrid(false);
		tableCopied.setRowSelectionAllowed(false);
		tableCopied.setBounds((int) (10 * lWidthFactor), (int) (154 * lHeightFactor), (int) (475 * lWidthFactor),
				(int) (132 * lHeightFactor));
		tableCopied.setRowHeight(132);
		panel.add(tableCopied);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setBackground(SystemColor.control);
		panel_1.setLayout(null);
		panel_1.setBounds((int) (495 * lWidthFactor), (int) (154 * lHeightFactor), (int) (465 * lWidthFactor),
				(int) (132 * lHeightFactor));
		panel.add(panel_1);

		JLabel label_3 = new JLabel("Prefix:");
		label_3.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		label_3.setBounds((int) (10 * lWidthFactor), (int) (11 * lHeightFactor), (int) (70 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_1.add(label_3);

		JLabel label_4 = new JLabel("Postfix");
		label_4.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		label_4.setBounds((int) (10 * lWidthFactor), (int) (52 * lHeightFactor), (int) (70 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_1.add(label_4);

		JLabel label_5 = new JLabel("Screenshot subfolder:");
		label_5.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		label_5.setBounds((int) (10 * lWidthFactor), (int) (93 * lHeightFactor), (int) (171 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_1.add(label_5);

		txtNewPrefix = new JTextField();
		txtNewPrefix.setToolTipText("");
		txtNewPrefix.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				theMainControl.changedPrefix();
			}
		});
		txtNewPrefix.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		txtNewPrefix.setColumns(10);
		txtNewPrefix.setBounds((int) (90 * lWidthFactor), (int) (12 * lHeightFactor), (int) (365 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_1.add(txtNewPrefix);

		txtNewPostfix = new JTextField();
		txtNewPostfix.setToolTipText("");
		txtNewPostfix.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				theMainControl.changedPostfix();
			}
		});
		txtNewPostfix.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		txtNewPostfix.setColumns(10);
		txtNewPostfix.setBounds((int) (90 * lWidthFactor), (int) (52 * lHeightFactor), (int) (365 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_1.add(txtNewPostfix);

		txtNewScreenshotSubfolder = new JTextField();
		txtNewScreenshotSubfolder.setToolTipText("");
		txtNewScreenshotSubfolder.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				theMainControl.changedSubfolder();
			}
		});
		txtNewScreenshotSubfolder.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		txtNewScreenshotSubfolder.setColumns(10);
		txtNewScreenshotSubfolder.setBounds((int) (191 * lWidthFactor), (int) (93 * lHeightFactor),
				(int) (264 * lWidthFactor), (int) (30 * lHeightFactor));
		panel_1.add(txtNewScreenshotSubfolder);

		txtCurrentPos = new JTextField();
		txtCurrentPos.setToolTipText("");
		txtCurrentPos.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		txtCurrentPos.setBounds((int) (407 * lWidthFactor), (int) (11 * lHeightFactor), (int) (1213 * lWidthFactor),
				(int) (30 * lHeightFactor));
		getContentPane().add(txtCurrentPos);
		txtCurrentPos.setColumns(10);

		JButton btnSaveCurrentPosition = new JButton("Save position");
		btnSaveCurrentPosition.setToolTipText("");
		btnSaveCurrentPosition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theMainControl.clickedSaveCurrentPosition();
			}
		});
		btnSaveCurrentPosition.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		btnSaveCurrentPosition.setBounds((int) (1630 * lWidthFactor), (int) (11 * lHeightFactor),
				(int) (180 * lWidthFactor), (int) (30 * lHeightFactor));
		getContentPane().add(btnSaveCurrentPosition);

		lblCurrentPos = new JLabel("Current image: ");
		lblCurrentPos.setToolTipText("");
		lblCurrentPos.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		lblCurrentPos.setBounds((int) (22 * lWidthFactor), (int) (11 * lHeightFactor), (int) (375 * lWidthFactor),
				(int) (30 * lHeightFactor));
		getContentPane().add(lblCurrentPos);

		JButton btnDeleteCurrentPosition = new JButton("DELETE");
		btnDeleteCurrentPosition.setToolTipText("");
		btnDeleteCurrentPosition.setBackground(Color.RED);
		btnDeleteCurrentPosition.setForeground(new Color(165, 42, 42));
		btnDeleteCurrentPosition.setFont(new Font("Arial", Font.BOLD, (int) (14 * lFontFactor)));
		btnDeleteCurrentPosition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theMainControl.pressedDelete();
			}
		});
		btnDeleteCurrentPosition.setIcon(null);
		btnDeleteCurrentPosition.setBounds((int) (1820 * lWidthFactor), (int) (11 * lHeightFactor),
				(int) (90 * lWidthFactor), (int) (30 * lHeightFactor));
		getContentPane().add(btnDeleteCurrentPosition);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.control);
		panel_2.setBounds((int) (983 * lWidthFactor), (int) (722 * lHeightFactor), (int) (937 * lWidthFactor),
				(int) (295 * lHeightFactor));
		getContentPane().add(panel_2);
		panel_2.setLayout(null);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_3.setBackground(SystemColor.control);
		panel_3.setBounds((int) (411 * lWidthFactor), (int) (78 * lHeightFactor), (int) (301 * lWidthFactor),
				(int) (134 * lHeightFactor));
		panel_2.add(panel_3);
		panel_3.setLayout(null);

		txtGlobalXOffset = new JTextField();
		txtGlobalXOffset.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		txtGlobalXOffset.setBounds((int) (205 * lWidthFactor), (int) (11 * lHeightFactor), (int) (86 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_3.add(txtGlobalXOffset);
		txtGlobalXOffset.setColumns(10);

		txtGlobalYOffset = new JTextField();
		txtGlobalYOffset.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		txtGlobalYOffset.setColumns(10);
		txtGlobalYOffset.setBounds((int) (205 * lWidthFactor), (int) (52 * lHeightFactor), (int) (86 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_3.add(txtGlobalYOffset);

		txtGlobalZOffset = new JTextField();
		txtGlobalZOffset.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		txtGlobalZOffset.setColumns(10);
		txtGlobalZOffset.setBounds((int) (205 * lWidthFactor), (int) (93 * lHeightFactor), (int) (86 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_3.add(txtGlobalZOffset);

		JLabel lblNewLabel_1 = new JLabel("Y-Offset: ");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds((int) (129 * lWidthFactor), (int) (51 * lHeightFactor), (int) (66 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_3.add(lblNewLabel_1);

		JLabel lblXoffset = new JLabel("X-Offset: ");
		lblXoffset.setHorizontalAlignment(SwingConstants.LEFT);
		lblXoffset.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		lblXoffset.setBounds((int) (129 * lWidthFactor), (int) (11 * lHeightFactor), (int) (66 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_3.add(lblXoffset);

		JLabel lblZoffset = new JLabel("Z-Offset: ");
		lblZoffset.setHorizontalAlignment(SwingConstants.LEFT);
		lblZoffset.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		lblZoffset.setBounds((int) (129 * lWidthFactor), (int) (93 * lHeightFactor), (int) (66 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_3.add(lblZoffset);

		JLabel lblGlobalOffset = new JLabel("Global offset");
		lblGlobalOffset.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		lblGlobalOffset.setBounds((int) (10 * lWidthFactor), (int) (52 * lHeightFactor), (int) (109 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_3.add(lblGlobalOffset);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_4.setBackground(SystemColor.control);
		panel_4.setLayout(null);
		panel_4.setBounds((int) (10 * lWidthFactor), (int) (78 * lHeightFactor), (int) (391 * lWidthFactor),
				(int) (191 * lHeightFactor));
		panel_2.add(panel_4);

		txtIndividualXOffset = new JTextField();
		txtIndividualXOffset.setToolTipText("");
		txtIndividualXOffset.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		txtIndividualXOffset.setColumns(10);
		txtIndividualXOffset.setBounds((int) (295 * lWidthFactor), (int) (11 * lHeightFactor),
				(int) (86 * lWidthFactor), (int) (30 * lHeightFactor));
		panel_4.add(txtIndividualXOffset);

		txtIndividualYOffset = new JTextField();
		txtIndividualYOffset.setToolTipText("");
		txtIndividualYOffset.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		txtIndividualYOffset.setColumns(10);
		txtIndividualYOffset.setBounds((int) (295 * lWidthFactor), (int) (52 * lHeightFactor),
				(int) (86 * lWidthFactor), (int) (30 * lHeightFactor));
		panel_4.add(txtIndividualYOffset);

		txtIndividualZOffset = new JTextField();
		txtIndividualZOffset.setToolTipText("");
		txtIndividualZOffset.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		txtIndividualZOffset.setColumns(10);
		txtIndividualZOffset.setBounds((int) (295 * lWidthFactor), (int) (93 * lHeightFactor),
				(int) (86 * lWidthFactor), (int) (30 * lHeightFactor));
		panel_4.add(txtIndividualZOffset);

		JLabel label = new JLabel("Y-Offset: ");
		label.setToolTipText("");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		label.setBounds(217, 52, (int) (68 * lWidthFactor), (int) (30 * lHeightFactor));
		panel_4.add(label);

		JLabel label_1 = new JLabel("X-Offset: ");
		label_1.setToolTipText("");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		label_1.setBounds((int) (217 * lWidthFactor), (int) (11 * lHeightFactor), (int) (68 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_4.add(label_1);

		JLabel label_2 = new JLabel("Z-Offset: ");
		label_2.setToolTipText("");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		label_2.setBounds(217, 94, (int) (68 * lWidthFactor), (int) (30 * lHeightFactor));
		panel_4.add(label_2);

		JLabel lblIndividualOffset = new JLabel("Individual offset");
		lblIndividualOffset.setHorizontalAlignment(SwingConstants.CENTER);
		lblIndividualOffset.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		lblIndividualOffset.setBounds((int) (10 * lWidthFactor), (int) (21 * lHeightFactor), (int) (197 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_4.add(lblIndividualOffset);

		JLabel lblImage = new JLabel("for the image:");
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		lblImage.setBounds((int) (10 * lWidthFactor), (int) (51 * lHeightFactor), (int) (197 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_4.add(lblImage);

		lblCurrentImage = new JLabel("None");
		lblCurrentImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentImage.setForeground(SystemColor.textHighlight);
		lblCurrentImage.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCurrentImage.setBounds((int) (10 * lWidthFactor), (int) (81 * lHeightFactor), (int) (197 * lWidthFactor),
				(int) (30 * lHeightFactor));
		panel_4.add(lblCurrentImage);

		JButton btnSave = new JButton("Save position");
		btnSave.setToolTipText("");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theMainControl.clickedSaveCurrentPosition();
			}
		});
		btnSave.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		btnSave.setBounds((int) (10 * lWidthFactor), (int) (134 * lHeightFactor), (int) (371 * lWidthFactor),
				(int) (45 * lHeightFactor));
		panel_4.add(btnSave);

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_5.setBackground(SystemColor.menu);
		panel_5.setBounds((int) (352 * lWidthFactor), (int) (0 * lHeightFactor), (int) (443 * lWidthFactor),
				(int) (67 * lHeightFactor));
		panel_2.add(panel_5);
		panel_5.setLayout(null);

		JButton btnMoveLeft = new JButton("\u25C4 ");
		btnMoveLeft.setToolTipText("");
		btnMoveLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnMoveLeftPressed();
			}
		});
		btnMoveLeft.setFont(new Font("Arial", Font.PLAIN, (int) (28 * lFontFactor)));
		btnMoveLeft.setBounds((int) (10 * lWidthFactor), (int) (11 * lHeightFactor), (int) (149 * lWidthFactor),
				(int) (45 * lHeightFactor));
		panel_5.add(btnMoveLeft);

		JButton btnMoveRight = new JButton(" \u25BA");
		btnMoveRight.setToolTipText("");
		btnMoveRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnMoveRightPressed();
			}
		});
		btnMoveRight.setFont(new Font("Arial", Font.PLAIN, (int) (28 * lFontFactor)));
		btnMoveRight.setBounds((int) (283 * lWidthFactor), (int) (11 * lHeightFactor), (int) (149 * lWidthFactor),
				(int) (45 * lHeightFactor));
		panel_5.add(btnMoveRight);

		JLabel lblNewLabel_2 = new JLabel("Move cells");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		lblNewLabel_2.setBounds((int) (169 * lWidthFactor), (int) (11 * lHeightFactor), (int) (104 * lWidthFactor),
				(int) (45 * lHeightFactor));
		panel_5.add(lblNewLabel_2);

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_6.setBackground(SystemColor.menu);
		panel_6.setBounds((int) (411 * lWidthFactor), (int) (223 * lHeightFactor), (int) (301 * lWidthFactor),
				(int) (70 * lHeightFactor));
		panel_2.add(panel_6);
		panel_6.setLayout(null);

		JButton btnGenerate = new JButton("Generate");
		btnGenerate.setToolTipText("");
		btnGenerate.setForeground(new Color(34, 139, 34));
		btnGenerate.setBackground(new Color(0, 255, 0));
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theMainControl.clickedGenerate();
			}
		});
		btnGenerate.setFont(new Font("Arial", Font.BOLD, (int) (16 * lFontFactor)));
		btnGenerate.setBounds((int) (10 * lWidthFactor), (int) (11 * lHeightFactor), (int) (281 * lWidthFactor),
				(int) (48 * lHeightFactor));
		panel_6.add(btnGenerate);

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_7.setLayout(null);
		panel_7.setBackground(SystemColor.menu);
		panel_7.setBounds((int) (10 * lWidthFactor), (int) (0 * lHeightFactor), (int) (332 * lWidthFactor),
				(int) (67 * lHeightFactor));
		panel_2.add(panel_7);

		btnMarkSwitchCells = new JButton("Mark cells");
		btnMarkSwitchCells.setToolTipText("");
		btnMarkSwitchCells.setBackground(SystemColor.menu);
		btnMarkSwitchCells.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theMainControl.clickedMarkSwitch();
			}
		});
		btnMarkSwitchCells.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		btnMarkSwitchCells.setBounds((int) (10 * lWidthFactor), (int) (11 * lHeightFactor), (int) (114 * lWidthFactor),
				(int) (45 * lHeightFactor));
		panel_7.add(btnMarkSwitchCells);

		btnSwitchCells = new JButton("Switch marked cells");
		btnSwitchCells.setToolTipText("");
		btnSwitchCells.setBackground(SystemColor.menu);
		btnSwitchCells.setEnabled(false);
		btnSwitchCells.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				theMainControl.clickedSwitchMarkedCells();
			}
		});
		btnSwitchCells.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		btnSwitchCells.setBounds((int) (134 * lWidthFactor), (int) (11 * lHeightFactor), (int) (186 * lWidthFactor),
				(int) (45 * lHeightFactor));
		panel_7.add(btnSwitchCells);

		setExtendedState(JFrame.MAXIMIZED_BOTH);

		tableMain.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), "F");
		tableMain.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK), "CtrlC");
		tableMain.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK), "CtrlV");
		tableMain.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "Delete");
		tableMain.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "Right");
		tableMain.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "Left");
		tableMain.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "Up");
		tableMain.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "Down");
		tableMain.getActionMap().put("F", new ActionFullscreen());
		tableMain.getActionMap().put("CtrlC", new ActionCopied());
		tableMain.getActionMap().put("CtrlV", new ActionPasted());
		tableMain.getActionMap().put("Delete", new ActionDeleted());
		tableMain.getActionMap().put("Right", new ActionRight());
		tableMain.getActionMap().put("Left", new ActionLeft());
		tableMain.getActionMap().put("Up", new ActionUp());
		tableMain.getActionMap().put("Down", new ActionDown());

		tableCopied.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK), "CtrlV");
		tableCopied.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "Delete");
		tableCopied.getActionMap().put("CtrlV", new ActionPasted());
		tableCopied.getActionMap().put("Delete", new ActionDeleted());

		tableDeleted.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK), "CtrlC");
		tableDeleted.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK), "CtrlV");
		tableDeleted.getActionMap().put("CtrlC", new ActionCopied());
		tableDeleted.getActionMap().put("CtrlV", new ActionPasted());

		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_8.setBounds((int) (722 * lWidthFactor), (int) (78 * lHeightFactor), (int) (205 * lWidthFactor),
				(int) (162 * lHeightFactor));
		panel_2.add(panel_8);
		panel_8.setLayout(null);

		btnConvertMainGUI = new JButton("Add commands from .txt");
		btnConvertMainGUI.setToolTipText("");
		btnConvertMainGUI.setEnabled(false);
		btnConvertMainGUI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theMainControl.clickedConvertMainGUI();
			}
		});
		btnConvertMainGUI.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		btnConvertMainGUI.setBounds((int) (10 * lWidthFactor), (int) (11 * lHeightFactor), (int) (185 * lWidthFactor),
				(int) (53 * lHeightFactor));
		panel_8.add(btnConvertMainGUI);

		lblDropHere = new JLabel("Drop .txt here");
		lblDropHere.setToolTipText("");
		lblDropHere.setIcon(new ImageIcon(MainGUI.class.getResource("/DnD.png")));
		lblDropHere.setHorizontalAlignment(SwingConstants.CENTER);
		lblDropHere.setFont(new Font("Arial", Font.PLAIN, (int) (14 * lFontFactor)));
		lblDropHere.setBorder(LineBorder.createBlackLineBorder());
		lblDropHere.setBackground(Color.WHITE);
		lblDropHere.setBounds((int) (10 * lWidthFactor), (int) (75 * lHeightFactor), (int) (185 * lWidthFactor),
				(int) (70 * lHeightFactor));
		panel_8.add(lblDropHere);

		new FileDrop(lblDropHere, new FileDrop.Listener() {
			public void filesDropped(java.io.File[] files) {
				try {
					String lPath = files[0].getCanonicalPath();
					theMainControl.droppedFileOntoMainGUI(lPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_9.setBounds((int) (860 * lWidthFactor), (int) (0 * lHeightFactor), (int) (67 * lWidthFactor),
				(int) (67 * lHeightFactor));
		panel_2.add(panel_9);
		panel_9.setLayout(null);

		JButton button = new JButton("");
		button.setToolTipText("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theMainControl.clickedSettingsButton();
			}
		});
		button.setIcon(new ImageIcon(MainGUI.class.getResource("/settings.png")));
		button.setBackground(SystemColor.menu);
		button.setBounds((int) (10 * lWidthFactor), (int) (11 * lHeightFactor), (int) (48 * lWidthFactor),
				(int) (48 * lHeightFactor));
		panel_9.add(button);
	}

	public ImageIcon getImageTblMain(int pColoum, int pRow) {
		return tblImageMain[pColoum][pRow];
	}

	public ImageIcon getImageTblDeleted(int pCell) {
		return tblImageDeleted[pCell];
	}

	public ImageIcon getImageTblCopied() {
		return tblImageCopied[1];
	}

	public JTextField getGlobalXOffset() {
		return txtGlobalXOffset;
	}

	public JTextField getGlobalYOffset() {
		return txtGlobalYOffset;
	}

	public JTextField getGlobalZOffset() {
		return txtGlobalZOffset;
	}

	public JTextField getIndividualXOffset() {
		return txtIndividualXOffset;
	}

	public JTextField getIndividualYOffset() {
		return txtIndividualYOffset;
	}

	public JTextField getIndividualZOffset() {
		return txtIndividualZOffset;
	}

	public JTable getTableMain() {
		return tableMain;
	}

	public JTable getTableCopied() {
		return tableCopied;
	}

	public JTable getTableDeleted() {
		return tableDeleted;
	}

	public JTextField getTxtCurrentPos() {
		return txtCurrentPos;
	}

	public JLabel getLblCurrentPos() {
		return lblCurrentPos;
	}

	public JLabel getLblCurrentImage() {
		return lblCurrentImage;
	}

	private void mousePressedOnTable() {
		theMainControl.updateSelection();
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public JTextField getTxtPrefix() {
		return txtNewPrefix;
	}

	public JTextField getTxtPostfix() {
		return txtNewPostfix;
	}

	public JTextField getTxtSubfolder() {
		return txtNewScreenshotSubfolder;
	}

	public JButton getSwitchCellsButton() {
		return btnSwitchCells;
	}

	public JButton getMarkCellsButton() {
		return btnMarkSwitchCells;
	}

	public JButton getBtnConvertMainGUI() {
		return btnConvertMainGUI;
	}

	public JLabel getLblDropHere() {
		return lblDropHere;
	}

	private void btnMoveRightPressed() {
		theMainControl.clickedMoveRight();
	}

	private void btnMoveLeftPressed() {
		theMainControl.clickedMoveLeft();
	}

	public void initialiseTableImages() {
		for (int lCellNumber = 0; lCellNumber < CONST_MaxCellNumber; lCellNumber++) {
			int lRow = theMainControl.calculateRowAt(lCellNumber);
			int lColoum = theMainControl.calculateColoumAt(lCellNumber);
			tblImageMain[lRow][lColoum] = new ImageIcon(MainGUI.class.getResource("/BlankImage.jpg"));
		}
		tblImageDeleted[0] = new ImageIcon(MainGUI.class.getResource("/Deleted.jpg"));
		tblImageDeleted[0]
				.setImage(tblImageDeleted[0].getImage().getScaledInstance(233, 131, java.awt.Image.SCALE_SMOOTH));
		for (int lDeletedCell = 1; lDeletedCell < 4; lDeletedCell++) {
			tblImageDeleted[lDeletedCell] = new ImageIcon(MainGUI.class.getResource("/BlankImage.jpg"));
		}
		tblImageCopied[0] = new ImageIcon(MainGUI.class.getResource("/Copied.jpg"));
		tblImageCopied[0]
				.setImage(tblImageCopied[0].getImage().getScaledInstance(233, 131, java.awt.Image.SCALE_SMOOTH));
		tblImageCopied[1] = new ImageIcon(MainGUI.class.getResource("/BlankImage.jpg"));
	}

//Serial version number for sake of not having warnings
	private class ActionFullscreen extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			theMainControl.pressedFOnImage();
		}
	}

	private class ActionCopied extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			theMainControl.pressedCtrlC();
		}
	}

	private class ActionPasted extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			theMainControl.pressedCtrlV();
		}
	}

	private class ActionDeleted extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			theMainControl.pressedDelete();
		}
	}

	private class ActionRight extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			theMainControl.pressedRight();
		}
	}

	private class ActionLeft extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			theMainControl.pressedLeft();
		}
	}

	private class ActionUp extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			theMainControl.pressedUp();
		}
	}

	private class ActionDown extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			theMainControl.pressedDown();
		}
	}
}