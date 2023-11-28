package datajungle.components;

import datajungle.GameObject;

public abstract class Component {

    public GameObject gameObject;


    protected Component(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public abstract void update();


}
