package battleship.ships;
import battleship.core.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import battleship.ships.QueenShip;
import java.io.PrintStream;

/*
 * Hive Ship
 * @author Vinesh
 */
public class HiveShip extends Ship {
    
    private QueenShip trueQueen;
    
    public HiveShip() {
        this.initializeName("Hive Ship");
        this.initializeOwner("The Evil Fleet");
        this.initializeHull(2);
        this.initializeFirepower(2);
        this.initializeSpeed(2);
        this.initializeRange(4);
    }
    
    /*
     * Determines what actions the ship will take on a given turn
     * @param arena (Arena) the battlefield for the match
     * @return void
     */
    @Override
    protected void doTurn(Arena arena) {
        try {
            QueenShip queen = this.getQueen(arena);
            Ship target = queen.getNextTarget(arena, this);
            while (target != null && this.getRemainingShots() > 0) {
                this.approachTarget(arena, target);
                Coord coord = target.getCoord();
                while (!target.isSunk() && this.getRemainingShots() > 0){
                    this.fire(arena, coord.getX(), coord.getY());
                }
                target = queen.getNextTarget(arena, this);
            }
            if (target == null && this.getRemainingMoves() > 0) {
                Ship nextEnemy = queen.getNextEnemy(arena, this);
                if (nextEnemy != null) {
                    this.approachTarget(arena, nextEnemy);   
                }
            }
        } catch (LostWillToFightException lwtfe) {
            System.out.println(lwtfe);
            for (int s = 0; s < this.getHealth(); s++) {
                Coord self = this.getCoord();
                this.fire(arena, self.getX(), self.getY());
            }
        }
    }
    
    public QueenShip getQueen(Arena arena) throws LostWillToFightException {
        if (this.trueQueen == null) {
            Ship self = this;
            List<Ship> allShips = arena.getAllShips();
            Collections.sort(allShips, new Comparator<Ship>() {
                public int compare(Ship s1, Ship s2) {
                    int comparison = 0;
                    int d1 = QueenShip.getArenaDistanceBetween(self, s1);
                    int d2 = QueenShip.getArenaDistanceBetween(self, s2);
                    int diff = d1 - d2;
                    comparison = diff;
                    return comparison;
                } 
            });
            for (Ship ship : allShips) {
                if (ship instanceof QueenShip) {
                    QueenShip queen = (QueenShip) ship;
                    this.trueQueen = queen;
                    break;
                }
            }
        }
        if (this.trueQueen == null || this.trueQueen.isSunk()) {
            throw new LostWillToFightException("Alas, my queen is gone!");
        }
        return this.trueQueen;
    }
    
    public void approachTarget(Arena arena, Ship target) {
        boolean notCloseEnough = !arena.isInRange(this, target);
        boolean canMove = this.getRemainingMoves() > 0;
        while (notCloseEnough && canMove) {
            Coord start = this.getCoord();
            Coord end = target.getCoord();
            int xDiff = end.getX() - start.getX();
            int yDiff = end.getY() - start.getY();
            int xMag = Math.abs(xDiff);
            int yMag = Math.abs(yDiff);
            Direction xDir = null;
            Direction yDir = null;
            if (xMag > this.getRange()) {
                xDir = xDiff > 0 ? Direction.EAST : Direction.WEST;
            }
            if (yMag > this.getRange()) {
                yDir = yDiff > 0 ? Direction.SOUTH : Direction.NORTH;
            }
            if (xDir != null) {
                boolean xFree = this.canMoveInDirection(arena, start, xDir);
                if (xFree) {
                    this.move(arena, xDir);
                    start = this.getCoord();
                } else {
                    xDir = null;
                }
            }
            if (yDir != null) {
                boolean yFree = this.canMoveInDirection(arena, start, yDir);
                if (yFree) {
                    this.move(arena, yDir);
                    start = this.getCoord();
                } else {
                    yDir = null;
                }
            }
            notCloseEnough = !arena.isInRange(this, target);
            canMove = this.getRemainingMoves() > 0;
            boolean noValidMove = xDir == null && yDir == null;
            if (noValidMove) {
                break;
            }
        }
    }
    
    public boolean canMoveInDirection(Arena arena, Coord start, Direction dir) {
        boolean isFree = false;
        switch (dir) {
            case NORTH:
                isFree = canMoveToSpace(arena, start.getX(), start.getY() - 1);
                break;
            case EAST:
                isFree = canMoveToSpace(arena, start.getX() + 1, start.getY());
                break;
            case SOUTH:
                isFree = canMoveToSpace(arena, start.getX(), start.getY() + 1);
                break;
            case WEST:
                isFree = canMoveToSpace(arena, start.getX() - 1, start.getY());
                break;
            default:
                break;
        }
        return isFree;
    }
    
    public boolean canMoveToSpace(Arena arena, int x, int y) {
        return arena.getShipAt(x, y) == null;
    }
    
    public static int getArenaDistanceBetween(Ship a, Ship b) {
        Coord start = a.getCoord();
        Coord end = b.getCoord();
        int xDiff = Math.abs(end.getX() - start.getX());
        int yDiff = Math.abs(end.getY() - start.getY());
        return xDiff + yDiff - 1;
    }
    
    public static class LostWillToFightException extends Exception {
        
        private String message;
        
        public LostWillToFightException(String message) {
            super(message);
            this.message = message;
        }
        
        public String getMessage() {
            return this.message;
        }
        
        @Override
        public String toString() {
            return "LostWillToFightException: " + this.getMessage();
        }
        
    }
    
}