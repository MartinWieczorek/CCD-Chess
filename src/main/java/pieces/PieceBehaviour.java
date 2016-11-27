package pieces;

import java.util.ArrayList;
import core.Chessboard;
import core.Player;
import core.Square;

public interface PieceBehaviour {
	
	abstract public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player);
}
