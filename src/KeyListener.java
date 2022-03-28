import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class KeyListener extends KeyAdapter {
    //导入对象
    private GameFrame gf;

    KeyListener(GameFrame gf) {
        this.gf = gf;
    }

    //按下按键时
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case 37://left
                gf.actor.setState(3,1);
                gf.actor.setDir(3);
                break;
            case 38://up
                gf.actor.setState(1,1);
                gf.actor.setDir(1);
                break;
            case 39://right
                gf.actor.setState(4,1);
                gf.actor.setDir(4);
                break;
            case 40://down
                gf.actor.setState(2,1);
                gf.actor.setDir(2);
                break;
            case 68: {
                gf.menu.setVisible(true);
                break;
            }
        }
    }

    //松开按键
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 39) {
            gf.actor.setState(4,0);
        }
        if (code == 37) {
            gf.actor.setState(3,0);
        }
        if (code == 38) {
            gf.actor.setState(1,0);
        }
        if (code == 40) {
            gf.actor.setState(2,0);
        }
        //D
        if (code == 68) {
            gf.menu.setVisible(false);
        }
        //C
        if (code == 67) {
            if (gf.test){
                int i,j;
                int check = 0;
                int wallsize = gf.gameMap.wallList.size();
                int watersize = gf.gameMap.waterList.size();
                for (i = 0; i < wallsize; ++i) {
                    Square wall = gf.gameMap.wallList.get(i);
                    //判断角色矩形与空气墙矩形是否交叉
                    if (gf.actor.getActorRect().intersects(wall.getRectangle())) {
                        check = i;
                        gf.gameMap.wallList.remove(i);
                        break;
                    }
                }
                for (j = 0; j < watersize; ++j) {
                    Square water = gf.gameMap.waterList.get(j);
                    if (gf.actor.getActorRect().intersects(water.getRectangle())) {
                        check = -j;
                    }
                }
                if (check == 0){
                    Square wall = new Square((gf.actor.getX())/32*32, (gf.actor.getY())/32*32,gf.gameMap.x,gf.gameMap.y, 32, 32);
                    gf.gameMap.wallList.add(wall);
                }
            }else {
                if (gf.actorData.isLive()){
                    gf.actor.ATK();
                }else if (gf.actorData.isDeath()){//已知，按得太快导致不显示死亡提示
                    gf.gameMap.initAirWall();
                    gf.actorData.setActorHPF(200);
                    gf.actorData.setActorHPT(200);
                    gf.actorData.hpControl(9999);
                    gf.actorData.spControl(9999);
                    gf.actorData.setLive(true);
                    gf.ChangeMap(2,20*32,13*32);
                }
            }
        }
        //X
        if (code == 88) {
            if (gf.test){
                int i,j;
                int check = 0;
                int wallsize = gf.gameMap.wallList.size();
                int watersize = gf.gameMap.waterList.size();
                for (i = 0; i < wallsize; ++i) {
                    Square wall = gf.gameMap.wallList.get(i);
                    //判断角色矩形与空气墙矩形是否交叉
                    if (gf.actor.getActorRect().intersects(wall.getRectangle())) {
                        check = i;
                    }
                }
                for (j = 0; j < watersize; ++j) {
                    Square water = gf.gameMap.waterList.get(j);
                    if (gf.actor.getActorRect().intersects(water.getRectangle())) {
                        check = -j;
                        gf.gameMap.waterList.remove(j);
                        break;
                    }
                }
                if (check == 0) {
                    Square wall = new Square((gf.actor.getX()) / 32 * 32, (gf.actor.getY()) / 32 * 32, gf.gameMap.x, gf.gameMap.y, 32, 32);
                    gf.gameMap.waterList.add(wall);
                }
            }
        }
        //Z
        if (code == 90) {
            gf.actorData.changeArm();
        }
        //A
        if (code == 65) {
            gf.actorData.changeItem();
        }
        //S
        if (code == 83) {
            gf.actorData.useItem(0);
        }
        //F12 test
        if (code == 123) {
            if (gf.test){
                gf.test = false;
                try {
                    gf.gameMap.writeAirWall();
                }catch (Exception e1) {
                    e1.printStackTrace();
                }
            }else gf.test = true;
        }
    }
}
