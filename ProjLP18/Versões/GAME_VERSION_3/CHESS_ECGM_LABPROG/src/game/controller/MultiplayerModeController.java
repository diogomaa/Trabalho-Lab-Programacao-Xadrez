package game.controller;

import game.Main;
import game.model.ChessBoard;
import game.model.Move;
import game.network.Client;
import java.awt.event.ActionListener;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.layout.Region;
import javax.naming.NamingException;
import org.w3c.dom.NodeList;


public class MultiplayerModeController implements Initializable, Client.DataReceiveListener, ChessBoard.OnPieceMoveListener{
    @FXML
    private Label nameP2;

    @FXML
    private Label lastMoveP2;

    @FXML
    private Label nameP1;
    
    @FXML
    private Label lblChessTimer;

    @FXML
    private Label lastMoveP1;

    @FXML
    private GridPane chessPane;

    @FXML
    private TextArea chatBox;

    @FXML
    private TextField textInput;

    

    private Client client;
    private ChessBoard chessBoard;
    private String playerId;
    private String playerName;
    private String rivalName;
    
    private Thread timerThread;
    private final Object timerLock = new Object();
    private int timeLeft;
    private int index = 0;
    private NodeList nList;
    private List<Move> moveList = new ArrayList<>();
    private boolean isGameOver = false;
    Timer timer = new Timer();
    
    int countdown= 60;
    
    
    TimerTask task = new TimerTask(){
    
    public void run()
        {
            countdown--;  
            Platform.runLater(() ->lblChessTimer.setText("Falta: " + countdown));
        }
    
    };
    
    
    
    
       
    
    

    public MultiplayerModeController(Client connection, String playerId, String playerName, String rivalName) {
        this.client = connection;
        this.playerId = playerId;
        this.playerName = playerName;
        this.rivalName = rivalName;

        client.setOnDataReceiveListener(this);
        client.startDataThread();

        chessBoard = new ChessBoard(playerId);
        chessBoard.setOnPieceMoveListener(this);
        
        
    
    
    
        timer.scheduleAtFixedRate(task,1000,1000);
        
        /*createTimer();*/
        /*pauseTimer();*/
    }
    
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStageTitle("Jogo Xadrez - Jogador: " + playerName);
        drawChessPane();
        displayPlayerName();

        if (playerId.equals("2"))   chessPane.setDisable(true);
        lblChessTimer.setText("Falta: 60");

    }

    @Override
    public void onChatReceive(String message) {
        chatBox.appendText(message + "\n");
    }

    @Override
    public void onMoveReceive(String move) {
        chessPane.setDisable(false);
        Move m = new Move(move);
        if (playerId.equals("1")) {
            lastMoveP2.setText(m.toBoardMove());
        } else if (playerId.equals("2")) {
            lastMoveP1.setText(m.toBoardMove());
        }
        chessBoard.updateBoard(m);
        countdown = 60;
    }

    @Override
    public void onPieceMove(Move move) {
        chessPane.setDisable(true);
        client.sendMessage(playerId + "_" + move.toString());
        if (playerId.equals("1")) {
            lastMoveP1.setText(move.toBoardMove());
        } else if (playerId.equals("2")) {
            lastMoveP2.setText(move.toBoardMove());
        }
        countdown=60;
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
                chessPane.add(chessBoard.getboardpiece(row, col).getPane(), col, row);
            }
        }
    }

    private void displayPlayerName() {
        if (playerId.equals("1")) {
            nameP1.setText(playerName);
            nameP2.setText(rivalName);
        } else {
            nameP1.setText(rivalName);
            nameP2.setText(playerName);
        }
    }

    private void setStageTitle(String title) {
        Main.getStage().setTitle(title);
    }
    
    
    
    
    /*private void pauseTimer() {
        this.timerPaused = true;
    }

    private void resumeTimer() {
        synchronized (timerLock) {
            this.timerPaused = false;
            timerLock.notifyAll();
        }
    }*/
    
    
    
    
    
}
