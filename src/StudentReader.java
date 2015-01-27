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
 
			while ((sCurrentLine = br.readLine()) != null) {
				//System.out.println(sCurrentLine);
				List<String> tokList = Arrays.asList( sCurrentLine.split(",") );
				
				Kid k = new Kid();
				k.name = tokList.get(1) + ", " + tokList.get(0);
				k.grade = tokList.get(2);
				k.act = tokList.subList(3, tokList.size() );

				kidList.add(k);
				//System.out.println(k);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
 
		return kidList;
	}
}
