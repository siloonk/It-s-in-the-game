package datajungle.scenes;

public abstract class Scene {

    private String name;
    private boolean isRunning = false;


    // Initialiseer een Scene object met een naam een een boolean isRunning
    public Scene(String name, boolean isRunning) {
        this.name = name;
        this.isRunning = isRunning;
    }

    // Wordt gecalled wanneer de scene geinitaliseert wordt.
    public void init() {

    }


    // Wordt elke frame gecalled
    public void update(boolean[] keysPressed) {

    }

    // kijk of de scene aan staat
    public boolean isRunning() {
        return isRunning;
    }

    // Verander de isRunning variable
    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    public String getName() {
        return name;
    }





}
