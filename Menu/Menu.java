package Menu;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager.*;

public class Menu extends JFrame{
    private JButton alarmBT;
    private JButton stopwatchBT;
    private JButton timerBT;
    private JButton keepNotesBT;
    private JButton settingsBT;
    private JLabel headingLable;
    private JPanel jPanel;

    public Menu()
    {
        //stopwatchBT.addActionListener(new sw());
        stopwatchBT.addActionListener(new ActionListener() //stopwatch listener
        {
            @Override
            public void actionPerformed(ActionEvent e) {
               Stopwatch sw = new Stopwatch();
               sw.setVisible(true);

            }
        });

        timerBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Timer timer = new Timer();
                timer.setVisible(true);
                // timer func
            }
        });
    }

    public static void main(String[] args) {
        new Menu();
        JFrame frame = new JFrame("Time Keeper");
        frame.setContentPane(new Menu().jPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignore) {

        }*/
        frame.pack();
        frame.setTitle("Time Kepper");
        frame.setSize(800, 400);
        frame.setLocation(250,100);

        frame.setVisible(true);
    }

}
