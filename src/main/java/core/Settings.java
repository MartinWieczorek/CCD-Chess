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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Class representings game settings available for the current player
 */
public class Settings implements Serializable
{
	private static final Logger logger = LogManager.getLogger(Settings.class);

    private static ResourceBundle loc = null;
    private int timeForGame;
    private boolean runningChat;
    private boolean runningGameClock;
    private boolean timeLimitSet;//tell us if player choose time 4 game or it's infinity
    private boolean upsideDown;

    public enum gameModes
    {
        newGame, loadGame
    } 
    
    public enum gameState
    {
        normal, chekmate, stalemate
    }
    
    private gameModes gameMode;
    private Player playerWhite;
    private Player playerBlack;
    private Player playerRed;
    private Player playerGreen;

    private ArrayList<Player>activePlayers;
    
    public enum gameTypes
    {
        local
    }
    private gameTypes gameType;
    private boolean renderLabels = true;
    
    /**
     * 
     */
    public Settings()
    {
        //temporally
    	logger.info("Settings-constructor");
        this.setPlayerWhite(new Player("", Player.colors.white));
        this.setPlayerBlack(new Player("", Player.colors.black));
        this.setPlayerRed(new Player("", Player.colors.red));
        this.setPlayerGreen(new Player("", Player.colors.green));
        this.setTimeLimitSet(false);

        setGameMode(gameModes.newGame);
        
        this.activePlayers = new ArrayList<>();
        this.activePlayers.add(playerWhite);
        this.activePlayers.add(playerRed);
        this.activePlayers.add(playerBlack);
        this.activePlayers.add(playerGreen);
    }

    /** Method to get game time set by player
     *  @return timeFofGame int with how long the game will leasts
     */
    public int getTimeForGame()
    {
        return this.timeForGame;
    }
	
    /**
     * 
     * @param key
     * @return
     */
    public static String lang(String key)
    {
        if (Settings.loc == null)
        {
            Settings.loc = PropertyResourceBundle.getBundle("ui.resources.i18n.main");
            Locale.setDefault(Locale.ENGLISH);
        }
        String result = "";
        try
        {
            result = Settings.loc.getString(key);
        }
        catch (java.util.MissingResourceException exc)
        {
            result = key;
        }
        logger.info(Settings.loc.getLocale().toString());
        return result;
    }

	public Player getPlayerWhite() {
		return playerWhite;
	}

	public void setPlayerWhite(Player playerWhite) {
		this.playerWhite = playerWhite;
	}

	public Player getPlayerBlack() {
		return playerBlack;
	}

	public void setPlayerBlack(Player playerBlack) {
		this.playerBlack = playerBlack;
	}

	public Player getPlayerRed() {
		return playerRed;
	}

	public void setPlayerRed(Player playerRed) {
		this.playerRed = playerRed;
	}

	public Player getPlayerGreen() {
		return playerGreen;
	}

	public void setPlayerGreen(Player playerGreen) {
		this.playerGreen = playerGreen;
	}

	public boolean isTimeLimitSet() {
		return timeLimitSet;
	}

	public void setTimeLimitSet(boolean timeLimitSet) {
		this.timeLimitSet = timeLimitSet;
	}

	public boolean isUpsideDown() {
		return upsideDown;
	}

	public void setUpsideDown(boolean upsideDown) {
		this.upsideDown = upsideDown;
	}

	public void setTimeForGame(int timeForGame) {
		this.timeForGame = timeForGame;
	}

	public gameModes getGameMode() {
		return gameMode;
	}

	public void setGameMode(gameModes gameMode) {
		this.gameMode = gameMode;
	}

	public gameTypes getGameType() {
		return gameType;
	}

	public void setGameType(gameTypes gameType) {
		this.gameType = gameType;
	}

	public boolean isRenderLabels() {
		return renderLabels;
	}

	public void setRenderLabels(boolean renderLabels) {
		this.renderLabels = renderLabels;
	}
	/**
	 * 
	 * @param pl
	 */
	public void removeActivePlayer(Player pl){
		for(int i = 0; i < getActivePlayers().size(); i++){
			if(pl == getActivePlayers().get(i)){
				getActivePlayers().remove(i);
			}
		}
	}

	public ArrayList<Player> getActivePlayers() {
		return activePlayers;
	}
	
}
