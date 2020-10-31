package Manager;

import javax.swing.*;
import java.awt.*;

public class AlarmItem extends JPanel {
    AlarmClock alarmClock;
    JLabel date;
    JLabel month;
    JLabel year;
    JLabel hour;
    JLabel min;
    JLabel sec;
    Button snooze;
    Button delete;
    AlarmItem(AlarmUI alarmUI,AlarmClock alarmclock){
        this.alarmClock=alarmclock;
        AlarmItem alarmItem=this;
        date=new JLabel();
        month=new JLabel();
        year=new JLabel();
        hour=new JLabel();
        min=new JLabel();
        sec=new JLabel();
        snooze=new Button("Snooze");
        delete=new Button("delete");
//        this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        this.setPreferredSize(new Dimension(400,100));
        date.setText(alarmclock.getDt().getDayOfMonth()+"");
        System.out.println(date.getText());
        month.setText(alarmclock.getDt().getMonth().toString());
        year.setText(alarmclock.getDt().getYear()+"");
        hour.setText(alarmclock.getDt().getHour()+"");
        min.setText(alarmclock.getDt().getMinute()+"");
        sec.setText(alarmclock.getDt().getSecond()+"");
        this.add(date);
        this.add(month);
        this.add(year);
        this.add(hour);
        this.add(min);
        this.add(sec);
        this.setBackground(Color.BLUE);


    }
}
