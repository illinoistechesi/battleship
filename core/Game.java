package battleship.core;
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
    
    public void setSeed(int seed) {
        getArena().setSeed(seed);
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
    
    private int maxTurns = 50;
    private boolean debugMode = true;
    private String arenaFile = "./files/game-arena.txt";
    private String turnFile = "./files/game-turns.txt";
    
    public int getMaxTurns() {
        return this.maxTurns;
    }
    
    public boolean getDebugMode() {
        return this.debugMode;
    }
    
    public String getArenaFile() {
        return this.arenaFile;
    }
    
    public String getTurnFile() {
        return this.turnFile;
    }
    
    public void setMaxTurns(int maxTurns) {
        this.maxTurns = maxTurns;
    }
    
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
    
    public void setArenaFile(String arenaFile) {
        this.arenaFile = arenaFile;
    }
    
    public void setTurnFile(String turnFile) {
        this.turnFile = turnFile;
    }
    
    public void runMission(Arena arena) {
        int maxTurns = this.getMaxTurns();
        boolean debugMode = this.getDebugMode();
        String arenaFile = this.getArenaFile();
        String debugFile = this.getTurnFile();
        System.out.print("\n");
        System.out.println(this.getObjective());
        Helper.writeFileLine(arenaFile, "Initial Map");
        Helper.writeFileLine(arenaFile, getArenaAsText(arena));
        System.out.println("\n" + getArenaAsText(arena));
        arena.setDebugMode(debugMode, debugFile);
        boolean success = false;
        int t = 0;
        while (t < maxTurns) {
            Helper.writeFileLine(debugFile, "Turn " + t);
            //if (t % 10 == 0) {
                System.out.print("\rTurn " + t);
            //}
            List<Ship> ships = this.sortShipsByPriority(arena);
            for (Ship ship : ships) {
                if (!ship.isSunk()) {
                    Helper.writeFileLine(debugFile, ship + "");
                    this.initializeTurn(ship);
                    try {
                        ship.doTurn(arena);
                    } catch (Exception e) {
                        // Squash problems with the doTurn function
                    }
                }
            }
            Helper.writeFileLine(arenaFile, "After T = " + t);
            Helper.writeFileLine(arenaFile, getArenaAsText(arena));
            if (this.isCompleted()) {
                success = true;
                break;
            } else {
                t++;
                arena.nextTurn();
            }
        }
        System.out.print("\r");
        if (!success) {
            System.out.println("Game expired after " + t + " turns.");
        } else {
            System.out.println("Game completed after " + t + " turns.");
        }
        System.out.print("\n");
        System.out.println(this.getResults());
        System.out.print("\n");
        Helper.closeAllFiles();
    }
    
}