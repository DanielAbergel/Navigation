package Game_objects;

import Game.Pixel;
import Geom.GpsPoint;

public class Fruit {

	private int id;
	private GpsPoint gpsLocation;
	private Pixel pixelLocation;
	private int value;


	public Fruit(int id,GpsPoint gpsLocation,int value)
	{
		this.id=id;
		this.gpsLocation=gpsLocation;
		this.value=value;

	}

}
