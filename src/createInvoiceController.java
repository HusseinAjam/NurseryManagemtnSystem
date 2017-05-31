import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import javax.mail.MessagingException;
import javax.swing.JOptionPane;

import com.itextpdf.text.DocumentException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class createInvoiceController {

    @FXML
    private ListView ChildListView;

    @FXML
    private ListView DueListView;

    @FXML
    private Label ChildNameLabel;
    
    @FXML
    private Button CreateCalculateAndSendButton;

    @FXML
    private DatePicker DatePicker;
    
    ObservableList<String> ChildrenList = FXCollections.observableArrayList();
    ObservableList<String> DueInDays = FXCollections.observableArrayList();
    
    private int StudentArraySize; 
    private Child[] child ;
     
    // This method is called when the FXML file is loaded
    public void initialize() 
    {
    	DueListView.setItems(DueInDays);
      	ChildListView.setItems(ChildrenList);
      	
		try {
			StartTheScene();
		} catch (ClassNotFoundException e) {
     		   JOptionPane.showMessageDialog(null, "Class Not Found", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
     		   JOptionPane.showMessageDialog(null, "Something happend with the Storage files", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (ParseException e) {
     		   JOptionPane.showMessageDialog(null, "Cannot parse the variable", "Error", JOptionPane.ERROR_MESSAGE);
		}
    }
    
    private void StartTheScene() throws ClassNotFoundException, IOException, ParseException
    {
    	// Read children objects
        FileInputStream readStream = new FileInputStream("Children.dat");
        ObjectInputStream readingObjects = new ObjectInputStream(readStream);

         ToolsForHelp tool = new ToolsForHelp();
         StudentArraySize = tool.FilesManagerGetVlaue("ChildrenCounter");
         
        // Create a  array
        child = new Child[StudentArraySize];
        
        // Read the serialized objects from the file.
        for (int i = 0; i < StudentArraySize; i++)
        {
      	  child[i] = (Child) readingObjects.readObject();
        }
        readingObjects.close();
        
        // List all children's next invoices count down        
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDate today = currentTime.toLocalDate();
         
        
          LocalDate CurrentInvoiceDate ;
          for (int i = 0; i < StudentArraySize; i++)
        {
        	if(child[i].getStatus().equals("Attending"))
        	{        		
        		// Get the invoice date of the current month, which is the same day and year of the registration date but with the current month
        		CurrentInvoiceDate = LocalDate.of(today.getYear(),today.getMonthValue(),child[i].getRegistrationDate().getDayOfMonth());
          		
         		if(!CheckIfThisMonthInvoiceBeenGeneratedOrNot(CurrentInvoiceDate,child[i].getFullName()))
         		{
            		ChildrenList.add(child[i].getFullName()); 
                    DueInDays.add("Invoice Due In "+ CalculateDaysLeftToGenerateMonthlyInvoice(CurrentInvoiceDate)+" Days" ); // Calculate days left to generate the invoice
         		}
         	}
        }
    }
    
    @FXML
    void ChildSelectedAction(MouseEvent event) {
  		ChildNameLabel.setText(ChildListView.getSelectionModel().getSelectedItem()+"");  	
    }
    
    @FXML
    void CreateCalculateAndSendAction(ActionEvent event) {
    	
    	String FullName = ChildNameLabel.getText();    	
      	if(FullName.equals("null") || FullName.equals("Child Name"))
    	{
		   JOptionPane.showMessageDialog(null, "Please Select Child", "Error", JOptionPane.ERROR_MESSAGE);
     	}
     	else
     	{
				try {
					CalculateAndCreate();
				} catch (ClassNotFoundException e) {
	 				   JOptionPane.showMessageDialog(null, "Class Not Found", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
	 				   JOptionPane.showMessageDialog(null, "Something happend with the Storage files", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (DocumentException e) {
	 				   JOptionPane.showMessageDialog(null, "Fail To Create PDF File", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (MessagingException e) {
	 				   JOptionPane.showMessageDialog(null, "Fail To Send the Email", "Error", JOptionPane.ERROR_MESSAGE);
				}
     	}
    }
    
    public  int CalculateDaysLeftToGenerateMonthlyInvoice(LocalDate invoiceDate) throws ParseException  // theLastOfTheMonthDate is the invoice month not calendar month
    {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDate today = currentTime.toLocalDate();
    	
        Period period = Period.between(today, invoiceDate);
        return period.getDays();
     	
    /*    LocalDateTime currentTime = LocalDateTime.now();
        LocalDate today = currentTime.toLocalDate();
        
        LocalDate date2 =  invoiceDate;
        int workDaysCounter = 0;
        
      

        do {
        	 date2 = date2.minus(1, ChronoUnit.DAYS);
        	 
              if (date2.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date2.getDayOfWeek().equals(DayOfWeek.SUNDAY))
              {
            	  continue;
              }
              else
              {
            	 ++workDaysCounter; 
              }
 
        } while (workDaysCounter < 7);
        System.out.println("ssss "+ date2);
        Period period = Period.between(today, date2);
        return period.getDays();   */
        
    
}
    
    public boolean CheckIfThisMonthInvoiceBeenGeneratedOrNot(LocalDate CurrentInvoiceDate,String ChildName) throws IOException, ClassNotFoundException
    {
    	// Read the MonthlyInvoiceFile
    	
    	FileInputStream readStream3 = new FileInputStream("MonthlyInvoices.dat");
        ObjectInputStream readingObjects3 = new ObjectInputStream(readStream3);
       
        ToolsForHelp tool = new ToolsForHelp();
        int invoiceNo = tool.FilesManagerGetVlaue("MonthlyInvoicesCounter");
       // Create a  array
        MonthlyInvoice[] invoice = new MonthlyInvoice[invoiceNo];
       
       // Read the serialized objects from the file.
       for (int i = 0; i < invoiceNo; i++)
       {
    	   invoice[i] = (MonthlyInvoice) readingObjects3.readObject();
       }
       readingObjects3.close();
       
       // Check If we have created a monthly invoice for the selected child this month or not
       int month = CurrentInvoiceDate.getMonthValue();
       
       for (int i = 0; i < invoiceNo; i++)
       {
    	   if( invoice[i].GetChildName().equals(ChildName) && invoice[i].GetDateOfIssue().getMonthValue() == month ) 
    	   {
    		  return true; 
    	   }
       }
    	return false;
    }
    public void CalculateAndCreate() throws IOException, ClassNotFoundException, DocumentException, MessagingException
    {
    	// Get Selected Child
    	String childName = ChildNameLabel.getText();    	
    	int ChildPossition = -1;
    	
    	// Get the child position
        for (int i = 0; i < StudentArraySize; i++)
        {
        	if(child[i].getFullName().equals(childName))
        		ChildPossition = i;	 
        }
        
        /* Calculate EarlyDroping and Late pick up fine for the current month to add them to the next month invoice,
         so off scheduling fine always calculated late by one month */
        
          FileInputStream readStream = new FileInputStream("PickUpAndDropping.dat");
	      ObjectInputStream readingObjects = new ObjectInputStream(readStream);
	      
	      ToolsForHelp tool = new ToolsForHelp();
	      int arraySize2 = tool.FilesManagerGetVlaue("PickUpAndDroppingCounter");
	      arraySize2 = arraySize2;
	      // Create a  array
	      DroppingAndPickUp[] attendArray = new DroppingAndPickUp [arraySize2];
	      // Read the serialized objects from the file.
	      for (int i = 0; i < arraySize2; i++)
	      {
	    	  attendArray[i] = (DroppingAndPickUp) readingObjects.readObject();
 	      }
	      readingObjects.close();
	      ////////////////////////
	      
	      // Now time to calculate the off scheduling dropping or pick up total minutes to get paid later (10 pounds for every 5 min)
          int NumberofOffMinutes = 0; 
	      LocalDate today = LocalDate.now();  // today should be the invoice date
	      LocalDate lastInvoiceDate = LocalDate.now().plusMonths(-1);

	      for (int i = 0; i < arraySize2; i++)
	      {
	    	  if (attendArray[i].getChildName().equals(child[ChildPossition].getFullName()) && !attendArray[i].getActivityDate().isBefore(lastInvoiceDate)) 
	    	  {
	    		   /* We have checked the off schedule attendance were happened of only the last month not previous months
	    		   *  because those should be paid in their invoice month, the idea is every 
	    		   *  monthly invoice to pay the off scheduling of the last month 
	    		   */
	    		  NumberofOffMinutes +=  attendArray[i].getTimeDifferenceinMinutes(); // The summation of all off minutes that stored in the file for that specific child and during the last month	only   		}
	    	  }
 	      }
	      
	      // Now time to calculate the actual fee of the child according to the session and age category for the next month
		     double feesAmount  = 0;  // Calculate the fees amount for monthly invoice (4 weeks for a month)
		    // We need to read the session of the selected child
		    // Read  Sessions
		      FileInputStream readStream2 = new FileInputStream("Sessions.dat");
		      ObjectInputStream readingObjects2 = new ObjectInputStream(readStream2);
		      
		      ToolsForHelp tool2 = new ToolsForHelp();
		      int arraySize3 = tool2.FilesManagerGetVlaue("SessionsCounter");
		      int sessionArrayLength = arraySize3;
		      // Create a  array
		      Session [] sessions = new Session[arraySize3];
		      
		      // Read the serialized objects from the file.
		      for (int i = 0; i < arraySize3; i++)
		      {
		    	  sessions[i] = (Session) readingObjects2.readObject();
		      }
		      readingObjects2.close(); 
		      
		      // Find the selected child's session
		      int targetSessionPosition = -1;
		      for (int i = 0; i < arraySize3; i++)
		      {
		    	  if(child[ChildPossition].getSession().equals(sessions[i].getSessionName()))
		    		  targetSessionPosition = i;
 		      }

		     // fees of 4 week  of the selected session (1 month)
		     
 		     int Age =  tool.ConvertBirthdayToAgeCategory(child[ChildPossition].getBirthday()); // Calculate the age of child from their birthday, 
		     /* The reason why I don't store the age with the child object is to calculate the
		        age every time we came to generate any invoice (Registration, Leaving and Monthly),
		        Because Children Grow up and that changes  their fees rate based on the new age category, 
		        In this way I guarantee accurate calculations each time I come to generate any time of Invoice
		    */
		     
		     if(Age == 0) // Age  between 0 to 2 
		    	 feesAmount = sessions[targetSessionPosition].getAgeCate1PerWeek() * 4;
		     else if(Age == 2) // Age between 2 and 3
		    	 feesAmount = sessions[targetSessionPosition].getAgeCate2PerWeek() * 4;
		     else if(Age == 3) // Age over 3
		    	 feesAmount = sessions[targetSessionPosition].getAgeCate3PerWeek() * 4;
		     
		     // Sibling discount 
       	   if(tool.CheckForYoungerSiblingAndAttendingNow(child[ChildPossition].getFullName()))
       	   {
       		feesAmount = feesAmount - ((feesAmount)/100)*10;
       	   }

	      
	      // Now We have the fees of the month and fine of the off schedule droppings or pick ups
		  // Time to generate the invoice
	      
	         FileInputStream readStream3 = new FileInputStream("MonthlyInvoices.dat");
		     ObjectInputStream readingObjects3 = new ObjectInputStream(readStream3);
		     
	 	     int monthlySize = tool.FilesManagerGetVlaue("MonthlyInvoicesCounter");
	 	     monthlySize = monthlySize + 1;
		     
		     // Create a  array
		     MonthlyInvoice[] monthlyarray = new MonthlyInvoice[monthlySize];
		     
		     // Read the serialized objects from the file.
		     for (int i = 0; i < monthlySize - 1; i++)
		     {
		    	 monthlyarray[i] = (MonthlyInvoice) readingObjects3.readObject();
		     }
		     readingObjects3.close();
		     
		     LocalDate currentDate = LocalDate.now();
		  
	 	     monthlyarray[monthlySize - 1] = new MonthlyInvoice();
		     monthlyarray[monthlySize - 1].SetChild(child[ChildPossition]);
		     monthlyarray[monthlySize - 1].SetDateOfIssue(currentDate);
		     monthlyarray[monthlySize - 1].SetStatus(false);
		     monthlyarray[monthlySize - 1].SetTotalAmount(feesAmount);
		    
		     // 10 pound for every 5 minutes
		     double fineAmount = NumberofOffMinutes / 5;
		     fineAmount = fineAmount * 10; //10 pounds
		     monthlyarray[monthlySize - 1].SetFineAmount(fineAmount);
		     
		     String ReportForEmail [] = new String[7];
		     ReportForEmail [0]= "\t Monthly Invoce for your son "+child[ChildPossition].getFullName();
		     ReportForEmail [1]= " Issued on "+currentDate;
		     ReportForEmail [2]= " Fees Amount "+feesAmount;
		     ReportForEmail [3]= " Off Schedule Arraival "+NumberofOffMinutes+" Minutes ";
		     ReportForEmail [4]= " Fine Amount " + fineAmount;
		     ReportForEmail [5]= " Note:  This invoce need to be paid within 7 woking days ";
		     ReportForEmail	[6]= "	Otherwise will be subjective to late payment, Thanks ";
		     
 		     
		     // Save the array back
		     FileOutputStream saveStream3 = new FileOutputStream("MonthlyInvoices.dat");
		     ObjectOutputStream savingObjects3 =  new ObjectOutputStream(saveStream3);
		     
		     // Write the serialized objects to the file.
		     for (int i = 0; i < monthlySize; i++)
		     {
		   	  savingObjects3.writeObject(monthlyarray[i]);
		     }
		     savingObjects3.close();
		     
		   //increase and save the counter
		    tool2.FilesManagerIncrease("MonthlyInvoicesCounter");
		    
		    // Prepare PDF to be send
		    Email servise = new Email();
		    servise.CreatePDF(ReportForEmail); 
		    servise.SendPDF(child[ChildPossition].getCarerEmail());
    }

}
