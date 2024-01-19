package datajungle.components;

import datajungle.Settings;
import datajungle.scenes.Scene;
import datajungle.systems.Animation;
import datajungle.systems.Collider;
import datajungle.systems.CollisionManager;
import datajungle.systems.Spritesheet;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;

import static datajungle.Settings.*;

public class VultureEnemy extends Enemy {

    Collider groundCollider = new Collider(x + 20, y + 34, 10, 30, UTIL);
    Collider collider = new Collider(x, y, 80, 64, DAMAGE);
    int yVelocity;
    int w = 80;
    int h = 64;

    long lastAttack;
    long attackTimer = 800;


    PC pc;
    Scene scene;
    boolean isGrounded = false;
    int direction;

    int maxHealth = 4;
    int speed = 1;
    int health;
    int attack = 6;

    long flyTime = 0;


    Animation enemyWalkRight;
    Animation enemyWalkLeft;
    Animation enemyDamageLeft;
    Animation enemyDamageRight;

    Animation currentAnimation; // makes an animation for all possible animations of the spider

    Spritesheet vultureWalkSpritesheet = new Spritesheet("./assets/images/sheets/vulture_walk.png", 240, 128, 80, 64);
    Spritesheet vultureAttackSpritesheet = new Spritesheet("./assets/images/sheets/vulture_attack.png", 320, 128, 80, 64);


    public VultureEnemy(int x, int y, int direction, PC pc, Scene scene) {
        super(x, y, direction);
        this.direction = direction;
        this.pc = pc;
        this.scene = scene;
        super.collider = collider;
        this.health = maxHealth;
        this.flyTime = System.currentTimeMillis() + SaxionApp.getRandomValueBetween(0, 2000);

        Animation.Builder animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(200);
        animBuilder.setAnimationSprites(vultureWalkSpritesheet.getImage(0), vultureWalkSpritesheet.getImage(1), vultureWalkSpritesheet.getImage(0), vultureWalkSpritesheet.getImage(2));
        enemyWalkRight = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(200);
        animBuilder.setAnimationSprites(vultureWalkSpritesheet.getImage(3), vultureWalkSpritesheet.getImage(4), vultureWalkSpritesheet.getImage(3), vultureWalkSpritesheet.getImage(5));
        enemyWalkLeft = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(200);
        animBuilder.setAnimationSprites(vultureAttackSpritesheet.getImage(0), vultureAttackSpritesheet.getImage(1), vultureAttackSpritesheet.getImage(2), vultureAttackSpritesheet.getImage(3));
        enemyDamageRight = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(200);
        animBuilder.setAnimationSprites(vultureAttackSpritesheet.getImage(4), vultureAttackSpritesheet.getImage(5), vultureAttackSpritesheet.getImage(6), vultureAttackSpritesheet.getImage(7));
        enemyDamageLeft = animBuilder.build(); // makes all the animations for all directions

        if (direction == 1) {currentAnimation = enemyWalkRight;}
        if (direction == -1) {currentAnimation = enemyWalkLeft;}
    }

    public void move() {

        if (flyTime < System.currentTimeMillis()) {
            isGrounded = groundCollider.isColliding(CollisionManager.getColliders(SOLID), 0, yVelocity * -1);

            //if (!isGrounded) this.y += SaxionApp.getRandomValueBetween(1, 2);
            if ((!isGrounded || yVelocity < 0)) this.y += yVelocity;

            if (!isGrounded || yVelocity < 0) {
                yVelocity += Settings.GRAVITY;
            } else {
                yVelocity = 0;
            }
        }

        this.x += this.speed * direction;

        if (lastAttack + attackTimer < System.currentTimeMillis() && (x == pc.pcCollider.getX() - w || x == pc.pcCollider.getX() + pc.pcCollider.getWidth())) {
            pc.damage(attack);
            lastAttack = System.currentTimeMillis();
        }

        if (x == pc.pcCollider.getX() - w) {
            this.x -= this.speed;
            currentAnimation = enemyDamageRight;
        }
        if (x == pc.pcCollider.getX() + pc.pcCollider.getWidth()) {
            this.x += this.speed;
            currentAnimation = enemyDamageLeft;
        }
    }

    public void draw() {
        if (isGrounded || currentAnimation.currentFrame == null)
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
        move();
        draw();

        groundCollider.updateCoords(x, y+34);
        collider.updateCoords(x, y);
    }

    @Override
    public void damage(int damage) {
        this.health -= damage;
        if (this.health < 1) scene.killEnemy(this);
    }
}
