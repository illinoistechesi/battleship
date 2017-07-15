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

Then, open the `/games/Mission01.java` file and, in line 10, change DeltaShip to your ship class name.

Navigate to the `/battleship` folder. Then you can compile and run the game like this:
```
$ javac games/*.java
$ java games.Mission01
```

Maps of the game play over time will print to `/files/arena.txt`.

If you run the game with the "descriptive" flag `-d`, then it will print your ships moves to `/files/log.txt`.
```
$ java games.Mission01 -d
```