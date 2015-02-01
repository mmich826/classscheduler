import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StudentReader {
	
	public List<Kid> readStudentActivities() {
		
		List<Kid> kidList = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader("StudentActivities.csv")))
		{
 
			String sCurrentLine;
			int i = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				i++;
				//System.out.println(sCurrentLine);
				List<String> tokList = Arrays.asList( sCurrentLine.split(",") );
				
				if (tokList == null || tokList.isEmpty() || tokList.get(0) == null || tokList.get(0).isEmpty()) {
					System.out.println("Bad line reading file - either blank line or empty name.  Line number " + i);
					continue;
				}
				
				Kid k = new Kid();
				k.name = tokList.get(0) + ", " + tokList.get(1);
				k.grade = tokList.get(2);
				k.teacher = tokList.get(3);
				k.act = tokList.subList(4, tokList.size() );

				kidList.add(k);
				//System.out.println(k);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
 
		return kidList;
	}
}
