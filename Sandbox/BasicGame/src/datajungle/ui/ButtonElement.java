package datajungle.ui;

import datajungle.BasicGame;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.attribute.UserPrincipalLookupService;

public class ButtonElement extends UIElement {

    int FONT_SIZE = 50;

    Image unfocussedImage;
    Image focussedImage;

    Image selectedImage;
    String text;
    Method function;
    Color unfocussedColor = new Color(0,0,0);
    Color focussedColor = new Color(255, 255, 255);
    Color currentColor;

    private static Builder builder;

    public ButtonElement(int x, int y) {
        super(x, y);
    }


    public ButtonElement(int x, int y, String text, String unfocussedImage, String focussedImage, Method function) {
        super(x, y);

        this.unfocussedImage = new Image(unfocussedImage, x, y);
        this.focussedImage = new Image(focussedImage, x, y);
        this.text = text;
        this.function = function;
        selectedImage = this.unfocussedImage;
        currentColor = unfocussedColor;
    }


    public ButtonElement(int x, int y, int width, int height, String text, String unfocussedImage, String focussedImage, Method function) {
        super(x, y);

        this.unfocussedImage = new Image(unfocussedImage, x, y, width, height);
        this.focussedImage = new Image(focussedImage, x, y, width, height);
        this.text = text;
        this.function = function;
        selectedImage = this.unfocussedImage;

    }

    public ButtonElement(int x, int y, String text, Image unfocussedImage, Image focussedImage, Method function) {
        super(x, y);

        this.unfocussedImage = unfocussedImage;
        this.focussedImage = focussedImage;
        this.text = text;
        this.function = function;
        selectedImage = this.unfocussedImage;

    }

    public ButtonElement(int x, int y, int width, int height, String text, String unfocussedImage, String focussedImage, Method function, Color color) {
        super(x, y);

        this.unfocussedImage = new Image(unfocussedImage, x, y, width, height);
        this.focussedImage = new Image(focussedImage, x, y, width, height);
        this.text = text;
        this.function = function;
        selectedImage = this.unfocussedImage;
        this.unfocussedColor = color;

    }


    private void switchImage(Image image) {
        this.selectedImage = image;
        this.selectedImage.setX(x);
        this.selectedImage.setY(y - image.getHeight()/2);
    }

    private boolean isFocussed() {
        int mouseX = MouseInfo.getPointerInfo().getLocation().x;
        int mouseY = MouseInfo.getPointerInfo().getLocation().y;

        int imageWidth = selectedImage.getWidth();
        int imageHeight = selectedImage.getHeight();

        return (mouseX < x + imageWidth && mouseX > x && mouseY < y + (imageHeight * 1.5) && mouseY > y);
    }

    private void drawText() {
        int textX = x + selectedImage.getWidth() / 2 - (text.length() * (FONT_SIZE / 4));
        int textY = y + selectedImage.getHeight() / 2 - (FONT_SIZE / 2);
        SaxionApp.setTextDrawingColor(currentColor);
        SaxionApp.drawText(this.text, textX, textY, FONT_SIZE);
    }

    @Override
    public void draw() {

        // Change selectedImage to the focussed image
        if (isFocussed() && selectedImage != focussedImage) {
            currentColor = focussedColor;
            switchImage(focussedImage);
        } else if (!isFocussed()){
            switchImage(unfocussedImage);
            currentColor = unfocussedColor;
        }

        if (isFocussed() && BasicGame.leftMouseButtonPressed) {
            try {
                function.invoke(null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        SaxionApp.add(selectedImage);
        drawText();
    }

    public Builder builder() {
        return new Builder();
    }


    public class Builder {
        String unfocussedImage;
        String focussedImage;

        String text;
        Method function;

        int height, width;

        public Builder setUnfocussedImage(String unfocussedImage) {
            this.unfocussedImage = unfocussedImage;
            return this;
        }

        public Builder setFocussedImage(String focussedImage) {
            this.focussedImage = focussedImage;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setExecutor(Method function) {
            this.function = function;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height)  {
            this.height = height;
            return this;
        }

        public ButtonElement build() {
            return new ButtonElement(x, y, text, new Image(unfocussedImage, x, y, width, height), new Image(focussedImage, x, y, width, height), function);
        }
    }
}
