package datajungle.components;

import datajungle.scenes.Scene;
import datajungle.scenes.SnowLevelScene;
import datajungle.systems.Collider;
import datajungle.Settings;
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

    long attackTimer = 900;
    long lastAttack;
    int jumpForce = -11;

    long playerAttackCooldown = 1000;
    long lastPlayerAttack;

    int attack = 5;

    Animation enemyWalkRight;
    Animation enemyWalkLeft;
    Animation enemyDamageLeft;
    Animation enemyDamageRight;

    Animation currentAnimation; // makes an animation for all possible animations of the spider

    Spritesheet enemyMoveSheet = new Spritesheet("./assets/images/sheets/spider.png", 448, 64, 64, 32, 0);


    int speed = 1;
    boolean isGrounded = false;
    int direction = 1; // sets the direction for where the enemy comes from
    PC pc;
    Scene scene;

    public SpiderEnemy(int x, int y, int direction, PC pc, Scene scene) {
        super(x, y, direction);
        this.pc = pc;
        this.collider = new Collider(x, y, w, h, DAMAGE);
        this.direction = direction;
        this.scene = scene;

        Animation.Builder animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(200);
        animBuilder.setAnimationSprites(enemyMoveSheet.getImage(11), enemyMoveSheet.getImage(12), enemyMoveSheet.getImage(11), enemyMoveSheet.getImage(13));
        enemyWalkRight = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(200);
        animBuilder.setAnimationSprites(enemyMoveSheet.getImage(0), enemyMoveSheet.getImage(1), enemyMoveSheet.getImage(0), enemyMoveSheet.getImage(2));
        enemyWalkLeft = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(300);
        animBuilder.setAnimationSprites(enemyMoveSheet.getImage(5), enemyMoveSheet.getImage(3), enemyMoveSheet.getImage(4));
        enemyDamageLeft = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(300);
        animBuilder.setAnimationSprites(enemyMoveSheet.getImage(8), enemyMoveSheet.getImage(10), enemyMoveSheet.getImage(9));
        enemyDamageRight = animBuilder.build(); // makes all the animations for all directions

        if (direction == 1) {currentAnimation = enemyWalkRight;}
        if (direction == -1) {currentAnimation = enemyWalkLeft;}
    }
    private void move() {
        this.x += this.speed * direction;

        if (lastAttack + attackTimer < System.currentTimeMillis() && (x == pc.pcCollider.getX() - w || x == pc.pcCollider.getX() + pc.pcCollider.getWidth())) {
            pc.damage(attack);
            lastAttack = System.currentTimeMillis();
        }
        if (scene instanceof SnowLevelScene) {
            if ((x == 203 - w || x == 302 - w || x == 401 - w || x == 549 - w || x == 734 || x == 883 || x == 982 || x == 1081) && y > 200) {
                yVelocity = jumpForce;
            }
        }

        if (x == pc.pcCollider.getX() - w) {
            this.x -= this.speed;
            currentAnimation = enemyDamageRight;
        }
        if (x == pc.pcCollider.getX() + pc.pcCollider.getWidth()) {
            this.x += this.speed;
            currentAnimation = enemyDamageLeft;
        }
        // Check if the enemy is on the ground
        if (CollisionManager.getColliders(SOLID) == null) return;
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
            scene.killEnemy(this);
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
