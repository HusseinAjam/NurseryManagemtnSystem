import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddStaffController {

    @FXML
    private TextField FirstNameText;

    @FXML
    private TextField LastNAmeText;

    @FXML
    private DatePicker BirthdayDate;

    @FXML
    private TextArea AddressText;

    @FXML
    private TextField UserNameText;

    @FXML
    private TextField PassWordText;

    @FXML
    private Button RegisterButton;

    @FXML
    private ComboBox SessionsCombo;

    @FXML
    private ComboBox GenderCombo;

    @FXML
    private ComboBox ShiftCombo;

    @FXML
    private ComboBox DutyCombo;

    @FXML
    private ComboBox JobsCombo;
    
    ObservableList<String> SessionList = FXCollections.observableArrayList();
    ObservableList<String> GenderList = FXCollections.observableArrayList("Male","Female");
    ObservableList<String> ShiftList = FXCollections.observableArrayList("Day Shift","Night Shift");
    ObservableList<String> DutyList = FXCollections.observableArrayList("Kitchen","Bathroom","Classroom A", "Classroom B", "Classroom C" );
    ObservableList<String> JobList = FXCollections.observableArrayList("Admin","Teacher","House Keeping Staff", "Security Guards" );
 
   
    // This method is called when the FXML file is loaded
    public void initialize()
    {	
     	JobsCombo.setItems(JobList);
     	GenderCombo.setItems(GenderList);
     	DutyCombo.setItems(DutyList);
     	ShiftCombo.setItems(ShiftList);
     	SessionsCombo.setItems(SessionList);
     	
	    JobsCombo.setValue("Select Job");
      	DutyCombo.setValue("Select Duty");
        ShiftCombo.setValue("Select Shift");
      	GenderCombo.setValue("Select Gender");
      	SessionsCombo.setValue("Select Session");
         
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
      	// Read and Sessions
        FileInputStream readStream2 = new FileInputStream("Sessions.dat");
       ObjectInputStream readingObjects2 = new ObjectInputStream(readStream2);
       
       ToolsForHelp tool2 = new ToolsForHelp();
       int arraySize3 = tool2.FilesManagerGetVlaue("SessionsCounter");
       // Create a  array
        Session[] sessions = new Session[arraySize3];
       
       // Read the serialized objects from the file.
       for (int i = 0; i < arraySize3; i++)
       {
     	  sessions[i] = (Session) readingObjects2.readObject();
       }
       readingObjects2.close();
       // Read through the array of objects
       for (int i = 0; i < arraySize3; i++)
       {
     	  SessionList.add(sessions[i].getSessionName());
       }	  
         
	     
	     UserNameText.setDisable(true);
	     PassWordText.setDisable(true);
	     ShiftCombo.setDisable(true);
	     DutyCombo.setDisable(true);
	     SessionsCombo.setDisable(true);
    }
	     


    @FXML
    void RegisterAction(ActionEvent event)  {
    	 
    	if(ValidationReport())  // Validate the all inputs 
    	{
	    	try {
				Register();
			} catch (ClassNotFoundException e) {
				   JOptionPane.showMessageDialog(null, "Class Not founded!", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				   JOptionPane.showMessageDialog(null, "Something went wrong with the files, or files not founded!", "Error", JOptionPane.ERROR_MESSAGE);
			}
    	}
    	
    	
    }

    @FXML
    void jobSelected(ActionEvent event) {
        UserNameText.setDisable(true);
        PassWordText.setDisable(true);
        ShiftCombo.setDisable(true);
        DutyCombo.setDisable(true);
        SessionsCombo.setDisable(true);
       	
       	if(JobsCombo.getValue().equals("Teacher"))
       	{
       	     SessionsCombo.setDisable(false);
       	}
       	else  if(JobsCombo.getValue().equals("Admin"))
           {
       	     UserNameText.setDisable(false);
       	     PassWordText.setDisable(false);
           }
       	else  if(JobsCombo.getValue().equals("House Keeping Staff"))
           {
       	     DutyCombo.setDisable(false);
           }
       	else if(JobsCombo.getValue().equals("Security Guards"))
           {
       	     ShiftCombo.setDisable(false);
           }
    }
    
    public void Register() throws ClassNotFoundException, IOException
    {
    	String FName = FirstNameText.getText();
    	String LName = LastNAmeText.getText();
    	LocalDate Birthday = BirthdayDate.getValue();
    	String Address = AddressText.getText();
    	String Gender = GenderCombo.getValue()+"";
    	
    	
    	
     	if(JobsCombo.getValue().equals("Teacher"))
       	{
	    	// Read and Store all Teachers objects to use them in the class functions
   	        FileInputStream readStream = new FileInputStream("Teachers.dat");
  	        ObjectInputStream readingObjects = new ObjectInputStream(readStream);
	  	      
	  	      ToolsForHelp tool = new ToolsForHelp();
	  	      int teacherNo = tool.FilesManagerGetVlaue("TeachersCounter");
	  	      teacherNo = teacherNo + 1; // Create space to the new staff
	  	      // Create a  array
	  	      Teacher[] teacherarray = new Teacher[teacherNo];
	  	      
		      // Read the serialized objects from the file.
		      for (int i = 0; i < teacherNo - 1; i++)
		      {
		    	  teacherarray[i] = (Teacher) readingObjects.readObject();
		      }
		      readingObjects.close();
 
     		  // load the new teacher into the array
	  	    teacherarray[teacherNo - 1] = new Teacher(FName, LName, Birthday, Address, Gender, SessionsCombo.getValue()+"");
     	      //Now Save the updated array back to the file
    	     FileOutputStream saveStream = new FileOutputStream("Teachers.dat");
    	     ObjectOutputStream savingObjects =  new ObjectOutputStream(saveStream);
    	     for (int i = 0; i < teacherNo; i++)
    	       {
    	    	  savingObjects.writeObject(teacherarray[i]);
    	       }  
     	      savingObjects.close();
     	      tool.FilesManagerIncrease("TeachersCounter");
     		}
     	
       	else  if(JobsCombo.getValue().equals("Admin"))
           {
	    	// Read and Store all Admins objects to use them in the class functions
   	        FileInputStream readStream = new FileInputStream("Admins.dat");
  	        ObjectInputStream readingObjects = new ObjectInputStream(readStream);
	  	      
	  	      ToolsForHelp tool = new ToolsForHelp();
	  	      int adminNo = tool.FilesManagerGetVlaue("AdminsCounter");
	  	      adminNo = adminNo + 1; // Create space to the new staff
	  	      // Create a  array
	  	      Admins [] staffrarray = new Admins[adminNo];
	  	      
		      // Read the serialized objects from the file.
		      for (int i = 0; i < adminNo - 1; i++)
		      {
		    	  staffrarray[i] = (Admins) readingObjects.readObject();
		      }
		      readingObjects.close();
	  	      
 
	   		  // load the new teacher into the array
	  	       staffrarray[adminNo - 1] = new Admins(FName, LName, Birthday, Address, Gender, UserNameText.getText()+"", PassWordText.getText());
	   	      //Now Save the updated array back to the file
	  	     FileOutputStream saveStream = new FileOutputStream("Admins.dat");
	  	     ObjectOutputStream savingObjects =  new ObjectOutputStream(saveStream);
	  	     for (int i = 0; i < adminNo; i++)
	  	       {
	  	    	  savingObjects.writeObject(staffrarray[i]);
	  	       }  
	   	      savingObjects.close();
	   	      tool.FilesManagerIncrease("AdminsCounter");
   	      
           }
       	else  if(JobsCombo.getValue().equals("House Keeping Staff"))
           {
	    	// Read and Store all HouseKeepers objects to use them in the class functions
   	        FileInputStream readStream = new FileInputStream("HouseKeepers.dat");
  	        ObjectInputStream readingObjects = new ObjectInputStream(readStream);
	  	      
	  	      ToolsForHelp tool = new ToolsForHelp();
	  	      int staffNo = tool.FilesManagerGetVlaue("HouseKeepersCounter");
	  	       staffNo = staffNo + 1; // Create space to the new staff
	  	      // Create a  array
	  	      HouseKeepingStaff [] staffrarray = new HouseKeepingStaff[staffNo];
	  	      
		      // Read the serialized objects from the file.
		      for (int i = 0; i < staffNo - 1; i++)
		      {
		    	  staffrarray[i] = (HouseKeepingStaff) readingObjects.readObject();
		      }
		      readingObjects.close();
 
	   		  // load the new teacher into the array
	  	       staffrarray[staffNo - 1] = new HouseKeepingStaff(FName, LName, Birthday, Address, Gender, DutyCombo.getValue()+"");
	   	      //Now Save the updated array back to the file
	  	     FileOutputStream saveStream = new FileOutputStream("HouseKeepers.dat");
	  	     ObjectOutputStream savingObjects =  new ObjectOutputStream(saveStream);
	  	     for (int i = 0; i < staffNo; i++)
	  	       {
	  	    	  savingObjects.writeObject(staffrarray[i]);
	  	       }  
	   	      savingObjects.close();
	   	      tool.FilesManagerIncrease("HouseKeepersCounter");
           }
     	
       	else if(JobsCombo.getValue().equals("Security Guards"))
        {
	    	// Read and Store all Security Guards objects to use them in the class functions
   	        FileInputStream readStream = new FileInputStream("SecurityGuards.dat");
  	        ObjectInputStream readingObjects = new ObjectInputStream(readStream);
 
	  	      
	  	      ToolsForHelp tool = new ToolsForHelp();
	  	      int staffNo = tool.FilesManagerGetVlaue("SecurityGuardsCounter");
	  	       staffNo = staffNo + 1; // Create space to the new staff
	  	      // Create a  array
	  	      SecurityGuards [] staffrarray = new SecurityGuards[staffNo];
	  	      
		      // Read the serialized objects from the file.
		      for (int i = 0; i < staffNo - 1; i++)
		      {
		    	  staffrarray[i] = (SecurityGuards) readingObjects.readObject();
		      }
		      readingObjects.close();
		      
	   		  // load the new teacher into the array
	  	       staffrarray[staffNo - 1] = new SecurityGuards(FName, LName, Birthday, Address, Gender, ShiftCombo.getValue()+"");
	   	      //Now Save the updated array back to the file
	  	      FileOutputStream saveStream = new FileOutputStream("SecurityGuards.dat");
	  	      ObjectOutputStream savingObjects =  new ObjectOutputStream(saveStream);
	  	      for (int i = 0; i < staffNo; i++)
	  	       {
	  	    	  savingObjects.writeObject(staffrarray[i]);
	  	       }  
	   	      savingObjects.close();
	   	      tool.FilesManagerIncrease("SecurityGuardsCounter");
           }
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
    	if(JobsCombo.getValue().equals("Select Job"))
    	{
		   JOptionPane.showMessageDialog(null, "Job is Required", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	System.out.println("Address"+ AddressText.getText()+"IsEmpty");
    	return checker;
	}

}
