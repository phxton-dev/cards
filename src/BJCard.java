import processing.core.PApplet;

public class BJCard extends Card {
    public BJCard(String value, String suit) {
        super(value, suit);
    }

    @Override
    public void drawFront(PApplet sketch) {
        // set card color based on suit
        switch (suit) {
            case "Hearts":
                sketch.fill(255, 255, 255);
                break;
            case "Diamonds":
                sketch.fill(255, 255, 255);
                break;
            case "Clubs":
                sketch.fill(255, 255, 255);
                break;
            case "Spades":
                sketch.fill(255, 255, 255);
                break;
            default:
                sketch.fill(200);
                break;
        }
        sketch.rect(x, y, width, height);

        // text color based on suit
        switch (suit) {
            case "Hearts":
                sketch.fill(255, 0, 0);
                break;
            case "Diamonds":
                sketch.fill(255, 0, 0);
                break;
            case "Clubs":
                sketch.fill(0, 0, 0);
                break;
            case "Spades":
                sketch.fill(0, 0, 0);
                break;
            default:
                sketch.fill(50);
                break;
        }
        sketch.textSize(14);
        // put on the upper left corner
        sketch.text(value, x + 10, y + 10);

    }

}