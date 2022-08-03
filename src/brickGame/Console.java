package brickGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * JPanel is basically for our layouts. it is a container that can store a group of components. JPanel helps
 * us organize the different components that we use
 *
 * KEYLISTENER-FOR ANY BUTTON WE PRESS,THE KEYLISTENER LISTEN TO IT AND ATTRACT CHANGES
 * ACTION LISTENER-ActionListener in Java is a class that is responsible for handling all
 * action events such as when the user clicks on a component,similar to key listener but not same.
 */

public class Console extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;

    private int score = 0;

    private int totalBricks = 25;

    private int delay = 8;

    private Timer timer; //timer is an util class that is used to schedule tasks in the background

    private int playerX = 310;

    private int ballposX = 120;

    private int ballposY = 350;

    private int ballXdir = -1;

    private int ballYdir = -2;

    private Mapper mapper;

    public Console(){
        mapper = new Mapper(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        timer = new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics graphics){
        //background
        graphics.setColor(Color.green);
        graphics.fillRect(1,1,692,592);

        //drawing map
        mapper.draw((Graphics2D)graphics);

        //borders
        graphics.setColor(Color.GRAY);
        graphics.fillRect(0,0,3,592);
        graphics.fillRect(0,0,692,3);
        graphics.fillRect(691,0,3,592);

        //scores
        graphics.setColor(Color.white);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString(""+score, 590, 30);

        //paddle
        graphics.setColor(Color.black);
        graphics.fillRect(playerX, 550, 100, 8);

        //ball
        graphics.setColor(Color.MAGENTA);
        graphics.fillOval(ballposX, ballposY,20,20);

        if(totalBricks <= 0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("serif",Font.BOLD, 30));
            graphics.drawString("You won!",190,300);
        }

        if(ballposY > 570){
            play =false;
            ballXdir = 0;
            ballYdir = 0;
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString("Game over Dummy!!!, Scores: "+score,190, 300);

            graphics.setFont(new Font("serif", Font.BOLD, 20));
            graphics.drawString("Press Enter to Restart", 230, 350);
        }
        graphics.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballposX, ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ballYdir = -ballYdir;
            }

            A: for(int i= 0; i<mapper.map.length; i++){
                for(int j = 0; j<mapper.map[0].length; j++){
                    if(mapper.map[i][j] > 0){
                        int brickX = j * mapper.brickBreakerWidth + 80;
                        int brickY = i * mapper.brickBreakerHeight + 50;
                        int brickWidth = mapper.brickBreakerWidth;
                        int brickHeight = mapper.brickBreakerHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)){
                            mapper.setBrickValue(0,i,j);
                            totalBricks--;
                            score += 5;

                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            }else{
                                ballYdir = -ballYdir;
                            }

                            break A;
                        }
                    }
                }
            }
            ballposX += ballXdir;
            ballposY += ballYdir;
            if(ballposX < 0){
                ballXdir = -ballXdir;
            }
            if(ballposY < 0){
                ballYdir = - ballYdir;
            }
            if(ballposX > 670){
                ballXdir = - ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 600){
                playerX = 600;
            }else{
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX < 10){
                playerX = 10;
            }else{
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                mapper = new Mapper(3, 7);

                repaint();
            }
        }
    }

    public void moveRight(){
        play = true;
        playerX += 20;
    }
    public void moveLeft(){
        play = true;
        playerX -= 20;
    }
}

