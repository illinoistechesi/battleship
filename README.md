# Battleship

## Instructions

First, download the code:
```
$ git clone https://github.com/illinoistechesi/battleship.git
$ cd battleship
```

Then, in the sidebar of Cloud9, find the `/combatants/CustomShip.java` file and copy it in the same folder.

Rename it to be `YourLastNameShip.java` (ex `HerreraShip.java`).

You will also need to change lines 6, 9, 11, and 12 to your new ship class name.

Then, open the `/core/Game.java` file and, in line 23, change DeltaShip to your ship class name.

Make sure your terminal is in the /battleship folder. Then you can compile and run the game like this:
```
$ javac core/*.java
$ java core.Game
```

Maps of the game play over time will print to `/files/arena.txt`.

If you run the game with the "descriptive" flag `-d`, then it will print your ships moves to `/files/log.txt`.
```
$ java core.Game -d
```