package core;
import samples.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    
    public static final int MAX_TURNS = 20;
    public static final String ARENA_FILE = "files/arena.txt";
    
    public static void main(String[] args) {
    
        Game game = new Game();
        game.simpleGame(DeltaShip.class, DummyShip.class);
        Helper.closeAllFiles();
        
    }
    
    public Game() {
        
    }
    
    public void simpleGame(Class<? extends Ship> playerClass, Class<? extends Ship> enemyClass) {
        try {
            Arena arena = new Arena(10, 10);
            Ship player = playerClass.newInstance();
            arena.spawnShip(5, 5, player);
            List<Ship> enemies = new ArrayList<Ship>();
            for (int n = 0; n < 5; n++) {
                Ship enemy = enemyClass.newInstance();
                arena.spawnShip(n, 0, enemy);
                enemies.add(enemy);
            }
            Objective objective = new Objective() {
                
                @Override
                public String getObjective() {
                    return "Mission Objective: Sink at least three enemy ships.";
                }
                
                @Override
                public boolean isMet(Arena gameArena) {
                    int enemiesSunk = 0;
                    for (Ship enemyShip : enemies) {
                        if (enemyShip.isSunk()) {
                            enemiesSunk++;
                        }
                    }
                    return enemiesSunk >= 3;
                }

            };
            run(arena, objective);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void run(Arena arena, Objective objective) {
        System.out.println(objective.getObjective());
        Helper.writeFileLine(ARENA_FILE, "Initial Map");
        Helper.writeFileLine(ARENA_FILE, arena.getArenaAsText());
        boolean success = false;
        int t = 0;
        while (t < MAX_TURNS) {
            if (t % 10 == 0) {
                System.out.println("Turn " + t);
            }
            List<Ship> ships = arena.sortShipsByPriority();
            for (Ship ship : ships) {
                ship.doTurn(arena);
            }
            Helper.writeFileLine(ARENA_FILE, "After T = " + t);
            Helper.writeFileLine(ARENA_FILE, arena.getArenaAsText());
            if (objective.isMet(arena)) {
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
    }
    
    public class Objective {
        
        public Objective() {
            
        }
        
        public String getObjective() {
            return "Game Mode: Free play.";
        }
        
        public boolean isMet(Arena arena) {
            return false;
        }
        
    }
    
}