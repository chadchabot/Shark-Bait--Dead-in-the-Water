import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;


public class Game implements MessageListener, KeyListener, ActionListener
{
/*	
	private GUI					gameGUI;
 */
    private Collection<Ship>	shipList;
    private World				gameWorld = null;
    private int					playerID;	
	
	public Communication comm;
    public Thread commThread;
	
	
	JFrame lobbyWindow;
	JButton readyButton;
	
	public void createLobby( )
	{
		this.lobbyWindow = new JFrame( "Shark Bait: Lobby" );
		this.readyButton = new JButton( "Ready" );
		this.readyButton.addActionListener( this );		
		this.lobbyWindow.setSize( 480, 240 );
		this.lobbyWindow.add( readyButton );
		
	}
	
	public void lobbyVisible( boolean pParam)
	{
		this.lobbyWindow.setVisible( pParam );
	}
	
	public Game()
	{
		createLobby();
        this.gameWorld = new World( );
	}
	public void handleMessage(Message pMessage)
	{
		pMessage.printMessage();
        /*
         * Gameplay Messages
         */
        if( pMessage.getMessageName().equals( "shipstate" ) )
        {
            Iterator mIterator = this.shipList.iterator();
            
            while( mIterator.hasNext() )
            {
                Ship tempShip = (Ship)mIterator.next();
                if( Integer.parseInt( pMessage.getArgument( 0 ) ) == tempShip.getShipID( ) )
                {
                    tempShip.updateShip( 
                        Integer.parseInt( pMessage.getArgument( 1 ) ),  // int x
                        Integer.parseInt( pMessage.getArgument( 2 ) ),  // int y
                        Double.parseDouble( pMessage.getArgument( 3 ) ),  // double speed
                        Integer.parseInt( pMessage.getArgument( 4 ) ),  // int direction
                        Double.parseDouble( pMessage.getArgument( 5 ) )   // double damage
                    );
                }
            }
            
        }
        else if( pMessage.getMessageName().equals( "ship" ) )
        {
            this.shipList.add( new Ship( Integer.parseInt( pMessage.getArgument( 0 ) ),
                                        Integer.parseInt( pMessage.getArgument( 1 ) ) ) );
           
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
            
        }
        /*
         * Setup Messages
         */
        else if( pMessage.getMessageName().equals( "start" ) )
        {
            //close lobby window
            this.lobbyWindow.setVisible( false );
            //show game GUI
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
                    /*
                     num points = 3
                        i == 0
                            x = 2 * i + 1 = 1
                            y = 2 * i + 2 = 2
                        i == 1
                            x = 2 * i + 1 = 3
                            y = 2 * i + 2 = 4
                        i == 2
                            x = 2 * i + 1 = 5
                            y = 2 * i + 2 = 6
                     done
                     */
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
		game.commThread = new Thread(game.comm = new Communication(game, "localhost", 7430));
		game.commThread.start();
	}
	
	public void actionPerformed( ActionEvent e )
	{
		System.out.println( "A button is clicked" );
		
        this.readyButton.setEnabled( false );
		this.readyButton.setText( "Waiting to start..." );
	}
    
    public void keyTyped( KeyEvent pEvent )
    {}
	
    public void keyPressed( KeyEvent pEvent )
    {
		if( pEvent.getKeyCode( ) == KeyEvent.VK_UP ){
            
		}
		else if( pEvent.getKeyCode( ) == KeyEvent.VK_DOWN )
        {
            System.out.println("Down!");
		}
		else if( pEvent.getKeyCode() == KeyEvent.VK_LEFT )
        { 
            System.out.println("Left!");
		}
		else if( pEvent.getKeyCode() == KeyEvent.VK_RIGHT ) 
        {
            System.out.println("Right!");
		}
        else if( pEvent.getKeyCode() == KeyEvent.VK_TAB ) 
        {
            System.out.println("TAB!");
		}
	}
	
    public void keyReleased(KeyEvent e)
    {}
}