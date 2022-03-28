import javax.swing.*;
import java.awt.*;

public class Bullet {
    private int bulletID;
    private int x, y;
    private int oldX, oldY;
    private int originX, originY;
    private int step = 1;
    private int steps = 0;
    private int tm = 100;
    private int bulletDir = 0;
    private int origin = 0;//0:无限制 1:于怪物 2:于角色
    private float atk;
    private boolean death;//控制子弹显示
    double angleX,angleY;
    private Image img;
    private Actor actor;
    private Mob mob;
    private String path = "src/image/Bullet/arm";
    //定义子弹信息
    /* PS:10寿命差不多飞出3格距离（飞到第三格）    */
    private int[][] bulletList = {
           /*0atk  1寿命  2速度   3特效  4矩形宽/高  6偏移x 7偏移y 8伤害类型（0：对怪物  1：对角色 2：对全部）*/
            {100000000,     50,   8,      1,     32,32,     0,     0,    0,     4,    0,     0,          0},//木质短弓0
            {100,    50,   8,      2,     48,48,     0,     0,    0,     5,    1,     0,          3},//寒霜长弓1
            {50,     50,   8,      3,     32,32,     0,     0,    0,     4,    0,     0,          1},//火炎短杖2
            {50,     50,   8,      4,     32,32,     0,     0,    0,     4,    0,     0,          2},//毒妖短杖3
            {200,   100,   3,      5,    130,130,    0,   -50,    0,     8,    1,     1,          1},//黑煌法杖4
            {1000,   78,   0,      6,      0,0,      0,     0,    0,    14,    2,     1,          4},//死神柬5
            {0,      50,   0,      11,   110,110,    0,     0,    1,     4,    0,     1,          0},//史莱姆6
            {10,     17,   0,      11,    55,55,   -12,   -12,    1,     4,    1,     1,          0},//丧尸7
            {15,    250,   3,      11,    32,32,     3,     3,    1,     6,    0,     1,          0},//鬼吸蝠8
            {20,    250,   4,      11,    32,32,     3,     3,    1,     6,    0,     1,          0},//杀人蜂9
            {25,    500,   3,      11,    32,32,     0,     0,    1,     7,    0,     1,          0},//荒漠蝎10
    };                                                                            /*11行走图一致 12异常状态*/
                                                                      /*9帧数  10攻击类型（0：单体 1：穿透 2：全屏）*/
    //平直（通常）
    public Bullet(int dir, int x, int y,int oldx,int oldy, int id, float atk) {
        super();
        bulletDir = dir;
        this.x = x;
        this.y = y;
        this.oldX = oldx;
        this.oldY = oldy;
        bulletID = id;
        this.atk = atk;
    }

    //近身攻击（怪物用）
    public Bullet(int dir, Mob mob, int oldx,int oldy, int id, float atk) {
        super();
        bulletDir = dir;
        this.x = mob.getX();
        this.y = mob.getY();
        this.oldX = oldx;
        this.oldY = oldy;
        bulletID = id;
        this.atk = atk;
        this.mob = mob;
        origin = 1;
    }

    //角度（怪物用）
    public Bullet(double a, double b, int x, int y, int id, float atk) {
        super();
        this.x = x;
        this.y = y;
        bulletID = id;
        this.atk = atk;
        this.angleX = a;
        this.angleY = b;
    }

    //跟踪（怪物用）
    public Bullet(int x, int y, int id, float atk) {
        super();
        this.x = x;
        this.y = y;
        bulletID = id;
        this.atk = atk;
        bulletDir = 100;
    }

    public void gameMapPosition(int x, int y){
        this.originX = x;
        this.originY = y;
    }

    public void actorPosition(Actor actor){
        this.actor = actor;
    }


    public int getBulletID() {
        return bulletID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setBulletDir(int bulletDir) {
        this.bulletDir = bulletDir;
    }

    public int getAtk() {
        return (int)(bulletList[getBulletID() - 1][0]*atk);
    }

    public int getTime() {
        return bulletList[getBulletID() - 1][1];
    }

    public int getSpeed() {
        return bulletList[getBulletID() - 1][2];
    }

    public int getEffect() {
        return bulletList[getBulletID() - 1][3];
    }

    public int getBulletWidth() {
        return bulletList[getBulletID() - 1][4];
    }

    public int getBulletHeight() {
        return bulletList[getBulletID() - 1][5];
    }

    public int getDeviationx() {
        return bulletList[getBulletID() - 1][6];
    }

    public int getDeviationy() {
        return bulletList[getBulletID() - 1][7];
    }

    public int getType() {
        return bulletList[getBulletID() - 1][8];
    }

    public int getFrame() {
        return bulletList[getBulletID() - 1][9];
    }

    public int getPenetrate() {
        return bulletList[getBulletID() - 1][10];
    }

    public int sameImg() {
        return bulletList[getBulletID() - 1][11];
    }

    public int getStatus() {
        return bulletList[getBulletID() - 1][12];
    }

    public boolean isDeath() {
        return death;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }

    public Image getImg() {
        String dir = null;
        String str = null;
        int bulletDirs;
        if (bulletDir > 10){
            bulletDirs = bulletDir/10;
        }else bulletDirs = bulletDir;
        switch (bulletDirs) {
            case 1:
                dir = "u";
                break;
            case 2:
                dir = "d";
                break;
            case 3:
                dir = "l";
                break;
            case 4:
                dir = "r";
                break;
        }
        if (sameImg() == 1) {
            str = path + bulletID + "_" + step + ".png";
        } else {
            str = path + bulletID + "_" + dir + "_" + step + ".png";
        }
        return (new ImageIcon(str)).getImage();
    }

    public int getWidth() {
        return getImg().getWidth(null);
    }

    public int getHeight() {
        return getImg().getHeight(null);
    }

    public void move() {
        //固定角度（跟踪）
        if (bulletDir == 0){
            x += angleX*getSpeed();
            y += angleY*getSpeed();
        }
        //动态角度（跟踪）
        if (bulletDir == 100){
            double a = Math.abs(x+originX - actor.getX());
            double b = Math.abs(y+originY - actor.getY());
            double c = Math.sqrt(a*a + b*b);
            if (x+originX > actor.getX()&&y+originY <= actor.getY()){
                x -= a/c*getSpeed();
                y += b/c*getSpeed();
            }
            if (x+originX >= actor.getX()&&y+originY > actor.getY()){
                x -= a/c*getSpeed();
                y -= b/c*getSpeed();
            }
            if (x+originX < actor.getX()&&y+originY >= actor.getY()){
                x += a/c*getSpeed();
                y -= b/c*getSpeed();
            }
            if (x+originX <= actor.getX()&&y+originY < actor.getY()){
                x += a/c*getSpeed();
                y += b/c*getSpeed();
            }
        }
        //平直
        if (bulletDir == 1){
            y -= getSpeed();
        }
        if (bulletDir == 2){
            y += getSpeed();
        }
        if (bulletDir == 3){
            x -= getSpeed();
        }
        if (bulletDir == 4){
            x += getSpeed();
        }
        //固定角度
        if (bulletDir >= 10){
            if (bulletDir/10 == 1){
                y -= getSpeed()*Math.cos(0.5);
                if ((bulletDir%10)%2 == 0){
                    x += (float)getSpeed()*Math.sin(0.5);
                }else if ((bulletDir%10)%2 == 1){
                    x -= (float)getSpeed()*Math.sin(0.5);
                }
            }
            if (bulletDir/10 == 2){
                y += getSpeed()*Math.cos(0.5);
                if ((bulletDir%10)%2 == 0){
                    x += (float)getSpeed()*Math.sin(0.5);
                }else if ((bulletDir%10)%2 == 1){
                    x -= (float)getSpeed()*Math.sin(0.5);
                }
            }
            if (bulletDir/10 == 3){
                x -= getSpeed()*Math.cos(0.5);
                if ((bulletDir%10)%2 == 0){
                    y += (float)getSpeed()*Math.sin(0.5);
                }else if ((bulletDir%10)%2 == 1){
                    y -= (float)getSpeed()*Math.sin(0.5);
                }
            }
            if (bulletDir/10 == 4){
                x += getSpeed()*Math.cos(0.5);
                if ((bulletDir%10)%2 == 0){
                    y += (float)getSpeed()*Math.sin(0.5);
                }else if ((bulletDir%10)%2 == 1){
                    y -= (float)getSpeed()*Math.sin(0.5);
                }
            }
        }
    }

    //获取子弹的范围
    public Rectangle getRect() {
        if (getBulletWidth() == 0) {
            return new Rectangle(0, 0, 1280, 720);
        } else return new Rectangle((originX-oldX)+getDeviationx() + x, (originY-oldY)+getDeviationy() + y, getBulletWidth(), getBulletHeight());
    }

    //子弹的碰撞过程
    public boolean hit(Rectangle w) {
        //如果子弹与矩形的范围重合子弹死亡
        if (!isDeath() && this.getRect().intersects(w)) {
            return true;
        }return false;
    }

    public void draw(int id, Graphics g) {
        bulletID = id;
        tm = getTime();
        if (death) return;
        if (steps == tm) {
            steps = 0;  //步骤时间归0
            setDeath(true);  //子弹死亡
        }
        move();
        if (getBulletWidth() == 0) {
            g.drawImage(getImg(), 1280 / 2 - getWidth() / 2, 720 / 2 - getHeight() / 2, null);
        } else g.drawImage(getImg(), (originX-oldX)+x - getWidth() / 2 + 16, (originY-oldY)+y - getHeight() / 2 + 16, null);
        steps++;
        if (steps%6 == 0) {
            step++;
            if (step == getFrame()) {
                step = 1;
            }
        }
    }
}

