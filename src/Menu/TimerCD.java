package src.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;

public class TimerCD extends JPanel implements Runnable {
    int second, minute, hour;
    private JButton startBT;
    private JPanel timerPanel;
    private JLabel timerLable;
    private JButton resetBT;
    private JButton pauseBT;
    Thread t1 = null;

    public void UpdateTime(int sec) {
        // hour=hr;
        // minute=min;
        second = sec;
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //to improve graphics
        g2d.translate(this.getWidth() / 2, this.getHeight() / 2);
        g2d.rotate(Math.toRadians(270));
        Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
        Arc2D.Float arc2 = new Arc2D.Float(Arc2D.PIE);
        Arc2D.Float arc3 = new Arc2D.Float(Arc2D.PIE);
        Ellipse2D circle = new Ellipse2D.Float(0, 0, 110, 110);
        arc.setFrameFromCenter(new Point(0, 0), new Point(120, 120));
        circle.setFrameFromCenter(new Point(0, 0), new Point(110, 110));
        arc.setAngleStart(1);
        arc.setAngleExtent(-second * 6); //360'/60sec=6
        g2d.setColor(Color.MAGENTA);
        g2d.draw(arc);
        g2d.fill(arc);
        /*arc2.setFrameFromCenter(new Point(0,0), new Point(100,100));
        arc2.setAngleStart(1);
        arc2.setAngleExtent(-minute*6); //360'/60sec=6
        g2d.setColor(Color.PINK);
        g2d.draw(arc2);
        g2d.fill(arc2);
        arc3.setFrameFromCenter(new Point(0,0), new Point(80,80));
        arc3.setAngleStart(1);
        arc3.setAngleExtent(-hour*15); //360'/24sec=15
        g2d.setColor(Color.YELLOW);
        g2d.draw(arc3);
        g2d.fill(arc3);*/
        g2d.setColor(Color.DARK_GRAY);
        g2d.draw(circle);
        g2d.fill(circle);
    }

    public TimerCD() {

//
//        startBT.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                //startBT.getDisabledIcon();
//
//            }
//        });

    }

    public void start() {
        if (t1 == null) {
            t1 = new Thread();
            t1.start();
        }
    }
    @Override
    public void run() {
        while (t1 != null) {
            try {
                for (int i = 0; i <= 60; i++) {

                    UpdateTime(i);
                    repaint();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();

                // System.out.println("repaint");
            }

            //repaint();
        }
    }

    public static void main(String[] args) {
        TimerCD tCD = new TimerCD();
        JFrame frame = new JFrame("Time Keeper");
        //frame.setContentPane(new Timer().timerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 400);
        frame.setLocation(250,100);
        frame.add(tCD);
        frame.setVisible(true);
        tCD.start();

    }
}
