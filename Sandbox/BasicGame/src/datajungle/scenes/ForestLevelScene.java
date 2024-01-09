package datajungle.scenes;

import datajungle.components.*;
import datajungle.systems.Collider;
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
        super("base_scene", true);
    }

    public Collider floorCollider = new Collider(0, 590, SaxionApp.getWidth() + 420, SaxionApp.getHeight() - 700, SOLID);
    public Collider platformLeftCollider = new Collider(0, 250, 343, 70, SOLID);
    public Collider platformRightCollider = new Collider(SaxionApp.getWidth() - 343, 250, 690, 70, SOLID);
    public Collider ropeCollider = new Collider(251, 467, 32, 406, LADDER);
    public Collider ladderCollider = new Collider(985, 467, 45, 406, LADDER);
    Clip backgroundSound;


    Player player;
    public static PC pc;
    Enemy enemy;

    private static ArrayList<Enemy> enemies = new ArrayList<>();

    private ArrayList<Spawnpoint> spawnpoints = new ArrayList<>();


    @Override
    public void init() {
        player = new Player("./assets/images/sheets/characters_ruben.png", "./assets/images/sheets/player_attack_sil.png");
        playSound("background.wav", true);
        enemies.clear();
        spawnpoints.clear();
        pc = new PC(SaxionApp.getWidth() / 2, 467, ForestLevelScene.class);
        enemy = new SpiderEnemy(-100,0,-1);
        enemies.add(enemy);
        spawnpoints.add(new Spawnpoint (-32, 555, 1));
        spawnpoints.add(new Spawnpoint (-32, 220, 1));
        spawnpoints.add(new Spawnpoint (SaxionApp.getWidth()+32, 555, -1));
        spawnpoints.add(new Spawnpoint (SaxionApp.getWidth()+32, 220, -1));
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
                enemy = new SpiderEnemy(point.x, point.y, point.direction);
                enemies.add(enemy);
                time = SaxionApp.getRandomValueBetween(100,250);
        } else {
            time--;
        }
    }

    @Override
    public void close() {
        backgroundSound.stop();
        backgroundSound.close();
    }


    public static void killEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }


    public static ArrayList<Enemy> getEnemies() {
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
