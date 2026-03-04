import java.util.ArrayList;
import java.util.Collections;

public class CardGame {
    // Core game components
    ArrayList<Card> deck = new ArrayList<>();
    Hand playerOneHand;
    Hand playerTwoHand;

    // Game state
    boolean playerOneTurn = true;
    boolean gameActive;
    boolean bettingPhase = true;
    int currentBet = 0;
    int balance = 1000; // Starting balance
    int buyIns = 0;
    
    // Animation state
    boolean gameEnding = false;
    boolean restarting = false;
    int animationProgress = 0;
    int animationDelay = 120; // 120 frames (2 seconds) delay before animation
    int animationDuration = 60; // 60 frames for animation

    // UI
    ClickableRectangle drawButton;
    int drawButtonX = 185;
    int drawButtonY = 300;
    int drawButtonWidth = 100;
    int drawButtonHeight = 35;

    //stand
    ClickableRectangle standButton;
    int standButtonX = 315;
    int standButtonY = 300;

    //double down
    ClickableRectangle doubleDownButton;
    int doubleButtonX = 485;
    int doubleButtonY = 400;
    
    // Betting buttons
    ClickableRectangle betButton;
    ClickableRectangle startButton;
    ClickableRectangle maxBetButton;
    int betButtonX = 75;
    int betButtonY = 300;
    int betButtonWidth = 100;
    int betButtonHeight = 35;
    int startButtonX = 250;
    int startButtonY = 300;
    int maxBetButtonX = 425;
    int maxBetButtonY = 300;

    public CardGame() {
        initializeGame();
        // Don't deal cards yet, wait for betting phase
    }

    protected void initializeGame() {
        // Initialize draw button
        drawButton = new ClickableRectangle();
        drawButton.x = drawButtonX;
        drawButton.y = drawButtonY;
        drawButton.width = drawButtonWidth;
        drawButton.height = drawButtonHeight;

        doubleDownButton = new ClickableRectangle();
        doubleDownButton.x = doubleButtonX;
        doubleDownButton.y = doubleButtonY;
        doubleDownButton.width = drawButtonWidth;
        doubleDownButton.height = drawButtonHeight;

        standButton = new ClickableRectangle();
        standButton.x = standButtonX;
        standButton.y = standButtonY;
        standButton.width = drawButtonWidth;
        standButton.height = drawButtonHeight;
        
        // Initialize betting buttons
        betButton = new ClickableRectangle();
        betButton.x = betButtonX;
        betButton.y = betButtonY;
        betButton.width = betButtonWidth;
        betButton.height = betButtonHeight;
        
        startButton = new ClickableRectangle();
        startButton.x = startButtonX;
        startButton.y = startButtonY;
        startButton.width = betButtonWidth;
        startButton.height = betButtonHeight;
        
        maxBetButton = new ClickableRectangle();
        maxBetButton.x = maxBetButtonX;
        maxBetButton.y = maxBetButtonY;
        maxBetButton.width = betButtonWidth;
        maxBetButton.height = betButtonHeight;

        // Initialize decks and hands
        deck = new ArrayList<>();
        playerOneHand = new Hand();
        playerTwoHand = new Hand();
        gameActive = true;
        bettingPhase = true;
        currentBet = 0;

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
            if(i != 1) {
                card.setTurned(true);
            }
            playerTwoHand.addCard(card);
        }

        // position cards
        playerOneHand.positionCards(50, 450, 80, 120, 20);
        playerTwoHand.positionCards(50, 50, 80, 120, 20);
    }

    public void drawCard(Hand hand) {
        if (deck != null && !deck.isEmpty()) {
            hand.addCard(deck.remove(0));
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

    public void handleDoubleDownClick(int mouseX, int mouseY){
        if(doubleDownButton.isClicked(mouseX, mouseY) && balance >= currentBet) {
            balance -= currentBet;
            currentBet *= 2;
            drawCard(playerOneHand);
            playerOneHand.positionCards(50, 450, 80, 120, 20);
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

    public int getDeckSize() {
        return deck != null ? deck.size() : 0;
    }

    public int getBuyIn() {
        return buyIns;
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
        gameEnding = true;
        animationProgress = 0;
        
        // Update balance based on outcome
        updateBalance();
        if(balance == 0){
            balance = 1000;
            buyIns += 1;
        }
    }
    
    public String getWinner() {
        if (gameActive) {
            return "";
        }
        
        int playerWorth = getHandWorth(playerOneHand);
        int dealerWorth = getHandWorth(playerTwoHand);
        
        if (playerWorth > 21) {
            return "\nDealer Wins! Player Busted \n Click to Play again";
        } else if (dealerWorth > 21) {
            return "\nPlayer Wins! Dealer Busted \n Click to Play again";
        } else if (playerWorth > dealerWorth) {
            return "\nPlayer Wins! \n Click to Play again";
        } else if (dealerWorth > playerWorth) {
            return "\nDealer Wins! \n Click to Play again";
        } else {
            return "\nPush! (Tie) \n Click to Play again";
        }
    }
    
    private void updateBalance() {
        int playerWorth = getHandWorth(playerOneHand);
        int dealerWorth = getHandWorth(playerTwoHand);
        
        if (playerWorth > 21) {
            // Player busts, lose bet (already deducted)
        } else if (dealerWorth > 21) {
            // Dealer busts, player wins - return bet + winnings
            balance += currentBet * 2;
        } else if (playerWorth > dealerWorth) {
            // Player wins - return bet + winnings
            balance += currentBet * 2;
        } else if (dealerWorth > playerWorth) {
            // Dealer wins, lose bet (already deducted)
        } else {
            // Push - return the bet
            balance += currentBet;
        }
    }
    
    public void updateEndGameAnimation() {
        if (!gameEnding && !restarting) {
            return;
        }
        
        animationProgress++;
        
        if (restarting) {
            // Reverse animation - slide everything back in
            if (animationProgress < animationDuration) {
                float progress = 1.0f - ((float) animationProgress / animationDuration);
                
                // Slide dealer cards back from up
                for (int i = 0; i < playerTwoHand.getSize(); i++) {
                    Card card = playerTwoHand.getCard(i);
                    if (card != null) {
                        card.y = 50 - (int)(200 * progress);
                    }
                }
                
                // Slide player cards back from down
                for (int i = 0; i < playerOneHand.getSize(); i++) {
                    Card card = playerOneHand.getCard(i);
                    if (card != null) {
                        card.y = 450 + (int)(200 * progress);
                    }
                }
                
                // Slide hit button back from left
                drawButton.x = drawButtonX - (int)(300 * progress);
                standButton.x = standButtonX + (int)(400 * progress);
                doubleDownButton.x = doubleButtonX + (int)(400*progress);
            } else {
                restarting = false;
                gameEnding = false;
                animationProgress = 0;
                playerOneTurn = true;
                gameActive = true;
            }
            return;
        }
        if (animationProgress <= animationDelay) {
            return;
        }
        int animFrame = animationProgress - animationDelay;
        if (animFrame < animationDuration) {
            float progress = (float) animFrame / animationDuration;
            for (int i = 0; i < playerTwoHand.getSize(); i++) {
                Card card = playerTwoHand.getCard(i);
                if (card != null) {
                    card.y = 50 - (int)(200 * progress);
                }
            }
            for (int i = 0; i < playerOneHand.getSize(); i++) {
                Card card = playerOneHand.getCard(i);
                if (card != null) {
                    card.y = 450 + (int)(200 * progress);
                }
            }
            drawButton.x = drawButtonX - (int)(300 * progress);
            standButton.x = standButtonX + (int)(400 * progress);
            doubleDownButton.x = doubleButtonX + (int)(400*progress);
        }
    }
    public boolean isAnimating() {
        return (gameEnding || restarting) && animationProgress < (animationDelay + animationDuration);
    }
    public boolean shouldHideText() {
        return gameEnding || restarting;
    }
    public boolean shouldShowWinner() {
        if (!gameEnding || restarting) {
            return false;
        }
        return animationProgress > (animationDelay + 30);
    }
    public void handleRestartClick() {
        if (gameEnding && !restarting && animationProgress >= (animationDelay + animationDuration)) {
            currentBet = 0;
            bettingPhase = true;
        }
    }
    public void handleBetButtonClick(int mouseX, int mouseY) {
        if (betButton.isClicked(mouseX, mouseY) && bettingPhase) {
            if (currentBet + 10 <= balance && currentBet != 500) {
                currentBet += 10;
            }
        }
    }
    public void handleStartButtonClick(int mouseX, int mouseY) {
        if (startButton.isClicked(mouseX, mouseY) && bettingPhase && currentBet > 0 && currentBet <= balance) {
            balance -= currentBet;
            bettingPhase = false;
            animationProgress = 0;
            restarting = true;
            playerOneHand = new Hand();
            playerTwoHand = new Hand();
            deck.clear();
            createDeck();
            dealCards(2);
        }
    }
    
    public void handleMaxBetButtonClick(int mouseX, int mouseY) {
        if (maxBetButton.isClicked(mouseX, mouseY) && bettingPhase) {
            // Bet up to $500 or balance, whichever is less
            int betAmount = Math.min(500, balance);
            if (betAmount > 0) {
                currentBet = betAmount;
                balance -= betAmount; // Deduct immediately
                bettingPhase = false;
                animationProgress = 0;
                restarting = true;
                // Clear any existing hands before dealing
                playerOneHand = new Hand();
                playerTwoHand = new Hand();
                deck.clear();
                createDeck();
                dealCards(2);
            }
        }
    }
    public boolean isBettingPhase() {
        return bettingPhase;
    }
    public int getCurrentBet() {
        return currentBet;
    }
    public int getBalance() {
        return balance;
    }
}
