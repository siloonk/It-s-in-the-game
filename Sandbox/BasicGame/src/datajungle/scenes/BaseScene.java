package datajungle.scenes;

import nl.saxion.app.SaxionApp;
import datajungle.GameObject;

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
    int radiusBall = 50;
    int speed = 5;
    @Override
    public void update(boolean[] keysPressed) {
        go.update();
        SaxionApp.drawCircle(x, y, radiusBall);
        if (keysPressed[KeyEvent.VK_W]) {
            y -= speed;
        } else if (keysPressed[KeyEvent.VK_S]) {
            y += speed;
        }
        if (keysPressed[KeyEvent.VK_A]) {
            x -= speed;
        } else if (keysPressed[KeyEvent.VK_D]) {
            x += speed;
        }
        if (x + radiusBall> SaxionApp.getWidth()) {
            x -= speed;
        } else if (x < +radiusBall) {
            x += speed;
        }
        if (y + radiusBall > SaxionApp.getHeight()) {
            y -= speed;
        } else if (y < +radiusBall) {
            y += speed;
        }
    }


}
