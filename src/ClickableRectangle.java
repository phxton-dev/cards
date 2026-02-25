import processing.core.PApplet;

public class ClickableRectangle {
    int x;
    int y;
    int width;
    int height;
    public boolean visible = true;

    boolean isClicked(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width &&
               mouseY >= y && mouseY <= y + height;
    }

    public void draw(PApplet app) {
        if (visible) {
            app.rect(x, y, width, height);
        }
    }

    public void setVisible(boolean b) {
        this.visible = b;
    } 
}
