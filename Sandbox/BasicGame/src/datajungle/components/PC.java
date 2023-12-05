package datajungle.components;

import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

public class PC {

    int x = SaxionApp.getWidth() /2 ;
    Image image;

    int dataTransferRate;
    int dataTransfered = 0;
    int dataToTransfer;

    long lastDataTransfered = 0;
    int health;



    public PC(Image image, int dataTransferRate, int dataToTransfer, int health) {
        this.image = image;
        this.dataTransferRate = dataTransferRate;
        this.dataToTransfer = dataToTransfer;
        this.health = health;

        this.image.setX(x);
        this.image.setY(600);
    }


    public void damage(int damage) {
        this.health -= damage;

        if (this.health <= 0) {
            // Gameover
        }
    }

    public void draw() {

    }

    public void update() {
        // Data ready to transfer
        if (lastDataTransfered + 100 < System.currentTimeMillis()) {
            lastDataTransfered = System.currentTimeMillis();
            this.dataTransfered += dataTransferRate;

            // Check if pc has transfered all it's data
            if (this.dataTransfered >= dataToTransfer) {
                // progress level
            }
        }
    }

}
