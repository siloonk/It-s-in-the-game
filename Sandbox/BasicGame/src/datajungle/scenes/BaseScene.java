package datajungle.scenes;

import datajungle.Collider;
import datajungle.components.PC;
import datajungle.components.Player;
import datajungle.components.Enemy;
import nl.saxion.app.SaxionApp;

import java.util.ArrayList;

import static datajungle.Settings.LADDER;
import static datajungle.Settings.SOLID;

public class BaseScene extends Scene {
    int time = 120;

    public BaseScene() {
        super("base_scene", true);
    }

    public Collider floorCollider = new Collider(0, 590, SaxionApp.getWidth(), SaxionApp.getHeight() - 700, SOLID);
    public Collider platformLeftCollider = new Collider(0, 250, 343, 70, SOLID);
    public Collider platformRightCollider = new Collider(SaxionApp.getWidth() - 343, 250, 343, 70, SOLID);
    public Collider ropeCollider = new Collider(251, 467, 32, 406, LADDER);
    public Collider ladderCollider = new Collider(985, 467, 45, 406, LADDER);

    Player player;
    PC pc;
    Enemy enemy;
    int counter = 0;

    ArrayList<Enemy> enemies = new ArrayList<>();

    @Override
    public void init() {
        player = new Player();
        pc = new PC(SaxionApp.getWidth() / 2, 467);
        enemy = new Enemy();
        enemies.add(enemy);
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
            if (counter < enemies.size()) {
                // Reset the counter if it exceeds the size of the list
                counter++;
            } else {
                // Spawn a new enemy and add it to the list
                enemy = new Enemy();
                enemies.add(enemy);
            }
            time = 120;
        } else {
            time--;
        }
    }
}
