package battleship.core;
import java.util.List;

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
	
	private String team;

	private Arena arena;
	
	public Ship() {
		
	}
	
	/**
	 * Classes extending this will need to implement this method to program their ship behavior
	 */
	protected abstract void doTurn(Arena arena);
	
	final void initializeTurn() {
		this.moves = this.getSpeed();
		this.shots = this.getFirepower();
	}
	
	final void setCoord(int x, int y) {
		this.coord = new Coord(x, y);
	}
	
	final void sustainHit() {
		this.health--;
		//System.out.println(this);
		//System.out.println("I was hit!");
	}
	
	/**
	 * Moves based on a ships speed property, if remain moves are used up, future moves will be voided until the start of the next turn
	 * @return int The remain points left for ship movement
	 */
	public final int getRemainingMoves() {
		return this.moves;
	}
	
	/**
	 * Number of shots are based on a ships firepower property, if remain shots are used up, future shots will be voided until the start of the next turn
	 * @return int The remain points left for ship to fire
	 */
	public final int getRemainingShots() {
		return this.shots;
	}
	
	final void useMove() {
		this.moves--;
	}
	
	final void useShot() {
		this.shots--;
	}
	
	private final int withdrawPoints(int amount) {
		int pointsUsed = getHull() + getFirepower() + getSpeed() + getRange();
		int pointsRemaining = LIMIT - pointsUsed;
		return Math.max(Math.min(pointsRemaining, amount), 0);
	}

	final void setArena(Arena arena) {
		this.arena = arena;
	}
	
	final void recordSinking(Ship attacker) {
		this.sunkBy = attacker;
		this.sunkAt = this.getCoord();
	}
	
	final void setTeam(String team) {
		this.team = team;
	}
	
	public final String getTeam() {
		return this.team;
	}
	
	/**
	 * @param String Set the ships name
	 */
	protected final void initializeName(String name) {
		this.name = name;
	}
	
	/**
	 * @param String Set the ships command/owner
	 */
	protected final void initializeOwner(String owner) {
		this.owner = owner;
	}
	
	/**
	 * (hull + firepower + speed + range <= LIMIT)
	 * @param int initialize Hull of the ship, must be below the LIMIT when summed up
	 */
	protected final void initializeHull(int hull) {
		this.hull = withdrawPoints(hull);
		this.health = this.hull;
	}
	
	/**
	 * (hull + firepower + speed + range <= LIMIT)
	 * @param int initialize Firepower of the ship, must be below the LIMIT when summed up
	 */
	protected final void initializeFirepower(int firepower) {
		this.firepower = withdrawPoints(firepower);
	}
	
	/**
	 * (hull + firepower + speed + range <= LIMIT)
	 * @param int initialize Speed of the ship, must be below the LIMIT when summed up
	 */
	protected final void initializeSpeed(int speed) {
		this.speed = withdrawPoints(speed);
	}
	
	/**
	 * (hull + firepower + speed + range <= LIMIT)
	 * @param int initialize Range of the ship, must be below the LIMIT when summed up
	 */
	protected final void initializeRange(int range) {
		this.range = withdrawPoints(range);
	}
	
	//************************//
	//**** Derived Values ****//
	//************************//
	
	/**
	 * Derived value based on ship's hull and its damage taken
	 * @return boolean Whether or not the ship is sunken
	*/
	public final boolean isSunk() {
		return this.getHealth() <= 0;
	}
	
	//************************************//
	//***** Start of setters/getters *****//
	//************************************//
	
	/**
	 * 
	 */
	public final Ship getSunkBy() {
		return this.sunkBy;
	}
	
	
	public final Coord getSunkAt() {
		return this.sunkAt;
	}
	
	public final boolean isSameTeamAs(Ship ship) {
		boolean haveTeams = this.getTeam() != null && ship.getTeam() != null;
		boolean sameTeam = false;
		if (haveTeams) {
			sameTeam = this.getTeam().equals(ship.getTeam());
		}
		return haveTeams && sameTeam;
	}
	
	/**
	 * @return String Determines the name of the ship, Ex. "Boaty McBoatFace"
	 */
	public final String getName() {
		return this.name;
	}
	
	/**
	 * @return String Determines who commands the ship
	 */
	public final String getOwner() {
		return this.owner;
	}
	
	/**
	 * Object that contains the ship's location
	 * @return Coord Object with a x and y variable
	*/
	public final Coord getCoord() {
		return this.coord;
	}
	
	protected final Coord getShipCoord(Arena arena, Ship ship) {
		return arena.getShipCoord(this, ship);
	}
	
	/*protected final Coord getSelfCoord(Arena arena) {
		return arena.getShipCoord(this, this);
	}*/
	
	protected final void move(Arena arena, Direction direction) {
		arena.move(this, direction);
	}
	
	protected final void fire(Arena arena, int x, int y) {
		arena.fire(this, x, y);
	}
	
	protected final List<Ship> getNearbyShips(Arena arena) {
		return arena.getNearbyShips(this);
	}

	// Simpler versions of the above methods

	protected final void move(Direction direction) {
		this.arena.move(this, direction);
	}
	
	protected final void fire(int x, int y) {
		this.arena.fire(this, x, y);
	}

	protected final List<Ship> getNearbyShips() {
		return this.arena.getNearbyShips(this);
	}
	
	/**
	 * Derived value based on the ship's hull and its damage taken
	 * @return int Value of the ship's remain hull
	*/
	public final int getHealth() {
		return this.health;
	}
	
	/**
	 * Once a ship's hull point reaches zero, the ship has been sunk
	 * @return int The amount of health assigned to the ship
	 */
	public final int getHull() {
		return this.hull;
	}
	
	/**
	 * Each shot takes one hull point if it hits, and ships may fire at multiple targets during their turn
	 * @return int Determines how much shots a ship can make during their turn
	 */
	public final int getFirepower() {
		return this.firepower;
	}
	
	/**
	 * The ship's speed determines travel distance, and used to decide which ships goes first in a sequential order
	 * @return int Determines how far the ship can travel during their turn
	 */
	public final int getSpeed() {
		return this.speed;
	}
	
	/**
	 * Ships firing range is determined in a squared radius, Ex. radius of 3 applies in up, down, left, right, and diagonally.
	 * @return int Determines the range in which ships can fire
	 */
	public final int getRange() {
		return this.range;
	}
	
}
