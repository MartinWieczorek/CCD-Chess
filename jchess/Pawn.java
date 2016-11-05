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

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Class to represent a pawn piece
 * Pawn can move only forward and can beat only across
 * In first move pawn can move 2 squares
 * pawn can be upgrade to rook, knight, bishop, Queen if it's in the
 * Squares nearest the side where opponent is located
 */

public class Pawn extends Piece
{

    boolean down;
    protected static final Image imageWhite = GUI.loadImage("Pawn-W.png");
    protected static final Image imageBlack = GUI.loadImage("Pawn-B.png");
    public static short value = 1;

    Pawn(Chessboard chessboard, Player player)
    {
        super(chessboard, player, new PawnBehaviour());
        //this.setImages("Pawn-W.png", "Pawn-B.png");
        this.symbol = "";
        this.setImage();
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

    void promote(Piece newPiece)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
