package datajungle.ui;

import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

public class ImageElement extends UIElement {

    Image img;
    String imgPath;

    public ImageElement(int x, int y, Image img) {
        super(x, y);
        img.setX(x);
        img.setY(y);
    }

    public ImageElement(int x, int y, String imgPath) {
        super(x, y);
        this.img = new Image(imgPath, x, y);
    }

    @Override
    public void draw() {
        SaxionApp.add(img);
    }
}
