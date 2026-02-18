import processing.core.PApplet;

public class Blackjack extends CardGame {
    // Uno-specific state
    UnoComputer computerPlayer;
    boolean choosingWildColor = false;
    BJCard pendingWildCard;
    ClickableRectangle[] wildColorButtons;
    int wildButtonSize = 24;
    int wildCenterX = 300;
    int wildCenterY = 300;
    static String[] suits = { "Hearts", "Diamonds", "Clubs", "Spades" };
    static String[] values = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };

    public Blackjack() {
        initializeGame();
    }

    @Override
    protected void createDeck() {
        // Create deck (Blackjack has 52 cards, 13 values in each of 4 suits)
        for (String suit : suits) {
            for (String value : values) {
                deck.add(createCard(suit, value));
            }
        }
    }

    @Override
    protected void initializeGame() {
        super.initializeGame();
        computerPlayer = new UnoComputer();
        dealCards(2);
    }

    private BJCard createCard(String suit, String value) {
        BJCard card = new BJCard(suit, value); // Image loading can be added later
        card.suit = suit;
        card.value = value;
        return card;
    }

    @Override 
    public boolean playCard(Card card, Hand hand) {
        super.playCard(card, hand);
        handleSpecialCards(card);
        return true;
    }

    private void handleSpecialCards(Card card) {
        if (card.value.equals("Skip") || card.value.equals("Reverse")) {
            // right now this only supports 2 players, so Reverse is the same as Skip
            System.out.println("Skipping opponent's turn"); 
            switchTurns(); // Skip opponent's turn
        } else if (card.value.startsWith("Draw ")) {
            System.out.println("Skipping opponent's turn");
            int drawNum = "Draw Two".equals(card.value) ? 2 : 4;
            for (int i = 0; i < drawNum; i++) {
                // refactored into superclass, assuming you've already switched turns to the opponent
                drawCard(playerOneTurn ? playerOneHand : playerTwoHand);
            }
            switchTurns();
        }
    }

    @Override
    public void handleDrawButtonClick(int mouseX, int mouseY) {
        if (choosingWildColor) {
            return;
        }
        super.handleDrawButtonClick(mouseX, mouseY);
    }

    @Override
    protected boolean isValidPlay(Card card) {
        BJCard unoCard = (BJCard) card;
        // Wild cards are always valid
        if (unoCard.suit.equals("Wild")) {
            return true;
        }
        // Card must match suit or value of last played card
        BJCard lastUno = (BJCard) lastPlayedCard;
        return unoCard.suit.equals(lastUno.suit) ||
                unoCard.value.equals(lastUno.value);
    }

    @Override
    public void handleCardClick(int mouseX, int mouseY) {
        if (choosingWildColor) {
            handleWildChooserClick(mouseX, mouseY);
            return;
        }
        BJCard clickedCard = (BJCard) getClickedCard(mouseX, mouseY);
        if (clickedCard == null) {
            return;
        }
        // this is for the first time
        if (selectedCard == null) {
            selectedCard = clickedCard;
            selectedCard.setSelected(true, selectedCardRaiseAmount);
            return;
        }
        // this is the second time
        if (clickedCard == selectedCard) {
            System.out.println("playing card: " + selectedCard.value + " of " + selectedCard.suit);
            if ("Wild".equals(selectedCard.suit)) {
                pendingWildCard = (BJCard) selectedCard;
                choosingWildColor = true;
                return;
            }
            if (playCard((BJCard) selectedCard, playerOneHand)) {
                selectedCard.setSelected(false, selectedCardRaiseAmount);
                selectedCard = null;
            }
            return;
        }

        selectedCard.setSelected(false, selectedCardRaiseAmount);
        selectedCard = clickedCard;
        selectedCard.setSelected(true, selectedCardRaiseAmount);
    }

    @Override
    public void handleComputerTurn() {
        BJCard choice = computerPlayer.playCard(playerTwoHand, (BJCard) lastPlayedCard);
        if (choice == null) {
            drawCard(playerTwoHand);
            playerTwoHand.getCard(0).setTurned(true);
            System.out.println("player two draws");
            switchTurns();
            return;
        }
        if (playCard(choice, playerTwoHand)) {
            if ("Wild".equals(choice.suit)) {
                choice.suit = computerPlayer.chooseComputerWildColor(playerTwoHand);
            }
            playerOneHand.positionCards(50, 450, 80, 120, 20);
            playerTwoHand.positionCards(50, 50, 80, 120, 20);
        } else {
            System.out.println("ERROR, player two / computer chose an invalid play");
        }
    }

    @Override
    public void drawChoices(PApplet app) {
        drawWildChooser(app);
    }

    public void drawWildChooser(PApplet app) {
        if (!choosingWildColor) {
            return;
        }
        app.push();
        app.fill(255, 255, 255, 230);
        app.noStroke();
        app.rect(wildCenterX - 50, wildCenterY - 50, 100, 100, 8);

        for (int i = 0; i < wildColorButtons.length; i++) {
            ClickableRectangle button = wildColorButtons[i];
            switch (suits[i]) {
                case "Red":
                    app.fill(255, 0, 0);
                    break;
                case "Yellow":
                    app.fill(255, 255, 0);
                    break;
                case "Green":
                    app.fill(0, 255, 0);
                    break;
                case "Blue":
                    app.fill(40, 40, 210);
                    break;
                default:
                    app.fill(200);
                    break;
            }
            app.rect(button.x, button.y, button.width, button.height, 4);
        }
        app.pop();
    }

    private void handleWildChooserClick(int mouseX, int mouseY) {
        int raiseAmount = 15;
        for (int i = 0; i < wildColorButtons.length; i++) {
            if (wildColorButtons[i].isClicked(mouseX, mouseY)) {
                if (playCard(pendingWildCard, playerOneHand)) {
                    // Set the wild card's suit to the chosen color AFTER it is validated
                    pendingWildCard.suit = suits[i];
                    pendingWildCard.setSelected(false, raiseAmount);
                    pendingWildCard = null;
                    choosingWildColor = false;
                    selectedCard = null;
                    playerOneHand.positionCards(50, 450, 80, 120, 20);
                    playerTwoHand.positionCards(50, 50, 80, 120, 20);
                }
                return;
            }
        }
    }

    private void initializeWildColorButtons() {
        wildColorButtons = new ClickableRectangle[4];
        int offset = 28;
        int half = wildButtonSize / 2;
        System.out.println("Initializing wild color buttons at center (" + wildCenterX + ", " + wildCenterY
                + ") with offset " + offset);
        wildColorButtons[0] = createWildButton(wildCenterX - offset - half, wildCenterY - half);
        wildColorButtons[1] = createWildButton(wildCenterX + offset - half, wildCenterY - half);
        wildColorButtons[2] = createWildButton(wildCenterX - half, wildCenterY - offset - half);
        wildColorButtons[3] = createWildButton(wildCenterX - half, wildCenterY + offset - half);
    }

    private ClickableRectangle createWildButton(int x, int y) {
        ClickableRectangle button = new ClickableRectangle();
        button.x = x;
        button.y = y;
        button.width = wildButtonSize;
        button.height = wildButtonSize;
        return button;
    }
}
