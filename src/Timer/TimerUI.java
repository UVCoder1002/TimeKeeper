package Timer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import Manager.*;
import Manager.Menu;


public class TimerUI extends JPanel  {
    private JPanel panel1;
    private JTextField hr;
    private JTextField min;
    private JTextField sec;
    private JButton startBT;
    private JButton pauseBT;
    private JButton resetBT;
    private JButton backBT;
    private JComboBox comboBox1;
    JFrame frame=null;
    TimerBackEnd tt;
    Thread t1;
    //TimerUI timer;
    int flag=1;
    int temp=1;
    TimerListener listener;
    File file;
    String path, tone;
    FileWriter fileWrite;
    AudioInputStream audioTnSt;
    Clip clip;


    public Integer getHr() {
        return Integer.parseInt(hr.getText());
    }

    public Integer getMin() {
        return Integer.parseInt(min.getText());
    }

    public Integer getSec() {
        return Integer.parseInt(sec.getText());
    }

    public void setTime(int hr, int min, int sec) {
        this.hr.setText(""+hr);
        this.min.setText(""+min);
        this.sec.setText(""+sec);
    }

    public TimerUI(JFrame jFrame) {

        //TimerUI tCD = new TimerUI();
        //tCD.setLayout(new GridLayout());
        tone = comboBox1.getSelectedItem().toString();
        System.out.println( comboBox1.getSelectedItem().toString());
        if(frame==null) {
            frame = new JFrame("Time Keeper");
            frame.add(panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(1010, 500);
            frame.setLocation(200, 100);
            //frame.add(tCD);
            frame.setVisible(true);

            tt = new TimerBackEnd();
            t1 = new Thread(tt);
            t1.start();

        } else frame.setVisible(true);


        //timer=this;


        startBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("start");
                int min=getMin();
                int sec=getMin();
                if (min>=0&&min<60&&sec>=0&&sec<60) {
                    startBT.setEnabled(false);
                    tt.addTimeListener(listener = new TimerListener(getHr(), getMin(), getSec()) {
                        @Override
                        public void updateTimer(int hr, int min, int sec) {
                            if(hr==0&&min==0&&sec==0)
                            {
                                startBT.setEnabled(true);
                                clip.start();
                            }
                            setTime(hr, min, sec);
                        }
                    });
                    flag = 0;
                }
                else {
                    tt.removeListener(listener);
                    setTime(0,0,0);
                    flag=1;
                }

            /*
                int min=Integer.parseInt(getMin().getText());
                int sec=Integer.parseInt(getSec().getText());
                if (min>=0&&min<60&&sec>=0&&sec<60) {
                    *//*if(t1.getState())
                    { t1.resume(); }*//*
                    if (!t1.isAlive()) {
                        System.out.println("New Thread");
                        t1.start();
                        flag=0;
                }
            } else
                setTime(0,0,0);*/
            }

        });

        pauseBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(flag==0) {
                    t1.suspend();
                    pauseBT.setText("Resume");
                    flag=1;

                }
                else{
                    t1.resume();
                    pauseBT.setText("Pause");
                    flag=0;
                }

            }
        });
        resetBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startBT.setEnabled(true);
                tt.removeListener(listener);
                setTime(0,0,0);
                flag=1;
            }
        });
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
                System.out.println("tone is"+tone);

            }
        });
        if (tone.compareToIgnoreCase("Tone1")==0) {
            path = "src\\ToneSetting\\sounds\\alarm2.wav";
        } else if (tone.compareTo("Tone2")==0) {
            path = "src\\ToneSetting\\sounds\\ringtone1.wav";
        }
        file = new File(path);
        if(file.exists())
        {
            try {
                fileWrite=new FileWriter("src\\ToneSetting\\toneSetting.txt");
                fileWrite.write(path);
                fileWrite.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

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
    }

    public void timerFrameVisible()
    {
        frame.setVisible(true);
    }


//    public static void main(String[] args) {
//        //new TimerUI();
//        //tCD.start();
//    }
}
