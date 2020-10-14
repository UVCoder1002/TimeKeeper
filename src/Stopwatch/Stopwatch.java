package Stopwatch;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stopwatch extends JPanel{
    private JTextArea hrTxt;
    private JTextArea minTxt;
    private JTextArea secTxt;
    private JButton startBT;
    private JButton pauseBT;
    private JButton resetBT;
    private JButton lapBT;
    private JButton Back;
    private JTextArea lapOutput;
    private JLabel hrL;
    private JLabel minL;
    private JLabel secL;
    private JPanel stopwatchJp;
    //private JTextArea milTxt;
    int noOfClickLap=0;
    int flag = 1;

    public JTextArea getHr() {
        return hrTxt;
    }

    public JTextArea getMin() {
        return minTxt;
    }

    public JTextArea getSec() {
        return secTxt;
    }
    /*public JTextArea getMil() {
        return milTxt;
    }*/


    public Stopwatch() {
        StopwatchTimer swTimer = new StopwatchTimer(this);
        Thread t1 =new Thread(swTimer);
        startBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!t1.isAlive()) {
                    t1.start();
                    flag = 0;
                }
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
        lapBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noOfClickLap++;
                lapOutput.append(noOfClickLap+". "+getHr().getText()+" : "+getMin().getText()+" : "+getSec().getText()+"\n");
            }
        });
        resetBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t1.stop();
                lapOutput.setText("");
                noOfClickLap=0;
                setTimer(0,0,0);
            }
        });
    }

    public void setTimer(int hr, int min, int sec) {
    hrTxt.setText(""+hr);
    minTxt.setText(""+min);
    secTxt.setText(""+sec);
    }

    public static void main(String[] args) {
        Stopwatch tCD = new Stopwatch();
        //tCD.setLayout(new GridLayout());
        JFrame frame = new JFrame("Time Keeper");
        frame.setContentPane(new Stopwatch().stopwatchJp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(750, 300);
        frame.setLocation(250,100);
        //frame.add(tCD);
        frame.setVisible(true);
        //tCD.start();
    }
}
