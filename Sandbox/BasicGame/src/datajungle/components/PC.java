package datajungle.components;

import datajungle.BasicGame;
import datajungle.scenes.MainMenuScene;
import datajungle.systems.Spritesheet;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;

public class PC {

    //Collider collider;
    Spritesheet sheet = new Spritesheet("./assets/images/sheets/objects.png", 96, 64, 32, 64, 3);

    int dataTransfered = 0;
    int dataToTransfer = 10;
    int dataTransferRate = 1; // How much data gets sent every .1 seconds

    long lastDataTransfer = System.currentTimeMillis();

    int health = 50;

    int x, y;

    public PC(int x, int y) {

        //collider = new Collider(x, y, 50, 50);
        this.x = x;
        this.y = y;
    }

    public void damage(int damage) {
        this.health -= health;
        if (this.health <= 0) {
            BasicGame.changeScene(new MainMenuScene());
        }
    }


    public void update() {
        draw();

        if (lastDataTransfer + 1000 > System.currentTimeMillis())  return;

        lastDataTransfer = System.currentTimeMillis();
        dataTransfered += dataTransferRate;


        if (dataTransfered >= dataToTransfer) {
            System.out.println("You have won!");
        }
    }

    private void draw() {
        int barX = this.x + 4;
        int barY = this.y + sheet.getImage(1).getHeight() / 2 - 13;
        int width = sheet.getImage(1).getWidth() - 8;
        int height = 5;

        SaxionApp.setBorderSize(0);

        Image img = sheet.getImage(1);
        img.setX(x);
        img.setY(y);
        SaxionApp.add(img);
        SaxionApp.setFill(Color.GRAY);
        SaxionApp.drawRectangle(barX, barY, width, height);
        SaxionApp.setFill(Color.YELLOW);
        SaxionApp.drawRectangle(barX, barY, Math.min((int)(width * (dataTransfered / (float)dataToTransfer)), width), height);
    }


}
