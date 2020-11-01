package Manager;


import Stopwatch.StopwatchBack;
import Stopwatch.StopwatchUI;
import Timer.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Menu extends JPanel{
    private JPanel jPanelMenu;
    private JButton stopwatchButton;
    private JButton timerButton;
    private JButton timeZonesButton;
    private JButton alarmButton;
    private JPanel jPanelCal1;
    TimeManager timeManager;
    TimerUI timerUI=null;
    StopwatchUI stopwatchUI= null;
    TimeZones timeZones = null;
    JFrame frame;
    String[] s;
    FlowLayout flowLayout;
    StopwatchBack stopwatchBack;
    TimerBack timerBack;
    Alarm alarmBack;
    AlarmUI alarmUI;
    public Menu() {
timeManager=new TimeManager();
 stopwatchBack=new StopwatchBack(timeManager);
 timerBack=new TimerBack(timeManager);
 alarmBack=new Alarm(timeManager,"Asia/Kolkata");
        jPanelCal1=new JPanel();
        flowLayout= new FlowLayout();
        FCalendar calendarObj = new FCalendar();
        StandardClock standardClock= new StandardClock("Asia/Kolkata");
        standardClock.start();
        //tCD.setLayout(new GridLayout());
        frame = new JFrame("Time Keeper");
        //timerUI=new TimerUI(frame);
        //frame.setContentPane(new Menu().jPanelMenu);
        //frame.setBackground(new Color(255,255,255));
        frame.setLayout(flowLayout);
        frame.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        frame.add(jPanelMenu);
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        jPanelCal1.setPreferredSize(new Dimension(310,310));
        //jPanelCal.setPreferredSize(new Dimension(310,310));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1000, 500);
        frame.setLocation(200,100);
        //setBackground(Color.BLACK);
        jPanelCal1.add(calendarObj);
        frame.add(standardClock,BorderLayout.EAST);
        frame.add(jPanelCal1,BorderLayout.CENTER);
        //frame.add(calendarObj,BorderLayout.CENTER);
        //frame.add(tCD);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {


            @Override
            public void windowClosing(WindowEvent e) {
                    timerBack.writeTimerFile();
                    alarmBack.writeAlarmFile();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }


        });


        timerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timerUI == null) {

                    timerUI = new TimerUI(timerBack);
                    timerUI.timerFrameVisible();
                    timerUI.passFrame(frame);
                }
                else
                    timerUI.timerFrameVisible();
                frame.setVisible(false);
            }
        });
        stopwatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stopwatchUI == null) {//dekh rhi na timemanager kitni baar banaya hai :(

                    stopwatchUI = new StopwatchUI(stopwatchBack);
                    stopwatchUI.stopwatchFrameVisible();
                    stopwatchUI.passFrame(frame);
                    frame.setVisible(false);
//                    stopwatchUI = new StopwatchUI(timeManager);
                }
                else
                    stopwatchUI.stopwatchFrameVisible();
                frame.setVisible(false);
            }
        });
        timeZonesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    timeZones = new TimeZones();
                    timeZones.passFrame(frame);
                    frame.setVisible(false);
                    TimeZones.main(s);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        alarmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*try {
                    alarmUI = new AlarmUI(alarmBack);
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
                alarmUI.passFrame(frame);*/
                frame.setVisible(false);
                    AlarmUI.start(alarmBack);
//                    frame.setVisible(false);

            }
        });
    }

    public void frameVisible()
    {
            frame.setVisible(true);

    }

    public static void main(String[] args) {
        new Menu();
    }
}