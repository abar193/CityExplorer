package me.mrabar.cityexplorer.model.Shapes;

import me.mrabar.cityexplorer.model.Point;
import me.mrabar.cityexplorer.model.PointDTO;

import java.util.List;

public interface Figure {
    boolean pointWithin(PointDTO p);
    float percentageCovered(List<Point> p);
    void compilePoints();
    List<PointDTO> getPointsList();
}
