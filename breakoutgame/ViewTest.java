import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javafx.application.Application;
import javafx.event.ActionEvent;
import java.util.ArrayList;
import java.util.Vector;
import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.beans.value.ObservableValue;
/**
 * The test class ViewTest.
 * @version (06/05/2020)
 */
public class ViewTest
{
    /**
     * Default constructor for test class ViewTest
     */
    public ViewTest()
    {
    }
    View view;
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
     view = new View(400, 600);    
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        view=null;
    }
    
    /**
     * test the constructor method
     */
    @Test
    public void testConstructor(){
     assertEquals(400, view.width);
     assertEquals(600, view.height);  
    } 
}
