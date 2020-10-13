import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;

public class Alarm extends JPanel implements Runnable {
    JLabel l1;
    JLabel l2;
    JLabel l3;
    JComboBox<String> Tz;
    Date date;
    String[] ampm;
    JLabel timeLeft;
    JTextField t1;
    JTextField t2;
    JTextField t3;
    Button b1;
    JLayeredPane lp;
    CurrentTime time;
    int hr=0,min=0,sec=0;
    String amOrpm;
    Alarm(String zone) throws IOException {
        TimeZones tzone=new TimeZones();
        time=new CurrentTime(tzone.dateFormat.getTimeZone().toString());
        ampm= new String[]{"AM", "PM"};
        Tz=new JComboBox<String>(ampm);
        l1.setText("Hr :");
        l2.setText("Min :");
        l3.setText("Sec :");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               time.setCurrentTime(hr,min,sec,amOrpm);
               if(checkAlarm()) {
                JLabel alarm=new JLabel("alarm");
                alarm.setText("Alarm");
                alarm.setOpaque(true);
                alarm.setBackground(Color.RED);
                lp.add(alarm,1);
               }
            }
        });
    }
boolean checkAlarm(){

if(hr==Integer.parseInt(t1.getText().toString())&&min==Integer.parseInt(t1.getText().toString())&&sec==Integer.parseInt(t1.getText().toString())&&(amOrpm.compareTo(Tz.getSelectedItem().toString())==0))
{
    return true;
}
return false;
    }

    @Override
    public void run() {

    }
}
