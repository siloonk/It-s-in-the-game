package datajungle;

import datajungle.systems.CollisionManager;
import nl.saxion.app.SaxionApp;

import java.util.ArrayList;

public class Collider {


    private int x, y, width, height;



    public Collider(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        CollisionManager.addCollider(this);
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
        return this.x + xOffset < collider.x + collider.width && this.x + this.width > collider.x + xOffset && this.y + yOffset < collider.y + collider.height && this.y + this.height > collider.y + yOffset;
    }

    public boolean isColliding(ArrayList<Collider> colliders, int xOffset, int yOffset) {
        for (Collider collider : colliders) {
            if (collider == this) continue;
            if (collider.isColliding(this, xOffset, yOffset)) return true;
        }

        return false;
    }

    public boolean isColliding(ArrayList<Collider> colliders, int direction)  {
        for (Collider collider : colliders) {
            if (collider == this) continue;
            boolean isColliding = this.x + direction * 3 < collider.x + collider.width && this.x + this.width > collider.x + direction * 3 && this.y + 10 < collider.y + collider.height && this.y + this.height > collider.y + 10;
            if (isColliding) return true;
        }
        return false;
    }
}
