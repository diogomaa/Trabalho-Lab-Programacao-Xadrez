package game.controller;

import game.Main;
import game.model.ChessBoard;
import game.model.Move;
import game.network.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.stage.Stage;

public class MultiplayerModeController implements Initializable, Client.DataReceiveListener, ChessBoard.OnPieceMoveListener{
    @FXML
    private Label nameP2;

    @FXML
    private Label lastMoveP2;

    @FXML
    private Label nameP1;


    
    @FXML
    private Label lastMoveP1;
    
    @FXML
    private Label infocurrent;

    @FXML
    private GridPane chessPane;

    @FXML
    private TextArea chatBox;

    @FXML
    private TextField textInput;
    
    @FXML
    public Button closeButton;

    @FXML
    private Button btnSend;

    private Client client;
    private ChessBoard chessBoard;
    private String playerId;
    private String playerName;
    private String rivalName;
    

    
    
    public MultiplayerModeController(Client connection, String playerId, String playerName, String rivalName) {
        this.client = connection;
        this.playerId = playerId;
        this.playerName = playerName;
        this.rivalName = rivalName;
        
        client.setOnDataReceiveListener(this);
        client.startDataThread();

  
          chessBoard = new ChessBoard(playerId);
         chessBoard.setOnPieceMoveListener(this);
       
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStageTitle("Jogo Xadrez - Jogador: " + playerName);
        drawChessPane();
        displayPlayerName();
      
        if (playerId.equals("2"))   chessPane.setDisable(true);
    }

    @Override
    public void onChatReceive(String message) {
        chatBox.appendText(message + "\n");
    }

      @FXML
     public void onButtongivup(ActionEvent event) {
    Stage stage = (Stage) closeButton.getScene().getWindow();
    stage.close();
    client.closeConnection(playerName + " Desistiu! "+ textInput.getText() );
   
 
    }
     
      public void onGiveUpReceive(String messages) {
       
        infocurrent.setText( messages );
      
    }

   

     
      
    
    
     
    @Override
    public void onMoveReceive(String move) {
        chessPane.setDisable(false);
        Move m = new Move(move);
     
        if (playerId.equals("1")) {
            lastMoveP2.setText(m.toBoardMove());
             infocurrent.setText("É a sua vez " +playerName+"!");
             
             
        } else if (playerId.equals("2")) {
            lastMoveP1.setText(m.toBoardMove());
          infocurrent.setText("É a sua vez " +playerName+"!");
        }
        
    
        chessBoard.updateBoard(m);
    }

    @Override
    public void onPieceMove(Move move) {
        chessPane.setDisable(true);
        client.sendMessage(playerId + "_" + move.toString());
        if (playerId.equals("1")) {
            lastMoveP1.setText(move.toBoardMove());
     infocurrent.setText("É a sua vez " +rivalName+"!");
        } else if (playerId.equals("2")) {
            lastMoveP2.setText(move.toBoardMove());
    infocurrent.setText("É a sua vez " +rivalName+"!");
        }
    }

    @FXML
    void onButtonSendClick(ActionEvent event) {
        client.sendMessage(playerName + ": " + textInput.getText());
        textInput.clear();
    }

    @FXML
    void onTextInputPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            client.sendMessage(playerName + ": " + textInput.getText());
            textInput.clear();
        }
    }

    private void drawChessPane() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                chessPane.add(chessBoard.getBoardpiece(row, col).getPane(), col, row);
            }
        }
    }

 





    
    private void displayPlayerName() {
        if (playerId.equals("1")) {
              infocurrent.setText("É a sua vez " +playerName+"!");
            nameP1.setText(playerName);
            nameP2.setText(rivalName);
        } else {
            infocurrent.setText("É a sua vez " +rivalName+"!");
            nameP1.setText(rivalName);
            nameP2.setText(playerName);
        }
    }

    private void setStageTitle(String title) {
        Main.getStage().setTitle(title);
    }
}
