package battleship.games;
import battleship.core.*;
import battleship.ships.*;
import java.util.*;

public class Mission03 extends Game {
    
    public Class<? extends Ship> PLAYER_CLASS = battleship.ships.DummyShip.class;
    
    public Mission03(Class<? extends Ship> c) {
        PLAYER_CLASS = c;
        Arena arena = initializeArena();
        setArena(arena);
    }
    
    @Override
    public String getObjective() {
        return "Mission Objective: Defeat the line of evil patrol ships.";
    }
    
    private Ship player;
    private List<Ship> enemies = new ArrayList<Ship>();
    
    @Override
    public Arena initializeArena() {
        Class<? extends Ship> playerClass = PLAYER_CLASS;
        Class<? extends Ship> enemyClass = battleship.ships.PatrolShip.class;
        Arena arena = new Arena(10, 5);
        try {
            // Spawn Protagonist
            int[] playerSpawn = {2, 2};
            this.player = playerClass.newInstance();
            this.spawnShip(arena, playerSpawn[0], playerSpawn[1], player);
            // Spawn Vertical Column of Patrol Ships
            int x = arena.getXSize() - 1;
            for (int i = 0; i < arena.getYSize(); i++) {
                Ship enemy = enemyClass.newInstance();
                this.spawnShip(arena, x, i, enemy);
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
        int sunk = 0;
        for (Ship enemy : enemies) {
            if (enemy.isSunk()) {
                sunk++;
            }
        }
        return sunk == enemies.size();
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