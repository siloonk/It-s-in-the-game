package datajungle.components;

import datajungle.BasicGame;
import datajungle.Collider;
import datajungle.scenes.MainMenuScene;
import datajungle.systems.Animation;
import datajungle.systems.Spritesheet;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;

public class PC {

    //Collider collider;
    Spritesheet sheet = new Spritesheet("./assets/images/sheets/objects.png", 96, 64, 32, 64, 3);

    int dataTransfered = 0;
    int dataToTransfer = 500;
    int dataTransferRate = 1; // How much data gets sent every .1 seconds

    long lastDataTransfer = System.currentTimeMillis();
    Animation dataTransferAnimation;
    Animation currentAnimation;

    int health = 50;

    int x, y;

    public PC(int x, int y) {

        //collider = new Collider(x, y, 50, 50);
        this.x = x;
        this.y = y;

        Animation.Builder animBuiler = new Animation.Builder();
        animBuiler.setAnimationSwitchDelay(1000);
        animBuiler.setAnimationSprites(sheet.getImage(1), sheet.getImage(0));
        dataTransferAnimation = animBuiler.build();

        currentAnimation = dataTransferAnimation;
    }

    public void damage(int damage) {
        this.health -= health;
        if (this.health <= 0) {
            BasicGame.changeScene(new MainMenuScene());
        }
    }


    public void update() {
        currentAnimation.update();
        draw();

        if (lastDataTransfer + 1000 > System.currentTimeMillis())  return;

        lastDataTransfer = System.currentTimeMillis();
        dataTransfered += dataTransferRate;

        System.out.println(dataTransfered);
        if (dataTransfered >= dataToTransfer) {
            System.out.println("You have won!");
        }
    }

    private void draw() {
        Image img = currentAnimation.currentFrame;
        img.setX(x);
        img.setY(y);
        SaxionApp.add(img);
    }


}
