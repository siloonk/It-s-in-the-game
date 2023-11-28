package datajungle;

import datajungle.components.CharacterController;
import datajungle.components.Component;
import nl.saxion.app.SaxionApp;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import java.awt.*;
import java.util.ArrayList;

public class GameObject {

    int x, y, width, height;
    String texturePath;

    private List<Component> components = new ArrayList<>();

    // Initalisser een gameobject met een texture
    public GameObject(int x, int y, int width, int height, String texturePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texturePath = texturePath;
    }

    // Initaliseer een gameobject zonder texture
    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texturePath = null;
    }

    // Update functie van de gameobject
    public void update() {
        for (Component c : components) {
            c.update();
        }

        if (texturePath == null) { // No Image given to the object
            SaxionApp.setBorderSize(0);
            SaxionApp.drawRectangle(x, y, width, height);
        } else { // image given thus draw the image
            SaxionApp.drawImage(texturePath, x, y, width, height);
        }
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void addComponent(Class<? extends Component> component) {
        try {
            this.components.add(component.getConstructor(GameObject.class).newInstance(this));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
