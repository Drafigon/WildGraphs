package external;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Use this class to write data into a file.
 * 
 * @author J. Wilde
 * @version 1.0
 * @since 01.04.2014
 *
 */
public final class DataWriter {
	private static BufferedWriter writer;
	
	// This is a abstract final class, not instanced, no subclasses
	private DataWriter(){};
	
	/**
	 * <pre>
	 * Open a file for writing.
	 * If the file does not exist, it will be created.
	 * 
	 * <b>Attention!: The function will clear the file 
	 * before writing into it.</b>
	 * </pre>
	 * 
	 * @param pathname : String
	 */
	public static void openFile(String pathname) {
		
		try {			
			File file = new File(pathname);
			FileWriter fileWriter = new FileWriter(file);
			writer = new BufferedWriter(fileWriter);
		} catch (IOException e) {
			String text = "DataWriter: ";
			text += "Cannot open file: " + pathname;
			System.out.println(text);
			e.printStackTrace();
		}
	}
	
	/**
	 * Write the text into the file.
	 * Add a wrap after doing this.
	 * 
	 * @param text : String
	 * @see #write(String)
	 * @see #addLineWrap()
	 */
	public static void writeln(String text) {
		write(text);
		addLineWrap();			
	}
		
	/**
	 * Write the text into the file.
	 * 
	 * @param text : String
	 * @see #addLineWrap()
	 * @see #writeln(String)
	 */
	public static void write(String text) {	
		try {
			writer.write(text);
			writer.flush();
		} catch (IOException e) {
			showException(e);
		} catch (NullPointerException e) {
			showException(e);
		}
	}
	
	/**
	 * Add a wrap to the file.
	 * 
	 * @see #write(String)
	 * @see #writeln(String)
	 */
	public static void addLineWrap() {
		try {
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			showException(e);
		} catch (NullPointerException e) {
			showException(e);
		}
	}
	
	/**
	 * Closes the writer.
	 */
	public static void close() {
		try {
			writer.close();
		} catch (IOException e) {
			showException(e);
		} catch (NullPointerException e) {
			showException(e);
		}
	}	
	
	private static void showException(Exception e) {
		System.out.println("DataWriter: Cannot write into file.");
		System.out.println("Have you loaded a file before?");
		e.printStackTrace();
	}
}
