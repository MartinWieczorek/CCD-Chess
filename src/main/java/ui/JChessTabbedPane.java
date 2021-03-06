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
#    along with this program.  If not, see <http://www.gnu.org/licenses/>..
 */

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package ui;

import javax.swing.*;

import core.JChessApp;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**Class responsible for drawing the tabs for the different chessboards.
 * 
 */
public class JChessTabbedPane extends JTabbedPane implements MouseListener, ImageObserver
{
	private static final Logger logger = LogManager.getLogger(JChessTabbedPane.class);

    private TabbedPaneIcon closeIcon;
    private Image addIcon = null;
    private Image clickedAddIcon = null;
    private Image unclickedAddIcon = null;
    private Rectangle addIconRect = null;

    /** Constructor to create a new JChessTabbedPane
     */
    JChessTabbedPane()
    {
        super();
        logger.info("JChessTabbedPane-constructor");
        this.closeIcon = new TabbedPaneIcon(this.closeIcon);
        this.unclickedAddIcon = GUI.loadImage("add-tab-icon.png");
        this.clickedAddIcon = GUI.loadImage("clicked-add-tab-icon.png");
        this.addIcon = this.unclickedAddIcon;
        this.setDoubleBuffered(true);
        super.addMouseListener(this);
    }

    /** Wrapper method for adding a tab
     * @param title Name that the tab will get
     * @param component 
     */
    @Override
    public void addTab(String title, Component component)
    {
        this.addTab(title, component, null);
    }
    
    /** Method for adding a tab
     * @param title Name that the tab will get
     * @param component 
     * @param closeIcon The Icon for the tab
     */
    public void addTab(String title, Component component, Icon closeIcon)
    {
        super.addTab(title, new TabbedPaneIcon(closeIcon), component);
        logger.info("Present number of tabs: " + this.getTabCount());
        this.updateAddIconRect();
    }

    public void mouseReleased(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
    }

    private void showNewGameWindow()
    {
        if (JChessApp.getJcv().newGameFrame == null)
        {
            JChessApp.getJcv().newGameFrame = new NewGameWindow();
        }
        JChessApp.getApplication().show(JChessApp.getJcv().newGameFrame);
    }

    /** Method to perform action when a mouseclick on a tab was recognized
     * @param e The event which occurs through the mouseclick
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        Rectangle rect; 
        int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());
        if (tabNumber >= 0)
        {
            rect = ((TabbedPaneIcon) getIconAt(tabNumber)).getBounds();
            if (rect.contains(e.getX(), e.getY()))
            {
            	logger.info("Removing tab with " + tabNumber + " number!...");
                this.removeTabAt(tabNumber);//remove tab
                this.updateAddIconRect();
            }
            if(this.getTabCount() == 0)
            {
                this.showNewGameWindow();
            }
        }
        else if (this.addIconRect != null && this.addIconRect.contains(e.getX(), e.getY()))
        {
        	logger.info("newGame by + button");
            this.showNewGameWindow();
        }
        //System.out.println("x:" +e.getX()+" y: "+e.getY()+" x:"+this.addIconRect.x+" y::"+this.addIconRect.y+" width:"+this.addIconRect.width+" height: "+this.addIconRect.height);
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    private void updateAddIconRect()
    {
        if (this.getTabCount() > 0)
        {
            Rectangle rect = this.getBoundsAt(this.getTabCount() - 1);
            this.addIconRect = new Rectangle(rect.x + rect.width + 5, rect.y, this.addIcon.getWidth(this), this.addIcon.getHeight(this));
        }
        else
        {
            this.addIconRect = null;
        }
    }

    private Rectangle getAddIconRect()
    {
        return this.addIconRect;
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
    {
        super.imageUpdate(img, infoflags, x, y, width, height);
        this.updateAddIconRect();
        return true;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Rectangle rect = this.getAddIconRect();
        if (rect != null)
        {
            g.drawImage(this.addIcon, rect.x, rect.y, null);
        }
    }

    @Override
    public void update(Graphics g)
    {
        this.repaint();
    }
}

class TabbedPaneIcon implements Icon
{

    private int x_pos;
    private int y_pos;
    private int width;
    private int height;
    private Icon fileIcon;

    public TabbedPaneIcon(Icon fileIcon)
    {
        this.fileIcon = fileIcon;
        width = 16;
        height = 16;
    }//--endOf-TabbedPaneIcon--
    
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y)
    {
        this.x_pos = x;
        this.y_pos = y;

        Color col = g.getColor();

        g.setColor(Color.black);
        int y_p = y + 2;
        g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);
        g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);
        g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);
        g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);
        g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);
        g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);
        g.setColor(col);
        if (fileIcon != null)
        {
            fileIcon.paintIcon(c, g, x + width, y_p);
        }
    }//--endOf-PaintIcon--

    public int getIconWidth()
    {
        return width + (fileIcon != null ? fileIcon.getIconWidth() : 0);
    }//--endOf-getIconWidth--

    public int getIconHeight()
    {
        return height;
    }//--endOf-getIconHeight()--

    public Rectangle getBounds()
    {
        return new Rectangle(x_pos, y_pos, width, height);
    }
}