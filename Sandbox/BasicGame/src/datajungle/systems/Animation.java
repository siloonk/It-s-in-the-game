package datajungle.systems;

import nl.saxion.app.canvas.drawable.Image;

import java.util.ArrayList;
import java.util.Arrays;

public class Animation {

    ArrayList<Image> animationSprites = new ArrayList<>();

    long animationSwitchDelay;
    public Image currentFrame;
    long lastTime = 0;
    int currentFrameIndex = 0;



    public void update() {
        if (currentFrame == null) {
            currentFrame = animationSprites.get(0);
        }

        if (lastTime + animationSwitchDelay < System.currentTimeMillis()) {
            if (currentFrameIndex == animationSprites.size() - 1) {
                currentFrame = animationSprites.get(0);
                currentFrameIndex = 0;
            } else {
                currentFrameIndex++;
                currentFrame = animationSprites.get(currentFrameIndex);
            }

            lastTime = System.currentTimeMillis();
        }
    }


    public static class Builder {

        Animation animation = new Animation();

        public void setAnimationSwitchDelay(long animationSwitchDelay) {
            animation.animationSwitchDelay = animationSwitchDelay;
        }

        public void setAnimationSprites(Image ...images) {
            animation.animationSprites.addAll(Arrays.asList(images));
        }

        public Animation build() {
            return animation;
        }

    }
}
