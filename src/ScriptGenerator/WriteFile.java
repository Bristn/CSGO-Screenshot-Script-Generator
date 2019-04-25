package ScreenshotScript;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class WriteFile {

	private String path;
	private boolean appendToFile = false;
	
	public WriteFile(String pPath)
	{
		path = pPath;
	}
	
	public WriteFile(String pPath, boolean pAppend)
	{
		path = pPath;
		appendToFile = pAppend;
	}
	
	public void writeToFile( String textLine ) throws IOException
	{
		
		FileWriter write = new FileWriter( path , appendToFile);
		PrintWriter printLine = new PrintWriter( write );
		printLine.printf( "%s" + "%n" , textLine);
		printLine.close();
	}
	
}
