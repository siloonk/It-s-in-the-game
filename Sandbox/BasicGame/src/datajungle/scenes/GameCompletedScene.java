package datajungle.scenes;
import datajungle.BasicGame;
import datajungle.systems.CollisionManager;
import datajungle.ui.ButtonElement;
import datajungle.ui.GUI;
import datajungle.ui.ImageElement;
import datajungle.ui.LabelElement;
import nl.saxion.app.SaxionApp;

import java.awt.*;
import java.lang.reflect.Method;


public class GameCompletedScene extends Scene {

    Scene scene;
    public GameCompletedScene() {
        super("game_completed_scene", false);
    }

    GUI gui = new GUI();
    Method mainMenuButtonMethod;
    @Override
    public void init() {
        try {
            mainMenuButtonMethod = getClass().getMethod("mainButtonClicked");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        ImageElement element = new ImageElement("./assets/images/end_screen.png", 0, 0);
        ButtonElement buttonElement = new ButtonElement(SaxionApp.getWidth()/2-115, 640, "", "./assets/images/mainMenuButtonUnfocussed.png", "./assets/images/mainMenuButtonFocussed.png", mainMenuButtonMethod);
        gui.addElement(element);
        gui.addElement(buttonElement);
    }



    public static void mainButtonClicked() {
        BasicGame.changeScene(new MainMenuScene());
    }

    @Override
    public void update(boolean[] keysPressed) {
        gui.update();
    }

    @Override
    public void close() {
        super.close();
    }
}
