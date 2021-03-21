package Game;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Game extends JFrame {

    public Game()
    {
        init();
    }

    private void init() 
    {
        add(new Board());

        setTitle("Pepe the Defender");
        setSize(Static.BOARD_WIDTH, Static.BOARD_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // brak mozliwosci poszerzania okna gry
        setLocationRelativeTo(null); // okno w centrum ekranu
    }

    public static void main(String[] args) 
    {

        EventQueue.invokeLater(() -> 
        {
            Game game = new Game();
            game.setVisible(true);
        });
    }
}