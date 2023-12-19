package datajungle.ui;

import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

public class ImageElement extends UIElement {

    Image img;
    String imgPath;

    public ImageElement(Image imgPath, int x, int y, Image img) {
        super(x, y);
        img.setX(x);
        img.setY(y);
    }

    public ImageElement(String imgPath, int x, int y) {
        super(x, y);
        this.img = new Image(imgPath, x, y);
    }

    public ImageElement(String imgPath, int x, int y, int width, int height) {
        super(x, y);
        this.img = new Image(imgPath, x, y, width, height);
    }

    @Override
    public void draw() {
        SaxionApp.add(img);
    }
}
