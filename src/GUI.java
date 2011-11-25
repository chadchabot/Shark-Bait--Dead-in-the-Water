import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;
import java.util.HashMap;

public class GUI extends Sprite
{
	
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
        this.windDirection = 180;
		this.windSpeed = 0;
		this.loadImage("default","compass_arrow");
        
    }

	//	?	we need to have a list of ships -> to get the health of each 
	//	?	wind direction, wind speed
    public void update ( int pWindDir, int pWindSpeed  ) 
    {
		System.out.println( "updating the wind in GUI" );
//		pWindDir = pWindDir - 90;
		
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
				System.out.println( shipHealth + " " + shipHealthMAX + " " + hpBarFill );			
				counter++;
			}
			else
			{
				g2D.drawString( "SHIP HP" , 700, 675 );
				g2D.fillRect( 825, 655, (int)(hpBarFill*1.5), 30 );
				g2D.drawRect( 825, 655, 150, 30 );
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
	
}