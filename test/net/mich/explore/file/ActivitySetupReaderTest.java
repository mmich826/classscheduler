package net.mich.explore.file;

import static org.junit.Assert.*;

import java.util.HashMap;

import net.mich.explore.Activity;

import org.junit.Test;

public class ActivitySetupReaderTest {

	@Test
	public void test() {
		ActivitySetupReader reader = new ActivitySetupReader();
		HashMap<String, Activity> activityMap = reader.read();
		
		assertNotNull(activityMap);
		assertTrue(activityMap.size() > 10);
	}

}
