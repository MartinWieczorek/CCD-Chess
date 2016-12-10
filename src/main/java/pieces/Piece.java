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
import java.awt.image.RescaleOp;

/**
 * Class to represent a piece (any kind) - this class should be extended to
 * represent pawn, bishop etc.
 */
public class Piece {
	private Chessboard chessboard;
	private boolean wasMotion;
	private Player player;
	private String name;
	private String symbol;
	protected PieceBehaviour[] behaviours;
	private Image orgImage;
	private Image image;
	private float[] imageColorScales;
    private float[] imageColorOffsets;
    private RescaleOp imageColorRescaleOperator;

	/**
	 * Construct a new Piece on given chessboard for given player with given behaviors.
	 * @param name the name of the type of the piece specifies the symbol of the pie and the way other pieces interact with this one.
	 * 	only Bishop, Knight, Pawn, Queen, Rook or King should be used.
	 */
	public Piece(Chessboard chessboard, Player player, PieceBehaviour[] behaviours, String name) {
		this.behaviours = behaviours;
		this.chessboard = chessboard;
		this.setPlayer(player);
		this.setName(name);
		this.setImage();
		this.orgImage = image;
		this.setWasMotion(false);
		setImageColorScales();
		this.imageColorOffsets = new float[4];
		this.imageColorRescaleOperator = new RescaleOp(imageColorScales, imageColorOffsets, null);

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

	private void setImageColorScales() {
		switch(this.getPlayer().getColor())
		{
		case black:
			imageColorScales = new float[]{ 1f, 1f, 1f, 1f };
			break;
		case green:
			imageColorScales = new float[]{ 0f, 1f, 0f, 1f };
			break;
		case red:
			imageColorScales = new float[]{ 1f, 0f, 0f, 1f };
			break;
		case white:
			imageColorScales = new float[]{ 1f, 1f, 1f, 1f };
			break;
		default:
			break;
			
		}
	}

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

	/**
	 * Sets the image which is used for representation on the board.
	 * Image name is specified by Piece name and player color.
	 */
	public void setImage() {
		if (this.getPlayer().getColor() == core.Player.colors.black) {
			image = ui.GUI.loadImage(getName() + "-B.png");
		} else {
			image = ui.GUI.loadImage(getName() + "-W.png");
		}
	}

	/**
	 * computes all current possible moves of the piece
	 * @param sq squarePosition of calling piece
	 * @return ArrayList<Square> of all possible movement destinations 
	 */
	public ArrayList<Square> allMoves(Square sq) {
		ArrayList<Square> result = new ArrayList<Square>();
		for (int i = 0; i < behaviours.length; ++i) {
			result.addAll(behaviours[i].getMoves(chessboard, sq, getPlayer()));
		}
		return result;
	}

	/**
	 * Overrides current PieceBehaviours
	 * @param behaviours new behaviors
	 */
	public void changeBehaviour(PieceBehaviour[] behaviours) {
		this.behaviours = behaviours;
	}

	/**
	 * checks if piece is check- or stalemate.
	 * @return 0 - when nothing <br>
	 *         1 - when checkmate <br>
	 *         2 - when stalemate <br>
	 */  
	public int isCheckmatedOrStalemated() 
	{
		for (int i = 0; i < chessboard.getNumSquares(); ++i) {
			for (int j = 0; j < chessboard.getNumSquares(); ++j) {
				if (chessboard.getSquares()[i][j].getPiece() != null && chessboard.getSquares()[i][j].getPiece().getPlayer() == this.getPlayer()
						&& chessboard.getSquares()[i][j].getPiece().allMoves(chessboard.getSquares()[i][j]).size() != 0) {
					return 0;
				}
			}
		}
		if (this.isChecked(chessboard.getKing(this.player)) ) {
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
		Piece tmp = sqWillBeThere.getPiece();
		sqWillBeThere.setPiece(sqIsHere.getPiece()); // move without redraw
		sqIsHere.setPiece(null);

		boolean ret = !this.isChecked(sqIsHere);

		sqIsHere.setPiece(sqWillBeThere.getPiece());
		sqWillBeThere.setPiece(tmp);

		return ret;
	}

	public boolean isChecked(Square sq) {
		return !KingBehaviour.getInstance().isSafe(sq, this.chessboard, sq, this.getPlayer());
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
                g2d.drawImage(resized, imageColorRescaleOperator, x, y);
                
            }
            else
            {
                System.out.println("image is null!");
            }

		} catch (java.lang.NullPointerException exc) {
			System.out.println("Something wrong when painting piece: " + exc.getMessage());
		}
	}


	public String getSymbol() {
		return this.symbol;
	}
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isWasMotion() {
		return wasMotion;
	}

	public void setWasMotion(boolean wasMotion) {
		this.wasMotion = wasMotion;
	}
}
