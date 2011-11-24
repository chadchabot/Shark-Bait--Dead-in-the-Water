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

public class World extends Sprite{

    private double  windDirection;
    private double  windSpeed;
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
    public void draw ( Graphics g, Point pPosition )
    {
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
	            Point Polycenter = new Point((int)bounds.getWidth()/2, (int)bounds.getHeight()/2);
	            copy.translate(pPosition.x, pPosition.y);
        	
                Graphics2D g2D = (Graphics2D) g;     
                g2D.setStroke(new BasicStroke(10F));
                
                //System.out.println("SHORES");
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
    public double getWindDirection ( ) 
    {
        return this.windDirection;
    }
    public double getWindSpeed ( ) 
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