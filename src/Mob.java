import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Mob {
    private int mobID;
    private int x, y;
    private int oldX, oldY;
    private int originX, originY;
    private int dx, dy;
    private int width, height;
    private int dir = 2;
    private int atkCd = 0;
    private int atkCdLimit = 0;
    private int step = 1;
    private int steps = 0;
    private int actionTimer = 0;
    private int bulletId;
    private int loot;
    private int speed;
    private float hp, hpLimit;
    private float atk,def;
    private boolean fly;
    private boolean live = true;
    private boolean action;
    private boolean actionATK;
    public int count = 5;
    public int point = 0;
    private Image img;
    private Rectangle actorRect = null;
    public ArrayList<Bullet> bullets = new ArrayList<>();
    public ArrayList<Effect> effects = new ArrayList<>();
    public int[] status = {  0,  0,  0,  0,  0};//0燃烧 1中毒 2冰冻 3恐惧 4没想好
    public int[] statusTime = {  500,  500,  500,  500,  500};
    //生成空气墙容器
    ArrayList<Square> wallList = new ArrayList<>();
    //生成水容器容器
    ArrayList<Square> waterList = new ArrayList<>();
    //定义怪物信息
    float[][] mobList = {
            /*0id 1hp    2子弹  3掉落物 4矩形宽 5矩形高  6偏移x 7偏移y 8移动方式  9飞行(0不会 1会) 10攻击CD 11速度 12/13攻防*/
            {0,   100,    7,     1,     32,     32,     0,     0,    1,        0,              200,     1,    1,0},//史莱姆
            {0,   500,    8,     2,     32,     32,     0,     0,     2,        0,             200,     2,    1,0},//丧尸男
            {0,   1000,   8,     2,     32,     32,     0,     0,     3,        0,             200,     2,    1,0},//丧尸女
            {0,   2500,   9,     2,     32,     32,     0,     0,     4,        1,             300,     3,    1,0},//鬼吸蝠
            {0,   2500,  10,     2,     32,     32,     0,     0,     5,        1,             300,     3,    1,0},//杀人蜂
            {0,   5000,  11,     2,     32,     32,     0,     0,     5,        0,             500,     2,    1,0},//荒漠蝎
            {1,   0,      0,     0,     32,     32,   -96,   -160,    0,        1,               0,     0,    1,0},//路牌1
            {2,   0,      0,     0,     32,     32,   -96,   -160,    0,        1,               0,     0,    1,0},//路牌2
            {3,   0,      0,     0,     32,     32,   -96,   -160,    0,        1,               0,     0,    1,0},//路牌3
    };

    public Mob(int id, int x, int y,int oldx,int oldy) {
        this.mobID = id;
        this.x = x;
        this.y = y;
        this.oldX = oldx;
        this.oldY = oldy;
        this.hp = mobList[getMobID() - 1][1];
        this.hpLimit = mobList[getMobID() - 1][1];
        this.bulletId = (int)mobList[getMobID() - 1][2];
        this.loot = (int)mobList[getMobID() - 1][3];
        this.width = (int)mobList[getMobID() - 1][4];
        this.height = (int)mobList[getMobID() - 1][5];
        this.dx = (int)mobList[getMobID() - 1][6];
        this.dy = (int)mobList[getMobID() - 1][7];
        this.atkCdLimit = (int)mobList[getMobID() - 1][10];
        this.speed = (int)mobList[getMobID() - 1][11];
        this.atk = mobList[getMobID() - 1][12];
        this.def = mobList[getMobID() - 1][13];
        if (mobList[getMobID() - 1][9] == 1) {
            this.fly = true;
        } else this.fly = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isLive() {
        return live;
    }

    public boolean isFly() {
        return fly;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void setMobHP(float hp) {
        this.hp = hp;
    }

    public int getMobID() {
        return mobID;
    }

    public float getMobHP() {
        return hp;
    }

    public int getBulletId() {
        return bulletId;
    }

    public int getLoot() {
        return loot;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getImgWidth() {
        return getMobimg().getWidth(null);
    }

    public int getImgHeight() {
        return getMobimg().getHeight(null);
    }

    public int getDeviationx() {
        return dx;
    }

    public int getDeviationy() {
        return dy;
    }

    public int getMoveMode() {
        return (int)mobList[getMobID() - 1][8];
    }

    public int getSpeed() {
        return speed;
    }

    public float getATK() {
        return atk;
    }

    public float getDEF() {
        return def;
    }

    public int getType() {
        return (int)mobList[getMobID() - 1][0];
    }


    private void stepping(){
        steps++;
        if (steps >= Math.abs(10/speed)+3){
            step++;
            steps = 0;
        }
        if (step == 3) {
            step = 1;
        }
    }

    private String getDirStr(){
        if (dir == 1){
            return "u";
        }else if (dir == 2){
            return "d";
        }else if (dir == 3){
            return "l";
        }else if (dir == 4){
            return "r";
        }return "d";
    }

    public String getMobName() {
        switch (getMobID()) {
            case 1:
                return "史莱姆";
            case 2:
                return "丧尸男";
            case 3:
                return "丧尸女";
            case 4:
                return "鬼吸蝠";
            case 5:
                return "杀人蜂";
            case 6:
                return "荒漠蝎";
        }
        return "";
    }

    private Image getMobimg() {
        if (getType() == 0){
            if (getMobID() != 1){
                img = (new ImageIcon("src/image/Mob/mob"+mobID+"_"+getDirStr()+"_"+step+".png")).getImage();
            }else img = (new ImageIcon("src/image/Mob/mob1_1.png")).getImage();
        }else {
            if (getType() == 1||getType() == 2||getType() == 3){
                img = (new ImageIcon("src/image/Mob/tip1.png")).getImage();
            }
        }
        return img;
    }

    public void setMobimg(Image img) {
        this.img = img;
    }

    public void resetMobimg() {
        this.img = getMobimg();
    }

    private Rectangle getRect(int point) {
        switch (point) {
            case 1: {
                return new Rectangle((originX-oldX)+x, (originY-oldY)+y - getSpeed(), getWidth(), getHeight());
            }
            case 2: {
                return new Rectangle((originX-oldX)+x, (originY-oldY)+y + getSpeed(), getWidth(), getHeight());
            }
            case 3: {
                return new Rectangle((originX-oldX)+x - getSpeed(), (originY-oldY)+y, getWidth(), getHeight());
            }
            case 4: {
                return new Rectangle((originX-oldX)+x + getSpeed(), (originY-oldY)+y, getWidth(), getHeight());
            }
        }
        return new Rectangle((originX-oldX)+x, (originY-oldY)+y, getWidth(), getHeight());
    }

    public void mobDataCheck() {
        if (hp > hpLimit) {
            hpLimit = hp;
        }
        if (hp < 0) {
            hp = 0;
        }
        if (hpLimit < 0) {
            hpLimit = 1;
        }
        if (hp <= 0) {
            if (mobList[getMobID() - 1][1] != 0){
                live = false;
            }
        }
    }

    //更新状态
    public void mobSatusUpdate(GameFrame gf) {
        //燃烧
        if (status[0] == 1) {
            statusTime[0]--;
            if (statusTime[0]%24 == 0){
                addEffect(7);
            }
            if (statusTime[0]%50 == 0){
                if (hp >= hp * 0.01) {
                    hp -= hp * 0.01;
                } else {
                    hp = 0;
                }
            }
            if (statusTime[0] <= 0) {
                status[0] = 0;
            }
        }
        //中毒
        if (status[1] == 1) {
            statusTime[1]--;
            if (statusTime[1]%24 == 0){
                addEffect(8);
            }
            if (statusTime[1]%50 == 0){
                if (hp >= (hpLimit - hp) * 0.01) {
                    hp -= (hpLimit - hp) * 0.01;
                } else {
                    hp = 0;
                }
            }
            if (statusTime[1] <= 0) {
                status[1] = 0;
            }
        }
        //冰冻
        if (status[2] == 1) {
            statusTime[2]--;
            if (statusTime[2]%24 == 0){
                addEffect(9);
            }
            if (statusTime[2] <= 0) {
                status[2] = 0;
            }
        }
        //致死
        if (status[3] == 1) {
            statusTime[3]--;
            if (statusTime[3]%100 == 0){
                addEffect(10);
            }
            if (statusTime[3]%50 == 0){
                if (hp >= hp * 0.05) {
                    hp -= hp * 0.05;
                } else {
                    hp = 0;
                }
            }
            if (statusTime[3] <= 0) {
                status[3] = 0;
            }
        }
    }

    //怪物受伤
    /*灵感
    * 伤害帧：在子弹类的子弹信息列表写上希望产生伤害的帧数，然后在这里进行判断，是否为伤害帧，产生伤害一次。*/
    public void mobHit(Bullet b, GameMap gm) {
        if (bullets.contains(b)){
            return;
        }
        bullets.add(b);
        if (b.getStatus() == 1){
            this.status[0] = 1;
            this.statusTime[0] = 500;
        }
        if (b.getStatus() == 2){
            this.status[1] = 1;
            this.statusTime[1] = 500;
        }
        if (b.getStatus() == 3){
            this.status[2] = 1;
            this.statusTime[2] = 500;
        }
        if (b.getStatus() == 4){
            this.status[3] = 1;
            this.statusTime[3] = 1000;
        }
        this.hp -= b.getAtk()*(1-getDEF());
        gm.addEffect(b.getX(), b.getY(), b.getEffect());
        if (b.getPenetrate() == 0){
            b.setDeath(true);
        }
    }

    //怪物攻击
    public void mobAtk(GameFrame gf) {
        if (actionATK) {
            //近战
            if (getMobID() == 2||getMobID() == 3){
                Bullet bullet = new Bullet(x, y, getBulletId(),atk);
                gf.gameMap.bullets.add(bullet);
                actionATK = false;
            }
            //固定瞄准角度
            if (getMobID() == 4||getMobID() == 5){
                double a = Math.abs((originX-oldX)+x - gf.actor.getX());
                double b = Math.abs((originY-oldY)+y - gf.actor.getY());
                double c = Math.sqrt(a*a + b*b);
                Bullet bullet = new Bullet(a/c,b/c,x, y, getBulletId(),atk);
                if ((originX-oldX)+x > gf.actor.getX()&&(originY-oldY)+y <= gf.actor.getY()){
                    bullet = new Bullet(-a/c,b/c,x, y, getBulletId(),atk);
                }
                if ((originX-oldX)+x >= gf.actor.getX()&&(originY-oldY)+y > gf.actor.getY()){
                    bullet = new Bullet(-a/c,-b/c,x, y, getBulletId(),atk);
                }
                if ((originX-oldX)+x < gf.actor.getX()&&(originY-oldY)+y >= gf.actor.getY()){
                    bullet = new Bullet(a/c,-b/c,x,  y, getBulletId(),atk);
                }
                if ((originX-oldX)+x <= gf.actor.getX()&&(originY-oldY)+y < gf.actor.getY()){
                    bullet = new Bullet(a/c,b/c, x, y, getBulletId(),atk);
                }
                gf.gameMap.bullets.add(bullet);
                actionATK = false;
            }
            //跟踪子弹
            if (getMobID() == 6){
                Bullet bullet = new Bullet(x, y, getBulletId(),atk);
                gf.gameMap.bullets.add(bullet);
                actionATK = false;
            }
        }
    }

    //************怪物AI部分***************
    public void move(GameFrame gf) {
        if (action){
            if (fly&&getType() == 0){
                stepping();
            }
            if (status[2] != 1){
                switch (getMoveMode()) {
                    case 0: {
                    }
                    break;
                    //史莱姆
                    case 1: {
                        RandomMove();
                    }
                    break;
                    //丧尸男
                    case 2:{
                        if (RangeCheck(0, 5)) {
                            closeMove();
                            if (RangeCheck(0, 1)){
                                mobAtk(gf);
                            }
                        }else RandomMove();
                    }
                    break;
                    //丧尸女
                    case 3: {
                        if (RangeCheck(0, 5)) {
                            closeMove();
                            if (RangeCheck(0, 1)){
                                mobAtk(gf);
                            }
                        }else RandomMove();
                    }
                    break;
                    //鬼吸蝠
                    case 4: {
                        if (RangeCheck(4, 7)) {
                            closeMove();
                        }
                        if (RangeCheck(0, 4)){
                            mobAtk(gf);
                        }else RandomMove();
                    }
                    break;
                    //杀人蜂
                    case 5: {
                        if (RangeCheck(4, 6)) {
                            closeMove();
                        }
                        if (RangeCheck(0, 4)){
                            mobAtk(gf);
                        }else RandomMove();
                    }
                    break;
                    //荒漠蝎
                    case 6: {
                        if (RangeCheck(5, 7)) {
                            closeMove();
                        }
                        if (RangeCheck(0, 5)){
                            mobAtk(gf);
                        }else RandomMove();
                    }
                    break;
                }
            }
            if (status[3] == 1&&status[2] != 1){
                leaveMove();
            }
            action = false;
        }
    }

    //瞬间执行
    public void action() {
        if (getMoveMode() == 2||getMoveMode() == 3){
            if (RangeCheck(0, 5)) {
                speed = 3;
            }else speed = (int)mobList[getMobID() - 1][11];
        }
        if (getType() == 1) {
            if (RangeCheck(0, 4)) {
                img = (new ImageIcon("src/image/Mob/tip2.png")).getImage();
            }
        }
        if (getType() == 2) {
            if (RangeCheck(0, 4)) {
                img = (new ImageIcon("src/image/Mob/tip3.png")).getImage();
            }
        }
        if (getType() == 3) {
            if (RangeCheck(0, 4)) {
                img = (new ImageIcon("src/image/Mob/tip4.png")).getImage();
            }
        }
    }

    public void getWallList(ArrayList<Square> wallList, ArrayList<Square> waterList) {
        this.wallList = wallList;
        this.waterList = waterList;
    }

    public void getActorRect(Rectangle rect) {
        actorRect = rect;
    }

    public void getGameMapOrigin(int x, int y) {
        originX = x;
        originY = y;
    }

    //范围检测（a=0时，范围包括中心点a>=0，b>=1）
    public boolean RangeCheck(int a, int b) {
        Rectangle rectB = new Rectangle((originX-oldX)+x-b*32, (originY-oldY)+y-b*32, (2*b+1)*32, (2*b+1)*32);
        Rectangle rectA = new Rectangle((originX-oldX)+x-(a-1)*32, (originY-oldY)+y-(a-1)*32, (2*(a-1)+1)*32, (2*(a-1)+1)*32);
        if (a == 0) {
            if (rectB.intersects(actorRect)) {
                return true;
            }
        } else if (rectB.intersects(actorRect) && !rectA.intersects(actorRect)) {
            return true;
        }
        return false;
    }

    //方向碰撞检测（与墙、水、人）
    public boolean hit(int point) {
        int check = 0;
        for (int i = 0; i < wallList.size(); ++i) {
            Square wall = wallList.get(i);
            wall.getGameMapOrigin(originX,originY);
            if (this.getRect(point).intersects(wall.getRectangle()) || this.getRect(point).intersects(actorRect)) {
                count = 0;
                check++;
            }
        }
        for (int j = 0; j < waterList.size(); ++j) {
            Square water = waterList.get(j);
            if (this.getRect(point).intersects(water.getRectangle()) && !this.fly) {
                count = 0;
                check++;
            }
        }
        if (check > 0) {
            return true;
        }
        return false;
    }

    //朝各方向移动
    public void moveU(int speed) {
        if (!hit(1)) {
            this.y -= speed;
            stepping();
            dir = 1;
            if (count >= 1){
                count--;
            }
        }
    }

    public void moveD(int speed) {
        if (!hit(2)) {
            this.y += speed;
            stepping();
            dir = 2;
            if (count >= 1){
                count--;
            }
        }
    }

    public void moveL(int speed) {
        if (!hit(3)) {
            this.x -= speed;
            stepping();
            dir = 3;
            if (count >= 1){
                count--;
            }
        }
    }

    public void moveR(int speed) {
        if (!hit(4)) {
            this.x += speed;
            stepping();
            dir = 4;
            if (count >= 1){
                count--;
            }
        }
    }

    //靠近角色移动
    public void closeMove() {
        if (Math.abs((originX-oldX)+x - actorRect.x) >= Math.abs((originY-oldX)+y - actorRect.y)) {
            count = (int) (Math.random() * 20 + 10);
            if ((originX-oldX)+x >= actorRect.x) {
                point = 3;
                moveL(speed);
            }
            if ((originX-oldX)+x < actorRect.x) {
                point = 4;
                moveR(speed);
            }
        }
        if (Math.abs((originY-oldX)+y - actorRect.y) > Math.abs((originX-oldX)+x - actorRect.x)) {
            count = (int) (Math.random() * 20 + 10);
            if ((originY-oldX)+y >= actorRect.y) {
                point = 1;
                moveU(speed);
            }
            if ((originY-oldX)+y < actorRect.y) {
                point = 2;
                moveD(speed);
            }
        }
    }

    //远离角色移动
    public void leaveMove() {
        if (Math.abs((originX-oldX)+x - actorRect.x) >= Math.abs((originY-oldX)+y - actorRect.y)) {
            count = (int) (Math.random() * 20 + 10);
            if ((originX-oldX)+x >= actorRect.x) {
                point = 4;
                moveR(speed);
            }
            if ((originX-oldX)+x < actorRect.x) {
                point = 3;
                moveL(speed);
            }
        }
        if (Math.abs((originY-oldX)+y - actorRect.y) > Math.abs((originX-oldX)+x - actorRect.x)) {
            count = (int) (Math.random() * 20 + 10);
            if ((originY-oldX)+y >= actorRect.y) {
                point = 2;
                moveD(speed);
            }
            if ((originY-oldX)+y < actorRect.y) {
                point = 1;
                moveU(speed);
            }
        }
    }

    //随机移动
    public void RandomMove() {
        int s = (int) (Math.random() * 8 + 1);
        if (count != 0) {
            switch (point) {
                case 1: {
                    dir = 1;
                    moveU(getSpeed());
                }
                break;
                case 2: {
                    dir = 2;
                    moveD(getSpeed());
                }
                break;
                case 3: {
                    dir = 3;
                    moveL(getSpeed());
                }
                break;
                case 4: {
                    dir = 4;
                    moveR(getSpeed());
                }
                break;
                case 0: {
                    count--;
                }
                break;
            }
        } else {
            switch (s) {
                case 1: {
                    point = 1;
                    dir = 1;
                    moveU(getSpeed());
                }
                break;
                case 2: {
                    point = 2;
                    dir = 2;
                    moveD(getSpeed());

                }
                break;
                case 3: {
                    point = 3;
                    dir = 3;
                    moveL(getSpeed());
                }
                break;
                case 4: {
                    point = 4;
                    dir = 4;
                    moveR(getSpeed());
                }
                break;
                default: {
                    point = 0;
                }
                break;
            }
            count = (int) (Math.random() * 20 + 10);
        }
    }

    public int getEffectX() {
        return (originX-oldX)+x;
    }

    public int getEffectY() {
        return (originY-oldY)+y;
    }

    //于怪物上显示特效
    public void addEffect(int efid) {
        effects.add(new Effect(this, originX, originY, efid));
    }

    //于怪物上绘制特效
    public void drawEffect(Graphics g) {
        for (int i = 0; i < effects.size(); i++) {
            effects.get(i).draw(g,originX,originY,this);
        }
    }

    public Rectangle getRectangle() {
        return new Rectangle((originX-oldX)+x, (originY-oldY)+y, getWidth(), getHeight());
    }

    public void drawMob(Graphics g) {
        g.drawImage(img, (originX-oldX)+this.x+getDeviationx(), (originY-oldY)+this.y+getDeviationy(), getImgWidth(), getImgHeight(), null);
        drawEffect(g);
    }

    public void drawInfo(Graphics g) {
        if (actionTimer >= 2) {
            action = true;
            actionTimer = 0;
        } else actionTimer++;
        if (atkCd >= atkCdLimit) {
            actionATK = true;
            atkCd = 0;
        } else atkCd++;
        Font mainfont = new Font("宋体", Font.PLAIN, 12);
        g.setFont(mainfont);
        g.setColor(Color.white);
        String HPstr = getMobName() + " " + ((int)this.hp) + "/" + ((int)this.hpLimit);
        if (mobList[getMobID() - 1][1] != 0){
            g.drawString(HPstr, (originX-oldX)+this.x + this.getImgWidth() / 2 - (HPstr.length() * 12) / 3, (originY-oldY)+this.y);

        }
    }
}
