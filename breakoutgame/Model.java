import java.util.ArrayList;
import java.util.Vector;
import javafx.scene.paint.*;
import javafx.application.Platform;
/**
 * This class represents the content and functionality of the app
 */
/** The model represents all the actual content and functionality of the app
 *For Breakout, it manages all the game objects that the View needs
 *(the bat, ball, bricks, and the score), provides methods to allow the Controller
 *to move the bat (and a couple of other fucntions - change the speed or stop 
 *the game), and runs a background process (a 'thread') that moves the ball 
 * every 20 milliseconds and checks for collisions 
 * @param destroyedBricks it creates a Vector of invisible bricks and it is used for changing level once the size of it
 * matches the size of the total bricks. Being a Vector instead of an ArrayList makes it 'thread-safe'; 
 * therfore, there is no need to manually synchronize it.
 */
public class Model 
{
    // First,a collection of useful values for calculating sizes and layouts etc.

    private int B              = 6;      // Border round the edge of the panel
    private int M              = 40;     // Height of menu bar space at the top

    private int BALL_SIZE      = 15;     // Ball radius
    private int BRICK_WIDTH    = 50;     // Brick size
    private int BRICK_HEIGHT   = 30;

    private int BAT_MOVE       = 5;      // Distance to move bat on each keypress
    private int BALL_MOVE      = 3;      // Units to move the ball on each step

    private int HIT_BRICK      = 50;     // Score for hitting a brick
    private int HIT_BOTTOM     = -200;   // Score (penalty) for hitting the bottom of the screen

    // The other parts of the model-view-controller setup and music
    View view;
    Controller controller;
   
    // The game 'model' - these represent the state of the game
    // and are used by the View to display it
    private GameObj ball;                // The ball
    protected ArrayList<Brick> bricks;   // The bricks
    private GameObj bat;                 // The bat
    private Sound sound;
    private Sound effect;
    protected int score;               // The score
    protected int lastScore;           // The score at the end of every level
    protected Vector destroyedBricks;     // The destroyed bricks
    protected int life = 3;                // life
    protected int level = 1;
    // variables that control the game 
    private boolean gameRunning = true;  // Set false to stop the game
    protected boolean fast = false;        // Set true to make the ball go faster

    // initialisation parameters for the model
    protected int width;                   // Width of game
    protected int height;                  // Height of game

    // CONSTRUCTOR - needs to know how big the window will be
    public Model( int w, int h )
    {
        Debug.trace("Model::<constructor>");  
        width = w; 
        height = h;        
    }

    // Initialise the game - reset the score, create the game objects, reset life, set level
    /**
     *
     * This method is first called by the {@link Main#start(Stage window) Main} class but the objects do not display until
     * the startGame method is called from the {@link View#start(Stage window) View} when the start button is pressed
     */
    public void initialiseGame()
    {       
            life = 3;// reset life every time game restarts            
            ball   = new Ball(width/2, height/2, BALL_SIZE, Color.WHITE);
            bat    = new GameObj(width/2, height - BRICK_HEIGHT*3/2, BRICK_WIDTH*3, BRICK_HEIGHT/4, Color.WHITE);
            bricks = new ArrayList<>();
            destroyedBricks = new Vector(); //reset destroyed bricks every time game restarts
        // *[1]******************************************************[1]*
        // * Fill in code to add the bricks to the arrayList            *
        // **************************************************************                     
       
       //set up level
       switch (level){
        case 1:        
         firstLevel();     
         sound = new Sound ("level1.wav");
         score = 0; // the score only resets to zero at level 1
         setFast(false);
         break;
        
        case 2:
         secondLevel();
         sound= new Sound ("level2.wav");
         score=lastScore; // the initial score equals the one reached when level 1 was completed.
         setFast(true);
         break;
       }
       sound.play(); // play music
    }
    /**
     * This method tells how and how many bricks should be displayed for the first level. It is separate from the initialiseGame method so that it can be modified more easily.
     */
    public void firstLevel() {              
       for (int r=1; r<4;r++){
         if (r==1){
          for (int i=0; i<6; i++){
           Brick brick = new Brick(i * 65 + B, r * 60, BRICK_WIDTH, BRICK_HEIGHT, Color.RED);
           bricks.add(brick);           
          }
         }
         else {
           for (int i=0; i<4; i++){
            Brick brick = new Brick(i * 65 + BRICK_WIDTH + 4 * B, r * 50, BRICK_WIDTH, BRICK_HEIGHT, Color.BLUE);           
            bricks.add(brick);
           }  
         }
         
        }
       Brick brick = new Brick (0, 165+BRICK_HEIGHT, BRICK_WIDTH, BRICK_HEIGHT, Color.ORANGE);
       bricks.add(brick);
    }
    /**
     * method that tells how and how many bricks should be displayed for the second level.
     */
    public void secondLevel() {                
        for (int r=1; r<5;r++){
         if (r==1){
          for (int i=0; i<6; i++){
           Brick brick = new Brick (i * 65 + B, r * 60, BRICK_WIDTH, BRICK_HEIGHT, Color.GREEN);
           bricks.add(brick);           
          }
         }
         else if(r==2) {
          for (int i=0; i<5; i++){
            Brick brick = new Brick (i * 65 + BRICK_WIDTH + 4 * B, r * 50, BRICK_WIDTH, BRICK_HEIGHT, Color.RED);
            bricks.add(brick);
           }   
         }
         else {
          for (int i=0; i<6; i++){
           Brick brick = new Brick(i * 65 + B, r * 60, BRICK_WIDTH, BRICK_HEIGHT, Color.BLUE);           
           bricks.add(brick);
          }  
         }  
        }
        Brick brick = new Brick (0, 240+BRICK_HEIGHT, BRICK_WIDTH, BRICK_HEIGHT, Color.ORANGE);
       bricks.add(brick);
    }
    // Animating the game
    // The game is animated by using a 'thread'. Threads allow the program to do 
    // two (or more) things at the same time. In this case the main program is
    // doing the usual thing (View waits for input, sends it to Controller,
    // Controller sends to Model, Model updates), but a second thread runs in 
    // a loop, updating the position of the ball, checking if it hits anything
    // (and changing direction if it does) and then telling the View the Model 
    // changed.
    
    // When we use more than one thread, we have to take care that they don't
    // interfere with each other (for example, one thread changing the value of 
    // a variable at the same time the other is reading it). We do this by 
    // SYNCHRONIZING methods. For any object, only one synchronized method can
    // be running at a time - if another thread tries to run the same or another
    // synchronized method on the same object, it will stop and wait for the
    // first one to finish.
    
    /** 
     * Start the animation thread
     */
    public void startGame()
    {       
        Thread t = new Thread( this::runGame );     // create a thread running the runGame method
        t.setDaemon(true);                          // Tell system this thread can die when it finishes
        t.start();                                  // Start the thread running         
    }   
    
    /**
     * The main animation loop
    */
    public void runGame() 
    {
        try
        {
            // set gameRunning true - game will stop if it is set false (eg from main thread)
            setGameRunning(true);
            while (getGameRunning())
            {
                updateGame();                        // update the game state
                modelChanged();                      // Model changed - refresh screen
                
                Thread.sleep( getFast() ? 10 : 20 ); // wait a few milliseconds                
            }
        } 
        catch (Exception e) 
        { 
            Debug.error("Model::runAsSeparateThread error: " + e.getMessage());
        }        
    }
  
    // updating the game - this happens about 50 times a second to give the impression of movement
    /**
     * It first updates the position of the objects and their possible hits, then it deals with life and levels.
     *  @param destroyedBricks a variable used to count all the bricks that are being destroyed.
     *  Once this reaches the same amount of the total bricks initially displayed a switch statement is activated to deal with what the game is going to do next.
     *  @param lastScore the score reached once the first level is finished. It is going to be the starting score for level 2, even when this is restarted.
     *  This is done by the {@link #initialiseGame() initialiseGame method}  
     */
     public synchronized void updateGame()
     {
        // move the ball one step (the ball knows which direction it is moving in)
        ball.moveX(BALL_MOVE);                      
        ball.moveY(BALL_MOVE);              
        // get the current ball position (top left corner)
        int x = ball.topX;  
        int y = ball.topY;
        int z = bat.topX;
        // Deal with possible edge of board hit
        if (x >= width - B - BALL_SIZE)  ball.changeDirectionX();
        if (x <= 0 + B + BALL_SIZE)  ball.changeDirectionX();
        if (y >= height - B - BALL_SIZE)  // Bottom
        { 
            ball.changeDirectionY();
            addToScore( HIT_BOTTOM );     // score penalty for hitting the bottom of the screen            
            life=life - 1;           
        }
        if (y <= 0 + M + BALL_SIZE)  ball.changeDirectionY();
        //Stop the bat from moving off the sides of the screen
        if (z >=width - B - BRICK_WIDTH*3 ) bat.topX = width - B - BRICK_WIDTH*3;
        if (z<= 0+B) bat.topX = 0+B;            
        
        // *[2]******************************************************[2]*
        // * Fill in code to check if a visible brick has been hit      *
        // * The ball has no effect on an invisible brick               *
        // * If a brick has been hit, change its 'visible' setting to   *
        // * false so that it will 'disappear'                          * 
        // **************************************************************
              
        // check whether ball has hit the bat
        if ( ball.hitBy(bat) ){            
            ball.changeDirectionY();           
        } 
                   
         for (Brick brick:bricks) {
          if (brick.colour==Color.ORANGE){
           brick.moveBrick(2); // brick moves
           if (brick.visible && brick.topY >= height - B - BRICK_HEIGHT){
            destroyedBricks.addElement(brick); // if the orange brick passes the bottom edge of the screen, the brick is added to the destroyed bricks.
            brick.visible = false; //brick becomes  invisible so it is not added when the method runs again
           }
          }
          if ( ball.hitBy(brick) && brick.visible ){                       
             ball.changeDirectionY();
            if (brick.colour == Color.ORANGE) {
               life += 1; //bonus life is added if orange brick is hit
            }
            if (brick.colour==Color.BLUE || brick.colour==Color.ORANGE){ 
             brick.visible = false;
             addToScore(HIT_BRICK);// score is added when brick is destroyed
             destroyedBricks.addElement(brick);             
            }
            else {
                brick.changeColour();
            }
            break; // ball only destroys one brick it hits two
         }                            
        } 
        //Pause the game when life is over or all bricks are destroyed
        if (life == 0){
         setGameRunning (false);
         effect = new Sound ("gameover.wav");
         effect.play();
         resetLevel();  //when game is over and the level is not 1, the level resets.         
        }
        if (level==1){
        lastScore = score; // allows to save the score after the first level 
        }
        //when all bricks are destroyed the level updates
        if(destroyedBricks.size()==bricks.size()){
         effect= new Sound ("youwin.wav");
         effect.play();
          switch (level){
          case 1:
          level++;
          setGameRunning(false); //Pauses the game when life is over or all bricks are destroyed
          break;
          
          case 2:
          resetLevel(); //when game is over and the level is not 1, the level resets.
          setGameRunning(false);
          break;
         }
        }      
     }
    
    /** This is how the Model talks to the View
    * Whenever the Model changes, this method calls the update method in
    * the View. It needs to run in the JavaFX event thread, and Platform.runLater 
    * is a utility that makes sure this happens even if called from the runGame thread
    */ 
    public synchronized void modelChanged()
    {
        Platform.runLater(view::update);        
    }
    
    
    // Methods for accessing and updating values
    // these are all synchronized so that the can be called by the main thread 
    // or the animation thread safely
    
    /**
     * Change game running state - set to false to stop the game
     */ 
    public synchronized void setGameRunning(Boolean value)
    {  
        gameRunning = value;
    }
    
    /** 
     * Return game running state 
     */
    public synchronized Boolean getGameRunning()
    {  
        return gameRunning;
    }

    /** 
     * Change game speed - false is normal speed, true is fast
     */
    public synchronized void setFast(Boolean value)
    {  
        fast = value;
    }
    /**
     * @param level 
     * @return level resets to 1, it is used in the {@link #updateGame() updateGame method}when the level is 2 and either all life is lost 
     * or all the bricks are destroyed to go back to the first level.
     */
    public synchronized void resetLevel(){
     level=1;     
    }
     //Return game speed - false is normal speed, true is fast 
    public synchronized Boolean getFast()
    {  
        return(fast);
    }
    // Return bat object
    public synchronized GameObj getBat()
    {
        return(bat);
    }    
    // return ball object
    public synchronized GameObj getBall()
    {
        return(ball);
    }    
    // return bricks
    public synchronized ArrayList<Brick> getBricks()
    {
        return(bricks);
    }    
    //return life
    public synchronized int getLife()
    {
        return (life);
    }
    //return level
    public synchronized int getLevel(){
        return(level);
    }
    // return score
    public synchronized int getScore()
    {
        return(score);
    }
    //return destroyed bricks
    public synchronized Vector getDestroyedBricks(){
        return (destroyedBricks);
    }
    //return sound
    public synchronized Sound getSound(){
        return (sound);
    }
     // update the score     
    private synchronized void addToScore(int n)    
    {
        score += n;
        if (score <0){ 
        score=0; //score cannot be negative
        }
    }  
    // move the bat one step - -1 is left, +1 is right
    public synchronized void moveBat( int direction )
    {        
        int dist = direction * BAT_MOVE;    // Actual distance to move
        Debug.trace( "Model::moveBat: Move bat = " + dist );
        bat.moveX(dist);
    }
}   
    