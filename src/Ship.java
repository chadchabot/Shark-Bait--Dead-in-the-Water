package SharkBait;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Graphics;

public class Ship extends Sprite{

    public static final int         REFRESH_RATE = 60;
    public static final int         PIXELS_PER_METER = 2;
    public static final int     PLAYER_X_CENTER = 500;
    public static final int     PLAYER_Y_CENTER = 300;
    public static final int     IMAGE_SCALING = 3;

    private int     shipID;
    private double  speed;
    private int         speedFactor;
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
        this.speed      = 0.9;
        this.type       = 0;
        this.position   = new Point(10,10);
        this.heading    = 270;
        this.health     = 125.5;
        this.status     = 1;
        this.firing     = false;
        if(this.type == 0)
        {
            this.loadImage("default","sloop");
            this.speedFactor = 5;
        }
        else if(this.type == 1)
        {
             this.loadImage("default","frigate");
             this.speedFactor = 4;
        }
        else if(this.type == 2)
        {
            this.loadImage("default","manowar");
            this.speedFactor = 3;
        }
    }
    
    public Ship ( int pID, int pType ) 
    {
        super("ship");
        this.shipID     = pID;
        this.speed      = 1 ;
        this.type       = pType;
        this.position   = new Point(50, 50);
        this.heading    = 270;
        this.health     = 125.5;
        this.status     = 1;
        this.firing     = false;
        if(this.type == 0)
        {
             this.loadImage("default","sloop");
             this.speedFactor = 5;
        }
        else if(this.type == 1)
        {
             this.loadImage("default","frigate");
             this.speedFactor = 4;
        }
        else if(this.type == 2)
        {
            this.loadImage("default","manowar");
            this.speedFactor = 3;
        }
    }
    public void updateShip ( int pX, int pY, double pSpeed, int pHeading, double pHealth ) {
        this.position = new Point( pX, pY );
        this.speed = pSpeed;
        this.heading = pHeading;
        this.health = pHealth;
    }
    
    public void update ( int pWindDir ) 
    {

        int angleDiff = 0;
        pWindDir = pWindDir - 180;
        if(pWindDir < 0)
        {
            pWindDir = pWindDir + 360;
        }
        if(Math.abs(pWindDir-this.heading) <= 180)
        {
            angleDiff = Math.abs(pWindDir-this.heading);
        }
        else if(Math.abs(pWindDir-this.heading) > 180)
        {
            angleDiff = 360 - Math.abs(pWindDir-this.heading);
        }
        this.deltaX = this.deltaX + (Math.cos(Math.toRadians(this.heading-90))
                                     *(this.speed*this.speedFactor/REFRESH_RATE*PIXELS_PER_METER))
        *SpeedTable.table[angleDiff];
        this.deltaY = this.deltaY + (Math.sin(Math.toRadians(this.heading-90))
                                     *(this.speed*this.speedFactor/REFRESH_RATE*PIXELS_PER_METER))
        *SpeedTable.table[angleDiff];

        
        this.position.setLocation(
            this.position.x + this.deltaX,
            this.position.y + this.deltaY
        );
        if(Math.round(deltaX) >= 1 || Math.round(deltaX) <= -1)
        {   
            this.deltaX = 0;
        }
       if(Math.round(deltaY) >= 1 || Math.round(deltaY) <= -1)
        {   
           this.deltaY = 0;
        }
    
    }
        
    public void draw ( Graphics g , Point playerPos, int targetID)
    {
        int drawX = (this.position.x - playerPos.x + PLAYER_X_CENTER/PIXELS_PER_METER)*PIXELS_PER_METER;
        int drawY = (this.position.y - playerPos.y + PLAYER_Y_CENTER/PIXELS_PER_METER)*PIXELS_PER_METER;
        Graphics2D g2D = (Graphics2D) g;
        g2D.rotate( (Math.toRadians(heading)), 
                   drawX + this.sWidth*IMAGE_SCALING/2, 
                   drawY + this.sHeight*IMAGE_SCALING/2 );
        g2D.drawImage(this.frames.get(this.currentState), 
                    drawX, drawY,
                    this.sWidth*IMAGE_SCALING, 
                    this.sHeight*IMAGE_SCALING, null);
        if(this.shipID == targetID){
                g.drawOval(drawX, drawY, this.sWidth*IMAGE_SCALING, this.sHeight*IMAGE_SCALING);
        }
        g2D.rotate( -1*Math.toRadians(heading), 
                drawX + this.sWidth*IMAGE_SCALING/2, 
                drawY + this.sHeight*IMAGE_SCALING/2 );
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
    public int getType()
    {
    	return this.type;
    }
}