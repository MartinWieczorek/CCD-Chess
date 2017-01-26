/**
 * 
 */
package pieces;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import core.Square;

/** fundamental test cases for behavior of bishop
 * @author peter
 * @version Dec 10/2016
 */

public class TestBishopGetAllMoves extends TestGetAllMoves 
{
		//Scenario testBishopMovement1
			/* X = Bishop
			 * o = movepoints		
			|-|-|-|_|_|_|_|K|_|_|_|-|-|-|00
			|-|-|-|_|_|_|_|_|_|_|_|-|-|-|01
			|-|-|-|_|_|_|_|_|_|_|o|-|-|-|02
			|_|o|_|_|_|_|_|_|_|o|_|_|_|_|03
			|_|_|o|_|_|_|_|_|o|_|_|_|_|_|04
			|_|_|_|o|_|_|_|o|_|_|_|_|_|_|05
			|_|_|_|_|o|_|o|_|_|_|_|_|_|_|06
			|_|_|_|_|_|X|_|_|_|_|_|_|_|_|07
			|_|_|_|_|o|_|o|_|_|_|_|_|_|_|08
			|_|_|_|o|_|_|_|o|_|_|_|_|_|_|09
			|_|_|o|_|_|_|_|_|o|_|_|_|_|_|10
			|-|-|-|_|_|_|_|_|_|o|_|-|-|-|11
			|-|-|-|_|_|_|_|_|_|_|o|-|-|-|12
			|-|-|-|_|_|_|K|_|_|_|_|-|-|-|13
			 0 1 2 3 4 5 6 7 8 9 10  12 
			*/
		@Test
		public void testBishopStandartMovement() {

			// create solution
			Square[] SolutionBishop = { new Square(4, 8, null), new Square(3, 9, null), new Square(2, 10, null),
					new Square(4, 6, null), new Square(3, 5, null), new Square(2, 4, null), new Square(1, 3, null),
					new Square(6, 6, null), new Square(7, 5, null), new Square(8, 4, null), new Square(9, 3, null), new Square(10, 2, null),
					new Square(6, 8, null), new Square(7, 9, null), new Square(8, 10, null), new Square(9, 11, null), new Square(10, 12, null)
					 };
			
			// Bishop position
			int[] p1 = { 5, 7 };
			
			// set other pieces
			testBoard.getSquares()[6][13].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "King"));
			testBoard.getSquares()[6][0].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "King"));
			
			// set Bishop 
			testBoard.getSquares()[p1[0]][p1[1]].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Bishop"));
			
			// test movements
			ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
			for (Square sq : testMoves) {
				assertTrue(isSolution(sq, SolutionBishop));
			}

		}
		
		//Scenario testBishopMovement2
				/* X = bishop white
				 * o = movepoints
				 * P = pawn Black
				 * p = pawn white
				 * S = knight black (Spinger)
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
				|_|_|P|_|_|_|_|_|_|_|_|_|_|_|10
				|-|-|-|S|_|_|_|p|_|_|_|-|-|-|11
				|-|-|-|_|o|p|o|_|_|_|_|-|-|-|12
				|-|-|-|_|_|X|K|_|_|_|_|-|-|-|13
				 0 1 2 3 4 5 6 7 8 9 10  12 
				                       11  13  
				*/
			@Test
			public void testBishopMovementOnBorder() {
				
			// create solution
			Square[] SolutionBishop = { new Square(4, 12, null), new Square(6, 12, null), new Square(3, 11, null) };
				
				// Bishop position
				int[] p1 = { 5, 13 };
				
				// set other kings
				testBoard.getSquares()[6][13].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "King"));
				testBoard.getSquares()[6][0].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "King"));
				// set white Pawn
				testBoard.getSquares()[5][12].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Pawn"));
				testBoard.getSquares()[7][11].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Pawn"));
				// set black Pawn
				testBoard.getSquares()[7][4].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "Pawn"));			
				// set black Knight
				testBoard.getSquares()[3][11].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "Knight"));				
				// set Bishop 
				testBoard.getSquares()[p1[0]][p1[1]].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Bishop"));
				
				// test movements
				ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
				for (Square sq : testMoves) {
					assertTrue(isSolution(sq, SolutionBishop));
				}

			}
			
			/** Scenario testBishopAttack to black rock and to the black king with Schach matt
			 * x = Bishop - white and o = his movepoints
			 * K = black king - k = white king
			 * P = black pawn - p = white pawn
			 * R = black rook
			 * S = black knight  (Springer)
			 * s = white knight  (Springer)
			 */
			/*
			|-|-|-|R|_|_|_|K|S|_|_|-|-|-|00
			|-|-|-|_|o|p|o|P|P|_|_|-|-|-|01
			|-|-|-|_|_|x|_|_|_|_|_|-|-|-|02
			|_|_|_|_|p|_|o|_|_|_|_|_|_|_|03
			|_|_|_|_|_|_|_|s|_|_|_|_|_|_|04
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|05
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|06
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|07
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|08
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|09
			|_|_|_|_|_|_|_|_|_|_|_|_|_|_|10
			|-|-|-|_|_|_|_|_|_|_|_|-|-|-|11
			|-|-|-|_|_|_|_|_|_|_|_|-|-|-|12
			|-|-|-|_|_|_|k|_|_|_|_|-|-|-|13
			 0 1 2 3 4 5 6 7 8 9 10  12 
			                       11  13  
			*/
		@Test
		public void testBishopAttackInAllDirections() {
			
		// create solution
		Square[] SolutionBishop = { new Square(4, 1, null), new Square(6, 1, null), new Square(6, 3, null),
				new Square(3, 0, null), new Square(7, 0, null) };
			
			// Bishop position
			int[] p1 = { 5, 2 };
			
			// set kings
			testBoard.getSquares()[6][13].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "King"));
			testBoard.getSquares()[6][0].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "King"));
			// set Pawns
			testBoard.getSquares()[4][3].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Pawn"));
			testBoard.getSquares()[5][1].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Pawn"));
			testBoard.getSquares()[7][1].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "Pawn"));
			testBoard.getSquares()[8][1].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "Pawn"));
			// set knights
			testBoard.getSquares()[7][4].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Knight"));
			testBoard.getSquares()[8][0].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "Knight"));
			// set rook 
			testBoard.getSquares()[3][0].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerBlack(), "Rook"));
			// set bishop 
			testBoard.getSquares()[p1[0]][p1[1]].setPiece(PieceFactory.getInstance().createNewPiece(testBoard, settings.getPlayerWhite(), "Bishop"));
			
			// test movements
			ArrayList<Square> testMoves = testBoard.getSquares()[p1[0]][p1[1]].allMoves();
			for (Square sq : testMoves) {
				assertTrue(isSolution(sq, SolutionBishop));
			}

		}

	}
