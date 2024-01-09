package datajungle.scenes;

import datajungle.BasicGame;
import datajungle.systems.Animation;
import datajungle.ui.ButtonElement;
import datajungle.ui.GUI;
import datajungle.ui.ImageElement;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;
import java.lang.reflect.Method;

public class MainMenuScene extends Scene {

    public MainMenuScene() {
        super("main_menu_scene", true);
    }
    GUI gui = new GUI();

    @Override
    public void init() {
        Method playButtonClicked;
        Method exitButtonClicked;
        try {
            playButtonClicked = getClass().getMethod("playButtonClicked");
            exitButtonClicked = getClass().getMethod("exitButtonClicked");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        ButtonElement playButton = new ButtonElement(SaxionApp.getWidth() / 2 - 55, 390).builder()
                .setText("Play")
                .setFocussedImage("./assets/images/blank_button.png")
                .setUnfocussedImage("./assets/images/blank_button.png")
                .setExecutor(playButtonClicked)
                .setWidth(120)
                .setHeight(50)
                .build();

        ButtonElement exitButton = new ButtonElement(SaxionApp.getWidth() / 2 - 50, 480).builder()
                .setText("Exit")
                .setFocussedImage("./assets/images/blank_button.png")
                .setUnfocussedImage("./assets/images/blank_button.png")
                .setExecutor(exitButtonClicked)
                .setWidth(120)
                .setHeight(50)
                .build();


        gui.addElement(new ImageElement("./assets/images/menu_background.png", SaxionApp.getWidth()/2-340, 0, 680, 1280));
        gui.addElement(new ImageElement("./assets/images/logo.png", SaxionApp.getWidth()/2-315, 50));
        gui.addElement(playButton);
        gui.addElement(exitButton);
    }

    public static void playButtonClicked() {
        BasicGame.changeScene(new ForestLevelScene());
    }

    public static void exitButtonClicked() {
        SaxionApp.quit();
    }

    @Override
    public void update(boolean[] keysPressed) {
        Image img = new Image("./assets/images/menu_backgrounds/forest_level.png", 0, 0);
        img.setColor(new Color(0,0,0,0.0f));

        SaxionApp.add(img);
        gui.update();
    }
}
