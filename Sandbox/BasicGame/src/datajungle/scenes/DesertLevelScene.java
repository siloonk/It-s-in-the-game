package datajungle.scenes;

import datajungle.Settings;
import datajungle.components.*;
import datajungle.systems.Collider;
import nl.saxion.app.SaxionApp;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DesertLevelScene extends Scene{

    int time = 10;

    Collider groundCollider;

    Player player;
    PC pc;
    Clip backgroundSound;

    private static ArrayList<Enemy> enemies = new ArrayList<>();

    private ArrayList<Spawnpoint> spawnpoints = new ArrayList<>();

    public DesertLevelScene() {
        super("desert_level_scene", false);
    }

    @Override
    public void init() {
        enemies = new ArrayList<>();
        groundCollider = new Collider(0, 588, 1300, 132, Settings.SOLID);
        player = new Player(Settings.selectedCharacterSheet, Settings.selectedAttackSheet, SaxionApp.getWidth()/2, 480);
        pc = new PC(SaxionApp.getWidth() / 2, 469, GameCompletedScene.class, 1, 100);
        playSound("Desert_level.wav", true);
        spawnpoints.add(new Spawnpoint(0, 100, 1));
        spawnpoints.add(new Spawnpoint(SaxionApp.getWidth()-80, 100, -1));
    }

    @Override
    public void update(boolean[] keysPressed) {
        SaxionApp.drawImage("./assets/images/desert_background.png", 0, 0);

        for (Enemy enemy : enemies) {
            enemy.update();
        }

        if (time == 0) {
            // Spawn a new enemy and add it to the list
            int whereComeFrom = SaxionApp.getRandomValueBetween(0,spawnpoints.size());
            Spawnpoint point = spawnpoints.get(whereComeFrom);
            Enemy enemy = new VultureEnemy(point.x, point.y, point.direction, pc, this);
            enemies.add(enemy);
            time = SaxionApp.getRandomValueBetween(100,250);
        } else {
            time--;
        }

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
