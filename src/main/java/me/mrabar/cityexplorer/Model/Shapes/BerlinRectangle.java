package me.mrabar.cityexplorer.model.Shapes;

import me.mrabar.cityexplorer.model.Point;
import me.mrabar.cityexplorer.model.PointDTO;

import java.util.ArrayList;
import java.util.List;

public class BerlinRectangle implements Figure {
    public static double minLat = 52.46629387;
    public static double maxLat = 52.55405421;
    public static double minLon = 13.28671947;
    public static double maxLon = 13.47898021;

    @Override
    public boolean pointWithin(PointDTO p) {
        return p.getLat() >= minLat && p.getLat() <= maxLat
            && p.getLng() >= minLon && p.getLng() <= maxLon;
    }

    @Override
    public float percentageCovered(List<Point> points) {
        double latStep = (maxLat - minLat) / 15;
        double lngStep = (maxLon - minLon) / 20;
        int hits = 0;
        Point p = new Point(minLat, minLon, 0.4);
        for(double lng = minLon; lng <= maxLon; lng += lngStep) {
            for(double lat = minLat; lat <= maxLat; lat += latStep) {
                p.moveTo(lat, lng);
                for(Point q : points) {
                    if(p.touches(q)) {
                        hits++;
                        break;
                    }
                }
            }
        }
        System.out.println("Hits: " + hits);
        return hits / (15.0f * 20.0f);
    }

    @Override
    public void compilePoints() {

    }

    @Override
    public List<PointDTO> getPointsList() {
        double latStep = (maxLat - minLat) / 15;
        double lngStep = (maxLon - minLon) / 20;
        int hits = 0;
        Point p = new Point(minLat, minLon, 0.4);
        ArrayList<PointDTO> points = new ArrayList<PointDTO>();
        for(double lng = minLon; lng <= maxLon; lng += lngStep) {
            for(double lat = minLat; lat <= maxLat; lat += latStep) {
                p.moveTo(lat, lng);
                points.add(new Point(p.getLat(), p.getLng(), p.getRadiusKm()));
            }
        }
        return points;
    }
}
