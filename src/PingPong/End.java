//End class handles the end condition
//child of Rectangle because it makes it easy to format and draw to the screen
import java.awt.*;

public class End extends Rectangle{

  public static int GAME_WIDTH;//width of the window
  public static int GAME_HEIGHT;//height of the window

  //constructor establishes dimensions of game window
  public End(int w, int h){
    End.GAME_WIDTH = w;
    End.GAME_HEIGHT = h;
  }

  //called from GamePanel class
  //announces who won at the end
  public void draw(Graphics g){
    g.setFont(new Font("Consolas", Font.PLAIN, 30));
    if (Score1.score == 5) {
        g.setColor(Color.green);
        g.drawString("WIN", (int)(GAME_WIDTH*0.22), (int)(GAME_HEIGHT*0.35)); 
        g.setColor(Color.red);
        g.drawString("LOSE", (int)(GAME_WIDTH*0.64), (int)(GAME_HEIGHT*0.35)); 
    }
    if (Score2.score == 5) {
        g.setColor(Color.red);
        g.drawString("LOSE", (int)(GAME_WIDTH*0.22), (int)(GAME_HEIGHT*0.35)); 
        g.setColor(Color.green);
        g.drawString("WIN", (int)(GAME_WIDTH*0.65), (int)(GAME_HEIGHT*0.35)); 
    }
  }
}
