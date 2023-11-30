package datajungle;

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
    int jumpHeight = 70;
    Animation walkAnimation;
    boolean isGrounded = true;
    boolean isJumping = false;

    Spritesheet sheet = new Spritesheet();

    public Player() {


        Animation.Builder animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(100);
        walkAnimation = animBuilder.build();
    }


    private void move() {

        boolean[] keysPressed = BasicGame.keysPressed;

        if (keysPressed[KeyEvent.VK_D]) {
            this.x += this.speed;
        }

        if (keysPressed[KeyEvent.VK_A]) {
            this.x -= this.speed;
        }

        if (keysPressed[KeyEvent.VK_SPACE] && !isJumping && isGrounded) {
            isJumping = true;
            isGrounded = false;



        }


        if ((this.y + collisionBox.height - Settings.GRAVITY < Settings.JUNGLE_FLOOR_LEVEL) && !isJumping) {
            isGrounded = false;
            this.y += Settings.GRAVITY;
        } else if (!isJumping){
            isGrounded = true;
        }
    }

    private void draw() {
        Image img = sheet.getImage(0);
        img.setX(x);
        img.setY(y);
        SaxionApp.add(img);
    }

    public void update() {
        draw();
        move();
        //animate();
    }
}
