/**
 * @author      Bonne Justin jbonne@uoguelph.ca
 * @author      Cardinal, Blake bcardina@uoguelph.ca
 * @author      Chabot, Chad chabot@uoguelph.ca
 * @version     0.9                 
 * @since       2011-11-29
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Point;

import java.io.File;
import java.io.IOException; 

import java.util.HashMap;

import javax.imageio.ImageIO;

public class Sprite 
{
    // details
    protected String                        name;
    protected Point                         position;
    protected int                           sWidth = 0;
    protected int                           sHeight = 0;
    protected int                           rotation = 0;
    
    //used for animations
    protected String                        currentState = "default";
    protected HashMap<String,BufferedImage> frames;
    
    /**
     * Class constructor intializes the name, frames hashmap and position to default values
     */
    public Sprite ( ) 
    {
        this.name = "noname";
        this.frames = new HashMap<String, BufferedImage>( );
        this.position = new Point(0,0);
    }
    /**
     * Class constructor intializes the frames hashmap and position to default values
     *
     * @param pName name of the sprite
     */
    public Sprite ( String pName ) 
    {
        this.name = pName;
        frames = new HashMap<String, BufferedImage>( );
        this.position = new Point(0,0);
    }
    /**
     * Class constructor intializes the frames hashmap and position to default values
     * Setups up the default frame image to specific image
     *
     * @param pName name of the sprite
     * @param pFileName name of the image used for the default frame
     */
    public Sprite ( String pName, String pFileName )
	{
        frames = new HashMap<String, BufferedImage>( );
        this.position = new Point(0,0);
        this.name = pName;
        
        // load default frame
        this.loadImage("default", pFileName);
        
        // get image dimensions
        this.sWidth = frames.get("default").getWidth();
        this.sHeight = frames.get("default").getHeight();
    }
    /**
     * loads an image and adds to the frames hashmap using a secified key
     *
     * only looks in the images folder
     * automatically appends .png to the filename so it is not needed
     *
     * @param pFrameName the desired key for the frame
     * @param name of the image file
     */
    public void loadImage ( String pFrameName, String pFileName )
    {
        try
        {
            frames.put( pFrameName, 
                       ImageIO.read( new File("../images/"+ pFileName + ".png") ) );
            
            // if a new default frame is being loaded reset the dimensions to match
            // the new image
            if( pFrameName.equals("default") )
            {
                this.sWidth = frames.get("default").getWidth();
                this.sHeight = frames.get("default").getHeight(); 
            }
        }
        catch ( IOException e )
        {
            System.out.println("Sprite: Can't load image: loadImage("+ pFileName +") ARGGGG");
        }
    }
    /**
     * usually updates the sprite based on some calculation
     *
     * extended in subclasses
     */
    public void update ( )
    {

    }
    /**
     * Draw the sprite to a graphics object
     *
     * @param g Graphics object that the sprite will be drawn on
     */
    public void draw ( Graphics g )
    {
        g.drawImage(this.frames.get(this.currentState), 
                    this.position.x, this.position.y, 
                    this.position.x + this.sWidth, this.position.y +this.sHeight, null);
    }
}