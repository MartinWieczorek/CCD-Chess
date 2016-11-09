package pieces;

import java.util.ArrayList;
import core.Chessboard;
import core.Player;
import core.Square;

/**
 * Rook can move:
|_|_|_|X|_|_|_|_|7
|_|_|_|X|_|_|_|_|6
|_|_|_|X|_|_|_|_|5
|_|_|_|X|_|_|_|_|4
|X|X|X|B|X|X|X|X|3
|_|_|_|X|_|_|_|_|2
|_|_|_|X|_|_|_|_|1
|_|_|_|X|_|_|_|_|0
0 1 2 3 4 5 6 7
 *
 */

public class RookBehaviour implements PieceBehaviour {

	@Override
	public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player){
		
		ArrayList<Square> list = new ArrayList<Square>();

        for (int i = square.getPozY() + 1; i <= 7; ++i)
        {//up

            if (PieceBehaviour.checkPiece(square.getPozX(), i, chessboard, player))
            {//if on this square isn't piece

                if (player.getColor() == Player.colors.white)
                {//white

                    if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.getPozX()][i]))
                    {
                        list.add(chessboard.squares[square.getPozX()][i]);
                    }
                }
                else
                {//or black

                    if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.getPozX()][i]))
                    {
                        list.add(chessboard.squares[square.getPozX()][i]);
                    }
                }

                if (PieceBehaviour.otherOwner(square.getPozX(), i, chessboard, player))
                {
                    break;
                }
            }
            else
            {
                break;//we've to break because we cannot go beside other piece!!
            }

        }
        
        for (int i = square.getPozY() - 1; i >= 0; --i)
        {//down

            if (PieceBehaviour.checkPiece(square.getPozX(), i, chessboard, player))
            {//if on this square isn't piece

                if (player.getColor() == Player.colors.white)
                {//white

                    if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.getPozX()][i]))
                    {
                        list.add(chessboard.squares[square.getPozX()][i]);
                    }
                }
                else
                {//or black

                    if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.getPozX()][i]))
                    {
                        list.add(chessboard.squares[square.getPozX()][i]);
                    }
                }

                if (PieceBehaviour.otherOwner(square.getPozX(), i, chessboard, player))
                {
                    break;
                }
            }
            else
            {
                break;//we've to break because we cannot go beside other piece!!
            }
        }

        for (int i = square.getPozX() - 1; i >= 0; --i)
        {//left

            if (PieceBehaviour.checkPiece(i, square.getPozY(), chessboard, player))
            {//if on this square isn't piece

                if (player.getColor() == Player.colors.white)
                {//white

                    if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[i][square.getPozY()]))
                    {
                        list.add(chessboard.squares[i][square.getPozY()]);
                    }
                }
                else
                {//or black

                    if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[i][square.getPozY()]))
                    {
                        list.add(chessboard.squares[i][square.getPozY()]);
                    }
                }

                if (PieceBehaviour.otherOwner(i, square.getPozY(), chessboard, player))
                {
                    break;
                }
            }
            else
            {
                break;//we've to break because we cannot go beside other piece!!
            }
        }
		
        for (int i = square.getPozX() + 1; i <= 7; ++i)
        {//right

            if (PieceBehaviour.checkPiece(i, square.getPozY(), chessboard, player))
            {//if on this square isn't piece

                if (player.getColor() == Player.colors.white)
                {//white

                    if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[i][square.getPozY()]))
                    {
                        list.add(chessboard.squares[i][square.getPozY()]);
                    }
                }
                else
                {//or black

                    if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[i][square.getPozY()]))
                    {
                        list.add(chessboard.squares[i][square.getPozY()]);
                    }
                }

                if (PieceBehaviour.otherOwner(i, square.getPozY(), chessboard, player))
                {
                    break;
                }
            }
            else
            {
                break;//we've to break because we cannot go beside other piece!!
            }
        }

        return list;
	}
}
