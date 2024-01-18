package datajungle.scenes;

import datajungle.Settings;
import datajungle.components.Enemy;
import datajungle.components.PC;
import datajungle.components.Player;
import datajungle.systems.Collider;
import nl.saxion.app.SaxionApp;

import java.util.ArrayList;
import java.util.List;

public class DesertLevelScene extends Scene{

    List<Enemy> enemies;
    Collider groundCollider;

    Player player;
    PC pc;

    public DesertLevelScene() {
        super("desert_level_scene", false);
    }

    @Override
    public void init() {
        enemies = new ArrayList<>();
        groundCollider = new Collider(0, 588, 1300, 132, Settings.SOLID);
        player = new Player(Settings.selectedCharacterSheet, Settings.selectedAttackSheet, SaxionApp.getWidth()/2, 500);
        pc = new PC(SaxionApp.getWidth() / 2, 469, SnowLevelScene.class, 150, 100);
    }

    @Override
    public void update(boolean[] keysPressed) {
        SaxionApp.drawImage("./assets/images/desert_background.png", 0, 0);
        pc.update();

        player.update();
    }

    @Override
    public void killEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    @Override
    public List<Enemy> getEnemies() {
        return enemies;
    }
}
