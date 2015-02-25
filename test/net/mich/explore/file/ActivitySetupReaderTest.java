package net.mich.explore.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ActivitySetupReaderTest {

	//@Test
	public void test() {
		ActivitySetupReader reader = new ActivitySetupReader();
		Map<String, Map> activityMap = reader.read();
		
		assertNotNull(activityMap);
		assertTrue(activityMap.size() > 10);
	}

	@Test
	public void gradeParserHappy() {
		ActivitySetupReader reader = new ActivitySetupReader();
		
		String grades = "1|2|3";
		List<Integer> gradeList = reader.parseGradeList(grades);
		assertEquals(3, gradeList.size());
		assertEquals(1, gradeList.get(0).intValue());
		assertEquals(2, gradeList.get(1).intValue());
		assertEquals(3, gradeList.get(2).intValue());
		
		grades = "3";
		gradeList = reader.parseGradeList(grades);
		assertEquals(1, gradeList.size());
		assertEquals(3, gradeList.get(0).intValue());
	}

	@Test
	public void gradeParserErrors() {
		ActivitySetupReader reader = new ActivitySetupReader();
		
		String grades = "";
		List<Integer> gradeList = reader.parseGradeList(grades);
		assertNotNull(gradeList);
		assertEquals(0, gradeList.size());
		
		grades = null;
		gradeList = reader.parseGradeList(grades);
		assertNotNull(gradeList);
		assertEquals(0, gradeList.size());
	}

	@Test
	public void setupGradeActivitiesHappy() {
		ActivitySetupReader reader = new ActivitySetupReader();
		
		String grades = "1|2|3";
		reader.setupGradeActivities(grades, "bb-1");
		assertEquals(1, reader.gradeActivityScheduleMap.size());
		assertNotNull(reader.gradeActivityScheduleMap.get("bb-1"));
		assertEquals(3, reader.gradeActivityScheduleMap.get("bb-1").size());
		assertEquals(1, reader.gradeActivityScheduleMap.get("bb-1").get(0).intValue());
		
	}
}
