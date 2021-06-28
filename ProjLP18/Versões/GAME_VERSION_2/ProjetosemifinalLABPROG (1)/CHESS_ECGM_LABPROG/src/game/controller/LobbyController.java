package game.controller;

import game.Main;

import game.network.Client;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LobbyController implements Initializable {

    @FXML
    private TextField textUserName;

    @FXML
    private RadioButton radioPvp;

    @FXML
    private TextField textIp;

    @FXML
    private Button btnStart;

    private ToggleGroup gameMode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupRadioGroup();
    }

    @FXML
    void onButtonStartClick(ActionEvent event) throws IOException {
        if (isValidToStart()) {
            if (getMode() == 0) { 
                Client client = new Client(textIp.getText());
                client.sendMessage(textUserName.getText());
                establishConnection(client);
            } 
        }
    }

    @FXML
    void onTextIpPress(KeyEvent event) {
        if (isValidToStart()) {
            if (event.getCode() == KeyCode.ENTER) {
                Client client = new Client(textIp.getText());
                client.sendMessage(textUserName.getText());
                establishConnection(client);
            }
        }
    }

    private void setupRadioGroup() {
        gameMode = new ToggleGroup();
        radioPvp.setToggleGroup(gameMode);
        radioPvp.setSelected(true);
        

        gameMode.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (gameMode.getSelectedToggle() == radioPvp) {
                    textIp.setDisable(false);
                } 
            }
        });
    }

    private boolean isValidToStart() {
        if (!textUserName.getText().equals("")) {
            if (gameMode.getSelectedToggle() == radioPvp) {
                return (!textIp.getText().equals(""));
            }
            return true;
        }
        return false;
    }

  
    private int getMode() {
        if (gameMode.getSelectedToggle() == radioPvp) {
            return 0;
        }
        
        return 0;
    }

    private void startPvpMode(Client connection, String playerId, String playerName, String rivalName) {
        try {
            MultiplayerModeController MultiplayerModeController = new MultiplayerModeController(connection, playerId, playerName, rivalName);
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/main_screen.fxml"));
            Stage stage = (Stage) btnStart.getScene().getWindow();
            loader.setController(MultiplayerModeController);
            Parent root = loader.load();
            stage.getScene().setRoot(root);
            stage.show();
            stage.sizeToScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void establishConnection(Client client) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] info = client.receiveMessage().split("_");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        startPvpMode(client, info[0], info[1], info[2]);
                    }
                });
            }
        });
        t.start();
    }

 
}
