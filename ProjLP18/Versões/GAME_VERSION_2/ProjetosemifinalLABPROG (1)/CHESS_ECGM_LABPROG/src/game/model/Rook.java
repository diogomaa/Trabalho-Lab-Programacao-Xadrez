package game.model;


public class Rook extends Piece {
    public Rook(String color) {
        super(color);
    }

    @Override
    public String getName() {
        return "rook";
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

        
        

          
        if (newRow == oldRow) {
            if (newCol > oldCol) {
                for (int i = oldCol + 1; i < newCol; i++) {
                    if (b.hasPiece(oldRow, i)) {
                        return false;
                    }
                }
                return true;
            }
            if (newCol < oldCol) {
                for (int i = oldCol - 1; i > newCol; i--) {
                    if (b.hasPiece(oldRow, i)) {
                        return false;
                    }
                }
                return true;
            }
            if (newCol == oldCol) {
                return true;
            }
            return true;
        }

          
        if (newCol == oldCol) {
            if (newRow > oldRow) {
                for (int i = oldRow + 1; i < newRow; i++) {
                    if (b.hasPiece(i, oldCol)) {
                        return false;
                    }
                }
                return true;
            }
            if (newRow < oldRow) {
                for (int i = oldRow - 1; i > newRow; i--) {
                    if (b.hasPiece(i, oldCol)) {
                        return false;
                    }
                }
                return true;
            }
            return true;
        }

        return false;
    }
}
