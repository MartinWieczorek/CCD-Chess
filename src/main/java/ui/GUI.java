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
package ui;

import java.awt.*;
import java.net.*;
import java.io.*;
import java.io.InputStreamReader;
import javax.swing.*;

import core.Game;
import core.JChessApp;

import java.io.IOException;
import java.util.Properties;
import java.io.FileOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/** Class representing the game interface which is seen by a player and
 * where are lockated available for player opptions, current games and where
 * can he start a new game (load it or save it)
 */
public class GUI
{

    public Game game;
    private static final Logger logger = LogManager.getLogger(GUI.class);
    static final public Properties configFile = GUI.getConfigFile();
    

    public GUI()
    {
        this.game = new Game();
        //this.drawGUI();
    }/*--endOf-GUI--*/

    /*Method load image by a given name with extension
     * @name     : string of image to load for ex. "chessboard.jpg"
     * @returns  : image or null if cannot load
     * */

    public static Image loadImage(String name)
    {
        if (configFile == null)
        {
            return null;
        }
        Image img = null;
        URL url = null;
        Toolkit tk = Toolkit.getDefaultToolkit();
        try
        {
        	String path = new File("").getAbsolutePath();
        	logger.debug("path: " + path);
        	logger.debug("gui name: "+ GUI.class.getName());
        	
        	//String imageLink = "../theme/" + configFile.getProperty("THEME", "default") + "/images/" + name;
        	String imageLink = "resources/theme/" + configFile.getProperty("THEME", "default") + "/images/" + name;
        	logger.debug(configFile.getProperty("THEME"));
            url = JChessApp.class.getResource(imageLink);
            logger.debug("loadImage: " + imageLink);
            logger.debug("url: " + url);
            img = tk.getImage(url);

        }
        catch (Exception e)
        {
        	logger.error("some error loading image!");
            e.printStackTrace();
        }
        return img;
    }/*--endOf-loadImage--*/


    static boolean themeIsValid(String name)
    {
        return true;
    }

    static String getJarPath()
    {
        String path = GUI.class.getProtectionDomain().getCodeSource().getLocation().getFile();  
        logger.debug(path);
        path = path.replaceAll("[a-zA-Z0-9%!@#$%^&*\\(\\)\\[\\]\\{\\}\\.\\,\\s]+\\.jar", "");
        int lastSlash = path.lastIndexOf(File.separator); 
        if(path.length()-1 == lastSlash)
        {
            path = path.substring(0, lastSlash);
        }
        path = path.replace("%20", " ");
        return path;
    }

    static Properties getConfigFile()
    {
        Properties defConfFile = new Properties();
        Properties confFile = new Properties();
        File outFile = new File(GUI.getJarPath() + File.separator + "config.txt");
        //File outFile = new File("config.txt");
        logger.debug(GUI.getJarPath());
        logger.debug(outFile.getAbsolutePath());
        logger.debug(outFile.exists());
//        try
//        {
//        	InputStream is = GUI.class.getResourceAsStream("config.txt");
//            defConfFile.load(is);
//            System.out.println("defConfFile" + defConfFile.toString());
//        }
//        catch (java.io.IOException exc)
//        {
//            System.out.println("some error loading image! what goes: " + exc);
//            exc.printStackTrace();
//        }
        if (!outFile.exists())
        {
        	if(defConfFile.isEmpty()){
        		defConfFile.setProperty("THEME", "default");
        		logger.info("Property THEME created");
        	}
        	logger.debug("outfile does not exists");
            try
            {
                defConfFile.store(new FileOutputStream(outFile), null);
            }
            catch (java.io.IOException exc)
            {
            	logger.error("some error storing config! what goes: " + exc);
                exc.printStackTrace();
            }
        }
        try
        {   
            confFile.load(new FileInputStream(outFile.getAbsolutePath()));
        }
        catch (java.io.IOException exc)
        {
        	logger.error("some error loading config! what goes: " + exc);
            exc.printStackTrace();
        }
        return confFile;
    }
}
