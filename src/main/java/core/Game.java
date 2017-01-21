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

import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import pieces.Piece;

import java.awt.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.ComponentListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Class responsible for the starts of new games, loading games,
 * saving it, and for ending it.
 * This class is also responsible for appoing player with have
 * a move at the moment
 */
public class Game extends JPanel implements MouseListener, ComponentListener
{
	private static final Logger logger = LogManager.getLogger(Game.class);
	
    private Settings settings;
    private boolean blockedChessboard;
    private Chessboard chessboard;
    private Player activePlayer;
    private GameClock gameClock;
    private Moves moves;

    /**
     * Constructor initializes with standrard values
     */
    public Game()
    {
    	logger.info("Game-constructor");
        this.setLayout(null);
        this.moves = new Moves(this);
        setSettings(new Settings());
        setChessboard(new Chessboard(this.getSettings(), this.moves));
        getChessboard().setVisible(true);
        getChessboard().setSize(840, 840);
        getChessboard().addMouseListener(this);
        getChessboard().setLocation(new Point(0, 0));
        this.add(getChessboard());
        //this.chessboard.
        setGameClock(new GameClock(this));
        getGameClock().setSize(new Dimension(200, 100));
        getGameClock().setLocation(new Point(500, 0));
        this.add(getGameClock());

        JScrollPane movesHistory = this.moves.getScrollPane();
        movesHistory.setSize(new Dimension(180, 350));
        movesHistory.setLocation(new Point(500, 121));
        this.add(movesHistory);

        this.blockedChessboard = false;
        this.setLayout(null);
        this.addComponentListener(this);
        this.setDoubleBuffered(true);
    }

    /** Method to save actual state of game
     * @param path address of place where game will be saved
     */
    public void saveGame(File path)
    {
        File file = path;
        FileWriter fileW = null;
        try
        {
            fileW = new FileWriter(file);
        }
        catch (java.io.IOException exc)
        {
        	logger.error("error creating fileWriter: " + exc);
            JOptionPane.showMessageDialog(this, Settings.lang("error_writing_to_file")+": " + exc);
            return;
        }
        Calendar cal = Calendar.getInstance();
        String str = new String("");
        String info = new String("[Event \"Game\"]\n[Date \"" + cal.get(cal.YEAR) + "." + (cal.get(cal.MONTH) + 1) + "." + cal.get(cal.DAY_OF_MONTH) + "\"]\n"
                + "[White \"" + this.getSettings().getPlayerWhite().getName() + "\"]\n[Black \"" + this.getSettings().getPlayerBlack().getName() + "\"]\n\n");
        str += info;
        str += this.moves.getMovesInString();
        try
        {
            fileW.write(str);
            fileW.flush();
            fileW.close();
        }
        catch (java.io.IOException exc)
        {
        	logger.error("error writing to file: " + exc);
            JOptionPane.showMessageDialog(this, Settings.lang("error_writing_to_file")+": " + exc);
            return;
        }
        JOptionPane.showMessageDialog(this, Settings.lang("game_saved_properly"));
    }

    /** Loading game method(loading game state from the earlier saved file)
     *  @param file File where is saved game
     */
    static public void loadGame(File file)
    {
        FileReader fileR = null;
        try
        {
            fileR = new FileReader(file);
        }
        catch (java.io.IOException exc)
        {
        	logger.error("Something wrong reading file: " + exc);
            return;
        }
        BufferedReader br = new BufferedReader(fileR);
        String tempStr = new String();
        String blackName, whiteName;
        try
        {
            tempStr = getLineWithVar(br, new String("[White"));
            whiteName = getValue(tempStr);
            tempStr = getLineWithVar(br, new String("[Black"));
            blackName = getValue(tempStr);
            tempStr = getLineWithVar(br, new String("1."));
        }
        catch (ReadGameError err)
        {
        	logger.error("Error reading file: " + err);
            return;
        }
        Game newGUI = JChessApp.getJcv().addNewTab(whiteName + " vs. " + blackName);
        Settings locSetts = newGUI.getSettings();
        locSetts.getPlayerBlack().setName(blackName);
        locSetts.getPlayerWhite().setName(whiteName);
        locSetts.getPlayerBlack().setType(Player.playerTypes.localUser);
        locSetts.getPlayerWhite().setType(Player.playerTypes.localUser);
        locSetts.setGameMode(Settings.gameModes.loadGame);
        locSetts.setGameType(Settings.gameTypes.local);

        newGUI.newGame();
        newGUI.blockedChessboard = true;
        newGUI.moves.setMoves(tempStr);
        newGUI.blockedChessboard = false;
        newGUI.getChessboard().repaint();
        //newGUI.chessboard.draw();
    }

    /** Method checking in with of line there is an error
     *  @param  br BufferedReader class object to operate on
     *  @param  srcStr String class object with text which variable you want to get in file
     *  @return String with searched variable in file (whole line)
     *  @throws ReadGameError class object when something goes wrong when reading file
     */
    static public String getLineWithVar(BufferedReader br, String srcStr) throws ReadGameError
    {
        String str = new String();
        while (true)
        {
            try
            {
                str = br.readLine();
            }
            catch (java.io.IOException exc)
            {
            	logger.error("Something wrong reading file: " + exc);
            }
            if (str == null)
            {
                throw new ReadGameError();
            }
            if (str.startsWith(srcStr))
            {
                return str;
            }
        }
    }

    /** Method to get value from loaded txt line
     *  @param line Line which is readed
     *  @return result String with loaded value
     *  @throws ReadGameError object class when something goes wrong
     */
    static public String getValue(String line) throws ReadGameError
    {
        //System.out.println("getValue called with: "+line);
        int from = line.indexOf("\"");
        int to = line.lastIndexOf("\"");
        int size = line.length() - 1;
        String result = new String();
        if (to < from || from > size || to > size || to < 0 || from < 0)
        {
            throw new ReadGameError();
        }
        try
        {
            result = line.substring(from + 1, to);
        }
        catch (java.lang.StringIndexOutOfBoundsException exc)
        {
        	logger.error("error getting value: " + exc);
            return "none";
        }
        return result;
    }

    /** Method to Start new game
     *
     */
    public void newGame()
    {
        ChessboardLogic.getInstance().setPieces(getChessboard(), "", getSettings().getPlayerWhite(), getSettings().getPlayerBlack(), getSettings().getPlayerRed(), getSettings().getPlayerGreen());

        activePlayer = getSettings().getPlayerWhite();
        if (activePlayer.getPlayerType() != Player.playerTypes.localUser)
        {
            this.blockedChessboard = true;
        }
        //dirty hacks starts over here :) 
        //to fix rendering artefacts on first run
        Game activeGame = JChessApp.getJcv().getActiveTabGame();
        //if( activeGame != null && JChessApp.getJcv().getNumberOfOpenedTabs() == 0 )
        if( activeGame != null  )
        {
            activeGame.getChessboard().resizeChessboard(activeGame.getChessboard().get_height(false));
            activeGame.getChessboard().repaint();
            activeGame.repaint();
        }
        getChessboard().repaint();
        this.repaint();
        //dirty hacks ends over here :)
    }

    /** Method to end game
     *  @param message what to show player(s) at end of the game (for example "draw", "black wins" etc.)
     */
    public void endGame(String message)
    {
        this.blockedChessboard = true;
        logger.info(message);
        JOptionPane.showMessageDialog(null, message);
    }

    /** Method to swich active players after move
     */
    public void switchActive()
    {
    	ArrayList<Player> activePlayers = getSettings().getActivePlayers();
    	
    	for (int i = 0; i < activePlayers.size(); i++)
    	{
    		if( activePlayer == activePlayers.get(i))
    		{
    			activePlayer = activePlayers.get( (i + 1) % activePlayers.size()); 
    			break;
    		}
    	}
//    	
//        if (activePlayer == getSettings().getPlayerWhite())
//        {
//            activePlayer = getSettings().getPlayerRed();
//        }
//        else  if (activePlayer == getSettings().getPlayerRed())
//        {
//            activePlayer = getSettings().getPlayerBlack();
//        }
//        else  if (activePlayer == getSettings().getPlayerBlack())
//        {
//            activePlayer = getSettings().getPlayerGreen();
//        }
//        else  if (activePlayer == getSettings().getPlayerGreen())
//        {
//            activePlayer = getSettings().getPlayerWhite();
//        }

        this.getGameClock().switch_clocks();
    }

    /** Method of getting accualy active player
     *  @return  player The player which have a move
     */
    public Player getActivePlayer()
    {
        return this.activePlayer;
    }

    /** Method to go to next move (checks if game is local/network etc.)
     */
    public void nextMove()
    {
        switchActive();

        logger.info("next move, active player: " + activePlayer.getName() + ", color: " + activePlayer.getColor().name() + ", type: " + activePlayer.getPlayerType().name());
        if (activePlayer.getPlayerType() == Player.playerTypes.localUser)
        {
            this.blockedChessboard = false;
        }
        else if (activePlayer.getPlayerType() == Player.playerTypes.networkUser)
        {
            this.blockedChessboard = true;
        }
        else if (activePlayer.getPlayerType() == Player.playerTypes.computer)
        {
        }
    }

    /** Method to simulate Move to check if it's correct etc. (usable for network game).
     * @param beginX from which X (on chessboard) move starts
     * @param beginY from which Y (on chessboard) move starts
     * @param endX   to   which X (on chessboard) move go
     * @param endY   to   which Y (on chessboard) move go
     * */
    public boolean simulateMove(int beginX, int beginY, int endX, int endY)
    {
        try 
        {
        	ChessboardLogic.getInstance().select(getChessboard(), getChessboard().getSquares()[beginX][beginY]);
            if (getChessboard().getActiveSquare().allMoves().indexOf(getChessboard().getSquares()[endX][endY]) != -1) //move
            {
            	ChessboardLogic.getInstance().move(getChessboard(), getChessboard().getSquares()[beginX][beginY], getChessboard().getSquares()[endX][endY]);
            }
            else
            {
            	logger.info("Bad move");
                return false;
            }
            ChessboardLogic.getInstance().unselect(getChessboard());
            nextMove();

            return true;
            
        } 
        catch(StringIndexOutOfBoundsException exc) 
        {
            return false;
        }    
        catch(ArrayIndexOutOfBoundsException exc) 
        {
            return false;
        }
        catch(NullPointerException exc)
        {
            return false;
        }
        finally
        {
            //Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, "ERROR");
        	logger.fatal("unknown exception in simulateMove");
        }
    }

    // MouseListener:
    public void mouseClicked(MouseEvent arg0)
    {
    }
    
    public boolean undo()
    {
        boolean status = false;
        
        if( this.getSettings().getGameType() == Settings.gameTypes.local )
        {
            status = ChessboardLogic.getInstance().undo(getChessboard(), true);
            if( status )
            {
                this.switchActive();
            }
            else
            {
                getChessboard().repaint();//repaint for sure
            }
        }
        return status;
    }
    
    public boolean rewindToBegin()
    {
        boolean result = false;
        
        if( this.getSettings().getGameType() == Settings.gameTypes.local )
        {
            while( ChessboardLogic.getInstance().undo(getChessboard(), true) )
            {
                result = true;
            }
        }
        else
        {
            throw new UnsupportedOperationException( Settings.lang("operation_supported_only_in_local_game") );
        }
        
        return result;
    }
    
    public boolean rewindToEnd() throws UnsupportedOperationException
    {
        boolean result = false;
        
        if( this.getSettings().getGameType() == Settings.gameTypes.local )
        {
            while( ChessboardLogic.getInstance().redo(getChessboard()) )
            {
                result = true;
            }
        }
        else
        {
            throw new UnsupportedOperationException( Settings.lang("operation_supported_only_in_local_game") );
        }
        
        return result;
    }
    /**
     * Methos to redo last Action on the chessboard
     * @return
     */
    public boolean redo()
    {
        boolean status = ChessboardLogic.getInstance().redo(getChessboard());
        if( this.getSettings().getGameType() == Settings.gameTypes.local )
        {
            if ( status )
            {
                this.nextMove();
            }
            else
            {
                getChessboard().repaint();//repaint for sure
            }
        }
        else
        {
            throw new UnsupportedOperationException( Settings.lang("operation_supported_only_in_local_game") );
        }
        return status;
    }
    
    
    /**
     * Callback method to invoke actions when Mouse pressed 
     */
    public void mousePressed(MouseEvent event)
    {
        if (event.getButton() == MouseEvent.BUTTON3) //right button
        {
            this.undo();
        }
        else if (event.getButton() == MouseEvent.BUTTON2 && getSettings().getGameType() == Settings.gameTypes.local)
        {
            this.redo();
        }
        else if (event.getButton() == MouseEvent.BUTTON1) //left button
        {

            if (!blockedChessboard)
            {
                try 
                {
                    int x = event.getX();//get X position of mouse
                    int y = event.getY();//get Y position of mouse

                    Square sq = ChessboardLogic.getInstance().getSquare(getChessboard(), x, y);
                    //Square sq = chessboard.getSquares()[x][y];
                    
                    if ((sq == null && getChessboard().getActiveSquare() == null)
                            || (this.getChessboard().getActiveSquare() == null && sq.getPiece() != null && sq.getPiece().getPlayer() != this.activePlayer))
                    {
                        return;
                    }

                    if (sq.getPiece() != null && sq.getPiece().getPlayer() == this.activePlayer && sq != getChessboard().getActiveSquare())
                    {
                    	ChessboardLogic.getInstance().unselect(getChessboard());
                    	ChessboardLogic.getInstance().select(getChessboard(), sq);
                    }
                    else if (getChessboard().getActiveSquare() == sq) //unselect
                    {
                    	ChessboardLogic.getInstance().unselect(getChessboard());
                    }
                    else if (getChessboard().getActiveSquare() != null && getChessboard().getActiveSquare().getPiece() != null
                            && getChessboard().getActiveSquare().allMoves().indexOf(sq) != -1) //move
                    {
                        if (getSettings().getGameType() == Settings.gameTypes.local)
                        {
                        	ChessboardLogic.getInstance().move(getChessboard(), getChessboard().getActiveSquare(), sq);
                        }

                        ChessboardLogic.getInstance().unselect(getChessboard());

                        //switch player
                        this.nextMove();

                        //checkmate or stalemate
                        Piece king;
                        if (this.activePlayer == getSettings().getPlayerWhite())
                        {
                            king = getChessboard().getKing(getSettings().getPlayerWhite()).getPiece();
                        }
                        else
                        {
                            king = getChessboard().getKing(getSettings().getPlayerBlack()).getPiece();
                        }

                        switch (king.isCheckmatedOrStalemated())
                        {
                            case 1:
                                this.endGame("Checkmate! " + king.getPlayer().getColor().toString() + " player lose!");
                                break;
                            case 2:
                                this.endGame("Stalemate! Draw!");
                                break;
                        }
                    }
                    
                } 
                catch(NullPointerException exc)
                {
                	logger.error("mousePressed(): " + exc.getMessage());
                    getChessboard().repaint();
                    return;
                }
            }
            else if (blockedChessboard)
            {
            	logger.info("Chessboard is blocked");
            }
        }
        //chessboard.repaint();
    }

    public void mouseReleased(MouseEvent arg0)
    {
    }

    public void mouseEntered(MouseEvent arg0)
    {
    }

    public void mouseExited(MouseEvent arg0)
    {
    }

    public void componentResized(ComponentEvent e)
    {
        int height = this.getHeight() >= this.getWidth() ? this.getWidth() : this.getHeight();
        int chess_height = (int)Math.round( (height * 0.8)/8 )*8;
        this.getChessboard().resizeChessboard((int)chess_height);
        chess_height = this.getChessboard().getHeight();
        this.moves.getScrollPane().setLocation(new Point(chess_height + 5, 100));
        this.moves.getScrollPane().setSize(this.moves.getScrollPane().getWidth(), chess_height - 100);
        this.getGameClock().setLocation(new Point(chess_height + 5, 0));
    }

    public void componentMoved(ComponentEvent e)
    {
    }

    public void componentShown(ComponentEvent e)
    {
    }

    public void componentHidden(ComponentEvent e)
    {
    }

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public Chessboard getChessboard() {
		return chessboard;
	}

	public void setChessboard(Chessboard chessboard) {
		this.chessboard = chessboard;
	}

	public GameClock getGameClock() {
		return gameClock;
	}

	public void setGameClock(GameClock gameClock) {
		this.gameClock = gameClock;
	}
}

class ReadGameError extends Exception
{
}