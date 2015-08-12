package me.mrabar.cityexplorer.model;

public class Point extends PointDTO {
    private int userID;
    private int lineID;
    private int pointNum;

    public Point() {
        super();
    }

    public Point(double lat, double lng) {
        super(lat, lng);
    }

    public Point(double lat, double lng, double size) {
        super(lat, lng, size);
    }

    public int getUserID() {
        return userID;
    }g

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getLineID() {
        return lineID;
    }

    public void setLineID(int lineID) {
        this.lineID = lineID;
    }

    public int getPointNum() {
        return pointNum;
    }

    public void setPointNum(int pointNum) {
        this.pointNum = pointNum;
    }
}
