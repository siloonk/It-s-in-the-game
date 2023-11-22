package rolfie.scenes;

import nl.saxion.app.SaxionApp;
import rolfie.GameObject;

public class BaseScene extends Scene {

    public BaseScene() {
        super("base_scene", true);
    }

    GameObject go;

    @Override
    public void init() {
        go = new GameObject(0, 0, SaxionApp.getWidth(), SaxionApp.getHeight(), "./assets/images/image.jpg");
    }

    @Override
    public void update(boolean[] keysPressed) {
        go.update();
    }


}
