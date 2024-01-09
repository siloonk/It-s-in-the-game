package datajungle.systems;

import nl.saxion.app.SaxionApp;

import java.util.ArrayList;

public class Collider {


    private int x, y, width, height, behaviour;


    public Collider(int x, int y, int width, int height, int behaviour) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.behaviour = behaviour;

        CollisionManager.addCollider(this, behaviour);
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw() {
        SaxionApp.drawRectangle(x, y, width, height);
    }

    public void updateCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isColliding(Collider collider, int xOffset, int yOffset) {
        return this.x < collider.x + collider.width + xOffset && this.x + this.width > collider.x + xOffset && this.y + yOffset < collider.y + collider.height && this.y + this.height > collider.y + yOffset;
    }

    public boolean isColliding(ArrayList<Collider> colliders, int xOffset, int yOffset) {
        for (Collider collider : colliders) {
            if (collider == this) continue;
            if (collider.isColliding(this, -xOffset, yOffset)) return true;
        }
        return false;
    }

    public boolean isColliding(ArrayList<Collider> colliders, int direction)  {
        for (Collider collider : colliders) {
            if (collider == this) continue;
            boolean isColliding;
            if (this.x < collider.x) {
                direction *= -1;
                isColliding = this.x + direction < collider.x + collider.width && this.x + this.width > collider.x + direction && this.y + 10 < collider.y + collider.height && this.y + this.height > collider.y + 10;
            } else {
                isColliding = this.x + direction < collider.x + collider.width && this.x + this.width > collider.x + direction && this.y + 10 < collider.y + collider.height && this.y + this.height > collider.y + 10;
            }
            if (isColliding) return true;
        }
        return false;
    }

    public float distance(Collider other) {
        return (float) (Math.max(this.x, other.x) - Math.min(this.x, other.x));
    }
}
