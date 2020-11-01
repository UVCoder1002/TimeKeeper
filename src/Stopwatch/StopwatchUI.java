package Stopwatch;

import Manager.TimeManager;
import Manager.UniqueCode;

import javax.swing.*;
import java.util.HashMap;
import java.util.UUID;

public class StopwatchUI extends JPanel {
    private JButton startBT;
    private JButton Back;
    public JTextArea lapOutput;
    private JPanel stopwatchJp;
    JScrollPane OuterScrollPane;
    JFrame frame = null;
    JFrame jFrame;
    StopwatchUI stopWatchui;
    StopwatchBack stopWatchBack;
    HashMap<UUID, StopWatchItem> map;
    JPanel scrollPane=null;
    JPanel OutsidePane;


    public StopwatchUI(StopwatchBack stopWatchBack) {
        if(scrollPane==null)
        scrollPane = new JPanel();
        scrollPane.setLayout(new BoxLayout(scrollPane, BoxLayout.Y_AXIS));
        OuterScrollPane=new JScrollPane(scrollPane);
        map = new HashMap<>();
        this.stopWatchBack = stopWatchBack;
        stopWatchui = this;

        if (frame == null) {
            frame = new JFrame("Time Keeper");
            map = new HashMap<>();
            this.stopWatchBack = stopWatchBack;
            stopWatchui = this;
            OutsidePane.add(OuterScrollPane);
            frame.setContentPane(stopwatchJp);
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(1010, 500);
            frame.setLocation(250, 100);
        }
            frame.setVisible(true);


        /*LISTENER OF CREATING NEW STOPWATCH*/
        startBT.addActionListener(e -> {
            UniqueCode id = new UniqueCode();
            StopWatch stopwatch = new StopWatch(id.generateunicode());
            //adding stopwatch in ui
            addStopwatch(stopwatch);
            //Adding stopwatch in backend
            stopWatchBack.startStopWatch(stopwatch);
        });


        //Joining ui and backend using stopWatchListener
        stopWatchui.stopWatchBack.stopWatchListener = new StopWatchListener() {
            @Override
            public void updateUI(StopWatch stopWatch) {
                printStopWatch(stopWatch);
            }
        };

        /*GETTING BACK TO MENU WITHOUT CREATING CREATING NEW OBJECT*/
        Back.addActionListener(e -> {
            frame.setVisible(false);
            jFrame.setVisible(true);
        });
    }

    public void stopwatchFrameVisible() {
        frame.setVisible(true);
    }

    public void passFrame(JFrame jframe) {
        this.jFrame = jframe;
    }

    public static void main(String[] args) {
        TimeManager timeManager = new TimeManager();
        StopwatchBack stopwatchBack = new StopwatchBack(timeManager);
        new StopwatchUI(stopwatchBack);

    }

    void printStopWatch(StopWatch clock) {
        //System.out.println("id is" + clock.id);
        map.get(clock.id).updateUI(clock);
    }

    void addStopwatch(StopWatch clock) {
        StopWatchItem stopWatchItem = new StopWatchItem(clock,stopWatchui);
        map.put(clock.id, stopWatchItem);
        scrollPane.add(stopWatchItem);
        scrollPane.repaint();
        scrollPane.revalidate();
    }

}

