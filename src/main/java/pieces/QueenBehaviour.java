package pieces;

import java.util.ArrayList;
import core.Chessboard;
import core.Player;
import core.Square;

/**
 * Queen can move:
 	|_|_|_|X|_|_|_|X|7
    |X|_|_|X|_|_|X|_|6
    |_|X|_|X|_|X|_|_|5
    |_|_|X|X|x|_|_|_|4
    |X|X|X|Q|X|X|X|X|3
    |_|_|X|X|X|_|_|_|2
    |_|X|_|X|_|X|_|_|1
    |X|_|_|X|_|_|X|_|0
    0 1 2 3 4 5 6 7
 */

public class QueenBehaviour implements PieceBehaviour {

	@Override
	public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player) {
	
		ArrayList<Square> list = new ArrayList<Square>();
		PieceBehaviour behaviour = new BishopBehaviour();
		list.addAll(behaviour.getMoves(chessboard,square,player));
		behaviour = new RookBehaviour();
		list.addAll(behaviour.getMoves(chessboard,square,player));
		return list;
	}

}
