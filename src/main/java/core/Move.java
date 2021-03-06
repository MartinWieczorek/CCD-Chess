/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package core;

import core.Moves.castling;
import pieces.Piece;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class to encapsulate a made Move on the chessboard
 * @author matlak
 *
 */
class Move
{
	private static final Logger logger = LogManager.getLogger(Move.class);

    protected Square from = null;
    protected Square to = null;
    protected Piece movedPiece = null;
    protected Piece takenPiece = null;
    protected Piece promotedTo = null;
    protected boolean wasEnPassant = false;
    protected castling castlingMove = castling.none;
    protected boolean wasPawnTwoFieldsMove = false;

    /**
     * Constructor for Move
     * @param from Square with start Positon
     * @param to Destination Square
     * @param movedPiece Piece that was moved 
     * @param takenPiece Piece that was taken by the moved Piece
     * @param castlingMove constant value for a castlingMove
     * @param wasEnPassant true if Move was en Passant
     * @param promotedPiece Piece that was promoted
     */
    public Move(Square from, Square to, Piece movedPiece, Piece takenPiece, castling castlingMove, boolean wasEnPassant, Piece promotedPiece)
    {
    	logger.info("Move-constructor");
        this.from = from;
        this.to = to;

        this.movedPiece = movedPiece;
        this.takenPiece = takenPiece;

        this.castlingMove = castlingMove;
        this.wasEnPassant = wasEnPassant;

        if (movedPiece.getName().equals("Pawn") && Math.abs(to.getPozY() - from.getPozY()) == 2)
        {
            this.wasPawnTwoFieldsMove = true;
        }
        else if (movedPiece.getName().equals("Pawn") && to.getPozY() == Chessboard.getBottom() || to.getPozY() == Chessboard.getTop() && promotedPiece != null)
        {
            this.promotedTo = promotedPiece;
        }
    }

    public Square getFrom()
    {
        return this.from;
    }

    public Square getTo()
    {
        return this.to;
    }

    public Piece getMovedPiece()
    {
        return this.movedPiece;
    }

    public Piece getTakenPiece()
    {
        return this.takenPiece;
    }

    public boolean wasEnPassant()
    {
        return this.wasEnPassant;
    }

    public boolean wasPawnTwoFieldsMove()
    {
        return this.wasPawnTwoFieldsMove;
    }

    public castling getCastlingMove()
    {
        return this.castlingMove;
    }

    public Piece getPromotedPiece()
    {
        return this.promotedTo;
    }
}
