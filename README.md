# Battleship

## Instructions

First, download the code:
```
$ git clone https://github.com/illinoistechesi/battleship.git
$ cd battleship
```

Then, in the sidebar of Cloud9, find the `/ships/CustomShip.java` file and copy it in the same folder.

Rename it to be `YourLastNameShip.java` (ex `HerreraShip.java`).

You will also need to change lines 6, 9, 11, and 12 to your new ship class name.

Navigate to the `/battleship` folder. Then you can compile and run the game like this:
```
$ javac games/*.java
$ java games.MissionRunner 1 ships.YourLastNameShip
```

The first argument after `games.MissionRunner` is the number of the mission you want to attempt. The second argument is the full class name of the ship you want to play as.

Maps of the game play over time will print to `/files/arena.txt`. Your ship's moves will print to `/files/log.txt`.