package net.mich.explore.file;
import java.util.List;

import static junit.framework.Assert.*;
import net.mich.explore.Student;
import net.mich.explore.file.StudentScheduleReader;

import org.junit.Before;
import org.junit.Test;


public class StudentReaderTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testReadStudentActivities() {
		StudentScheduleReader reader = new StudentScheduleReader();
		List<Student> kidList = reader.readStudentActivities();
		
//		assertEquals(2, kidList.size());
		
	}

}
