package rolfie;

import nl.saxion.app.SaxionApp;

public class GameObject {

    int x, y, width, height;
    String texturePath;

    public GameObject(int x, int y, int width, int height, String texturePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texturePath = texturePath;
    }
    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texturePath = null;
    }


    public void update() {
        if (texturePath == null) { // No Image given to the object
            SaxionApp.setBorderSize(0);
            SaxionApp.drawRectangle(x, y, width, height);
        } else {
            SaxionApp.drawImage(texturePath, x, y, width, height);
        }
    }
}
