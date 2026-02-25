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

        // Display current player
        fill(0);
        textSize(16);
        text("Current Player: " + cardGame.getCurrentPlayer(), width / 2, 20);

        // Display hand worth
        text("Player Hand: " + cardGame.getHandWorth(cardGame.playerOneHand), 100, height - 20);
        
        // Display deck size
        text("Deck Size: " + cardGame.getDeckSize(), width / 2,
                height - 20);
        
        // Display winner if game is over
        String winner = cardGame.getWinner();
        if (!winner.isEmpty()) {
            fill(255, 255, 0);
            textSize(32);
            text(winner, width / 2, height / 2);
        }
        // Display last played card
        if (cardGame.getLastPlayedCard() != null) {
            cardGame.getLastPlayedCard().setPosition(width / 2 - 40, height / 2 - 60, 80, 120);
            cardGame.getLastPlayedCard().draw(this);
        }
        if (cardGame.getCurrentPlayer() == "Player Two") {
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

    
    @Override
    public void mousePressed() {
        cardGame.handleDrawButtonClick(mouseX, mouseY);
        cardGame.handleStandButtonClick(mouseX, mouseY);
    }

}
