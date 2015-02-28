package net.mich.explore.scheduler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import net.mich.explore.MainTestDataGenerator;
import net.mich.explore.SchedulerMain;
import net.mich.explore.Student;

import org.junit.Before;
import org.junit.Test;

public class ActivitySchedulerTest {

	ActivityScheduler scheduler = null;
	
	MainTestDataGenerator dataGen = new MainTestDataGenerator();
	SchedulerMain mainSched = new SchedulerMain();
	
	@Before
	public void setUp() throws Exception {
			
	}

	@Test
	public void testScheduleHapppy() {
		scheduler = new ActivityScheduler();
		
		List<Student> studentList = dataGen.generateTestData(mainSched);
		scheduler.schedule(mainSched, studentList);

		assertNotNull( mainSched.getScheduleMap().get("hockey-1") );
		assertEquals(4, mainSched.getScheduleMap().get("hockey-1").size());
		
		assertNotNull( mainSched.getScheduleMap().get("flag-1") );
		assertEquals(1, mainSched.getScheduleMap().get("flag-1").size());
		assertEquals("Dave M",  mainSched.getScheduleMap().get("flag-1").get(0).getName() );
		
		assertNotNull( mainSched.getScheduleMap().get("flag-2") );
		
		assertNotNull( mainSched.getScheduleMap().get("hockey-1") );
		assertEquals(4, mainSched.getScheduleMap().get("hockey-1").size());
		
		assertNotNull( mainSched.getScheduleMap().get("soccer-1") );
		assertEquals(4, mainSched.getScheduleMap().get("soccer-1").size());
	}
	

}
