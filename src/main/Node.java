/*
    Main objective of the class ("Node") is to simulate a single box for a number in a board grid.

    Author: Vadym Kopko
    Version: 1.0.0
 */
package main;

public class Node {

    // Constants:

    final int SIZE = 9;  // A maximum possible number in Sudoku.

    // Initialization of private variables:

    private int id;  // Refers to an according position in a list
    private int data;  // A set value
    private Node north, east, south, west;  // Links between Nodes
    private boolean[] possible = new boolean[SIZE];  // An array of possible numbers for the Node

    // Class constructor
    public Node(int id){
        this.id = id;
        this.data = 0;
        this.north = null;
        this.east = null;
        this.south = null;
        this.west = null;
        // Initially all numbers are a possibility, excluding 0
        possible[0] = false;
        for(int i = 1; i < SIZE; i++) {possible[i] = true;}
    }

    // Getters and Setters:

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getNorth() {
        return north;
    }

    public void setNorth(Node north) {
        this.north = north;
    }

    public Node getEast() {
        return east;
    }

    public void setEast(Node east) {
        this.east = east;
    }

    public Node getSouth() {
        return south;
    }

    public void setSouth(Node south) {
        this.south = south;
    }

    public Node getWest() {
        return west;
    }

    public void setWest(Node west) {
        this.west = west;
    }

    // Set a number to impossible
    public void setImposible(int id){
        this.possible[id] = false;
    }

    // Get possibilities
    public boolean[] getPossible(){
        return this.possible;
    }

}
