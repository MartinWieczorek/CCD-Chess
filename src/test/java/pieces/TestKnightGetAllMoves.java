package pieces;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import core.Square;

public class TestKnightGetAllMoves extends TestGetAllMoves{

	
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
	public void testKnightMovementsInCorner() {
		
		// create solution
		Square[] Solution1 = { new Square(1, 8, null), 
				 new Square(2, 9, null) };

		//knight position
		int[] p1 = { 0, 10 };
		
		// place kings 
		testBoard.getSquares()[6][13].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "King"));
		testBoard.getSquares()[6][0].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "King"));
		
		// test Knight Movements
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Knight"));
		ArrayList<Square>testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, Solution1));
		}
	}
	
	/**
	 * Scenario KnightTest2 in chess 14x14 X - knight, K - king, S - possible
	 * moves of the knight.
	 */
	/*
	 * lower case - white, upper case black A - 10, B - 11, C - 12, D - 13; O -
	 * not usable square
	 * 
	 * |O|O|O|_|_|_|K|_|_|_|_|O|O|O|0 
	 * |O|O|O|_|s|_|_|_|_|_|_|O|O|O|1
	 * |O|O|O|_|_|s|_|_|_|_|_|O|O|O|2 
	 * |_|_|_|x|_|_|_|_|_|_|_|_|_|_|3
	 * |_|s|_|_|_|s|_|_|_|_|_|_|_|_|4 
	 * |_|_|s|_|s|_|_|_|_|_|_|_|_|_|5
	 * |_|_|_|_|_|_|_|_|_|_|_|_|_|_|6 
	 * |_|_|_|_|_|_|_|_|_|_|_|_|_|_|7
	 * |_|_|_|_|_|_|_|_|_|_|_|_|_|_|8 
	 * |_|_|_|_|_|_|_|_|_|_|_|_|_|_|9
	 * |_|_|_|_|_|_|_|_|_|_|_|_|_|_|A 
	 * |O|O|O|_|_|_|_|_|_|_|_|O|O|O|B
	 * |O|O|O|_|_|_|_|_|_|_|_|O|O|O|C 
	 * |O|O|O|_|_|_|k|_|_|_|_|O|O|O|D
	 *  0 1 2 3 4 5 6 7 8 9 A B C D
	 */

	@Test
	public void testKnightMovementOnCorner() {

		// create solution
		Square[] Solution1 = { new Square(4, 1, null), 
				 new Square(5, 2, null),
				 new Square(5, 4, null),
				 new Square(4, 5, null),
				 new Square(2, 5, null),
				 new Square(1, 4, null) };
		
		// knight position
		int[] p1 = { 3, 3 };
		
		// place kings 
		testBoard.getSquares()[6][13].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "King"));
		testBoard.getSquares()[6][0].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "King"));
		
		// test Knight Movements
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "Knight"));
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, Solution1));
		}
	}
	
	
	


	//Scenario testKnightAttack
		/* X = Knight-white
		 * o = movepoints
		 * B = black piece to attack
		 * W = white piece - cannot attack
		|-|-|-|_|_|_|_|K|_|_|_|-|-|-|00
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|01
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|02
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|03
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|04
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|05
		|_|_|_|_|B|_|W|_|_|_|_|_|_|_|06
		|_|_|_|B|_|_|_|B|_|_|_|_|_|_|07
		|_|_|_|_|_|X|_|_|_|_|_|_|_|_|08
		|_|_|_|W|_|_|_|B|_|_|_|_|_|_|09
		|_|_|_|_|W|_|B|_|_|_|_|_|_|_|10
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|11
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|12
		|-|-|-|_|_|_|K|_|_|_|_|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  
		*/
	@Test
	public void testKnightAttack() {

		// create solution
		Square[] Solution1 = { new Square(3, 7, null), new Square(4, 6, null), new Square(7, 7, null), new Square(7, 9, null), new Square(6, 10, null) };

		// Pawn position
		int[] p1 = { 5, 8 };

		// set kings
		testBoard.getSquares()[6][13].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "King"));
		testBoard.getSquares()[7][0].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "King"));
		// set white pieces
		testBoard.getSquares()[3][9].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Pawn"));
		testBoard.getSquares()[4][10].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Pawn"));
		testBoard.getSquares()[6][6].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Pawn"));
		// set black pieces
		testBoard.getSquares()[3][7].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "Pawn"));
		testBoard.getSquares()[4][6].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "Pawn"));
		testBoard.getSquares()[7][7].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "Pawn"));
		testBoard.getSquares()[7][9].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "Pawn"));
		testBoard.getSquares()[6][10].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "Pawn"));
		
		// set Knight
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Knight"));

		// test movements
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, Solution1));
		}
	}
	
	
}
