package datajungle.ui;

import java.awt.*;

public abstract class UIElement {

    int x, y;


    public UIElement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void draw();

    protected void update() {
        draw();
    }
}
