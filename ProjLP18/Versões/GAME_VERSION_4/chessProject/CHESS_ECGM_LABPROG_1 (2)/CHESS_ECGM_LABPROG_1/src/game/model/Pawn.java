package game.model;

import game.network.Client;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;



public class Pawn extends Piece{
    public Pawn(String color) {
        super(color);
    }
    
    private Client client;  
    private Piece piece;
    
     public static ChessBoard Instance;
    @Override
    public String getName() {
        return "pawn";
    }

    @Override
    public boolean isLegalMove(ChessBoard b, int newRow, int newCol) {
        int oldRow = this.boardpieceOn.getRow();
        int oldCol = this.boardpieceOn.getCol();
          
         
        if (b.hasPiece(newRow, newCol)) {
            if (b.getPiece(newRow, newCol).getColor().equals(getColor())) {
                return false;
            }
        }

        if (this.getColor().equals(Piece.WHITE)) {
            if (oldRow == 6) {   
                if (Math.abs(newCol - oldCol) == 1 && newRow == oldRow - 1 && b.hasPiece(newRow, newCol)) {
                    return true;
                } else if (newCol == oldCol && newRow == oldRow - 1 && !b.hasPiece(newRow, newCol)) {
                    return true;
                } else if (newCol == oldCol && newRow == oldRow - 2 && !b.hasPiece(newRow, newCol) && !b.hasPiece(newRow + 1, newCol)) {
                    
                    return true;
               
                }
               
                return false;
            } else  if ( oldRow == 3 ) { //"en passant"
                if (Math.abs(newCol - oldCol) == 1 && newRow == oldRow - 1 && b.hasPiece(newRow, newCol)) {
                    return true;
                } else if (newCol == oldCol && newRow == oldRow - 1 && !b.hasPiece(newRow, newCol)) {
                    return true;
                    
                  }else if (newCol == oldCol - 1 && newRow == oldRow - 1  && b.hasPiece(newRow + 1, newCol ) && piece.getName().equals("pawn") ) {
                  
                      return true;
                }else if (newCol == oldCol + 1 && newRow == oldRow - 1  && b.hasPiece(newRow + 1, newCol ) && piece.getName().equals("pawn") ) {
                    return true;
                }
               
              
               
                return false; 
            }else  if ( oldRow == 4) { //"promover peão"
                if (Math.abs(newCol - oldCol) == 1 && newRow == oldRow - 1 && b.hasPiece(newRow, newCol)) {
                    
                    return true;
                    
                   
                } else if (newCol == oldCol && newRow == oldRow - 1 && !b.hasPiece(newRow, newCol)) {
                   
                    return true;
                }
                
             
                      
                return false;
            }else  {   
                if (Math.abs(newCol - oldCol) == 1 && newRow == oldRow - 1 && b.hasPiece(newRow, newCol)) {
                    return true;
                } else if (newCol == oldCol && newRow == oldRow - 1 && !b.hasPiece(newRow, newCol)) {
                    return true;
                }
                return false;
            }
        } else {
            if (oldRow == 1) {   
                if (Math.abs(newCol - oldCol) == 1 && newRow == oldRow + 1 && b.hasPiece(newRow, newCol)) {
                    return true;
                } else if (newCol == oldCol && newRow == oldRow + 1 && !b.hasPiece(newRow, newCol)) {
                    return true;
                } else if (newCol == oldCol  && newRow == oldRow + 2 && !b.hasPiece(newRow, newCol) && !b.hasPiece(newRow - 1, newCol)) {
                    return true;
                }
                return false;
            } else  if (oldRow == 4 ) { //"en passant"
               if (Math.abs(newCol - oldCol) == 1 && newRow == oldRow + 1 && b.hasPiece(newRow, newCol)) {
                    return true;
                } else if (newCol == oldCol && newRow == oldRow + 1 && !b.hasPiece(newRow, newCol)) {
                    return true;
                } else if (newCol == oldCol - 1 && newRow == oldRow + 1  && b.hasPiece(newRow - 1, newCol )) {
                    return true;
                }else if (newCol == oldCol + 1 && newRow == oldRow + 1  && b.hasPiece(newRow - 1, newCol )) {
                    return true;
                }
               
                return false;
            }else {   
                if (Math.abs(newCol - oldCol) == 1 && newRow == oldRow + 1 && b.hasPiece(newRow, newCol)) {
                    return true;
                } else if (newCol == oldCol && newRow == oldRow + 1 && !b.hasPiece(newRow, newCol)) {
                    return true;
                }
                return false;
            }
        }
    }
}
