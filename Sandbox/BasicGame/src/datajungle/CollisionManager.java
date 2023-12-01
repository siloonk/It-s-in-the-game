package datajungle;

import java.util.ArrayList;

public class CollisionManager {



    private static ArrayList<CollisionBox> colliders = new ArrayList<>();


    public static void addCollider(CollisionBox collider) {
        colliders.add(collider);
    }

    public static ArrayList<CollisionBox> getColliders() {
        return colliders;
    }
}
