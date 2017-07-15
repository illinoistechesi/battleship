package combatants;
import core.*;
import java.util.List;

/*
 * Custom Ship
 * @author Your Name
 */
public class CustomShip extends Ship {
    
    public CustomShip() {
        this.initializeName("Custom Ship");
        this.initializeOwner("Your Name");
        this.initializeHull(1);
        this.initializeFirepower(1);
        this.initializeSpeed(2);
        this.initializeRange(1);
    }
    
    /*
     * Determines what actions the ship will take on a given turn
     * @param arena (Arena) the battlefield for the match
     * @return void
     */
    @Override
    public void doTurn(Arena arena) {
        // Fill in your strategy here
        int goalX = 1;
        int goalY = 1;
        
        Coord shipLocation = this.getSelfCoord(arena);
        int shipX = shipLocation.getX();
        int shipY = shipLocation.getY();
        
        arena.move(this, Direction.WEST); // 
        arena.move(this, Direction.NORTH); //
        
        
        // arena.move(this, Direction.SOUTH);
        // arena.move(this, Direction.WEST);
        // arena.move(this, Direction.EAST);
    }
    
}
 /* Accessible Information
    
        Arena
            // commonly used
            public void move(Ship self, Direction dir)
            
            public void fire(Ship self, int x, int y) 
            
            public List<Ship> getNearbyEnemies(Ship self) 
        
            public boolean isInRange(Ship self, Ship target) 
            
        Ship
            public boolean isSunk()
            
            public Ship getSunkBy()
            public Coord getSunkAt()
            public String getName()
            public String getOwner()
            
            public int getHealth()
            
            public int getHull()
            public int getFirepower()
            public int getSpeed()
            public int getRange()
            
            // Commonly used
            Coord getSelfCoord(Arena arena)
            
            Coord getShipCoord(Arena arena, Ship target)
            
        Direction
            NORTH, SOUTH, WEST, EAST
        
        Coord
            public int getX()
            public int getY()
            public String toString()
            
        Grid
            public boolean isInBounds(int x, int y)
            public int getXSize()
            public int getYSize()
            
    */