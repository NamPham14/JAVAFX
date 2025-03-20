package com.hsf302.spring.javafx01;

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

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController extends BaseController implements Initializable {

    @FXML
    public TextField ftAgent;
    @FXML
    public TextField ftEmail;
    @FXML
    public TextField ftAddress;
    @FXML
    public PasswordField ftPassword;
    @FXML
    public Button btDangKi;
    @FXML
    public Label agentErrorLabel;
    @FXML
    public Label emailErrorLabel;
    @FXML
    public Label addressErrorLabel;
    @FXML
    public Label passwordErrorLabel;

    private ValidationSupport validationSupport;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        agentService = new AgentServiceImpl();

        // Initialize ValidationSupport
        validationSupport = new ValidationSupport();
        validationSupport.setErrorDecorationEnabled(false);


        AgentValidator.validateRegistration(ftAgent, ftEmail, ftAddress, ftPassword, validationSupport);


        validationSupport.validationResultProperty().addListener((obs, oldResult, newResult) -> {
            updateErrorLabels();
        });

        // Cập nhật lỗi ngay khi người dùng nhập liệu
        ftAgent.textProperty().addListener((obs, oldValue, newValue) -> updateErrorLabels());
        ftEmail.textProperty().addListener((obs, oldValue, newValue) -> updateErrorLabels());
        ftAddress.textProperty().addListener((obs, oldValue, newValue) -> updateErrorLabels());
        ftPassword.textProperty().addListener((obs, oldValue, newValue) -> updateErrorLabels());

        // Set up the register button action
        btDangKi.setOnAction(event -> handleRegister());
    }

    // Phương thức cập nhật các Label hiển thị lỗi
    private void updateErrorLabels() {
        // Xóa các thông báo lỗi cũ
        agentErrorLabel.setText("");
        emailErrorLabel.setText("");
        addressErrorLabel.setText("");
        passwordErrorLabel.setText("");

        // Lấy kết quả xác thực
        if (validationSupport.getValidationResult() != null) {
            for (ValidationMessage message : validationSupport.getValidationResult().getErrors()) {
                if (message.getTarget() == ftAgent) {
                    agentErrorLabel.setText(message.getText());
                } else if (message.getTarget() == ftEmail) {
                    emailErrorLabel.setText(message.getText());
                } else if (message.getTarget() == ftAddress) {
                    addressErrorLabel.setText(message.getText());
                } else if (message.getTarget() == ftPassword) {
                    passwordErrorLabel.setText(message.getText());
                }
            }
        }
    }

    @FXML
    private void handleRegister() {
        // Cập nhật các Label lỗi trước khi kiểm tra
        updateErrorLabels();

        // Kiểm tra xem có lỗi xác thực nào không
        if (validationSupport.isInvalid()) {
            showAlert("Lỗi", "Vui lòng sửa các lỗi trong form trước khi tiếp tục.");
            return;
        }

        String agentName = ftAgent.getText();
        String email = ftEmail.getText();
        String address = ftAddress.getText();
        String password = ftPassword.getText();

        // Register the agent
        try {
            agentService.createAgent(agentName, email, address, password);
            showAlert("Thành công", "Đăng ký thành công! Vui lòng đăng nhập.");

            // Navigate back to the login screen
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/JavaFx/Login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 474);
            Stage stage = (Stage) ftAgent.getScene().getWindow();
            stage.setTitle("Đăng Nhập");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Đăng ký thất bại. Vui lòng thử lại.");
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