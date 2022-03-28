import javax.swing.*;
import java.awt.*;

public class UI {
    private int x = 0;
    private int y = 0;
    private boolean visible = true;
    private Image UIBlackimg = (new ImageIcon("src/image/UI/UI_black.png")).getImage();
    private Image UIBackimg = (new ImageIcon("src/image/UI/UI_back.png")).getImage();
    private Image ItemBackimg = (new ImageIcon("src/image/UI/item_back.png")).getImage();
    private Image CoinBackimg = (new ImageIcon("src/image/UI/coin_back.png")).getImage();
    private Image HPTimg = (new ImageIcon("src/image/UI/HPT.png")).getImage();
    private Image HPFimg = (new ImageIcon("src/image/UI/HPF.png")).getImage();
    private Image SPimg = (new ImageIcon("src/image/UI/SP.png")).getImage();
    private Image UITopimg = (new ImageIcon("src/image/UI/UI_top.png")).getImage();
    private Image UIMarkimg = (new ImageIcon("src/image/UI/mark.png")).getImage();
    private Image UICDimg = (new ImageIcon("src/image/UI/red.png")).getImage();
    private Image UICDCoverimg = (new ImageIcon("src/image/UI/atk_cover.png")).getImage();
    private Image UICoinimg = (new ImageIcon("src/image/Item/prop/coin.png")).getImage();
    private GameFrame gf;

    public UI(GameFrame gf) {
        this.gf = gf;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void draw(Graphics g) {
        if (visible){
            //定义HP数值字体
            Font HPfont = new Font("宋体", Font.PLAIN, 24);
            //定义SP数值字体
            Font SPfont = new Font("宋体", Font.PLAIN, 16);
            //绘制HP图像
            g.drawImage(UIBlackimg, x, y, null);
            g.drawImage(HPTimg, x + 40, y + 18, (int) (300 * (gf.actorData.getActorHPT() / gf.actorData.getActorHPLimit())), 30, null);
            g.drawImage(CoinBackimg, x + 1135, y + 647, null);
            g.drawImage(UICDimg, x + 30, y + 659 - (int) (54 * gf.actorData.arms.get(0).getCd() / gf.actorData.arms.get(0).getCdLimit()), 54, (int) (54 * gf.actorData.arms.get(0).getCd() / gf.actorData.arms.get(0).getCdLimit()), null);
            g.drawImage(UICDCoverimg, x + 15, y + 485 + 100, null);
            g.drawImage(ItemBackimg, x + 15, y + 485, null);
            g.drawImage(HPFimg, x + 40, y + 18, (int) (300 * (gf.actorData.getActorHPF() / gf.actorData.getActorHPLimit())), 30, null);
            g.drawImage(HPTimg, x + 40, y + 18, (int) (300 * (gf.actorData.getActorHPT() / gf.actorData.getActorHPLimit())), 30, null);
            //绘制HP数值
            g.setFont(HPfont);
            g.setColor(Color.white);
            g.drawString("" + (int) gf.actorData.getActorHPLimit() + "/" + (int) gf.actorData.getActorHPT(), 51, 42);
            //绘制SP图像
            g.drawImage(SPimg, x + 39, y + 66, (int) (300 * (gf.actorData.getActorSP() / gf.actorData.getActorSPLimit())), 10, null);
            //绘制SP数值
            g.setFont(SPfont);
            g.setColor(Color.white);
            g.drawString("" + (int) gf.actorData.getActorSPLimit() + "/" + (int) gf.actorData.getActorSP(), 51, 75);
            g.drawImage(UITopimg, x + 9, y + 5, null);
            g.drawImage(UIMarkimg, x + 39 + (int) (300 * (gf.actorData.getActorHPF() / gf.actorData.getActorHPLimit())), y + 18, null);
            g.drawImage(UIMarkimg, x + 39 + (int) (300 * (gf.actorData.getActorHPT() / gf.actorData.getActorHPLimit())), y + 18, null);
            //绘制金币图像和数值
            g.drawImage(UICoinimg, x + 1141, y + 650, 32, 32, null);
            g.drawString("" + gf.actorData.getMoney(), x + 1180, x + 673);
            //显示武器
            for (int i = 0; i < gf.actorData.arms.size(); i++) {
                Image img = null;
                if (i == 0) {
                    g.drawImage(gf.actorData.arms.get(i).getImage(), 35, 605, 48, 48, null);
                } else {
                    g.drawImage(gf.actorData.arms.get(i).getImage(), 32, 600 - i * 50, 24, 24, null);
                }
            }
            //显示道具
            int s = gf.actorData.items.size();
            if (gf.actorData.items.size() > 4) {
                s = 4;
            }
            for (int i = 0; i < s; i++) {
                Image img = null;
                if (i == 0) {
                    g.drawImage(gf.actorData.items.get(i).getImage(), 121, 637, 32, 32, null);
                    g.drawString("" + gf.actorData.items.get(i).getNumber(), 144, 667);
                } else {
                    g.drawImage(gf.actorData.items.get(i).getImage(), 133 + i * 40, 645, 24, 24, null);
                    g.drawString("" + gf.actorData.items.get(i).getNumber(), 145 + i * 40, 667);
                }
            }
        }
    }
}
