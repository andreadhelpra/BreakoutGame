import javafx.scene.paint.*;
import javafx.application.Platform;
/**
 * This class is used to create a ball along with its properties.
 * It is subclass of {@link GameObj}
 */
public class Ball extends GameObj
{
   // instance variables - replace the example below with your own
    public Ball( int x, int y, int r, Color c) {
    super(x,y,r,c);
   }
   /**
    * hitBy method for the Ball. Similar to the hitBy method for the other objects, 
    * with the only difference that in this case topX is the centre of the ball so it needs the radius to calculate its extremity.
    */
    public boolean hitBy( GameObj obj ) //overriding hitBy method to adapt it to ball shape
   {  
          return ! ( topX - radius >= obj.topX+obj.width     ||
            topX+width + radius<= obj.topX         ||
            topY - radius >= obj.topY+obj.height    ||
            topY+height + radius <= obj.topY 
          );      
   }
}
