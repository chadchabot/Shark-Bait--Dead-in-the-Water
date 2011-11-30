/**
 * @author      Bonne Justin jbonne@uoguelph.ca
 * @author      Cardinal, Blake bcardina@uoguelph.ca
 * @author      Chabot, Chad chabot@uoguelph.ca
 * @version     0.9                 
 * @since       2011-11-29
 */


import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;
import java.util.HashMap;

public class GUI extends Sprite
{
	
    public static final int		REFRESH_RATE = 60;
	public static final int 	PIXELS_PER_METER = 1;
    public static final int     PLAYER_X_CENTER = 500;
    public static final int     PLAYER_Y_CENTER = 300;
	
	//	environmental measures
	private int		windDirection;
	private int		windSpeed;
	private Point	windSockPosition;
	private int		imgWidth = 81;
	
	//	fonts used to display ship health
	private	static Font healthDisplayFont = new Font( "Courier", Font.BOLD, 20 );
	private	static Font enemyListTitleFont = new Font( "Courier", Font.BOLD, 30 );
	
	/**
     * Class constructor sets intialized GUI with default values
     * Loads images and frames compass and targeting arrow
     */
    public GUI( )
    {
		this.windSockPosition	= new Point( 923, 61 );
        this.windDirection		= 45;
		this.windSpeed			= 0;
		this.loadImage("default","compass_arrow");
        this.loadImage("target_arrow","target_arrow");        
    }

	
	/**
     * Update for GUI elements
     *
     * @param pWindDir the direction of the wind, from 0-359
     * @param pWindSpeed the value of the wind speed in the world
     */
    public void update ( int pWindDir, int pWindSpeed  ) 
    {
		//	System.out.println( "updating the wind in GUI" );
        this.windDirection	= pWindDir;
        this.windSpeed		= pWindSpeed;
    }
	
	/**
     * Draws the parts of the HUD on-screen
	 *
     * @param g Graphics object that will be used to draw on-screen HUD elements
     * @param pShipList HashMap of ships in the game world
     * @param pPlayerID ID number of the player's ship
	 * @param pTargetedShipID ID number of the currently targeted enemy ship
     */
    public void draw ( Graphics g, HashMap<String, Ship> pShipList, int pPlayerID, int pTargetedShipID )
    {    	
		Graphics2D g2D = (Graphics2D) g;
		g2D.setColor( Color.white );
		g2D.setFont( enemyListTitleFont );
		g2D.setStroke( new BasicStroke( 2F ));
		int stringOffset	= 30;
		int rectOffset		= 30;
		int counter			= 0;
		int hpBarFill		= 0;
        int target_rot      = 0;
		g2D.drawString( "Enemy ships:", 40, 45 );

		g2D.setFont( healthDisplayFont );
		//	for each ship in pShipList, draw their health in the appropriate area
		for ( String key : pShipList.keySet() )
		{
            hpBarFill = (int) (100 * pShipList.get(key).getHealth()/pShipList.get(key).getHealthMAX());
            //	for all enemy ships, draw in the top-left of the screen
			if ( pShipList.get(key).getShipID() != pPlayerID )
			{
				if ( pShipList.get(key).getShipID() != -1 &&
					 pShipList.get(key).getShipID() == pTargetedShipID )
				{
					g2D.setColor( Color.red );
				}
				else
				{
					g2D.setColor( Color.white );
				}
				g2D.drawString( "SHIP " + pShipList.get(key).getShipID(), 40, 80 + counter * stringOffset);
				//	for the health bars of all enemy ships
				g2D.setColor( Color.green );
				if ( hpBarFill >= 34 && hpBarFill < 67 )
				{
					g2D.setColor( Color.yellow );
				}
				else if ( hpBarFill < 34 )
				{
					g2D.setColor( Color.red );
				}
				g2D.fillRect( 150, 63 + counter * rectOffset, hpBarFill, 20 );
				g2D.setColor( Color.white );
				g2D.drawRect( 150, 63 + counter * rectOffset, 100, 20 );
				counter++;
                
				//	handles the targeting arrow rotation and display
                target_rot = getShipArrow(pShipList.get(key), pShipList.get(Integer.toString(pPlayerID)));
                
                if(target_rot > 0)
                {
                    g2D.rotate( (Math.toRadians( target_rot )),
                               PLAYER_X_CENTER ,
                               PLAYER_Y_CENTER );
                    g2D.drawImage(this.frames.get("target_arrow"), 
                                  PLAYER_X_CENTER-(100*PIXELS_PER_METER/2), PLAYER_Y_CENTER-(100*PIXELS_PER_METER/2),
                                  100*PIXELS_PER_METER, 100*PIXELS_PER_METER,
                                  null);
                    g2D.rotate( -1*(Math.toRadians(target_rot)),
                               PLAYER_X_CENTER ,
                               PLAYER_Y_CENTER );
                }
			}
			//	for the player's ship, draw in the bottom right
			else
			{
				g2D.drawString( "SHIP HP" , 700, 635 );
				g2D.fillRect( 825, 615, (int)(hpBarFill*1.5), 30 );
				g2D.drawRect( 825, 615, 150, 30 );
				
				g2D.drawString( "SPEED", 700, 675 );
				g2D.fillRect( 825, 650, (int)(pShipList.get(key).getSpeed()*10)/10, 30 );
				g2D.drawRect( 825, 650, 150, 30 );
				
				g2D.drawString( "(X, Y): (" + new Double(pShipList.get(key).getPosition().getX()).intValue() + ","+ new Double( pShipList.get(key).getPosition().getY()).intValue() +")", 700, 705 );
			}

		}
		
        g2D.rotate( (Math.toRadians(this.windDirection)),
				    this.windSockPosition.x + this.imgWidth/2,
				    this.windSockPosition.y + this.imgWidth/2 );
        g2D.drawImage(this.frames.get(this.currentState), 
                      this.windSockPosition.x, this.windSockPosition.y,
					  this.imgWidth, this.imgWidth,
                      null);
        g2D.rotate( -1*(Math.toRadians(this.windDirection)),
				   this.windSockPosition.x + this.imgWidth/2,
				   this.windSockPosition.y + this.imgWidth/2 );		
    }

	
	/*
     *
     * Mutators
     *
     */

	/*
	 *
     * Mutator for wind direction
	 *
     * @param pWindDirection The updated value for wind direction in the game world
     */
    public void setWindDirection ( int pWindDirection ) 
    {
		this.windDirection = pWindDirection;
	}
	
	/**
     * Mutator for wind speed
	 *
     * @param pWindSpeed The updated value for wind speed in the game world
     */
    public void setWindSpeed ( int pWindSpeed ) 
    {
		this.windSpeed = pWindSpeed;
	}
	
	
	
	/*
     *
     * Accessors
     *
     */
	
	/**
     * Accessor for the angle of an enemy ship, relative to the player's ship
     *
	 * @param enemy A Ship that is one of the enemies in the game world
	 * @param player The player's Ship
     * @return The angle/orientation of the enemy ship relative to the player's ship
     */
    private int getShipArrow(Ship enemy, Ship player)
    {
        int enemyHeading = enemy.getHeading();
        Point2D.Double shipPos = player.getPosition();
        Point2D.Double enemyPos = enemy.getPosition();
        int angle = -1;
        //straight up
        if(enemyPos.getX() == shipPos.getX() && enemyPos.getX() < shipPos.getX())
        {
            angle = 0;
        }
        //straight right
        else if(enemyPos.getX() > shipPos.getX() && enemyPos.getY() == shipPos.getY())
        {
            angle = 90;
        }
        //straight down
        else if(enemyPos.getX() == shipPos.getX() && enemyPos.getY() > shipPos.getY())
        {
            angle = 180;    
        }
        //straight left
        else if(enemyPos.getX() < shipPos.getX() && enemyPos.getY() == shipPos.getY())
        {
            angle = 270;
        }
        //top right
        else if(enemyPos.getX() > shipPos.getX() && enemyPos.getY() < shipPos.getY())
        {   
            angle = (int)Math.round(Math.abs(Math.toDegrees( 
                    Math.atan( (enemyPos.getX()-shipPos.getX())/(enemyPos.getY()-shipPos.getY()) )
                    ))); 
        }
        //bottom right
        else if(enemyPos.getY() > shipPos.getX() && enemyPos.getY() > shipPos.getY())
        {
            angle = 180 - (int)Math.round(Math.abs(Math.toDegrees( 
                    Math.atan( (enemyPos.getX()-shipPos.getX())/(enemyPos.getY()-shipPos.getY()) )
                    ))); 
        }
        //bottom left
        else if(enemyPos.getX() < shipPos.getX() && enemyPos.getY() > shipPos.getY())
        {
            angle = 180 + (int)Math.round(Math.abs(Math.toDegrees( 
                      Math.atan( (enemyPos.getX()-shipPos.getX())/(enemyPos.getY()-shipPos.getY()) )
                      )));
        }
        //top left
        else if(enemyPos.getX() < shipPos.getX() && enemyPos.getY() < shipPos.getY())
        {
            angle = 360 - (int)Math.round(Math.abs(Math.toDegrees( 
                      Math.atan( (enemyPos.getX()-shipPos.getX())/(enemyPos.getY()-shipPos.getY()) )
                      )));
        }
        return angle;
    }
	
}