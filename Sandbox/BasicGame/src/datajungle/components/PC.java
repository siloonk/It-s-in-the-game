package datajungle.components;

import datajungle.BasicGame;
import datajungle.scenes.MainMenuScene;
import datajungle.systems.Spritesheet;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;

public class PC {

    //Collider collider;
    Spritesheet sheet = new Spritesheet("./assets/images/sheets/objects.png", 192, 64, 63, 64, 2);

    int dataTransfered = 0;
    int dataToTransfer = 90;
    int dataTransferRate = 1; // How much data gets sent every .1 seconds

    long lastDataTransfer = System.currentTimeMillis();

    int health = 50;
    int maxHealth = 50;
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
        }
    }

    private void draw() {
        int barX = this.x + 9 - sheet.getImage(1).getWidth() / 2;
        int barY = this.y + sheet.getImage(1).getHeight() / 2 - 13;
        int width = sheet.getImage(1).getWidth() - 20;
        int height = 9;

        SaxionApp.setBorderSize(0);

        Image img = sheet.getImage(1);
        img.setX(x - sheet.getImage(1).getWidth() / 2);
        img.setY(y);
        SaxionApp.add(img);
        SaxionApp.setFill(Color.GRAY);
        SaxionApp.drawRectangle(barX, barY, width, height);
        SaxionApp.setFill(Color.YELLOW);
        SaxionApp.drawRectangle(barX, barY, Math.min((int)(width * (dataTransfered / (float)dataToTransfer)), width), height);


        // Draw health bar
        int healthBarX = 100;
        int healthBarWidth = SaxionApp.getWidth() - 200;
        int healthBarY = 70;
        int healthBarHeight = 20;

        SaxionApp.setFill(Color.BLACK);
        SaxionApp.drawRectangle(healthBarX - 2, healthBarY - 2, healthBarWidth + 4, healthBarHeight + 4);
        SaxionApp.setFill(Color.GRAY);
        SaxionApp.drawRectangle(healthBarX, healthBarY, healthBarWidth, healthBarHeight);
        SaxionApp.setFill(Color.GREEN);
        SaxionApp.drawRectangle(healthBarX, healthBarY, (int)((float)health/maxHealth * healthBarWidth), healthBarHeight);
        SaxionApp.setTextDrawingColor(Color.WHITE);
        SaxionApp.drawText("PC Health", healthBarX + healthBarWidth / 2 - 68, healthBarY - 35, 30);
    }


}
