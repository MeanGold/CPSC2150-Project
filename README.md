# CPSC 2150 - Software Engineering Group Project 
<p align="center">
  <img src="https://github.com/MeanGold/CPSC2150-Project/blob/main/Images/Connect_Four.gif">
</p>

This is the repo for a project that I built in collaboration with three of my classmates. The project is based on the idea for a basic Connect4 game, but this version of the game allows the user to choose the dimensions of the gameboard and how many tokens are required to win. On the backend side, my team and I designed two different implementations: one for ease of access and quickly referencing token placements (using an array), and the other for memory efficiency (using key-value pairs). Throughout the project, I learned aobut different aspects of Software Engineering. The project and course material specifically highlighted areas such as interface design, contract specifications, testing, and enabling multiple implementations through abstraction. Some of the work that I did for the project included...
- Adding a Factory constructor
- Creating helper functions for comparing board states
- Writing JUnit tests for checkIfFree(), the Constructor, WhatsAtPos(), and isPlayerAtPos()
- Adding testing functionality in the Makefile
- Writing `GameBoardMem` implementation
- Testing functionality of `GameBoardMem` functions
- Carefully reading through Project description doc and compiling tasks for our team to complete
- Generating Javadocs for Part 3 files
- Making small edits to the `GameBoard` and `GameBoardMem` contracts
- Creating the contracts for, writing code for, and testing functionality of `BoardPosition.java`
- Creating the `BoardPosition` UML diagram

## Makefile Instructions

### `make` / `make default`: Build the project into an executable `.jar` file

This builds the project and places an executable `.jar` in `build/Project2.jar`

```sh
$ make
# to use: java -jar build/Project2.jar
```

### `make run`: Run the project

Equivalent to the above 2 commands, builds the project and then
executes the `.jar` file.

```sh
$ make run
```

### `make clean`: Removes `.class` files and the `build/` directory

```sh
$ make clean
```

### `make test`: Compiles the JUnit testing files

```sh
$ make test
```

### `make testGB`: Runs the JUnit tests for the GameBoard class

```sh
$ make testGB
```

### `make testGBMem`: Runs the JUnit tests for the GameBoardMem class

```sh
$ make testGBMem
```

## Contribution Statements:

Evan Cox (`evanacox`): 
- moved `GameBoard` code to `IGameBoard`, created `AbsGameBoard` 
- debugged, tested and fixed various issues in `GameScreen`/`IGameBoard`/`GameBoard`
- updated contracts for consistency across whole project
- made (original) `Makefile` targets
- fixed various issues in `GameBoardMem`
- updated contracts at several phases
- created test cases for several methods, edited all tests to make them consistent
- debugged and fixed faults found via. testing

Christian Herrman (`echerrman`):
- Wrote `GameScreen` function implementations
- Revised some of `GameBoard` & `IGameBoard`
- Added new project 3 requirements: ability to choose number of players and fast or mem implementation
- Wrote updated contracts for `GameScreen`
- Helped play/test to find various small bugs and fixed them
- Updated final requirements to match program
- Wrote checkVertWin() and checkDiagWin() functions for `TestGameBoard`
- Created `TestGameBoardMem` with correct Factory constructor and using same tests as `TestGameBoard1`
- Documented checkVertWin() and checkDiagWin() tests

Jonathan Maenche (`MeanGold`) 
- Added Factory constructor
- Created helper function for comparing board states
- Wrote JUnit tests for checkIfFree(), the Constructor, WhatsAtPos(), and isPlayerAtPos()
- Added testing functionality in the Makefile
- Wrote `GameBoardMem` implementation
- Tested functionality of `GameBoardMem` functions
- Carefully read through Project description doc and compiled tasks for our team to complete
- Generated Javadocs for Project 3 files
- Made small edits to the `GameBoard` and `GameBoardMem` contracts
- Created the contracts for, wrote code for, and tested functionality of `BoardPosition.java`
- Created the `BoardPosition` UML diagram

Benjamin McDonnough (`bmcdonnough`) 
- Created the javadocs/contracts for `GameBoard.java`.
- Created the `GameBoard` class diagram.
- Wrote the functions for `Gameboard.java`/`IGameBoard.java`
- Did several play tests to find and eliminate any issues
- Wrote `AbsGameBoard` UML diagram
- Added the functionality to resize GameBoard and number in a row to win.
- Continued adding to the UML diagrams
- Helped with debugging for GameBoardMem
- Wrote some of the tests for checking for wins
- Revised several of the tests to make sure they followed the specifications provided
