package nayanda.droid.eatr.model;

/**
 * Created by nayanda on 13/02/18.
 */

public class Location {

    private double latitude;
    private double longitude;
    private double speed;
    private double bearing;
    private long time;

    public Location(double latitude, double longitude, double speed, double bearing, long time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.bearing = bearing;
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
