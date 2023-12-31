// Saivenkat Jilla
// May 30th, 2022
// Class used for paddle 1

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Paddle1 extends Rectangle{
	
	  public int y1Velocity;
	  public final int PADDLE1SPEED = 5; //movement speed of paddle
	  public static final int PADDLE_LENGTH = 40; //length of paddle
	  public static final int PADDLE_WIDTH = 5; // width of paddle
	  
	  //constructor creates paddle at given location with given dimensions
	  public Paddle1(int x, int y){
	    super(x, y, PADDLE_WIDTH, PADDLE_LENGTH);
	  }
	  
	  //called from GamePanel when any keyboard input is detected
	  //updates the direction of the paddle up or down based on user input
	  //if the keyboard input isn't any of the options (d, a, w, s), then nothing happens
	  // checkCollision() prevents the paddle from going offscreen
	  public void keyPressed(KeyEvent e){
	    if(e.getKeyChar() == 'w'){
	      setYDirection(PADDLE1SPEED*-1);
	      move();
	    }

	    if(e.getKeyChar() == 's'){
	      setYDirection(PADDLE1SPEED);
	      move();
	    }
	  }

	  //called from GamePanel when any key is released (no longer being pressed down)
	  //Makes the paddle stop moving in that direction
	  public void keyReleased(KeyEvent e){
	    if(e.getKeyChar() == 'w'){
	      setYDirection(0);
	      move();
	    }

	    if(e.getKeyChar() == 's'){
	      setYDirection(0);
	      move();
	    }
	  }

	  //called whenever the movement of the paddle changes in the y-direction (up/down)
	  public void setYDirection(int yDirection){
	    y1Velocity = yDirection;
	  }

	  //called frequently from both Paddle1 class and GamePanel class
	  //updates the current location of the paddle
	  public void move(){
	    y = y + y1Velocity;
	  }

	  //called frequently from the GamePanel class
	  //draws the current location of paddle1 to the screen
	  public void draw(Graphics g){
	    g.setColor(Color.black);
	    g.fillRect(x, y, PADDLE_WIDTH, PADDLE_LENGTH);

	  }
	  
	}
