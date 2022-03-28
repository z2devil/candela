import javax.swing.*;
import java.awt.*;
//0号武器那个挨千刀的
public class Event{
    private int x,y;
    private int width,height;
    private int dx,dy;
    private int oldX, oldY;
    private int originX, originY;
    private int lootID;
    private int mapid;
    private int eventid;
    private int type;//事件类型：0：与角色接触执行 1：自动执行
    private int frame = 1;
    private int cost = 0;
    private int timer = 0;
    private boolean live;
    private boolean action = true;
    private Rectangle actorRect = null;
    private Image img = null;
    private Font font = new Font("宋体", Font.PLAIN, 12);
    private String str = null;
    private GameMap gameMap;
    private int[][][] eventArrayList = {
           /*0type    1x      2y      3w     4h      5f     6live  7dx    8dy    9掉落物类型(0：不是 1：是 2：商品 3:商人)*/
            {{0,      0,      0,     24,     24,     1,     1,     0,     0,     1},//掉落物
             {0,      0,      0,      0,      0,     1,     0,     0,     0,     0}},//0
            {{0,    556,      0,    128,     32,     1,     1,     0,     0,     0},//传送点
             {1,    608,    320,     64,     32,     1,     1,    16,     0,     1},//路障
             {1,      0,      0,      0,      0,     0,     1,     0,     0,     0}},//1
            {{0,    568,    236,     40,     40,     4,     1,   -22,   -50,     0},//传送门
             {0,    816,    390,     32,     32,     1,     1,     0,     0,     3},//商人
             {0,    776,    440,     24,     24,     1,     1,     0,     0,     2},//商品1
             {0,    816,    440,     24,     24,     1,     1,     0,     0,     2},//商品2
             {0,    856,    440,     24,     24,     1,     1,     0,     0,     2},//商品3
             {0,      0,      0,     0,       0,     1,     0,     0,     0,     0}},//2
            {{0,    556,      0,    128,     32,     1,     1,     0,     0,     0},
             {0,   1248,    450,     32,    130,     1,     1,     0,     0,     0}},//3
            {{0,    556,    928,    128,     32,     1,     1,     0,     0,     0},
             {0,   1248,    450,     32,    130,     1,     1,     0,     0,     0}},//4
            {{0,    556,      0,    128,     32,     1,     1,     0,     0,     0},
             {0,      0,    450,     32,    128,     1,     1,     0,     0,     0},
             {0,    556,    928,    128,     32,     1,     1,     0,     0,     0}},//5
            {{0,    556,      0,    128,     32,     1,     1,     0,     0,     0},
             {0,      0,    450,     32,    128,     1,     1,     0,     0,     0},
             {0,    556,    928,    128,     32,     1,     1,     0,     0,     0}},//6
            {{0,    556,    928,    128,     32,     1,     1,     0,     0,     0},
             {0,   1248,    450,     32,    130,     1,     1,     0,     0,     0}},//7
            {{0,    556,      0,    128,     32,     1,     1,     0,     0,     0},
             {0,   1248,    450,     32,    130,     1,     1,     0,     0,     0}},//8
            {{0,      0,    450,     32,    128,     1,     1,     0,     0,     0},
             {0,    816,    390,     32,     32,     1,     1,     0,     0,     3},//商人
             {0,    776,    440,     24,     24,     1,     1,     0,     0,     2},//商品1
             {0,    816,    440,     24,     24,     1,     1,     0,     0,     2},//商品2
             {0,    856,    440,     24,     24,     1,     1,     0,     0,     2},//商品3
             {0,      0,      0,      0,      0,     1,     0,     0,     0,     0}},//9
            {{0,    556,      0,    128,     32,     1,     1,     0,     0,     0},
             {0,      0,    450,     32,    128,     1,     1,     0,     0,     0},
             {0,    620,    200,     32,     32,     3,     1,     0,   -32,     0},},//10
            {{0,    -100,  -100,     40,     40,     4,     1,   -22,   -50,     0},
             {1,      0,      0,      0,      0,     1,     1,     0,     0,     0}},//11
    };

    public Event(int mid, int oldX, int oldY, int eid) {
        mapid = mid;
        eventid = eid;
        this.oldX = oldX;
        this.oldY = oldY;
        this.type = eventArrayList[mapid][eid][0];
        this.x = oldX+eventArrayList[mapid][eid][1];
        this.y = oldY+eventArrayList[mapid][eid][2];
        this.width = eventArrayList[mapid][eid][3];
        this.height = eventArrayList[mapid][eid][4];
        this.dx = eventArrayList[mapid][eid][7];
        this.dy = eventArrayList[mapid][eid][8];
        if (eventArrayList[mapid][eid][6] == 1){//其掉落倍率在GameMap中写
            this.live = true;
        }
        if (eventArrayList[mapid][eid][9] == 2){
            int s = (int) (Math.random()*16+1);
            if (s >= 1&&s<=5){
                lootID = s;
            }
            if (s >= 6&&s<=8){
                lootID = 100+s-5;
            }
            if (s >= 9&&s<=11){
                lootID = 200+s-8;
            }
            if (s >= 12&&s<=16){
                lootID = 300+s-11;
            }
            cost = s*10;
        }
        if (eventArrayList[mapid][eid][9] == 3){
            cost = 20;
        }
        getEventimg();
    }

    public int getEventid() {
        return eventid;
    }

    public int getLootID() {
        return lootID;
    }

    public void setLootID(int lootID) {
        this.lootID = lootID;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int getType() {
        return type;
    }

    public boolean isAction(){
        return action;
    }

    public boolean isLive() {
        return live;
    }

    public int getDX(){
        return dx;
    }

    public int getDY(){
        return dy;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public boolean isLoot(){
        if (lootID != 0){
            return true;
        }
        return false;
    }

    public int getFrame(){
        return eventArrayList[mapid][eventid][5];
    }

    public Image getEventimg() {
        if (isLoot()){
            if (lootID == 500) {
                img = new ImageIcon("src/image/Item/prop/coin.png").getImage();//金币
            }
            if (lootID >= 1 && lootID <= 100) {
                img = new ImageIcon("src/image/Item/prop/item_" + lootID + ".png").getImage();//道具
            }
            if (lootID >= 101 && lootID <= 200) {
                img = new ImageIcon("src/image/Item/hat/hat" + (lootID - 100) + ".png").getImage();//帽子
            }
            if (lootID >= 201 && lootID < 300) {
                img = new ImageIcon("src/image/Item/equip/equip" + (lootID - 200) + ".png").getImage();//衣服
            }
            if (lootID >= 300 && lootID <= 400) {
                img = new ImageIcon("src/image/Item/arm/arm" + (lootID - 300) + ".png").getImage();//武器
            }

        }else if (mapid == 1){
            if (eventid == 1){
                img = new ImageIcon("src/image/Event/stop.png").getImage();
            }
        }else if (mapid == 2){
            if (eventid == 0){
                img = new ImageIcon("src/image/Event/portal"+frame+".png").getImage();
            }
            if (eventid == 1){
                img = new ImageIcon("src/image/Event/trader.png").getImage();
            }
        }else if (mapid == 9){
            if (eventid == 1){
                img = new ImageIcon("src/image/Event/trader.png").getImage();
            }
        }else if (mapid == 10){
            if (eventid == 2){
                img = new ImageIcon("src/image/Event/crystal"+frame+".png").getImage();
            }
        }else if (mapid == 11){
            if (eventid == 0){
                img = new ImageIcon("src/image/Event/portal"+frame+".png").getImage();
            }
        }else {
//            img = new ImageIcon("src/image/X.png").getImage();
        }
        return img;
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

    public Rectangle getRectangle() {
        return new Rectangle((originX-oldX)+x, (originY-oldY)+y, width, height);
    }

    public Rectangle getSmallRectangle(int a) {
        return new Rectangle((originX-oldX)+x+a, (originY-oldY)+y+a, width-a*2, height-a*2);
    }

    public void draw(int x, int y, Graphics g){
        timer++;
        if (timer == 10&&getFrame()!=1){
            frame++;
            timer = 0;
        }
        if (frame == getFrame()) {
            frame = 1;
        }
        if (cost != 0){
            g.setFont(font);
            g.setColor(Color.CYAN);
            str = "$"+cost;
            g.drawString(str,(originX-oldX)+x+getDX()+getWidth()/2-str.length()*3,(originY-oldY)+y+getDY()-5);
        }
        g.drawImage(getEventimg(),(originX-oldX)+x+getDX(),(originY-oldY)+y+getDY(),null);
    }

    //事件页
    public void eventPlay(GameFrame gf) {
        if (mapid == 0){
            if (eventid == 0){
                //掉落物
                if (lootID == 500){
                    gf.actorData.adjustMoney((int)(Math.random()*20+11));
                    this.setLive(false);
                }
                if (lootID >= 1&&lootID <= 6){
                    gf.actorData.getItem(new Item(lootID-1,0,1),1);
                    this.setLive(false);
                }
                if (lootID >= 101&&lootID <= 200){
                    int s;
                    s = lootID - 100;
                    lootID = gf.actorData.getHat() + 100;
                    gf.actorData.setHat(s);
                    cost = 0;
                }
                if (lootID >= 201&&lootID < 300){
                    int s;
                    s = lootID - 200;
                    lootID = gf.actorData.getEquip() + 200;
                    gf.actorData.setEquip(s);
                    cost = 0;
                }
                if (lootID >= 300&&lootID <= 400){
                    int s;
                    s = lootID - 300;
                    if (gf.actorData.arms.size() == 3){
                        lootID = gf.actorData.arms.get(0).getId() + 300;
                        gf.actorData.getArm(new Arm(s));
                    }else {
                        gf.actorData.getArm(new Arm(lootID-300));
                        this.setLive(false);
                    }
                }
            }else System.out.println(mapid+"地图"+eventid+"事件"+"启动");
        }
        if (mapid == 1){
            //传送点
            if (eventid == 0){
                gf.ChangeMap(2,576,896);
            }
            //路障
            if (eventid == 1){
                if (gf.gameMap.mobList.size() == 2){
                    this.setLive(false);
                }
            }
            //地图卷动
            if (eventid == 2){
                gf.gameMapScroll(0,-580);
                this.setLive(false);
            }
        }
        if (mapid == 2){
            //传送门
            if (eventid == 0){
                gf.ChangeMap(3,gf.actor.getX()+3*32,25*32);
            }
            if (eventid == 1){
                if (gf.actorData.getMoney() >= cost){
                    //商店
                    for (int i = 2; i < gf.gameMap.eventList.size(); i++) {
                        if (gf.gameMap.eventList.get(i).isLive()&&((gf.gameMap.eventList.get(i).eventid == 2||gf.gameMap.eventList.get(i).eventid == 3||gf.gameMap.eventList.get(i).eventid == 4))){
                            gf.gameMap.eventList.remove(i);
                            gf.gameMap.eventList.add(i,new Event(gf.gameMap.id,gf.gameMap.x,gf.gameMap.y,i));
                        }
                    }
                    gf.actorData.adjustMoney(-cost);
                }
            }
            if (eventid == 2||eventid == 3||eventid == 4){
                //商品
                if (gf.actorData.getMoney() >= cost){
                    if (lootID <= 100){
                        gf.actorData.getItem(new Item(lootID-1,0,1),1);
                        gf.actorData.adjustMoney(-cost);
                        this.setLive(false);
                    }
                    if (lootID >= 101&&lootID <= 200){
                        int s;
                        s = lootID - 100;
                        lootID = gf.actorData.getHat() + 100;
                        gf.actorData.setHat(s);
                        gf.actorData.adjustMoney(-cost);
                        cost = 0;
                    }
                    if (lootID >= 201&&lootID < 300){
                        int s;
                        s = lootID - 200;
                        lootID = gf.actorData.getEquip() + 200;
                        gf.actorData.setEquip(s);
                        gf.actorData.adjustMoney(-cost);
                        cost = 0;
                    }
                    if (lootID >= 300&&lootID <= 400){
                        int s;
                        s = lootID - 300;
                        if (gf.actorData.arms.size() == 3){
                            lootID = gf.actorData.arms.get(0).getId() + 300;
                            gf.actorData.getArm(new Arm(s));
                        }else {
                            gf.actorData.getArm(new Arm(lootID-300));
                            this.setLive(false);
                        }
                        gf.actorData.adjustMoney(-cost);
                        cost = 0;
                    }
                }
            }
        }
        if (mapid == 3){
            //传送点上
            if (eventid == 0){
                if (gf.gameMap.mobList.size() == 0){
                    gf.ChangeMap(4,576,896);
                }
            }
            //传送点右
            if (eventid == 1){
                if (gf.gameMap.mobList.size() == 0){
                    gf.ChangeMap(6,32,512);
                }
            }
        }
        if (mapid == 4){
            //传送点下
            if (eventid == 0){
                if (gf.gameMap.mobList.size() == 0){
                    gf.ChangeMap(3,576,32);
                }
            }
            //传送点右
            if (eventid == 1){
                if (gf.gameMap.mobList.size() == 0){
                    gf.ChangeMap(5,32,512);
                }
            }
        }
        if (mapid == 5){
            //传送点上
            if (eventid == 0){
                if (gf.gameMap.mobList.size() == 0){
                    gf.ChangeMap(7,576,896);
                }
            }
            //传送点左
            if (eventid == 1){
                if (gf.gameMap.mobList.size() == 0){
                    gf.ChangeMap(4,1218,512);
                }
            }
            //传送点下
            if (eventid == 2){
                if (gf.gameMap.mobList.size() == 0){
                    gf.ChangeMap(6,576,32);
                }
            }
        }
        if (mapid == 6){
            //传送点上
            if (eventid == 0){
                if (gf.gameMap.mobList.size() == 0){
                    gf.ChangeMap(5,576,896);
                }
            }
            //传送点左
            if (eventid == 1){
                if (gf.gameMap.mobList.size() == 0){
                    gf.ChangeMap(3,1218,512);
                }
            }
            //传送点下
            if (eventid == 2){
                if (gf.gameMap.mobList.size() == 0){
                    gf.ChangeMap(8,gf.actor.getX(),32);
                }
            }
        }
        if (mapid == 7){
            //传送点下
            if (eventid == 0){
                if (gf.gameMap.mobList.size() == 0){
                    gf.ChangeMap(5,576,32);
                }
            }
            //传送点右
            if (eventid == 1){
                if (gf.gameMap.mobList.size() == 0){
                    gf.ChangeMap(9,32,512);
                }
            }
        }
        if (mapid == 8){
            //传送点上
            if (eventid == 0){
                if (gf.gameMap.mobList.size() == 0){
                    gf.ChangeMap(6,576,896);
                }
            }
            //传送点右
            if (eventid == 1){
                if (gf.gameMap.mobList.size() == 0){
                    gf.ChangeMap(10,32,512);
                }
            }
        }
        if (mapid == 9){
            //传送点左
            if (eventid == 0){
                if (gf.gameMap.mobList.size() == 0){
                    gf.ChangeMap(7,1218,512);
                }
            }
            if (eventid == 1){
                if (gf.actorData.getMoney() >= cost){
                    //商店
                    for (int i = 2; i < gf.gameMap.eventList.size(); i++) {
                        if (gf.gameMap.eventList.get(i).isLive()&&((gf.gameMap.eventList.get(i).eventid == 2||gf.gameMap.eventList.get(i).eventid == 3||gf.gameMap.eventList.get(i).eventid == 4))){
                            gf.gameMap.eventList.remove(i);
                            gf.gameMap.eventList.add(i,new Event(gf.gameMap.id,gf.gameMap.x,gf.gameMap.y,i));
                        }
                    }
                    gf.actorData.adjustMoney(-cost);
                }
            }
            if (eventid == 2||eventid == 3||eventid == 4){
                //商品
                if (gf.actorData.getMoney() >= cost){
                    if (lootID <= 100){
                        gf.actorData.getItem(new Item(lootID-1,0,1),1);
                        gf.actorData.adjustMoney(-cost);
                        this.setLive(false);
                    }
                    if (lootID >= 101&&lootID <= 200){
                        int s;
                        s = lootID - 100;
                        lootID = gf.actorData.getHat() + 100;
                        gf.actorData.setHat(s);
                        gf.actorData.adjustMoney(-cost);
                        cost = 0;
                    }
                    if (lootID >= 201&&lootID < 300){
                        int s;
                        s = lootID - 200;
                        lootID = gf.actorData.getEquip() + 200;
                        gf.actorData.setEquip(s);
                        gf.actorData.adjustMoney(-cost);
                        cost = 0;
                    }
                    if (lootID >= 300&&lootID <= 400){
                        int s;
                        s = lootID - 300;
                        if (gf.actorData.arms.size() == 3){
                            lootID = gf.actorData.arms.get(0).getId() + 300;
                            gf.actorData.getArm(new Arm(s));
                        }else {
                            gf.actorData.getArm(new Arm(lootID-300));
                            this.setLive(false);
                        }
                        gf.actorData.adjustMoney(-cost);
                        cost = 0;
                    }
                }
            }
        }
        if (mapid == 10){
            //传送点上
            if (eventid == 0){
                if (gf.gameMap.mobList.size() == 0){
                    gf.ChangeMap(11,gf.actor.getX()+5*32,1280-96);
                }
            }
            //传送点左
            if (eventid == 1){
                if (gf.gameMap.mobList.size() == 0){
                    gf.ChangeMap(8,1216,640);
                }
            }
            //治愈水晶
            if (eventid == 2){
                gf.actorData.hpControl(9999);
                gf.actorData.spControl(9999);
                gf.actor.addEffect(12);
            }
        }
        if (mapid == 11){
            //传送点上
            if (eventid == 0){
                if (gf.gameMap.mobList.size() == 0){
                    gf.ChangeMap(2,20*32,10*32);
                }
            }
            //传送点上
            if (eventid == 1){
                if (gf.gameMap.mobList.size() == 0){
                    gf.gameMap.eventList.get(0).setX(800);
                    gf.gameMap.eventList.get(0).setY(500);
                    this.setLive(false);
                }
            }
        }
    }
}
