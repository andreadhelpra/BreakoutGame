
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



/** The View class creates and manages the GUI for the application.
* It doesn't know anything about the game itself, it just displays
* the current state of the Model, and handles user input
*/
public class View implements EventHandler<KeyEvent>
{ 
    // variables for components of the user interface
    protected int width;       // width of window
    protected int height;      // height of window

    // usr interface objects
    private Pane pane;       // basic layout pane
    private Canvas canvas;   // canvas to draw game on
    private Label infoText;  // info at top of screen
    private Label infoLife;  //info at bottom of screen  
    private Label infoMessage; //info at center of the screen
    private Label username; // asking for usarname
    private Label label1;   
    private Button nextLevelButton; // button to go to next level
    private Button startButton; //the start button
    private Button restartButton; //the restart button
    private Button pauseButton; //the pause button
    private Button musicButton; // the music button
    private TextField usernameInput; // username input 
   
    
    // The other parts of the model-view-controller setup
    Controller controller;
    Model model;
        

    private GameObj  bat;            // The bat
    private GameObj  ball;           // The ball
    private ArrayList<Brick> bricks;     // The bricks
    private Sound sound;
    private Sound effect;
    private int       score =  0;     // The score
    private int       life = 3;       // The life
    private Vector    destroyedBricks; // The destroyed bricks
    private int       level=1;      // The level
    
    // we don't really need a constructor method, but include one to print a 
    // debugging message if required
    public View(int w, int h)
    {
        Debug.trace("View::<constructor>");
        width = w;
        height = h;
    }

    // start is called from Main, to start the GUI up
    // Note that it is important not to create controls etc here and
    // not in the constructor (or as initialisations to instance variables),
    // because we need things to be initialised in the right order
    /**
         * This method is responsible for creating the GUI. It creates first a main menu that asks for username  and shows a start button. 
         * Once the start button is pressed {@link #startButtonClick(ActionEvent event)} is called to start the first level and manage what happens next.
         * @param infoText this is a label at the top of the page that shows the game title on the start menu and the score once 'start' is pressed.
         * To style it, it uses webfonts by Google.</br>
         * {@code setTextFill} is used to target the CSS without modifying the stylesheet {@link <a href = "http://www.java2s.com/Code/Java/JavaFX/SetLabelTextcolor.htm">
         * http://www.java2s.com/Code/Java/JavaFX/SetLabelTextcolor.htm</a>(@see only)}
         * @see <a href="http://fxexperience.com/2012/12/use-webgoogle-fonts-in-javafx/">
         * http://fxexperience.com/2012/12/use-webgoogle-fonts-in-javafx/</a>
         *     
     */
    public void start(Stage window) 
    {
        // breakout is basically one big drawing canvas, and all the objects are
        // drawn on it as rectangles, except for the text at the top - this
        // is a label which sits 'on top of' the canvas.
        pane = new Pane();       // a simple layout pane
        pane.setId("Breakout");  // Id to use in CSS file to style the pane if needed
        
        // canvas object - we set the width and height here (from the constructor), 
        // and the pane and window set themselves up to be big enough
        canvas = new Canvas(width,height);  
        pane.getChildren().add(canvas);     // add the canvas to the pane
                
        //ask user for username
        username = new Label("Select username: ");
        username.setTranslateX(50);
        username.setTranslateY(100);
        pane.getChildren().add(username);
        //get username
        usernameInput=new TextField ();
        usernameInput.setTranslateX(50);
        usernameInput.setTranslateY(150);
        pane.getChildren().add(usernameInput);
        usernameInput.setOnAction(this::usernameInputClick);
        
       
        //add start button
        startButton = new Button ("Start");
        startButton.setTranslateX(150);
        startButton.setTranslateY(300);
        pane.getChildren().add(startButton); //add button to the pane
        startButton.setVisible(false);
        startButton.setOnAction(this::startButtonClick);//set button on action
        startButton.setId("startButton");
        // infoText box for the score - a label which we position on 
        //the canvas with translations in X and Y coordinates
        infoText = new Label("BreakOut");
        infoText.setTranslateX(50);
        infoText.setTranslateY(10);      
        infoText.setStyle("-fx-font-family:'Press Start 2P', cursive; ");        
        pane.getChildren().add(infoText);  // add label to the pane        
        
        // add the complete GUI to the scene
        Scene scene = new Scene(pane);   
        scene.getStylesheets().add("breakout.css"); // tell the app to use our css file
        scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Press+Start+2P&display=swap");
         
        // Add an event handler for key presses. We use the View object itself
        // and provide a handle method to be called when a key is pressed.
        scene.setOnKeyPressed(this);

        // put the scene in the window and display it
        window.setScene(scene);
        window.show();        
    }       
    /**
     * Even handler for usernameInputClick
     */
    protected void usernameInputClick(ActionEvent event){
       TextField t = ((TextField) event.getSource()); //textfield gets input from the user       
       username.setText("Hello " + t.getText() + ",\npress 'start' to \nsmash those bricks!"); //Label is updated with username       
       startButton.setVisible(true);
       usernameInput.setVisible(false);
    }
    /**
     * Event handler for start button  
     * it sets some buttons as invisible and it calls {@link breakoutball.Model#startGame() startGame} from the Model in order to start the first game. 
     */  
    public void startButtonClick(ActionEvent event){
        model.startGame();
        startButton.setVisible(false);
        username.setVisible(false);
        usernameInput.setVisible(false);
       //add pause button
        pauseButton = new Button ("Pause");
        pauseButton.setTranslateX(350);
        pauseButton.setTranslateY(565);
        pane.getChildren().add(pauseButton); //add button to the pane               
        pauseButton.setOnAction(this::pauseButtonClick); //set button on action
        
        //add restart button
        restartButton = new Button ("Restart");
        restartButton.setTranslateX(300);
        restartButton.setTranslateY(565);
        pane.getChildren().add(restartButton); //add button to the pane              
        restartButton.setOnAction(this::restartButtonClick); //set button on action
        
        // music button
         musicButton = new Button ("Music off");
         musicButton.setTranslateX(235);
         musicButton.setTranslateY(565);
         pane.getChildren().add(musicButton); //add button to the pane      
         musicButton.setOnAction(this::musicButtonClick); //set button on action
        
        //infoLife box for displaying lives remaining
        infoLife = new Label ("Life=" + life); 
        infoLife.setTranslateX(5);
        infoLife.setTranslateY(565);
        infoLife.setTextFill(Color.WHITE);
        pane.getChildren().add(infoLife);
        infoLife.setStyle("-fx-font-family:'Press Start 2P', cursive; ");
        //empty label 
        infoMessage = new Label ();
        infoMessage.setTranslateX(140);
        infoMessage.setTranslateY(270);
        infoMessage.setTextFill(Color.YELLOW);
        pane.getChildren().add(infoMessage);
        
        nextLevelButton = new Button ("Next Level");
        nextLevelButton.setTranslateX(160);
        nextLevelButton.setTranslateY(300);
        pane.getChildren().add(nextLevelButton);
        nextLevelButton.setVisible(false);
    }
    /**
     * Event handler for restart button
     */
    private void restartButtonClick(ActionEvent event){
       if (life==0 ||destroyedBricks.size()==bricks.size()){
       model.startGame();   //when life is over or all bricks are destroyed, game restarts  
       }
       infoMessage.setText(" "); //message disappears
       model.initialiseGame();       //game is initialised again
       sound.close();
    }   
    /**
     * Event handler for pause button
     */
    private void pauseButtonClick(ActionEvent event){
       if (pauseButton.getText().equals("Pause")){
        model.setGameRunning(false);
         sound.stop();
        pauseButton.setText("Play  ");//when paused, button switches to 'Play'
       }
       else {
        model.startGame();
         sound.play();
        pauseButton.setText("Pause");// reset text to 'Pause'. 
       }
    }
    /**
     * Event handler for music button
     */
    public void musicButtonClick(ActionEvent event){
      if (musicButton.getText().equals("Music off")){
        musicButton.setText("Music on");//when paused, button switches to 'Music on'
        sound.stop(); // music stops
       }
      else{
        musicButton.setText("Music off");//when paused, button switches to 'Music off'
        sound.play(); // music resumes
       }
    }
    /**
     * Event handler for next level button
      */
    private void nextLevelButtonClick(ActionEvent event){          
     model.startGame();     
     model.initialiseGame();
     infoMessage.setText(" ");
     nextLevelButton.setVisible(false);
    }
    /**
     *  Event handler for key presses - it just passes th event to the controller
     */
    public void handle(KeyEvent event)
    {
        // send the event to the controller
        controller.userKeyInteraction( event );
    }    
    /**
     * drawing the game
     */ 
    protected void drawPicture()
    {
        // the ball movement is running 'i the background' so we have
        // add the following line to make sure
        synchronized( Model.class )   // Make thread safe
        {
            GraphicsContext gc = canvas.getGraphicsContext2D();

            // clear the canvas to redraw
            gc.setFill( Color.BLACK );
            
            gc.fillRect( 0, 0, width, height );
            // draw the bat and ball
            displayBall( gc, ball );   // Display the Ball
            displayGameObj( gc, bat  );   // Display the Bat
           
            // *[3]****************************************************[3]*
            // * Display the bricks that make up the game                 *
            // * Fill in code to display bricks from the ArrayList        *
            // * Remember only a visible brick is to be displayed         *
            // ************************************************************            
            for (Brick brick:bricks){
                 if (brick.visible){
                  displayGameObj( gc, brick );                  
                }
            }       
            // update score
            infoText.setText("Score = " + score);
            infoText.setTextFill(Color.WHITE);
            // update life
            infoLife.setText("Life=" + life);
            
            if (life==0){
             infoMessage.setText("GAME OVER"); //draws "GAME OVER" message when life is 0
             sound.close();
            }
             if (destroyedBricks.size()== bricks.size()){
             infoMessage.setText ("YOU WIN!"); //draws "YOU WIN" message when level is passed
             sound.close();             
             //new button for next level
             if (level==1){
              nextLevelButton.setVisible(true);
              nextLevelButton.setText("Go to Level 1"); // this displays once level 2 is completed (it first returns to level 1 and then changes the message)
              nextLevelButton.setOnAction(this::nextLevelButtonClick);
              }
             else if (level==2){
                nextLevelButton.setVisible(true);
                nextLevelButton.setText("Next Level");
                nextLevelButton.setOnAction(this::nextLevelButtonClick);
              }
            }      
        }
    }
    /**
     *  Display a game object - it is just a rectangle on the canvas
     */
    protected void displayGameObj( GraphicsContext gc, GameObj go ) 
    {
        gc.setFill( go.colour );
        gc.fillRect( go.topX, go.topY, go.width, go.height );
    }
    /**
     * Display a circle
     * it uses a fillOval method to display the object
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics.html">https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics.html</a>
     */
    protected void displayBall(GraphicsContext gc, GameObj go) { 
     gc.setFill(go.colour);   
     gc.fillOval(go.topX - go.radius, go.topY - go.radius, go.radius * 2, go.radius * 2);  
    }
    
    /**This is how the Model talks to the View
     * This method gets called BY THE MODEL, whenever the model changes
     * It has to do whatever is required to update the GUI to show the new model status
     */
    public void update()
    {
        // Get from the model the ball, bat, bricks, score & life
        ball    = model.getBall();              // Ball
        bricks  = model.getBricks();            // Bricks
        bat     = model.getBat();               // Bat                    
        score   = model.getScore();             // Score
        life    = model.getLife();              // Life
        level   = model.getLevel();             // Level
        destroyedBricks = model.getDestroyedBricks(); // Destroyed Bricks
        sound = model.getSound();               // Sound
        Debug.trace("Update");
        drawPicture();                     // Re draw game
    }
}
