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

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

public class manageChildAttendanceController {

    @FXML
    private TextField FirstNameText;

    @FXML
    private TextField LastNAmeText;

    @FXML
    private ComboBox SessionsCombo;

    @FXML
    private Button ChildNameSearchButton;

    @FXML
    private ListView  ChildrenListView;

    @FXML
    private Label ChildNameLabel;

    @FXML
    private RadioButton AttendButton;

    @FXML
    private ToggleGroup AttendaceButtonSelector;

    @FXML
    private RadioButton AbsenceButton;

    @FXML
    private Button RegisterAttendanceButton;

    @FXML
    private DatePicker DatePicker;

    @FXML
    private Label ChildNameLabel1;

    @FXML
    private TextArea AttendanceReportTextView;

    @FXML
    private Button GenerateAttendaceReportButton;

    @FXML
    private Button SaveReportToPDF;
    
    ObservableList<String> SessionsLsiForCombo = FXCollections.observableArrayList();
    ObservableList<String> ChildrenList = FXCollections.observableArrayList();
   private  int arraySize;
   private Child[] child ;
    // This method is called when the FXML file is loaded
    public void initialize()
    {
    	AttendButton.setUserData("Attend");
    	AbsenceButton.setUserData("Absent");
     	SessionsCombo.setValue("Select Session");
    	SessionsCombo.setItems(SessionsLsiForCombo);
    	ChildrenListView.setItems(ChildrenList); 
	      
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
	      
	      /////////////////////////////////////
	      
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
	    	  SessionsLsiForCombo.add(sessions[i].getSessionName());
	      }	 
    }

    @FXML
    void ChildNameSearchAction(ActionEvent event) {
    	
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
    	
    	if(checker)
    	{ 
	      String FullName = FirstNameText.getText()+" "+LastNAmeText.getText();
	      int TempCheckr = 0;
	      // Read through the array of objects
	      for (int i = 0; i < arraySize; i++)
	      {
	    	  if (child[i].getFullName().equals(FullName)  && child[i].getStatus().equals("Attending"))
	    		  TempCheckr = 1;
 	      }
	      
	      	if(TempCheckr == 0)
	      	{
	 		   JOptionPane.showMessageDialog(null, "Child Not Found", "Error", JOptionPane.ERROR_MESSAGE);
	      	}
	      	else
	      	{
	      		ChildNameLabel.setText(FullName);
	      	}
    	}
    }

    @FXML
    void ChildSessionSearchListAction(MouseEvent event) {

	      		ChildNameLabel.setText(ChildrenListView.getSelectionModel().getSelectedItem()+"");  	
     }

    @FXML
    void GenerateAttendaceReportAction(ActionEvent event) throws IOException, ClassNotFoundException {
    	 String report = "";
    	 
    	 String FullName = ChildNameLabel.getText() ;
	      int TempCheckr = 0;
	      // Read through the array of objects
	      for (int i = 0; i < arraySize; i++)
	      {
	    	  if (child[i].getFullName().equals(FullName))
	    		  TempCheckr = 1;
	      }
	      
	      	if(TempCheckr == 0)
	      	{
	      		System.out.println("Child Not Found");
	      	}
	      	else
	      	{
	      		report += "         " + FullName +" Attendance Report \n \n" ;
	      		
	      	    //  Read that Child Stored attendance objects
	      	  FileInputStream readStream = new FileInputStream("Attendance.dat");
	  	      ObjectInputStream readingObjects = new ObjectInputStream(readStream);
	  	      
	  	      ToolsForHelp tool = new ToolsForHelp();
	  	      int arraySize2 = tool.FilesManagerGetVlaue("ChildrentAttendanceCounter");
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
		      		report += attendArray[i].getAttendance() + " \n" ;
  	   	      }
 	  	      AttendanceReportTextView.setText(report);
	      	} 
    }

    @FXML
    void RegisterAttendanceAction(ActionEvent event){
    	
    	String FullName = ChildNameLabel.getText(); 
    	
      	Validations validator = new Validations();
    	boolean checker = true;
    	if(FullName.equals("null") || FullName.equals("Child Name"))
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
				RegisterAttendance();
			} catch (ClassNotFoundException e) {
				   JOptionPane.showMessageDialog(null, "Class Not Found", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				   JOptionPane.showMessageDialog(null, "Something happend with the storage files", "Error", JOptionPane.ERROR_MESSAGE);
			}
    	}
    }

    @FXML
    void SaveReportToAction(ActionEvent event) {
    	WritableImage snapshot = AttendanceReportTextView.snapshot(new SnapshotParameters(), null);
    	File pic = new File("snap.png");
    	
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", pic);
        } catch (IOException e) {
	 		   JOptionPane.showMessageDialog(null, "error while saving  image", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    void SessionSelectedAction(ActionEvent event) {
    	// Clear the observable list to give it new information
    	ChildrenList.clear();
    	// Search children accourding to the selected session
 	      String SelectedSession = SessionsCombo.getValue()+"";
 	      String temp = null;
  	      // Read through the array of objects
	      for (int i = 0; i < arraySize; i++)
	      {
	    	  //System.out.println(child[i].getSession());
	    	  temp = child[i].getSession();
	    	  if (temp.equals(SelectedSession)  && child[i].getStatus().equals("Attending"))
	    	  {
 	  		    ChildrenList.add(child[i].getFullName()+"");
	    	  }
	      }	
    }
    
    public void RegisterAttendance () throws IOException, ClassNotFoundException
    {
    	String FullName = ChildNameLabel.getText(); 
    	String Status = AttendaceButtonSelector.getSelectedToggle().getUserData()+"";
	      int TempCheckr = 0;
	      // Read through the array of objects
	      for (int i = 0; i < arraySize; i++)
	      {
	    	  if (child[i].getFullName().equals(FullName))
	    		  TempCheckr = i;
	      }
	      	    
	    // Save the Object To file 
	      FileInputStream readStream = new FileInputStream("Attendance.dat");
	      ObjectInputStream readingObjects = new ObjectInputStream(readStream);

  	      ToolsForHelp tool = new ToolsForHelp();
	      int arraySize2 = tool.FilesManagerGetVlaue("ChildrentAttendanceCounter");
	      arraySize2 = arraySize2 + 1;
	      
	      // Create a  array
	      Attendance[] attendArray = new Attendance [arraySize2];
	      
	      // Read the serialized objects from the file.
	      for (int i = 0; i < arraySize2 - 1; i++)
	      {
	    	  attendArray[i] = (Attendance) readingObjects.readObject();
 	      }
	      
	      // Create Attendance object for the selected child
	      attendArray[arraySize2 - 1] = new Attendance(child[TempCheckr],Status,DatePicker.getValue()+"");
  	  
	      // Save the array back
	      FileOutputStream saveStream = new FileOutputStream("Attendance.dat");
	      ObjectOutputStream savingObjects =  new ObjectOutputStream(saveStream);
	      
	      // Write the serialized objects to the file.
	      for (int i = 0; i < arraySize2; i++)
	      {
	    	  savingObjects.writeObject(attendArray[i]);
	      }
	     
	      savingObjects.close();
	      
       //increase and save the counter
  	      ToolsForHelp tool2 = new ToolsForHelp();
          tool2.FilesManagerIncrease("ChildrentAttendanceCounter");
    }
  
}
