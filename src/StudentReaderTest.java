import java.util.List;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class StudentReaderTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testReadStudentActivities() {
		StudentReader reader = new StudentReader();
		List<Kid> kidList = reader.readStudentActivities();
		
//		assertEquals(2, kidList.size());
		
	}

}
