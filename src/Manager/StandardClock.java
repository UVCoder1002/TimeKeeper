package Manager;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class StandardClock extends JPanel implements Runnable {
   // SimpleDateFormat dateFormat=new SimpleDateFormat("s");
    JLabel currentTime;
    CurrentTime time;
    Date currentDate;
    String selectedtimezone;
    Thread thread = null;
    int xcenter=180, ycenter=180, endsx=0, endsy=0, endmx=0, endmy=0, endhx=0, endhy=0;

    public StandardClock(String timezone) {
        selectedtimezone= timezone;
//        System.out.println(timezone);
        time=new CurrentTime(selectedtimezone);
//        System.out.println(time.zone);
        currentTime = new JLabel("Test");
        currentTime.setForeground(Color.BLACK);
        this.setPreferredSize(new Dimension(350,350));
        this.setLayout(new FlowLayout());
        currentTime.setBackground(Color.RED);
        this.add(currentTime);
//        this.setBounds(0,0,700,700);
    }

    private void drawClock(Graphics g){
        g.setFont(new Font("Algerian",Font.BOLD,20));
//       g.setColor(new Color(229,218,218));
        g.setColor(Color.BLACK);
        g.fillOval(xcenter - 155,ycenter - 155,310,310);
        g.setColor(Color.WHITE);
        g.fillOval(xcenter - 150,ycenter - 150,300,300);
        g.setColor(Color.BLACK);
        g.fillOval(xcenter - 125,ycenter - 125,250,250);
        g.setColor(Color.BLACK);
        g.drawString("9", xcenter - 143, ycenter + 0);
        g.drawString("3", xcenter + 130, ycenter + 0);
        g.drawString("12", xcenter - 10, ycenter - 130);
        g.drawString("6", xcenter - 10, ycenter + 143);
    }
    public void paint(Graphics g){
        g.clearRect(0,0,250,250);
        g.setColor(new Color(118, 73, 190));
        g.fillRect(0,0,400,400);
        int hx,hy,mx,my,sx,sy,min=0,hr=0,sec=0;
        String ampm="AM";
        drawClock(g);
        time.setCurrentTime();
        hr=time.getHr();
        min=time.getMin();
        sec=time.getSec();
        ampm=time.getAmpm();
//       g.setColor(Color.WHITE);
//       JLabel time= new JLabel("HII");
//       JPanel panel= new JPanel();
//       time.setForeground(Color.WHITE);
//       time.setBounds(xcenter,ycenter,100,100);
        g.setColor(Color.BLACK);
        String s=hr + " : "+ min + " : "+ sec+" "+ampm;
        g.drawString(s, xcenter - 55,ycenter + 200);
//       String s=hr + " : "+ min + " : "+ sec;
//       time.setText(s);
        // g.setFont(new Font("TimesRoman",Font.BOLD,20));


        //(hr * 60 + (int)(min/2)) * 3.14f / 1800
        sx=(int)(Math.cos(sec*3.14f/30 -3.14f/2)*120 + xcenter);
        sy=(int)(Math.sin(sec*3.14f/30 -3.14f/2)*120 + ycenter);
        mx=(int)(Math.cos(min * 3.14f/30 +sec*3.14f/1800 - 3.14f / 2)*102+ xcenter);
        my=(int)(Math.sin(min * 3.14f/30 + sec*3.14f/1800 - 3.14f / 2)*102 + ycenter);
        hx=(int)(Math.cos((hr * 30 + min / 2) * 3.14f / 180- 3.14f / 2)*70 + xcenter);
        hy=(int)(Math.sin((hr * 30 + min / 2) * 3.14f / 180 - 3.14f / 2)*70 + ycenter);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.RED);
        g2.drawLine(xcenter,ycenter,sx,sy);
        g2.setColor(Color.WHITE);
        g2.drawLine(xcenter,ycenter-1,mx,my);
        g2.drawLine(xcenter-1,ycenter,mx,my);
        g2.setColor(Color.WHITE);
        g2.drawLine(xcenter,ycenter-1,hx,hy);
        g2.drawLine(xcenter-1,ycenter,hx,hy);
        endsx=sx;
        endsy=sy;
        endmx=mx;
        endmy=my;
        endhx=hx;
        endhy=hy;

    }
    public void start()
    {
        if (thread == null)
        {
            thread = new Thread(this);
            thread.start();
        }
    }
    public void stop()
    {
        thread = null;
    }
    public void run()
    {
        while (thread != null)
        {
            try
            {
                Thread.sleep(100);
//                System.out.println("repaint");
            }
            catch (InterruptedException e) {}
            repaint();
        }
        thread = null;
    }
    public void update(Graphics g)
    {
        paint(g);
    }
//    public static void main(String args[])
//    {
//        JFrame window = new JFrame();
//        Color c = new Color(118, 73, 190);
////        window.setBackground(Color.white);
//        window.setBackground(c);
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setBounds(0, 0, 400, 400);
//        StandardClock clock = new StandardClock("Asia/Kolkata");
//        window.getContentPane().add(clock);
//        window.setVisible(true);
//        clock.start();
//    }

}
