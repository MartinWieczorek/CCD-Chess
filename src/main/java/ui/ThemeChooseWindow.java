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
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;

import core.JChessApp;
import core.Settings;

import javax.swing.event.ListSelectionEvent;
import java.io.File;
import java.util.Properties;
import java.io.FileOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Class responsible for creating the window where the user can switch between themes for new games
 */
public class ThemeChooseWindow extends JDialog implements ActionListener, ListSelectionListener
{
	private static final Logger logger = LogManager.getLogger(ThemeChooseWindow.class);

    JList themesList;
    ImageIcon themePreview;
    GridBagLayout gbl;
    public String result;
    GridBagConstraints gbc;
    JButton themePreviewButton;
    JButton okButton;

    /** Constructor to create a ThemeChooseWindow
     * @param parent Information about the frame in which this window will be created
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
	ThemeChooseWindow(Frame parent) throws Exception
    {
        super(parent);
        logger.info("ThemeChooseWindow-constructor");
        
        File dir = new File(GUI.getJarPath() + File.separator + "ui/resources/theme" + File.separator);

        File[] files = dir.listFiles();
        if (files != null && dir.exists())
        {
            this.setTitle(Settings.lang("choose_theme_window_title"));
            Dimension winDim = new Dimension(550, 230);
            this.setMinimumSize(winDim);
            this.setMaximumSize(winDim);
            this.setSize(winDim);
            this.setResizable(false);
            this.setLayout(null);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);            
            String[] dirNames = new String[files.length];
            for (int i = 0; i < files.length; i++)
            {
                dirNames[i] = files[i].getName();
            }
            this.themesList = new JList(dirNames);
            this.themesList.setLocation(new Point(10, 10));
            this.themesList.setSize(new Dimension(100, 120));
            this.add(this.themesList);
            this.themesList.setSelectionMode(0);
            this.themesList.addListSelectionListener(this);
            Properties prp = GUI.getConfigFile();
            
            this.gbl = new GridBagLayout();
            this.gbc = new GridBagConstraints();
            try
            {
                this.themePreview = new ImageIcon(GUI.loadImage("Preview.png"));//JChessApp.class.getResource("theme/"+GUI.configFile.getProperty("THEME")+"/images/Preview.png"));
            }
            catch (java.lang.NullPointerException exc)
            {
            	logger.error("Cannot find preview image: " + exc);
                this.themePreview = new ImageIcon(JChessApp.class.getResource("theme/noPreview.png"));
                return;
            }
            this.result = "";
            this.themePreviewButton = new JButton(this.themePreview);
            this.themePreviewButton.setLocation(new Point(110, 10));
            this.themePreviewButton.setSize(new Dimension(420, 120));
            this.add(this.themePreviewButton);
            this.okButton = new JButton("OK");
            this.okButton.setLocation(new Point(175, 140));
            this.okButton.setSize(new Dimension(200, 50));
            this.add(this.okButton);
            this.okButton.addActionListener(this);
            this.setModal(true);
        }
        else
        {
            throw new Exception(Settings.lang("error_when_creating_theme_config_window"));
        }

    }
    
    /** Method for selecting the theme that will be used for new games out of a list
     * @param event Capt information about performed action e.g. mouseclick
     */
    @Override
    public void valueChanged(ListSelectionEvent event)
    {
        String element = this.themesList.getModel().getElementAt(this.themesList.getSelectedIndex()).toString();
        String path = GUI.getJarPath() + File.separator + "ui/resources/theme/";
        //String path  = JChessApp.class.getResource("theme/").getPath().toString();
        logger.debug(path + element + "/images/Preview.png");
        this.themePreview = new ImageIcon(path + element + "/images/Preview.png");
        this.themePreviewButton.setIcon(this.themePreview);
    }

    /** Method which is changing the used theme for new games
     * @param evt Capt information about performed action e.g. mouseclick
     */
    public void actionPerformed(ActionEvent evt)
    {
        if (evt.getSource() == this.okButton)
        {
            Properties prp = GUI.getConfigFile();
            int element = this.themesList.getSelectedIndex();
            String name = this.themesList.getModel().getElementAt(element).toString();
            if (themeIsValid(name))
            {
                prp.setProperty("THEME", name);
                try
                {
                    //FileOutputStream fOutStr = new FileOutputStream(ThemeChooseWindow.class.getResource("config.txt").getFile());
                    FileOutputStream fOutStr = new FileOutputStream(GUI.getJarPath() + File.separator + "config.txt");
                    prp.store(fOutStr, null);
                    fOutStr.flush();
                    fOutStr.close();
                }
                catch (java.io.IOException exc)
                {
                }
                JOptionPane.showMessageDialog(this, Settings.lang("changes_visible_after_restart"));
                this.setVisible(false);

            }
            logger.debug(prp.getProperty("THEME"));
        }
    }
    


    /**Method to validate the theme (doesn't do anything atm.)
     * @param name Name of the theme
     * @return True if theme is valid
     */
    private static boolean themeIsValid(String name)
    {
        return true;
    }
}
