package core;

import static org.junit.Assert.*;


import org.junit.Test;

import pieces.Piece;
import pieces.PieceFactory;

public class CheckmateTest {

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
		initTest();

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
        king = game.getChessboard().getKing(settings.getPlayerWhite()).getPiece();
        
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
		initTest();

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
        king = game.getChessboard().getKing(settings.getPlayerWhite()).getPiece();
        
        assertEquals(Settings.gameState.stalemate, king.isCheckmatedOrStalemated());

	}

}
