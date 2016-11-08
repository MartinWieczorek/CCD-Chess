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


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

import jchess.Player.colors;

import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
Class to represent a piece (any kind) - this class should be extended to represent pawn, bishop etc.
 */
public class Piece
{
    Chessboard chessboard; // <-- this relations isn't in class diagram, but it's necessary :/
    public boolean wasMotion;
    public Square square;
    public Player player;
    public String name;
    public String symbol;
    protected PieceBehaviour behaviour;
    public Image orgImage;
    public Image image;
    public short value;

    public Piece(Chessboard chessboard, Player player, PieceBehaviour behaviour, String name)
    {
    	this.behaviour = behaviour;
        this.chessboard = chessboard;
        this.player = player;
        this.name = name;
        this.setImage();
        this.orgImage = image;
        
        if(name=="Bishop")
        {
        	this.symbol = "B";
        	this.value = 3;
        }
        if(name=="Knight")
        {
        	this.symbol = "N";
        	this.value = 3;
        }
        if(name=="Pawn")
        {
        	this.symbol = "";
        	this.value = 1;
        }
        if(name=="Queen")
        {
        	this.symbol = "Q";
        	this.value = 9;
        }
        if(name=="Rook")
        {
        	this.symbol = "R";
        	this.value = 5;
        }
        if(name=="King")
        {
        	this.symbol = "K";
        	this.value = 99;
        }
    }
    /* Method to draw piece on chessboard
     * @graph : where to draw
     */

    public final void draw(Graphics g)
    {
        try
        {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Point topLeft = this.chessboard.getTopLeftPoint();
            int height = this.chessboard.get_square_height();
            int x = (this.square.pozX * height) + topLeft.x;
            int y = (this.square.pozY * height) + topLeft.y;
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

        }
        catch (java.lang.NullPointerException exc)
        {
            System.out.println("Something wrong when painting piece: " + exc.getMessage());
        }
    }

    //void clean(){}

    /** method check if Piece can move to given square
     * @param square square where piece want to move (Square object)
     * @param allmoves  all moves which can piece do
     * */
    public boolean canMove(Square square, ArrayList<Square> allmoves)
    {
        ArrayList<Square> moves = allmoves;
        for (Iterator<Square> it = moves.iterator(); it.hasNext();)
        {
            Square sq = (Square) it.next();//get next from iterator
            if (sq == square)
            {//if address is the same
                return true; //piece canMove
            }
        }
        return false;//if not, piece cannot move
    }

    public void setImage()
    {
    	if (this.player.color == colors.black)
        {
            image = GUI.loadImage(name+"-B.png");
        }
        else
        {
            image = GUI.loadImage(name+"-W.png");
        }
    }

    public ArrayList<Square> allMoves() {
    	return this.behaviour.getMoves(this.chessboard, this.square, this.player);
    }

    public void changeBehaviour(PieceBehaviour behaviour){
    	this.behaviour = behaviour;
    }
    
    public String getSymbol()
    {
        return this.symbol;
    }
    public int isCheckmatedOrStalemated()
    {
        /*
         *returns: 0-nothing, 1-checkmate, 2-stalemate
         */
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
