package datajungle.components;

import datajungle.BasicGame;
import datajungle.Collider;
import datajungle.scenes.MainMenuScene;
import nl.saxion.app.SaxionApp;

import java.awt.*;

public class PC {

    //Collider collider;

    int dataTransfered = 0;
    int dataToTransfer = 500;
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
        drawBox();

        if (lastDataTransfer + 100 > System.currentTimeMillis())  return;

        lastDataTransfer = System.currentTimeMillis();
        dataTransfered += dataTransferRate;
    }

    private void drawBox() {
        SaxionApp.drawRectangle(this.x, this.y, 50, 50);
    }


}
