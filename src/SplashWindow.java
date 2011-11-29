

import java.awt.Container;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.TextField;
import javax.swing.JLabel;

import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SplashWindow {

	public	JFrame			frame;
	private JButton			serverConnectButton;
	public	TextField		serverAddressField, serverPortField;
	private JLabel			serverAddressLabel, serverPortLabel;
	private Dimension		Dim;
	private	ActionListener	listener;
	
	public SplashWindow ( ActionListener game ){
		this.listener = game;
		splashWindow();
	}
	
	
    private void splashWindow( ) {
		
		Dim = Toolkit.getDefaultToolkit().getScreenSize();

        //Create and set up the window.
        this.frame = new JFrame("AbsoluteLayoutDemo");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.frame.setResizable( false );
        //Set up the content pane.
        this.frame.getContentPane().setLayout( null );
        Insets insets = frame.getContentPane().getInsets();		

		try {
			this.frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("../images/startup_background.png")))));
		}
		catch ( IOException e ) {
			
		}

		
        this.serverConnectButton = new JButton("Connect to Server");
		this.serverConnectButton.addActionListener( this.listener );
        this.serverConnectButton.setActionCommand( "connect_to_server" );
		this.frame.getContentPane().add( this.serverConnectButton );
		
		this.serverAddressField = new TextField( "localhost" );
		this.frame.getContentPane().add( this.serverAddressField );
		this.serverAddressLabel = new JLabel( "Server IP:" );
		this.frame.getContentPane().add( this.serverAddressLabel );

		this.serverPortField	= new TextField( "5283" );
		this.frame.getContentPane().add( this.serverPortField );
		this.serverPortLabel = new JLabel( "Server Port:" );
		this.frame.getContentPane().add( this.serverPortLabel );

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