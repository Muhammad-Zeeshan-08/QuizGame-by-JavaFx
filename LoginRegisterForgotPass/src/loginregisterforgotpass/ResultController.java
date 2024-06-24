package loginregisterforgotpass;
import loginregisterforgotpass.FXMLDocumentController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;

public class ResultController {

    @FXML
    public Label remark, marks, markstext, correcttext, wrongtext;

    @FXML
    public ProgressIndicator correct_progress, wrong_progress;

    int correct;
    int wrong;
    
    @FXML
    private Connection  connect;
    @FXML
    private PreparedStatement prepare;
    @FXML
    private ResultSet result;
    @FXML
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
    
    @FXML
    public void grade() throws Exception {
    	
    	String insertGrades = "UPDATE users SET grade=?, update_date=? "+"  WHERE username=? '"+"'  ";
		connect = connectDB();
		
		prepare = connect.prepareStatement(insertGrades);
		prepare.setString(1, String.valueOf(correct));
		Date date = new Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		prepare.setString(2, String.valueOf(sqlDate));
		prepare.setString(3, FXMLDocumentController.Gamer);
		
		prepare.executeUpdate();
    }

    @FXML
    private void initialize() {
        correct = QuizController.correct;
        wrong = QuizController.wrong;

        correcttext.setText("Correct Answers : " + correct);
        wrongtext.setText("Incorrect Answers : " + String.valueOf(QuizController.wrong));

        marks.setText(correct + "/10");
        float correctf = (float) correct/10;
        correct_progress.setProgress(correctf);

        float wrongf = (float) wrong/10;
        wrong_progress.setProgress(wrongf);


        markstext.setText(correct + " Marks Scored");

        if (correct<2) {
            remark.setText("Oh no..! You have failed the quiz. It seems that you need to improve your general knowledge. Practice daily! Check your results here.");
        } else if (correct>=2 && correct<5) {
            remark.setText("Oops..! You have scored less marks. It seems like you need to improve your general knowledge. Check your results here.");
        } else if (correct>=5 && correct<=7) {
            remark.setText("Good. A bit more improvement might help you to get better results. Practice is the key to success. Check your results here.");
        } else if (correct==8 || correct==9) {
            remark.setText("Congratulations! Its your hardwork and determination which helped you to score good marks. Check you results here.");
        } else if (correct==10) {
            remark.setText("Congratulations! You have passed the quiz with full marks because of your hardwork and dedication towards studies. Keep it up! Check your results here.");
        }
    }
}