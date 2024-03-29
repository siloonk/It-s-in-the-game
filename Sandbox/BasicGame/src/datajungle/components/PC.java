package datajungle.components;

import datajungle.BasicGame;
import datajungle.Settings;
import datajungle.scenes.GameOverScene;
import datajungle.scenes.Scene;
import datajungle.systems.Collider;
import datajungle.systems.CollisionManager;
import datajungle.systems.Spritesheet;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class PC {


    //Collider collider;
    Spritesheet sheet = new Spritesheet("./assets/images/sheets/objects.png", 192, 64, 64, 64, 2);
    Collider pcCollider;

    int dataTransfered;
    int dataToTransfer;
    int dataTransferRate = 1; // How much data gets sent every .1 seconds

    long lastDataTransfer = System.currentTimeMillis();

    public int health;
    public int maxHealth;
    int x, y;
    private final Class<? extends Scene> nextLevel;

    public PC(int x, int y, Class<? extends Scene> nextLevel, int dataToTransfer, int maxHealth) {
        pcCollider = new Collider(x - 40, y, 80, 50, Settings.PC);
        this.x = x;
        this.y = y;
        this.nextLevel = nextLevel;
        this.dataToTransfer = dataToTransfer;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public void damage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            BasicGame.changeScene(new GameOverScene(BasicGame.currentScene));
        }
    }


    public void update() {
        draw();

        if (lastDataTransfer + 1000 > System.currentTimeMillis())  return;

        lastDataTransfer = System.currentTimeMillis();
        dataTransfered += dataTransferRate;


        if (dataTransfered >= dataToTransfer) {
            try {
                CollisionManager.clearColliders();
                BasicGame.changeScene((Scene) nextLevel.getConstructors()[0].newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
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
        SaxionApp.setTextDrawingColor(Color.BLACK);
        SaxionApp.drawText("PC Health", healthBarX + healthBarWidth / 2 - 68, healthBarY - 35, 30);
        SaxionApp.drawText("Defend the PC!", SaxionApp.getWidth()/2-65, 100, 20);

    }


}
