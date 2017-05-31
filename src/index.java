import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class index extends Application {

		/* Welcome to the index page of my application 
		   Administrators should login with their stored user names and 
		   passwords in order to get to the home page */
	
	   public void start(Stage stage) throws Exception
	   {  
	      // Load the login FXML file.
	      Parent parent = FXMLLoader.load(
	      getClass().getResource("login.fxml"));      
	      Scene scene = new Scene(parent); 
	      scene.getStylesheets().addAll(this.getClass().getResource("Style.css").toExternalForm());
	      stage.setTitle("Login System"); 
	      stage.setScene(scene);
	      stage.show(); 
	   }
	   
	   public static void main(String[] args) throws IOException
	   {
		   launch(args);
		     // FileOutputStream saveStream = new FileOutputStream("RegisterationInvoices.dat");
		    // ObjectOutputStream savingObjects =  new ObjectOutputStream(saveStream);
			// savingObjects.writeObject(null);  
		    // savingObjects.close();   
	   }
	 } 

