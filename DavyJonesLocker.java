import battleship.core.*;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/** Generate JSON
 * sed "s/^/import mission00.*;\\n/" RawDavyJonesLocker.java > DavyJonesLocker.java
 * javac -cp ".:lib/*" DavyJonesLocker.java
 * java -cp ".:lib/*" DavyJonesLocker
 */

/** Post JSON for Visualization URL
 * curl --data "@results.json" https://mimirbattleships.glitch.me/data --header "Content-Type: application/json"
 */

public class DavyJonesLocker {

	public static void main(String[] args) {
		int seed = 42;
		if (args.length >= 1) {
			seed = Integer.parseInt(args[0]);
		}
		JSONObject d = new JSONObject();
		// This is where you can change the game class that runs
		Game mission = Main.launch(seed);
		Arena arena = mission.getArena();
		d.put("init", getInitialMap(arena, mission));
		mission.run();
		d.put("turns", getData(mission, arena));
		Helper.writeFileLine("results.json", d.toString());
		Helper.closeAllFiles();
	}
	
	public static List<JSONObject> getData(Game game, Arena arena) {
		List<JSONObject> list = new ArrayList<JSONObject>();
		for (Action act : game.getActions(arena)) {
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
			map.put("x", arena.getXSize());
			map.put("y", arena.getYSize());
		init.put("map", map);
		List<JSONObject> ships = new ArrayList<JSONObject>();
		for(Ship ship : game.getAllSpawnedShips(arena)) {
			JSONObject shipData = shipToJSON(ship, game);
			if (ship.getTeam() != null){
				if (ship.getTeam().equals("player")) {
					shipData.put("color", "#357df2");
				}
			}
			ships.add(shipData);
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
		s.put("team", ship.getTeam());
		s.put("x", game.getShipCoord(ship).getX());
		s.put("y", game.getShipCoord(ship).getY());
		s.put("hull", ship.getHull());
		s.put("firepower", ship.getFirepower());
		s.put("speed", ship.getSpeed());
		s.put("range", ship.getRange());
		try {
			String color = ship.getColor();
			if (color != null) {
				s.put("color", color);
			} else {
				s.put("color", "#19191c");
			}
		} catch (Exception e) {
			// No getColor method, ignore
			s.put("color", "#19191c");
		}
		return s;
	}

}