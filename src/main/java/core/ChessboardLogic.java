package core;

import core.Moves.castling;
import pieces.BishopBehaviour;
import pieces.KingBehaviour;
import pieces.KnightBehaviour;
import pieces.PawnBehaviour;
import pieces.Piece;
import pieces.QueenBehaviour;
import pieces.RookBehaviour;

public class ChessboardLogic {
	
	private static ChessboardLogic chessboardLogic;

	 private ChessboardLogic()
	 {
	 }
	
	 public static ChessboardLogic getInstance()
	 {
		 if(chessboardLogic == null)
		 {
			 chessboardLogic = new ChessboardLogic();
			 return chessboardLogic;
		 }
		 else
		 {
			 return chessboardLogic;
		 }
	 }
	 
	 public void setPiece(Chessboard chessboard, Piece piece, int x, int y){
		 chessboard.squares[x][y].setPiece(piece);
	    }
	 
	 public void setPieces(Chessboard chessboard, String places, Player plWhite, Player plBlack)
	    {

	        if (places.equals("")) //if newGame
	        {
	            if (chessboard.getSettings().upsideDown)
	            {
	            	this.setPieces4NewGame(chessboard, true, plWhite, plBlack);
	            }
	            else
	            {
	            	this.setPieces4NewGame(chessboard, false, plWhite, plBlack);
	            }

	        } 
	        else //if loadedGame
	        {
	            return;
	        }
	    }/*--endOf-setPieces--*/
	 
	 private void setPieces4NewGame(Chessboard chessboard, boolean upsideDown, Player plWhite, Player plBlack)
	    {
	        /* WHITE PIECES */
	        Player player = plBlack;
	        Player player1 = plWhite;
	        if (upsideDown) //if white on Top
	        { 
	            player = plWhite;
	            player1 = plBlack;
	        }
	        this.setFigures4NewGame(chessboard, 0, player, upsideDown);
	        this.setPawns4NewGame(chessboard, 1, player);
	        this.setFigures4NewGame(chessboard, 7, player1, upsideDown);
	        this.setPawns4NewGame(chessboard, 6, player1);
	    }/*--endOf-setPieces(boolean upsideDown)--*/
	 
	 /**  method set Figures in row (and set Queen and King to right position)
	     *  @param i row where to set figures (Rook, Knight etc.)
	     *  @param player which is owner of pawns
	     *  @param upsideDown if true white pieces will be on top of chessboard
	     * */
	    private void setFigures4NewGame(Chessboard chessboard, int i, Player player, boolean upsideDown)
	    {

	        if (i != 0 && i != 7)
	        {
	            return;
	        }
	        else if (i == 0)
	        {
	            player.setGoDown(true);
	        }

	        chessboard.squares[0][i].setPiece(new Piece(chessboard, player, new RookBehaviour(), "Rook"));
	        chessboard.squares[7][i].setPiece(new Piece(chessboard, player, new RookBehaviour(), "Rook"));
	        chessboard.squares[1][i].setPiece(new Piece(chessboard, player, new KnightBehaviour(), "Knight"));
	        chessboard.squares[6][i].setPiece(new Piece(chessboard, player, new KnightBehaviour(), "Knight"));
	        chessboard.squares[2][i].setPiece(new Piece(chessboard, player, new BishopBehaviour(), "Bishop"));
	        chessboard.squares[5][i].setPiece(new Piece(chessboard, player, new BishopBehaviour(), "Bishop"));
	        if (upsideDown)
	        {
	        	chessboard.squares[4][i].setPiece(new Piece(chessboard, player, new QueenBehaviour(), "Queen"));
	            if (player.getColor() == Player.colors.white)
	            {
	            	chessboard.squares[3][i].setPiece(chessboard.kingWhite = new Piece(chessboard, player, new KingBehaviour(), "King"));
	            }
	            else
	            {
	            	chessboard.squares[3][i].setPiece(chessboard.kingBlack = new Piece(chessboard, player, new KingBehaviour(), "King"));
	            }
	        }
	        else
	        {
	        	chessboard.squares[3][i].setPiece(new Piece(chessboard, player, new QueenBehaviour(), "Queen"));
	            if (player.getColor() == Player.colors.white)
	            {
	            	chessboard.squares[4][i].setPiece(chessboard.kingWhite = new Piece(chessboard, player, new KingBehaviour(), "King"));
	            }
	            else
	            {
	            	chessboard.squares[4][i].setPiece(chessboard.kingBlack =new Piece(chessboard, player, new KingBehaviour(), "King"));
	            }
	        }
	    }
	    
	    /**  method set Pawns in row
	     *  @param i row where to set pawns
	     *  @param player player which is owner of pawns
	     * */
	    private void setPawns4NewGame(Chessboard chessboard, int i, Player player)
	    {
	        if (i != 1 && i != 6)
	        {
	            return;
	        }
	        for (int x = 0; x < 8; x++)
	        {
	        	chessboard.squares[x][i].setPiece(new Piece(chessboard, player, new PawnBehaviour(), "Pawn"));
	        }
	    }
	    
	    /** method to get reference to square from given x and y integeres
	     * @param x x position on chessboard
	     * @param y y position on chessboard
	     * @return reference to searched square
	     */
	    public Square getSquare(Chessboard chessboard, int x, int y)
	    { 
	        if ((x > chessboard.get_height()) || (y > chessboard.get_widht())) //test if click is out of chessboard
	        {
	        	//logger.info("click out of chessboard.");
	            return null;
	        }
	        if (chessboard.getSettings().renderLabels)
	        {
	            x -= chessboard.getUpDownLabel().getHeight(null);
	            y -= chessboard.getUpDownLabel().getHeight(null);
	        }
	        double square_x = x / chessboard.getSquare_height();//count which field in X was clicked
	        double square_y = y / chessboard.getSquare_height();//count which field in Y was clicked

	        if (square_x > (int) square_x) //if X is more than X parsed to Integer
	        {
	            square_x = (int) square_x + 1;//parse to integer and increment
	        }
	        if (square_y > (int) square_y) //if X is more than X parsed to Integer
	        {
	            square_y = (int) square_y + 1;//parse to integer and increment
	        }
	        //Square newActiveSquare = this.squares[(int)square_x-1][(int)square_y-1];//4test
	        //logger.info("square_x: " + square_x + " square_y: " + square_y + " \n"); //4tests
	        Square result;
	        try
	        {
	            result = chessboard.squares[(int) square_x - 1][(int) square_y - 1];
	        }
	        catch (java.lang.ArrayIndexOutOfBoundsException exc)
	        {
	        	//logger.error("!!Array out of bounds when getting Square with Chessboard.getSquare(int,int) : " + exc);
	            return null;
	        }
	        return chessboard.squares[(int) square_x - 1][(int) square_y - 1];
	    }
	    
	    public void select(Chessboard chessboard, Square sq)
	    {
	    	chessboard.activeSquare = sq;
	    	chessboard.setActive_x_square(sq.getPozX() + 1);
	    	chessboard.setActive_y_square(sq.getPozY() + 1);

	        //this.draw();//redraw
	        //logger.info("active_x: " + this.active_x_square + " active_y: " + this.active_y_square);//4tests
	        chessboard.repaint();

	    }/*--endOf-select--*/

		public void unselect(Chessboard chessboard)
	    {
			chessboard.setActive_x_square(0);
	        chessboard.setActive_y_square(0);
	        chessboard.activeSquare = null;
	        //this.draw();//redraw
	        chessboard.repaint();
	    }/*--endOf-unselect--*/
		
		public void move(Chessboard chessboard, Square begin, Square end)
	    {
	        move(chessboard, begin, end, true, true);
	    }

		public void move(Chessboard chessboard, int xFrom, int yFrom, int xTo, int yTo)
	    {
	        Square fromSQ = null;
	        Square toSQ = null;
	        try
	        {
	            fromSQ = chessboard.squares[xFrom][yFrom];
	            toSQ = chessboard.squares[xTo][yTo];
	        }
	        catch (java.lang.IndexOutOfBoundsException exc)
	        {
	        	//logger.error("error moving piece: " + exc);
	            return;
	        }
	        this.move(chessboard, chessboard.squares[xFrom][yFrom], chessboard.squares[xTo][yTo], true, true);
	    }

	    public void move(Chessboard chessboard, Square begin, Square end, boolean refresh, boolean clearForwardHistory)
	    {
	        castling wasCastling = Moves.castling.none;
	        Piece promotedPiece = null;
	        boolean wasEnPassant = false;
	        if (end.piece != null)
	        {
	            end.piece.square = null;
	        }

	        Square tempBegin = new Square(begin);//4 moves history
	        Square tempEnd = new Square(end);  //4 moves history

	        begin.piece.square = end;//set square of piece to ending
	        end.piece = begin.piece;//for ending square set piece from beginin square
	        begin.piece = null;//make null piece for begining square

	        if (end.piece.name.equals("King"))
	        {
	        	wasCastling = specialKingMovement(chessboard, begin, end);
	        }
	        else if (end.piece.name.equals("Rook"))
	        {
	            if (!(end.piece).wasMotion)
	            {
	                (end.piece).wasMotion = true;
	            }
	        }
	        else if (end.piece.name.equals("Pawn"))
	        {
	        	//en passant
	            if (chessboard.twoSquareMovedPawn != null && chessboard.squares[end.getPozX()][begin.getPozY()] == chessboard.twoSquareMovedPawn.square) //en passant
	            {
	                tempEnd.piece = chessboard.squares[end.getPozX()][begin.getPozY()].piece; //ugly hack - put taken pawn in en passant plasty do end square

	                chessboard.squares[end.getPozX()][begin.getPozY()].piece = null;
	                wasEnPassant = true;
	            }

	            if (begin.getPozY() - end.getPozY() == 2 || end.getPozY() - begin.getPozY() == 2) //moved two square
	            {
	            	chessboard.twoSquareMovedPawn = end.piece;
	            }
	            else
	            {
	            	chessboard.twoSquareMovedPawn = null; //erase last saved move (for En passant)
	            }
	            //en passant end

	            if (end.getPozY() == 0 || end.getPozY() == 7) //promote Pawn
	            {
	                if (clearForwardHistory)
	                {
	                	promotedPiece = this.promotePawn(chessboard, end);
	                }
	            }
	        }
	        else if (!end.piece.name.equals("Pawn"))
	        {
	        	chessboard.twoSquareMovedPawn = null; //erase last saved move (for En passant)
	        }
	        //}

	        if (refresh)
	        {
	            this.unselect(chessboard);//unselect square
	            chessboard.repaint();
	        }

	        if (clearForwardHistory)
	        {
	        	chessboard.getMoves_history().clearMoveForwardStack();
	        	chessboard.getMoves_history().addMove(tempBegin, tempEnd, true, wasCastling, wasEnPassant, promotedPiece);
	        }
	        else
	        {
	        	chessboard.getMoves_history().addMove(tempBegin, tempEnd, false, wasCastling, wasEnPassant, promotedPiece);
	        }
	    }/*endOf-move()-*/
	    
	    private castling specialKingMovement(Chessboard chessboard, Square begin, Square end) {
	    	castling wasCastling = Moves.castling.none;
	    	
	    	if (!(end.piece).wasMotion)
	        {
	            (end.piece).wasMotion = true;
	        }

	        //Castling
	        if (begin.getPozX() + 2 == end.getPozX())
	        {
	        	this.move(chessboard, chessboard.squares[7][begin.getPozY()], chessboard.squares[end.getPozX() - 1][begin.getPozY()], false, false);
	            wasCastling = Moves.castling.shortCastling;
	        }
	        else if (begin.getPozX() - 2 == end.getPozX())
	        {
	        	this.move(chessboard, chessboard.squares[0][begin.getPozY()], chessboard.squares[end.getPozX() + 1][begin.getPozY()], false, false);
	            wasCastling = Moves.castling.longCastling;
	        }
	    	return wasCastling;
	    } 
	    
	    private Piece promotePawn(Chessboard chessboard, Square end) {
	    	String color;
	        if (end.piece.player.getColor() == Player.colors.white)
	        {
	            color = "W"; // promotionWindow was show with pieces in this color
	        }
	        else
	        {
	            color = "B";
	        }

	        String newPiece = JChessApp.getJcv().showPawnPromotionBox(color); //return name of new piece

	        if (newPiece.equals("Queen")) // transform pawn to queen
	        {
	        	Piece queen = new Piece(chessboard, end.piece.player, new QueenBehaviour(), "Queen");
	        	queen.chessboard = chessboard;
	            queen.player = end.piece.player;
	            queen.square = end;
	            end.piece = queen;
	        }
	        else if (newPiece.equals("Rook")) // transform pawn to rook
	        {
	        	Piece rook = new Piece(chessboard, end.piece.player, new RookBehaviour(), "Rook");
	            rook.chessboard = chessboard;
	            rook.player = end.piece.player;
	            rook.square = end;
	            end.piece = rook;
	        }
	        else if (newPiece.equals("Bishop")) // transform pawn to bishop
	        {
	        	Piece bishop = new Piece(chessboard, end.piece.player, new BishopBehaviour(), "Bishop");
	            bishop.chessboard = chessboard;
	            bishop.player = end.piece.player;
	            bishop.square = end;
	            end.piece = bishop;
	        }
	        else // transform pawn to knight
	        {
	        	Piece knight = new Piece(chessboard, end.piece.player, new KnightBehaviour(), "Knight");
	            knight.chessboard = chessboard;
	            knight.player = end.piece.player;
	            knight.square = end;
	            end.piece = knight;
	        }
	        return end.piece;
	    }
	    
	    public boolean redo(Chessboard chessboard, boolean refresh)
	    {
	        if ( chessboard.getSettings().gameType == Settings.gameTypes.local ) //redo only for local game
	        {
	            Move first = chessboard.getMoves_history().redo();

	            Square from = null;
	            Square to = null;

	            if (first != null)
	            {
	                from = first.getFrom();
	                to = first.getTo();

	                this.move(chessboard, chessboard.squares[from.getPozX()][from.getPozY()], chessboard.squares[to.getPozX()][to.getPozY()], true, false);
	                if (first.getPromotedPiece() != null)
	                {
	                    Piece pawn = chessboard.squares[to.getPozX()][to.getPozY()].piece;
	                    pawn.square = null;

	                    chessboard.squares[to.getPozX()][to.getPozY()].piece = first.getPromotedPiece();
	                    Piece promoted = chessboard.squares[to.getPozX()][to.getPozY()].piece;
	                    promoted.square = chessboard.squares[to.getPozX()][to.getPozY()];
	                }
	                return true;
	            }
	            
	        }
	        return false;
	    }
		
		public synchronized boolean undo(Chessboard chessboard, boolean refresh) //undo last move
	    {
	        Move last = chessboard.getMoves_history().undo();


	        if (last != null && last.getFrom() != null)
	        {
	            Square begin = last.getFrom();
	            Square end = last.getTo();
	            try
	            {
	                Piece moved = last.getMovedPiece();
	                chessboard.squares[begin.getPozX()][begin.getPozY()].piece = moved;

	                moved.square = chessboard.squares[begin.getPozX()][begin.getPozY()];

	                Piece taken = last.getTakenPiece();
	                if (last.getCastlingMove() != castling.none)
	                {
	                    Piece rook = null;
	                    if (last.getCastlingMove() == castling.shortCastling)
	                    {
	                        rook = chessboard.squares[end.getPozX() - 1][end.getPozY()].piece;
	                        chessboard.squares[7][begin.getPozY()].piece = rook;
	                        rook.square = chessboard.squares[7][begin.getPozY()];
	                        chessboard.squares[end.getPozX() - 1][end.getPozY()].piece = null;
	                    }
	                    else
	                    {
	                        rook = chessboard.squares[end.getPozX() + 1][end.getPozY()].piece;
	                        chessboard.squares[0][begin.getPozY()].piece = rook;
	                        rook.square = chessboard.squares[0][begin.getPozY()];
	                        chessboard.squares[end.getPozX() + 1][end.getPozY()].piece = null;
	                    }
	                    moved.wasMotion = false;
	                    rook.wasMotion = false;
	                }
	                else if (moved.name.equals("Rook"))
	                {
	                	moved.wasMotion = false;
	                }
	                else if (moved.name.equals("Pawn") && last.wasEnPassant())
	                {
	                	Piece pawn = last.getTakenPiece();
	                	chessboard.squares[end.getPozX()][begin.getPozY()].piece = pawn;
	                    pawn.square = chessboard.squares[end.getPozX()][begin.getPozY()];

	                }
	                else if (moved.name.equals("Pawn") && last.getPromotedPiece() != null)
	                {
	                    Piece promoted = chessboard.squares[end.getPozX()][end.getPozY()].piece;
	                    promoted.square = null;
	                    chessboard.squares[end.getPozX()][end.getPozY()].piece = null;
	                }

	                //check one more move back for en passant
	                Move oneMoveEarlier = chessboard.getMoves_history().getLastMoveFromHistory();
	                if (oneMoveEarlier != null && oneMoveEarlier.wasPawnTwoFieldsMove())
	                {
	                    Piece canBeTakenEnPassant = chessboard.squares[oneMoveEarlier.getTo().getPozX()][oneMoveEarlier.getTo().getPozY()].piece;
	                    if (canBeTakenEnPassant.name.equals("Pawn"))
	                    {
	                    	chessboard.twoSquareMovedPawn = canBeTakenEnPassant;
	                    }
	                }

	                if (taken != null && !last.wasEnPassant())
	                {
	                	chessboard.squares[end.getPozX()][end.getPozY()].piece = taken;
	                    taken.square = chessboard.squares[end.getPozX()][end.getPozY()];
	                }
	                else
	                {
	                	chessboard.squares[end.getPozX()][end.getPozY()].piece = null;
	                }

	                if (refresh)
	                {
	                	ChessboardLogic.getInstance().unselect(chessboard);//unselect square
	                	chessboard.repaint();
	                }

	            }
	            catch (java.lang.ArrayIndexOutOfBoundsException exc)
	            {
	                return false;
	            }
	            catch (java.lang.NullPointerException exc)
	            {
	                return false;
	            }

	            return true;
	        }
	        else
	        {
	            return false;
	        }
	    }
}
