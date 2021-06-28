package game.model;


public class Queen extends Piece{
    public Queen(String color) {
        super(color);
    }

    @Override
    public String getName() {
        return "queen";
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
