import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javafx.scene.paint.*;
import javafx.application.Platform;
/**
 * The test class BallTest.
 *
 * @version (a version number or a date)
 */
public class BallTest
{
    /**
     * Default constructor for test class BallTest
     */
    public BallTest()
    {
    }
    GameObj bat;
    GameObj ball;
    GameObj brick; 
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        bat    = new GameObj(200, 100, 150, 8, Color.BLUE);
        ball = new Ball(200, 300, 15, Color.WHITE);
        brick = new GameObj(65, 60, 50, 30, Color.RED);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
       bat=null;
       ball=null;
       brick=null;
    }
    /**
     * Test the constructor
     */
    @Test
    public void testConstructor() {
    GameObj b = new Ball (100,100, 50, Color.RED);
    assertEquals(100, b.topX);
    assertEquals(100, b.topY);
    assertEquals(50, b.radius);
    assertEquals(Color.RED, b.colour);
    }
    /**
     * Test the hitBy method
     */
    @Test
    public void testHitBy() {
     ball = new Ball(60, 65, 10, Color.WHITE);   //brick hits side of the ball
     assertTrue(ball.hitBy(brick));
          
     Ball b = new Ball(30, 20, 10, Color.WHITE);   //brick does not hit side of the ball
     assertFalse(b.hitBy(brick));
    }  
}
