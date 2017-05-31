import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class homeController {
	

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button RegisterNewChildButton;

    @FXML
    private Button WithdrawChildButton;

    @FXML
    private Button DisplayChildProfileButton;

    @FXML
    private Button ManageStaffButton;

    @FXML
    private Button ChildAttendanceButton;

    @FXML
    private Button SetLatePickUpButton;

    @FXML
    private Button SetEarlyDroppingButton;

    @FXML
    private Button ManageInvoceButton;
    
    @FXML
    private Button CreateInvoiceButton;

    @FXML
    private Button ManageWaitingListButton;

    @FXML
    private Button ManageSessionButton;

    @FXML
    private Button StaffAttendance;

    @FXML
    private Pane subPane;

    @FXML
    private Button StaffProfilesButton;
    
    @FXML
    private Button ChangeChildSessionButton;
    
    @FXML
    private Button exist;
    
    @FXML
    private Button logoff;


    @FXML
    void ChangeChildSessionAction(ActionEvent event) throws IOException {
    	Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("ChangeChildSession.fxml")); 
    	subPane.getChildren().clear();
    	subPane.getChildren().add(newLoadedPane);
    }

    @FXML
    void ChildAttendanceAction(ActionEvent event) throws IOException {
    	Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("RegisterChildrenAttendance.fxml")); 
    	subPane.getChildren().clear();
    	subPane.getChildren().add(newLoadedPane);
    }

    @FXML
    void DisplayChildProfileAction(ActionEvent event) throws IOException {
    	Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("ChildrenProfiles.fxml")); 
    	subPane.getChildren().clear();
    	subPane.getChildren().add(newLoadedPane);
    }
    
    @FXML
    void CreateInvoiceAction(ActionEvent event) throws IOException {
    	Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("CreateInvoice.fxml")); 
    	subPane.getChildren().clear();
    	subPane.getChildren().add(newLoadedPane);
    }

    @FXML
    void ManageInvoceAction(ActionEvent event) throws IOException {
    	Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("Manageinvoice.fxml")); 
    	subPane.getChildren().clear();
    	subPane.getChildren().add(newLoadedPane);
    }

    @FXML
    void StaffProfilesAction(ActionEvent event) throws IOException {
    	Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("StaffProfiles.fxml")); 
    	subPane.getChildren().clear();
    	subPane.getChildren().add(newLoadedPane);
    }

    @FXML
    void ManageSessionAction(ActionEvent event) throws IOException {
    	Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("ManageSession.fxml")); 
    	subPane.getChildren().clear();
    	subPane.getChildren().add(newLoadedPane);
    }

    @FXML
    void ManageStaffAction(ActionEvent event) throws IOException {
    	Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("AddStaff.fxml")); 
    	subPane.getChildren().clear();
    	subPane.getChildren().add(newLoadedPane);
    }

    @FXML
    void ManageWaitingListAction(ActionEvent event) throws IOException {
    	Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("ManageWaitingList.fxml")); 
    	subPane.getChildren().clear();
    	subPane.getChildren().add(newLoadedPane);
    }

    @FXML
    void RegisterNewChildAction(ActionEvent event) throws IOException {
    	Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("RegisterNewChild.fxml"));     
    	subPane.getChildren().clear();
    	subPane.getChildren().add(newLoadedPane);
    }

    @FXML
    void SetEarlyDroppingAction(ActionEvent event) throws IOException {
    	Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("SetEarlyDroppingOrLatePickUp.fxml")); 
    	subPane.getChildren().clear();
    	subPane.getChildren().add(newLoadedPane);
    }

    @FXML
    void SetLatePickUpAction(ActionEvent event) throws IOException {
    	Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("SetEarlyDroppingOrLatePickUp.fxml")); 
    	subPane.getChildren().clear();
    	subPane.getChildren().add(newLoadedPane);
    }

    @FXML
    void StaffAttendanceAction(ActionEvent event) throws IOException {
    	Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("StaffAttendance.fxml")); 
    	subPane.getChildren().clear();
    	subPane.getChildren().add(newLoadedPane);
    }

    @FXML
    void WithdrawChildAction(ActionEvent event) throws IOException {
    	Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("WithdrawChild.fxml")); 
    	subPane.getChildren().clear();
    	subPane.getChildren().add(newLoadedPane);
    }   
    

    public void initialize() 
    {
    	// Get the username 
    	String name = readUsernameFromTemporaryFile();
    	welcomeLabel.setText("Welcome "+ name);
    	
    	// exist Button using Lambda Expression 
    	exist.setOnAction(e -> System.exit(0)); 
    } 
    
    @FXML
    void logoffAction(ActionEvent event) throws IOException {
		  ((Node)event.getSource()).getScene().getWindow().hide();
		   // Create new stage to have the login page
	        Stage stage = new Stage();
	        stage.setTitle("Login Page");
	        Pane myPane = null;
	        myPane = FXMLLoader.load(getClass().getResource("login.fxml"));
	        Scene scene2 = new Scene(myPane);
	        scene2.getStylesheets().addAll(this.getClass().getResource("Style.css").toExternalForm());
		    stage.setTitle("The Login Page"); 
		    stage.setScene(scene2);
	        stage.show();    
    }
    
      
    public String readUsernameFromTemporaryFile()
    {
    	  File file = new File("TemporaryFile");
    	  Scanner fileReader;
    	  String name = "";
		try {
			fileReader = new Scanner(file);
			 name = fileReader.nextLine();
			 fileReader.close();
			 file.delete();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
    }

    
}
