

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Graphics;

public class Ship extends Sprite{

    private int     shipID;
    private double  speed;
    private int     type;
    private Point   position;
    private int     heading;
    private double  health;
    private int     status;
    private boolean firing;
    
    private double deltaX; 
    private double deltaY;
    
        
        //      constructor
    public Ship ( ) 
    {
        //super("ship");
        this.shipID     = 69;
        this.speed      = 15.5;
        this.type       = 0;
        this.position   = new Point(10,10);
        this.heading    = 270;
        this.health     = 125.5;
        this.status     = 1;
        this.firing     = false;
        if(this.type == 0)
        {
            this.loadImage("default","sloop");
        }
    }
    
    public Ship ( int pID, int pType ) 
    {
        super("ship");
        this.shipID     = pID;
        this.speed      = 5 ;
        this.type       = pType;
        this.position   = new Point(50, 50);
        this.heading    = 135;
        this.health     = 125.5;
        this.status     = 1;
        this.firing     = false;
        if(this.type == 0)
        {
             this.loadImage("default","sloop");
        }
        else if(this.type == 1)
        {
             this.loadImage("default","frigate");
        }
        else if(this.type == 2)
        {
            this.loadImage("default","manowar");
        }
    }
    public void updateShip ( int pX, int pY, double pSpeed, int pHeading, double pHealth ) {
        System.out.println("UPDATED");
        this.position = new Point( pX, pY );
        this.speed = pSpeed;
        this.heading = pHeading;
        this.health = pHealth;
    }
    
    public void update ( ) 
    {
        this.deltaX = this.deltaX + (Math.cos(Math.toRadians(this.heading-90))*(this.speed/60*10));
        this.deltaY = this.deltaY + (Math.sin(Math.toRadians(this.heading-90))*(this.speed/60*10));
        
        this.position.setLocation(
            this.position.x + this.deltaX,
            this.position.y + this.deltaY
        );
        if(Math.round(deltaX) >= 1)
        {   
            this.deltaX = 0;
        }
       if(Math.round(deltaY) >= 1)
        {   
           this.deltaY = 0;
        }
    
    }
        
    public void draw ( Graphics g )
    {
        Graphics2D g2D = (Graphics2D) g;
        g2D.scale(3, 3);
        g2D.rotate( (Math.toRadians(heading)), 
                   this.position.x + this.sWidth*3/2, 
                   this.position.y + this.sHeight*3/2 );
        g.drawImage(this.frames.get(this.currentState), 
                    this.position.x, this.position.y,
                    this.sWidth, 
                    this.sHeight, null);
    }
    /*
     * Mutators
     */
    public void setHeading ( int pHeading ) 
    {
                this.heading = pHeading;
        }
    public void sethealth ( double pHealth ) 
    {
                this.health = pHealth;
        }
        public void setSpeed ( double pSpeed ) 
    {
                this.speed = pSpeed;
        }
        public void setPosition ( Point pPosition ) 
    {
                this.position = pPosition;
        }
        public void setStatus (int pStatus ) 
    {
                this.status = pStatus;
        }
        /*
     * Accessors
     */
    public int getHeading ( ) 
    {
                return this.heading;
        }
        public double getHealth ( ) 
    {
                return this.health;
        }
        public double getSpeed ( ) 
    {
                return this.speed;
        }
    public Point getPosition ( ) 
    {
                return this.position;
        }
    public int getStatus ( ) {
                return this.status;
        }
    public int getShipID ( )
    {
        return this.shipID;
    }
}