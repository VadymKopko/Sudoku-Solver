/*
    Main objective of the class ("Board") is to simulate game-board grid.

    Author: Vadym Kopko
    Version: 1.0.0
 */
package main;

import java.io.IOException;

public class Board {

    // Constants:

    final int SIZE = 9;  // A maximum possible number in Sudoku.

    // Initialization of private variables:

    private Node root;  // Initialization for a root node
    private int id = -1;  // -1 when no Nodes have been created

    // Class constructor
    public Board()throws IOException {

        // Creating a Node Root
        this.root = new Node(giveID());
        // Pointers:
        Node rowPointer = root;
        Node colPointer;

        for(int row = 0; row < SIZE; row++){

            // After the first iteration
            if(row != 0){
                // Add and Connect Nodes Horizontally:
                Node newNode = new Node(giveID());  // Create a new Node
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
                Node newNode = new Node(giveID());  // Create a new Node
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

        // Trackers:
        int rowTracker = 1;
        int colTracker = 1;

        // Pointers:
        Node rowPointer = root;
        Node colPointer;

        // Display top line
        if(showID) {
            System.out.println("----------------------------------");
        }
        else
            System.out.println("-");

        while (rowPointer != null) {

            colPointer = rowPointer;

            System.out.print("| ");

            while (colPointer != null) {

                // For testing unpopulated grid, display ids instead of data inside each Node
                if (showID)
                    System.out.printf("%2d", colPointer.getId());
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
                if(showID)
                    System.out.println("----------------------------------");
                else
                    System.out.println("-");
            }

            // Move column pointer East
            rowPointer = rowPointer.getSouth();
            rowTracker++;
        }
    }

    // Keeps track of Identifications for Nodes. Returns: ID.
    private int giveID(){
        this.id++;
        return this.id;
    }
}
