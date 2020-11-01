package Manager;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class AlarmUI extends JPanel {
    Alarm alarm;

    Date date;
    JLabel timeLeft;
    JLabel hourLabel, yrLabel, dayLabel, monLabel;
    JLabel minLabel;
    JLabel secLabel;
    JTextField hrText, monText, yrText, dayText;
    JTextField minText;
    JTextField secText;
    TextArea textArea;
    Button set;
    JLayeredPane lp;
    CurrentTime time;
    ArrayList<AlarmClock> alarmArr;
    int hr = 0, min = 0, sec = 0;

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

    FlowLayout flowLayout;
    Button backButton;
    JPanel currentJpanel;
    String[] tones = {"Tone1", "Tone2"};
    JComboBox comboBoxTone = new JComboBox(tones);
    String tone;
    String path = "src\\ToneSetting\\sounds\\alarm2.wav";
    File file;
    Clip clip;
    AudioInputStream audioTnSt;
    ArrayList<AlarmTone> alarmTones;

    JFrame jframe;
    JFrame frame;

    JTextArea message;
    JButton Back;


    public Alarm getAl() {
        return alarm;
    }
    // Setter And Getter Functions To Take Values From UI

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
    // Initialising AlarmUI with backend Object

    public AlarmUI(Alarm alarm) throws IOException, ClassNotFoundException {

        this.setLayout(new FlowLayout());
        alui = this;


        Back = new JButton("Back");

        alarmArr = alarm.alarmArr;
        this.alarm = alarm;
        JPanel currentJpanel = this;


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
        set = new Button();
        backButton = new Button("Back");
        map = new HashMap<>();
        time=new CurrentTime("Asia/Kolkata");

        //Setting Layout in The List Of Alarms Along Y axis
        scrollPaneContent.setLayout(new BoxLayout(scrollPaneContent, BoxLayout.Y_AXIS));

        //adding scrollbar to The list
        scrollPane = new JScrollPane(scrollPaneContent);
        scrollPane.setPreferredSize(new Dimension(400, 400));
        scrollPaneContent.setBackground(Color.GRAY);

        //Setting default Values to the Inputs
        time.setCurrentTime();
        yrText.setText(time.yr + "");
        monText.setText(time.mon + "");
        dayText.setText(time.day + "");
        hrText.setText(time.hr + "");
        yrLabel.setText("Year :");
        monLabel.setText("Month :");
        dayLabel.setText("Day :");
        hourLabel.setText("Hr :");
        minLabel.setText("Min :");
        secLabel.setText("Sec :");
        set.setLabel("SET");

        // Setting Dimensions Of Inputs Area
        yrText.setPreferredSize(new Dimension(50, 30));
        monText.setPreferredSize(new Dimension(40, 30));
        dayText.setPreferredSize(new Dimension(40, 30));
        hrText.setPreferredSize(new Dimension(40, 30));
        minText.setPreferredSize(new Dimension(40, 30));
        secText.setPreferredSize(new Dimension(40, 30));
        message.setPreferredSize(new Dimension(400, 50));


        //Adding Components to The UI
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
        this.add(message);
        this.add(scrollPane);
        this.add(backButton, BorderLayout.NORTH);

        //Printing List After Every Run from the File
        printAlarmitem();

        set.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                alarm.setAlarm(getYrText(), getMonText(), getDayText(), getHrText(), getMinText(), getSecText(), message.getText(), getPath());
                printAlarmitem();


            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu();

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

        //getting id of the AlarmClock Passed in the backend
        UUID code = alarm.id;

        //Creating Anonymous object of AlarmFireListener which is called when alarm is fired from TimeManager
        alarm.afl = new AlarmFireListener() {

            @Override
            public void fire(UUID id, String path) {

                file = new File(path);
                if (file.exists()) {
                    try {
                        audioTnSt = AudioSystem.getAudioInputStream(file);
                        clip = AudioSystem.getClip();
                        clip.open(audioTnSt);
                    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException unsupportedAudioFileException) {
                        unsupportedAudioFileException.printStackTrace();
                    }
                } else {
                    System.out.println("File doesn't exist");
                }

                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);

                //Getting Alarmitem from the map
                AlarmItem item = map.get(id);

                //Adding snooze and Message Button
                addSnoozeAndMessage(item);

                //revalidating ui
                item.revalidate();
                scrollPaneContent.revalidate();
                scrollPane.revalidate();
            }
        };

    }

    //Repaint the scrollPane when a New Alarm is added
    void printAlarmitem() {

        scrollPaneContent.removeAll();
        Iterator<AlarmClock> iter = alarmArr.iterator();
        while (iter.hasNext()) {
            System.out.println("print");
            AlarmClock clock = iter.next();
            AlarmItem alarmItem = new AlarmItem(alui, clock);

            //adding delete button to each of the alarmItem
            JButton delete = new JButton("DELETE");
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(clock);
                    alarm.deleteAlarm(clock.id);
                    printAlarmitem();
                }
            });
            alarmItem.add(delete);
            //checking if fired then show snooze and Message components
            if (clock.isfired) {
                addSnoozeAndMessage(alarmItem);
            }

            //Adding alarm item to map
            //map is used to identify a particular item using id
            map.put(clock.id, alarmItem);
            scrollPaneContent.add(alarmItem);

        }
        scrollPaneContent.repaint();
        scrollPane.revalidate();
//        revalidate();

    }

  //function to add snooze and message
    void addSnoozeAndMessage(AlarmItem item) {
        AlarmClock clock = item.alarmClock;
        JButton snooze = new JButton("Snooze");
        JLabel message = new JLabel(clock.message);
        snooze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alarm.setSnooze(clock.id);
                map.get(clock.id).remove(snooze);
                notify = new Notifications(alui, clock.id, map);
                Thread t = new Thread(notify);
                t.start();
            }
        });
        item.add(snooze);
        item.add(message);
    }

    public void passFrame(JFrame frame) {
        this.frame = frame;
    }

   //function to initiate alarmui
    public static void start(Alarm alarm) {
        try {

            AlarmUI alarmui = new AlarmUI(alarm);

            alarmui.setPreferredSize(new Dimension(100, 100));
//            jframe.add(alarmui);
//            jframe.setVisible(true);

            alarmui.jframe = new JFrame();
            alarmui.jframe.add(alarmui);
            alarmui.jframe.setSize(1000, 500);
            alarmui.jframe.setLocation(200, 100);
            alarmui.jframe.setVisible(true);

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
