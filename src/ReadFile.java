package ScreenshotScript;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class ReadFile {

	private String path;
	
	public ReadFile(String pPath)
	{
		path = pPath;
	}
	
	public String[] openFile() throws IOException
	{
		FileReader reader = new FileReader(path);
		BufferedReader txtReader = new BufferedReader(reader);
		
		int maxLines = readLines();
		String[] txtArray = new String[maxLines];
		
		for (int i=0; i < maxLines; i++) 
		{
			txtArray[i] = txtReader.readLine();
		}
		
		txtReader.close( );
		return txtArray;
	}
	
	public int readLines() throws IOException
	{
		FileReader fileToRead = new FileReader(path);
		BufferedReader bufferReader = new BufferedReader(fileToRead);
//Suppres Warnings to reduce Warnings	
		@SuppressWarnings("unused")
		String lLine;
		int maxLines = 0;
		while ((lLine = bufferReader.readLine()) != null) 
		{
			maxLines++;
		}
		bufferReader.close();
		return maxLines;
	}
}
