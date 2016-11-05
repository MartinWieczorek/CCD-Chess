package jchess;

import java.util.ArrayList;

/**
 * Bishop can move:
|_|_|_|_|_|_|_|X|7
|X|_|_|_|_|_|X|_|6
|_|X|_|_| |X|_|_|5
|_|_|X|_|X|_|_|_|4
|_|_|_|B|_|_|_|_|3
|_| |X|_|X|_|_|_|2
|_|X|_|_|_|X|_|_|1
|X|_|_|_|_|_|X|_|0
0 1 2 3 4 5 6 7
 */

public class BishopBehaviour implements PieceBehaviour {

	@Override
	public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player) {
		
		ArrayList<Square> list = new ArrayList<Square>();

	        for (int h = square.pozX - 1, i = square.pozY + 1; !PieceBehaviour.isout(h, i); --h, ++i) //left-up
	        {
	            if (PieceBehaviour.checkPiece(h, i, chessboard, player)) //if on this square isn't piece
	            {
	                if (player.color == Player.colors.white) //white
	                {
	                    if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[h][i]))
	                    {
	                        list.add(chessboard.squares[h][i]);
	                    }
	                }
	                else //or black
	                {
	                    if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[h][i]))
	                    {
	                        list.add(chessboard.squares[h][i]);
	                    }
	                }

	                if (PieceBehaviour.otherOwner(h, i, chessboard, player))
	                {
	                    break;
	                }
	            }
	            else
	            {
	                break;//we've to break because we cannot go beside other piece!!
	            }
	        }

	        for (int h = square.pozX - 1, i = square.pozY - 1; !PieceBehaviour.isout(h, i); --h, --i) //left-down
	        {
	            if (PieceBehaviour.checkPiece(h, i, chessboard, player)) //if on this square isn't piece
	            {
	                if (player.color == Player.colors.white) //white
	                {
	                    if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[h][i]))
	                    {
	                        list.add(chessboard.squares[h][i]);
	                    }
	                }
	                else //or black
	                {
	                    if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[h][i]))
	                    {
	                        list.add(chessboard.squares[h][i]);
	                    }
	                }

	                if (PieceBehaviour.otherOwner(h, i, chessboard, player))
	                {
	                    break;
	                }
	            }
	            else
	            {
	                break;//we've to break because we cannot go beside other piece!!
	            }
	        }

	        for (int h = square.pozX + 1, i = square.pozY + 1; !PieceBehaviour.isout(h, i); ++h, ++i) //right-up
	        {
	            if (PieceBehaviour.checkPiece(h, i, chessboard, player)) //if on this square isn't piece
	            {
	                if (player.color == Player.colors.white) //white
	                {
	                    if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[h][i]))
	                    {
	                        list.add(chessboard.squares[h][i]);
	                    }
	                }
	                else //or black
	                {
	                    if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[h][i]))
	                    {
	                        list.add(chessboard.squares[h][i]);
	                    }
	                }

	                if (PieceBehaviour.otherOwner(h, i, chessboard, player))
	                {
	                    break;
	                }
	            }
	            else
	            {
	                break;//we've to break because we cannot go beside other piece!!
	            }
	        }

	        for (int h = square.pozX + 1, i = square.pozY - 1; !PieceBehaviour.isout(h, i); ++h, --i) //right-down
	        {
	            if (PieceBehaviour.checkPiece(h, i, chessboard, player)) //if on this square isn't piece
	            {
	                if (player.color == Player.colors.white) //white
	                {
	                    if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[h][i]))
	                    {
	                        list.add(chessboard.squares[h][i]);
	                    }
	                }
	                else //or black
	                {
	                    if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[h][i]))
	                    {
	                        list.add(chessboard.squares[h][i]);
	                    }
	                }

	                if (PieceBehaviour.otherOwner(h, i, chessboard, player))
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
