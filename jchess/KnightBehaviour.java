package jchess;

import java.util.ArrayList;

// knight all moves
//  _______________ Y:
// |_|_|_|_|_|_|_|_|7
// |_|_|_|_|_|_|_|_|6
// |_|_|2|_|3|_|_|_|5
// |_|1|_|_|_|4|_|_|4
// |_|_|_|K|_|_|_|_|3
// |_|8|_|_|_|5|_|_|2
// |_|_|7|_|6|_|_|_|1
// |_|_|_|_|_|_|_|_|0
//X:0 1 2 3 4 5 6 7
//

public class KnightBehaviour implements PieceBehaviour {

	@Override
	public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player) {
		ArrayList<Square> list = new ArrayList<Square>();
        int newX, newY;

        //1
        newX = square.pozX - 2;
        newY = square.pozY + 1;

        if (!PieceBehaviour.isout(newX, newY) && PieceBehaviour.checkPiece(newX, newY, chessboard, player))
        {
            if (player.color == Player.colors.white) //white
            {
                if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //2
        newX = square.pozX - 1;
        newY = square.pozY + 2;

        if (!PieceBehaviour.isout(newX, newY) && PieceBehaviour.checkPiece(newX, newY, chessboard, player))
        {
            if (player.color == Player.colors.white) //white
            {
                if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //3
        newX = square.pozX + 1;
        newY = square.pozY + 2;

        if (!PieceBehaviour.isout(newX, newY) && PieceBehaviour.checkPiece(newX, newY, chessboard, player))
        {
            if (player.color == Player.colors.white) //white
            {
                if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //4
        newX = square.pozX + 2;
        newY = square.pozY + 1;

        if (!PieceBehaviour.isout(newX, newY) && PieceBehaviour.checkPiece(newX, newY, chessboard, player))
        {
            if (player.color == Player.colors.white) //white
            {
                if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //5
        newX = square.pozX + 2;
        newY = square.pozY - 1;

        if (!PieceBehaviour.isout(newX, newY) && PieceBehaviour.checkPiece(newX, newY, chessboard, player))
        {
            if (player.color == Player.colors.white) //white
            {
                if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //6
        newX = square.pozX + 1;
        newY = square.pozY - 2;

        if (!PieceBehaviour.isout(newX, newY) && PieceBehaviour.checkPiece(newX, newY, chessboard, player))
        {
            if (player.color == Player.colors.white) //white
            {
                if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //7
        newX = square.pozX - 1;
        newY = square.pozY - 2;

        if (!PieceBehaviour.isout(newX, newY) && PieceBehaviour.checkPiece(newX, newY, chessboard, player))
        {
            if (player.color == Player.colors.white) //white
            {
                if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        //8
        newX = square.pozX - 2;
        newY = square.pozY - 1;

        if (!PieceBehaviour.isout(newX, newY) && PieceBehaviour.checkPiece(newX, newY, chessboard, player))
        {
            if (player.color == Player.colors.white) //white
            {
                if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
            else //or black
            {
                if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                {
                    list.add(chessboard.squares[newX][newY]);
                }
            }
        }

        return list;
	}

}
