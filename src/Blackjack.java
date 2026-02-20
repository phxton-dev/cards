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
    public void handleDrawButtonClick(int mouseX, int mouseY) {
        super.handleDrawButtonClick(mouseX, mouseY);
    }
}
