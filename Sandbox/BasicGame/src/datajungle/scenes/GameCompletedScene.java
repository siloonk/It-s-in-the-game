package datajungle.scenes;
import datajungle.BasicGame;
import datajungle.systems.CollisionManager;
import datajungle.ui.ButtonElement;
import datajungle.ui.GUI;
import datajungle.ui.ImageElement;
import datajungle.ui.LabelElement;
import nl.saxion.app.SaxionApp;

import java.awt.*;


public class GameCompletedScene extends Scene {

    Scene scene;
    public GameCompletedScene() {
        super("game_completed_scene", false);
    }

    GUI gui = new GUI();
    @Override
    public void init() {
        ImageElement element = new ImageElement("./assets/images/end_screen.png", 0, 0);
        gui.addElement(element);
}



    public static void retryButtonClicked() {
        BasicGame.changeScene(new ForestLevelScene());
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
