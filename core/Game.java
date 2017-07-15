package core;
import java.util.List;
import java.util.ArrayList;

public abstract class Game {
    
    private Arena arena;
    
    public Game() {
        
    }
    
    public abstract String getObjective();
    
    public abstract Arena initializeArena();
    
    public abstract boolean isCompleted();
    
    public abstract String getResults();
    
    public abstract void run();
    
    public Arena getArena() {
        return this.arena;
    }
    
    public void setArena(Arena arena) {
        this.arena = arena;
    }
    
    /*
     * Functions for building games outside of core package
     */
    
    public Coord getShipCoord(Ship ship) {
        return ship.getCoord();
    }
    
    public void setSeed(Arena arena, int seed) {
        arena.setSeed(seed);
    }
    
    public boolean spawnShip(Arena arena, int x, int y, Ship ship) {
        return arena.spawnShip(x, y, ship);
    }
    
    public String getArenaAsText(Arena arena) {
        return arena.getArenaAsText();
    }
    
    public List<Ship> sortShipsByPriority(Arena arena) {
        return arena.sortShipsByPriority();
    }
    
    public void initializeTurn(Ship ship) {
        ship.initializeTurn();
    }
    
    public List<Ship> getAllShips(Arena arena) {
        return arena.getAllShips();
    }
    
    public List<Ship> getAllSpawnedShips(Arena arena) {
        return arena.getAllSpawnedShips();
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
            if (this.isCompleted()) {
                success = true;
                break;
            } else {
                t++;
                arena.nextTurn();
            }
        }
        if (!success) {
            System.out.println("Game expired after " + t + " turns.");
        } else {
            System.out.println("Game completed after " + t + " turns.");
        }
        System.out.println(this.getResults());   
    }
    
}