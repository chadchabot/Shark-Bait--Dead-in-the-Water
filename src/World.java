/**
 * @author      Bonne Justin jbonne@uoguelph.ca
 * @author      Cardinal, Blake bcardina@uoguelph.ca
 * @author      Chabot, Chad chabot@uoguelph.ca
 * @version     0.9                 
 * @since       2011-11-29
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.TexturePaint;

import java.util.ArrayList;
import java.util.ListIterator;

public class World extends Sprite
{
    //game constants
    public static final int		REFRESH_RATE = 60;
	public static final int 	PIXELS_PER_METER = 3;
    public static final int     PLAYER_X_CENTER = 500;
    public static final int     PLAYER_Y_CENTER = 300;

    // world height and width
    private int                 width;
    private int                 height;
    
    // environment details
    private int                 windDirection;
    private int                 windSpeed;
    private int                 time;
    private int                 rain;
    private int                 fog;
    
    // used for animations and frame tracking
    private int                 frame;
    private String              temp;
    private int                 index = 0;
    
    // list of island polygons and texture paint for island graphic
    private ArrayList<Polygon>  shore;
    private TexturePaint        tp;
    
    /**
     * Class constructor sets intialized game environment with default values
     * Loads images and frames for weather effects and islands
     */
    public World ( ) 
    {
        this.windDirection  = 45;
        this.windSpeed      = 0;
        this.time           = 1;
        this.rain           = 0;
        this.fog            = 0;
        this.width          = 5000;
        this.height         = 4000;
        this.shore = new ArrayList<Polygon>();
        this.loadImage("land", "land");
        this.loadImage("water", "water");
        this.loadImage("fog", "fog");
        this.loadImage("rain0", "rain_0");
        this.loadImage("rain1", "rain_1");
        this.loadImage("rain2", "rain_2");
        this.loadImage("rain3", "rain_3");
        this.loadImage("night", "night");
        this.loadImage("dawn", "dawn");
    }
    
    /**
     * Constructs a polygon out of an array of x cooridinates and
     * y coordinates
     *
     * to properly create the polygon the points must be sent in
     * in the clockwise direction
     *
     * @param pX array of x coordinates
     * @param pY array of y coordinates
     * @param pNumPoints number of vertices in the polygon
     */
    public void addShore ( int [] pX, int [] pY, int pNumPoints  ) 
    {
            Polygon mShore = null;
                            
            try {
                    mShore = new Polygon (pX, pY, pNumPoints);
            }
            catch ( NegativeArraySizeException e ){
                System.out.println( "ERROR: Couldn't create polygon" );
                System.out.println( "Array size must be positiveinteger" );
            }
            catch ( IndexOutOfBoundsException e ){
                    System.out.println( "ERROR: Couldn't create polygon" );
                    System.out.println( "Array Index is out of bounds" );
            }
            catch ( NullPointerException e ){
                    System.out.println( "ERROR: Couldn't create polygon" );
                    System.out.println( "Array Null pointer exception" );
            }
            if (mShore != null){
                    this.shore.add(mShore);
            }
    }
    /**
     * Draw the weather effects of the game to the passed graphics object
     *
     * @param g Graphics object that the weather will be drawn on
     */
    public void drawWeather ( Graphics g )
    {
    	
        // draw fog
    	if ( this.fog == 1 )
    	{
	    	g.drawImage(this.frames.get("fog"), 0, 0, null);
    	}
        
        // draw and animate rain
    	if ( this.rain == 1)
    	{
    		this.frame++;
    		
    		if (this.frame == 10) {
    			this.index++;
    			this.temp = "rain" + (this.index % 4);
        		this.frame = 0;
        	}
    		
    		g.drawImage(this.frames.get(temp), 0, 0, null);
            
    	}
        
        //draw the time conditions
        if(this.time == 0)
        {
            g.drawImage(this.frames.get("dawn"), 0, 0, null);
        }
        else if(this.time == 2 )
        {
            g.drawImage(this.frames.get("night"), 0, 0, null);
        }
    }
    
    /**
     * Draw the water background and the polygons to a graphics object
     *
     * @param g Graphics object that the weather will be drawn on
     * @param playerPos player position used to calculate relative positions
     */
    public void draw ( Graphics g, Point2D.Double playerPos )
    {
    	Graphics2D g2D = ( Graphics2D ) g;
    	
        int drawX;
        int drawY;
        
        //draw water back ground
        tp = new TexturePaint(this.frames.get("water"), 
                              new Rectangle(1024, 768));
        g2D.setPaint(tp);
		g2D.fillRect(0, 0, 1024, 768);
        
        //draw and move islands to their position relavtive to the player
        for( int i = 0; i< this.shore.size(); i++ )
        {
            int scaledx[ ] = new int[this.shore.get( i ).npoints ];
            int scaledy[ ] = new int[this.shore.get( i ).npoints ];
            
            // scale the islands to the porper size for PIXEL_PER_METER ratio
            for( int j = 0; j < this.shore.get( i ).npoints; j++ )
            {
                scaledx[ j ] = this.shore.get( i ).xpoints[ j ]*PIXELS_PER_METER;
                scaledy[ j ] = this.shore.get( i ).ypoints[ j ]*PIXELS_PER_METER;
            }
            
            //use the scaled points to create a new polygon
            Polygon copy = new Polygon( scaledx, scaledy, 
                                       this.shore.get( i ).npoints );
            
            //find the center of the new scaled island
            Rectangle bounds = new Rectangle( copy.getBounds( ) );
            Point2D.Double polycenter = new Point2D.Double( bounds.x + ( (int)bounds.getWidth( ) )/2,  
                                         bounds.y + ( (int)bounds.getHeight( ) )/2 );
            
            //find where the island should be drawn on screen
            drawX = new Double(PLAYER_X_CENTER 
                    - ( playerPos.getX()*PIXELS_PER_METER - polycenter.getX() )).intValue();
            drawY = new Double(PLAYER_Y_CENTER 
                    - ( playerPos.getY()*PIXELS_PER_METER - polycenter.getY() )).intValue();
            
            //move the island to where it should be drawn
            copy.translate( (drawX - new Double(polycenter.getX()).intValue()),( drawY - new Double(polycenter.getY()).intValue()) ); 
            
            
            //start drawing the island
            
            // load the texture paint and add it to the graphics context.
            tp = new TexturePaint( this.frames.get( "land" ), 
                                  new Rectangle( copy.getBounds( ) ) );
            g2D.setPaint( tp );    
            g2D.setStroke( new BasicStroke( 5F ) );
            
            //draw and fille the polygon with the texture
            g.drawPolygon( copy );
            g.fillPolygon( copy );
            
            //draw the sand
            g.setColor( new Color( 255, 226, 28 ) );
            g.drawPolygon( copy );
            copy.reset( );
        }
    }
    /*
     *
     * Mutators
     *
     */
    /**
     * Mutator for the wind direction
     *
     * should be between 0 and 359
     *
     * @param pWindDirection the new direction for the wind
     */
    public void setWindDirection ( int pWindDirection ) 
    {
        this.windDirection = pWindDirection;
    }
    /**
     * Mutator for the wind speed
     * Wind speed is not currently used in the game
     *
     * @param pWindSpeed the new speed for the wind
     */
    public void setWindSpeed ( int pWindSpeed ) 
    {
        this.windSpeed = pWindSpeed;
    }
    /**
     * Mutator for the environment time
     *
     * 0 = "dawn"
     * 1 = "midday"
     * 2 = "night"
     *
     * @param pTime the new time for the environment
     */
    public void setTime ( int pTime ) 
    {
        this.time = pTime;
    }
    /**
     * Mutator for the world dimensions
     *
     * @param pWidth the width of the world in "game units" or meters
     * as we like to call them
     * @param pHeight the height of the world in "game units" or meters
     * as we like to call them
     */
    public void setWorldDimensions ( int pWidth , int pHeight ) 
    {
        this.width = pWidth;
        this.height = pHeight;
    }
    /**
     * Mutator for the rain
     *
     * turns the rain in the environment on or off
     *
     * 0 = off
     * 1 = on
     *
     * may be made boolean in the future
     *
     * @param pRain integer flag to turn the rain on or off
     */
    public void setRain ( int pRain ) 
    {
        this.rain = pRain;
    }
    /**
      * Mutator for the fog
      *
      * turns the fog in the environment on or off
      *
      * 0 = off
      * 1 = on
      *
      * may be made boolean in the future
      *
      * @param pFog integer flag to turn the fog on or off
      */
    public void setFog ( int pFog ) 
    {
        this.fog = pFog;
    }
    /*
     *
     * Accessors
     *
     */
    /**
     * Accessor for the wind direction
     *
     * @return the game environments current wind direction
     */
    public int getWindDirection ( ) 
    {
        return this.windDirection;
    }
    /**
     * Accessor for the wind speed
     *
     * @return the game environments current wind speed
     */
    public int getWindSpeed ( ) 
    {
        return this.windSpeed;
    }
    /**
     * Accessor for the game environment time
     *
     * @return the game environments current time
     */
    public int getTime ( ) 
    {
        return this.time;
    }
    /**
     * Accessor for the width of the game environment
     *
     * @return the game environments current width
     */
    public int getWorldWidth ( ) 
    {
        return this.width;
    }
    /**
     * Accessor for the height of the game environment
     *
     * @return the game environments current height
     */
    public int getWorldHeight ( ) 
    {
        return this.height;
    }
}