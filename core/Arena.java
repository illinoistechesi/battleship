package battleship.core;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;

public class Arena {
	
	private int turn = 0;
	private List<Ship> ships = new ArrayList<Ship>();
	private Grid<Ship> grid;
	private Random random = new Random();
	private List<Action> actions = new ArrayList<Action>();

	private Ship current;
	
	public Arena(int xSize, int ySize) {
		this.grid = new Grid<Ship>(xSize, ySize);
	}

	protected void setCurrentShip(Ship ship) {
		this.current = ship;
	}
	
	protected void fire(Ship source, int x, int y) {
		if (source == this.current) {
			String detail = "- Fired at (" + x + ", " + y + "), ";
			if (source.getRemainingShots() > 0) {
				source.useShot();
				Ship target = getGrid().get(x, y);
				if (target != null) {
					if (this.isInRange(source, target)) {
						actions.add(new Action(source, getTurn(), x, y)); // Moved save here
						target.sustainHit();
						actions.add(new Action(target, getTurn()));
						if (target.isSunk()) {
							getGrid().set(x, y, null);
							target.recordSinking(source);
							actions.add(new Action(target, getTurn(), source));
							detail += "sunk ship!";
						} else {
							detail += "hit ship, hull strength remaining: ";
							detail += target.getHealth();
						}
					} else {
						detail += "ship was out of range.";
					}
				} else {
					actions.add(new Action(source, getTurn(), x, y)); // Also show these shots
					detail += "could not find a target.";
				}
			} else {
				detail += "no shots remaining on this turn.";
			}
			if (inDebugMode()) {
				Helper.writeFileLine(DEBUG_FILE, detail);
			}
		}
	}
	
	/**
	 * 
	 * @param Ship Object that is moving
	 * @param Direction 
	 */
	protected void move(Ship source, Direction dir) {
		if (source == this.current) {
			String detail = "- Moved " + dir + ", ";
			if (source.getRemainingMoves() > 0) {
				int x = source.getCoord().getX();
				int y = source.getCoord().getY();
				switch (dir) {
					case NORTH:
						y--;
						break;
					case SOUTH:
						y++;
						break;
					case WEST:
						x--;
						break;
					case EAST:
						x++;
						break;
				}
				if (getGrid().get(x, y) == null) {
					boolean success = getGrid().set(x, y, source);
					if (success) {
						int oldX = source.getCoord().getX();
						int oldY = source.getCoord().getY();
						getGrid().set(oldX, oldY, null);
						source.setCoord(x, y);
						source.useMove();
						detail += "now at (" + x + ", " + y+ ").";
					} else {
						detail += "reached edge of the map.";
					}
				} else {
					detail += "but space was occupied by another object.";
				}
			} else {
				detail += "no moves remaining on this turn.";
			}
			if (inDebugMode()) {
				Helper.writeFileLine(DEBUG_FILE, detail);
			}
			actions.add(new Action(source, getTurn(), dir));
		}
	}
	
	/**
	 * This will return a list of enemy ships that the current ship can see (Does not include your own ship)
	 * @param Ship object to reference when looking for nearby enemy ships
	 * @return List<Ship> list of enemy ships
	 */
	//protected List<Ship> getNearbyEnemies(Ship source) {
	protected List<Ship> getNearbyShips(Ship source) {
		List<Ship> res = new ArrayList<Ship>();
		int range = source.getRange();
		int xPos = source.getCoord().getX();
		int yPos = source.getCoord().getY();
		for (int x = xPos - range; x <= xPos + range; x++) {
			for (int y = yPos - range; y <= yPos + range; y++) {
				Ship ship = getGrid().get(x, y);
				if (ship != null) {
					if (!ship.equals(source)) {
						res.add(ship);
					}
				}
			}
		}
		return res;
	}
	
	public boolean isInRange(Ship self, Ship target) {
		Coord st = self.getCoord();
		Coord ct = target.getCoord();
		int range = self.getRange();
		int[] sXRange = {st.getX() - range, st.getX() + range};
		int[] sYRange = {st.getY() - range, st.getY() + range};
		boolean inXRange = ct.getX() >= sXRange[0] && ct.getX() <= sXRange[1];
		boolean inYRange = ct.getY() >= sYRange[0] && ct.getY() <= sYRange[1];
		return inXRange && inYRange;
	}
	
	protected Coord getShipCoord(Ship self, Ship target) {
		Coord res = null;
		if (this.isInRange(self, target)) {
			res = target.getCoord();
		}
		return res;
	}
	
	public int getXSize() {
		return this.getGrid().getXSize();
	}
	
	public int getYSize() {
		return this.getGrid().getYSize();
	}
	
	public Ship getShipAt(int x, int y) {
		return this.getGrid().get(x, y);
	}
	
	private String DEBUG_FILE = null;
	private boolean debugMode = false;
	
	public boolean inDebugMode() {
		return this.debugMode && DEBUG_FILE != null;
	}
	
	public void setDebugMode(boolean mode, String filename) {
		this.debugMode = mode;
		this.DEBUG_FILE = filename;
	}
	
	//*********************************//
	//**** TO BE IMPLEMENTED LATER ****//
	//*********************************//
	
	/*public List<Ship> getNearbyAllies(Ship source) {
		return new ArrayList<Ship>();
	}
	
	public List<Ship> getAllAllies(Ship source) {
		return new ArrayList<Ship>();
	}
	
	public int countEnemies(Ship source) {
		return getAllShips().size();
	}
	
	public int countAllies(Ship source) {
		return 0;
	}*/
	
	public Random getRandom() {
		return this.random;
	}
	
	protected void nextTurn() {
		this.turn++;    
	}
	
	/**
	 * Get the number of the current game turn
	 * @param void
	 * @return int the number of the current turn
	 */
	public int getTurn() {
		return this.turn;
	}
	
	public int countLiveShips() {
		return this.getAllShips().size();
	}
	
	//***************************************************//
	//**** Protected Method not used by custom ships ****//
	//***************************************************//
	
	protected boolean spawnShip(int x, int y, Ship ship) {
		boolean success = false;
		if (getGrid().get(x, y) == null) {
			success = getGrid().set(x, y, ship);
			if (success) {
				ship.setArena(this);
				ship.setCoord(x, y);
				this.ships.add(ship);
			}
		}
		return success;
	}
	
	public List<Ship> getAllShips() {
		List<Ship> res = new ArrayList<Ship>();
		for (Ship ship : getGrid().getAll()) {
			if (ship != null) {
				if (!ship.isSunk()) {
					res.add(ship);
				}
			}
		}
		return res;
	}
	
	public List<Ship> getAllSpawnedShips() {
		return this.ships;
	}
	
	protected List<Ship> sortShipsByPriority() {
		List<Ship> ships = this.getAllShips();
		Map<Ship, Double> priorityMap = new HashMap<Ship, Double>();
		for (Ship ship: ships) {
			double baseSpeed = (double) ship.getSpeed();
			double rand = getRandom().nextDouble();
			double priority = baseSpeed + rand;
			priorityMap.put(ship, priority);
		}
		Collections.sort(ships, new Comparator<Ship>() {
			public int compare(Ship s1, Ship s2) {
				double p1 = priorityMap.get(s1);
				double p2 = priorityMap.get(s2);
				double diff = p2 - p1;
				int comparison = 0;
				if (diff > 0) {
					comparison = 1;
				} else if (diff < 0) {
					comparison = -1;
				}
				return comparison;
			}
		});
		return ships;
	}
	
	protected void setSeed(int seed) {
		this.getRandom().setSeed(seed);
	}
	
	protected List<Action> getActions() {
		return this.actions;
	}
	
	protected Grid<Ship> getGrid() {
		return this.grid;
	}
	
	protected void printArena() {
		for (int y = 0; y < grid.getYSize(); y++) {
			for (int x = 0; x < grid.getXSize(); x++) {
				Ship ship = grid.get(x, y);
				if (ship != null) {
					System.out.print("[S]");
				} else {
					System.out.print("[ ]");
				}
			}
			System.out.print("\n");
		}
	}
	
	protected String getArenaAsText() {
		String out = "";
		out += "    ";
		for (int h = 0; h < grid.getXSize(); h++) {
			out += String.format(" %02d ", h);
		}
		out += "\n";
		for (int y = 0; y < grid.getYSize(); y++) {
			out += String.format(" %02d ", y);
			for (int x = 0; x < grid.getXSize(); x++) {
				Ship ship = grid.get(x, y);
				if (ship != null) {
					String flag = ship.getName().substring(0, 1);
					out += String.format("[%s%d]", flag, ship.getHealth());
				} else {
					out += " ~~ ";
				}
			}
			out += "\n";
		}
		return out;
	}
	
}
