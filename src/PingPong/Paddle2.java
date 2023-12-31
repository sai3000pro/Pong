// Saivenkat Jilla
// May 30th, 2022
// Class used for paddle 2

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Paddle2 extends Rectangle{
	
	  public int y2Velocity;
	  public final int PADDLE2SPEED = 5; //movement speed of paddle
	  public static final int PADDLE_LENGTH = 40; //length of paddle
	  public static final int PADDLE_WIDTH = 5; // width of paddle
	  
	  //constructor creates paddle at given location with given dimensions
	  public Paddle2(int x, int y){
	    super(x, y, PADDLE_WIDTH, PADDLE_LENGTH);
	  }
	  
	  //called from GamePanel when any keyboard input is detected
	  //updates the direction of the paddle up or down based on user input
	  //if the keyboard input isn't any of the options (the arrow keys), then nothing happens
	  // checkCollision() prevents the paddle from going offscreen
	  public void keyPressed(KeyEvent e){
	     int c = e.getKeyCode();
		 if (c == KeyEvent.VK_UP) {
	      setYDirection(PADDLE2SPEED*-1);
	      move();
	    }

		 if (c == KeyEvent.VK_DOWN) {
	      setYDirection(PADDLE2SPEED);
	      move();
	    }
	  }

	  //called from GamePanel when any key is released (no longer being pressed down)
	  //Makes the paddle stop moving in that direction
	  public void keyReleased(KeyEvent e){
		  int k = e.getKeyCode();
		  if (k == KeyEvent.VK_UP) {
	      setYDirection(0);
	      move();
	    }

		  if (k == KeyEvent.VK_DOWN) {
	      setYDirection(0);
	      move();
	    }
	  }


	  //called whenever the movement of the paddle changes in the y-direction (up/down)
	  public void setYDirection(int yDirection){
	    y2Velocity = yDirection;
	  }

	  //called frequently from both Paddle2 class and GamePanel class
	  //updates the current location of the paddle
	  public void move(){
	    y = y + y2Velocity;
	  }

	  //called frequently from the GamePanel class
	  //draws the current location of paddle2 to the screen
	  public void draw(Graphics g){
	    g.setColor(Color.black);
	    g.fillRect(x, y, PADDLE_WIDTH, PADDLE_LENGTH);

	  }
	  
	}
