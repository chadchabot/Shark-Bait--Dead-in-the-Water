import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import java.io.File;
import java.io.IOException; 
import javax.imageio.ImageIO;

public class Sprite extends JComponent {
	private Point position;
    private String name;
	private int index;
	private int width;
	private int height;
    private int rotation;
    private String currentState = "default";
	private HashMap<String,BufferedImage> frames;

    public Sprite ( ) {
		frames = new HashMap<String, BufferedImage>( );
	}
    
	public Sprite ( String pName )
    {
        BufferedImage temp;
        
        frames = new HashMap<String, BufferedImage>( );
        this.name = pName;
        //	load default
        try
        {
            frames.put( "default", ImageIO.read( new File("../images/" + pName + ".png" ) ) );
        }
        catch( IOException e )
        {
            System.out.println("can't load image ARRRRRGGG");
        }
		//	get image dimensions
        temp = frames.get("default"); 
        this.width = temp.getWidth();
        this.height = temp.getHeight();
	}
	
	//	loads a particular frame (or frames?) of the image
	public void loadImage ( String pName )
    {
        try
        {
            frames.put( pName, ImageIO.read( new File(pName + ".png") ) );
        }
        catch ( IOException e )
        {
            System.out.println("Can't load image: loadImage() ARGGGG");
        }
	}
	
	//	change the index of the animation
    public void printDim( )
    { 
        System.out.println( "Width: " + this.width + " Height: " +  this.height );
    }
	public void update ( Graphics g )
    {
		
	}
	public void draw ( Graphics g )
    {
        
	}
    public void paint ( Graphics g )
    {
        super.paint(g);
        g.drawImage(this.frames.get(this.currentState), 150, 150, 150+75, 150+75, 0, 0, 75, 75,null);
        /*Point origin = this.getLocation();
        for( int i = 0; i < 1024; i = i + 75 )
        {
            for( int j = 0; j < 768; j = j + 75)
            {
                g.drawImage(this.frames.get(this.currentState), i, j, i+75, j+75, 0, 0, 75, 75,null);
            }
        }*/
	}
    public static void main(String[] args )
    {
        Sprite image = new Sprite( "water");
        image.printDim();
    }
}