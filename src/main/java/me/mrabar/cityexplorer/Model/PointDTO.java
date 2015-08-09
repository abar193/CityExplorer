package me.mrabar.cityexplorer.model;

public class PointDTO {
    private double lat;
    private double lng;
    private double radiusKm;

    public PointDTO() {
        radiusKm = 0.1;
    }

    public PointDTO(double lat, double lng) {
        radiusKm = 0.1;
        this.lng = lng;
        this.lat = lat;
    }

    public PointDTO(double lat, double lng, double size) {
        radiusKm = size;
        this.lng = lng;
        this.lat = lat;
    }

    public void moveTo(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double distanceToInKm(PointDTO p) {
        return ((Math.acos(Math.sin(latigudeRad()) * Math.sin(p.latigudeRad())
                + Math.cos(latigudeRad()) * Math.cos(p.latigudeRad())
                * Math.cos((lng - p.getLng()) * Math.PI / 180)) * 180 / Math.PI)
                *  60 * 1.1515 * 1.609344);
    }

    public boolean touches(PointDTO p) {
        return distanceToInKm(p) <= (p.getRadiusKm() + getRadiusKm());
    }

    public double latigudeRad() {
        return getLat() * Math.PI / 180;
    }

    public double longitudeRad() {
        return getLng() * Math.PI / 180;
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

    public double getRadiusKm() {
        return radiusKm;
    }

    public void setRadiusKm(double radiusKm) {
        this.radiusKm = radiusKm;
    }
}
