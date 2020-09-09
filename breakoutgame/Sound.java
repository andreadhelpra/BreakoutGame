import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * This class deals with playing, stopping, and looping of background music for the game.
 * @see royalty free music - <a href= "https://www.bensound.com/royalty-free-music/cinematic">https://www.bensound.com/royalty-free-music/cinematic</a>
 */
public class Sound {
    private Clip clip;
      /** This is the constructor method of this class, it asks for a .wav file and then creates a clip which will be played in a loop. 
       * It throws some debugging messages in case the operation does not run as expected
       * @param fileName this is the name of the file that will be played. It has to be a .wav file
         * @throws RuntimeException if file is not found
         * @throws MalformedURLException if the URL is malformed
         * @throws UnsupportedAudioFileException if the file is not supported
         * @throws IOException if an input or output exception occurs
         * @throws LineUnavailableException if line cannot be opened because it is unavailable
         * @see <a href="https://stackoverflow.com/questions/11919009/using-javax-sound-sampled-clip-to-play-loop-and-stop-multiple-sounds-in-a-game">
         * https://stackoverflow.com/questions/11919009/using-javax-sound-sampled-clip-to-play-loop-and-stop-multiple-sounds-in-a-game</a>
         */
    public Sound(String fileName) {      
        try {
            File file = new File(fileName);
            if (file.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(sound);
            }
            else {
                throw new RuntimeException("Sound: file not found: " + fileName);
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Malformed URL: " + e);
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }       
    }
    /**
     *  it allows to play the clip
     */
    public void play(){
        clip.start();
    }
    /**
     *  it pauses the clip
     */
    public void stop(){
        clip.stop();
    }
    /** 
     * it arrests the clip
     */
    public void close(){
        clip.close();    
    }
  }