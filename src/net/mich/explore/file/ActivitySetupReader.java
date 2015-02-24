package net.mich.explore.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.mich.explore.Activity;
import net.mich.explore.SchedulerConstants;
import net.mich.explore.scheduler.GradeActivityScheduler;

import org.apache.log4j.Logger;


public class ActivitySetupReader {
	
	private static final Logger LOGGER = Logger.getLogger(ActivitySetupReader.class);

	HashMap<String, Activity> activityMap = new HashMap<String, Activity>();
	Map< String,List<Integer> > gradeActivityScheduleMap = new HashMap< String,List<Integer> >();
	
	
	public HashMap<String, Activity> read() {
		
		try (BufferedReader br = new BufferedReader(new FileReader(SchedulerConstants.ACTIVITY_SETUP_FILENAME)))
		{
 
			String sCurrentLine;
			int i = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				i++;
				LOGGER.debug(sCurrentLine);
				List<String> tokList = Arrays.asList( sCurrentLine.split(",") );
				
				if (tokList == null || tokList.isEmpty() || tokList.get(0) == null || tokList.get(0).isEmpty()) {
					LOGGER.error("Bad line reading file - either blank line or empty name.  Line number " + i);
					continue;
				}
				
				Activity act = new Activity();
				act.setActivityName(tokList.get(0));
				
				String activityCode = tokList.get(1) + "-" + tokList.get(2);
				act.setActivityCode(activityCode);
				
				act.setActivityLeader(tokList.get(3));
				act.setAltLocation(tokList.get(4));
				act.setLocation(tokList.get(5));
				act.setCapacity( Integer.valueOf(tokList.get(6)) );

				activityMap.put(act.getActivityCode(), act);
				
				if (hasGradeActivityInfo(tokList) ) {
					setupGradeActivities(tokList.get(7), activityCode);
				}
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
 
		return activityMap;
	}
	
	boolean hasGradeActivityInfo(List<String> tokList) {
		return (tokList.size() == 8);
	}
	
	void setupGradeActivities(String grades, String activityCode) {
		if (grades == null || grades.isEmpty()) {
			return;
		}
		
		List<Integer> gradeList = parseGradeList(grades);
		gradeActivityScheduleMap.put(activityCode, gradeList);
		
	}
	
	List<Integer> parseGradeList(String grades) {
		List<Integer> gradeList = new ArrayList<Integer>();
		
		if (grades == null || grades.isEmpty()) {
			return gradeList;
		}
		
		List<String> gradeToks = Arrays.asList( grades.split("\\|") );
		for (String grade : gradeToks) {
			gradeList.add(Integer.valueOf(grade));
		}
		
		return gradeList;
		
	}

}
