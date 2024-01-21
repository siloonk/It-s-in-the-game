package datajungle.components;

import datajungle.scenes.Scene;
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

public class ShroomyEnemy extends Enemy {
    int w = 100;
    int h = 64;

    int health = 2; // enemy health

    int maxHealth = 2; // max health
    int yVelocity; // y velocity is needed for the above enemies to drop down

    long attackTimer = 900;
    long lastAttack;

    long playerAttackCooldown = 1000;
    long lastPlayerAttack;

    int attack = 4;

    Animation enemyWalkRight;
    Animation enemyWalkLeft;
    Animation enemyDamageLeft;
    Animation enemyDamageRight;

    Animation currentAnimation; // makes an animation for all possible animations of the spider

    Spritesheet enemyMoveSheet = new Spritesheet("./assets/images/sheets/shroomy_walk.png", 300, 128, 100, 64, 0);
    Spritesheet enemyAttackSheet = new Spritesheet("./assets/images/sheets/shroomy_attack.png", 300, 128, 100, 64, 0);


    int speed = 1;
    boolean isGrounded = false;
    int direction = 1; // sets the direction for where the enemy comes from
    PC pc;
    Scene scene;

    public ShroomyEnemy(int x, int y, int direction, PC pc, Scene scene) {
        super(x, y, direction);
        this.pc = pc;
        this.collider = new Collider(x, y, w, h, DAMAGE);
        this.direction = direction;
        this.scene = scene;

        Animation.Builder animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(200);
        animBuilder.setAnimationSprites(enemyMoveSheet.getImage(0), enemyMoveSheet.getImage(1), enemyMoveSheet.getImage(0), enemyMoveSheet.getImage(2));
        enemyWalkLeft = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(200);
        animBuilder.setAnimationSprites(enemyMoveSheet.getImage(3), enemyMoveSheet.getImage(4), enemyMoveSheet.getImage(3), enemyMoveSheet.getImage(5));
        enemyWalkRight = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(300);
        animBuilder.setAnimationSprites(enemyMoveSheet.getImage(0), enemyAttackSheet.getImage(0), enemyAttackSheet.getImage(1), enemyAttackSheet.getImage(2));
        enemyDamageLeft = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(300);
        animBuilder.setAnimationSprites(enemyMoveSheet.getImage(3), enemyAttackSheet.getImage(3), enemyAttackSheet.getImage(4), enemyAttackSheet.getImage(5));
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



        // Check of enemy bij player is
        if (scene.player != null && collider.isColliding(scene.player.collider, 0, 0) && this.health > 0) {
            // Check of enemy player mag damagen
            if (lastPlayerAttack + playerAttackCooldown < System.currentTimeMillis()) {
                if (direction == 1) currentAnimation = enemyDamageRight;
                if (direction == -1) currentAnimation = enemyDamageLeft;
                scene.player.damage(1);

                // 30% chance to inflict bleeding
                int random = SaxionApp.getRandomValueBetween(0, 100);
                if (random < 30) scene.player.applyPoison();

                lastPlayerAttack = System.currentTimeMillis();
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

        if (direction == 1) {
            SaxionApp.setFill(Color.DARK_GRAY); // draws the healthbar of the enemy
            SaxionApp.drawRectangle(x + 4, y - 8, w / 2, 8);
            SaxionApp.setFill(Color.GREEN);
            SaxionApp.drawRectangle(x + 4, y - 8, (int) ((float) health / maxHealth * w / 2), 8);
        }
        else {
            SaxionApp.setFill(Color.DARK_GRAY); // draws the healthbar of the enemy
            SaxionApp.drawRectangle(x + 46, y - 8, w / 2, 8);
            SaxionApp.setFill(Color.GREEN);
            SaxionApp.drawRectangle(x + 46, y - 8, (int) ((float) health / maxHealth * w / 2), 8);
        }
    }

    @Override
    public void update() {
        draw();
        move();
    }
}
