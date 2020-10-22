package Manager;

import javax.swing.*;

public class AlarmItem extends JPanel {
    JLabel date;
    JLabel month;
    JLabel year;
    JLabel hour;
    JLabel min;
    JLabel sec;
    AlarmItem(AlarmClock alarmclock){
        date=new JLabel();
        month=new JLabel();
        year=new JLabel();
        hour=new JLabel();
        min=new JLabel();
        sec=new JLabel();
        this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        date.setText(alarmclock.getDt().getDayOfMonth()+"");
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


    }
}
