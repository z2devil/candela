import javax.swing.*;
import java.awt.*;

public class Overlays {
    private int x = 0;
    private int y = 0;
    private int olid = 0;
    private Image coverimg = null;
    private Image lightimg = null;

    public void drawCover(int id, int x, int y, Graphics g) {
        this.x = x;
        this.y = y;
        olid = id;
        coverimg = (new ImageIcon("src/image/Cover/cover" + olid + ".png")).getImage();
        g.drawImage(coverimg, this.x, this.y, null);
    }
    public void drawLine(int id, int x, int y, Graphics g) {
        this.x = x;
        this.y = y;
        olid = id;
        for (int i = 0; i < coverimg.getWidth(null); i++) {
            g.drawLine(x+i*32,y,x+i*32,y+coverimg.getHeight(null));
        }
        for (int i = 0; i < coverimg.getHeight(null); i++) {
            g.drawLine(x,y+i*32,x+coverimg.getWidth(null),y+i*32);
        }
    }
    public void drawLight(int id, int x, int y, Graphics g) {
        this.x = x;
        this.y = y;
        olid = id;
        lightimg = (new ImageIcon("src/image/Light/light" + olid + ".png")).getImage();
        g.drawImage(lightimg, 0, 0, null);
    }
}
