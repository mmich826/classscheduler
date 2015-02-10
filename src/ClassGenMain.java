import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

//TODO - Create File Writers.  First, impl csv writer
//TODO - log4j.  2 appenders; Errors(console), file for registration issues
public class ClassGenMain {
	
	Map< String,Activity > actCapacityMap = null;
	Map< String,List<KidAct> > scheduleMap = null;
	Map< String,List<Integer> > gradeScheduleMap2 = null; // key=grade, value=List hours offered

	public static void main(String[] args) {
		
		ClassGenMain th = new ClassGenMain();

		List<Kid> kidList = th.genData();

		List<KidAct> clazList = null;
		KidAct ka = null;
		try {

			// Schedule grade specific activities first
			for (int i = 0; i < 4; i++) {  // CLASS PRIORITY
				for(Kid k : kidList) {   // STUDENTS
					ka = new KidAct();
					ka.name = k.name; 
					try {
						ka.act = k.act.get(i);
					} catch (IndexOutOfBoundsException e) {
						StringBuilder sb = new StringBuilder();
						sb.append(ka.act).append("|").append(ka.name).append("|")
							.append("No activity choice for ").append(i+1);
						System.out.println(sb.toString());
						continue;
					}
					ka.grade = k.grade;
					ka.teacher = k.teacher;
	
					List<Integer> gradeActHours = th.gradeScheduleMap2.get(ka.act + "-" + ka.grade);
					boolean isRegistrationSuccess = false;
					boolean isGradeSpecActivity = gradeActHours != null && !gradeActHours.isEmpty();
					if (isGradeSpecActivity) {						
						for (int j=0; j<gradeActHours.size(); j++) {
							int hour = gradeActHours.get(j);
							if (k.actSchedList.get(hour-1)) continue;  // true=Student booked this hour
							
							String actName = ka.act + "-" + hour;
							Activity act = th.actCapacityMap.get(actName);
							if (act.isFull()) continue;

							clazList = th.scheduleMap.get(actName);							
							ka.hour = Integer.toString(hour);
							clazList.add(ka);
							act.enrollment++;
							k.actSchedList.set(hour-1, true);
							isRegistrationSuccess = true;
							break;
						}
					}
					
					if ( isGradeSpecActivity && !isRegistrationSuccess ){
						StringBuilder sb = new StringBuilder();
						sb.append(ka.act).append("|").append(ka.name).append("|")
							.append("Failed to register student for grade-spec activity choice ").append(i+1);
						System.out.println(sb.toString());
					}
	
				}
			}
			//th.printFullSchedule(th.scheduleMap);		
		} catch (Exception e) {
			System.out.println("Error scheduling:  " + ka);
			e.printStackTrace();
		}
	
			
			
			// Now schedule remaining activities
			ka = null;
			try {
			for (int i = 0; i < 4; i++) {  // CLASS PRIORITY
				for(Kid k : kidList) {   // KIDS
					
					ka = new KidAct();
					ka.name = k.name; 
					try {
						ka.act = k.act.get(i);
					} catch (IndexOutOfBoundsException e) {
						// Nothing to log.  Logged in prev loop
						continue;
					}
					ka.grade = k.grade;
					ka.teacher = k.teacher;
                    ka.hour = Integer.toString(i+1);
					
					List<Integer> gradeActHours = th.gradeScheduleMap2.get(ka.act + "-" + k.grade);
					boolean isGradeSpecActivity = gradeActHours != null && !gradeActHours.isEmpty();
					if (isGradeSpecActivity) continue;
					
					boolean isRegistrationSuccess = false;
					for(int j=0; j<4; j++) {   // ACT HOURS
					
						boolean isBooked = k.actSchedList.get(j);
						if (isBooked) continue;
						
                        String actClassName = ka.act + "-" + (j+1);
                        ka.hour = String.valueOf(j+1);
						Activity act = th.actCapacityMap.get(actClassName);
						if (act == null) {
							StringBuilder sb = new StringBuilder();
							sb.append(ka.act).append("|").append(ka.name).append("|")
								.append("Activity not found:  ").append(actClassName);
							System.out.println(sb.toString());
							continue;
						}
						
						if (act.isFull()) continue;
						
						clazList = th.scheduleMap.get(actClassName);	
						clazList.add(ka);
						act.enrollment++;
						k.actSchedList.set(j, true);
						isRegistrationSuccess = true;
						break;
					}
					
					if (!isRegistrationSuccess) {
						StringBuilder sb = new StringBuilder();
						sb.append(ka.act).append("|").append(ka.name).append("|")
							.append("Failed to register student for grade-spec activity choice ").append(i+1);
						System.out.println(sb.toString());
					}
				}
			}
			
			//System.out.println("-----------> End Loop");
		} catch (Exception e) {
			System.out.println("Error scheduling:  " + ka);
			e.printStackTrace();
		}
		
		th.printFullSchedule(th.scheduleMap);
		//th.printRosterByActivity(th.scheduleMap);
		//th.printStudentSchedule(th.scheduleMap);
		
	}
	
	void printFullSchedule(Map< String,List<KidAct> > scheduleMap) {
		List<KidAct> kidActList = new ArrayList<KidAct>();
		
		Iterator<String> iter = scheduleMap.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			List<KidAct> kids = scheduleMap.get(key) ;
			for (KidAct kid : kids) {
				//System.out.println(key + "|" + kid.name);
				kidActList.add(kid);
			}
		}
		
		KidAct[] studentActivities = new KidAct[kidActList.size()];
		kidActList.toArray(studentActivities);
		Arrays.sort(studentActivities, new KidActByStudentComparator() );
		 
		 for (KidAct studentAct : studentActivities) {			
			System.out.println(studentAct.getAct() + "-" + studentAct.getHour() + "|" + studentAct.getName() + "|" + studentAct.getGrade());		
		}
	}
	
	void printRosterByActivity(Map< String,List<KidAct> > scheduleMap) {
		 Object[] keys = scheduleMap.keySet().toArray();
		 Arrays.sort(keys);
		 
 		 for (Object key : keys) {			
			List<KidAct> kids = scheduleMap.get((String)key) ;
			// Set html header
			for (KidAct kid : kids) {
				System.out.println(key + "|" + kid.name);		
			}
			// Set html footer
		}
	}
	
	void printStudentSchedule(Map< String,List<KidAct> > scheduleMap) {
		List<KidAct> kidActList = new ArrayList<KidAct>();
		
		Iterator<String> iter = scheduleMap.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			List<KidAct> kids = scheduleMap.get(key) ;
			for (KidAct kid : kids) {
				//System.out.println(key + "|" + kid.name);
				kidActList.add(kid);
			}
		}
		
		KidAct[] studentActivities = new KidAct[kidActList.size()];
		kidActList.toArray(studentActivities);
		Arrays.sort(studentActivities, new KidActByStudentComparator() );
		 
		String name = "_BEGIN";
		for (KidAct studentAct : studentActivities) {	
			 if (name == null || !name.equals(studentAct.getName()) ) {
				 name = studentAct.getName();
				 // If not first, set footer hnml
				 // Set header html
			 }
			 System.out.println(studentAct.getAct() + "-" + studentAct.getHour() + "|" + name + " " + studentAct.getName() + "|" + studentAct.getGrade());		
		}
		// Pg end.  Set footer html
	}
	
	
	public List<Kid> genData() {
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
		gradeScheduleMap2 = new HashMap< String,List<Integer> >();
		List<Integer> gradeGroup = new ArrayList<Integer>();
		gradeGroup.add(new Integer(4)); gradeGroup.add(new Integer(3));
			gradeScheduleMap2.put("bb-1", gradeGroup);
		gradeGroup = new ArrayList<Integer>();
		gradeGroup.add(new Integer(4)); gradeGroup.add(new Integer(3));
			gradeScheduleMap2.put("bb-2", gradeGroup );
		gradeGroup = new ArrayList<Integer>();
		gradeGroup.add(new Integer(2)); gradeGroup.add(new Integer(1));
			gradeScheduleMap2.put("bb-3", gradeGroup );
		gradeGroup = new ArrayList<Integer>();
		gradeGroup.add(new Integer(2)); gradeGroup.add(new Integer(1));
			gradeScheduleMap2.put("bb-4", gradeGroup );
		
		gradeGroup = new ArrayList<Integer>();
		gradeGroup.add(new Integer(2)); gradeGroup.add(new Integer(1));
			gradeScheduleMap2.put("fb-1", gradeGroup );
		gradeGroup = new ArrayList<Integer>();
		gradeGroup.add(new Integer(2)); gradeGroup.add(new Integer(1));
			gradeScheduleMap2.put("fb-2", gradeGroup );
		gradeGroup = new ArrayList<Integer>();
		gradeGroup.add(new Integer(4)); gradeGroup.add(new Integer(3));
			gradeScheduleMap2.put("fb-3", gradeGroup );
		gradeGroup = new ArrayList<Integer>();
		gradeGroup.add(new Integer(4)); gradeGroup.add(new Integer(3));
			gradeScheduleMap2.put("fb-4", gradeGroup );

		scheduleMap = new HashMap< String,List<KidAct> >();
		scheduleMap.put("bb-1", new ArrayList<KidAct>() );
		scheduleMap.put("bb-2", new ArrayList<KidAct>() );
		scheduleMap.put("bb-3", new ArrayList<KidAct>() );
		scheduleMap.put("bb-4", new ArrayList<KidAct>() );
		scheduleMap.put("hockey-1", new ArrayList<KidAct>() );
		scheduleMap.put("hockey-2", new ArrayList<KidAct>() );
		scheduleMap.put("hockey-3", new ArrayList<KidAct>() );
		scheduleMap.put("hockey-4", new ArrayList<KidAct>() );
		scheduleMap.put("rock-1", new ArrayList<KidAct>() );
		scheduleMap.put("rock-2", new ArrayList<KidAct>() );
		scheduleMap.put("rock-3", new ArrayList<KidAct>() );
		scheduleMap.put("rock-4", new ArrayList<KidAct>() );
		scheduleMap.put("fb-1", new ArrayList<KidAct>() );
		scheduleMap.put("fb-2", new ArrayList<KidAct>() );
		scheduleMap.put("fb-3", new ArrayList<KidAct>() );
		scheduleMap.put("fb-4", new ArrayList<KidAct>() );
		scheduleMap.put("soccer-1", new ArrayList<KidAct>() );
		scheduleMap.put("soccer-2", new ArrayList<KidAct>() );
		scheduleMap.put("soccer-3", new ArrayList<KidAct>() );
		scheduleMap.put("soccer-4", new ArrayList<KidAct>() );
		scheduleMap.put("pizza-1", new ArrayList<KidAct>() );
		scheduleMap.put("pizza-2", new ArrayList<KidAct>() );
		scheduleMap.put("pizza-3", new ArrayList<KidAct>() );
		scheduleMap.put("pizza-4", new ArrayList<KidAct>() );
		scheduleMap.put("flag-1", new ArrayList<KidAct>() );
		scheduleMap.put("flag-2", new ArrayList<KidAct>() );
		scheduleMap.put("flag-3", new ArrayList<KidAct>() );
		scheduleMap.put("flag-4", new ArrayList<KidAct>() );
		
		int CAPACITY =5;
		actCapacityMap = new HashMap<String, Activity>();
		actCapacityMap.put("bb-1", new Activity(null, CAPACITY));
		actCapacityMap.put("bb-2", new Activity(null, CAPACITY));
		actCapacityMap.put("bb-3", new Activity(null, CAPACITY));
		actCapacityMap.put("bb-4", new Activity(null, CAPACITY));
		actCapacityMap.put("hockey-1", new Activity(null, CAPACITY));
		actCapacityMap.put("hockey-2", new Activity(null, CAPACITY));
		actCapacityMap.put("hockey-3", new Activity(null, CAPACITY));
		actCapacityMap.put("hockey-4", new Activity(null, CAPACITY));
		actCapacityMap.put("fb-1", new Activity(null, CAPACITY));
		actCapacityMap.put("fb-2", new Activity(null, CAPACITY));
		actCapacityMap.put("fb-3", new Activity(null, CAPACITY));
		actCapacityMap.put("fb-4", new Activity(null, CAPACITY));
		actCapacityMap.put("rock-1", new Activity(null, CAPACITY));
		actCapacityMap.put("rock-2", new Activity(null, CAPACITY));
		actCapacityMap.put("rock-3", new Activity(null, CAPACITY));
		actCapacityMap.put("rock-4", new Activity(null, CAPACITY));
		actCapacityMap.put("soccer-1", new Activity(null, CAPACITY));
		actCapacityMap.put("soccer-2", new Activity(null, CAPACITY));
		actCapacityMap.put("soccer-3", new Activity(null, CAPACITY));
		actCapacityMap.put("soccer-4", new Activity(null, CAPACITY));
		actCapacityMap.put("pizza-1", new Activity(null, CAPACITY));
		actCapacityMap.put("pizza-2", new Activity(null, CAPACITY));
		actCapacityMap.put("pizza-3", new Activity(null, CAPACITY));
		actCapacityMap.put("pizza-4", new Activity(null, CAPACITY));
		actCapacityMap.put("flag-1", new Activity(null, CAPACITY));
		actCapacityMap.put("flag-2", new Activity(null, CAPACITY));
		actCapacityMap.put("flag-3", new Activity(null, CAPACITY));
		actCapacityMap.put("flag-4", new Activity(null, CAPACITY));
		
		
		StudentReader reader = new StudentReader();
		List<Kid> kidList = reader.readStudentActivities();
		
		return kidList;
	}
	
	List<Kid> populateKidList() {
		List<Kid> kidList = new ArrayList<Kid>();
		Kid kid = new Kid();
		kid.name = "Mike M";
		kid.grade = "1";
		kid.act.add("bb"); kid.act.add("hockey"); kid.act.add("rock"); kid.act.add("fb");
		kidList.add(kid);
		
		kid = new Kid();
		kid.name = "Willie W";
		kid.grade = "1";
		// TEST accid reg for same classes
		kid.act.add("bb"); kid.act.add("bb"); kid.act.add("bb"); kid.act.add("fb");
		kidList.add(kid);
		
		kid = new Kid();
		kid.name = "Fetti M";
		kid.grade = "1";
		kid.act.add("bb"); kid.act.add("hockey"); kid.act.add("rock"); kid.act.add("fb");
		kidList.add(kid);
		
		kid = new Kid();
		kid.name = "Dave M";
		kid.grade = "1";
		kid.act.add("flag"); kid.act.add("hockey"); kid.act.add("rock"); kid.act.add("fb");
		kidList.add(kid);
		
		kid = new Kid();
		kid.name = "Chris C";
		kid.grade = "2";
		kid.act.add("bb"); kid.act.add("fb"); kid.act.add("rock"); kid.act.add("soccer");
		kidList.add(kid);
		
		kid = new Kid();
		kid.name = "Bill B";
		kid.grade = "2";
		kid.act.add("hockey"); kid.act.add("soccer"); kid.act.add("rock"); kid.act.add("fb");
		kidList.add(kid);

		kid = new Kid();
		kid.name = "Julie B";
		kid.grade = "3";
		kid.act.add("hockey"); kid.act.add("soccer"); kid.act.add("rock"); kid.act.add("bb");
		kidList.add(kid);

		for (int i=0; i<10; i++) {
			kid = new Kid();
			kid.name = "Kid " + i;
			kid.grade = "3";
			kid.act.add("bb"); kid.act.add("hockey"); kid.act.add("rock"); kid.act.add("fb");
			kidList.add(kid);	
		}

		return kidList;
	}

}

class Kid {
	String name = "";
	String grade = "";
	String teacher = "";
	List<String> act = new ArrayList<String>();
	List<Boolean> actSchedList = new ArrayList<Boolean>();
	
	public Kid() {
		super();
		for(int i=0; i<4; i++) {
			actSchedList.add(false);
		}
	}

	@Override
	public String toString() {
		return "Kid [name=" + name + ", grade=" + grade + ", teacher="
				+ teacher + ", act=" + act + ", actSchedList=" + actSchedList
				+ "]";
	}

}

class KidAct {
	String name = "";
	String grade = "";
	String teacher = "";

	String act = "";
	String hour = "";
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "KidAct [name=" + name + ", grade=" + grade + ", teacher="
				+ teacher + ", act=" + act + ", hour=" + hour + "]";
	}
}

class Activity {
	String name;
	int capacity;
	int enrollment;
	
	public Activity(String name, int capacity) {
		super();
		this.capacity = capacity;
		this.name = name;
	}
	
	public boolean isFull() {
		return (capacity == enrollment);
	}

	@Override
	public String toString() {
		return "Activity [name=" + name + ", capacity=" + capacity
				+ ", enrollment=" + enrollment + "]";
	}
}