package datajungle.scenes;

import datajungle.components.Player;
import datajungle.systems.Collider;
import nl.saxion.app.SaxionApp;

import java.awt.*;

import static datajungle.Settings.SOLID;

public class SnowLevelScene extends Scene {

    /*
            Nieuwe waarden zijn nodig!!
     */
    public Collider topGroundCollider = new Collider(553, 418, 193, 47, SOLID);
    public Collider secondGroundCollider = new Collider(404, 511, 492, 47, SOLID);
    public Collider thirdGroundCollider = new Collider(305, 558, 668, 47, SOLID);
    public Collider fourthGroundCollider = new Collider(207, 605, 883, 47, SOLID);
    public Collider mainGroundCollider = new Collider(0, 652, 1280, 10, SOLID);

    Player player;

    public SnowLevelScene() {
        super("snow_level_scene", false);
    }

    @Override
    public void init() {
        player = new Player("./assets/images/sheets/characters_sil.png", "./assets/images/sheets/player_attack_sil.png");
    }


    @Override
    public void update(boolean[] keysPressed) {
        SaxionApp.drawImage("./assets/images/snowy_game_background.png", 0, 0);
        player.update();
    }
}
