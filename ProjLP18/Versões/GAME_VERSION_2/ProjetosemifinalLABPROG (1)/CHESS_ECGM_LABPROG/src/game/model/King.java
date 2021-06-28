package game.model;


public class King extends Piece {
    public King(String color) {
        super(color);
    }

    @Override
    public String getName() {
        return "king";
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

         
        if (Math.abs(newRow - oldRow) < 2 && Math.abs(newCol - oldCol) < 2) {
            return true;
        }

        
       
        return false;
    }
}
