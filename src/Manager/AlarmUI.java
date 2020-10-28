package Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class AlarmUI extends JPanel {
    TextArea textArea;
    Alarm al;
    Button delete;
    JLabel hourLabel,yrLabel,dayLabel,monLabel;
    JLabel minLabel;
    JLabel secLabel;
//    JComboBox<String> ampmDrop;
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
    JScrollPane scrollPane;
    JPanel scrollPaneContent;
    AlarmItem alarmItem;
    AlarmUI alui;
    HashMap<UUID,AlarmItem> map;
    Notifications notify;

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

    public AlarmUI(Alarm al) throws IOException, ClassNotFoundException {
//        this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
//        this.setLayout(new GridLayout(0,0));

        this.setLayout(new FlowLayout());
        alui = this;
        snooze = new Button();
        snooze.setLabel("Snooze");
        alarmArr =al.alarmArr;
        JPanel currentJpanel = this;
        //Alarm newAlarm = new Alarm(zone);
       // Thread t1 = new Thread(newAlarm);
        //this.setLayout();
        //t1.start();
        //TimeZones tzone=new TimeZones();

        ampm = new String[]{"AM", "PM"};
//        ampmDrop = new JComboBox<String>(ampm);
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
        scrollPaneContent=new JPanel();
        scrollPaneContent.setLayout(new BoxLayout(scrollPaneContent,BoxLayout.Y_AXIS));
        scrollPane=new JScrollPane(scrollPaneContent);
        scrollPane.setPreferredSize(new Dimension(400,400));
        scrollPaneContent.setPreferredSize(new Dimension(400,400));
        scrollPaneContent.setBackground(Color.RED);
        delete=new Button("Stop");
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
//        this.add(ampmDrop);
        this.add(set);
        this.add(scrollPane);
        map = new HashMap<>();
        printAlarmitem();

        set.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                al.setAlarm(getYrText(),getMonText(),getDayText(),getHrText(),getMinText(),getSecText());
                printAlarmitem();


            }

        });

        UUID code=al.id;
        al.afl = new AlarmFireListener() {
            @Override
            public void fire(UUID id) {
               map.get(id).add(snooze);
               map.get(id).add(delete);
                snooze.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        al.setSnooze(id);
                        map.get(id).remove(snooze);
                        notify=new Notifications(alui,id,map);
                        Thread t=new Thread(notify);
                        t.start();
//                        JLabel msg = new JLabel("Alarm snoozed for 5 minutes");
//                        map.get(id).add(msg);
//                      map.get(id).revalidate();
                        alui.revalidate();
                        alui.repaint();
//                        map.get(id).remove(msg);
//                        alui.revalidate();
//                        alui.repaint();

                    }
                });
                delete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        al.deleteAlarm(id);
                        printAlarmitem();
//                        alui.revalidate();
                    }
                });
//                alui.revalidate();
                 map.get(id).revalidate();
//                 alui.revalidate();
//                 map.get(id).repaint();
                 scrollPaneContent.revalidate();
                 scrollPane.revalidate();

            }
        };


    }
     void printAlarmitem(){
        scrollPaneContent.removeAll();
        Iterator<AlarmClock> iter=alarmArr.iterator();
        while(iter.hasNext()) {
            System.out.println("print");
            AlarmClock clock=iter.next();
            AlarmItem alarmItem = new AlarmItem(alui,clock);
            map.put(clock.id,alarmItem);
            scrollPaneContent.add(alarmItem);
        }
         scrollPaneContent.repaint();
         scrollPane.revalidate();
//        revalidate();

     }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JFrame jframe = new JFrame();
        TimeZones timezon = new TimeZones();
        TimeManager tm = new TimeManager();
        Alarm al = new Alarm(tm,timezon.dateFormat.getTimeZone().getID());
       AlarmUI alarm = new AlarmUI(al);

      //  System.out.println(alarm);
    //    Alarm alarm = new Alarm(TimeZone.getDefault().toString());
        jframe.add(alarm);
        jframe.setVisible(true);

    }
}
