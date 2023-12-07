package datajungle.components;

import datajungle.*;
import datajungle.systems.Animation;
import datajungle.systems.CollisionManager;
import datajungle.systems.Spritesheet;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.event.KeyEvent;

public class Player {

    int x = SaxionApp.getWidth() / 2;
    int y = SaxionApp.getHeight() / 2;

    Collider collider = new Collider(x, y, 44, 96);
    int speed = 2;
    int jumpForce = -11;
    int zVelocity = 0;
    Animation walkAnimationLeft;
    Animation walkAnimationRight;
    Animation idleAnimationleft;
    Animation idleAnimationRight;
    boolean isGrounded = false;
    boolean isJumping = false;
    int direction = -1;

    Animation currentAnimation;

    Spritesheet sheet = new Spritesheet("./assets/images/sheets/characters.png", 222, 196, 45, 96, 0);

    public Player() {
        Animation.Builder animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(300);
        animBuilder.setAnimationSprites(sheet.getImage(0), sheet.getImage(2), sheet.getImage(0), sheet.getImage(3));
        walkAnimationLeft = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(300);
        animBuilder.setAnimationSprites(sheet.getImage(5), sheet.getImage(7), sheet.getImage(5), sheet.getImage(8));
        walkAnimationRight = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(700);
        animBuilder.setAnimationSprites(sheet.getImage(0), sheet.getImage(1));
        idleAnimationleft = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(700);
        animBuilder.setAnimationSprites(sheet.getImage(5), sheet.getImage(6));
        idleAnimationRight = animBuilder.build();


        currentAnimation = idleAnimationleft;
    }

    private void move() {

        boolean[] keysPressed = BasicGame.keysPressed;

        boolean canMove = !collider.isColliding(CollisionManager.getColliders(), direction);

        // Boolean for the walking animation
        boolean hasMoved = false;

        if (keysPressed[KeyEvent.VK_D] && canMove) {
            this.x += this.speed;
            this.direction = 1;
            currentAnimation = walkAnimationRight;
            hasMoved = true;
        }

        if (keysPressed[KeyEvent.VK_A] && canMove) {
            this.x -= this.speed;
            this.direction = -1;
            currentAnimation = walkAnimationLeft;
            hasMoved = true;
        }

        if (!hasMoved && direction == -1)
            currentAnimation = idleAnimationleft;
        if (!hasMoved && direction == 1)
            currentAnimation = idleAnimationRight;

        isGrounded = collider.isColliding(CollisionManager.getColliders(), 0, 0);

        if (keysPressed[KeyEvent.VK_SPACE] && !isJumping && isGrounded) {
            isJumping = true;
            this.zVelocity = jumpForce;
        }

        if (!isGrounded || zVelocity < 0) this.y += zVelocity;

        if (!isGrounded || zVelocity < 0) {
            zVelocity += Settings.GRAVITY;
        } else {
            zVelocity = 0;
            isJumping = false;
        }

        collider.updateCoords(x, y);
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
