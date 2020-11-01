package Timer;

import Manager.UniqueCode;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;


public class TimerUI extends JPanel {
    private JPanel panel1;
    private JTextField hr;
    private JTextField min;
    private JTextField sec;
    private JButton startBT;
    private JButton backBT;
    private JComboBox comboBox1;
    private JTextField milli;
    public JPanel scrollPane;
    private JPanel OutsidePanel;

    JFrame frame = null;
    JFrame jFrame;
    File file;
    String tone, path = "src\\ToneSetting\\sounds\\alarm2.wav";
    FileWriter fileWrite;
    AudioInputStream audioTnSt;
    Clip clip;
    TimerBack timerBack;
    UniqueCode uniId;
    TimerUI timerui;
    JScrollPane scrollPaneBar;
    HashMap<UUID, TimerItem> map;

    public Integer getHr() {
        return Integer.parseInt(hr.getText());
    }

    public Integer getMin() {
        return Integer.parseInt(min.getText());
    }

    public Integer getSec() {
        return Integer.parseInt(sec.getText());
    }

    public Integer getMilli() {
        return Integer.parseInt(milli.getText());
    }


    public TimerUI(TimerBack timerBack) {
        timerui = this;
        scrollPane = new JPanel();
        scrollPane.setLayout(new BoxLayout(scrollPane, BoxLayout.Y_AXIS));
        uniId = new UniqueCode();
        this.timerBack = timerBack;
        scrollPaneBar = new JScrollPane(scrollPane);
        map = new HashMap<UUID, TimerItem>();
        tone = Objects.requireNonNull(comboBox1.getSelectedItem()).toString();
        OutsidePanel.add(scrollPaneBar);

        addAllTimer();

        if (frame == null) {
            frame = new JFrame("Time Keeper");
            frame.setContentPane(panel1);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(1010, 500);
            frame.setLocation(200, 100);
        } else frame.setVisible(true);


        startBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Timer timer = new Timer(uniId.generateunicode(), getHr(), getMin(), getSec(), getMilli());
                timerBack.startTimer(timer);
                timerui.addTimer(timer);
            }

        });

        timerui.timerBack.timerBackEndListener = new TimerBackEndListener() {
            @Override
            public void updateui(Timer timer) {
                printTimer(timer);
            }

        };

        backBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                jFrame.setVisible(true);
            }
        });

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tone = comboBox1.getSelectedItem().toString();
                System.out.println("tone is" + tone);
                if (tone.equals("Tone1")) {
                    path = "src\\ToneSetting\\sounds\\alarm2.wav";
                } else if (tone.equals("Tone2")) {
                    path = "src\\ToneSetting\\sounds\\ringtone1.wav";
                }
            }
        });
    }

    public void timerFrameVisible() {
        frame.setVisible(true);
    }

    public void passFrame(JFrame jframe) {
        this.jFrame = jframe;
    }

    void printTimer(Timer timer) {
       //using object in Timer to update ui 
        try {
            map.get(timer.id).updateui(timer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    void addTimer(Timer timer) {
        //adding new Timer to map
        TimerItem timerItem = new TimerItem(timer, timerui);
        map.put(timer.id, timerItem);
        scrollPane.add(timerItem);
        scrollPane.repaint();

    }

    void addAllTimer() {
        //Adding all timers to map
        ArrayList<Timer> initialTimers = new ArrayList<>(timerBack.timerList);
        for (Timer initialTimer : initialTimers) {
            TimerItem timerItem = new TimerItem(initialTimer, timerui);
            map.put(initialTimer.id, timerItem);
            scrollPane.add(timerItem);
        }
        scrollPane.repaint();
    }
}

