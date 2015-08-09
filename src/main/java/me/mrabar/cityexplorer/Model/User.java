package me.mrabar.cityexplorer.model;

public class User {
    private String name;
    private int id;
    private int linesCount;
    private int pointsInLine;

    public User() {
        setId((int)(Math.random() * 1000));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Point newLine(PointDTO q) {
        Point p = new Point(q.getLat(), q.getLng(), q.getRadiusKm());
        p.setUserID(id);
        p.setLineID(linesCount++);
        p.setPointNum(0);
        pointsInLine = 1;
        return p;
    }

    public Point appendPoint(PointDTO q) {
        Point p = new Point(q.getLat(), q.getLng(), q.getRadiusKm());
        p.setUserID(id);
        p.setLineID(linesCount - 1);
        p.setPointNum(pointsInLine++);
        return p;
    }

    public int getLinesCount() {
        return linesCount;
    }

    public void setLinesCount(int linesCount) {
        this.linesCount = linesCount;
    }

    public int getPointsInLine() {
        return pointsInLine;
    }

    public void setPointsInLine(int pointsInLine) {
        this.pointsInLine = pointsInLine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
