package core;

public abstract class Ship {
    
    public static final int LIMIT = 10;
    
    private String name;
    private String owner;
    private int hull;
    private int firepower;
    private int speed;
    private int range;
    private Coord coord;
    private int health;
    
    public Ship() {
        
    }
    
    public abstract void doTurn(Arena arena);
    
    private int withdrawPoints(int amount) {
        int pointsUsed = getHull() + getFirepower() + getSpeed() + getRange();
        int pointsRemaining = LIMIT - pointsUsed;
        return Math.min(pointsRemaining, amount);
    }
    
    public void initializeName(String name) {
        
    }

    public void initializeOwner(String owner) {
        
    }
    
    public void initializeHull(int hull) {
        this.hull = withdrawPoints(hull);
    }
    
    public void initializeFirepower(int firepower) {
        this.firepower = withdrawPoints(firepower);
    }
    
    public void initializeSpeed(int speed) {
        this.speed = withdrawPoints(speed);
    }
    
    public void initializeRange(int range) {
        this.range = withdrawPoints(range);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getOwner() {
        return this.owner;
    }
    
    public int getHull() {
        return this.hull;
    }
    
    public int getFirepower() {
        return this.firepower;
    }
    
    public int getSpeed() {
        return this.speed;
    }
    
    public int getRange() {
        return this.range;
    }
    
    public Coord getCoord() {
        return this.coord;
    }
    
    public int getHealth() {
        return this.health;
    }
    
    /**
     * 
    */
    public boolean isSunk() {
        return this.getHealth() > 0;
    }
    
}
