import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JButton;


public class Game implements MessageListener, KeyListener, ActionListener
{
/*	
	private int					playerID;
	private Collection<Ship>	shipList;
	private World				gameWorld;
	private GUI					gameGUI;
 */
	
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
	}
	public void handleMessage(Message pMessage)
	{
		pMessage.printMessage();
        /*
         * Gameplay Messages
         */
        if( pMessage.getMessageName().equals( "shipstate" ) )
        {
            
        }
        else if( pMessage.getMessageName().equals( "ship" ) )
        {
           
        }
        else if( pMessage.getMessageName().equals( "wind" ) )
        {
            
        }
        else if( pMessage.getMessageName().equals( "fog" ) )
        {
            
        }
        else if( pMessage.getMessageName().equals( "rain" ) )
        {
            
        }
        else if( pMessage.getMessageName().equals( "time" ) )
        {
            
        }
        /*
         * Setup Messages
         */
        else if( pMessage.getMessageName().equals( "start" ) )
        {
            //close lobby window
        }
        else if( pMessage.getMessageName().equals( "registered" ) )
        {
            // set player ship properties
        }
        else if( pMessage.getMessageName().equals( "shore" ) )
        {
            if( pMessage.getArgumentsNum() == 1 && pMessage.getArgument(0).equals("x") )
            {
                System.out.println("I am Lobby!");
//				lobbyVisible( true );
				this.lobbyWindow.setVisible( true );
				//createLobby();
                //runLobby();
            }
            // handle shore 
            // if shore x set ready button active
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
		//		readyState = true;
		System.out.println( "A button is clicked is performed" );
		
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