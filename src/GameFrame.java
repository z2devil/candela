import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameFrame extends JFrame {
    static final int WIDTH = 1280;
    static final int HEIGHT = 720;
    boolean test = false;
    //黑场过渡
    private int blacky1 = -1080;
    private int blacky2 = HEIGHT;
    //切换地图变量
    public int changing = 0;
    private int toId,toX,toY;
    //生成角色数据
    public Data actorData = new Data();
    //生成角色
    public Actor actor = new Actor(this);
    //生成地图
    public GameMap gameMap = new GameMap(this);
    //生成菜单
    public Menu menu = new Menu(actorData);
    //生成UI
    public UI ui = new UI(this);
    //游戏窗口
    public GameFrame() throws Exception {
        //游戏分辨率
        this.setSize(WIDTH, HEIGHT);
//        this.setSize(1920, 1080);
        //游戏名称
        this.setTitle("Candela");
        //更改窗口图标
        ImageIcon icon = new ImageIcon("src/image/Item/arm/arm5.png");
        this.setIconImage(icon.getImage());
        //无边框模式
//        this.setUndecorated(true);
        //居中显示窗口
        this.setLocationRelativeTo(null);
        //设置窗口不可变
        this.setResizable(false);
        //设置窗口关闭
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置窗口可见
        this.setVisible(true);
        //创造空气墙
        gameMap.createSquare();
        //角色线程开始
        actor.start();
        //窗口线程
        Runnable gameThread = () -> {
            while (true) {
                this.repaint();
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread mainlined = new Thread(gameThread);
        mainlined.start();
    }

    //地图卷动  卷动到极限返回true
    public void gameMapScroll(int x, int y){
        if (x>0){
            if (gameMap.x+x<0){
                gameMap.x += x;
                gameMap.bg.x += x;
            }else {
                gameMap.x = 0;
                gameMap.bg.x = 0;
            }
        }else {
            if (gameMap.x+x > -(gameMap.airWall[0].length * 32 - WIDTH)) {
                gameMap.x += x;
                gameMap.bg.x += x;
            }else {
                gameMap.x = -(gameMap.airWall[0].length * 32 - WIDTH);
                gameMap.bg.x = -(gameMap.airWall[0].length * 32 - WIDTH);
            }
        }
        if (y>0){
            if (gameMap.y+y<0){
                gameMap.y += y;
                gameMap.bg.y += y;
            }else {
                gameMap.y = 0;
                gameMap.bg.y = 0;
            }
        }else {
            if (gameMap.y+y >= -(gameMap.airWall.length * 32 - HEIGHT)) {
                gameMap.y += y;
                gameMap.bg.y += y;
            }else {
                gameMap.y = -(gameMap.airWall.length * 32 - HEIGHT);
                gameMap.bg.y = -(gameMap.airWall.length * 32 - HEIGHT);
            }
        }
    }

    public boolean checkGameMapScroll(int x, int y){
        if (x>0){
            if (gameMap.x+x<0){
                gameMap.x += x;
                gameMap.bg.x += x;
            }else {
                gameMap.x = 0;
                gameMap.bg.x = 0;
            }
        }else {
            if (gameMap.x+x >= -(gameMap.airWall[0].length * 32 - WIDTH)) {
                gameMap.x += x;
                gameMap.bg.x += x;
            }else {
                gameMap.x = -(gameMap.airWall[0].length * 32 - WIDTH);
                gameMap.bg.x = -(gameMap.airWall[0].length * 32 - WIDTH);
                return true;
            }
        }
        if (y>0){
            if (gameMap.y+y<0){
                gameMap.y += y;
                gameMap.bg.y += y;
            }else {
                gameMap.y = 0;
                gameMap.bg.y = 0;
            }
        }else {
            if (gameMap.y+y >= -(gameMap.airWall.length * 32 - HEIGHT)) {
                gameMap.y += y;
                gameMap.bg.y += y;
            }else {
                gameMap.y = -(gameMap.airWall.length * 32 - HEIGHT);
                gameMap.bg.y = -(gameMap.airWall.length * 32 - HEIGHT);
                return true;
            }
        }return false;
    }

    //切换地图
    public void ChangeMap(int id,int x, int y){
        toId = id;
        toX = x;
        toY = y;
        changing = 1;
        actor.actorStop();
        actor.setActorLined(false);
        actorData.bullets.clear();//清理子弹容器
    }

    //刷新处理
    public void paint(Graphics g) {
        //双缓存
        BufferedImage bufferedImage = (BufferedImage) this.createImage(this.getSize().width, this.getSize().height);
        Graphics gp = bufferedImage.getGraphics();
        //角色死亡判定
        if ((int)actorData.getActorHPT() == 0){
            actorData.setLive(false);//角色不存活
            actorData.setDeath(true);//角色死亡
            actor.actorStop();//角色立定
            actor.setActorLined(false);//角色固定
            changing = 1;//进入场景改变阶段一
        }
        //角色数据刷新
        if (actorData.isLive()){
            actorData.update();
            actorData.SatusUpdate(actor);
        }
        //绘制底部地图（空气墙，事件，怪物）
        gameMap.drawBace(gp);
        //绘制角色
        if (actorData.isLive()){
            gp.drawImage(actor.getImg(), actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight(), this);
        }else gp.drawImage((new ImageIcon("src/image/Characters/death.png")).getImage(), actor.getX()-25, actor.getY()-12, this);
        actor.drawEffect(gp);
        //绘制顶部地图（子弹，特效）
        gameMap.drawMedium(gp);
        gameMap.drawTop(gp);
        //test/////////////////////////////////////////////
        if (test){
            for (int i = 0; i < gameMap.wallList.size(); i++) {
                gameMap.wallList.get(i).getGameMapOrigin(gameMap.x,gameMap.y);
                gameMap.wallList.get(i).drawX(gp);
            }
            for (int i = 0; i < gameMap.waterList.size(); i++) {
                gameMap.waterList.get(i).getGameMapOrigin(gameMap.x,gameMap.y);
                gameMap.waterList.get(i).drawY(gp);
            }
            System.out.println((actor.getX()+gameMap.x)+"/"+(actor.getY()+gameMap.y));
        }
//        //绘制角色
//        if (actorData.isLive()){
//            gp.drawImage(actor.getImg(), actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight(), this);
//        }else gp.drawImage((new ImageIcon("src/image/Characters/death.png")).getImage(), actor.getX()-25, actor.getY()-12, this);
        //画UI
        if (!test){
            ui.draw(gp);
        }
        //画菜单
        if (menu.isVisible()) {
            menu.draw(gp);
        }
        //场景切换阶段一（落幕 ）
        if (changing == 1) {
            gp.drawImage((new ImageIcon("src/image/UI/black.png")).getImage(), 0, blacky1, this);
            gp.drawImage((new ImageIcon("src/image/UI/black.png")).getImage(), 0, blacky2, this);
            if (!(blacky1 >= 200 && blacky2 <= -(1080-HEIGHT+200))) {
                blacky1 += 15;
                blacky2 -= 15;
            }else if (actorData.isLive()){//判断是 切换地图 还是 死亡界面
                gameMap.id = toId;
                try {
                    gameMap.createSquare();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //X方向
                if (toX>WIDTH/2-180&&toX<WIDTH/2+180){//是否在安全区域
                    actor.setX(toX);
                }else if (toX<WIDTH/2-180){//地图无需卷动且人物在安全区内
                    actor.setX(toX);
                }else if (toX>WIDTH/2+180){//地图需要卷动且人物不在安全区内
                    if (checkGameMapScroll(-(toX-(WIDTH/2+180)),0)){//先将地图卷动，如果地图卷动到极限
                        gameMapScroll(-(toX-(WIDTH/2+180)),0);
                        if (toX >= gameMap.airWall[0].length*32){//地图无法卷动且人物仍然在画面以下
                            actor.setX(WIDTH-32);
                        }else {
                            actor.setX(toX-(gameMap.airWall[0].length*32-WIDTH));
                        }
                    }else {//先将地图卷动，如果地图没有卷动到极限
                        actor.setX(WIDTH/2+180);
                    }
                }
                //Y方向
                if (toY>HEIGHT/2-180&&toY<HEIGHT/2+180){//是否在安全区域
                    actor.setY(toY);
                }else if (toY<HEIGHT/2-180){//地图无需卷动且人物在安全区内
                    actor.setY(toY);
                }else if (toY>HEIGHT/2+180){//地图需要卷动且人物不在安全区内
                    if (checkGameMapScroll(0,-(toY-(HEIGHT/2+180)))){//先将地图卷动，判断地图卷动到极限
                        gameMapScroll(0,-(toY-(HEIGHT/2+180)));
                        if (toY >= gameMap.airWall.length*32){//地图无法卷动且人物仍然在画面以下
                            actor.setY(HEIGHT-32);
                        }else {
                            actor.setY(toY-(gameMap.airWall.length*32-HEIGHT));
                        }
                    }else {//先将地图卷动，如果地图没有卷动到极限
                        actor.setY(HEIGHT/2+180);
                    }
                }
                changing = 2;
            }else if (actorData.isDeath()){
                gp.drawImage((new ImageIcon("src/image/UI/death_txt.png")).getImage(), 0, 0, this);
            }
        }
        //场景切换阶段二（启幕 释放角色）
        if (changing == 2) {
            gp.drawImage((new ImageIcon("src/image/UI/black.png")).getImage(), 0, blacky1, this);
            gp.drawImage((new ImageIcon("src/image/UI/black.png")).getImage(), 0, blacky2, this);
            if (!(blacky1 >= HEIGHT && blacky2 <= -1080)) {
                blacky1 += 15;
                blacky2 -= 15;
            }else {
                blacky1 = -1080;
                blacky2 = HEIGHT;
                changing = 0;
                if (actorData.isDeath()){//复活 播放特效
                    actorData.setDeath(false);
                    actor.addEffect(12);
                }
                actor.setActorLined(true);
            }
        }
        g.drawImage(bufferedImage, 3, 26, null);
    }
}
