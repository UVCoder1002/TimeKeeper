package Stopwatch;

import javax.swing.*;
import java.awt.*;

public class StopWatchItem extends JPanel{
    JLabel hr;
    JLabel min;
    JLabel sec;
    JLabel milli;
    JButton stop;
    JButton pause;

    StopWatchItem(StopWatch stopWatch){
        this.setPreferredSize(new Dimension(200,100));
        this.setMaximumSize(new Dimension(200,100));
        this.setMinimumSize(new Dimension(200,100));
        this.hr=new JLabel("0");
        this.min=new JLabel("0");
        this.sec=new JLabel("0");
        this.milli=new JLabel("0");
        this.stop=new JButton("STOP");
        this.pause=new JButton("PAUSE");
        hr.setPreferredSize(new Dimension(20,20));
        min.setPreferredSize(new Dimension(20,20));
        sec.setPreferredSize(new Dimension(20,20));
//        this.hr.setText(stopWatch.hr+"");

//        this.min.setText(stopWatch.min+"");
//        this.sec.setText(stopWatch.sec+"");
//        this.milli.setText(stopWatch.milli+"");
        this.add(this.hr);
        this.add(this.min);
        this.add(this.sec);
        this.add(this.milli);
        this.add(this.pause);
        this.add(this.stop);

    }
    void updateui(StopWatch clock){
        this.hr.setText(clock.hr+"");
        this.min.setText(clock.min+"");
        this.sec.setText(clock.sec+"");
        this.milli.setText(clock.milli+"");
    }
}
