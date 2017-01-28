package pieces;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import core.Chessboard;
import core.ChessboardLogic;
import core.Game;
import core.Settings;
import core.Square;
import pieces.PieceFactory;

public class TestWallGetAllMoves extends TestGetAllMoves {

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

	//Scenario test wall destruction
		/* W = King white
		 * K = King black
		 * R = King Red
		 * G = King Green
		 * r = Rook black
		 * q = queen red
		 * w = white wall
		|-|-|-|_|_|_|_|B|_|_|_|-|-|-|00
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|01
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|02
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|03
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|04
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|05
		|_|_|_|_|_|_|_|_|_|_|_|_|_|G|06
		|R|_|_|_|_|q|_|_|_|_|_|_|_|_|07
		|_|_|_|_|_|r|_|_|_|_|_|_|_|_|08
		|_|_|_|_|_|w|_|_|_|_|_|_|_|_|09
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|10
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|11
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|12
		|-|-|-|_|_|_|W|_|_|_|_|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  */
	@Test
	public void testWallDestruction() {
		//set white rook
		testBoard.getSquares()[5][8].setPiece(factory.createNewPiece(testBoard, settings.getPlayerBlack(), "Rook"));
		testBoard.getSquares()[5][7].setPiece(factory.createNewPiece(testBoard, settings.getPlayerBlack(), "Queen"));
		testBoard.getSquares()[5][9].setPiece(factory.createNewPiece(testBoard, settings.getPlayerWhite(), "Wall"));
		
        ArrayList<Square>testMoves = testBoard.getSquares()[5][8].allMoves();
		assertTrue(testMoves.contains(testBoard.getSquares()[5][9]));
	}

	//Scenario test wall destruction
			/* W = King white
			 * K = King black
			 * R = King Red
			 * G = King Green
			 * r = Rook black
			 * q = queen red
			 * w = white wall
			|-|-|-|_|_|_|_|B|_|_|_|-|-|-|00
			|-|-|-|_|_|_|_|_|_|_|_|-|-|-|01
			|-|-|-|_|_|_|_|_|_|_|_|-|-|-|02
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|03
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|04
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|05
			|_|_|_|_|_|_|_|_|_|_|_|_|_|G|06
			|R|_|_|_|_|_|_|_|_|_|_|_|_|_|07
			|_|_|_|_|_|r|_|_|_|_|_|_|_|_|08
			|_|_|_|_|_|w|_|_|_|_|_|_|_|_|09
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|10
			|-|-|-|_|_|_|_|_|_|_|_|-|-|-|11
			|-|-|-|_|_|_|_|_|_|_|_|-|-|-|12
			|-|-|-|_|_|_|W|_|_|_|_|-|-|-|13
			0 1 2 3 4 5 6 7 8 9 10  12 
			                      11  13  */
		@Test
		public void testWallInvincible() {
			//set white rook
			testBoard.getSquares()[5][8].setPiece(factory.createNewPiece(testBoard, settings.getPlayerBlack(), "Rook"));
			testBoard.getSquares()[5][9].setPiece(factory.createNewPiece(testBoard, settings.getPlayerWhite(), "Wall"));
	        ArrayList<Square>testMoves = testBoard.getSquares()[5][8].allMoves();
			assertTrue(!testMoves.contains(testBoard.getSquares()[5][9]));
		}
		
		//Scenario test wall movement blocked
		/* W = King white
		 * K = King black
		 * R = King Red
		 * G = King Green
		 * r = Rook black
		 * q = queen red
		 * w = white wall
		|-|-|-|_|_|_|_|B|_|_|_|-|-|-|00
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|01
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|02
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|03
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|04
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|05
		|_|_|_|_|_|_|_|_|_|_|_|_|_|G|06
		|R|_|_|_|_|_|_|_|_|_|_|_|_|_|07
		|_|_|_|_|_|r|_|_|_|_|_|_|_|_|08
		|_|_|_|_|_|w|_|_|_|_|_|_|_|_|09
		|_|_|_|_|_|_|_|_|_|_|_|_|_|_|10
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|11
		|-|-|-|_|_|_|_|_|_|_|_|-|-|-|12
		|-|-|-|_|_|_|W|_|_|_|_|-|-|-|13
		0 1 2 3 4 5 6 7 8 9 10  12 
		                      11  13  */
	@Test
	public void testWallMovementBlocked() {
		int[] pos = {5, 9};
		Square[] SolutionWall = { new Square(pos[0]+1, pos[1], null), new Square(pos[0], pos[1]+1, null),
				new Square(pos[0]-1, pos[1], null)};
		// set pieces
		testBoard.getSquares()[5][8].setPiece(factory.createNewPiece(testBoard, settings.getPlayerBlack(), "Rook"));
		testBoard.getSquares()[pos[0]][pos[1]].setPiece(factory.createNewPiece(testBoard, settings.getPlayerWhite(), "Wall"));
        ArrayList<Square>testMoves = testBoard.getSquares()[pos[0]][pos[1]].allMoves();
		for (Square sq : testMoves) {
			assertTrue(isSolution(sq, SolutionWall));
		}
	}
	
	//Scenario test wall movement unblocked
			/* W = King white
			 * K = King black
			 * R = King Red
			 * G = King Green
			 * r = Rook black
			 * q = queen red
			 * w = white wall
			|-|-|-|_|_|_|_|B|_|_|_|-|-|-|00
			|-|-|-|_|_|_|_|_|_|_|_|-|-|-|01
			|-|-|-|_|_|_|_|_|_|_|_|-|-|-|02
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|03
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|04
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|05
			|_|_|_|_|_|_|_|_|_|_|_|_|_|G|06
			|R|_|_|_|_|_|_|_|_|_|_|_|_|_|07
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|08
			|_|_|_|_|_|w|_|_|_|_|_|_|_|_|09
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|10
			|-|-|-|_|_|_|_|_|_|_|_|-|-|-|11
			|-|-|-|_|_|_|_|_|_|_|_|-|-|-|12
			|-|-|-|_|_|_|W|_|_|_|_|-|-|-|13
			0 1 2 3 4 5 6 7 8 9 10  12 
			                      11  13  */
		@Test
		public void testWallMovementUnBlocked() {
			int[] pos = {5, 9};
			Square[] SolutionWall = { new Square(pos[0]+1, pos[1], null), new Square(pos[0], pos[1]+1, null),
					new Square(pos[0]-1, pos[1], null), new Square(pos[0], pos[1]-1, null)};
			testBoard.getSquares()[pos[0]][pos[1]].setPiece(factory.createNewPiece(testBoard, settings.getPlayerWhite(), "Wall"));
	        ArrayList<Square>testMoves = testBoard.getSquares()[pos[0]][pos[1]].allMoves();
			for (Square sq : testMoves) {
				assertTrue(isSolution(sq, SolutionWall));
			}
		}

}
