# Battleship

## Getting Started
The entire setup guide is [here](https://illinoistechesi/github.io/battleshipsetup).

## API
These are the functions you can use when developing your own ships.

# Ship Class

## Methods for setting the properties of your ship

### initializeName()
...
- Returns: ...

### initializeOwner()
...
- Returns: ...

### initializeHull()
...
- Returns: ...

### initializeFirepower()
...
- Returns: ...

### initializeSpeed()
...
- Returns: ...

### initializeRange()
...
- Returns: ...

## Methods that can only be called by your own ship

### doTurn()
Determines what a ship does on each turn.
- Returns: void

### move(Arena arena, Direction direction)
Move ship in a given direction if remaining moves available.
- Arena arena: arena for the game
- Direction direction: direction to move in
- Returns: void

### fire(Arena arena, int x, int y)
Fire at given coordinates if remaining shots available.
- Arena arena: arena for the game
- int x: x coordinate to fire at
- int y: y coordinate to fire at
- Returns: void

### getCoord()
Get the coordinate of your ship.
- Returns: Coord

**Example of getting x and y from Coord object:**
```
Coord coord = this.getCoord();
System.out.println("X: " + coord.getX());
System.out.println("Y: " + coord.getY());
```

### getShipCoord(Arena arena, Ship ship)
Get the coordinates of another ship if it is within the range of your ship.
- Ship ship: another ship
- Returns: ...

### getNearbyShips(Arena arena)
...
- Returns: ...

## Methods that can be used on any ship

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
...
- Returns: ...

### getOwner()
...
- Returns: ...

### getHealth()
...
- Returns: ...

### getHull()
...
- Returns: ...

### getFirepower()
...
- Returns: ...

### getSpeed()
...
- Returns: ...

### getRange()
...
- Returns: ...

## Arena Class

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




