import javafx.scene.paint.*;
import javafx.application.Platform;

/**
 * This class is used to create a brick along with its properties.
 * It is subclass of {@link GameObj}
 */
public class Brick extends GameObj
{
  private int BRICK_WIDTH    = 50;     // Brick size
  private int BRICK_HEIGHT   = 30;
  /**
   * this constructor method is inherited from its superclass
   * @see <a href="https://stackoverflow.com/questions/13955516/java-actual-arguments-dont-match-formal-arguments-but-they-do">
   * https://stackoverflow.com/questions/13955516/java-actual-arguments-dont-match-formal-arguments-but-they-do</a>
   */
  public Brick( int x, int y, int w, int h, Color c) {
    super(x,y,w,h,c);
  }
  /**
   * Method used to change the color of a brick everytime it gets hit. 
   */
  public void changeColour(){
   if(colour==Color.GREEN){
    colour = Color.RED;
   }
   else {
    colour=Color.BLUE;
   }  
  }
  /**
   * 
   */
  public void moveBrick(int units){
     topX+=units * dirX;
     if (topX + width >= 500){
      topX= 0;
      topY+=BRICK_HEIGHT;
    }
  }
}
