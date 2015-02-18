package net.mich.explore.file;
import java.util.List;

import net.mich.explore.Student;

import org.junit.Before;
import org.junit.Test;


public class StudentReaderTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testReadStudentActivities() {
		StudentClassChoiceReader reader = new StudentClassChoiceReader();
		List<Student> kidList = reader.read();
		
//		assertEquals(2, kidList.size());
		
	}

}
