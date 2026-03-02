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
                if(!turned){
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
                }
                else{
                    sketch.fill(20,20,100);
                    sketch.rect(x, y, width, height);
                    sketch.stroke(50,50,130);
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+i*(height/8),x+width,y+height);
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+height,x+width,y+i*(height/8));
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y,x+width,y+i*(height/8));
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+i*(height/8),x+width,y);
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+(height/2),x+width,y+i*(height/8));
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+i*(height/8),x+width,y+(height/2));
                    }
                    
                }
                break;
            case "Diamonds":
                if(!turned){
                sketch.fill(255, 0, 0);
                sketch.quad(x+(width/4),y+(height/2),x+((width/2)),y+(height/4),x+(3*(width/4)),y+(height/2),x+(width/2),y+(3*(height/4)));
                }
                else{
                    sketch.fill(20,20,100);
                    sketch.rect(x, y, width, height);
                    sketch.stroke(50,50,130);
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+i*(height/8),x+width,y+height);
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+height,x+width,y+i*(height/8));
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y,x+width,y+i*(height/8));
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+i*(height/8),x+width,y);
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+(height/2),x+width,y+i*(height/8));
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+i*(height/8),x+width,y+(height/2));
                    }
                    
                }
                break;
            case "Clubs":
                if(!turned){
                sketch.fill(0);
                sketch.circle(x+25,y+(height/2),20);
                sketch.circle(x+50,y+(height/2),20);
                sketch.circle(x+37,y+50,20);
                sketch.strokeWeight(5);
                sketch.line(x+37,y+50,x+37,y+75);
                }
                else{
                    sketch.fill(20,20,100);
                    sketch.rect(x, y, width, height);
                    sketch.stroke(50,50,130);
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+i*(height/8),x+width,y+height);
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+height,x+width,y+i*(height/8));
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y,x+width,y+i*(height/8));
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+i*(height/8),x+width,y);
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+(height/2),x+width,y+i*(height/8));
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+i*(height/8),x+width,y+(height/2));
                    }
                    
                }
                break;
            case "Spades":
                if(!turned){
                sketch.fill(0);
                sketch.strokeWeight(5);
                sketch.triangle(x+(width/4), y+(height/2), x+(width/2), y+(height/4), x+(3 * (width/4)), y+(height/2));
                sketch.line(x+(width/2),y+(height/2),x+(width/2),y+((4*height/6)));
                sketch.circle(x+(width/3)+1,y+(height/2)+2,16);
                sketch.circle(x+(2*width/3),y+(height/2)+2,16);
                }
                else{
                    sketch.fill(20,20,100);
                    sketch.rect(x, y, width, height);
                    sketch.stroke(50,50,130);
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+i*(height/8),x+width,y+height);
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+height,x+width,y+i*(height/8));
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y,x+width,y+i*(height/8));
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+i*(height/8),x+width,y);
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+(height/2),x+width,y+i*(height/8));
                    }
                    for(int i = 0; i < 8; i++){
                        sketch.line(x,y+i*(height/8),x+width,y+(height/2));
                    }
                    
                }
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

    }

}