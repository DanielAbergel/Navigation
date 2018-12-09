package Game_objects;

import Geom.GpsPoint;
import Geom.Point3D;
import Maps.Map;
import Maps.Pixel;
import Coords.MyCoords;

public class Packman {

	private int id;
	private GpsPoint gpsLocation;
	private Point3D XYZlocation;
	private Pixel pixelLocation;
	private int speed;
	private int radius;
	public Path path;
	public double time ; 
	public Point3D p ;

	public Packman(int id,GpsPoint gpsLocation,int speed,int radius)
	{
		path = new Path();
		this.id=id;
		this.gpsLocation=gpsLocation;
		this.speed=speed;
		this.radius=radius;
		p = new Point3D(gpsLocation.getLon(),gpsLocation.getLat(),gpsLocation.getAlt());
		this.XYZlocation=new Point3D(gpsLocation.getLat(),gpsLocation.getLon(),gpsLocation.getAlt()).GPS2Meter();
		this.pixelLocation = new Pixel(p);

	}
	public Packman(int id,Pixel pixelLocation,int speed,int radius)
	{
		Map map=new Map();
		this.id=id;
		this.pixelLocation=pixelLocation;
		this.gpsLocation=new GpsPoint(map.Pixel2GPSPoint(pixelLocation.get_PixelX(),pixelLocation.get_PixelY()).x(),map.Pixel2GPSPoint(pixelLocation.get_PixelX(),pixelLocation.get_PixelY()).y(),0);
		this.speed=speed;
		this.radius=radius;
	}
	
		public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}


		public GpsPoint getGpsLocation() {
			return gpsLocation;
		}


		public void setGpsLocation(GpsPoint gpsLocation) {
			this.gpsLocation = gpsLocation;
		}


		public Pixel getPixelLocation() {
			return pixelLocation;
		}


		public void setPixelLocation(Pixel pixelLocation) {
			this.pixelLocation = pixelLocation;
		}


		public int getSpeed() {
			return speed;
		}


		public void setSpeed(int speed) {
			this.speed = speed;
		}


		public int getRadius() {
			return radius;
		}


		public void setRadius(int radius) {
			this.radius = radius;
		}

		public Point3D getXYZlocation() {
			return XYZlocation;
		}

		public void setXYZlocation(Point3D xYZlocation) {
			XYZlocation = xYZlocation;
		}










	}
