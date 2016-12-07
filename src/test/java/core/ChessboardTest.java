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

public class ChessboardTest {
	
	//Szenarien bspw. als Konstanten speichern 
	public static Square[] Solution1 = {new Square(4, 1, null),
										new Square(5, 2, null),
										new Square(5, 4, null),
										new Square(4, 5, null),
										new Square(2, 5, null),
										new Square(1, 4, null)
										};
	
	//um strin des szenarios erweitern
	private boolean isSolution(Square sq, Square[] Solution){
		//System.out.println("x: " + sq.getPozX() + " y: " + sq.getPozY());
		for(int i = 0; i< Solution.length; i++ ){
			if(Solution[i].getPozX() == sq.getPozX() && Solution[i].getPozY() == sq.getPozY())
				return true;
		}
		System.out.println("x: " + sq.getPozX() + " y: " + sq.getPozY());
		return false;
	}
	
	//Scenario1 KnightTest1
	/* X = Knight
	|-|-|-|_|_|_|_|K|_|_|_|-|-|-|00
	|-|-|-|_|o|_|_|_|_|_|_|-|-|-|01
	|-|-|-|_|_|o|_|_|_|_|_|-|-|-|02
	|_|_|_|X|_|_|_|_|_|_|_|_|_|_|03
	|_|o|_|_|_|o|_|_|_|_|_|_|_|_|04
	|_|_|o|_|o|_|_|_|_|_|_|_|_|_|05
	|_|_|_|_|_|_|_|_|_|_|_|_|_|_|06
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
	public void testKnight_Scenario1() {
		Game game = new Game();
		Settings settings = new Settings();
		Moves moves_history = new Moves(game);
		Chessboard testBoard = new Chessboard(settings, moves_history);
		//testBoard.setPieces("", settings.playerWhite, settings.playerBlack);
		//knight position
		int[] p1 = {3, 3};
		//kings placen
		testBoard.getSquares()[6][13].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] {KingBehaviour.getInstance()}, "King"));
		testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(), new PieceBehaviour[] {KingBehaviour.getInstance()}, "King"));
		//test Knight Movements
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] {KnightBehaviour.getInstance()}, "Knight"));
		ArrayList<Square>testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for(Square sq : testMoves){
			assertTrue(isSolution(sq, Solution1));
		}
		
	}
	
	
	//Szenarien bspw. als Konstanten speichern 
		public static Square[] SolutionPawn1 = {new Square(4, 9, null),
												new Square(4, 8, null),
												new Square(2, 10, null),
												new Square(3, 10, null),
												new Square(5, 10, null),
												new Square(6, 10, null)
												};
	
	//Scenario1 PawnTest1
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
		|_|_|_|_|o|_|_|_|_|_|_|_|_|_|08
		|_|_|_|_|o|_|_|_|_|_|_|_|_|_|09
		|_|_|o|o|X|o|o|_|_|_|_|_|_|_|10
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|11
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|12
		|-|-|-|_|_|_|K|_|_|_|_|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10 	12 
		                      11  13  
		*/
	@Test
	public void testPawn_Scenario1() {
		Game game = new Game();
		Settings settings = new Settings();
		Moves moves_history = new Moves(game);
		Chessboard testBoard = new Chessboard(settings, moves_history);
		// testBoard.setPieces("", settings.playerWhite, settings.playerBlack);
		// knight position
		int[] p1 = { 4, 12 };
		// kings placen
		testBoard.getSquares()[6][13].setPiece(new Piece(testBoard, settings.getPlayerWhite(),
				new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(),
				new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		// test Knight Movements
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(
				new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			System.out.println("sol: x"+sq.getPozX()+" y:"+sq.getPozY());
			assertTrue(isSolution(sq, SolutionPawn1));
		}

	}

	//Szenarien bspw. als Konstanten speichern 
			public static Square[] SolutionPawn2 = {new Square(4, 11, null),
													new Square(4, 10, null),
													};
		
		//Scenario1 PawnTest1
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
			|-|-|-|_|o|_|_|_|_|_|_|-|-|-|11
			|-|-|-|_|X|_|_|_|_|_|_|-|-|-|12
			|-|-|-|_|_|_|K|_|_|_|_|-|-|-|13
			0 1 2 3 4 5 6 7 8 9 10 	12 
			                      11  13  
			*/
		@Test
		public void testPawn_Scenario2() {
			Game game = new Game();
			Settings settings = new Settings();
			Moves moves_history = new Moves(game);
			Chessboard testBoard = new Chessboard(settings, moves_history);
			// testBoard.setPieces("", settings.playerWhite, settings.playerBlack);
			// knight position
			int[] p1 = { 4, 12 };
			// kings placen
			testBoard.getSquares()[6][13].setPiece(new Piece(testBoard, settings.getPlayerWhite(),
					new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
			testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(),
					new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
			// test Knight Movements
			testBoard.getSquares()[p1[0]][p1[1]].setPiece(
					new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { PawnBehaviour.getInstance() }, "Pawn"));
			ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
			for (Square sq : testMoves) {
				System.out.println("sol: x"+sq.getPozX()+" y:"+sq.getPozY());
				assertTrue(isSolution(sq, SolutionPawn2));
			}

		}
		
	// Szenarien bspw. als Konstanten speichern
	public static Square[] SolutionRook1 = { new Square(4, 10, null), new Square(4, 9, null), new Square(4, 8, null),
			new Square(4, 7, null), new Square(4, 6, null), new Square(4, 5, null), new Square(4, 4, null),
			new Square(4, 3, null), new Square(4, 2, null), new Square(4, 1, null), new Square(4, 0, null),
			new Square(3, 11, null), new Square(5, 11, null), new Square(6, 11, null), new Square(7, 11, null),
			new Square(8, 11, null), new Square(9, 11, null), new Square(10, 11, null), new Square(4, 12, null),
			new Square(4, 13, null) };
			
	//Scenario1 RookTest1
	/* X = Rook
	 * o = movepoints
	|-|-|-|_|o|_|_|K|_|_|_|-|-|-|00
	|-|-|-|_|o|_|_|_|_|_|_|-|-|-|01
	|-|-|-|_|o|_|_|_|_|_|_|-|-|-|02
	|_|_|_|_|o|_|_|_|_|_|_|_|_|_|03
	|_|_|_|_|o|_|_|_|_|_|_|_|_|_|04
	|_|_|_|_|o|_|_|_|_|_|_|_|_|_|05
	|_|_|_|_|o|_|_|_|_|_|_|_|_|_|06
	|_|_|_|_|o|_|_|_|_|_|_|_|_|_|07
	|_|_|_|_|o|_|_|_|_|_|_|_|_|_|08
	|_|_|_|_|o|_|_|_|_|_|_|_|_|_|09
	|_|_|_|_|o|_|_|_|_|_|_|_|_|_|10
	|-|-|-|o|x|o|o|o|o|o|o|-|-|-|11
	|-|-|-|_|o|_|_|_|_|_|_|-|-|-|12
	|-|-|-|_|o|_|K|_|_|_|_|-|-|-|13
	0 1 2 3 4 5 6 7 8 9 10  12 
	                      11  13  
	*/
	@Test
	public void testRook_Scenario1() {
		Game game = new Game();
		Settings settings = new Settings();
		Moves moves_history = new Moves(game);
		Chessboard testBoard = new Chessboard(settings, moves_history);
		// testBoard.setPieces("", settings.playerWhite, settings.playerBlack);
		// knight position
		int[] p1 = { 4, 11 };
		// kings placen
		testBoard.getSquares()[6][13].setPiece(new Piece(testBoard, settings.getPlayerWhite(),
				new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(),
				new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		// test Knight Movements
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(
				new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { RookBehaviour.getInstance() }, "Rook"));
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, SolutionRook1));
		}
	}
	
	
	
	// Szenarien bspw. als Konstanten speichern
	public static Square[] SolutionBishop1 = { new Square(3, 12, null), new Square(5, 12, null), new Square(3, 10, null),
			new Square(2, 9, null), new Square(1, 8, null), new Square(0, 7, null), new Square(5, 10, null),
			new Square(6, 9, null), new Square(7, 8, null), new Square(8, 7, null), new Square(9, 6, null),
			new Square(10, 5, null), new Square(11, 4, null), new Square(12, 3, null)
			};
				
		//Scenario1 BishopTest1
		/* X = Bishop
		 * o = movepoints
		|-|-|-|_|_|_|_|K|_|_|_|-|-|-|00
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|01
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|02
		|_|_|_|_|_|_|_|_|_|_|_|_|o|_|03
		|_|_|_|_|_|_|_|_|_|_|_|o|_|_|04
		|_|_|_|_|_|_|_|_|_|_|o|_|_|_|05
		|_|_|_|_|_|_|_|_|_|o|_|_|_|_|06
		|o|_|_|_|_|_|_|_|o|_|_|_|_|_|07
		|_|o|_|_|_|_|_|o|_|_|_|_|_|_|08
		|_|_|o|_|_|_|o|_|_|_|_|_|_|_|09
		|_|_|_|o|_|o|_|_|_|_|_|_|_|_|10
		|-|-|-|_|x|_|_|_|_|_|_|-|-|-|11
		|-|-|-|o|_|o|_|_|_|_|_|-|-|-|12
		|-|-|-|_|_|_|K|_|_|_|_|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  
		*/
	@Test
	public void testBishop_Scenario1() {
		Game game = new Game();
		Settings settings = new Settings();
		Moves moves_history = new Moves(game);
		Chessboard testBoard = new Chessboard(settings, moves_history);
		// testBoard.setPieces("", settings.playerWhite, settings.playerBlack);
		// knight position
		int[] p1 = { 4, 11 };
		// kings placen
		testBoard.getSquares()[6][13].setPiece(new Piece(testBoard, settings.getPlayerWhite(),
				new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(),
				new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		// test Knight Movements
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(
				new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { BishopBehaviour.getInstance() }, "Bishop"));
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, SolutionBishop1));
		}
	}
	
	
	
	// Szenarien bspw. als Konstanten speichern
	public static Square[] SolutionQueen1 = { new Square(4, 8, null), new Square(3, 9, null),
			new Square(2, 10, null), new Square(4, 7, null), new Square(3, 7, null), new Square(2, 7, null),
			new Square(1, 7, null), new Square(0, 7, null), new Square(4, 6, null), new Square(3, 5, null),
			new Square(2, 4, null), new Square(1, 3, null), new Square(5, 6, null), new Square(5, 5, null),
			new Square(5, 4, null), new Square(5, 3, null), new Square(5, 2, null), new Square(5, 1, null),
			new Square(5, 0, null), new Square(6, 6, null), new Square(7, 5, null), new Square(8, 4, null),
			new Square(9, 3, null), new Square(10, 2, null), new Square(6, 7, null), new Square(7, 7, null),
			new Square(8, 7, null), new Square(9, 7, null), new Square(10, 7, null), new Square(11, 7, null),
			new Square(12, 7, null), new Square(13, 7, null), new Square(6, 8, null), new Square(7, 9, null),
			new Square(8, 10, null), new Square(9, 11, null), new Square(10, 12, null), new Square(5, 8, null),
			new Square(5, 9, null), new Square(5, 10, null), new Square(5, 11, null), new Square(5, 12, null),
			new Square(5, 13, null)
			};
					
	//Scenario1 QueenTest1
	/* X = Queen
	 * o = movepoints
	|-|-|-|_|_|o|_|K|_|_|_|-|-|-|00
	|-|-|-|_|_|o|_|_|_|_|_|-|-|-|01
	|-|-|-|_|_|o|_|_|_|_|o|-|-|-|02
	|_|o|_|_|_|o|_|_|_|o|_|_|_|_|03
	|_|_|o|_|_|o|_|_|o|_|_|_|_|_|04
	|_|_|_|o|_|o|_|o|_|_|_|_|_|_|05
	|_|_|_|_|o|o|o|_|_|_|_|_|_|_|06
	|o|o|o|o|o|x|o|o|o|o|o|o|o|o|07
	|_|_|_|_|o|o|o|_|_|_|_|_|_|_|08
	|_|_|_|o|_|o|_|o|_|_|_|_|_|_|09
	|_|_|o|_|_|o|_|_|o|_|_|_|_|_|10
	|-|-|-|_|_|o|_|_|_|o|_|-|-|-|11
	|-|-|-|_|_|o|_|_|_|_|o|-|-|-|12
	|-|-|-|_|_|o|K|_|_|_|_|-|-|-|13
	0 1 2 3 4 5 6 7 8 9 10  12 
	                      11  13  
	*/
	@Test
	public void testQueen_Scenario1() {
		Game game = new Game();
		Settings settings = new Settings();
		Moves moves_history = new Moves(game);
		Chessboard testBoard = new Chessboard(settings, moves_history);
		// testBoard.setPieces("", settings.playerWhite, settings.playerBlack);
		// knight position
		int[] p1 = { 5, 7 };
		// kings placen
		testBoard.getSquares()[6][13].setPiece(new Piece(testBoard, settings.getPlayerWhite(),
				new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(),
				new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		// test Knight Movements
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(new Piece(testBoard, settings.getPlayerWhite(),
				new PieceBehaviour[] { BishopBehaviour.getInstance(), RookBehaviour.getInstance() }, "Queen"));
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, SolutionQueen1));
		}
	}
	
	
	
	// Szenarien bspw. als Konstanten speichern
	public static Square[] SolutionKing1 = { new Square(4, 11, null), new Square(5, 11, null), new Square(5, 12, null),
			new Square(5, 13, null), new Square(4, 13, null), new Square(3, 13, null), new Square(3, 12, null),
			new Square(3, 11, null)
			};
						
	//Scenario1 PawnTest1
	/* X = King white
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
	|-|-|-|o|o|o|_|_|_|_|_|-|-|-|11
	|-|-|-|o|X|o|_|_|_|_|_|-|-|-|12
	|-|-|-|o|o|o|_|_|_|_|_|-|-|-|13
	0 1 2 3 4 5 6 7 8 9 10  12 
	                      11  13  
	*/
	@Test
	public void testKing_Scenario1() {
		Game game = new Game();
		Settings settings = new Settings();
		Moves moves_history = new Moves(game);
		Chessboard testBoard = new Chessboard(settings, moves_history);
		// testBoard.setPieces("", settings.playerWhite, settings.playerBlack);
		// knight position
		int[] p1 = { 4, 12 };
		// kings placen
		testBoard.getSquares()[4][12].setPiece(new Piece(testBoard, settings.getPlayerWhite(),
				new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(),
				new PieceBehaviour[] { KingBehaviour.getInstance() }, "King"));
		// test Knight Movements
		//testBoard.squares[p1[0]][p1[1]].setPiece(new Piece(testBoard, settings.getPlayerWhite(), new PieceBehaviour[] { new QueenBehaviour() }, "Queen"));
		ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, SolutionKing1));
		}
	}
			
	
	
}
