package Game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel {

    private Dimension d;
    private List<Alien> aliens;
    private Player player;
    private Shot shot;

    private int direction = -1;
    private int deaths = 0;

    private boolean inGame = true;
    private String explImg = "src/images/explosion.png";
    private String message = "GAME OVER :(";

    private Timer timer;


    public Board() 
    {
        initBoard();
        gameInit();
    }

    private void initBoard()  // inicjalizacja mapy
    {
        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(Static.BOARD_WIDTH, Static.BOARD_HEIGHT);
        setBackground(Color.black);

        timer = new Timer(Static.DELAY, new GameCycle());
        timer.start();

       // gameInit();
    }


    private void gameInit() 
    {
        aliens = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {

                Alien alien = new Alien(Static.ALIEN_INIT_X + 18 * j,
                		Static.ALIEN_INIT_Y + 18 * i);
                aliens.add(alien);
            }
        }

        player = new Player();
        shot = new Shot();
    }

    private void drawAliens(Graphics g) 
    {
        for (Alien alien : aliens) 
        {
            if (alien.isVisible()) 
            {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) 
            {
                alien.die();
            }
        }
    }

    private void drawPlayer(Graphics g) 
    {
        if (player.isVisible()) {

            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {                                                // przegrana

            player.die();
            inGame = false;
        }
    }

    private void drawShot(Graphics g) 
    {
        if (shot.isVisible()) {

            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    private void drawBombing(Graphics g) 
    {

        for (Alien a : aliens) {

            Alien.Bomb b = a.getBomb();

            if (!b.isDestroyed()) {

                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) 
    {
        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (inGame) 
        {
            g.drawLine(0, Static.GROUND,
            		Static.BOARD_WIDTH, Static.GROUND);

            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
        } 
        else 
        {
            if (timer.isRunning()) 
            {
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) 
    {
        g.setColor(Color.black);
        g.fillRect(0, 0, Static.BOARD_WIDTH, Static.BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, Static.BOARD_WIDTH / 2 - 30, Static.BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, Static.BOARD_WIDTH / 2 - 30, Static.BOARD_WIDTH - 100, 50);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (Static.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
        		Static.BOARD_WIDTH / 2);
    }

    private void update() // zawiera pêtle i warunki kontroluj¹ce przebieg gry
    {

        if (deaths == Static.NUMBER_OF_ALIENS_TO_DESTROY) // przypadek wygrania gry
        { 

            inGame = false;
            timer.stop();
            message = "GAME WON !!1!11!";
        }

        // gracz
        player.act();

        // strza³y
        if (shot.isVisible()) 
        {

            int shotX = shot.getX();
            int shotY = shot.getY();

            for (Alien alien : aliens) 
            {
                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && shot.isVisible()) 
                {
                    if (shotX >= (alienX)
                        && shotX <= (alienX + Static.ALIEN_WIDTH)
                        && shotY >= (alienY)
                        && shotY <= (alienY + Static.ALIEN_HEIGHT)) 
                    {
                        ImageIcon ii = new ImageIcon(explImg);
                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        deaths++;
                        shot.die();
                    }
                }
            }

            int y = shot.getY();
            y -= 4; // odejmuje 4 piksele (dlugosc pocisku) od wspolrzednej zmieniajac pozycje pocisku na mapie

            if (y < 0) 
            {
                shot.die(); // znikanie pocisków "w pod³odze"
            } 
            else // ruch pocisków
            {
                shot.setY(y);
            }
        }

        // wrogowie

        for (Alien alien : aliens) 
        {
            int x = alien.getX();

            if (x >= Static.BOARD_WIDTH - Static.BORDER_RIGHT && direction != -1) 
            {
                direction = -1; // zmiana kierunku ruchu po zderzeniu ze œcian¹ praw¹

                Iterator<Alien> i1 = aliens.iterator();

                while (i1.hasNext()) 
                {

                    Alien a2 = i1.next();
                    a2.setY(a2.getY() + Static.GO_DOWN);
                }
            }

            if (x <= Static.BORDER_LEFT && direction != 1) 
            {
                direction = 1; // zmiana kierunku ruchu po zderzeniu ze œcian¹ lew¹

                Iterator<Alien> i2 = aliens.iterator();

                while (i2.hasNext()) 
                {
                    Alien a = i2.next();
                    a.setY(a.getY() + Static.GO_DOWN);
                }
            }
        }

        Iterator<Alien> it = aliens.iterator();

        while (it.hasNext()) 
        {
        	Alien alien = it.next();
        	
            if (alien.isVisible()) 
            {
                int y = alien.getY();

                if (y > Static.GROUND - Static.ALIEN_HEIGHT) // przypadek przegranej przez zderzenie wroga z pod³og¹
                { 
                    inGame = false;
                    message = "Invasion!";  
                }
                
                alien.act(direction);
            }
        }

        // pociski wrogow
        Random generator = new Random();

        for (Alien alien : aliens) 
        {

            int shot = generator.nextInt(15);
            Alien.Bomb bomb = alien.getBomb();

            if (shot == Static.CHANCE && alien.isVisible() && bomb.isDestroyed()) 
            {
                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !bomb.isDestroyed()) 
            {
                if (bombX >= (playerX)
                        && bombX <= (playerX + Static.PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + Static.PLAYER_HEIGHT)) 
                {
                    ImageIcon ii = new ImageIcon(explImg);
                    player.setImage(ii.getImage());
                    player.setDying(true);
                    bomb.setDestroyed(true);                       // kolizja gracza z pociskiem wroga
                }
            }

            if (!bomb.isDestroyed()) // ruch pocisków wroga i zderzenie z pod³og¹
            { 
                bomb.setY(bomb.getY() + 1);

                if (bomb.getY() >= Static.GROUND - Static.BOMB_HEIGHT) 
                {
                    bomb.setDestroyed(true);
                }
            }
        }
    }

    private void doGameCycle()  // odœwie¿anie gry
    {
        update();
        repaint();
    }

    private class GameCycle implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter 
    {
        @Override
        public void keyReleased(KeyEvent e) 
        {
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) 
        {
            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE)  // tworzenie pocisku spacj¹
            {
                if (inGame) 
                {
                    if (!shot.isVisible()) 
                    {
                        shot = new Shot(x, y);
                    }
                }
            }
        }
    }
}