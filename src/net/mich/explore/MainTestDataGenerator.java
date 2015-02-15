package net.mich.explore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.mich.explore.file.StudentScheduleReader;

public class MainTestDataGenerator {


	public List<Student> generateTestData(SchedulerMain mn) {
/*		gradeScheduleMap = new HashMap< String,Integer >();
		gradeScheduleMap.put("bb-1", new Integer(4) );
		gradeScheduleMap.put("bb-2", new Integer(3) );
		gradeScheduleMap.put("bb-3", new Integer(2) );
		gradeScheduleMap.put("bb-4", new Integer(1) );
		gradeScheduleMap.put("fb-1", new Integer(1) );
		gradeScheduleMap.put("fb-2", new Integer(2) );
		gradeScheduleMap.put("fb-3", new Integer(3) );
		gradeScheduleMap.put("fb-4", new Integer(4) );
*/
		mn.gradeScheduleMap2 = new HashMap< String,List<Integer> >();
		List<Integer> gradeGroup = new ArrayList<Integer>();
		gradeGroup.add(new Integer(4)); gradeGroup.add(new Integer(3));
			mn.gradeScheduleMap2.put("bb-1", gradeGroup);
		gradeGroup = new ArrayList<Integer>();
		gradeGroup.add(new Integer(4)); gradeGroup.add(new Integer(3));
			mn.gradeScheduleMap2.put("bb-2", gradeGroup );
		gradeGroup = new ArrayList<Integer>();
		gradeGroup.add(new Integer(2)); gradeGroup.add(new Integer(1));
			mn.gradeScheduleMap2.put("bb-3", gradeGroup );
		gradeGroup = new ArrayList<Integer>();
		gradeGroup.add(new Integer(2)); gradeGroup.add(new Integer(1));
			mn.gradeScheduleMap2.put("bb-4", gradeGroup );
		
		gradeGroup = new ArrayList<Integer>();
		gradeGroup.add(new Integer(2)); gradeGroup.add(new Integer(1));
			mn.gradeScheduleMap2.put("fb-1", gradeGroup );
		gradeGroup = new ArrayList<Integer>();
		gradeGroup.add(new Integer(2)); gradeGroup.add(new Integer(1));
			mn.gradeScheduleMap2.put("fb-2", gradeGroup );
		gradeGroup = new ArrayList<Integer>();
		gradeGroup.add(new Integer(4)); gradeGroup.add(new Integer(3));
			mn.gradeScheduleMap2.put("fb-3", gradeGroup );
		gradeGroup = new ArrayList<Integer>();
		gradeGroup.add(new Integer(4)); gradeGroup.add(new Integer(3));
			mn.gradeScheduleMap2.put("fb-4", gradeGroup );

		mn.scheduleMap = new HashMap< String,List<StudentActivity> >();
		mn.scheduleMap.put("bb-1", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("bb-2", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("bb-3", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("bb-4", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("hockey-1", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("hockey-2", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("hockey-3", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("hockey-4", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("rock-1", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("rock-2", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("rock-3", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("rock-4", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("fb-1", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("fb-2", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("fb-3", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("fb-4", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("soccer-1", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("soccer-2", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("soccer-3", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("soccer-4", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("pizza-1", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("pizza-2", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("pizza-3", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("pizza-4", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("flag-1", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("flag-2", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("flag-3", new ArrayList<StudentActivity>() );
		mn.scheduleMap.put("flag-4", new ArrayList<StudentActivity>() );
		
		int CAPACITY =8;
		mn.actCapacityMap = new HashMap<String, Activity>();
		mn.actCapacityMap.put("bb-1", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("bb-2", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("bb-3", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("bb-4", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("hockey-1", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("hockey-2", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("hockey-3", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("hockey-4", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("fb-1", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("fb-2", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("fb-3", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("fb-4", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("rock-1", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("rock-2", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("rock-3", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("rock-4", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("soccer-1", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("soccer-2", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("soccer-3", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("soccer-4", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("pizza-1", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("pizza-2", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("pizza-3", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("pizza-4", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("flag-1", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("flag-2", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("flag-3", new Activity(null, CAPACITY));
		mn.actCapacityMap.put("flag-4", new Activity(null, CAPACITY));
		
		
		StudentScheduleReader reader = new StudentScheduleReader();
		List<Student> StudentList = reader.readStudentActivities();
		
		return StudentList;
	}
	
	List<Student> populateStudentList() {
		List<Student> StudentList = new ArrayList<Student>();
		Student Student = new Student();
		Student.setName("Mike M");
		Student.setGrade("1");
		Student.getAct().add("bb"); Student.getAct().add("hockey"); Student.getAct().add("rock"); Student.getAct().add("fb");
		StudentList.add(Student);
		
		Student = new Student();
		Student.setName("Willie W");
		Student.setGrade("1");
		// TEST accid reg for same classes
		Student.getAct().add("bb"); Student.getAct().add("bb"); Student.getAct().add("bb"); Student.getAct().add("fb");
		StudentList.add(Student);
		
		Student = new Student();
		Student.setName("Fetti M");
		Student.setGrade("1");
		Student.getAct().add("bb"); Student.getAct().add("hockey"); Student.getAct().add("rock"); Student.getAct().add("fb");
		StudentList.add(Student);
		
		Student = new Student();
		Student.setName("Dave M");
		Student.setGrade("1");
		Student.getAct().add("flag"); Student.getAct().add("hockey"); Student.getAct().add("rock"); Student.getAct().add("fb");
		StudentList.add(Student);
		
		Student = new Student();
		Student.setName("Chris C");
		Student.setGrade("2");
		Student.getAct().add("bb"); Student.getAct().add("fb"); Student.getAct().add("rock"); Student.getAct().add("soccer");
		StudentList.add(Student);
		
		Student = new Student();
		Student.setName("Bill B");
		Student.setGrade("2");
		Student.getAct().add("hockey"); Student.getAct().add("soccer"); Student.getAct().add("rock"); Student.getAct().add("fb");
		StudentList.add(Student);

		Student = new Student();
		Student.setName("Julie B");
		Student.setGrade("3");
		Student.getAct().add("hockey"); Student.getAct().add("soccer"); Student.getAct().add("rock"); Student.getAct().add("bb");
		StudentList.add(Student);

		for (int i=0; i<10; i++) {
			Student = new Student();
			Student.setName("Student " + i);
			Student.setGrade("3");
			Student.getAct().add("bb"); Student.getAct().add("hockey"); Student.getAct().add("rock"); Student.getAct().add("fb");
			StudentList.add(Student);	
		}

		return StudentList;
	}

}