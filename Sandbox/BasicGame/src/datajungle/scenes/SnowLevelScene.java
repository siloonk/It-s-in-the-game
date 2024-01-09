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
    public Collider mainGroundCollider = new Collider(0, 582, 1280, 138, SOLID);
    public Collider secondGroundCollider = new Collider(203, 535, 878, 49, SOLID);
    public Collider thirdGroundCollider = new Collider(302, 487, 680, 49, SOLID);
    public Collider fourthGroundCollider = new Collider(401, 439, 482, 49, SOLID);
    public Collider fifthGroundCollider = new Collider(549, 391, 185, 49, SOLID);

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
        mainGroundCollider.draw();
        secondGroundCollider.draw();
        thirdGroundCollider.draw();
        fourthGroundCollider.draw();
        fifthGroundCollider.draw();

    }
}
