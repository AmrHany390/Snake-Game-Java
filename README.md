# Snake Game (Java + JavaFX)\ Snake Game (Java + JavaFX)



A classic Snake game built in Java, split cleanly between game logic and rendering.



\## 👥 Team



\- \*\*Amr Hany\*\* (\[@AmrHany390](https://github.com/AmrHany390)) — Backend / game logic

\- \*\*Hassan Abdalla\*\* (\[@UpLightt](https://github.com/UpLightt)) — Frontend / JavaFX UI



\## 🎮 About



This project implements Snake with a deliberate separation of concerns: the backend has no knowledge of pixels, rendering, or JavaFX — it operates purely on a coordinate grid. The frontend reads grid state from the backend each tick and renders it.



\*\*Backend responsibilities:\*\*

\- Grid-based game state (snake body, food position, score, game-over status)

\- Movement, growth, and collision logic

\- Random food spawning that avoids the snake's body



\*\*Frontend responsibilities:\*\*

\- Rendering the grid using JavaFX

\- Capturing keyboard input

\- Running the game loop (timer/animation)



\## 🏗️ Architecture



```

Position   — immutable (x, y) coordinate

Direction  — enum: UP, DOWN, LEFT, RIGHT

Snake      — owns its body (list of Position), handles movement/growth/self-collision

GameBoard  — owns a Snake and food position, runs one "tick" of game logic per call

```



The contract between backend and frontend is small and intentional:



```java

board.update(Direction);       // advances the game by one tick

board.getSnakeBody();          // List<Position> — for drawing the snake

board.getFood();               // Position — for drawing the food

board.getScore();               // int

board.isGameOver();             // boolean

```



\## 🚀 Running It



\### Option 1: Download the prebuilt executable

Grab the latest release from the \[Releases](../../releases) page (or wherever you're hosting it — e.g. a Google Drive link), unzip, and run `SnakeGame.exe`. No installation needed.



\### Option 2: Run from source

Requires JDK 21+ and the JavaFX SDK.



```bash

javac --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml -d out src/\*.java

java --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml -cp out Main

```



\## 🧠 What I Learned



This was my first time designing a multi-class system from scratch rather than following a tutorial. Some of the more interesting problems I ran into:



\- \*\*Defensive copying\*\* — returning copies of mutable internal lists (`getBody()`, `getSnakeBody()`) so outside code can't corrupt internal state by mutating what's returned.

\- \*\*Immutability\*\* — making `Position` immutable (`final` fields, no setters) to avoid subtle aliasing bugs.

\- \*\*Ordering bugs\*\* — food-eating logic initially checked the \*current\* head position against food, which is always one tick too late. Solved with a `getNextHeadPosition()` "peek" method that computes the next position without mutating any state, so the correct `ateFood` flag can be determined \*before\* committing to a move.

\- \*\*Retry loops\*\* — food respawning uses a `do-while` loop to keep generating random positions until one isn't occupied by the snake.



\## 📋 Possible Future Improvements



\- Difficulty levels / increasing speed over time

\- High score persistence

\- Pause/resume functionality

