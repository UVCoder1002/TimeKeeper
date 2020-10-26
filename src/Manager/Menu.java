package Manager;


import Timer.TimerUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Menu extends JPanel{
    private JPanel jPanelMenu;
    private JButton stopwatchButton;
    private JButton timerButton;
    private JButton timeZonesButton;
    private JButton settingButton;
    private JButton alarmButton;
    TimeManager timeManager;
    JFrame frame;



    public Menu() {

        FCalendar calendarObj = new FCalendar();
        StandardClock standardClock= new StandardClock("Asia/Kolkata");
        standardClock.start();
        //tCD.setLayout(new GridLayout());
        frame = new JFrame("Time Keeper");
        //frame.setContentPane(new Menu().jPanelMenu);
        //frame.setBackground(new Color(255,255,255));
        frame.add(jPanelMenu);
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1010, 500);
        frame.setLocation(200,100);

        frame.add(standardClock,BorderLayout.EAST);
        frame.add(calendarObj,BorderLayout.CENTER);
        //frame.add(tCD);
        frame.setVisible(true);



        timerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TimerUI(frame);
                frame.setVisible(false);
                /*JFrame timerFrame = new JFrame("Time Keeper");
                frame.setContentPane(new TimerUI());
                timerFrame.setVisible(true);*/
            }
        });
        stopwatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //new StopwatchUI(frame);
                frame.setVisible(false);
            }
        });
        timeZonesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new TimeZones();
                    frame.setVisible(false);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        new Menu();
    }
}