package pieces;

import java.util.ArrayList;

import core.Chessboard;
import core.Player;
import core.Square;

public class BehaviourFunktions {
	
	  /** Method to check if the given Square is targeted by an opponent
     * @param s Square for testing
     * @return boolean true if Square is save, else returns false
     */
	public static boolean isSafe(Chessboard chessboard, Square square, Player player)
	{
		for(int x = 0; x < chessboard.getNumSquares(); ++x){
			for(int y = 0; y < chessboard.getNumSquares(); ++y){
				Square originSquare = chessboard.getSquares()[x][y];
				if(originSquare.getPiece() != null && originSquare.getPiece().getPlayer() != player){
					for (PieceBehaviour behaviour : originSquare.getPiece().getBehaviours()) {
						if(behaviour.getUnsaveMoves(chessboard, originSquare, originSquare.getPiece().getPlayer()).contains(square)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	/** Method check if piece on given position has other owner than calling piece
     * @param x x position on chessboard
     * @param y y position on chessboard
     * @param player owning player of calling piece
     * @param chessboard chessboard of the Game
     * @return true if owner(player) is different
     * */
    static boolean enemyPieceOnPosition(int x, int y, Chessboard chessboard, Player player)
    {
        Square square = chessboard.getSquares()[x][y];
        if (square.getPiece() == null) {
            return false;
        }
        if (player != square.getPiece().getPlayer()) {
            return true;
        }
        return false;
    }
    
    /**
     * checks if an given Position is empty
     * @param x x position on chessboard
     * @param y y position on chessboard
     * @param player owning player of calling piece
     * @param chessboard chessboard of the Game
     * @return true if position is empty
     */
    static boolean checkSpaceAtPosition (int x, int y, Player player, Chessboard chessboard)
    {
    	if(chessboard.getSquares()[x][y].getPiece() == null) return true;
    	else return false;
    }  

    /** Method is useful for out of bounds protection
     * @param x x position on chessboard
     * @param y y position on chessboard
     * @param chessboard chessboard of the Game
     * @return true if parameters are out of bounds (array)
     * */
    static boolean isout(int x, int y, Chessboard chessboard)
    {
    	int numSquares = chessboard.getNumSquares();
    	int cornerSquares = chessboard.getCornerSquares();
        if (x < 0 || y < 0 || x > numSquares - 1 || y > numSquares - 1 || //out of outer borders?
        	(x < cornerSquares && ( y > numSquares - 1 - cornerSquares || y < cornerSquares)) || //inside left corners?
        	(x > numSquares - cornerSquares - 1 && ( y > numSquares - 1 - cornerSquares || y < cornerSquares ))) //inside right corners?
        {
            return true;
        }
        return false;
    }

	public static boolean isTarget(int x, int y, Chessboard chessboard, Square square, PieceBehaviour behaviour, Player player) {
		if(chessboard.getSquares()[x][y].getPiece() != null && chessboard.getSquares()[x][y].getPiece().getName() == "Wall"
				&& !testBreakWall(square, chessboard, behaviour, player)) {
			return false;
		}
		return true;
	}
	
	private static boolean testBreakWall(Square square, Chessboard chessboard, PieceBehaviour behaviour, Player player) {
		
		ArrayList<PieceBehaviour> neigbourBehaviours = new ArrayList<PieceBehaviour>();
		
		if(square.getPozX()-1 < 0 && chessboard.getSquares()[square.getPozX()-1][square.getPozY()].getPiece() != null &&
				chessboard.getSquares()[square.getPozX()-1][square.getPozY()].getPiece().getPlayer() == player) {
			neigbourBehaviours.addAll(chessboard.getSquares()[square.getPozX()-1][square.getPozY()].getPiece().getBehaviours());
		}
		if(square.getPozX()+1 < chessboard.getNumSquares() && chessboard.getSquares()[square.getPozX()+1][square.getPozY()].getPiece() != null &&
				chessboard.getSquares()[square.getPozX()+1][square.getPozY()].getPiece().getPlayer() == player) {
			neigbourBehaviours.addAll(chessboard.getSquares()[square.getPozX()+1][square.getPozY()].getPiece().getBehaviours());
		}
		if(square.getPozY()-1 < 0 && chessboard.getSquares()[square.getPozX()][square.getPozY()-1].getPiece() != null &&
				chessboard.getSquares()[square.getPozX()][square.getPozY()-1].getPiece().getPlayer() == player) {
			neigbourBehaviours.addAll(chessboard.getSquares()[square.getPozX()][square.getPozY()-1].getPiece().getBehaviours());
		}
		if(square.getPozY()+1 < chessboard.getNumSquares() && chessboard.getSquares()[square.getPozX()][square.getPozY()+1].getPiece() != null &&
				chessboard.getSquares()[square.getPozX()][square.getPozY()+1].getPiece().getPlayer() == player) {
			neigbourBehaviours.addAll(chessboard.getSquares()[square.getPozX()][square.getPozY()+1].getPiece().getBehaviours());
		}
		
		for (PieceBehaviour pieceBehaviour : neigbourBehaviours) {
			if (behaviour.getClass() == BreakWall.class && pieceBehaviour.getClass() == BreakWall.class) {
				return true;
			}
		}

		return false;
	}
}
