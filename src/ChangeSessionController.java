import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ChangeSessionController {

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
	    private Label ChildNameLabelForInstantChange;

	    @FXML
	    private ComboBox NewSessionCombo;

	    @FXML
	    private ListView ChangeSessionWatingList;

	    @FXML
	    private ListView DaysLeftList;

	    @FXML
	    private Button SetToChangeInOneMontheButton;

	    @FXML
	    private Button ChangeSessionNowButton;
    
    ObservableList<String> ChildrenList = FXCollections.observableArrayList();
    ObservableList<String> SessionsLsiForCombo = FXCollections.observableArrayList();
    ObservableList<String> ChangeSessionList = FXCollections.observableArrayList();
    ObservableList<String> DueTimeList = FXCollections.observableArrayList();

    private  int arraySize;
    private Child[] child ;
    private  ChangeSessionWaitingList[] ChangeSessionChildrenArray;
    private int ordersArray;
    
    // This method is called when the FXML file is loaded
    public void initialize() throws IOException, ClassNotFoundException 
    {
 	    SessionsCombo.setValue("Select Session");
 	   	SessionsCombo.setItems(SessionsLsiForCombo);
 	    NewSessionCombo.setValue("Select Session");
 	    NewSessionCombo.setItems(SessionsLsiForCombo); 	   	
     	ChildrenListView.setItems(ChildrenList);
     	ChangeSessionWatingList.setItems(ChangeSessionList);
     	DaysLeftList.setItems(DueTimeList);
     	
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
       
       //////////////////////////////////////
     //  Read the Change session waiting list
       
       FileInputStream readStream3 = new FileInputStream("ChangeSessionList.dat");
       ObjectInputStream readingObjects3 = new ObjectInputStream(readStream3);

       ordersArray = tool.FilesManagerGetVlaue("ChangeSessionListCounter");
        
       // Create a  array
       ChangeSessionChildrenArray = new ChangeSessionWaitingList [ordersArray];
       
       // Read the serialized objects from the file.
       for (int i = 0; i < ordersArray; i++)
       {
      	 ChangeSessionChildrenArray[i] = (ChangeSessionWaitingList) readingObjects3.readObject();
       }
       
       long DueTimeInDays;
       for (int i = 0; i < ordersArray; i++)
       { 
    	   if (StillExsists(ChangeSessionChildrenArray[i].getChildName()))
    	   {
    	   DueTimeInDays =   CaculateDifferenceInDays(ChangeSessionChildrenArray[i].getDateOfSet());
         ChangeSessionList.add(ChangeSessionChildrenArray[i].getChildName());
         DueTimeList.add(DueTimeInDays+ " Days Left");
    	   }
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
    void ChildsListViewAction(MouseEvent event) {
    	ChildNameLabelForInstantChange.setText(ChangeSessionWatingList.getSelectionModel().getSelectedItem()+"");
    	System.out.println("kkkkkkk");
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
	    	  if (temp.equals(SelectedSession) && child[i].getStatus().equals("Attending") )
	    	  {
 	  		    ChildrenList.add(child[i].getFullName()+"");
	    	  }
	      }	
	      
    }

    @FXML
    void SetToChangeInOneMontheAction(ActionEvent event) throws IOException, ClassNotFoundException {
    	
        String FullName = ChildNameLabel.getText(); 
      	if(FullName.equals("null") || FullName.equals("Child Name"))
    	{
		   JOptionPane.showMessageDialog(null, "Please Select Child", "Error", JOptionPane.ERROR_MESSAGE);
    	}
      	if(NewSessionCombo.getValue().equals("Select Session"))
    	{
		   JOptionPane.showMessageDialog(null, "Please Select the new Session", "Error", JOptionPane.ERROR_MESSAGE);
    	}
     	else
     	{
     		SetToChange();
     	}
    }

    @FXML
    void ChangeSessionNowAction(ActionEvent event)  {
    	
        
        String ChildToChange = ChildNameLabelForInstantChange.getText();
     	if(ChildToChange.equals("null") || ChildToChange.equals("Child Name"))
 	  	{
 			   JOptionPane.showMessageDialog(null, "Please Select Child", "Error", JOptionPane.ERROR_MESSAGE);
 	  	}
 	   	else
 	   	{
 	   		try {
 				ChangeNow();
 			} catch (IOException e) {
 				   JOptionPane.showMessageDialog(null, "File Storage Error", "Error", JOptionPane.ERROR_MESSAGE);
 			}
 	   	} 
    }
    
    public void ChangeNow() throws IOException
    {
        String ChildToChangeTheirSession = ChildNameLabelForInstantChange.getText();
        
 		 // Search for the child
 	   int childfounder = -1;
 		   for (int i = 0; i < arraySize; i++)
 		   {
  	 	 	  if (child[i].getFullName().equals(ChildToChangeTheirSession))
 		 	  {
  	 	 		childfounder = i;
 	 		   }
 		 	}	   
 		   String NewSession = null;
 		   int ArrayPossition = 0;
 		   for (int i = 0; i < ordersArray; i++)
 		   {
  	 	 	  if (ChangeSessionChildrenArray[i].getChildName().equals(ChildToChangeTheirSession))
 		 	  {
  	 	 		NewSession = ChangeSessionChildrenArray[i].getNewSession();
  	 	 	    ArrayPossition = i;
 	 		   }
 		 	}	
 		   
 		   // Now edit the array of objects
 		   child[childfounder].setSession(NewSession);
  		      		 	   
 		   // Save the array back
 		      FileOutputStream saveStream = new FileOutputStream("Children.dat");
 		      ObjectOutputStream savingObjects =  new ObjectOutputStream(saveStream);
 		      
 		      // Write the serialized objects to the file.
 		      for (int i = 0; i < arraySize; i++)
 		      {
 		    	  savingObjects.writeObject(child[i]);
 		      }
 		      savingObjects.close();	
 	 		
 		     // Delete this request from the change session waiting list
 		      
 		      if(ArrayPossition < ordersArray - 1) // not the last object in the array
 		      {
 		    	 for (int i = 0; i < ordersArray - 1; i++)
 	 	 		   {
 	 	  	 	 	  if (i == ArrayPossition)
 	 	 		 	  {
 	 	  	 	 		  for(int j = i; j < ordersArray - 1; j ++)
 	 	  	 	 		  {
 	  	 	  	 	 		  ChangeSessionChildrenArray[j] = ChangeSessionChildrenArray[j+1]; 
 	 	  	 	 		  }
 	 	  	 	 	   break;
 	 	 	 		   }
 	 	 		 	}  
 		      }
 		      // save the array back to the change session waiting list file
  		      FileOutputStream saveStreami = new FileOutputStream("ChangeSessionList.dat");
 		      ObjectOutputStream savingObjectsi =  new ObjectOutputStream(saveStreami);
 		      
 		      // Write the serialized objects to the file.
 		      for (int i = 0; i < ordersArray - 1; i++)
 		      {
 		    	  savingObjectsi.writeObject(ChangeSessionChildrenArray[i]);
 		      }
 		      savingObjectsi.close();	
 		      
 		     ToolsForHelp tool = new ToolsForHelp();
 		     tool.FilesManagerDecrease("ChangeSessionListCounter");
    }
    
    public void SetToChange() throws IOException, ClassNotFoundException
    {
    	String FullName = ChildNameLabel.getText();
	     int TempCheckr = 0;
	     // Read through the array of objects and find the selected child's object
	     for (int i = 0; i < arraySize; i++)
	     {
	   	  if (child[i].getFullName().equals(FullName))
	   		  TempCheckr = i;
	     }
	     
	      // Save the Object To file 
	      FileInputStream readStream = new FileInputStream("ChangeSessionList.dat");
	      ObjectInputStream readingObjects = new ObjectInputStream(readStream);

	      ToolsForHelp tool = new ToolsForHelp();
	      int arraySize2 = tool.FilesManagerGetVlaue("ChangeSessionListCounter"); 
	      arraySize2 = arraySize2 + 1;
	      
	      // Create a  array
	      ChangeSessionWaitingList[] processArray = new ChangeSessionWaitingList [arraySize2];
	      
	      // Read the serialized objects from the file.
	      for (int i = 0; i < arraySize2 - 1; i++)
	      {
	    	  processArray[i] = (ChangeSessionWaitingList) readingObjects.readObject();
	      }
	      LocalDate today = LocalDate.now();
	      LocalDate nextMonth = today.plus(1, ChronoUnit.MONTHS);
	      processArray[arraySize2 - 1] = new ChangeSessionWaitingList(nextMonth, child[TempCheckr],NewSessionCombo.getValue()+"");
	  
	      // Save the array back
	      FileOutputStream saveStream = new FileOutputStream("ChangeSessionList.dat");
	      ObjectOutputStream savingObjects =  new ObjectOutputStream(saveStream);
	      
	      // Write the serialized objects to the file.
	      for (int i = 0; i < arraySize2; i++)
	      {
	    	  savingObjects.writeObject(processArray[i]);
	      }
	     
	      savingObjects.close();
	      
       //increase and save the counter
	      ToolsForHelp tool2 = new ToolsForHelp();
       tool2.FilesManagerIncrease("ChangeSessionListCounter");
    }
    
    public boolean StillExsists(String name)
    {
        for (int i = 0; i < arraySize; i++)
        {
        	if(child[i].getFullName().equals(name))
        		return true;
         }
        return false;
    }
    
    public long CaculateDifferenceInDays(LocalDate ChangingSettingDate)
    {
   	   LocalDate currentDay = LocalDate.now();
  	   return ChronoUnit.DAYS.between(currentDay, ChangingSettingDate);
      }
}
