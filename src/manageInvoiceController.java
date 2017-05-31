
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

public class manageInvoiceController {

	  @FXML
	    private ListView ChildListView;

	    @FXML
	    private ListView DueListView;

	    @FXML
	    private Button CalculateTotalButton;

	    @FXML
	    private Label TotalAmountLabel;

	    @FXML
	    private Button SetAsPaidButton;

	    @FXML
	    private ListView RegistrationInvoicesListView;

	    @FXML
	    private ListView LeavingInvoicesListView;

	    @FXML
	    private ListView MonthlyInvicesListView;
	    
	    @FXML
	    private Label ChildNameLabel;
    
    ObservableList<String> LeavingInvoices = FXCollections.observableArrayList();
    ObservableList<String> RegisterationInvoices = FXCollections.observableArrayList();
    ObservableList<String> ChildrenList = FXCollections.observableArrayList();
    ObservableList<String> PaidMonthlyInvoices = FXCollections.observableArrayList();
    ObservableList<String> UnpaidMonthlyInvoices = FXCollections.observableArrayList();
    
    private int StudentArraySize;
    private int MonthlyInvoicesArraySize;
    private int LeavingInvoicesArraySize;
    private int RegistrationInvoicesArraySize;

    private Child[] child ;
    private RegistrationInvoice[] registrationInvoice ;
    private LeavingInvoice[] leavingInvoice ;
    private MonthlyInvoice[] monthlyInvoice ;
    
    // This method is called when the FXML file is loaded
    public void initialize() 
    {
     	LeavingInvoicesListView.setItems(LeavingInvoices);
    	MonthlyInvicesListView.setItems(PaidMonthlyInvoices);
    	DueListView.setItems(UnpaidMonthlyInvoices);
    	RegistrationInvoicesListView.setItems(RegisterationInvoices);
    	ChildListView.setItems(ChildrenList);
     
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
     	// Read children list
        FileInputStream readStream = new FileInputStream("Children.dat");
        ObjectInputStream readingObjects = new ObjectInputStream(readStream);

         ToolsForHelp tool = new ToolsForHelp();
         StudentArraySize = tool.FilesManagerGetVlaue("ChildrenCounter");
         
        // Create a  array
        child = new Child[StudentArraySize];
        
        // Read the serialized objects from the file.
        for (int i = 0; i < StudentArraySize; i++)
        {
      	  child[i] = (Child) readingObjects.readObject();
        }
        readingObjects.close();
            	
     	// Read Registration Invoices
        
       FileInputStream readStream2 = new FileInputStream("RegistrationInvoices.dat");
       ObjectInputStream readingObjects2 = new ObjectInputStream(readStream2);

       RegistrationInvoicesArraySize = tool.FilesManagerGetVlaue("RegistrationInvoicesCounter");
        
       // Create a  array
       registrationInvoice = new RegistrationInvoice[RegistrationInvoicesArraySize];
       
       // Read the serialized objects from the file.
       for (int i = 0; i < RegistrationInvoicesArraySize; i++)
       {
     	  registrationInvoice[i] = (RegistrationInvoice) readingObjects2.readObject();
       }
       readingObjects2.close();  
       
 		/////////////////////////////////////  
 	// Read Leaving Invoices
  
 		FileInputStream readStream3 = new FileInputStream("LeavingInvoices.dat");
 		ObjectInputStream readingObjects3 = new ObjectInputStream(readStream3);
 		
 		LeavingInvoicesArraySize = tool.FilesManagerGetVlaue("LeavingInvoicesCounter");
 		 
 		// Create a  array
 		leavingInvoice = new LeavingInvoice[LeavingInvoicesArraySize];
 		
 		// Read the serialized objects from the file.
 		for (int i = 0; i < LeavingInvoicesArraySize; i++)
 		{
 			leavingInvoice[i] = (LeavingInvoice) readingObjects3.readObject();
 		}
 		readingObjects3.close();
 		
 		/////////////////////////////////////  
 		// Read Monthly Invoices
 	 
 			FileInputStream readStream4 = new FileInputStream("MonthlyInvoices.dat");
 			ObjectInputStream readingObjects4 = new ObjectInputStream(readStream4);
 			
 			MonthlyInvoicesArraySize = tool.FilesManagerGetVlaue("MonthlyInvoicesCounter");
 			 
 			// Create a  array
 			monthlyInvoice = new MonthlyInvoice[MonthlyInvoicesArraySize];
 			
 			// Read the serialized objects from the file.
 			for (int i = 0; i < MonthlyInvoicesArraySize; i++)
 			{
 				monthlyInvoice[i] = (MonthlyInvoice) readingObjects4.readObject();
 			}
 			readingObjects4.close();
 			
 			// Now After Storing All Required Objects in arrays of Objects, Time to set the List Views with these arays
 			
 			// Registration ListView
 			  for (int i = 0; i < RegistrationInvoicesArraySize; i++)
 		      {
 		    	 RegisterationInvoices.add(registrationInvoice[i].GetChildName() + "\t On: "+registrationInvoice[i].GetDateOfIssue() +"\t Total Amount: "+registrationInvoice[i].GetTotalAmount());
 		      }
 			// Leaving Invoices
 			  for (int i = 0; i < LeavingInvoicesArraySize; i++)
 				{
 			    	 LeavingInvoices.add(leavingInvoice[i].GetChildName() + "\t On: "+leavingInvoice[i].GetDateOfIssue() +"\t Total Amount: "+leavingInvoice[i].GetTotalAmount());
  				}
 			 // Paid Monthly Invoices
 			  for (int i = 0; i < MonthlyInvoicesArraySize; i++)
 				{
 				  if(monthlyInvoice[i].GetStatus()) // status paid "true"
 				  {
 					  double total = monthlyInvoice[i].GetTotalAmount() +  monthlyInvoice[i].GetFineAmount() ;
 			    	 PaidMonthlyInvoices.add(monthlyInvoice[i].GetChildName() + "\t On: "+monthlyInvoice[i].GetDateOfIssue() +"\t Total Amount Paid: "+ total);
 				  }
  				}
 			  // Unpaid yet Monthly Invoice
 			  int DaysLeft = 0;
 			  double Amount = 0;
 			  for (int i = 0; i < MonthlyInvoicesArraySize; i++)
 				{
 				  if(!monthlyInvoice[i].GetStatus()) // status Unpaid "false"
 				  {
 					  ChildrenList.add(monthlyInvoice[i].GetChildName());
 					  DaysLeft = CalculateNoOfDaysLeftToPay(monthlyInvoice[i].GetChildName());
 					  Amount  = monthlyInvoice[i].GetTotalAmount()+monthlyInvoice[i].GetFineAmount();
 					  UnpaidMonthlyInvoices.add(Amount+" Pounds \t \t"+DaysLeft+" Day");
 				  }
  				}
 			  
    }
    
    @FXML
    void ChildSelectedAction(MouseEvent event) {
  		ChildNameLabel.setText(ChildListView.getSelectionModel().getSelectedItem()+"");  	
    }


    @FXML
    void CalculateTotalAction(ActionEvent event) {
    	
    	double total = 0;
    	String childName = ChildNameLabel.getText()+"";     	
    	if(childName.equals("null") || childName.equals("Child Name"))
    	{
		   JOptionPane.showMessageDialog(null, "Please Select Child", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    	else
    	{
    		if(CalculateNoOfDaysLeftToPay(childName) < 0)  // Late payment
    		{
    			  for (int i = 0; i < MonthlyInvoicesArraySize; i++)
  				{
  				  if(monthlyInvoice[i].GetChildName().equals(childName) && !monthlyInvoice[i].GetStatus()) 
  				  {
  					total = monthlyInvoice[i].GetTotalAmount()+monthlyInvoice[i].GetFineAmount();
  					total = total +  (total / 100)*10; // Adding 10 percent for late paymant
  					TotalAmountLabel.setText(total + " Pounds");
   					break;
  				  }
  				}
    		}
    		else // come in time, no late payment
    		{
  			  for (int i = 0; i < MonthlyInvoicesArraySize; i++)
				{
 				  if(monthlyInvoice[i].GetChildName().equals(childName) && !monthlyInvoice[i].GetStatus()) 
				  {
					total = monthlyInvoice[i].GetTotalAmount()+monthlyInvoice[i].GetFineAmount();
 					TotalAmountLabel.setText(total + " Pounds");
					break;
				  }
				}
    		}
    	}
    }

    
    @FXML
    void SetAsPaidAction(ActionEvent event) {
    	    	
      	if(TotalAmountLabel.getText().equals("Total Amount"))
    	{
		   JOptionPane.showMessageDialog(null, "Please Check total amount first!", "Error", JOptionPane.ERROR_MESSAGE);
     	}
      	else
      	{   
	        try {
				ChangeStatusToPaid();
			} catch (IOException e) {
				   JOptionPane.showMessageDialog(null, "Something wrong happend with the Storage files", "Error", JOptionPane.ERROR_MESSAGE);
			}	
      	}

    }
    
    public void ChangeStatusToPaid() throws IOException
    {
        String childName = ChildNameLabel.getText(); 
 		  for (int i = 0; i < MonthlyInvoicesArraySize; i++)
			{
			  if(!monthlyInvoice[i].GetStatus() && monthlyInvoice[i].GetChildName().equals(childName)) // status paid "false"
			  {
				  monthlyInvoice[i].SetStatus(true);
				  
			  }
		    }
		    // Write the Invoice array to the File 
		     FileOutputStream saveStream3 = new FileOutputStream("MonthlyInvoices.dat");
		     ObjectOutputStream savingObjects3 =  new ObjectOutputStream(saveStream3);
		     // Write the serialized objects to the file.
		     for (int i = 0; i < MonthlyInvoicesArraySize; i++)
		     {
		   	  savingObjects3.writeObject(monthlyInvoice[i]);
		     }
		     savingObjects3.close();
    }
    
   
    public int CalculateNoOfDaysLeftToPay(String childName)
    {  		
    	int ChildPossition = -1;
        for (int i = 0; i < StudentArraySize; i++)
        {
        	if(child[i].getFullName().equals(childName))
        		ChildPossition = i;	 
        }
        
        // check if today is after 7 Working Days of invoice generating day
        
        int DaysLeft = 0;
         for (int i = 0; i < MonthlyInvoicesArraySize; i++)
		{
		  if(monthlyInvoice[i].GetChildName().equals(childName) && !monthlyInvoice[i].GetStatus())  // Unpaid invoice for the selected child
		  {
			  DaysLeft =  CalculateperiodWithoutWeekends (monthlyInvoice[i].GetDateOfIssue());
			  break;
		  }
		}
         return DaysLeft;
    }
    
	public int CalculateperiodWithoutWeekends (LocalDate invoiceDate){
        LocalDate today = LocalDate.now();
	    LocalDate After7WorkingDays = invoiceDate; 
	    
	    int workDaysCounter = 0;
        do {
        	After7WorkingDays = After7WorkingDays.plusDays(1);
              if (After7WorkingDays.getDayOfWeek().equals(DayOfWeek.SATURDAY) || After7WorkingDays.getDayOfWeek().equals(DayOfWeek.SUNDAY))
              {
            	  continue;
              }
              else
              {
            	 ++workDaysCounter; 
              }
        } while (workDaysCounter < 7);
        
       // System.out.println("After 7 working days: "+ After7WorkingDays);
        Period period = Period.between(today, After7WorkingDays);
        return period.getDays();     
	}
}


    