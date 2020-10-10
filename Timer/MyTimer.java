package Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;

public class MyTimer extends JPanel implements Runnable {
    private JPanel timerPanel;
    //private JLabel sec;
    Label sec= new Label("60");
    Thread t1 = null;
    int second;

    public void UpdateTime(int sec) {
        // hour=hr;
        // minute=min;
        second = sec;
    }

    public MyTimer() {
        
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
                for (int i = 60; i >= 0; i--) {

                    UpdateTime(i);
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
        MyTimer tCD = new MyTimer();
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
