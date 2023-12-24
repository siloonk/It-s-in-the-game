package datajungle.components;

public abstract class Enemy {

    private int x;
    private int y;
    private int w;
    private int h;
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

    public void update(boolean[] keysPressed) {

    }

    public void close() {

    }
}