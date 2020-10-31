package Timer;

import Manager.TimeListener;
import Manager.TimeManager;
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
    private JButton pauseBT;
    private JButton resetBT;
    private JButton backBT;
    private JComboBox comboBox1;
    private JTextField milli;
    public JPanel scrollPane;
    private JPanel OutsidePanel;
    TimeManager timeManager;
    TimeListener listener;
    JFrame frame = null;
    JFrame jFrame;
    //TimerBackEnd tt;
    //Thread t1;
    //TimerUI timer;
    int flag = 1;
    int temp = 1;
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

    public void setTime(int hr, int min, int sec) {
        this.hr.setText("" + hr);
        this.min.setText("" + min);
        this.sec.setText("" + sec);
    }

    public void setMilli(int milli) {
        this.milli.setText("" + milli);
    }

    public TimerUI(TimerBack timerBack) {
        timerui = this;
//        scrollPane.setBackground(Color.red);

        scrollPane=new JPanel();
        scrollPane.setLayout(new BoxLayout(scrollPane,BoxLayout.Y_AXIS));
//        scrollPane.setLayout(new BoxLayout(scrollPane, BoxLayout.Y_AXIS));
        uniId = new UniqueCode();
        this.timerBack = timerBack;
        scrollPaneBar=new JScrollPane(scrollPane);
        map = new HashMap<UUID, TimerItem>();
        //TimerUI tCD = new TimerUI();
        //tCD.setLayout(new GridLayout());
        tone = Objects.requireNonNull(comboBox1.getSelectedItem()).toString();
        System.out.println(comboBox1.getSelectedItem().toString());
        OutsidePanel.add(scrollPaneBar);
        addAllTimer();
        if (frame == null) {
            frame = new JFrame("Time Keeper");
            frame.setContentPane(panel1);
//            frame.add(panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(1010, 500);
            frame.setLocation(200, 100);
            //frame.add(tCD);
            /*tt = new TimerBackEnd();
            t1 = new Thread(tt);
            t1.start();*/
//            timeManager = tm;

        } else frame.setVisible(true);


        //timer=this;


        startBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("start");

                Timer timer = new Timer(uniId.generateunicode(), getHr(), getMin(), getSec(), getMilli());
                timerBack.startTimer(timer);
                timerui.addTimer(timer);
//                    timeManager.addTimeListener(listener = new TimerListener(timer) {
//                        @Override
//                        public void timeUpdated(int hr, int min, int sec) {
//
//                        }
//
//                        @Override
//                        public void timeUpdated(int hr, int min, int sec, int milli) {
//                            setTime(hr, min, sec);
//                            setMilli(milli);
//                            if(getHr()==0&&getMin()==0&&getSec()==0&&getMilli()==0)
//                            {
//                                startBT.setEnabled(true);
//                                clip.start();
//                            }
//                        }

//                        @Override
//                        public void timeUpdated(int milli) {
//                            if(getHr()==0&&getMin()==0&&getSec()==0&&getMilli()==0)
//                            {
//                                startBT.setEnabled(true);
//                                clip.start();
//                            }
//                            setMilli(milli);
//                        }
//                    });
//                    flag = 0;

//                else {
////                    timeManager.removeListener(listener);
//                    setTime(0,0,0);
//                    setMilli(0);
//                    flag=1;
//                }

//                file = new File(path);
//                if(file.exists())
//                {
//                    try {
//                        fileWrite=new FileWriter("src\\ToneSetting\\toneSetting.txt");
//                        fileWrite.write(path);
//                        fileWrite.close();
//                    } catch (IOException ioException) {
//                        ioException.printStackTrace();
//                    }
//
//                    try {
//                        audioTnSt = AudioSystem.getAudioInputStream(file);
//                        clip = AudioSystem.getClip();
//                        clip.open(audioTnSt);
//                    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException unsupportedAudioFileException) {
//                        unsupportedAudioFileException.printStackTrace();
//                    }
//                } else
//                {
//                    System.out.println("File doesn't exist");
//                }
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
//        System.out.println("id is" + timer.id);
        try {
            map.get(timer.id).updateui(timer);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    void addTimer(Timer timer) {
        TimerItem timerItem = new TimerItem(timer, timerui);
        map.put(timer.id, timerItem);
        scrollPane.add(timerItem);
        scrollPane.repaint();
//        scrollPane.revalidate();
//        revalidate();

    }

    void addAllTimer(){
        ArrayList<Timer> initialTimers =  new ArrayList<>(timerBack.timerList);
        for (Timer initialTimer : initialTimers) {
            TimerItem timerItem = new TimerItem(initialTimer, timerui);
            map.put(initialTimer.id,timerItem);
            scrollPane.add(timerItem);
        }
        scrollPane.repaint();
    }
//    public static void main(String[] args) {
//       TimerBack timerBack=new TimerBack(new TimeManager());
//    }
}
