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

        this.setPreferredSize(new Dimension(300,50));
        this.setMaximumSize(new Dimension(300,50));
        this.setMinimumSize(new Dimension(300,50));
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
        this.add(this.hr,BorderLayout.CENTER);
        this.add(this.min,BorderLayout.CENTER);
        this.add(this.sec,BorderLayout.CENTER);
        this.add(this.milli,BorderLayout.CENTER);
        this.add(this.pause,BorderLayout.CENTER);
        this.add(this.delete,BorderLayout.CENTER);
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerUI.timerBack.PressedPause(timer);
                if(timer.isPaused){
                    pause.setText("RESUME");
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
