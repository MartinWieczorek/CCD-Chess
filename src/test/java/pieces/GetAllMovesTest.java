package pieces;

import org.junit.Before;

import core.Chessboard;
import core.Game;
import core.Moves;
import core.Settings;
import core.Square;

public class GetAllMovesTest {

	protected Game game;
	protected Settings settings;
	protected Moves moves_history;
	protected Chessboard testBoard;
	
	@Before
	public void initTest() {
		game = new Game();
		settings = game.getSettings();
		moves_history = new Moves(game);
		testBoard = game.getChessboard();
	}
	
	// um string des szenarios erweitern
	protected boolean isSolution(Square sq, Square[] Solution) {
		for (int i = 0; i < Solution.length; i++) {
			if (Solution[i].getPozX() == sq.getPozX() && Solution[i].getPozY() == sq.getPozY())
				return true;
		}
		System.out.println("First error on Pos x: " + sq.getPozX() + " y: " + sq.getPozY());
		return false;
	}
}
