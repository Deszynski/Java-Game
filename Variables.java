package Game;
import java.awt.Image;

public class Variables {

    private boolean visible;
    private boolean dying;
    private Image image;
    
    int x;
    int y;
    int dx;

    public Variables() 
    {
        visible = true;
    }

    public void die() 
    {
        visible = false;
    }

    public boolean isVisible() 
    {
        return visible;
    }

    protected void setVisible(boolean visible) 
    {
        this.visible = visible;
    }

    public void setImage(Image image) 
    {
        this.image = image;
    }

    public Image getImage() 
    {
        return image;
    }

    public void setX(int x) 
    {
        this.x = x;
    }

    public void setY(int y) 
    {
        this.y = y;
    }

    public int getY() 
    {
        return y;
    }

    public int getX() 
    {
        return x;
    }

    public void setDying(boolean dying) 
    {
        this.dying = dying;
    }

    public boolean isDying() 
    {
        return this.dying;
    }
}