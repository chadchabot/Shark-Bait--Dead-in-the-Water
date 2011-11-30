/**
 * @author      Bonne Justin jbonne@uoguelph.ca
 * @author      Cardinal, Blake bcardina@uoguelph.ca
 * @author      Chabot, Chad chabot@uoguelph.ca
 * @version     0.9                 
 * @since       2011-11-29
 */


import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;

import java.awt.BorderLayout;
import java.awt.geom.Point2D;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;


//      for radio buttons
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Dimension;

public class Game extends JComponent implements MessageListener, KeyListener, ActionListener
{

	//	game "constants"
    public static final int				REFRESH_RATE			= 60;
    public static final int				PIXELS_PER_METER		= 3;
    public static final int				PLAYER_X_CENTER			= 500;
    public static final int				PLAYER_Y_CENTER			= 300;
    public static final int				SLOOP_TURN_MAX			= 3;
    public static final int				FRIGATE_TURN_MAX		= 2;
    public static final int				MAN_OF_WAR_TURN_MAX		= 1;
    public static final double			TURN_AMOUNT_INCREMENT	= 0.5;
    public static final double			SPEED_AMOUNT_INCREMENT	= 0.10;
    
	private static final int			windowWidth				= 1024;
	private static final int			windowHeight			= 768;
	
	
	//	
    private GUI                         gameGUI;
	private	boolean						windUpdateFLAG	= false;
	private boolean						shiftPressed	= false;
	
    protected HashMap<String, Ship>		shipList;
    private ArrayList<Integer>			shipID;
    public	HashMap<String, String>		speedTable;
    private World                       gameWorld		= null;
    private int                         playerID;
    private boolean                     gameRunning		= false;
    private int                         shipSelection	= 0;
    private String                      pMessage;
    private int                         targetID		= -1;
    private long 						lastTurn		= 0;
    private long 						lastShot		= 0;
	
	//	"secondary" windows - even though they precede the actual window that shows stuff in the game
    private Lobby						lobbyWindow;
	private SplashWindow				splashWindow;
	private boolean						showLobby		= false;
	private	boolean						showSplash		= true;
    
	//	"main" window components
	private	JFrame						frame;
    private Dimension 					Dim;
    private JFrame						helpWindow;
    private JMenuBar 					Menu;
	private JMenu 						fileMenu;
	private JMenu						helpMenu;
	private JMenuItem					close;
	private JMenuItem					help;
        
    public  Communication				comm;
    public  Thread						commThread;
	public	boolean						commConnection	= false;
	public	String						serverIP;
	public	int							serverPort;
        
    //      communication / message variables
    public  double                      turnAmount	= 0.00;
    public  double                      speedAmount = 0.00;
        

    /**
     * Class constructor.
	 * The entry point in to the client application.
	 *
	 * Creates the welcome screen [SplashWindow], sets up
	 * communication services, loads lobby window, and then drops the
	 * player in to the game, and gives them control of a ship in the game world.
     */
    public Game( )
	{
	
		splashWindow = new SplashWindow( this );
		
		//	wait until communication has been set up and then show the lobby
		while ( !this.showLobby ) {
//			System.out.println( this.showLobby );
			try {
				Thread.sleep( 1000 / REFRESH_RATE );
			}
			catch ( InterruptedException e ) {
                e.printStackTrace();
			}

		}
	
		//	sets up, and hides, Help Window
		this.helpWindow = new JFrame("Help Menu");
        try {
			this.helpWindow.setContentPane(new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("images/help_menu.png")))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        this.helpWindow.setSize(748,308);
        this.helpWindow.setLocation(200,200);
        this.helpWindow.setResizable(false);
        
        Dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        int w = this.helpWindow.getSize().width;
		int h = this.helpWindow.getSize().height;
		int x = (this.Dim.width-w)/2;
		int y = (this.Dim.height-h)/2;
		 
		this.helpWindow.setLocation(x, y);
		
		this.lobbyWindow = new Lobby(this);
    	this.lobbyWindow.createLobby();
        this.lobbyWindow.lobbyWindow.setVisible(true );
        
//        System.out.println(SpeedTable.speedTable[45]);
			
        this.shipList = new HashMap<String, Ship>();
        this.shipID = new ArrayList<Integer>();
        this.gameWorld = new World( );

        this.frame = new JFrame ("Shark Bait");
        this.gameGUI = new GUI( );

        Menu = new JMenuBar();
        this.frame.setJMenuBar(Menu);
        
        fileMenu = new JMenu("File");
        close = new JMenuItem("Close");
		fileMenu.add(close);
		close.addActionListener(this);
		
		helpMenu = new JMenu("Help");
		help = new JMenuItem("Help");
		helpMenu.add(help);
		help.addActionListener(this);
		
		Menu.add(fileMenu);
		Menu.add(helpMenu);
        
        this.frame.setResizable(true);
        this.frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        this.frame.setResizable( false );
        this.frame.pack();
        this.frame.setSize(Game.windowWidth, Game.windowHeight);
        this.frame.add( this, BorderLayout.CENTER );
	
		this.frame.setFocusTraversalKeysEnabled(false);
        this.frame.addKeyListener( this );
        //Thread thread = new Thread(this);
        //thread.start();
/*        this.commThread = new Thread(this.comm = new Communication(this, this.serverIP, this.serverPort));
        this.commThread.start();
*/        while(true){
			try {
				Thread.sleep(1000 / REFRESH_RATE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
                e.printStackTrace();
			}
            if(this.gameRunning)
            {
                this.update();
            }
		}
        
	}
	
	/**
     * Draw the various components in the game.
	 *
     * The order of drawing is significant, in order to make sure that no GUI HUD elements
	 * or ships are obscured by weather or interface/map elements
	 *
     * @param g Graphics object that is used to draw all/most 'things' in the game world
     */
	public void paint ( Graphics g )
    {        
		Point2D.Double playerPos = null;
		
		if ( this.shipList.size() != 0 ) {
			playerPos = this.getShip(Integer.toString(this.playerID)).getPosition();
        }
        if ( playerPos != null )
        {
            //      draw world
        	this.gameWorld.draw(g, playerPos);

            //      draw ships
           	for (String key : this.shipList.keySet()) {
        		this.getShip(key).draw(g, playerPos, targetID);
        	}

			this.gameWorld.drawWeather( g );
			//  draw HUD
			this.gameGUI.draw( g, this.shipList, this.playerID, this.targetID );

        }
		
    }
	
	/**
     * Updates the various components in the game
     *
	 * Unlike paint(), order is not significant for this method.
     */
    public void update()
    {
        //update ships
        for (String key : shipList.keySet()) {
            this.getShip(key).update(this.gameWorld.getWindDirection());
        }
        //update world
		if (this.windUpdateFLAG == true) {
			this.gameGUI.update( this.gameWorld.getWindDirection(), this.gameWorld.getWindSpeed() );
			this.windUpdateFLAG = false;
		}
        //update HUD
        this.repaint();
    }
	
	/**
     * Determines which action is being performed, and how to respond to it
     *
     * @param e ActionEvent object that is generated by interaction with an interface element.
	 * Typically button clicks.
     */
    public void actionPerformed( ActionEvent e )
    {
            //	System.out.println( "A button is clicked" );

		if ( e.getActionCommand().equals( "connect_to_server" ) ) {
			//	System.out.println( "connecting to server" );
			
			this.serverIP = this.splashWindow.serverAddressField.getText();
			this.serverPort = Integer.parseInt( this.splashWindow.serverPortField.getText() );
			//	System.out.println( this.serverIP + ", " + this.serverPort );

			//	set up communication
			this.commThread = new Thread(this.comm = new Communication( this, this.serverIP, this.serverPort) );
			this.commThread.start();

			//	hide welcome screen
			this.splashWindow.frame.setVisible( false );
			//	display the ship selection lobby window
			this.showLobby = true;

			//	System.out.println( "showSplash is false" );
		}

		if ( e.getActionCommand().equals( "sloop_selected" ) )
		{
			//	System.out.println( "SLOOP class ship selected" );
			//      set the variable for the user's ship preference
			this.shipSelection = 0;
		}
		
		if ( e.getActionCommand().equals( "frigate_selected" ) )
		{
			//	System.out.println( "FRIGATE class ship selected" );
			//      set the variable for the user's ship preference
			this.shipSelection = 1;
		}
		
		if ( e.getActionCommand().equals( "man-o-war_selected" ) )
		{
			//	System.out.println( "MAN-O-WAR class ship selected" );
			//      set the variable for the user's ship preference
			this.shipSelection = 2;
		}       
				
		if ( e.getActionCommand().equals( "register" ) )
		{
			//	System.out.println( "REGISTER button clicked" ) ;
			this.lobbyWindow.sloopButton.setEnabled( false );
			this.lobbyWindow.frigateButton.setEnabled( false );
			this.lobbyWindow.manOwarButton.setEnabled( false );
			this.lobbyWindow.registerButton.setEnabled( false );
			this.lobbyWindow.registerButton.setText( "Waiting for registration..." );
			//      send REGISTER message to server
			this.comm.sendMessage( "register:"+shipSelection+";" );				
		}
		
		if ( e.getActionCommand().equals( "ready" ) )
		{
				//	System.out.println( "READY button clicked" ) ;
				this.lobbyWindow.readyButton.setEnabled( false );
				this.lobbyWindow.readyButton.setText( "Waiting to start..." );
				//      send READY message to server
				this.comm.sendMessage( "ready;" );
				
		}
		
		//	displays help window
		if (e.getActionCommand().equals("Help"))
		{
			helpWindow.setVisible(true);
		}
		
		if (e.getActionCommand().equals( "Close") )
		{
			this.comm.sendMessage ( "disconnect;");
			System.exit(0);
		}
    }
	
	/**
     * Responds to a KeyEvent.
	 * Not used or implemented
     *
     * @param pEvent KeyEvent to respond to.
     */
    public void keyTyped( KeyEvent pEvent )
    {
                
    }

	/**
     * Responds to a KeyEvent by creating a [request] message to be sent to the server.
	 *
	 * The KeyEvents generated/watched for here are keyboard commands to change speed,
	 * fire, steer, and cycle between targets.
     *
     * @param pEvent User generated KeyEvent to respond to.
     */
	public void keyPressed( KeyEvent pEvent )
    {
		
		//      System.out.println("Key Pressed!!!");
		if( pEvent.getKeyCode( ) == KeyEvent.VK_UP )
		{
		//System.out.println("Up!");
		//      change the current speed of the player's ship
			this.speedAmount += SPEED_AMOUNT_INCREMENT;

			if ( this.speedAmount > 1.00 )
			{
				this.speedAmount = 1.00;
			}
				
			this.pMessage = "speed:" +  this.speedAmount + ";";                     
			comm.sendMessage(this.pMessage);
				
		}
		else if( pEvent.getKeyCode( ) == KeyEvent.VK_DOWN )
        {
            //System.out.println("Down!");
			//      change the current speed of the player's ship
			this.speedAmount -= SPEED_AMOUNT_INCREMENT;

			if ( this.speedAmount < 0.00 )
			{
				this.speedAmount = 0.00;
			}
			
			this.pMessage = "speed:" +  this.speedAmount + ";";                     
			comm.sendMessage(this.pMessage);
		}
        else if( pEvent.getKeyCode() == KeyEvent.VK_LEFT )
        { 
			 //System.out.println("Left!");
			 
			 int  turn = 0;
			 
			 if (this.getShip(Integer.toString(this.playerID)).getType() == 0)
			 {
				turn = -1*SLOOP_TURN_MAX;
			 }
			 
			 if (this.getShip(Integer.toString(this.playerID)).getType() == 1)
			 {
				turn = -1*FRIGATE_TURN_MAX;
			 }
			 
			 if (this.getShip(Integer.toString(this.playerID)).getType() == 2)
			 {
				turn = -1*MAN_OF_WAR_TURN_MAX;
			 }
			//System.out.println(System.currentTimeMillis() - this.lastTurn);
			if(System.currentTimeMillis() - this.lastTurn > 100 || this.lastTurn == 0)
			{
				this.pMessage = "setHeading:" + turn + ";";
				comm.sendMessage(pMessage);
				this.lastTurn = System.currentTimeMillis();
			}

		}
		else if( pEvent.getKeyCode() == KeyEvent.VK_RIGHT ) 
		{
			int  turn = 0;
			
			if (this.getShip(Integer.toString(this.playerID)).getType() == 0)
			{
				turn = SLOOP_TURN_MAX;
			}
			
			if (this.getShip(Integer.toString(this.playerID)).getType() == 1)
			{
				turn = FRIGATE_TURN_MAX;
			}
			
			if (this.getShip(Integer.toString(this.playerID)).getType() == 2)
			{
				turn = MAN_OF_WAR_TURN_MAX;
			}
			//	add to the amount of the turn, to a maximum of -25
			//	turn amount is in degrees?
			/*if ( this.turnAmount >= -maxIncrement)
			 {
			 this.turnAmount -= TURN_AMOUNT_INCREMENT;
			 }
			 else
			 {
			 this.turnAmount = - maxIncrement;
			 }
			 */
			//System.out.println(System.currentTimeMillis() - this.lastTurn);
			if(System.currentTimeMillis() - this.lastTurn > 100 || this.lastTurn == 0)
			{
				this.pMessage = "setHeading:" + turn + ";";
				comm.sendMessage(pMessage);
				this.lastTurn = System.currentTimeMillis();
			}
		}
		else if( pEvent.getKeyCode() == KeyEvent.VK_TAB ) 
		{
			if (this.targetID == -1 && this.shipID.size() != 0 )
			{
				this.targetID = shipID.get(0);
			}
			
			for(int i = 0; i < shipID.size(); i++)
			{
				//	check if the ship from the list is the ship targeted by the user
				if (shipID.get(i).intValue() == targetID){
/*						if ( pEvent.isShiftDown() )
					{
						if ( i > 0 )
						{
							this.targetID = shipID.get((i-1) % shipID.size()).intValue();
						}
						else
						{
							this.targetID = shipID.get((shipID.size()-1) % shipID.size()).intValue();
						}

						System.out.println( "\tShift is pressed." );
					}
					else {
*/							this.targetID = shipID.get((i+1) % shipID.size()).intValue();
//						}

					break;
				}
			}
			//System.out.println(this.targetID);
			//System.out.println("TAB!");
		}
        else if( pEvent.getKeyCode() == KeyEvent.VK_SPACE)
        {
            //	System.out.println("Space!");
            if(targetID != -1)
            {
                int		shipHeading		= this.getShip(new Integer(playerID).toString()).getHeading();
                int		enemyHeading	= this.getShip(new Integer(targetID).toString()).getHeading();
                Point2D.Double	shipPos			= this.getShip(new Integer(playerID).toString()).getPosition();
                Point2D.Double	enemyPos		= this.getShip(new Integer(targetID).toString()).getPosition();
                int		angleDiff		= 0;
                char	shotDir			= ' ';
                boolean firable			= false;
                //straight up
                if(enemyPos.getX() == shipPos.getX() && enemyPos.getY() < shipPos.getY())
                {
                    if( shipHeading > 260 && shipHeading < 280 ) 
                    {
                        firable = true;
                        shotDir = 'r';                        
                    }
                    else if (shipHeading > 80 && shipHeading < 100)
                    {
                        firable = true;
                        shotDir = 'l'; 
                    }
                    else if (shipHeading > 350 || shipHeading < 10 )
                    {
                        firable = true;
                        shotDir = 'f'; 
                    }
                }
                //straight right
                else if(enemyPos.getX() > shipPos.getX() && enemyPos.getY() == shipPos.getY())
                {
                    if(  shipHeading > 80 && shipHeading < 100)
                    {
                        firable = true;
                        shotDir = 'f';
                    }
                    else if(shipHeading > 170 && shipHeading < 190)
                    {
                        firable = true;
                        shotDir = 'l';
                    }
                    else if(shipHeading > 350 || shipHeading < 10 )
                    {
                        firable = true;
                        shotDir = 'r';
                    }
                }
                //straight down
                else if(enemyPos.getX() == shipPos.getX() && enemyPos.getY() > shipPos.getY())
                {
                    if(  shipHeading > 260 && shipHeading < 280)
                    {
                        firable = true;
                        shotDir = 'l';
                    }
                    else if(shipHeading > 170 && shipHeading < 190)
                    {
                        firable = true;
                        shotDir = 'f';
                    }
                    else if(shipHeading > 80 && shipHeading < 100 )
                    {
                        firable = true;
                        shotDir = 'f';
                    }
                }
                //straight left
                else if(enemyPos.getX() < shipPos.getX() && enemyPos.getY() == shipPos.getY())
                {
                    if(  shipHeading > 260 && shipHeading < 280)
                    {
                        firable = true;
                        shotDir = 'f';
                    }
                    else if(shipHeading > 170 && shipHeading < 190)
                    {
                        firable = true;
                        shotDir = 'r';
                    }
                    else if(shipHeading > 350 || shipHeading < 10 )
                    {
                        firable = true;
                        shotDir = 'l';
                    }
                }
                //top right
                else if(enemyPos.getX() > shipPos.getX() && enemyPos.getY() < shipPos.getY())
                {   
                    angleDiff = shipHeading - (int)Math.round(Math.abs(
                        Math.toDegrees( Math.atan((enemyPos.getX()-shipPos.getX())/(enemyPos.getY()-shipPos.getY()) ))
                        ));
                    angleDiff = (angleDiff+360)%360;
                    
                    if( shipHeading > 260 && shipHeading < 280 ) 
                    {
                        firable = true;
                        shotDir = 'r';                        
                    }
                    else if (shipHeading > 80 && shipHeading < 100)
                    {
                        firable = true;
                        shotDir = 'l'; 
                    }
                    else if (shipHeading > 350 || shipHeading < 10 )
                    {
                        firable = true;
                        shotDir = 'f'; 
                    }
                }
                //bottom right
                else if(enemyPos.getX() > shipPos.getX() && enemyPos.getY() > shipPos.getY())
                {
                    angleDiff = shipHeading - (180 - (int)Math.round(Math.abs(
                       Math.toDegrees( Math.atan((enemyPos.getX()-shipPos.getX())/(enemyPos.getX()-shipPos.getY())))
                       ))); 
                    angleDiff = (angleDiff+360)%360;
                    
                    if( shipHeading > 260 && shipHeading < 280 ) 
                    {
                        firable = true;
                        shotDir = 'r';                        
                    }
                    else if (shipHeading > 80 && shipHeading < 100)
                    {
                        firable = true;
                        shotDir = 'l'; 
                    }
                    else if (shipHeading > 350 || shipHeading < 10 )
                    {
                        firable = true;
                        shotDir = 'f'; 
                    }
                }
                //bottom left
                else if(enemyPos.getX() < shipPos.getX() && enemyPos.getY() > shipPos.getY())
                {
                    angleDiff = shipHeading - (180 + (int)Math.round(  Math.abs(
                         Math.toDegrees( Math.atan((enemyPos.getX()-shipPos.getX()/(enemyPos.getY()-shipPos.getY())) )
                         )))); 
                    angleDiff = (angleDiff+360)%360;
                    
                    if( shipHeading > 260 && shipHeading < 280 ) 
                    {
                        firable = true;
                        shotDir = 'r';                        
                    }
                    else if (shipHeading > 80 && shipHeading < 100)
                    {
                        firable = true;
                        shotDir = 'l'; 
                    }
                    else if (shipHeading > 350 || shipHeading < 10 )
                    {
                        firable = true;
                        shotDir = 'f'; 
                    }
                }
                //top left
                else if(enemyPos.getX() < shipPos.getX() && enemyPos.getY() < shipPos.getY())
                {
                    angleDiff = shipHeading - (360 - (int)Math.round(Math.abs(
                       Math.toDegrees( Math.atan((enemyPos.getX()-shipPos.getX())/(enemyPos.getY()-shipPos.getY())) )
                       ))); 
                    angleDiff = (angleDiff+360)%360;
                    
                    if( shipHeading > 260 && shipHeading < 280 ) 
                    {
                        firable = true;
                        shotDir = 'r';                        
                    }
                    else if (shipHeading > 80 && shipHeading < 100)
                    {
                        firable = true;
                        shotDir = 'l'; 
                    }
                    else if (shipHeading > 350 || shipHeading < 10 )
                    {
                        firable = true;
                        shotDir = 'f'; 
                    }
                }
                //	System.out.println("angle:"+angleDiff+" player: "+shipHeading);
                if(firable == true)
                {
                    //	System.out.println("FIRE!!!!!!");
                    int mShipType = this.getShip(new Integer(playerID).toString()).getType();
                    int shotAllowance = 0;
                    if(mShipType == 0)
                    {
                        shotAllowance = 100; 
                    }
                    else if(mShipType == 1)
                    {
                        shotAllowance = 200; 
                    }
                    else if(mShipType == 2)
                    {
                        shotAllowance = 300; 
                    }
                    if(System.currentTimeMillis() - this.lastShot > shotAllowance || this.lastShot == 0)
                    {
                        this.comm.sendMessage("fire:"+targetID+";");
                        this.lastShot = System.currentTimeMillis();
                    }
                    
                }
            }
        }
    }
	
	/**
     * Responds to a KeyEvent.
	 * 
	 * Commented out by default. This method is used to echo to System.out the request message being sent to the server.
     *
     * @param pEvent KeyEvent to respond to.
     */
    public void keyReleased(KeyEvent pEvent)
    {
                
                //      send message
                /*System.out.println( "sending message to the server" );
                System.out.println( "Turning " + turnAmount + " degrees." );
                System.out.println( "Speed requested " + speedAmount );
                //      clear turnAmount, speedChange, and other message specific variables
                this.turnAmount = 0;*/
    }
	
	/**
     * Responds to an incoming message.
	 * 
	 * The possible incoming messages, and their effects, are defined in the NECS specification.
     *
     * @param pMessage The message that Communication received, and may result in a change to the game world
     */
    public void handleMessage(Message pMessage)
        {
        /*
         * Possible Gameplay Messages, as defined by the NECS
         */
        if( pMessage.getMessageName().equals( "shipState" ) )
        {
			this.getShip(pMessage.getArgument( 0 )).updateShip( 
                                        Double.parseDouble( pMessage.getArgument( 1 ) ),	// int x
                                        Double.parseDouble( pMessage.getArgument( 2 ) ),    // int y
                                        Double.parseDouble( pMessage.getArgument( 3 ) ),    // double speed
                                        Double.parseDouble( pMessage.getArgument( 4 ) ),    // int direction
                                        Double.parseDouble( pMessage.getArgument( 5 ) )     // double damage
										);
            
        }
        else if( pMessage.getMessageName().equals( "ship" ) )
        {
            this.shipList.put( pMessage.getArgument( 0 ), new Ship( Integer.parseInt( pMessage.getArgument( 0 ) ), 
                                        Integer.parseInt(pMessage.getArgument( 1 ) ), this.playerID ) );
            if (Integer.parseInt( pMessage.getArgument( 0 )) != playerID){
                shipID.add(new Integer( pMessage.getArgument( 0 )));
            }
        }
        else if( pMessage.getMessageName().equals( "wind" ) )
        {
			this.windUpdateFLAG = true;
            this.gameWorld.setWindDirection( Integer.parseInt( pMessage.getArgument( 0 ) ) );
			this.gameGUI.setWindDirection( Integer.parseInt( pMessage.getArgument( 0 ) ) );
/*
			//	We ignore wind speed, as it has no impact on the client side.
            this.gameWorld.setWindSpeed( Integer.parseInt( pMessage.getArgument( 1 ) ) );
            this.gameGUI.setWindSpeed( Integer.parseInt( pMessage.getArgument( 1 ) ) );
*/
		}
        else if( pMessage.getMessageName().equals( "fog" ) )
        {
            this.gameWorld.setFog( Integer.parseInt( pMessage.getArgument( 0 ) ) );
        }
        else if( pMessage.getMessageName().equals( "rain" ) )
        {
            this.gameWorld.setRain( Integer.parseInt( pMessage.getArgument( 0 ) ) );
        }
        else if( pMessage.getMessageName().equals( "time" ) )
        {
            this.gameWorld.setTime( Integer.parseInt( pMessage.getArgument( 0 ) ) );
        }
        else if( pMessage.getMessageName().equals( "firing" ) )
        {
//			this.getShip( Integer.parseInt( pMessage.getArgument( 0 )) ).setFiring( true );
			this.getShip( pMessage.getArgument( 0 ) ).setFiring( true );

        }
        /*
         * Setup Messages
         */
        else if( pMessage.getMessageName().equals( "start" ) )
        {
            this.gameRunning = true;
            // close lobby window
            this.lobbyWindow.lobbyWindow.setVisible( false );
            this.frame.setVisible(true);
        }
        else if( pMessage.getMessageName().equals( "registered" ) )
        {
            this.playerID = Integer.parseInt( pMessage.getArgument( 0 ) );
			//      set register button to "registered"
			this.lobbyWindow.registerButton.setText( "Registered!" );
			this.lobbyWindow.readyButton.setText( "Waiting for map" );
        }
        else if( pMessage.getMessageName().equals( "shore" ) )
        {
            //	If "shore:x;" is received, it is the final shore
			//	Set the "ready" button in Lobby to active
            if( pMessage.getArgumentsNum() == 1 && pMessage.getArgument(0).equals("x") )
            {
				this.lobbyWindow.readyButton.setEnabled( true );
				this.lobbyWindow.readyButton.setText( "Ready to start?" );
            }
            // handle shore 
            else
            {   
                //ArrayList mX = new ArrayList<Integer> ( );
                //ArrayList mY = new ArrayList<Integer> ( );
                int [] mX = new int[ Integer.parseInt( pMessage.getArgument( 0 ))];
                int [] mY = new int[ Integer.parseInt( pMessage.getArgument( 0 ))];
                
                
                for( int i = 0; i < Integer.parseInt( pMessage.getArgument( 0 ) ); i++)
                {
                    mX[i] = ( Integer.parseInt( pMessage.getArgument( 2 * i + 1 ) ) );
                    mY[i] = ( Integer.parseInt( pMessage.getArgument( 2 * i + 2 ) ) );
                    
            
                }
                this.gameWorld.addShore( mX, mY, Integer.parseInt( pMessage.getArgument( 0 ) ) );
            }
        }
        /*
         * Endgame Messages
         */
        else if( pMessage.getMessageName().equals( "gameover" ) )
        {
            this.comm.closeThread( );
            if( !this.commThread.isAlive( ) )
            {
                this.comm.disconnect( );
            }
            System.exit(0);
        }
    }
	
	
	/**
     * Accessor
	 * 
	 * Returns 
     *
     * @param key The identifier of the ship being sought.
	 * @return Ship object specifed by the input parameter key.
     */	
    public synchronized Ship getShip(String key){
    	return this.shipList.get(key);
    }
	
	
	/**
     * Main.
	 * 
	 * Nuff said?
	 *
     * @param args String array
     */
    public static void main(String[] args)
        {
                Game game = new Game( );
		}
}
