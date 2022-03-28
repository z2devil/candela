import javax.swing.*;
import java.awt.*;

class Square {
    private int x,y;
    private int oldX, oldY;
    private int originX, originY;
    private int width,height;

    public Square(int x, int y,int oldx,int oldy, int width, int height) {
        this.x = x;
        this.y = y;
        this.oldX = oldx;
        this.oldY = oldy;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void getGameMapOrigin(int x, int y) {
        originX = x;
        originY = y;
    }

    public Rectangle getRectangle() {
        return new Rectangle((originX-oldX)+x,(originY-oldY)+y,width,height);
    }
    public void drawX(Graphics g){
        g.drawImage((new ImageIcon("src/image/X.png")).getImage(), (originX-oldX)+x, (originY-oldY)+y, null);
    }
    public void drawY(Graphics g){
        g.drawImage((new ImageIcon("src/image/Y.png")).getImage(), (originX-oldX)+x, (originY-oldY)+y, null);
    }
}