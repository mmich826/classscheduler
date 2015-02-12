package net.mich.explore;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.mich.explore.report.ReportGenerator;
import net.mich.explore.scheduler.ActivityScheduler;
import net.mich.explore.scheduler.GradeActivityScheduler;


//TODO - log4j.  2 appenders; Errors(console), file for registration issues
public class SchedulerMain {
	
	private static final Logger LOGGER = Logger.getLogger(SchedulerMain.class);
	
	Map< String,Activity > actCapacityMap = null;
    Map< String,List<StudentActivity> > scheduleMap = null;
	Map< String,List<Integer> > gradeScheduleMap2 = null; // key=grade, value=List hours offered

	public static void main(String[] args) {
		SchedulerMain th = new SchedulerMain();
		ReportGenerator rptGenerator = new ReportGenerator();
		
		List<Student> studentList = new MainTestDataGenerator().generateTestData(th);
		
		new GradeActivityScheduler().schedule(th, studentList);
			//rptGenerator.printFullSchedule(th.scheduleMap);  // Print just grade-spec schedule
		new ActivityScheduler().schedule(th, studentList);
		
		//rptGenerator.printFullSchedule(th.scheduleMap);
		//rptGenerator.printRosterByActivity(th.scheduleMap);
		//rptGenerator.printStudentSchedule(th.scheduleMap);

	}

	
	public Map<String, Activity> getActCapacityMap() {
		return actCapacityMap;
	}

	public void setActCapacityMap(Map<String, Activity> actCapacityMap) {
		this.actCapacityMap = actCapacityMap;
	}

	public Map<String, List<Integer>> getGradeScheduleMap2() {
		return gradeScheduleMap2;
	}

	public void setGradeScheduleMap2(Map<String, List<Integer>> gradeScheduleMap2) {
		this.gradeScheduleMap2 = gradeScheduleMap2;
	}


	public Map<String, List<StudentActivity>> getScheduleMap() {
		return scheduleMap;
	}


	public void setScheduleMap(Map<String, List<StudentActivity>> scheduleMap) {
		this.scheduleMap = scheduleMap;
	}
}
