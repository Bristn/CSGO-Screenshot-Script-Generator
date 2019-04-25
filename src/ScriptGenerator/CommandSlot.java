package ScreenshotScript;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class CommandSlot {

	private int CONST_ImageWidth;
	private int CONST_ImageHeight;
	private int CONST_FullImageHeight;
	private int CONST_FullImageWidth;
	private int CONST_DefaultScreenWidth = 1920;
	private int CONST_DefaultScreenHeight = 1080;
	private String aCommand = "";
	private int aPosition;
	private ImageIcon aImage = new ImageIcon(CommandSlot.class.getResource("/BlankImage.jpg"));
	private Image aNormalImage;
	private Image aScaledImage;
	private Image aFullscreenImage;
	private double aXPosition;
	private double aYPosition;
	private double aZPosition;
	private int aIndividualXOffset = 0;
	private int aIndividualYOffset = 0;
	private int aIndividualZOffset = 0;
	private boolean aAllowNewImage = false;
	private double aHeightFactor;
	private double aWidthFactor;
	private Image aSelectionFrame;
	private boolean aNoImage = false;

	public CommandSlot(int pPosition) {
		aPosition = pPosition;

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double lScreenWidth = (double) screenSize.getWidth();
		double lScreenHeight = (double) screenSize.getHeight();

		aWidthFactor = lScreenWidth / CONST_DefaultScreenWidth;
		aHeightFactor = lScreenHeight / CONST_DefaultScreenHeight;

		CONST_FullImageWidth = (int) lScreenWidth;
		CONST_FullImageHeight = (int) lScreenHeight;

		CONST_ImageWidth = (int) (230 * aWidthFactor);
		CONST_ImageHeight = (int) (130 * aHeightFactor);
	}

	public String getCommand() {
		return aCommand;
	}

	public void setCommand(String pCommand) {
		aCommand = pCommand;
		int lIndexOfSemi = aCommand.indexOf(";");
		String lOnlySetPosNumbers = aCommand.substring(7, lIndexOfSemi);
		int lIndexOfXSpace = lOnlySetPosNumbers.indexOf(" ");
		int lIndexOfYSpace = lOnlySetPosNumbers.indexOf(" ", lIndexOfXSpace + 1);
		aXPosition = Double.parseDouble(lOnlySetPosNumbers.substring(0, lIndexOfXSpace));
		aYPosition = Double.parseDouble(lOnlySetPosNumbers.substring(lIndexOfXSpace, lIndexOfYSpace));
		aZPosition = Double.parseDouble(lOnlySetPosNumbers.substring(lIndexOfYSpace, lOnlySetPosNumbers.length()));
	}

	public double getXPosition() {
		return aXPosition;
	}

	public double getYPosition() {
		return aYPosition;
	}

	public double getZPosition() {
		return aZPosition;
	}

	public int getPosition() {
		return aPosition;
	}

	public void setPosition(int pPosition) {
		aPosition = pPosition;
	}

	public Image getFullscreenImage() {
		if (aFullscreenImage == null || aAllowNewImage == true) {
			Image lTempImage = aImage.getImage();
			aFullscreenImage = lTempImage.getScaledInstance(CONST_FullImageWidth, CONST_FullImageHeight,
					java.awt.Image.SCALE_SMOOTH);
		}
		return aFullscreenImage;
	}

	public Image getScaledImage() {
		if (aScaledImage == null || aAllowNewImage == true) {
			Image lTempImage = aImage.getImage();

			aScaledImage = lTempImage.getScaledInstance(CONST_ImageWidth, CONST_ImageHeight,
					java.awt.Image.SCALE_SMOOTH);

			if (getPosition() <= -5 || aNoImage == true) {
				BufferedImage lSclaedImage = new BufferedImage(CONST_ImageWidth, CONST_ImageHeight,
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D l2DGraphic = lSclaedImage.createGraphics();
				l2DGraphic.drawImage(lTempImage, 0, 0, CONST_ImageWidth, CONST_ImageHeight, null);
				l2DGraphic.dispose();
				return lSclaedImage;
			}
		}
		return aScaledImage;
	}

	public Image getFullImage() {
		aNormalImage = aImage.getImage();
		return aNormalImage;
	}

	public Image getSelectionImage(String pColor) {
		setSelectionFrame(pColor);
		Image lForegroundImage = getSelectionFrame();
		Image lBackgroundImage = getScaledImage();

		BufferedImage lFinalImage = new BufferedImage(CONST_ImageWidth, CONST_ImageHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D l2DGraphic = lFinalImage.createGraphics();
		l2DGraphic.drawImage(lBackgroundImage, 0, 0, CONST_ImageWidth, CONST_ImageHeight, null);
		l2DGraphic.drawImage(lForegroundImage, 0, 0, CONST_ImageWidth, CONST_ImageHeight, null);
		l2DGraphic.dispose();
		return lFinalImage;
	}

	private void setSelectionFrame(String pColor) {
		ImageIcon lTempImage = new ImageIcon(
				CommandSlot.class.getResource("/SelectionColors/selectedImage_" + pColor + ".png"));
		aSelectionFrame = lTempImage.getImage();
	}

	private Image getSelectionFrame() {
		return aSelectionFrame;
	}

	public void setImage(String pImagePath) {
		aImage = new ImageIcon(pImagePath);
	}

	public void setImage(String pImagePath, boolean pActive) {
		aImage = new ImageIcon(CommandSlot.class.getResource(pImagePath));
		aAllowNewImage = pActive;
		if (pImagePath.equals("/NoImageFound.jpg")) {
			aNoImage = true;
		}
	}

	public int getIndividualXOffset() {
		return aIndividualXOffset;
	}

	public void setIndividualXOffset(int pIndividualXOffset) {
		aIndividualXOffset = pIndividualXOffset;
	}

	public int getIndividualYOffset() {
		return aIndividualYOffset;
	}

	public void setIndividualYOffset(int pIndividualYOffset) {
		aIndividualYOffset = pIndividualYOffset;
	}

	public int getIndividualZOffset() {
		return aIndividualZOffset;
	}

	public void setIndividualZOffset(int pIndividualZOffset) {
		aIndividualZOffset = pIndividualZOffset;
	}

}
