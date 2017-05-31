import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class childrenProfilesController {

    @FXML
    private TextField FirstNameText;

    @FXML
    private TextField LastNAmeText;

    @FXML
    private ComboBox  SessionsCombo;

    @FXML
    private Button ChildNameSearchButton;

    @FXML
    private ListView  ChildrenListView;

    @FXML
    private Label ChildNameLabel;

    @FXML
    private TextArea InvoiceReportTextView;

    @FXML
    private Button SaveReportToPDF;

    @FXML
    private TextArea ChildDetailsTextView;
    
    ObservableList<String> SessionsLsiForCombo = FXCollections.observableArrayList();
    ObservableList<String> ChildrenList = FXCollections.observableArrayList();
 
   private  int arraySize;
   private Child[] child ;
   
   // This method is called when the FXML file is loaded
   public void initialize()
   {
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
    void ChildNameSearchAction(ActionEvent event) throws ClassNotFoundException, IOException {
    	
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
    void ChildSessionSearchListAction(MouseEvent event) throws ClassNotFoundException, IOException {
    	ChildNameLabel.setText(ChildrenListView.getSelectionModel().getSelectedItem()+"");  
      	GenerateProfileText();
      	ReadInvoicesOfSelctedChild(ChildNameLabel.getText());
       
    }

    @FXML
    void SaveReportToAction(ActionEvent event) {
    	WritableImage snapshot = ChildDetailsTextView.snapshot(new SnapshotParameters(), null);
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
	    	  temp = child[i].getSession();
	    	  if (temp.equals(SelectedSession)  && !child[i].getStatus().equals("Waiting"))
	    	  {
 	  		    ChildrenList.add(child[i].getFullName()+"");
	    	  }
	      }	
	      
    }
    
    public void GenerateProfileText() throws IOException, ClassNotFoundException
    {
    	 String report = "";
    	 String FullName = ChildNameLabel.getText() ;
	     int TempCheckr = 0;
	     int childId = 0;
	      
	     report += "         " + FullName +" Profile Details \n \n" ;
	      // Read through the array of objects
	      for (int i = 0; i < arraySize; i++)
	      {
	    	  if (child[i].getFullName().equals(FullName))
	    	  {
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
	  	     ChildDetailsTextView.setText(report);
   	
    }
    
    public void ReadInvoicesOfSelctedChild(String ChildName) throws ClassNotFoundException, IOException
    {
        FileInputStream readStream3 = new FileInputStream("RegistrationInvoices.dat");
        ObjectInputStream readingObjects3 = new ObjectInputStream(readStream3);
       
        ToolsForHelp tool = new ToolsForHelp();
        int invoiceNo = tool.FilesManagerGetVlaue("RegistrationInvoicesCounter");
       // Create a  array
        RegistrationInvoice[] invoice = new RegistrationInvoice[invoiceNo];
       
       // Read the serialized objects from the file.
       for (int i = 0; i < invoiceNo; i++)
       {
    	   invoice[i] = (RegistrationInvoice) readingObjects3.readObject();
       }
       readingObjects3.close();
       
       // Read the monthly Invoices
       
       FileInputStream readStream4 = new FileInputStream("MonthlyInvoices.dat");
       ObjectInputStream readingObjects4 = new ObjectInputStream(readStream4);
      
        int monthlyinvoiceNo = tool.FilesManagerGetVlaue("MonthlyInvoicesCounter");
      // Create a  array
      MonthlyInvoice[] monthlyinvoice = new MonthlyInvoice[monthlyinvoiceNo];
      
      // Read the serialized objects from the file.
      for (int i = 0; i < monthlyinvoiceNo; i++)
      {
    	  monthlyinvoice[i] = (MonthlyInvoice) readingObjects4.readObject();
      }
      readingObjects4.close();
       
       String report = "";
       
       // Read through the array of objects
       for (int i = 0; i < invoiceNo; i++)
       {
    	   if(invoice[i].GetChildName().equals(ChildName))
    	   {
    		   report += "\t The Registration Invoice Info \n";
    		   report += "Registration Fee : "+ invoice[i].GetRegistrationFee()+" \n"; 
    		   report += "Total Amount Paid: "+ invoice[i].GetTotalAmount()+" \n"; 
    		   report += "Registration Date: "+ invoice[i].GetDateOfIssue()+" \n";  
    	   }
       }	 
      // Get the Carer Personal Information
       for (int i = 0; i < arraySize; i++)
       {
     	  if (child[i].getFullName().equals(ChildName))
     	  {
    		   report += "Carer Full Name    : "+ child[i].getCarerName()+" \n"; 
    		   report += "Carer Email Address: "+ child[i].getCarerEmail()+" \n"; 
     	  }
       }
       report += "\t ---------------------------------\n";
       
       // Next display all child's monthly invoices
       report += "\t The Monthly Invoices Info \n";
       for (int i = 0; i < monthlyinvoiceNo; i++)
       {
    	   if(monthlyinvoice[i].GetChildName().equals(ChildName))
    	   {
    		   report += "Issued On : "+ monthlyinvoice[i].GetDateOfIssue()+" \n";  
    		   report += "Fine Amount: "+ monthlyinvoice[i].GetFineAmount()+" \n"; 
    		   report += "Total Fee : "+ monthlyinvoice[i].GetTotalAmount()+" \n"; 
    		   report += "Paid Or Not : "+ monthlyinvoice[i].GetStatus()+" \n"; 
    	       report += "\t ------------------------ \n";
    	   }
       }
       // Print the Report o the Text Area 
       InvoiceReportTextView.setText(report);
       
    }
}
