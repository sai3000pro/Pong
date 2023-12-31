// Saivenkat Jilla
// May 30th, 2022
// GamePanel class holds objects, acts as the main game loop, listens to keyboard input, and uses runnable to let the game do multiple things at once.
/* GamePanel class acts as the main "game loop" - continuously runs the game and calls whatever needs to be called

Child of JPanel because JPanel contains methods for drawing to the screen

Implements KeyListener interface to listen for keyboard input

Implements Runnable interface to use "threading" - let the game do two things at once

*/ 
import java.awt.*;
import java.awt.event.*;
import java.awt.BasicStroke;
import javax.swing.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GamePanel extends JPanel implements Runnable, KeyListener{

  //dimensions of window
  public static final int GAME_WIDTH = 500;
  public static final int GAME_HEIGHT = 500;
  
  static Clip clip; // music
  
  public boolean showingInstructions = true;
  public boolean showingTitle = true;
  public boolean victoryPlayed = false;
  
  public Thread gameThread;
  public Image image;
  public Graphics graphics;
  
  public PongBall ball;
  public Score1 score1;
  public Score2 score2;
  public Paddle1 paddle1;
  public Paddle2 paddle2;
  
  public End end;
  private Music music;


  public GamePanel(){
	// initializes objects in the pong game
	  
    ball = new PongBall((GAME_WIDTH-PongBall.BALL_DIAMETER)/2, (GAME_HEIGHT-PongBall.BALL_DIAMETER)/2); //create a ball, set start location to middle of screen
    end = new End(GAME_WIDTH, GAME_HEIGHT); // end screen
    ball.xVelocity = 5; // x velocity is 5 for a fast-paced game
    ball.yVelocity = 0; // initial y velocity is 0 so that the ball travels straight for an easy first hit
    score1 = new Score1(GAME_WIDTH, GAME_HEIGHT); //start counting the score
    score2 = new Score2(GAME_WIDTH, GAME_HEIGHT);
    paddle1 = new Paddle1(10, GAME_HEIGHT/2); // create a player controlled paddle, set start location to the left and middle of the screen
    paddle2 = new Paddle2(GAME_WIDTH-20, GAME_HEIGHT/2); // create a player controlled paddle, set start location to the right and middle of the screen
    music = new Music(); // music system

    this.setFocusable(true); //make everything in this class appear on the screen
    this.addKeyListener(this); //start listening for keyboard input
    

    this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

    //make this class run at the same time as other classes (without this each class would "pause" while another class runs). By using threading we can remove lag, and also allows us to do features like display timers in real time!
    gameThread = new Thread(this); 
    gameThread.start();
  }

  //paint is a method in java.awt library that we are overriding. It is a special method - it is called automatically in the background in order to update what appears in the window. You NEVER call paint() yourself
  public void paint (Graphics g){
    //we are using "double buffering" here - if we draw images directly onto the screen, it takes time and the human eye can actually notice flashes of lag as each pixel on the screen is drawn one at a time. Instead, we are going to draw images OFF the screen (outside dimensions of the frame), then simply move the image on screen as needed. 
    image = createImage(GAME_WIDTH, GAME_HEIGHT); //draw off screen
    graphics = image.getGraphics();
    draw(graphics); //update the positions of everything on the screen 
    g.drawImage(image, 0, 0, this); //redraw everything on the screen
  }
  
  // draws the dashed line in the middle - helps with user interface
  public void drawDashedLine(Graphics g){
	  // intializing values so that the line travels right through the middle of the window
	  int x1 = GAME_WIDTH/2 - 1;
	  int y1 = - 1;
	  int x2 = GAME_WIDTH/2 - 1;
	  int y2 = GAME_HEIGHT + 1;

      Graphics2D g2d = (Graphics2D) g;
	  Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
	  g2d.setStroke(dashed); // makes the line dashed
	  g2d.drawLine(x1, y1, x2, y2); // draws line
	}

  //call the draw methods in each class to update positions as things move
  public void draw(Graphics g){
    ball.draw(g);
    score1.draw(g);
    score2.draw(g);
    paddle1.draw(g);
    paddle2.draw(g);
    drawDashedLine(g);
	
	if (showingInstructions) { // only displays if this is true, which is only the case at startup
		instructions(g);
	}
	
	if (showingTitle) { // only displays if this is true
		title(g);
		play(g);
	}
	end.draw(g);
  }
  // showing the title
  public void title(Graphics g) {
	g.setFont(new Font("Consolas", Font.BOLD, 40));
	g.setColor(Color.red);
	g.drawString("P", GAME_WIDTH/2 - 80, 60);
	g.setColor(Color.green);
	g.drawString("O", GAME_WIDTH/2 - 40, 60);
	g.setColor(Color.cyan);
	g.drawString("N", GAME_WIDTH/2 + 20, 60);
	g.setColor(Color.pink);
	g.drawString("G", GAME_WIDTH/2 + 60, 60);
}
	//press space to begin/restart/continue
	public void play(Graphics g) {
		g.setColor(Color.black);
		g.setFont(new Font("Consolas", Font.BOLD, 25));
		if (Score1.score == 0 && Score2.score == 0) {
			g.drawString("Press space to begin", GAME_WIDTH/2 - 155, 400);
		}
		else if (Score1.score == 5 || Score2.score == 5) {
			g.drawString("Press space to restart", GAME_WIDTH/2 - 166, 400);
		}
		else {
			g.drawString("Press space to continue", GAME_WIDTH/2 - 170, 400);
		}
	}
	//shows instructions
	public void instructions(Graphics g) {
		g.setFont(new Font("Consolas", Font.BOLD, 15));
		g.setColor(Color.black);
		g.drawString("Player 1: ", 20, 200);
		g.drawString("W to go up", 20, 240);
		g.drawString("S to go down", 20, 280);
		g.drawString("Player 2: ", 400, 200);
		g.drawString("Arrow Keys", 400, 240);
	}
	
  //call the move methods in other classes to update positions
  //this method is constantly called from run(). By doing this, movements appear fluid and natural. If we take this out the movements appear sluggish and laggy
  public void move(){
	if (showingTitle == false) { // doesn't allow ball to move if the game is on the title screen
		ball.move();
	}
    paddle1.move();
    paddle2.move();
  }

  //handles all collision detection and responds accordingly
  public void checkCollision() {

    //force players and ball to remain on screen
	if (paddle1.y<=0) {
		paddle1.y=0;
	}
	if (paddle2.y<=0) {
		paddle2.y=0;
	}
	if (paddle1.y>=GAME_HEIGHT - Paddle1.PADDLE_LENGTH) {
		paddle1.y=GAME_HEIGHT - Paddle1.PADDLE_LENGTH;
	}
	if (paddle2.y>=GAME_HEIGHT - Paddle2.PADDLE_LENGTH) {
		paddle2.y=GAME_HEIGHT - Paddle2.PADDLE_LENGTH;
	}
    if(ball.y <= 0){ // if ball hits the top wall
    	ball.y = 1; // prevents glitches
    	music.playMusic("Tap.wav"); // plays particular music
    	ball.setYDirection(ball.yVelocity*-1); // ball now travels away from the top of the wall
    }
    
    if(ball.y >= GAME_HEIGHT - PongBall.BALL_DIAMETER){ // if ball hits bottom wall
    	ball.y = GAME_HEIGHT - PongBall.BALL_DIAMETER - 1; // prevents glitches
    	music.playMusic("Tap.wav");
    	ball.setYDirection(ball.yVelocity*-1); // ball now travels away from the bottom of the wall
    }
    if(ball.x == (0 - PongBall.BALL_DIAMETER)){ // if ball has passed the left wall
      Score2.score++; // updates score
      music.playMusic("Boop.wav"); // plays particular sound effect
   	  ball.x = GAME_WIDTH/2 - PongBall.BALL_DIAMETER; // places ball so that is on the side of the losing player
   	  ball.y = GAME_HEIGHT/2;
      showingTitle = true; // pauses game
 	  ball.setXDirection(-5); // happens after game is resumed
 	  ball.setYDirection(0); // ball goes straight to the paddle for an easy first hit
  	
    }
    if(ball.x == GAME_WIDTH){ // if ball has passed the right wall
    	Score1.score++;
    	music.playMusic("Boop.wav");
   	  	ball.x = GAME_WIDTH/2;
   	  	ball.y = GAME_HEIGHT/2;
        showingTitle = true;
   	  	ball.xVelocity= 5;
   	  	ball.yVelocity = 0;
    }
      
    if(ball.intersects(paddle1)){ // if ball intersects left paddle
    	ball.setXDirection(5);
    	ball.setYDirection((ball.y + PongBall.BALL_DIAMETER/2 - (paddle1.y + Paddle1.PADDLE_LENGTH/2))/5); // y velocity depends on where ball hits the paddle
    	music.playMusic("Tap.wav");
    }
    
    if(ball.intersects(paddle2)){
    	ball.setXDirection(-5);
    	ball.setYDirection((ball.y + PongBall.BALL_DIAMETER/2 - (paddle2.y + Paddle2.PADDLE_LENGTH/2))/5); // y velocity depends on where ball hits the paddle
    	music.playMusic("Tap.wav");
    } 

    if (Score1.score == 5 || Score2.score == 5) { // if someone has won
    	if (victoryPlayed == false) {
    	music.playMusic("Victory.wav"); // plays only once
    	victoryPlayed = true;
    	}
    	if (ball.x == GAME_WIDTH/2 && ball.y == GAME_HEIGHT/2 && victoryPlayed == true) { // happens after sound is played
    	showingTitle = true;
		}    
    }
  }
  //run() method is what makes the game continue running without end. It calls other methods to move objects,  check for collision, and update the screen
  public void run(){
    //the CPU runs our game code too quickly - we need to slow it down! The following lines of code "force" the computer to get stuck in a loop for short intervals between calling other methods to update the screen. 
    long lastTime = System.nanoTime();
    double amountOfTicks = 60;
    double ns = 1000000000/amountOfTicks;
    double delta = 0;
    long now;

    while(true){ //this is the infinite game loop
      now = System.nanoTime();
      delta = delta + (now-lastTime)/ns;
      lastTime = now;

      //only move objects around and update screen if enough time has passed
      if(delta >= 1){
        move();
        checkCollision();
        repaint();
        delta--;
      }
    }
  }

  //if a key is pressed, we'll send it over to the PlayerBall class for processing
  public void keyPressed(KeyEvent e){
	  //starts game after space is pressed
		if(e.getKeyChar()==' ') {
			if (Score1.score == 0 && Score2.score == 0) {
				showingInstructions = false; // only displays on startup		
				}
			else if (Score1.score == 5 || Score2.score == 5) {
				// resets everything
				Score1.score = 0;
				Score2.score = 0;
				victoryPlayed = false;
				}
		showingTitle = false;
  }
    paddle1.keyPressed(e);
    paddle2.keyPressed(e);
  }

  //if a key is released, we'll send it over to the PlayerBall class for processing
  public void keyReleased(KeyEvent e){
    paddle1.keyReleased(e);
    paddle2.keyReleased(e);
  }

  //left empty because we don't need it; must be here because it is required to be overridden by the KeyListener interface
  public void keyTyped(KeyEvent e){

  }

}