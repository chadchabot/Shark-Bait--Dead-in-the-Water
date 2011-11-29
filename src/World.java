
import java.util.ArrayList;
import java.util.ListIterator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.TexturePaint;

import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

public class World extends Sprite{
    
    public static final int		REFRESH_RATE = 60;
	public static final int 	PIXELS_PER_METER = 3;
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
    private TexturePaint tp;
    
    //      default constructor
    public World ( ) {
        this.windDirection  = 45;
        this.windSpeed      = 0;
        this.time           = 0;
        this.weatherState   = 0;
        this.rain           = 0;
        this.fog            = 0;
        this.width          = 5000;
        this.height         = 4000;
        shore = new ArrayList<Polygon>();
        this.loadImage("fog", "fog");
        this.loadImage("land", "land");
        this.loadImage("water", "water");
        this.loadImage("rain0", "rain_0");
        this.loadImage("rain1", "rain_1");
        this.loadImage("rain2", "rain_2");
        this.loadImage("rain3", "rain_3");
        this.loadImage("night", "night");
        this.loadImage("dawn", "dawn");
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
        if(this.time == 0)
        {
            g.drawImage(this.frames.get("dawn"), 0, 0, null);
        }
        else if(this.time == 2 )
        {
            g.drawImage(this.frames.get("night"), 0, 0, null);
        }
    }
    public void draw ( Graphics g, Point playerPos )
    {
    	Graphics2D g2D = (Graphics2D) g;
    	
        int drawX;// = (this.position.x - playerPos.x + PLAYER_X_CENTER/PIXELS_PER_METER)*PIXELS_PER_METER;
        int drawY;// = (this.position.y - playerPos.y + PLAYER_Y_CENTER/PIXELS_PER_METER)*PIXELS_PER_METER;
        
        tp = new TexturePaint(this.frames.get("water"), new Rectangle(1024, 768));
        g2D.setPaint(tp);
        
		g2D.fillRect(0, 0, 1024, 768);
        
        for(int i = 0; i< this.shore.size(); i++)
        {
            int scaledx[] = new int[this.shore.get(i).npoints];
            int scaledy[] = new int[this.shore.get(i).npoints];
            
            for(int j = 0; j < this.shore.get(i).npoints; j++)
            {
                scaledx[j] = this.shore.get(i).xpoints[j]*PIXELS_PER_METER;
                scaledy[j] = this.shore.get(i).ypoints[j]*PIXELS_PER_METER;
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
            
            tp = new TexturePaint(this.frames.get("land"), new Rectangle(copy.getBounds()));
             
            // Add the texture paint to the graphics context. 
            g2D.setPaint(tp);    
            g2D.setStroke(new BasicStroke(5F));
            
            //g.setColor(new Color(0, 128, 64));
            g.drawPolygon(copy);
            g.fillPolygon(copy);
            
            g.setColor(new Color(255, 226, 28));
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