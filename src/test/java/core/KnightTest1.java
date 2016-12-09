package core;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import pieces.BishopBehaviour;
import pieces.KingBehaviour;
import pieces.KnightBehaviour;
import pieces.PawnBehaviour;
import pieces.Piece;
import pieces.PieceBehaviour;
import pieces.RookBehaviour;

// TODO: Auto-generated Javadoc
/**
 * behavior of knight in 4 person chess - 1. test case with 2 active players in a final position
 * @author Felix Kühner & Peter Schreiber
 * @version Dec 06/2016
 */
public class KnightTest1 {
	
	/**
	 * The Solution 1.
	 *
	 */

	public static Square[] Solution1 = { new Square(1, 8, null), 
										 new Square(2, 9, null) };

	/**
	 * Checks if is solution.
	 *
	 * @param sq the sq
	 * @param Solution the solution
	 * @return true, if is solution
	 */
	// um strin des szenarios erweitern
	private boolean isSolution(Square sq, Square[] Solution) {
		for (int i = 0; i < Solution1.length; i++) {
			if (Solution[i].getPozX() == sq.getPozX() && Solution[i].getPozY() == sq.getPozY())
				return true;			/** @return Test was o.k. */
		}
		return false;					/** @return Test was NOT o.k. */
	}

	/**
	 * Scenario KnightTest1 in chess 14x14
	 * X - knight, K - king, S - possible moves of the knight lower case.
	 */
	/* white, upper case black A - 10, B - 11, C - 12, D - 13; O - not usable
	 * square
	 * 
	 * |O|O|O|_|_|_|K|_|_|_|_|O|O|O|0 
	 * |O|O|O|_|_|_|_|_|_|_|_|O|O|O|1
	 * |O|O|O|_|_|_|_|_|_|_|_|O|O|O|2 
	 * |_|_|_|_|_|_|_|_|_|_|_|_|_|_|3
	 * |_|_|_|_|_|_|_|_|_|_|_|_|_|_|4 
	 * |_|_|_|_|_|_|_|_|_|_|_|_|_|_|5
	 * |_|_|_|_|_|_|_|_|_|_|_|_|_|_|6 
	 * |_|_|_|_|_|_|_|_|_|_|_|_|_|_|7
	 * |_|S|_|_|_|_|_|_|_|_|_|_|_|_|8 
	 * |_|_|S|_|_|_|_|_|_|_|_|_|_|_|9
	 * |x|_|_|_|_|_|_|_|_|_|_|_|_|_|A 
	 * |O|O|O|_|_|_|_|_|_|_|_|O|O|O|B
	 * |O|O|O|_|_|_|_|_|_|_|_|O|O|O|C 
	 * |O|O|O|_|_|_|k|_|_|_|_|O|O|O|D
	 *  0 1 2 3 4 5 6 7 8 9 A B C D
	 */
	
	@Test
	public void testKnight_Scenario1() {
		Game game = new Game();
		Settings settings = new Settings();
		Moves moves_history = new Moves(game);
		Chessboard testBoard = new Chessboard(settings, moves_history);
		//testBoard.setPieces("", settings.playerWhite, settings.playerBlack);
		//knight position
		int[] p1 = { 0, 10 };
		// kings placen
		testBoard.getSquares()[6][13].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] {KingBehaviour.getInstance()}, "King"));
		testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(), new PieceBehaviour[] {KingBehaviour.getInstance()}, "King"));
		// test Knight Movements
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] {KnightBehaviour.getInstance()}, "Knight"));
		ArrayList<Square>testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, Solution1));
		}
	}
}
