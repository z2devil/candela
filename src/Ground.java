import javax.swing.*;
import java.awt.*;
import java.util.Observable;

class Ground {
    public int x = 0;
    public int y = 0;
    private int bgid = 0;
    private Image img = null;

    public void draw(int id, Graphics g) {
        bgid = id;
        img = (new ImageIcon("src/image/Ground/ground" + bgid + ".png")).getImage();
        g.drawImage(img, x, y, null);
    }
}
