import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ManageSessionController {

    @FXML
    private ListView ChildrenListView;

    @FXML
    private ListView TeacherListView;

    @FXML
    private ListView SessionListView;

    @FXML
    private Button UpdatePricesButton;

    @FXML
    private TextField Cate1PerWeekText;

    @FXML
    private TextArea SessionInformation;

    @FXML
    private TextField Cate1PerDayText;

    @FXML
    private TextField Cate2PerWeekText;

    @FXML
    private TextField Cate2PerDayText;

    @FXML
    private TextField Cate3PerWeekText;

    @FXML
    private TextField Cate3PerDayText;

    ObservableList<String> ChildrenList = FXCollections.observableArrayList();
    ObservableList<String> SessionList = FXCollections.observableArrayList();
    ObservableList<String> TeachersList = FXCollections.observableArrayList();
    
    private String SessionName;
    private String SessionStart;
    private String SessionEnd;
    private Session[] sessionArray ;
    private int arraySize;
    
  public void initialize()
  {
	  SessionListView.setItems(SessionList);
	  ChildrenListView.setItems(ChildrenList);
	  TeacherListView.setItems(TeachersList);
	  
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
 	  FileInputStream readStream = new FileInputStream("Sessions.dat");
      ObjectInputStream readingObjects = new ObjectInputStream(readStream);
      ToolsForHelp tool = new ToolsForHelp();
      arraySize = tool.FilesManagerGetVlaue("SessionsCounter"); 
      
      // initilize our session's array a  array
      sessionArray = new  Session [arraySize];

	      
      // Read the serialized objects from the file.
      for (int i = 0; i < arraySize; i++)
      {
    	  sessionArray[i] = (Session) readingObjects.readObject();
      }
      readingObjects.close();

     // Clear the observable list to give it new information
    	SessionList.clear();

	      // Read through the array of objects
      for (int i = 0; i < arraySize; i++)
      {
    	  SessionList.add(sessionArray[i].getSessionName());
      }	  
  }
    
    @FXML
    void SelectSessionAction(MouseEvent event){
    	try {
			SessionSelected();
		} catch (ClassNotFoundException e) {
			   JOptionPane.showMessageDialog(null, "Class Not Found", "Error", JOptionPane.ERROR_MESSAGE);
 		} catch (IOException e) {
			   JOptionPane.showMessageDialog(null, "Something happend with the Storage files", "Error", JOptionPane.ERROR_MESSAGE);
 		}
    }

    @FXML
    void UpdatePricesAction(ActionEvent event){
		
    	// Get the selected session name
      	String SelectedSession = SessionListView.getSelectionModel().getSelectedItem() +"";
      	
      	if(SelectedSession.equals("null"))
      	{
 		   JOptionPane.showMessageDialog(null, "Please Select Session", "Error", JOptionPane.ERROR_MESSAGE);
      	}
      	else
      	{
      		try {
				Update();
			} catch (IOException e) {
			    JOptionPane.showMessageDialog(null, "Something happend with the Storage files", "Error", JOptionPane.ERROR_MESSAGE);
			}
      	}
    }
    
    private void Update() throws IOException
    {
      	String SelectedSession = SessionListView.getSelectionModel().getSelectedItem() +"";

   	    // Update the selected session from our arrayOf Object 
         for (int i = 0; i < arraySize; i++)
        {
           if(sessionArray[i].getSessionName().equals(SelectedSession))
           {   
        	   sessionArray[i].setAgeCate1PerDay(Double.parseDouble(Cate1PerDayText.getText()+""));
        	   sessionArray[i].setAgeCate2PerDay(Double.parseDouble(Cate2PerDayText.getText()+""));
        	   sessionArray[i].setAgeCate3PerDay(Double.parseDouble(Cate3PerDayText.getText()+""));  
        	   sessionArray[i].setAgeCate1PerWeek(Double.parseDouble(Cate1PerWeekText.getText()+""));  
        	   sessionArray[i].setAgeCate2PerWeek(Double.parseDouble(Cate2PerWeekText.getText()+""));  
        	   sessionArray[i].setAgeCate3PerWeek(Double.parseDouble(Cate3PerWeekText.getText()+""));  
           }
        }
         
        // Save the updated array of object to the file
	    FileOutputStream saveStream = new FileOutputStream("Sessions.dat");
	     ObjectOutputStream savingObjects =  new ObjectOutputStream(saveStream);
	     
 	      // Write the serialized objects to the file.
	       for (int i = 0; i < 5; i++)
	       {
	    	   savingObjects.writeObject(sessionArray[i]);
	       }  
	     savingObjects.close();
     

    	// sessionArray[0] = new Session ("All Day", 35.0, 145.0, 34.0, 140.0, 32.0, 135.0, "8am", "6pm");
    	// sessionArray[1] = new Session ("Mornning", 16.50, 66.0, 15.50, 63.0, 14.50, 60.0, "8am", "12pm");
    	// sessionArray[2] = new Session ("Lunch", 5.0, 20.0, 5.0, 20.0, 5.0, 20.0, "12pm", "1pm");
    	// sessionArray[3] = new Session ("Afternoon", 18.0, 72.0, 17.0, 69.0, 16.0, 66.0, "1pm", "6pm");
    	// sessionArray[4] = new Session ("Pre School", 25.0, 100.0, 24.0, 96.0, 23.0, 92.0, "9am", "3.30pm");
 	      // Save the array back
	 //    FileOutputStream saveStream = new FileOutputStream("Sessions.dat");
	 //    ObjectOutputStream savingObjects =  new ObjectOutputStream(saveStream);
	     
 	      // Write the serialized objects to the file.
	//       for (int i = 0; i < 5; i++)
	//       {
	//    	   savingObjects.writeObject(sessionArray[i]);
	//       }  
	 //     savingObjects.close();
    }
    public void SessionSelected() throws ClassNotFoundException, IOException
    {
    	// first we List all children that attend the selected session
    	FileInputStream readStream = new FileInputStream("Children.dat");
        ObjectInputStream readingObjects = new ObjectInputStream(readStream);

        ToolsForHelp tool = new ToolsForHelp();
        int arraySize2 = tool.FilesManagerGetVlaue("ChildrenCounter");
         
        // Create a  array
      	Child[] childArray = new  Child[arraySize2];

        // Read the serialized objects from the file.
        for (int i = 0; i < arraySize2; i++)
        {
        	childArray[i] = (Child) readingObjects.readObject();
        }
        readingObjects.close();

        // Clear the observable list to give it new information
      	ChildrenList.clear();
      	
      	String SelectedSession = SessionListView.getSelectionModel().getSelectedItem() + "";
  	    // Read through the array of objects
        for (int i = 0; i < arraySize2; i++)
        {
           if(childArray[i].getSession().equals(SelectedSession)  && childArray[i].getStatus().equals("Attending"))
      	     ChildrenList.add(childArray[i].getFullName());
        }
        ////////////////////////////////////////////////////////////
        // Second Display Teacher who work in the selected session
    	// Read and Store all Teacher objects to use them in the class functions
	      FileInputStream readStream2 = new FileInputStream("Teachers.dat");
	      ObjectInputStream readingObjects2 = new ObjectInputStream(readStream2);
	        
 	          int teacherNo = tool.FilesManagerGetVlaue("TeachersCounter");
	      
	      // Create a  array
 	         Teacher[] teacher  = new Teacher[teacherNo];
	      
	      // Read the serialized objects from the file.
	      for (int i = 0; i < teacherNo; i++)
	      {
	    	  teacher[i] = (Teacher) readingObjects2.readObject();
	      }
	      readingObjects2.close();
	    // Clear the list from the previous search 
	      TeachersList.clear();
	   //   list teacher(s) of the selected session
	 
	      for (int i = 0; i < teacherNo; i++)
	      {
	    	  if(teacher[i].getSession().equals(SelectedSession))
	    	  TeachersList.add(teacher[i].getFullName());
	      }
        
        /////////////////////////////////////////////////////////
        
    	// Third Display selected Session Information
        
        String information = "";
        
        for (int i = 0; i < arraySize; i++)
        {
      	 if(sessionArray[i].getSessionName().equals(SelectedSession))
      	 {
       		information += "Session Name : "+sessionArray[i].getSessionName()+" \n";
      		information += "Start From   : "+sessionArray[i].getSessionStartTime()+" \n";
      		information += "End At       \t: "+sessionArray[i].getSessionEndTime()+" \n\n";
      		information += "   \t\t   Prices Per Day    \n";
      		information += "Birth to 2 Years Age Category :"+sessionArray[i].getAgeCate1PerDay()+" \n";
      		information += "2 to 3 Years Age Category     :"+sessionArray[i].getAgeCate2PerDay()+" \n";
      		information += "3 Years and Over Age Category :"+sessionArray[i].getAgeCate3PerDay()+" \n\n";
      		information += "   \t\t   Prices Per Week    \n";
      		information += "Birth to 2 Years Age Category :"+sessionArray[i].getAgeCate1PerWeek()+" \n";
      		information += "2 to 3 Years Age Category     :"+sessionArray[i].getAgeCate2PerWeek()+" \n";
      		information += "3 Years and Over Age Category :"+sessionArray[i].getAgeCate3PerWeek()+" \n";
      		
      		// Set the text boxes with the original price values so it easier to update any of them
      		Cate1PerDayText.setText(sessionArray[i].getAgeCate1PerDay()+"");
      		Cate2PerDayText.setText(sessionArray[i].getAgeCate2PerDay()+"");
      		Cate3PerDayText.setText(sessionArray[i].getAgeCate3PerDay()+"");
      		Cate1PerWeekText.setText(sessionArray[i].getAgeCate1PerWeek()+"");
      		Cate2PerWeekText.setText(sessionArray[i].getAgeCate2PerWeek()+"");
      		Cate3PerWeekText.setText(sessionArray[i].getAgeCate3PerWeek()+"");


      		
      	 }
        }	 
        SessionInformation.setText(information);
    }
}
