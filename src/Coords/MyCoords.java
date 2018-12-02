package Coords;
import Geom.Point3D;

public class MyCoords implements coords_converter  {
	final int earth_r=6371000;
	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		// TODO Auto-generated method stub
		
		gps.GPS2Meter();
		gps.add(local_vector_in_meter);
		gps.Meter2GPS();
		Point3D returnP = new Point3D(gps);
		return returnP;
	}
	public double distance2d(Point3D gps0, Point3D gps1) 
	{
		double lon_norm=Math.cos(gps0.x()*(Math.PI/180));
		System.out.println(lon_norm);

		double diff_x=gps1.x()-gps0.x();
		double diff_y=gps1.y()-gps0.y();


		double diff_rad_x=diff_x*(Math.PI/180);
		double diff_rad_y=diff_y*(Math.PI/180);

		double x_meter=Math.sin(diff_rad_x)*earth_r;
		double y_meter=Math.sin(diff_rad_y)*earth_r*lon_norm;
		return Math.sqrt(Math.pow(x_meter, 2)+Math.pow(y_meter, 2));
	}
	@Override
	public double distance3d(Point3D gps0, Point3D gps1) {
		// TODO Auto-generated method stub

		double diff_z=gps1.z()-gps0.z();
		double xy_distance=distance2d(gps0, gps1);

		return Math.sqrt(Math.pow(xy_distance, 2)+Math.pow(diff_z, 2));
	}

	@Override
		public Point3D vector3D(Point3D gps0, Point3D gps1) {
		// TODO Auto-generated method stub
		gps0.GPS2Meter();
		gps1.GPS2Meter();
		double X = gps0.x() - gps1.x();
		double Y = gps0.y() - gps1.y();
		double Z = gps0.z() - gps1.z();
		return new Point3D(X, Y, Z);
	}
/**
 * azimuth-https://he.wikipedia.org/wiki/%D7%90%D7%96%D7%99%D7%9E%D7%95%D7%98
 * 
 */
	@Override
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
		DOUBLE A
	}

	@Override
	public boolean isValid_GPS_Point(Point3D p) {
		// TODO Auto-generated method stub
		return p.x()<90&&p.x()>-90&&p.y()>-180&&p.y()<180&&p.z()>-450;
	}



}
