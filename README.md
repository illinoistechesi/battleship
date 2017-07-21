# Battleship

## Getting Started
The entire setup guide is [here](https://illinoistechesi/github.io/battleshipsetup).

## API
These are the functions you can use when developing your own ships.

# Ship Class

## Methods for setting the properties of your ship

### initializeName(String name)

- String name: String name of your ship
- Returns: void

### initializeOwner(String owner)
- String owner: String name of the person who created the ship
- Returns: void

### initializeHull(int hull)
- int hull: The amount of points dedicated to hull points, the number of shots the ships can sustain
- Returns: void

### initializeFirepower(int firepower)
- int firepower: The amount of points dedicated to firepower, the number of shots the ship can make in one turn
- Returns: void

### initializeSpeed(int speed)
- int speed: The amount of points dedicated to speed, the number of (NORTH, SOUTH, WEST, EAST) movement a ship can make in one turn
- Returns: void

### initializeRange(int range)
- int range: The amount of points dedicated to range, the firing and sight range of the ship (in a squared radius)
- Returns: void
 

**Example Usage of methods above**
``` java
// examples usage, place in constructor
this.initializeName("Boaty McBoatFace");
this.initializeOwner("Nick");
// There is a 10 point limit to distribute among the below methods
this.initializeHull(2);
this.initializeFirepower(2);
this.initializeSpeed(3);
this.initializeRange(3);
```

## Methods that can only be called by your own ship

### doTurn(Arena arena);
Determines what a ship does on each turn.
- Arena arena: Arena object that can be used to get more information
- Returns: void

### move(Arena arena, Direction direction)
Move ship in a given direction if remaining moves available.
- Arena arena: arena for the game, that can be used to get more information
- Direction direction: direction to move in
- Returns: void

**Example Usage:**
```
// Move North 3 spaces, then move East one space
for (int d = 0; d < 3; d++) {
    this.move(arena, Direction.NORTH);
}
this.move(arena, Direction.EAST);
```

### fire(Arena arena, int x, int y)
Fire at given coordinates if remaining shots available.
- Arena arena: arena for the game
- int x: x coordinate to fire at
- int y: y coordinate to fire at
- Returns: void

**Example Usage:**
```
// Fire at the coordinate (1, 3)
this.fire(arena, 1, 3);
// Fire at a ship that is in range of your ship
Coord location = ship.getCoord();
int x = location.getX();
int y = location.getY();
this.fire(arena, x, y);
```

### getShipCoord(Arena arena, Ship ship)
Get the coordinates of another ship if it is within the range of your ship.
- **Note:** You do not have to use this method because you can now get the coordinates of any ship, even if it is outside the range of your ship.
- Ship ship: another ship
- Returns: Coord

### getNearbyShips(Arena arena)
Returns a list of ships (exclusing your own) that are within the range of your ship.
- Returns: List<Ship>

> Q: "What do the angle brackets mean?"

> A: These are called "generics." You can use Lists to store any kind of object. So the way to read `List<Ship>` out loud is: "a List of Ship objects."

**Example Usage:*
```
// Loop over all nearby ships
List<Ship> nearby = this.getNearbyShips(arena);
for (Ship ship : nearby) {
    System.out.println("One nearby ship has " + ship.getHealth() + " HP left.");
}
```
```
// Get the first ship in the list, if there are that many
Ship first = nearby.get(0);
// Get the 4th ship in the list, if there are that many
Ship fourth = nearby.get(3);
```
```
// Get the number of items in a list
int count = nearby.size();
System.out.println("There are " + count + " ships near me.");
```

## Methods that can be used on any ship

### getCoord()
Get the coordinate of your ship, check out the Coord class for more information.
- **Note:** You can now get the coordinates of any ship, even if it is outside the range of your ship.
- Returns: Coord

**Example of getting x and y from Coord object**
```
Coord coord = this.getCoord():
int x = coord.getX();
int y = coord.getY();
System.out.println("My ship is at (" + x + ", " + y + ").");
```

### isSameTeamAs(Ship other)
Check if another ship is on the same team as the ship calling this method.
- Ship other: another ship to compare
- Returns: boolean

**Example of checking if a ship is on your team:**
```
List<Ship> nearby = this.getNearbyShips(arena);
for (int i = 0; i < nearby.size(); i++) {
    Ship other = nearby.get(i);
    boolean isOnMyTeam = this.isSameTeamAs(other);
    if (isOnMyTeam) {
        System.out.println("This ship is on my team!");
    } else {
        System.out.println("This ship is an enemy.");
    }
}
```
**Example of checking if two different ships are on the same team as each other:**
```
Ship first = nearby.get(0);
Ship second = nearby.get(1);
if (first.isSameTeamAs(second)) {
    System.out.println("These two ships are conspiring with each other.");
} else {
    System.out.println("These two ships are not friends with each other.");
}
```

### getRemainingMoves()
Get the number of moves a ship has left on this turn.
- Returns: int

### getRemainingShots()
Get the number of shots a ship has left on this turn.
- Returns: int

### isSunk()
Get whether or not the ship has sunk
- Returns: boolean

### getName()
Get the name of a ship.
- Returns: String

### getOwner()
Get the name of the owner of a ship.
- Returns: String

### getHealth()
Get the number of hits a ship has remaining before it sinks.
- Returns: int

### getHull()
Get the hull strength of a ship.
- Returns: int

### getFirepower()
Get the firepower of a ship.
- Returns: int

### getSpeed()
Get the speed of a ship.
- Returns: int

### getRange()
Get the range of a ship.
- Returns: int
 
**Example Usage:**
```
List<Ship> list = this.getNearbyShips(arena);
Ship enemy = list.get(0);
System.out.println("Enemy Ship: " + enemy.getName());
System.out.println("Firepower: " + enemy.getFirepower());

if (enemy.getSpeed() > this.getSpeed()) {
    System.out.println("The enemy ship will move before my ship.");
} else if (enemy.getSpeed() > this.getSpeed()) {
    System.out.println("The enemy ship will move after my ship.");
} else {
    System.out.println("The enemy ship might move before or after my ship.");
}

int hitsTaken = enemy.getHull() - enemy.getHealth();
System.out.println("Enemy has taken " + hitsTaken + " hits.");
```

## Arena Class

### getAllShips()
Returns a list of ships in the arena that have not yet sunk.
- Returns: List<Ship>

### getAllSpawnedShips()
Returns a list of ships, whether or not they have sunk.
- Returns: List<Ship>

### isInRange(Ship a, Ship b)
Determines if ship b is within the range of ship a.
- Ship a: ship to check range from
- Ship b: ship to check for
- Returns: boolean

### getXSize()
Get horizontal size of arena.
- Returns: int

### getYSize()
Get vertical size of arena.
- Returns: int

### getTurn()
Get the number of this turn.
- Returns: int

### countLiveShips()
Get the number of ships that are currently alive in the arena.
- Returns: int

### getRandom()
Get the random object for the arena. Do not create your own random objects, use this object for any randomness.
- Returns: Random

**Examples of common random methods:**
```
// Get a random integer between 0 (inclusive) and 4 (exclusive)
int i = arena.getRandom().nextInt(4);
```
```
// Get a random double between 0.0 and 1
double d = arena.getRandom().nextDouble();
```
```
// Get a random object from a list
List<Ship> list = this.getNearbyShips(arena);
int index = arena.getRandom().nextInt(list.size());
Ship r = list.get(index);
```



**Full Example of a simple ship**
```
// doTurn Example, place in your ship class
@Override
public void doTurn(Arena arena) {
    // your implementation of a ship
    
    // gets the current location of the ship
    Coord coord = this.getCoord();
    int x = coord.getX();
    int y = coord.getY();
    
    if (x > 8) {
        this.move(arena, Direction.WEST);
    }
    else if (x < 2) {
        this.move(arena, Direction.EAST);
    }
    else if (y > 8) {
        this.move(arena, Direction.NORTH);
    }
    else if (y < 2) {
        this.move(arena, Direction.SOUTH);
    }
    else {
        // make a list of all the location, and store it in a variable
        Direction[] possibleMovement = {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
        
        // get a random number and store it in a variable
        Direction randomNumber = arena.getRandom().nextInt(4)
        
        // get a random movement by using the random number to access one possibleMovement 
        this.move(arena, possibleMovement[randomNumber]);
    }
    
    // ship using this instruction will fire at location (x: 0, y: 0) each turn
    this.fire(arena, 0, 0);
}
```