import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

public class StaffAttendanceController {

    @FXML
    private ComboBox JobsCombo;

    @FXML
    private ListView StaffListView;

    @FXML
    private Label NameLabel;

    @FXML
    private RadioButton AttendButton;

    @FXML
    private ToggleGroup ButtonGroup;

    @FXML
    private RadioButton AbsenceButton;
    
    @FXML
    private DatePicker DatePicker;

    @FXML
    private Button RegisterAttendanceButton;

    @FXML
    private TextArea AttendanceReportTextView;

    @FXML
    private Button GenerateAttendaceReportButton;

    @FXML
    private Button SaveToPDFButton;
    
    ObservableList<String> JobsListForCombo = FXCollections.observableArrayList();
    ObservableList<String> StaffList = FXCollections.observableArrayList();
    
   private Teacher[] teacher ;
   private  int teacherNo;

   private HouseKeepingStaff[] houseKeeping ;
   private  int houseKeepingNo;

   private SecurityGuards[] securityGuards ;
   private  int securityGuardsNo;

   private Admins[] admin ;
   private  int AdminsNo;
   
    // This method is called when the FXML file is loaded
    public void initialize() 
    {
    	AttendButton.setUserData("Attend");
    	AbsenceButton.setUserData("Absent");
     	JobsCombo.setValue("Select Session");
     	JobsCombo.setItems(JobsListForCombo);
    	StaffListView.setItems(StaffList);
        JobsListForCombo.add("Teachers");
        JobsListForCombo.add("Admins");
        JobsListForCombo.add("Security Guards");
        JobsListForCombo.add("House Keeping Staff");
        
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
    	// Read and Store all Teacher objects to use them in the class functions
	      FileInputStream readStream = new FileInputStream("Teachers.dat");
	      ObjectInputStream readingObjects = new ObjectInputStream(readStream);
	      
	      ToolsForHelp tool = new ToolsForHelp();
	       teacherNo = tool.FilesManagerGetVlaue("TeachersCounter");
	      
	      // Create a  array
	      teacher = new Teacher[teacherNo];
	      
	      // Read the serialized objects from the file.
	      for (int i = 0; i < teacherNo; i++)
	      {
	    	  teacher[i] = (Teacher) readingObjects.readObject();
	      }
	      readingObjects.close();
	      
	      /////////////////////////////////////
	      
	    	// Read and Store all House/keepingStaff objects to use them in the class functions
	      
	      FileInputStream readStream2 = new FileInputStream("HouseKeepers.dat");
	      ObjectInputStream readingObjects2 = new ObjectInputStream(readStream2);
	      
	      ToolsForHelp tool2 = new ToolsForHelp();
	       houseKeepingNo = tool2.FilesManagerGetVlaue("HouseKeepersCounter");
	      
	      // Create a  array
	      houseKeeping = new HouseKeepingStaff[houseKeepingNo];
	      
	      // Read the serialized objects from the file.
	      for (int i = 0; i < houseKeepingNo; i++)
	      {
	    	  houseKeeping[i] = (HouseKeepingStaff) readingObjects2.readObject();
	      }
	      readingObjects2.close();
	      
	      ////////////////////////////////////////////
	      
	    	// Read and Store all Admins objects to use them in the class functions
	      FileInputStream readStream3 = new FileInputStream("Admins.dat");
	      ObjectInputStream readingObjects3 = new ObjectInputStream(readStream3);
	      
	      ToolsForHelp tool3 = new ToolsForHelp();
	       AdminsNo = tool3.FilesManagerGetVlaue("AdminsCounter");
	      
	      // Create a  array
	      admin = new Admins[AdminsNo];
	      
	      // Read the serialized objects from the file.
	      for (int i = 0; i < AdminsNo; i++)
	      {
	    	  admin[i] = (Admins) readingObjects3.readObject();
	      }
	      readingObjects3.close();
	      
	      ///////////////////////////////////////////////
	      
	    	// Read and Store all Security Guards objects to use them in the class functions
	      FileInputStream readStream4 = new FileInputStream("SecurityGuards.dat");
	      ObjectInputStream readingObjects4 = new ObjectInputStream(readStream4);
	      
	      ToolsForHelp tool4 = new ToolsForHelp();
	       securityGuardsNo = tool4.FilesManagerGetVlaue("SecurityGuardsCounter");
	      
	      // Create a  array
	      securityGuards = new SecurityGuards[securityGuardsNo];
	      
	      // Read the serialized objects from the file.
	      for (int i = 0; i < securityGuardsNo; i++)
	      {
	    	  securityGuards[i] = (SecurityGuards) readingObjects4.readObject();
	      }
	      readingObjects4.close();
    }


    @FXML
    void SearchListAction(MouseEvent event) {
  		NameLabel.setText(StaffListView.getSelectionModel().getSelectedItem()+"");  	
    }

    @FXML
    void GenerateAttendaceReportAction(ActionEvent event) {
    	String FullName = NameLabel.getText() ; 
    	if(FullName.equals("Staff Name") || FullName.equals("null") )
    	{
	    	JOptionPane.showMessageDialog(null, "Please Select a member of staff", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    	else
    	{
    		try {
				GenerateReport();
			} catch (ClassNotFoundException e) {
				   JOptionPane.showMessageDialog(null, "Class Not Found", "Error", JOptionPane.ERROR_MESSAGE);
  			} catch (IOException e) {
				  JOptionPane.showMessageDialog(null, "Something happend with the storage files", "Error", JOptionPane.ERROR_MESSAGE);
			}
    	}
    	  
    }

    @FXML
    void JobsSelectedAction(ActionEvent event) {
    	// Clear the observable list to give it new information
    	StaffList.clear();
    	
    	// Search staff accourding to the selected job title
    	
 	      String SelectedJobTitle = JobsCombo.getValue()+"";
 	      String temp = null;
 	      
 	      if(SelectedJobTitle.equals("Teachers"))
 	      {
 		      for (int i = 0; i < teacherNo; i++)
		      {
 	 	  		StaffList.add(teacher[i].getFullName()+"");
		      }	
 	      }
 	      else if (SelectedJobTitle.equals("Admins"))
 	      {
 		      for (int i = 0; i < AdminsNo; i++)
		      {
 	 	  		StaffList.add(admin[i].getFullName()+"");
		      }	
 	      }
 	      else if (SelectedJobTitle.equals("Security Guards"))
 	      {
 		      for (int i = 0; i < securityGuardsNo; i++)
		      {
 	 	  		StaffList.add(securityGuards[i].getFullName()+"");
		      }	
 	      }
 	      else if (SelectedJobTitle.equals("House Keeping Staff"))
 	      {
 		      for (int i = 0; i < houseKeepingNo; i++)
		      {
 	 	  		StaffList.add(houseKeeping[i].getFullName()+"");
		      }	
 	      }
    }

    @FXML
    void RegisterAttendanceAction(ActionEvent event) throws IOException, ClassNotFoundException {
    	
  	    String FullName = NameLabel.getText();
      	Validations validator = new Validations();
    	boolean checker = true;
    	if(FullName.equals("null") || FullName.equals("Staff Name"))
    	{
		   JOptionPane.showMessageDialog(null, "Please Select Child", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	if(!AttendButton.isSelected() && !AbsenceButton.isSelected())
    	{
		   JOptionPane.showMessageDialog(null, "Please Select Status", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	if(DatePicker.getValue() == null)
    	{
		   JOptionPane.showMessageDialog(null, "Please Select the Date of Attendance", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	
    	if(checker)
    	{
    		try {
				RegisterStaffAttendance();
			} catch (ClassNotFoundException e) {
				   JOptionPane.showMessageDialog(null, "Class Not Found", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				   JOptionPane.showMessageDialog(null, "Something happend with the storage files", "Error", JOptionPane.ERROR_MESSAGE);
			}
    	}
    }

    @FXML
    void SaveToPDFAction(ActionEvent event) {
    	WritableImage snapshot = AttendanceReportTextView.snapshot(new SnapshotParameters(), null);
    	File pic = new File("snap.png");
    	
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", pic);
        } catch (IOException e) {
	 		   JOptionPane.showMessageDialog(null, "error while saving  image", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void GenerateReport() throws IOException, ClassNotFoundException
    {
    	String report = "";
 	      String FullName = NameLabel.getText() ; 
	      		report += "         " + FullName +" Attendance Report \n \n " ;
	      		
	      	    //  Read the selected staff Stored attendance objects
	      	  FileInputStream readStream = new FileInputStream("StaffAttendance.dat");
	  	      ObjectInputStream readingObjects = new ObjectInputStream(readStream);
	  	      
	  	      ToolsForHelp tool = new ToolsForHelp();
	  	      int arraySize2 = tool.FilesManagerGetVlaue("StaffAttendanceCounter");
	  	      arraySize2 = arraySize2;
	  	      // Create a  array
	  	      Attendance[] attendArray = new Attendance [arraySize2];
	  	      // Read the serialized objects from the file.
	  	      for (int i = 0; i < arraySize2; i++)
	  	      {
	  	    	  attendArray[i] = (Attendance) readingObjects.readObject();
	   	      }
	  	      
	  	       for (int i = 0; i < arraySize2; i++)
	  	      {
	  	    	   if(attendArray[i].getPersonForAtendance().equals(FullName))
		      		report += attendArray[i].getAttendance() +" \n" ;
	   	      }
	  	      AttendanceReportTextView.setText(report);
	
    }
    
    public void RegisterStaffAttendance() throws IOException, ClassNotFoundException
    {
    	// Get the object of the selected staff rom the pre-defined staff objects arrays
    	
  	  String FullName = NameLabel.getText();
	      int TempCheckr = 0;
	      
	      String SelectedJobTitle = JobsCombo.getValue()+"";
	      String temp = null;
	      
	      if(SelectedJobTitle.equals("Teachers"))
	      {
		      for (int i = 0; i < teacherNo; i++)
		      {
		    	  if (teacher[i].getFullName().equals(FullName))
		    		  TempCheckr = i;
		      }	
	      }
	      else if (SelectedJobTitle.equals("Admins"))
	      {
		      for (int i = 0; i < AdminsNo; i++)
		      {
		    	  if (admin[i].getFullName().equals(FullName))
		    		  TempCheckr = i;
		      }	
	      }
	      else if (SelectedJobTitle.equals("Security Guards"))
	      {
		      for (int i = 0; i < securityGuardsNo; i++)
		      {
		    	  if (securityGuards[i].getFullName().equals(FullName))
		    		  TempCheckr = i;
		      }	
	      }
	      else if (SelectedJobTitle.equals("House Keeping Staff"))
	      {
		      for (int i = 0; i < houseKeepingNo; i++)
		      {
		    	  if (houseKeeping[i].getFullName().equals(FullName))
		    		  TempCheckr = i;
		      }	
	      }
	      
	      // Read the stored objects and Store them in array 
	     FileInputStream readStream = new FileInputStream("StaffAttendance.dat");
	      ObjectInputStream readingObjects = new ObjectInputStream(readStream);
	           
	    // Save the Object To file 
	      ToolsForHelp tool = new ToolsForHelp();
	      int attendanceArrayNo = tool.FilesManagerGetVlaue("StaffAttendanceCounter");
	      attendanceArrayNo = attendanceArrayNo + 1;
	      
	      // Create a  array
	      Attendance[] attendArray = new Attendance [attendanceArrayNo];
	      
	      // Read the serialized objects from the file.
	      for (int i = 0; i < attendanceArrayNo - 1; i++)
	      {
	    	  attendArray[i] = (Attendance) readingObjects.readObject();
	      }
	      
	  
	      
	      // Create Attendance object for the selected staff
	      String Status = ButtonGroup.getSelectedToggle().getUserData()+"";

	      if(SelectedJobTitle.equals("Teachers"))
	      {
		      attendArray[attendanceArrayNo - 1] = new Attendance(teacher[TempCheckr],Status,DatePicker.getValue()+"");
	      }
	      else if (SelectedJobTitle.equals("Admins"))
	      {
		      attendArray[attendanceArrayNo - 1] = new Attendance(admin[TempCheckr],Status,DatePicker.getValue()+"");
	      }
	      else if (SelectedJobTitle.equals("Security Guards"))
	      {
		      attendArray[attendanceArrayNo - 1] = new Attendance(securityGuards[TempCheckr],Status,DatePicker.getValue()+"");
	      }
	      else if (SelectedJobTitle.equals("House Keeping Staff"))
	      {
		      attendArray[attendanceArrayNo - 1] = new Attendance(houseKeeping[TempCheckr],Status,DatePicker.getValue()+"");
	      }
	      
	  
	      // Save the array back
	      FileOutputStream saveStream = new FileOutputStream("StaffAttendance.dat");
	      ObjectOutputStream savingObjects =  new ObjectOutputStream(saveStream);
	      
	      // Write the serialized objects to the file.
	      for (int i = 0; i < attendanceArrayNo; i++)
	      {
	    	  savingObjects.writeObject(attendArray[i]);
	      }
	     
	      savingObjects.close();
	      
       //increase and save the counter
	     ToolsForHelp tool2 = new ToolsForHelp();
       tool2.FilesManagerIncrease("StaffAttendanceCounter");
    }

}
