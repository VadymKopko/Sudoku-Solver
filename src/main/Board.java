/*
    Main objective of the class ("Board") is to simulate game-board grid.

    Author: Vadym Kopko
    Version: 1.0.0
 */
package main;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Board {

    // Constants:

    final int SIZE = 9;  // A maximum possible number in Sudoku.

    // Initialization of private variables:

    private Node root;  // Initialization for a root node
    private int iterSolve = 0; // Keeps track of each iteration of solve method

    // Class constructor
    public Board()throws IOException {

        // Creating a Node Root
        this.root = new Node(giveBoxID(0,0));

        // Pointers:
        Node rowPointer = root;
        Node colPointer;

        for(int row = 0; row < SIZE; row++){

            // After the first iteration
            if(row != 0){
                // Add and Connect Nodes Horizontally:
                Node newNode = new Node(giveBoxID(row, 0));  // Create a new Node
                rowPointer.setSouth(newNode);  // Connect new Node to current for South
                newNode.setNorth(rowPointer);  // Connect current Node to new for North

                // Move row pointer South
                rowPointer = newNode;
            }

            // Set column point to current row
            colPointer = rowPointer;

            // Create columns in a row
            for(int col = 1; col < SIZE; col++) {
                // Add and Connect Nodes Horizontally
                Node newNode = new Node(giveBoxID(row,col));  // Create a new Node
                colPointer.setEast(newNode);  // Connect new Node to current for East
                newNode.setWest(colPointer);  // Connect current Node to new for West

                // After the first iteration
                if(row != 0){
                    // Connect Nodes Vertically
                    newNode.setNorth(colPointer.getNorth().getEast()); // Connect North node to new for North
                    colPointer.getNorth().getEast().setSouth(newNode);  // Connect new Node to North for South
                }

                // Move column pointer East
                colPointer = newNode;
            }
        }
    }

    // Method for displaying board grid by printing it to the console.
    public void display(boolean showID) {

        System.out.println();

        // Trackers:
        int rowTracker = 1;
        int colTracker = 1;

        // Pointers:
        Node rowPointer = root;
        Node colPointer;

        // Display top line
        displayHLine(25);

        while (rowPointer != null) {

            colPointer = rowPointer;

            System.out.print("| ");

            while (colPointer != null) {

                // For testing unpopulated grid, display ids instead of data inside each Node'
                if(showID)
                    System.out.print(colPointer.getBoxID());
                else
                    System.out.print(colPointer.getData());

                // Display Sudoku Grid every 3 iterations
                if (colTracker % 3 == 0)
                    System.out.print(" | ");
                else
                    System.out.print(" ");

                // Move column pointer East
                colPointer = colPointer.getEast();
                colTracker++;
            }

            System.out.println();

            // Display Sudoku Grid every 3 iterations
            if (rowTracker % 3 == 0){
                displayHLine(25);
            }

            // Move column pointer East
            rowPointer = rowPointer.getSouth();
            rowTracker++;
        }
    }

    // Method for loading the data for board from a file
    public void populateFromFile(String filepath) throws IOException {

        // Create file and file scanner
        File file =  new File(filepath);
        Scanner input = new Scanner(file);

        // Pointers:
        Node rowPointer = root;
        Node colPointer;

        while(rowPointer != null){

            colPointer = rowPointer;

            while(colPointer != null){
                // Populate with data
                colPointer.setData(input.nextInt());

                // Move column pointer East
                colPointer = colPointer.getEast();
            }
            // Move row pointer South
            rowPointer = rowPointer.getSouth();
        }

        // Finish scanning the file
        input.close();
    }

    // Method of elimination
    private void eliminate(){

        // Pointers:
        Node rowPointer = root;
        Node colPointer;

        while(rowPointer != null){

            colPointer = rowPointer;

            while (colPointer != null){

                // Receive data for a Node
                int cell = colPointer.getData();

                // When number is already known delete possibilities
                if(cell != 0){

                    Node cellPointer = colPointer;
                    int boxID = cellPointer.getBoxID();

                    // Set possibilities o this number to false, aka solved
                    for(int i = 0; i < SIZE; i++)
                        colPointer.setImposible(i);

                    // Eliminate possibility of this number horizontally
                    cellPointer = colPointer;
                    // Move East
                    while(cellPointer != null){
                        cellPointer.setImposible(cell);
                        cellPointer = cellPointer.getEast();
                    }
                    cellPointer = colPointer;
                    // Move West
                    while(cellPointer != null){
                        cellPointer.setImposible(cell);
                        cellPointer = cellPointer.getWest();
                    }

                    // Eliminate possibility of this number vertically
                    cellPointer = colPointer;
                    // Move North
                    while(cellPointer != null){
                        cellPointer.setImposible(cell);
                        cellPointer = cellPointer.getNorth();
                    }
                    cellPointer = colPointer;
                    // Move South
                    while(cellPointer != null){
                        cellPointer.setImposible(cell);
                        cellPointer = cellPointer.getSouth();
                    }

                    // Eliminate possibilities within the same box
                    eliminateBox(boxID, cell);
                }

                // Move column pointer East
                colPointer = colPointer.getEast();
            }
            // Move row pointer South
            rowPointer = rowPointer.getSouth();
        }
    }

    // Eliminate possibilities within a box
    private void eliminateBox(int boxID, int num) {

        // Pointers:
        Node rowPointer = root;
        Node colPointer;

        while (rowPointer != null) {

            colPointer = rowPointer;

            while (colPointer != null) {

                // In case where it is the right box, eliminate possibility of num
                if (colPointer.getBoxID() == boxID)
                    colPointer.setImposible(num);

                // Move column pointer East
                colPointer = colPointer.getEast();
            }
            // Move row pointer South
            rowPointer = rowPointer.getSouth();

        }
    }

    // Keeps track of Identifications for Nodes. Returns: ID.
    private int giveBoxID(int row, int col){

        // Staring with a zero
        int boxID = 0;

        boxID += 3 * (row / 3);
        boxID += (col / 3);

        return boxID;
    }

    // Display horizontal line
    private void displayHLine(int size){
        for(int x = 0; x < size; x++){
            System.out.print("-");
        }
        System.out.print("\n");
    }

    // Display each node possibilities
    private void displayPossibilities(){


        // Trackers:
        int rowTracker = 1;
        int colTracker = 1;

        // Pointers:
        Node rowPointer = root;
        Node colPointer;

        while (rowPointer != null) {

            colPointer = rowPointer;
            colTracker = 1;

            while (colPointer != null) {

                //Display all possible numbers
                System.out.print("Row: " + rowTracker + " Col: " + colTracker + " | ");
                colPointer.displayPossible();

                // Move column pointer East
                colPointer = colPointer.getEast();
                colTracker++;
            }
            // Move row pointer South
            rowPointer = rowPointer.getSouth();
            rowTracker++;
        }

    }

    // Checks if sudoku is solved already
    private boolean isSolved(){
        // Pointers:
        Node rowPointer = root;
        Node colPointer;

        while(rowPointer != null) {

            colPointer = rowPointer;

            while (colPointer != null) {

                // If at least one number is not zero, aka no solved
                if(colPointer.getData() == 0)
                    return false;

                // Move column pointer East
                colPointer = colPointer.getEast();
            }
            // Move row pointer South
            rowPointer = rowPointer.getSouth();
        }
        return true;

    }

    // Runs through all Nodes and solves them if possible,
    // Returns boolean:
    //      true - if at least one Node has been solved,
    //      false - if couldn't solve any Nodes, aka the sudoku is either solved or needs a guess
    private boolean solveNodes(){

        // Keeps track if at least one Node has been solved
        boolean solvedAny = false;

        // Pointers:
        Node rowPointer = root;
        Node colPointer;

        while(rowPointer != null) {

            colPointer = rowPointer;

            while (colPointer != null) {

                // If solved, then at least one has been solved
                if(colPointer.solveNode())
                    solvedAny = true;


                // Move column pointer East
                colPointer = colPointer.getEast();
            }
            // Move row pointer South
            rowPointer = rowPointer.getSouth();
        }

        // Returns
        return solvedAny;
    }

    // Main method for solving sudoku
    public void solve(){

        // Each time this method is used
        iterSolve++;

        // Eliminates all possibilities of number
        eliminate();

        if(!isSolved()){ // Checks if the System is initially solved, aka only if solved sudoku is given
            if(solveNodes()) { // An attempt to solve sudoku
                if (!isSolved()) // Checks if the System is solved
                    solve();  // In case it is not uses recursion
            }

        }




    }

}
