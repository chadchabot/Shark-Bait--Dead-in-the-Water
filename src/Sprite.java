import java.util.HashMap;
import java.awt.Point;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException; 
import javax.imageio.ImageIO;

public class Sprite {
	protected Point position;
    protected String name;
	protected int sWidth;
	protected int sHeight;
    protected int rotation;
    protected String currentState = "default";
	protected HashMap<String,BufferedImage> frames;

    public Sprite ( ) {
		frames = new HashMap<String, BufferedImage>( );
		this.position = new Point(0,0);
		
	}
    
	public Sprite ( String pName )
    {
        
        frames = new HashMap<String, BufferedImage>( );
        this.position = new Point(0,0);
        this.name = pName;
        //	load default
        try
        {
            frames.put( "default", ImageIO.read( new File( pName + ".png" ) ) );
        }
        catch( IOException e )
        {
            System.out.println("can't load image ARRRRRGGG");
        }
		//	get image dimensions
        this.sWidth = frames.get("default").getWidth();
        this.sHeight = frames.get("default").getHeight();
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
        System.out.println( "Width: " + this.sWidth + " Height: " +  this.sHeight );
    }
	public void update ( Graphics g )
    {

	}
	public void draw ( Graphics g )
    {
        g.drawImage(this.frames.get(this.currentState), 150, 150, 150+75, 150+75, 0, 0, 75, 75, null);
	}
    
    public static void main(String[] args )
    {
        Sprite image = new Sprite( "water");
        image.printDim();
    }
}