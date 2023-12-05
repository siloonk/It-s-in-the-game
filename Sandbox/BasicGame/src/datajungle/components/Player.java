package datajungle.components;

import datajungle.*;
import datajungle.scenes.BaseScene;
import datajungle.systems.Spritesheet;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {

    int x = SaxionApp.getWidth() / 2;
    int y = SaxionApp.getHeight() / 2;

    CollisionBox collisionBox = new CollisionBox(x, y,44, 96);
    int speed = 2;
    int jumpForce = -15;
    int zVelocity = 0;
    Animation walkAnimation;
    boolean isGrounded = false;
    boolean isJumping = false;

    Spritesheet sheet = new Spritesheet("./assets/images/sheets/characters.png", 256, 96, 44, 96);

    public Player() {
        Animation.Builder animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(300);
        animBuilder.setAnimationSprites(sheet.getImage(0), sheet.getImage(1), sheet.getImage(0), sheet.getImage(2));
        walkAnimation = animBuilder.build();
    }

    private void move() {

        boolean[] keysPressed = BasicGame.keysPressed;

        boolean canMove = !collisionBox.isColliding(CollisionManager.getColliders());

        if (keysPressed[KeyEvent.VK_D] && canMove) {
            this.x += this.speed;
        }

        if (keysPressed[KeyEvent.VK_A] && canMove) {
            this.x -= this.speed;
        }

        if (keysPressed[KeyEvent.VK_SPACE] && !isJumping && isGrounded) {
            isJumping = true;
            this.zVelocity = jumpForce;
        }

        if (canMove) this.y += zVelocity;

        isGrounded = collisionBox.isOnGround(CollisionManager.getColliders());

        if (!isGrounded) {
            zVelocity += Settings.GRAVITY;
        } else {
            zVelocity = 0;
            isJumping = false;
        }

        collisionBox.updateCoords(x, y);
    }

    private void draw() {
        walkAnimation.update();
        Image img = walkAnimation.currentFrame;


        img.setX(x);
        img.setY(y);
        SaxionApp.add(img);
    }

    public void update() {
        draw();
        move();
    }
}
