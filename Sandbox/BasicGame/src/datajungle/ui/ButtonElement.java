package datajungle.ui;

import datajungle.BasicGame;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.attribute.UserPrincipalLookupService;

public class ButtonElement extends UIElement {

    int FONT_SIZE = 20;

    Image unfocussedImage;
    Image focussedImage;

    Image selectedImage;
    String text;
    Method function;

    public ButtonElement(int x, int y, String text, String unfocussedImage, String focussedImage, Method function) {
        super(x, y);

        this.unfocussedImage = new Image(unfocussedImage, x, y);
        this.focussedImage = new Image(focussedImage, x, y);
        this.text = text;
        this.function = function;
        selectedImage = this.unfocussedImage;

    }


    public ButtonElement(int x, int y, int width, int height, String text, String unfocussedImage, String focussedImage, Method function) {
        super(x, y);

        this.unfocussedImage = new Image(unfocussedImage, x, y, width, height);
        this.focussedImage = new Image(focussedImage, x, y, width, height);
        this.text = text;
        this.function = function;
        selectedImage = this.unfocussedImage;

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

        return (mouseX < x + imageWidth && mouseX > x && mouseY < y + (imageHeight * 1.5) && mouseY > y);
    }

    private void drawText() {
        int textX = x + selectedImage.getWidth() / 2 - (text.length() * (FONT_SIZE / 4));
        int textY = y + selectedImage.getHeight() / 2 - (FONT_SIZE / 2);
        SaxionApp.drawText(this.text, textX, textY, FONT_SIZE);
    }

    @Override
    public void draw() {

        // Change selectedImage to the focussed image
        if (isFocussed() && selectedImage != focussedImage) {
            switchImage(focussedImage);
        } else if (!isFocussed()){
            switchImage(unfocussedImage);
        }

        if (isFocussed() && BasicGame.leftMouseButtonPressed) {
            try {
                function.invoke(null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        SaxionApp.add(selectedImage);
        drawText();
    }

}
