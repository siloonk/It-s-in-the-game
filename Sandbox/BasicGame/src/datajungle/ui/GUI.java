package datajungle.ui;

import java.util.ArrayList;

public class GUI {

    ArrayList<UIElement> elements = new ArrayList<>();

    public void addElement(UIElement element) {
        elements.add(element);
    }

    public void update() {
        for (UIElement element : elements) {
            element.update();
        }
    }
}
