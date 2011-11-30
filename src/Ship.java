/**
 * @author      Bonne Justin jbonne@uoguelph.ca
 * @author      Cardinal, Blake bcardina@uoguelph.ca
 * @author      Chabot, Chad chabot@uoguelph.ca
 * @version     0.9                 
 * @since       2011-11-29
 */
import java.awt.geom.AffineTransform;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.AffineTransformOp;
import java.awt.Point;

public class Ship extends Sprite{

    // game constants
    public static final int		REFRESH_RATE = 60;
	public static final int 	PIXELS_PER_METER = 3;
    public static final int     PLAYER_X_CENTER = 500;
    public static final int     PLAYER_Y_CENTER = 300;
    
    // ship specifications
	public static final int     SLOOP_W_M = 20;
    public static final int     SLOOP_H_M = 20;
    public static final int     SLOOP_SPEED = 50;
    public static final double	SLOOP_HP = 3.0;
	
	public static final int     FRIGATE_W_M = 20;
    public static final int     FRIGATE_H_M = 60;
    public static final int     FRIGATE_SPEED = 70;
    public static final double	FRIGATE_HP = 4.0;
	
    public static final int     MOW_W_M = 20;
    public static final int     MOW_H_M = 80;
    public static final int     MOW_SPEED = 90;
    public static final double	MOW_HP = 5.0;

    // attributes
    private int     shipID;
    private double  speed;
    private int     speedFactor;
    private int     type;
    private Point   position;
    private int     heading;
    private double  health;
	private double	healthMAX;
    private int     shipWidthM;
    private int     shipHeightM;
	
    // animation flags and trackers
    private boolean firing = false;
    private boolean hit = false;
    private int		frame = 0;
    
    // ship movement deltas
    private double deltaX; 
    private double deltaY;
    
        
    /**
     * Class constructor sets intialized game environment with default values
     */
    public Ship ( ) 
    {
        super( "ship" );
        this.shipID     = 69;
        this.speed      = 0.9;
        this.type       = 0;
        this.position   = new Point( 10,10 );
        this.heading    = 270;
    }
    /**
     * Class constructor sets intialized game environment with default values
     *
     * assigns values and load images depending on which which ship is assigned
     * and whether or not this ship is the player
     *
     * @param pID unique id of the ship
     * @param pType ship type assigned by the server
     * @param playerID id of the player's ship (used as a reference)
     *
     */
    public Ship ( int pID, int pType, int playerID ) 
    {
        super( "ship" );
        this.shipID     = pID;
        this.speed      = 1 ;
        this.type       = pType;
        this.position   = new Point( 50, 50 );
        this.heading    = 270;
		this.setupShipStats( playerID );
        this.loadImage( "dead",      "dead" );
        this.loadImage( "target",    "targeted_circle" );
    }
    /**
     * assigns values and load images depending on which which ship is assigned
     * and whether or not this ship is the player
     *
     * @param playerID id of the player's ship (used as a reference)
     *
     */
    public void setupShipStats( int playerID )
    {
        //sloop
        if( this.type == 0 )
        {
        	if ( this.shipID != playerID )
        	{
        		this.loadImage( "default",   "sloop_enemy" );
        		this.loadImage( "firing",    "sloop_enemy_f" );
        		this.loadImage( "hit",       "sloop_enemy_h" );
        	} 
        	else 
        	{ 
        		this.loadImage( "default",   "sloop" );
        		this.loadImage( "firing",    "sloop_f" );
        		this.loadImage( "hit",       "sloop_h" ); 
        	}
            
            this.speedFactor    = SLOOP_SPEED;
            this.shipWidthM     = SLOOP_W_M;
            this.shipHeightM    = SLOOP_H_M;
			this.health         = SLOOP_HP;
			this.healthMAX      = SLOOP_HP;
        }
        //frigate
        else if( this.type == 1 )
        {
        	if ( this.shipID != playerID )
        	{
        		this.loadImage( "default",   "frigate_enemy" );
        		this.loadImage( "firing",    "frigate_enemy_f" );
        		this.loadImage( "hit",       "frigate_enemy_h" );
        	} 
        	else 
        	{ 
        		this.loadImage( "default",   "frigate" );
        		this.loadImage( "firing",    "frigate_f" );
        		this.loadImage( "hit",       "frigate_h" );
    		}      
            
        	this.speedFactor	= FRIGATE_SPEED;
            this.shipWidthM		= FRIGATE_W_M;
            this.shipHeightM	= FRIGATE_H_M;
			this.health			= FRIGATE_HP;
			this.healthMAX		= FRIGATE_HP;
        }
        //man of war
        else if( this.type == 2 )
        {
        	if ( this.shipID != playerID )
        	{
        		this.loadImage( "default",   "mow_enemy" );
        		this.loadImage( "firing",    "mow_enemy_f" );
        		this.loadImage( "hit",       "mow_enemy_h" );
        	} 
        	else 
        	{ 
        		this.loadImage( "default",   "mow" );
        		this.loadImage( "firing",    "mow_f" );
        		this.loadImage( "hit",       "mow_h" );
        	}
            
        	this.speedFactor	= MOW_SPEED;
            this.shipWidthM		= MOW_W_M;
            this.shipHeightM	= MOW_H_M;
   			this.health			= MOW_HP;
			this.healthMAX		= MOW_HP;
        }
    }
    /**
     * updates the ships main attributes
     * 
     *  new heading should be between [-180 , 180]
     *
     * @param pX the ships new x coordinate in the world
     * @param pY the ships new y coordinate in the world
     * @param pSpeed the ships new travel speed
     * @param pHeading the ships new heading
     * @param pHealth the ships new health
     *
     */
    public void updateShip ( double pX, double pY, double pSpeed, 
                            double pHeading, double pHealth ) {
        
        this.position.setLocation( pX, pY );
        this.speed = pSpeed;
        
        //change the roation to a 360 degree basis (easier for rotation 
        this.heading = ( new Double( pHeading ).intValue( ) + 360 ) % 360;

        // set animation flag for damage if health changes
        if( this.health != pHealth )
        {
            this.hit = true;
        }
       
        this.health = pHealth;
    }
    /**
     * updates the ships position and frames based on calculations
     * 
     * windDir is not needed for speed calculation anymore
     * but it has been left in to make changing back to old speed 
     * calculations easier if a server is not compatible
     *
     * @param pWindDir direction of the wind in the game environment
     *
     */
    public void update ( int pWindDir ) 
    {

        int angleDiff = 0;
        // switch the wind around so an easy subtraction can be made
        pWindDir = (pWindDir + 180)%360;

        // calcualte change in position
        // !!! needs to be updated not match servers calculations
        this.deltaX = this.deltaX + (Math.cos(Math.toRadians(this.heading-90))
                                     *(this.speed/REFRESH_RATE*PIXELS_PER_METER));
        this.deltaY = this.deltaY + (Math.sin(Math.toRadians(this.heading-90))
                                     *(this.speed/REFRESH_RATE*PIXELS_PER_METER));
        
        // old speed calculation with wind and ship speed factor
        /*this.deltaX = this.deltaX + (Math.cos(Math.toRadians(this.heading-90))
                        *(this.speed*this.speedFactor/REFRESH_RATE*PIXELS_PER_METER))
                        *SpeedTable.table[angleDiff];
        this.deltaY = this.deltaY + (Math.sin(Math.toRadians(this.heading-90))
                        *(this.speed*this.speedFactor/REFRESH_RATE*PIXELS_PER_METER))
                        *SpeedTable.table[angleDiff];*/
        
        //update the location
        this.position.setLocation(
            this.position.x + this.deltaX,
            this.position.y + this.deltaY
        );
        
        // delta x and y were made global for small values that would get rounded
        // and cause no change in position on an update
        // that way when the sum of the deltas are over 1 (or -1) a change would finally
        // happen then we can reset the deltas back to 0
        if(Math.round( deltaX ) >= 1 || Math.round(deltaX) <= -1)
        {   
            this.deltaX = 0;
        }
        if(Math.round( deltaY ) >= 1 || Math.round(deltaY) <= -1)
        {   
           this.deltaY = 0;
        }
       
        // dead animation
        if ( this.health == 0 )
      	{
      		this.currentState = "dead";
      	}
		else 
        {
        	this.currentState = "default";
        }
        
        // damage animation
        if ( this.hit )
        {
        	this.frame++;
        	this.currentState = "hit";

			if ( frame == 50 ) {
        		this.currentState = "default";
        		this.hit = false;
        		this.frame = 0;
        	}
        }
		//firing animation
        else if ( firing )
       	{
			this.frame++; 
			this.currentState = "firing";

			if ( frame == 100 ) {
        		this.currentState = "default";
        		this.firing = false;
        		this.frame = 0;
        	}
       	}
    }
    
    /**
     * Draw the ship to the passed graphics object
     *
     * @param g Graphics object that the weather will be drawn on
     * @param playerPos used as a reference point for relative movement
     * @param targetID used for drawing target ring
     */
    public void draw ( Graphics g , Point playerPos, int targetID )
    {
        // ( ship position - player position - ( center of screen converted to meter ) ) 
        // * conversion to pixels - the width of ship / 2 ( to make the center of the 
        // image on the position )
        int drawX = ( this.position.x - playerPos.x + PLAYER_X_CENTER/PIXELS_PER_METER )
                    *PIXELS_PER_METER - this.shipWidthM*PIXELS_PER_METER / 2;
        int drawY = ( this.position.y - playerPos.y + PLAYER_Y_CENTER/PIXELS_PER_METER )
                    *PIXELS_PER_METER - this.shipHeightM*PIXELS_PER_METER / 2;
        
        Graphics2D g2D = (Graphics2D) g;
        //rotate graphics
        g2D.rotate( ( Math.toRadians( heading ) ), 
                   drawX + this.shipWidthM*PIXELS_PER_METER/2, 
                   drawY + this.shipHeightM*PIXELS_PER_METER/2 );
        
        //scale to pixel/m ratio
        // is this even need.....
        AffineTransform at = new AffineTransform( );
        at.scale( PIXELS_PER_METER, PIXELS_PER_METER );

        //draw image
        g2D.drawImage( this.frames.get( this.currentState ), drawX, drawY,
                      this.shipWidthM*PIXELS_PER_METER,
                      this.shipHeightM*PIXELS_PER_METER, //doesn't this do scaling?...
                      null );
        
        // draw target ring if this ship is targeted
        // weird half assed calculations used
        // need to figure out why this works....
        if(this.shipID == targetID){
        	g.drawImage( this.frames.get("target"), 
                        drawX - ( 20*PIXELS_PER_METER - 20*PIXELS_PER_METER/2 ), 
                        drawY + ( ( this.shipHeightM*PIXELS_PER_METER - 20*PIXELS_PER_METER )/2 ) - 10, 
                        20*PIXELS_PER_METER*2,
                        20*PIXELS_PER_METER*2, 
                        null );
        }
        //rotate graphics back
        g2D.rotate( -1*Math.toRadians( heading ), 
                   drawX + this.shipWidthM*PIXELS_PER_METER/2, 
                   drawY + this.shipHeightM*PIXELS_PER_METER/2 );
    }
    /*
     * Mutators
     */
    /**
     * Mutator for the ships direction
     *
     * [-180, 180] is expected but [0,359] is also excepted
     *
     * @param pHeading new heading of the ship
     */
    public void setHeading ( int pHeading ) 
    {
        this.heading = ( new Double( pHeading ).intValue( ) + 360 ) % 360;
    }
    /**
     * Mutator for the ships health
     *
     * @param pHealth the ships new health
     */
    public void sethealth ( double pHealth ) 
    {
        this.health = pHealth;
    }
    /**
     * Mutator for the ships speed
     *
     * @param pSpeed the ships new speed
     */
    public void setSpeed ( double pSpeed ) 
    {
        this.speed = pSpeed;
    }
    /**
     * Mutator for the ships position
     *
     * @param pPosition the ships new position
     */
    public void setPosition ( Point pPosition ) 
    {
        this.position = pPosition;
    }
    /**
     * Mutator for the ships firing flag
     *
     * @param pBool whether the ship is firing or not
     */

    public void setFiring( boolean pBool )
    {
        this.firing = pBool;
    }
    /*
     * Accessors
     */
    /**
     * Accessor for the ships heading
     *
     * @return the ships current heading
     */
    public int getHeading ( ) 
    {
        return this.heading;
    }
    /**
     * Accessor for the ships health
     *
     * @return the ships current health
     */
    public double getHealth ( ) 
    {
        return this.health;
    }
    /**
     * Accessor for the ships max health
     *
     * @return the ships max health
     */
	public double getHealthMAX ( ) 
    {
		return this.healthMAX;
	}
    /**
     * Accessor for the ships speed
     *
     * @return the ships current speed
     */
	public double getSpeed ( ) 
    {
        return this.speed;
    }
    /**
     * Accessor for the ships position
     *
     * @return the ships current position
     */
    public Point getPosition ( ) 
    {
        return this.position;
    }
    /**
     * Accessor for the ships ID
     *
     * @return the ships ID
     */
    public int getShipID ( )
    {
        return this.shipID;
    }
    /**
     * Accessor for the ships type
     *
     * 0 = sloop
     * 1 = frigate
     * 2 = man of war
     *
     * @return the ships type
     */
    public int getType( )
    {
    	return this.type;
    }
}