package datajungle.components;

import datajungle.systems.Collider;

public abstract class Enemy {

    public int x;
    public int y;
    public int w;
    public int h;
    public Collider collider;
    private int direction;

    private int health;
    private int maxHealth;

    private int attack;
    private int attackTimer;

    public Enemy(int x, int y, int w, int h, int direction) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.direction = direction;
    }

    public Enemy(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void init() {

    }

    public void update() {

    }

    public abstract void damage(int damage);

    public void close() {

    }
}