package Manager;

import javax.sound.sampled.*;
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
    FlowLayout flowLayout;
    Button backButton;
    JPanel currentJpanel;
    JFrame jframe;
    String[] tones = {"Tone1", "Tone2"};
    JComboBox comboBoxTone = new JComboBox(tones);
    String tone;
    String path="src\\ToneSetting\\sounds\\alarm2.wav";
    File file;
    Clip clip;
    AudioInputStream audioTnSt;
    ArrayList<AlarmTone> alarmTones;



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

    public String getPath() {
        return path;
    }

    public AlarmUI(Alarm al) throws IOException, ClassNotFoundException {
//        this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
//        this.setLayout(new GridLayout(0,0));

        this.setLayout(new FlowLayout());
        alui = this;
        snooze = new Button();
        snooze.setLabel("Snooze");
        alarmArr =al.alarmArr;
        currentJpanel = this;
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
        scrollPaneContent.setBackground(Color.GRAY);
        delete=new Button("Stop");
        yrText.setText("2020");
        monText.setText("10");
        dayText.setText("31");
        hrText.setText("20");
        yrText.setPreferredSize(new Dimension(50, 30));
        monText.setPreferredSize(new Dimension(40, 30));
        dayText.setPreferredSize(new Dimension(40, 30));
        hrText.setPreferredSize(new Dimension(40, 30));
        minText.setPreferredSize(new Dimension(40, 30));
        secText.setPreferredSize(new Dimension(40, 30));
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
        backButton = new Button("Back");

        map = new HashMap<>();
        printAlarmItem();

        set.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                al.setAlarm(getYrText(),getMonText(),getDayText(),getHrText(),getMinText(),getSecText(),getPath());
                printAlarmItem();


            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu();
                menu.frameVisible();
                jframe.setVisible(false);
            }
        });

        comboBoxTone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tone = comboBoxTone.getSelectedItem().toString();
                if (tone.equals("Tone1")) {
                    path = "src\\ToneSetting\\sounds\\alarm2.wav";
                } else if (tone.equals("Tone2")) {
                    path = "src\\ToneSetting\\sounds\\ringtone1.wav";
                }
            }
        });



        UUID code=al.id;
        al.afl = new AlarmFireListener() {//tone will be fired here
            @Override
            public void fire(UUID id,String path) {

                file = new File(path);
                if(file.exists())
                {
                    try {
                        audioTnSt = AudioSystem.getAudioInputStream(file);
                        clip = AudioSystem.getClip();
                        clip.open(audioTnSt);
                    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException unsupportedAudioFileException) {
                        unsupportedAudioFileException.printStackTrace();
                    }
                } else
                {
                    System.out.println("File doesn't exist");
                }

                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
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
                        clip.close();
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
                        printAlarmItem();
                        clip.close();
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
    void printAlarmItem(){
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
        TimeZones timezone = new TimeZones();
        TimeManager tm = new TimeManager();
        Alarm al = new Alarm(tm,timezone.dateFormat.getTimeZone().getID());
        AlarmUI alarm = new AlarmUI(al);
        alarm.jframe = new JFrame();
        alarm.flowLayout = new FlowLayout();
        alarm.jframe.setLayout(alarm.flowLayout);
        alarm.jframe.add(alarm.comboBoxTone,BorderLayout.EAST);
        alarm.jframe.add(alarm.backButton,BorderLayout.NORTH);
        alarm.currentJpanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        //jframe.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        //  System.out.println(alarm);
        //    Alarm alarm = new Alarm(TimeZone.getDefault().toString());
        alarm.jframe.setSize(1000, 500);
        alarm.jframe.setLocation(200,100);
        alarm.jframe.add(alarm);
        //alarm.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        alarm.jframe.setVisible(true);

    }
}
