# Tic Tac Toe - Java Swing Edition

A classic, interactive Tic Tac Toe game built with Java Swing and packaged as a self-contained Windows executable.

## Features
- **Interactive GUI**: Clean and responsive 3x3 grid.
- **Game Logic**: Automatic win and draw detection.
- **Visual Cues**: 
  - Player X is **Red**, Player O is **Blue**.
  - Winning combinations are highlighted in **Green**.
- **Self-Contained**: Runs as a standalone Windows EXE without requiring a local Java installation.

## Project Structure
- `src/com/tictactoe/TicTacToe.java`: Main Java source code containing UI and logic.
- `dist/TicTacToe/`: The packaged application folder.
  - `TicTacToe.exe`: The main executable file.
  - `runtime/`: Bundled Java Runtime Environment (JRE).
- `TicTacToe.jar`: The compiled executable JAR file.

## How to Run
### Standalone Executable (Recommended)
1. Navigate to the `dist/TicTacToe/` directory.
2. Double-click **`TicTacToe.exe`**.

### Executable JAR (Requires Java 14+)
1. Run the following command in the terminal:
   ```bash
   java -jar TicTacToe.jar
   ```

## How to Build (Development)
If you wish to rebuild the project from source:

1. **Compile**:
   ```bash
   javac -d out src/com/tictactoe/TicTacToe.java
   ```
2. **Package JAR**:
   ```bash
   jar cfm TicTacToe.jar MANIFEST.MF -C out .
   ```
3. **Generate EXE** (using `jpackage`):
   ```bash
   jpackage --name TicTacToe --input . --main-jar TicTacToe.jar --main-class com.tictactoe.TicTacToe --type app-image --dest dist
   ```

## Requirements
- **To Run**: Windows OS (for the EXE).
- **To Build**: JDK 14 or higher (for `jpackage` support).
