import java.awt.Point;
import java.awt.Graphics;

public class Ship extends Sprite{

	private int		shipID;
	private double  speed;
	private int		type;
	private Point	position;
	private int     heading;
	private double	health;
	private int		status;
	private boolean firing;
	
	
	//	constructor
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
		this.speed      = 15.5;
		this.type       = pType;
		this.position   = new Point(10,10);
		this.heading    = 0;
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
		this.position = new Point( pX, pY );
        this.speed = pSpeed;
        this.heading = pHeading;
        this.health = pHealth;
	}
    
	public void update ( ) 
    {
		
	}
	
	public void draw ( Graphics g )
    {
        g.drawImage(this.frames.get(this.currentState), this.position.x, this.position.y,
                    (this.position.x + this.sWidth)*3, (this.position.y +this.sHeight)*3, null);
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