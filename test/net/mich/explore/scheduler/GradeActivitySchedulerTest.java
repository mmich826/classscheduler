package net.mich.explore.scheduler;

import static org.junit.Assert.*;

import java.util.List;

import net.mich.explore.MainTestDataGenerator;
import net.mich.explore.SchedulerMain;
import net.mich.explore.Student;

import org.junit.Before;
import org.junit.Test;

public class GradeActivitySchedulerTest {

	GradeActivityScheduler scheduler = null;
	
	MainTestDataGenerator dataGen = new MainTestDataGenerator();
	SchedulerMain mainSched = new SchedulerMain();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSchedule() {
		scheduler = new GradeActivityScheduler();
		
		List<Student> studentList = dataGen.generateTestData(mainSched);
		scheduler.schedule(mainSched, studentList);

		assertEquals(4, mainSched.getScheduleMap().get("bb-1").size());
		assertEquals(4, mainSched.getScheduleMap().get("bb-2").size());
		assertEquals(0, mainSched.getScheduleMap().get("bb-3").size());
		assertEquals(4, mainSched.getScheduleMap().get("bb-4").size());
		
		assertEquals(0, mainSched.getScheduleMap().get("fb-1").size());
		assertEquals(4, mainSched.getScheduleMap().get("fb-2").size());
		assertEquals(4, mainSched.getScheduleMap().get("fb-3").size());
		assertEquals(4, mainSched.getScheduleMap().get("fb-4").size());
		
		assertEquals(0, mainSched.getScheduleMap().get("hockey-1").size());
		assertEquals(0, mainSched.getScheduleMap().get("flag-1").size());
		assertEquals(0, mainSched.getScheduleMap().get("hockey-1").size());
		assertEquals(0, mainSched.getScheduleMap().get("soccer-1").size());
	}

}
