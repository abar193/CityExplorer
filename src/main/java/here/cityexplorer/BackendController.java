package here.cityexplorer;


import here.cityexplorer.Controllers.Repo;
import here.cityexplorer.Model.Point;
import here.cityexplorer.Model.Rectangle;
import here.cityexplorer.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BackendController {

    @Autowired
    public Repo userDB;

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

    @RequestMapping(value="/rectangles", method= RequestMethod.GET)
    public List<Rectangle> rectangles(@RequestParam(value="user", required = true) String name) {
        ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
        User u = greeting(name);
        boolean[][] points = u.getPoints();
        Rectangle r = null;
        for(int x = 0; x < 100; x++) {
            for(int y = 0; y < 100; y++) {
                if(points[x][y]) {
                    if(r == null) r = new Rectangle(x, y);
                } else if(r != null) {
                    r.setEnd(Point.fromXY(Math.min(99, x + 1), Math.min(99, y + 1)));
                    rects.add(r);
                    r = null;
                }
            }
            if(r != null) {
                r.setEnd(Point.fromXY(Math.min(99, x + 1), 100));
                rects.add(r);
                r = null;
            }
        }
        return rects;
    }

    @RequestMapping(value = "/point", method = RequestMethod.PUT)
    public String update(@RequestParam(value="user", required = true) String name, @RequestBody Point p) {
        User u = greeting(name);
        String a = (u.activatePointAt(p.getX(), p.getY())) ? "OK" : "FAIL";
        userDB.save(u);
        return "{\"result\": \"" + a + "\"}";
    }

    @RequestMapping(value = "/append", method = RequestMethod.PUT)
    public String append(@RequestParam(value="user", required = true) String name, @RequestBody Point p) {
        User u = greeting(name);
        String a = (u.appendTo(p.getX(), p.getY())) ? "OK" : "FAIL";
        userDB.save(u);
        return "{\"result\": \"" + a + "\"}";
    }
}
