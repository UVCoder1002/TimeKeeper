package Timer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class My_Timer  {
    private JPanel panel1;
    private JTextField hr;
    private JTextField min;
    private JTextField sec;
    private JButton startBT;
    private JButton pauseBT;
    private JButton resetBT;
    //My_Timer timer;
    int flag=1;


    public JTextField getHr() {
        return hr;
    }

    public JTextField getMin() {
        return min;
    }

    public JTextField getSec() {
        return sec;
    }
    public void setTime(int hr, int min, int sec) {
        this.hr.setText(""+hr);
        this.min.setText(""+min);
        this.sec.setText(""+sec);
    }

    public My_Timer() {

        TimerTimer tt = new TimerTimer(this);
        Thread t1 =new Thread(tt);
        //timer=this;

        startBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int min=Integer.parseInt(getMin().getText());
                int sec=Integer.parseInt(getSec().getText());
                if (min>=0&&min<60&&sec>=0&&sec<60) {
                    /*if(t1.getState())
                    { t1.resume(); }*/
                    if (!t1.isAlive()) {
                        System.out.println("New Thread");
                        /*TimerTimer tt = new TimerTimer(timer);
                        Thread t1 = new Thread(tt);
                        try {
                            t1.join();
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }*/
                        t1.start();
                        flag=0;
                }
            } else
                setTime(0,0,0);
            }

        });

        pauseBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(flag==0) {
                    t1.suspend();
                    pauseBT.setText("Resume");
                    flag=1;
                }
                else{
                    t1.resume();
                    pauseBT.setText("Pause");
                    flag=0;
                }

            }
        });
        resetBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                t1.stop();
                setTime(0,0,0);
            }
        });
    }

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
