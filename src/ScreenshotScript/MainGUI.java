package ScreenshotScript;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.border.LineBorder;
import java.awt.event.KeyAdapter;

public class MainGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private int CONST_MaxRow = 96;
	private int CONST_MaxColoum = 8;
	private int CONST_MaxCellNumber = CONST_MaxRow * CONST_MaxColoum;
	private int CONST_DefaultScreenWidth = 1920;
	private int CONST_DefaultScreenHeight = 1080;
	private ImageIcon tblImageMain[][] = new ImageIcon[CONST_MaxRow][CONST_MaxColoum];
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
				{ tblImageMain[0][0], tblImageMain[0][1], tblImageMain[0][2], tblImageMain[0][3], tblImageMain[0][4], tblImageMain[0][5], tblImageMain[0][6], tblImageMain[0][7] },
				{ tblImageMain[1][0], tblImageMain[1][1], tblImageMain[1][2], tblImageMain[1][3], tblImageMain[1][4], tblImageMain[1][5], tblImageMain[1][6], tblImageMain[1][7] },
				{ tblImageMain[2][0], tblImageMain[2][1], tblImageMain[2][2], tblImageMain[2][3], tblImageMain[2][4], tblImageMain[2][5], tblImageMain[2][6], tblImageMain[2][7] },
				{ tblImageMain[3][0], tblImageMain[3][1], tblImageMain[3][2], tblImageMain[3][3], tblImageMain[3][4], tblImageMain[3][5], tblImageMain[3][6], tblImageMain[3][7] },
				{ tblImageMain[4][0], tblImageMain[4][1], tblImageMain[4][2], tblImageMain[4][3], tblImageMain[4][4], tblImageMain[4][5], tblImageMain[4][6], tblImageMain[4][7] },
				{ tblImageMain[5][0], tblImageMain[5][1], tblImageMain[5][2], tblImageMain[5][3], tblImageMain[5][4], tblImageMain[5][5], tblImageMain[5][6], tblImageMain[5][7] },
				{ tblImageMain[6][0], tblImageMain[6][1], tblImageMain[6][2], tblImageMain[6][3], tblImageMain[6][4], tblImageMain[6][5], tblImageMain[6][6], tblImageMain[6][7] },
				{ tblImageMain[7][0], tblImageMain[7][1], tblImageMain[7][2], tblImageMain[7][3], tblImageMain[7][4], tblImageMain[7][5], tblImageMain[7][6], tblImageMain[7][7] },
				
				{ tblImageMain[8][0], tblImageMain[8][1], tblImageMain[8][2], tblImageMain[8][3], tblImageMain[8][4], tblImageMain[8][5], tblImageMain[8][6], tblImageMain[8][7] },
				{ tblImageMain[9][0], tblImageMain[9][1], tblImageMain[9][2], tblImageMain[9][3], tblImageMain[9][4], tblImageMain[9][5], tblImageMain[9][6], tblImageMain[9][7] },
				{ tblImageMain[10][0], tblImageMain[10][1], tblImageMain[10][2], tblImageMain[10][3], tblImageMain[10][4], tblImageMain[10][5], tblImageMain[10][6], tblImageMain[10][7] },
				{ tblImageMain[11][0], tblImageMain[11][1], tblImageMain[11][2], tblImageMain[11][3], tblImageMain[11][4], tblImageMain[11][5], tblImageMain[11][6], tblImageMain[11][7] },
				{ tblImageMain[12][0], tblImageMain[12][1], tblImageMain[12][2], tblImageMain[12][3], tblImageMain[12][4], tblImageMain[12][5], tblImageMain[12][6], tblImageMain[12][7] },
				{ tblImageMain[13][0], tblImageMain[13][1], tblImageMain[13][2], tblImageMain[13][3], tblImageMain[13][4], tblImageMain[13][5], tblImageMain[13][6], tblImageMain[13][7] },
				{ tblImageMain[14][0], tblImageMain[14][1], tblImageMain[14][2], tblImageMain[14][3], tblImageMain[14][4], tblImageMain[14][5], tblImageMain[14][6], tblImageMain[14][7] },
				{ tblImageMain[15][0], tblImageMain[15][1], tblImageMain[15][2], tblImageMain[15][3], tblImageMain[15][4], tblImageMain[15][5], tblImageMain[15][6], tblImageMain[15][7] },
				
				{ tblImageMain[16][0], tblImageMain[16][1], tblImageMain[16][2], tblImageMain[16][3], tblImageMain[16][4], tblImageMain[16][5], tblImageMain[16][6], tblImageMain[16][7] },
				{ tblImageMain[17][0], tblImageMain[17][1], tblImageMain[17][2], tblImageMain[17][3], tblImageMain[17][4], tblImageMain[17][5], tblImageMain[17][6], tblImageMain[17][7] },
				{ tblImageMain[18][0], tblImageMain[18][1], tblImageMain[18][2], tblImageMain[18][3], tblImageMain[18][4], tblImageMain[18][5], tblImageMain[18][6], tblImageMain[18][7] },
				{ tblImageMain[19][0], tblImageMain[19][1], tblImageMain[19][2], tblImageMain[19][3], tblImageMain[19][4], tblImageMain[19][5], tblImageMain[19][6], tblImageMain[19][7] },
				{ tblImageMain[20][0], tblImageMain[20][1], tblImageMain[20][2], tblImageMain[20][3], tblImageMain[20][4], tblImageMain[20][5], tblImageMain[20][6], tblImageMain[20][7] },
				{ tblImageMain[21][0], tblImageMain[21][1], tblImageMain[21][2], tblImageMain[21][3], tblImageMain[21][4], tblImageMain[21][5], tblImageMain[21][6], tblImageMain[21][7] },
				{ tblImageMain[22][0], tblImageMain[22][1], tblImageMain[22][2], tblImageMain[22][3], tblImageMain[22][4], tblImageMain[22][5], tblImageMain[22][6], tblImageMain[22][7] },
				{ tblImageMain[23][0], tblImageMain[23][1], tblImageMain[23][2], tblImageMain[23][3], tblImageMain[23][4], tblImageMain[23][5], tblImageMain[23][6], tblImageMain[23][7] },
				
				{ tblImageMain[24][0], tblImageMain[24][1], tblImageMain[24][2], tblImageMain[24][3], tblImageMain[24][4], tblImageMain[24][5], tblImageMain[24][6], tblImageMain[24][7] },
				{ tblImageMain[25][0], tblImageMain[25][1], tblImageMain[25][2], tblImageMain[25][3], tblImageMain[25][4], tblImageMain[25][5], tblImageMain[25][6], tblImageMain[25][7] },
				{ tblImageMain[26][0], tblImageMain[26][1], tblImageMain[26][2], tblImageMain[26][3], tblImageMain[26][4], tblImageMain[26][5], tblImageMain[26][6], tblImageMain[26][7] },
				{ tblImageMain[27][0], tblImageMain[27][1], tblImageMain[27][2], tblImageMain[27][3], tblImageMain[27][4], tblImageMain[27][5], tblImageMain[27][6], tblImageMain[27][7] },
				{ tblImageMain[28][0], tblImageMain[28][1], tblImageMain[28][2], tblImageMain[28][3], tblImageMain[28][4], tblImageMain[28][5], tblImageMain[28][6], tblImageMain[28][7] },
				{ tblImageMain[29][0], tblImageMain[29][1], tblImageMain[29][2], tblImageMain[29][3], tblImageMain[29][4], tblImageMain[29][5], tblImageMain[29][6], tblImageMain[29][7] },
				{ tblImageMain[30][0], tblImageMain[30][1], tblImageMain[30][2], tblImageMain[30][3], tblImageMain[30][4], tblImageMain[30][5], tblImageMain[30][6], tblImageMain[30][7] },
				{ tblImageMain[31][0], tblImageMain[31][1], tblImageMain[31][2], tblImageMain[31][3], tblImageMain[31][4], tblImageMain[31][5], tblImageMain[31][6], tblImageMain[31][7] },
				
				{ tblImageMain[32][0], tblImageMain[32][1], tblImageMain[32][2], tblImageMain[32][3], tblImageMain[32][4], tblImageMain[32][5], tblImageMain[32][6], tblImageMain[32][7] },
				{ tblImageMain[33][0], tblImageMain[33][1], tblImageMain[33][2], tblImageMain[33][3], tblImageMain[33][4], tblImageMain[33][5], tblImageMain[33][6], tblImageMain[33][7] },
				{ tblImageMain[34][0], tblImageMain[34][1], tblImageMain[34][2], tblImageMain[34][3], tblImageMain[34][4], tblImageMain[34][5], tblImageMain[34][6], tblImageMain[34][7] },
				{ tblImageMain[35][0], tblImageMain[35][1], tblImageMain[35][2], tblImageMain[35][3], tblImageMain[35][4], tblImageMain[35][5], tblImageMain[35][6], tblImageMain[35][7] },
				{ tblImageMain[36][0], tblImageMain[36][1], tblImageMain[36][2], tblImageMain[36][3], tblImageMain[36][4], tblImageMain[36][5], tblImageMain[36][6], tblImageMain[36][7] },
				{ tblImageMain[37][0], tblImageMain[37][1], tblImageMain[37][2], tblImageMain[37][3], tblImageMain[37][4], tblImageMain[37][5], tblImageMain[37][6], tblImageMain[37][7] },
				{ tblImageMain[38][0], tblImageMain[38][1], tblImageMain[38][2], tblImageMain[38][3], tblImageMain[38][4], tblImageMain[38][5], tblImageMain[38][6], tblImageMain[38][7] },
				{ tblImageMain[39][0], tblImageMain[39][1], tblImageMain[39][2], tblImageMain[39][3], tblImageMain[39][4], tblImageMain[39][5], tblImageMain[39][6], tblImageMain[39][7] },
				
				{ tblImageMain[40][0], tblImageMain[40][1], tblImageMain[40][2], tblImageMain[40][3], tblImageMain[40][4], tblImageMain[40][5], tblImageMain[40][6], tblImageMain[40][7] },
				{ tblImageMain[41][0], tblImageMain[41][1], tblImageMain[41][2], tblImageMain[41][3], tblImageMain[41][4], tblImageMain[41][5], tblImageMain[41][6], tblImageMain[41][7] },
				{ tblImageMain[42][0], tblImageMain[42][1], tblImageMain[42][2], tblImageMain[42][3], tblImageMain[42][4], tblImageMain[42][5], tblImageMain[42][6], tblImageMain[42][7] },
				{ tblImageMain[43][0], tblImageMain[43][1], tblImageMain[43][2], tblImageMain[43][3], tblImageMain[43][4], tblImageMain[43][5], tblImageMain[43][6], tblImageMain[43][7] },
				{ tblImageMain[44][0], tblImageMain[44][1], tblImageMain[44][2], tblImageMain[44][3], tblImageMain[44][4], tblImageMain[44][5], tblImageMain[44][6], tblImageMain[44][7] },
				{ tblImageMain[45][0], tblImageMain[45][1], tblImageMain[45][2], tblImageMain[45][3], tblImageMain[45][4], tblImageMain[45][5], tblImageMain[45][6], tblImageMain[45][7] },
				{ tblImageMain[46][0], tblImageMain[46][1], tblImageMain[46][2], tblImageMain[46][3], tblImageMain[46][4], tblImageMain[46][5], tblImageMain[46][6], tblImageMain[46][7] },
				{ tblImageMain[47][0], tblImageMain[47][1], tblImageMain[47][2], tblImageMain[47][3], tblImageMain[47][4], tblImageMain[47][5], tblImageMain[47][6], tblImageMain[47][7] },
				
				{ tblImageMain[48][0], tblImageMain[48][1], tblImageMain[48][2], tblImageMain[48][3], tblImageMain[48][4], tblImageMain[48][5], tblImageMain[48][6], tblImageMain[48][7] },
				{ tblImageMain[49][0], tblImageMain[49][1], tblImageMain[49][2], tblImageMain[49][3], tblImageMain[49][4], tblImageMain[49][5], tblImageMain[49][6], tblImageMain[49][7] },
				{ tblImageMain[50][0], tblImageMain[50][1], tblImageMain[50][2], tblImageMain[50][3], tblImageMain[50][4], tblImageMain[50][5], tblImageMain[50][6], tblImageMain[50][7] },
				{ tblImageMain[51][0], tblImageMain[51][1], tblImageMain[51][2], tblImageMain[51][3], tblImageMain[51][4], tblImageMain[51][5], tblImageMain[51][6], tblImageMain[51][7] },
				{ tblImageMain[52][0], tblImageMain[52][1], tblImageMain[52][2], tblImageMain[52][3], tblImageMain[52][4], tblImageMain[52][5], tblImageMain[52][6], tblImageMain[52][7] },
				{ tblImageMain[53][0], tblImageMain[53][1], tblImageMain[53][2], tblImageMain[53][3], tblImageMain[53][4], tblImageMain[53][5], tblImageMain[53][6], tblImageMain[53][7] },
				{ tblImageMain[54][0], tblImageMain[54][1], tblImageMain[54][2], tblImageMain[54][3], tblImageMain[54][4], tblImageMain[54][5], tblImageMain[54][6], tblImageMain[54][7] },
				{ tblImageMain[55][0], tblImageMain[55][1], tblImageMain[55][2], tblImageMain[55][3], tblImageMain[55][4], tblImageMain[55][5], tblImageMain[55][6], tblImageMain[55][7] },
				
				{ tblImageMain[56][0], tblImageMain[56][1], tblImageMain[56][2], tblImageMain[56][3], tblImageMain[56][4], tblImageMain[56][5], tblImageMain[56][6], tblImageMain[56][7] },
				{ tblImageMain[57][0], tblImageMain[57][1], tblImageMain[57][2], tblImageMain[57][3], tblImageMain[57][4], tblImageMain[57][5], tblImageMain[57][6], tblImageMain[57][7] },
				{ tblImageMain[58][0], tblImageMain[58][1], tblImageMain[58][2], tblImageMain[58][3], tblImageMain[58][4], tblImageMain[58][5], tblImageMain[58][6], tblImageMain[58][7] },
				{ tblImageMain[59][0], tblImageMain[59][1], tblImageMain[59][2], tblImageMain[59][3], tblImageMain[59][4], tblImageMain[59][5], tblImageMain[59][6], tblImageMain[59][7] },
				{ tblImageMain[60][0], tblImageMain[60][1], tblImageMain[60][2], tblImageMain[60][3], tblImageMain[60][4], tblImageMain[60][5], tblImageMain[60][6], tblImageMain[60][7] },
				{ tblImageMain[61][0], tblImageMain[61][1], tblImageMain[61][2], tblImageMain[61][3], tblImageMain[61][4], tblImageMain[61][5], tblImageMain[61][6], tblImageMain[61][7] },
				{ tblImageMain[62][0], tblImageMain[62][1], tblImageMain[62][2], tblImageMain[62][3], tblImageMain[62][4], tblImageMain[62][5], tblImageMain[62][6], tblImageMain[62][7] },
				{ tblImageMain[63][0], tblImageMain[63][1], tblImageMain[63][2], tblImageMain[63][3], tblImageMain[63][4], tblImageMain[63][5], tblImageMain[63][6], tblImageMain[63][7] },
				
				{ tblImageMain[64][0], tblImageMain[64][1], tblImageMain[64][2], tblImageMain[64][3], tblImageMain[64][4], tblImageMain[64][5], tblImageMain[64][6], tblImageMain[64][7] },
				{ tblImageMain[65][0], tblImageMain[65][1], tblImageMain[65][2], tblImageMain[65][3], tblImageMain[65][4], tblImageMain[65][5], tblImageMain[65][6], tblImageMain[65][7] },
				{ tblImageMain[66][0], tblImageMain[66][1], tblImageMain[66][2], tblImageMain[66][3], tblImageMain[66][4], tblImageMain[66][5], tblImageMain[66][6], tblImageMain[66][7] },
				{ tblImageMain[67][0], tblImageMain[67][1], tblImageMain[67][2], tblImageMain[67][3], tblImageMain[67][4], tblImageMain[67][5], tblImageMain[67][6], tblImageMain[67][7] },
				{ tblImageMain[68][0], tblImageMain[68][1], tblImageMain[68][2], tblImageMain[68][3], tblImageMain[68][4], tblImageMain[68][5], tblImageMain[68][6], tblImageMain[68][7] },
				{ tblImageMain[69][0], tblImageMain[69][1], tblImageMain[69][2], tblImageMain[69][3], tblImageMain[69][4], tblImageMain[69][5], tblImageMain[69][6], tblImageMain[69][7] },
				{ tblImageMain[70][0], tblImageMain[70][1], tblImageMain[70][2], tblImageMain[70][3], tblImageMain[70][4], tblImageMain[70][5], tblImageMain[70][6], tblImageMain[70][7] },
				{ tblImageMain[71][0], tblImageMain[71][1], tblImageMain[71][2], tblImageMain[71][3], tblImageMain[71][4], tblImageMain[71][5], tblImageMain[71][6], tblImageMain[71][7] },
				
				{ tblImageMain[72][0], tblImageMain[72][1], tblImageMain[72][2], tblImageMain[72][3], tblImageMain[72][4], tblImageMain[72][5], tblImageMain[72][6], tblImageMain[72][7] },
				{ tblImageMain[73][0], tblImageMain[73][1], tblImageMain[73][2], tblImageMain[73][3], tblImageMain[73][4], tblImageMain[73][5], tblImageMain[73][6], tblImageMain[73][7] },
				{ tblImageMain[74][0], tblImageMain[74][1], tblImageMain[74][2], tblImageMain[74][3], tblImageMain[74][4], tblImageMain[74][5], tblImageMain[74][6], tblImageMain[74][7] },
				{ tblImageMain[75][0], tblImageMain[75][1], tblImageMain[75][2], tblImageMain[75][3], tblImageMain[75][4], tblImageMain[75][5], tblImageMain[75][6], tblImageMain[75][7] },
				{ tblImageMain[76][0], tblImageMain[76][1], tblImageMain[76][2], tblImageMain[76][3], tblImageMain[76][4], tblImageMain[76][5], tblImageMain[76][6], tblImageMain[76][7] },
				{ tblImageMain[77][0], tblImageMain[77][1], tblImageMain[77][2], tblImageMain[77][3], tblImageMain[77][4], tblImageMain[77][5], tblImageMain[77][6], tblImageMain[77][7] },
				{ tblImageMain[78][0], tblImageMain[78][1], tblImageMain[78][2], tblImageMain[78][3], tblImageMain[78][4], tblImageMain[78][5], tblImageMain[78][6], tblImageMain[78][7] },
				{ tblImageMain[79][0], tblImageMain[79][1], tblImageMain[79][2], tblImageMain[79][3], tblImageMain[79][4], tblImageMain[79][5], tblImageMain[79][6], tblImageMain[79][7] },
				
				{ tblImageMain[80][0], tblImageMain[80][1], tblImageMain[80][2], tblImageMain[80][3], tblImageMain[80][4], tblImageMain[80][5], tblImageMain[80][6], tblImageMain[80][7] },
				{ tblImageMain[81][0], tblImageMain[81][1], tblImageMain[81][2], tblImageMain[81][3], tblImageMain[81][4], tblImageMain[81][5], tblImageMain[81][6], tblImageMain[81][7] },
				{ tblImageMain[82][0], tblImageMain[82][1], tblImageMain[82][2], tblImageMain[82][3], tblImageMain[82][4], tblImageMain[82][5], tblImageMain[82][6], tblImageMain[82][7] },
				{ tblImageMain[83][0], tblImageMain[83][1], tblImageMain[83][2], tblImageMain[83][3], tblImageMain[83][4], tblImageMain[83][5], tblImageMain[83][6], tblImageMain[83][7] },
				{ tblImageMain[84][0], tblImageMain[84][1], tblImageMain[84][2], tblImageMain[84][3], tblImageMain[84][4], tblImageMain[84][5], tblImageMain[84][6], tblImageMain[84][7] },
				{ tblImageMain[85][0], tblImageMain[85][1], tblImageMain[85][2], tblImageMain[85][3], tblImageMain[85][4], tblImageMain[85][5], tblImageMain[85][6], tblImageMain[85][7] },
				{ tblImageMain[86][0], tblImageMain[86][1], tblImageMain[86][2], tblImageMain[86][3], tblImageMain[86][4], tblImageMain[86][5], tblImageMain[86][6], tblImageMain[86][7] },
				{ tblImageMain[87][0], tblImageMain[87][1], tblImageMain[87][2], tblImageMain[87][3], tblImageMain[87][4], tblImageMain[87][5], tblImageMain[87][6], tblImageMain[87][7] },
				
				{ tblImageMain[88][0], tblImageMain[88][1], tblImageMain[88][2], tblImageMain[88][3], tblImageMain[88][4], tblImageMain[88][5], tblImageMain[88][6], tblImageMain[88][7] },
				{ tblImageMain[89][0], tblImageMain[89][1], tblImageMain[89][2], tblImageMain[89][3], tblImageMain[89][4], tblImageMain[89][5], tblImageMain[89][6], tblImageMain[89][7] },
				{ tblImageMain[90][0], tblImageMain[90][1], tblImageMain[90][2], tblImageMain[90][3], tblImageMain[90][4], tblImageMain[90][5], tblImageMain[90][6], tblImageMain[90][7] },
				{ tblImageMain[91][0], tblImageMain[91][1], tblImageMain[91][2], tblImageMain[91][3], tblImageMain[91][4], tblImageMain[91][5], tblImageMain[91][6], tblImageMain[91][7] },
				{ tblImageMain[92][0], tblImageMain[92][1], tblImageMain[92][2], tblImageMain[92][3], tblImageMain[92][4], tblImageMain[92][5], tblImageMain[92][6], tblImageMain[92][7] },
				{ tblImageMain[93][0], tblImageMain[93][1], tblImageMain[93][2], tblImageMain[93][3], tblImageMain[93][4], tblImageMain[93][5], tblImageMain[93][6], tblImageMain[93][7] },
				{ tblImageMain[94][0], tblImageMain[94][1], tblImageMain[94][2], tblImageMain[94][3], tblImageMain[94][4], tblImageMain[94][5], tblImageMain[94][6], tblImageMain[94][7] },
				{ tblImageMain[95][0], tblImageMain[95][1], tblImageMain[95][2], tblImageMain[95][3], tblImageMain[95][4], tblImageMain[95][5], tblImageMain[95][6], tblImageMain[95][7] },
				};
			
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
				(int) (659 *  lHeightFactor));
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
		txtGlobalXOffset.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				theMainControl.changedGloabalXOffset(txtGlobalXOffset.getText());
			}
		});
		txtGlobalXOffset.setColumns(10);

		txtGlobalYOffset = new JTextField();
		txtGlobalYOffset.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		txtGlobalYOffset.setColumns(10);
		txtGlobalYOffset.setBounds((int) (205 * lWidthFactor), (int) (52 * lHeightFactor), (int) (86 * lWidthFactor),
				(int) (30 * lHeightFactor));
		txtGlobalYOffset.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				theMainControl.changedGloabalYOffset(txtGlobalYOffset.getText());
			}
		});
		panel_3.add(txtGlobalYOffset);

		txtGlobalZOffset = new JTextField();
		txtGlobalZOffset.setFont(new Font("Arial", Font.PLAIN, (int) (16 * lFontFactor)));
		txtGlobalZOffset.setColumns(10);
		txtGlobalZOffset.setBounds((int) (205 * lWidthFactor), (int) (93 * lHeightFactor), (int) (86 * lWidthFactor),
				(int) (30 * lHeightFactor));
		txtGlobalZOffset.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				theMainControl.changedGloabalZOffset(txtGlobalZOffset.getText());
			}
		});
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