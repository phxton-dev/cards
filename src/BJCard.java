import java.awt.Shape;
import processing.core.PApplet;

public class BJCard extends Card {

    public BJCard(String value, String suit) {
        super(value, suit);
        
    }

    @Override
    public void draw(PApplet sketch) {
        super.draw(sketch);
        // card
        sketch.strokeWeight(5);
        sketch.stroke(200);
        sketch.line(x, y+height-5, x+width, y+height-5);
        sketch.line(x+width-5, y, x+width-5, y+height);
        sketch.stroke(255);


        sketch.noFill();
        sketch.stroke(0);
        sketch.strokeWeight(5);
        sketch.rect(x, y, width, height);

        sketch.strokeWeight(1);
        switch(suit) {
            case "Hearts":
                sketch.fill(255, 255, 255);
                sketch.smooth();
                sketch.noStroke();
                sketch.fill(255,0,0);
                sketch.beginShape();
                sketch.vertex(x+40, y+50);
                sketch.bezierVertex(x+40, y+30, x+80, y+40, x+40, y+75);
                sketch.vertex(x+40, y+50);
                sketch.bezierVertex(x+40, y+30, x, y+40, x+40, y+75);
                sketch.endShape();
                break;
            case "Diamonds":
                sketch.fill(255, 0, 0);
                //sketch.circle(x,y,30);
                sketch.quad(x+(width/4),y+(height/2),x+((width/2)),y+(height/4),x+(3*(width/4)),y+(height/2),x+(width/2),y+(3*(height/4)));
                break;
            case "Clubs":
                sketch.fill(0);
                sketch.circle(x+25,y+(height/2),20);
                sketch.circle(x+50,y+(height/2),20);
                sketch.circle(x+37,y+50,20);
                sketch.strokeWeight(5);
                sketch.line(x+37,y+50,x+37,y+75);
                break;
            case "Spades":
                sketch.fill(0);
                sketch.strokeWeight(5);
                sketch.triangle(x+(width/4), y+(height/2), x+(width/2), y+(height/4), x+(3 * (width/4)), y+(height/2));
                sketch.line(x+(width/2),y+(height/2),x+(width/2),y+((4*height/6)));
                sketch.circle(x+(width/3)+1,y+(height/2)+2,16);
                sketch.circle(x+(2*width/3),y+(height/2)+2,16);
                break;
            default:
                sketch.fill(200);
                break;
        }
        

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
        if (value == "Skip") {
            // skip symbol can be represented as a circle with a line through it
            sketch.push();
            sketch.noFill();
            sketch.stroke(0);
            sketch.strokeWeight(3);
            sketch.ellipse(x + 15, y + 15, 20, 20);
            sketch.line(x + 5, y + 5, x + 25, y + 25);
            sketch.pop();
        } else {
            sketch.text(value, x + 10, y + 10);
        }

    }

}