package datajungle.scenes;

import datajungle.CollisionBox;
import datajungle.Player;
import nl.saxion.app.SaxionApp;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class BaseScene extends Scene {

    public BaseScene() {
        super("base_scene", true);
    }

    ArrayList<CollisionBox> collisons = new ArrayList<CollisionBox>();

    Player player;

    @Override
    public void init() {
        player = new Player();
    }

    @Override
    public void update(boolean[] keysPressed) {
        SaxionApp.drawImage("./assets/images/gameBackground.png", 0, 0);
        player.update();

    }


}
