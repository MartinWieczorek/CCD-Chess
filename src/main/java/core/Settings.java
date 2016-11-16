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
import java.util.Locale;
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
    public int timeForGame;
    public boolean runningChat;
    public boolean runningGameClock;
    public boolean timeLimitSet;//tel us if player choose time 4 game or it's infinity
    public boolean upsideDown;

    public enum gameModes
    {

        newGame, loadGame
    }
    public gameModes gameMode;
    private Player playerWhite;
    private Player playerBlack;

    public enum gameTypes
    {

        local, network
    }
    public gameTypes gameType;
    public boolean renderLabels = true;

    public Settings()
    {
        //temporally
    	logger.info("Settings-constructor");
        this.setPlayerWhite(new Player("", "white"));
        this.setPlayerBlack(new Player("", "black"));
        this.timeLimitSet = false;

        gameMode = gameModes.newGame;
    }

    /** Method to get game time set by player
     *  @return timeFofGame int with how long the game will leasts
     */
    public int getTimeForGame()
    {
        return this.timeForGame;
    }

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
}
