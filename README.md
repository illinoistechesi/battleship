# Battleship

## Getting Started
The entire setup guide is [here](https://illinoistechesi.github.io/battleshipsetup).

## API
These are the functions you can use when developing your own ships.<br>

Table of contents

**[Ship](#ship-class)**<br>
**[Arena](#arena-class)**<br>
**[Coordinate](#coord-class)**<br>

## Ship Class

### Methods for setting the properties of your ship
These methods must be called in the constructor of the ship class
``` java
public class SampleShip extends Ship {
    public SampleShip() { // <- called once when a new object is instantiated(created)
        // Constructor method 
    }
}
```

#### initializeName(String name)

- String name: String name of your ship
- Returns: void

#### initializeOwner(String owner)
- String owner: String name of the person who created the ship
- Returns: void

#### initializeHull(int hull)
- int hull: The amount of points dedicated to hull points, the number of shots the ships can sustain
- Returns: void

#### initializeFirepower(int firepower)
- int firepower: The amount of points dedicated to firepower, the number of shots the ship can make in one turn
- Returns: void

#### initializeSpeed(int speed)
- int speed: The amount of points dedicated to speed, the number of (NORTH, SOUTH, WEST, EAST) movement a ship can make in one turn
- Returns: void

#### initializeRange(int range)
- int range: The amount of points dedicated to range, the firing and sight range of the ship (in a squared radius)
- Returns: void
 

**Example Usage of methods above**
``` java
// examples usage, place in constructor method
this.initializeName("Boaty McBoatFace");
this.initializeOwner("Nick");
// There is a 10 point limit to distribute among the below methods
this.initializeHull(2);
this.initializeFirepower(2);
this.initializeSpeed(3);
this.initializeRange(3);
```

### Methods that can only be called by your own ship

#### doTurn(Arena arena);
Determines what a ship does on each turn, the code put in here will execute sequentially which means the same instruction is read each turn. But different actions can be taken based on the inputs of the battlefield by using the Arena object, check out the [Arena Class](#arena-class) and search for if-statements.

- Arena arena: Arena object used to get battlefield information
- Returns: void

``` java
@override
public void doTurn(Arena arena) {
    // instruction for ship behavior should be placed here
    // where it will be read/executed sequentially each turn
}
```

#### move(Arena arena, Direction direction)
Move ship in a given direction if remaining moves available and the spot is not taken by another ship or considered outside of the game boundary.
- Arena arena: arena for the game, that can be used to get more information
- Direction direction: direction to move in 
- Returns: void

``` java
// Move North 3 spaces, then move East one space
for (int d = 0; d < 3; d++) {
    this.move(arena, Direction.NORTH);
}
this.move(arena, Direction.EAST);
// check for remaining moves, if there are more, move south one space
if (this.getRemainingMoves() > 0) {
    this.move(arena, Direction.SOUTH);
}

```

#### fire(Arena arena, int x, int y)
Fire at given coordinates if remaining shots available.
- Arena arena: arena for the game
- int x: x coordinate to fire at
- int y: y coordinate to fire at
- Returns: void

``` java
// Fire at the coordinate (1, 3)
this.fire(arena, 1, 3);
// Fire at a ship that is in range of your ship
Coord location = ship.getCoord();
int x = location.getX();
int y = location.getY();
this.fire(arena, x, y);
```

#### getShipCoord(Arena arena, Ship ship)
Get the coordinates of another ship if it is within the range of your ship.
- **Note:** You do not have to use this method because you can now get the coordinates of any ship, even if it is outside the range of your ship.
- Arena arena: takes an arena object
- Ship ship: another ship
- Returns: Coord


#### getNearbyShips(Arena arena)
Returns a list of ships (exclusing your own) that are within the range of your ship.
- Arena arena: takes an arena object
- Returns: List<Ship>

> Q: "What do the angle brackets mean?"

> A: These are called "generics." You can use Lists to store any kind of object. So the way to read `List<Ship>` out loud is: "a List of Ship objects."

**Example Usage:*
``` java
// Loop over all nearby ships
List<Ship> nearby = this.getNearbyShips(arena);
for (Ship ship : nearby) {
    System.out.println("One nearby ship has " + ship.getHealth() + " HP left.");
}
```
``` java
// Get the first ship in the list, if there are that many
Ship first = nearby.get(0);
// Get the 4th ship in the list, if there are that many
Ship fourth = nearby.get(3);
```
``` java
// Get the number of items in a list
int count = nearby.size();
System.out.println("There are " + count + " ships near me.");
```

### Methods that can be used on any ship

#### getCoord()
Get the coordinate of your ship, check out the Coord class for more information.
- **Note:** You can now get the coordinates of any ship, even if it is outside the range of your ship.
- Returns: Coord

**Example of getting x and y from Coord object**
``` java
Coord coord = this.getCoord():
int x = coord.getX();
int y = coord.getY();
System.out.println("My ship is at (" + x + ", " + y + ").");
```

#### isSameTeamAs(Ship other)
Check if another ship is on the same team as the ship calling this method.
- Ship other: another ship to compare
- Returns: boolean

**Example of checking if a ship is on your team:**
``` java
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
``` java
Ship first = nearby.get(0);
Ship second = nearby.get(1);
if (first.isSameTeamAs(second)) {
    System.out.println("These two ships are conspiring with each other.");
} else {
    System.out.println("These two ships are not friends with each other.");
}
```

#### getRemainingMoves()
Get the number of moves a ship has left on this turn.
- Returns: int

#### getRemainingShots()
Get the number of shots a ship has left on this turn.
- Returns: int

#### isSunk()
Get whether or not the ship has sunk
- Returns: boolean

#### getName()
Get the name of a ship.
- Returns: String

#### getOwner()
Get the name of the owner of a ship.
- Returns: String

#### getHealth()
Get the number of hits a ship has remaining before it sinks.
- Returns: int

#### getHull()
Get the hull strength of a ship.
- Returns: int

#### getFirepower()
Get the firepower of a ship.
- Returns: int

#### getSpeed()
Get the speed of a ship.
- Returns: int

#### getRange()
Get the range of a ship.
- Returns: int
 
**Example Usage:**
``` java
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

#### getAllShips()
Returns a list of ships in the arena that have not yet sunk.
- Returns: List<Ship>

#### getAllSpawnedShips()
Returns a list of ships, whether or not they have sunk.
- Returns: List<Ship>

#### isInRange(Ship a, Ship b)
Determines if ship b is within the range of ship a.
- Ship a: ship to check range from
- Ship b: ship to check for
- Returns: boolean

#### getXSize()
Get horizontal size of arena.
- Returns: int

#### getYSize()
Get vertical size of arena.
- Returns: int

**Example of map size usage**
``` java
Coord myShipLocation = this.getCoord();
// if current x location plus one is equal to the size of the map
// then we have reached the EAST edge of the map
if (myShipLocation.getX() + 1 == arena.getXSize()) {
    this.move(arena, Direction.WEST);
}
// similar example but uses y location and a variable to store the values
int shipYLocation = myShipLocation.getY();
// if current y location minus one is equal to zero
// then we have reached the NORTH edge of the map
if (shipYLocation - 1 == 0) {
    this.move(arena, Direction.SOUTH);
}
```

#### getTurn()
Get the number of this turn.
- Returns: int

#### countLiveShips()
Get the number of ships that are currently alive in the arena.
- Returns: int

#### getRandom()
Get the random object for the arena. Do not create your own random objects, use this object for any randomness.
- Returns: Random

**Examples of common random methods:**
``` java
// Get a random integer between 0 (inclusive) and 4 (exclusive)
int i = arena.getRandom().nextInt(4);
```
``` java
// Get a random double between 0.0 and 1
double d = arena.getRandom().nextDouble();
```
``` java
// Get a random object from a list
List<Ship> list = this.getNearbyShips(arena);
int index = arena.getRandom().nextInt(list.size());
Ship r = list.get(index);
```

## Coord Class

#### public int getX() 
gets the x location of a coordinate object
- returns: int 
    
#### public int getY() 
gets the y location of a coordinate object
- returns: int



**Full Example of a simple ship**
``` java
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
        int randomNumber = arena.getRandom().nextInt(4)
        
        // get a random movement by using the random number to access one possibleMovement 
        this.move(arena, possibleMovement[randomNumber]);
    }
    
    // ship using this instruction will fire at location (x: 0, y: 0) each turn
    this.fire(arena, 0, 0);
}
```
