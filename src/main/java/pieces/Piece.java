/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package pieces;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

import core.Chessboard;
import core.Player;
import core.Square;
import pieces.KingBehaviour;
import pieces.Piece;
import pieces.PieceBehaviour;

import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Class to represent a piece (any kind) - this class should be extended to
 * represent pawn, bishop etc.
 */
public class Piece {
	private Chessboard chessboard;
	public boolean wasMotion;
	// public Square square;
	public Player player;
	public String name;
	public String symbol;
	protected PieceBehaviour[] behaviours;
	public Image orgImage;
	public Image image;

	public Piece(Chessboard chessboard, Player player, PieceBehaviour[] behaviours, String name) {
		this.behaviours = behaviours;
		this.chessboard = chessboard;
		this.player = player;
		this.name = name;
		this.setImage();
		this.orgImage = image;
		this.wasMotion = false;

		switch (name) {
		case "Bishop":
			this.symbol = "B";
			break;
		case "Knight":
			this.symbol = "N";
			break;
		case "Pawn":
			this.symbol = "P";
			break;
		case "Queen":
			this.symbol = "Q";
			break;
		case "Rook":
			this.symbol = "R";
			break;
		case "King":
			this.symbol = "K";
			break;
		}
	}

	// public void setSquare(Square square)
	// {
	// this.square = square;
	// }
	//
	// public Square getSquare()
	// {
	// return this.square;
	// }

	/**
	 * method check if Piece can move to given square
	 * 
	 * @param square
	 *            square where piece want to move (Square object)
	 * @param allmoves
	 *            all moves which can piece do
	 */
	public boolean canMove(Square square, ArrayList<Square> allmoves) {
		ArrayList<Square> moves = allmoves;
		for (Iterator<Square> it = moves.iterator(); it.hasNext();) {
			Square sq = (Square) it.next();// get next from iterator
			if (sq == square) {// if address is the same
				return true; // piece canMove
			}
		}
		return false;// if not, piece cannot move
	}

	public void setImage() {
		if (this.player.getColor() == core.Player.colors.black) {
			image = ui.GUI.loadImage(name + "-B.png");
		} else {
			image = ui.GUI.loadImage(name + "-W.png");
		}
	}

	public ArrayList<Square> allMoves(Square sq) {
		ArrayList<Square> result = new ArrayList<Square>();
		for (int i = 0; i < behaviours.length; ++i) {
			result.addAll(behaviours[i].getMoves(chessboard, sq, player));
		}
		return result;
	}

	public void changeBehaviour(PieceBehaviour[] behaviours) {
		this.behaviours = behaviours;
	}

	public String getSymbol() {
		return this.symbol;
	}

	/**
	 * 
	 * @return
	 */
	public int isCheckmatedOrStalemated() {
		int x = 0;
		int y = 0;
		for (int i = 0; i < chessboard.getNumSquares(); ++i) {
			for (int j = 0; j < chessboard.getNumSquares(); ++j) {
				if (chessboard.getSquares()[i][j].piece != null && chessboard.getSquares()[i][j].piece.player == this.player
						&& chessboard.getSquares()[i][j].piece.allMoves(chessboard.getSquares()[i][j]).size() != 0) {
					return 0;
				}
				y = j;
			}
			x = i;
		}
		if (this.isChecked(chessboard.getSquares()[x][y])) {
			return 1;
		} else {
			return 2;
		}
	}

	/**
	 * Method to check will the king be safe when move
	 * 
	 * @return bool true if king is save, else returns false
	 */
	public boolean willBeSafeWhenMoveOtherPiece(Square sqIsHere, Square sqWillBeThere) 
	{
		Piece tmp = sqWillBeThere.piece;
		sqWillBeThere.piece = sqIsHere.piece; // move without redraw
		sqIsHere.piece = null;

		boolean ret = !this.isChecked(sqIsHere);

		sqIsHere.piece = sqWillBeThere.piece;
		sqWillBeThere.piece = tmp;

		return ret;
	}

	public boolean isChecked(Square sq) {
		return !KingBehaviour.getInstance().isSafe(sq, this.chessboard, sq, this.player);
	}
    
    /** Method to draw piece on chessboard
     * @param g : graph where to draw
     */
    public final void draw(Graphics g, int posX, int posY)
    {
        try
        {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Point topLeft = this.chessboard.getTopLeftPoint();
            int height = this.chessboard.get_square_height();
            int x = (posX * height) + topLeft.x;
			int y = (posY * height) + topLeft.y;
            //float addX = (height - image.getWidth(null)) / 2;
            //float addY = (height - image.getHeight(null)) / 2;
            if (image != null && g != null)
            {
                Image tempImage = orgImage;
                BufferedImage resized = new BufferedImage(height, height, BufferedImage.TYPE_INT_ARGB_PRE);
                Graphics2D imageGr = (Graphics2D) resized.createGraphics();
                imageGr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                imageGr.drawImage(tempImage, 0, 0, height, height, null);
                imageGr.dispose();
                image = resized.getScaledInstance(height, height, 0);
                g2d.drawImage(image, x, y, null);
            }
            else
            {
                System.out.println("image is null!");
            }

		} catch (java.lang.NullPointerException exc) {
			System.out.println("Something wrong when painting piece: " + exc.getMessage());
		}
	}
}
