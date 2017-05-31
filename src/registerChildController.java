import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.swing.JOptionPane;

import com.itextpdf.text.DocumentException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class registerChildController {

    @FXML
    private TextField FirstNameText;

    @FXML
    private TextField LastNAmeText;

    @FXML
    private DatePicker BirthdayDate;

    @FXML
    private TextArea AddressText;

    @FXML
    private TextArea AlergyInformationText;

    @FXML
    private TextField FatherFirstNameText;

    @FXML
    private TextField FatherLastNameText;

    @FXML
    private TextField MotherFirstNameText;

    @FXML
    private TextField MotherLastNameText;

    @FXML
    private TextField CarerFullNameText;

    @FXML
    private TextField CarerContactNumberText;

    @FXML
    private TextField CarerEmailAddressText;

    @FXML
    private Button CheckPlaceAvailabilityButton;

    @FXML
    private Button AddToWaitingListButton;

    @FXML
    private Button RegisterButton;

    @FXML
    private ComboBox SessionsCombo;
    
    @FXML
    private ComboBox GenderCombo;
    
    ObservableList<String> SessionsLsiForCombo = FXCollections.observableArrayList();
    ObservableList<String> Gender = FXCollections.observableArrayList("Male","Female");
    private Child[] children;
    private int Counter = 0;
    private Session[] sessions;
    private int sessionArrayLength;
    // This method is called when the FXML file is loaded
    public void initialize() throws IOException, ClassNotFoundException 
    {
    	SessionsCombo.setValue("Select Session");
    	SessionsCombo.setItems(SessionsLsiForCombo);
    	GenderCombo.setValue("Select Gender");
    	GenderCombo.setItems(Gender); 	
    	
	   	try {
			StartTheScene();
		} catch (ClassNotFoundException e) {
			   JOptionPane.showMessageDialog(null, "Class Not Found", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			   JOptionPane.showMessageDialog(null, "Something happend with the Storage files", "Error", JOptionPane.ERROR_MESSAGE);
		} 
	      
    }
    
    private void StartTheScene() throws ClassNotFoundException, IOException
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
	      // Read through the array of objects
	      for (int i = 0; i < arraySize3; i++)
	      {
	    	  SessionsLsiForCombo.add(sessions[i].getSessionName());
	      }	
	      ///////////////////////////////////////
	        // Read the number of objects stored to go through them
	    	ToolsForHelp tool = new ToolsForHelp();
		    Counter = tool.FilesManagerGetVlaue("ChildrenCounter");
		    // Read the stored children objects
	 		   
	 	      FileInputStream inStream2 = new FileInputStream("Children.dat");
		      ObjectInputStream objectInputFile = new ObjectInputStream(inStream2);

		      // Create array of objects
		      children = new Child[Counter];
		  
		      // Read the serialized objects from the file.
		      for (int i = 0; i < Counter; i++)
		      {
		    	  children[i] = (Child) objectInputFile.readObject();
		      }
		      objectInputFile.close();
    }
    
    @FXML
    void AddToWaitingListAction(ActionEvent event) {
    
    	if(ValidationReport())  // Validate the all inputs 
    	{
	    	try {
				AddToWaitingList();  // Add to Waiting List
			} catch (ClassNotFoundException e) {
				   JOptionPane.showMessageDialog(null, "Class Not founded!", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				   JOptionPane.showMessageDialog(null, "Something went wrong with the files, or files not founded!", "Error", JOptionPane.ERROR_MESSAGE);
			}
    	}
     }

    @FXML
    void CheckPlaceAvailabilityAction(ActionEvent event) {
    	if(ValidationReport())  // Validate the all inputs 
    	{
    	CheckPlaceAvailiabilty();
    	}
    }

    @FXML
    void RegisterAction(ActionEvent event){
    	if(ValidationReport())  // Validate the all inputs 
    	{
	    	
				try {
					Register();
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
   
    public boolean CheckSessionAvailability (int Age, String SessionName)
    {
	      int tempDate;
	      int numberOfChildinSameAgeCategoryAndSameSession = 0;
	      String ChildSession;
	      for (int i = 0; i < Counter; i++)
	      {
  	       // Convert String Date of Birhday to Age
	    	  ToolsForHelp tool = new ToolsForHelp();
	    	tempDate = tool.ConvertBirthdayToAgeCategory(children[i].getBirthday());
	    	ChildSession = children[i].getSession();
	    	if (Age == tempDate && ChildSession.equals(SessionName)) 
	    	{
	    		numberOfChildinSameAgeCategoryAndSameSession++;
	    	}
	      } 
	      System.out.println(numberOfChildinSameAgeCategoryAndSameSession);
	      if(numberOfChildinSameAgeCategoryAndSameSession >= 15 ) // Number of stored children in the same session and same age category less than 15
	      return  false;
	      else
	      return true;
     }
    
    public void Register() throws IOException, ClassNotFoundException, DocumentException, MessagingException
    {
    	ToolsForHelp tool = new ToolsForHelp();
    	int Age =  tool.ConvertBirthdayToAgeCategory(BirthdayDate.getValue());
    	String SessionName = SessionsCombo.getValue()+"";
    	Boolean Checker = CheckSessionAvailability(Age, SessionName);
   if (Checker)
    {
    	  FileInputStream readStream = new FileInputStream("Children.dat");
   	      ObjectInputStream readingObjects = new ObjectInputStream(readStream);

   	      int arraySize2 = Counter;
   	      arraySize2 = arraySize2 + 1;
   	      
   	      // Create a  array
   	      Child[] children2 = new Child[arraySize2];
   	      
   	      // Read the serialized objects from the file.
   	      for (int i = 0; i < arraySize2 - 1; i++)
   	      {
   	    	  children2[i] = (Child) readingObjects.readObject();
    	  }
   		   
   	      Parents p1 = new Parents(FatherFirstNameText.getText(), FatherLastNameText.getText() );
   	      Parents p2 = new Parents(MotherFirstNameText.getText(), MotherLastNameText.getText() ); 
   	      LocalDateTime currentTime = LocalDateTime.now();
    	  LocalDate today = currentTime.toLocalDate();

   	      children2[arraySize2 - 1] = new Child(FirstNameText.getText() , LastNAmeText.getText() , BirthdayDate.getValue(),
   					AddressText.getText(), GenderCombo.getValue() + "", p1 , p2 , AlergyInformationText.getText(), CarerFullNameText.getText() 
   					, CarerEmailAddressText.getText(), CarerContactNumberText.getText(), SessionsCombo.getValue()+"", today); 
   	      
   	   readingObjects.close();
   	   
   	      // Save the array back
   	      FileOutputStream saveStream = new FileOutputStream("Children.dat");
   	      ObjectOutputStream savingObjects =  new ObjectOutputStream(saveStream);
   	      
   	      // Write the serialized objects to the file.
   	      for (int i = 0; i < arraySize2; i++)
   	      {
   	    	  savingObjects.writeObject(children2[i]);
   	      }
   	      savingObjects.close();
   	      
          //increase and save the counter
   	    ToolsForHelp tool2 = new ToolsForHelp();
   	    tool2.FilesManagerIncrease("ChildrenCounter");
   	    
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
	     array[arraySize3 - 1].SetChild(children2[arraySize2 - 1]);
	     array[arraySize3 - 1].SetDateOfIssue(children2[arraySize2 - 1].getRegistrationDate());
	    
	     // Get session Details
	     int targetSession = 0;	     
	     for(int i = 0 ; i < sessionArrayLength; i++)
	     {
	    	 if(sessions[i].getSessionName().equals(SessionsCombo.getValue()+""))
	    	 {
	    		 targetSession = i;
	    		 break;
	    	 }
	     }
	     double discount = 0;
	     if(Age == 0) // Age  between 0 to 2 
	     {
	    	 if(tool.CheckForYoungerSiblingAndAttendingNow(children2[arraySize2 - 1].getFullName()))
	    		 discount =  ((sessions[targetSession].getAgeCate1PerWeek() + array[arraySize3 - 1].GetRegistrationFee())/100)*10;
	          array[arraySize3 - 1].SetTotalAmount(sessions[targetSession].getAgeCate1PerWeek() + array[arraySize3 - 1].GetRegistrationFee() - discount );
	    
	     }
          else if(Age == 2) // Age between 2 and 3
          {
        	  if(tool.CheckForYoungerSiblingAndAttendingNow(children2[arraySize2 - 1].getFullName()))
        		  discount = ((sessions[targetSession].getAgeCate2PerWeek() + array[arraySize3 - 1].GetRegistrationFee())/100)*10;
          array[arraySize3 - 1].SetTotalAmount(sessions[targetSession].getAgeCate2PerWeek() + array[arraySize3 - 1].GetRegistrationFee() - discount );
          }
          else if(Age == 3) // Age over 3
          {
        	  if(tool.CheckForYoungerSiblingAndAttendingNow(children2[arraySize2 - 1].getFullName()))
        		  discount = ((sessions[targetSession].getAgeCate3PerWeek() + array[arraySize3 - 1].GetRegistrationFee())/ 100)*10;
          array[arraySize3 - 1].SetTotalAmount(sessions[targetSession].getAgeCate3PerWeek() + array[arraySize3 - 1].GetRegistrationFee() - discount );
          }
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
	    tool2.FilesManagerIncrease("RegistrationInvoicesCounter");	    
	    
	    // Send it to carer
	     String ReportForEmail [] = new String[7];
	     ReportForEmail [0]= "\t Registration Invoce for your son "+children2[arraySize2 - 1].getFullName();
	     ReportForEmail [1]= " Issued on "+children2[arraySize2 - 1].getRegistrationDate();
	     ReportForEmail [2]= " Fees Amount "+array[arraySize3 - 1].GetTotalAmount();
	     ReportForEmail [3]= " Session"+children2[arraySize2 - 1].getSession();
	     ReportForEmail [4]= " ------------------------------------------------------";
	     ReportForEmail [5]= " Note:  This invoce need to be paid within 7 woking days ";
	     ReportForEmail	[6]= " Otherwise will be subjective to late payment, Thanks you";
	     
		    // Prepare PDF to be send
		    Email servise = new Email();
		    servise.CreatePDF(ReportForEmail); 
		    servise.SendPDF(children2[arraySize2 - 1].getCarerEmail());
    }
	   else
		   JOptionPane.showMessageDialog(null, " Sorry Space Un Available", "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    
	public void CheckPlaceAvailiabilty()
	{
	// Read the age from birthday of the Child
	ToolsForHelp tool = new ToolsForHelp();
	int Age =  tool.ConvertBirthdayToAgeCategory(BirthdayDate.getValue());
	String SessionName = SessionsCombo.getValue()+"";
	Boolean Checker = CheckSessionAvailability(Age, SessionName);
	if (Checker)
	   JOptionPane.showMessageDialog(null, "space Available", "Info", JOptionPane.INFORMATION_MESSAGE);
	else
	   JOptionPane.showMessageDialog(null, " Sorry Space Un Available", "Error", JOptionPane.ERROR_MESSAGE);

	}
	
	public void AddToWaitingList() throws IOException, ClassNotFoundException
	{
		// In this case we still register the child but with "waiting" status
		
  	  FileInputStream readStream = new FileInputStream("Children.dat");
	      ObjectInputStream readingObjects = new ObjectInputStream(readStream);

	      int arraySize2 = Counter;
	      arraySize2 = arraySize2 + 1;
	      
	      // Create a  array
	      Child[] children2 = new Child[arraySize2];
	      
	      // Read the serialized objects from the file.
	      for (int i = 0; i < arraySize2 - 1; i++)
	      {
	    	  children2[i] = (Child) readingObjects.readObject();
	  }
		   
	      Parents p1 = new Parents(FatherFirstNameText.getText(), FatherLastNameText.getText() );
	      Parents p2 = new Parents(MotherFirstNameText.getText(), MotherLastNameText.getText() ); 
	      LocalDateTime currentTime = LocalDateTime.now();
	  LocalDate today = currentTime.toLocalDate();

	      children2[arraySize2 - 1] = new Child(FirstNameText.getText() , LastNAmeText.getText() , BirthdayDate.getValue(),
					AddressText.getText(), GenderCombo.getValue() + "", p1 , p2 , AlergyInformationText.getText(), CarerFullNameText.getText() 
					, CarerEmailAddressText.getText(), CarerContactNumberText.getText(), SessionsCombo.getValue()+"", today); 
	      
	      children2[arraySize2 - 1].setStatus("Waiting");
	   readingObjects.close();
	   
	      // Save the array back
	      FileOutputStream saveStream = new FileOutputStream("Children.dat");
	      ObjectOutputStream savingObjects =  new ObjectOutputStream(saveStream);
	      
	      // Write the serialized objects to the file.
	      for (int i = 0; i < arraySize2; i++)
	      {
	    	  savingObjects.writeObject(children2[i]);
	      }
	      savingObjects.close();
	      
      //increase and save the counter
	    ToolsForHelp tool2 = new ToolsForHelp();
	    tool2.FilesManagerIncrease("ChildrenCounter");
	    
	   
	}
	
	public boolean ValidationReport()
	{
		
    	Validations validator = new Validations();
    	boolean checker = true;
    	if(!validator.validateName(FirstNameText.getText()))
    	{
		   JOptionPane.showMessageDialog(null, "Invalid First Name", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	if(!validator.validateName(LastNAmeText.getText()))
    	{
		   JOptionPane.showMessageDialog(null, "Invalid Last Name", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	if(BirthdayDate.getValue() == null)
    	{
		   JOptionPane.showMessageDialog(null, "Birthday is Required", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	if(AddressText.getText().equals(""))
    	{
		   JOptionPane.showMessageDialog(null, "Address Is Required", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	if(GenderCombo.getValue().equals("Select Gender"))
    	{
		   JOptionPane.showMessageDialog(null, "Gender is Required", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	if(!validator.validateName(FatherFirstNameText.getText()) || !validator.validateName(FatherLastNameText.getText()))
    	{
		   JOptionPane.showMessageDialog(null, "Invalid Fathers Details", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	if(!validator.validateName(MotherFirstNameText.getText()) || !validator.validateName(MotherLastNameText.getText()))
    	{
		   JOptionPane.showMessageDialog(null, "Invalid Mother Details", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	if(CarerFullNameText.getText().equals(""))
    	{
		   JOptionPane.showMessageDialog(null, "Invalid Carer Name", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	if(!validator.validateEmailAddress(CarerEmailAddressText.getText()))
    	{
 		   JOptionPane.showMessageDialog(null, "Invalid Carer Email", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	if(!validator.validateNumber(CarerContactNumberText.getText()))
    	{
		   JOptionPane.showMessageDialog(null, "Invalid Carer Contact Number", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	if(SessionsCombo.getValue().equals("Select Session"))
    	{
		   JOptionPane.showMessageDialog(null, "Session Is Required", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	return checker;
	}
}
