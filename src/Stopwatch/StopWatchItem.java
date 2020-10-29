package Stopwatch;

import javax.swing.*;
import java.awt.*;

public class StopWatchItem extends JPanel{
    JLabel hr;
    JLabel min;
    JLabel sec;
    JLabel milli;

    StopWatchItem(StopWatch stopWatch){
        this.setPreferredSize(new Dimension(40,40));
        this.hr=new JLabel();
        this.min=new JLabel();
        this.sec=new JLabel();
        this.milli=new JLabel();
        this.hr.setText(stopWatch.hr+"");
        this.min.setText(stopWatch.min+"");
        this.sec.setText(stopWatch.sec+"");
        this.milli.setText(stopWatch.milli+"");
        this.add(this.hr);
        this.add(this.min);
        this.add(this.sec);
        this.add(this.milli);
    }
//    void updateui(int hr,int min,int sec,int milli){
//        this.hr.setText(hr+"");
//        this.min.setText(min+"");
//        this.sec.setText(sec+"");
//        this.milli.setText(milli+"");
//    }
}
