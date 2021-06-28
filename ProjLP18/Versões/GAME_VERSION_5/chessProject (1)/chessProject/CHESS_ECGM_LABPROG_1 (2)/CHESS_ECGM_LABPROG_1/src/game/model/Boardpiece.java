package game.model;

import game.controller.MultiplayerModeController;
import game.factory.PieceFactory;
import game.network.Client;
import java.util.Optional;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Boardpiece implements Piece.OnDragCompleteListener {
    public static final String DARK = "dark";
    public static final String LIGHT = "light";
    
      @FXML
    private Label infocurrent;
      
      @FXML
    private TextField textInput;
      
        private Boardpiece[][] boardpieces ;
    private ChessBoard board;
    private Pane pane;
    private String color;
    private Piece piece;
    private int row, col;
    private Client client;
    
    
    public Boardpiece(ChessBoard board, int row, int col) {
        this.board = board;
        this.row = row;
        this.col = col;
        this.pane = new Pane();
        if ((row % 2 == 0 && col % 2 == 1) || (row % 2 == 1 && col % 2 == 0)) {
            this.color = DARK;
            this.pane.setStyle("-fx-background-color: #1389C8");
        } else {
            this.color = LIGHT;
            this.pane.setStyle("-fx-background-color: #F37723");
        }

        createDragDropEvent();
    }

    @Override
    public void onDragComplete() {
        removePiece();
    }

  
    public static boolean iChangePiece =false;
    private void createDragDropEvent() {
        this.pane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                
                if (event.getGestureSource() != pane &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }

                event.consume();
            }
        });

        this.pane.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                  Dragboard db = event.getDragboard();
                boolean success = false;

                if(db.hasString()) {
                    
                    String[] pieceInfo = db.getString().split("_");
                    int oldRow = Integer.parseInt(pieceInfo[2]);
                    int oldCol = Integer.parseInt(pieceInfo[3]);
                    Piece newPiece = PieceFactory.getPiece(pieceInfo[1], pieceInfo[0]);
                    newPiece.setboardpieceOn(board.getboardpiece(oldRow, oldCol));
                     
                    if (newPiece.isLegalMove(board, row, col)) {
                   
                        success = true;
                    }
                }
                if (success) {
                    pane.setStyle("-fx-background-color: #669438");
                }else{
                    pane.setStyle("-fx-background-color: #b50b0b");
                }
                
                
                event.consume();
            }
        });
        
        
        this.pane.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (color.equals(DARK)) {
                    pane.setStyle("-fx-background-color: #1389C8");
                } else {
                    pane.setStyle("-fx-background-color: #F37723");
                }

                event.consume();
            }
        });

        this.pane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;

                if(db.hasString()) {
                    
                    String[] pieceInfo = db.getString().split("_");
                    int oldRow = Integer.parseInt(pieceInfo[2]);
                    int oldCol = Integer.parseInt(pieceInfo[3]);
                    Piece newPiece = PieceFactory.getPiece(pieceInfo[1], pieceInfo[0]);
                    newPiece.setboardpieceOn(board.getboardpiece(oldRow, oldCol));
                    if (newPiece.isLegalMove(board, row, col)) {
                        if (hasPiece()) {

                            if(piece.getName()=="king"){
                 
                            Client.Instance.winyes("Adversário ganhou o jogo!");
                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Xeque-mate!");
                            alert.setHeaderText("Parabéns ganhas-te o jogo conseguiste comer o Rei do teu adversário!");                           
                            alert.setContentText("Esperemos que voltes a jogar...");
                            ButtonType buttonTypeOne = new ButtonType("Sair");
                            alert.getButtonTypes().setAll(buttonTypeOne);
                      
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == buttonTypeOne){
                                
                                System.exit(0);       
                            } 
                            }else{  
                                removePiece();}
                                
                        }
                        
                  
                       
                          
                    
                        
                        newPiece.addImageDragEvent();
                        setPiece(newPiece);
                        
                        board.setLastMove(new Move(oldRow, oldCol, row, col));
                        success = true;
                        
                       
                    }
              if(  getRow()==0 && piece.getName().equals("pawn") && piece.getColor().equals(Piece.WHITE)){ 
                          
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("PROMOÇÃO DO PEÃO A OUTRA PEÇA...");
                    alert.setHeaderText("Carregue no botão com a peça que deseja substituir...");
                    alert.setContentText("Escolha a sua peça!");

                    ButtonType buttonTypeOne = new ButtonType("Bispo");
                    ButtonType buttonTypeTwo = new ButtonType("Torre");
                    ButtonType buttonTypeThree = new ButtonType("Rainha");
                    ButtonType buttonTypeFour = new ButtonType("Cavalo");
                    

                    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeFour);
                         
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeOne){
                        removePiece();
                        Piece newPieces = PieceFactory.getPiece("bishop", Piece.WHITE);
                        newPieces.addImageDragEvent();
                        
                        iChangePiece=true;
                        board.setNewPiece(row, col, "bishop#"+Piece.WHITE);
                        board.setLastMove(new Move(oldRow, oldCol, row, col));
                        success = true;
                       setPiece(newPieces);
                    
                    } else if (result.get() == buttonTypeTwo) {
                        removePiece();
                        Piece newPieces = PieceFactory.getPiece("rook", Piece.WHITE);
                        newPieces.setboardpieceOn(board.getboardpiece(oldRow, oldCol));
                        newPieces.addImageDragEvent();
                       
                        iChangePiece=true;
                        board.setNewPiece(row, col, "rook#"+Piece.WHITE);
                        board.setLastMove(new Move(oldRow, oldCol, row, col));
                        success = true;
                        setPiece(newPieces);
                    } else if (result.get() == buttonTypeThree) {
                        removePiece();
                        Piece newPieces = PieceFactory.getPiece("queen", Piece.WHITE);
                        newPieces.setboardpieceOn(board.getboardpiece(oldRow, oldCol));
                        newPieces.addImageDragEvent();
                       
                        iChangePiece=true;
                        board.setNewPiece(row, col, "queen#"+Piece.WHITE);
                        board.setLastMove(new Move(oldRow, oldCol, row, col));
                        success = true;
                        setPiece(newPieces);
                    } else if (result.get() == buttonTypeFour){
                        removePiece();
                        Piece newPieces = PieceFactory.getPiece("knight", Piece.WHITE);
                        newPieces.setboardpieceOn(board.getboardpiece(oldRow, oldCol));
                        newPieces.addImageDragEvent();
                       
                        iChangePiece=true;
                        board.setNewPiece(row, col, "knight#"+Piece.WHITE);
                        board.setLastMove(new Move(oldRow, oldCol, row, col));
                        success = true;
                        setPiece(newPieces);
                    }
                   }else if(  getRow()==7 && piece.getName().equals("pawn") && piece.getColor().equals(Piece.BLACK)){ 
                          
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("PROMOÇÃO DO PEÃO A OUTRA PEÇA...");
                    alert.setHeaderText("Carregue no botão com a peça que deseja substituir...");
                    alert.setContentText("Escolha a sua peça!");

                    ButtonType buttonTypeOne = new ButtonType("Bispo");
                    ButtonType buttonTypeTwo = new ButtonType("Torre");
                    ButtonType buttonTypeThree = new ButtonType("Rainha");
                    ButtonType buttonTypeFour = new ButtonType("Cavalo");
                    

                    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeFour);
                         
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeOne){
                       removePiece();
                       Piece newPieces = PieceFactory.getPiece("bishop", Piece.BLACK);
                       newPieces.setboardpieceOn(board.getboardpiece(oldRow, oldCol));
                       newPieces.addImageDragEvent();
                       
                       iChangePiece=true;
                       board.setNewPiece(row, col, "bishop#"+Piece.BLACK);
                       board.setLastMove(new Move(oldRow, oldCol, row, col));
                       success = true;
                      setPiece(newPieces);
                        
                    } else if (result.get() == buttonTypeTwo) {
                        removePiece();
                        Piece newPieces = PieceFactory.getPiece("rook", Piece.BLACK);
                        newPieces.setboardpieceOn(board.getboardpiece(oldRow, oldCol));
                        newPieces.addImageDragEvent();
                       
                        iChangePiece=true;
                        board.setNewPiece(row, col, "rook#"+Piece.BLACK);
                        board.setLastMove(new Move(oldRow, oldCol, row, col));
                        success = true;
                        setPiece(newPieces);
                    } else if (result.get() == buttonTypeThree) {
                        removePiece();
                        Piece newPieces = PieceFactory.getPiece("queen", Piece.BLACK);
                        newPieces.setboardpieceOn(board.getboardpiece(oldRow, oldCol));
                        newPieces.addImageDragEvent();
                        
                        iChangePiece=true;
                        board.setNewPiece(row, col, "queen#"+Piece.BLACK);
                        board.setLastMove(new Move(oldRow, oldCol, row, col));
                        success = true;
                        setPiece(newPieces);
                    } else if (result.get() == buttonTypeFour){
                        removePiece();
                        Piece newPieces = PieceFactory.getPiece("knight", Piece.BLACK);
                        newPieces.setboardpieceOn(board.getboardpiece(oldRow, oldCol));
                        newPieces.addImageDragEvent();
                        
                        iChangePiece=true;
                        board.setNewPiece(row, col, "knight#"+Piece.BLACK);
                        board.setLastMove(new Move(oldRow, oldCol, row, col));
                        success = true;
                        setPiece(newPieces);
                    }
                  
                        }        
                            
                
                }

                event.setDropCompleted(success);
                event.consume();
            }
        });
    }

    public boolean hasPiece() {
      
        return (this.piece != null);
    }
    
    public boolean hasPiecePawnBlack() {
        if (piece.getName().equals("pawn") && piece.getColor().equals(Piece.BLACK)) {
        return (this.piece != null); 
        }else{
           return false; 
        }}
     
      public boolean hasPiecePawnWhite() {
        if (piece.getName().equals("pawn") && piece.getColor().equals(Piece.WHITE)) {
        return (this.piece != null); 
        }else{
           return false; 
        }}

    public Piece getPiece() {
        return this.piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        this.piece.setboardpieceOn(this);
        this.pane.getChildren().add(piece.getImage());
        this.piece.setOnDragCompleteListener(this);
    }
    
    public void changePiece(Piece piece){
        this.piece = piece;
        this.piece.setboardpieceOn(this);
        this.pane.getChildren().add(piece.getImage());
        this.piece.setOnDragCompleteListener(this);
    }

    public void removePiece() {
        this.pane.getChildren().remove(this.piece.getImage());
        this.piece = null;
      
    }

    public Pane getPane() {
        return this.pane;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
