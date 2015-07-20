package here.cityexplorer.Model;

/**
 * Created by abar on 6/19/15.
 */
public class Rectangle {
    private Point start, end;

    public Rectangle() {
    }

    public Rectangle(int x, int y) {
        this.start = Point.fromXY(x, y);
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }
}
