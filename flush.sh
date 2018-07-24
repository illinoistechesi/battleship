#!/bin/bash

# To execute/run this script in the terminal:
# $ ./flush.sh
# If you get Permission Denied, execute this once:
# $ chmod +x flush.sh

rm *.class
rm -rf esi18/*.class
rm -rf files/*.txt

if [ -d "$battleship"]; then
	rm -rf core/*.class
    rm -rf ships/*.class
    rm -rf games/*.class
    rm -rf server/*.class
else
    rm -rf battleship/core/*.class
    rm -rf battleship/ships/*.class
    rm -rf battleship/games/*.class
    rm -rf battleship/server/*.class
fi
