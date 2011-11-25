package SharkBait;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Lobby implements ActionListener {
	
	public JFrame lobbyWindow;
	private JMenuBar 				menu;
	private JMenu 					file;
	private JMenu					help;
    
    public JButton                 registerButton;
    public JButton                 readyButton;

    public JRadioButton    			sloopButton;
    public JRadioButton    			frigateButton;
    public JRadioButton    			manOwarButton;
    private ButtonGroup            	lobbyButtonGroup;
	
	public Lobby(ActionListener Game){
		createLobby();	
	}
	
	public void createLobby( )
    {
    		this.lobbyWindow = new JFrame( "Shark Bait: Lobby" );
    		menu = new JMenuBar();
    		this.lobbyWindow.setJMenuBar(menu);
    		this.lobbyWindow.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
            this.lobbyWindow.setResizable( false );
    		
    		file = new JMenu("File");
    		file.add("Close");
    		help = new JMenu("Help");
    		help.add("Controls");
    		
    		menu.add(file);
    		menu.add(help);
    		
    		JPanel topPanel = new JPanel();
    		topPanel.add(new JLabel("SHARK BAIT: UNCHARTERED WATERS!"));
    		
    		//	radio button selections
    		this.lobbyButtonGroup   = new ButtonGroup();
    		
    		JPanel SloopPanel = new JPanel();
    		this.sloopButton = new JRadioButton( "Sloop" );
            this.sloopButton.setActionCommand( "sloop_selected" );
            this.sloopButton.addActionListener( this );
            this.lobbyButtonGroup.add( sloopButton );
            SloopPanel.add( this.sloopButton);
    		
    		JPanel FrigatePanel = new JPanel();
    		this.frigateButton = new JRadioButton( "Frigate" );
            this.frigateButton.setActionCommand( "frigate_selected" );
            this.frigateButton.addActionListener( this );
            this.lobbyButtonGroup.add( frigateButton );
            this.frigateButton.setSelected( true );
            FrigatePanel.add( this.frigateButton);
            Toolkit img = null;;
            FrigatePanel.add(new JLabel( new ImageIcon( img.getDefaultToolkit().createImage("c://sloop"))));
            
    		JPanel ManOfWarPanel = new JPanel();
    		this.manOwarButton = new JRadioButton( "Man-o-war" );
            this.manOwarButton.setActionCommand( "man-o-war_selected" );
            this.manOwarButton.addActionListener( this );
            this.lobbyButtonGroup.add( manOwarButton );
            ManOfWarPanel.add( this.manOwarButton);
    		
            JPanel bottomButtonPanel = new JPanel();
            this.readyButton = new JButton( "Register first!" );
            this.readyButton.setEnabled( false );
            this.registerButton = new JButton( "Register" );
            bottomButtonPanel.add( registerButton );
            bottomButtonPanel.add( readyButton );

            this.registerButton.setActionCommand( "register" );
            this.registerButton.addActionListener( this );
            this.readyButton.setActionCommand( "ready" );
            this.readyButton.addActionListener( this );
            this.lobbyWindow.setSize( 300, 180 );
            
            this.lobbyWindow.add( topPanel, BorderLayout.PAGE_START);
            this.lobbyWindow.add( SloopPanel, BorderLayout.LINE_START );
            this.lobbyWindow.add( FrigatePanel, BorderLayout.CENTER );
            this.lobbyWindow.add( ManOfWarPanel, BorderLayout.LINE_END  );
            this.lobbyWindow.add( bottomButtonPanel, BorderLayout.PAGE_END );

    }
	
	public void draw(Graphics g){
		
	}
	
	public void update (){
		
	}

	public void actionPerformed(ActionEvent e) 
	{
	
	}

}
