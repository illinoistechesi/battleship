package core;
import java.util.List;
import java.util.ArrayList;

public abstract class Game {
    
    public Game() {
        
    }
    
    public abstract String getObjective();
    
    public abstract Arena initializeArena();
    
    public abstract boolean isCompleted(Arena arena);
    
    public abstract String getResults(Arena arena);
    
    public abstract void run(Arena arena);
    
    /*
     * Functions for building games outside of core package
     */
    
    protected Coord getShipCoord(Ship ship) {
        return ship.getCoord();
    }
    
    protected void setSeed(Arena arena, int seed) {
        arena.setSeed(seed);
    }
    
    protected boolean spawnShip(Arena arena, int x, int y, Ship ship) {
        return arena.spawnShip(x, y, ship);
    }
    
    protected String getArenaAsText(Arena arena) {
        return arena.getArenaAsText();
    }
    
    protected List<Ship> sortShipsByPriority(Arena arena) {
        return arena.sortShipsByPriority();
    }
    
    protected void initializeTurn(Ship ship) {
        ship.initializeTurn();
    }
    
    public void runMission(Arena arena, int maxTurns, boolean debugMode,
                                String arenaFile, String debugFile) {
        System.out.println(this.getObjective());
        Helper.writeFileLine(arenaFile, "Initial Map");
        Helper.writeFileLine(arenaFile, this.getArenaAsText(arena));
        arena.setDebugMode(debugMode, debugFile);
        boolean success = false;
        int t = 0;
        while (t < maxTurns) {
            Helper.writeFileLine(debugFile, "Turn " + t);
            if (t % 10 == 0) {
                System.out.println("Turn " + t);
            }
            List<Ship> ships = this.sortShipsByPriority(arena);
            for (Ship ship : ships) {
                this.initializeTurn(ship);
                ship.doTurn(arena);
            }
            Helper.writeFileLine(arenaFile, "After T = " + t);
            Helper.writeFileLine(arenaFile, this.getArenaAsText(arena));
            if (this.isCompleted(arena)) {
                success = true;
                break;
            } else {
                t++;
            }
        }
        if (!success) {
            System.out.println("Game expired after " + t + " turns.");
        } else {
            System.out.println("Game completed after " + t + " turns.");
        }
        System.out.println(this.getResults(arena));   
    }
    
}