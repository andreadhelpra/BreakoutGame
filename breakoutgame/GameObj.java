import javafx.scene.paint.*;
import javafx.application.Platform;

/** An object in the game with a position, a colour, and a direction of movement.
 *  It is super class of {@link Brick} and {@link Ball}
 *  @see Brick
 *  @see Ball
 */ 
public class GameObj
{
    // state variables for a game object
    public boolean visible  = true;     // Can see (change to false when the brick gets hit)
   
    protected int topX   = 0;              // Position - top left corner X
    protected int topY   = 0;              // position - top left corner Y
    protected int width  = 0;              // Width of object
    protected int height = 0;              // Height of object
    protected int radius =0;              //Radius of object             
    protected Color colour;                // Colour of object
    protected int   dirX   = 1;            // Direction X (1 or -1)
    protected int   dirY   = 1;            // Direction Y (1 or -1)
    /**
     * This creates a constructor for a round object
     * @radius this is the radius of the object, it is enough to create a circle with no position or color.
     * @see <a href="https://www.tutorialspoint.com/javafx/2dshapes_circle.htm">https://www.tutorialspoint.com/javafx/2dshapes_circle.htm</a>
     */
    public GameObj(int x, int y,int r, Color c){
     topX = x;
     topY = y;
     radius = r;
     colour = c;
    }
    /**
     * Constructor for square/rectangular object.
     */
    public GameObj( int x, int y, int w, int h, Color c) //overloading
    {
        topX  = x;       
        topY = y;
        width  = w; 
        height = h; 
        colour = c;       
    }
    
    /**
     * move in x axis
     */ 
    public void moveX( int units )
    {
        topX += units * dirX;
    }

    /**
     * move in y axis
     */ 
    public void moveY( int units )
    {
        topY += units * dirY;
    }

    /** 
     * change direction of movement in x axis (-1, 0 or +1)
     */
    public void changeDirectionX()
    {
        dirX = -dirX;
    }

    /**
     * change direction of movement in y axis (-1, 0 or +1)
     */ 
    public void changeDirectionY()
    {
        dirY = -dirY;
    }

    /** Detect collision between this object and the argument object
    * It's easiest to work out if they do NOT overlap, and then
    * return the negative (with the ! at the beginning)
    */
    public boolean hitBy( GameObj obj )
    {  
          return ! ( topX >= obj.topX+obj.width     ||
            topX+width <= obj.topX         ||
            topY >= obj.topY+obj.height    ||
            topY+height <= obj.topY 
          );      
    }      
}
