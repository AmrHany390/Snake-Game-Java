# Snake Game (Java + JavaFX)

A classic Snake game built in Java, split cleanly between game logic and rendering.

## 👥 Team

- **Amr Hany** ([@AmrHany390](https://github.com/AmrHany390)) — Backend / game logic
- **Hassan Abdalla** ([@UpLightt](https://github.com/UpLightt)) — Frontend / JavaFX UI

## 🎮 About

This project implements Snake with a deliberate separation of concerns: the backend has no knowledge of pixels, rendering, or JavaFX — it operates purely on a coordinate grid. The frontend reads grid state from the backend each tick and renders it.

**Backend responsibilities:**
- Grid-based game state (snake body, food position, score, game-over status)
- Movement, growth, and collision logic
- Random food spawning that avoids the snake's body

**Frontend responsibilities:**
- Start menu, settings screen with rebindable keybinds, and game-over screen
- Rendering the grid using JavaFX `Canvas`/`GraphicsContext`
- Keyboard input handling
- Game loop via `AnimationTimer`

## 🏗️ Architecture

```
Position   — immutable (x, y) coordinate
Direction  — enum: UP, DOWN, LEFT, RIGHT
Snake      — owns its body (list of Position), handles movement/growth/self-collision
GameBoard  — owns a Snake and food position, runs one "tick" of game logic per call
Main       — JavaFX Application: menus, rendering, input, game loop
```

The contract between backend and frontend is small and intentional:

```
board.update(Direction);       // advances the game by one tick
board.getSnakeBody();          // List<Position> — for drawing the snake
board.getFood();               // Position — for drawing the food
board.getScore();               // int
board.isGameOver();             // boolean
```

## 🚀 Running It

### Option 1: Download the prebuilt game (easiest)

Grab the latest `.rar` from the [Releases](../../releases) page, extract it, and run `SnakeGame.exe`. No installation needed — Java is bundled inside.

> **Note:** Windows may show a blue "Windows protected your PC" SmartScreen warning when you run it. This happens because the app isn't digitally signed (that costs money and isn't worth it for a small project) — it doesn't mean anything is wrong. Click **"More info"** → **"Run anyway"**.

### Option 2: Run from source

Requires JDK 17+ and the JavaFX SDK.

```
javac --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml -d out src/*.java
java --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml -cp out Main
```

## 📋 Possible Future Improvements

- Difficulty levels / increasing speed over time
- High score persistence
- Pause/resume functionality
