import javax.swing.*;
import java.awt.*;

public class Item {
    //基本信息
    private int id;
    private int type;//0：消耗品（UI，个数，菜单）；1：弹药（个数，菜单）；2：其他（菜单）
    private int number;
    private Image image = null;
    private String name = null;
    private String info = null;
    private int[][] itemList = {
           /*0hp    1sp */
            {60,     0},//苹果
            {0,     60},//葡萄
            {100,    100},//黑面包
            {200,    0},//小红
            {0,    200},//小蓝
            {500,  500},//大蓝
    };
    //构造
    Item(int id, int type, int number) {
        this.id = id;
        this.type = type;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public void adjustNumber(int a) {
        number += a;
    }
    //获取数量
    public int getNumber() {
        return number;
    }

    //获取效果
    public void useEffect(int i,Data data) {
        data.hpControl(itemList[i][0]);
        data.spControl(itemList[i][1]);
        switch (id){
            case 0:{
                data.adjustStatus(0,1);
                data.adjustStatusTime(0,100);
            }
            break;
            case 1:{
                data.adjustStatus(1,1);
                data.adjustStatusTime(1,100);
            }
                break;
            case 2:{
                data.adjustStatus(0,1);
                data.adjustStatus(1,1);
                data.adjustStatusTime(0,100);
                data.adjustStatusTime(1,100);
            }break;
        }
    }

    //获取图像
    public Image getImage() {
        image = (new ImageIcon("src/image/Item/prop/item_"+(id+1)+".png")).getImage();
        return image;
    }

    //获取名字
    public String getName() {
        switch (id) {
            case 0:
                name = "苹果";
                break;
            case 1:
                name = "葡萄";
                break;
            case 2:
                name = "黑面包";
                break;
            case 3:
                name = "粉红浓缩液";
                break;
            case 4:
                name = "兰淬果冻";
                break;
            case 5:
                name = "湛蓝精华";
                break;
        }
        return name;
    }

    //获取介绍
    public String getInfo() {
        switch (id) {
            case 0:
                info = "不能沾着氰化钾吃.";
                break;
            case 1:
                info = "香甜~";
                break;
            case 2:
                info = "小麦粉中掺杂了大量的麦糠，廉价常见，味道不是很好。";
                break;
            case 3:
                info = "略有些粘稠的粉红色液体.";
                break;
            case 4:
                info = "晶莹的淡蓝色果冻体，怎么倒出来？";
                break;
            case 5:
                info = "浓稠的湛蓝色液体，仔细观察其中好像有点点星光流转。";
                break;
        }
        return info;
    }
    //获取介绍
    public String getEffect() {
        switch (id) {
            case 0:
                info = "恢复30HP 短时间恢复HP";
                break;
            case 1:
                info = "恢复30SP 短时间恢复SP";
                break;
            case 2:
                info = "恢复50HP 50SP 短时间恢复HP SP";
                break;
            case 3:
                info = "恢复100HP";
                break;
            case 4:
                info = "恢复100SP";
                break;
            case 5:
                info = "恢复500HP 500SP";
                break;
        }
        return info;
    }
}
