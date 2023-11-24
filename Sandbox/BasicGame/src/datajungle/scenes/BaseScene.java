package datajungle.scenes;

import nl.saxion.app.SaxionApp;
import datajungle.GameObject;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.event.KeyEvent;

public class BaseScene extends Scene {

    // Maak een scene aan met de naam "base_scene" en zorg ervoor dat isRunning aan staat
    public BaseScene() {
        super("base_scene", true);
    }


    // Initaliseer een GameObject go;
    GameObject go;
    @Override
    public void init() {
        // defineer go en geef het een nieuw GameObject
        go = new GameObject(0, 0, SaxionApp.getWidth(), SaxionApp.getHeight(), "./assets/images/image.jpg");

    }

    int x = SaxionApp.getWidth()/2;
    int y = SaxionApp.getHeight()/2;
    int radiusBall = 50;
    int speed = 5;

    // Wordt elke frame gecalled
    @Override
    public void update(boolean[] keysPressed) {
        go.update(); // update de GameObject go


        // Check for character movement
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

        // Check for collisions
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
