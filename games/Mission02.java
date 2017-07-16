package battleship.games;
import battleship.core.*;
import battleship.ships.*;
import java.util.*;

public class Mission02 extends Game {
    
    public Class<? extends Ship> PLAYER_CLASS = battleship.ships.DummyShip.class;
    
    public Mission02(Class<? extends Ship> c) {
        PLAYER_CLASS = c;
        Arena arena = initializeArena();
        setArena(arena);
    }
    
    @Override
    public String getObjective() {
        return "Mission Objective: Reach the end of a narrow straight.";
    }
    
    private Ship player;
    private List<Ship> enemies = new ArrayList<Ship>();
    
    @Override
    public Arena initializeArena() {
        Class<? extends Ship> playerClass = PLAYER_CLASS;
        Class<? extends Ship> enemyClass = battleship.ships.DummyShip.class;
        
        Arena arena = new Arena(10, 2);
        
        try {
            int[] playerSpawn = {0, 0};
            this.player = playerClass.newInstance();
            this.spawnShip(arena, playerSpawn[0], playerSpawn[1], player);
        
            int[][] enemySpawns = {
                {4, 0},
                {6, 0},
                {2, 1},
                {8, 1}
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
        boolean success = false;
        Coord coord = this.getShipCoord(player);
        if (coord.getX() == 9) {
            success = true;
        }
        return success;
    }
    
    @Override
    public String getResults() {
        return "No additional details to report.";
    }

    @Override
    public void run() {
        this.runMission(getArena());
    }
   
}