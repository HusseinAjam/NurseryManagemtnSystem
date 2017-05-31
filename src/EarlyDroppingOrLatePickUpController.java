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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

public class EarlyDroppingOrLatePickUpController {

    @FXML
    private TextField FirstNameText;

    @FXML
    private TextField LastNAmeText;

    @FXML
    private ComboBox SessionsCombo;

    @FXML
    private Button ChildNameSearchButton;

    @FXML
    private ListView ChildrenListView;

    @FXML
    private Label ChildNameLabel;

    @FXML
    private RadioButton DroppingButton;

    @FXML
    private ToggleGroup AttendaceButtonSelector;

    @FXML
    private RadioButton PickUpButton;

    @FXML
    private Button SaveButton;

    @FXML
    private DatePicker DatePicker;

    @FXML
    private TextArea AttendanceReportTextView;

    @FXML
    private Button GenerateAttendaceReportButton;

    @FXML
    private Button SaveReportToPDF;

    @FXML
    private ComboBox hours;

    @FXML
    private ComboBox minutes;
    ObservableList<String> minitesList = FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14"
    																		,"15","16","17","18","19","20","21","22","23","24","25","26","27","28","29"
    																		,"30","31","32","33","34","35","36","37","38","39","40","41","42","43","44"
    																		,"45","46","47","48","49","50","51","52","53","54","55","56","57","58","59");
    
    ObservableList<String> hoursList = FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14"
																			,"15","16","17","18","19","20","21","22","23","24");
    
    ObservableList<String> SessionsLsiForCombo = FXCollections.observableArrayList();
    ObservableList<String> ChildrenList = FXCollections.observableArrayList();
    private  int arraySize;
    private Child[] child ;
   
    // This method is called when the FXML file is loaded
    public void initialize()
    {
    	DroppingButton.setUserData("Dropping");
    	PickUpButton.setUserData("PickUp");
     	SessionsCombo.setValue("Select Session");
    	SessionsCombo.setItems(SessionsLsiForCombo);
    	ChildrenListView.setItems(ChildrenList);
    	minutes.setItems(minitesList);
    	hours.setItems(hoursList);
	      
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
	      
	      // Check the number of Children Objects
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
	      		System.out.println("Child Not Found");
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
	    	  if (child[i].getFullName().equals(FullName)  && child[i].getStatus().equals("Attending"))
	    		  TempCheckr = 1;
	      }
	      
	      	if(TempCheckr == 0)
	      	{
	      		System.out.println("Child Not Found");
	      	}
	      	else
	      	{
	      		report += "      " + FullName +" PickUp and Drooping \n \n" ;
	      		
	      	   
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
	  	      
	  	       for (int i = 0; i < arraySize2; i++)
	  	      {
	  	    	   if(attendArray[i].getChildName().equals(FullName))
		      		report += attendArray[i].getTimeDifferenceinMinutes() + " Minutes off the Schedual on " +
		      				  attendArray[i].getDateOfDroppingOrPickUp()+" \n";
	  	      
 	   	      }
 	  	      AttendanceReportTextView.setText(report);
	      	} 
    }

    @FXML
    void SaveAction(ActionEvent event){
    	
    	
    	String FullName = ChildNameLabel.getText(); 
    	
      	Validations validator = new Validations();
    	boolean checker = true;
    	if(FullName.equals("null") || FullName.equals("Child Name"))
    	{
		   JOptionPane.showMessageDialog(null, "Please Select Child", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	if(!DroppingButton.isSelected() && !PickUpButton.isSelected())
    	{
		   JOptionPane.showMessageDialog(null, "Please Select Status", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	if(DatePicker.getValue() == null)
    	{
		   JOptionPane.showMessageDialog(null, "Please Select the Date", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	
    	if(minutes.getValue() == null)
    	{
		   JOptionPane.showMessageDialog(null, "Please Select Minutes time", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	
    	if(hours.getValue() == null)
    	{
		   JOptionPane.showMessageDialog(null, "Please Select hours time", "Error", JOptionPane.ERROR_MESSAGE);
		   checker = false;
    	}
    	
    	if(checker)
    	{
    			try {
					CalculateAndRegisterOffTime();
				} catch (NumberFormatException e) {
					   JOptionPane.showMessageDialog(null, "Numbers Error", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (ClassNotFoundException e) {
					   JOptionPane.showMessageDialog(null, "Class Not Found", "Error", JOptionPane.ERROR_MESSAGE);
  				} catch (ParseException e) {
 				   JOptionPane.showMessageDialog(null, "Cannot Parse To Numbers", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					   JOptionPane.showMessageDialog(null, "Something happend with the Storage files", "Error", JOptionPane.ERROR_MESSAGE);
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
	    	  if (temp.equals(SelectedSession) && child[i].getStatus().equals("Attending"))
	    	  {
 	  		    ChildrenList.add(child[i].getFullName()+"");
	    	  }
	      }	
    }
    
    
    public static String  CalculateTime (int minutes, int hours, String situation, String session) throws ParseException 
    { 
    
        int Start = 0;
        int end = 0;
        switch (session) {
            case "All Day":  Start = 8; end = 18;    // convert from pm ,am to 24 hours
                     break;
            case "Mornning": Start = 8; end = 12;
                     break;
            case "Lunch":  Start = 12; end = 13;
                     break;
            case "Afternoon":  Start = 13; end = 18;
                     break;
            case "Pre School":  Start = 9; end = 15;    // will deal 30 minutes of  15:30 later in this function
	                     break;
        }
        
		String TimeToProcess1 = "";
    	String TimeToProcess2 = "";
    	
    	if (situation.equals("Dropping"))
    	{
    		  TimeToProcess1 = Start +":00";
        	  TimeToProcess2 = hours +":"+ minutes;
    	}
    	else
    	{
    		if (end == 15)
    		{
    		  TimeToProcess2 = end +":30";
        	  TimeToProcess1 = hours +":"+ minutes;    			
    		}
    		else
    		{
      		  TimeToProcess2 = end +":00";
        	  TimeToProcess1 = hours +":"+ minutes;
    		}
    	}
        
    	 SimpleDateFormat timeForm = new SimpleDateFormat("HH:mm");
    	 Date dateform = timeForm.parse(TimeToProcess1); 
    	 Calendar call = Calendar.getInstance();
    	 call.setTime(dateform);
    	 
    	 SimpleDateFormat timeForm2 = new SimpleDateFormat("HH:mm");
    	 Date dateform2 = timeForm2.parse(TimeToProcess2); 
    	 Calendar call2 = Calendar.getInstance();
    	 call2.setTime(dateform2);

    	 return CalculateNumberOfDifferenceMinutes(call,call2)+"";

	    }

    public static long CalculateNumberOfDifferenceMinutes(Calendar callone, Calendar calltwo) {
        long firstTime = callone.getTimeInMillis();
        long secondTime = calltwo.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toMinutes(Math.abs(firstTime - secondTime));
    }
    
    private void CalculateAndRegisterOffTime() throws NumberFormatException, ParseException, IOException, ClassNotFoundException
    {
    	 String FullName = ChildNameLabel.getText();
         String session = null;
         int ChildID = 0;
           for (int i = 0; i < arraySize; i++)
   	      {
   	    	  if (child[i].getFullName().equals(FullName))
   	    	  {
   	    		  session =child[i].getSession()+"" ;
   	    		  ChildID = i;

    	    	  }
   	      }
   	      int min = Integer.parseInt(minutes.getValue()+"");
   	      int hou = Integer.parseInt(hours.getValue()+"");
   	      String Status = AttendaceButtonSelector.getSelectedToggle().getUserData()+"";

    	   
   	    int timeDiffenrece = Integer.parseInt(CalculateTime (min, hou, Status , session)); // calculate the Carer arriving time with the actual times of the child's session and return results in minutes 
   	   // detect the selected child object 
         int TempCheckr = 0;
        // Read through the array of objects
        for (int i = 0; i < arraySize; i++)
        {
      	  if (child[i].getFullName().equals(FullName))
      		  TempCheckr = i;
        }
        
   	    // Save to Object To file 
        FileInputStream readStream = new FileInputStream("PickUpAndDropping.dat");
        ObjectInputStream readingObjects = new ObjectInputStream(readStream);

   	 ToolsForHelp tool = new ToolsForHelp();
        int arraySize2 = tool.FilesManagerGetVlaue("PickUpAndDroppingCounter");
        arraySize2 = arraySize2 + 1;
        
        // Create a  array
        DroppingAndPickUp[] processArray = new DroppingAndPickUp [arraySize2];
        
        // Read the serialized objects from the file.
        for (int i = 0; i < arraySize2 - 1; i++)
        {
       	 processArray[i] = (DroppingAndPickUp) readingObjects.readObject();
         }
        
        // Create  object for the selected child
        LocalDate today = LocalDate.now();
         processArray [arraySize2 - 1] = new DroppingAndPickUp(timeDiffenrece, child[ChildID],DatePicker.getValue()+"",Status, today);
   	  
        // Save the array back
        FileOutputStream saveStream = new FileOutputStream("PickUpAndDropping.dat");
        ObjectOutputStream savingObjects =  new ObjectOutputStream(saveStream);
        
         // Write the serialized objects to the file.
   	     for (int i = 0; i < arraySize2; i++)
   	     {
   	   	  savingObjects.writeObject(processArray[i]);
   	     }
        savingObjects.close();     
     //increase and save the counter
   	 ToolsForHelp tool2 = new ToolsForHelp();
   	 tool2.FilesManagerIncrease("PickUpAndDroppingCounter");
    }
    
}
