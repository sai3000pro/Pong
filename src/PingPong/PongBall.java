// Saivenkat Jilla
// May 30th, 2022
// This class handles the ball in the game
/* Ball class defines behaviours for the ball
child of Rectangle because that makes it easy to draw and check for collision
In 2D GUI, basically everything is a rectangle even if it doesn't look like it!
*/

import java.awt.*;
import java.awt.event.*;

public class PongBall extends Rectangle{
	
	  public int yVelocity; // x velocity of the ball
	  public int xVelocity; // y velocity of the ball
	  public static final int BALL_DIAMETER = 10; //size of ball

	  //constructor creates ball at given location with given dimensions
	  public PongBall(int x, int y){
	    super(x, y, BALL_DIAMETER, BALL_DIAMETER);
	  }
	  
	  //called whenever the movement of the ball changes in the y-direction (up/down)
	  // happens when it hits a paddle or the top or bottom of the screen
	  public void setYDirection(int yDirection){
	    yVelocity = yDirection;
	  }

	  //called whenever the movement of the ball changes in the x-direction (left/right)
	  // happens when it hits a paddle or the ball is dropped
	  public void setXDirection(int xDirection){
	    xVelocity = xDirection;
	  }

	  //called frequently from both PlayerBall class and GamePanel class
	  //updates the current location of the ball
	  public void move(){
	    y = y + yVelocity;
	    x = x + xVelocity;
	  }

	  //called frequently from the GamePanel class
	  //draws the current location of the ball to the screen
	  public void draw(Graphics g){
	    g.setColor(Color.black);
	    g.fillOval(x, y, BALL_DIAMETER, BALL_DIAMETER);

	  }
	  
	  
}
