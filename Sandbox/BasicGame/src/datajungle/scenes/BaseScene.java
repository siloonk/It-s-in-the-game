package datajungle.scenes;

import datajungle.CollisionBox;
import datajungle.components.PC;
import datajungle.components.Player;
import nl.saxion.app.SaxionApp;

import java.util.ArrayList;

public class BaseScene extends Scene {

    public BaseScene() {
        super("base_scene", true);
    }

    ArrayList<CollisionBox> collisons = new ArrayList<CollisionBox>();
    public CollisionBox floorCollider = new CollisionBox(0, 570, SaxionApp.getWidth(), SaxionApp.getHeight() - 590, true);
    Player player;
    PC pc;

    @Override
    public void init() {
        player = new Player();
        //pc = new PC(, 1, 300, 10);
    }

    @Override
    public void update(boolean[] keysPressed) {
        SaxionApp.drawImage("./assets/images/gameBackground.png", 0, 0);
        player.update();

    }
}
