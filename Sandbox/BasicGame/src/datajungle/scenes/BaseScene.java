package datajungle.scenes;

import datajungle.Collider;
import datajungle.components.PC;
import datajungle.components.Player;
import datajungle.components.Enemy;
import nl.saxion.app.SaxionApp;

import java.util.ArrayList;

public class BaseScene extends Scene {

    public BaseScene() {
        super("base_scene", true);
    }

    //public CollisionBox floorCollider = new CollisionBox(0, 570, SaxionApp.getWidth(), SaxionApp.getHeight() - 590, true);
    public Collider floorCollider = new Collider(0, 590, SaxionApp.getWidth(), SaxionApp.getHeight() - 700);


    Player player;
    PC pc;
    Enemy enemy;

    @Override
    public void init() {
        player = new Player();
        pc = new PC(SaxionApp.getWidth() / 2, 520);
        enemy = new Enemy();
    }

    @Override
    public void update(boolean[] keysPressed) {
        SaxionApp.drawImage("./assets/images/gameBackground.png", 0, 0);
        pc.update();
        player.update();
        enemy.update();

    }
}
