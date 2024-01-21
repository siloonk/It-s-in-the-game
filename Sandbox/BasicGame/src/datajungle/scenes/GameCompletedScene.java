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
    public GameCompletedScene(Scene scene) {
        super("game_completed_scene", false);
        this.scene = scene;
    }

    GUI gui = new GUI();
    @Override
    public void init() {
        ImageElement element = new ImageElement("./assets/images/forest_gameover.png", 0, 0);
        if (scene instanceof SnowLevelScene) element = new ImageElement("./assets/images/snow_gameover.png", 0, 0);
        else if (scene instanceof DesertLevelScene) element = new ImageElement("./assets/images/desert_gameover.png", 0, 0);
        gui.addElement(element);
        gui.addElement(new ImageElement("./assets/images/gameover_text.png", SaxionApp.getWidth()/2-257, 200));
        try {
            gui.addElement(new ButtonElement(580, 350, "", "./assets/images/retry_unfocussed.png", "./assets/images/retry_focussed.png", getClass().getMethod("retryButtonClicked")));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
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
