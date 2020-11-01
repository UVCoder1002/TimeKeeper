package Manager;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class AlarmUI extends JPanel {
    Alarm al;
    JLabel hourLabel, yrLabel, dayLabel, monLabel;
    JLabel minLabel;
    JLabel secLabel;
    String[] ampm;
    JTextField hrText, monText, yrText, dayText;
    JTextField minText;
    JTextField secText;
    Button set;
    ArrayList<AlarmClock> alarmArr;
    JScrollPane scrollPane;
    JPanel scrollPaneContent;
    AlarmUI alui;
    HashMap<UUID, AlarmItem> map;
    Notifications notify;
    Button backButton;
    String[] tones = {"Tone1", "Tone2"};
    JComboBox comboBoxTone = new JComboBox(tones);
    String tone;
    String path = "src\\ToneSetting\\sounds\\alarm2.wav";
    File file;
    Clip clip;
    AudioInputStream audioTnSt;
    JFrame jframe;
    JFrame frame;
    JTextArea message;
    JButton Back;


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

    public AlarmUI(Alarm al) {
        this.setLayout(new FlowLayout());
        alui = this;
        Back=new JButton("Back");
        alarmArr = al.alarmArr;
        this.al = al;
        ampm = new String[]{"AM", "PM"};
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
        scrollPaneContent.setLayout(new BoxLayout(scrollPaneContent, BoxLayout.Y_AXIS));
        scrollPaneContent.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        scrollPane = new JScrollPane(scrollPaneContent);
        scrollPane.setPreferredSize(new Dimension(400, 400));
        scrollPaneContent.setBackground(Color.GRAY);

        yrText.setText("2020");
        monText.setText("11");
        dayText.setText("1");
        hrText.setText("0");

        yrText.setPreferredSize(new Dimension(50, 30));
        monText.setPreferredSize(new Dimension(40, 30));
        dayText.setPreferredSize(new Dimension(40, 30));
        hrText.setPreferredSize(new Dimension(40, 30));
        minText.setPreferredSize(new Dimension(40, 30));
        secText.setPreferredSize(new Dimension(40, 30));


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
        message.setPreferredSize(new Dimension(400, 50));
        this.add(message);
        this.add(scrollPane);
        this.add(comboBoxTone);
        backButton = new Button("Back");
        this.add(backButton,BorderLayout.WEST);
        map = new HashMap<>();
        printAlarmitem();

        set.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                al.setAlarm(getYrText(), getMonText(), getDayText(), getHrText(), getMinText(), getSecText(), message.getText(), getPath());
                printAlarmitem();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
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


        UUID code = al.id;
        al.afl = new AlarmFireListener() {
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
                    al.deleteAlarm(clock.id,clip);
                    printAlarmitem();
                }
            });
            alarmItem.add(delete);
            if (clock.isfired) {
                addSnoozeAndMessage(alarmItem);
            }
            map.put(clock.id, alarmItem);
            scrollPaneContent.add(alarmItem);
        }
        scrollPaneContent.repaint();
        scrollPane.revalidate();
    }


    void addSnoozeAndMessage(AlarmItem item) {
        AlarmClock clock = item.alarmClock;
        JButton snooze = new JButton("Snooze");
        JLabel message = new JLabel(clock.message);
        snooze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clip!=null)
                clip.stop();
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



    public static void start(Alarm alarm, JFrame frame) {
            AlarmUI alarmui = new AlarmUI(alarm);
            alarmui.setPreferredSize(new Dimension(100,100));
            alarmui.frame = frame;
            alarmui.jframe = new JFrame();
            alarmui.jframe.add(alarmui);
            alarmui.jframe.setSize(1000, 500);
            alarmui.jframe.setLocation(200,100);
            alarmui.jframe.setVisible(true);
    }
}
