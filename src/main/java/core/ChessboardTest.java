package core;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import pieces.KingBehaviour;
import pieces.KnightBehaviour;
import pieces.Piece;
import ui.NewGameWindow;

public class ChessboardTest {
	
	//Szenarien bspw. als Konstanten speichern und über Namen mit getRefSquares Daten holen
	public static Square[] Solution1 = {new Square(1, 4, null),new Square(2, 5, null), new Square(2, 7, null)};
	
	//um strin des szenarios erweitern
	private boolean isSolution(Square sq, Square[] Solution){
		for(int i = 0; i< Solution1.length; i++ ){
			if(Solution[i].getPozX() == sq.getPozX() && Solution[i].getPozY() == sq.getPozY())
				return true;
		}
		return false;
	}
	
//	private ArrayList<Square> getRefSquares(){
//		return null;
//	}
	
	//Scenario1 KnightTest1
	/* X = Knight
	|_|_|_|K|_|_|_|_|7
	|_|_|_|_|_|_|_|_|6
	|_|_|_|_|_|_|_|_|5
	|_|_|_|_|_|_|_|_|4
	|_|_|_|_|_|_|_|_|3
	|_|_|_|_|_|_|_|_|2
	|X|_|_|_|_|_|_|_|1
	|_|_|_|K|_|_|_|_|0
	0 1 2 3 4 5 6 7
	*/
	@Test
	public void testKnight_Scenario1() {
		Game game = new Game();
		Settings settings = new Settings();
		Moves moves_history = new Moves(game);
		Chessboard testBoard = new Chessboard(settings, moves_history);
		//testBoard.setPieces("", settings.playerWhite, settings.playerBlack);
		//knight position
		int[] p1 = {0, 6};
		//kings placen
		testBoard.squares[3][7].setPiece(testBoard.kingWhite = new Piece(testBoard, settings.playerWhite, new KingBehaviour(), "King"));
		testBoard.squares[3][0].setPiece(testBoard.kingBlack = new Piece(testBoard, settings.playerBlack, new KingBehaviour(), "King"));
		//test Knight Movements
		testBoard.setPiece(new Piece(testBoard, settings.playerWhite, new KnightBehaviour(), "Knight"), p1[0], p1[1]);
		ArrayList<Square>testMoves = testBoard.squares[p1[0]][p1[1]].piece.allMoves();
		for(Square sq : testMoves){
			assertTrue(isSolution(sq, Solution1));
		}
		
	}
	
//	public void testKnight(){
//		
//	}
	
}
