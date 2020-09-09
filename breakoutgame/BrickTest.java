import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javafx.scene.paint.*;
import javafx.application.Platform;
/**
 * The test class BrickTest.
 *
 * @version (a version number or a date)
 */
public class BrickTest
{
    /**
     * Default constructor for test class BrickTest
     */
    public BrickTest()
    {
    }
    Brick b;
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        b= new Brick(65, 100, 50, 30, Color.GREEN);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        b=null;
    }
    /**
     * Test constructor.
     */
    @Test
    public void testConstructor()
    {
        assertEquals(b.topX, 65);
        assertEquals(b.topY, 100);
        assertEquals(b.width, 50);
        assertEquals(b.height, 30);
        assertEquals(b.colour,Color.GREEN);
    }
    /**
     * Test change colour method.
     */
    @Test
    public void testChangeColour()
    {
        b.changeColour();
        assertEquals(Color.RED, b.colour);
        
        Brick brick = new Brick (65, 100, 50, 30, Color.RED);
        brick.changeColour();
        assertEquals(Color.BLUE, brick.colour);
    }
}
