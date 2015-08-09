package me.mrabar.cityexplorer.controllers;

import me.mrabar.cityexplorer.model.Point;
import me.mrabar.cityexplorer.model.User;

import java.util.List;

public interface Repo {
    // User-related actions
    public boolean exists(String name);
    public User findOne(String name);
    public User insert(User u);
    public User save(User u);
    //Points-related actions
    public List<Point> pointsForUser(int id);
    public List<Point> pointsForUserInLine(int userid, int lineid);
    public Point storePoint(Point p);
}
