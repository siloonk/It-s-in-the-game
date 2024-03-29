package datajungle;

import datajungle.scenes.MainMenuScene;
import datajungle.systems.CollisionManager;
import nl.saxion.app.SaxionApp;


import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;
import datajungle.scenes.ForestLevelScene;
import datajungle.scenes.Scene;

public class BasicGame implements GameLoop {
    // Een lijst van alle keys die op dit moment ingedrukt zijn
    public static boolean[] keysPressed = new boolean[525];
    public static boolean leftMouseButtonPressed = false;

    // De huidige scene die geselecteerd is.
    public static Scene currentScene;


    public static void main(String[] args) {
        SaxionApp.startGameLoop(new BasicGame(), 1280, 720, 1);
    }


    @Override
    public void init() {
        // Zet de huidige scene naar een instance van BaseScene
        currentScene = new MainMenuScene();
        // Initialiseer de scene
        currentScene.init();
    }


    @Override
    public void loop() {
        // Clear de screen
        SaxionApp.clear();
        // Update de huidige scene

        if (currentScene.isRunning())
            currentScene.update(keysPressed);
    }

    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {
        // Check for keyboard input
        if (keyboardEvent.isKeyPressed()) { // Key wordt ingedrukt, dus voeg toe aan de lijst
            keysPressed[keyboardEvent.getKeyCode()] = true;
        } else { // Key is los gelaten dus zet keysPressed[keyboardEvent.getKeyCode()] naar false
            keysPressed[keyboardEvent.getKeyCode()] = false;
        }
    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {
        if (mouseEvent.isMouseDown() && mouseEvent.isLeftMouseButton())
            leftMouseButtonPressed = true;
        else
            leftMouseButtonPressed = false;

    }

    public static void changeScene(Scene scene) {
        currentScene.close();
        CollisionManager.clearColliders();
        scene.init();
        currentScene = scene;
        currentScene.setRunning(true);
    }


}