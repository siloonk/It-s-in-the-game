package datajungle;

import nl.saxion.app.canvas.drawable.Image;

import java.util.ArrayList;
import java.util.Arrays;

public class Animation {

    ArrayList<Image> animationSprites = new ArrayList<>();

    float animationSwitchDelay;
    Image currentFrame;
    long lastTime = System.currentTimeMillis();



    public void update() {
        if (currentFrame == null) {
            currentFrame = animationSprites.get(0);
        }
        System.out.println(System.currentTimeMillis());
        System.out.println(lastTime + animationSwitchDelay);
        if (lastTime + animationSwitchDelay < System.currentTimeMillis()) {
            if (animationSprites.indexOf(currentFrame) == animationSprites.size() - 1) {
                currentFrame = animationSprites.get(0);
            } else {
                currentFrame = animationSprites.get(animationSprites.indexOf(currentFrame) + 1);
            }

            lastTime = System.currentTimeMillis();
        }
    }


    static class Builder {

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
