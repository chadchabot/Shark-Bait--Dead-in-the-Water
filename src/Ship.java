import java.Point;

public class Ship extends Sprite{

	private int shipID;
	private double speed;
	private int type;
	private Point position;
	private int heading;
	private double health;
	private int status;
	private boolean firing;
	
	
	//	constructor
	public Ship ( ) {
		this.shipID = 69;
		this.speed = 15.5;
		this.type = 1;
		this.position = new Point(10,10);
		this.heading = 270;
		this.health = 125.5;
		this.status = 1;
		this.firing = false;
	}
	
	
	public void setSpeed ( double pSpeed ) {
		this.speed = pSpeed;
	}
	public double getSpeed ( ) {
		return this.speed;
	}
	
	
	public void setPosition ( Point pPosition ) {
		this.position = pPosition;
	}
	public Point getPosition ( ) {
		return this.position;
	}
	
	
	public void setHeading ( int pHeading ) {
		this.heading = pHeading;
	}
	public int getHeading ( ) {
		return this.heading;
	}
	
	
	public void sethealth ( double pHealth ) {
		this.health = pHealth;
	}
	public double getHealth ( ) {
		return this.health;
	}
	
	
	public void setStatus (int pStatus ) {
		this.status = pStatus;
	}
	public int getStatus ( ) {
		return this.status;
	}
	
	
	public update ( ) {
		
	}
	
	
	public draw ( ) {
		
	}
}