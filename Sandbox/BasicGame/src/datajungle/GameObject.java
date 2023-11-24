package datajungle;

import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameObject {

    int x, y, width, height;
    String texturePath;

    // Initalisser een gameobject met een texture
    public GameObject(int x, int y, int width, int height, String texturePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texturePath = texturePath;
    }

    // Initaliseer een gameobject zonder texture
    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texturePath = null;
    }

    // Update functie van de gameobject
    public void update() {
        if (texturePath == null) { // No Image given to the object
            SaxionApp.setBorderSize(0);
            SaxionApp.drawRectangle(x, y, width, height);
        } else { // image given thus draw the image
            SaxionApp.drawImage(texturePath, x, y, width, height);
        }
    }
}
