package datajungle.scenes;

public abstract class Scene {

    private String name;
    private boolean isRunning = false;


    public Scene(String name, boolean isRunning) {
        this.name = name;
        this.isRunning = isRunning;
    }

    public void init() {

    }

    public void update(boolean[] keysPressed) {

    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        this.isRunning = running;
    }





}
