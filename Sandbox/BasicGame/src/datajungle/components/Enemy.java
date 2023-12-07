package datajungle.components;

import datajungle.BasicGame;
import datajungle.Collider;
import datajungle.Settings;
import datajungle.systems.Animation;
import datajungle.systems.CollisionManager;
import datajungle.systems.Spritesheet;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Enemy {
    int w = 64;
    int h = 32;

    int x = -w;
    int y = 540;
    Collider collider = new Collider(x, y, w, h);
    int speed = 1;
    boolean isGrounded = false;
    int direction = -1;
    // 64 bij 32 voor de enemy width and height
    private void move() {

        boolean[] keysPressed = BasicGame.keysPressed;

        boolean canMove = !collider.isColliding(CollisionManager.getColliders(), direction);


        if (canMove) {
            this.x += this.speed;
            this.direction = 1;
        } if (x == 590 - w) {
            this.x -= this.speed;
        }

        isGrounded = collider.isColliding(CollisionManager.getColliders(), 0, 0);

        collider.updateCoords(x, y);
    }

    private void draw() {
        SaxionApp.setFill(Color.WHITE);
        SaxionApp.drawRectangle(x, y, w, h);
    }

    public void update() {
        draw();
        move();
    }
}
