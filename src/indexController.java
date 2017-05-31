import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class indexController {

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;
    
    @FXML
    private Label wronginputs;

    @FXML
    private Button LoginButton;

    private Admins[] admin;
    private int AdminsNo;
    public void initialize() 
    {
        //LoginButton.setOnAction(e -> System.exit(0));
    	try {
			ReadAdminsFile();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void ReadAdminsFile() throws IOException, ClassNotFoundException
    {
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
    }
    @FXML
    void loginAction(ActionEvent event) throws IOException {
    
    	int adminPosition = -1;
	      for (int i = 0; i < AdminsNo; i++)
	      {
	     	 if(username.getText().equals(admin[i].getUserName()) && password.getText().equals(admin[i].getPassWord()))
	     	 {
	     		adminPosition = i;
	     	 }
	      }
	      
    	 if(adminPosition > -1)
    	 {
    	       // Write the user name temperarly to a file so we can read it in the next frame 
    		   writeUsernameToFile(username.getText());
    		  ((Node)event.getSource()).getScene().getWindow().hide();
    		   // Create new stage to have the home page
     	        Stage stage = new Stage();
    	        stage.setTitle("Home Page");
    	        Pane myPane = null;
    	        myPane = FXMLLoader.load(getClass().getResource("home.fxml"));
    	        Scene scene2 = new Scene(myPane);
    	        scene2.getStylesheets().addAll(this.getClass().getResource("Style.css").toExternalForm());
    		    stage.setTitle("The Home Page"); 
    		    stage.setScene(scene2);
    	        stage.show();    	       
    	 }
      else
     {
    	 wronginputs.setText("Wrong Username or Password");
     }
    }
    
   private void writeUsernameToFile (String user) throws FileNotFoundException
   {
	      PrintWriter outputFile = new PrintWriter("TemporaryFile");
	      outputFile.println(user);
	      outputFile.close();
   }
   
}
