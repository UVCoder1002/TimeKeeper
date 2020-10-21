package Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

public class AlarmUI extends JPanel {
    ScrollPane scrollPane;
    TextArea textArea;
    Alarm al;
    JLabel hourLabel,yrLabel,dayLabel,monLabel;
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
    Button snooze,stop;
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
        return Integer.parseInt(monText.getText());
    }

    public Integer getYrText() {
        return Integer.parseInt(yrText.getText());
    }

    public Integer getDayText() {
        return Integer.parseInt(dayText.getText());
    }

    public Integer getMinText() {
        return Integer.parseInt(minText.getText());
    }

    public Integer getSecText() {
        return Integer.parseInt(secText.getText());
    }

    AlarmUI(Alarm al) throws IOException, ClassNotFoundException {

        snooze = new Button();
        snooze.setLabel("Snooze");
        alarmArr = new ArrayList<AlarmClock>();
        JPanel currentJpanel = this;
        //Alarm newAlarm = new Alarm(zone);
       // Thread t1 = new Thread(newAlarm);
        //this.setLayout();
        //t1.start();
        //TimeZones tzone=new TimeZones();

        ampm = new String[]{"AM", "PM"};
        ampmDrop = new JComboBox<String>(ampm);
        yrLabel = new JLabel();
        monLabel = new JLabel();
        dayLabel = new JLabel();
        hourLabel = new JLabel();
        minLabel = new JLabel();
        secLabel = new JLabel();
        hrText = new JTextField();
        minText = new JTextField();
        secText = new JTextField();
        monText = new JTextField();
        yrText = new JTextField();
        dayText = new JTextField();
        yrText.setText("2020");
        monText.setText("10");
        dayText.setText("21");
        hrText.setText("22");
        yrText.setPreferredSize(new Dimension(20, 30));
        monText.setPreferredSize(new Dimension(20, 30));
        dayText.setPreferredSize(new Dimension(20, 30));
        hrText.setPreferredSize(new Dimension(20, 30));
        minText.setPreferredSize(new Dimension(20, 30));
        secText.setPreferredSize(new Dimension(20, 30));
        set = new Button();
        snooze=new Button("Snooze");
        stop=new Button("Stop");
        yrLabel.setText("Year :");
        monLabel.setText("Month :");
        dayLabel.setText("Day :");
        hourLabel.setText("Hr :");
        minLabel.setText("Min :");
        secLabel.setText("Sec :");
        set.setLabel("SET");
        this.add(yrLabel);
        this.add(yrText);
        this.add(monLabel);
        this.add(monText);
        this.add(dayLabel);
        this.add(dayText);
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
        AlarmUI alui = this;
        UUID code=al.id;
        al.afl = new AlarmFireListener() {
            @Override
            public void fire(UUID id) {
               alui.add(snooze);

                snooze.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      al.setSnooze(id);
                      alui.remove(snooze);
                      alui.revalidate();
                    }
                });
                alui.revalidate();

            }
        };


    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JFrame jframe = new JFrame();
        TimeZones timezon = new TimeZones();
        TimeManager tm = new TimeManager();
        Alarm al = new Alarm(tm,timezon.dateFormat.getTimeZone().getID());
       AlarmUI alarm = new AlarmUI(al);
       alarm.setLayout();
      //  System.out.println(alarm);
    //    Alarm alarm = new Alarm(TimeZone.getDefault().toString());
        jframe.add(alarm);
        jframe.setVisible(true);

    }
}
