package datajungle.components;

import datajungle.BasicGame;
import datajungle.GameObject;
import datajungle.PlayerData;

import java.awt.event.KeyEvent;

public class CharacterController extends Component {

    public CharacterController(GameObject gameObject) {
        super(gameObject);
    }

    boolean isJumping = false;
    int jumpTime = PlayerData.JUMP_HEIGHT;

    @Override
    public void update() {
        if (jumpTime == PlayerData.JUMP_HEIGHT*-1) {
            isJumping = false;
            jumpTime = PlayerData.JUMP_HEIGHT;
        }


        if (BasicGame.keysPressed[KeyEvent.VK_D] || BasicGame.keysPressed[KeyEvent.VK_RIGHT]) {
            gameObject.move(PlayerData.SPEED, 0);
        }

        if (BasicGame.keysPressed[KeyEvent.VK_A] || BasicGame.keysPressed[KeyEvent.VK_LEFT]) {
            gameObject.move(PlayerData.SPEED * -1, 0);
        }


        if (!isJumping) {
            if (BasicGame.keysPressed[KeyEvent.VK_SPACE] || BasicGame.keysPressed[KeyEvent.VK_W]) {
                isJumping = true;
                jumpTime = PlayerData.JUMP_HEIGHT;
            }
        } else if (jumpTime > PlayerData.JUMP_HEIGHT*-1){
            if (jumpTime <= 0) {
                gameObject.move(0, 5);
            } else {
                gameObject.move(0, -5);
            }
            jumpTime -= 5;
        }
    }


}
