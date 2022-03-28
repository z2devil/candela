import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameMap {
    public int id = 1;
    public int x;
    public int y;
    public int w;
    public int h;
    //导入游戏画面对象
    private GameFrame gf;
    GameMap(GameFrame gf){
        this.gf = gf;
    }
    //读取地图数据
    private ArrayList<String> list = new ArrayList<>();
    public int[][] airWall;
    //生成背景
    public Ground bg = new Ground();
    //生成遮盖光影
    public Overlays overlays = new Overlays();
    //生成空气墙容器
    public Square wall;
    ArrayList<Square> wallList = new ArrayList<>();
    //生成水容器
    public Square water;
    ArrayList<Square> waterList = new ArrayList<>();
    //生成事件容器
    private int eventNumber;
    LinkedList<Event> eventList = new LinkedList<>();
    //生成怪物容器
    public Mob mob;
    ArrayList<Mob> mobList = new ArrayList<>();
    //生成子弹容器
    public Bullet bullet;
    ArrayList<Bullet> bullets = new ArrayList<>();
    //生成特效容器
    public Effect effect;
    ArrayList<Effect> effects = new ArrayList<>();
    //默认地图路径
    private String mappath = "src/map/air.txt";

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public ArrayList<Mob> getMobList() {
        return mobList;
    }

    //初始化地图数据
    public void initAirWall() {
        x = 0;
        y = 0;
        w = 0;
        h = 0;
        bg.x = 0;
        bg.y = 0;
        list.clear();
        wallList.clear();
        waterList.clear();
        eventList.clear();
        mobList.clear();
        bullets.clear();
        effects.clear();
        switch (id){
            case 1:eventNumber = 3;
                break;
            case 2:eventNumber = 5;
                break;
            case 3:eventNumber = 2;
                break;
            case 4:eventNumber = 2;
                break;
            case 5:eventNumber = 3;
                break;
            case 6:eventNumber = 3;
                break;
            case 7:eventNumber = 2;
                break;
            case 8:eventNumber = 2;
                break;
            case 9:eventNumber = 6;
                break;
            case 10:eventNumber = 3;
                break;
            case 11:eventNumber = 2;
                break;
        }
    }

    //读取地图数据
    private void readAirWall() throws Exception {
        mappath = "src/map/air"+gf.gameMap.id+".txt";
        FileInputStream fis = new FileInputStream(mappath);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        //有就读，一回读一行，存储到list字符串容器中
        for (String value = br.readLine(); value != null; value = br.readLine()) {
            list.add(value);
        }
        br.close();
        //确定行高
        int row = list.size();
        int cloum = 0;
        int i;
        String str;
        String[] values;
        //先读取一行，确定宽度
        for (i = 0; i < 1; ++i) {
            str = list.get(i);
            values = str.split(",");//用","作为分隔符
            cloum = values.length;
        }
        //确定宽度后，创建二维数组
        airWall = new int[row][cloum];
        for (i = 0; i < list.size(); ++i) {
            str = list.get(i);
            values = str.split(",");
            for (int j = 0; j < values.length; ++j) {
                airWall[i][j] = Integer.parseInt(values[j]);
            }
        }
    }

    //写入地图数据
    public void writeAirWall() throws Exception {
        readAirWall();
        int[][] newAirWall = new int[airWall.length][airWall[0].length];
        mappath = "src/map/air"+gf.gameMap.id+".txt";
        File file = new File(mappath);
        if (file.exists()&&file.isFile()){
            file.delete();
            System.out.println("删除老文件");
        }
        FileWriter out = new FileWriter(file);
        for (int i = 0; i < newAirWall.length; i++) {
            for (int j = 0; j < newAirWall[i].length; j++) {
                Rectangle rectangle = new Rectangle(i*32+1,j*32+1,30,30);
                for (int k = 0; k < wallList.size(); k++) {
                    if (wallList.get(k).getRectangle().intersects(rectangle)){
                        newAirWall[j][i] = 1;
                    }
                }
                for (int k = 0; k < waterList.size(); k++) {
                    if (waterList.get(k).getRectangle().intersects(rectangle)){
                        newAirWall[j][i] = 2;
                    }
                }
            }
        }
        for (int i = 0; i < newAirWall.length/2; i++) {
            for (int j = 0; j < newAirWall[i].length; j++) {
                out.write(newAirWall[i][j]+",");
            }
            out.write("\n");
        }
        out.close();
    }

    //创造矩形
    public void createSquare() throws Exception {
        initAirWall();
        readAirWall();
        //生成事件
        for (int k = 0; k < eventNumber; k++) {
            eventList.add(new Event(id,x,y,k));
        }
        this.w = airWall[0].length*32;
        this.h = airWall.length*32;
        for (int i = 0; i < airWall.length; ++i) {
                for (int j = 0; j < airWall[0].length; ++j) {
                //空气墙（完全障碍）
                if (airWall[i][j] == 1) {
                    wall = new Square(x + j * 32, y + i * 32,x,y, 32, 32);
                    wallList.add(wall);
                }
                //水（低位障碍）
                if (airWall[i][j] == 2) {
                    water = new Square(x + j * 32, y + i * 32,x,y, 32, 32);
                    waterList.add(water);
                }
                //怪物
                if (airWall[i][j] >= 10) {
                    mob = new Mob(airWall[i][j] - 9, x + j * 32, y + i * 32, x, y);
                    mobList.add(mob);
                }
            }
        }
    }

    public void addEffect(int x, int y, int efid) {
        effects.add(new Effect(x, y, gf.gameMap.x, gf.gameMap.y, efid));
    }

    //绘制底部地图元素
    public void drawBace(Graphics g) {
        //绘制背景
        bg.draw(id, g);
        //处理事件
        for (int i = 0; i < eventList.size(); i++) {
            Event event = eventList.get(i);
            event.getGameMapOrigin(x,y);
            event.getActorRect(gf.actor.getActorRect());
            if (event.isLive()){
                event.draw(event.getX(),event.getY(),g);
                if (eventList.get(i).getType() == 0){
                    //判断角色矩形与事件矩形是否交叉
                    if (gf.actor.getActorRect().intersects(event.getRectangle())&&eventList.get(i).isLive()) {
                        if (event.isAction()){
                            event.eventPlay(gf);
                            event.setAction(false);
                        }
                    }else event.setAction(true);
                }else if (eventList.get(i).getType() == 1){
                    eventList.get(i).eventPlay(gf);
                }
            }
        }
    }
    //绘制顶部地图元素
    public void drawMedium(Graphics g) {
        //处理怪物
        for (int m = 0; m < mobList.size(); ++m) {
            Mob mob = mobList.get(m);
            if (!mob.isFly()){
                //传参
                mob.getGameMapOrigin(x,y);
                mob.mobSatusUpdate(gf);
                mob.mobDataCheck();
                mob.getWallList(wallList, waterList);
                mob.getActorRect(gf.actor.getActorRect());
                mob.action();
                mob.move(gf);
                if (!mob.isLive()) {
                    mobList.remove(m);
                    if (mob.getLoot() == 1){
                        Event coin = new Event(0,x,y,0);
                        coin.setLootID(500);
                        coin.setX(x+mob.getX()-12);
                        coin.setY(y+mob.getY());
                        eventList.add(coin);
                        Event loot = new Event(0,x,y,0);
                        loot.setX(x+mob.getX()+12);
                        loot.setY(y+mob.getY());
                        loot.setLootID(202);
                        eventList.add(loot);
                    }
                    if (mob.getLoot() == 2){
                        Event coin = new Event(0,x,y,0);
                        coin.setLootID(500);
                        coin.setX(x+mob.getX()-12);
                        coin.setY(y+mob.getY());
                        eventList.add(coin);
                        Event loot = new Event(0,x,y,0);
                        loot.setX(x+mob.getX()+12);
                        loot.setY(y+mob.getY());
                        int lootNum = (int) (Math.random()*100+1);//此处是怪物掉落倍率
                        int lootID;
                        if (lootNum <= 50){//道具
                            lootID = (int) (Math.random()*6+1);
                            loot.setLootID(lootID);
                        }
                        if (lootNum > 50&&lootNum <= 70){//帽子
                            lootID = (int) (Math.random()*5+1);
                            loot.setLootID(lootID+100);
                        }
                        if (lootNum > 70&&lootNum <= 90){//衣服
                            lootID = (int) (Math.random()*5+1);
                            loot.setLootID(lootID+200);
                        }
                        if (lootNum > 90&&lootNum <= 100){//武器
                            lootID = (int) (Math.random()*5+1);
                            loot.setLootID(lootID+300);
                        }
                        eventList.add(loot);
                    }
                }
                //绘制怪物
                mob.drawMob(g);
            }
        }
        //处理子弹
        for (int j = 0; j < bullets.size(); j++) {
            Bullet bullet = bullets.get(j);  //获取当前子弹
            if (bullet.getPenetrate() != 2){
                bullet.gameMapPosition(x,y);
                bullet.actorPosition(gf.actor);
                bullet.draw(bullet.getBulletID(), g);  //画子弹
                //与墙碰撞
                for (int m = 0; m < wallList.size(); ++m) {
                    Square wall = wallList.get(m);
                    wall.getGameMapOrigin(x,y);
                    if (bullet.hit(wall.getRectangle())) {
                        if (bullet.getPenetrate() != 2) {
                            bullet.setDeath(true);
                            if (bullets.get(j).isDeath()) {
                                addEffect(bullet.getX(), bullet.getY(), bullet.getEffect());
                                bullets.remove(j);
                            }
                        }
                    }
                }
                //三种伤害类型（0：对怪 1：对人 2：对全部）
                if (bullet.getType() == 0){
                    //与怪碰撞
                    for (int m = 0; m < mobList.size(); ++m) {
                        Mob mob = mobList.get(m);
                        if (bullet.hit(mob.getRectangle())) {
                            if (bullet.getPenetrate() == 0) {
                                bullet.setDeath(true);
                            }
                            mob.mobHit(bullet, this);
                            if (bullet.isDeath()) {
                                bullets.remove(j);
                            }
                        }
                    }
                }
                if (bullet.getType() == 1){
                    if (bullet.hit(gf.actor.getActorRect())) {
                        gf.actorData.actorHit(bullet, gf.actor);
                        if (bullet.isDeath()) {
                            bullets.remove(j);
                        }
                    }
                }
                if (bullet.getType() == 2){
                    for (int m = 0; m < mobList.size(); ++m) {
                        Mob mob = mobList.get(m);
                        if (bullet.hit(mob.getRectangle())) {
                            if (bullet.getPenetrate() == 0) {
                                bullet.setDeath(true);
                            }
                            mob.mobHit(bullet, this);
                            if (bullet.isDeath()) {
                                bullets.remove(j);
                            }
                        }
                    }
                    if (bullet.hit(gf.actor.getActorRect())) {
                        gf.actorData.actorHit(bullet, gf.actor);
                        if (bullet.isDeath()) {
                            bullets.remove(j);
                        }
                    }
                }
            }
        }
        //绘制怪物信息
        for (int m = 0; m < mobList.size(); ++m) {
            Mob mob = mobList.get(m);
            mob.drawInfo(g);
        }
        //画特效
        for (int i = 0; i < effects.size(); i++) {
            effects.get(i).draw(g,gf.gameMap);
        }
        //画遮盖光影
        overlays.drawCover(id, x, y, g);
    }
    public void drawTop(Graphics g) {
        //处理怪物
        for (int m = 0; m < mobList.size(); ++m) {
            Mob mob = mobList.get(m);
            if (mob.isFly()) {
                //传参
                mob.getGameMapOrigin(x,y);
                mob.mobSatusUpdate(gf);
                mob.mobDataCheck();
                mob.getWallList(wallList, waterList);
                mob.getActorRect(gf.actor.getActorRect());
                mob.action();
                mob.move(gf);
                if (!mob.isLive()) {
                    mobList.remove(m);
                    if (mob.getLoot() == 1){
                        Event coin = new Event(0,x,y,0);
                        coin.setLootID(500);
                        coin.setX(x+mob.getX()-12);
                        coin.setY(y+mob.getY());
                        eventList.add(coin);
                        Event loot = new Event(0,x,y,0);
                        loot.setX(x+mob.getX()+12);
                        loot.setY(y+mob.getY());
                        loot.setLootID(202);
                        eventList.add(loot);
                    }
                    if (mob.getLoot() == 2){
                        Event coin = new Event(0,x,y,0);
                        coin.setLootID(500);
                        coin.setX(x+mob.getX()-12);
                        coin.setY(y+mob.getY());
                        eventList.add(coin);
                        Event loot = new Event(0,x,y,0);
                        loot.setX(x+mob.getX()+12);
                        loot.setY(y+mob.getY());
                        int lootNum = (int) (Math.random()*100+1);//此处是怪物掉落倍率
                        int lootID;
                        if (lootNum <= 50){//道具
                            lootID = (int) (Math.random()*6+1);
                            loot.setLootID(lootID);
                        }
                        if (lootNum > 50&&lootNum <= 70){//帽子
                            lootID = (int) (Math.random()*5+1);
                            loot.setLootID(lootID+100);
                        }
                        if (lootNum > 70&&lootNum <= 90){//衣服
                            lootID = (int) (Math.random()*5+1);
                            loot.setLootID(lootID+200);
                        }
                        if (lootNum > 90&&lootNum <= 100){//武器
                            lootID = (int) (Math.random()*5+1);
                            loot.setLootID(lootID+300);
                        }
                        eventList.add(loot);
                    }
                }
                //绘制怪物
                mob.drawMob(g);
            }
        }
        //处理子弹
        for (int j = 0; j < bullets.size(); j++) {
            Bullet bullet = bullets.get(j);  //获取当前子弹
            if (bullet.getPenetrate() == 2) {
                bullet.gameMapPosition(x,y);
                bullet.actorPosition(gf.actor);
                bullet.draw(bullet.getBulletID(), g);  //画子弹
                //与墙碰撞
                for (int m = 0; m < wallList.size(); ++m) {
                    Square wall = wallList.get(m);
                    wall.getGameMapOrigin(x,y);
                    if (bullet.hit(wall.getRectangle())) {
                        if (bullet.getPenetrate() != 2) {
                            bullet.setDeath(true);
                            if (bullets.get(j).isDeath()) {
                                addEffect(bullet.getX(), bullet.getY(), bullet.getEffect());
                                bullets.remove(j);
                            }
                        }
                    }
                }
                //三种伤害类型（0：对怪 1：对人 2：对全部）
                if (bullet.getType() == 0){
                    //与怪碰撞
                    for (int m = 0; m < mobList.size(); ++m) {
                        Mob mob = mobList.get(m);
                        if (bullet.hit(mob.getRectangle())) {
                            if (bullet.getPenetrate() == 0) {
                                bullet.setDeath(true);
                            }
                            mob.mobHit(bullet, this);
                            if (bullet.isDeath()) {
                                bullets.remove(j);
                            }
                        }
                    }
                }
                if (bullet.getType() == 1){
                    if (bullet.hit(gf.actor.getActorRect())) {
                        gf.actorData.actorHit(bullet, gf.actor);
                        if (bullet.isDeath()) {
                            bullets.remove(j);
                        }
                    }
                }
                if (bullet.getType() == 2){
                    for (int m = 0; m < mobList.size(); ++m) {
                        Mob mob = mobList.get(m);
                        if (bullet.hit(mob.getRectangle())) {
                            if (bullet.getPenetrate() == 0) {
                                bullet.setDeath(true);
                            }
                            mob.mobHit(bullet, this);
                            if (bullet.isDeath()) {
                                bullets.remove(j);
                            }
                        }
                    }
                    if (bullet.hit(gf.actor.getActorRect())) {
                        gf.actorData.actorHit(bullet, gf.actor);
                        if (bullet.isDeath()) {
                            bullets.remove(j);
                        }
                    }
                }
            }
        }
        //绘制怪物信息
        for (int m = 0; m < mobList.size(); ++m) {
            Mob mob = mobList.get(m);
            mob.drawInfo(g);
        }
        if (gf.test){
            overlays.drawLine(id, x, y, g);
        }
    }
}
