package battleship.games;
import battleship.core.*;
import battleship.ships.*;
import java.util.*;

public class CustomBattle extends Game {
    
    private List<Spawn> combatants;
    private int starterSeed = 0;
    private int xSize = 10;
    private int ySize = 10;
    
    public CustomBattle(List<Spawn> combatants, int seed, int x, int y) {
        this.starterSeed = seed;
        this.combatants = combatants;
        this.xSize = x;
        this.ySize = y;
        Arena arena = initializeArena();
        setArena(arena);
        this.setSeed(seed);
    }
    
    @Override
    public String getObjective() {
        return "Battle Mode: Be the last team standing!";
    }
    
    @Override
    public Arena initializeArena() {
        
        int xSize = this.getXSize();
        int ySize = this.getYSize();
        Arena arena = new Arena(xSize, ySize);
        setArena(arena);
        setSeed(getStarterSeed());
        
        try {
            
            for (Spawn record : getCombatants()) {
                Class<? extends Ship> shipClass = record.getShipClass();
                String team = record.getTeam();
                Ship player = shipClass.newInstance();
                setShipTeam(player, team);
                int x = record.getX();
                int y = record.getY();
                spawnShip(arena, x, y, player);
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
        Map<String, Integer> teamMap = new HashMap<String, Integer>();
        for (Ship ship : getArena().getAllSpawnedShips()) {
            String team = ship.getTeam();
            if (!teamMap.containsKey(team)) {
                teamMap.put(team, 0);
            }
            boolean stillSailing = !ship.isSunk();
            if (stillSailing) {
                int count = teamMap.get(team);
                count++;
                teamMap.put(team, count);
            }
        }
        int survivingTeams = 0;
        for (Map.Entry<String, Integer> entry : teamMap.entrySet()) {
            if (entry.getValue() > 0) {
                survivingTeams++;
            }
        }
        if (survivingTeams <= 1) {
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
    
    public int getXSize() {
        return this.xSize;
    }
    
    public int getYSize() {
        return this.ySize;
    }
   
}