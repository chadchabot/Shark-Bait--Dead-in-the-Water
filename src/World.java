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
    
    
    //      default constructor
    public World ( ) {
        super("worlds","water");
        this.windDirection  = 0;
        this.windSpeed      = 0;
        this.time           = 1;
        this.weatherState   = 0;
        this.rain           = 0;
        this.fog            = 0;
        this.width          = 5000;
        this.height         = 4000;
        shore = new ArrayList<Polygon>();
    }
    public World (int pWidth, int pHeight, String pBack) {
        super("world",pBack);
        this.windDirection  = 0;
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
    public void draw ( Graphics g, Point playerPos )
    {
        int drawX;// = (this.position.x - playerPos.x + PLAYER_X_CENTER/PIXELS_PER_METER)*PIXELS_PER_METER;
        int drawY;// = (this.position.y - playerPos.y + PLAYER_Y_CENTER/PIXELS_PER_METER)*PIXELS_PER_METER;
        int origX;
        int origY;
        //System.println(drawX +", "+drawY);
        for(int i = 0; i < 1024; i = i + this.sWidth)
        {
            for(int j = 0; j < 768; j = j + this.sHeight)
            {
                //g.drawImage(this.frames.get(this.currentState), 150, 150, 150+75, 150+75, 0, 0, 75, 75, null);
                g.drawImage(this.frames.get(this.currentState), i, j, i+this.sHeight, j+this.sWidth, 
                            0, 0, this.sHeight,this.sWidth, null);
            }
        }
        
        for(int i = 0; i< this.shore.size(); i++)
        {
            Polygon copy = new Polygon(this.shore.get(i).xpoints, this.shore.get(i).ypoints, this.shore.get(i).npoints);
            Rectangle bounds = new Rectangle(copy.getBounds());
            
            
            Point polycenter = new Point( bounds.x + ((int)bounds.getWidth( ) )/2,  bounds.y + ((int)bounds.getHeight( ) )/2 );
            
            drawX = PLAYER_X_CENTER - (playerPos.x - polycenter.x)*PIXELS_PER_METER;
            drawY = PLAYER_Y_CENTER - (playerPos.y - polycenter.y)*PIXELS_PER_METER;
            //(polycenter.x - playerPos.x + PLAYER_X_CENTER/PIXELS_PER_METER)*PIXELS_PER_METER;
            //drawY = (polycenter.y - playerPos.y + PLAYER_Y_CENTER/PIXELS_PER_METER)*PIXELS_PER_METER;
            
            copy.translate(drawX - polycenter.x, drawY - polycenter.y);
            
            Graphics2D g2D = (Graphics2D) g;
            g2D.setStroke(new BasicStroke(10F));
            g2D.scale( (double)PIXELS_PER_METER, (double)PIXELS_PER_METER );

            
            //System.out.println("SHORES");
            g.setColor(Color.GREEN);
            g.drawPolygon(copy);
            g.fillPolygon(copy);
            
            g.setColor(Color.YELLOW);
            g2D.drawPolygon(copy);
            g2D.scale( 1.0, 1.0 );
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