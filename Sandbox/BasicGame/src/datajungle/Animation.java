package datajungle;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class Animation {

    ArrayList<BufferedImage> animationSprites;

    float animationSwitchDelay;
    BufferedImage currentFrame;
    long lastTime = System.nanoTime();



    public void update() {
        if (lastTime + animationSwitchDelay > System.nanoTime()) {
            currentFrame = animationSprites.get(animationSprites.indexOf(currentFrame) + 1);
        }
    }


    static class Builder {

        Animation animation = new Animation();

        public void setAnimationSwitchDelay(long animationSwitchDelay) {
            animation.animationSwitchDelay = animationSwitchDelay;
        }

        public void setAnimationSprites(BufferedImage ...images) {
            animation.animationSprites = (ArrayList<BufferedImage>) Arrays.stream(images).toList();
        }

        public Animation build() {
            return animation;
        }

    }
}
