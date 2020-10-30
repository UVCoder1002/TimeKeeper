package Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerItem extends JPanel{
    JLabel hr;
    JLabel min;
    JLabel sec;
    JLabel milli;
    JButton delete;
    JButton pause;
    TimerItem timerItem;
    TimerItem(Timer timer, TimerUI timerUI){
        timerItem=this;
        this.setPreferredSize(new Dimension(200,100));
        this.setMaximumSize(new Dimension(200,100));
        this.setMinimumSize(new Dimension(200,100));
        this.hr=new JLabel("0");
        this.min=new JLabel("0");
        this.sec=new JLabel("0");
        this.milli=new JLabel("0");
        this.delete=new JButton("DELETE");
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
        this.add(this.delete);
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerUI.timerBack.PressedPause(timer);
                if(timer.isPaused){
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
                timerUI.timerBack.PressedDelete(timer);
                timerUI.scrollPane.remove(timerItem);
                timerUI.scrollPane.repaint();
            }
        });
    }

    void updateui(Timer timer){
        this.hr.setText(timer.hr+"");
        this.min.setText(timer.min+"");
        this.sec.setText(timer.sec+"");
        this.milli.setText(timer.milli+"");
    }
}
