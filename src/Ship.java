import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Graphics;

public class Ship extends Sprite{

    public static final int		REFRESH_RATE = 60;
	public static final int 	PIXELS_PER_METER = 1;
    public static final int     PLAYER_X_CENTER = 500;
    public static final int     PLAYER_Y_CENTER = 300;
    public static final int     SLOOP_W_M = 20;
    public static final int     SLOOP_H_M = 20;
    public static final int     SLOOP_SPEED = 50;
    public static final int     FRIGATE_W_M = 20;
    public static final int     FRIGATE_H_M = 60;
    public static final int     FRIGATE_SPEED = 70;
    public static final int     MOW_W_M = 20;
    public static final int     MOW_H_M = 80;
    public static final int     MOW_SPEED = 90;

    private int     shipID;
    private double  speed;
    private int         speedFactor;
    private int     type;
    private Point   position;
    private int     heading;
    private double  health;
    private int     status;
    private boolean firing;
    private int     shipWidthM;
    private int     shipHeightM;
    
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
         this.setupShipStats();
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
         this.setupShipStats();
    }
    public void setupShipStats()
    {
        if(this.type == 0)
        {
            this.loadImage("default","sloop");
            this.speedFactor = SLOOP_SPEED;
            this.shipWidthM = SLOOP_W_M;
            this.shipHeightM = SLOOP_H_M;
        }
        else if(this.type == 1)
        {
            this.loadImage("default","frigate");
            this.speedFactor = FRIGATE_SPEED;
            this.shipWidthM = FRIGATE_W_M;
            this.shipHeightM = FRIGATE_H_M;
        }
        else if(this.type == 2)
        {
            this.loadImage("default","mow");
            this.speedFactor = SLOOP_SPEED;
            this.shipWidthM = MOW_W_M;
            this.shipHeightM = MOW_H_M;
            
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
        int drawX = (this.position.x - playerPos.x + PLAYER_X_CENTER/PIXELS_PER_METER)
        *PIXELS_PER_METER - this.shipWidthM*PIXELS_PER_METER/2;
        int drawY = (this.position.y - playerPos.y + PLAYER_Y_CENTER/PIXELS_PER_METER)
        *PIXELS_PER_METER - this.shipHeightM*PIXELS_PER_METER/2;
        Graphics2D g2D = (Graphics2D) g;
        g2D.rotate( (Math.toRadians(heading)), 
                   drawX + this.shipWidthM*PIXELS_PER_METER/2, 
                   drawY + this.shipHeightM*PIXELS_PER_METER/2 );
        g2D.drawImage(this.frames.get(this.currentState), 
                      drawX, drawY,
                      this.shipWidthM*PIXELS_PER_METER,
                      this.shipHeightM*PIXELS_PER_METER,
                      null);
        if(this.shipID == targetID){
        	g.drawOval(drawX, drawY, 
                       this.shipWidthM*PIXELS_PER_METER, this.shipHeightM*PIXELS_PER_METER );
        }
        g2D.rotate( -1*Math.toRadians(heading), 
                   drawX + this.shipWidthM*PIXELS_PER_METER/2, 
                   drawY + this.shipHeightM*PIXELS_PER_METER/2 );
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