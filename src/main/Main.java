/*
    Main objective of the program ("Sudoku Solver") is to efficiently find solution to a Sudoku board-game.
    A hard Sudoku grid, requires recension, as well as guessing with memorization of a grid before the guess, if
    unsuccessful, requires to undo all steps and perform a guess for an any other directions.
    with memory.

    Author: Vadym Kopko
    Version: 1.0.0
 */
package main;

import java.io.IOException;

public class Main {

    // Constants:

    final int SIZE = 9;  // A maximum possible number in Sudoku.

    /* File Path:
        sudoku_easy.txt only involves elimination algorithm
        sudoku_hard.txt uses both an elimination and recursive guessing algorithms
    */
    public static String SUDOKU_PATH = "resources/sudoku_hard.txt";

    public static void main(String[] args) throws IOException{

        // Create a Sudoku game board
        Board sudoku = new Board();
        sudoku.setSelf(sudoku);

        // Populate the board from a file
        sudoku.populate(SUDOKU_PATH);

        // Display the board before solving
        sudoku.display(false);

        // Solve sudoku
        sudoku.solve();

        // Display the board after solving
        sudoku.display(false);
    }
}