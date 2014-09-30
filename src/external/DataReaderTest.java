package external;

import static org.junit.Assert.*;

import org.junit.Test;

public class DataReaderTest {

	@Test
	public void testLoadData() {
		// Correct
		DataReader.loadData("test.datei");
		String[] content = DataReader.getContentAsStringArray();
		assertTrue(content.length > 0);
		
		// Incorrect
		DataReader.loadData("");
		content = DataReader.getContentAsStringArray();
		assertTrue(content.length == 0);
	}

}
