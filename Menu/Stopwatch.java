package Menu;

import Menu.Countdown;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stopwatch extends JFrame{
    Countdown cd;
    Thread t;
    private JTextArea minutesBT;
    private JTextArea secondsBT;
    private JButton Start;
    private JButton Lap;
    private JButton Back;
    private JButton Pause;
    private JTextArea hoursBT;
    private JTextArea LapOutput;
    private JLabel hr;
    private JLabel min;
    private JLabel sec;
    JPanel stopwatchjp;
    JFrame swjf= new JFrame("Time Keeper");

    public Stopwatch() {
        setTitle("Time Kepper");
        setSize(400, 500);
        cd = new Countdown(this); //object of stopwatch
        t = new Thread(cd,"time1");
        swjf.setContentPane(new Stopwatch().stopwatchjp);
        swjf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        swjf.pack();
        swjf.setVisible(true);
        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t.start();
            }
        });
    }

    public void setCountdown(int hr, int min, int sec) {
    hoursBT.setText(""+hr);
    minutesBT.setText(""+min);
    secondsBT.setText(""+sec);
    }
}
