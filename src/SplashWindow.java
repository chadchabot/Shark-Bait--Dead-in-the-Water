/**
 * @author      Bonne Justin jbonne@uoguelph.ca
 * @author      Cardinal, Blake bcardina@uoguelph.ca
 * @author      Chabot, Chad chabot@uoguelph.ca
 * @version     0.9                 
 * @since       2011-11-29
 */

import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.Toolkit;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SplashWindow {

	public	JFrame			frame;
    public	TextField		serverAddressField, serverPortField;
    
	private JButton			serverConnectButton;
	private JLabel			serverAddressLabel, serverPortLabel;
	private Dimension		Dim;
	private	ActionListener	listener;
	
    /**
     * Class constructor sets up action listener
     *
     * creates splash window
     */
	public SplashWindow ( ActionListener game )
    {
		this.listener = game;
		splashWindow();
	}
    /**
     * 
     */
    private void splashWindow( ) 
    {
		
		Dim = Toolkit.getDefaultToolkit( ).getScreenSize();

        //Create and set up the window.
        this.frame = new JFrame("AbsoluteLayoutDemo");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable( false );
        
        //Set up the content pane.
        this.frame.getContentPane().setLayout( null );
        Insets insets = frame.getContentPane().getInsets();		

        //load background image
		try 
        {
			this.frame.setContentPane( new JLabel( new ImageIcon(
                 ImageIO.read( 
                  this.getClass( ).getResourceAsStream( "images/startup_background.png" ) 
                  )
                ) ) );
		}
		catch ( IOException e ) 
        {
			System.out.println( "ERROR: SplashWindow" );
            System.out.println( "Could not load image" );
		}

		
        //connect button
        this.serverConnectButton = new JButton("Connect to Server");
		this.serverConnectButton.addActionListener( this.listener );
        this.serverConnectButton.setActionCommand( "connect_to_server" );
		this.frame.getContentPane().add( this.serverConnectButton );
		
        //address field
		this.serverAddressField = new TextField( "localhost" );
		this.frame.getContentPane().add( this.serverAddressField );
		
        //address label
        this.serverAddressLabel = new JLabel( "Server IP:" );
		this.frame.getContentPane().add( this.serverAddressLabel );

        //port field
		this.serverPortField	= new TextField( "5283" );
		this.frame.getContentPane().add( this.serverPortField );
		
        //port label
        this.serverPortLabel = new JLabel( "Server Port:" );
		this.frame.getContentPane().add( this.serverPortLabel );

        //set bounds for stuff
		serverAddressLabel.setBounds ( 634 + insets.left, 488 + insets.top, 100, 50 );		
		serverAddressField.setBounds ( 700 + insets.left, 500 + insets.top, 200, 20 );
		
		serverPortLabel.setBounds	 ( 620 + insets.left, 535 + insets.top, 100, 50 );		
        serverPortField.setBounds	 ( 700 + insets.left, 550 + insets.top, 200, 20 );
        serverConnectButton.setBounds( 700 + insets.left, 600 + insets.top, 200, 50 );
		
        //Size and display the window.
		this.frame.setSize( 1024, 768 );
		
		int w = this.frame.getSize().width;
		int h = this.frame.getSize().height;
		int x = ( Dim.width-w )/2;
		int y = ( Dim.height-h )/2;
		this.frame.setLocation(x, y);
        this.frame.setVisible(true);
    }

}