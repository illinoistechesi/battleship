package core;
import samples.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    
    public static void main(String[] args) {
    
        Game game = new Game();
        game.simpleGame(DeltaShip.class, DeltaShip.class);
        
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
        System.out.println("Initial Map");
        arena.printArena();
        for (int t = 0; t < 5; t++) {
            List<Ship> ships = arena.sortShipsByPriority(this);
            for (Ship ship : ships) {
                ship.doTurn(arena);
            }
            System.out.println("After T = " + t);
            arena.printArena();
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