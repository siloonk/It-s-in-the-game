package datajungle.components;

import datajungle.BasicGame;
import datajungle.Collider;
import datajungle.Settings;
import datajungle.scenes.BaseScene;
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

    int health = 3; // enemy health

    int x = -w;
    int y = 550;

    Animation walkEnemyRight;

    Animation currentAnimation;

    Spritesheet EnemyMoveSheet = new Spritesheet("./assets/images/sheets/spider.png", 448, 64, 64, 32, 0);

    Collider collider = new Collider(x, y, w, h, DAMAGE);

    int speed = 1;
    boolean isGrounded = false;
    int direction = -1;
    // 64 bij 32 voor de enemy width and height

    public Enemy() {
        Animation.Builder animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(200);
        animBuilder.setAnimationSprites(EnemyMoveSheet.getImage(11), EnemyMoveSheet.getImage(12), EnemyMoveSheet.getImage(11), EnemyMoveSheet.getImage(13));
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

        collider.updateCoords(x, y);
    }

    public void damage(int damage) {
        this.health -= damage;
        if (health <= 0)
            BaseScene.killEnemy(this);
    }

    private void draw() {
        currentAnimation.update();
        Image img = currentAnimation.currentFrame;

        img.setX(x);
        img.setY(y);
        SaxionApp.add(img);
    }

    public void update() {
        draw();
        move();
    }
}
