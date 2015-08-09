package me.mrabar.cityexplorer.controllers;

import me.mrabar.cityexplorer.model.Point;
import me.mrabar.cityexplorer.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RepoController implements Repo {
    public ArrayList<User> users = new ArrayList<>(5);
    public ArrayList<Point> points = new ArrayList<>(100);
    @Override
    public boolean exists(String name) {
        return findOne(name) != null;
    }

    @Override
    public User findOne(String name) {
        return users.stream()
                .filter(m -> name.equals(m.getName())).findFirst().orElse(null);
    }

    @Override
    public User insert(User u) {
        if(users.contains(u)) return null;
        users.add(u);
        return u;
    }

    @Override
    public User save(User u) {
        if(users.contains(u)) return u;
        return null;
    }

    @Override
    public List<Point> pointsForUser(int id) {
        return points.stream().filter(p -> id == p.getUserID()).collect(Collectors.toList());
    }

    @Override
    public List<Point> pointsForUserInLine(int userid, int lineid) {
        return points.stream()
                .filter(p -> userid == p.getUserID() && lineid == p.getLineID())
                .collect(Collectors.toList());
    }

    @Override
    public Point storePoint(Point p) {
        points.add(p);
        return p;
    }
}
