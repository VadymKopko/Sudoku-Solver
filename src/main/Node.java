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

    private int BoxID;  // Refers to an according box(3x3) position in a grid
    private int data;  // A set value
    private Node north, east, south, west;  // Links between Nodes
    private boolean[] possible = new boolean[SIZE+1];  // An array of possible numbers for the Node

    // Class constructor
    public Node(int BoxID){
        this.BoxID = BoxID;
        this.data = 0;
        this.north = null;
        this.east = null;
        this.south = null;
        this.west = null;
        // Initially all numbers are a possibility, excluding 0
        possible[0] = false;
        for(int i = 1; i <= SIZE; i++) {possible[i] = true;}
    }

    // Getters and Setters:

    public int getNumOfPoss(){

        int numOfPoss = 0;

        // Checks for each possible number
        for(int i = 1; i < possible.length; i++) {
            if (this.possible[i]) {
                numOfPoss++;
            }
        }

        return numOfPoss;
    }

    public int getBoxID() {
        return BoxID;
    }

    public void setBoxID(int BoxID) {
        this.BoxID = BoxID;
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
    public void setImpossible(int num){
        this.possible[num] = false;
    }

    // Get possibilities
    public boolean[] getPossible(){
        return this.possible;
    }

    // Display Node possibilities
    public void displayPossible(){
        boolean atLeastOne = false;
        for(int i = 0; i <= SIZE; i++) {
            if(possible[i]) {
                System.out.print(i + " ");
                atLeastOne = true;
            }
        }
        if(!atLeastOne)
            System.out.print("SOLVED");
        System.out.print("\n");
    }

    // Runs through all Nodes possibilities,
    // Returns boolean :
    //      true - if it is solved with, aka only one possibility
    //      false - if more than one possibility for solving or already solved
    public boolean solveNode(){
        // Check if not solved already
        if(this.data == 0){

            int numOfPoss = 0; // Number of possibilities for the node to be solved
            int solved = 0; // Number that is a possible solution

            // Checks for each possible number
            for(int i = 1; i < possible.length; i++){
                if(this.possible[i]){
                    numOfPoss++;
                    solved = i;
                }
            }

            // If only one possibility found plugs in the number, aka this Node is solved
            if(numOfPoss == 1){
                this.data = solved;
                return true;
            }
        }

        // Returns
        return false;
    }


}
