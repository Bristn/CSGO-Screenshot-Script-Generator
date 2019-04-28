package ScreenshotScript;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Control {

	private int CONST_TblWidth = 8;
	private int CONST_TblHeigth = 96;
	private int CONST_MaxCellNumber = CONST_TblHeigth * CONST_TblWidth;
	private int CONST_MaxNewCommandSlots = 1024;
	private int CONST_DefaultScreenHeight = 1080;

	private boolean aStartGUIAlreadyExtended = false;
	private boolean aInFullImageTable = false;
	private boolean aInMainTable = false;
	private boolean aInCopiedTable = false;
	private boolean aInDeletedTable = false;
	private int aDeleteScreenshotsMode = 1;
	private boolean aDoneReadingLastSessionCfg = false;
	private int aCurrentCellMain = -5;
	private int aCurrentCellDeleted;
	private int aDeletedRotation = 1;
	private int aNewImagePosition = -5;
	private int aFullImageStartedAt = -1;
	private int aPreviousSelectedCellMain = -5;
	private int aHistoryPathCounter = 0;

	private MainGUI theMainGUI;
	private InspectFullImage theFullImageGUI;
	private StartGUI theStartGUI;
	private SettingsGUI theSettingsGUI;

	private WriteFile writeSettingsCfg;
	private WriteFile clearSettingsCfg;
	private WriteFile writeScriptNut;
	private WriteFile clearScriptNut;
	private WriteFile writeConsoleCfg;
	private WriteFile clearConsoleCfg;
	private WriteFile writeHistoryCfg;

	private CommandSlot aTmpCommandSlot = new CommandSlot(-1);
	private CommandSlot[] aNewCommandSlot = new CommandSlot[CONST_MaxNewCommandSlots];
	private CommandSlot aEmptyComandSlot = new CommandSlot(-1);
	private CommandSlot[][] aTblSlotsMain = new CommandSlot[CONST_TblWidth][CONST_TblHeigth];
	private CommandSlot[] aTblSlotDeleted = new CommandSlot[4];
	private CommandSlot aTblSlotCopied = new CommandSlot(-1);
	private int[] aMarkedCommandSlotCell = new int[4];

	private String aNoImageFound = "/NoImageFound.jpg";
	private String aNewImage = "/NewImage.jpg";
	private int aGlobaXOffset = 0;
	private int aGlobaYOffset = 0;
	private int aGlobaZOffset = 0;
	private int aDefaultXOffset = 0;
	private int aDefaultYOffset = 0;
	private int aDefaultZOffset = -64;
	private double aDelayFirst = 0.5;
	private double aDelaySecond = 1;
	private double aDelayThird = 1.5;

	private String aGameSubfolder = "";
	private String aSelectedGame = "";
	private String aPathToSteamApps = "";
	private String aDefaultPathToSteamApps = "F:\\Program Files (x86)\\Steam\\SteamApps";
	private String aCompletePathToScreenshots = "";
	private String aCompletePathToScript = "";
	private String aScreenshotPreFix = "";
	private String aScreenshotPostFix = "";
	private String aSubfolder = "";
	private String aScriptFile = "";
	private String aPathToBaseFile = "";
	private String aUnSelectedIcon = "\u25CF ";
	private String aDelayType = "Default";
	private String aCompletePathToCfg = "";

	private String aSelectionColor = "orange";
	private String aMarkingColor = "green";

	private int aMarkedCellCount = 0;
	private boolean aMarkingEnabled = false;

	public Control(StartGUI pStartGUI) {
		theStartGUI = pStartGUI;
		clearSettingsCfg = new WriteFile("__ScriptGeneratorSettings.cfg", false);
		writeSettingsCfg = new WriteFile("__ScriptGeneratorSettings.cfg", true);
		writeHistoryCfg = new WriteFile("__ScriptGeneratorFileHistory.cfg", true);
	}

//Called to calculate the row at a specific cell number. Is a own method to ensure that there is no difference in the calculations	
	public int calculateRowAt(int pCellNumber) {
		return (pCellNumber / CONST_TblWidth);
	}

//Called to calculate the coloum at a specific cell number. Is a own method to ensure that there is no difference in the calculations		
	public int calculateColoumAt(int pCellNumber) {
		int lRow = (pCellNumber / CONST_TblWidth);
		return (pCellNumber - (lRow * CONST_TblWidth));
	}

//Called to calculate the cell number at a specific row and coloum count. Is a own method to ensure that there is no difference in the calculations		
	public int calculateCellNumberAt(int pColoum, int pRow) {
		return ((CONST_TblWidth * pRow) + pColoum);
	}

//Called to initialise all	all table slots in the three different tables
	private void initialiseAllTblSlots() {
		for (int lCellNumber = 0; lCellNumber < CONST_MaxCellNumber; lCellNumber++) {
			int lRow = calculateRowAt(lCellNumber);
			int lColoum = calculateColoumAt(lCellNumber);
			aTblSlotsMain[lColoum][lRow] = new CommandSlot(lCellNumber);
		}
		for (int lDeletedSlots = 0; lDeletedSlots < 4; lDeletedSlots++) {
			aTblSlotDeleted[lDeletedSlots] = new CommandSlot(-1);
		}
		for (int lNewSlots = 0; lNewSlots < CONST_MaxNewCommandSlots; lNewSlots++) {
			aNewCommandSlot[lNewSlots] = new CommandSlot(-1);
		}
	}

//Called to read the vscript file with the according pre/postfix	
	public void readScriptFile() {
		try {
			ReadFile file = new ReadFile(aCompletePathToScript + aScriptFile);
			String[] linesScriptFile;
			linesScriptFile = file.openFile();
			for (int i = 0; i < linesScriptFile.length; i++) {
				if (linesScriptFile[i].contains("case") == true) {
					int lNumberIdxStart = (linesScriptFile[i].indexOf("case")) + 5;
					int lnumberIdxEnd = linesScriptFile[i].indexOf(":");
					int lNumber = Integer.parseInt(linesScriptFile[i].substring(lNumberIdxStart, lnumberIdxEnd));
					int lRow = calculateRowAt(lNumber);
					int lColoum = calculateColoumAt(lNumber);
					int lCmdIdxStart = linesScriptFile[i + 1].indexOf("setpos");
					int lCmdIdxEnd = (linesScriptFile[i + 1].indexOf(",", lCmdIdxStart)) - 1;
					String lCommand = linesScriptFile[i + 1].substring(lCmdIdxStart, lCmdIdxEnd);

					aTblSlotsMain[lColoum][lRow].setCommand(lCommand);

					String lScreenshotNr = "" + 0;
					if (lNumber >= 100) {
						lScreenshotNr = "" + lNumber;
					} else if (lNumber >= 10) {
						lScreenshotNr =  "0" + lNumber;
					}
					else {
						lScreenshotNr =  "00" + lNumber;
					}
					if (aScreenshotPostFix.equals("")) {
						File tempFile = new File(
								aCompletePathToScreenshots + aScreenshotPreFix + "_position_" + lScreenshotNr + ".jpg");
						if (tempFile.exists() == true) {
							aTblSlotsMain[lColoum][lRow].setImage(aCompletePathToScreenshots + aScreenshotPreFix
									+ "_position_" + lScreenshotNr + ".jpg");
						} else {
							aTblSlotsMain[lColoum][lRow].setImage(aNoImageFound, true);
						}
					} else {
						File tempFile = new File(
								aCompletePathToScreenshots + aScreenshotPreFix + "_position_" + lScreenshotNr + ".jpg");
						if (tempFile.exists() == true) {
							aTblSlotsMain[lColoum][lRow].setImage(aCompletePathToScreenshots + aScreenshotPreFix
									+ "_position_" + lScreenshotNr + "_" + aScreenshotPostFix + ".jpg");
						} else {
							aTblSlotsMain[lColoum][lRow].setImage(aNoImageFound, true);
						}
					}
					updateImageTblAt(lNumber);

					if (linesScriptFile[i + 3].contains("X:") == true) {
						int temp01 = linesScriptFile[i + 3].indexOf("X:");
						int temp02 = temp01 + 2;
						int temp03 = linesScriptFile[i + 3].indexOf(" ", temp02);
						int lIndividualXOffset = Integer.parseInt(linesScriptFile[i + 3].substring(temp02, temp03));
						aTblSlotsMain[lColoum][lRow].setIndividualXOffset(lIndividualXOffset);
					}

					if (linesScriptFile[i + 3].contains("Y:") == true) {
						int temp01 = linesScriptFile[i + 3].indexOf("Y:");
						int temp02 = temp01 + 2;
						int temp03 = linesScriptFile[i + 3].indexOf(" ", temp02);
						int lIndividualYOffset = Integer.parseInt(linesScriptFile[i + 3].substring(temp02, temp03));
						aTblSlotsMain[lColoum][lRow].setIndividualYOffset(lIndividualYOffset);
					}

					if (linesScriptFile[i + 3].contains("Z:") == true) {
						int temp01 = linesScriptFile[i + 3].indexOf("Z:");
						int temp02 = temp01 + 2;
						int lIndividualZOffset = Integer
								.parseInt(linesScriptFile[i + 3].substring(temp02, linesScriptFile[i + 3].length()));
						aTblSlotsMain[lColoum][lRow].setIndividualZOffset(lIndividualZOffset);
					}
				}
				if (linesScriptFile[i].contains("Offset <") == true) {
					int lNumberIdxStart = (linesScriptFile[i].indexOf("-")) + 2;
					int lnumberIdxEnd = linesScriptFile[i].indexOf(";");
					int lNumber = Integer.parseInt(linesScriptFile[i].substring(lNumberIdxStart, lnumberIdxEnd));
					if (linesScriptFile[i].contains("X") == true) {
						theMainGUI.getGlobalXOffset().setText("" + lNumber);
						aGlobaXOffset = lNumber;
					}
					if (linesScriptFile[i].contains("Y") == true) {
						theMainGUI.getGlobalYOffset().setText("" + lNumber);
						aGlobaYOffset = lNumber;
					}
					if (linesScriptFile[i].contains("Z") == true) {
						theMainGUI.getGlobalZOffset().setText("" + lNumber);
						aGlobaZOffset = lNumber;
					}
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(theStartGUI,
					"Could not find a \"script_screenshots.nut\" file in the given path.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

//Called to change an image at a specific point in the main table;	
	public void updateImageTblAt(int pCellNumber) {
		int lRow = calculateRowAt(pCellNumber);
		int lColoum = calculateColoumAt(pCellNumber);
		try {
			theMainGUI.getImageTblMain(lRow, lColoum).setImage(aTblSlotsMain[lColoum][lRow].getScaledImage());
		} catch (Exception e) {
		}
	}

//Called to add a selection frame to a specific image in the main table
	public void updateImageTblAtToSelectedImage(int pCellNumber, String pColor) {
		int lRow = calculateRowAt(pCellNumber);
		int lColoum = calculateColoumAt(pCellNumber);
		try {
			theMainGUI.getImageTblMain(lRow, lColoum).setImage(aTblSlotsMain[lColoum][lRow].getSelectionImage(pColor));

		} catch (Exception e) {
		}
	}

//Called to update the image in the copied table	
	public void updateImageCopied() {
		theMainGUI.getTableCopied().grabFocus();
		try {
			theMainGUI.getImageTblCopied().setImage(aTblSlotCopied.getScaledImage());

		} catch (Exception e) {
		}
		theMainGUI.getTableCopied().changeSelection(0, 1, false, false);
	}

//Called to update the image in the deletion table at a given coloum	
	public void updateImageDeleted(int pColoum) {
		theMainGUI.getTableDeleted().grabFocus();
		try {
			theMainGUI.getImageTblDeleted(pColoum).setImage(aTblSlotDeleted[pColoum].getScaledImage());
		} catch (Exception e) {
		}
		theMainGUI.getTableDeleted().changeSelection(0, pColoum, false, false);
	}

//Called to update various textfileds according to the selected command. Additionally adds a frame to the currently selected image.
	public void updateSelection() {
		String lPosition = "";
		if (getInMainTable() == true) {
			aPreviousSelectedCellMain = aCurrentCellMain;
			int lColoum = theMainGUI.getTableMain().getSelectedColumn();
			int lRow = theMainGUI.getTableMain().getSelectedRow();
			aCurrentCellMain = calculateCellNumberAt(lColoum, lRow);

			if (aMarkingEnabled == true) {
				aMarkedCellCount++;
				if (aMarkedCellCount <= 4) {
					
					updateImageTblAtToSelectedImage(aCurrentCellMain, aMarkingColor);
					if (aMarkedCellCount == 3 || aMarkedCellCount == 1) {
						aMarkedCommandSlotCell[aMarkedCellCount] = aCurrentCellMain;
					}
					
					if (aMarkedCellCount == 4) {
						theMainGUI.getSwitchCellsButton().setEnabled(true);
					}
				} else {
					aMarkingEnabled = false;
					aMarkedCellCount = 0;
					clearSwicthSelection();
					aCurrentCellMain = -5;
					updateSelection();
					theMainGUI.getSwitchCellsButton().setEnabled(false);
				}
				
			} else {

				aCurrentCellDeleted = -5;
				updateImageTblAtToSelectedImage(aCurrentCellMain, aSelectionColor);
				updateImageTblAt(aPreviousSelectedCellMain);
				if (aTblSlotsMain[lColoum][lRow].getCommand().equals("") == false) {
					theMainGUI.getTxtCurrentPos().setText(aTblSlotsMain[lColoum][lRow].getCommand());
					theMainGUI.getIndividualXOffset().setText("" + aTblSlotsMain[lColoum][lRow].getIndividualXOffset());
					theMainGUI.getIndividualYOffset().setText("" + aTblSlotsMain[lColoum][lRow].getIndividualYOffset());
					theMainGUI.getIndividualZOffset().setText("" + aTblSlotsMain[lColoum][lRow].getIndividualZOffset());
					if (aTblSlotsMain[lColoum][lRow].getPosition() <= -5) {
						theMainGUI.getLblCurrentPos().setText("Current image: Newly created image Nr."
								+ (Math.abs(aTblSlotsMain[lColoum][lRow].getPosition()) - 5) + "     (Normal)");
						theMainGUI.getLblCurrentImage()
								.setText("Newly created image Nr." + (Math.abs(aTblSlotsMain[lColoum][lRow].getPosition()) - 5));
					} else {
						if (aTblSlotsMain[lColoum][lRow].getPosition() < 10) {
							lPosition = "00" + aTblSlotsMain[lColoum][lRow].getPosition();
						} else if (aTblSlotsMain[lColoum][lRow].getPosition() < 100) {
							lPosition = "0" + aTblSlotsMain[lColoum][lRow].getPosition();
						}
						else {
							lPosition = "" + aTblSlotsMain[lColoum][lRow].getPosition();
						}
						if (aScreenshotPostFix.equals("")) {
							theMainGUI.getLblCurrentPos().setText("Current image: " + aScreenshotPreFix + "_position_"
									+ lPosition + ".jpg     (Normal)");
							theMainGUI.getLblCurrentImage()
									.setText(aScreenshotPreFix + "_position_" + lPosition + ".jpg");
						} else {
							theMainGUI.getLblCurrentPos().setText("Current image: " + aScreenshotPreFix + "_position_"
									+ lPosition + "_" + aScreenshotPostFix + ".jpg     (Normal)");
							theMainGUI.getLblCurrentImage().setText(
									aScreenshotPreFix + "_position_" + lPosition + aScreenshotPostFix + ".jpg");
						}
					}
				} else {
					theMainGUI.getLblCurrentPos().setText("Current image: No image selected");
					theMainGUI.getLblCurrentImage().setText("None");
					theMainGUI.getTxtCurrentPos().setText("");
					theMainGUI.getIndividualXOffset().setText("");
					theMainGUI.getIndividualYOffset().setText("");
					theMainGUI.getIndividualZOffset().setText("");
				}
			}
		} else if (getInCopiedTable() == true) {
			aCurrentCellMain = -5;
			aCurrentCellDeleted = -5;

			theMainGUI.getTableCopied().changeSelection(0, 1, false, false);
			if (aTblSlotCopied.getCommand().equals("") == false) {
				theMainGUI.getTxtCurrentPos().setText(aTblSlotCopied.getCommand());
				theMainGUI.getIndividualXOffset().setText("" + aTblSlotCopied.getIndividualXOffset());
				theMainGUI.getIndividualYOffset().setText("" + aTblSlotCopied.getIndividualYOffset());
				theMainGUI.getIndividualZOffset().setText("" + aTblSlotCopied.getIndividualZOffset());
				if (aTblSlotCopied.getPosition() <= -5) {
					theMainGUI.getLblCurrentPos().setText("Current image: Newly created image Nr."
							+ (Math.abs(aTblSlotCopied.getPosition()) - 5) + "     (Normal)");
					theMainGUI.getLblCurrentImage()
							.setText("Newly created image Nr." + (Math.abs(aTblSlotCopied.getPosition()) - 5));
				} else {
					if (aTblSlotCopied.getPosition() < 10) {
						lPosition = "00" + aTblSlotCopied.getPosition();
					} else if (aTblSlotCopied.getPosition() < 100) {
						lPosition = "0" + aTblSlotCopied.getPosition();
					}
					else {
						lPosition = "" + aTblSlotCopied.getPosition();
					}
					if (aScreenshotPostFix.equals("")) {
						theMainGUI.getLblCurrentPos().setText(
								"Current image: " + aScreenshotPreFix + "_position_" + lPosition + ".jpg     (Copied)");
						theMainGUI.getLblCurrentImage().setText(aScreenshotPreFix + "_position_" + lPosition + ".jpg");
					} else {
						theMainGUI.getLblCurrentPos().setText("Current image: " + aScreenshotPreFix + "_position_"
								+ lPosition + aScreenshotPostFix + ".jpg     (Copied)");
						theMainGUI.getLblCurrentImage()
								.setText(aScreenshotPreFix + "_position_" + lPosition + aScreenshotPostFix + ".jpg");
					}
				}
			} else {
				theMainGUI.getLblCurrentPos().setText("Current image: No image selected");
				theMainGUI.getLblCurrentImage().setText("None");
				theMainGUI.getTxtCurrentPos().setText("");
				theMainGUI.getIndividualXOffset().setText("");
				theMainGUI.getIndividualYOffset().setText("");
				theMainGUI.getIndividualZOffset().setText("");
			}
		} else if (getInDeletedTable() == true) {
			aCurrentCellMain = -5;

			aCurrentCellDeleted = theMainGUI.getTableDeleted().getSelectedColumn();
			if (aCurrentCellDeleted == 0) {
				theMainGUI.getTableDeleted().changeSelection(0, 1, false, false);
			}

			if (aTblSlotDeleted[aCurrentCellDeleted].getCommand().equals("") == false) {
				theMainGUI.getTxtCurrentPos().setText(aTblSlotDeleted[aCurrentCellDeleted].getCommand());
				theMainGUI.getIndividualXOffset()
						.setText("" + aTblSlotDeleted[aCurrentCellDeleted].getIndividualXOffset());
				theMainGUI.getIndividualYOffset()
						.setText("" + aTblSlotDeleted[aCurrentCellDeleted].getIndividualYOffset());
				theMainGUI.getIndividualZOffset()
						.setText("" + aTblSlotDeleted[aCurrentCellDeleted].getIndividualZOffset());
				if (aTblSlotDeleted[aCurrentCellDeleted].getPosition() <= -5) {
					theMainGUI.getLblCurrentPos().setText("Current image: Newly created image Nr."
							+ (Math.abs(aTblSlotDeleted[aCurrentCellDeleted].getPosition()) - 5) + "     (Normal)");
					theMainGUI.getLblCurrentImage()
							.setText("Newly created image Nr." + (Math.abs(aTblSlotDeleted[aCurrentCellDeleted].getPosition()) - 5));
				} else {
					if (aTblSlotDeleted[aCurrentCellDeleted].getPosition() < 10) {
						lPosition = "00" + aTblSlotDeleted[aCurrentCellDeleted].getPosition();
					} else if (aTblSlotDeleted[aCurrentCellDeleted].getPosition() < 100){
						lPosition = "0" + aTblSlotDeleted[aCurrentCellDeleted].getPosition();
					} else{
						lPosition = "" + aTblSlotDeleted[aCurrentCellDeleted].getPosition();
					}
					
					if (aScreenshotPostFix.equals("")) {
						theMainGUI.getLblCurrentPos().setText("Current image: " + aScreenshotPreFix + "_position_"
								+ lPosition + ".jpg     (Deleted)");
						theMainGUI.getLblCurrentImage().setText(aScreenshotPreFix + "_position_" + lPosition + ".jpg");
					} else {
						theMainGUI.getLblCurrentPos().setText("Current image: " + aScreenshotPreFix + "_position_"
								+ lPosition + aScreenshotPostFix + ".jpg     (Deleted)");
						theMainGUI.getLblCurrentImage().setText(aScreenshotPreFix + "_position_" + lPosition + ".jpg");
					}
				}
			} else {
				theMainGUI.getLblCurrentPos().setText("Current image: No image selected");
				theMainGUI.getLblCurrentImage().setText("None");
				theMainGUI.getTxtCurrentPos().setText("");
				theMainGUI.getIndividualXOffset().setText("");
				theMainGUI.getIndividualYOffset().setText("");
				theMainGUI.getIndividualZOffset().setText("");
			}
		}
	}

//Images don't update when changed. This selects every cell for a brief time, so it updates the image within.	
	private void updateAllImages(int pJumpBackTo, int pLastMovedCell) {
		Point lBackToPoint = theMainGUI.getScrollPane().getViewport().getViewPosition();
		int lLastMovedRow = calculateRowAt(pLastMovedCell);
		int lLastMovedColoum = calculateColoumAt(pLastMovedCell);
		theMainGUI.getTableMain().changeSelection(lLastMovedRow, lLastMovedColoum, false, false);

		theMainGUI.getScrollPane().getViewport().setViewPosition(lBackToPoint);
		int lRow = calculateRowAt(pJumpBackTo);
		int lColoum = calculateColoumAt(pJumpBackTo);
		theMainGUI.getTableMain().changeSelection(lRow, lColoum, false, false);
	}

//Returns the first unused cell of the main table. Starts search from the "pCurrentCell" parameter. Counts forward	
	public int getNextFreeCellBeginningAt(int pCurrentCell) {
		for (int lCellNumber = pCurrentCell; lCellNumber < CONST_MaxCellNumber; lCellNumber++) {
			int lColoum = calculateColoumAt(lCellNumber);
			int lRow = calculateRowAt(lCellNumber);
			if (aTblSlotsMain[lColoum][lRow].getCommand().equals("")) {
				int lNextFreeCell = calculateCellNumberAt(lColoum, lRow);
				return lNextFreeCell;
			}
		}
		return -1;
	}

//Returns the first unused cell of the main table. Starts search from the "pCurrentCell" parameter. Counts backward
	public int getPreviousFreeCellBeginningAt(int pCurrentCell) {
		for (int lCellNumber = pCurrentCell; lCellNumber >= 0; lCellNumber--) {
			int lColoum = calculateColoumAt(lCellNumber);
			int lRow = calculateRowAt(lCellNumber);
			if (aTblSlotsMain[lColoum][lRow].getCommand().equals("")) {
				int lPreviousFreeCell = calculateCellNumberAt(lColoum, lRow);
				return lPreviousFreeCell;
			}
		}
		return -1;
	}

//Called when use clicks the "->" Button	
	public void clickedMoveRight() {
		int lCurrentRow = calculateRowAt(aCurrentCellMain);
		int lCurrentColoum = calculateColoumAt(aCurrentCellMain);
		if (aTblSlotsMain[lCurrentColoum][lCurrentRow].getCommand().equals("") == false) {
			int lFirstFreeCell = getNextFreeCellBeginningAt(aCurrentCellMain);
			if (lFirstFreeCell != -1) {
				for (int lCellNumber = lFirstFreeCell; lCellNumber > aCurrentCellMain; lCellNumber--) {
					int lColoumPrevCell = calculateColoumAt(lCellNumber - 1);
					int lRowPrevCell = calculateRowAt(lCellNumber - 1);
					int lColoum = calculateColoumAt(lCellNumber);
					int lRow = calculateRowAt(lCellNumber);

					aTblSlotsMain[lColoum][lRow] = aTblSlotsMain[lColoumPrevCell][lRowPrevCell];
					aTblSlotsMain[lColoumPrevCell][lRowPrevCell] = aEmptyComandSlot;
					updateImageTblAt(lCellNumber - 1);
					updateImageTblAt(lCellNumber);
				}
				updateAllImages(aCurrentCellMain + 1, lFirstFreeCell);
				theMainGUI.getTableMain().grabFocus();
				theMainGUI.getTableMain().changeSelection(calculateRowAt(aCurrentCellMain + 1),
						calculateColoumAt(aCurrentCellMain + 1), false, false);
				updateSelection();
			}
		}
	}

//Called when user clicks the "<-" Button	
	public void clickedMoveLeft() {
		int lCurrentRow = calculateRowAt(aCurrentCellMain);
		int lCurrentColoum = calculateColoumAt(aCurrentCellMain);
		if (aTblSlotsMain[lCurrentColoum][lCurrentRow].getCommand().equals("") == false) {
			int lFirstFreeCell = getPreviousFreeCellBeginningAt(aCurrentCellMain);
			if (lFirstFreeCell != -1) {
				for (int lCellNumber = lFirstFreeCell; lCellNumber < aCurrentCellMain; lCellNumber++) {
					int lColoum = calculateColoumAt(lCellNumber);
					int lRow = calculateRowAt(lCellNumber);
					int lColoumNext = calculateColoumAt(lCellNumber + 1);
					int lRowNext = calculateRowAt(lCellNumber + 1);

					aTblSlotsMain[lColoum][lRow] = aTblSlotsMain[lColoumNext][lRowNext];
					aTblSlotsMain[lColoumNext][lRowNext] = aEmptyComandSlot;
					updateImageTblAt(lCellNumber + 1);
					updateImageTblAt(lCellNumber);
				}
				updateAllImages(aCurrentCellMain - 1, lFirstFreeCell);
				updateAllImages(aCurrentCellMain + 1, lFirstFreeCell);
				theMainGUI.getTableMain().grabFocus();
				theMainGUI.getTableMain().changeSelection(calculateRowAt(aCurrentCellMain - 1),
						calculateColoumAt(aCurrentCellMain - 1), false, false);
				updateSelection();
			}
		}
	}

//Called when "Ctrl" + "C" is pressed	
	public void pressedCtrlC() {
		if (aInMainTable == true) {
			int lCurrentRow = calculateRowAt(aCurrentCellMain);
			int lCurrentColoum = calculateColoumAt(aCurrentCellMain);
			if (aTblSlotsMain[lCurrentColoum][lCurrentRow].getCommand().equals("") == false) {
				if (aTblSlotCopied.getCommand().equals("") == true) {
					aTblSlotCopied = aTblSlotsMain[lCurrentColoum][lCurrentRow];
					aTblSlotsMain[lCurrentColoum][lCurrentRow] = aEmptyComandSlot;
					updateImageTblAt(aCurrentCellMain);
					updateImageCopied();
					aCurrentCellMain = -5;

				}
			}
		} else if (aInDeletedTable == true) {
			if (aTblSlotDeleted[aCurrentCellDeleted].getCommand().equals("") == false) {
				if (aTblSlotCopied.getCommand().equals("") == true) {
					aTblSlotCopied = aTblSlotDeleted[aCurrentCellDeleted];
					aTblSlotDeleted[aCurrentCellDeleted] = aEmptyComandSlot;
					updateImageDeleted(aCurrentCellDeleted);
					updateImageCopied();
					aCurrentCellDeleted = -5;
				}
			}
		}
	}

//Called when "Ctrl" + "V" is pressed	
	public void pressedCtrlV() {
		if (aInMainTable == true) {
			int lCurrentRow = calculateRowAt(aCurrentCellMain);
			int lCurrentColoum = calculateColoumAt(aCurrentCellMain);
			if (aTblSlotsMain[lCurrentColoum][lCurrentRow].getCommand().equals("") == true) {
				if (aTblSlotCopied.getCommand().equals("") == false) {
					aTblSlotsMain[lCurrentColoum][lCurrentRow] = aTblSlotCopied;
					aTblSlotCopied = aEmptyComandSlot;
					updateImageCopied();
					updateImageTblAt(aCurrentCellMain);
					aCurrentCellMain = -5;
				}
			}
		}
	}

//Called when "Delete" is pressed
	public void pressedDelete() {
		if (aInMainTable == true) {
			int lCurrentRow = calculateRowAt(aCurrentCellMain);
			int lCurrentColoum = calculateColoumAt(aCurrentCellMain);
			if (aTblSlotsMain[lCurrentColoum][lCurrentRow].getCommand().equals("") == false) {
				for (int lCellDeleted = 1; lCellDeleted < 4; lCellDeleted++) {
					if (aTblSlotDeleted[lCellDeleted].getCommand().equals("") == true) {
						aTblSlotDeleted[lCellDeleted] = aTblSlotsMain[lCurrentColoum][lCurrentRow];
						aTblSlotsMain[lCurrentColoum][lCurrentRow] = aEmptyComandSlot;
						updateImageDeleted(lCellDeleted);
						updateImageTblAt(aCurrentCellMain);
						aCurrentCellMain = -5;
						return;
					}
				}

				aTblSlotDeleted[aDeletedRotation] = aTblSlotsMain[lCurrentColoum][lCurrentRow];
				aTblSlotsMain[lCurrentColoum][lCurrentRow] = aEmptyComandSlot;
				updateImageDeleted(aDeletedRotation);
				updateImageTblAt(aCurrentCellMain);
				aDeletedRotation++;
				if (aDeletedRotation == 4) {
					aDeletedRotation = 1;
				}
			}
		}
		if (aInCopiedTable == true) {
			if (aTblSlotCopied.getCommand().equals("") == false) {
				for (int lCellDeleted = 1; lCellDeleted < 4; lCellDeleted++) {
					if (aTblSlotDeleted[lCellDeleted].getCommand().equals("") == true) {
						aTblSlotDeleted[lCellDeleted] = aTblSlotCopied;
						aTblSlotCopied = aEmptyComandSlot;
						updateImageDeleted(lCellDeleted);
						updateImageCopied();
						return;
					}
				}
				aTblSlotDeleted[aDeletedRotation] = aTblSlotCopied;
				aTblSlotCopied = aEmptyComandSlot;
				updateImageDeleted(aDeletedRotation);
				updateImageTblAt(aCurrentCellMain);
				aDeletedRotation++;
				if (aDeletedRotation == 4) {
					aDeletedRotation = 1;
				}
			}
		}
	}

//called when user hits "Save current position"
	public void clickedSaveCurrentPosition() {
		int lColoum = calculateColoumAt(aCurrentCellMain);
		int lRow = calculateRowAt(aCurrentCellMain);
		if (theMainGUI.getTxtCurrentPos().getText().equals("") == false) {
			if (theMainGUI.getTxtCurrentPos().getText().equals(aTblSlotsMain[lColoum][lRow].getCommand()) == false) {
				if (theMainGUI.getTxtCurrentPos().getText().contains("setpos")
						|| theMainGUI.getTxtCurrentPos().getText().contains("setang")) {
					if (theMainGUI.getTxtCurrentPos().getText().contains(";")) {
						aTblSlotsMain[lColoum][lRow] = aNewCommandSlot[(Math.abs(aNewImagePosition) - 5)];
						aTblSlotsMain[lColoum][lRow].setImage(aNewImage, true);
						aTblSlotsMain[lColoum][lRow].setCommand(theMainGUI.getTxtCurrentPos().getText());
						aTblSlotsMain[lColoum][lRow].setPosition(aNewImagePosition);
						updateImageTblAt(aCurrentCellMain);
						aNewImagePosition--;
					} else {
						JOptionPane.showMessageDialog(theMainGUI, "No semicolon found in command. Not saving.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(theMainGUI, "No 'setpos' or 'setang' found in command. Not saving.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			if (theMainGUI.getIndividualXOffset().getText().equals("")) {
				aTblSlotsMain[lColoum][lRow].setIndividualXOffset(0);
			} else {
				aTblSlotsMain[lColoum][lRow]
						.setIndividualXOffset(Integer.parseInt(theMainGUI.getIndividualXOffset().getText()));
			}

			if (theMainGUI.getIndividualYOffset().getText().equals("")) {
				aTblSlotsMain[lColoum][lRow].setIndividualYOffset(0);
			} else {
				aTblSlotsMain[lColoum][lRow]
						.setIndividualYOffset(Integer.parseInt(theMainGUI.getIndividualYOffset().getText()));
			}

			if (theMainGUI.getIndividualZOffset().getText().equals("")) {
				aTblSlotsMain[lColoum][lRow].setIndividualZOffset(0);
			} else {

				aTblSlotsMain[lColoum][lRow]
						.setIndividualZOffset(Integer.parseInt(theMainGUI.getIndividualZOffset().getText()));
			}

		} else {
			pressedDelete();
		}
		updateAllImages(aCurrentCellMain - 1, aCurrentCellMain + 1);
		aCurrentCellMain = -5;
	}

//Called when user clicks "Generate"
	public void clickedGenerate() {
		generateScriptFile();
		writeLastSessionCfg();
		if (aDeleteScreenshotsMode == 1) {
			deleteScreenshotswithSameScheme(aCompletePathToScreenshots, aScreenshotPreFix, aScreenshotPostFix);
		} else if (aDeleteScreenshotsMode == 2) {
			File file = new File(aCompletePathToScreenshots);
			file.delete();
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
		}
		JOptionPane.showMessageDialog(theMainGUI, "Successfully generated files.");
	}

//Updates the the global variables (prefix, postfix, ...)
	public void updateGlobalVariables() {
		theMainGUI.getGlobalXOffset().setText("" + aGlobaXOffset);
		theMainGUI.getGlobalYOffset().setText("" + aGlobaYOffset);
		theMainGUI.getGlobalZOffset().setText("" + aGlobaZOffset);
		theMainGUI.getTxtPostfix().setText(aScreenshotPostFix);
		theMainGUI.getTxtPrefix().setText(aScreenshotPreFix);
		theMainGUI.getTxtSubfolder().setText(aSubfolder);
	}

//Called when user presses "F" on an image to fullscreen it
	public void pressedFOnImage() {
		if (aInMainTable == true) {
			aFullImageStartedAt = aCurrentCellMain;
			int lRow = calculateRowAt(aFullImageStartedAt);
			int lColoum = calculateColoumAt(aFullImageStartedAt);
			if (aTblSlotsMain[lColoum][lRow].getCommand().equals("") == false) {
				theFullImageGUI.getFullImage().setImage(aTblSlotsMain[lColoum][lRow].getFullscreenImage());
				theFullImageGUI.setVisible(true);
				aInMainTable = false;
			}
		} else if (aInFullImageTable == true) {
			int lRow = calculateRowAt(aFullImageStartedAt);
			int lColoum = calculateColoumAt(aFullImageStartedAt);
			if (aFullImageStartedAt >= 0 && aFullImageStartedAt <= 63) {
				if (aTblSlotsMain[lColoum][lRow].getCommand().equals("") == false) {
					theFullImageGUI.getFullImage().setImage(aTblSlotsMain[lColoum][lRow].getFullscreenImage());
				} else {
					theFullImageGUI.getFullImage()
							.setImage(new ImageIcon(Control.class.getResource(aNoImageFound)).getImage());
				}
			}
		}
		int lRow = calculateRowAt(aFullImageStartedAt);
		int lColoum = calculateColoumAt(aFullImageStartedAt);
		if (aTblSlotsMain[lColoum][lRow].getCommand().equals("") == false) {
			String lPosition = "";
			if (aTblSlotsMain[lColoum][lRow].getPosition() <= -5) {
				theFullImageGUI.getLabelFileName().setText("Filename: Newly added image.");
			} else {
				if (aTblSlotsMain[lColoum][lRow].getPosition() < 10) {
					lPosition = "00" + aTblSlotsMain[lColoum][lRow].getPosition();
				} else if (aTblSlotsMain[lColoum][lRow].getPosition() < 100) {
					lPosition = "0" + aTblSlotsMain[lColoum][lRow].getPosition();
				}
				else {
					lPosition = "" + aTblSlotsMain[lColoum][lRow].getPosition();
				}
				if (aScreenshotPostFix.equals("")) {
					theFullImageGUI.getLabelFileName()
							.setText("Filename: " + aScreenshotPreFix + "_position_" + lPosition + ".jpg");
				} else {
					theFullImageGUI.getLabelFileName().setText(
							"Filename: " + aScreenshotPreFix + "_position_" + lPosition + aScreenshotPostFix + ".jpg");
				}
			}
		} else {
			theFullImageGUI.getLabelFileName().setText("Filename: No image found");
		}
		theFullImageGUI.getTableFullImage().setFocusable(false);
		theFullImageGUI.getTableFullImage().setFocusable(true);
		theFullImageGUI.getTableFullImage().grabFocus();
		theFullImageGUI.getTableFullImage().changeSelection(0, 0, false, false);
	}

//Called when user presses "ESC" when in full image mode
	public void pressedEscFullImage() {
		theFullImageGUI.setVisible(false);
	}

//Called when user presses "ESC" when in full image mode
	public void pressedLeftFullImage() {
		if (aFullImageStartedAt > 0) {
			aFullImageStartedAt--;
		}
		pressedFOnImage();
	}

//Called when user presses "ESC" when in full image mode
	public void pressedRightFullImage() {
		if (aFullImageStartedAt < 63) {
			aFullImageStartedAt++;
		}
		pressedFOnImage();
	}

//Called when user clicks edit or convert
	public void clickedMainButton() {
		theMainGUI = new MainGUI(this);
		initialiseAllTblSlots();
		if (aPathToSteamApps.equals("") == true) {
			aPathToSteamApps = aDefaultPathToSteamApps;
		}

		if (aSubfolder.equals("") == true) {
			new File(aPathToSteamApps + "\\common\\" + aSelectedGame + "\\" + aGameSubfolder
					+ "\\screenshots\\__ScriptGenerator").mkdirs();
			aCompletePathToScreenshots = aPathToSteamApps + "\\common\\" + aSelectedGame + "\\" + aGameSubfolder
					+ "\\screenshots\\__ScriptGenerator\\";

			new File(aPathToSteamApps + "\\common\\" + aSelectedGame + "\\" + aGameSubfolder
					+ "\\scripts\\vscripts\\__ScriptGenerator").mkdirs();
			aCompletePathToScript = aPathToSteamApps + "\\common\\" + aSelectedGame + "\\" + aGameSubfolder
					+ "\\scripts\\vscripts\\__ScriptGenerator\\";

			new File(aPathToSteamApps + "\\common\\" + aSelectedGame + "\\" + aGameSubfolder
					+ "\\cfg\\__ScriptGenerator").mkdirs();
			aCompletePathToCfg = aPathToSteamApps + "\\common\\" + aSelectedGame + "\\" + aGameSubfolder
					+ "\\cfg\\__ScriptGenerator\\";
		} else {
			new File(aPathToSteamApps + "\\common\\" + aSelectedGame + "\\" + aGameSubfolder
					+ "\\screenshots\\__ScriptGenerator\\" + aSubfolder).mkdirs();
			aCompletePathToScreenshots = aPathToSteamApps + "\\common\\" + aSelectedGame + "\\" + aGameSubfolder
					+ "\\screenshots\\__ScriptGenerator\\" + aSubfolder + "\\";

			new File(aPathToSteamApps + "\\common\\" + aSelectedGame + "\\" + aGameSubfolder
					+ "\\scripts\\vscripts\\__ScriptGenerator\\" + aSubfolder).mkdirs();
			aCompletePathToScript = aPathToSteamApps + "\\common\\" + aSelectedGame + "\\" + aGameSubfolder
					+ "\\scripts\\vscripts\\__ScriptGenerator\\" + aSubfolder + "\\";

			new File(aPathToSteamApps + "\\common\\" + aSelectedGame + "\\" + aGameSubfolder
					+ "\\cfg\\__ScriptGenerator\\" + aSubfolder).mkdirs();
			aCompletePathToCfg = aPathToSteamApps + "\\common\\" + aSelectedGame + "\\" + aGameSubfolder
					+ "\\cfg\\__ScriptGenerator\\" + aSubfolder + "\\";
		}
		aScreenshotPreFix = theStartGUI.getTxtDesiredPrefix().getText();
		aScreenshotPostFix = theStartGUI.getTxtDesiredPostfix().getText();

		updateScriptFile();
		writeLastSessionCfg();
	}

//Called to update the name of the vscript file	
	public void updateScriptFile() {
		String lPostfix = "";
		if (aScreenshotPostFix.equals("") == false) {
			lPostfix = "_" + aScreenshotPostFix;
		}
		String lPrefix = "";
		if (aScreenshotPreFix.equals("") == false) {
			lPrefix = aScreenshotPreFix + "_";
		}
		aScriptFile = lPrefix + "GeneratedScript" + lPostfix + ".nut";
	}

//Called when user clicks on the "Edit script" Button in the start gui
	public void clickedEditScript() {
		updateGlobalVariables();
		theFullImageGUI = new InspectFullImage(this);
		readScriptFile();
		theMainGUI.setVisible(true);
		theStartGUI.setVisible(false);
	}

//Called when textfield containing the subfolder changes	
	public void updateSubfolder() {
		aSubfolder = theStartGUI.getTxtDesiredSubfolder().getText();
		if (aGameSubfolder.equals("") == false) {
			updateStartDisplay();
		}
	}

//Called when user ticks the checkbox
	public void checkedDefaultScript(boolean pActive) {
		if (pActive == true) {
			theStartGUI.getCheckBoxScript().setText("Deafult");
			theStartGUI.getTxtPathToSteamApps().setText(aDefaultPathToSteamApps);
		} else {
			theStartGUI.getCheckBoxScript().setText("Custom");
		}
	}

//Called when textfield containing the SteamApps path changes	
	public void pathToSteamAppsChanged() {
		aPathToSteamApps = theStartGUI.getTxtPathToSteamApps().getText();
		if (theStartGUI.getTxtPathToSteamApps().getText().equals(aDefaultPathToSteamApps)) {
			theStartGUI.getCheckBoxScript().setSelected(true);
			theStartGUI.getCheckBoxScript().setText("Deafult");
		} else {
			theStartGUI.getCheckBoxScript().setSelected(false);
			theStartGUI.getCheckBoxScript().setText("Custom");
		}
	}

//Called to update the two custom textfields
	public void updateStartDisplay() {
		if (aSubfolder.equals("")) {
			theStartGUI.getTxtPathToScreenshots()
					.setText("common\\" + aSelectedGame + "\\" + aGameSubfolder + "\\screenshots\\__ScriptGenerator\\");
			theStartGUI.getTxtPathToVscript().setText(
					"common\\" + aSelectedGame + "\\" + aGameSubfolder + "\\scripts\\vscripts\\__ScriptGenerator\\");
		} else {
			theStartGUI.getTxtPathToScreenshots().setText("common\\" + aSelectedGame + "\\" + aGameSubfolder
					+ "\\screenshots\\__ScriptGenerator\\" + aSubfolder + "\\");
			theStartGUI.getTxtPathToVscript().setText("common\\" + aSelectedGame + "\\" + aGameSubfolder
					+ "\\scripts\\vscripts\\__ScriptGenerator\\" + aSubfolder + "\\");
		}
	}

//Called when user selects an item from the combo box	
	public void selectedGame() {
		if (aDoneReadingLastSessionCfg == true) {
			aSelectedGame = (String) theStartGUI.getComboBoxGame().getSelectedItem();
			aGameSubfolder = "";

			if (aSelectedGame.equals("Please select a game") == false) {
				if (aSelectedGame.equals("Custom game configuration") == true) {
					sizeStartGUI(0);
					customSelectedGameChanged();
					customShortVersionChanged();
				} else {
					switch (aSelectedGame) {
					case "Counter-Strike Global Offensive":
						aGameSubfolder = "csgo";
						break;
					case "Portal 2":
						aGameSubfolder = "portal2";
						break;
					case "Left 4 Dead 2":
						aGameSubfolder = "left4dead2";
						break;
					case "Alien Swarm":
						aGameSubfolder = "swarm";
						break;
					}
					theStartGUI.getTxtPathToVscript().setEditable(false);
					theStartGUI.getTxtPathToScreenshots().setEditable(false);
					updateStartDisplay();
					theStartGUI.getBtnEdit().setEnabled(true);
					sizeStartGUI(1);
				}
			} else {
				sizeStartGUI(1);
				aGameSubfolder = "";
				theStartGUI.getBtnEdit().setEnabled(false);
				theStartGUI.getTxtPathToScreenshots().setText("");
				theStartGUI.getTxtPathToVscript().setText("");
			}
		}
	}

//Used to change height of start gui	
	public void sizeStartGUI(int pType) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double lScreenHeight = (double) screenSize.getHeight();
		double lHeightFactor = lScreenHeight / CONST_DefaultScreenHeight;

		int lXOffset = (int) theStartGUI.getBounds().getX();
		int lYOffset = (int) theStartGUI.getBounds().getY();
		int lWidth = (int) theStartGUI.getBounds().getWidth();
		int lHeight = (int) theStartGUI.getBounds().getHeight();
		if (pType == 0) {
			if (aStartGUIAlreadyExtended == false) {
				theStartGUI.setBounds(lXOffset, lYOffset, lWidth, (int) (lHeight + 65 * lHeightFactor));
				aStartGUIAlreadyExtended = true;
			}
		}
		if (pType == 1) {
			if (aStartGUIAlreadyExtended == true) {
				theStartGUI.setBounds(lXOffset, lYOffset, lWidth, (int) (lHeight - 65 * lHeightFactor));
				aStartGUIAlreadyExtended = false;
			}
		}
	}

//Called when the custom game selected changes
	public void customSelectedGameChanged() {
		aSelectedGame = theStartGUI.getTxtCustomGame().getText();
		enableMainButtons();
		updateStartDisplay();
	}

//Called when the custom short version changes
	public void customShortVersionChanged() {
		aGameSubfolder = theStartGUI.getTxtShortVersion().getText();
		enableMainButtons();
		updateStartDisplay();
	}	
	
//Called to enable the two buttons, if custom fields arent empty	
	private void enableMainButtons() {
		if (aSelectedGame.equals("") == true || aGameSubfolder.equals("") == true) {
			theStartGUI.getBtnEdit().setEnabled(false);
		} else if (aSelectedGame.equals("") == false && aGameSubfolder.equals("") == false) {
			theStartGUI.getBtnEdit().setEnabled(true);
		}
	}
	

//Called to save settings to the last session cfg	
	public void writeLastSessionCfg() {
		try {
			if (aSelectedGame.equals("") == false && aGameSubfolder.equals("") == false) {
				clearSettingsCfg.writeToFile("Config file which saves the settings of the last session");
				writeSettingsCfg.writeToFile("SteamApps: " + aPathToSteamApps);
				writeSettingsCfg.writeToFile("Screenshots: " + aCompletePathToScreenshots);
				writeSettingsCfg.writeToFile("Scripts: " + aCompletePathToScript);
				writeSettingsCfg.writeToFile("Game: " + aSelectedGame);
				writeSettingsCfg.writeToFile("GameShort: " + aGameSubfolder);
				writeSettingsCfg.writeToFile("Subfolder: " + aSubfolder);
				writeSettingsCfg.writeToFile("Prefix: " + aScreenshotPreFix);
				writeSettingsCfg.writeToFile("Postfix: " + aScreenshotPostFix);
				writeSettingsCfg.writeToFile("Selection color: " + aSelectionColor);
				writeSettingsCfg.writeToFile("Switching color: " + aMarkingColor);
				writeSettingsCfg.writeToFile("Delete old screenshots: " + aDeleteScreenshotsMode);
				writeSettingsCfg.writeToFile("Delay type: " + aDelayType);
				writeSettingsCfg.writeToFile("Default X-Offset: " + aDefaultXOffset);
				writeSettingsCfg.writeToFile("Default Y-Offset: " + aDefaultYOffset);
				writeSettingsCfg.writeToFile("Default Z-Offset: " + aDefaultZOffset);
				writeSettingsCfg.writeToFile("Cfgs: " + aCompletePathToCfg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//Called to read the last session cfg	
	public void readLastSessionCfg() {
		try {
			File lLastSession = new File("__ScriptGeneratorSettings.cfg");
			if (lLastSession.exists() == true) {
				ReadFile file = new ReadFile("__ScriptGeneratorSettings.cfg");
				String[] linesScriptFile;
				linesScriptFile = file.openFile();
				for (int i = 0; i < linesScriptFile.length; i++) {
					if (linesScriptFile[i].contains("SteamApps: ") == true) {
						aPathToSteamApps = linesScriptFile[i].substring(11, linesScriptFile[i].length());
						theStartGUI.getTxtPathToSteamApps().setText(aPathToSteamApps);
						if (aPathToSteamApps.equals(aDefaultPathToSteamApps) == false) {
							theStartGUI.getCheckBoxScript().setSelected(false);
							theStartGUI.getCheckBoxScript().setText("Custom");
						}
					}
					if (linesScriptFile[i].contains("Screenshots: ") == true) {
						aCompletePathToScreenshots = linesScriptFile[i].substring(13, linesScriptFile[i].length());
						int lIdxStart = aCompletePathToScreenshots.indexOf("common");
						String lPath = aCompletePathToScreenshots.substring(lIdxStart,
								aCompletePathToScreenshots.length());
						theStartGUI.getTxtPathToScreenshots().setText(lPath);
					}
					if (linesScriptFile[i].contains("Scripts: ") == true) {
						aCompletePathToScript = linesScriptFile[i].substring(9, linesScriptFile[i].length());
						int lIdxStart = aCompletePathToScript.indexOf("common");
						String lPath = aCompletePathToScript.substring(lIdxStart, aCompletePathToScript.length());
						theStartGUI.getTxtPathToVscript().setText(lPath);
					}
					if (linesScriptFile[i].contains("Game: ") == true) {
						aSelectedGame = linesScriptFile[i].substring(6, linesScriptFile[i].length());
						int lCounter = 0;
						for (int j = 0; j < 4; j++) {
							String lItem = (String) theStartGUI.getComboBoxGame().getItemAt(j);
							if (lItem.equals(aSelectedGame) == true) {
								theStartGUI.getComboBoxGame().setSelectedIndex(j);
							} else {
								lCounter++;
							}
						}
						if (lCounter == 4) {
							theStartGUI.getComboBoxGame().setSelectedIndex(4);
							sizeStartGUI(0);
							theStartGUI.getTxtCustomGame().setText(aSelectedGame);
						}
					}
					if (linesScriptFile[i].contains("GameShort: ") == true) {
						aGameSubfolder = linesScriptFile[i].substring(11, linesScriptFile[i].length());
						if (theStartGUI.getComboBoxGame().getSelectedIndex() == 4) {
							theStartGUI.getTxtShortVersion().setText(aGameSubfolder);
						}
					}
					if (linesScriptFile[i].contains("Subfolder: ") == true) {
						aSubfolder = linesScriptFile[i].substring(11, linesScriptFile[i].length());
						theStartGUI.getTxtDesiredSubfolder().setText(aSubfolder);
					}
					if (linesScriptFile[i].contains("Prefix: ") == true) {
						aScreenshotPreFix = linesScriptFile[i].substring(8, linesScriptFile[i].length());
						theStartGUI.getTxtDesiredPrefix().setText(aScreenshotPreFix);
					}
					if (linesScriptFile[i].contains("Postfix: ") == true) {
						aScreenshotPostFix = linesScriptFile[i].substring(9, linesScriptFile[i].length());
						theStartGUI.getTxtDesiredPostfix().setText(aScreenshotPostFix);
					}
					if (linesScriptFile[i].contains("Selection color: ") == true) {
						aSelectionColor = linesScriptFile[i].substring(17, linesScriptFile[i].length());
					}
					if (linesScriptFile[i].contains("Switching color: ") == true) {
						aMarkingColor = linesScriptFile[i].substring(17, linesScriptFile[i].length());
					}
					if (linesScriptFile[i].contains("Delete old screenshots: ") == true) {
						aDeleteScreenshotsMode = Integer
								.parseInt(linesScriptFile[i].substring(24, linesScriptFile[i].length()));
					}
					if (linesScriptFile[i].contains("Delay type: ") == true) {
						aDelayType = linesScriptFile[i].substring(12, linesScriptFile[i].length());
					}
					if (linesScriptFile[i].contains("Default X-Offset: ") == true) {
						aDefaultXOffset = Integer
								.parseInt(linesScriptFile[i].substring(18, linesScriptFile[i].length()));
					}
					if (linesScriptFile[i].contains("Default Y-Offset: ") == true) {
						aDefaultYOffset = Integer
								.parseInt(linesScriptFile[i].substring(18, linesScriptFile[i].length()));
					}
					if (linesScriptFile[i].contains("Default Z-Offset: ") == true) {
						aDefaultZOffset = Integer
								.parseInt(linesScriptFile[i].substring(18, linesScriptFile[i].length()));
					}
					if (linesScriptFile[i].contains("Cfgs: ") == true) {
						aCompletePathToScript = linesScriptFile[i].substring(6, linesScriptFile[i].length());
					}
				}
			} else {
				aSelectedGame = "Alien Swarm";
				aGameSubfolder = "swarm";
				aCompletePathToScreenshots = "common\\" + aSelectedGame + "\\" + aGameSubfolder
						+ "\\screenshots\\__ScriptGenerator\\";
				aCompletePathToScript = "common\\" + aSelectedGame + "\\" + aGameSubfolder
						+ "\\scripts\\vscripts\\__ScriptGenerator\\";
				theStartGUI.getTxtCustomGame().setText(aSelectedGame);
				theStartGUI.getTxtShortVersion().setText(aGameSubfolder);
				theStartGUI.getTxtPathToScreenshots().setText(aCompletePathToScreenshots);
				theStartGUI.getTxtPathToVscript().setText(aCompletePathToScript);
			}
		} catch (IOException e) {
		}
		aDoneReadingLastSessionCfg = true;
	}

//Called when clicked the convert button in the start gui	
	public void clickedConvertStartGUI() {
		try {
			ReadFile file = new ReadFile(aPathToBaseFile);
			String[] arrayLines;
			arrayLines = file.openFile();
			int lCellNumber = 0;
			int lCommandsInTxtFile = 0;
			for (int i = 0; i < arrayLines.length; i++) {
				if (arrayLines[i].contains("setpos") == true) {
					lCommandsInTxtFile++;
				}
			}
			if (lCommandsInTxtFile <= CONST_MaxCellNumber) {
				for (int i = 0; i < arrayLines.length; i++) {
					if (arrayLines[i].contains("setpos") == true) {
						int lRow = calculateRowAt(lCellNumber);
						int lColoum = calculateColoumAt(lCellNumber);
						aTblSlotsMain[lColoum][lRow].setCommand(arrayLines[i]);
						aGlobaXOffset = aDefaultXOffset;
						aGlobaYOffset = aDefaultYOffset;
						aGlobaZOffset = aDefaultZOffset;
						lCellNumber++;
					}
				}
			} else {
				JOptionPane.showMessageDialog(theStartGUI,
						"Detected too many 'setpos' commands. Maximum command count is 64. To continue delete some commands.",
						"Error too many commands!", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e) {
		}
		generateScriptFile();
		JOptionPane.showMessageDialog(theStartGUI, "Successfully generated files.");
	}

//Called when user clicks the convert button on the main GUI	
	public void clickedConvertMainGUI() {
		try {
			ReadFile file = new ReadFile(aPathToBaseFile);
			String[] arrayLines;
			arrayLines = file.openFile();
			int lCellNumber = 0;
			int lCommandsInTxtFile = 0;
			int lFreeCells = getFreeCells();
			for (int i = 0; i < arrayLines.length; i++) {
				if (arrayLines[i].contains("setpos") == true || arrayLines[i].contains("setang") == true) {
					lCommandsInTxtFile++;
				}
			}
			if (lCommandsInTxtFile <= lFreeCells) {
				for (int i = 0; i < arrayLines.length; i++) {
					lCellNumber = getNextFreeCellBeginningAt(0);
					int lRow = calculateRowAt(lCellNumber);
					int lColoum = calculateColoumAt(lCellNumber);
					if (arrayLines[i].equals(aTblSlotsMain[lColoum][lRow].getCommand()) == false) {
						if (arrayLines[i].contains("setpos") == true || arrayLines[i].contains("setang")) {
							aTblSlotsMain[lColoum][lRow] = aNewCommandSlot[(Math.abs(aNewImagePosition) - 5)];
							aTblSlotsMain[lColoum][lRow].setImage(aNewImage, true);
							aTblSlotsMain[lColoum][lRow].setCommand(arrayLines[i]);
							aTblSlotsMain[lColoum][lRow].setPosition(aNewImagePosition);
							aNewImagePosition--;
							theMainGUI.getTableMain().grabFocus();
							updateImageTblAt(lCellNumber);
							theMainGUI.getTableMain().changeSelection(lRow, lColoum, false, false);

						}
					}
				}
			} else {
				JOptionPane.showMessageDialog(theStartGUI,
						"Detected too many 'setpos' commands. You only have " + lFreeCells
								+ " available cells and in the selected file there are " + lCommandsInTxtFile
								+ ". To continue delete some commands.",
						"Error too many commands!", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e) {
		}
	}

//Called to get all available cells	
	public int getFreeCells() {
		int lFreeCells = 0;
		for (int lCellNumber = 0; lCellNumber < CONST_MaxCellNumber; lCellNumber++) {
			int lColoum = calculateColoumAt(lCellNumber);
			int lRow = calculateRowAt(lCellNumber);
			if (aTblSlotsMain[lColoum][lRow].getCommand().equals("") == true) {
				lFreeCells++;
			}
		}
		return lFreeCells;
	}

//Called to generate the script file	
	public void generateScriptFile() {
		updateScriptFile();
		String lPrefix = "";
		if (aScreenshotPreFix.equals("") == false) {
			lPrefix = aScreenshotPreFix + "_";
		}
		String lPostfix = "";
		if (aScreenshotPostFix.equals("") == false) {
			lPostfix = "_" + aScreenshotPostFix;
		}
		String lSubFolder = "";
		if (aSubfolder.equals("") == false) {
			lSubFolder = aSubfolder + "/";
		}
		aCompletePathToScript = aPathToSteamApps + "\\common\\" + aSelectedGame + "\\" + aGameSubfolder
				+ "\\scripts\\vscripts\\__ScriptGenerator\\" + lSubFolder;

		clearScriptNut = new WriteFile(aCompletePathToScript + aScriptFile, false);
		writeScriptNut = new WriteFile(aCompletePathToScript + aScriptFile, true);
		try {
			clearScriptNut.writeToFile(
					"//Don't change variable names and/or the formatting, as the executable depends on them");
			writeScriptNut.writeToFile("::aScreenshotNumber <- 0;");
			writeScriptNut.writeToFile("::aPlayer <- null;");
			writeScriptNut.writeToFile("::aXOffset <- " + aGlobaXOffset + ";");
			writeScriptNut.writeToFile("::aYOffset <- " + aGlobaYOffset + ";");
			writeScriptNut.writeToFile("::aZOffset <- " + aGlobaZOffset + ";");
			writeScriptNut.writeToFile("::aClientCommand  <- null;");
			writeScriptNut.writeToFile("function runScreenshotRoutine() {");
			writeScriptNut.writeToFile("	if(::aPlayer == null) {");
			writeScriptNut
					.writeToFile("		::aClientCommand <- Entities.CreateByClassname(\"point_clientcommand\");");
			writeScriptNut.writeToFile(
					"		::aClientCommand.__KeyValueFromString(\"targetname\", \"entity_client_command\");");
			writeScriptNut.writeToFile("		::aPlayer = Entities.FindByClassname(null, \"player\");");
			writeScriptNut.writeToFile(
					"		DoEntFire(\"entity_client_command\", \"Command\", \"sv_cheats 1\", 0, ::aPlayer, ::aPlayer);");
			writeScriptNut.writeToFile(
					"		DoEntFire(\"entity_client_command\", \"Command\", \"developer 0\", 0, ::aPlayer, ::aPlayer); ");
			writeScriptNut.writeToFile(
					"		DoEntFire(\"entity_client_command\", \"Command\", \"r_drawviewmodel 0\", 0, ::aPlayer, ::aPlayer); ");
			writeScriptNut.writeToFile(
					"		DoEntFire(\"entity_client_command\", \"Command\", \"cl_drawhud 0\", 0, ::aPlayer, ::aPlayer); ");
			writeScriptNut.writeToFile(
					"		DoEntFire(\"entity_client_command\", \"Command\", \"net_graph 0\", 0, ::aPlayer, ::aPlayer); ");
			writeScriptNut.writeToFile(
					"		DoEntFire(\"entity_client_command\", \"Command\", \"r_disable_distance_fade_on_big_props 1\", 0, ::aPlayer, ::aPlayer); ");
			writeScriptNut.writeToFile(
					"		DoEntFire(\"entity_client_command\", \"Command\", \"r_disable_distance_fade_on_big_props_thresh 2\", 0, ::aPlayer, ::aPlayer); ");
			writeScriptNut.writeToFile("		if(::aPlayer.IsNoclipping() == false) {");
			writeScriptNut.writeToFile(
					"			DoEntFire(\"entity_client_command\", \"Command\", \"noclip\", 0, ::aPlayer, ::aPlayer); ");
			writeScriptNut.writeToFile("		}");
			writeScriptNut.writeToFile("	}");
			writeScriptNut.writeToFile("	switch(::aScreenshotNumber) {");

			for (int lCellAt = 0; lCellAt < CONST_MaxCellNumber; lCellAt++) {
				int lColoum = calculateColoumAt(lCellAt);
				int lRow = calculateRowAt(lCellAt);
				if (aTblSlotsMain[lColoum][lRow].getCommand().equals("") == false) {
					writeScriptNut.writeToFile("		case " + lCellAt + ":");
					writeScriptNut.writeToFile("		DoEntFire(\"entity_client_command\", \"Command\", \""
							+ aTblSlotsMain[lColoum][lRow].getCommand() + "\", 0, ::aPlayer, ::aPlayer); ");
					double lXPosition = aTblSlotsMain[lColoum][lRow].getXPosition() + aGlobaXOffset
							+ aTblSlotsMain[lColoum][lRow].getIndividualXOffset();
					double lYPosition = aTblSlotsMain[lColoum][lRow].getYPosition() + aGlobaYOffset
							+ aTblSlotsMain[lColoum][lRow].getIndividualYOffset();
					double lZPosition = aTblSlotsMain[lColoum][lRow].getZPosition() + aGlobaZOffset
							+ aTblSlotsMain[lColoum][lRow].getIndividualZOffset();
					writeScriptNut.writeToFile("		DoEntFire(\"entity_client_command\", \"Command\", \"setpos "
							+ lXPosition + " " + lYPosition + " " + lZPosition + "\", " + aDelayFirst
							+ ", ::aPlayer, ::aPlayer); ");
					writeScriptNut.writeToFile("		//X:" + aTblSlotsMain[lColoum][lRow].getIndividualXOffset()
							+ " Y:" + aTblSlotsMain[lColoum][lRow].getIndividualYOffset() + " Z:"
							+ aTblSlotsMain[lColoum][lRow].getIndividualZOffset());
					String lNumber = "";
					if (lCellAt < 10) {
						lNumber = "00" + lCellAt;
					} else if (lCellAt < 100){
						lNumber = "0" + lCellAt;
					} else {
						lNumber = "" + lCellAt;
					}
					writeScriptNut.writeToFile("		DoEntFire(\"entity_client_command\", \"Command\", \"jpeg "
							+ "__ScriptGenerator/" + lSubFolder + lPrefix + "position_" + lNumber + lPostfix
							+ " 100\", " + aDelaySecond + ", ::aPlayer, ::aPlayer);");
					writeScriptNut.writeToFile("			break;");
				}
			}
			writeScriptNut.writeToFile("	}");
			int lLastEntryAt = getLastEntryCell();
			writeScriptNut.writeToFile("	if(::aScreenshotNumber != " + lLastEntryAt + ") {");
			writeScriptNut.writeToFile("		::aScreenshotNumber += 1;");
			writeScriptNut.writeToFile(
					"		EntFire(\"entity_script_screenshots\", \"RunscriptCode\", \"runScreenshotRoutine()\", "
							+ aDelayThird + ");");
			writeScriptNut.writeToFile("	}");
			writeScriptNut.writeToFile("}");
			writeScriptNut.writeToFile("runScreenshotRoutine();");
		} catch (IOException e) {
			e.printStackTrace();
		}

		String lPathToCfgs = "";
		lPathToCfgs = aPathToSteamApps + "\\common\\" + aSelectedGame + "\\" + aGameSubfolder
				+ "\\cfg\\__ScriptGenerator\\" + lSubFolder + lPrefix + "ConsoleCfg" + lPostfix + ".cfg";
		clearConsoleCfg = new WriteFile(lPathToCfgs, false);
		writeConsoleCfg = new WriteFile(lPathToCfgs, true);

		try {
			clearConsoleCfg.writeToFile("hideconsole");
			writeConsoleCfg.writeToFile("ent_create logic_script");
			writeConsoleCfg.writeToFile("Ent_fire !picker Addoutput \"targetname entity_script_screenshots");
			writeConsoleCfg.writeToFile("Ent_fire entity_script_screenshots runscriptfile \"__scriptgenerator/"
					+ lSubFolder + lPrefix + "GeneratedScript" + lPostfix + "\"");

		} catch (IOException e) {
		}
		addFilesToHistoryCfg();
	}

//Called when user drag and drops a file onto the start gui	
	public void droppedFileOntoStartGUI(String pPath) {
		if (pPath.endsWith(".txt") == true) {
			aPathToBaseFile = pPath;
			theStartGUI.getBtnConvert().setEnabled(true);
			int lLastSlash = pPath.lastIndexOf("\\");
			theStartGUI.getLblDropFile().setText(pPath.substring(lLastSlash + 1, pPath.length()));
			theStartGUI.getLblDropFile().setIcon(new ImageIcon(StartGUI.class.getResource("/correct.png")));
		}
	}

//Called when user drag and drops a file onto the main gui	
	public void droppedFileOntoMainGUI(String pPath) {
		if (pPath.endsWith(".txt") == true) {
			aPathToBaseFile = pPath;
			theMainGUI.getBtnConvertMainGUI().setEnabled(true);
			int lLastSlash = pPath.lastIndexOf("\\");
			theMainGUI.getLblDropHere().setText(pPath.substring(lLastSlash + 1, pPath.length()));
			theMainGUI.getLblDropHere().setIcon(new ImageIcon(StartGUI.class.getResource("/correct.png")));
		}
	}

//Called to get the number of the last used cell	
	private int getLastEntryCell() {
		int lLastEntryAt = 0;
		for (int lCellNumber = 0; lCellNumber < CONST_MaxCellNumber; lCellNumber++) {
			int lRow = calculateRowAt(lCellNumber);
			int lColoum = calculateColoumAt(lCellNumber);
			if (aTblSlotsMain[lColoum][lRow].getCommand().equals("") == true) {
				lLastEntryAt = lCellNumber;
			}
		}
		return lLastEntryAt;
	}

//Called when user clicks the mark switching button	
	public void clickedMarkSwitch() {
		if (aMarkingEnabled == true) {
			aMarkingEnabled = false;
			clearSwicthSelection();
		} else {
			aMarkingEnabled = true;
		}
		theMainGUI.getSwitchCellsButton().setEnabled(false);
		aMarkedCellCount = 0;
		updateImageTblAt(aCurrentCellMain);
	}

//Called when user clicks the settings wheel	
	public void clickedSettingsButton() {
		if (theSettingsGUI == null) {
			theSettingsGUI = new SettingsGUI(this);
		}
		theSettingsGUI.setVisible(true);
		theSettingsGUI.getListSelectionColor().setSelectedValue(aUnSelectedIcon + aSelectionColor, false);
		theSettingsGUI.getListSwitchingColor().setSelectedValue(aUnSelectedIcon + aMarkingColor, false);
		theSettingsGUI.getTxtDefaultX().setText("" + aDefaultXOffset);
		theSettingsGUI.getTxtDefaultY().setText("" + aDefaultYOffset);
		theSettingsGUI.getTxtDefaultZ().setText("" + aDefaultZOffset);
		switch (aDelayType) {
		case "Default":
			theSettingsGUI.getRadioButtonDefaultDelay().setSelected(true);
			break;

		case "Short":
			theSettingsGUI.getRadioButtonShortDelay().setSelected(true);
			break;

		case "Long":
			theSettingsGUI.getRadioButtonLongDelay().setSelected(true);
			break;
		}
		if (aDeleteScreenshotsMode == 0) {
			theSettingsGUI.getCheckBoxMode3().setSelected(true);
		} else if (aDeleteScreenshotsMode == 1) {
			theSettingsGUI.getCheckBoxMode1().setSelected(true);
		} else if (aDeleteScreenshotsMode == 2) {
			theSettingsGUI.getCheckBoxMode2().setSelected(true);
		}
	}

//Called when user clicks apply and return
	public void clickedApplySettings() {
		aDefaultXOffset = Integer.parseInt(theSettingsGUI.getTxtDefaultX().getText());
		aDefaultYOffset = Integer.parseInt(theSettingsGUI.getTxtDefaultY().getText());
		aDefaultZOffset = Integer.parseInt(theSettingsGUI.getTxtDefaultZ().getText());
		theSettingsGUI.setVisible(false);
		writeLastSessionCfg();
	}

//Called when user changes the selection color
	public void selectionColorChangedTo(String pColor) {
		int lStartIdx = pColor.indexOf(" ") + 1;
		String lColor = pColor.substring(lStartIdx, pColor.length());
		aSelectionColor = lColor;
	}

//Called when user changes the marking color
	public void switchingColorChangedTo(String pColor) {
		int lStartIdx = pColor.indexOf(" ") + 1;
		String lColor = pColor.substring(lStartIdx, pColor.length());
		aMarkingColor = lColor;
	}

//Called to clear the marked cells 
	private void clearSwicthSelection() {
		int lRowFirstCommand = calculateRowAt(aMarkedCommandSlotCell[1]);
		int lColoumFirstCommand = calculateColoumAt(aMarkedCommandSlotCell[1]);
		int lRowSecondCommand = calculateRowAt(aMarkedCommandSlotCell[3]);
		int lColoumSecondCommand = calculateColoumAt(aMarkedCommandSlotCell[3]);
		updateImageTblAt(aMarkedCommandSlotCell[1]);
		updateImageTblAt(aMarkedCommandSlotCell[3]);
		for (int i = 0; i < aMarkedCommandSlotCell.length; i++) {
			if (aMarkedCommandSlotCell[i] != -5) {
				theMainGUI.getTableMain().changeSelection(lRowFirstCommand, lColoumFirstCommand, false, false);
				theMainGUI.getTableMain().changeSelection(lRowSecondCommand, lColoumSecondCommand, false, false);
			}
		}
		for (int i = 0; i < aMarkedCommandSlotCell.length; i++) {
			aMarkedCommandSlotCell[i] = -5;
		}
	}

//Called when user clicks the switch marked cells
	public void clickedSwitchMarkedCells() {
		int lRowFirstCommand = calculateRowAt(aMarkedCommandSlotCell[1]);
		int lColoumFirstCommand = calculateColoumAt(aMarkedCommandSlotCell[1]);
		int lRowSecondCommand = calculateRowAt(aMarkedCommandSlotCell[3]);
		int lColoumSecondCommand = calculateColoumAt(aMarkedCommandSlotCell[3]);
		aTmpCommandSlot = aTblSlotsMain[lColoumFirstCommand][lRowFirstCommand];
		aTblSlotsMain[lColoumFirstCommand][lRowFirstCommand] = aTblSlotsMain[lColoumSecondCommand][lRowSecondCommand];
		aTblSlotsMain[lColoumSecondCommand][lRowSecondCommand] = aTmpCommandSlot;
		aMarkingEnabled = false;
		clearSwicthSelection();
		theMainGUI.getSwitchCellsButton().setEnabled(false);
		theMainGUI.getTableMain().grabFocus();
		aCurrentCellMain = -5;
		updateSelection();
	}

//Called when user presses Arrow Right in the main table	
	public void pressedRight() {
		int lNextCell = aCurrentCellMain + 1;
		if (lNextCell <= 63) {
			int lColoum = calculateColoumAt(lNextCell);
			int lRow = calculateRowAt(lNextCell);
			theMainGUI.getTableMain().changeSelection(lRow, lColoum, false, false);
			updateSelection();
		}
	}

//Called when user presses Arrow Left in the main table	
	public void pressedLeft() {
		int lPrevCell = aCurrentCellMain - 1;
		if (lPrevCell >= 0) {
			int lColoum = calculateColoumAt(lPrevCell);
			int lRow = calculateRowAt(lPrevCell);

			theMainGUI.getTableMain().changeSelection(lRow, lColoum, false, false);
			updateSelection();
		}
	}

//Called when user presses Arrow up in the main table	
	public void pressedUp() {
		int lPrevCell = aCurrentCellMain - 8;
		if (lPrevCell >= 0) {
			int lColoum = calculateColoumAt(lPrevCell);
			int lRow = calculateRowAt(lPrevCell);

			theMainGUI.getTableMain().changeSelection(lRow, lColoum, false, false);
			updateSelection();
		}
	}

//Called when user presses Arrow down in the main table
	public void pressedDown() {
		int lPrevCell = aCurrentCellMain + 8;
		if (lPrevCell <= 63) {
			int lColoum = calculateColoumAt(lPrevCell);
			int lRow = calculateRowAt(lPrevCell);

			theMainGUI.getTableMain().changeSelection(lRow, lColoum, false, false);
			updateSelection();
		}
	}

//Cakked wheb the user changes the Delete mode	
	public void settingsDeleteScreenshotsChanged(int pMode) {
		aDeleteScreenshotsMode = pMode;
	}

//Called when user schanges the delay settings
	public void settingsScreenshotDelayChanged(String pChangedTo) {
		switch (pChangedTo) {
		case "Default":
			aDelayFirst = 0.5;
			aDelaySecond = 1;
			aDelayThird = 1.5;
			break;
		case "Short":
			aDelayFirst = 0.25;
			aDelaySecond = 0.5;
			aDelayThird = 0.75;
			break;
		case "Long":
			aDelayFirst = 1;
			aDelaySecond = 2;
			aDelayThird = 3;
			break;
		}
		aDelayType = pChangedTo;
	}

//Called to find all screenshots with the same prefix and postfix	
	public void deleteScreenshotswithSameScheme(String pPath, String pPrefix, String pPostfix) {
		File dir = new File(pPath);
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (pPostfix.equals("") == true) {
					int lPrefixLength = pPrefix.length();
					int lCompleteLength = lPrefixLength + 16;
					return name.length() == lCompleteLength;
				} else {
					return name.startsWith(pPrefix) && name.endsWith(pPostfix + ".jpg");
				}
			}
		};
		String[] children = dir.list(filter);
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				String filename = children[i];
				File file = new File(aCompletePathToScreenshots + filename);
				file.delete();
			}
		}
	}

//Called to add the currently selected path to SteamApps, vscripts and screenhots to a custom file. This is used to enable the uninstall button	
	public void addFilesToHistoryCfg() {
		try {
			int lNotMatching = 0;
			File lHistoryFile = new File("__ScriptGeneratorFileHistory.cfg");
			lHistoryFile.createNewFile();
			ReadFile file = new ReadFile("__ScriptGeneratorFileHistory.cfg");
			String[] linesScriptFile;

			linesScriptFile = file.openFile();
			if (lHistoryFile.exists() == true && linesScriptFile.length != 0) {
				for (int i = 0; i < linesScriptFile.length; i += 5) {
					int lIdxStart = linesScriptFile[i].indexOf(":") + 2;
					String lCPathScreenshots = linesScriptFile[i].substring(lIdxStart, linesScriptFile[i].length());
					String lCPathScript = linesScriptFile[i + 1].substring(lIdxStart, linesScriptFile[i + 1].length());
					String lCPathCfg = linesScriptFile[i + 2].substring(lIdxStart, linesScriptFile[i + 2].length());
					String lSubfolder = linesScriptFile[i + 3].substring(lIdxStart, linesScriptFile[i + 3].length());
					if (aCompletePathToScreenshots.equals(lCPathScreenshots) == false
							|| aCompletePathToScript.equals(lCPathScript) == false
							|| aCompletePathToCfg.equals(lCPathCfg) == false
							|| aSubfolder.equals(lSubfolder) == false) {
						lNotMatching++;
					}
				}

				int lLastPathLine = linesScriptFile.length - 2;
				int lIdxEnd = linesScriptFile[lLastPathLine].indexOf(":");
				aHistoryPathCounter = Integer.parseInt(linesScriptFile[lLastPathLine].substring(5, lIdxEnd));
			}
			if (lNotMatching == (linesScriptFile.length / 5)) {
				aHistoryPathCounter++;
				writeHistoryCfg.writeToFile("Path " + aHistoryPathCounter + ": " + aCompletePathToScreenshots);
				writeHistoryCfg.writeToFile("Path " + aHistoryPathCounter + ": " + aCompletePathToScript);
				writeHistoryCfg.writeToFile("Path " + aHistoryPathCounter + ": " + aCompletePathToCfg);
				writeHistoryCfg.writeToFile("Path " + aHistoryPathCounter + ": " + aSubfolder);
				writeHistoryCfg.writeToFile("- - - - - - - - - - - - - - - -");
			}
		} catch (IOException e) {
		}
	}

//Called when the selected subfolder changes in the main gui	
	public void changedSubfolder() {
		aSubfolder = theMainGUI.getTxtSubfolder().getText();
	}

//Called when the selected prefix changes in the main gui		
	public void changedPrefix() {
		aScreenshotPreFix = theMainGUI.getTxtPrefix().getText();
	}

//Called when the selected postfix changes in the main gui	
	public void changedPostfix() {
		aScreenshotPostFix = theMainGUI.getTxtPostfix().getText();
	}

//Called when user clicks the uninstall button	
	public void clickedUninstall() {
		Object[] options = { "YES!", "NO!" };
		int lUninstallNumber = JOptionPane.showOptionDialog(theSettingsGUI,
				"Are you sure you want to delete every file that was created by this program?\n"
						+ "The .jar has to be deleted manually, because I couldn't figure out how to delete it.",
				"Confirm the deletion of generated files", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[1]);
		if (lUninstallNumber == 0) {
			try {
				ReadFile file = new ReadFile("__ScriptGeneratorFileHistory.cfg");
				String[] linesScriptFile;
				linesScriptFile = file.openFile();
				for (int i = 0; i < linesScriptFile.length; i += 5) {
					int lIdxStart = linesScriptFile[i].indexOf(":") + 2;
					String lCPathScreenshots = linesScriptFile[i].substring(lIdxStart, linesScriptFile[i].length());
					String lCPathScript = linesScriptFile[i + 1].substring(lIdxStart, linesScriptFile[i + 1].length());
					String lCPathCfg = linesScriptFile[i + 2].substring(lIdxStart, linesScriptFile[i + 2].length());
					String lSubfolder = linesScriptFile[i + 3].substring(lIdxStart, linesScriptFile[i + 3].length());

					String lDeleteScreenshotPath = lCPathScreenshots;
					String lDeleteScriptPath = lCPathScript;
					String lDeleteCfgPath = lCPathCfg;

					if (lSubfolder.equals("") == false) {
						int lSubfolderLength = lSubfolder.length() + 2;
						lDeleteScreenshotPath = lCPathScreenshots.substring(0,
								lCPathScreenshots.length() - lSubfolderLength);
						lDeleteScriptPath = lCPathScript.substring(0, lCPathScript.length() - lSubfolderLength);
						lDeleteCfgPath = lCPathCfg.substring(0, lCPathCfg.length() - lSubfolderLength);
					}

					Path lPathScreenshot = Paths.get(lDeleteScreenshotPath);
					deleteDirectoryStream(lPathScreenshot);
					Path lPathScript = Paths.get(lDeleteScriptPath);
					deleteDirectoryStream(lPathScript);
					Path lPathCfg = Paths.get(lDeleteCfgPath);
					deleteDirectoryStream(lPathCfg);

				}
			} catch (IOException e) {
			}

			File lHistoryFile = new File("__ScriptGeneratorFileHistory.cfg");
			lHistoryFile.delete();
			File lSettingsFile = new File("__ScriptGeneratorSettings.cfg");
			lSettingsFile.delete();
			theStartGUI.setVisible(false);
			try {
				theMainGUI.setVisible(false);
			} catch (Exception e) {
			}
			theSettingsGUI.setVisible(false);
			System.exit(0);

		}
	}
	
	public void changedGloabalXOffset(String pOffset) {
		aGlobaXOffset = Integer.parseInt(pOffset);
	}
	
	public void changedGloabalYOffset(String pOffset) {
		aGlobaYOffset = Integer.parseInt(pOffset);
	}
	
	public void changedGloabalZOffset(String pOffset) {
		aGlobaZOffset = Integer.parseInt(pOffset);
	}

//Called to delete every file within the __ScriptGenerator directory	
	private void deleteDirectoryStream(Path path) throws IOException {
		Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
		Files.delete(path);
	}

	// Setter and getter
	public void setInMainTable(boolean pActive) {
		aInMainTable = pActive;
	}

	public boolean getInMainTable() {
		return aInMainTable;
	}

	public void setInCopiedTable(boolean pActive) {
		aInCopiedTable = pActive;
	}

	public boolean getInCopiedTable() {
		return aInCopiedTable;
	}

	public void setInDeletedTable(boolean pActive) {
		aInDeletedTable = pActive;
	}

	public boolean getInDeletedTable() {
		return aInDeletedTable;
	}

	public void setInFullImageTable(boolean pActive) {
		aInFullImageTable = pActive;
	}

	public boolean getInFullImageTable() {
		return aInFullImageTable;
	}
}
