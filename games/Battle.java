package battleship.games;
import battleship.core.*;
import battleship.ships.*;
import java.util.*;

public class Battle extends Game {
    
    public enum Mode {
        RANDOM_SPAWN,
        ZONE_SPAWN
    }
    
    private List<Class<? extends Ship>> combatants;
    private Mode mode = Mode.ZONE_SPAWN;
    private int starterSeed = 0;
    
    public Battle(List<Class<? extends Ship>> combatants, int seed, Mode mode) {
        this.mode = mode;
        this.starterSeed = seed;
        this.combatants = combatants;
        Arena arena = initializeArena();
        setArena(arena);
        this.setSeed(seed);
    }
    
    @Override
    public String getObjective() {
        return "Battle Mode: Be the last ship standing.";
    }
    
    @Override
    public Arena initializeArena() {
        
        int padding = 2;
        int margin = 1 + (2 * padding);
        int xSize = getCombatants().size() * margin;
        int ySize = margin;
        Arena arena = new Arena(xSize, ySize);
        setArena(arena);
        setSeed(getStarterSeed());
        
        try {
            
            int count = 0;
            for (Class<? extends Ship> shipClass : getCombatants()) {
                Ship player = shipClass.newInstance();
                switch (getMode()) {
                    case RANDOM_SPAWN:
                        int x = 0;
                        int y = 0;
                        boolean spawned = false;
                        while (!spawned) {
                            x = arena.getRandom().nextInt(10);
                            y = arena.getRandom().nextInt(10);
                            spawned = spawnShip(arena, x, y, player);
                        }
                        break;
                    case ZONE_SPAWN:
                        int center = (count * margin) + padding;
                        spawnShip(arena, center, padding, player);
                        count++;
                        break;
                }
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
        List<Ship> ships = getAllShips(getArena());
        return ships.size() == 1;
    }
    
    @Override
    public String getResults() {
        String res = "Battle Results";
        List<Ship> ships = getAllSpawnedShips(getArena());
        for (Ship ship : ships) {
            if (ship.isSunk()) {
                res += "\n";
                res += "- " + ship + " destroyed by " + ship.getSunkBy() + ".";
            } else {
                res += "\n";
                res += "- " + ship + " is still sailing.";
            }
        }
        return res;
    }

    @Override
    public void run() {
        this.runMission(getArena());
    }
    
    public List<Class<? extends Ship>> getCombatants() {
        return this.combatants;
    }
    
    public Mode getMode() {
        return this.mode;
    }
    
    public int getStarterSeed() {
        return this.starterSeed;
    }
   
}