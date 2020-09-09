import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import javafx.scene.paint.*;
import javafx.application.Platform;
/**
 * The test class ModelTest.
 *
 * @version (06/05/2020)
 */
public class ModelTest
{
    /**
     * Default constructor for test class ModelTest
     */
    public ModelTest()
    {
    }    
    Model model;
    int level;
    GameObj ball;    
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
     model = new Model (400, 600);  
     level = 2;
     ball = new Ball(200, 300, 15, Color.WHITE);
    }
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
      model=null;
      level=0;
      ball = null;
    }
    /**
     * Test the constructor
     */
    @Test
    public void testConstructor() {    
     assertEquals(400, model.width);
     assertEquals(600, model.height);
    }
    /**
     * Test initialiseGame.
     */
    @Test
    public void testInitialiseGame(){     
     model.initialiseGame();     
     assertEquals(3, model.life);
     assertEquals(0, model.score);
     assertFalse(model.fast);          
     assertEquals(0, model.destroyedBricks.size());    
    }       
}