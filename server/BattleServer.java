/*
 * REFERENCES
 * http://sparkjava.com/documentation#getting-started
 * http://www.programcreek.com/2014/01/compile-and-run-java-in-command-line-with-external-jars/
 */

package server;
import core.*;
import ships.*;
import games.*;

import static spark.Spark.*;
import org.json.simple.JSONObject;

import java.lang.*;
import java.text.*;
import java.io.*;
import java.util.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.CompletableFuture.*;
import java.util.concurrent.ExecutorService.*;
import java.util.concurrent.Executors.*;

/*
 * RUN
   >> javac -cp ".:lib/*" server/BattleServer.java
   >> java -cp ".:lib/*" server/BattleServer
   Open: http://battleship-vingkan.c9users.io/{route}
 */

public class BattleServer {

    public static void main(String[] args){
        
        // Cloud9 environments serve localhost to port 8080
        port(8080);
        // Enable Cross-Origin Requests
        enableCORS("*", "*", "*");
        
        before((req, res) -> {
            String path = req.pathInfo();
            if(path.endsWith("/") && path.length() > 1){
                res.redirect(path.substring(0, path.length() - 1));
            }
        });
        
        System.out.println("\nStarted.\n");
            
        // get("/", (req, res) -> {
        //   return "ESI CS Battleship Server"; 
        // });
        
        /*
         * Get Mission Results
         * http://battleship-vingkan.c9users.io/mission/1
         */
        get("/mission/:n", (req, res) -> {
            Class<? extends Ship> pc = ships.DeltaShip.class;
            int n = Integer.parseInt(req.params(":n"));
            System.out.println("GET: /mission/" + n);
            Game mission = MissionRunner.getMission(n, pc);
            mission.setArenaFile("files/server-mission-arena.txt");
            mission.setTurnFile("files/server-mission-turns.txt");
            JSONObject d = new JSONObject();
            Arena arena = mission.getArena();
            d.put("init", getInitialMap(arena, mission));
            mission.run();
            d.put("turns", getData(arena));
            Helper.closeAllFiles();
            return d;
        });
        
        /*
         * Get Battle Results
         * http://battleship-vingkan.c9users.io/mission/1
         */
        get("/battle", (req, res) -> {
            System.out.println("GET: /battle");
            Game battle = new Battle();
            battle.setArenaFile("files/server-battle-arena.txt");
            battle.setTurnFile("files/server-battle-turns.txt");
            JSONObject d = new JSONObject();
            Arena arena = battle.getArena();
            d.put("init", getInitialMap(arena, battle));
            battle.run();
            d.put("turns", getData(arena));
            Helper.closeAllFiles();
            return d;
        });
        
        System.out.println("\nEnd.\n");
    
    }
    
    public static List<JSONObject> getData(Arena arena) {
        List<JSONObject> list = new ArrayList<JSONObject>();
        for (Action act : arena.actions) {
            JSONObject a = new JSONObject();
            a.put("type", act.getType());
            a.put("turn", act.getTurn());
            a.put("id", act.getID());
            a.put("x", act.getX());
            a.put("y", act.getY());
            a.put("health", act.getHealth());
            if (act.getType().equals("MOVE")) {
                a.put("direction", act.getDirection());
            }
            else if (act.getType().equals("FIRE")) {
                a.put("atX", act.getAtX());
                a.put("atY", act.getAtY());
            } else if (act.getType().equals("SINK")) {
                a.put("attacker", act.getAttacker());
            }
            list.add(a);
        }
        return list;
    }
    
    public static JSONObject getInitialMap(Arena arena, Game game) {
        JSONObject init = new JSONObject();
        JSONObject map = new JSONObject();
            map.put("x", 10);
            map.put("y", 10);
        init.put("map", map);
        List<JSONObject> ships = new ArrayList<JSONObject>();
        for(Ship ship : game.getAllSpawnedShips(arena)) {
            ships.add(shipToJSON(ship, game));
        }
        init.put("ships", ships);
        return init;
    }
   
    public static JSONObject shipToJSON(Ship ship, Game game) {
        JSONObject s = new JSONObject();
        String id = ship + "";
        s.put("id", id);
        s.put("name", ship.getName());
        s.put("owner", ship.getOwner());
        s.put("x", game.getShipCoord(ship).getX());
        s.put("y", game.getShipCoord(ship).getY());
        s.put("hull", ship.getHull());
        s.put("firepower", ship.getFirepower());
        s.put("speed", ship.getSpeed());
        s.put("range", ship.getRange());
        return s;
    }
    
    // Enables CORS on requests. This method is an initialization method and should be called once.
    private static void enableCORS(final String origin, final String methods, final String headers) {
    
        options("/*", (request, response) -> {
    
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
    
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
    
            return "OK";
        });
    
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });
    }
    
}