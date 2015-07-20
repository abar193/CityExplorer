package here.cityexplorer.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ExplorerUser")
public class User {
    private static int SIZE_X = 100;
    private static int SIZE_Y = 100;

    @Id
    private String name;

    private boolean[][] points;
    private int lastX, lastY;
    private int totalCovered = 0;

    public User() {
        points = new boolean[SIZE_X][SIZE_Y];
    }

    public boolean activatePointAt(int x, int y) {
        if(x >= 0 && x < SIZE_X && y >= 0 && y < SIZE_Y) {
            if(!points[x][y])
                totalCovered++;
            points[x][y] = true;
            this.lastX = x;
            this.lastY = y;
            return true;
        }
        return false;
    }

//    public boolean appendTo(int x, int y) {
//        if(x >= 0 && x < SIZE_X && y >= 0 && y < SIZE_Y) {
//            for(int tx = Math.min(x, lastX); tx <= Math.max(x, lastX); tx++) {
//                for(int ty = Math.min(y, lastY); ty <= Math.max(y, lastY); ty++) {
//                    if(!points[tx][ty])
//                        totalCovered++;
//                    points[tx][ty] = true;
//                }
//            }
//
//            this.lastX = x;
//            this.lastY = y;
//            return true;
//        }
//        return false;
//    }

    public boolean appendTo(int x, int y) {
        if(x >= 0 && x < SIZE_X && y >= 0 && y < SIZE_Y) {
            float stepx = (float)(x - lastX) / 10.0f;
            float stepy = (float)(y - lastY) / 10.0f;
            float px = lastX, py = lastY;
            System.out.println(stepx + " " + stepy + " " + px + " " + py + " (" + x + " " + y);
            for(int i = 0; i < 10; i++) {
                System.out.println((int)px + " " + (int)py);
                px += stepx;
                py += stepy;

                if(!points[(int)px][(int)py])
                    totalCovered++;
                points[(int)px][(int)py] = true;
            }

            this.lastX = x;
            this.lastY = y;
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean[][] getPoints() {
        return points;
    }

    public void setPoints(boolean[][] points) {
        this.points = points;
    }

    public int getLastX() {
        return lastX;
    }

    public void setLastX(int lastX) {
        this.lastX = lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public void setLastY(int lastY) {
        this.lastY = lastY;
    }

    public int getTotalCovered() {
        return totalCovered;
    }

    public void setTotalCovered(int totalCovered) {
        this.totalCovered = totalCovered;
    }
}
