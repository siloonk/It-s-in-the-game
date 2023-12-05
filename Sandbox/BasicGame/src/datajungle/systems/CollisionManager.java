package datajungle.systems;

import datajungle.Collider;

import java.util.ArrayList;

public class CollisionManager {



    private static ArrayList<Collider> colliders = new ArrayList<>();


    public static void addCollider(Collider collider) {
        colliders.add(collider);
    }

    public static ArrayList<Collider> getColliders() {
        return colliders;
    }
}
