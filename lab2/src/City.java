import java.time.ZoneId;

public class City {
    private String name;
    private double latitude;
    private double longitude;
    private ZoneId timeZone;

    public City(String name, double latitude, double longitude, ZoneId timeZone) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeZone = timeZone;
    }

    public String getName() {
        return name;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return name + ": latitude:" + latitude + ", longitude:" + longitude + ", timeZone:" + timeZone;
    }
}