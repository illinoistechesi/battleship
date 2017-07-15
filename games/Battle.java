package games;
import core.*;
import ships.*;
import java.util.*;

public class Battle extends Game {
    
    private List<Class<? extends Ship>> combatants = new ArrayList<Class<? extends Ship>>();
    public static int SEED = 42;
    public static Mode MODE = Mode.ZONE_SPAWN;
    
    public static void main(String[] args) {
        if (args.length >= 1) {
            switch (args[0]) {
                case "random":
                    MODE = Mode.RANDOM_SPAWN;
                    break;
                case "zone":
                    MODE = Mode.ZONE_SPAWN;
                    break;
            }
        }
        if (args.length >= 2) {
            SEED = Integer.parseInt(args[1]);
        }
        Game battle = new Battle();
        battle.setMaxTurns(100);
        battle.setArenaFile("files/battle-arena.txt");
        battle.setTurnFile("files/battle-turns.txt");
        battle.run();
    }
    
    public enum Mode {
        RANDOM_SPAWN,
        ZONE_SPAWN
    }
    
    public Battle() {
        combatants.add(ships.KannanShip.class);
        combatants.add(games.DummyShip.class);
        combatants.add(ships.NickShip.class);
        Arena arena = initializeArena();
        setArena(arena);
    }
    
    @Override
    public String getObjective() {
        return "Battle Mode: Be the last ship standing.";
    }
    
    @Override
    public Arena initializeArena() {
        
        int padding = 2;
        int margin = 1 + (2 * padding);
        int xSize = getCombatants().size() * margin;
        int ySize = margin;
        Arena arena = new Arena(xSize, ySize);
        setArena(arena);
        setSeed(SEED);
        
        try {
            
            int count = 0;
            for (Class<? extends Ship> shipClass : getCombatants()) {
                Ship player = shipClass.newInstance();
                switch (MODE) {
                    case RANDOM_SPAWN:
                        int x = 0;
                        int y = 0;
                        boolean spawned = false;
                        while (!spawned) {
                            x = arena.getRandom().nextInt(10);
                            y = arena.getRandom().nextInt(10);
                            spawned = spawnShip(arena, x, y, player);
                        }
                        break;
                    case ZONE_SPAWN:
                        int center = (count * margin) + padding;
                        spawnShip(arena, center, padding, player);
                        count++;
                        break;
                }
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
        List<Ship> ships = getAllShips(getArena());
        return ships.size() == 1;
    }
    
    @Override
    public String getResults() {
        String res = "Battle Results";
        List<Ship> ships = getAllSpawnedShips(getArena());
        for (Ship ship : ships) {
            if (ship.isSunk()) {
                res += "\n";
                res += "- " + ship + " destroyed by " + ship.getSunkBy() + ".";
            } else {
                res += "\n";
                res += "- " + ship + " is still sailing.";
            }
        }
        return res;
    }

    @Override
    public void run() {
        this.runMission(getArena());
    }
    
    public List<Class<? extends Ship>> getCombatants() {
        return this.combatants;
    }
   
}