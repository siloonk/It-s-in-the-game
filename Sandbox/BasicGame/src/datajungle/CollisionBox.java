package datajungle;

import nl.saxion.app.SaxionApp;

import java.util.ArrayList;

public class CollisionBox {


    int x;
    int y;
    int width;
    int height;
    boolean isGround;


    public CollisionBox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        isGround = false;


        // Register all colliders in the collisions manager
        CollisionManager.addCollider(this);
    }

    public CollisionBox(int x, int y, int width, int height, boolean isGround) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.isGround = isGround;

        CollisionManager.addCollider(this);
    }

    public void draw() {
        SaxionApp.drawRectangle(this.x, this.y, this.width, this.height);
    }


    public boolean isColliding(CollisionBox other) {
        return this.x<other.x + other.width && this.x + this.width > other.x;
    }

    public boolean isColliding(ArrayList<CollisionBox> colliders) {
        for (CollisionBox other : colliders) {
            if ((other == this)) continue;
            if (other.isGround) continue;
            if (this.x<other.x + other.width && this.x + this.width > other.x)
                return true;
        }
        return false;
    }

    public boolean isOnGround(ArrayList<CollisionBox> colliders) {
        for (CollisionBox other : colliders) {
            if (other == this) continue;
            if (!other.isGround) continue;
            if (this.y < other.y + other.height && this.y + this.height > other.y)
                return true;
        }
        return false;
    }

    public void updateCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
