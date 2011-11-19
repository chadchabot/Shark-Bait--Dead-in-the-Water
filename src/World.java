package SharkBait;

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Polygon;

public class World {

	private double 	windDirection;
	private double 	windSpeed;
	private int 	time;
	private int 	weatherState;
	private ArrayList<Polygon> shore;
	private int 	width;
	private int 	height;
	
	
	//	default constructor
	public World ( ) {
		this.windDirection = 0;
		this.windSpeed = 0;
		this.time = 1;
		this.weatherState = 0;
		this.width = 5000;
		this.height = 4000;
		shore = new ArrayList<Polygon>();
	}
	
	
	public void setWindDirection ( int pWindDirection ) {
		this.windDirection = pWindDirection;
	}
	public double getWindDirection ( ) {
		return this.windDirection;
	}
	
	
	public void setWindSpeed ( int pWindSpeed ) {
		this.windSpeed = pWindSpeed;
	}
	public double getWindSpeed ( ) {
		return this.windSpeed;
	}
	
	
	public void setTime ( int pTime ) {
		this.time = pTime;
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
		

	public void addShore ( int [] pX, int [] pY, int pNumPoints  ) {
		Polygon mShore = null;
		
		try {
			mShore = new Polygon (pX, pY, pNumPoints);
		}
		catch (NegativeArraySizeException e){
			System.out.println( "Array size must be positive integer" );
		}
		catch (IndexOutOfBoundsException e){
			System.out.println( "Array Index is out of bounds" );
		}
		catch (NullPointerException e){
			System.out.println( "Array Null pointer exception" );
		}
		
		if (mShore != null){
			this.shore.add(mShore);
		}
	}
		
	public void draw (Graphics g ) {
		
	}
	
	public void update (Graphics g ) {
		
	}
	
}