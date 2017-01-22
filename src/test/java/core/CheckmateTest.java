package core;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import pieces.KingBehaviour;
import pieces.MoveStraight;
import pieces.Piece;
import pieces.PieceBehaviour;

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
		ArrayList<PieceBehaviour> tmp = new ArrayList<PieceBehaviour>();
		tmp.add(KingBehaviour.getInstance());
		testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(), tmp, "King", "K"));
		tmp.clear();
		tmp.add(new MoveStraight(Integer.MAX_VALUE, true, true, true, true, true, true, true, false, false, false, false));
		testBoard.getSquares()[4][12].setPiece(new Piece(testBoard, settings.getPlayerBlack(), tmp, "Rook", "R"));
		testBoard.getSquares()[3][13].setPiece(new Piece(testBoard, settings.getPlayerBlack(),tmp, "Rook", "R"));
		
		// set white King
		tmp.clear();
		tmp.add(KingBehaviour.getInstance());
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(new Piece(testBoard, settings.getPlayerWhite(),tmp, "King", "K"));
		
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
		ArrayList<PieceBehaviour> tmp = new ArrayList<PieceBehaviour>();
		tmp.add(KingBehaviour.getInstance());
		testBoard.getSquares()[6][0].setPiece(new Piece(testBoard, settings.getPlayerBlack(), tmp, "King", "K"));
		tmp.clear();
		tmp.add(new MoveStraight(Integer.MAX_VALUE, true, true, true, true, true, true, true, false, false, false, false));
		testBoard.getSquares()[4][12].setPiece(new Piece(testBoard, settings.getPlayerBlack(), tmp, "Rook", "R"));
		testBoard.getSquares()[9][11].setPiece(new Piece(testBoard, settings.getPlayerBlack(), tmp, "Rook", "R"));
		
		// set white King
		tmp.clear();
		tmp.add(KingBehaviour.getInstance());
		testBoard.getSquares()[p1[0]][p1[1]].setPiece(new Piece(testBoard, settings.getPlayerWhite(), tmp, "King", "K"));
		
		// test checkmate
		
        Piece king;
        king = game.getChessboard().getKing(settings.getPlayerWhite()).getPiece();
        
        assertEquals(Settings.gameState.stalemate, king.isCheckmatedOrStalemated());

	}

}
