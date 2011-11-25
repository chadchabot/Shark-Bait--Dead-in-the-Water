import java.util.HashMap;
import java.awt.Point;
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
        this.name = "noname";
                frames = new HashMap<String, BufferedImage>( );
                this.position = new Point(0,0);
        }
    public Sprite (String pName ) {
        this.name = pName;
        frames = new HashMap<String, BufferedImage>( );
        this.position = new Point(0,0);
    }
    
    public Sprite ( String pName, String pFileName )
	{
        frames = new HashMap<String, BufferedImage>( );
        this.position = new Point(0,0);
        this.name = pName;
        //      load default
        this.loadImage("default", pFileName);
                //      get image dimensions
        this.sWidth = frames.get("default").getWidth();
        this.sHeight = frames.get("default").getHeight();
        }
        
        //      loads a particular frame (or frames?) of the image
    public void loadImage ( String pName, String pFileName )
    {
        try
        {
            frames.put( pName, ImageIO.read( new File("../images/"+ pFileName + ".png") ) );
            if( pName.equals("default") )
            {
                this.sWidth = frames.get("default").getWidth();
                this.sHeight = frames.get("default").getHeight(); 
            }
        }
        catch ( IOException e )
        {
            System.out.println("Can't load image: loadImage("+ pFileName +") ARGGGG");
        }
        }
        
        //      change the index of the animation
        public void update ( Graphics g )
    {

        }
        public void draw ( Graphics g )
    {
        g.drawImage(this.frames.get(this.currentState), 
                    this.position.x, this.position.y, 
                    this.position.x + this.sWidth, this.position.y +this.sHeight, null);
        }
    
    public static void main(String[] args )
    {
        Sprite image = new Sprite( "water");
    }
}