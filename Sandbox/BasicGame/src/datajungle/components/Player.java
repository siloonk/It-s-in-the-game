package datajungle.components;

import datajungle.*;
import datajungle.systems.Animation;
import datajungle.systems.CollisionManager;
import datajungle.systems.Spritesheet;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static datajungle.Settings.*;

public class Player {

    int x = SaxionApp.getWidth() / 2;
    int y = SaxionApp.getHeight() / 2;

    Collider collider = new Collider(x, y, 44, 96, DAMAGE);
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
    boolean isOnLadder = false;
    Collider usedLadder = null;

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

        boolean canMove = !collider.isColliding(CollisionManager.getColliders(SOLID), direction);

        // Boolean for the walking animation
        boolean hasMoved = false;

        if (keysPressed[KeyEvent.VK_D] && canMove && !isOnLadder) {
            this.x += this.speed;
            this.direction = 1;
            currentAnimation = walkAnimationRight;
            hasMoved = true;
        }

        if (keysPressed[KeyEvent.VK_A] && canMove && !isOnLadder) {
            this.x -= this.speed;
            this.direction = -1;
            currentAnimation = walkAnimationLeft;
            hasMoved = true;
        }

        Collider ladderInRange = null;
        // Check if there are ladders in range
        ArrayList<Collider> colliders = CollisionManager.getColliders(LADDER);
        for (Collider c : colliders) {
            System.out.println(collider.distance(c));
            if (collider.distance(c) < 40) {
                SaxionApp.setTextDrawingColor(Color.BLACK);
                SaxionApp.drawText("Press E to use ladder!", c.getX() - 100, c.getY(), 24);
                ladderInRange = c;
            }
        }

        // Check if the player is on a ladder, if so move and check if they have exceeded the ladder's height
        if (keysPressed[KeyEvent.VK_W] && isOnLadder) {
            this.y -= 2;

            if (this.y - this.collider.getHeight() < usedLadder.getY() - usedLadder.getHeight()) {
                isOnLadder = false;
            }
        }

        // Check if the player is in range of a ladder and if so clamp to the ladder
        if (keysPressed[KeyEvent.VK_E] && (ladderInRange != null)) {
            isOnLadder = true;
            usedLadder = ladderInRange;
        }


        // Change idle animations
        if (!hasMoved && direction == -1)
            currentAnimation = idleAnimationleft;
        if (!hasMoved && direction == 1)
            currentAnimation = idleAnimationRight;


        // Check if the player is on the ground
        isGrounded = collider.isColliding(CollisionManager.getColliders(SOLID), 0, zVelocity * -1);


        // Check if the player is allowed to jump
        if (keysPressed[KeyEvent.VK_SPACE] && !isJumping && isGrounded && !isOnLadder) {
            isJumping = true;
            this.zVelocity = jumpForce;
        }

        // Check if the player player should be able to move according to the zVelocity
        if ((!isGrounded || zVelocity < 0) && !isOnLadder) this.y += zVelocity;

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
