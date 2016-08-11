import java.awt.AWTException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.*; //fk it. yolo import everything.
import java.util.Timer;
import java.util.TimerTask;


public class AutoAlchemy implements Runnable, ActionListener
{
   public static Thread thread;
   public static int count = 0;
   public static boolean running = false;
   public static JFrame frame = new JFrame("ALCHING");
   public static JPanel panel = new JPanel();
   public static JButton alchStart = new JButton("Alch START");
   public static JButton alchStop = new JButton("Alch STOP");
   public static JButton Exit = new JButton("EXIT");

   public static int CPS = 500;
   public static double CPStimer = 1000000000.0D / CPS;
   public static final int width = 190;
   public static final int height = 200;
   public static final int scale = 3;
   public static AutoAlchemy main;
   public static Robot robot;
   public static boolean alching;
   private static int rate = 0;
   public final long start; //start time
   public static int seconds = 0;//secnods used
   public static int minutes = 0;//minutes used
   public static int hours = 0;//hours used
   
   public static JLabel alchLabel = new JLabel("Alching: "+alching);
   //public static JLabel timerLabel = new JLabel("Time running: "+hours+":"+minutes+":"+seconds);

   public static void main(String[] args)
   {
      frame = new JFrame("nothing :^)");
      main = new AutoAlchemy();
      frame.setVisible(true);
      frame.setLocation(0, 0);
      frame.isFocusableWindow();
      panel.setBorder(new LineBorder(Color.BLACK));
      frame.getContentPane().add(panel, "Center");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      main.alchStart();
      new AutoAlchemy();
   }

//Setting the buttons and labels in the right places
   public AutoAlchemy()
   {
      start = System.currentTimeMillis();
           
      frame.add(panel);
      panel.setLayout(null);
      panel.setBackground(Color.GREEN);
      frame.setSize(180, 180);
      frame.setVisible(true);
   
      Dimension size = new Dimension(190, 200);
      frame.setPreferredSize(size);
      frame.setMaximumSize(size);
      frame.setMinimumSize(size);
      frame.setResizable(false);
      frame.add(panel);
      panel.setLayout(null);
      panel.setBackground(Color.GREEN);
   
      panel.add(alchStart);
      alchStart.setBounds(10, 50, 155, 20);
   
      panel.add(alchStop);
      alchStop.setBounds(10, 80, 155, 20);
      
      panel.add(Exit);
      Exit.setBounds(45, 110, 80, 20);
      
      panel.add(alchLabel);
      alchLabel.setBounds(0,0,100,20);
      
      //panel.add(timerLabel);
      //timerLabel.setBounds(0,20,150,20);
   
      alchStart.addActionListener(this);
      alchStop.addActionListener(this);
      Exit.addActionListener(this);
   }

   public void alchStart()
   {
      if (running)
         return;
      running = true;
      thread = new Thread(this);
      thread.start();
   }

   public void alchStop()
   {
      if (!running)
         return;
      running = false;
      try
      {
         thread.join();
      }
      catch (InterruptedException e)
      {
         e.printStackTrace();
      }
   }
      
   public void run() //getting the time of the computer to run the alch method a certain amount of times
   {
      long lastTime = System.nanoTime();
      double delta = 0.0D;
      long time;
      while (running)
      {
         long now = System.nanoTime();
         delta += (now - lastTime) / CPStimer;
         lastTime = now;
         
         while (delta >= 1.0D)
         {
            alch();
            delta -= 1.0D;
         }
         
         //timerLabel.setText("Time running: "+hours+":"+minutes+":"+seconds);
      }
   }


//Alching
//The randomness of the time between clicks is a 2 way random selection
//the maximum and minimum amount of time spent between clicks is random
//as well as the time in between clicks, making it even harder to detect.
//Humanized clicking is also implemented to make the mouse click longer instead of an instant click.
   private void alch()
   {
   //random formula (int)(Math.random() * (max - min) + min)
   
      int randomClickTime; //random time between clicks
      int randomClickPressTime; //random time to press the mouse down
      int randomClickReleaseTime; //random time to release the mouse click
     
      //These are done because random intervals of time inbetween is better for the bot to be undetectable
      int randMax; 
      int randMin;
      if (this.alching == true) //random Alch timer to bypass anti-bot
      {
         try
         {
            Robot robot = new Robot();
            while (this.alching == true)
            {
               try
               {
                  randMax  = (int)(Math.random() * (2200 - 1800) + 1800); //maximum amount of time between clicks between 2200MS(2.2 seconds) and 1800MS(1.8 seconds)
                  randMin = (int)(Math.random() * (500 - 300) + 300); //minimum amount of time between clicks between 300MS(0.3 seconds) and 500MS(0.5 seconds)
                  randomClickTime = (int)(Math.random() * (randMax - randMin) + randMin); //random number between the designated max and min numbers
                  randomClickPressTime = (int)(Math.random() * (100 - 20) + 20);
                  randomClickReleaseTime = (int)(Math.random() * (20 - 10) + 10);
                  rate = randomClickTime; //makes the rate the click time
                  Thread.sleep(rate); //skipping time in between clicks
                  
                  System.out.println("ClickTime: "+randomClickTime+" PressTime: "+randomClickPressTime+" ReleaseTime: "+randomClickReleaseTime);
                  
                  //Humanized clicking
                  robot.mousePress(16);
                  Thread.sleep(randomClickPressTime);//mouse pressing for certain amount of time
                  robot.mouseRelease(16);
                  Thread.sleep(randomClickReleaseTime);//mouse releasing for certain amount of time
               }
               catch (InterruptedException ex) {
               }
            }
         }
         catch (AWTException i) {
         }
         frame.setAlwaysOnTop(true);
      }
      //timerLabel.setText("Time running: "+hours+":"+minutes+":"+seconds);
   }

   //updates every time an action (button press) is performed
   public void actionPerformed(ActionEvent e)
   {
      if (e.getSource() == alchStart) //Start alching if button has been clicked
      {
         this.alching = true;
      }
      else if (e.getSource() == alchStop) //Stop alching if button has been clicked
      {
         this.alching = false;
      }
      else if (e.getSource() == Exit) //Close the program if button has been clicked
      {
         System.exit(0);
      }
      alchLabel.setText("Alching: "+alching); //Updating the alch label depending if the user is alching or not
   }
}