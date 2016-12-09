package pieces;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import core.Chessboard;
import core.Game;
import core.Moves;
import core.Settings;
import core.Square;

public class PawnBehaviorTest 
{
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

	//Scenario testPawnMovement1
		/* X = Pawn
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
		|_|_|_|_|o|_|_|_|_|_|_|_|_|_|10
		|-|-|-|o|X|o|_|_|_|_|_|-|-|-|11
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|12
		|-|-|-|_|_|_|K|_|_|_|_|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  
		*/
	@Test
	public void testPawnMovement1() {
		initTest();

		// create solution
		Square[] SolutionPawn = { new Square(4, 10, null), new Square(3, 11, null), new Square(5, 11, null) };
		
		// Pawn position
		int[] p1 = { 4, 11 };
		
		// set other pieces
		testBoard.getSquares()[6][13].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(), new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		
		// set Pawn 
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));
		
		// test movements
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, SolutionPawn));
		}

	}
	
	
	
	
	//Scenario testPawnMovementTwoSteps
		/* X = Pawn
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
		|_|_|_|_|_|_|_|_|o|_|_|_|_|_|10
		|-|-|-|_|_|_|_|_|o|_|_|-|-|-|11
		|-|-|-|_|_|P|o|o|X|P|_|-|-|-|12
		|-|-|-|_|_|_|K|_|_|_|_|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  
		*/
	@Test
	public void testPawnMovementTwoSteps() {
		initTest();

		// create solution
		Square[] SolutionPawn = { new Square(6, 12, null), new Square(7, 12, null), new Square(8, 11, null), new Square(8, 10, null) };

		// Pawn position
		int[] p1 = { 8, 12 };

		// set other pieces
		testBoard.getSquares()[6][13].setPiece(new Piece(testBoard, settings.getPlayerWhite(),
				new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(),
				new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		testBoard.getSquares()[5][12].setPiece(new Piece(testBoard, settings.getPlayerWhite(),
				new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));
		testBoard.getSquares()[9][12].setPiece(new Piece(testBoard, settings.getPlayerWhite(),
				new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));

		// set Pawn
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(new Piece(testBoard, settings.getPlayerWhite(),
				new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));

		// test movements
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, SolutionPawn));
		}
	}
	
	

	//Scenario testPawnMovementTwoSteps
		/* X = Pawn-white
		 * o = movepoints
		 * B = black piece to attack
		 * W = white piece - cannot attack
		|-|-|-|_|_|_|_|K|_|_|_|-|-|-|00
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|01
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|02
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|03
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|04
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|05
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|06
		|_|_|_|_|B|o|W|_|_|_|_|_|_|_|07
		|_|_|_|_|o|X|o|_|_|_|_|_|_|_|08
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|09
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|10
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|11
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|12
		|-|-|-|_|_|_|K|_|_|_|_|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  
		*/
	@Test
	public void testPawnMovementAttack() {
		initTest();

		// create solution
		Square[] SolutionPawn = { new Square(4, 8, null), new Square(6, 8, null), new Square(5, 7, null), new Square(4, 7, null) };

		// Pawn position
		int[] p1 = { 5, 8 };

		// set other pieces
		testBoard.getSquares()[6][13].setPiece(new Piece(testBoard, settings.getPlayerWhite(),
				new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(),
				new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		testBoard.getSquares()[4][7].setPiece(new Piece(testBoard, settings.getPlayerBlack(),
				new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));
		testBoard.getSquares()[6][7].setPiece(new Piece(testBoard, settings.getPlayerWhite(),
				new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));

		// set Pawn
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(new Piece(testBoard, settings.getPlayerWhite(),
				new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));

		// test movements
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, SolutionPawn));
		}
	}
	
	

		
}
