package datajungle.scenes;

import datajungle.BasicGame;
import datajungle.Settings;
import datajungle.systems.Animation;
import datajungle.systems.Spritesheet;
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
        Spritesheet rolfSheet = new Spritesheet("./assets/images/sheets/characters_rolf.png", 222, 196, 48, 96, 0);
        Spritesheet rubenSheet = new Spritesheet("./assets/images/sheets/characters_ruben.png", 222, 196, 48, 96, 0);
        Spritesheet silSheet = new Spritesheet("./assets/images/sheets/characters_sil.png", 222, 196, 48, 96, 0);


        Method playButtonClicked;
        Method exitButtonClicked;
        Method rolfSelectButtonClicked;
        Method rubenSelectButtonClicked;
        Method silSelectButtonClicked;
        try {
            playButtonClicked = getClass().getMethod("playButtonClicked");
            exitButtonClicked = getClass().getMethod("exitButtonClicked");
            rolfSelectButtonClicked = getClass().getMethod("rolfSelectButtonClicked");
            rubenSelectButtonClicked = getClass().getMethod("rubenSelectButtonClicked");
            silSelectButtonClicked = getClass().getMethod("silSelectButtonClicked");

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

//        ButtonElement exitButton = new ButtonElement(SaxionApp.getWidth() / 2 - 50, 480).builder()
//                .setText("")
//                .setFocussedImage("./assets/images/exit_knop.png")
//                .setUnfocussedImage("./assets/images/exit_knop2.png")
//                .setExecutor(exitButtonClicked)
//                .build();
        ButtonElement exitButton = new ButtonElement(SaxionApp.getWidth()/2-50, 500, "", "./assets/images/exit_knop.png", "./assets/images/exit_knop2.png", exitButtonClicked);

        ButtonElement rolfSelectButton = new ButtonElement(SaxionApp.getWidth() / 2 - 200, 250, "", rolfSheet.getImage(0), rolfSheet.getImage(4), rolfSelectButtonClicked);
        ButtonElement rubenSelectButton = new ButtonElement(SaxionApp.getWidth() / 2, 250, "", rubenSheet.getImage(0), rubenSheet.getImage(4), rubenSelectButtonClicked);
        ButtonElement silSelectButton = new ButtonElement(SaxionApp.getWidth() / 2 + 200, 250, "", silSheet.getImage(0), silSheet.getImage(4), silSelectButtonClicked);

        gui.addElement(new ImageElement("./assets/images/menu_background.png", SaxionApp.getWidth()/2-340, 0, 680, 1280));
        gui.addElement(new ImageElement("./assets/images/logo.png", SaxionApp.getWidth()/2-315, 50));
        gui.addElement(rolfSelectButton);
        gui.addElement(rubenSelectButton);
        gui.addElement(silSelectButton);
        gui.addElement(exitButton);
    }

    public static void rolfSelectButtonClicked() {
        Settings.selectedAttackSheet = "./assets/images/sheets/player_attack_rolf.png";
        Settings.selectedCharacterSheet = "./assets/images/sheets/characters_rolf.png";
        playButtonClicked();
    }

    public static void rubenSelectButtonClicked() {
        System.out.println("test");
        Settings.selectedAttackSheet = "./assets/images/sheets/player_attack_ruben.png";
        Settings.selectedCharacterSheet = "./assets/images/sheets/characters_ruben.png";
        playButtonClicked();
    }

    public static void silSelectButtonClicked() {
        Settings.selectedAttackSheet = "./assets/images/sheets/player_attack_sil.png";
        Settings.selectedCharacterSheet = "./assets/images/sheets/characters_sil.png";
        playButtonClicked();
    }

    public static void playButtonClicked() {
        BasicGame.changeScene(new ForestLevelScene());
    }

    public static void exitButtonClicked() {
        SaxionApp.quit();
    }

    @Override
    public void update(boolean[] keysPressed) {
        Image img = new Image("./assets/images/forest_game_background.png", 0, 0);
        img.setColor(new Color(0,0,0,0.0f));

        SaxionApp.add(img);
        gui.update();
    }
}
