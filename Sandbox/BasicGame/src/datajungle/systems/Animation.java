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
    boolean fireOnce = false;
    boolean isDone = false;


    public void update() {
        if (currentFrame == null) {
            currentFrame = animationSprites.get(0);
        }

        if (lastTime + animationSwitchDelay < System.currentTimeMillis()) {
            if (currentFrameIndex == animationSprites.size() - 1 && !fireOnce) {
                currentFrame = animationSprites.get(0);
                currentFrameIndex = 0;
            } else if (fireOnce && currentFrameIndex == animationSprites.size() - 1) {
                isDone = true;
            }
            else {
                currentFrameIndex++;
                currentFrame = animationSprites.get(currentFrameIndex);
            }

            lastTime = System.currentTimeMillis();
        }
    }

    public void reset() {
        currentFrameIndex = 0;
        isDone = false;
    }

    public Image getLastFrame() {
        return animationSprites.get(animationSprites.size() - 1);
    }

    public boolean isDone() {
        return (currentFrame == animationSprites.get(animationSprites.size() - 1) && lastTime + animationSwitchDelay < System.currentTimeMillis()) || isDone;
    }


    public static class Builder {

        Animation animation = new Animation();

        public void setAnimationSwitchDelay(long animationSwitchDelay) {
            animation.animationSwitchDelay = animationSwitchDelay;
        }

        public void setOnce(boolean once) {
            animation.fireOnce = once;
        }

        public void setAnimationSprites(Image ...images) {
            animation.animationSprites.addAll(Arrays.asList(images));
        }

        public Animation build() {
            return animation;
        }

    }
}
