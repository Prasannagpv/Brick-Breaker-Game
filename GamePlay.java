import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.*;
//import java.util.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener{

    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int  delay = 5;

    private int playerX = 310;//STARTING POSISION OF THE SLIDER

    private int ballposX = 120;//X POSISION OF THE BALL
    private int ballposY = 350;//Y POSISION OF THE BALL
    private int ballXdir =  5;// X direction speed of ball
    private int ballYdir = -5;// Y direction speed of ball

    private MapGenerator map;

    GamePlay(){
        
        map = new MapGenerator(3,7);// Generates the bricks
        this.addKeyListener(this);
        //this.addActionListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.setBackground(Color.black);
        timer = new Timer(delay, this);
        timer.start();

    }

    public void paint(Graphics g)
    {   
        super.paint(g);

        //background
        g.setColor(Color.BLACK);
        g.fillRect(1,1,692,592);

        //drawing map
        map.draw((Graphics2D) g);

        //borders
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(682,0,3,592);

        //paddel
        g.setColor( Color.GREEN);
        g.fillRect(playerX, 550,100,8);

        //ball
        g.setColor( Color.BLUE);
        g.fillOval(ballposX, ballposY,20,20);

        //score
        g.setColor(Color.GRAY);
        g.setFont(new Font("serif", Font.BOLD,25));
        g.drawString("Score: " + score, 10, 30);

        if(ballposY > 570)
        {
            play = false;
            ballXdir=0;
            ballYdir=0;

            g.setColor(Color.MAGENTA);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("Game Over , Scores: "+score , 190, 300);

            g.setColor(Color.MAGENTA);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("Press ENTER to Restart" , 180, 350);
        }

        //all the bricks are over
        if(totalBricks<=0)
        {
            play = false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.MAGENTA);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("You Won, Scores: "+score , 190, 300);

            g.setColor(Color.MAGENTA);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("Press ENTER to Restart" , 180, 350);
        }

        g.dispose(); // Dispose the graphics context


    }

    //public void start(){
//
    //}

    public void moveRight(){
        play =true;
        playerX += 30;
    }

    public void moveLeft(){
        play =true;
        playerX -= 30;
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){

            // bounce back from the paddle
            if(new Rectangle(ballposX, ballposY,20,20).intersects(new Rectangle(playerX, 550,100,8)))
            {
                ballYdir = -ballYdir;
                ballXdir = +ballXdir;
            }
//
            // bounce back from the bricks
            for(int i=0 ; i<map.map.length ; i++)
            {
                for(int j=0 ; j<map.map[0].length;j++)
                {
                    if (map.map[i][j] > 0)
                    {
                      int brickX = j * map.brickWidth +80;
                      int brickY = i * map.brickHeight +50;
                      int brickWidth = map.brickWidth;
                      int brickHeight = map.brickHeight;

                    
                      Rectangle rect = new Rectangle(brickX, brickY,brickWidth,brickHeight);
                      Rectangle ballRect = new Rectangle(ballposX, ballposY, 20,20);
                      Rectangle brickRect = rect;

                      if(ballRect.intersects(brickRect))
                      {
                        map.setBrickValue(0, i, j);
                        totalBricks--;
                        score += 5;


                        if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width)
                        {
                            ballXdir = -ballXdir;
                        }
                        else{
                            ballYdir = -ballYdir;
                        }

                      }


                    }
                }
            }


            // BOUNCE BACK FROM THE WALLS
            ballposX += ballXdir;
            ballposY += ballYdir;
            if(ballposX <0){
                ballXdir=-ballXdir;
            }
            if(ballposY <0){
                ballYdir=-ballYdir;
            }
            if(ballposX > 670){
                ballXdir=-ballXdir;
            }
        }

        repaint();
    }

    
    

    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode()==KeyEvent.VK_RIGHT)
       {
        if(playerX >= 600)
        {
            playerX = 600;
        }
        else {
            moveRight();
        }
       }
       if(e.getKeyCode()==KeyEvent.VK_LEFT)
       {
        if(playerX < 10)
        {
            playerX = 10;
        }
        else {
            moveLeft();
        }

       }
       if(e.getKeyCode()==KeyEvent.VK_ENTER)
       {
            if(!play){
               play = true; 
               ballXdir = 5;
               ballYdir = -5;
               ballposX = 120;
               ballposY = 350;
               playerX = 310;
               score   =0;

               totalBricks = 21;

               map = new MapGenerator(3,7);

                repaint();
            }
       }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    


}
