import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

import javax.mail.MessagingException;
import javax.swing.JOptionPane;

import com.itextpdf.text.DocumentException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class ManageWaitingListController {

	  @FXML
	    private ListView StudentListView;

	    @FXML
	    private Button CheckAvailabilityButton;

	    @FXML
	    private Button RegisterButton;

	    @FXML
	    private Button DeleteButton;

	    @FXML
	    private TextArea StudentDetails;
	    
	    @FXML
	    private Label ChildNameLabel;
    
    ObservableList<String> ChildrenList = FXCollections.observableArrayList();
 
    private  int arraySize;
    private Child[] child ;
    
    private Session[] sessions;
    private int sessionArrayLength;
    
    // This method is called when the FXML file is loaded
    public void initialize()
    {
        StudentListView.setItems(ChildrenList);

	   	try {
			StartTheScene();
		} catch (ClassNotFoundException e) {
			   JOptionPane.showMessageDialog(null, "Class Not Found", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			   JOptionPane.showMessageDialog(null, "Something happend with the Storage files", "Error", JOptionPane.ERROR_MESSAGE);
		} 
    }
    
    private void StartTheScene() throws IOException, ClassNotFoundException
    {     	
    	// Read  Sessions
	      FileInputStream readStream2 = new FileInputStream("Sessions.dat");
	      ObjectInputStream readingObjects2 = new ObjectInputStream(readStream2);
	      
	      ToolsForHelp tool2 = new ToolsForHelp();
	      int arraySize3 = tool2.FilesManagerGetVlaue("SessionsCounter");
	      sessionArrayLength = arraySize3;
	      // Create a  array
	      sessions = new Session[arraySize3];
	      
	      // Read the serialized objects from the file.
	      for (int i = 0; i < arraySize3; i++)
	      {
	    	  sessions[i] = (Session) readingObjects2.readObject();
	      }
	      readingObjects2.close();
	      ///////////////////////////////////////
	      
    // Read and Store all Children objects to use them in the class functions
    	
      FileInputStream readStream = new FileInputStream("Children.dat");
      ObjectInputStream readingObjects = new ObjectInputStream(readStream);

      ToolsForHelp tool = new ToolsForHelp();
      arraySize = tool.FilesManagerGetVlaue("ChildrenCounter");
      
     // Create a  array
     child = new Child[arraySize];
     
     // Read the serialized objects from the file.
     for (int i = 0; i < arraySize; i++)
     {
   	  child[i] = (Child) readingObjects.readObject();
     }
     readingObjects.close();
 
     // Read the Waiting To Register Children
     for (int i = 0; i < arraySize; i++)
     {
     if(child[i].getStatus().equals("Waiting"))
    	 {
     		 ChildrenList.add(child[i].getFullName());
    	 }
      }
    }
    
    @FXML
    void ChildSelectedAction(MouseEvent event) {
    	
   	  String report = "";
	  String FullName = StudentListView.getSelectionModel().getSelectedItem()+"";
	  ChildNameLabel.setText(FullName);
	   	 int TempCheckr = 0;
	     int childId = 0;
	   
	      // Read through the array of objects
	      for (int i = 0; i < arraySize; i++)
	      {
	    	  if (child[i].getFullName().equals(FullName))
	    	  {
		        report += "Contacted us On: " + child[i].getRegistrationDate()+"\n" ;
		        report += "Current Status    : " + child[i].getStatus()+"\n" ;
		        report += "\t ------------------------\n" ;
	
	    		report += "Birthday Date     : " + child[i].getBirthday()+"\n" ;
	    		report += "Alergy Information: " + child[i].getAlergyInformation()+"\n" ;
	    		report += "Address           : " + child[i].getAddress()+"\n" ;
	    		report += "Gender            : " + child[i].getGender()+"\n" ;
	    		report += "Father Name       : " + child[i].getFather()+"\n" ;
	    		report += "Mother Name       : " + child[i].getMother()+"\n" ;
	    		report += "Carer Name        : " + child[i].getCarerName()+"\n" ;
	    		report += "Carer Number      : " + child[i].getCareContactNumber()+"\n" ;
	    		report += "Carer Email       : " + child[i].getCarerEmail()+"\n" ;
	    		report += "Session      : " + child[i].getSession()+"\n" ;
	    		
	    	  }
	    	     
	      }
	  	 StudentDetails.setText(report);
   	}
    

    @FXML
    void CheckAvailabilityAction(ActionEvent event) 
    {
    	
     	 String report = "";
    	  String FullName = ChildNameLabel.getText()+"";
    	  
         	if(FullName.equals("null") || FullName.equals("Child Name"))
      	{
    		   JOptionPane.showMessageDialog(null, "Please Select Child", "Error", JOptionPane.ERROR_MESSAGE);
      	}
       	else
       	{
       	//Get the Selected Child
         	int ChildPossition = -1;
        	// Get the Selected Child Object
            for (int i = 0; i < arraySize; i++)
            {
    	       	 if(child[i].getFullName().equals(FullName));
    	       	 {
    	       		ChildPossition = i;
    	       		break;
    	       	 }
             }
      	    ToolsForHelp tool = new ToolsForHelp();
        	int Age =  tool.ConvertBirthdayToAgeCategory(child[ChildPossition].getBirthday());
        	
        	String SessionName = child[ChildPossition].getSession();
        	
        	int Checker = CheckSessionAvailability(Age, SessionName);
        	if (Checker < 15)
     		   JOptionPane.showMessageDialog(null, Checker+" Children currently in this session \n Session is Available", "Error", JOptionPane.ERROR_MESSAGE);
        	else
      		   JOptionPane.showMessageDialog(null, Checker+" Children currently in this session \n Session is not Available", "Error", JOptionPane.ERROR_MESSAGE);
       	}
    	
    }

    @FXML
    void DeleteAction(ActionEvent event){
    	
  	  String FullName = ChildNameLabel.getText()+"";
	  
   	if(FullName.equals("null") || FullName.equals("Child Name"))
	{
		   JOptionPane.showMessageDialog(null, "Please Select Child", "Error", JOptionPane.ERROR_MESSAGE);
	}
 	else
 	{
 		try {
			DeleteFromWaitingList();
		} catch (IOException e) {
			   JOptionPane.showMessageDialog(null, "Error Accessing the Storage file", "Error", JOptionPane.ERROR_MESSAGE);
		}
 	}
    	  
    }

    @FXML
    void RegisterAction(ActionEvent event) {
    	
    	  String FullName = ChildNameLabel.getText()+"";
    	  
    	   	if(FullName.equals("null") || FullName.equals("Child Name"))
    		{
    			   JOptionPane.showMessageDialog(null, "Please Select Child", "Error", JOptionPane.ERROR_MESSAGE);
    		}
    	 	else
    	 	{
    	 		
						try {
							RegisterChild();
						} catch (ClassNotFoundException e) {
							   JOptionPane.showMessageDialog(null, "Class Not founded!", "Error", JOptionPane.ERROR_MESSAGE);
						} catch (IOException e) {
							   JOptionPane.showMessageDialog(null, "Something went wrong with the files, or files not founded!", "Error", JOptionPane.ERROR_MESSAGE);
						} catch (DocumentException e) {
							   JOptionPane.showMessageDialog(null, "Error Generating PDF File", "Error", JOptionPane.ERROR_MESSAGE);
						} catch (MessagingException e) {
		 					   JOptionPane.showMessageDialog(null, "Error Sending Email", "Error", JOptionPane.ERROR_MESSAGE);
						}
				
    	 	}
    }
    
    public void RegisterChild() throws IOException, ClassNotFoundException, DocumentException, MessagingException
    {
    	// Check Availability once again in case the user did not click check
    	//Get the Selected Child
    	String SelectedChild = StudentListView.getSelectionModel().getSelectedItem()+"";
    	int ChildPossition = -1;
    	// Get the Selected Child Object
        for (int i = 0; i < arraySize; i++)
        {
	       	 if(child[i].getFullName().equals(SelectedChild));
	       	 {
	       		ChildPossition = i;
	       		break;
	       	 }
         }
  	    ToolsForHelp tool = new ToolsForHelp();
    	int Age =  tool.ConvertBirthdayToAgeCategory(child[ChildPossition].getBirthday());
    	
    	String SessionName = child[ChildPossition].getSession();
    	int Childpossition = -1;
    	int Checker = CheckSessionAvailability(Age, SessionName);
    	if (Checker < 15) // Space Available
    		{
    		 LocalDate today = LocalDate.now();
    		 String ChildTo = StudentListView.getSelectionModel().getSelectedItem()+"";
    	 	   for (int i = 0; i < arraySize; i++)
    		   {
    	 	 	  if (child[i].getFullName().equals(ChildTo))
    		 	  {
    	 	 		child[i].setStatus("Attending");
    	 	 		child[i].setRegistrationDate(today);   // update the registration date since the previous one was only to fill the gap not the actual registration  date
    	 	 		Childpossition = i;
    	 	 		break;
    	 		   }
    		    }
    		 	   
    		   // Save the array back
    		      FileOutputStream saveStream = new FileOutputStream("Children.dat");
    		      ObjectOutputStream savingObjects =  new ObjectOutputStream(saveStream);
    		      
    		      // Write the serialized objects to the file.
    		      for (int i = 0; i < arraySize; i++)
    		      {
    		    	  savingObjects.writeObject(child[i]);
    		      }
    		      savingObjects.close();
    		      
    		   	    // Now Time To create Registration Invoice
    		   	    
    			   	 FileInputStream readStream2 = new FileInputStream("RegistrationInvoices.dat");
    			     ObjectInputStream readingObjects2 = new ObjectInputStream(readStream2);
    			     
    		 	     int arraySize3 = tool.FilesManagerGetVlaue("RegistrationInvoicesCounter");
    			     arraySize3 = arraySize3 + 1;
    			     
    			     // Create a  array
    			     RegistrationInvoice[] array = new RegistrationInvoice[arraySize3];
    			     
    			     // Read the serialized objects from the file.
    			     for (int i = 0; i < arraySize3 - 1; i++)
    			     {
    			   	  array[i] = (RegistrationInvoice) readingObjects2.readObject();
    			     }
    			     
    			     array[arraySize3 - 1] = new RegistrationInvoice();
    			     array[arraySize3 - 1].SetChild(child[ChildPossition]);
    			     array[arraySize3 - 1].SetDateOfIssue(today);
    			    
    			     // Get session Details
    			     int targetSession = 0;	     
    			     for(int i = 0 ; i < sessionArrayLength; i++)
    			     {
    			    	 if(sessions[i].getSessionName().equals(child[ChildPossition].getSession()+""))
    			    	 {
    			    		 targetSession = i;
    			    		 break;
    			    	 }
    			     }
    			     
    			     if(Age == 0) // Age  between 0 to 2 
    			          array[arraySize3 - 1].SetTotalAmount(sessions[targetSession].getAgeCate1PerWeek() + array[arraySize3 - 1].GetRegistrationFee() );
    			     else if(Age == 2) // Age between 2 and 3
    			          array[arraySize3 - 1].SetTotalAmount(sessions[targetSession].getAgeCate2PerWeek() + array[arraySize3 - 1].GetRegistrationFee() );
    			     else if(Age == 3) // Age over 3
    			          array[arraySize3 - 1].SetTotalAmount(sessions[targetSession].getAgeCate3PerWeek() + array[arraySize3 - 1].GetRegistrationFee() );
    			    
    			     readingObjects2.close();
    			     // Save the array back
    			     FileOutputStream saveStream2 = new FileOutputStream("RegistrationInvoices.dat");
    			     ObjectOutputStream savingObjects2 =  new ObjectOutputStream(saveStream2);
    			     
    			     // Write the serialized objects to the file.
    			     for (int i = 0; i < arraySize3; i++)
    			     {
    			   	  savingObjects2.writeObject(array[i]);
    			     }
    			     savingObjects2.close();
    			     
    			   //increase and save the counter
    			    tool.FilesManagerIncrease("RegistrationInvoicesCounter");	
    			    
    			    // Send it to the user
    			    
    			    // Send it to carer
    			     String ReportForEmail [] = new String[7];
    			     ReportForEmail [0]= "\t Registration Invoce for your son "+child[ChildPossition].getFullName();
    			     ReportForEmail [1]= " Issued on "+today;
    			     ReportForEmail [2]= " Fees Amount "+array[arraySize3 - 1].GetTotalAmount();
    			     ReportForEmail [3]= " Session "+child[ChildPossition].getSession()+"";
    			     ReportForEmail [4]= " ------------------------------------------------------";
    			     ReportForEmail [5]= " Note:  This invoce need to be paid within 7 woking days ";
    			     ReportForEmail	[6]= " Otherwise will be subjective to late payment, Thanks you";
    			     
    				    // Prepare PDF to be send
    				    Email servise = new Email();
    				    servise.CreatePDF(ReportForEmail); 
    				    servise.SendPDF(child[ChildPossition].getCarerEmail());
    		      
    		      
    		      
    		}
    	else // Space Not Available
   		   JOptionPane.showMessageDialog(null, Checker+" Children currently in this session \n Session is not Available", "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void DeleteFromWaitingList() throws IOException
    {
    	String ChildToWithdraw = StudentListView.getSelectionModel().getSelectedItem()+"";
   	   for (int i = 0; i < arraySize; i++)
  	   {
   	 	  if (child[i].getFullName().equals(ChildToWithdraw))
  	 	  {
   	 		child[i].setStatus("Delete");
   	 		break;
   		   }
  	    }
  	 	   
  	   // Save the array back
  	      FileOutputStream saveStream = new FileOutputStream("Children.dat");
  	      ObjectOutputStream savingObjects =  new ObjectOutputStream(saveStream);
  	      
  	      // Write the serialized objects to the file.
  	      for (int i = 0; i < arraySize; i++)
  	      {
  	    	  savingObjects.writeObject(child[i]);
  	      }
  	      savingObjects.close();
    }
    public int CheckSessionAvailability (int Age, String SessionName)
    {
	      int tempDate;
	      int numberOfChildinSameAgeCategoryAndSameSession = 0;
	      String ChildSession;
	      for (int i = 0; i < arraySize; i++)
	      {
  	       // Convert String Date of Birthday to Age
	    	  ToolsForHelp tool = new ToolsForHelp();
	    	tempDate = tool.ConvertBirthdayToAgeCategory(child[i].getBirthday());
	    	ChildSession = child[i].getSession();
	    	if (Age == tempDate && ChildSession.equals(SessionName)) 
	    	{
	    		numberOfChildinSameAgeCategoryAndSameSession++;
	    	}
	      } 
  	      return  numberOfChildinSameAgeCategoryAndSameSession;
	   
     }

}
