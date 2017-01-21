package pieces;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import core.ChessboardLogic;
import core.Square;

public class TestPawnGetAllMoves extends TestGetAllMoves
{

	//Scenario testPawnMovement1
		/* X = Pawn Startposition
		 * x = Pawn position after first step
		 * o = movepoints for second step
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
		|-|-|-|o|x|o|_|_|_|_|_|-|-|-|11
		|-|-|-|_|X|_|_|_|_|_|_|-|-|-|12
		|-|-|-|_|_|_|K|_|_|_|_|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  
		*/
	@Test
	public void testPawnMovementOneStep() {
		
		// create solution
		Square[] SolutionPawn = { new Square(4, 10, null), new Square(3, 11, null), new Square(5, 11, null) };
		
		// Pawn position
		int[] p1 = { 4, 10 };
		
		// set other pieces
		testBoard.getSquares()[6][13].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(), new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		
		// set Pawn 
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));
		
		// first move of pawn to cancel twoSteps
		ChessboardLogic.getInstance().move(testBoard, testBoard.getSquares()[p1[0]][p1[1]], testBoard.getSquares()[p1[0]][p1[1] + 1], false, false);
		
		// test movements
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1] + 1].allMoves();
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
		|-|-|-|_|_|P|o|o|X|P|o|-|-|-|12
		|-|-|-|_|_|_|K|_|_|_|_|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  
		*/
	@Test
	public void testPawnMovementTwoSteps() {
		initTest();

		// create solution
		Square[] SolutionPawn = { new Square(6, 12, null), new Square(7, 12, null), new Square(8, 11, null), new Square(8, 10, null), new Square(10, 12, null) };

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
	
	

	//Scenario testPawnMovementAttack
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
		|_|_|_|_|o|x|o|_|_|_|_|_|_|_|08
		|_|_|_|_|_|X|_|_|_|_|_|_|_|_|09
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|10
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|11
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|12
		|-|-|-|_|_|_|K|_|_|_|_|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  
		*/
	@Test
	public void testPawnMovementAttackFront() {
		initTest();

		// create solution
		Square[] SolutionPawn = { new Square(4, 8, null), new Square(6, 8, null), new Square(5, 7, null), new Square(4, 7, null) };

		// Pawn position
		int[] p1 = { 5, 9 };

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
		
		// first move of pawn to cancel twoSteps
		ChessboardLogic.getInstance().move(testBoard, testBoard.getSquares()[p1[0]][p1[1]], testBoard.getSquares()[p1[0]][p1[1] - 1], false, false);
				

		// test movements
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1] - 1].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, SolutionPawn));
		}
	}
	
	

		
}
