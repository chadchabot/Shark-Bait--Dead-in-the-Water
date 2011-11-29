
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
	
	private int		windDirection;
	private int		windSpeed;
	private Point	windSockPosition;	//	position of windsock on screen
	private int		imgWidth = 81;
	
	//	on-screen 'chrome'
	private	static Font healthDisplayFont = new Font( "Courier", Font.BOLD, 20 );
	private	static Font enemyListTitleFont = new Font( "Courier", Font.BOLD, 30 );
	private double shipHealth		= 0.0;
	private double shipHealthMAX	= 0.0;
	
	
	//	when GUI() constructor is called, do we already have wind information?
    public GUI( )
    {
		this.windSockPosition = new Point( 923, 61 );
        this.windDirection = 45;
		this.windSpeed = 0;
		this.loadImage("default","compass_arrow");
        this.loadImage("target_arrow","target_arrow");        
    }

	//	?	we need to have a list of ships -> to get the health of each 
	//	?	wind direction, wind speed
    public void update ( int pWindDir, int pWindSpeed  ) 
    {
		//	System.out.println( "updating the wind in GUI" );
		
        this.windDirection = pWindDir;
        this.windSpeed   = pWindSpeed;
    }
	
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
		for ( String key : pShipList.keySet() )
		{
            hpBarFill = (int) (100 * pShipList.get(key).getHealth()/pShipList.get(key).getHealthMAX());
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
//				System.out.println( shipHealth + " " + shipHealthMAX + " " + hpBarFill );			
				counter++;
                
                target_rot = getShipArrow(pShipList.get(key), pShipList.get(Integer.toString(pPlayerID)));
                
                if(target_rot > 0)
                {
                    g2D.rotate( (Math.toRadians(target_rot)),
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
			else
			{
				g2D.drawString( "SHIP HP" , 700, 655 );
				g2D.fillRect( 825, 635, (int)(hpBarFill*1.5), 30 );
				g2D.drawRect( 825, 635, 150, 30 );
				g2D.drawString( "(X, Y): (" + pShipList.get(key).getPosition().x + ","+pShipList.get(key).getPosition().y +")", 700, 705 );
			}

		}
		
        g2D.rotate( (Math.toRadians(this.windDirection + 180)),
				    this.windSockPosition.x + this.imgWidth/2,
				    this.windSockPosition.y + this.imgWidth/2 );
        g2D.drawImage(this.frames.get(this.currentState), 
                      this.windSockPosition.x, this.windSockPosition.y,
					  this.imgWidth, this.imgWidth,
                      null);
        g2D.rotate( -1*(Math.toRadians(this.windDirection + 180)),
				   this.windSockPosition.x + this.imgWidth/2,
				   this.windSockPosition.y + this.imgWidth/2 );		
    }
    /*
     * Mutators
     */
    public void setWindDirection ( int pWindDirection ) 
    {
		this.windDirection = pWindDirection;
	}

    public void setWindSpeed ( int pWindSpeed ) 
    {
		this.windSpeed = pWindSpeed;
	}
    private int getShipArrow(Ship enemy, Ship player)
    {
        int enemyHeading = enemy.getHeading();
        Point shipPos = player.getPosition();
        Point enemyPos = enemy.getPosition();
        int angle = -1;
        //straight up
        if(enemyPos.x == shipPos.x && enemyPos.y < shipPos.y)
        {
            angle = 0;
        }
        //straight right
        else if(enemyPos.x > shipPos.x && enemyPos.y == shipPos.y)
        {
            angle = 90;
        }
        //straight down
        else if(enemyPos.x == shipPos.x && enemyPos.y > shipPos.y)
        {
            angle = 180;    
        }
        //straight left
        else if(enemyPos.x < shipPos.x && enemyPos.y == shipPos.y)
        {
            angle = 270;
        }
        //top right
        else if(enemyPos.x > shipPos.x && enemyPos.y < shipPos.y)
        {   
            angle = (int)Math.round(Math.abs(Math.toDegrees( 
                    Math.atan( (enemyPos.x-shipPos.x)/(enemyPos.y-shipPos.y) )
                    ))); 
        }
        //bottom right
        else if(enemyPos.x > shipPos.x && enemyPos.y > shipPos.y)
        {
            angle = 180 - (int)Math.round(Math.abs(Math.toDegrees( 
                    Math.atan( (enemyPos.x-shipPos.x)/(enemyPos.y-shipPos.y) )
                    ))); 
        }
        //bottom left
        else if(enemyPos.x < shipPos.x && enemyPos.y > shipPos.y)
        {
            angle = 180 + (int)Math.round(Math.abs(Math.toDegrees( 
                      Math.atan( (enemyPos.x-shipPos.x)/(enemyPos.y-shipPos.y) )
                      )));
        }
        //top left
        else if(enemyPos.x < shipPos.x && enemyPos.y < shipPos.y)
        {
            angle = 360 - (int)Math.round(Math.abs(Math.toDegrees( 
                      Math.atan( (enemyPos.x-shipPos.x)/(enemyPos.y-shipPos.y) )
                      )));
        }
        return angle;
    }
	
}