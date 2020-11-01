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
    JLabel hourLabel, yrLabel, dayLabel, monLabel;
    JLabel minLabel;
    JLabel secLabel;
    //    JComboBox<String> ampmDrop;
    Date date;
    String[] ampm;
    JLabel timeLeft;
    JTextField hrText, monText, yrText, dayText;
    JTextField minText;
    JTextField secText;
    Button set;
    AlarmClock setA, getA;
    JLayeredPane lp;
    CurrentTime time;
    ArrayList<AlarmClock> alarmArr;
    int hr = 0, min = 0, sec = 0;
    Button stop;
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
    HashMap<UUID, AlarmItem> map;
    Notifications notify;
    JTextArea message;
    JButton Back;

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
//        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
//        this.setLayout(new GridLayout(0,0));

        this.setLayout(new FlowLayout());
        alui = this;

        Back=new JButton("Back");
        alarmArr = al.alarmArr;
        this.al = al;
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
        message = new JTextArea();
        scrollPaneContent = new JPanel();
        yrText.setPreferredSize(new Dimension(60,30));
       dayText.setPreferredSize(new Dimension(60,30));
        minText.setPreferredSize(new Dimension(60,30));
        secText.setPreferredSize(new Dimension(60,30));
        monText.setPreferredSize(new Dimension(60,30));

        scrollPaneContent.setLayout(new BoxLayout(scrollPaneContent, BoxLayout.Y_AXIS));
//        scrollPaneContent.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        scrollPane = new JScrollPane(scrollPaneContent);
        scrollPane.setPreferredSize(new Dimension(400, 400));
//        scrollPaneContent.setPreferredSize(new Dimension(400,400));
//        scrollPaneContent.setBackground(Color.RED);
        yrText.setText("2020");
        monText.setText("11");
        dayText.setText("1");
        hrText.setText("0");

        set = new Button();
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
        this.add(set);
        message.setPreferredSize(new Dimension(400,50));
        this.add(message);
        this.add(scrollPane);
        map = new HashMap<>();
        printAlarmitem();

        set.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                al.setAlarm(getYrText(), getMonText(), getDayText(), getHrText(), getMinText(), getSecText(),message.getText());
                printAlarmitem();


            }

        });

        UUID code = al.id;
        al.afl = new AlarmFireListener() {
            @Override
            public void fire(UUID id) {
                AlarmItem item = map.get(id);
                addSnoozeAndMessage(item);
                item.revalidate();
                scrollPaneContent.revalidate();
                scrollPane.revalidate();

            }
        };

    }

    void printAlarmitem() {

        scrollPaneContent.removeAll();
        Iterator<AlarmClock> iter = alarmArr.iterator();
        while (iter.hasNext()) {
            System.out.println("print");
            AlarmClock clock = iter.next();
            AlarmItem alarmItem = new AlarmItem(alui, clock);
            JButton delete = new JButton("DELETE");
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(clock);
                    al.deleteAlarm(clock.id);
                    printAlarmitem();
                }
            });
            alarmItem.add(delete);
            if(clock.isfired) {
                addSnoozeAndMessage(alarmItem);
            }
            map.put(clock.id, alarmItem);
            scrollPaneContent.add(alarmItem);

        }
        scrollPaneContent.repaint();
        scrollPane.revalidate();
//        revalidate();

    }


    void addSnoozeAndMessage(AlarmItem item) {
        AlarmClock clock = item.alarmClock;
        JButton snooze = new JButton("Snooze");
        JLabel message = new JLabel(clock.message);
        snooze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                al.setSnooze(clock.id);
                map.get(clock.id).remove(snooze);
                notify = new Notifications(alui, clock.id, map);
                Thread t = new Thread(notify);
                t.start();
            }
        });
        item.add(snooze);
        item.add(message);
    }

   public static void start(Alarm alarm) {
        try {
            JFrame jframe = new JFrame();
            AlarmUI alarmui = new AlarmUI(alarm);
            alarmui.setPreferredSize(new Dimension(100,100));
            jframe.add(alarmui);
            jframe.setVisible(true);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
