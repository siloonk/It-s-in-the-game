package rolfie.scenes;

import nl.saxion.app.SaxionApp;
import rolfie.GameObject;

import java.awt.event.KeyEvent;

public class BaseScene extends Scene {

    public BaseScene() {
        super("base_scene", true);
    }

    GameObject go;

    @Override
    public void init() {
        go = new GameObject(0, 0, SaxionApp.getWidth(), SaxionApp.getHeight(), "./assets/images/image.jpg");
    }
    int x = SaxionApp.getWidth()/2;
    int y = SaxionApp.getHeight()/2;
    int radiusBall = 20;
    @Override
    public void update(boolean[] keysPressed) {
        SaxionApp.drawCircle(x, y, radiusBall);
        if (keysPressed[KeyEvent.VK_W]) {
            y -= 4;
        } else if (keysPressed[KeyEvent.VK_S]) {
            y += 4;
        }
        if (keysPressed[KeyEvent.VK_A]) {
            x -= 4;
        } else if (keysPressed[KeyEvent.VK_D]) {
            x += 4;
        }
        if (x + radiusBall> SaxionApp.getWidth()) {
            x -= 4;
        } else if (x < +radiusBall) {
            x += 4;
        }
        if (y + radiusBall > SaxionApp.getHeight()) {
            y -= 4;
        } else if (y < +radiusBall) {
            y += 4;
        }
    }


}
