package com.hsf302.spring.javafx01;

import com.hsf302.spring.javafx01.dto.AgentDTO;
import com.hsf302.spring.javafx01.service.impl.AgentServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AgentDetailController extends BaseController implements Initializable {
    @FXML
    public Label ftAgent;
    @FXML
    public Label ftEmail;
    @FXML
    public Label ftAddress;
    @FXML
    public Label ftStatus;
    @FXML
    public Label ftBalance;
    @FXML
    public Label ftDate;
    @FXML
    public Button btReturn;
    @FXML
    public Hyperlink hpLogout;


    private  final ObservableList<AgentDTO> agentList= FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(agentService == null){
            agentService = new AgentServiceImpl();
        }
    }
    public void setAgent(AgentDTO agent) {

        ftAgent.setText("Tên Đại Lý: " + agent.getAgentName());
        ftStatus.setText("Trạng Thái: " + agent.getStatus());
        ftEmail.setText("Email: " + agent.getEmail());
        ftAddress.setText("Địa Chỉ: " + agent.getAddress());
        ftDate.setText("Ngày Đăng Ký: " + agent.getRegisterDate().toString());
        ftBalance.setText("Số Dư Tài Khoản: " + agent.getAccountBalance());

    }
    @FXML
    protected void onCloseClick() {
        Stage detailStage = (Stage) ftAgent.getScene().getWindow();
        detailStage.close();
    }

    @FXML
    protected void onLogoutClick() {
        try {
            Stage detailStage = (Stage) ftAgent.getScene().getWindow();
            detailStage.close();

            Stage agentStage = (Stage)  detailStage.getOwner();
            agentStage.close();

            //hien thi login
            Stage loginStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/JavaFx/Login-view.fxml"));
            Scene loginScene = new Scene(fxmlLoader.load(), 600, 474);
            loginStage.setTitle("Đăng Nhập");
            loginStage.setScene(loginScene);
            loginStage.show();

        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }









}
