package core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import pieces.PieceFactory;

public class CastlingTest {

	private Game game;
	private Settings settings;
	private Chessboard testBoard;
	private PieceFactory factory = PieceFactory.getInstance();
	
	
	@Before
	public void initTest() {
		game = new Game();
		settings = game.getSettings();
		//moves_history = new Moves(game);
		testBoard = game.getChessboard();
		// set Kings
		testBoard.getSquares()[0][7].setPiece(factory.createNewPiece(testBoard, settings.getPlayerRed(), "King"));
		testBoard.getSquares()[13][6].setPiece(factory.createNewPiece(testBoard, settings.getPlayerGreen(), "King"));
		testBoard.getSquares()[7][0].setPiece(factory.createNewPiece(testBoard, settings.getPlayerBlack(), "King"));
		testBoard.getSquares()[6][13].setPiece(factory.createNewPiece(testBoard, settings.getPlayerWhite(), "King"));
		
	}

	//Scenario testshort Castling
		/* W = King white
		 * K = King black
		 * R = King Red
		 * G = King Green
		 * r = Rook white
		|-|-|-|_|_|_|_|B|_|_|_|-|-|-|00
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|01
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|02
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|03
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|04
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|05
		|_|_|_|_|_|_|_|_|_|_|_|_|_|G|06
		|R|_|_|_|_|_|_|_|_|_|_|_|_|_|07
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|08
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|09
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|10
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|11
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|12
		|-|-|-|r|_|_|W|_|_|_|_|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  
		*/
	@Test
	public void testShortCastling() {
		//set white rook
		testBoard.getSquares()[3][13].setPiece(factory.createNewPiece(testBoard, settings.getPlayerWhite(), "Rook"));
        ChessboardLogic.getInstance().move(testBoard,  testBoard.getKing(settings.getPlayerWhite()),  testBoard.getSquares()[4][13]);
        assertEquals("Rook", testBoard.getSquares()[5][13].getPiece().getName());
	}

	//Scenario testshort Castling
	/* W = King white
	 * K = King black
	 * R = King Red
	 * G = King Green
	 * r = Rook white
	|-|-|-|_|_|_|_|B|_|_|_|-|-|-|00
	|-|-|-|_|_|_|_|_|_|_|_|-|-|-|01
	|-|-|-|_|_|_|_|_|_|_|_|-|-|-|02
	|_|_|_|_|_|_|_|_|_|_|_|_|_|_|03
	|_|_|_|_|_|_|_|_|_|_|_|_|_|_|04
	|_|_|_|_|_|_|_|_|_|_|_|_|_|_|05
	|_|_|_|_|_|_|_|_|_|_|_|_|_|G|06
	|R|_|_|_|_|_|_|_|_|_|_|_|_|_|07
	|_|_|_|_|_|_|_|_|_|_|_|_|_|_|08
	|_|_|_|_|_|_|_|_|_|_|_|_|_|_|09
	|_|_|_|_|_|_|_|_|_|_|_|_|_|_|10
	|-|-|-|_|_|_|_|_|_|_|_|-|-|-|11
	|-|-|-|_|_|_|_|_|_|_|_|-|-|-|12
	|-|-|-|_|_|_|W|_|_|_|r|-|-|-|13
	0 1 2 3 4 5 6 7 8 9 10  12 
	                      11  13  
	*/
	@Test
	public void testLongCastling() {
		//set white rook
		testBoard.getSquares()[10][13].setPiece(factory.createNewPiece(testBoard, settings.getPlayerWhite(), "Rook"));
	    ChessboardLogic.getInstance().move(testBoard,  testBoard.getKing(settings.getPlayerWhite()),  testBoard.getSquares()[9][13]);
	    assertEquals("Rook", testBoard.getSquares()[8][13].getPiece().getName());
	}

}
