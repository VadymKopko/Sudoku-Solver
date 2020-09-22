/*
    Main objective of the class ("Board") is to simulate game-board grid.

    Author: Vadym Kopko
    Version: 1.0.0
 */
package main;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Board {

    // Initialization of constant variables:
    final int SIZE = 9;  // A maximum possible number in Sudoku.
    final boolean DEBUG = false;  // Set true for helpful outputs to into console

    // Initialization of private variables:
    private Node root;  // Initialization for a root node
    private boolean atLeastOneNodeSolved = false;
    
    /**
     * Class Constructor
     * @throws IOException
     */
    public Board()throws IOException {
    	
        // Creating a Node Root
        this.root = new Node(giveNameToNode(0, 0), giveBoxID(0,0));

        // Pointers:
        Node rowPointer = root;
        Node colPointer;

        // A process of creation 
        for(int row = 0; row < SIZE; row++){

            // After the first iteration
            if(row != 0){
                // Add and Connect Nodes Horizontally:
                Node newNode = new Node(giveNameToNode(row, 0), giveBoxID(row, 0));  // Create a new Node
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
                Node newNode = new Node(giveNameToNode(row, col), giveBoxID(row, col));  // Create a new Node
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

    /**
     * Gives each box in a grid an identification by row and col values.
     * @param row
     * @param col
     * @return boxID
     */
    private int giveBoxID(int row, int col){

        // Staring with a zero
        int boxID = 0;

        boxID += 3 * (row / 3);
        boxID += (col / 3);
        
        return boxID;
    }
    
    /**
     * Displays current grid onto a console
     */
    public void display() {

        System.out.println();
        // DEBUGGING
        boolean showID = false;

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
    
    /**
     * Displays a horizontal bar with a specific size in spaces 
     * @param size
     */
    private void displayHLine(int size){
        for(int x = 0; x < size; x++){
            System.out.print("-");
        }
        System.out.print("\n");
    }

    /**
     * Debugging tool for displaying a list of all possible numbers for all nodes in a grid onto a console
     */
    private void displayPossibilities(){

        // Trackers:
        int rowTracker = 1;
        int colTracker;

        // Pointers:
        Node rowPointer = root;
        Node colPointer;

        while (rowPointer != null) {

            colPointer = rowPointer;
            colTracker = 1;

            while (colPointer != null) {

                //Display all possible numbers
            	if(DEBUG) {
	                System.out.print("Row: " + rowTracker + " Col: " + colTracker + " | ");
	                colPointer.displayPossible();
            	}

                // Move column pointer East
                colPointer = colPointer.getEast();
                colTracker++;
            }
            // Move row pointer South
            rowPointer = rowPointer.getSouth();
            rowTracker++;
        }

    }

    /**
     * Populates the data into the grid by reading from an existing file(*.txt)
     * @param filepath, file must contain 9x9 gird of single digit numbers(0-9), with space in between each number.
     * @throws IOException
     */
    public void populate(String filepath) throws IOException {

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

        input.close();
    }

    /**
     * Tries to solve current grid, if sudoku has a dead end terminates.
     * @throws IOException
     */
    public void solve() throws IOException{
    	
    	// Assumes that not a single node has been solved up to this point
    	atLeastOneNodeSolved = false;
    	
    	if(DEBUG) {
	    	display();
	    	System.out.print("Solve -> ");
    	}

        // Eliminates all possibilities of number
    	if(DEBUG)
    		System.out.print("Eliminate\n");
        eliminatePossibilitiesForAll();

        // Checks if the system is solved
        if(DEBUG)
        	System.out.println("Is sudoku solved: " + isSudokuSolved());
        if(!isSudokuSolved()){
            // An attempt to solve nodes
        	solveNodes();
            if(atLeastOneNodeSolved){
            	if(DEBUG)
            		System.out.println("Is node solved: TRUE");
                // Checks if the System is solved
            	if(DEBUG)
            		System.out.println("Is sudoku solved: " + isSudokuSolved());
                if (!isSudokuSolved()) {
                    // In case it is not use recursion of the method
                    solve();
                }
            }
            // If no more cells can be eliminated-solved then start guessing
            else {
            	if(DEBUG)
            		System.out.println("Is node solved: FALSE");
                // Find the first unsolved node with least number of possibilities
            	if(DEBUG)
            		System.out.println("Start guessing ->");
                Node unsolvedNode = findUnsolvedNode();
                
                // Guess one of its possibilities
                if(unsolvedNode != null)
                    guess(unsolvedNode);
            }
            if(DEBUG)
            	System.out.print("DEAD END ---------------| \n");
        }
    }

    /**
     * Checks for grid to be solved
     * @return true, when grid is solved
     * @return false, when in process
     */
    public boolean isSudokuSolved(){
    	
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

    /**
     * Eliminates possibilities for numbers in ALL Nodes
     */
    private void eliminatePossibilitiesForAll(){
    	
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
                        colPointer.setImpossible(i);

                    // Eliminate possibility of this number horizontally
                    cellPointer = colPointer;
                    // Move East
                    while(cellPointer != null){
                        cellPointer.setImpossible(cell);
                        cellPointer = cellPointer.getEast();
                    }
                    cellPointer = colPointer;
                    // Move West
                    while(cellPointer != null){
                        cellPointer.setImpossible(cell);
                        cellPointer = cellPointer.getWest();
                    }

                    // Eliminate possibility of this number vertically
                    cellPointer = colPointer;
                    // Move North
                    while(cellPointer != null){
                        cellPointer.setImpossible(cell);
                        cellPointer = cellPointer.getNorth();
                    }
                    cellPointer = colPointer;
                    // Move South
                    while(cellPointer != null){
                        cellPointer.setImpossible(cell);
                        cellPointer = cellPointer.getSouth();
                    }

                    // Eliminate possibilities within the same box
                    eliminatePossibilitesInBox(boxID, cell);
                }

                // Move column pointer East
                colPointer = colPointer.getEast();
            }
            // Move row pointer South
            rowPointer = rowPointer.getSouth();
        }
    }
    
    /**
     * Eliminates possibilities for numbers in a Node
     * @param node
     */
    private void eliminatePossibilitiesFor(Node node){
    	
        // Receive data for a Node
        int cell = node.getData();

        // When number is already known delete possibilities
        if(cell != 0){

            Node cellPointer = node;
            int boxID = cellPointer.getBoxID();

            // Set possibilities o this number to false, aka solved
            for(int i = 0; i < SIZE; i++)
            	node.setImpossible(i);

            // Eliminate possibility of this number horizontally
            cellPointer = node;
            // Move East
            while(cellPointer != null){
                cellPointer.setImpossible(cell);
                cellPointer = cellPointer.getEast();
            }
            cellPointer = node;
            // Move West
            while(cellPointer != null){
                cellPointer.setImpossible(cell);
                cellPointer = cellPointer.getWest();
            }

            // Eliminate possibility of this number vertically
            cellPointer = node;
            // Move North
            while(cellPointer != null){
                cellPointer.setImpossible(cell);
                cellPointer = cellPointer.getNorth();
            }
            cellPointer = node;
            // Move South
            while(cellPointer != null){
                cellPointer.setImpossible(cell);
                cellPointer = cellPointer.getSouth();
            }

            // Eliminate possibilities within the same box
            eliminatePossibilitesInBox(boxID, cell);
        }
    }

    /**
     * Eliminates possibilities for numbers in one of boxes in a grid
     * @param boxID, which box
     * @param num, which number
     */
    private void eliminatePossibilitesInBox(int boxID, int num) {

        // Pointers:
        Node rowPointer = root;
        Node colPointer;

        while (rowPointer != null) {

            colPointer = rowPointer;

            while (colPointer != null) {

                // In case where it is the right box, eliminate possibility of num
                if (colPointer.getBoxID() == boxID)
                    colPointer.setImpossible(num);

                // Move column pointer East
                colPointer = colPointer.getEast();
            }
            // Move row pointer South
            rowPointer = rowPointer.getSouth();

        }
    }

    /**
     * Runs through all nodes in a grid add tries to solve them.
     * Changes instance variable atLeastOneNodeSolved to true whenever at least one node has been solved.
     */
    private void solveNodes(){

        // Pointers:
        Node rowPointer = root;
        Node colPointer;

        while(rowPointer != null) {

            colPointer = rowPointer;

            while (colPointer != null) {

                // If solved, then at least one has been solved
                if(colPointer.solveNode()) {
                	
                	//Update possibilities after the
                	eliminatePossibilitiesFor(colPointer);
                	
                    atLeastOneNodeSolved = true;
                }


                // Move column pointer East
                colPointer = colPointer.getEast();
            }
            // Move row pointer South
            rowPointer = rowPointer.getSouth();
        }
    }
   
    /**
     * Attempts to find an unsolved node
     * @return unsolvedNode, returns null when found none
     */
    private Node findUnsolvedNode(){
    	
    	if(DEBUG)
    		System.out.print("Finding Unsolved Node -> (");
    	
        int currentMinNumOfPoss = SIZE+1;

        // Pointers:
        Node rowPointer = root;
        Node colPointer;

        // Node to return
        Node returnNode = null;

        while(rowPointer != null) {

            colPointer = rowPointer;

            while (colPointer != null) {

                // If at least one number is not zero and it is the least number of possibilities
                if(colPointer.getData() == 0) {
                    // If number of possibilities is less than current maximum but not zero
                    if (colPointer.getNumOfPoss() < currentMinNumOfPoss && colPointer.getNumOfPoss() > 0) {
                        currentMinNumOfPoss = colPointer.getNumOfPoss();
                        returnNode = colPointer;
                    }
                }
                // Move column pointer East
                colPointer = colPointer.getEast();
            }
            // Move row pointer South
            rowPointer = rowPointer.getSouth();
        }
        
        //Debugging comment
        if(DEBUG) {
	        if(returnNode != null)
	        	System.out.print(returnNode.getName()+")\n");
	        else
	        	System.out.print("NOT FOUND ANY)\n");
        }
        
        return returnNode;
    }
    
    /**
     * Creates a new for a node based on its row and col values.
     * @param row
     * @param col
     * @return String name;
     */
    private String giveNameToNode(int row, int col) {
    	return "(" + row + " | " + col +")";
    }

    /**
     * Guesses a specific node as many times as node has possibilities.
     * @param unsolvedNode
     * @throws IOException
     */
    private void guess(Node unsolvedNode) throws IOException{
    	
    	if(DEBUG) {
	    	System.out.print("Guessing...\n");
	        System.out.print(unsolvedNode.getName() + " | ");
	        unsolvedNode.displayPossible();
	        display();
    	}

        // Create an array for all possible numbers for current unsolved node
        boolean[] possibilities = unsolvedNode.getPossible();

        // Iterate through and try each possibility of current unsolved node
        for(int i = 1; i < 10; i++) {

            // When number is possible for a guess
            if(possibilities[i]) {

                // Pretend that this is a right number
                unsolvedNode.setData(i);

                // Create a new board
                Board tempBoard = new Board();

                // Populate with data that already exists for current board, but with the guess
                copyGridDataTo(tempBoard);

                // Attempt to solve temporary board
                tempBoard.solve();

                // In case the board is not solved set guessed number to impossible
                if(!tempBoard.isSudokuSolved()) {
                    unsolvedNode.setImpossible(i);
                } else {
                    tempBoard.copyGridDataTo(this);
                    return;
                    // Finishes loop when the solution is found!
                }

            }
        }
    }
    
    /**
     * Copies all data from this grid to a provided grid.
     * @param tempBoard
     */
    public void copyGridDataTo(Board tempBoard){
    	
        // Trackers:
        int rowTracker = 1;
        int colTracker;

        // Pointers:
        Node rowPointer = root;
        Node colPointer;

        while(rowPointer != null){

            colPointer = rowPointer;
            colTracker = 1;

            while(colPointer != null){

                // Copy existing data
                tempBoard.copyNodeDataFrom(rowTracker, colTracker, colPointer.getData());

                // Move column pointer East
                colPointer = colPointer.getEast();
                colTracker++;
            }
            // Move row pointer South
            rowPointer = rowPointer.getSouth();
            rowTracker++;
        }
    }
    
    /**
     * Copies a particular node data to another node
     * @param row
     * @param col
     * @param data
     * TODO: Redesign a method for coping nodes while creating a new grid. This method takes way too much time.
     */
    public void copyNodeDataFrom(int row, int col, int data){

        // Trackers:
        int rowTracker = 1;
        int colTracker;

        // Pointers:
        Node rowPointer = root;
        Node colPointer;

        while(rowPointer != null){

            colPointer = rowPointer;
            colTracker = 1;

            while(colPointer != null){

                // In case we found the right slot to copy data into
                if(rowTracker == row && colTracker == col){
                    colPointer.setData(data);
                    return;
                }

                // Move column pointer East
                colPointer = colPointer.getEast();
                colTracker++;
            }
            // Move row pointer South
            rowPointer = rowPointer.getSouth();
            rowTracker++;
        }
    }

}
