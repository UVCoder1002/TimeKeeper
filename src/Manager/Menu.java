package Manager;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Stopwatch.*;

public class Menu extends JPanel{
    private JPanel jPanelMenu;
    private JButton stopwatchButton;
    private JButton timerButton;
    private JButton timeZonesButton;
    private JButton settingButton;
    private JButton alarmButton;
    TimeManager timeManager;



    public Menu() {

        FCalendar calendarObj = new FCalendar();
        StandardClock standardClock= new StandardClock("Asia/Kolkata");
        //tCD.setLayout(new GridLayout());
        JFrame frame = new JFrame("Time Keeper");
        //frame.setContentPane(new Menu().jPanelMenu);
        frame.add(jPanelMenu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1000, 500);
        frame.setLocation(200,100);
        frame.add(calendarObj);
        //frame.setLocation(270,100);
        //frame.add(standardClock);
        //frame.setLocation(500,100);
        //frame.add(tCD);
        frame.setVisible(true);

        /*stopwatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StopwatchUI stopwatchPanel = new StopwatchUI(timeManager);
                frame.add(stopwatchPanel);
            }
        });*/
    }

    public static void main(String[] args) {
        new Menu();

    }
}
