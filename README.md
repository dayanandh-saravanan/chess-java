# ♟️ Chess

A fully playable chess game built in Java, supporting two-player gameplay with complete rule enforcement.

## Features

- Two-player local multiplayer
- Full chess rule implementation (castling, en passant, pawn promotion)
- Check and checkmate detection
- GUI interface 

### Installation
```bash
git clone https://github.com/dayanandh-username/chess.git
cd chess
javac -d out src/**/*.java
java -cp out Main
```

## How to Play

- Players alternate turns as White and Black
- The game ends on checkmate or stalemate

## Project Structure
```
chess/
├── src/
│   ├── Main.java
│   ├── Board.java
│   ├── pieces/
│   │   ├── Piece.java
│   │   ├── King.java
│   │   └── ...
│   └── ...
└── README.md
```

## Tech Stack

- Fully coded using Java

## Future Improvements

- AI opponent
- Move history / undo
- Online multiplayer

## Author

Dayanandh — [GitHub](https://github.com/dayanandh-saravanan)
