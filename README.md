# ♟️ Chess

A two-player chess game built in Java featuring a graphical interface, custom piece rendering, and sound effects.

![Chess Board](images/screenshot.png)

## Features

- Two-player local multiplayer (White vs Black)
- Full chess rule enforcement for all pieces
- Graphical board with custom piece images
- Sound effects for moves and game events
- Mouse-based click-to-move controls

### Prerequisites

- Java 17+

### Run the Game
```bash
git clone https://github.com/your-username/chess.git
cd chess
javac *.java
java Chess
```

> If you're using VS Code, just open the repo and run `Chess.java` directly.

## How to Play

- Click a piece to select it, then click a square to move
- Players alternate turns — White goes first
- The game detects check and checkmate automatically

## Project Structure
```
chess/
├── images/          # Piece sprites
├── sfx/             # Sound effect files
├── .vscode/         # VS Code config
├── Piece.java       # Abstract base class for all pieces
├── Board.java       # Board state and rendering
├── GamePanel.java   # Main game loop and display
├── Chess.java       # Entry point
├── King.java
├── Queen.java
├── Rook.java
├── Bishop.java
├── Knight.java
├── Pawn.java
├── MouseListener.java
└── SoundEffect.java
```

## Tech Stack

- **Language:** Java
- **UI:** Java Swing
- **Audio:** Java Sound API

## Future Improvements

- Check/checkmate highlighting
- Move history panel
- AI opponent
- UI Resolution scaling

## Author

Dayanandh — [GitHub](https://github.com/your-username)

## License

[MIT](LICENSE)
