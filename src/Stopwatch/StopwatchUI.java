package Stopwatch;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopwatchUI extends JPanel{
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
    StopwatchListener listener;

    public Integer getHr() {
        return Integer.parseInt(hrTxt.getText());
    }

    public Integer getMin() {
        return Integer.parseInt(minTxt.getText());
    }

    public Integer getSec() {
        return Integer.parseInt(secTxt.getText());
    }
    /*public JTextArea getMil() {
        return milTxt;
    }*/
    public void setTimer(int hr, int min, int sec) {
        hrTxt.setText(""+hr);
        minTxt.setText(""+min);
        secTxt.setText(""+sec);
    }


    public StopwatchUI() {
        StopwatchBackEnd sw = new StopwatchBackEnd();
        Thread t1 =new Thread(sw);
        t1.start();

        startBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startBT.setEnabled(false);
                sw.addStopwatchListener(listener = new StopwatchListener() {
                    @Override
                    public void updateTime(int hr,int min,int sec) {
                        setTimer(hr,min,sec);
                    }
                });
                flag = 0;
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
                lapOutput.append(noOfClickLap+". "+getHr()+":"+getMin()+":"+getSec()+"\n");
            }
        });
        resetBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sw.removeListener(listener);
                lapOutput.setText("");
                noOfClickLap=0;
                flag=1;
                setTimer(0,0,0);
                startBT.setEnabled(true);
            }
        });
    }



    public static void main(String[] args) {
        StopwatchUI tCD = new StopwatchUI();
        //tCD.setLayout(new GridLayout());
        JFrame frame = new JFrame("Time Keeper");
        frame.setContentPane(new StopwatchUI().stopwatchJp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(750, 400);
        frame.setLocation(250,100);
        //frame.add(tCD);
        frame.setVisible(true);
        //tCD.start();
    }
}
