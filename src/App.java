import processing.core.PApplet;

public class App extends PApplet {

    CardGame cardGame = new Blackjack();
    private int timer;

    public static void main(String[] args) {
        PApplet.main("App");
    }
    @Override
    public void settings() {
        size(600, 600);  
    }

    @Override
    public void draw() {
        background(50,168,82);
        
        // Update end game animations
        cardGame.updateEndGameAnimation();
        
        if (cardGame.isBettingPhase()) {
            // Draw betting buttons
            fill(0);
            cardGame.betButton.draw(this);
            fill(255);
            textAlign(CENTER, CENTER);
            text("Bet", cardGame.betButton.x + cardGame.betButton.width / 2, cardGame.betButton.y + cardGame.betButton.height / 2);
            
            fill(0);
            cardGame.startButton.draw(this);
            fill(255);
            text("Start", cardGame.startButton.x + cardGame.startButton.width / 2, cardGame.startButton.y + cardGame.startButton.height / 2);
            
            fill(0);
            cardGame.maxBetButton.draw(this);
            fill(255);
            text("MaxBet", cardGame.maxBetButton.x + cardGame.maxBetButton.width / 2, cardGame.maxBetButton.y + cardGame.maxBetButton.height / 2);
            

            //Displays how many times they had to buy back in
            fill(0);
            textSize(16);
            text("You have bought back in " + cardGame.getBuyIn() + " times", width/2,380 );
            // Display current bet
            textSize(24);
            text("Current Bet: $" + cardGame.getCurrentBet(), width / 2, 200);
            
            // Display balance
            textSize(20);
            text("Balance: $" + cardGame.getBalance(), width / 2, 230);
            
            // Instructions
            textSize(16);
            text("Place your bet to start the game", width / 2, 260);
        } else {
            // Draw player hands
            for (int i = 0; i < cardGame.playerOneHand.getSize(); i++) {
                Card card = cardGame.playerOneHand.getCard(i);
                if (card != null) {
                    card.draw(this);
                }
            }
            // Draw computer hand
            for (int i = 0; i < cardGame.playerTwoHand.getSize(); i++) {
                Card card = cardGame.playerTwoHand.getCard(i);
                if (card != null) {
                    card.draw(this);

                }
            }
            
            // Draw draw button
            fill(0);
            cardGame.drawButton.draw(this);
            fill(255);
            textAlign(CENTER, CENTER);
            text("Hit", cardGame.drawButton.x + cardGame.drawButton.width / 2, cardGame.drawButton.y + cardGame.drawButton.height / 2);

            //draw stand button
            fill(0);
            cardGame.standButton.draw(this);
            fill(255);
            textAlign(CENTER, CENTER);
            text("Stand", cardGame.standButton.x + cardGame.standButton.width / 2, cardGame.standButton.y + cardGame.standButton.height / 2);

            // Only display text if not animating end game
            if (!cardGame.shouldHideText()) {
                // Display current player
                fill(0);
                textSize(16);
                text("Current Player: " + cardGame.getCurrentPlayer(), width / 2, 20);

                // Display hand worth
                text("Player Hand: " + cardGame.getHandWorth(cardGame.playerOneHand), 100, height - 20);
                
                // Display deck size
                text("Deck Size: " + cardGame.getDeckSize(), width / 2,
                        height - 20);
                        
                // Display current bet and balance
                text("Bet: $" + cardGame.getCurrentBet() + " | Balance: $" + cardGame.getBalance(), width / 2, height - 50);
            }
            
            // Display winner if game is over (with delay to avoid button overlap)
            String winner = cardGame.getWinner();
            if (!winner.isEmpty() && cardGame.shouldShowWinner()) {
                fill(255, 255, 0);
                textSize(32);
                text(winner, width / 2, height / 2);
            }
            if (cardGame.getCurrentPlayer() == "Player Two" && !cardGame.shouldHideText()) {
                fill(0);
                textSize(16);
                text("Computer is thinking...", width / 2, height / 2 + 80);
                timer++;
                if (timer == 100) {
                    cardGame.handleComputerTurn();
                    timer = 0;
                }
            }
        }
    }

    
    @Override
    public void mousePressed() {
        if (cardGame.isBettingPhase()) {
            // Handle betting phase clicks
            cardGame.handleBetButtonClick(mouseX, mouseY);
            cardGame.handleStartButtonClick(mouseX, mouseY);
            cardGame.handleMaxBetButtonClick(mouseX, mouseY);
        } else if (!cardGame.gameActive && cardGame.gameEnding) {
            // Handle restart on end screen
            cardGame.handleRestartClick();
        } else {
            // Normal game clicks
            cardGame.handleDrawButtonClick(mouseX, mouseY);
            cardGame.handleStandButtonClick(mouseX, mouseY);
        }
    }

}
