import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class StartFrame extends JPanel implements ActionListener {
    private int count = 0;
    private boolean step;
    private JFrame jf;
    private JButton play, exit;
    private Pic crystal1 = new Pic(370, 0, (new ImageIcon("src/image/Title/crystal1.png")).getImage());
    private Pic crystal2 = new Pic(480, 220, (new ImageIcon("src/image/Title/crystal2.png")).getImage());
    private Pic crystal3 = new Pic(330, 30, (new ImageIcon("src/image/Title/crystal3.png")).getImage());
    private Pic crystal4 = new Pic(350, 250, (new ImageIcon("src/image/Title/crystal4.png")).getImage());
    private Image icon = new ImageIcon("src/image/Item/arm/arm5.png").getImage();
    public StartFrame() {
        this.setSize(640, 480);
        jf = new JFrame("Candela");
        play = new JButton("Start");
        play.setFont(new Font("微软雅黑", Font.BOLD, 24));
        play.setForeground(Color.white);
        play.setContentAreaFilled(false);//消除背景
        play.setFocusPainted(false);//消除选择
        play.setBounds(255, 300, 130, 30);
        exit = new JButton("Exit");
        exit.setFont(new Font("微软雅黑", Font.BOLD, 24));
        exit.setForeground(Color.white);
        exit.setContentAreaFilled(false);//消除背景
        exit.setFocusPainted(false);//消除选择
        exit.setBounds(255, 350, 130, 30);
        jf.setSize(640, 450);
        jf.setIconImage(icon);
        jf.setLocationRelativeTo(null);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.add(play);
        jf.add(exit);
        play.addActionListener(this);
        exit.addActionListener(this);
        jf.add(this);
        jf.setVisible(true);
        //窗口线程
        Runnable startThread = () -> {
            while (true) {
                if (count < 50) {
                    count++;
                    step = true;
                } else if (count >= 50) {
                    count++;
                    step = false;
                }
                if (count == 100) {
                    count = 0;
                }
                this.repaint();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread startline = new Thread(startThread);
        startline.start();
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(0.7, 0.7);
        g.drawImage((new ImageIcon("src/image/Title/title_back.png")).getImage(), 0, 0, this);
        crystal1.draw(g);
        crystal2.draw(g);
        crystal3.draw(g);
        crystal4.draw(g);
        if (step) {
            crystal1.y += 0.0005;
            crystal2.y -= 0.001;
            crystal3.y += 0.001;
            crystal4.y -= 0.002;
        }
        if (!step) {
            crystal1.y -= 0.0005;
            crystal2.y += 0.001;
            crystal3.y -= 0.001;
            crystal4.y += 0.002;
        }
        g.drawImage((new ImageIcon("src/image/Title/title.png")).getImage(), 260, 100, this);
        g.drawImage((new ImageIcon("src/image/Title/title_light.png")).getImage(), -50, -50, this);
        play.repaint();
        exit.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == play) {
            jf.dispose();
            //新建游戏窗口
            GameFrame gf = null;
            try {
                gf = new GameFrame();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //新建按键监听器
            KeyListener kl = new KeyListener(gf);
            //按键监听器向游戏窗口注册
            gf.addKeyListener(kl);
        }
        if (e.getSource() == exit) {
            System.exit(0);
        }
    }

    public class Pic {
        float x, y;
        Image img = null;

        Pic(float x, float y, Image img) {
            this.x = x;
            this.y = y;
            this.img = img;
        }

        public void draw(Graphics g) {
            g.drawImage(img, (int) x, (int) y, null);
        }
    }
}
