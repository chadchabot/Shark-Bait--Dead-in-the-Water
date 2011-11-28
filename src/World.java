package SharkBait;

import java.util.ArrayList;
import java.util.ListIterator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

public class World extends Sprite{
    
    public static final int		REFRESH_RATE = 60;
	public static final int 	PIXELS_PER_METER = 1;
    public static final int     PLAYER_X_CENTER = 500;
    public static final int     PLAYER_Y_CENTER = 300;

    private int  windDirection;
    private int  windSpeed;
    private int     time;
    private int     weatherState;
    private int     rain;
    private int     fog;
    private ArrayList<Polygon> shore;
    private int     width;
    private int     height;
    private int		frame;
    private String 	temp;
    private int		index = 0;
    
    //      default constructor
    public World ( ) {
        this.windDirection  = 45;
        this.windSpeed      = 0;
        this.time           = 1;
        this.weatherState   = 0;
        this.rain           = 0;
        this.fog            = 0;
        this.width          = 5000;
        this.height         = 4000;
        shore = new ArrayList<Polygon>();
        this.loadImage("fog", "fog");
        this.loadImage("water", "water");
        this.loadImage("rain0", "rain0");
        this.loadImage("rain1", "rain1");
        this.loadImage("rain2", "rain2");
        this.loadImage("rain3", "rain3");
    }
    public World (int pWidth, int pHeight, String pBack) {
        this.windDirection  = 45;
        this.windSpeed      = 0;
        this.time           = 1;
        this.weatherState   = 0;
        this.rain           = 0;
        this.fog            = 0;
        this.width          = pWidth;
        this.height         = pHeight;
        shore = new ArrayList<Polygon>();
    }
    
    public void addShore ( int [] pX, int [] pY, int pNumPoints  ) 
    {
            Polygon mShore = null;
                            
            try {
                    mShore = new Polygon (pX, pY, pNumPoints);
            }
            catch (NegativeArraySizeException e){
                    System.out.println( "Array size must be positive integer" );
            }
            catch (IndexOutOfBoundsException e){
                    System.out.println( "Array Index is out of bounds" );
            }
            catch (NullPointerException e){
                    System.out.println( "Array Null pointer exception" );
            }
            
            if (mShore != null){
                    this.shore.add(mShore);
            }
    }
    public void drawWeather ( Graphics g ){
    	
    	if ( this.fog == 1 )
    	{
	    	g.drawImage(this.frames.get("fog"), 0, 0, null);
    	}
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
    	
    }
    public void draw ( Graphics g, Point playerPos )
    {
        int drawX;// = (this.position.x - playerPos.x + PLAYER_X_CENTER/PIXELS_PER_METER)*PIXELS_PER_METER;
        int drawY;// = (this.position.y - playerPos.y + PLAYER_Y_CENTER/PIXELS_PER_METER)*PIXELS_PER_METER;
        
		g.drawImage(this.frames.get("water"), 0, 0, null);
        
        for(int i = 0; i< this.shore.size(); i++)
        {
            int scaledx[] = new int[this.shore.get(i).npoints];
            int scaledy[] = new int[this.shore.get(i).npoints];
            
            for(int j = 0; j < this.shore.get(i).npoints; j++)
            {
                scaledx[j] = this.shore.get(i).xpoints[j]*PIXELS_PER_METER;
                scaledy[j] = this.shore.get(i).ypoints[j]*PIXELS_PER_METER;
                System.out.println("("+scaledx[j]+","+scaledy[j]+")");
            }
            
            Polygon copy = new Polygon(scaledx, scaledy, this.shore.get(i).npoints);
            Rectangle bounds = new Rectangle(copy.getBounds());
            
            Point polycenter = new Point( bounds.x + ((int)bounds.getWidth( ) )/2,  bounds.y + ((int)bounds.getHeight( ) )/2 );
            
            drawX = PLAYER_X_CENTER - (playerPos.x*PIXELS_PER_METER - polycenter.x);
            drawY = PLAYER_Y_CENTER - (playerPos.y*PIXELS_PER_METER - polycenter.y);
            
            /* AffineTransform at = new AffineTransform();
             at.scale(PIXELS_PER_METER, PIXELS_PER_METER);
             copy.getPathIterator(at);*/
            
            copy.translate((drawX - polycenter.x),( drawY - polycenter.y));
            
            Graphics2D g2D = (Graphics2D) g;     
            g2D.setStroke(new BasicStroke(10F));
            
            g.setColor(Color.GREEN);
            g.drawPolygon(copy);
            g.fillPolygon(copy);
            
            g.setColor(Color.YELLOW);
            g.drawPolygon(copy);
            copy.reset();
        }
    }
    /*
     *
     * Mutators
     *
     */
    public void setWindDirection ( int pWindDirection ) 
    {
        this.windDirection = pWindDirection;
    }
    public void setWindSpeed ( int pWindSpeed ) 
    {
        this.windSpeed = pWindSpeed;
    }
    public void setTime ( int pTime ) 
    {
        this.time = pTime;
    }
    public void setWeatherState ( int pWeatherState ) 
    {
        this.weatherState = pWeatherState;
    }
    public void setWorldDimensions ( int pWidth , int pHeight) 
    {
        this.width = pWidth;
        this.height = pHeight;
    }
    public void setRain ( int pRain ) 
    {
    	System.out.println("Rain on them hoes");
        this.rain = pRain;
    }
    public void setFog ( int pFog ) 
    {
        this.fog = pFog;
    }
    /*
     *
     * Accessors
     *
     */
    public int getWindDirection ( ) 
    {
        return this.windDirection;
    }
    public int getWindSpeed ( ) 
    {
        return this.windSpeed;
    }
    public int getTime ( ) 
    {
        return this.time;
    }
    public int getWeatherState ( ) 
    {
        return this.weatherState;
    }
    public int getWorldWidth ( ) 
    {
        return this.width;
    }
    public int getWorldHeight ( ) 
    {
        return this.height;
    }
}