import javax.swing.*;
import java.awt.*;

public class Arm {
    //基本信息
    private int id;
    private int bulletID;
    private float cd;
    private float cdLimit;
    private Image image = null;
    private String name = null;
    private String armInfo = null;
    Arm(int id) {
        this.id = id;
        cd = 0;
        cdLimit = getCdLimit();
    }
    //获取武器ID
    public int getId() {
        return id;
    }
    //设置CD
    public void setCd(int cd){
        this.cd = cd;
    }
    //设置CDLimit
    public void setCdLimit(int cdLimit){
        this.cdLimit = cdLimit;
    }
    //调整CD
    public void adjustCd(int a){
        this.cd += a;
    }
    //获取CD
    public float getCd(){
        return cd;
    }
    //获取CDLimit
    public float getCdLimit() {
        switch (id) {
            case 0:
                cdLimit = 20f;
                break;
            case 1:
                cdLimit = 100f;
                break;
            case 2:
                cdLimit = 100f;
                break;
            case 3:
                cdLimit = 100f;
                break;
            case 4:
                cdLimit = 500f;
                break;
            case 5:
                cdLimit = 2000f;
                break;
        }
        return cdLimit;
    }
    //获取CD
    public int spCost() {
        int spCost = 0;
        switch (id) {
            case 0:
                spCost = 1;
                break;
            case 1:
                spCost = 10;
                break;
            case 2:
                spCost = 2;
                break;
            case 3:
                spCost = 2;
                break;
            case 4:
                spCost = 10;
                break;
            case 5:
                spCost = 20;
                break;
        }
        return spCost;
    }
    //获取子弹ID
    public int getBulletID() {
        switch (id) {
            case 0:
                bulletID = 1;
                break;
            case 1:
                bulletID = 2;
                break;
            case 2:
                bulletID = 3;
                break;
            case 3:
                bulletID = 4;
                break;
            case 4:
                bulletID = 5;
                break;
            case 5:
                bulletID = 6;
                break;
        }
        return bulletID;
    }

    //获取图像
    public Image getImage() {
        image = (new ImageIcon("src/image/Item/arm/arm"+id+".png")).getImage();
        return image;
    }

    //获取名字
    public String getName() {
        switch (id) {
            case 0:
                name = "木质短弓";
                break;
            case 1:
                name = "冰霜长弓";
                break;
            case 2:
                name = "火炎短杖";
                break;
            case 3:
                name = "毒妖短杖";
                break;
            case 4:
                name = "煌黑法杖";
                break;
            case 5:
                name = "死神柬";
                break;
        }
        return name;
    }
    //获取效果
    public String getEffect() {
        switch (id) {
            case 0:
                armInfo = "ATK:10 CD:20 spCost:1";
                break;
            case 1:
                armInfo = "ATK:100 CD:250 spCost:10";
                break;
            case 2:
                armInfo = "ATK:50 CD:100 spCost:6";
                break;
            case 3:
                armInfo = "ATK:50 CD:100 spCost:6";
                break;
            case 4:
                armInfo = "ATK:200 CD:500 spCost:10";
                break;
            case 5:
                armInfo = "ATK:1000 CD:2000 spCost:20";
                break;
        }
        return armInfo;
    }
    //获取介绍
    public String getInfo() {
        switch (id) {
            case 0:
                armInfo = "常见的木质短弓。";
                break;
            case 1:
                armInfo = "永霜冻土中发现的奇异长弓，周身散发着刺骨的寒气。";
                break;
            case 2:
                armInfo = "能产生熊熊烈火的法杖，触摸它能感觉到到很高的温度。";
                break;
            case 3:
                armInfo = "能产生诡异绿色气体的法杖，本能告诉自己，它有毒。";
                break;
            case 4:
                armInfo = "煌黒龙的天角所制成的法杖，带来时空割裂、世界消没的灾难。";
                break;
            case 5:
                armInfo = "死神的请柬，可以请出死神使用用巨大的镰刀砍向敌人。";
                break;
        }
        return armInfo;
    }
}
