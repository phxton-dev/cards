import java.util.ArrayList;
import java.util.Collections;

public class CardGame {
    // Core game components
    ArrayList<Card> deck = new ArrayList<>();
    Hand playerOneHand;
    Hand playerTwoHand;
    ArrayList<Card> discardPile = new ArrayList<>();
    Card selectedCard;
    int selectedCardRaiseAmount = 15;

    // Game state
    boolean playerOneTurn = true;
    Card lastPlayedCard;
    boolean gameActive;

    // UI
    ClickableRectangle drawButton;
    int drawButtonX = 175;
    int drawButtonY = 300;
    int drawButtonWidth = 100;
    int drawButtonHeight = 35;

    //stand
    ClickableRectangle standButton;
    int standButtonX = 300;
    int standButtonY = 300;

    public CardGame() {
        initializeGame();
        dealCards(2);

    }

    protected void initializeGame() {
        // Initialize draw button
        drawButton = new ClickableRectangle();
        drawButton.x = drawButtonX;
        drawButton.y = drawButtonY;
        drawButton.width = drawButtonWidth;
        drawButton.height = drawButtonHeight;

        standButton = new ClickableRectangle();
        standButton.x = standButtonX;
        standButton.y = standButtonY;
        standButton.width = drawButtonWidth;
        standButton.height = drawButtonHeight;

        // Initialize decks and hands
        deck = new ArrayList<>();
        discardPile = new ArrayList<>();
        playerOneHand = new Hand();
        playerTwoHand = new Hand();
        gameActive = true;

        createDeck();
    }

    protected void createDeck() {
        // Create a standard deck of cards (for simplicity, using numbers and suits)
        String[] suits = { "Hearts", "Diamonds", "Clubs", "Spades" };
        String[] values = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
        String[] worth = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "10", "10", "10", "11" };
        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < values.length; j++) {
                Card card = new Card(values[j], suits[i]);
                card.setWorth(Integer.parseInt(worth[j]));
                deck.add(card);
            }
        }
    }

    protected void dealCards(int numCards) {
        Collections.shuffle(deck);
        for (int i = 0; i < numCards; i++) {
            playerOneHand.addCard(deck.remove(0));
            Card card = deck.remove(0);
            card.setTurned(true); // face down for computer player
            playerTwoHand.addCard(card);
        }

        // position cards
        playerOneHand.positionCards(50, 450, 80, 120, 20);
        playerTwoHand.positionCards(50, 50, 80, 120, 20);
    }

    public void drawCard(Hand hand) {
        if (deck != null && !deck.isEmpty()) {
            hand.addCard(deck.remove(0));
        } else if (discardPile != null && discardPile.size() > 1) {
            // Reshuffle discard pile into deck if deck is empty
            lastPlayedCard = discardPile.remove(discardPile.size() - 1);
            deck.addAll(discardPile);
            discardPile.clear();
            discardPile.add(lastPlayedCard);
            Collections.shuffle(deck);

            if (!deck.isEmpty()) {
                hand.addCard(deck.remove(0));
            }
        }
    }

    public void handleDrawButtonClick(int mouseX, int mouseY) {
        if (drawButton.isClicked(mouseX, mouseY) && playerOneTurn) {
            drawCard(playerOneHand);
            // Reposition cards after drawing
            playerOneHand.positionCards(50, 450, 80, 120, 20);
            
            // Check for bust after drawing
            if (getHandWorth(playerOneHand) > 21) {
                // Player busts, switch turns
                switchTurns();
            }
        }
    }
    
    public void handleStandButtonClick(int mouseX, int mouseY) {
        if (standButton.isClicked(mouseX, mouseY) && playerOneTurn) {
            // Player chooses to stand, switch turns
            switchTurns();
        }
    }
    
    public int getHandWorth(Hand hand) {
        int total = 0;
        int aceCount = 0;
        
        for (int i = 0; i < hand.getSize(); i++) {
            Card card = hand.getCard(i);
            if (card != null) {
                int worth = card.getWorth();
                total += worth;
                if (worth == 11) {
                    aceCount++;
                }
            }
        }
        
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }
        
        return total;
    }

    public void switchTurns() {
        playerOneTurn = !playerOneTurn;
        playerOneHand.positionCards(50, 450, 80, 120, 20);
        playerTwoHand.positionCards(50, 50, 80, 120, 20);
    }

    public String getCurrentPlayer() {
        return playerOneTurn ? "Player One" : "Player Two";
    }

    public Card getLastPlayedCard() {
        return lastPlayedCard;
    }

    public int getDeckSize() {
        return deck != null ? deck.size() : 0;
    }

    public Hand getPlayerOneHand() {
        return playerOneHand;
    }

    public Hand getPlayerTwoHand() {
        return playerTwoHand;
    }

    public void handleComputerTurn() {
        // Flip all dealer cards face up
        for (int i = 0; i < playerTwoHand.getSize(); i++) {
            Card card = playerTwoHand.getCard(i);
            if (card != null) {
                card.setTurned(false);
            }
        }
        while (getHandWorth(playerTwoHand) < 17) {
            drawCard(playerTwoHand);
            playerTwoHand.positionCards(50, 50, 80, 120, 20);
        }
        
        // Game is over, determine winner
        gameActive = false;
    }
    
    public String getWinner() {
        if (gameActive) {
            return "";
        }
        
        int playerWorth = getHandWorth(playerOneHand);
        int dealerWorth = getHandWorth(playerTwoHand);
        
        if (playerWorth > 21) {
            return "Dealer Wins! Player Busted";
        } else if (dealerWorth > 21) {
            return "Player Wins! Dealer Busted";
        } else if (playerWorth > dealerWorth) {
            return "Player Wins!";
        } else if (dealerWorth > playerWorth) {
            return "Dealer Wins!";
        } else {
            return "Push! (Tie)";
        }
    }

}
