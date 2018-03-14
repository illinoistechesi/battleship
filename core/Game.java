package battleship.core;
import java.util.List;
import java.util.ArrayList;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.PrintStream;

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
     
    public void setShipTeam(Ship ship, String team) {
        ship.setTeam(team);
    }

    public void setShipColor(Ship ship, String color) {
        ship.setColor(color);
    }
    
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
    
    public List<Action> getActions(Arena arena) {
        return arena.getActions();
    }
    
    private boolean verbose = true;
    private boolean canPrint = true;
    private int maxTurns = 50;
    private boolean debugMode = true;
    private String arenaFile = "./files/game-arena.txt";
    private String turnFile = "./files/game-turns.txt";
    private String logFile = "./files/game-log.txt";
    
    public boolean isVerbose() {
        return this.verbose;
    }
    
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
    public boolean canPrint() {
        return this.canPrint;
    }
    
    public void setCanPrint(boolean canPrint) {
        this.canPrint = canPrint;
    }
    
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
    
    public String getLogFile() {
        return this.logFile;
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
    
    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }
    
    public void println(String content) {
        if (this.isVerbose()) {
            System.out.println(content);
        }
    }
    
    public void print(String content) {
        if (this.isVerbose()) {
            System.out.print(content);
        }
    }
    
    public void runMission(Arena arena) {
        boolean hadAnyProblems = false;
        int maxTurns = this.getMaxTurns();
        boolean debugMode = this.getDebugMode();
        String arenaFile = this.getArenaFile();
        String debugFile = this.getTurnFile();
        String logFile = this.getLogFile();
        this.print("\n");
        this.println(this.getObjective());
        Helper.writeFileLine(logFile, "Log File: Exceptions");
        Helper.writeFileLine(arenaFile, "Initial Map");
        Helper.writeFileLine(arenaFile, getArenaAsText(arena));
        this.println("\n" + getArenaAsText(arena));
        arena.setDebugMode(debugMode, debugFile);
        boolean success = false;
        int t = 0;
        while (t < maxTurns) {
            Helper.writeFileLine(debugFile, "Turn " + t);
            //if (t % 10 == 0) {
                this.print("\rTurn " + t + "\t\t");
            //}
            List<Ship> ships = this.sortShipsByPriority(arena);
            for (Ship ship : ships) {
                if (!ship.isSunk()) {
                    Helper.writeFileLine(debugFile, ship + "");
                    this.initializeTurn(ship);
                    PrintStream oldOut = System.out;
                    if (!this.canPrint()) {
                        System.setOut(null);   
                    }
                    try {
                        arena.setCurrentShip(ship);
                        ship.doTurn(arena);
                    } catch (Exception e) {
                        StringWriter sw = new StringWriter();
                        e.printStackTrace(new PrintWriter(sw));
                        String exceptionAsString = sw.toString();
                        String err = "Exception on Turn " + t + ", caused by " + ship + ":\n";
                        err += exceptionAsString;
                        err += "\n";
                        Helper.writeFileLine(logFile, err);
                        hadAnyProblems = true;
                        // Squash problems with the doTurn function
                    }
                    System.setOut(oldOut);
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
        this.print("\r");
        if (!success) {
            this.println("Game expired after " + t + " turns.");
        } else {
            this.println("Game completed after " + t + " turns.");
        }
        if (hadAnyProblems) {
            this.println("There were exceptions in at least one ship class.");
            this.println("- View " + logFile + " for details.");
        }
        this.println("- View " + arenaFile + " for arena map.");
        this.println("- View " + debugFile + " for turn log.");
        this.print("\n");
        this.println(this.getResults());
        this.print("\n");
    }
    
}