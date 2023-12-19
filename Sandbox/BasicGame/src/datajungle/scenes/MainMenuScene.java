package datajungle.scenes;
import datajungle.BasicGame;
import datajungle.systems.Spritesheet;
import datajungle.ui.ButtonElement;
import datajungle.ui.GUI;
import datajungle.ui.ImageElement;
import datajungle.ui.LabelElement;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;


public class MainMenuScene extends Scene {

    public MainMenuScene() {
        super("main_menu", false);
    }

    GUI gui = new GUI();
    @Override
    public void init() {
        gui.addElement(new ImageElement("./assets/images/background.png", 0, 0));
        gui.addElement(new LabelElement(500, 100, "GAME OVER!!", 50, Color.RED));
        try {
            gui.addElement(new ButtonElement(500, 500, 400, 100, "Retry", "./assets/images/unfocussed_button.png", "./assets/images/focussed_button.png", getClass().getMethod("retryButtonClicked")));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static void retryButtonClicked() {
        BasicGame.changeScene(new BaseScene());
    }

    @Override
    public void update(boolean[] keysPressed) {
        gui.update();
    }
}
