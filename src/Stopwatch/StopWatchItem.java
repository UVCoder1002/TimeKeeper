package Stopwatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopWatchItem extends JPanel{
    JLabel hr;
    JLabel min;
    JLabel sec;
    JLabel milli;
    JButton delete;
    JButton pause;
    JButton lap;
    StopWatchItem stopWatchItem;
    StopWatchItem(StopWatch stopWatch,StopwatchUI stopwatchUI){

        stopWatchItem=this;

        this.setPreferredSize(new Dimension(200,100));
        this.setMaximumSize(new Dimension(200,100));
        this.setMinimumSize(new Dimension(200,100));

        this.hr=new JLabel("0");
        this.min=new JLabel("0");
        this.sec=new JLabel("0");
        this.milli=new JLabel("0");
        this.delete=new JButton("STOP");
        this.pause=new JButton("PAUSE");
        this.lap=new JButton("LAP");

        hr.setPreferredSize(new Dimension(20,20));
        min.setPreferredSize(new Dimension(20,20));
        sec.setPreferredSize(new Dimension(20,20));

        this.add(this.hr);
        this.add(this.min);
        this.add(this.sec);
        this.add(this.milli);
        this.add(this.pause);
        this.add(this.delete);
        this.add(this.lap);

        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                stopwatchUI.stopWatchBack.PressedPause(stopWatch);
                if(stopWatch.isPaused){
                    pause.setText("Resume");
                }
                else
                {
                    pause.setText("Pause");
                }
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                stopwatchUI.stopWatchBack.PressedDelete(stopWatch);
                stopwatchUI.scrollPane.remove(stopWatchItem);
                stopwatchUI.scrollPane.repaint();
                stopwatchUI.lapOutput.setText(stopwatchUI.stopWatchBack.AddLap(stopWatch)+"");
            }
        });

        lap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                stopwatchUI.lapOutput.setText(stopwatchUI.stopWatchBack.AddLap(stopWatch)+"");

            }
        });
    }

    void updateui(StopWatch clock){
        this.hr.setText(clock.hr+"");
        this.min.setText(clock.min+"");
        this.sec.setText(clock.sec+"");
        this.milli.setText(clock.milli+"");
    }
}
