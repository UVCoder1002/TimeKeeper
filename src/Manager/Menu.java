package Manager;

import Stopwatch.StopwatchBack;
import Stopwatch.StopwatchUI;
import Timer.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Menu extends JPanel {
    private JPanel jPanelMenu;
    private JButton stopwatchButton;
    private JButton timerButton;
    private JButton timeZonesButton;
    private JButton alarmButton;
    private JPanel jPanelCalendar;
    TimeManager timeManager;
    TimerUI timerUI = null;
    StopwatchUI stopwatchUI = null;
    TimeZones timeZones = null;
    JFrame jFrameMenu;
    String[] s;
    FlowLayout flowLayout;
    StopwatchBack stopwatchBackEnd;
    TimerBack timerBackEnd;
    Alarm alarmBackEnd;

    public Menu() {
        timeManager = new TimeManager();
        stopwatchBackEnd = new StopwatchBack(timeManager);
        timerBackEnd = new TimerBack(timeManager);
        alarmBackEnd = new Alarm(timeManager, "Asia/Kolkata");
        jPanelCalendar = new JPanel();
        flowLayout = new FlowLayout();
        FCalendar calendarObj = new FCalendar();
        StandardClock standardClock = new StandardClock("Asia/Kolkata");
        standardClock.start();
        jFrameMenu = new JFrame("Time Keeper");
        jFrameMenu.setLayout(flowLayout);
        jFrameMenu.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        jFrameMenu.add(jPanelMenu);
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");//plaf = pluggable look and feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        jPanelCalendar.setPreferredSize(new Dimension(310,310));
        jFrameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrameMenu.pack();
        jFrameMenu.setSize(1000, 500);
        jFrameMenu.setLocation(200,100);

        jPanelCalendar.add(calendarObj);
        jFrameMenu.add(standardClock,BorderLayout.EAST);
        jFrameMenu.add(jPanelCalendar,BorderLayout.CENTER);
        jFrameMenu.setVisible(true);
        jFrameMenu.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                    timerBackEnd.writeTimerFile();
                    alarmBackEnd.writeAlarmFile();
                timerBackEnd.writeTimerFile();
                alarmBackEnd.writeAlarmFile();
            }
            @Override
            public void windowClosed(WindowEvent e) { }
        });


        /*ALARM LISTENER - IF ALARM OBJECT NULL CREATING NEW AND PASSING FRAME*/
        alarmButton.addActionListener(e -> {
            try {
                AlarmUI.start(alarmBackEnd, jFrameMenu);
            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }
            jFrameMenu.setVisible(false);
        });


        /*STOPWATCH LISTENER - IF STOPWATCH OBJECT NULL CREATING NEW AND PASSING FRAME*/
        stopwatchButton.addActionListener(e -> {
            if (stopwatchUI == null) {
                stopwatchUI = new StopwatchUI(stopwatchBackEnd);
                stopwatchUI.stopwatchFrameVisible();
                stopwatchUI.passFrame(jFrameMenu);
                jFrameMenu.setVisible(false);
            }else
                stopwatchUI.stopwatchFrameVisible();
                jFrameMenu.setVisible(false);
            });


        /*TIMER LISTENER - IF TIMER OBJECT NULL CREATING NEW AND PASSING FRAME*/
        timerButton.addActionListener(e -> {
            if (timerUI == null) {
                timerUI = new TimerUI(timerBackEnd);
                timerUI.timerFrameVisible();
                timerUI.passFrame(jFrameMenu);
            } else
                timerUI.timerFrameVisible();/*OTHERWISE ONLY MAKE THE FRAME VISIBLE*/
                jFrameMenu.setVisible(false);
            });

        /*TIME ZONE LISTENER - IF TIME ZONE OBJECT NULL CREATING NEW AND PASSING FRAME*/
                timeZonesButton.addActionListener(e -> {
                    try {
                        timeZones = new TimeZones();
                        jFrameMenu.setVisible(false);
                        TimeZones.main(s, jFrameMenu);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
    }


    public static void main(String[] args) {
        new Menu();
    }
}