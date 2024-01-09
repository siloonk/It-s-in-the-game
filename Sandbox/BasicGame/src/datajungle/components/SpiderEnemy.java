package datajungle.components;

import datajungle.scenes.Scene;
import datajungle.systems.Collider;
import datajungle.Settings;
import datajungle.scenes.ForestLevelScene;
import datajungle.systems.Animation;
import datajungle.systems.CollisionManager;
import datajungle.systems.Spritesheet;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;

import static datajungle.Settings.DAMAGE;
import static datajungle.Settings.SOLID;

public class SpiderEnemy extends Enemy {
    int w = 64;
    int h = 32;

    int health = 3; // enemy health

    int maxHealth = 3; // max health
    int yVelocity; // y velocity is needed for the above enemies to drop down

    int attackTimer = 50;

    int attack = 0;

    Animation enemyWalkRight;
    Animation enemyWalkLeft;
    Animation enemyDamageLeft;
    Animation enemyDamageRight;

    Animation currentAnimation; // makes an animation for all possible animations of the spider

    Spritesheet enemyMoveSheet = new Spritesheet("./assets/images/sheets/spider.png", 448, 64, 64, 32, 0);


    int speed = 1;
    boolean isGrounded = false;
    int direction = 1; // sets the direction for where the enemy comes from

    public SpiderEnemy(int x, int y, int direction) {
        super(x, y, direction);
        this.collider = new Collider(x, y, w, h, DAMAGE);
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
        enemyDamageRight = animBuilder.build(); // makes all the animations for all directions

        if (direction == 1) {currentAnimation = enemyWalkRight;}
        if (direction == -1) {currentAnimation = enemyWalkLeft;}
    }
    private void move() {
        this.x += this.speed * direction;

        attackTimer--;
        if (attackTimer == 0 && x == 590 - w || attackTimer == 0 && x == 620 + w) {
            ForestLevelScene.pc.damage(attack);
            System.out.println(this.x - w);
            attackTimer = 50;
        } else if (attackTimer == 0) {
            attackTimer = 1;
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

    @Override
    public void damage(int damage) {
        this.health -= damage;
        if (health <= 0)
            ForestLevelScene.killEnemy(this);
    }

    private void draw() {
        currentAnimation.update();
        Image img = currentAnimation.currentFrame;
        img.setX(x);
        img.setY(y);
        SaxionApp.add(img);

        SaxionApp.setFill(Color.DARK_GRAY); // draws the healthbar of the enemy
        SaxionApp.drawRectangle(x, y-8, w, 8);
        SaxionApp.setFill(Color.GREEN);
        SaxionApp.drawRectangle(x, y-8, (int) ((float) health / maxHealth * w), 8);
    }

    @Override
    public void update() {
        draw();
        move();
    }
}
