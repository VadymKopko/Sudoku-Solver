# Sudoku Solver
The main objective of the program ("Sudoku Solver") is to find a solution to any difficulty level Sudoku board-game fast and efficiently.
Is written on Java. Method includes linked nodes in a grid with an elimination, guessing and recursive algorithm.
## Step-by-Step Instalation:
---
##### Initialize a new 9x9 Sudoku Board: 
> Board *sudoku* = new Board();

This creates an empty board with all Nodes set to '0'*

##### To populate the Sudoku Board from a file (.txt):
> *sudoku*.populate(**filepath**);

The file itself should be a (9 x 9) grid with spaces in between numbers.

##### To dsiplay the Sudoku Board to console:
> *sudoku*.display();

##### To solve the Sudoku Board:
> *sudoku*.solve();
