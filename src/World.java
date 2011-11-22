package SharkBait;

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
        super("water");
                this.windDirection      = 0;
                this.windSpeed          = 0;
                this.time                       = 1;
                this.weatherState       = 0;
        this.rain                       = 0;
        this.fog                        = 0;
                this.width                      = 5000;
                this.height                     = 4000;
                shore = new ArrayList<Polygon>();
        }
        
        
        public void setWindDirection ( int pWindDirection ) {
                this.windDirection = pWindDirection;
        }
        public double getWindDirection ( ) {
                return this.windDirection;
        }
        public void setWindSpeed ( int pWindSpeed ) {
                this.windSpeed = pWindSpeed;
        }
        public double getWindSpeed ( ) {
                return this.windSpeed;
        }
        
        public void setRain ( int pRain ) {
                this.rain = pRain;
        }
        public void setFog ( int pFog ) {
                this.fog = pFog;
        }
   
    public void setTime ( int pTime ) {
                this.time = pTime;
        }
        public int getTime ( ) {
                return this.time;
        }
        public void setWeatherState ( int pWeatherState ) {
                this.weatherState = pWeatherState;
        }
        public int getWeatherState ( ) {
                return this.weatherState;
        }
        
        
        public void setWorldWidth ( int pWidth ) {
                this.width = pWidth;
        }
        public int getWorldWidth ( ) {
                return this.width;
        }
        
        
        public void setWorldHeight ( int pHeight ) {
                this.height = pHeight;
        }
        public int getWorldHeight ( ) {
                return this.height;
        }
        
        public void addShore ( int [] pX, int [] pY, int pNumPoints  ) {
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
        public void draw ( Graphics g )
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
        	
        	 	Graphics2D g2D = (Graphics2D) g;     
        	    g2D.setStroke(new BasicStroke(10F));
        	    
                //System.out.println("SHORES");
        		g.setColor(Color.GREEN);
                g.drawPolygon(this.shore.get(i));
                g.fillPolygon(this.shore.get(i));
                
                g.setColor(Color.YELLOW);
                g.drawPolygon(this.shore.get(i));
        }
        }
}