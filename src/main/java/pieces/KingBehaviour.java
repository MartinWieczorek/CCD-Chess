package pieces;

import java.util.ArrayList;
import core.Chessboard;
import core.Player;
import core.Square;

/*
|_|_|_|_|_|_|_|_|7
|_|_|_|_|_|_|_|_|6
|_|_|_|_|_|_|_|_|5
|_|_|X|X|X|_|_|_|4
|_|_|X|K|X|_|_|_|3
|_|_|X|X|X|_|_|_|2
|_|_|_|_|_|_|_|_|1
|_|_|_|_|_|_|_|_|0
0 1 2 3 4 5 6 7
*/

public class KingBehaviour implements PieceBehaviour {

	@Override
	public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player) {
		ArrayList<Square> list = new ArrayList<Square>();
        Square sq;
        Square sq1;
        for (int i = square.getPozX() - 1; i <= square.getPozX() + 1; i++)
        {
            for (int y = square.getPozY() - 1; y <= square.getPozY() + 1; y++)
            {
                if (!PieceBehaviour.isout(i, y))
                {//out of bounds protection
                    sq = chessboard.squares[i][y];
                    if (square == sq)
                    {//if we're checking square on which is King
                        continue;
                    }
                    if (PieceBehaviour.checkPiece(i, y, chessboard, player))
                    {//if square is empty
                        if (isSafe(sq, chessboard, square, player))
                        {
                            list.add(sq);
                        }
                    }
                }
            }
        }

        if (!square.piece.wasMotion && isSafe(square, chessboard, square, player))
        {//check if king was not moved before


            if (chessboard.squares[0][square.getPozY()].piece != null
                    && chessboard.squares[0][square.getPozY()].piece.name.equals("Rook"))
            {
                boolean canCastling = true;

                Piece rook = chessboard.squares[0][square.getPozY()].piece;
                if (!rook.wasMotion)
                {
                    for (int i = square.getPozX() - 1; i > 0; i--)
                    {//go left
                        if (chessboard.squares[i][square.getPozY()].piece != null)
                        {
                            canCastling = false;
                            break;
                        }
                    }
                    sq = chessboard.squares[square.getPozX() - 2][square.getPozY()];
                    sq1 = chessboard.squares[square.getPozX() - 1][square.getPozY()];
                    if (canCastling && isSafe(sq, chessboard, square, player) && isSafe(sq1, chessboard, square, player))
                    { //can do castling when none of Sq,sq1 is checked
                        list.add(sq);
                    }
                }
            }
            if (chessboard.squares[7][square.getPozY()].piece != null
                    && chessboard.squares[7][square.getPozY()].piece.name.equals("Rook"))
            {
                boolean canCastling = true;
                Piece rook = chessboard.squares[7][square.getPozY()].piece;
                if (!rook.wasMotion)
                {//if king was not moves before and is not checked
                    for (int i = square.getPozX() + 1; i < 7; i++)
                    {//go right
                        if (chessboard.squares[i][square.getPozY()].piece != null)
                        {//if square is not empty
                            canCastling = false;//cannot castling
                            break; // exit
                        }
                    }
                    sq = chessboard.squares[square.getPozX() + 2][square.getPozY()];
                    sq1 = chessboard.squares[square.getPozX() + 1][square.getPozY()];
                    if (canCastling && isSafe(sq, chessboard, square, player) && isSafe(sq1, chessboard, square, player))
                    {//can do castling when none of Sq, sq1 is checked
                        list.add(sq);
                    }
                }
            }
        }
        return list;
	}
	
    /** Method to check is the king is checked by an opponent
     * @param s Square where is a king
     * @return bool true if king is save, else returns false
     */
	public boolean isSafe(Square s, Chessboard chessboard, Square square, Player player) //A bit confusing code.
    {
        // Rook & Queen
        for (int i = s.getPozY() + 1; i <= 7; ++i) //up
        {
            if (chessboard.squares[s.getPozX()][i].piece == null || chessboard.squares[s.getPozX()][i] == square) //if on this square isn't piece
            {
                continue;
            }
            else if (chessboard.squares[s.getPozX()][i].piece.player != player) //if isn't our piece
            {
                if (chessboard.squares[s.getPozX()][i].piece.name.equals("Rook")
                        || chessboard.squares[s.getPozX()][i].piece.name.equals("Queen"))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        for (int i = s.getPozY() - 1; i >= 0; --i) //down
        {
            if (chessboard.squares[s.getPozX()][i].piece == null || chessboard.squares[s.getPozX()][i] == square) //if on this square isn't piece
            {
                continue;
            }
            else if (chessboard.squares[s.getPozX()][i].piece.player != player) //if isn't our piece
            {
                if (chessboard.squares[s.getPozX()][i].piece.name.equals("Rook")
                        || chessboard.squares[s.getPozX()][i].piece.name.equals("Queen"))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        for (int i = s.getPozX() - 1; i >= 0; --i) //left
        {
            if (chessboard.squares[i][s.getPozY()].piece == null || chessboard.squares[i][s.getPozY()] == square) //if on this square isn't piece
            {
                continue;
            }
            else if (chessboard.squares[i][s.getPozY()].piece.player != player) //if isn't our piece
            {
                if (chessboard.squares[i][s.getPozY()].piece.name.equals("Rook")
                        || chessboard.squares[i][s.getPozY()].piece.name.equals("Queen"))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        for (int i = s.getPozX() + 1; i <= 7; ++i) //right
        {
            if (chessboard.squares[i][s.getPozY()].piece == null || chessboard.squares[i][s.getPozY()] == square) //if on this square isn't piece
            {
                continue;
            }
            else if (chessboard.squares[i][s.getPozY()].piece.player != player) //if isn't our piece
            {
                if (chessboard.squares[i][s.getPozY()].piece.name.equals("Rook")
                        || chessboard.squares[i][s.getPozY()].piece.name.equals("Queen"))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        // Bishop & Queen
        for (int h = s.getPozX() - 1, i = s.getPozY() + 1; !PieceBehaviour.isout(h, i); --h, ++i) //left-up
        {
            if (chessboard.squares[h][i].piece == null || chessboard.squares[h][i] == square) //if on this square isn't piece
            {
                continue;
            }
            else if (chessboard.squares[h][i].piece.player != player) //if isn't our piece
            {
                if (chessboard.squares[h][i].piece.name.equals("Bishop")
                        || chessboard.squares[h][i].piece.name.equals("Queen"))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        for (int h = s.getPozX() - 1, i = s.getPozY() - 1; !PieceBehaviour.isout(h, i); --h, --i) //left-down
        {
            if (chessboard.squares[h][i].piece == null || chessboard.squares[h][i] == square) //if on this square isn't piece
            {
                continue;
            }
            else if (chessboard.squares[h][i].piece.player != player) //if isn't our piece
            {
                if (chessboard.squares[h][i].piece.name.equals("Bishop")
                        || chessboard.squares[h][i].piece.name.equals("Queen"))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        for (int h = s.getPozX() + 1, i = s.getPozY() + 1; !PieceBehaviour.isout(h, i); ++h, ++i) //right-up
        {
            if (chessboard.squares[h][i].piece == null || chessboard.squares[h][i] == square) //if on this square isn't piece
            {
                continue;
            }
            else if (chessboard.squares[h][i].piece.player != player) //if isn't our piece
            {
                if (chessboard.squares[h][i].piece.name.equals("Bishop")
                        || chessboard.squares[h][i].piece.name.equals("Queen"))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        for (int h = s.getPozX() + 1, i = s.getPozY() - 1; !PieceBehaviour.isout(h, i); ++h, --i) //right-down
        {
            if (chessboard.squares[h][i].piece == null || chessboard.squares[h][i] == square) //if on this square isn't piece
            {
                continue;
            }
            else if (chessboard.squares[h][i].piece.player != player) //if isn't our piece
            {
                if (chessboard.squares[h][i].piece.name.equals("Bishop")
                        || chessboard.squares[h][i].piece.name.equals("Queen"))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }

        // Knight
        int newX, newY;

        //1
        newX = s.getPozX() - 2;
        newY = s.getPozY() + 1;

        if (!PieceBehaviour.isout(newX, newY))
        {
            if (chessboard.squares[newX][newY].piece == null) //if on this square isn't piece
            {
            }
            else if (chessboard.squares[newX][newY].piece.player == player) //if is our piece
            {
            }
            else if (chessboard.squares[newX][newY].piece.name.equals("Knight"))
            {
                return false;
            }
        }

        //2
        newX = s.getPozX() - 1;
        newY = s.getPozY() + 2;

        if (!PieceBehaviour.isout(newX, newY))
        {
            if (chessboard.squares[newX][newY].piece == null) //if on this square isn't piece
            {
            }
            else if (chessboard.squares[newX][newY].piece.player == player) //if is our piece
            {
            }
            else if (chessboard.squares[newX][newY].piece.name.equals("Knight"))
            {
                return false;
            }
        }

        //3
        newX = s.getPozX() + 1;
        newY = s.getPozY() + 2;

        if (!PieceBehaviour.isout(newX, newY))
        {
            if (chessboard.squares[newX][newY].piece == null) //if on this square isn't piece
            {
            }
            else if (chessboard.squares[newX][newY].piece.player == player) //if is our piece
            {
            }
            else if (chessboard.squares[newX][newY].piece.name.equals("Knight"))
            {
                return false;
            }
        }

        //4
        newX = s.getPozX() + 2;
        newY = s.getPozY() + 1;

        if (!PieceBehaviour.isout(newX, newY))
        {
            if (chessboard.squares[newX][newY].piece == null) //if on this square isn't piece
            {
            }
            else if (chessboard.squares[newX][newY].piece.player == player) //if is our piece
            {
            }
            else if (chessboard.squares[newX][newY].piece.name.equals("Knight"))
            {
                return false;
            }
        }

        //5
        newX = s.getPozX() + 2;
        newY = s.getPozY() - 1;

        if (!PieceBehaviour.isout(newX, newY))
        {
            if (chessboard.squares[newX][newY].piece == null) //if on this square isn't piece
            {
            }
            else if (chessboard.squares[newX][newY].piece.player == player) //if is our piece
            {
            }
            else if (chessboard.squares[newX][newY].piece.name.equals("Knight"))
            {
                return false;
            }
        }

        //6
        newX = s.getPozX() + 1;
        newY = s.getPozY() - 2;

        if (!PieceBehaviour.isout(newX, newY))
        {
            if (chessboard.squares[newX][newY].piece == null) //if on this square isn't piece
            {
            }
            else if (chessboard.squares[newX][newY].piece.player == player) //if is our piece
            {
            }
            else if (chessboard.squares[newX][newY].piece.name.equals("Knight"))
            {
                return false;
            }
        }

        //7
        newX = s.getPozX() - 1;
        newY = s.getPozY() - 2;

        if (!PieceBehaviour.isout(newX, newY))
        {
            if (chessboard.squares[newX][newY].piece == null) //if on this square isn't piece
            {
            }
            else if (chessboard.squares[newX][newY].piece.player == player) //if is our piece
            {
            }
            else if (chessboard.squares[newX][newY].piece.name.equals("Knight"))
            {
                return false;
            }
        }

        //8
        newX = s.getPozX() - 2;
        newY = s.getPozY() - 1;

        if (!PieceBehaviour.isout(newX, newY))
        {
            if (chessboard.squares[newX][newY].piece == null) //if on this square isn't piece
            {
            }
            else if (chessboard.squares[newX][newY].piece.player == player) //if is our piece
            {
            }
            else if (chessboard.squares[newX][newY].piece.name.equals("Knight"))
            {
                return false;
            }
        }

        // King
        Piece otherKing;
        if (square.piece == chessboard.kingWhite)
        {
            otherKing = chessboard.kingBlack;
        }
        else
        {
            otherKing = chessboard.kingWhite;
        }

        if (s.getPozX() <= otherKing.square.getPozX() + 1
                && s.getPozX() >= otherKing.square.getPozX() - 1
                && s.getPozY() <= otherKing.square.getPozY() + 1
                && s.getPozY() >= otherKing.square.getPozY() - 1)
        {
            return false;
        }

        // Pawn
        if (player.isGoDown()) //check if player "go" down or up
        {//System.out.println("go down");
            newX = s.getPozX() - 1;
            newY = s.getPozY() + 1;
            if (!PieceBehaviour.isout(newX, newY))
            {
                if (chessboard.squares[newX][newY].piece == null) //if on this square isn't piece
                {
                }
                else if (chessboard.squares[newX][newY].piece.player == player) //if is our piece
                {
                }
                else if (chessboard.squares[newX][newY].piece.name.equals("Pawn"))
                {
                    return false;
                }
            }
            newX = s.getPozX() + 1;
            if (!PieceBehaviour.isout(newX, newY))
            {
                if (chessboard.squares[newX][newY].piece == null) //if on this square isn't piece
                {
                }
                else if (chessboard.squares[newX][newY].piece.player == player) //if is our piece
                {
                }
                else if (chessboard.squares[newX][newY].piece.name.equals("Pawn"))
                {
                    return false;
                }
            }
        }
        else
        {//System.out.println("go up");
            newX = s.getPozX() - 1;
            newY = s.getPozY() - 1;
            if (!PieceBehaviour.isout(newX, newY))
            {
                if (chessboard.squares[newX][newY].piece == null) //if on this square isn't piece
                {
                }
                else if (chessboard.squares[newX][newY].piece.player == player) //if is our piece
                {
                }
                else if (chessboard.squares[newX][newY].piece.name.equals("Pawn"))
                {
                    return false;
                }
            }
            newX = s.getPozX() + 1;
            if (!PieceBehaviour.isout(newX, newY))
            {
                if (chessboard.squares[newX][newY].piece == null) //if on this square isn't piece
                {
                }
                else if (chessboard.squares[newX][newY].piece.player == player) //if is our piece
                {
                }
                else if (chessboard.squares[newX][newY].piece.name.equals("Pawn"))
                {
                    return false;
                }
            }
        }

        return true;
    }
}
