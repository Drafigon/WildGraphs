package external;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/** 
 * Use this class to get data out of a file.
 * 
 * @author J. Wilde
 * @version 1.2
 * @since 01.04.2014
 */
public final class DataReader {
	
	/**
	 * All content of the read file
	 */
	private static ArrayList<String> content = new ArrayList<String>();
	
	// This is a abstract class, not instanced, no subclasses
	private DataReader(){};
	
	/**<pre>
	 * Load the content of the given pathname(file) in a ArrayList of String.
	 * Each line is a field in the ArrayList.
	 * </pre>
	 * @param pathname : String
	 */
	public static void loadData(String pathname) {
		try {
			// get file
			File file = new File(pathname);
			
			// clear array
			content.clear();

			// initiate the reader
			FileReader filereader = new FileReader(file);
			BufferedReader reader = new BufferedReader(filereader); //+#+

			// current line
			String row;	
			
			// save all lines into the array
			while((row = reader.readLine()) != null) {
				content.add(row);
			}	
			reader.close();
		}
		catch(FileNotFoundException e) {
			String text = "DataReader: ";
			text += "Cannot find file: " + pathname;
			System.out.println(text);
			e.printStackTrace();
		}
		catch(IOException e) {
			String text = "DataReader: ";
			text += "Cannot open file: " + pathname;
			System.out.println(text);
			e.printStackTrace();
		}
		
	}
	

	/** <pre>
	 * Searches for a specific line, ignoring the case.
	 * If the line exists, the function return true, else false.
	 *  </pre>
	 *  
	 * @param text: String
	 * @return true, false
	 */
	public static boolean lineExists(String text) {
		boolean result = false;
		
		// Search in all rows
		for(String line : content) {
			// Ignoring case
			if(line.equalsIgnoreCase(text)) {
				result = true;
			}
		}
		
		return result;		
	}
	
	
	/** <pre>
	 * Return all cells of all lines after the keyword 
	 * split by the delimiter as a 2D ArrayList of String.
	 * 
	 * First all lines are get, which are after the line with 
	 * the keyword to the next blank line. In each line all 
	 * spaces will be deleted, if deleteSpacesBefore is true, 
	 * else no spaces will be deleted. Next each line will split
	 * by the delimiter to a String array.
	 * 
	 * For a String[][] use {@link #getDataAsStringArray}.
	 * 
	 * <b>Attention!: Use a logical delimiter. Use this method
	 * only, if the file is formated correctly (see Example 3)!
	 * So all rows have the same number of fields, when its split.</b>
	 * 
	 * Note: The keyword must be the only word in
	 * the row > row.equals(keyword).
	 * 
	 * <b>Example:</b>
	 * 	"Rows"
	 * 	"First Row, 1, 90"
	 * 	"Second Row, 2, 100"
	 * 	"Third Row, 3, 110"
	 * 
	 * 1	getDataAsArrayList("Rows", "," , false) return
	 * 		(("First Row"), (" 1"), (" 90"))
	 * 		(("Second Row"), (" 2"), (" 100"))
	 * 		(("Third Row"), (" 3"), (" 110"))
	 * 2	getDataAsArrayList("Rows", "," , true) return
	 * 		(("FirstRow"), ("1"), ("90"))
	 * 		(("SecondRow"), ("2"), ("100"))
	 * 		(("ThirdRow"), ("3"), ("110"))
	 * 3	getDataAsArrayList("First Row, 1, 90", "1" , true) return
	 * 		(("SecondRow,2,"),("00"))		// wrong use
	 * 		(("ThirdRow,3,"),(""),("0"))	// wrong use
	 * 
	 * </pre>
	 * @param keyword : String
	 * @param delimiter : String
	 * @param deleteSpacesBefore : boolean
	 * @return ArrayList of ArrayList of String
	 */
	public static ArrayList<ArrayList<String>> getDataAsArrayList(String keyword, String delimiter, boolean deleteSpacesBefore) {
		// All content as a array
		ArrayList<ArrayList<String>> allFields = new ArrayList<ArrayList<String>>();
		
		// Get all lines after the keyword to the next blank line
		ArrayList<String> rows = getRowsAsArrayList(keyword);
		
		// needed
		int index;
		String row;
		
		// Delete spaces
		if(deleteSpacesBefore == true) {
			for(index = 0; index < rows.size(); index++) {
				row = rows.get(index);
				row = row.replace(" ", "");
				rows.set(index, row);
			}
		}
		
		// Split each line at each delimiter
		for(index = 0; index < rows.size(); index++) {
			// Current row
			row = rows.get(index);
			
			// Get all fields
			String[] fields = row.split(delimiter);
			
			// All fields of the current Row
			ArrayList<String> fieldsOfRow = new ArrayList<String>();
			
			// Add each field to the ArrayList
			for(String field : fields) {
				fieldsOfRow.add(field);
			}
			
			// Add all fields of the row to the whole content
			allFields.add(fieldsOfRow);			
		}
		
		return allFields;
	}
	
	/** <pre>
	 * Return all cells of all lines after the keyword 
	 * split by the delimiter as a String[][].
	 * 
	 * First all lines are get, which are after the line with 
	 * the keyword to the next blank line. In each line all 
	 * spaces will be deleted, if deleteSpacesBefore is true, 
	 * else no spaces will be deleted. Next each line will split
	 * by the delimiter to a String array.
	 * 
	 * For a ArrayList of ArrayList of String use {@link #getDataAsArrayList}.
	 * 
	 * <b>Attention!: Use a logical delimiter. Use this method
	 * only, if the file is formated correctly (see Example 3)!
	 * So all rows have the same number of fields, when its split.</b>
	 * 
	 * Note: The keyword must be the only word in
	 * the row > row.equals(keyword).
	 * 
	 * <b>Example:</b>
	 * 	"Rows"
	 * 	"First Row, 1, 90"
	 * 	"Second Row, 2, 100"
	 * 	"Third Row, 3, 110"
	 * 
	 * 1	getDataAsStringArray("Rows", "," , false) return
	 * 		(("First Row"), (" 1"), (" 90"))
	 * 		(("Second Row"), (" 2"), (" 100"))
	 * 		(("Third Row"), (" 3"), (" 110"))
	 * 2	getDataAsStringArray("Rows", "," , true) return
	 * 		(("FirstRow"), ("1"), ("90"))
	 * 		(("SecondRow"), ("2"), ("100"))
	 * 		(("ThirdRow"), ("3"), ("110"))
	 * 3	getDataAsStringArray("First Row, 1, 90", "1" , true) return
	 * 		(("SecondRow,2,"),("00"))		// wrong use
	 * 		(("ThirdRow,3,"),(""),("0"))	// wrong use
	 * 
	 * </pre>
	 * @param keyword : String
	 * @param delimiter : String
	 * @param deleteSpacesBefore : boolean
	 * @return String[][]
	 */
	public static String[][] getDataAsStringArray(String keyword, String delimiter, boolean deleteSpacesBefore) {
		ArrayList<ArrayList<String>> tmp = getDataAsArrayList(keyword, delimiter, deleteSpacesBefore);
		
		String[][] data;
		
		if(tmp.size() > 0) {
			data = new String[tmp.size()][tmp.get(0).size()];  // +#+
			
			int index = 0;
			for(ArrayList<String> row : tmp) {
				data[index] = getArray(row);		
				index++;
			}
		}
		else {
			data = new String[0][0];
		}
		
		return data;
	}
	
	/** <pre>
	 * Return all cells of all lines after the keyword 
	 * split by the delimiter as a ArrayList of ArrayList of String.
	 * 
	 * First a number of lines are get, which are after the lines with 
	 * the keyword. In each line all spaces will be deleted, if 
	 * deleteSpacesBefore is true, else no spaces will be deleted. 
	 * Next each line will split by the delimiter to a String array.
	 * 
	 * For a String[][] use {@link #getDataAsStringArray}.
	 * 
	 * <b>Attention!: Use a logical delimiter. Use this method
	 * only, if the file is formated correctly (see Example 3)!
	 * So all rows have the same number of fields, when its split.</b>
	 * 
	 * Note: The keyword must be the only word in
	 * the row > row.equals(keyword).
	 * 
	 * <b>Example:</b>
	 * 	"Rows"
	 * 	"First Row, 1, 90"
	 * 	"Second Row, 2, 100"
	 * 	"Third Row, 3, 110"
	 * 
	 * 1	getDataAsStringArray("Rows", 2, "," , false) return
	 * 		(("First Row"), (" 1"), (" 90"))
	 * 		(("Second Row"), (" 2"), (" 100"))
	 * 2	getDataAsStringArray("Rows", 3, "," , true) return
	 * 		(("FirstRow"), ("1"), ("90"))
	 * 		(("SecondRow"), ("2"), ("100"))
	 * 		(("ThirdRow"), ("3"), ("110"))
	 * 3	getDataAsStringArray("First Row, 1, 90", 1, "1" , true) return
	 * 		(("SecondRow,2,"),("00"))		// wrong use
	 * 
	 * </pre>
	 * @param keyword : String
	 * @param numberOfRows : int
	 * @param delimiter : String
	 * @param deleteSpacesBefore : boolean
	 * @return ArrayList of ArrayList of String
	 */
	public static ArrayList<ArrayList<String>> getDataAsArrayList(String keyword, int numberOfRows, String delimiter, boolean deleteSpacesBefore) {
		// all Fields
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();		
		
		// Get a number of rows after the keyword 
		ArrayList<String> rows = getRowsAsArrayList(keyword, numberOfRows);
		
		// needed
		int index;
		String row;
		
		// Delete spaces
		if(deleteSpacesBefore == true) {
			for(index = 0; index < rows.size(); index++) {
				row = rows.get(index);
				row = row.replace(" ", "");
				rows.set(index, row);
			}
		}
		
		// Split each line at each delimiter
		for(index = 0; index < rows.size(); index++) {
			// Current row
			row = rows.get(index);
			
			// Get all fields
			String[] fields = row.split(delimiter);
			
			// All fields of the current Row
			ArrayList<String> fieldsOfRow = new ArrayList<String>();
			
			// Add each field to the ArrayList
			for(String field : fields) {
				fieldsOfRow.add(field);
			}
			
			// Add all fields of the row to the whole content
			data.add(fieldsOfRow);			
		}
		
		return data;
	}
	
	/** <pre>
	 * Return all cells of all lines after the keyword 
	 * split by the delimiter as a String[][].
	 * 
	 * First a number of lines are get, which are after the lines with 
	 * the keyword. In each line all spaces will be deleted, if 
	 * deleteSpacesBefore is true, else no spaces will be deleted. 
	 * Next each line will split by the delimiter to a String array.
	 * 
	 * For a ArrayList of ArrayList of String use {@link #getDataAsArrayList}.
	 * 
	 * <b>Attention!: Use a logical delimiter. Use this method
	 * only, if the file is formated correctly (see Example 3)!
	 * So all rows have the same number of fields, when its split.</b>
	 * 
	 * Note: The keyword must be the only word in
	 * the row > row.equals(keyword).
	 * 
	 * <b>Example:</b>
	 * 	"Rows"
	 * 	"First Row, 1, 90"
	 * 	"Second Row, 2, 100"
	 * 	"Third Row, 3, 110"
	 * 
	 * 1	getDataAsStringArray("Rows", 2, "," , false) return
	 * 		(("First Row"), (" 1"), (" 90"))
	 * 		(("Second Row"), (" 2"), (" 100"))
	 * 2	getDataAsStringArray("Rows", 3, "," , true) return
	 * 		(("FirstRow"), ("1"), ("90"))
	 * 		(("SecondRow"), ("2"), ("100"))
	 * 		(("ThirdRow"), ("3"), ("110"))
	 * 3	getDataAsStringArray("First Row, 1, 90", 1, "1" , true) return
	 * 		(("SecondRow,2,"),("00"))		// wrong use
	 * </pre>
	 * @param keyword : String
	 * @param numberOfRows : int
	 * @param delimiter : String
	 * @param deleteSpacesBefore : boolean
	 * @return ArrayList of ArrayList of String
	 */
	public static String[][] getDataAsStringArray(String keyword, int numberOfRows, String delimiter, boolean deleteSpacesBefore) {
		ArrayList<ArrayList<String>> tmp = getDataAsArrayList(keyword, numberOfRows, delimiter, deleteSpacesBefore);
		
		String[][] data;
		
		if(tmp.size() > 0) {
			data = new String[tmp.size()][tmp.get(0).size()];  // +#+
			
			int index = 0;
			for(ArrayList<String> row : tmp) {
				data[index] = getArray(row);		
				index++;
			}
		}
		else {
			data = new String[0][0];
		}
		
		return data;
	}
	
	
	/**
	 * <pre>
	 * Searches for the keyword in a row and returns 
	 * a number of rows after this row.
	 * 
	 * Note: The keyword must be the only word in
	 * the row > row.equals(keyword).
	 * 
	 * <b>Example:</b>
	 * 	"Rows" 
	 * 	"Row 1" 
	 * 	"Row 2" 
	 * 	"" (~Nothing~)
	 * 	"Row 3"
	 * 
	 * 	getDataAsArrayList("Rows", 2) returns 
	 * 		("Row 1")
	 * 		("Row 2")
	 * 	getDataAsArrayList("Rows", 4) returns 
	 * 		("Row 1")
	 * 		("Row 2")
	 * 		("")
	 * 		("Row 3")
	 * </pre>
	 * @param keyword : String
	 * @param numberOfRows : int
	 * @return ArrayList of String
	 */
	public static ArrayList<String> getRowsAsArrayList(String keyword, int numberOfRows) {
		ArrayList<String> rows = new ArrayList<String>();
		
		// current line
		String row;
		// current index
		int index = 0;
		// if the kayword was found
		boolean begin = false;
		// the number of rows, wich add
		int count = 0;
		
		// if the number of rows are reached, stop
		while(index < content.size() && count < numberOfRows ) {
			// current row
			row = content.get(index);
			
			// Add a line
			if(begin == true) {
				rows.add(row);
				// increase number of rows
				count++;
			}
			
			// Keyword found
			// Only the first found is relevant
			if(begin == false && row.equals(keyword)) {
				begin = true;
			}
			
			// next row
			index++;
		}
		
		// return all rows
		return rows;
	}
	
	/**
	 * <pre>
	 * Searches for the keyword in a row and returns 
	 * a number of rows after this row.
	 * 
	 * Note: The keyword must be the only word in
	 * the row > row.equals(keyword).
	 * 
	 * <b>Example:</b>
	 * 	"Rows" 
	 * 	"Row 1" 
	 * 	"Row 2" 
	 * 	"" (~Nothing~)
	 * 	"Row 3"
	 * 
	 * 	getDataAsArrayList("Rows", 2) returns 
	 * 		("Row 1")
	 * 		("Row 2")
	 * 	getDataAsArrayList("Rows", 4) returns 
	 * 		("Row 1")
	 * 		("Row 2")
	 * 		("")
	 * 		("Row 3")
	 * </pre>
	 * @param keyword : String
	 * @return String[]
	 */
	public static String[] getRowsAsStringArray(String keyword, int numberOfRows) {
		ArrayList<String> tmp = getRowsAsArrayList(keyword, numberOfRows);
		return getArray(tmp);	
	}
	
	/**
	 * <pre>
	 * Returns all lines after the line with the keyword to the next blank line.
	 * 
	 * Note: The keyword must be the only word in
	 * the row > row.equals(keyword).
	 * 
	 * <b>Example:</b>
	 * 	"Rows" 
	 * 	"Row 1" 
	 * 	"Row 2" 
	 * 	"" (~Nothing~)
	 * 	"Row 3"
	 * 
	 * 	getDataAsArrayList("Rows") returns 
	 * 		("Row 1")
	 * 		("Row 2")
	 * 	getDataAsArrayList("Row 1") returns 
	 * 		("Row 2")
	 * </pre>
	 * @param keyword : String
	 * @return ArrayList of String
	 */
	public static ArrayList<String> getRowsAsArrayList(String keyword) {
		// All lines after the line with the keyword to the next blank line
		ArrayList<String> rows = new ArrayList<String>();
		
		// current line
		String row;
		// current index
		int index = 0;
		// if the kayword was found
		boolean begin = false;
		// if the first blank line was found
		boolean end = false;
		
		while(index < content.size() && end == false) {
			row = content.get(index);
			
			// Add a line, if it is not blank
			if(begin == true) {
				// Endcondition
				if(row.equals("") || row.equals(System.lineSeparator())) {
					// end the loop
					end = true;
				}
				// Add all rows
				else {
					rows.add(row);
				}
			}
			
			// Keyword found
			// Only the first found is relevant
			if(begin == false && row.equalsIgnoreCase(keyword)) {
				begin = true;
			}
			
			// next row
			index++;
		}
		
		// return all rows
		return rows;
	}
	
	
	/**
	 * <pre>
	 * Returns all lines after the line with the keyword to the next blank line.
	 * 
	 * Note: The keyword must be the only word in
	 * the row > row.equals(keyword).
	 * 
	 * <b>Example:</b>
	 * 	"Rows" 
	 * 	"Row 1" 
	 * 	"Row 2" 
	 * 	"" (~Nothing~)
	 * 	"Row 3"
	 * 
	 * 	getDataAsStringArray("Rows") returns 
	 * 		("Row 1")
	 * 		("Row 2")
	 * 	getDataAsStringArray("Row 1") returns 
	 * 		("Row 2")
	 * </pre>
	 * @param keyword : String
	 * @return String[]
	 */
	public static String[] getRowsAsStringArray(String keyword) {
		ArrayList<String> tmp = getRowsAsArrayList(keyword);
		return getArray(tmp);	
	}	

	
	/**
	 * Return the whole content as a Arraylist of String.
	 * 
	 * @return ArrayList of String
	 */
	public static ArrayList<String> getContentAsArrayList() {
		return content;
	}
	
	/**
	 * Return the whole content as a String[].
	 * 
	 * @return String[]
	 */
	public static String[] getContentAsStringArray() {
		return getArray(content);
	}
	
	/**
	 * Creates a String[] from a ArrayList of String.
	 * 
	 * @param arraylist
	 * @return String[]
	 */
	private static String[] getArray(ArrayList<String> arraylist) {
		// Create Array
		String[] array = new String[arraylist.size()];
		
		int index = 0;
		// Copy
		for(String line : arraylist) {
			array[index] = line;
			index++;
		}
		
		return array;
	}	
}
