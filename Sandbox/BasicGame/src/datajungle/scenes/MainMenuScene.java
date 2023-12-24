package datajungle.scenes;
import datajungle.ui.GUI;
import datajungle.ui.ImageElement;
import datajungle.ui.LabelElement;

import java.awt.*;

public class MainMenuScene extends Scene {

    public MainMenuScene() {
        super("main_menu", false);
    }

    GUI gui = new GUI();
    @Override
    public void init() {
        gui.addElement(new ImageElement("./assets/images/background.png", 0, 0));
        gui.addElement(new LabelElement(500, 500, "GAME OVER!!", 50, Color.RED));
    }

    @Override
    public void update(boolean[] keysPressed) {
        gui.update();
    }
}
