

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * The test class SoundTest.
 *
 * 
 */
public class SoundTest
{
    /**
     * Default constructor for test class SoundTest
     */
    public SoundTest()
    {
    }
    Sound sound;
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    @Test(expected = RuntimeException.class)
    public void testRuntimeException()
    {
     sound = new Sound("level3.wav");  //it does not find the file as expected.   
    }
}
