package pieces;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import core.Square;

public class RookGetAllMovesTest extends GetAllMovesTest{


	//Scenario testRookMovement1
		/* X = Rook
		 * o = movepoints
		|-|-|-|_|_|o|_|K|_|_|_|-|-|-|00
		|-|-|-|_|_|o|_|_|_|_|_|-|-|-|01
		|-|-|-|_|_|o|_|_|_|_|_|-|-|-|02
		|_|_|_|_|_|o|_|_|_|_|_|_|_|_|03
		|_|_|_|_|_|o|_|_|_|_|_|_|_|_|04
		|_|_|_|_|_|o|_|_|_|_|_|_|_|_|05
		|_|_|_|_|_|o|_|_|_|_|_|_|_|_|06
		|_|_|_|_|_|o|_|_|_|_|_|_|_|_|07
		|o|o|o|o|o|X|o|o|o|o|o|o|o|o|08
		|_|_|_|_|_|o|_|_|_|_|_|_|_|_|09
		|_|_|_|_|_|o|_|_|_|_|_|_|_|_|10
		|-|-|-|_|_|o|_|_|_|_|_|-|-|-|11
		|-|-|-|_|_|o|_|_|_|_|_|-|-|-|12
		|-|-|-|_|_|o|K|_|_|_|_|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  
		*/
	@Test
	public void testRookStandartMovement() {
		initTest();

		// create solution
		Square[] SolutionRook = { new Square(4, 8, null), new Square(3, 8, null), new Square(2, 8, null),
				new Square(1, 8, null), new Square(0, 8, null), new Square(6, 8, null), new Square(7, 8, null),
				new Square(8, 8, null), new Square(9, 8, null), new Square(10, 8, null), new Square(11, 8, null),
				new Square(12, 8, null), new Square(13, 8, null), new Square(5, 9, null), new Square(5, 10, null),
				new Square(5, 11, null), new Square(5, 12, null), new Square(5, 13, null), new Square(5, 7, null),
				new Square(5, 6, null), new Square(5, 5, null), new Square(5, 4, null), new Square(5, 3, null),
				new Square(5, 2, null), new Square(5, 1, null), new Square(5, 0, null) };
		
		// Pawn position
		int[] p1 = { 5, 8 };
		
		// set other pieces
		testBoard.getSquares()[6][13].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "King"));
		testBoard.getSquares()[6][0].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "King"));
		
		// set Pawn 
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Rook"));
		
		// test movements
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, SolutionRook));
		}

	}
	
	
	//Scenario testRookMovement2
			/* X = Rook
			 * o = movepoints
			|-|-|-|o|_|_|_|K|_|_|_|-|-|-|00
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
			|-|-|-|o|_|_|_|_|_|_|_|-|-|-|11
			|-|-|-|o|_|_|_|_|_|_|_|-|-|-|12
			|-|-|-|X|o|o|K|_|_|_|_|-|-|-|13
			0 1 2 3 4 5 6 7 8 9 10  12 
			                      11  13  
			*/
		@Test
		public void testRookMovementInCorner() {
			initTest();

		// create solution
		Square[] SolutionRook = { new Square(3, 12, null), new Square(3, 11, null), new Square(3, 10, null),
				new Square(3, 9, null), new Square(3, 8, null), new Square(3, 7, null), new Square(3, 6, null),
				new Square(3, 5, null), new Square(3, 4, null), new Square(3, 3, null), new Square(3, 2, null),
				new Square(3, 1, null), new Square(3, 0, null), new Square(4, 13, null), new Square(5, 13, null) };
			
			// Pawn position
			int[] p1 = { 3, 13 };
			
			// set other pieces
			testBoard.getSquares()[6][13].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "King"));
			testBoard.getSquares()[6][0].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "King"));
			
			// set Pawn 
			testBoard.getSquares()[p1[0]][p1[1]].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Rook"));
			
			// test movements
			ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
			for (Square sq : testMoves) {
				assertTrue(isSolution(sq, SolutionRook));
			}

		}
		
		
		//Scenario testRookMovement2
		/* X = Rook - white
		 * B = black piece
		 * w = white piece 
		 * o = movepoints
		|-|-|-|_|_|_|_|K|_|_|_|-|-|-|00
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|01
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|02
		|_|_|_|_|_|_|_|_|_|_|_|o|_|_|03
		|_|_|_|_|_|_|_|B|o|o|o|X|w|_|04
		|_|_|_|_|_|_|_|_|_|_|_|o|_|_|05
		|_|_|_|_|_|_|_|_|_|_|_|w|_|_|06
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
	public void testRookAttack() {
		initTest();

	// create solution
	Square[] SolutionRook = { new Square(11, 3, null), new Square(11, 5, null), new Square(10, 4, null),
			new Square(9, 4, null), new Square(8, 4, null), new Square(7, 4, null) };
		
		// Pawn position
		int[] p1 = { 11, 4 };
		
		// set other pieces
		testBoard.getSquares()[6][13].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "King"));
		testBoard.getSquares()[6][0].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "King"));
			// set white Pieces
		testBoard.getSquares()[12][4].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Pawn"));
		testBoard.getSquares()[11][6].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Pawn"));
			// set black Pieces
		testBoard.getSquares()[7][4].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "Pawn"));
		
		
		// set Rook 
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Rook"));
		
		// test movements
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, SolutionRook));
		}

	}

}
