import javax.swing.*;
import java.awt.*;

public class Effect {



    // 计数器
    private int step = 1;
    // 当前帧数
    private int steps = 0;
    private int x, y;
    private int oldX, oldY;
    private int originX, originY;
    private int efid;
    private boolean live = true;
    private String str = null;
    private Image img = null;
    //定义特效信息
    private int[][] effecttList = {
            /*w     h     dx   dy   f  tm  fs*/
            {0,     0,    0,    0,  1,  1, 0},//被击特效0
            {200, 200,    15,  15,  4, 17, 0},//物理箭特效1
            {300, 300,     0,  20,  5, 23, 0},//冰霜箭特效2
            {200, 200,    15,  15,  5, 17, 0},//火球特效3
            {200, 200,    15,  15,  5, 23, 0},//毒球特效4
            {420, 420,    30,  30,  6, 29, 0},//黑暗球特效5
            {300, 300,    30,   0,  9, 47, 0},//死神特效6
            {40,   50,    17,   0,  5, 23, 0},//燃烧特效7
            {40,   50,    15,   0,  5, 23, 0},//中毒特效8
            {80,   80,    20,  30,  5, 23, 0},//冰冻特效9
            {110, 110,    20,  15,  5, 23, 0},//致死特效10
            {64,   64,    10,  10,  5, 23, 0},//角色受伤特效11
            {350, 300,    20,  8, 10, 53, 0},//复活特效12
    };

    //通常
    public Effect(int x, int y, int oldX, int oldY, int id) {
        super();
        this.x = x;
        this.y = y;
        this.efid = id;
        this.oldX = oldX;
        this.oldY = oldY;
    }

    //于怪物
    public Effect(Mob mob, int oldX, int oldY, int id) {
        super();
        this.x = mob.getX();
        this.y = mob.getY();
        this.efid = id;
        this.oldX = oldX;
        this.oldY = oldY;
    }

    //于角色
    public Effect(Actor actor, int id) {
        super();
        this.x = actor.getX();
        this.y = actor.getY();
        this.efid = id;
    }

    private Image getImage() {
        str = "src/image/Effect/ef" + efid + "_" + step + ".png";
        return img = (new ImageIcon(str)).getImage();
    }

    private int getWidth() {
        return effecttList[efid][0];
    }

    private int getHeight() {
        return effecttList[efid][1];
    }

    private int getDeviationx() {
        return effecttList[efid][2];
    }

    private int getDeviationy() {
        return effecttList[efid][3];
    }

    private int getFrame() {
        return effecttList[efid][4];
    }

    private int getTime() {
        return effecttList[efid][5];
    }

    private int fullScreen() {
        return effecttList[efid][6];
    }

    //显示于怪物
    public void draw(Graphics g,int x, int y,Mob mob) {
        this.originX = x;
        this.originY = y;
        if (!live) return;
        if (steps == getTime()) {
            live = false;  //爆炸死亡
            steps = 0;  //步骤时间归0
            mob.effects.remove(this);  //集合中删除
            return;
        }
        steps++;
        if (steps % 6 == 0) {
            step++;
        }
        if (step == getFrame()) {
            step = 1;
        }
        g.drawImage(getImage(), mob.getEffectX() - getWidth() / 2 + getDeviationx(), mob.getEffectY() - getHeight() / 2 + getDeviationy(),getWidth(),getHeight(), null);
    }

    //显示于角色
    public void draw(Graphics g,Actor actor) {
        if (!live) return;
        if (steps == getTime()) {
            live = false;  //爆炸死亡
            steps = 0;  //步骤时间归0
            actor.effects.remove(this);  //集合中删除
            return;
        }
        steps++;
        if (steps % 6 == 0) {
            step++;
        }
        if (step == getFrame()) {
            step = 1;
        }
        g.drawImage(getImage(), actor.getX() - getWidth() / 2 + getDeviationx(), actor.getY() - getHeight() / 2 + getDeviationy(),getWidth(),getHeight(), null);
    }

    //显示于地图
    public void draw(Graphics g,GameMap gm) {
        this.originX = gm.x;
        this.originY = gm.y;
        if (!live) return;
        if (steps == getTime()) {
            live = false;  //爆炸死亡
            steps = 0;  //步骤时间归0
            gm.effects.remove(this);  //集合中删除
            return;
        }
        steps++;
        if (steps % 6 == 0) {
            step++;
        }
        if (step == getFrame()) {
            step = 1;
        }
        if (fullScreen() != 1) {
            g.drawImage(getImage(), (originX-oldX)+x - getWidth() / 2 + getDeviationx(), (originY-oldY)+y - getHeight() / 2 + getDeviationy(),getWidth(),getHeight(), null);
        } else g.drawImage(getImage(), 1280 / 2 - getWidth() / 2, 720 / 2 - getHeight() / 2, null);
    }
}
