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
