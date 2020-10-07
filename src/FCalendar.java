import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FCalendar extends JPanel {
    String[] months;
    int[] daysOfMonths;
    String[] dayNames;
    int currentMonth;
    Date currentDate;
    int currentDateNum;
    int currentYear;
    String dayName;
    JLayeredPane lp;
    JPanel task;
    JPanel cal;
    JLabel labels[] = new JLabel[42];
    Thread thread = null;

    FCalendar() {
        months = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        daysOfMonths = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        dayNames = new String[]{"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
        currentDate = new Date();
        cal = new JPanel();
        task = new JPanel();
        lp = new JLayeredPane();
        lp.setLayout(new FlowLayout());
        lp.setPreferredSize(new Dimension(300, 310));
        this.drawCal();
    }

    public void drawCal() {
        cal.setLayout(new GridLayout(0, 7,5,5));
        cal.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        lp.add(cal,0);
        lp.setBorder(BorderFactory.createTitledBorder(
                "CALY"));
        this.add(lp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("y", Locale.getDefault());
        dateFormat.applyPattern("y");
        currentYear = Integer.parseInt(dateFormat.format(currentDate));
        dateFormat.applyPattern("M");
        currentMonth = Integer.parseInt(dateFormat.format(currentDate));
        dateFormat.applyPattern("d");
        currentDateNum = Integer.parseInt(dateFormat.format(currentDate));
        dateFormat.applyPattern("E");
        dayName = dateFormat.format(currentDate);
        showCal(currentMonth, currentYear);
    }

    public void showCal(int mon, int yr) {
        int counter = 0;
        if ((yr % 4 == 0 && yr % 100 != 0) || (yr % 400 == 0)) {
            daysOfMonths[1] = 29;
        }
        System.out.println(months[mon - 1] + " " + yr);
        int space = (mon + daysOfMonths[mon - 1]) % 7;
        System.out.println(space);
        for (int i = 0; i < 7; i++) {
            System.out.print(dayNames[i] + " ");
        }
        System.out.println();
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        for (int i = 0; i < space; i++) {
            labels[i] = new JLabel("",SwingConstants.CENTER);
            labels[counter].setOpaque(true);
            labels[counter].setBackground(Color.CYAN);
            labels[i].setBorder(border);
            labels[i].setText(" ");
            //System.out.print("    ");
            counter++;
            cal.add(labels[i]);
        }
        System.out.print(counter);
        //System.out.println();
        for (int i = 1; i <= daysOfMonths[mon - 1]; i++) {
            labels[counter] = new JLabel("",SwingConstants.CENTER);
            labels[counter].setOpaque(true);
            labels[counter].setBackground(Color.CYAN);
            labels[counter].setBorder(border);
//
            labels[counter].setText(" " + i + "  ");
            labels[counter].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
//                    cal.removeAll();
                    task.removeAll();
                    lp.add(task,1);
                    JLabel j1=new JLabel("Your scheduled Task");
                   task.add(j1);
                    task.setBackground(Color.RED);
                    lp.revalidate();

                }
            });
            cal.add(labels[counter]);
            counter++;

        }

    }

    public static void main(String[] args) {
        JFrame window = new JFrame();
        Color c = new Color(118, 73, 190);
        window.setBackground(c);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, 1000, 1000);
        FCalendar fCal = new FCalendar();
        window.add(fCal);
        window.setVisible(true);
    }
}
