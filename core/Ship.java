package core;

public abstract class Ship {
    
    public static final int LIMIT = 10;
    
    // Ships information
    private String name;
    private String owner;
    private Coord coord;
    private int health;
    
    // Ships inital properties
    private int hull;
    private int firepower;
    private int speed;
    private int range;
    
    private int moves;
    private int shots;
    private Ship sunkBy;
    private Coord sunkAt;
    
    public Ship() {
        
    }
    
    public abstract void doTurn(Arena arena);
    
    protected void initializeTurn() {
        this.moves = this.getSpeed();
        this.shots = this.getFirepower();
    }
    
    protected void setCoord(int x, int y) {
        this.coord = new Coord(x, y);
    }
    
    protected void sustainHit() {
        this.health--;
        //System.out.println(this);
        //System.out.println("I was hit!");
    }
    
    public int getRemainingMoves() {
        return this.moves;
    }
    
    public int getRemainingShots() {
        return this.shots;
    }
    
    protected void useMove() {
        this.moves--;
    }
    
    protected void useShot() {
        this.shots--;
    }
    
    private int withdrawPoints(int amount) {
        int pointsUsed = getHull() + getFirepower() + getSpeed() + getRange();
        int pointsRemaining = LIMIT - pointsUsed;
        return Math.min(pointsRemaining, amount);
    }
    
    /**
     * This method will not be used directly outside of the API
     * @param Ship Takes a ship object that sunk the current ship
     */
    protected void recordSinking(Ship attacker) {
        this.sunkBy = attacker;
        this.sunkAt = this.getCoord();
    }
    
    /**
     * @param String Set the ships name
     */
    public void initializeName(String name) {
        this.name = name;
    }
    
    /**
     * @param String Set the ships command/owner
     */
    public void initializeOwner(String owner) {
        this.owner = owner;
    }
    
    /**
     * (hull + firepower + speed + range <= LIMIT)
     * @param int initialize Hull of the ship, must be below the LIMIT when summed up
     */
    public void initializeHull(int hull) {
        this.hull = withdrawPoints(hull);
        this.health = this.hull;
    }
    
    /**
     * (hull + firepower + speed + range <= LIMIT)
     * @param int initialize Firepower of the ship, must be below the LIMIT when summed up
     */
    public void initializeFirepower(int firepower) {
        this.firepower = withdrawPoints(firepower);
    }
    
    /**
     * (hull + firepower + speed + range <= LIMIT)
     * @param int initialize Speed of the ship, must be below the LIMIT when summed up
     */
    public void initializeSpeed(int speed) {
        this.speed = withdrawPoints(speed);
    }
    
    /**
     * (hull + firepower + speed + range <= LIMIT)
     * @param int initialize Range of the ship, must be below the LIMIT when summed up
     */
    public void initializeRange(int range) {
        this.range = withdrawPoints(range);
    }
    

    

    
    //************************//
    //**** Derived Values ****//
    //************************//
    
    /**
     * Derived value based on ship's hull and its damage taken
     * @return boolean Whether or not the ship is sunken
    */
    public boolean isSunk() {
        return this.getHealth() <= 0;
    }
    
    //************************************//
    //***** Start of setters/getters *****//
    //************************************//
    
    /**
     * 
     */
    public Ship getSunkBy() {
        return this.sunkBy;
    }
    
    
    public Coord getSunkAt() {
        return this.sunkAt;
    }
    
    /**
     * @return String Determines the name of the ship, Ex. "Boaty McBoatFace"
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * @return String Determines who commands the ship
     */
    public String getOwner() {
        return this.owner;
    }
    
    /**
     * Object that contains the ship's location
     * @return Coord Object with a x and y variable
    */
    protected Coord getCoord() {
        return this.coord;
    }
    
    Coord getShipCoord(Arena arena, Ship ship) {
        return arena.getShipCoord(this, ship);
    }
    
    Coord getSelfCoord(Arena arena) {
        return arena.getShipCoord(this, this);
    }
    
    /**
     * Derived value based on the ship's hull and its damage taken
     * @return int Value of the ship's remain hull
    */
    public int getHealth() {
        return this.health;
    }
    
    /**
     * Once a ship's hull point reaches zero, the ship has been sunk
     * @return int The amount of health assigned to the ship
     */
    public int getHull() {
        return this.hull;
    }
    
    /**
     * Each shot takes one hull point if it hits, and ships may fire at multiple targets during their turn
     * @return int Determines how much shots a ship can make during their turn
     */
    public int getFirepower() {
        return this.firepower;
    }
    
    /**
     * The ship's speed determines travel distance, and used to decide which ships goes first in a sequential order
     * @return int Determines how far the ship can travel during their turn
     */
    public int getSpeed() {
        return this.speed;
    }
    
    /**
     * Ships firing range is determined in a squared radius, Ex. radius of 3 applies in up, down, left, right, and diagonally.
     * @return int Determines the range in which ships can fire
     */
    public int getRange() {
        return this.range;
    }
    

    
}
