package datajungle.scenes;

import datajungle.components.Enemy;
import datajungle.components.Player;
import datajungle.systems.CollisionManager;

import java.util.List;

public abstract class Scene {

    private String name;
    private boolean isRunning;
    public Player player;


    // Initialiseer een Scene object met een naam een een boolean isRunning
    public Scene(String name, boolean isRunning) {
        this.name = name;
        this.isRunning = isRunning;
    }

    // Wordt gecalled wanneer de scene geinitaliseert wordt.
    public void init() {

    }


    // Wordt elke frame gecalled
    public void update(boolean[] keysPressed) {

    }

    // Wordt gecalled wanneer de scene gesloten wordt
    public void close() {
        setRunning(false);
    }

    // kijk of de scene aan staat
    public boolean isRunning() {
        return isRunning;
    }

    // Verander de isRunning variable
    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    public String getName() {
        return name;
    }

    public void killEnemy(Enemy enemy) {

    }

    public List<Enemy> getEnemies() {
        return null;
    }
}
