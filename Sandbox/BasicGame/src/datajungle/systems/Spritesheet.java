package datajungle.systems;

import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Spritesheet {

    Image spritesheet = new Image("./assets/images/sheets/characters.png", 0, 0);

    ArrayList<Image> sprites = new ArrayList<>();

    public Spritesheet() {
        // 44, 96

        spritesheet.setFilename("character");
        splitSheet(44, 96, 2);

    }


    public void splitSheet(int width, int height, int whiteSpace) {
        int x = 0;
        int y = 0;

        int w = spritesheet.getWidth();
        int h = spritesheet.getHeight();

        for (x = 0; x < 256; x += width) {
            sprites.add(spritesheet.createSubImage("character", x + "Character", Math.max(x - whiteSpace, 0), y, width, height));
        }


    }


    public Image getImage(int index) {
        return sprites.get(index);
    }
}
