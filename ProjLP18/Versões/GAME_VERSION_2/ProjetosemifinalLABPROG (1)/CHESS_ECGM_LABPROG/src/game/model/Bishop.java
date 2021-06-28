package game.model;


public class Bishop extends Piece{
    public Bishop(String color) {
        super(color);
    }

    @Override
    public String getName() {
        return "bishop";
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

        
        if (Math.abs(newRow - oldRow) == Math.abs(newCol - oldCol)) {
             
            if (newRow > oldRow && newCol > oldCol) {
                for (int i = 1; i < newRow - oldRow; i++) {
                    if (b.hasPiece(oldRow + i, oldCol + i)) {
                        return false;
                    }
                }
                return true;
            }

              
            if (newRow < oldRow && newCol > oldCol) {
                for (int i = 1; i < newCol - oldCol; i++) {
                    if (b.hasPiece(oldRow - i, oldCol + i)) {
                        return false;
                    }
                }
                return true;
            }

              
            if (newRow > oldRow && newCol < oldCol) {
                for (int i = 1; i < newRow - oldRow; i++) {
                    if (b.hasPiece(oldRow + i, oldCol - i)) {
                        return false;
                    }
                }
                return true;
            }

             
            if (newRow < oldRow && newCol < oldCol) {
                for (int i = 1; i < oldCol - newCol; i++) {
                    if (b.hasPiece(oldRow - i, oldCol - i)) {
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
