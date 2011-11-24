import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.JComponent;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


//      for radio buttons
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.GridLayout;
import java.awt.Dimension;

public class Game extends JComponent implements MessageListener, KeyListener, ActionListener
{

    public static final int         REFRESH_RATE = 60;
    public static final int         PIXELS_PER_METER = 1;
    public static final int         PLAYER_X_CENTER = 500;
    public static final int         PLAYER_Y_CENTER = 300;
    public	static final int     SLOOP_TURN_MAX = 3;
    public	static final int     FRIGATE_TURN_MAX = 2;
    public	static final int     MAN_OF_WAR_TURN_MAX = 1;
    public  static final double     TURN_AMOUNT_INCREMENT = 0.5;
    public  static final double     SPEED_AMOUNT_INCREMENT = 0.05;
    
    private GUI                                     gameGUI;
    protected HashMap<String, Ship> shipList;
    private ArrayList<Integer>      shipID;
    public HashMap<String, String> speedTable;
    private World                               gameWorld = null;
    private int                                 playerID;
    private boolean                             gameRunning = false;
    private int                                 shipSelection = 0;
    private String                              pMessage;
    private int                                 targetID = -1;
    private long lastTurn = 0;
        
    private     JFrame                          frame;
        
        public  Communication           comm;
    public      Thread                          commThread;
        
        
        
        //      wait lobby gui components
        JFrame                  lobbyWindow;
//      JPanel  lobbyWindow;
        JButton                 registerButton;
        JButton                 readyButton;

        JPanel                  bottomButtonPanel;
        JRadioButton    sloopButton;
        JRadioButton    frigateButton;
        JRadioButton    manOwarButton;
        ButtonGroup             lobbyButtonGroup;
        
        
        //      communication / message variables
        
        public  double                          turnAmount = 0.00;
        public  double                          speedAmount = 0.00;
        
        
        
    public Game()
        {
                createLobby();
                this.lobbyWindow.setVisible( true );
        
//        System.out.println(SpeedTable.speedTable[45]);
                
        this.shipList = new HashMap<String, Ship>();
        this.shipID = new ArrayList<Integer>();
        this.gameWorld = new World( );
        //this.gameGUI = new GUI( );
        this.frame = new JFrame ("Shark Bait");
      
        
        this.frame.setResizable(true);
        this.frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
                this.frame.setResizable( false );
        this.frame.pack();
        this.frame.setSize(1024, 768);
        this.frame.add(this, BorderLayout.CENTER);
        this.frame.setFocusTraversalKeysEnabled(false);
                this.frame.addKeyListener( this );
        //Thread thread = new Thread(this);
                //thread.start();
                this.commThread = new Thread(this.comm = new Communication(this, "localhost", 7430));
                this.commThread.start();
                while(true){
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
        public void createLobby( )
        {
                this.bottomButtonPanel = new JPanel( new GridLayout( 1,1 ) );
                this.lobbyWindow = new JFrame( "Shark Bait: Lobby" );

                this.readyButton = new JButton( "Register first!" );
                this.readyButton.setEnabled( false );
                this.registerButton = new JButton( "Register" );
                this.bottomButtonPanel.add( registerButton );
                this.bottomButtonPanel.add( readyButton );

                this.registerButton.setActionCommand( "register" );
                this.registerButton.addActionListener( this );
                this.readyButton.setActionCommand( "ready" );
                this.readyButton.addActionListener( this );
                this.lobbyWindow.setSize( 800, 640 );

                //      radio button selections
                this.lobbyButtonGroup   = new ButtonGroup();
                this.sloopButton                = new JRadioButton( "Sloop" );
                this.sloopButton.setActionCommand( "sloop_selected" );
                this.sloopButton.addActionListener( this );
                this.lobbyButtonGroup.add( sloopButton );
                
                this.frigateButton              = new JRadioButton( "Frigate" );
                this.frigateButton.setActionCommand( "frigate_selected" );
                this.frigateButton.addActionListener( this );
                this.lobbyButtonGroup.add( frigateButton );
                this.frigateButton.setSelected( true );

                this.manOwarButton              = new JRadioButton( "Man-o-war" );
                this.manOwarButton.setActionCommand( "man-o-war_selected" );
                this.manOwarButton.addActionListener( this );
                this.lobbyButtonGroup.add( manOwarButton );

                this.lobbyWindow.add( this.sloopButton, BorderLayout.LINE_START );
                this.lobbyWindow.add( this.frigateButton, BorderLayout.CENTER );
                this.lobbyWindow.add( this.manOwarButton, BorderLayout.LINE_END  );
                this.lobbyWindow.add( this.bottomButtonPanel, BorderLayout.PAGE_END );

        }
        public void paint ( Graphics g )
    {        
        Point playerPos = this.shipList.get(Integer.toString(this.playerID)).getPosition();
        
        if (playerPos != null) 
        {
            //      draw world
        	this.gameWorld.draw(g, playerPos);
            //      draw ships
        
        	for (String key : this.shipList.keySet()) {
        		this.shipList.get(key).draw(g, playerPos, targetID);
        	}//  draw chrome
        }
    }
    public void update()
    {
        //update ships
        for (String key : shipList.keySet()) {
            this.shipList.get(key).update(this.gameWorld.getWindDirection());
        }
        //update world
        //update chrome
        this.repaint();
    }
        public void actionPerformed( ActionEvent e )
        {
                System.out.println( "A button is clicked" );

                if ( e.getActionCommand().equals( "sloop_selected" ) )
                {
                        System.out.println( "SLOOP class ship selected" );
                        //      set the variable for the user's ship preference
                        this.shipSelection = 0;
                }
                if ( e.getActionCommand().equals( "frigate_selected" ) )
                {
                        System.out.println( "FRIGATE class ship selected" );
                        //      set the variable for the user's ship preference
                        this.shipSelection = 1;
                }
                if ( e.getActionCommand().equals( "man-o-war_selected" ) )
                {
                        System.out.println( "MAN-O-WAR class ship selected" );
                        //      set the variable for the user's ship preference
                        this.shipSelection = 2;
                }       
                        
                if ( e.getActionCommand().equals( "register" ) )
                {
                        System.out.println( "REGISTER button clicked" ) ;
                        this.sloopButton.setEnabled( false );
                        this.frigateButton.setEnabled( false );
                        this.manOwarButton.setEnabled( false );
                        this.registerButton.setEnabled( false );
                        this.registerButton.setText( "Waiting for registration..." );
                        //      send REGISTER message to server
                        this.comm.sendMessage( "register:"+shipSelection+";" );
                        
                }
                if ( e.getActionCommand().equals( "ready" ) )
                {
                        System.out.println( "READY button clicked" ) ;
                        this.readyButton.setEnabled( false );
                        this.readyButton.setText( "Waiting to start..." );
                        //      send READY message to server
                        this.comm.sendMessage( "ready;" );
                        
                }
                

        }
    public void keyTyped( KeyEvent pEvent )
    {
                
    }
    public void keyPressed( KeyEvent pEvent )
    {
        //      System.out.println("Key Pressed!!!");
                if( pEvent.getKeyCode( ) == KeyEvent.VK_UP ){
            System.out.println("Up!");
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
            System.out.println("Down!");
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
                	 System.out.println("Left!");
                     
                     int  turn = 0;
                     
                     if (this.shipList.get(Integer.toString(this.playerID)).getType() == 0)
                     {
                     	turn = -1*SLOOP_TURN_MAX;
                     }
                     
                     if (this.shipList.get(Integer.toString(this.playerID)).getType() == 1)
                     {
                     	turn = -1*FRIGATE_TURN_MAX;
                     }
                     
                     if (this.shipList.get(Integer.toString(this.playerID)).getType() == 2)
                     {
                     	turn = -1*MAN_OF_WAR_TURN_MAX;
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
                    System.out.println(System.currentTimeMillis() - this.lastTurn);
                    if(System.currentTimeMillis() - this.lastTurn > 100 || this.lastTurn == 0)
                    {
                        System.out.println("ADSDA");    
                        this.pMessage = "setHeading:" + turn + ";";
                        comm.sendMessage(pMessage);
                        this.lastTurn = System.currentTimeMillis();
                    }

         		}
                else if( pEvent.getKeyCode() == KeyEvent.VK_RIGHT ) 
                {
                    System.out.println("Right!");
                    
                    double maxIncrement = 0;
                    
                    if (this.shipList.get(Integer.toString(this.playerID)).getType() == 0)
                    {
                    	maxIncrement = SLOOP_TURN_MAX;
                    }
                    
                    if (this.shipList.get(Integer.toString(this.playerID)).getType() == 1)
                    {
                    	maxIncrement = FRIGATE_TURN_MAX;
                    }
                    
                    if (this.shipList.get(Integer.toString(this.playerID)).getType() == 2)
                    {
                    	maxIncrement = MAN_OF_WAR_TURN_MAX;
                    }
        			//	add to the amount of the turn, to a maximum of -25
        			//	turn amount is in degrees?
        			if ( this.turnAmount <= maxIncrement)
        			{
        				this.turnAmount += TURN_AMOUNT_INCREMENT;
        			}
        			else
        			{
        				this.turnAmount = maxIncrement;
        			}
        			
        			this.pMessage = "setHeading:" + this.turnAmount + ";";
        			comm.sendMessage(pMessage);
        		}
                else if( pEvent.getKeyCode() == KeyEvent.VK_TAB ) 
        {
                if (this.targetID == -1){
                        this.targetID = shipID.get(0);
                }
                
                for(int i = 0; i < shipID.size(); i++){
                        if (shipID.get(i).intValue() == targetID){
                                this.targetID = shipID.get((i+1) % shipID.size()).intValue();
                                break;
                        }
                }
                System.out.println(this.targetID);
            System.out.println("TAB!");
                }
        else if( pEvent.getKeyCode() == KeyEvent.VK_SPACE)
        {
                System.out.println("Space!");
        }
        }
    public void keyReleased(KeyEvent e)
    {
                
                //      send message
                /*System.out.println( "sending message to the server" );
                System.out.println( "Turning " + turnAmount + " degrees." );
                System.out.println( "Speed requested " + speedAmount );
                //      clear turnAmount, speedChange, and other message specific variables
                this.turnAmount = 0;*/
    }
    public void handleMessage(Message pMessage)
        {
        /*
         * Gameplay Messages
         */
        if( pMessage.getMessageName().equals( "shipState" ) )
        {
                    this.shipList.get(pMessage.getArgument( 0 )).updateShip( 
                                        Integer.parseInt( pMessage.getArgument( 1 ) ),          // int x
                                        Integer.parseInt( pMessage.getArgument( 2 ) ),          // int y
                                        Double.parseDouble( pMessage.getArgument( 3 ) ),        // double speed
                                        Integer.parseInt( pMessage.getArgument( 4 ) ),          // int direction
                                        Double.parseDouble( pMessage.getArgument( 5 ) )         // double damage
                                          );
            
        }
        else if( pMessage.getMessageName().equals( "ship" ) )
        {
            this.shipList.put( pMessage.getArgument( 0 ), new Ship( Integer.parseInt( pMessage.getArgument( 0 ) ), 
                                        Integer.parseInt(pMessage.getArgument( 1 ) ) ) );
            if (Integer.parseInt( pMessage.getArgument( 0 )) != playerID){
                shipID.add(new Integer( pMessage.getArgument( 0 )));
            }
        }
        else if( pMessage.getMessageName().equals( "wind" ) )
        {
            this.gameWorld.setWindSpeed( Integer.parseInt( pMessage.getArgument( 0 ) ) );
            this.gameWorld.setWindDirection( Integer.parseInt( pMessage.getArgument( 1 ) ) );
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
            //needs to be implemented
        }
        /*
         * Setup Messages
         */
        else if( pMessage.getMessageName().equals( "start" ) )
        {
            this.gameRunning = true;
            // close lobby window
            this.lobbyWindow.setVisible( false );
            this.frame.setVisible(true);
            // show game GUI
            // this.gameGUI.addKeyListener( this );
            // this.gameGUI.addToLayer(this, 1);
            // this.gameGUI.setVisible( true );
            
        }
        else if( pMessage.getMessageName().equals( "registered" ) )
        {
            this.playerID = Integer.parseInt( pMessage.getArgument( 0 ) );
                        //      set register button to "registered"
                        this.registerButton.setText( "Registered!" );
                        this.readyButton.setText( "Waiting for map" );
        }
        else if( pMessage.getMessageName().equals( "shore" ) )
        {
            // if shore x set ready button active
            if( pMessage.getArgumentsNum() == 1 && pMessage.getArgument(0).equals("x") )
            {
//                              this.lobbyWindow.setVisible( true );
                                this.readyButton.setEnabled( true );
                                this.readyButton.setText( "Ready to start?" );

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
        }
        }
    public static void main(String[] args)
        {
                Game game = new Game();
        }
}
