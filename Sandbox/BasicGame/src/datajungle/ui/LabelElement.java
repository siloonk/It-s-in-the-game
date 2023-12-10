package datajungle.ui;

import nl.saxion.app.SaxionApp;

import java.awt.*;

public class LabelElement extends UIElement {

    String text;
    Color color;
    int fontSize;

    public LabelElement(int x, int y, String text, int fontSize, Color color) {
        super(x, y);
        this.text = text;
        this.fontSize = fontSize;
        this.color = color;
    }

    @Override
    public void draw() {
        SaxionApp.setTextDrawingColor(color);
        SaxionApp.drawText(text, x, y, fontSize);
    }
}
