import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements MessageListener, KeyListener
{
	public Communication comm;
	public Game()
	{
		
	}
	public void handleMessage(Message message)
	{
		message.printMessage();
	}
	public static void main(String[] args)
	{
		Game game = new Game();
		Thread theThread = new Thread(game.comm = new Communication(game, "localhost", 7430));
		theThread.start();
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