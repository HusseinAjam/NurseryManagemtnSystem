import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class StaffProfilesController {

    @FXML
    private Label NameLabel;

    @FXML
    private TextArea ChildDetailsTextView;

    @FXML
    private ComboBox JobsCombo;

    @FXML
    private ListView StaffListView;
    
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

     private void StartTheScene() throws ClassNotFoundException, IOException
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
    void SearchListAction(MouseEvent event) {
  		NameLabel.setText(StaffListView.getSelectionModel().getSelectedItem()+"");  
  		
		////////////////////////////////////////
		/// Generate Profiles
		String report = "";
		String FullName = StaffListView.getSelectionModel().getSelectedItem()+"";
		String JobTitle = JobsCombo.getValue()+"";
		int TempCheckr = 0;
 		
		report += "         " + FullName +" Profile Details \n \n" ;
		if(JobTitle.equals("Teachers"))
		{
			// Read through the array of objects
			for (int i = 0; i < teacherNo; i++)
			{
				if (teacher[i].getFullName().equals(FullName))
				{
					report += "Birthday Date : " + teacher[i].getBirthday()+"\n" ;
	 				report += "Address       : " + teacher[i].getAddress()+"\n" ;
					report += "Gender        : " + teacher[i].getGender()+"\n" ;
	 				report += "Session       : " + teacher[i].getSession()+"\n" ;
				}
			}
		}
		else if(JobTitle.equals("Admins"))
		{
			// Read through the array of objects
			for (int i = 0; i < AdminsNo; i++)
			{
				if (admin[i].getFullName().equals(FullName))
				{
					report += "Birthday Date : " + admin[i].getBirthday()+"\n" ;
	 				report += "Address       : " + admin[i].getAddress()+"\n" ;
					report += "Gender        : " + admin[i].getGender()+"\n" ;
	 				report += "UserName      : " + admin[i].getUserName()+"\n" ;
	 				report += "PassWord      : " + admin[i].getPassWord()+"\n" ;

				}
			}
		}
		else if(JobTitle.equals("Security Guards"))
		{
			// Read through the array of objects
			for (int i = 0; i < securityGuardsNo; i++)
			{
				if (securityGuards[i].getFullName().equals(FullName))
				{
					report += "Birthday Date : " + securityGuards[i].getBirthday()+"\n" ;
	 				report += "Address       : " + securityGuards[i].getAddress()+"\n" ;
					report += "Gender        : " + securityGuards[i].getGender()+"\n" ;
	 				report += "Shift         : " + securityGuards[i].getShift()+"\n" ;
				}
			}
		}
		else if(JobTitle.equals("House Keeping Staff"))
		{
			// Read through the array of objects
			for (int i = 0; i < houseKeepingNo; i++)
			{
				if (houseKeeping[i].getFullName().equals(FullName))
				{
					report += "Birthday Date : " + houseKeeping[i].getBirthday()+"\n" ;
	 				report += "Address       : " + houseKeeping[i].getAddress()+"\n" ;
					report += "Gender        : " + houseKeeping[i].getGender()+"\n" ;
	 				report += "Duty          : " + houseKeeping[i].getDuty()+"\n" ;
				}
			}
		}		
		ChildDetailsTextView.setText(report);

}
}

 
