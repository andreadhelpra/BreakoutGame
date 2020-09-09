import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javafx.scene.paint.*;
import javafx.application.Platform;
/**
 * The test class GameObjTest.
 * @version (06/05/2020)
 */
public class GameObjTest
{
    /**
     * Default constructor for test class GameObjTest
     */
    public GameObjTest()
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
        ball = new GameObj(200, 300, 15, Color.WHITE);
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
     * Test the first constructor
     */
    @Test
    public void testConstructor1() {
    GameObj b = new GameObj (100,100, 50, 50, Color.RED);
    assertEquals(100, b.topX);
    assertEquals(100, b.topY);
    assertEquals(50, b.width);
    assertEquals(50, b.height);
    assertEquals(Color.RED, b.colour);
    }
    /**
     * Test the second constructor
     */
    @Test
    public void testConstructor2() {
    GameObj c = new GameObj (100,100, 50, Color.RED);
    assertEquals(100, c.topX);
    assertEquals(100, c.topY);
    assertEquals(50, c.radius);
    assertEquals(Color.RED, c.colour);
    }
    /**
    * Test the moveX method
    */
    @Test
    public void testMoveX() {
    bat.moveX(5);
    assertEquals(205, bat.topX);
    }
    /**
    * Test the moveY method
    */
    @Test
    public void testMoveY() {
    bat.moveY(5);
    assertEquals(105, bat.topY);
    }
    /**
    * Test the changeDirectionX method
    */
    @Test
    public void testChangeDirectionX() {
    bat.changeDirectionX();
    assertEquals(-1, bat.dirX);    
    }
    /**
     * Test the changeDirectionY method
     */
    @Test
    public void testChangeDirectionY() {
    bat.changeDirectionY();
    assertEquals(-1, bat.dirY);    
    }
    /**
     * Test the hitBy method
     */
    @Test
    public void testHitBy1() {     
     ball = new GameObj(70, 70, 15, 15, Color.WHITE);   //square ball hits right side of the brick
     assertTrue(brick.hitBy(ball));
     
     GameObj go = new GameObj(1, 1, 15, 15, Color.WHITE);   //go does not hit side of the brick
     assertFalse(brick.hitBy(go));
    }    
}
