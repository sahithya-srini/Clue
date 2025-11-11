# Clue Game

In the board game Clue, the goal is to figure out who committed the crime, with what weapon, and in which room. There are six suspects, six weapons, and nine rooms. At the start, one card from each category is hidden in an envelope (the solution), and the rest are dealt to players. Players move around the board, entering rooms to make suggestions (e.g., “I think it was Professor Plum with the candlestick in the library”). Other players must secretly show a card if they have one of the suggested cards, helping eliminate possibilities. When someone is confident, they can make an accusation; if correct, they win, but if wrong, they’re out of the game.

We like the board game Clue, so we decided to create our own version with a movie theme. The game involves 4 players who are movie characters, and 6 rooms represented by movies like Tangled and Despicable Me. Players are represented by the circles on the grid. The yellow circle is the Minion and is the human player. On the right side, we displayed the cards each player has. Since the human player, the minion, knows which cards they have, only their cards are displayed at the start of the game. Each other player has two grey rectangles to represent their cards, which are not visible to the human player at the start of the game. To simplify Clue, we only have room and suspect cards; we don't have weapon cards. So the players are only guessing the killer and the room where the murder occurred. 

[gameplay rules like the actual board game]
During the game, the human player will make a guess for the killer, and the room they’re in counts as a guess for the location of the murder like in the actual Clue game. If the human player guesses the killer and/or the room incorrectly, the code will find the Players that own those cards and reveal one of them to the human player on the screen. Otherwise, the human player guessed the correct answer and won the game.

## Process
We first spent a lot of time discussing and designing our plan together for the game. Our goals when figuring out the logic were to simplify the Clue board game and also add our own ideas. We identified the core components of the game, drew diagrams of the digital game board, and wrote pseudocode.
We settled on using separate classes:
- Board.java for the game board and handled player movement (graphics using JavaFX)
- Player.java for tracking player data and actions (managed player names, cards, positions, and turns)
- Room.java for defining room identities and possible clue associations
- GameController.java to manage the game flow and enforce rules; handles card distribution, turn order, suggestions, and accusations
- Main.java as the entry point to run the game; ties everything together with a text-based interface, letting players input actions and see results in the console

## Challenges & Accomplishments
- figuring out how to work on the project’s code together & divide steps so we could work simultaneously on the same project
- figuring out the logic, lots of trial and error

- creating appealing UI/UX using JavaFX (& learning how to use JavaFX)
- adding movie poster images for rooms


## How to run
This project is built using JavaFX, so JavaFX runtime (https://openjfx.io/openjfx-docs/) will be needed to run this project.

Download the Clue.jar file from the release and run the following command.
```bash
   java --module-path C:\Apps\javaproject\javafx-sdk-24.0.1\lib --add-modules javafx.controls,javafx.fxml -jar Clue.jar
   ```

Below is an image of the game board. The section on the right displays each player's cards and below the board is a place for the user to enter their guesses.
 <p align="left">
      <img width="326" height="294" alt="image" src="https://github.com/user-attachments/assets/10d9b095-833d-4425-b394-e316a0ab315b" />
    </p>

 <p align="left">
      <img width="380" height="307" alt="image" src="https://github.com/user-attachments/assets/98ee6ad0-7cc4-4eed-bfc3-8b99fa8cdd88" />
    </p>

