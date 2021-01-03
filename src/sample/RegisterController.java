package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXPasswordField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.scene.image.Image;

public class RegisterController implements Initializable{

    @FXML
    private JFXButton cancelButton;
    @FXML
    private ImageView registerImageView;
    @FXML
    private JFXTextField fnameTextField;
    @FXML
    private JFXTextField lnameTextField;
    @FXML
    private JFXTextField unameTextField;
    @FXML
    private JFXPasswordField pwTextField;
    @FXML
    private JFXPasswordField confirmPwTextField;
    @FXML
    private JFXButton registerButton;
    @FXML
    private Label errorMessage;

    public void registerButtonOnAction(ActionEvent event){
        errorMessage.setTextFill(Color.color(1,0,0));
        if(unameTextField.getText().isBlank() || lnameTextField.getText().isBlank()){
            errorMessage.setText("Name fields are required.");
        }else if(unameTextField.getText().isBlank()){
            errorMessage.setText("Usernames are required.");
        }else if(pwTextField.getText().isBlank()) {
            errorMessage.setText("Password field is empty!");
        }else if (!pwTextField.getText().equals(confirmPwTextField.getText())) {
            errorMessage.setText("Passwords does not match!");
        }else{
            register();
        }
    }

    public void register(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String fname = fnameTextField.getText();
        String lname = lnameTextField.getText();
        String uname = unameTextField.getText();
        String password = pwTextField.getText();

        String insertFields = "INSERT INTO user_accounts(firstname, lastname, username, password) VALUES ('";
        String insertValues = fname + "','" + lname + "','" + uname + "','" + password + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
            errorMessage.setTextFill(Color.color(0,0,1));
            errorMessage.setText("Registered!");
        }catch (Exception e){
            e.getCause();
            e.printStackTrace();
            errorMessage.setTextFill(Color.color(1,0,1));
            errorMessage.setText("SQL Error, Registration failed.");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
       // File brandingFile = new File("resources/281.jpg");
        //Image brandingImage = new Image(brandingFile.toURI().toString());
        // registerImageView.setImage(brandingImage);

        Image img = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("resources/281.jpg")));
        registerImageView.setImage(img);
    }

    public void CancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
