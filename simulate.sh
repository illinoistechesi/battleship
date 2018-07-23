#!/bin/bash
javac esi18/*/*.java
javac -cp ".:lib/*" DavyJonesLocker.java
java -cp ".:lib/*" DavyJonesLocker
mv results.json files/results.json