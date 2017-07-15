package ships;

// BetaShip will be design as a speedy/scouting ship
public class BetaShip extends Ship {
    
    private String NAME = "Beta Ship";
    private String OWNER = "Nick";
    private int HULL = 2;
    private int FIREPOWER = 1;
    private int SPEED = 3;
    private int RANGE = 4;

    public BetaShip() {
        this.initializeName(NAME);
        this.initializeOwner(OWNER);
        this.initializeHull(HULL);
        this.initializeFirepower(FIREPOWER);
        this.initializeSpeed(SPEED);
        this.initializeRange(RANGE);
    }
    
    @Override
    public void doTurn(Arena arena) {
        
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
}