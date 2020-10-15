import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class AlarmUI extends JPanel {
    Alarm al;
    JLabel hourLabel;
    JLabel minLabel;
    JLabel secLabel;
    JComboBox<String> ampmDrop;
    Date date;
    String[] ampm;
    JLabel timeLeft;
    JTextField hrText,monText,yrText,dayText;
    JTextField minText;
    JTextField secText;
    Button set;
    AlarmClock setA, getA;
    JLayeredPane lp;
    CurrentTime time;
    ArrayList<AlarmClock> alarmArr;
    int hr = 0, min = 0, sec = 0;
    Button snooze;
    String amOrpm;
    FileOutputStream fos;
    ObjectOutputStream oos;
    FileInputStream fis;
    ObjectInputStream ois;
    Iterator<AlarmClock> iter;
    Iterator<AlarmClock> iter2;


    public Alarm getAl() {
        return al;
    }

    public Integer getHrText() {
        return Integer.parseInt(hrText.getText());
    }

    public Integer getMonText() {
        return Integer.parseInt(hrText.getText());
    }

    public Integer getYrText() {
        return Integer.parseInt(hrText.getText());
    }

    public Integer getDayText() {
        return Integer.parseInt(hrText.getText());
    }

    public Integer getMinText() {
        return Integer.parseInt(hrText.getText());
    }

    public Integer getSecText() {
        return Integer.parseInt(hrText.getText());
    }

    AlarmUI(Alarm al, String zone) throws IOException, ClassNotFoundException {
        snooze = new Button();
        snooze.setLabel("Snooze");
        System.out.println(zone);
        alarmArr = new ArrayList<AlarmClock>();
        JPanel currentJpanel = this;
        //Alarm newAlarm = new Alarm(zone);
       // Thread t1 = new Thread(newAlarm);
        //this.setLayout();
        //t1.start();
        //TimeZones tzone=new TimeZones();

        ampm = new String[]{"AM", "PM"};
        ampmDrop = new JComboBox<String>(ampm);
        hourLabel = new JLabel();
        minLabel = new JLabel();
        secLabel = new JLabel();
        hrText = new JTextField();
        minText = new JTextField();
        secText = new JTextField();
        monText = new JTextField();
        yrText = new JTextField();
        dayText = new JTextField();
        hrText.setPreferredSize(new Dimension(20, 30));
        minText.setPreferredSize(new Dimension(20, 30));
        secText.setPreferredSize(new Dimension(20, 30));
        set = new Button();
        hourLabel.setText("Hr :");
        minLabel.setText("Min :");
        secLabel.setText("Sec :");
        set.setLabel("SET");
        this.add(hourLabel);
        this.add(hrText);
        this.add(minLabel);
        this.add(minText);
        this.add(secLabel);
        this.add(secText);
        this.add(ampmDrop);
        this.add(set);


        set.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                al.setAlarm(getYrText(),getMonText(),getDayText(),getHrText(),getMinText(),getSecText());
            }

        });
    }

//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        JFrame jframe = new JFrame();
//        TimeZones timezon = new TimeZones();
//       Alarm alarm = new Alarm(timezon.dateFormat.getTimeZone().toString());
//      //  System.out.println(alarm);
//    //    Alarm alarm = new Alarm(TimeZone.getDefault().toString());
//        jframe.add(alarm);
//        jframe.setVisible(true);
//
//    }
}
