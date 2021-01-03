package sample;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;

public class LoginController implements Initializable{

    @FXML
    private Button closeButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXPasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("resources/866.jpg");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);
    }

    public void CancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void loginButtonOnAction(ActionEvent event){
        if(usernameField.getText().isBlank() == false && passwordField.getText().isBlank() == false){
            validateLogin();
        }else{
            loginMessageLabel.setText("Invalid login attempted.");
        }
    }

    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM user_accounts WHERE username = '" + usernameField.getText() + "' AND password = '" + passwordField.getText() + "'";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    loginMessageLabel.setText("Login successful.");
                }else{
                    loginMessageLabel.setText("Login failed.");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    private static double xOffset = 0;
    private static double yOffset = 0;
    private Stage registerStage = null;
    public void registerForm(){
        try{
            if(registerStage==null) {
                registerStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
                registerStage.initStyle(StageStyle.UNDECORATED);
                registerStage.setScene(new Scene(root, 277, 492));
                root.getStylesheets().add("sample/styles.css");

                root.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset = registerStage.getX() - event.getScreenX();
                        yOffset = registerStage.getY() - event.getScreenY();
                    }
                });
                root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        registerStage.setX(event.getScreenX() + xOffset);
                        registerStage.setY(event.getScreenY() + yOffset);
                    }
                });
                registerStage.show();
            }else if(registerStage.isShowing()){
                registerStage.toFront();
            }else{
                registerStage.show();
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
