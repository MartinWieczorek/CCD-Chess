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
package core;

import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import core.Moves.castling;
import pieces.PawnBehaviour;
import pieces.KingBehaviour;
import pieces.QueenBehaviour;
import pieces.BishopBehaviour;
import pieces.KnightBehaviour;
import pieces.RookBehaviour;
import pieces.Piece;
import ui.GUI;

/** Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squers of chessboard and sets the pieces(pawns)
 * witch the owner is current player on it.
 */
public class Chessboard extends JPanel
{
	private static final Logger logger = LogManager.getLogger(Chessboard.class);
	
    public static final int top = 0;
    public static final int bottom = 7;
    public Square squares[][];//squares of chessboard
    private static final Image orgImage = GUI.loadImage("chessboard.png");//image of chessboard
    private static Image image = Chessboard.orgImage;//image of chessboard
    private static final Image org_sel_square = GUI.loadImage("sel_square.png");//image of highlited square
    private static Image sel_square = org_sel_square;//image of highlited square
    private static final Image org_able_square = GUI.loadImage("able_square.png");//image of square where piece can go
    private static Image able_square = org_able_square;//image of square where piece can go
    public Square activeSquare;
    private Image upDownLabel = null;
    private Image LeftRightLabel = null;
    private Point topLeft = new Point(0, 0);
    private int active_x_square;
    private int active_y_square;
    private float square_height;//height of square
    public static final int img_x = 5;//image x position (used in JChessView class!)
    public static final int img_y = img_x;//image y position (used in JChessView class!)
    public static final int img_widht = 480;//image width
    public static final int img_height = img_widht;//image height
    private ArrayList<Square> moves;
    private Settings settings;
    public Piece kingWhite;
    public Piece kingBlack;
    //For En passant:
    //|-> Pawn whose in last turn moved two square
    public Piece twoSquareMovedPawn = null;
    private Moves moves_history;

    /** Chessboard class constructor
     * @param settings reference to Settings class object for this chessboard
     * @param moves_history reference to Moves class object for this chessboard 
     */
    public Chessboard(Settings settings, Moves moves_history)
    {
    	logger.info("chessboard constructor");
    	
        this.settings = settings;
        this.activeSquare = null;
        this.setSquare_height(img_height / 8);//we need to devide to know height of field
        this.squares = new Square[8][8];//initalization of 8x8 chessboard
        this.setActive_x_square(0);
        this.setActive_y_square(0);
        for (int i = 0; i < 8; i++)
        {//create object for each square
            for (int y = 0; y < 8; y++)
            {
                this.squares[i][y] = new Square(i, y, null);
            }
        }//--endOf--create object for each square
        this.moves_history = moves_history;
        this.setDoubleBuffered(true);
        this.drawLabels((int) this.getSquare_height());
    }/*--endOf-Chessboard--*/
    
    public int get_widht()
    {
        return this.get_widht(false);
    }
    
    public int get_height()
    {
        return this.get_height(false);
    }

    public int get_widht(boolean includeLables)
    {
        return this.getHeight();
    }/*--endOf-get_widht--*/

    int get_height(boolean includeLabels)
    {
        if (this.settings.renderLabels)
        {
            return Chessboard.image.getHeight(null) + getUpDownLabel().getHeight(null);
        }
        return Chessboard.image.getHeight(null);
    }/*--endOf-get_height--*/

    public int get_square_height()
    {
        int result = (int) this.getSquare_height();
        return result;
    }

    /**
     * Annotations to superclass Game updateing and painting the crossboard
     */
    @Override
    public void update(Graphics g)
    {
        repaint();
    }

    public Point getTopLeftPoint()
    {
        if (this.settings.renderLabels)
        {
            return new Point(this.topLeft.x + this.getUpDownLabel().getHeight(null), this.topLeft.y + this.getUpDownLabel().getHeight(null));
        }
        return this.topLeft;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Point topLeftPoint = this.getTopLeftPoint();
        if (this.settings.renderLabels)
        {
            if(topLeftPoint.x <= 0 && topLeftPoint.y <= 0) //if renderLabels and (0,0), than draw it! (for first run)
            {
                this.drawLabels();
            }
            g2d.drawImage(this.getUpDownLabel(), 0, 0, null);
            g2d.drawImage(this.getUpDownLabel(), 0, Chessboard.image.getHeight(null) + topLeftPoint.y, null);
            g2d.drawImage(this.LeftRightLabel, 0, 0, null);
            g2d.drawImage(this.LeftRightLabel, Chessboard.image.getHeight(null) + topLeftPoint.x, 0, null);
        }
        g2d.drawImage(image, topLeftPoint.x, topLeftPoint.y, null);//draw an Image of chessboard
        for (int i = 0; i < 8; i++) //drawPiecesOnSquares
        {
            for (int y = 0; y < 8; y++)
            {
                if (this.squares[i][y].piece != null)
                {
                    this.squares[i][y].piece.draw(g);//draw image of Piece
                }
            }
        }//--endOf--drawPiecesOnSquares
        if ((this.getActive_x_square() != 0) && (this.getActive_y_square() != 0)) //if some square is active
        {
            g2d.drawImage(sel_square, 
                            ((this.getActive_x_square() - 1) * (int) getSquare_height()) + topLeftPoint.x,
                            ((this.getActive_y_square() - 1) * (int) getSquare_height()) + topLeftPoint.y, null);//draw image of selected square
            Square tmpSquare = this.squares[(int) (this.getActive_x_square() - 1)][(int) (this.getActive_y_square() - 1)];
            if (tmpSquare.piece != null)
            {
                this.moves = this.squares[(int) (this.getActive_x_square() - 1)][(int) (this.getActive_y_square() - 1)].piece.allMoves();
            }

            for (Iterator<Square> it = moves.iterator(); moves != null && it.hasNext();)
            {
                Square sq = (Square) it.next();
                g2d.drawImage(able_square, 
                        (sq.getPozX() * (int) getSquare_height()) + topLeftPoint.x,
                        (sq.getPozY() * (int) getSquare_height()) + topLeftPoint.y, null);
            }
        }
    }/*--endOf-paint--*/

    public void resizeChessboard(int height)
    {
        BufferedImage resized = new BufferedImage(height, height, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics g = resized.createGraphics();
        g.drawImage(Chessboard.orgImage, 0, 0, height, height, null);
        g.dispose();
        Chessboard.image = resized.getScaledInstance(height, height, 0);
        this.setSquare_height((float) (height / 8));
        if (this.settings.renderLabels)
        {
            height += 2 * (this.getUpDownLabel().getHeight(null));
        }
        this.setSize(height, height);

        resized = new BufferedImage((int) getSquare_height(), (int) getSquare_height(), BufferedImage.TYPE_INT_ARGB_PRE);
        g = resized.createGraphics();
        g.drawImage(Chessboard.org_able_square, 0, 0, (int) getSquare_height(), (int) getSquare_height(), null);
        g.dispose();
        Chessboard.able_square = resized.getScaledInstance((int) getSquare_height(), (int) getSquare_height(), 0);

        resized = new BufferedImage((int) getSquare_height(), (int) getSquare_height(), BufferedImage.TYPE_INT_ARGB_PRE);
        g = resized.createGraphics();
        g.drawImage(Chessboard.org_sel_square, 0, 0, (int) getSquare_height(), (int) getSquare_height(), null);
        g.dispose();
        Chessboard.sel_square = resized.getScaledInstance((int) getSquare_height(), (int) getSquare_height(), 0);
        this.drawLabels();
    }

    protected void drawLabels()
    {
        this.drawLabels((int) this.getSquare_height());
    }

    protected final void drawLabels(int square_height)
    {
        //BufferedImage uDL = new BufferedImage(800, 800, BufferedImage.TYPE_3BYTE_BGR);
        int min_label_height = 20;
        int labelHeight = (int) Math.ceil(square_height / 4);
        labelHeight = (labelHeight < min_label_height) ? min_label_height : labelHeight;
        int labelWidth =  (int) Math.ceil(square_height * 8 + (2 * labelHeight)); 
        BufferedImage uDL = new BufferedImage(labelWidth + min_label_height, labelHeight, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D uDL2D = (Graphics2D) uDL.createGraphics();
        uDL2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        uDL2D.setColor(Color.white);

        uDL2D.fillRect(0, 0, labelWidth + min_label_height, labelHeight);
        uDL2D.setColor(Color.black);
        uDL2D.setFont(new Font("Arial", Font.BOLD, 12));
        int addX = (square_height / 2);
        if (this.settings.renderLabels)
        {
            addX += labelHeight;
        }

        String[] letters =
        {
            "a", "b", "c", "d", "e", "f", "g", "h"
        };
        if (!this.settings.upsideDown)
        {
            for (int i = 1; i <= letters.length; i++)
            {
                uDL2D.drawString(letters[i - 1], (square_height * (i - 1)) + addX, 10 + (labelHeight / 3));
            }
        }
        else
        {
            int j = 1;
            for (int i = letters.length; i > 0; i--, j++)
            {
                uDL2D.drawString(letters[i - 1], (square_height * (j - 1)) + addX, 10 + (labelHeight / 3));
            }
        }
        uDL2D.dispose();
        this.setUpDownLabel(uDL);

        uDL = new BufferedImage(labelHeight, labelWidth + min_label_height, BufferedImage.TYPE_3BYTE_BGR);
        uDL2D = (Graphics2D) uDL.createGraphics();
        uDL2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        uDL2D.setColor(Color.white);
        //uDL2D.fillRect(0, 0, 800, 800);
        uDL2D.fillRect(0, 0, labelHeight, labelWidth + min_label_height);
        uDL2D.setColor(Color.black);
        uDL2D.setFont(new Font("Arial", Font.BOLD, 12));

        if (this.settings.upsideDown)
        {
            for (int i = 1; i <= 8; i++)
            {
                uDL2D.drawString(new Integer(i).toString(), 3 + (labelHeight / 3), (square_height * (i - 1)) + addX);
            }
        }
        else
        {
            int j = 1;
            for (int i = 8; i > 0; i--, j++)
            {
                uDL2D.drawString(new Integer(i).toString(), 3 + (labelHeight / 3), (square_height * (j - 1)) + addX);
            }
        }
        uDL2D.dispose();
        this.LeftRightLabel = uDL;
    }

    public void componentMoved(ComponentEvent e)
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void componentShown(ComponentEvent e)
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void componentHidden(ComponentEvent e)
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    

	public Settings getSettings()
	{
		return settings;
	}
	

	public Image getUpDownLabel()
	{
		return upDownLabel;
	}

	public void setUpDownLabel(Image upDownLabel)
	{
		this.upDownLabel = upDownLabel;
	}
	

	public float getSquare_height()
	{
		return square_height;
	}
	
	public void setSquare_height(float square_height)
	{
		this.square_height = square_height;
	}

	public int getActive_x_square()
	{
		return active_x_square;
	}

	public void setActive_x_square(int active_x_square)
	{
		this.active_x_square = active_x_square;
	}

	public int getActive_y_square()
	{
		return active_y_square;
	}

	public void setActive_y_square(int active_y_square)
	{
		this.active_y_square = active_y_square;
	}
	

	public Moves getMoves_history()
	{
		return moves_history;
	}
}
