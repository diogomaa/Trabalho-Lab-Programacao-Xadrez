package game.controller;

import game.Main;
import game.factory.PieceFactory;
import game.model.ChessBoard;
import game.model.Move;
import game.model.Piece;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.w3c.dom.NodeList;

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
    private Label lblChessTimer;

    @FXML
    private TextArea chatBox;

    @FXML
    private TextField textInput;
    
    @FXML
    public Button closeButton;
    
    @FXML
    public Button yesdraw;
     
    @FXML
    public Button nodraw;
        
    @FXML
    public Button drawask;
    
    @FXML
    public Button exitbotton;
      
    @FXML
    public SplitPane drawsplit;


    @FXML
    private Button btnSend;
    

    private Client client;
    private ChessBoard chessBoard;
    private String playerId;
    private String playerName;
    private String rivalName;
    public static boolean iwantdraw=false;  
    public static boolean wantnodraw=false;
    int countdown= 240;

    Timer timer = new Timer();
    TimerTask task = new TimerTask(){
    
    public void run()
       {
            if(countdown >0)
            countdown--;  
            
            if(countdown > 8){
            Platform.runLater(() ->lblChessTimer.setText("Falta: " + countdown+ " segundos"));}
            else{
            Platform.runLater(() ->lblChessTimer.setText("Atenção! Falta: " + countdown+ " segundos"));
            }
            if(countdown == 0){
            Platform.runLater(() ->lblChessTimer.setText("À espera de jogada!"));
            }
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
    }

    
    public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("view/main_screen.fxml"));
    Parent root = (Parent) loader.load();        
    primaryStage.setResizable(false);

    primaryStage.initStyle(StageStyle.DECORATED);
   
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
}
    @Override
    
    public void initialize(URL location, ResourceBundle resources) {
        setStageTitle("Jogo Xadrez - Jogador: " + playerName +"  --> Id: "+ playerId);
        drawChessPane();
        displayPlayerName();
      
 
      
        if (playerId.equals("2"))   chessPane.setDisable(true);
          lblChessTimer.setText("Falta: 240");
    }

    
    @Override
    public void onChatReceive(String message) {
        chatBox.appendText(message + "\n");
    }

      @FXML
     public void onButtongivup(ActionEvent event) {
     
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        client.closeConnection(playerName + " desistiu! "+ textInput.getText() );
    }
     
      public void onGiveUpReceive(String messages) {
        chessPane.setDisable(true);
        infocurrent.setText(messages );
        yesdraw.setVisible(false);
        nodraw.setVisible(false);
        drawask.setVisible(false);  
        closeButton.setVisible(false);
  
        exitbotton.setVisible(true);
        btnSend.setDisable(true);
        textInput.setDisable(true);        
        
      }

  
      
     
               public void onButtondraw(ActionEvent event) {
            iwantdraw=true;
         client.draw("Pedido de Empate de "+ playerName+ "!!"+ textInput.getText() );
         infocurrent.setText(" Pedido de Empate. Aguade!!" );
         chessPane.setDisable(true);
         drawask.setVisible(false);  
         closeButton.setVisible(false);
         exitbotton.setVisible(false);
      
        
    }
  
      
    public void onDrawReceive(String messagess) {
      
         chessPane.setDisable(true);
        infocurrent.setText(messagess );
         yesdraw.setVisible(true);
         nodraw.setVisible(true);
         drawask.setVisible(false);  
         closeButton.setVisible(false);
         exitbotton.setVisible(false);
     
        
        
    }  

 public void onButtonnodraw(ActionEvent event) {
      
         chessPane.setDisable(false);
         
         wantnodraw=true;
         yesdraw.setVisible(false);
         nodraw.setVisible(false);
         drawask.setVisible(true); 
         closeButton.setVisible(true);
         exitbotton.setVisible(false);
         
         
        
      if (playerId.equals("1")) {
        
             infocurrent.setText("É a sua vez " +playerName+"!");
             client.draw("REJEITADO! É a sua vez " +playerName+"!"+ textInput.getText() );
           
        } else if (playerId.equals("2")) {
      
        
           infocurrent.setText("É a sua vez " +playerName+"!");
           client.draw("REJEITADO! É a sua vez " +playerName+"!"+ textInput.getText() );
        }
        
        
    }     
 
  public void onnodrawReceive(String messagessss) {
      
         chessPane.setDisable(true);
         
         infocurrent.setText(messagessss );
         nodraw.setVisible(false);
         drawask.setVisible(true); 
         closeButton.setVisible(true);
         exitbotton.setVisible(false);
            if (playerId.equals("1")) {
       
             
           
        } else if (playerId.equals("2")) {
      
          
        }
        
    }  
 
 
  public void onButtonyesdraw(ActionEvent event) {
      
       chessPane.setDisable(true);
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        client.draw(playerName + " aceitou o Empate!"+ textInput.getText() );
  
      
     
        
        
    }   
         public void onyesdrawReceive(String messagess) {
   
         chessPane.setDisable(true);
         infocurrent.setText( messagess );
         yesdraw.setVisible(false);
         nodraw.setVisible(false);
         drawask.setVisible(false);  
         closeButton.setVisible(false);
         exitbotton.setVisible(true);
         btnSend.setDisable(true);
        textInput.setDisable(true); 
     
        
        
    }  
          
   public void onwinReceive(String winmessage) {
   
       
         chessPane.setDisable(true);
         infocurrent.setText( winmessage );
         yesdraw.setVisible(false);
         nodraw.setVisible(false);
         drawask.setVisible(false);  
         closeButton.setVisible(false);
         exitbotton.setVisible(true);
         btnSend.setDisable(true);
        textInput.setDisable(true); 
     
        
        
    }  
      
     public void onPawnAskReceive(String pawnmessage) {
   
   
       
         infocurrent.setText(pawnmessage);
      
          yesdraw.setVisible(false);
         nodraw.setVisible(false);
          
         
      
        
    }  
      
 
    
    
     
    @Override
    public void onMoveReceive(String move) {
        chessPane.setDisable(false);
        Move m = new Move(move);
      yesdraw.setVisible(false);
         nodraw.setVisible(false);
            
            drawask.setVisible(false); 
       
        if (playerId.equals("1")) {
            lastMoveP2.setText(m.toBoardMove());
             infocurrent.setText("É a sua vez " +playerName+"!");
             
             
        } else if (playerId.equals("2")) {
            lastMoveP1.setText(m.toBoardMove());
          infocurrent.setText("É a sua vez " +playerName+"!");
        }
        
    
        chessBoard.updateBoard(m);
         countdown = 240;
    }

    @Override
    public void onPieceMove(Move move) {
        chessPane.setDisable(true);
               yesdraw.setVisible(false);
         nodraw.setVisible(false);
            drawask.setVisible(true);  
    
        client.sendMessage(playerId + "_" + move.toString());
        if (playerId.equals("1")) {
            lastMoveP1.setText(move.toBoardMove());
    infocurrent.setText("É a sua vez " +rivalName+"!");
        } else if (playerId.equals("2")) {
            lastMoveP2.setText(move.toBoardMove());
    infocurrent.setText("É a sua vez " +rivalName+"!");
        }
        
         countdown = 240;
    }
    @Override
    public void onPieceChange(int row, int col, String piece){
        client.sendMessage("?-"+row+"-"+col+"-"+piece);
    }
    
    @Override
    public void onChangePieceReceive(String changemessage){
        String[] s = changemessage.split("-");
        int col = Integer.parseInt(s[1]);
        int row = Integer.parseInt(s[2]);
        String[] t = s[3].split("#");
        Piece piece = PieceFactory.getPiece(t[0], t[1]);
        chessBoard.changePiece(col,row,piece);
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
