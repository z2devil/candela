import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Menu{
    private Data data;
    private int x,y;
    private boolean visible;
    private int hat;
    private int equip;
    private ArrayList<Arm> arms = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private Image menuBackImage = new ImageIcon("src/image/UI/menu_back.png").getImage();
    private Image circleSImage = new ImageIcon("src/image/UI/circle_s.png").getImage();
    private Image circleXImage = new ImageIcon("src/image/UI/circle_x.png").getImage();
    public Menu(Data data){
        equip = data.getEquip();
        arms = data.arms;
        items = data.items;
        hat = data.getHat();
        this.data = data;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void draw(Graphics g) {
        Font nameFont = new Font("黑体", Font.PLAIN, 18);
        Font effectFont = new Font("宋体", Font.PLAIN, 12);
        Font infoFont = new Font("宋体", Font.PLAIN, 12);
        g.drawImage(menuBackImage,0,0,null);
        //显示装备(L侧)
        if (arms.size()+2 == 3){
            g.drawImage(circleXImage,386,219,null);
            g.drawImage(circleXImage,339,312,null);
            g.drawImage(circleXImage,386,405,null);
            g.drawImage(arms.get(0).getImage(),386+23,219+23,48,48,null);
            g.drawImage(data.getHatimg(),339+23,312+23,48,48,null);
            g.drawImage(data.getEquipimg(),386+23,405+23,48,48,null);
            g.setFont(nameFont);
            g.setColor(Color.GREEN);
            g.drawString(arms.get(0).getName(),386-arms.get(0).getName().length()*18,219+40);
            g.drawString(data.getHatName(),339-data.getHatName().length()*18,312+40);
            g.drawString(data.getEquipName(),386-data.getEquipName().length()*18,405+40);
            g.setFont(effectFont);
            g.setColor(Color.CYAN);
            g.drawString(arms.get(0).getEffect(),386-arms.get(0).getName().length()*18-arms.get(0).getEffect().length()*12/2,219+40);
            g.drawString(data.getHatEffect(),339-data.getHatName().length()*18-data.getHatEffect().length()*12/2,312+40);
            g.drawString(data.getEquipEffect(),386-data.getEquipName().length()*18-data.getEquipEffect().length()*12/2,405+40);
            g.setFont(infoFont);
            g.setColor(Color.white);
            g.drawString(arms.get(0).getInfo(),386-arms.get(0).getInfo().length()*12,219+60);
            g.drawString(data.getHatInfo(),339-data.getHatInfo().length()*12,312+60);
            g.drawString(data.getEquipInfo(),386-data.getEquipInfo().length()*12,405+60);
        }
        if (arms.size()+2 == 4){
            for (int i = 0; i < arms.size(); i++) {
                g.drawImage(circleXImage,339+(i*93/2)+47-24,312-(i*93)-47,null);
                g.drawImage(arms.get(i).getImage(),339+(i*93/2)+47-24+23,312-(i*93)-47+23,48,48,null);
                g.setFont(nameFont);
                g.setColor(Color.GREEN);
                g.drawString(arms.get(i).getName(),339+(i*93/2)+47-24-arms.get(i).getName().length()*18,312-(i*93)-47+40);
                g.setFont(effectFont);
                g.setColor(Color.CYAN);
                g.drawString(arms.get(i).getEffect(),339+(i*93/2)+47-24-arms.get(i).getName().length()*18-arms.get(i).getEffect().length()*12/2,312-(i*93)-47+40);
                g.setFont(infoFont);
                g.setColor(Color.white);
                g.drawString(arms.get(i).getInfo(),339+(i*93/2)+47-24-arms.get(i).getInfo().length()*12,312-(i*93)-47+60);
            }
            for (int i = 1; i < 3; i++) {
                g.drawImage(circleXImage,339+(i*93/2)-47+24,312+(i*93)-47,null);
                g.drawImage(data.getHatimg(),386,382,48,48,null);
                g.drawImage(data.getEquipimg(),433,474,48,48,null);
                g.setFont(nameFont);
                g.setColor(Color.GREEN);
                g.drawString(data.getHatName(),339+24-data.getHatName().length()*18,399);
                g.drawString(data.getEquipName(),409-data.getEquipName().length()*18,491);
                g.setFont(effectFont);
                g.setColor(Color.CYAN);
                g.drawString(data.getHatEffect(),363-data.getHatName().length()*18-data.getHatEffect().length()*12/2,398);
                g.drawString(data.getEquipEffect(),339+47+24-data.getEquipName().length()*18-data.getEquipEffect().length()*12/2,491);
                g.setFont(infoFont);
                g.setColor(Color.white);
                g.drawString(data.getHatInfo(),364-data.getHatInfo().length()*12,418);
                g.drawString(data.getEquipInfo(),410-data.getEquipInfo().length()*12,511);
            }
        }
        if (arms.size()+2 == 5){
            g.drawImage(circleXImage,339,312,null);
            for (int i = 0; i < arms.size(); i++) {
                g.drawImage(circleXImage,339+(i*93/2),312-(i*93),null);
                g.drawImage(arms.get(i).getImage(),339+(i*93/2)+23,312-(i*93)+23,48,48,null);
                g.setFont(nameFont);
                g.setColor(Color.GREEN);
                g.drawString(arms.get(i).getName(),339+(i*93/2)-arms.get(i).getName().length()*18,312-(i*93)+40);
                g.setFont(effectFont);
                g.setColor(Color.CYAN);
                g.drawString(arms.get(i).getEffect(),339+(i*93/2)-arms.get(i).getName().length()*18-arms.get(i).getEffect().length()*12/2,312-(i*93)+40);
                g.setFont(infoFont);
                g.setColor(Color.white);
                g.drawString(arms.get(i).getInfo(),339+(i*93/2)-arms.get(i).getInfo().length()*12,312-(i*93)+60);
            }
            for (int i = 1; i < 3; i++) {
                g.drawImage(circleXImage,339+(i*93/2),312+(i*93),null);
            }
            g.drawImage(data.getHatimg(),409,428,48,48,null);
            g.drawImage(data.getEquipimg(),339+(2*93/2)+23,312+(2*93)+23,48,48,null);
            g.setFont(nameFont);
            g.setColor(Color.GREEN);
            g.drawString(data.getHatName(),339+47-data.getHatName().length()*18,312+93+40);
            g.drawString(data.getEquipName(),339+(2*93/2)-data.getEquipName().length()*18,312+(2*93)+40);
            g.setFont(effectFont);
            g.setColor(Color.CYAN);
            g.drawString(data.getHatEffect(),339+47-data.getHatName().length()*18-data.getHatEffect().length()*12/2,312+93+40);
            g.drawString(data.getEquipEffect(),339+(2*93/2)-data.getEquipName().length()*18-data.getEquipEffect().length()*12/2,312+(2*93)+40);
            g.setFont(infoFont);
            g.setColor(Color.white);
            g.drawString(data.getHatInfo(),339+47-data.getHatInfo().length()*12,312+93+60);
            g.drawString(data.getEquipInfo(),339+(2*93/2)-data.getEquipInfo().length()*12,312+(2*93)+60);
        }
        //显示物品(R侧)
        if (items.size()%2 == 0){
            for (int i = 0; i < items.size(); i++) {
                if (i%2 == 0){
                    g.drawImage(circleSImage,873-((i/2)*52/2),362+((i/2)*52),null);
                    g.drawImage(items.get(i).getImage(),873-((i/2)*52/2)+14,362+((i/2)*52)+14,null);
                    g.setFont(nameFont);
                    g.setColor(Color.GREEN);
                    g.drawString(items.get(i).getName(),873-((i/2)*52/2)+14+40,362+((i/2)*52)+14+10);
                    g.setFont(effectFont);
                    g.setColor(Color.CYAN);
                    g.drawString(items.get(i).getEffect(),873-((i/2)*52/2)+14+40+items.get(i).getName().length()*18,362+((i/2)*52)+14+10);
                    g.setFont(infoFont);
                    g.setColor(Color.white);
                    g.drawString(items.get(i).getInfo(),873-((i/2)*52/2)+14+40,362+((i/2)*52)+14+25);
                }else {
                    g.drawImage(circleSImage,873-((i/2)*52/2),306-((i/2)*52),null);
                    g.drawImage(items.get(i).getImage(),873-((i/2)*52/2)+14,306-((i/2)*52)+14,null);
                    g.setFont(nameFont);
                    g.setColor(Color.GREEN);
                    g.drawString(items.get(i).getName(),873-((i/2)*52/2)+14+40,306-((i/2)*52)+14+10);
                    g.setFont(effectFont);
                    g.setColor(Color.CYAN);
                    g.drawString(items.get(i).getEffect(),873-((i/2)*52/2)+14+40+items.get(i).getName().length()*18,306-((i/2)*52)+14+10);
                    g.setFont(infoFont);
                    g.setColor(Color.white);
                    g.drawString(items.get(i).getInfo(),873-((i/2)*52/2)+14+40,306-((i/2)*52)+14+25);
                }
            }
        }else {
            g.drawImage(circleSImage,873,334,null);
            g.drawImage(items.get(0).getImage(),873+14,334+14,null);
            g.setFont(nameFont);
            g.setColor(Color.GREEN);
            g.drawString(items.get(0).getName(),873+14+40,334+14+10);
            g.setFont(effectFont);
            g.setColor(Color.CYAN);
            g.drawString(items.get(0).getEffect(),873+14+40+items.get(0).getName().length()*18,334+14+10);
            g.setFont(infoFont);
            g.setColor(Color.white);
            g.drawString(items.get(0).getInfo(),873+14+40,334+14+25);
            for (int i = 1; i < items.size(); i++) {
                if (i%2 == 0){
                    g.drawImage(circleSImage,873-((i/2)*52/2),334-((i/2)*52),null);
                    g.drawImage(items.get(i).getImage(),873-((i/2)*52/2)+14,334-((i/2)*52)+14,null);
                    g.setFont(nameFont);
                    g.setColor(Color.GREEN);
                    g.drawString(items.get(i).getName(),873-((i/2)*52/2)+14+40,334-((i/2)*52)+14+10);
                    g.setFont(effectFont);
                    g.setColor(Color.CYAN);
                    g.drawString(items.get(i).getEffect(),873-((i/2)*52/2)+14+40+items.get(i).getName().length()*18,334-((i/2)*52)+14+10);
                    g.setFont(infoFont);
                    g.setColor(Color.white);
                    g.drawString(items.get(i).getInfo(),873-((i/2)*52/2)+14+40,334-((i/2)*52)+14+25);
                }else {
                    g.drawImage(circleSImage,873-((i/2+1)*52/2),334+((i/2+1)*52),null);
                    g.drawImage(items.get(i).getImage(),873-((i/2+1)*52/2)+14,334+((i/2+1)*52)+14,null);
                    g.setFont(nameFont);
                    g.setColor(Color.GREEN);
                    g.drawString(items.get(i).getName(),873-((i/2+1)*52/2)+14+40,334+((i/2+1)*52)+14+10);
                    g.setFont(effectFont);
                    g.setColor(Color.CYAN);
                    g.drawString(items.get(i).getEffect(),873-((i/2+1)*52/2)+14+40+items.get(i).getName().length()*18,334+((i/2+1)*52)+14+10);
                    g.setFont(infoFont);
                    g.setColor(Color.white);
                    g.drawString(items.get(i).getInfo(),873-((i/2+1)*52/2)+14+40,334+((i/2+1)*52)+14+25);
                }
            }
        }
    }
}