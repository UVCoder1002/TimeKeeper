package Menu;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.String;
import java.applet.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Tone_Setting extends JFrame{
    private JComboBox comboBox1;
    private JPanel settingJP;
    private JButton playBT;
    private JButton stopBT;
    private JButton chooseBT;
    private String file;
    public static Tone_Setting instance;
    private URL urlFile;

    FileReader fileRead;
    FileWriter fileWrite;
    BufferedReader buffRead;
    BufferedWriter buffWrite;
    AudioInputStream audioTnSt;
    AudioFormat format;
    DataLine.Info info;
    Clip clip;
    String path;



    public Tone_Setting() {
        /*filePath = "F:/Coding/Avishkar2020/softablitz/project/TimeKeeper/src/audioFiles/alarm1.wav";
        audioInSt = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();*/

        chooseBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fD = new FileDialog(instance);
                fD.setMode(FileDialog.LOAD);
                fD.setVisible(true);
                String audio = fD.getDirectory() + fD.getFile();
                if (audio != null)
                    file = audio;
                try {
                    urlFile = new URL("file:" + file);
                } catch (MalformedURLException ex) {
                    System.err.println(ex);
                }
            }
        });

        /*comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    urlFile = new URL("file:" + path);
                } catch (MalformedURLException ex) {
                    System.err.println(ex);
                }
                Applet.newAudioClip(urlFile).play();

                try {
                    audioTnSt = AudioSystem.getAudioInputStream(myFile);
                } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                    unsupportedAudioFileException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                format = audioTnSt.getFormat();
                info = new DataLine.Info(Clip.class, format);
                try {
                    clip = (Clip) AudioSystem.getLine(info);
                } catch (LineUnavailableException lineUnavailableException) {
                    lineUnavailableException.printStackTrace();
                }
                try {
                    clip.open(audioTnSt);
                } catch (LineUnavailableException lineUnavailableException) {
                    lineUnavailableException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                clip.start();


                Object item = (Object) comboBox1.getSelectedItem();
                try {
                    item = audioInSt = AudioSystem.getAudioInputStream(myFile);
                } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                    unsupportedAudioFileException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                format = audioInSt.getFormat();
                info = new DataLine.Info(Clip.class, format);
                clip = (Clip) AudioSystem.getLine(info);
                setTone(item);
                try {
                    clip.open(audioInSt);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } catch (LineUnavailableException lineUnavailableException) {
                    lineUnavailableException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });*/


        playBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Applet.newAudioClip(urlFile).play();
                System.out.println("playing");
            }
        });

        stopBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Applet.newAudioClip(urlFile).stop();
                System.out.println("stopped");
            }
        });

    }

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Tone_Setting toneS = new Tone_Setting();
        JFrame frame = new JFrame("Tone Setting");
        frame.setContentPane(new Tone_Setting().settingJP);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 300);
        frame.setLocation(250,100);
        frame.setVisible(true);
    }

    /*public Music(String path) {

    }*/

    /*public void setTone(String tone)
    {
        if(tone.equals("Tone1"))
        {
            filePath= "F:/Coding/Avishkar2020/softablitz/project/TimeKeeper/src/audioFiles/alarm1.wav";
        } else if (tone.equals("Tone2"))
        {
            filePath= "F:/Coding/Avishkar2020/softablitz/project/TimeKeeper/src/audioFiles//audioFiles/alarm2.wav";
        } else if (tone.equals("Tone3"))
        {
            filePath= "F:/Coding/Avishkar2020/softablitz/project/TimeKeeper/src/audioFiles//audioFiles/ringtone1.wav";
        } else if (tone.equals("Tone4"))
        {
            filePath= "F:/Coding/Avishkar2020/softablitz/project/TimeKeeper/src/audioFiles//audioFiles/ringtone2.wav";
        }

    }*/
}
