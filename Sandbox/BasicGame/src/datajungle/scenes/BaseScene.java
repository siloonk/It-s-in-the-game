package datajungle.scenes;

import datajungle.Collider;
import datajungle.components.PC;
import datajungle.components.Player;
import datajungle.components.Enemy;
import datajungle.components.Spawnpoint;
import nl.saxion.app.SaxionApp;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static datajungle.Settings.LADDER;
import static datajungle.Settings.SOLID;

public class BaseScene extends Scene {
    int time = 10;

    public BaseScene() {
        super("base_scene", true);
    }

    public Collider floorCollider = new Collider(0, 590, SaxionApp.getWidth() + 420, SaxionApp.getHeight() - 700, SOLID);
    public Collider platformLeftCollider = new Collider(0, 250, 343, 70, SOLID);
    public Collider platformRightCollider = new Collider(SaxionApp.getWidth() - 343, 250, 690, 70, SOLID);
    public Collider ropeCollider = new Collider(251, 467, 32, 406, LADDER);
    public Collider ladderCollider = new Collider(985, 467, 45, 406, LADDER);

    Player player;
    PC pc;
    Enemy enemy;

    private static ArrayList<Enemy> enemies = new ArrayList<>();

    private ArrayList<Spawnpoint> spawnpoints = new ArrayList<>();

    @Override
    public void init() {
        player = new Player();
        pc = new PC(SaxionApp.getWidth() / 2, 467);
        enemy = new Enemy(-100,0,-1);
        enemies.add(enemy);
        spawnpoints.add(new Spawnpoint (-32, 555, 1));
        spawnpoints.add(new Spawnpoint (-32, 220, 1));
        spawnpoints.add(new Spawnpoint (SaxionApp.getWidth()+32, 555, -1));
        spawnpoints.add(new Spawnpoint (SaxionApp.getWidth()+32, 220, -1));
    }

    @Override
    public void update(boolean[] keysPressed) {
        SaxionApp.drawImage("./assets/images/gameBackground.png", 0, 0);
        pc.update();
        player.update();

        for (Enemy enemy : enemies) {
            enemy.update();
        }
        if (time == 0) {
                // Spawn a new enemy and add it to the list
                int whereComeFrom = SaxionApp.getRandomValueBetween(0,4); // 0, 1, 2, 3
                Spawnpoint point = spawnpoints.get(whereComeFrom);
                enemy = new Enemy(point.x, point.y, point.direction);
                enemies.add(enemy);
                time = 250;
        } else {
            time--;
        }
    }

    public static void killEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }


    public static ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
