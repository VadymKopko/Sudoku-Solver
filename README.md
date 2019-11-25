# Sudoku Solver
The main objective of the program ("Sudoku Solver") is to find a solution to any difficulty level Sudoku board-game fast and efficiently.
Is written on Java. Method includes linked nodes in a grid with an elimination, guessing and recursive algorithm.

## Step-by-Step Instalation:

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

## License:

MIT License

Copyright (c) 2019 Vadym Kopko

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
