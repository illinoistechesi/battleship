package battleship.games;
import battleship.core.*;
import battleship.ships.*;
import java.util.*;

public class TeamBattle extends Game {
    
    private List<Loyalty> combatants;
    private int starterSeed = 0;
    
    public TeamBattle(List<Loyalty> combatants, int seed) {
        this.starterSeed = seed;
        this.combatants = combatants;
        Arena arena = initializeArena();
        setArena(arena);
        this.setSeed(seed);
    }
    
    public static class Loyalty {
        
        private Class<? extends Ship> shipClass;
        private String team;
        
        public Loyalty(Class<? extends Ship> shipClass, String team) {
            this.shipClass = shipClass;
            this.team = team;
        }
        
        public Class<? extends Ship> getShipClass() {
            return this.shipClass;
        }
        
        public String getTeam() {
            return this.team;
        }
        
    }
    
    @Override
    public String getObjective() {
        return "Battle Mode: Defeat the other team!";
    }
    
    @Override
    public Arena initializeArena() {
        
        int xSize = 10;
        int ySize = 5;
        Arena arena = new Arena(xSize, ySize);
        setArena(arena);
        setSeed(getStarterSeed());
        
        try {
            
            /*Map<String, Integer> teamCountMap = new HashMap<String, Integer>();
            for (String team : getCombatantMap.values()) {
                
            }*/
            
            int[][] spawns = {
                {0, 1},
                {0, 2},
                {0, 3},
                {xSize - 1, 1},
                {xSize - 1, 2},
                {xSize - 1, 3}
            };
            int count = 0;
            for (Loyalty record : getCombatants()) {
                Class<? extends Ship> shipClass = record.getShipClass();
                String team = record.getTeam();
                Ship player = shipClass.newInstance();
                setShipTeam(player, team);
                int[] point = spawns[count];
                int x = point[0];
                int y = point[1];
                spawnShip(arena, x, y, player);
                count++;
                //System.out.println(shipClass + " " + team + " " + player.getCoord());
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
        boolean warOver = true;
        List<Ship> ships = getAllShips(getArena());
        String firstTeam = ships.get(0).getTeam();
        for (Ship ship : ships) {
            //System.out.println(firstTeam + " " + ship.getTeam());
            if (!ship.getTeam().equals(firstTeam)) {
                warOver = false;
            }
        }
        return warOver;
    }
    
    @Override
    public String getResults() {
        String res = "Battle Results";
        List<Ship> survivors = getAllShips(getArena());
        Ship winner = survivors.get(0);
        if (winner != null) {
            res += "\n";
            if (isCompleted()) {
                res += "Winner: " + winner.getTeam();
            } else {
                res += "Draw: No Winner";
            }
        }
        List<Ship> ships = getAllSpawnedShips(getArena());
        for (Ship ship : ships) {
            if (ship.isSunk()) {
                res += "\n";
                res += "- [" + ship.getTeam() + "] " + ship + " destroyed by " + ship.getSunkBy() + ".";
            } else {
                res += "\n";
                res += "- [" + ship.getTeam() + "] "  + ship + " is still sailing.";
            }
        }
        return res;
    }

    @Override
    public void run() {
        this.runMission(getArena());
    }
    
    public List<Loyalty> getCombatants() {
        return this.combatants;
    }
    
    public int getStarterSeed() {
        return this.starterSeed;
    }
   
}