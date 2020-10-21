package Stopwatch;

import Manager.StopwatchListener;
import Manager.TimeListener;
import Manager.TimeManager;

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
    private JTextField millisec;
    //private JTextArea milTxt;
    int noOfClickLap=0;
    int flag = 1;
    TimeManager timeManager;
    TimeListener listener;

    public Integer getHr() {
        return Integer.parseInt(hrTxt.getText());
    }

    public Integer getMin() {
        return Integer.parseInt(minTxt.getText());
    }

    public Integer getSec() {
        return Integer.parseInt(secTxt.getText());
    }
    public Integer getmilli(){
        return Integer.parseInt(millisec.getText());
    }
    /*public JTextArea getMil() {
        return milTxt;
    }*/
    public void setTimer(int hr, int min, int sec) {
        hrTxt.setText(""+hr);
        minTxt.setText(""+min);
        secTxt.setText(""+sec);

    }
    public void setmilli(int milli){
        millisec.setText(""+milli);
    }


    public StopwatchUI(TimeManager tm) {
        timeManager = tm;

        startBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startBT.setEnabled(false);
                timeManager.addTimeListener(listener = new StopwatchListener() {

                    @Override
                    public void timeUpdated(int hr, int min, int sec) {
                        setTimer(hr,min,sec);
                    }


                    @Override
                    public void timeUpdated(int milli) {
                      setmilli(milli);
                    }

                });
                flag = 0;
            }
        });
        pauseBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(flag==0) {
                   listener.ispaused=true;
                    pauseBT.setText("Resume");
                    flag=1;
                }
                else{
                    listener.ispaused=false;
                    pauseBT.setText("Pause");
                    flag=0;
                }
            }
        });
        lapBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noOfClickLap++;
                lapOutput.append(noOfClickLap+". "+getHr()+":"+getMin()+":"+getSec()+"."+getmilli()+"\n");
            }
        });
        resetBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeManager.removeListener(listener);
                lapOutput.setText("");
                noOfClickLap=0;
                flag=1;
                setTimer(0,0,0);
                setmilli(0);
                startBT.setEnabled(true);
            }
        });
    }



    public static void main(String[] args) {
        TimeManager timeManager = new TimeManager();
        StopwatchUI tCD = new StopwatchUI(timeManager);
        //tCD.setLayout(new GridLayout());
        JFrame frame = new JFrame("Time Keeper");
        frame.setContentPane(tCD.stopwatchJp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(750, 400);
        frame.setLocation(250,100);
        //frame.add(tCD);
        frame.setVisible(true);
        //tCD.start();
    }
}
