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

import javax.mail.MessagingException;
import javax.swing.JOptionPane;

import com.itextpdf.text.DocumentException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class WithdrawChildController {
	
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
	    private Label ChildNameLabelForInstantWithdraw;

	    @FXML
	    private Button WithdrawNowButton;

	    @FXML
	    private Button SetToWithdrawInOneMonthButton;

	    @FXML
	    private ListView ChildrenWaitingListView;

	    @FXML
	    private ListView DaysLeftListView;

    
    ObservableList<String> ChildrenList = FXCollections.observableArrayList();
    ObservableList<String> SessionsLsiForCombo = FXCollections.observableArrayList();
    ObservableList<String> WitdrawList = FXCollections.observableArrayList();
    ObservableList<String> DueTimeList = FXCollections.observableArrayList();

    private  int arraySize;
    private Child[] child ;
    
    // This method is called when the FXML file is loaded
    public void initialize() 
    {
 	    SessionsCombo.setValue("Select Session");
 	   	SessionsCombo.setItems(SessionsLsiForCombo);
     	ChildrenListView.setItems(ChildrenList);
     	ChildrenWaitingListView.setItems(WitdrawList);
     	DaysLeftListView.setItems(DueTimeList);
     	
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
       //  Read the withdraw waiting list
         
         // Save the Object To file 
         FileInputStream readStream3 = new FileInputStream("WithdrawChildrenList.dat");
         ObjectInputStream readingObjects3 = new ObjectInputStream(readStream3);

         int arraySize2 = tool.FilesManagerGetVlaue("WithdrawChildrenListCounter");
          
         // Create a  array
         WithdrawWaitngList[] processArray = new WithdrawWaitngList [arraySize2];
         
         // Read the serialized objects from the file.
         for (int i = 0; i < arraySize2; i++)
         {
       	  processArray[i] = (WithdrawWaitngList) readingObjects3.readObject();
         }
         
         long DueTimeInDays;
         for (int i = 0; i < arraySize2; i++)
         { 
             for (int j = 0; j < arraySize; j++)
             {
           	  if(child[j].getStatus().equals("Attending") && child[j].getFullName().equals(processArray[i].getChildName()))
           	  {
             	   System.out.println("bis bis bis");
              	   DueTimeInDays = CaculateDifferenceInDays(processArray[i].getDateOfSet());
                   WitdrawList.add(processArray[i].getChildName());
                   DueTimeList.add(DueTimeInDays+ " Days Left"); 
           	  }
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
    
    @FXML
    void ChildsListViewAction(MouseEvent event) {
    	ChildNameLabelForInstantWithdraw.setText(ChildrenWaitingListView.getSelectionModel().getSelectedItem()+"");
    }

    @FXML
    void SetToWithdrawInOneMonthAction(ActionEvent event){
    	
     String FullName = ChildNameLabel.getText(); 
    	
      	if(FullName.equals("null") || FullName.equals("Child Name"))
    	{
		   JOptionPane.showMessageDialog(null, "Please Select Child", "Error", JOptionPane.ERROR_MESSAGE);
    	}
     	else
     	{
     		try {
				SetToWithdraw();
			} catch (ClassNotFoundException e) {
				   JOptionPane.showMessageDialog(null, "Class Not Found", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				   JOptionPane.showMessageDialog(null, "Something happend with the storage files", "Error", JOptionPane.ERROR_MESSAGE);
 			}
     	}

    	 
    }
    

    @FXML
    void WithdrawNowAction(ActionEvent event){
      
       String ChildToWithdraw = ChildNameLabelForInstantWithdraw.getText();
    	if(ChildToWithdraw.equals("null") || ChildToWithdraw.equals("Child Name"))
	  	{
			   JOptionPane.showMessageDialog(null, "Please Select Child", "Error", JOptionPane.ERROR_MESSAGE);
	  	}
	   	else
	   	{
	   		
				try {
					WithdrawNow();
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
    
    public void WithdrawNow() throws IOException, ClassNotFoundException, DocumentException, MessagingException
    {
        String ChildToWithdraw = ChildNameLabelForInstantWithdraw.getText();
        int childPosition = 0;
    	for (int i = 0; i < arraySize; i++)
   	   {
    	 	  if (child[i].getFullName().equals(ChildToWithdraw))
   	 	  {
    	 		child[i].setStatus("Left");
    	 		childPosition = i;
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
   	      
   	      // Generate Leaving invoice 
   	      
   	      // Get the refundable fees from registration invoice
   	      
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
      double refund = 0;
      for (int i = 0; i < invoiceNo; i++)
      {
    	  if(invoice[i].GetChildName().equals(ChildToWithdraw))
    	  {
    		  refund = invoice[i].GetTotalAmount() - invoice[i].GetRegistrationFee();
    	  }
      }
      
      // Time to create the leaving invoice
      
 	   	 FileInputStream readStream2 = new FileInputStream("LeavingInvoices.dat");
 	     ObjectInputStream readingObjects2 = new ObjectInputStream(readStream2);
 	     
   	     int arraySize3 = tool.FilesManagerGetVlaue("LeavingInvoicesCounter");
 	     arraySize3 = arraySize3 + 1;
 	     
 	     // Create a  array
 	     LeavingInvoice[] array = new LeavingInvoice[arraySize3];
 	     
 	     // Read the serialized objects from the file.
 	     for (int i = 0; i < arraySize3 - 1; i++)
 	     {
 	   	  array[i] = (LeavingInvoice) readingObjects2.readObject();
 	     }
 	
 	     LocalDate today = LocalDate.now();
 	     array[arraySize3 - 1] = new LeavingInvoice();
 	     array[arraySize3 - 1].SetChild(child[childPosition]);
 	     array[arraySize3 - 1].SetDateOfIssue(today);
 	     array[arraySize3 - 1].SetTotalAmount(refund);

 	    
 	    
 	     readingObjects2.close();
 	     // Save the array back
 	     FileOutputStream saveStream2 = new FileOutputStream("LeavingInvoices.dat");
 	     ObjectOutputStream savingObjects2 =  new ObjectOutputStream(saveStream2);
 	     
 	     // Write the serialized objects to the file.
 	     for (int i = 0; i < arraySize3; i++)
 	     {
 	   	  savingObjects2.writeObject(array[i]);
 	     }
 	     savingObjects2.close();
 	     
 	   //increase and save the counter
 	    tool.FilesManagerIncrease("LeavingInvoicesCounter");	
 	    
	    // Send it to carer
	     String ReportForEmail [] = new String[7];
	     ReportForEmail [0]= "\t Leaving Invoce for your son "+child[childPosition].getFullName();
	     ReportForEmail [1]= " Issued on "+today;
	     ReportForEmail [2]= " Refundable Amount pay back "+array[arraySize3 - 1].GetTotalAmount();
	     ReportForEmail [3]= "-------------------------------------------------------";
 	     ReportForEmail [4]= " We wish all the best to you and your young child ";
	     ReportForEmail [5]= " Keep us updated with their achivments ";
	     ReportForEmail	[6]= child[childPosition].getFullName()+" We always proud of you!";
	     
		    // Prepare PDF to be send
		    Email servise = new Email();
		    servise.CreatePDF(ReportForEmail); 
		    servise.SendPDF(child[childPosition].getCarerEmail());
    }
    
    public long CaculateDifferenceInDays(LocalDate WithdrawSettingDate)
    {
  	   LocalDate currentDay = LocalDate.now();
 	   return ChronoUnit.DAYS.between(currentDay, WithdrawSettingDate);
      }
    
    public void SetToWithdraw() throws ClassNotFoundException, IOException
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
	      FileInputStream readStream = new FileInputStream("WithdrawChildrenList.dat");
	      ObjectInputStream readingObjects = new ObjectInputStream(readStream);

	      ToolsForHelp tool = new ToolsForHelp();
	      int arraySize2 = tool.FilesManagerGetVlaue("WithdrawChildrenListCounter");
	      arraySize2 = arraySize2 + 1;
	      
	      // Create a  array
	      WithdrawWaitngList[] processArray = new WithdrawWaitngList [arraySize2];
	      
	      // Read the serialized objects from the file.
	      for (int i = 0; i < arraySize2 - 1; i++)
	      {
	    	  processArray[i] = (WithdrawWaitngList) readingObjects.readObject();
	      }
	      
	      LocalDate today = LocalDate.now();
	      LocalDate nextMonth = today.plus(1, ChronoUnit.MONTHS);
	      System.out.println(nextMonth);
	      processArray[arraySize2 - 1] = new WithdrawWaitngList(nextMonth, child[TempCheckr]);
	  
	      // Save the array back
	      FileOutputStream saveStream = new FileOutputStream("WithdrawChildrenList.dat");
	      ObjectOutputStream savingObjects =  new ObjectOutputStream(saveStream);
	      
	      // Write the serialized objects to the file.
	      for (int i = 0; i < arraySize2; i++)
	      {
	    	  savingObjects.writeObject(processArray[i]);
	      }
	     
	      savingObjects.close();
	      
        //increase and save the counter
	    ToolsForHelp tool2 = new ToolsForHelp();
        tool2.FilesManagerIncrease("WithdrawChildrenListCounter");
    }
    
}
