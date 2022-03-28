import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Data {
    //角色属性相关
    private boolean live = true;//存活状态
    private boolean death;//死亡状态
    private float actorHPT = 200;
    private float actorHPF = 200;
    private float actorHPLimit = 200;
    private float actorSP = 200;
    private float actorSPLimit = 200;
    private float actorHPResume = (float)0.15;
    private float actorHPRecover = (float)0;
    private float actorSPRecover = (float)0;
    private float atk = 1f;
    private float def = 0f;
    private boolean checkHP = true;
    private boolean checkSP = true;
    private int[] status = {0,0,0,0,0,0,0};//0hp恢复 1sp恢复 2速度提升 3攻击力提升 4防御力提升 5燃烧 6中毒
    private int[] statusTime = {100,100,1000,1000,1000,500,500};
    //声明角色数据
    private int money;
    private int hat;
    private int equip;
    //生成武器容器
    private Arm arm;
    private Arm armRepeater = new Arm(0);
    public ArrayList<Arm> arms = new ArrayList<>();
    //生成物品容器
    private Item item;
    private Item itemRepeater = new Item(0, 0, 0);
    public ArrayList<Item> items = new ArrayList<>();
    //生成子弹容器
    public ArrayList<Bullet> bullets = new ArrayList<>();
    //装备相关
    private float[][] hatList = {
            /*0HP      1SP     2ATK     3DEF*/
            {0,         0,    0,           0},
            {40,        0,    0,       0.10f},//草帽
            {80,        0,    0.10f,   0.15f},//士兵头盔
            {40,      100,    0.15f,   0.10f},//学士法帽
            {400,       0,    0.20f,   0.30f},//骑士头盔
            {240,     500,    0.30f,   0.20f}//光明法帽
    };
    private float[][] equipList = {
           /*0HP      1SP      2ATK      3DEF*/
            {0,        0,     0,           0},
            {50,       0,     0,       0.10f},//布衣
            {100,      0,     0.10f,   0.15f},//士兵铠甲
            {50,     100,     0.15f,   0.10f},//学士法袍
            {500,      0,     0.20f,   0.30f},//骑士铠甲
            {300,    500,     0.30f,   0.20f}//光明法袍
    };

    //构造
    public Data() {
        //初始装备道具
        money = 500;
        hat = 1;
        equip = 1;
        for (int i = 0; i < 1; i++) {
            arm = new Arm(i);
            arms.add(arm);
        }
        for (int i = 0; i < 3; i++) {
            item = new Item(i, 0, 5);
            items.add(item);
        }
    }

    //获取金钱
    public int getMoney() {
        return money;
    }

    public void adjustMoney(int a){
        money += a;
    }

    //检查角色HP
    public void updateHP() {
        if ((actorHPF+actorHPRecover) < (actorHPLimit+getHPplus())){
            actorHPF += actorHPRecover;
        }else actorHPF = actorHPLimit+getHPplus();
        if (actorHPT < actorHPF) actorHPT += (actorHPLimit+getHPplus())*actorHPResume/100;
        if (actorHPT > actorHPF) actorHPT -= (actorHPLimit+getHPplus())*actorHPResume/100;
        if (actorHPF < 0) actorHPF = 0;
        if (actorHPLimit < 1) actorHPLimit = 1;
        if ((actorHPLimit+getHPplus()) < actorHPF) actorHPF = actorHPLimit+getHPplus();
        if ((actorHPLimit+getHPplus()) < actorHPT) actorHPT = actorHPLimit+getHPplus();
    }

    //检查角色SP
    public void updateSP() {
        if (checkSP) {
            if ((actorSPLimit+getSPplus()) > actorSP) actorSP += actorSPRecover;
        }
        if ((actorSPLimit+getSPplus()) < actorSP) actorSP = actorSPLimit+getSPplus();
        if (actorSPLimit < 1) actorSPLimit = 1;
        if (actorSP < 0) actorSP = 0;
    }

    //角色状态相关
    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isDeath() {
        return death;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }

    public void setActorHPT(int aHPT) {
        this.actorHPT = aHPT;
    }

    public void setActorHPF(int aHPF) {
        this.actorHPF = aHPF;
    }

    public void setActorHPLimit(int aHPLimit) {
        this.actorHPLimit = aHPLimit;
    }

    public void setActorSP(int aSP) {
        this.actorSP = aSP;
    }

    public void setActorSPLimit(int aSPLimit) {
        this.actorSPLimit = aSPLimit;
    }

    public float getActorHPT() {
        return actorHPT;
    }

    public float getActorHPF() {
        return actorHPF;
    }

    public float getActorHPLimit() {
        return actorHPLimit+getHPplus();
    }

    public float getActorSP() {
        return actorSP;
    }

    public float getActorSPLimit() {
        return actorSPLimit+getSPplus();
    }

    public float getAtk() {
        return atk+getATKplus();
    }

    public float getDEF() {
        return def+getDEFplus();
    }

    public void adjustStatus(int a, int b){
        status[a] = b;
    }

    public void adjustStatusTime(int a, int b){
        statusTime[a] = b;
    }

    //更新状态
    public void SatusUpdate(Actor actor) {
        //hp恢复
        if (status[0] == 1) {
            actorHPRecover = (float) 0.15;
            statusTime[0]--;
            if (statusTime[0] <= 0) {
                actorHPRecover = (float) 0;
                status[0] = 0;
            }
        }
        //sp恢复
        if (status[1] == 1) {
            actorSPRecover = (float) 0.15;
            statusTime[1]--;
            if (statusTime[1] <= 0) {
                actorSPRecover = (float) 0;
                status[1] = 0;
            }
        }
        //速度提升
        if (status[2] == 1) {
            actor.adjustSpeed(2);
            statusTime[2]--;
            if (statusTime[2] <= 0) {
                actor.adjustSpeed(-2);
                status[2] = 0;
            }
        }
        //攻击力提升
        if (status[3] == 1) {
            atk = (float) 1.25;
            statusTime[3]--;
            if (statusTime[3] <= 0) {
                atk = (float) 1;
                status[3] = 0;
            }
        }
        //防御力提升
        if (status[4] == 1) {
            def = (float) 0.25;
            statusTime[4]--;
            if (statusTime[4] <= 0) {
                def = (float) 0;
                status[4] = 0;
            }
        }
        //燃烧
        if (status[5] == 1) {
            if (actorHPF >= actorHPF * 0.002) {
                actorHPF -= actorHPF * 0.002;
            } else {
                actorHPF = 0;
            }
            statusTime[5]--;
            if (statusTime[5] <= 0) {
                status[5] = 0;
            }
        }
        //中毒
        if (status[6] == 1) {
            if (actorHPF >= (actorHPLimit - actorHPF) * 0.002) {
                actorHPF -= (actorHPLimit - actorHPF) * 0.002;
            } else {
                actorHPF = 0;
            }
            statusTime[6]--;
            if (statusTime[6] <= 0) {
                status[6] = 0;
            }
        }
    }
    //角色HP控制
    public void hpControl(int h) {
        if (h > 0){
            if ((actorHPF+h)>=(actorHPLimit+getHPplus())){
                actorHPF = actorHPLimit+getHPplus();
            }else actorHPF += h;
        }else {
            if ((actorHPF-h)<=0){
                actorHPF = 0;
            }else actorHPF -= h;
        }
    }
    //角色SP控制
    public void spControl(int s) {
        if (s > 0){
            if ((actorSP+s)>=(actorSPLimit+getSPplus())){
                actorSP = actorSPLimit+getSPplus();
            }else actorSP += s;
        }else {
            if ((actorSP+s)<=0){
                actorSP = 0;
            }else actorSP += s;
        }
    }
    //角色受伤
    public void actorHit(Bullet b, Actor actor) {
        if (bullets.contains(b)){
            return;
        }
        bullets.add(b);
        if (b.getStatus() == 1){
            this.status[5] = 1;
            this.statusTime[0] = 500;
        }
        if (b.getStatus() == 2){
            this.status[6] = 1;
            this.statusTime[1] = 500;
        }
        if (b.getAtk()*(1-getDEF()) > (actorHPF)) {
            actorHPF  = 0;
        } else actorHPF -= b.getAtk()*(1-getDEF());
        actor.addEffect(b.getEffect());
        if (b.getPenetrate() == 0){
            b.setDeath(true);
        }
    }
    //角色武器相关
    public int getArm(int i) {
        return arms.get(i).getId();//获取已装备的首位武器ID
    }
    //获取武器CD
    public int getATKCD(int i) {
        return (int) arms.get(i).getCd();
    }
    //设置武器CD
    public void setATKCD(int i) {
        arms.get(i).setCd(0);
        arms.get(i).setCdLimit(getATKCD(i));
    }
    //刷新武器CD
    public void updateATKCD() {
        for (int i = 0; i < arms.size(); i++) {
            if (arms.get(i).getCdLimit() > arms.get(i).getCd()){
                arms.get(i).adjustCd(1);
            }
        }
    }
    //检查武器CD
    public boolean checkATKCD(int i) {
        if (arms.get(i).getCdLimit() == arms.get(i).getCd()){
            return true;
        }return false;
    }
    //获取子弹ID
    public int getBullet(int i) {
        return arms.get(i).getBulletID();
    }
    //切换武器
    public void changeArm() {
        if (!arms.isEmpty()){
            armRepeater = arms.get(0);
            for (int i = 0; i < arms.size() - 1; i++) {
                arms.set(i, arms.get(i + 1));
            }
            arms.set(arms.size() - 1, armRepeater);
        }
    }
    //获得武器
    public void getArm(Arm arm) {
        if (arms.size() == 1||arms.size() == 2){
            arms.add(0,arm);
        }else {
            arms.set(0,arm);
        }
    }
    //角色装备相关
    public int getHat() {
        return hat;//获取已装备的防具ID
    }

    public void setHat(int hat){
        this.hat = hat;
    }

    public int getEquip() {
        return equip;//获取已装备的防具ID
    }

    public void setEquip(int equip) {
        this.equip = equip;
    }

    public float getHPplus() {
        return equipList[getEquip()][0]+hatList[getHat()][0];
    }

    public float getSPplus() {
        return equipList[getEquip()][1]+hatList[getHat()][1];
    }

    public float getATKplus() {
        return equipList[getEquip()][2]+hatList[getHat()][2];
    }

    public float getDEFplus() {
        return equipList[getEquip()][3]+hatList[getHat()][3];
    }

    public String getHatName() {
        String name = "";
        switch (getHat()) {
            case 0:
                name = "无";
                break;
            case 1:
                name = "草帽";
                break;
            case 2:
                name = "士兵头盔";
                break;
            case 3:
                name = "法师帽";
                break;
            case 4:
                name = "骑士头盔";
                break;
            case 6:
                name = "光明法帽";
                break;
        }
        return name;
    }

    public String getEquipName() {
        String name = "";
        switch (getEquip()) {
            case 0:
                name = "无";
                break;
            case 1:
                name = "布衣";
                break;
            case 2:
                name = "士兵铠甲";
                break;
            case 3:
                name = "学士法袍";
                break;
            case 4:
                name = "骑士铠甲";
                break;
            case 5:
                name = "光明法袍";
                break;
        }
        return name;
    }

    public String getHatEffect() {
        String effect = "";
        switch (getHat()) {
            case 0:
                effect = " ";
                break;
            case 1:
                effect = "HP:40 DEF:0.05";
                break;
            case 2:
                effect = "HP:80 DEF:0.1";
                break;
            case 3:
                effect = "HP:40 SP:100 DEF:0.05";
                break;
            case 4:
                effect = "HP:400 DEF:0.25";
                break;
            case 5:
                effect = "HP:240 SP:500 DEF:0.15";
                break;
        }
        return effect;
    }

    public String getEquipEffect() {
        String effect = "";
        switch (getEquip()) {
            case 0:
                effect = " ";
                break;
            case 1:
                effect = "HP:50 DEF:0.05";
                break;
            case 2:
                effect = "HP:100 DEF:0.1";
                break;
            case 3:
                effect = "HP:50 SP:100 DEF:0.05";
                break;
            case 4:
                effect = "HP:500 DEF:0.25";
                break;
            case 5:
                effect = "HP:300 SP:500 DEF:0.15";
                break;
        }
        return effect;
    }

    public String getHatInfo() {
        String info = "";
        switch (getHat()) {
            case 0:
                info = " ";
                break;
            case 1:
                info = "席草编制的帽子，可以遮雨。";
                break;
            case 2:
                info = "帝国制式的士兵头盔。";
                break;
            case 3:
                info = "魔法学院学士学位统一配发的法帽。";
                break;
            case 4:
                info = "成为帝国骑士的军人会继承前辈的头盔。";
                break;
            case 5:
                info = "光明神殿的魔法师的法帽十分昂贵。";
                break;
        }
        return info;
    }

    public String getEquipInfo() {
        String info = "";
        switch (getEquip()) {
            case 0:
                info = " ";
                break;
            case 1:
                info = "麻布制成的衣服，穿起来十分凉爽。";
                break;
            case 2:
                info = "帝国制式的士兵铠甲。";
                break;
            case 3:
                info = "魔法学院学士学位统一配发的法袍。";
                break;
            case 4:
                info = "成为帝国骑士的军人会继承前辈的铠甲。";
                break;
            case 5:
                info = "光明神殿的魔法师的法袍十分昂贵。";
                break;
        }
        return info;
    }

    public Image getHatimg() {
        Image img = (new ImageIcon("src/image/Item/hat/hat"+getHat()+".png")).getImage();
        return img;
    }

    public Image getEquipimg() {
        Image img = (new ImageIcon("src/image/Item/equip/equip"+getEquip()+".png")).getImage();
        return img;
    }

    public boolean getUseful(int i) {
        if (items.get(i).getType() == 0) {
            return true;
        }
        return false;
    }
    //道具相关
    public void useItem(int i) {
        if (!items.isEmpty() && getUseful(i)) {
            items.get(i).useEffect(items.get(i).getId(),this);
            items.get(i).adjustNumber(-1);
            if (items.get(i).getNumber() == 0) {
                items.remove(i);
                items.trimToSize();
            }
        }
    }
    //切换物品
    public void changeItem() {
        if (!items.isEmpty()){
            itemRepeater = items.get(0);
            for (int i = 0; i < items.size() - 1; i++) {
                items.set(i, items.get(i + 1));
            }
            items.set(items.size() - 1, itemRepeater);
        }
    }
    //获得物品
    public void getItem(Item item,int num) {
        int sum = 0;
        if (!items.isEmpty()){
            for (int j = 0; j < items.size(); j++) {
                if (item.getId() == items.get(j).getId()){
                    items.get(j).adjustNumber(num);
                    sum++;
                }
            }
            if (sum==0){
                items.add(item);
            }
        }else {
            items.add(item);
            items.get(0).adjustNumber(num-1);
        }
    }
    //更新数据
    public void update() {
        updateHP();
        updateSP();
        updateATKCD();
    }
}
