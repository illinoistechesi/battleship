package battleship.games;
import battleship.core.*;
import battleship.ships.*;
import java.util.*;

public class DroneBattle extends Game {
    
    private List<Spawn> combatants;
    private Class<? extends Ship> droneClass;
    private int starterSeed = 0;
    
    public DroneBattle(List<Spawn> combatants, Class<? extends Ship> droneClass, int seed) {
        this.starterSeed = seed;
        this.combatants = combatants;
        this.droneClass = droneClass;
        Arena arena = initializeArena();
        setArena(arena);
        this.setSeed(seed);
    }
    
    @Override
    public String getObjective() {
        return "Battle Mode: Defeat the most drones!";
    }
    
    @Override
    public Arena initializeArena() {
        
        int xSize = 15;
        int ySize = 5;
        Arena arena = new Arena(xSize, ySize);
        setArena(arena);
        setSeed(getStarterSeed());
        
        try {
            
            int[][] spawns = {
                {0, 0},
                {0, 1},
                {0, 3},
                {0, 4},
                {xSize - 1, 0},
                {xSize - 1, 1},
                {xSize - 1, 3},
                {xSize - 1, 4}
            };
            int count = 0;
            for (Spawn record : getCombatants()) {
                Class<? extends Ship> shipClass = record.getShipClass();
                String team = record.getTeam();
                Ship player = shipClass.newInstance();
                setShipTeam(player, team);
                int[] point = spawns[count];
                int x = point[0];
                int y = point[1];
                spawnShip(arena, x, y, player);
                count++;
            }
            
            String droneTeam = "Drone Army";
            int[][] drones = {
                                {5, 2},
                        {6, 1}, {6, 2}, {6, 3},
                {7, 0}, {7, 1}, {7, 2}, {7, 3}, {7, 4},
                        {8, 1}, {8, 2}, {8, 3},
                                {9, 2}
            };
            
            for (int d = 0; d < drones.length; d++) {
                Ship drone = this.droneClass.newInstance();
                setShipTeam(drone, droneTeam);
                int[] droneSpawn = drones[d];
                int x = droneSpawn[0];
                int y = droneSpawn[1];
                spawnShip(arena, x, y, drone);
            }
            
        } catch (InstantiationException ie) {
            ie.printStackTrace();
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
        }
        
        return arena;
    }
    
    @Override
    public boolean isCompleted() {
        boolean warOver = false;
        int otherCount = 0;
        int droneCount = 0;
        for (Ship ship : getArena().getAllSpawnedShips()) {
            boolean stillSailing = !ship.isSunk();
            if (stillSailing) {
                if (this.droneClass.isInstance(ship)) {
                    droneCount++;
                } else {
                    otherCount++;
                }
            }
        }
        if (otherCount == 0) {
            System.out.println("All other ships sunk.");
            warOver = true;
        }
        if (droneCount == 0) {
            System.out.println("All drones sunk.");
            warOver = true;
        }
        return warOver;
    }
    
    @Override
    public String getResults() {
        String res = "Battle Results";
        Map<String, Integer> scoreMap = new HashMap<String, Integer>();
        List<Ship> ships = getAllSpawnedShips(getArena());
        for (Ship ship : ships) {
            /*if (ship.isSunk()) {
                res += "\n";
                res += "- [" + ship.getTeam() + "] " + ship + " destroyed by " + ship.getSunkBy() + ".";
            } else {
                res += "\n";
                res += "- [" + ship.getTeam() + "] "  + ship + " is still sailing.";
            }*/
            String shipTeam = ship.getTeam();
            if (!scoreMap.containsKey(shipTeam)) {
                scoreMap.put(shipTeam, 0);
            }
            if (ship.isSunk()) {
                Ship attacker = ship.getSunkBy();
                String team = attacker.getTeam();
                if (!scoreMap.containsKey(team)) {
                    scoreMap.put(team, 0);
                }
                int kills = scoreMap.get(team);
                kills++;
                scoreMap.put(team, kills);
            }
        }
        String winningTeam = null;
        for (Map.Entry<String, Integer> score : scoreMap.entrySet()) {
            if (winningTeam == null) {
                winningTeam = score.getKey();
            } else if (score.getValue() > scoreMap.get(winningTeam)) {
                winningTeam = score.getKey();
            }
        }
        res += "\n";
        res += "Winner: " + winningTeam;
        for (Map.Entry<String, Integer> score : scoreMap.entrySet()) {
            String teamKey = score.getKey();
            int shipsSunk = score.getValue();
            String scoreLine =  String.format("- %s sunk %d ships.", teamKey, shipsSunk);
            res += "\n";
            res += scoreLine;
        }
        return res;
    }

    @Override
    public void run() {
        this.runMission(getArena());
    }
    
    public List<Spawn> getCombatants() {
        return this.combatants;
    }
    
    public int getStarterSeed() {
        return this.starterSeed;
    }
   
}