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
            for (int n = 0; n < 5; n++) {
                Ship enemy = enemyClass.newInstance();
                arena.spawnShip(n, 0, enemy);
            }
            run(arena);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void run(Arena arena) {
        Helper.writeFileLine(ARENA_FILE, "Initial Map");
        Helper.writeFileLine(ARENA_FILE, arena.getArenaAsText());
        for (int t = 0; t < MAX_TURNS; t++) {
            List<Ship> ships = arena.sortShipsByPriority(this);
            for (Ship ship : ships) {
                ship.doTurn(arena);
            }
            Helper.writeFileLine(ARENA_FILE, "After T = " + t);
            Helper.writeFileLine(ARENA_FILE, arena.getArenaAsText());
        }
    }
    
    private Random random = new Random();
    
    public Random getRandom() {
        return this.random;
    }
    
    public void setSeed(int seed) {
        this.getRandom().setSeed(seed);
    }
    
}