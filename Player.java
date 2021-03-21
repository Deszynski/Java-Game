package Game;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player extends Variables {

    private int width;

    public Player() 
    {
        initPlayer();
    }

    private void initPlayer() 
    {
        String playerImg = "src/images/player.png";
        ImageIcon ii = new ImageIcon(playerImg);

        width = ii.getImage().getWidth(null);
        setImage(ii.getImage());

        int START_X = 270;
        setX(START_X);

        int START_Y = 280;
        setY(START_Y);
    }

    public void act()  
    {
        x += dx;

        if (x <= 2) 
        {
            x = 2;
        }
        if (x >= Static.BOARD_WIDTH - 2 * width) // utrzymuje wspolrzedne gracza w granicach mapy
        {
            x = Static.BOARD_WIDTH - 2 * width;
        }
    }

    public void keyPressed(KeyEvent e) // ruch gracza
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) 
        {
            dx = -2;
        }
        if (key == KeyEvent.VK_RIGHT) 
        {
            dx = 2;
        }
    }

    public void keyReleased(KeyEvent e) 
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) 
        {
            dx = 0;
        }
        if (key == KeyEvent.VK_RIGHT) 
        {
            dx = 0;
        }
    }
}