import esi18.*;
import battleship.core.*;
import java.util.*;

/*
 * Duo Battle: Red Team vs Blue Team
 * Win the 2v2 battle against the other team.
 */
public class Main extends Game {

	public static void main(String[] args) {
		int seed = 42;
		if (args.length >= 1) {
			seed = Integer.parseInt(args[0]);
		}
		Game mission = Main.launch(seed);
		mission.run();
	}

	public static Game launch(int seed) {
		Game mission = new Main();
		mission.setSeed(seed);
		mission.setArenaFile("./files/arena.txt");
		mission.setTurnFile("./files/turns.txt");
		mission.setLogFile("./files/logs.txt");
		return mission;
	}
	
	public Main() {
		Arena arena = initializeArena();
		setArena(arena);
	}
	
	@Override
	public String getObjective() {
		return "Mission Objective: Win the 2v2 battle against the other team.";
	}

	private List<Ship> redTeam = new ArrayList<Ship>();
	private List<Ship> blueTeam = new ArrayList<Ship>();
	
	@Override
	public Arena initializeArena() {
		Arena arena = new Arena(10, 10);

		// add ship and team here
		// Ship ship1 = new [YourShipName]Ship.java
		// setShipTeam(ship1, "Team Name");
		// setShipColor(ship1, "#ffffff");
		// spawnShip(arena, x, y, ship1);

		Ship scarlet = new SimpleShip();
		Ship crimson = new SimpleShip();
		Ship azure = new AzureShip();
		Ship teal = new TealShip();

		setShipTeam(scarlet, "Red Team");
		setShipColor(scarlet, "#ff291e");
		spawnShip(arena, 0, 5, scarlet);
		redTeam.add(scarlet);
		
		setShipTeam(crimson, "Red Team");
		setShipColor(crimson, "#dc143c");
		spawnShip(arena, 0, 6, crimson);
		redTeam.add(crimson);

		setShipTeam(azure, "Blue Team");
		setShipColor(azure, "#1c89ff");
		spawnShip(arena, 9, 5, azure);
		blueTeam.add(azure);

		setShipTeam(teal, "Blue Team");
		setShipColor(teal, "#149eba");
		spawnShip(arena, 9, 6, teal);
		blueTeam.add(teal);

		return arena;
	}

	private int countSunkShips(List<Ship> team) {
		int sunkCount = 0;
		for (Ship ship : team) {
			if (ship.isSunk()) {
				sunkCount++;
			}
		}
		return sunkCount;
	}

	private boolean isTeamSunk(List<Ship> team) {
		int sunkCount = countSunkShips(team);
		boolean allSunk = sunkCount == team.size();
		return allSunk;
	}
	
	@Override
	public boolean isCompleted() {
		boolean redSunk = isTeamSunk(redTeam);
		boolean blueSunk = isTeamSunk(blueTeam);
		return redSunk || blueSunk;
	}
	
	@Override
	public String getResults() {
		List<Ship> sunk = new ArrayList<Ship>();
		List<Ship> allShips = new ArrayList<Ship>(redTeam);
		allShips.addAll(blueTeam);
		for (Ship ship : allShips) {
			if (ship.isSunk()) {
				sunk.add(ship);
			}
		}
		// Score = number of enemy ships sunk
		int redScore = countSunkShips(blueTeam);
		int blueScore = countSunkShips(redTeam);
		String res = "";
		if (redScore == blueScore) {
			res += "Battle was a draw.";
		} else if (redScore > blueScore) {
			res += "Red Team wins.";
		} else {
			res += "Blue Team wins.";
		}
		res += String.format(" Ships Sunk: Red (%d) - Blue (%d)", redScore, blueScore);
		for (Ship ship : sunk) {
			res += "\n";
			res += "- " + ship.getName() + " sunk by " + ship.getSunkBy().getName() + ".";
		}
		boolean isDraw = redScore == blueScore;
		// if (!isDraw) {
		// 	Helper.writeFileLine("./OUTPUT", "100");
		// } else {
		// 	Helper.writeFileLine("./OUTPUT", "0");
		// }
		return res;
	}

	@Override
	public void run() {
		this.runMission(getArena());
	}
   
}