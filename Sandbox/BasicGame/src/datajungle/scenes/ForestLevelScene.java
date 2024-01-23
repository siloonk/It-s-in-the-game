package datajungle.scenes;

import datajungle.Settings;
import datajungle.components.*;
import datajungle.systems.Collider;
import datajungle.systems.CollisionManager;
import nl.saxion.app.SaxionApp;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static datajungle.Settings.LADDER;
import static datajungle.Settings.SOLID;

public class ForestLevelScene extends Scene {
    int time = 10;

    public ForestLevelScene() {
        super("base_scene", false);
    }

    public Collider floorCollider;
    public Collider platformLeftCollider;
    public Collider platformRightCollider;
    public Collider ropeCollider;
    public Collider ladderCollider;
    Clip backgroundSound;


    Player player;
    public static PC pc;
    Enemy enemy;

    private static ArrayList<Enemy> enemies = new ArrayList<>();

    private ArrayList<Spawnpoint> spawnpoints = new ArrayList<>();


    @Override
    public void init() {
        floorCollider = new Collider(0, 590, SaxionApp.getWidth() + 420, SaxionApp.getHeight() - 700, SOLID);
        platformLeftCollider = new Collider(0, 250, 343, 70, SOLID);
        platformRightCollider = new Collider(SaxionApp.getWidth() - 343, 250, 690, 70, SOLID);
        ropeCollider = new Collider(251, 467, 32, 406, LADDER);
        ladderCollider = new Collider(985, 467, 45, 406, LADDER);

        player = new Player(Settings.selectedCharacterSheet, Settings.selectedAttackSheet, SaxionApp.getWidth()/2, SaxionApp.getHeight()/2);
        playSound("background-compressed.wav", true);
        enemies.clear();
        spawnpoints.clear();
        pc = new PC(SaxionApp.getWidth() / 2, 467, SnowLevelScene.class, 1, 100);
        enemy = new ShroomyEnemy(-100,0,-1, pc, this);
        enemies.add(enemy);
        spawnpoints.add(new Spawnpoint (-32, 523, 1));
        spawnpoints.add(new Spawnpoint (-32, 188, 1));
        spawnpoints.add(new Spawnpoint (SaxionApp.getWidth()+32, 523, -1));
        spawnpoints.add(new Spawnpoint (SaxionApp.getWidth()+32, 188, -1));
    }

    @Override
    public void update(boolean[] keysPressed) {
        SaxionApp.drawImage("./assets/images/forest_game_background.png", 0, 0);
        pc.update();
        player.update();

        for (Enemy enemy : enemies) {
            enemy.update();
        }
        if (time == 0) {
                // Spawn a new enemy and add it to the list
                int whereComeFrom = SaxionApp.getRandomValueBetween(0,4); // 0, 1, 2, 3
                Spawnpoint point = spawnpoints.get(whereComeFrom);
                enemy = new ShroomyEnemy(point.x, point.y, point.direction, pc, this);
                enemies.add(enemy);
                time = SaxionApp.getRandomValueBetween(100,250);
        } else {
            time--;
        }
    }

    @Override
    public void close() {
        CollisionManager.clearColliders(SOLID);
        backgroundSound.stop();
        backgroundSound.close();
    }


    @Override
    public void killEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    @Override
    public ArrayList<Enemy> getEnemies() {
        return enemies;
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
}
