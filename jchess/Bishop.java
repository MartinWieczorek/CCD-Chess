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

public class Bishop extends Piece
{

    public static short value = 3;
    protected static final Image imageWhite = GUI.loadImage("Bishop-W.png");
    protected static final Image imageBlack = GUI.loadImage("Bishop-B.png");

    Bishop(Chessboard chessboard, Player player)
    {
        super(chessboard, player, new BishopBehaviour());      //call initializer of super type: Piece
        //this.setImages("Bishop-W.png", "Bishop-B.png");
        this.symbol = "B";
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

    /**
     * Annotation to superclass Piece changing pawns location
     * @return  ArrayList with new possition of piece
     */
}
