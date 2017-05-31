import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

public class CalcuatorsMenthodsTesting {

	@Test
	public void Counters() {
		ToolsForHelp accessor = new ToolsForHelp();
		 int counterOfChildren = accessor.FilesManagerGetVlaue("ChildrenCounter");
		 int counterOfTeacher = accessor.FilesManagerGetVlaue("TeachersCounter");
		 int counterOfAdmin = accessor.FilesManagerGetVlaue("AdminsCounter");
		 int counterOfSecurityGuards = accessor.FilesManagerGetVlaue("SecurityGuardsCounter");
		 int counterOfHouseKeeper = accessor.FilesManagerGetVlaue("HouseKeepersCounter");
		 
		assertEquals(3, counterOfChildren);
		assertEquals(2, counterOfTeacher);
		assertEquals(1, counterOfAdmin);
		assertEquals(1, counterOfSecurityGuards);
		assertEquals(0, counterOfHouseKeeper);	
  	}
	
	@Test
	public void Siblings() {
		ToolsForHelp accessor = new ToolsForHelp();
		 boolean checker;
		try {
			checker = accessor.CheckForYoungerSiblingAndAttendingNow("Hussein Ajam");
			assertEquals(false, checker);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
  	}
	
	@Test
	public void ConvertDateToAgeCategory() {
		ToolsForHelp accessor = new ToolsForHelp();
		 int checker;
	 
		      LocalDate today = LocalDate.now();
 		      //subtract 1 year to the current date
		      LocalDate sample = today.plus(-1, ChronoUnit.YEARS);
 			checker = accessor.ConvertBirthdayToAgeCategory(sample);
			assertEquals(0, checker); // age category 1 // I did make it 0 instead of 1 in the code for a reason
									  // 0 means age category 1 in my code 
			
			////////////////////////////////////////////////
			
		     LocalDate sample2 = today.plus(-13, ChronoUnit.MONTHS);
			checker = accessor.ConvertBirthdayToAgeCategory(sample2);
 			assertEquals(0, checker); // age category 1 
 			
 			////////////////////////////////////////////////////////

		    LocalDate sample3 = today.plus(-25, ChronoUnit.MONTHS);
			checker = accessor.ConvertBirthdayToAgeCategory(sample3);
			assertEquals(2, checker); // age category 2
			
 			////////////////////////////////////////////////////////

		    LocalDate sample4 = today.plus(-3, ChronoUnit.YEARS);
			checker = accessor.ConvertBirthdayToAgeCategory(sample4);
			assertEquals(3, checker); // age category 3
  	}
	
	
	
	
	@Test
	public void workingdays() {
		manageInvoiceController accessor = new manageInvoiceController();
		 int checker;
 		 LocalDate today = LocalDate.now();
 		 LocalDate sample = today.plus(7, ChronoUnit.DAYS);
 		 checker = accessor.CalculateperiodWithoutWeekends(sample);
 		 System.out.println(checker);
	     assertEquals(17, checker);						
	}
	

}
