package me.mrabar.cityexplorer.rest;


import me.mrabar.cityexplorer.controllers.Repo;
import me.mrabar.cityexplorer.model.Point;
import me.mrabar.cityexplorer.model.PointDTO;
import me.mrabar.cityexplorer.model.shapes.BerlinRectangle;
import me.mrabar.cityexplorer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

@RestController
public class BackendController {

    @Autowired
    public Repo userDB;

    private static String msg = "{\"result\": \"%s\"}";

    @RequestMapping("/greeting")
    public User greeting(@RequestParam(value="user", defaultValue="World") String name) {
        if(userDB.exists(name))
            return userDB.findOne(name);
        else {
            User u = new User();
            u.setName(name);
            return userDB.insert(u);
        }
    }

    @RequestMapping(value="/points", method= RequestMethod.GET)
    public List<Point> getPoints(@RequestParam(value="user", required = true) String name) {
        return userDB.pointsForUser(userDB.findOne(name).getId());
    }

    @RequestMapping(value = "/shape/{id}", method = RequestMethod.GET)
    public String getCoverage(@PathVariable(value = "id") int id,
                              @RequestParam(value="user", required = true) String name)
    {
        double perc = 0.0;
        if(id == 0) {
            perc = new BerlinRectangle().percentageCovered(userDB.pointsForUser(userDB.findOne(name).getId()));
        }
        DecimalFormat formatter = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        formatter.setRoundingMode( RoundingMode.UP);
        return String.format(msg, formatter.format(perc));
    }

    @RequestMapping(value = "/shape/{id}/points", method = RequestMethod.GET)
    public List<PointDTO> shapePoints(@PathVariable(value = "id") int id)
    {
        if(id == 0) {
            return new BerlinRectangle().getPointsList();
        }
        return null;
    }

    @RequestMapping(value = "/point/new", method = RequestMethod.PUT)
    public String update(@RequestParam(value="user", required = true) String name, @RequestBody PointDTO p) {
        return (userDB.storePoint(userDB.findOne(name).newLine(p)) != null)
                ? String.format(msg, "OK") : String.format(msg, "FAIL");
    }

    @RequestMapping(value = "/point/old", method = RequestMethod.PUT)
    public String append(@RequestParam(value="user", required = true) String name, @RequestBody PointDTO p) {
        return (userDB.storePoint(userDB.findOne(name).appendPoint(p)) != null)
                ? String.format(msg, "OK") : String.format(msg, "FAIL");
    }

    @RequestMapping(value = "/distance", method = RequestMethod.GET)
    public String calcDist(@RequestParam(value="user", required = true) String name) {
        Point p = null;
        float f = 0.0f;
        for(Point m: userDB.pointsForUser(userDB.findOne(name).getId())) {
            if(p != null) f += m.distanceToInKm(p);
            p = m;
        }
        return String.format(msg, Float.toString(f));
    }
}
