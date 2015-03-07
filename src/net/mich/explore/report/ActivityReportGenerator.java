package net.mich.explore.report;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.mich.explore.SchedulerMain;
import net.mich.explore.StudentActivity;

import org.apache.log4j.Logger;

public class ActivityReportGenerator implements ReportGenerator {
	
	private static final Logger LOGGER = Logger.getLogger(ActivityReportGenerator.class);

	public void generate(SchedulerMain mainSched) {
		
		Map< String, List<StudentActivity> > scheduleMap = mainSched.getScheduleMap();
		
		Object[] keys = scheduleMap.keySet().toArray();
		Arrays.sort(keys);
		 
 		 for (Object key : keys) {			
			List<StudentActivity> Students = scheduleMap.get((String)key) ;
			// Set html header
			for (StudentActivity Student : Students) {
				System.out.println(key + "|" + Student.getName());		
			}
			// Set html footer
		}
	}
	
}
