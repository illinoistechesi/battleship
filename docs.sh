#!/bin/bash
rm -rf docs
mkdir docs
cd docs
javadoc ../*/*.java
cd ..