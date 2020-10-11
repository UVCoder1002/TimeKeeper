package src.Timer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class My_Timer  {
    private JPanel panel1;
    private JTextField hr;
    private JTextField min;
    private JTextField sec;
    private JButton startBT;
    Thread t1=null;


    public JTextField getHr() {
        return hr;
    }

    public JTextField getMin() {
        return min;
    }

    public JTextField getSec() {
        return sec;
    }
    public void setCountdown(int hr, int min, int sec) {
        this.hr.setText(""+hr);
        this.min.setText(""+min);
        this.sec.setText(""+sec);
    }

    public My_Timer() {
        TimerTimer tt = new TimerTimer(this);
        t1 = new Thread(tt);
        startBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 if(!t1.isAlive()) {
                     t1.start();
                 }
            }

        });

    }
    /*@Override
    public void run() {
        int i=60;
        while (i>-1) {
            try {
                                    *//*UpdateTime(i);*//*
                    sec.setText(""+i--);
                    Thread.sleep(1000);

                //t1=null;
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }*/

    public static void main(String[] args) {
        My_Timer tCD = new My_Timer();
        //tCD.setLayout(new GridLayout());
        JFrame frame = new JFrame("Time Keeper");
        frame.setContentPane(new My_Timer().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 300);
        frame.setLocation(250,100);
        //frame.add(tCD);
        frame.setVisible(true);
        //tCD.start();
    }


}
