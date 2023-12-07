package datajungle.scenes;

import datajungle.Collider;
import datajungle.components.PC;
import datajungle.components.Player;
import nl.saxion.app.SaxionApp;

import java.util.ArrayList;

public class BaseScene extends Scene {

    public BaseScene() {
        super("base_scene", true);
    }

    public Collider floorCollider = new Collider(0, 590, SaxionApp.getWidth(), SaxionApp.getHeight() - 700);
    public Collider platformLeftCollider = new Collider(0, 250, 343, 70);
    public Collider platformRightCollider = new Collider(SaxionApp.getWidth() - 343, 250, 343, 70);

    Player player;
    PC pc;

    @Override
    public void init() {
        player = new Player();
        pc = new PC(SaxionApp.getWidth() / 2, 467);
    }

    @Override
    public void update(boolean[] keysPressed) {
        SaxionApp.drawImage("./assets/images/gameBackground.png", 0, 0);
        pc.update();
        player.update();

    }
}
