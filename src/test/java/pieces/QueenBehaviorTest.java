/**
 * 
 */
package pieces;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import core.Chessboard;
import core.Game;
import core.Moves;
import core.Settings;
import core.Square;

/** fundamental test cases to behavior of a queen
 * @author peter
 * @version Dec 10/2016
 */
public class QueenBehaviorTest {

	Game game;
	Settings settings;
	Moves moves_history;
	Chessboard testBoard;
	
	private void initTest() {
		game = new Game();
		settings = new Settings();
		moves_history = new Moves(game);
		testBoard = new Chessboard(settings, moves_history);
	}
	
	// um string des szenarios erweitern
	private boolean isSolution(Square sq, Square[] Solution) {
		for (int i = 0; i < Solution.length; i++) {
			if (Solution[i].getPozX() == sq.getPozX() && Solution[i].getPozY() == sq.getPozY())
				return true;
		}
		System.out.println("First error on Pos x: " + sq.getPozX() + " y: " + sq.getPozY());
		return false;
	}

	/** Scenario testQueenMovement1 with fundamentals and one attack to a black pawn
		/* X = white Queen and o = her movepoints
		 * K = king black and P = king white
		 * P = pawn black and p = pawn white
		 */
		/* 
		|-|-|-|_|_|o|K|_|_|_|_|-|-|-|00
		|-|-|-|_|_|o|_|_|_|_|_|-|-|-|01
		|-|-|-|_|_|o|_|_|_|_|_|-|-|-|02
		|_|_|_|_|_|o|_|_|_|_|_|_|_|_|03
		|_|_|_|_|_|o|_|_|_|_|_|_|_|_|04
		|_|_|_|_|_|o|_|_|P|_|_|_|_|_|05
		|_|_|_|_|_|o|_|o|_|_|_|_|_|_|06
		|_|_|_|_|_|o|o|_|_|_|_|_|_|_|07
		|o|o|o|o|o|X|o|o|o|o|o|o|o|o|08
		|_|_|_|_|o|o|_|_|_|_|_|_|_|_|09
		|_|_|_|p|_|o|_|_|_|_|_|_|_|_|10
		|-|-|-|_|_|o|_|_|_|_|_|-|-|-|11
		|-|-|-|_|_|p|_|_|_|_|_|-|-|-|12
		|-|-|-|_|_|_|k|_|_|_|_|-|-|-|13
		 0 1 2 3 4 5 6 7 8 9 10  12 
		                       11  13  
		*/
	@Test
	public void testQueenMovement1() {
		initTest();

		// create solution
		Square[] SolutionQueen = { new Square(4, 8, null), new Square(3, 8, null), new Square(2, 8, null),
				new Square(1, 8, null), new Square(0, 8, null), 
				new Square(6, 8, null), new Square(7, 8, null), new Square(8, 8, null), new Square(9, 8, null), 
				new Square(10, 8, null), new Square(11, 8, null), new Square(12, 8, null), new Square(13, 8, null), 
				new Square(5, 9, null), new Square(5, 10, null), new Square(5, 11, null), 
				new Square(5, 7, null), new Square(5, 6, null), new Square(5, 5, null), new Square(5, 4, null), new Square(5, 3, null),
				new Square(5, 2, null), new Square(5, 1, null), new Square(5, 0, null),
				new Square(4, 9, null), new Square(6, 7, null), new Square(7, 6, null), new Square(8, 5, null) };
		
		// queen position
		int[] p1 = { 5, 8 };
		
		// set kings
		testBoard.getSquares()[6][13].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(), new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		
		// set white pawns
		testBoard.getSquares()[3][10].setPiece(new Piece(testBoard, settings.getPlayerWhite(),new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));
		testBoard.getSquares()[5][12].setPiece(new Piece(testBoard, settings.getPlayerWhite(),new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));
		testBoard.getSquares()[8][5].setPiece(new Piece(testBoard, settings.getPlayerBlack(),new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));			
	
		// set Queen 
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { BishopBehaviour.getInstance(), RookBehaviour.getInstance() }, "Queen"));
	
		// test movements
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, SolutionQueen));
		}

	}
	
	/** Scenario testQueenMovement2 with more fundamentals
	 * X = white Queen and o = her movepoints
	 * K = king black and P = king white
	 * P = pawn black and p = pawn white
	 */
	/*
			|-|-|-|o|_|_|K|_|_|_|_|-|-|-|00
			|-|-|-|o|_|_|_|_|_|_|_|-|-|-|01
			|-|-|-|o|_|_|_|_|_|_|_|-|-|-|02
			|_|_|_|o|_|_|_|_|_|_|_|_|_|_|03
			|_|_|_|o|_|_|_|_|_|_|_|_|_|_|04
			|_|_|_|o|_|_|_|_|_|_|_|_|_|_|05
			|_|_|_|o|_|_|_|_|_|_|_|_|_|_|06
			|_|_|_|o|_|_|_|_|_|_|_|_|_|_|07
			|_|_|_|o|_|_|_|_|_|_|_|_|_|_|08
			|_|_|_|o|_|_|_|_|_|_|_|_|_|_|09
			|_|_|_|o|_|_|_|_|_|_|_|_|_|_|10
			|-|-|-|o|_|p|_|_|_|_|_|-|-|-|11
			|-|-|-|o|o|_|_|_|_|_|_|-|-|-|12
			|-|-|-|X|o|o|k|_|_|_|_|-|-|-|13
			 0 1 2 3 4 5 6 7 8 9 10  12 
			                      11  13  
			*/
		@Test
		public void testQueenMovement2() {
			initTest();

		// create solution
		Square[] SolutionQueen = { new Square(3, 12, null), new Square(3, 11, null), new Square(3, 10, null),
				new Square(3, 9, null), new Square(3, 8, null), new Square(3, 7, null), new Square(3, 6, null),
				new Square(3, 5, null), new Square(3, 4, null), new Square(3, 3, null), new Square(3, 2, null),
				new Square(3, 1, null), new Square(3, 0, null), 
				new Square(4, 13, null), new Square(5, 13, null), 
				new Square(4, 12, null) };
			
			// queen position
			int[] p1 = { 3, 13 };
			
			// set kings
			testBoard.getSquares()[6][13].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
			testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(), new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
			// set white pawn
			testBoard.getSquares()[5][11].setPiece(new Piece(testBoard, settings.getPlayerWhite(),new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));
		
			// set Queen 
			testBoard.getSquares()[p1[0]][p1[1]].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { BishopBehaviour.getInstance(), RookBehaviour.getInstance() }, "Queen"));
			
			// test movements
			ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
			for (Square sq : testMoves) {
				assertTrue(isSolution(sq, SolutionQueen));
			}

		}
		
		
		/** Scenario testQueenMovement2 with attack to two black pawns
		 * X = white Queen and o = her movepoints
		 * K = king black and P = king white
		 * P = pawn black and p = pawn white
		 */
		/*
		|-|-|-|_|_|_|_|K|_|_|_|-|-|-|00
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|01
		|-|-|-|_|_|_|_|_|_|P|_|-|-|-|02
		|_|_|_|_|_|_|_|_|_|_|o|o|_|_|03
		|_|_|_|_|_|_|_|P|o|o|o|X|p|_|04
		|_|_|_|_|_|_|_|_|_|_|_|o|o|_|05
		|_|_|_|_|_|_|_|_|_|_|_|p|_|o|06
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|07
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|08
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|09
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|10
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|11
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|12
		|-|-|-|_|_|_|K|_|_|_|_|-|-|-|13
		 0 1 2 3 4 5 6 7 8 9 10  12 
		                       11  13  
		*/
	@Test
	public void testQueenAttack() {
		initTest();

	// create solution
	Square[] SolutionQueen = { new Square(11, 3, null), new Square(11, 5, null), 
			new Square(10, 4, null), new Square(9, 4, null), new Square(8, 4, null), new Square(7, 4, null),
			new Square(13, 6, null), new Square(12, 5, null), new Square(10, 3, null), new Square(9, 2, null) };
		
		// Queen position
		int[] p1 = { 11, 4 };
		
		// set other pieces
		testBoard.getSquares()[6][13].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(), new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
			// set white Pieces
		testBoard.getSquares()[12][4].setPiece(new Piece(testBoard, settings.getPlayerWhite(),new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));
		testBoard.getSquares()[11][6].setPiece(new Piece(testBoard, settings.getPlayerWhite(),new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));
			// set black Pieces
		testBoard.getSquares()[7][4].setPiece(new Piece(testBoard, settings.getPlayerBlack(),new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));
		
		
		// set Queen 
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { BishopBehaviour.getInstance(), RookBehaviour.getInstance() }, "Queen"));
		
		// test movements
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, SolutionQueen));
		}

	}

}
