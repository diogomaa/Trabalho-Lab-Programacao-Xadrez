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

        
        if (this.color.equals(Piece.WHITE)) {
            if (oldRow == 7 && oldCol == 4 && newRow == 7 && newCol == 6) {
                if (b.hasPiece(7, 7)) {
                    if (b.getPiece(7, 7).getName().equals("rook")
                            && !b.hasPiece(7, 5)) {
                        return true;
                    }
                }
            } else if (oldRow == 7 && oldCol == 4 && newRow == 7 && newCol == 2) {
                if (b.hasPiece(7, 0)) {
                    if (b.getPiece(7, 0).getName().equals("rook")
                            && !b.hasPiece(7, 1)
                            && !b.hasPiece(7, 3)) {
                        return true;
                    }
                }
            }
        } else if (this.color.equals(Piece.BLACK)) {
            if (oldRow == 0 && oldCol == 4 && newRow == 0 && newCol == 6) {
                if (b.hasPiece(0, 7)) {
                    if (b.getPiece(0, 7).getName().equals("rook")
                            && !b.hasPiece(0, 5)) {
                        return true;
                    }
                }
            } else if (oldRow == 0 && oldCol == 4 && newRow == 0 && newCol == 2) {
                if (b.hasPiece(0, 0)) {
                    if (b.getPiece(0, 0).getName().equals("rook")
                            && !b.hasPiece(0, 1)
                            && !b.hasPiece(0, 3)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
