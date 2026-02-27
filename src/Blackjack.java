

public class Blackjack extends CardGame {
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
        // Don't deal cards here, wait for betting phase to complete
    }

    private BJCard createCard(String suit, String value) {
        BJCard card = new BJCard(value, suit);
        card.suit = suit;
        card.value = value;
        
        // Set card worth for Blackjack
        int worth;
        switch (value) {
            case "2": worth = 2; break;
            case "3": worth = 3; break;
            case "4": worth = 4; break;
            case "5": worth = 5; break;
            case "6": worth = 6; break;
            case "7": worth = 7; break;
            case "8": worth = 8; break;
            case "9": worth = 9; break;
            case "10":
            case "J":
            case "Q":
            case "K":
                worth = 10;
                break;
            case "A":
                worth = 11;
                break;
            default:
                worth = 0;
                break;
        }
        card.setWorth(worth);
        
        return card;
    }

    @Override
    public void handleDrawButtonClick(int mouseX, int mouseY) {
        super.handleDrawButtonClick(mouseX, mouseY);
    }
}
