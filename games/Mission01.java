package games;
import core.*;
import ships.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Mission01 extends Game {
    
    public static Class<? extends Ship> PLAYER_CLASS = games.DummyShip.class;
    public static final int MAX_TURNS = 50;
    public static boolean DEBUG_MODE = true;
    public static final String ARENA_FILE = "files/arena.txt";
    public static String DEBUG_FILE = "files/log.txt";
    
    public Mission01(Class<? extends Ship> c) {
        PLAYER_CLASS = c;
        Arena arena = initializeArena();
        setArena(arena);
    }
    
    @Override
    public String getObjective() {
        return "Mission Objective: Sink at least three enemy ships.";
    }
    
    private List<Ship> enemies = new ArrayList<Ship>();
    
    @Override
    public Arena initializeArena() {
        Class<? extends Ship> playerClass = PLAYER_CLASS;
        Class<? extends Ship> enemyClass = games.DummyShip.class;
        
        Arena arena = new Arena(10, 10);
        this.setSeed(arena, 42);
        
        try {
            int[] playerSpawn = {5, 5};
            Ship player = playerClass.newInstance();
            this.spawnShip(arena, playerSpawn[0], playerSpawn[1], player);
        
            int[][] enemySpawns = {
                {4, 0},
                {5, 0},
                {3, 1},
                {6, 1}
            };
            for (int n = 0; n < enemySpawns.length; n++) {
                int[] enemySpawn = enemySpawns[n];
                Ship enemy = enemyClass.newInstance();
                this.spawnShip(arena, enemySpawn[0], enemySpawn[1], enemy);
                enemies.add(enemy);
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
        int enemiesSunk = 0;
        for (Ship enemyShip : enemies) {
            if (enemyShip.isSunk()) {
                enemiesSunk++;
            }
        }
        return enemiesSunk >= 3;
    }
    
    @Override
    public String getResults() {
        int enemiesSunk = 0;
        List<Ship> sunk = new ArrayList<Ship>();
        for (Ship enemyShip : enemies) {
            if (enemyShip.isSunk()) {
                sunk.add(enemyShip);
                enemiesSunk++;
            }
        }
        String res = "Sunk " + enemiesSunk + " enemy ships.";
        for (Ship ship : sunk) {
            res += "\n";
            res += "- Sunk " + ship + " at " + getShipCoord(ship) + ".";
        }
        return res;
    }

    @Override
    public void run() {
        this.runMission(getArena(), MAX_TURNS, DEBUG_MODE, ARENA_FILE, DEBUG_FILE);
    }
   
}