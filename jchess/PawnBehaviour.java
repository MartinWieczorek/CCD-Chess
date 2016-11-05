package jchess;

import java.util.ArrayList;
/*
|_|_|_|_|_|_|_|_|7
|_|_|_|_|_|_|_|_|6
|_|_|_|X|_|_|_|_|5
|_|_|_|X|_|_|_|_|4
|_|_|_|P|_|_|_|_|3
|_|_|_|_|_|_|_|_|2
|_|_|_|_|_|_|_|_|1
|_|_|_|_|_|_|_|_|0
0 1 2 3 4 5 6 7
 *
 * Move of a pawn:
|_|_|_|_|_|_|_|_|7
|_|_|_|_|_|_|_|_|6
|_|_|_|_|_|_|_|_|5
|_|_|_|X|_|_|_|_|4
|_|_|_|P|_|_|_|_|3
|_|_|_|_|_|_|_|_|2
|_|_|_|_|_|_|_|_|1
|_|_|_|_|_|_|_|_|0
0 1 2 3 4 5 6 7
 * Beats with can take pawn:
|_|_|_|_|_|_|_|_|7
|_|_|_|_|_|_|_|_|6
|_|_|_|_|_|_|_|_|5
|_|_|X|_|X|_|_|_|4
|_|_|_|P|_|_|_|_|3
|_|_|_|_|_|_|_|_|2
|_|_|_|_|_|_|_|_|1
|_|_|_|_|_|_|_|_|0
0 1 2 3 4 5 6 7
 */

public class PawnBehaviour implements PieceBehaviour {

	@Override
	public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player) {
		ArrayList<Square> list = new ArrayList<Square>();
        Square sq;
        Square sq1;
        int first = square.pozY - 1;//number where to move
        int second = square.pozY - 2;//number where to move (only in first move)
        if (player.goDown)
        {//check if player "go" down or up
            first = square.pozY + 1;//if yes, change value
            second = square.pozY + 2;//if yes, change value
        }
        if (PieceBehaviour.isout(first, first))
        {//out of bounds protection
            return list;//return empty list
        }
        sq = chessboard.squares[square.pozX][first];
        if (sq.piece == null)
        {//if next is free
            //list.add(sq);//add
            if (player.color == Player.colors.white)
            {//white

                if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.pozX][first]))
                {
                    list.add(chessboard.squares[square.pozX][first]);
                }
            }
            else
            {//or black

                if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.pozX][first]))
                {
                    list.add(chessboard.squares[square.pozX][first]);
                }
            }

            if ((player.goDown && square.pozY == 1) || (!player.goDown && square.pozY == 6))
            {
                sq1 = chessboard.squares[square.pozX][second];
                if (sq1.piece == null)
                {
                    //list.add(sq1);//only in first move
                    if (player.color == Player.colors.white)
                    {//white

                        if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.pozX][second]))
                        {
                            list.add(chessboard.squares[square.pozX][second]);
                        }
                    }
                    else
                    {//or black

                        if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.pozX][second]))
                        {
                            list.add(chessboard.squares[square.pozX][second]);
                        }
                    }
                }
            }
        }
        if (!PieceBehaviour.isout(square.pozX - 1, square.pozY)) //out of bounds protection
        {
            //capture
            sq = chessboard.squares[square.pozX - 1][first];
            if (sq.piece != null)
            {//check if can hit left
                if (player != sq.piece.player && !sq.piece.name.equals("King"))
                {
                    if (player.color == Player.colors.white)
                    {//white

                        if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.pozX - 1][first]))
                        {
                            list.add(chessboard.squares[square.pozX - 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.pozX - 1][first]))
                        {
                            list.add(chessboard.squares[square.pozX - 1][first]);
                        }
                    }
                }
            }

            //En passant
            sq = chessboard.squares[square.pozX - 1][square.pozY];
            if (sq.piece != null
                    && chessboard.twoSquareMovedPawn != null
                    && sq == chessboard.twoSquareMovedPawn.square)
            {//check if can hit left
                if (player != sq.piece.player && !sq.piece.name.equals("King"))
                {// unnecessary

                    //list.add(sq);
                    if (player.color == Player.colors.white)
                    {//white

                        if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.pozX - 1][first]))
                        {
                            list.add(chessboard.squares[square.pozX - 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.pozX - 1][first]))
                        {
                            list.add(chessboard.squares[square.pozX - 1][first]);
                        }
                    }
                }
            }
        }
        if (!PieceBehaviour.isout(square.pozX + 1, square.pozY))
        {//out of bounds protection

            //capture
            sq = chessboard.squares[square.pozX + 1][first];
            if (sq.piece != null)
            {//check if can hit right
                if (player != sq.piece.player && !sq.piece.name.equals("King"))
                {
                    //list.add(sq);
                    if (player.color == Player.colors.white)
                    { //white

                        if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.pozX + 1][first]))
                        {
                            list.add(chessboard.squares[square.pozX + 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.pozX + 1][first]))
                        {
                            list.add(chessboard.squares[square.pozX + 1][first]);
                        }
                    }
                }
            }

            //En passant
            sq = chessboard.squares[square.pozX + 1][square.pozY];
            if (sq.piece != null
                    && chessboard.twoSquareMovedPawn != null
                    && sq == chessboard.twoSquareMovedPawn.square)
            {//check if can hit left
                if (player != sq.piece.player && !sq.piece.name.equals("King"))
                {// unnecessary

                    //list.add(sq);
                    if (player.color == Player.colors.white)
                    {//white

                        if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.pozX + 1][first]))
                        {
                            list.add(chessboard.squares[square.pozX + 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.pozX + 1][first]))
                        {
                            list.add(chessboard.squares[square.pozX + 1][first]);
                        }
                    }
                }
            }
        }

        return list;
	}

}
