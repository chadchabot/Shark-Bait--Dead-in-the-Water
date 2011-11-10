import java.awt.Point;
import java.util.ArrayList;
import java.awt.Polygon;

public class World extends Sprite {

	private double windDirection;
	private double windSpeed;
	private int time;
	private int weatherState;
	private ArrayList<Polygon> shore;
	private int width;
	private int height;
	
	
	//	default constructor
	public World ( ) {
		
	}
	
	
	public void setWindDirection ( int pWindDirection ) {
		this.windDirection = pWindDirection;
	}
	public double getWindDirection ( ) {
		return this.windDirection;
	}
	
	
	public void setWindSpeed ( int pWindSpeed ) {
		this.windSpeed;
	}
	public double getWindSpeed ( ) {
		return this.windSpeed;
	}
	
	
	public void setTime ( int pTime ) {
		this.time;
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
		

	public void addShore ( ) {
		
	}
	
	
	public update ( ) {
		
	}
	
	
	public draw ( ) {
		
	}
	
}