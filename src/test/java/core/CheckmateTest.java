package core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pieces.Piece;
import pieces.PieceFactory;

public class CheckmateTest {

	private Game game;
	private Settings settings;
	private Chessboard testBoard;
	private PieceFactory factory = PieceFactory.getInstance();
	
	@Before
	public void initTest() {
		game = new Game();
		settings = game.getSettings();
		testBoard = game.getChessboard();
		
	}

	//Scenario testCheckmate
		/* X = King white
		 * K = King black
		 * R = Rook black
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
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|11
		|-|-|-|_|R|_|_|_|_|_|_|-|-|-|12
		|-|-|-|R|_|_|_|_|_|_|X|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  
		*/
	@Test
	public void testCheckmate() {

		// position king white
		int[] p1 = { 10, 13 };
		
		// set other pieces
		testBoard.getSquares()[6][0].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "King"));
		testBoard.getSquares()[4][12].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "Rook"));
		testBoard.getSquares()[3][13].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "Rook"));
		
		// set white King
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "King"));
		
		// test checkmate
		
        Piece king;
        king = testBoard.getKing(settings.getPlayerWhite()).getPiece();
        
        assertEquals(Settings.gameState.chekmate, king.isCheckmatedOrStalemated());

	}
	
	
	

	//Scenario testCheckmate
		/* X = King white
		 * K = King black
		 * R = Rook black
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
		|-|-|-|_|_|_|_|_|_|R|_|-|-|-|11
		|-|-|-|_|R|_|_|_|_|_|_|-|-|-|12
		|-|-|-|_|_|_|_|_|_|_|X|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  
		*/
	@Test
	public void testStalemate() {

		// position king white
		int[] p1 = { 10, 13 };
		
		// set other pieces
		testBoard.getSquares()[6][0].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "King"));
		testBoard.getSquares()[4][12].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "Rook"));
		testBoard.getSquares()[9][11].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "Rook"));
		
		// set white King
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "King"));
		
		// test checkmate
		
        Piece king;
        king = testBoard.getKing(settings.getPlayerWhite()).getPiece();
        
        assertEquals(Settings.gameState.stalemate, king.isCheckmatedOrStalemated());

	}
	
	
	


	//Scenario testCheckmate
		/* X = King white
		 * K = King black
		 * R = Rook black
		|-|-|-|_|_|_|_|K|_|_|_|-|-|-|00
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|01
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|02
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|03
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|04
		|w|_|_|_|_|_|_|_|_|_|_|_|_|_|05
		|r|k|Q|_|_|_|_|_|_|_|_|_|_|_|06
		|w|_|_|_|_|_|_|_|_|_|_|_|_|_|07
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|08
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|09
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|10
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|11
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|12
		|-|-|-|_|_|_|X|_|_|_|_|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  
		*/
	@Test
	public void testCheckmate3Player() {
		
		// set kings
		testBoard.getSquares()[7][0].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "King"));
		testBoard.getSquares()[6][13].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "King"));
		testBoard.getSquares()[0][6].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerRed(), "King"));
		// set red walls
		testBoard.getSquares()[0][5].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerRed(), "Wall"));
		testBoard.getSquares()[0][7].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerRed(), "Wall"));
		// set black knight
		testBoard.getSquares()[1][6].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "Knight"));
		// set white Queen
		testBoard.getSquares()[2][6].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Queen"));
		
        Piece king;
        king = testBoard.getKingRed().getPiece();
        
        // test check of red Player
        assertEquals(false, king.isChecked(testBoard.getKingRed()) );
        // white Queen cant kill red king yet
        assertEquals(false, testBoard.getSquares()[2][6].allMoves().contains(testBoard.getSquares()[0][6]) );
        
        // move knight away
        ChessboardLogic.getInstance().move(testBoard, testBoard.getSquares()[1][6], testBoard.getSquares()[2][4], false, false);
        
        assertEquals(Settings.gameState.chekmate, king.isCheckmatedOrStalemated());
        
        // Queen should now be able to kill red king
        assertEquals(true, testBoard.getSquares()[2][6].allMoves().contains(testBoard.getSquares()[0][6]) );
        
	}
	
	//Scenario testCheckmate
			/* W = King white
			 * B = King black
			 * R = King Red
			 * w = wall
			 * Q = queen white
			 * r = rook
			 * b = white bishop
			|-|-|-|_|_|_|_|B|_|_|_|-|-|-|00
			|-|-|-|_|_|_|_|_|_|_|_|-|-|-|01
			|-|-|-|_|_|_|_|_|_|_|_|-|-|-|02
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|03
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|04
			|_|w|_|w|_|_|_|_|_|_|_|_|_|_|05
			|R|_|b|_|_|_|_|_|_|_|_|_|_|_|06
			|_|w|_|w|_|_|_|_|_|_|_|_|_|_|07
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|08
			|_|_|_|_|_|r|_|r|_|_|_|_|_|_|09
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|10
			|-|-|-|_|_|_|_|_|_|_|_|-|-|-|11
			|-|-|-|r|_|_|_|_|_|_|_|-|-|-|12
			|-|-|-|_|_|_|W|_|_|_|_|-|-|-|13
			0 1 2 3 4 5 6 7 8 9 10  12 
			                      11  13  
			*/
		@Test
		public void testStalemate3Player() {
			// set kings
			testBoard.getSquares()[7][0].setPiece(factory.createNewPiece(testBoard, settings.getPlayerBlack(), "King"));
			testBoard.getSquares()[6][13].setPiece(factory.createNewPiece(testBoard, settings.getPlayerWhite(), "King"));
			testBoard.getSquares()[0][6].setPiece(factory.createNewPiece(testBoard, settings.getPlayerRed(), "King"));
			// set red walls
			testBoard.getSquares()[1][5].setPiece(factory.createNewPiece(testBoard, settings.getPlayerRed(), "Wall"));
			testBoard.getSquares()[1][7].setPiece(factory.createNewPiece(testBoard, settings.getPlayerRed(), "Wall"));
			// set green walls
			testBoard.getSquares()[3][5].setPiece(factory.createNewPiece(testBoard, settings.getPlayerGreen(), "Wall"));
			testBoard.getSquares()[3][7].setPiece(factory.createNewPiece(testBoard, settings.getPlayerGreen(), "Wall"));
			// set red rooks
			testBoard.getSquares()[5][9].setPiece(factory.createNewPiece(testBoard, settings.getPlayerRed(), "Rook"));
			testBoard.getSquares()[7][9].setPiece(factory.createNewPiece(testBoard, settings.getPlayerRed(), "Rook"));
			// set black rook
			testBoard.getSquares()[3][12].setPiece(factory.createNewPiece(testBoard, settings.getPlayerBlack(), "Rook"));
			// set white bishop
			//testBoard.getSquares()[2][6].setPiece(factory.createNewPiece(testBoard, settings.getPlayerWhite(), "Bishop"));
			
	        Piece king;
	        king = testBoard.getKingWhite().getPiece();
	        assertEquals(Settings.gameState.stalemate, king.isCheckmatedOrStalemated());
		}
		
		//Scenario testKingDefeat
		/* W = King white
		 * B = King black
		 * R = King Red
		 * r = rook white
		|-|-|-|_|_|_|_|B|_|_|_|-|-|-|00
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|01
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|02
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|03
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|04
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|05
		|R|_|r|_|_|_|_|_|_|_|_|_|_|_|06
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|07
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|08
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|09
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|10
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|11
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|12
		|-|-|-|_|_|_|W|_|_|_|_|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  
		*/
	@Test
	public void testKingDefeat() {
		// set kings
		testBoard.getSquares()[7][0].setPiece(factory.createNewPiece(testBoard, settings.getPlayerBlack(), "King"));
		testBoard.getSquares()[6][13].setPiece(factory.createNewPiece(testBoard, settings.getPlayerWhite(), "King"));
		testBoard.getSquares()[0][6].setPiece(factory.createNewPiece(testBoard, settings.getPlayerRed(), "King"));
		// set white rook
		testBoard.getSquares()[2][6].setPiece(factory.createNewPiece(testBoard, settings.getPlayerWhite(), "Rook"));
		
		ChessboardLogic.getInstance().move(testBoard, testBoard.getSquares()[2][6], testBoard.getSquares()[0][6]);
		
        assertEquals(false, settings.isPlayerActive(settings.getPlayerRed()));
	}

}
