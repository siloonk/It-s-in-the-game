package datajungle;

public class CollisionBox {


    int x, y, width, height;


    public CollisionBox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }



    public boolean isCollising(CollisionBox other) {
        return this.x<other.x + other.width && this.x + this.width > other.x && this.y < other.y + other.height && this.y + this.height > other.y;
    }
}
