package game.model;

import game.factory.PieceFactory;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javax.swing.SwingUtilities;


public class ChessBoard {
    
    @FXML
    private Label lblChessTimer;
    
    
    private Boardpiece[][] boardpieces ;
    private Move lastMove;
    private String playerId;
    private OnPieceMoveListener mListener = null;
    
        
       
    

    public ChessBoard(String playerId) {
        boardpieces = new Boardpiece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boardpieces[row][col] = new Boardpiece(this, row, col);
                setInitPieces(row, col);
            }
        }
        this.playerId = playerId;
        addDragEvent(playerId);
        
        
    }

    public ChessBoard(ChessBoard board) {
        boardpieces = new Boardpiece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boardpieces[row][col] = new Boardpiece(this, row, col);
                if (board.hasPiece(row, col)) {
                    Piece piece = board.getPiece(row, col);
                    boardpieces[row][col].setPiece(PieceFactory.getPiece(piece.getName(), piece.getColor()));
                }
            }
        }
        
        
        
  
        
    }

    public Boardpiece[][] getboardpieces() {
        return this.boardpieces;
    }

    public Boardpiece getboardpiece(int row, int col) {
        return this.boardpieces[row][col];
    }

    public void setLastMove(Move move) {
        this.lastMove = move;
        mListener.onPieceMove(move);
    }

    private void setInitPieces(int row, int col) {
        String pieceName = "";
        String pieceColor = "";

        if (row == 1) {
            pieceName = "pawn";
            pieceColor = Piece.BLACK;
            boardpieces[row][col].setPiece(PieceFactory.getPiece(pieceName, pieceColor));
        } else if (row == 6) {
            pieceName = "pawn";
            pieceColor = Piece.WHITE;
            boardpieces[row][col].setPiece(PieceFactory.getPiece(pieceName, pieceColor));
        } else if (row == 0 || row == 7) {
            if (row == 0)   pieceColor = Piece.BLACK;
            if (row == 7)   pieceColor = Piece.WHITE;

            switch (col) {
                case 0:
                case 7:
                    pieceName = "rook";
                    break;
                case 1:
                case 6:
                    pieceName = "knight";
                    break;
                case 2:
                case 5:
                    pieceName = "bishop";
                    break;
                case 3:
                    pieceName = "queen";
                    break;
                case 4:
                    pieceName = "king";
                    break;
            }

            boardpieces[row][col].setPiece(PieceFactory.getPiece(pieceName, pieceColor));
        }
    }

    public boolean hasPiece(int row, int col) {
        return boardpieces[row][col].hasPiece();
    }

    public Piece getPiece(int row, int col) {
        return boardpieces[row][col].getPiece();
    }

    public void updateBoard(Move move) {
        int oldRow = move.getOldRow();
        int oldCol = move.getOldCol();
        int newRow = move.getNewRow();
        int newCol = move.getNewCol();

        Piece piece = getPiece(oldRow, oldCol);
        getboardpiece(oldRow, oldCol).removePiece();
        if (hasPiece(newRow, newCol)) {
            getboardpiece(newRow, newCol).removePiece();
        }
        getboardpiece(newRow, newCol).setPiece(piece);
    }


    public void addDragEvent(String playerId) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (hasPiece(row, col)) {
                    Piece piece = getPiece(row, col);
                    if ((playerId.equals("1") && piece.getColor().equals(Piece.WHITE))
                            || (playerId.equals("2") && piece.getColor().equals(Piece.BLACK))) {
                        piece.addImageDragEvent();
                    }
                }
            }
        }
    }

    public void setOnPieceMoveListener(OnPieceMoveListener listener) {
        this.mListener = listener;
    }

    public interface OnPieceMoveListener {
        void onPieceMove(Move move);
    }
}
