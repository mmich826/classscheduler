package net.mich.explore;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.mich.explore.file.ActivitySetupReader;
import net.mich.explore.file.StudentClassScheduleReader;
import net.mich.explore.report.ReportGenerator;
import net.mich.explore.scheduler.ActivityScheduler;
import net.mich.explore.scheduler.GradeActivityScheduler;


//TODO - log4j.  2 appenders; Errors(console), file for registration issues
//TODO - logic to fill gaps with extra classes until fill.  Add Kid isSchedFull method
//TODO - reader for schedule file.  Also write sched to file
//TODO - full schedule file writer

public class SchedulerMain {
	
	private static final Logger LOGGER = Logger.getLogger(SchedulerMain.class);
	
	Map< String,Activity > actCapacityMap = null;
    Map< String,List<StudentActivity> > scheduleMap = null;
	Map< String,List<Integer> > gradeScheduleMap2 = null; // key=grade, value=List hours offered

	public static void main(String[] args) {
		List<String> argList = Arrays.asList(args);
		SchedulerMain th = new SchedulerMain();
		ReportGenerator rptGenerator = new ReportGenerator();
		MainTestDataGenerator dataGenerator = new MainTestDataGenerator();
		
		List<Student> studentList = dataGenerator.generateTestData(th);
		th.actCapacityMap = new ActivitySetupReader().read();
		
		
		// Default.  Run everything
		if (argList.isEmpty()) {
			LOGGER.info("Beginning full scheduler run.");
			new GradeActivityScheduler().schedule(th, studentList);
			//rptGenerator.printFullSchedule(th.scheduleMap);  // Print just grade-spec schedule
			new ActivityScheduler().schedule(th, studentList);
		}
		else if (argList.contains("runreports")) {
			LOGGER.info("Beginning report-only run.");
			dataGenerator.createScheduleMap(th);
			th.scheduleMap = th.readStudentSchedules();
		}
		
		rptGenerator.printFullSchedule(th.scheduleMap);
		//rptGenerator.printRosterByActivity(th.scheduleMap);
		//rptGenerator.printStudentSchedule(th.scheduleMap);

		LOGGER.info("Scheduler run complete.");
	}

	Map< String,List<StudentActivity> >  readStudentSchedules() {
		StudentClassScheduleReader reader = new StudentClassScheduleReader();
		return reader.read(this);
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
