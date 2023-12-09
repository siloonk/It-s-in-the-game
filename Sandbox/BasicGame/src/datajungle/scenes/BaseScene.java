package datajungle.scenes;

import datajungle.Collider;
import datajungle.components.PC;
import datajungle.components.Player;
import datajungle.components.Enemy;
import nl.saxion.app.SaxionApp;

import java.util.ArrayList;

import static datajungle.Settings.SOLID;

public class BaseScene extends Scene {
    int time = 200;

    public BaseScene() {
        super("base_scene", true);
    }

    public Collider floorCollider = new Collider(0, 590, SaxionApp.getWidth(), SaxionApp.getHeight() - 700, SOLID);
    public Collider platformLeftCollider = new Collider(0, 250, 343, 70, SOLID);
    public Collider platformRightCollider = new Collider(SaxionApp.getWidth() - 343, 250, 343, 70, SOLID);

    Player player;
    PC pc;
    Enemy enemy;

    @Override
    public void init() {
        player = new Player();
        pc = new PC(SaxionApp.getWidth() / 2, 467);
        enemy = new Enemy();
    }

    @Override
    public void update(boolean[] keysPressed) {
        SaxionApp.drawImage("./assets/images/gameBackground.png", 0, 0);
        pc.update();
        player.update();
        enemy.update();
        if (time == 0) {
            enemy = new Enemy();
            time = 200;
        } else {time--;}
    }
}
