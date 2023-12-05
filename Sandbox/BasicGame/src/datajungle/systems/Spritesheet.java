package datajungle.systems;

import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Spritesheet {

    Image spritesheet;

    ArrayList<Image> sprites = new ArrayList<>();
    String path;

    public Spritesheet(String path, int totalWidth, int totalheight, int spriteWidth, int spriteHeight) {
        spritesheet = new Image(path, 0, 0);

        this.path = path;
        spritesheet.setFilename(path);
        splitSheet(totalWidth, totalheight, spriteWidth, spriteHeight, 2);

    }


    public void splitSheet(int totalWidth, int totalHeight, int spriteWidth, int spriteHeight, int whiteSpace) {
        int x = 0;
        int y = 0;

        for (x = 0; x < totalWidth; x += spriteWidth) {
            for (y = 0; y < totalHeight; y += spriteHeight) {
                sprites.add(spritesheet.createSubImage(path, x + path, Math.max(x - whiteSpace, 0), y, spriteWidth, spriteHeight));
            }
        }

    }


    public Image getImage(int index) {
        return sprites.get(index);
    }
}
