package datajungle.ui;

import datajungle.BasicGame;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;
import java.lang.reflect.Method;
import java.nio.file.attribute.UserPrincipalLookupService;

public class ButtonElement extends UIElement {

    int FONT_SIZE = 20;

    Image unfocussedImage;
    Image focussedImage;
    Image clickedImage;

    Image selectedImage;
    String text;
    Method function;

    public ButtonElement(int x, int y, String text, Image unfocussedImage, Image focussedImage, Image clickedImage, Method function) {
        super(x, y);

        this.unfocussedImage = unfocussedImage;
        this.focussedImage = focussedImage;
        this.clickedImage = clickedImage;
        this.text = text;
        this.function = function;
        selectedImage = unfocussedImage;

    }

    private void switchImage(Image image) {
        this.selectedImage = image;
        this.selectedImage.setX(x);
        this.selectedImage.setY(y);
    }

    private boolean isFocussed() {
        int mouseX = MouseInfo.getPointerInfo().getLocation().x;
        int mouseY = MouseInfo.getPointerInfo().getLocation().y;

        int imageWidth = unfocussedImage.getWidth();
        int imageHeight = unfocussedImage.getHeight();

        return (mouseX < x + imageWidth && mouseX > x && mouseY < y + imageHeight && mouseY > y);
    }

    private void drawText() {
        int textX = x - selectedImage.getWidth() / 2 - text.length() * FONT_SIZE / 2;
        int textY = y - selectedImage.getHeight() / 2 + FONT_SIZE;
    }

    @Override
    public void draw() {

        // Change selectedImage to the focussed image
        if (isFocussed() && selectedImage != focussedImage) {
            selectedImage = focussedImage;
        } else if (isFocussed() && BasicGame.leftMouseButtonPressed) {
            selectedImage = clickedImage;
        } else {
            selectedImage = unfocussedImage;
        }

        drawText();
        SaxionApp.add(selectedImage);
    }

}
