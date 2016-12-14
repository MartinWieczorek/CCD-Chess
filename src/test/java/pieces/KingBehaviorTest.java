package pieces;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import core.Chessboard;
import core.Game;
import core.Moves;
import core.Settings;
import core.Square;

public class KingBehaviorTest {

	Game game;
	Settings settings;
	Moves moves_history;
	Chessboard testBoard;
	
	private void initTest() {
		game = new Game();
		settings = game.getSettings();
		moves_history = new Moves(game);
		testBoard = game.getChessboard();
	}
	
	// um strin des szenarios erweitern
	private boolean isSolution(Square sq, Square[] Solution) {
		// System.out.println("x: " + sq.getPozX() + " y: " + sq.getPozY());
		for (int i = 0; i < Solution.length; i++) {
			if (Solution[i].getPozX() == sq.getPozX() && Solution[i].getPozY() == sq.getPozY())
				return true;
		}
		System.out.println("First error on Pos x: " + sq.getPozX() + " y: " + sq.getPozY());
		return false;
	}

	//Scenario testKingMovement1
		/* X = King white
		 * K = King black
		 * o = movepoints
		|-|-|-|_|_|_|_|K|_|_|_|-|-|-|00
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|01
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|02
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|03
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|04
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|05
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|06
		|_|_|_|_|o|o|o|_|_|_|_|_|_|_|07
		|_|_|_|_|o|X|o|_|_|_|_|_|_|_|08
		|_|_|_|_|o|o|o|_|_|_|_|_|_|_|09
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|10
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|11
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|12
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  
		*/
	@Test
	public void testKingMovement1() {
		initTest();

		// create solution
		Square[] SolutionKing = { new Square(5, 7, null), new Square(6, 7, null), new Square(6, 8, null),
				new Square(6, 9, null), new Square(5, 9, null), new Square(4, 9, null), new Square(4, 8, null),
				new Square(4, 7, null) };
		
		// Pawn position
		int[] p1 = { 5, 8 };
		
		// set other pieces
		testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(), new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		
		// set King white 
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		
		// test movements
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, SolutionKing));
		}

	}
	
	
	

	//Scenario testKingMovement2
		/* X = King white
		 * K = King black
		 * w = white piece
		 * o = movepoints
		|-|-|-|_|_|_|_|K|_|_|_|-|-|-|00
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|01
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|02
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|03
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|04
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|05
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|06
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|07
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|08
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|09
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|10
		|-|-|-|_|_|_|_|_|_|o|o|-|-|-|11
		|-|-|-|_|_|_|_|_|_|o|X|-|-|-|12
		|-|-|-|_|_|_|_|_|_|w|o|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  
		*/
	@Test
	public void testKingMovement2() {
		initTest();

		// create solution
		Square[] SolutionKing = { new Square(10, 13, null), new Square(9, 12, null), new Square(9, 11, null),
				new Square(10, 11, null) };
		
		// Pawn position
		int[] p1 = { 10, 12 };
		
		// set other pieces
		testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(), new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		testBoard.getSquares()[9][13].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));
		
		
		// set King white ip
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		
		// test movements
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, SolutionKing));
		}

	}
	
	

	//Scenario testKingAttack
		/* X = King white
		 * K = King black
		 * w = white piece
		 * P = black Pawn
		 * R = black Rook
		 * o = movepoints
		|-|-|-|_|_|_|_|K|_|_|_|-|-|-|00
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|01
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|02
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|03
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|04
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|05
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|06
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|07
		|_|_|_|_|_|_|_|R|_|_|_|_|_|_|08
		|_|_|_|_|_|_|_|P|X|W|_|_|_|_|09
		|_|_|_|_|_|_|_|o|W|W|_|_|_|_|10
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|11
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|12
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  
		*/
	@Test
	public void testKingAttack() {
		initTest();

		// create solution
		Square[] SolutionKing = { new Square(7, 10, null), new Square(7, 8, null) };
		
		// Pawn position
		int[] p1 = { 8, 9 };
		
		// set other pieces
		testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(), new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		  	// white Pawns 
		testBoard.getSquares()[8][10].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));
		testBoard.getSquares()[9][10].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));
		testBoard.getSquares()[9][9].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));
			// black Pawn and Rook
		testBoard.getSquares()[7][9].setPiece(new Piece(testBoard, settings.getPlayerBlack(), new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));
		testBoard.getSquares()[7][8].setPiece(new Piece(testBoard, settings.getPlayerBlack(), new PieceBehaviour[] { RookBehaviour.getInstance() }, "Rook"));
		
		// set King white 
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		
		// test movements
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, SolutionKing));
		}

	}
}
