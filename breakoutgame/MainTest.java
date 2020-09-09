

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javafx.application.Application;
import javafx.stage.Stage;
/**
 * The test class MainTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class MainTest
{
    /**
     * Default constructor for test class MainTest
     */
    public MainTest()
    {
    }
    Main main;
    Model model;
    View view;
    Controller controller;
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        main = new Main();
        model = new Model(600,400);
        view  = new View(600,400);
        controller  = new Controller();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        model = null;
        view = null;
        controller = null;        
    }
    /**
    * Test the variables
    */
    @Test
    public void testConstructor() {
       assertEquals(600, main.H);
       assertEquals(400, main.W);
    }
}
