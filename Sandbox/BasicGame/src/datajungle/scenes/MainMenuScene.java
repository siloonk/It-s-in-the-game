package datajungle.scenes;
import datajungle.ui.GUI;
import datajungle.ui.LabelElement;
import nl.saxion.app.SaxionApp;

import java.awt.*;

public class MainMenuScene extends Scene {

    public MainMenuScene() {
        super("main_menu", false);
    }

    GUI gui = new GUI();
    @Override
    public void init() {
        gui.addElement(new LabelElement(SaxionApp.getWidth()/2-50*3, SaxionApp.getHeight()/2, "GAME OVER!!", 50, Color.RED));
    }

    @Override
    public void update(boolean[] keysPressed) {
        gui.update();
    }
}
