package datajungle.scenes;

import datajungle.components.*;
import datajungle.systems.Collider;
import datajungle.systems.CollisionManager;
import nl.saxion.app.SaxionApp;

import java.awt.*;
import java.util.ArrayList;

import static datajungle.Settings.SOLID;

public class SnowLevelScene extends Scene {

    public Collider mainGroundCollider = new Collider(0, 582, 1380, 138, SOLID);
    public Collider secondGroundCollider = new Collider(203, 535, 878, 49, SOLID);
    public Collider thirdGroundCollider = new Collider(302, 487, 680, 49, SOLID);
    public Collider fourthGroundCollider = new Collider(401, 439, 482, 49, SOLID);
    public Collider fifthGroundCollider = new Collider(549, 391, 185, 49, SOLID);
    public Collider topleft = new Collider(0, 150, 290, 182, SOLID);
    public Collider toplefttwo = new Collider(0, 150, 340, 134, SOLID);
    public Collider topleftthree = new Collider(0, 150, 390, 86, SOLID);

    public Collider topright = new Collider(993, 150, 286, 182, SOLID); // onderste
    public Collider toprighttwo = new Collider(944, 150, 336, 134, SOLID); // middeslte
    public Collider toprightthree = new Collider(894, 150, 440, 86, SOLID); // bovenste


    private static ArrayList<Enemy> enemies = new ArrayList<>();

    private ArrayList<Spawnpoint> spawnpoints = new ArrayList<>();
    int time = 10;


    Player player;
    PC pc;

    public SnowLevelScene() {
        super("snow_level_scene", false);
    }

    @Override
    public void init() {
        player = new Player("./assets/images/sheets/characters_sil.png", "./assets/images/sheets/player_attack_sil.png");
        pc = new PC(SaxionApp.getWidth() / 2, 274, SnowLevelScene.class, 150, 75);
        Enemy enemy = new SpiderEnemy(-100,0,-1, pc, this);
        enemies.add(enemy);
        spawnpoints.add(new Spawnpoint (-32, 555, 1));
        spawnpoints.add(new Spawnpoint (-32, 120, 1));
        spawnpoints.add(new Spawnpoint (SaxionApp.getWidth()+32, 555, -1));
        spawnpoints.add(new Spawnpoint (SaxionApp.getWidth()+32, 120, -1));
    }


    @Override
    public void update(boolean[] keysPressed) {
        SaxionApp.drawImage("./assets/images/snowy_game_background.png", 0, 0);
        pc.update();

        for (Enemy enemy : enemies) {
            enemy.update();
        }
        if (time == 0) {
            // Spawn a new enemy and add it to the list
            int whereComeFrom = SaxionApp.getRandomValueBetween(0,4); // 0, 1, 2, 3
            Spawnpoint point = spawnpoints.get(whereComeFrom);
            Enemy enemy = new SpiderEnemy(point.x, point.y, point.direction, pc, this);
            enemies.add(enemy);
            time = SaxionApp.getRandomValueBetween(100,250);
        } else {
            time--;
        }

        player.update();
    }
}
