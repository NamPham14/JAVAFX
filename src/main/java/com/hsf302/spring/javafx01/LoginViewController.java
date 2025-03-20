package com.hsf302.spring.javafx01;

import com.hsf302.spring.javafx01.dto.AgentDTO;
import com.hsf302.spring.javafx01.service.AgentService;
import com.hsf302.spring.javafx01.service.impl.AgentServiceImpl;
import com.hsf302.spring.javafx01.validate.AgentValidator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController extends BaseController implements Initializable {
    @FXML
    public TextField ftEmail;
    @FXML
    public PasswordField ftPassword;
    @FXML
    public Button btLogin;
    @FXML
    public Hyperlink hlRegister;
    @FXML
    public Label errorEmail;
    @FXML
    public Label errorPassword;


    private ValidationSupport validationSupport;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        agentService = new AgentServiceImpl();

        // Initialize ValidationSupport
        validationSupport = new ValidationSupport();
        validationSupport.setErrorDecorationEnabled(true);// Enable visual feedback for errors
        // Set up validators for login fields
        AgentValidator.validateLogin(ftEmail, ftPassword, validationSupport);

        validationSupport.validationResultProperty().addListener((observable, oldValue, newValue) -> {
            updateErrorLabels();
        });
        ftEmail.textProperty().addListener((observable, oldValue, newValue) -> {updateErrorLabels();});
        ftPassword.textProperty().addListener((observable, oldValue, newValue) -> {updateErrorLabels();});

        // Set up the login button action
        btLogin.setOnAction(event -> handleLogin());

        // Set up the register hyperlink action
        hlRegister.setOnAction(event -> handleRegister());
    }


    //cap nhat loi
    private void updateErrorLabels(){
        errorEmail.setText("");
        errorPassword.setText("");

        //lay kq
        if(validationSupport.getValidationResult() !=null){
            for(ValidationMessage message : validationSupport.getValidationResult().getErrors()){
                if (message.getTarget() ==ftEmail){
                    errorEmail.setText(message.getText());
                } else if (message.getTarget() ==ftPassword) {
                    errorPassword.setText(message.getText());

                }

            }
        }
    }

    @FXML
    private void handleLogin() {

        updateErrorLabels();
        // Check if there are any validation errors
        if(validationSupport.isInvalid()){
            showAlert("Lỗi", "Vui lòng sửa các lỗi trong form trước khi tiếp tục.");
            return;
        }



        String email = ftEmail.getText();
        String password = ftPassword.getText();
        // Authenticate



        AgentDTO agentDTO = agentService.authenticate(email, password);
        if (agentDTO == null) {
            showAlert("Lỗi", "Email hoặc mật khẩu không đúng.");
        } else {
            showAlert("Thành công", "Đăng nhập thành công! Chào " + agentDTO.getAgentName());
            // Navigate to the agent list screen
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/JavaFx/Agent-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                Stage stage = (Stage) ftEmail.getScene().getWindow();
                stage.setTitle("Danh Sách Đại Lý");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Lỗi", "Không thể mở danh sách đại lý.");
            }
        }

    }
    @FXML
    private void handleRegister() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/JavaFx/Register-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 474);
            Stage stage = (Stage) ftEmail.getScene().getWindow();
            stage.setTitle("Đăng Ký Đại Lý");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể mở form đăng ký.");
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }









}
