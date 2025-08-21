#!/bin/bash
echo "🎮 Chess Engine Demo"
echo "Compiling chess engine..."
cd ../src/main/java
javac *.java
echo "Starting chess game..."
java ChessGame
