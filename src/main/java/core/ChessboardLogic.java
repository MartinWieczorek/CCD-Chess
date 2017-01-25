package core;

import java.util.ArrayList;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.Moves.castling;
import pieces.BreakWall;
import pieces.MoveOnesStraight;
import pieces.MoveSaveStraight;
import pieces.MoveStraight;
import pieces.MoveTwoDirections;
import pieces.Piece;
import pieces.PieceBehaviour;
import pieces.PieceFactory;
import pieces.RochadeMove;
import pieces.RochadeStay;

public class ChessboardLogic {

	private static final Logger logger = LogManager.getLogger(ChessboardLogic.class);

	private static ChessboardLogic chessboardLogic;

	private ChessboardLogic() {
	}

	/**
	 * Method to return Instance of the Class to warok with
	 * @return
	 */
	public static ChessboardLogic getInstance() {
		if (chessboardLogic == null) {
			chessboardLogic = new ChessboardLogic();
			return chessboardLogic;
		} else {
			return chessboardLogic;
		}
	}

	/**
	 * set a Piece on given chessboard with specified Position
	 * @param chessboard to set Piece on
	 * @param piece Piece that has to be placed
	 * @param x coordinate on chessboard
	 * @param y coordinate on chessboard
	 */
	public void setPiece(Chessboard chessboard, Piece piece, int x, int y) {
		chessboard.getSquares()[x][y].setPiece(piece);
	}

	/**
	 * Method setPieces on begin of new game or loaded game
	 * 
	 * @param places
	 *            string with pieces to set on chessboard
	 * @param plWhite
	 *            reference to white player
	 * @param plBlack
	 *            reference to black player
	 */
	public void setPieces(Chessboard chessboard, String places, Player plWhite, Player plBlack, Player plRed,
			Player plGreen) {
		if (places.equals("")) // if newGame
		{
			if (chessboard.getSettings().isUpsideDown()) {
				setPieces4NewGame(chessboard, true, plWhite, plBlack, plRed, plGreen);
			} else {
				setPieces4NewGame(chessboard, false, plWhite, plBlack, plRed, plGreen);
			}

		} else // if loadedGame
		{
			return;
		}
	}/*--endOf-setPieces--*/

	/**
	 *
	 */
	private void setPieces4NewGame(Chessboard chessboard, boolean upsideDown, Player plWhite, Player plBlack,
			Player plRed, Player plGreen) {
		/* WHITE PIECES */
		Player player = plBlack;
		Player player1 = plWhite;
		if (upsideDown) // if white on Top
		{
			player = plWhite;
			player1 = plBlack;
		}

		setFigures4NewGame(chessboard, Chessboard.getTop(), player, true, false);
		setPawns4NewGame(chessboard, Chessboard.getTop() + 1, player, false);
		setWalls4NewGame(chessboard, Chessboard.getTop() + 2, player, false);

		setFigures4NewGame(chessboard, Chessboard.getBottom(), player1, false, false);
		setPawns4NewGame(chessboard, Chessboard.getBottom() - 1, player1, false);
		setWalls4NewGame(chessboard, Chessboard.getBottom() - 2, player1, false);

		setFigures4NewGame(chessboard, Chessboard.getTop(), plRed, true, true);
		setPawns4NewGame(chessboard, Chessboard.getTop() + 1, plRed, true);
		setWalls4NewGame(chessboard, Chessboard.getTop() + 2, plRed, true);

		setFigures4NewGame(chessboard, Chessboard.getBottom(), plGreen, false, true);
		setPawns4NewGame(chessboard, Chessboard.getBottom() - 1, plGreen, true);
		setWalls4NewGame(chessboard, Chessboard.getBottom() - 2, plGreen, true);
	}/*--endOf-setPieces(boolean upsideDown)--*/


	private void setFigures4NewGame(Chessboard chessboard, int row, Player player, boolean invertOrder,
			boolean switchRowCol) {
		String[] pieces = { "Rook", "Knight", "Bishop", "King", "Queen", "Bishop", "Knight", "Rook" };
		int col = 0;

		// if true, position pieces on left or right on chessboard
		if (switchRowCol) {
			// if true, switch position of king and queen
			if (invertOrder) {
				for (int figure = pieces.length - 1; figure >= 0; figure--) {
					setFigure(chessboard, chessboard.getCornerSquares() + col++, row, player, pieces[figure]);
				}
			} else {
				for (int figure = 0; figure < pieces.length; figure++) {
					setFigure(chessboard, chessboard.getCornerSquares() + col++, row, player, pieces[figure]);
				}
			}
		} else {
			if (invertOrder) {
				for (int figure = pieces.length - 1; figure >= 0; figure--) {
					setFigure(chessboard, row, chessboard.getCornerSquares() + col++, player, pieces[figure]);
				}
			} else {
				for (int figure = 0; figure < pieces.length; figure++) {
					setFigure(chessboard, row, chessboard.getCornerSquares() + col++, player, pieces[figure]);
				}
			}
		}
	}
	
	private void setFigure(Chessboard chessboard, int row, int col, Player player, String pieceName) {
		chessboard.getSquares()[col][row].setPiece(PieceFactory.getInstance().createNewPiece(chessboard, player, pieceName));
		logger.debug("set " + pieceName + " on pos: (" + row + "," + col + ") for player: " + player.getName());
	}

	private void setPawns4NewGame(Chessboard chessboard, int row, Player player, boolean switchRowCol) {
		if (row != Chessboard.getTop() + 1 && row != Chessboard.getBottom() - 1) {
			logger.error("error setting pawns etc.");
			return;
		}
		for (int x = Chessboard.getTop() + chessboard.getCornerSquares(); x < chessboard.getNumSquares() - chessboard.getCornerSquares(); x++) {
			
			if (switchRowCol)
				chessboard.getSquares()[row][x].setPiece(PieceFactory.getInstance().createNewPiece(chessboard, player, "Pawn"));
			else
				chessboard.getSquares()[x][row].setPiece(PieceFactory.getInstance().createNewPiece(chessboard, player, "Pawn"));

			logger.debug("set pawn on pos: (" + x + "," + row + ") for player: " + player.getName());
		}
	}
	
	private void setWalls4NewGame(Chessboard chessboard, int row, Player player, boolean switchRowCol) 
	{
		if(switchRowCol){
			chessboard.getSquares()[row][6].setPiece(PieceFactory.getInstance().createNewPiece(chessboard, player, "Wall"));
			chessboard.getSquares()[row][7].setPiece(PieceFactory.getInstance().createNewPiece(chessboard, player, "Wall"));
		}
		else{
			chessboard.getSquares()[6][row].setPiece(PieceFactory.getInstance().createNewPiece(chessboard, player, "Wall"));
			chessboard.getSquares()[7][row].setPiece(PieceFactory.getInstance().createNewPiece(chessboard, player, "Wall"));
		}
	}

	/**
	 * method to get reference to square from given x and y integeres
	 * 
	 * @param x
	 *            x position on chessboard
	 * @param y
	 *            y position on chessboard
	 * @return reference to searched square
	 */
	public Square getSquare(Chessboard chessboard, int x, int y) {
		if ((x > chessboard.get_height()) || (y > chessboard.get_widht())) 
		{
			logger.info("click out of chessboard.");
			return null;
		}
		if (chessboard.getSettings().isRenderLabels()) {
			x -= chessboard.getUpDownLabel().getHeight(null);
			y -= chessboard.getUpDownLabel().getHeight(null);
		}
		double square_x = x / chessboard.get_square_height();// count which
																// field in X
																// was clicked
		double square_y = y / chessboard.get_square_height();// count which
																// field in Y
																// was clicked

		if (square_x > (int) square_x) // if X is more than X parsed to Integer
		{
			square_x = (int) square_x + 1;// parse to integer and increment
		}
		if (square_y > (int) square_y) // if X is more than X parsed to Integer
		{
			square_y = (int) square_y + 1;// parse to integer and increment
		}
		
		logger.info("square_x: " + square_x + " square_y: " + square_y + " \n"); // 4tests
		Square result;
		try {
			result = chessboard.getSquares()[(int) square_x][(int) square_y];
		} catch (java.lang.ArrayIndexOutOfBoundsException exc) {
			logger.error("!!Array out of bounds when getting Square with Chessboard.getSquare(int,int) : " + exc);
			return null;
		}
		return chessboard.getSquares()[(int) square_x][(int) square_y];
	}
	
	/**
	 * activate a Square
	 * @param chessboard reference to chessboard
	 * @param sq Square that was selected
	 */
	public void select(Chessboard chessboard, Square sq) {
		chessboard.setActiveSquare(sq);
		chessboard.setActive_x_square(sq.getPozX() + 1);
		chessboard.setActive_y_square(sq.getPozY() + 1);

		// logger.info("active_x: " + this.active_x_square + " active_y: " +
		// this.active_y_square);//4tests
		chessboard.repaint();

	}

	/**
	 * Unselect the current active Square
	 * @param chessboard reference to working chessboard
	 */
	public void unselect(Chessboard chessboard) {
		chessboard.setActive_x_square(0);
		chessboard.setActive_y_square(0);
		chessboard.setActiveSquare(null);
		// this.draw();//redraw
		chessboard.repaint();
	}

	/**
	 * Wrapper Method to move a Piece
	 * @param chessboard, is the current chessboard where the move should happen
	 * @param begin Square with piece
	 * @param end Square destination
	 */
	public void move(Chessboard chessboard, Square begin, Square end) {
		move(chessboard, begin, end, true, true);
	}

	/**
	 * Wrapper method for Move
	 * @param chessboard
	 * @param xFrom X Position of the Piece on the Chessboard
	 * @param yFrom Y Position of the Piece on the Chessboard
	 * @param xTo X Destination of the Piece on the Chessboard
	 * @param yTo Y Destination of the Piece on the Chessboard
	 */
	public void move(Chessboard chessboard, int xFrom, int yFrom, int xTo, int yTo) {
		Square fromSQ = null;
		Square toSQ = null;
		try {
			fromSQ = chessboard.getSquares()[xFrom][yFrom];
			toSQ = chessboard.getSquares()[xTo][yTo];
		} catch (java.lang.IndexOutOfBoundsException exc) {
			// logger.error("error moving piece: " + exc);
			return;
		}
		this.move(chessboard, chessboard.getSquares()[xFrom][yFrom], chessboard.getSquares()[xTo][yTo], true, true);
	}

	/**
	 * Method to move a to Move a Piece
	 * @param chessboard, is the current chessboard where the move should happen
	 * @param begin Square with piece
	 * @param end Square destination
	 * @param refresh
	 * @param clearForwardHistory
	 */
	public void move(Chessboard chessboard, Square begin, Square end, boolean refresh, boolean clearForwardHistory) {
		begin.getPiece().setWasMotion(true);
		castling wasCastling = Moves.castling.none;
		Piece promotedPiece = null;
		boolean wasEnPassant = false;
		Piece usedPiece = begin.getPiece();

		Square tempBegin = new Square(begin);// 4 moves history
		Square tempEnd = new Square(end); // 4 moves history

		//Schlagen eines Konigs -> entfernen eines Spielers
		if(end.getPiece() != null && end.getPiece().getName().equals("King")){
			chessboard.getSettings().removeActivePlayer(end.getPiece().getPlayer());
		}
		
		//call all moveTo methods of piece
		ArrayList<PieceBehaviour> toDelete = new ArrayList<PieceBehaviour>();
		for (PieceBehaviour behaviour : usedPiece.getBehaviours()) {
			toDelete.add(behaviour.moveTo(chessboard, begin, end, usedPiece.getPlayer()));
		}
		for (PieceBehaviour pieceBehaviour : toDelete) {
			usedPiece.getBehaviours().remove(pieceBehaviour);
		}

		if (refresh) {
			this.unselect(chessboard);// unselect square
			chessboard.repaint();
		}
		if (clearForwardHistory) {
			chessboard.getMoves_history().clearMoveForwardStack();
			chessboard.getMoves_history().addMove(tempBegin, tempEnd, true, wasCastling, wasEnPassant, promotedPiece);
		} else {
			chessboard.getMoves_history().addMove(tempBegin, tempEnd, false, wasCastling, wasEnPassant, promotedPiece);
		}
	}/* endOf-move()- */

	/**
	 * Method to cheek if there is a possible promotion 
	 * @param pl defines the player who checks the promotion
	 * @param pawn Square where pawn will be moved
	 * @return true if there is a possible promotion on the given Square
	 */
	private boolean isPawnPromotion(Square pawn) {
		switch (pawn.getPiece().getPlayer().getColor()) {
		case red://rot beginnt rechts
			if(pawn.getPozX() == 0 || pawn.getPozY() == 0 || pawn.getPozY() == 13 ){
				return true;
			}
			break;
		case green:
			if(pawn.getPozX() == 13 || pawn.getPozY() == 0 || pawn.getPozY() == 13 ){
				return true;
			}
			break;
		case black:
			if(pawn.getPozX() == 0 || pawn.getPozY() == 0 || pawn.getPozX() == 13 ){
				return true;
			}
			break;
		case white:
			if(pawn.getPozX() == 13 || pawn.getPozX() == 0 || pawn.getPozY() == 13 ){
				return true;
			}
			break;

		}
		return false;
	}

	private Piece promotePawn(Chessboard chessboard, Square end) {
		String color;
		if (end.getPiece().getPlayer().getColor() == Player.colors.white) {
			color = "W"; // promotionWindow was show with pieces in this color
		} else {
			color = "B";
		}

		String newPiece = JChessApp.getJcv().showPawnPromotionBox(color); // return name of new piece
		
		end.setPiece(PieceFactory.getInstance().createNewPiece(chessboard, end.getPiece().getPlayer(), newPiece));

		return end.getPiece();
	}

	/**
	 * Method to redo last Step
	 * @param chessboard working chessboard
	 * @return true if Step was redone
	 */
	public boolean redo(Chessboard chessboard) {
		if (chessboard.getSettings().getGameType() == Settings.gameTypes.local) 
		{
			Move first = chessboard.getMoves_history().redo();

			Square from = null;
			Square to = null;

			if (first != null) {
				from = first.getFrom();
				to = first.getTo();

				this.move(chessboard, chessboard.getSquares()[from.getPozX()][from.getPozY()],
						chessboard.getSquares()[to.getPozX()][to.getPozY()], true, false);
				if (first.getPromotedPiece() != null) {
					Piece pawn = chessboard.getSquares()[to.getPozX()][to.getPozY()].getPiece();
					// pawn.square = null;

					chessboard.getSquares()[to.getPozX()][to.getPozY()].setPiece(first.getPromotedPiece());
					Piece promoted = chessboard.getSquares()[to.getPozX()][to.getPozY()].getPiece();
					// promoted.square =
					// chessboard.getSquares()[to.getPozX()][to.getPozY()];
				}
				return true;
			}

		}
		return false;
	}

	public synchronized boolean undo(Chessboard chessboard, boolean refresh) // undo last move
	{
		Move last = chessboard.getMoves_history().undo();

		if (last != null && last.getFrom() != null) {
			Square begin = last.getFrom();
			Square end = last.getTo();
			try {
				Piece moved = last.getMovedPiece();
				chessboard.getSquares()[begin.getPozX()][begin.getPozY()].setPiece(moved);

				// moved.square =
				// chessboard.getSquares()[begin.getPozX()][begin.getPozY()];

				Piece taken = last.getTakenPiece();
				if (last.getCastlingMove() != castling.none) {
					Piece rook = null;
					if (last.getCastlingMove() == castling.shortCastling) {
						rook = chessboard.getSquares()[end.getPozX() - 1][end.getPozY()].getPiece();
						chessboard.getSquares()[7][begin.getPozY()].setPiece(rook);
						// rook.square =
						// chessboard.getSquares()[7][begin.getPozY()];
						chessboard.getSquares()[end.getPozX() - 1][end.getPozY()].setPiece(null);
					} else {
						rook = chessboard.getSquares()[end.getPozX() + 1][end.getPozY()].getPiece();
						chessboard.getSquares()[0][begin.getPozY()].setPiece(rook);
						// rook.square =
						// chessboard.getSquares()[0][begin.getPozY()];
						chessboard.getSquares()[end.getPozX() + 1][end.getPozY()].setPiece(null);
					}
					moved.setWasMotion(false);
					rook.setWasMotion(false);
				} else if (moved.getName().equals("Rook")) {
					moved.setWasMotion(false);
				} else if (moved.getName().equals("Pawn") && last.wasEnPassant()) {
					Piece pawn = last.getTakenPiece();
					chessboard.getSquares()[end.getPozX()][begin.getPozY()].setPiece(pawn);
					// pawn.square =
					// chessboard.getSquares()[end.getPozX()][begin.getPozY()];

				} else if (moved.getName().equals("Pawn") && last.getPromotedPiece() != null) {
					Piece promoted = chessboard.getSquares()[end.getPozX()][end.getPozY()].getPiece();
					// promoted.square = null;
					chessboard.getSquares()[end.getPozX()][end.getPozY()].setPiece(null);
				}

				// check one more move back for en passant
				Move oneMoveEarlier = chessboard.getMoves_history().getLastMoveFromHistory();
				if (oneMoveEarlier != null && oneMoveEarlier.wasPawnTwoFieldsMove()) {
					Square canBeTakenEnPassant = chessboard.getSquares()[oneMoveEarlier.getTo()
							.getPozX()][oneMoveEarlier.getTo().getPozY()];
					if (canBeTakenEnPassant.getPiece().getName().equals("Pawn")) {
						chessboard.setTwoSquareMovedPawn(canBeTakenEnPassant);
					}
				}

				if (taken != null && !last.wasEnPassant()) {
					chessboard.getSquares()[end.getPozX()][end.getPozY()].setPiece(taken);
					taken = chessboard.getSquares()[end.getPozX()][end.getPozY()].getPiece();
				} else {
					chessboard.getSquares()[end.getPozX()][end.getPozY()].setPiece(null);
				}

				if (refresh) {
					ChessboardLogic.getInstance().unselect(chessboard);// unselect
																		// square
					chessboard.repaint();
				}

			} catch (java.lang.ArrayIndexOutOfBoundsException exc) {
				return false;
			} catch (java.lang.NullPointerException exc) {
				return false;
			}

			return true;
		} else {
			return false;
		}
	}
}
