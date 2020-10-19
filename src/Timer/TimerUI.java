package Timer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;

public class TimerUI extends JPanel  {
    private JPanel panel1;
    private JTextField hr;
    private JTextField min;
    private JTextField sec;
    private JButton startBT;
    private JButton pauseBT;
    private JButton resetBT;
    //TimerUI timer;
    int flag=1;
    int temp=1;
    TimerListener listener;

    public Integer getHr() {
        return Integer.parseInt(hr.getText());
    }

    public Integer getMin() {
        return Integer.parseInt(min.getText());
    }

    public Integer getSec() {
        return Integer.parseInt(sec.getText());
    }

    public void setTime(int hr, int min, int sec) {
        this.hr.setText(""+hr);
        this.min.setText(""+min);
        this.sec.setText(""+sec);
    }

    public TimerUI() {

        TimerBackEnd tt = new TimerBackEnd();
        Thread t1 = new Thread(tt);
        t1.start();
        //timer=this;


        startBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("start");
                int min=getMin();
                int sec=getMin();
                if (min>=0&&min<60&&sec>=0&&sec<60) {
                    tt.addTimeListener(listener = new TimerListener(getHr(), getMin(), getSec()) {
                        @Override
                        public void updateTimer(int hr, int min, int sec) {
                            setTime(hr, min, sec);
                        }
                    });
                    flag = 0;
                }
                else {
                    tt.removeListener(listener);
                    setTime(0,0,0);
                    flag=1;
                }

            /*
                int min=Integer.parseInt(getMin().getText());
                int sec=Integer.parseInt(getSec().getText());
                if (min>=0&&min<60&&sec>=0&&sec<60) {
                    *//*if(t1.getState())
                    { t1.resume(); }*//*
                    if (!t1.isAlive()) {
                        System.out.println("New Thread");
                        t1.start();
                        flag=0;
                }
            } else
                setTime(0,0,0);*/
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
                tt.removeListener(listener);
                setTime(0,0,0);
                flag=1;
            }
        });
    }


    public static void main(String[] args) {
        TimerUI tCD = new TimerUI();
        //tCD.setLayout(new GridLayout());
        JFrame frame = new JFrame("Time Keeper");
        frame.setContentPane(new TimerUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 300);
        frame.setLocation(250,100);
        //frame.add(tCD);
        frame.setVisible(true);
        //tCD.start();
    }
}
