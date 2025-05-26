    import javafx.scene.Scene;
    import javafx.scene.control.Label;
    import javafx.scene.control.TextField;
    import javafx.scene.input.KeyCode;
    import javafx.scene.layout.HBox;
    import javafx.scene.layout.Pane;

    //for displaying cards
    import javafx.scene.layout.StackPane;
    import javafx.scene.layout.VBox;
    import javafx.geometry.Insets;
    import javafx.geometry.Pos;

    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.Collections; //for shuffle method
    import java.util.List;

    public class GameController {

        private Board board;
        private Label infoLabel;
        private TextField guessField;

        private List<Player> players = new ArrayList<>();
        private int currentPlayerIndex = 0;
        private int movesLeft = 0;
        private ArrayList<String> userCards; //NOT USED??
        private Pane rootPane;


        private final ArrayList<String> roomList = new ArrayList<>();
        private final ArrayList<String> suspectList = new ArrayList<>();
        private final String roomKilled;
        private final String killer;


        private Scene currentScene;

        public GameController(Board board, Label infoLabel, TextField guessField, Pane rootPane) {
            this.board = board;
            this.infoLabel = infoLabel;
            this.guessField = guessField;
            this.rootPane = rootPane;

            roomList.add("Lavender Room");
            roomList.add("Blue Room");
            roomList.add("Blush Room");
            roomList.add("White Blue Room");
            roomList.add("Green Room");
            roomList.add("Coral Room");

            suspectList.add("Suspect 1");
            suspectList.add("Suspect 2");
            suspectList.add("Suspect 3");
            suspectList.add("Suspect 4");

            this.roomKilled = roomList.get((int)(Math.random() * roomList.size()));
            this.killer = suspectList.get((int)(Math.random() * suspectList.size()));

            System.out.println("Killer: " + killer + "\nRoom Killed: " + roomKilled);

            setupPlayers();
            setupGuessField();
        }

        public void setupUI() {
            VBox playerInfo = new VBox(10);
            playerInfo.setPadding(new Insets(10)); // gives the inside edges a 10 px margin
            playerInfo.setPrefWidth(200);
            rootPane.getChildren().add(playerInfo);

            //positioning
            playerInfo.setLayoutX(600);
            playerInfo.setLayoutY(10);
//            playerInfo.setAlignment(Pos.TOP_LEFT); //or this?


            Label heading = new Label("Cards");
            heading.setStyle("-fx-font-weight: bold;"); // -fx-font-size: 14px; ??
            playerInfo.getChildren().add(heading);

            for(Player p: players) {
                VBox playerBox = new VBox(5);
                playerBox.setPadding(new Insets(5));
                playerBox.setStyle("-fx-border-color: salmon; -fx-border-width: 3;");

                Label playerName = new Label(p.getName().toUpperCase()); //toUpperCase()??
                playerName.setStyle("-fx-font-weight: bold;");
                playerBox.getChildren().add(playerName);

//                Label hand = new Label("Cards:");
//                playerBox.getChildren().addAll(playerName, hand);
                HBox cardDisplay = new HBox(10);
                cardDisplay.setAlignment(Pos.CENTER_LEFT);

                for (String card: p.getCards()) { //each player cards
                    StackPane cardPane = new StackPane();
                    cardPane.setPrefSize(120, 100);
                    cardPane.setStyle("-fx-border-color: black; -fx-background-color: #D3D3D3;");

                    Label cardName = new Label(card);
                    cardName.setWrapText(true);
                    cardPane.getChildren().add(cardName);

                    cardDisplay.getChildren().add(cardPane);
                }

                playerBox.getChildren().add(cardDisplay);
                playerInfo.getChildren().add(playerBox);
            }
//            rootPane.getChildren().add(userCardsLabel);
//            userCardsLabel.setLayoutX(10);
//            userCardsLabel.setLayoutY(10);
//            userCardsLabel.setPrefWidth(300);
        }

        private void setupPlayers() {
            Player user = new Player("User", javafx.scene.paint.Color.BLACK, 2, 2);
            Player comp1 = new Player("Comp 1", javafx.scene.paint.Color.BLUE, 2, 10);
            Player comp2 = new Player("Comp 2", javafx.scene.paint.Color.DARKRED, 10, 10);
            Player comp3 = new Player("Comp 3", javafx.scene.paint.Color.GREEN, 10, 2);

            players.add(user);
            players.add(comp1);
            players.add(comp2);
            players.add(comp3);

            for (Player p : players) {
                board.addPlayerToken(p, p.getToken());
            }
            dealCards();
        }

        private void dealCards() {
            ArrayList<String> deck = new ArrayList<>();

            // Add rooms except the murder room
            for (String room : roomList) {
                if (!room.equals(roomKilled)) {
                    deck.add(room);
                }
            }
            // Add suspects except the killer
            for (String suspect : suspectList) {
                if (!suspect.equals(killer)) {
                    deck.add(suspect);
                }
            }

            // Shuffle deck
            Collections.shuffle(deck);

            // Deal cards: 2 cards each to 4 players
            int cardIndex = 0;
            for (Player player : players) {
                for (int c = 0; c < 2; c++) {
                    player.addCard(deck.get(cardIndex));
                    cardIndex++;
                }
            }

//            updateUserCardsDisplay();


        }

        private void handleUserGuess() {
            String guess = guessField.getText().trim();
            Player current = players.get(currentPlayerIndex);
            Room currentRoom = board.getRoomAt(current.getRow(), current.getCol());

            if (guess.isEmpty()) {
                infoLabel.setText("Please enter a suspect guess.");
                return;
            }
            if (currentRoom == null) {
                infoLabel.setText("You are not in a room, cannot guess.");
                guessField.clear();
                return;
            }
            if (!suspectList.contains(guess)) {
                infoLabel.setText("Invalid suspect. Try again.");
                guessField.clear();
                return;
            }

            infoLabel.setText("You guessed: " + guess + " in " + currentRoom.getName());

            // Attempt to find disproving card from other players
            boolean disproved = false;
            for (int i = 1; i < players.size(); i++) {
                Player nextPlayer = players.get((currentPlayerIndex + i) % players.size());
                List<String> matchingCards = new ArrayList<>();
                for (String card : nextPlayer.getCards()) {
                    if (card.equalsIgnoreCase(guess) || card.equalsIgnoreCase(currentRoom.getName())) {
                        matchingCards.add(card);
                    }
                }
                if (!matchingCards.isEmpty()) {
                    String revealedCard = matchingCards.get(0);
                    infoLabel.setText("Player " + nextPlayer.getName() + " shows you: " + revealedCard);
                    disproved = true;
                    break;
                }
            }


            // Check for correct guess
            if (guess.equalsIgnoreCase(killer) && currentRoom.getName().equalsIgnoreCase(roomKilled)) {
                infoLabel.setText("Correct! You solved the murder!");
                guessField.setDisable(true);
                currentScene.setOnKeyPressed(null);
                // You can add game over logic here
                return;
            } else {
                infoLabel.setText(infoLabel.getText() + "\nIncorrect guess. Turn ends.");
            }

            guessField.clear();
            guessField.setDisable(true);
            endTurn();
        }

        private void setupGuessField() {
            guessField.setDisable(true);
            guessField.setOnAction(e -> handleUserGuess());
        }

        public void startGame(Scene scene) {
            this.currentScene = scene;
            startTurn();
        }

        private void startTurn() {
            Player current = players.get(currentPlayerIndex);
            movesLeft = rollDice();
            infoLabel.setText(current.getName() + "'s turn. Moves rolled: " + movesLeft);

            if (current.getName().equals("User")) {
                enableUserMovement(current);
            } else {
                guessField.setDisable(true);
                computerTurn(players.get(currentPlayerIndex));
            }
        }

        private int rollDice() {
            int die1 = (int)(Math.random() * 6) + 1;
            int die2 = (int)(Math.random() * 6) + 1;
            return die1 + die2;
        }

        private void enableUserMovement(Player user) {
            guessField.setDisable(true);

            currentScene.setOnKeyPressed(event -> {
                if (movesLeft <= 0) {
                    infoLabel.setText("No moves left. Enter your guess if in a room.");
                    currentScene.setOnKeyPressed(null);
                    checkUserInRoomAndPromptGuess(user);
                    return;
                }

                KeyCode code = event.getCode();
                int newRow = user.getRow();
                int newCol = user.getCol();

                switch (code) {
                    case UP -> newRow--;
                    case DOWN -> newRow++;
                    case LEFT -> newCol--;
                    case RIGHT -> newCol++;
                    default -> {
                        return;
                    }
                }

                if (newRow < 0 || newRow >= Board.BOARD_SIZE || newCol < 0 || newCol >= Board.BOARD_SIZE) return;

                movePlayer(user, newRow, newCol);
                movesLeft--;
                infoLabel.setText(user.getName() + " has " + movesLeft + " moves left.");
            });
        }

        private void movePlayer(Player player, int newRow, int newCol) {
            board.movePlayerToken(player, player.getToken(), newRow, newCol);
            player.setPosition(newRow, newCol);
        }

        private void checkUserInRoomAndPromptGuess(Player user) {
            Room currentRoom = board.getRoomAt(user.getRow(), user.getCol());
            if (currentRoom != null) {
                infoLabel.setText("You are in " + currentRoom.getName() + ". Enter your suspect guess:");
                guessField.setDisable(false);
                guessField.requestFocus();
            } else {
                infoLabel.setText("You are not in a room. No guesses allowed. Ending turn.");
                guessField.setDisable(true);
                endTurn();
            }
        }

        private void computerTurn(Player comp) {
            new Thread(() -> {
                try {
                    int moves = movesLeft;

                    boolean[][] visited = new boolean[Board.BOARD_SIZE][Board.BOARD_SIZE];
                    visited[comp.getRow()][comp.getCol()] = true; //start square visited

                    while (moves > 0) {
                        Thread.sleep(500);
                        int direction = (int)(Math.random() * 4);
                        int newRow = comp.getRow();
                        int newCol = comp.getCol();

                        switch (direction) {
                            case 0 -> newRow--;
                            case 1 -> newRow++;
                            case 2 -> newCol--;
                            case 3 -> newCol++;
                        }

                        //check if out of bounds
                        if (newRow < 0 || newRow >= Board.BOARD_SIZE || newCol < 0 || newCol >= Board.BOARD_SIZE)
                            continue;

                        if (visited[newRow][newCol]) { //check if already been here
                            continue;
                        }

                        visited[newRow][newCol] = true;
                        comp.setPosition(newRow, newCol);

                        final int row = newRow;
                        final int col = newCol;

                        javafx.application.Platform.runLater(() -> {
                            board.movePlayerToken(comp, comp.getToken(), row, col);
                        });
                        moves--;
                    }

                    Room currentRoom = board.getRoomAt(comp.getRow(), comp.getCol());
                    if (currentRoom != null) {
                        String guess = suspectList.get((int)(Math.random() * suspectList.size()));
                        javafx.application.Platform.runLater(() -> {
                            infoLabel.setText(comp.getName() + " guesses " + guess + " in " + currentRoom.getName());
                            if (guess.equalsIgnoreCase(killer) && currentRoom.getName().equalsIgnoreCase(roomKilled)) {
                                infoLabel.setText(comp.getName() + " solved the murder! Game over.");
                                currentScene.setOnKeyPressed(null);
                            } else {
                                endTurn();
                            }
                        });
                    } else {
                        javafx.application.Platform.runLater(this::endTurn);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        private void endTurn() {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

            // Start next turn after a slight delay so UI updates properly
            javafx.application.Platform.runLater(() -> startTurn());
        }

    }
