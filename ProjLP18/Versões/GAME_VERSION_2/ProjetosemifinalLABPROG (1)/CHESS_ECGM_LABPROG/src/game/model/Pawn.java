package game.model;


public class Pawn extends Piece{
    public Pawn(String color) {
        super(color);
    }

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
            } else {   
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
                } else if (newCol == oldCol && newRow == oldRow + 2 && !b.hasPiece(newRow, newCol) && !b.hasPiece(newRow - 1, newCol)) {
                    return true;
                }
                return false;
            } else {   
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
