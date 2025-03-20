package com.hsf302.spring.javafx01;

import com.hsf302.spring.javafx01.dto.AgentDTO;
import com.hsf302.spring.javafx01.service.impl.AgentServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AgentController extends BaseController implements Initializable {
    @FXML
    public TextField ftEmail;
    @FXML
    public TextField ftAgent;
    @FXML
    public TextField ftStatus;
    @FXML
    public Button btSearch;
    @FXML
    public TableView<AgentDTO> TableviewAgent;
    @FXML
    private Label welcomeText;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    if(agentService == null){
    agentService = new AgentServiceImpl();
    }
      putDataTableView();
    }
    private  final ObservableList<AgentDTO> agentList= FXCollections.observableArrayList();

    @FXML
    protected void onButtonClick() {
        String email = ftEmail.getText();
        String agentName = ftAgent.getText();
        String status = ftStatus.getText();

        try {
            agentList.clear();
            agentList.addAll(agentService.findAgents(email,agentName,status));
            TableviewAgent.setItems(agentList);

        }catch (Exception e){
            e.printStackTrace();
            showAlert("Lỗi", "Không thể tìm kiếm đại lý: " + e.getMessage());
        }




    }



    private void putDataTableView() {
        agentList.clear();
        agentList.addAll(agentService.getAllAgents());
        TableviewAgent.getColumns().clear();
        TableviewAgent.setItems(agentList);
        TableColumn<AgentDTO,Long> agentId = new TableColumn<>("Agent_ID");
        agentId.setCellValueFactory(new PropertyValueFactory<>("agentId"));
        TableviewAgent.getColumns().add(agentId);

        TableColumn<AgentDTO,String> agentName = new TableColumn<>("Agent_Name");
        agentName.setCellValueFactory(new PropertyValueFactory<>("agentName"));
        TableviewAgent.getColumns().add(agentName);

        TableColumn<AgentDTO,String> agentStatus = new TableColumn<>("Status");
        agentStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        TableviewAgent.getColumns().add(agentStatus);


        TableColumn<AgentDTO,String> agentEmail = new TableColumn<>("Email");
        agentEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableviewAgent.getColumns().add(agentEmail);


        TableColumn<AgentDTO,String> agentAddress = new TableColumn<>("Address");
        agentAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableviewAgent.getColumns().add(agentAddress);

        TableColumn<AgentDTO,String> agentRegister = new TableColumn<>("Register_Date");
        agentRegister.setCellValueFactory(new PropertyValueFactory<>("registerDate"));
        TableviewAgent.getColumns().add(agentRegister);

        TableColumn<AgentDTO,Double> agentBalance = new TableColumn<>("Account_Balance");
        agentBalance.setCellValueFactory(new PropertyValueFactory<>("accountBalance"));
        TableviewAgent.getColumns().add(agentBalance);

        TableColumn<AgentDTO,String> agentPasswords = new TableColumn<>("Password");
        agentPasswords.setCellValueFactory(new PropertyValueFactory<>("password"));
        TableviewAgent.getColumns().add(agentPasswords);

        TableColumn<AgentDTO,Void> detailColumn = new TableColumn<>("Xem Chi Tiet");
        detailColumn.setCellFactory(param -> new TableCell<AgentDTO,Void>(){
            private final Button detailButton = new Button("Xem Chi Tiet");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                }else {
                    detailButton.setOnAction(event -> {
                        AgentDTO agentDTO = getTableView().getItems().get(getIndex());
                        showAgentDetails(agentDTO);
                    });
                    setGraphic(detailButton);
                }

            }
        });
        TableviewAgent.getColumns().add(detailColumn);

        TableviewAgent.setOnMouseClicked(event -> {
            AgentDTO agentDTO = TableviewAgent.getSelectionModel().getSelectedItem();
            if (agentDTO != null) {
                ftAgent.setText(agentDTO.getAgentName());
                ftEmail.setText(agentDTO.getEmail());
                ftStatus.setText(agentDTO.getStatus());
            }
        });
    }

    private  void showAgentDetails(AgentDTO agentDTO){
       try {
           FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/JavaFx/Agent-detail-view.fxml"));
           Stage detailStage = new Stage();
           detailStage.initModality(Modality.APPLICATION_MODAL);

           //lay stage
           Stage agentStage = (Stage)TableviewAgent.getScene().getWindow();
           detailStage.initOwner(agentStage);


           detailStage.setTitle("Chi Tiet Dai Ly");
           Scene detailSence = new Scene(fxmlLoader.load(),881,672);
           detailStage.setScene(detailSence);

           //lay controller va truyen du lieu
           AgentDetailController agentDetailController = fxmlLoader.getController();
           agentDetailController.setAgent(agentDTO);




           detailStage.showAndWait();
       }
       catch (Exception e){
           e.printStackTrace();
           showAlert("Lỗi", "Không thể mở chi tiết đại lý: " + e.getMessage());
       }



    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}