// Saivenkat Jilla
// May 30th, 2022
//Score2 class handles the game score for the player on the right
//child of Rectangle because it makes it easy to format and draw to the screen
import java.awt.*;

public class Score2 extends Rectangle{

  public static int GAME_WIDTH;//width of the window
  public static int GAME_HEIGHT;//height of the window
  public static int score;

  //constructor sets score to 0 and establishes dimensions of game window
  public Score2(int w, int h){
    score = 0;
    Score2.GAME_WIDTH = w;
    Score2.GAME_HEIGHT = h;
  }

  //called frequently from GamePanel class
  //updates the current score and draws it to the screen
  public void draw(Graphics g){
    g.setColor(Color.black);
    g.setFont(new Font("Consolas", Font.PLAIN, 60));
    g.drawString(String.valueOf(score), (int)(GAME_WIDTH*0.68), (int)(GAME_HEIGHT*0.2)); //setting location of score to be about the middle of player 2's side
  }
}
