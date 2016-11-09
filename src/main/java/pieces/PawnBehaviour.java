package pieces;

import java.util.ArrayList;
import core.Chessboard;
import core.Player;
import core.Square;

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
        int first = square.getPozY() - 1;//number where to move
        int second = square.getPozY() - 2;//number where to move (only in first move)
        if (player.isGoDown())
        {//check if player "go" down or up
            first = square.getPozY() + 1;//if yes, change value
            second = square.getPozY() + 2;//if yes, change value
        }
        if (PieceBehaviour.isout(first, first))
        {//out of bounds protection
            return list;//return empty list
        }
        sq = chessboard.squares[square.getPozX()][first];
        if (sq.piece == null)
        {//if next is free
            //list.add(sq);//add
            if (player.getColor() == Player.colors.white)
            {//white

                if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.getPozX()][first]))
                {
                    list.add(chessboard.squares[square.getPozX()][first]);
                }
            }
            else
            {//or black

                if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.getPozX()][first]))
                {
                    list.add(chessboard.squares[square.getPozX()][first]);
                }
            }

            if ((player.isGoDown() && square.getPozY() == 1) || (!player.isGoDown() && square.getPozY() == 6))
            {
                sq1 = chessboard.squares[square.getPozX()][second];
                if (sq1.piece == null)
                {
                    //list.add(sq1);//only in first move
                    if (player.getColor() == Player.colors.white)
                    {//white

                        if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.getPozX()][second]))
                        {
                            list.add(chessboard.squares[square.getPozX()][second]);
                        }
                    }
                    else
                    {//or black

                        if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.getPozX()][second]))
                        {
                            list.add(chessboard.squares[square.getPozX()][second]);
                        }
                    }
                }
            }
        }
        if (!PieceBehaviour.isout(square.getPozX() - 1, square.getPozY())) //out of bounds protection
        {
            //capture
            sq = chessboard.squares[square.getPozX() - 1][first];
            if (sq.piece != null)
            {//check if can hit left
                if (player != sq.piece.player && !sq.piece.name.equals("King"))
                {
                    if (player.getColor() == Player.colors.white)
                    {//white

                        if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.getPozX() - 1][first]))
                        {
                            list.add(chessboard.squares[square.getPozX() - 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.getPozX() - 1][first]))
                        {
                            list.add(chessboard.squares[square.getPozX() - 1][first]);
                        }
                    }
                }
            }

            //En passant
            sq = chessboard.squares[square.getPozX() - 1][square.getPozY()];
            if (sq.piece != null
                    && chessboard.twoSquareMovedPawn != null
                    && sq == chessboard.twoSquareMovedPawn.square)
            {//check if can hit left
                if (player != sq.piece.player && !sq.piece.name.equals("King"))
                {// unnecessary

                    //list.add(sq);
                    if (player.getColor() == Player.colors.white)
                    {//white

                        if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.getPozX() - 1][first]))
                        {
                            list.add(chessboard.squares[square.getPozX() - 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.getPozX() - 1][first]))
                        {
                            list.add(chessboard.squares[square.getPozX() - 1][first]);
                        }
                    }
                }
            }
        }
        if (!PieceBehaviour.isout(square.getPozX() + 1, square.getPozY()))
        {//out of bounds protection

            //capture
            sq = chessboard.squares[square.getPozX() + 1][first];
            if (sq.piece != null)
            {//check if can hit right
                if (player != sq.piece.player && !sq.piece.name.equals("King"))
                {
                    //list.add(sq);
                    if (player.getColor() == Player.colors.white)
                    { //white

                        if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.getPozX() + 1][first]))
                        {
                            list.add(chessboard.squares[square.getPozX() + 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.getPozX() + 1][first]))
                        {
                            list.add(chessboard.squares[square.getPozX() + 1][first]);
                        }
                    }
                }
            }

            //En passant
            sq = chessboard.squares[square.getPozX() + 1][square.getPozY()];
            if (sq.piece != null
                    && chessboard.twoSquareMovedPawn != null
                    && sq == chessboard.twoSquareMovedPawn.square)
            {//check if can hit left
                if (player != sq.piece.player && !sq.piece.name.equals("King"))
                {// unnecessary

                    //list.add(sq);
                    if (player.getColor() == Player.colors.white)
                    {//white

                        if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.getPozX() + 1][first]))
                        {
                            list.add(chessboard.squares[square.getPozX() + 1][first]);
                        }
                    }
                    else
                    {//or black

                        if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[square.getPozX() + 1][first]))
                        {
                            list.add(chessboard.squares[square.getPozX() + 1][first]);
                        }
                    }
                }
            }
        }

        return list;
	}

}
