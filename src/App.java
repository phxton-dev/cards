import processing.core.PApplet;
import processing.core.PFont;

public class App extends PApplet {

    CardGame cardGame = new Blackjack();
    private int timer;
    private PFont font;

    public static void main(String[] args) {
        PApplet.main("App");
    }
    @Override
    public void settings() {
        size(600, 600);  
    }

    @Override
    public void setup() {
        font = createFont("font.ttf", 32);
        textFont(font);
    }

    @Override
    public void draw() {
        textFont(font);
        background(50,168,82);
        if(!cardGame.isBettingPhase()){
        push();
        fill(70,188,102);
        stroke(181, 117, 0);
        strokeWeight(3);
        rect(-3,440, width+4,175);
        circle(width/2,-100,750);
        pop();
        }
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
            text("You have bought back in " + cardGame.getBuyIn() + " times", width/2,400 );
            text("Profit: $" + (cardGame.balance - (1000 * (cardGame.getBuyIn()+1))), width/2,380 );
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
            
            //Double down button
            if(cardGame.playerOneHand.getSize() == 2 && cardGame.getCurrentPlayer().equals("Player One")){
                strokeWeight(1);
                fill(218,165,32);
                cardGame.doubleDownButton.draw(this);
                fill(0);
                textAlign(CENTER,CENTER);
                text("Double Bet", cardGame.doubleDownButton.x +cardGame.doubleDownButton.width/2, cardGame.doubleDownButton.y + cardGame.doubleDownButton.height / 2);
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
            if (cardGame.getCurrentPlayer().equals("Player Two") && !cardGame.shouldHideText()) {
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

            if(cardGame.playerOneHand.getSize() == 2){
                cardGame.handleDoubleDownClick(mouseX, mouseY);
            }
        }
    }

}
