package core;
import combatants.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    
    public static final int MAX_TURNS = 50;
    public static final String ARENA_FILE = "files/arena.txt";
    public static boolean DEBUG_MODE = false;
    public static String DEBUG_FILE = "files/log.txt"; 
    
    public static void main(String[] args) {
    
        if (args.length >= 1) {
            if (args[0].equals("-d")) {
                DEBUG_MODE = true;   
            }
        }
    
        Game game = new Game();
        Class<? extends Ship> player = NickShip.class;
        Class<? extends Ship> enemy = DummyShip.class;
        game.simpleGame(player, enemy);
        Helper.closeAllFiles();
        
    }
    
    public Game() {
        
    }
    
    public void simpleGame(Class<? extends Ship> playerClass, Class<? extends Ship> enemyClass) {
        try {
            
            Arena arena = new Arena(10, 10);
            arena.setSeed(42);
            
            int[] playerSpawn = {5, 5};
            Ship player = playerClass.newInstance();
            arena.spawnShip(playerSpawn[0], playerSpawn[1], player);
            
            int[][] enemySpawns = {
                {4, 0},
                {5, 0},
                {3, 1},
                {6, 1}
            };
            List<Ship> enemies = new ArrayList<Ship>();
            for (int n = 0; n < enemySpawns.length; n++) {
                int[] enemySpawn = enemySpawns[n];
                Ship enemy = enemyClass.newInstance();
                arena.spawnShip(enemySpawn[0], enemySpawn[1], enemy);
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
                
                @Override
                public String getResults(Arena arena) {
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
                        res += "- Sunk " + ship + " at " + ship.getCoord() + ".";
                    }
                    return res;
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
        arena.setDebugMode(DEBUG_MODE, DEBUG_FILE);
        boolean success = false;
        int t = 0;
        while (t < MAX_TURNS) {
            Helper.writeFileLine(DEBUG_FILE, "Turn " + t);
            if (t % 10 == 0) {
                System.out.println("Turn " + t);
            }
            List<Ship> ships = arena.sortShipsByPriority();
            for (Ship ship : ships) {
                ship.initializeTurn();
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
        System.out.println(objective.getResults(arena));
    }
    
}