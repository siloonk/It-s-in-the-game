package datajungle.scenes;

import datajungle.components.CharacterController;
import nl.saxion.app.SaxionApp;
import datajungle.GameObject;

import java.awt.event.KeyEvent;

public class BaseScene extends Scene {

    public BaseScene() {
        super("base_scene", true);
    }


    GameObject player;

    @Override
    public void init() {
        player = new GameObject(SaxionApp.getWidth()/2, SaxionApp.getHeight() / 2, 64, 128);
        player.addComponent(CharacterController.class);
    }


    @Override
    public void update(boolean[] keysPressed) {
        player.update();

    }


}
