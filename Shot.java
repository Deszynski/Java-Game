package Game;
import javax.swing.ImageIcon;

public class Shot extends Variables {

    public Shot(){}
    public Shot(int x, int y) 
    {
        initShot(x, y);
    }

    private void initShot(int x, int y) // tworzenie pocisku
    {
        String shotImg = "src/images/shot.png";
        ImageIcon ii = new ImageIcon(shotImg);
        setImage(ii.getImage());

        int X_SPACE = 6;
        setX(x + X_SPACE);

        int Y_SPACE = 1;
        setY(y - Y_SPACE);
    }
}