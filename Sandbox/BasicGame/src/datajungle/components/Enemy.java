package datajungle.components;

import datajungle.BasicGame;
import datajungle.Collider;
import datajungle.Settings;
import datajungle.systems.Animation;
import datajungle.systems.CollisionManager;
import datajungle.systems.Spritesheet;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;
import java.awt.event.KeyEvent;

import static datajungle.Settings.DAMAGE;
import static datajungle.Settings.SOLID;

public class Enemy {
    int w = 64;
    int h = 32;

    int x = -w;
    int y = 540;

    Animation walkEnemyRight;

    Animation currentAnimation;

    Spritesheet EnemyMoveSheet = new Spritesheet("./assets/images/sheets/spider.png", 222, 196, 64, 32, 0);

    Collider collider = new Collider(x, y, w, h, DAMAGE);

    int speed = 1;
    boolean isGrounded = false;
    int direction = -1;
    // 64 bij 32 voor de enemy width and height

    public Enemy() {
        Animation.Builder animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(200);
        animBuilder.setAnimationSprites(EnemyMoveSheet.getImage(0), EnemyMoveSheet.getImage(2));
        walkEnemyRight = animBuilder.build();
        currentAnimation = walkEnemyRight;
    }
    private void move() {
        this.x += this.speed;
        this.direction = 1;
        if (currentAnimation.isDone()) {
            currentAnimation = walkEnemyRight;
        }
        if (x == 590 - w) {
            this.x -= this.speed;
        }
    }

    private void draw() {
        SaxionApp.turnBorderOn();
        SaxionApp.setFill(Color.WHITE);
        SaxionApp.drawRectangle(x, y, w, h);
    }

    public void update() {
        draw();
        move();
    }
}
