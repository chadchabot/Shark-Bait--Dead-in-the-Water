import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JComponent;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Point;

public class Game extends JComponent implements MessageListener, KeyListener, ActionListener
{

    public static final int		REFRESH_RATE = 60;
	private GUI					gameGUI;
	protected HashMap<String, Ship> shipList;
    private World				gameWorld = null;
    private int					playerID;	
    private	JFrame				frame;
	
	public	Communication		comm;
    public	Thread				commThread;
	
	
	JFrame	lobbyWindow;
	JButton	readyButton;
	
	
	//	communication / message variables
	public	static final double	TURN_AMOUNT_MAX = 25.0;
	public	static final double	TURN_AMOUNT_INCREMENT = 0.5;
	public	double				turnAmount = 0.00;
	
	public	static final double	SPEED_AMOUNT_INCREMENT = 0.05;
	public	double				speedAmount = 0.00;
	
	
	
    public Game()
	{
		createLobby();
        this.shipList = new HashMap<String, Ship>();
        this.gameWorld = new World( );
        //this.gameGUI = new GUI( );
        this.frame = new JFrame ("Shark Bait");
      
        
        this.frame.setResizable(true);
        this.frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable( false );
        this.frame.pack();
        this.frame.setSize(1024, 768);
        this.frame.setVisible(true);
        this.frame.add(this, BorderLayout.CENTER);
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
			this.update();
			
		}
        
	}
	public void createLobby( )
	{
		this.lobbyWindow = new JFrame( "Shark Bait: Lobby" );
		this.readyButton = new JButton( "Ready" );
		this.readyButton.addActionListener( this );		
		this.lobbyWindow.setSize( 480, 240 );
		this.lobbyWindow.add( readyButton );
        //this.lobbyWindow.addKeyListener(this);
	}
    public void paint ( Graphics g )
    {
        Point playerPos = this.shipList.get(Integer.toString(this.playerID)).getPosition();
		//	draw world
        this.gameWorld.draw(g);
		//	draw ships
        
        for (String key : shipList.keySet()) {
            this.shipList.get(key).draw(g, playerPos);
        }//  draw chrome
       
    }
    public void update()
    {
        //update ships
    	for (String key : shipList.keySet()) {
            this.shipList.get(key).update();
        }
        //update world
        //update chrome
    	this.repaint();
    }
	public void actionPerformed( ActionEvent e )
	{
		System.out.println( "A button is clicked" );
		
        this.readyButton.setEnabled( false );
		this.readyButton.setText( "Waiting to start..." );
	}
    public void keyTyped( KeyEvent pEvent )
    {
		
    }
    public void keyPressed( KeyEvent pEvent )
    {
        //	System.out.println("Key Pressed!!!");
		if( pEvent.getKeyCode( ) == KeyEvent.VK_UP ){
            System.out.println("Up!");
			//	change the current speed of the player's ship
			this.speedAmount += SPEED_AMOUNT_INCREMENT;

			if ( this.speedAmount > 1.00 )
			{
				this.speedAmount = 1.00;
			}
			
		}
		else if( pEvent.getKeyCode( ) == KeyEvent.VK_DOWN )
        {
            System.out.println("Down!");
			//	change the current speed of the player's ship
			this.speedAmount -= SPEED_AMOUNT_INCREMENT;

			if ( this.speedAmount < 0.00 )
			{
				this.speedAmount = 0.00;
			}
		}
		else if( pEvent.getKeyCode() == KeyEvent.VK_LEFT )
        { 
            System.out.println("Left!");
			//	add to the amount of the turn, to a maximum of -25
			//	turn amount is in degrees?
			if ( this.turnAmount >= -TURN_AMOUNT_MAX )
			{
				this.turnAmount -= TURN_AMOUNT_INCREMENT;
			}
			else
			{
				this.turnAmount = - TURN_AMOUNT_MAX;
			}

		}
		else if( pEvent.getKeyCode() == KeyEvent.VK_RIGHT ) 
        {
            System.out.println("Right!");
			//	add to the amount of the turn, to a maximum of +25
			//	turn amount is in degrees?
			if ( this.turnAmount <= TURN_AMOUNT_MAX )
			{
				this.turnAmount += TURN_AMOUNT_INCREMENT;
			}
			else
			{
				this.turnAmount = TURN_AMOUNT_MAX;
			}
		}
        else if( pEvent.getKeyCode() == KeyEvent.VK_TAB ) 
        {
            System.out.println("TAB!");
		}
	}
    public void keyReleased(KeyEvent e)
    {
		
		//	send message
		System.out.println( "sending message to the server" );
		System.out.println( "Turning " + turnAmount + " degrees." );
		System.out.println( "Speed requested " + speedAmount );
		//	clear turnAmount, speedChange, and other message specific variables
		this.turnAmount = 0;
    }
    public void handleMessage(Message pMessage)
	{
		pMessage.printMessage();
        /*
         * Gameplay Messages
         */
        if( pMessage.getMessageName().equals( "shipState" ) )
        {
                    this.shipList.get(pMessage.getArgument( 0 )).updateShip( 
                                        Integer.parseInt( pMessage.getArgument( 1 ) ),		// int x
                                        Integer.parseInt( pMessage.getArgument( 2 ) ),		// int y
                                        Double.parseDouble( pMessage.getArgument( 3 ) ),	// double speed
                                        Integer.parseInt( pMessage.getArgument( 4 ) ),		// int direction
                                        Double.parseDouble( pMessage.getArgument( 5 ) )		// double damage
                                 	  );
            
        }
        else if( pMessage.getMessageName().equals( "ship" ) )
        {
            this.shipList.put( pMessage.getArgument( 0 ), new Ship( Integer.parseInt( pMessage.getArgument( 0 ) ), 
                                        Integer.parseInt(pMessage.getArgument( 1 ) ) ) );
            System.out.println("SIZE: "+    this.shipList.size());
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
            System.out.println("Starting Game");
            // close lobby window
            this.lobbyWindow.setVisible( false );
            // show game GUI
            // this.gameGUI.addKeyListener( this );
            // this.gameGUI.addToLayer(this, 1);
            // this.gameGUI.setVisible( true );
            
        }
        else if( pMessage.getMessageName().equals( "registered" ) )
        {
            this.playerID = Integer.parseInt( pMessage.getArgument( 0 ) );
        }
        else if( pMessage.getMessageName().equals( "shore" ) )
        {
            // if shore x set ready button active
            if( pMessage.getArgumentsNum() == 1 && pMessage.getArgument(0).equals("x") )
            {
                System.out.println("I am Lobby!");
				this.lobbyWindow.setVisible( true );
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