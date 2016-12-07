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
import pieces.BishopBehaviour;
import pieces.KnightBehaviour;
import pieces.RookBehaviour;
import pieces.Piece;
import pieces.PieceBehaviour;
import ui.GUI;

/** Class to represent chessboard. Chessboard is made from squares.
 * It is setting the squers of chessboard and sets the pieces(pawns)
 * witch the owner is current player on it.
 */
public class Chessboard extends JPanel
{
	private static final Logger logger = LogManager.getLogger(Chessboard.class);
	
	private static final int top = 0;
    private static final int bottom = 13;
    private Square squares[][]; //squares of chessboard
    private int numberSquares = 14;
    private int cornerSquares = 3;
    private static final Image orgImage = GUI.loadImage("chessboard_14x14.png");//image of chessboard
    private static Image image = Chessboard.orgImage;//image of chessboard
    private static final Image org_sel_square = GUI.loadImage("sel_square.png");//image of highlited square
    private static Image sel_square = org_sel_square;//image of highlited square
    private static final Image org_able_square = GUI.loadImage("able_square.png");//image of square where piece can go
    private static Image able_square = org_able_square;//image of square where piece can go
    private Square activeSquare;
    private Image upDownLabel = null;
    private Image LeftRightLabel = null;
    private Point topLeft = new Point(0, 0);
    private int active_x_square;
    private int active_y_square;
    private float square_height;//height of square
    private static final int img_x = 5;//image x position (used in JChessView class!)
    private static final int img_y = img_x;//image y position (used in JChessView class!)
    private static final int img_widht = 840;//image width
    private static final int img_height = img_widht;//image height
    private ArrayList<Square> moves;
    private Settings settings;
//    private Square kingWhite;
//    private Square kingBlack;
//    private Square kingRed;
//    private Square kingGreen;
    //For En passant:
    //|-> Pawn whose in last turn moved two square
    private Square twoSquareMovedPawn = null;
    private Moves moves_history;

    /** Chessboard class constructor
     * @param settings reference to Settings class object for this chessboard
     * @param moves_history reference to Moves class object for this chessboard 
     */
    public Chessboard(Settings settings, Moves moves_history)
    {
    	logger.info("chessboard constructor");
    	
        this.settings = settings;
        this.setActiveSquare(null);
        //this.square_height = img_height / 8;//we need to devide to know height of field
        this.square_height = img_height / numberSquares;//we need to devide to know height of field
        this.setSquares(new Square[numberSquares][numberSquares]);//initalization of 8x8 chessboard
        this.setActive_x_square(0);
        this.setActive_y_square(0);
        for (int i = 0; i < numberSquares; i++)
        {//create object for each square
            for (int y = 0; y < numberSquares; y++)
            {
                this.getSquares()[i][y] = new Square(i, y, null);
            }
        }//--endOf--create object for each square
        this.moves_history = moves_history;
        this.setDoubleBuffered(true);
        this.drawLabels((int) this.getSquare_height());
    }/*--endOf-Chessboard--*/

    /** Method selecting piece in chessboard
     * @param  sq square to select (when clicked))
     */
    public void select(Square sq)
    {
        this.setActiveSquare(sq);
        this.active_x_square = sq.getPozX() + 1;
        this.active_y_square = sq.getPozY() + 1;

        //this.draw();//redraw
        logger.info("active_x: " + this.active_x_square + " active_y: " + this.active_y_square);//4tests
        repaint();

    }/*--endOf-select--*/


    /** Method set variables active_x_square & active_y_square
     * to 0 values.
     */
    public void unselect()
    {
        this.active_x_square = 0;
        this.active_y_square = 0;
        this.setActiveSquare(null);
        //this.draw();//redraw
        repaint();
    }/*--endOf-unselect--*/
    

    public int getCornerSquares(){
    	return this.cornerSquares;
    }
    
	public int getNumSquares() {
		return this.numberSquares;
	}
    
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

    // responsible for drawing chessboard and pieces
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
        for (int i = 0; i < numberSquares; i++) //drawPiecesOnSquares
        {
            for (int y = 0; y < numberSquares; y++)
            {
                if (this.getSquares()[i][y].piece != null)
                {
                    this.getSquares()[i][y].piece.draw(g, i, y);//draw image of Piece
                }
            }
        }//--endOf--drawPiecesOnSquares
        if ((this.getActive_x_square() != 0) && (this.getActive_y_square() != 0)) //if some square is active
        {
            g2d.drawImage(sel_square, 
                            ((this.getActive_x_square() - 1) * (int) getSquare_height()) + topLeftPoint.x,
                            ((this.getActive_y_square() - 1) * (int) getSquare_height()) + topLeftPoint.y, null);//draw image of selected square
            Square tmpSquare = this.getSquares()[(int) (this.getActive_x_square() - 1)][(int) (this.getActive_y_square() - 1)];
            if (tmpSquare.piece != null)
            {
                this.moves = this.getSquares()[(int) (this.getActive_x_square() - 1)][(int) (this.getActive_y_square() - 1)].allMoves();
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
        //this.square_height = (float) (height / 8);
        this.square_height = (float) (height / numberSquares);
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
        int labelWidth =  (int) Math.ceil(square_height * numberSquares + (2 * labelHeight)); 
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
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"
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
            for (int i = 1; i <= numberSquares; i++)
            {
                uDL2D.drawString(new Integer(i).toString(), 3 + (labelHeight / 3), (square_height * (i - 1)) + addX);
            }
        }
        else
        {
            int j = 1;
            for (int i = numberSquares; i > 0; i--, j++)
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

	public Square[][] getSquares() {
		return squares;
	}

	public void setSquares(Square squares[][]) {
		this.squares = squares;
	}
	
	public Square getKingBlack(){
		for(int x = 0; x < squares.length; x++){
			for(int y = 0; y < squares[x].length; y++){
				if(squares[x][y].piece != null && squares[x][y].piece.name.equals("King") && squares[x][y].piece.player == settings.getPlayerBlack() ){
					return squares[x][y];
				}
			}
		}
		return null;
	}
	
	public Square getKingWhite(){
		for(int x = 0; x < squares.length; x++){
			for(int y = 0; y < squares[x].length; y++){
				if(squares[x][y].piece != null && squares[x][y].piece.name.equals("King") && squares[x][y].piece.player == settings.getPlayerWhite() ){
					return squares[x][y];
				}
			}
		}
		return null;
	}
	
	public Square getKingGreen(){
		for(int x = 0; x < squares.length; x++){
			for(int y = 0; y < squares[x].length; y++){
				if(squares[x][y].piece != null && squares[x][y].piece.name.equals("King") && squares[x][y].piece.player == settings.getPlayerGreen() ){
					return squares[x][y];
				}
			}
		}
		return null;
	}
	
	public Square getKingRed(){
		for(int x = 0; x < squares.length; x++){
			for(int y = 0; y < squares[x].length; y++){
				if(squares[x][y].piece != null && squares[x][y].piece.name.equals("King") && squares[x][y].piece.player == settings.getPlayerRed() ){
					return squares[x][y];
				}
			}
		}
		return null;
	}
	
	public Square getKing(Player pl){
		for(int x = 0; x < squares.length; x++){
			for(int y = 0; y < squares[x].length; y++){
				if(squares[x][y].piece != null && squares[x][y].piece.name.equals("King") && squares[x][y].piece.player == pl ){
					return squares[x][y];
				}
			}
		}
		return null;
	}

	public static int getTop() {
		return top;
	}

	public static int getBottom() {
		return bottom;
	}

	public Square getActiveSquare() {
		return activeSquare;
	}

	public void setActiveSquare(Square activeSquare) {
		this.activeSquare = activeSquare;
	}

	public Square getTwoSquareMovedPawn() {
		return twoSquareMovedPawn;
	}

	public void setTwoSquareMovedPawn(Square twoSquareMovedPawn) {
		this.twoSquareMovedPawn = twoSquareMovedPawn;
	}
}
