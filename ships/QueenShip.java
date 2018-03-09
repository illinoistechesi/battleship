package battleship.ships;
import battleship.core.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

/*
 * Queen Ship
 * @author Vinesh
 */
public class QueenShip extends Ship {
    
    private boolean inSuicideProtocol = false;
    
    public QueenShip() {
        this.initializeName("Queen Ship");
        this.initializeOwner("The Evil Fleet");
        this.initializeHull(3);
        this.initializeFirepower(4);
        this.initializeSpeed(0);
        this.initializeRange(3);
    }
    
    /*
     * Determines what actions the ship will take on a given turn
     * @param arena (Arena) the battlefield for the match
     * @return void
     */
    @Override
    protected void doTurn(Arena arena) {
        if (arena.getTurn() < 2 && this.inSuicideProtocol) {
            for (int s = 0; s < this.getHealth(); s++) {
                Coord self = this.getCoord();
                this.fire(arena, self.getX(), self.getY());
            }
        } else {
            for (Ship ship : QueenShip.getAllEnemyShips(arena, this)) {
                boolean canFire = this.getRemainingShots() > 0;
                boolean inRange = arena.isInRange(this, ship);
                if (canFire && inRange) {
                    boolean targetIsAlive = !ship.isSunk();
                    Coord coord = ship.getCoord();
                    while (canFire && targetIsAlive) {
                        this.fire(arena, coord.getX(), coord.getY());
                        canFire = this.getRemainingShots() > 0;
                        targetIsAlive = !ship.isSunk();
                    }
                }
            }
        }
    }
    
    public Ship getNextTarget(Arena arena, Ship servant) {
        Ship target = null;
        List<Ship> options = new ArrayList<Ship>();
        for (Ship ship : QueenShip.getAllEnemyShips(arena, servant)) {
            int dist = QueenShip.getArenaDistanceBetween(servant, ship);
            int reach = servant.getRemainingMoves() + servant.getRange();
            boolean canReach = reach >= dist;
            if (canReach) {
                options.add(ship);
            }
        }
        Collections.sort(options, new Comparator<Ship>() {
            public int compare(Ship s1, Ship s2) {
                int comparison = 0;
                int d1 = HiveShip.getArenaDistanceBetween(servant, s1);
                int d2 = HiveShip.getArenaDistanceBetween(servant, s2);
                int diff = d1 - d2;
                if (diff == 0) {
                    int h1 = s1.getHealth();
                    int h2 = s2.getHealth();
                    comparison = h1 - h2;
                } else {
                    comparison = diff;
                }
                return comparison;
            } 
        });
        if (options.size() > 0) {
            target = options.get(0);
        }
        return target;
    }
    
    public Ship getNextEnemy(Arena arena, Ship servant) {
        Ship nextEnemy = null;
        List<Ship> ships = QueenShip.getAllEnemyShips(arena, servant);
        Collections.sort(ships, new Comparator<Ship>() {
            public int compare(Ship s1, Ship s2) {
                int comparison = 0;
                int d1 = QueenShip.getArenaDistanceBetween(servant, s1);
                int d2 = QueenShip.getArenaDistanceBetween(servant, s2);
                int diff = d1 - d2;
                comparison = diff;
                return comparison;
            } 
        });
        if (ships.size() > 0) {
            nextEnemy = ships.get(0);
        }
        return nextEnemy;
    }
    
    public static List<Ship> getAllEnemyShips(Arena arena, Ship servant) {
        List<Ship> res = new ArrayList<Ship>();
        for (Ship ship : arena.getAllShips()) {
            if (!ship.isSameTeamAs(servant) && !ship.isSunk()) {
                res.add(ship);
            }
        }
        return res;
    }
    
    public static int getArenaDistanceBetween(Ship a, Ship b) {
        Coord start = a.getCoord();
        Coord end = b.getCoord();
        int xDiff = Math.abs(end.getX() - start.getX());
        int yDiff = Math.abs(end.getY() - start.getY());
        return xDiff + yDiff - 1;
    }
    
}