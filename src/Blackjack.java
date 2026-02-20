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
}
