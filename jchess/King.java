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
package jchess;

/**
 * Class to represent a chess pawn king. King is the most important
 * piece for the game. Loose of king is the and of game.
 * When king is in danger by the opponent then it's a Checked, and when have
 * no other escape then stay on a square "in danger" by the opponent
 * then it's a CheckedMate, and the game is over.
 */
import java.util.ArrayList;
import java.awt.Image;

public class King extends Piece
{

    public boolean wasMotion = false;//maybe change to: 'wasMotioned'
    //public boolean checked     = false;
    public static short value = 99;
    private static final Image imageWhite = GUI.loadImage("King-W.png");
    private static final Image imageBlack = GUI.loadImage("King-B.png");

    King(Chessboard chessboard, Player player)
    {
        super(chessboard, player, new KingBehaviour());
        //this.setImages("King-W.png", "King-B.png");
        this.symbol = "K";
        this.setImage();
        //this.image = imageWhite;
    }

    @Override
    void setImage()
    {
        if (this.player.color == this.player.color.black)
        {
            image = imageBlack;
        }
        else
        {
            image = imageWhite;
        }
        orgImage = image;
    }



    /** Method to check is the king is checked or stalemated
     *  @return int 0 if nothing, 1 if checkmate, else returns 2
     */
    public int isCheckmatedOrStalemated()
    {
        /*
         *returns: 0-nothing, 1-checkmate, 2-stalemate
         */
    	KingBehaviour behaviour = new KingBehaviour();
        if (this.allMoves().size() == 0)
        {
            for (int i = 0; i < 8; ++i)
            {
                for (int j = 0; j < 8; ++j)
                {
                    if (chessboard.squares[i][j].piece != null
                            && chessboard.squares[i][j].piece.player == this.player
                            && chessboard.squares[i][j].piece.allMoves().size() != 0)
                    {
                        return 0;
                    }
                }
            }

            if (this.isChecked())
            {
                return 1;
            }
            else
            {
                return 2;
            }
        }
        else
        {
            return 0;
        }
    }

    /** Method to check will the king be safe when move
     *  @return bool true if king is save, else returns false
     */
    public boolean willBeSafeWhenMoveOtherPiece(Square sqIsHere, Square sqWillBeThere) //long name ;)
    {
        Piece tmp = sqWillBeThere.piece;
        sqWillBeThere.piece = sqIsHere.piece; // move without redraw
        sqIsHere.piece = null;

        boolean ret = !this.isChecked();

        sqIsHere.piece = sqWillBeThere.piece;
        sqWillBeThere.piece = tmp;

        return ret;
    }
    
    public boolean isChecked(){
    	KingBehaviour behaviour = new KingBehaviour();
    	return !behaviour.isSafe(this.square, this.chessboard, this.square, this.player);
    }
}