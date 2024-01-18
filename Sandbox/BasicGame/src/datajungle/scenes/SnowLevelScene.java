package datajungle.scenes;

import datajungle.Settings;
import datajungle.components.*;
import datajungle.systems.Collider;
import datajungle.systems.CollisionManager;
import datajungle.Settings.*;
import nl.saxion.app.SaxionApp;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static datajungle.Settings.SOLID;

public class SnowLevelScene extends Scene {

    public Collider mainGroundCollider;
    public Collider secondGroundCollider;
    public Collider thirdGroundCollider;
    public Collider fourthGroundCollider;
    public Collider fifthGroundCollider;
    public Collider topleft;
    public Collider toplefttwo;
    public Collider topleftthree;

    public Collider topright;
    public Collider toprighttwo;
    public Collider toprightthree;

    Clip backgroundSound;


    private static ArrayList<Enemy> enemies = new ArrayList<>();

    private ArrayList<Spawnpoint> spawnpoints = new ArrayList<>();
    int time = 10;


    Player player;
    public static PC pc;

    public SnowLevelScene() {
        super("snow_level_scene", false);
    }

    @Override
    public void init() {
        enemies = new ArrayList<>();
        // Colliders
        mainGroundCollider = new Collider(0, 582, 1380, 138, SOLID);// onderste
        secondGroundCollider = new Collider(203, 535, 878, 49, SOLID); // 1 boven onderste
        thirdGroundCollider = new Collider(302, 487, 680, 49, SOLID); // 2 boven onderste
        fourthGroundCollider = new Collider(401, 439, 482, 49, SOLID); // 3 boven onderste
        fifthGroundCollider = new Collider(549, 391, 185, 49, SOLID); // bovenste (met tafel)
        topleft = new Collider(0, 150, 290, 182, SOLID); // onderste
        toplefttwo = new Collider(0, 150, 340, 134, SOLID); // middelste
        topleftthree = new Collider(0, 150, 390, 86, SOLID); // bovenste
        topright  = new Collider(993, 150, 286, 182, SOLID); // onderste
        toprighttwo = new Collider(944, 150, 336, 134, SOLID); // middeslte
        toprightthree = new Collider(894, 150, 440, 86, SOLID); // bovenste

        player = new Player(Settings.selectedCharacterSheet, Settings.selectedAttackSheet, SaxionApp.getWidth()/2, SaxionApp.getHeight()/2 - 100);
        pc = new PC(SaxionApp.getWidth() / 2, 274, DesertLevelScene.class, 200, 100);
        Enemy enemy = new SpiderEnemy(-100,0,-1, pc, this);
        enemies.add(enemy);
        spawnpoints.add(new Spawnpoint (-32, 555, 1));
        spawnpoints.add(new Spawnpoint (-32, 120, 1));
        spawnpoints.add(new Spawnpoint (SaxionApp.getWidth()+32, 555, -1));
        spawnpoints.add(new Spawnpoint (SaxionApp.getWidth()+32, 120, -1));
        playSound("Sneeuw_level.wav", true);
    }

    @Override
    public void killEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    @Override
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public void update(boolean[] keysPressed) {
        System.out.println(getEnemies().size());
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
        for (Collider c: CollisionManager.getColliders(SOLID)) {
            c.draw();
        }
    }

    public void playSound(String soundFile, boolean loop){
        try {
            File f = new File("./assets/sounds/" + soundFile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            backgroundSound = clip;
            if (loop) backgroundSound.loop(-1);
            else backgroundSound.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void close() {
        super.close();
        backgroundSound.stop();
        backgroundSound.close();
    }
}
