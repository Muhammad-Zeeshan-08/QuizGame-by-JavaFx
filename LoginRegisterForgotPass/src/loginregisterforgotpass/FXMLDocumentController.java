package loginregisterforgotpass;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//public class FXMLDocumentController {
//
//}

public class FXMLDocumentController implements Initializable{
	@FXML
	public static String Gamer;
	
	@FXML
    private Button changePass_backBtn;

    @FXML
    private PasswordField changePass_cPassword;

    @FXML
    private VBox changePass_form;

    @FXML
    private PasswordField changePass_password;

    @FXML
    private Button changePass_proceedBtn;

    @FXML
    private TextField forgot_answer;

    @FXML
    private Button forgot_backBtn;

    @FXML
    private VBox forgot_form;

    @FXML
    private Button forgot_proceedBtn;

    @FXML
    private ComboBox<?> forgot_selectQuestion;

    @FXML
    private TextField forgot_username;

    @FXML
    private Button login_btn;

    @FXML
    private Button login_createAccount;

    @FXML
    private Hyperlink login_forgotPassword;

    @FXML
    private VBox login_form;

    @FXML
    private PasswordField login_password;

    @FXML
    private CheckBox login_selectShowPassword;

    @FXML
    private TextField login_username;

    @FXML
    private TextField signup_answer;

    @FXML
    private Button signup_btn;

    @FXML
    private PasswordField signup_cPassword;

    @FXML
    private TextField signup_email;

    @FXML
    private VBox signup_form;

    @FXML
    private Button signup_loginAccount;

    @FXML
    private PasswordField signup_password;

    @FXML
    private ComboBox<?> signup_selectQuestion;

    @FXML
    private TextField signup_username;
    
    private Connection  connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    
    public Connection connectDB() {
    	
    	try {
    		//Registering mysql Driver first then do connection
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		//"jdbc:mysql://localhost/(DATABASE NAME)","USERNAME","PASSWORD");		
    		//here we do connection, default username is root & password is null in xampp 
    		connect = DriverManager.getConnection("jdbc:mysql://localhost/useraccount","root","");
    		return connect;
    	}
    	catch(Exception e){e.printStackTrace();}
    	return null;
    }
    
//----------------------------------------------------------------------------------------------------------
    public void login(){
    	//here we are making object of that class we we create by our self as AlertMessage.java
    	//to print error or success message
    	AlertMessage a_message = new AlertMessage();
    	
    	if( login_username.getText().isEmpty() || login_password.getText().isEmpty() ) {
    		a_message.errorMessage("Fields can't be empty");
    	}
    	else {
    		//In this else statement we are basically extracting username & pass from DB to check
    		//We are applying here SQL queries such as: SELECT, FROM ,WHERE
    		//here users is the name of table which we create in DB
    		
    		String selectData = "SELECT username,password FROM users "+" WHERE username=? and password=? ";
    		connect = connectDB();
    		
    		try {
    			prepare = connect.prepareStatement(selectData); //ye dusra method as compare to Register method sey
    			prepare.setString(1, login_username.getText());
    			prepare.setString(2, login_password.getText());
    			
        		result = prepare.executeQuery();
        		
        		if(result.next()) {
        			Gamer= login_username.getText();
        			a_message.successMessage("Successfully Login!");
        			loginClearFields();									//calling clear field method written below
        			
        			Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        			Stage stage = new Stage();
        			Scene scene = new Scene(root);
        			
        			stage.setScene(scene);
        			stage.show();
        			
        			login_btn.getScene().getWindow().hide();
        		}
        		else {
        			a_message.errorMessage("Incorrect Username or Password");
        		}
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    			}
    	}
    }
//----------------------------------------------------------------------------------------------------------
    public void forgotPassword() {
    	
    	AlertMessage a_message = new AlertMessage();
    	
    	if(forgot_username.getText().isEmpty()  || forgot_selectQuestion.getSelectionModel().getSelectedItem()==null
        		|| forgot_answer.getText().isEmpty()) {
        		a_message.errorMessage("Fields can't be empty");
        	}
        else {
        		//SELECT key baad jo hum likhtey h wo basically column name hotey h hamare DB key
        		//In this else statement we are basically checking if the user is already in our DB or if not then 
        		//we are inserting its record in our DB
        		//We are applying here SQL queries such as: SELECT, FROM ,WHERE
        		//here users is the name of table which we create in DB
        		String checkData = "SELECT  username,question,answer FROM users "+" WHERE username=? and question=? and answer=? ";
        		connect = connectDB();
        		
        		try {
        			prepare = connect.prepareStatement(checkData); //ye dusra method as compare to Register method sey
        			prepare.setString(1, forgot_username.getText());
        			prepare.setString(2, (String)forgot_selectQuestion.getSelectionModel().getSelectedItem());
        			prepare.setString(3, forgot_answer.getText());
        			
        			
            		result = prepare.executeQuery();
            		//If Correct
            		if(result.next()) {
            			//proceeding to Change the Password
            			signup_form.setVisible(false);					
            			login_form.setVisible(false);					
            			forgot_form.setVisible(false);					
            			changePass_form.setVisible(true);
            		}
            		else {
            			a_message.errorMessage("Incorrect Username or Wrong answer");
            		}
        		}
        		catch(Exception e) {
        			e.printStackTrace();
        			}
        	}
    }
    
//----------------------------------------------------------------------------------------------------------
    public void register() {
    	//here we are making object of that class we we create by our self as AlertMessage.java
    	//to print error success message
    	AlertMessage a_message = new AlertMessage();
    	
    	if(signup_email.getText().isEmpty() || signup_username.getText().isEmpty() 
    		|| signup_password.getText().isEmpty() ||signup_cPassword.getText().isEmpty() 
    		|| signup_selectQuestion.getSelectionModel().getSelectedItem()==null
    		|| signup_answer.getText().isEmpty()) {
    		a_message.errorMessage("Fields can't be empty");
    	}
    	else if(!signup_password.getText().equals(signup_cPassword.getText())){
//In Java, you should use the .equals() method to compare the content of strings instead of ==, which compares object references.
// that's why this arises issue: else if(signup_password.getText() != signup_cPassword.getText()){
    		a_message.errorMessage("Password does not match");
    	}
    	else if(signup_password.getText().length()<8 || signup_cPassword.getText().length()<8 ) {
    		a_message.errorMessage("The length of Password must atleast 8 characters");
    	}
    	else {
    		//In this else statement we are basically checking if the user is already in our DB or if not then 
    		//we are inserting its record in our DB
    		//We are applying here SQL queries such as: SELECT, FROM ,WHERE
    		//here users is the name of table which we create in DB
    		String checkUsername = "SELECT * FROM users WHERE username = '"+ signup_username.getText()+"' ";
    		connect = connectDB();
    		
    		try {
    			statement = connect.createStatement();
        		result = statement.executeQuery(checkUsername);
        		
        		if(result.next()) {
        			//checking if the user is already in our DB 
        			a_message.errorMessage(signup_username.getText() + "is already present in our DB or already SignIn ");
        		}
        		else {
        			//we are inserting its record in our DB by using SQL queries
        			String insertData = "INSERT INTO users "+" (email,username,password,question,answer,date)"
        			+ "VALUES(?,?,?,?,?,?)";
        			//yaha jo Values mein ques mark h inko neechey sey from left to right ki sequence mein hm value provide kr rahe h
        			
        			prepare = connect.prepareStatement(insertData);
        			prepare.setString(1, signup_email.getText());
        			prepare.setString(2, signup_username.getText());
        			prepare.setString(3, signup_password.getText());
//        			prepare.setString(4, signup_selectQuestion.getSelectionModel().getSelectedItem());  
        			//Wrong b/c setString method may only accept String in its 2nd parameter
        			prepare.setString(4, (String)signup_selectQuestion.getSelectionModel().getSelectedItem());
        			prepare.setString(5, signup_answer.getText());		
        			
        			Date date = new Date();
        			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        			prepare.setString(6, String.valueOf(sqlDate));
        			
        			prepare.executeUpdate();
        			
        			a_message.successMessage("Registered Successfully!");
        			registerClearFields();							//calling clear field method written below
        			
    	    		signup_form.setVisible(false);					//unvisibling the SignUp form
    	    		login_form.setVisible(true);					//visibling the Login  form
        		}
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    			}
    	}
    }
//----------------------------------------------------------------------------------------------------------    
	public void changePassword() {
    	
	    	AlertMessage a_message = new AlertMessage();
    	
	    	if(!changePass_password.getText().equals(changePass_cPassword.getText())) {
	        		a_message.errorMessage("Password not match");
	        	}
	    	else if(changePass_password.getText().isEmpty() || changePass_cPassword.getText().isEmpty()) {
	    			a_message.errorMessage("Fields can't be empty");
	    	}
    		else if(changePass_password.getText().length()<8 || changePass_cPassword.getText().length()<8 ) {
    			a_message.errorMessage("The length of Password must atleast 8 characters");
    		}
    		else {
    			//reseting the Passwrod through USERNAME
    			String updateData = "UPDATE users SET password=? , update_date=? "+"  WHERE username=  '"+forgot_username.getText()+"'  ";
    			connect = connectDB();
    		
    			try {
        				prepare = connect.prepareStatement(updateData);
        				prepare.setString(1, changePass_password.getText());	
        			
        				Date date = new Date();
        				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        				prepare.setString(2, String.valueOf(sqlDate));
        			
        				prepare.executeUpdate();
        				a_message.successMessage("Password Updated Successfully!");
        			
        				signup_form.setVisible(false);					
        				login_form.setVisible(true);					
        				forgot_form.setVisible(false);					
        				changePass_form.setVisible(false);
        			
        				passwordRenewClearFields();							//calling clear field method written below
    			}
    			catch(Exception e) {
    				e.printStackTrace();
    				}
    	}
    	
    }
//----------------------------------------------------------------------------------------------------------
    //Now for the Questions in the sign in section:
    // creating an Array
    private String[] questionList = {"What is your favourite sport?","What is your favourite food?"};
    
    public void questions() {
    	List<String> listQ= new ArrayList<>();
    	
    	//using for-each loop to access elements of array
    	for(String Data: questionList) {
    		listQ.add(Data);
    	}
    	
    	ObservableList listdata = FXCollections.observableArrayList(listQ);
    	signup_selectQuestion.setItems(listdata);	// this is for those ques which are asked in SignUp
    	forgot_selectQuestion.setItems(listdata);	// this is for those ques which are asked in Identity Confirmation 
    	
    }
    
//----------------------------------------------------------------------------------------------------------    
    //Method to clear All the fields of SignUp Form
    public void registerClearFields() {
    	signup_email.setText("");
    	signup_username.setText("");
    	signup_password.setText("");
    	signup_cPassword.setText("");
    	signup_selectQuestion.getSelectionModel().clearSelection();
    	signup_answer.setText("");
    }
    
    //Method to clear All the fields of LogIn Form
    public void loginClearFields() {
    	login_username.setText("");
    	login_password.setText("");
    }
    
    public void passwordRenewClearFields() {
    	changePass_password.setText("");
    	changePass_cPassword.setText("");
    }
 
  //----------------------------------------------------------------------------------------------------------
    //Now writing method to Switiching b/w the FORMS when clicking on the buttons
    public void switchForm(ActionEvent event) {
    	
		//Login Form visible
			if(event.getSource() == signup_loginAccount || event.getSource() == forgot_backBtn) {
    			signup_form.setVisible(false);					//unvisibling the SignUp form
    			login_form.setVisible(true);					// visibling the Login Form
    			forgot_form.setVisible(false);					//unvisibling the Confirmation Ident form
    			changePass_form.setVisible(false);				//unvisibling the Password Renew form
    	}
    	//Signup Form visible
			else if(event.getSource() == login_createAccount) {
    			signup_form.setVisible(true);					
    			login_form.setVisible(false);					
    			forgot_form.setVisible(false);					
    			changePass_form.setVisible(false);
    		}
	    	//Identity Confirmation Form Visible
	    	else if(event.getSource() == login_forgotPassword) {
    			signup_form.setVisible(false);					
    			login_form.setVisible(false);					
    			forgot_form.setVisible(true);					
    			changePass_form.setVisible(false);
    			//to show the data to the combobox
    			questions();
//    			forgot_questions();
    		}
	    	//Identity Confirmation Form Visible
	    	else if(event.getSource() == changePass_backBtn) {
    			signup_form.setVisible(false);					
    			login_form.setVisible(false);					
    			forgot_form.setVisible(true);					
    			changePass_form.setVisible(false);
    		}
    }
    
  //----------------------------------------------------------------------------------------------------------
	@Override
	public void initialize(URL url,ResourceBundle rb) {
		//TO DO
		questions();
	}
}