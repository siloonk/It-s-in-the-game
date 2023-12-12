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

    int maxHealth = 3; // max health

    int x = -w;
    int y = 0;
    int yVelocity;

    Animation enemyWalkRight;
    Animation enemyWalkLeft;
    Animation enemyDamageLeft;
    Animation enemyDamageRight;

    Animation currentAnimation;

    Spritesheet enemyMoveSheet = new Spritesheet("./assets/images/sheets/spider.png", 448, 64, 64, 32, 0);

    Collider collider = new Collider(x, y, w, h, DAMAGE);

    int speed = 1;
    boolean isGrounded = false;
    int direction = 1;
    // 64 bij 32 voor de enemy width and height

    public Enemy(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;

        Animation.Builder animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(200);
        animBuilder.setAnimationSprites(enemyMoveSheet.getImage(11), enemyMoveSheet.getImage(12), enemyMoveSheet.getImage(11), enemyMoveSheet.getImage(13));
        enemyWalkRight = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(200);
        animBuilder.setAnimationSprites(enemyMoveSheet.getImage(0), enemyMoveSheet.getImage(1), enemyMoveSheet.getImage(0), enemyMoveSheet.getImage(2));
        enemyWalkLeft = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(200);
        animBuilder.setOnce(true);
        animBuilder.setAnimationSprites(enemyMoveSheet.getImage(0), enemyMoveSheet.getImage(6));
        enemyDamageLeft = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(200);
        animBuilder.setOnce(true);
        animBuilder.setAnimationSprites(enemyMoveSheet.getImage(7), enemyMoveSheet.getImage(13));
        enemyDamageRight = animBuilder.build();

        if (direction == 1) {currentAnimation = enemyWalkRight;}
        if (direction == -1) {currentAnimation = enemyWalkLeft;}
    }
    private void move() {
        this.x += this.speed * direction;
        if (currentAnimation.isDone()) {
            currentAnimation = enemyWalkRight;
        }
        if (x == 590 - w) {
            this.x -= this.speed;
        }
        if (x == 620 + w) {
            this.x += this.speed;
        }
        // Check if the player is on the ground
        isGrounded = collider.isColliding(CollisionManager.getColliders(SOLID), 0, yVelocity * -1);

        if (!isGrounded || yVelocity < 0) {
            yVelocity += Settings.GRAVITY;
        } else {
            yVelocity = 0;
        }

        y += yVelocity;

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
        SaxionApp.setFill(Color.DARK_GRAY);
        SaxionApp.drawRectangle(x, y-8, w, 8);
        SaxionApp.setFill(Color.GREEN);
        SaxionApp.drawRectangle(x, y-8, (int) ((float) health/maxHealth * w), 8);
    }

    public void update() {
        draw();
        move();
    }
}
