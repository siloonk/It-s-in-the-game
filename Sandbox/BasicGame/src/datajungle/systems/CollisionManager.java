package datajungle.systems;

import java.util.ArrayList;
import java.util.HashMap;

public class CollisionManager {


    private static HashMap<Integer, ArrayList<Collider>> colliders = new HashMap<>();


    public static void addCollider(Collider collider, int action) {
        ArrayList<Collider> c = colliders.getOrDefault(action, new ArrayList<>());
        c.add(collider);
        colliders.put(action, c);
    }

    public static ArrayList<Collider> getColliders(int action) {
        return colliders.get(action);
    }

    public static void clearColliders() {
        colliders = new HashMap<>();
    }

    public static void clearColliders(int action) {
        colliders.put(action, new ArrayList<>());
    }
}
