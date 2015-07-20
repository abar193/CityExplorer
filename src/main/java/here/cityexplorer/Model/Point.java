package here.cityexplorer.Model;


public class Point {
    public static double minLat = 52.46629387;
    public static double maxLat = 52.55405421;
    public static double minLon = 13.28671947;
    public static double maxLon = 13.47898021;

    private double lat, lng;
    public Point() {
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public static Point fromXY(int x, int y) {
        Point p = new Point();
        p.setLat(minLat + x * (maxLat - minLat) / 100);
        p.setLng(minLon + y * (maxLon - minLon) / 100);
        return p;
    }

    public int getX() {
        if(this.getLat() <= maxLat && this.getLat() >= minLat) {
            return (int)(100 * ((this.getLat() - minLat) / (maxLat - minLat)));
        }
        return -1;
    }

    public int getY() {
        if(this.getLng() <= maxLon && this.getLng() >= minLon) {
            return (int)(100 * ((this.getLng() - minLon) / (maxLon - minLon)));
        }
        return -1;
    }
}
