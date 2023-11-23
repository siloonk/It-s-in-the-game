package datajungle;

import nl.saxion.app.SaxionApp;


import nl.saxion.app.interaction.GameLoop;
import nl.saxion.app.interaction.KeyboardEvent;
import nl.saxion.app.interaction.MouseEvent;
import datajungle.scenes.BaseScene;
import datajungle.scenes.Scene;

public class BasicGame implements GameLoop {


    boolean[] keysPressed = new boolean[300];
    Scene currentScene;


    public static void main(String[] args) {
        SaxionApp.startGameLoop(new BasicGame(), 1280, 720, 1);
    }


    @Override
    public void init() {
        currentScene = new BaseScene();
        currentScene.init();
    }

    @Override
    public void loop() {
        SaxionApp.clear();
        currentScene.update(keysPressed);
    }

    @Override
    public void keyboardEvent(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.isKeyPressed()) {
            keysPressed[keyboardEvent.getKeyCode()] = true;
        } else {
            keysPressed[keyboardEvent.getKeyCode()] = false;
        }
    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {

    }


}






