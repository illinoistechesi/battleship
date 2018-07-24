#!/bin/bash

# To execute/run this script in the terminal:
# $ ./simulate.sh
# If you get Permission Denied, execute this once:
# $ chmod +x simulate.sh

javac esi18/*.java
javac -cp ".:lib/*" DavyJonesLocker.java
java -cp ".:lib/*" DavyJonesLocker
mv results.json files/results.json