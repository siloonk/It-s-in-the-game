package datajungle.components;

import datajungle.*;
import datajungle.systems.Collider;
import datajungle.scenes.ForestLevelScene;
import datajungle.systems.Animation;
import datajungle.systems.CollisionManager;
import datajungle.systems.Spritesheet;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.canvas.drawable.Image;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static datajungle.Settings.*;

public class Player {

    int x = SaxionApp.getWidth() / 2;
    int y = SaxionApp.getHeight() / 2;


    // Player data
    Collider collider = new Collider(x, y, 44, 96, DAMAGE);
    Collider groundCheckCollider = new Collider(x + 20, y, 10, 96, UTIL);

    boolean canMove;
    int speed = 2;
    long attackCooldown = 500; // Cooldown is in milliseconds
    long dashCooldown = 700; // Cooldown is in milliseconds
    int damage = 1;
    int jumpForce = -11;
    int dashForce = 20;
    int yVelocity = 0;

    // Animations
    Animation walkAnimationLeft;
    Animation walkAnimationRight;
    Animation idleAnimationleft;
    Animation idleAnimationRight;
    Animation attackLeft;
    Animation attackRight;
    Animation jumpRight;
    Animation jumpLeft;

    long lastAttack = 0;
    long lastDash = 0;

    int direction = -1;
    int currentDashForce = 0;
    Collider usedLadder = null;

    // modifiers
    boolean isGrounded = false;
    boolean isJumping = false;
    boolean isOnLadder = false;
    boolean isAttacking = false;
    boolean isDashing = false;

    Animation currentAnimation;

    Spritesheet playerMoveSheet;
    Spritesheet playerAttackSheet;

    public Player(String characterSheet, String characterAttackSheet) {
        playerMoveSheet = new Spritesheet(characterSheet, 222, 196, 48, 96, 0);
        playerAttackSheet = new Spritesheet(characterAttackSheet, 480, 186, 48, 93, 0);
        Animation.Builder animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(300);
        animBuilder.setAnimationSprites(playerMoveSheet.getImage(0), playerMoveSheet.getImage(2), playerMoveSheet.getImage(0), playerMoveSheet.getImage(3));
        walkAnimationLeft = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(300);
        animBuilder.setAnimationSprites(playerMoveSheet.getImage(5), playerMoveSheet.getImage(7), playerMoveSheet.getImage(5), playerMoveSheet.getImage(8));
        walkAnimationRight = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(700);
        animBuilder.setAnimationSprites(playerMoveSheet.getImage(0), playerMoveSheet.getImage(1));
        idleAnimationleft = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSwitchDelay(700);
        animBuilder.setAnimationSprites(playerMoveSheet.getImage(5), playerMoveSheet.getImage(6));
        idleAnimationRight = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSprites(playerAttackSheet.getImage(10), playerAttackSheet.getImage(11), playerAttackSheet.getImage(12), playerAttackSheet.getImage(13), playerAttackSheet.getImage(14), playerAttackSheet.getImage(15), playerAttackSheet.getImage(16), playerAttackSheet.getImage(17), playerAttackSheet.getImage(18), playerAttackSheet.getImage(19));
        animBuilder.setAnimationSwitchDelay(25);
        animBuilder.setOnce(true);
        attackLeft = animBuilder.build();


        animBuilder = new Animation.Builder();
        animBuilder.setAnimationSprites(playerAttackSheet.getImage(9), playerAttackSheet.getImage(8), playerAttackSheet.getImage(7), playerAttackSheet.getImage(6), playerAttackSheet.getImage(5), playerAttackSheet.getImage(4), playerAttackSheet.getImage(3), playerAttackSheet.getImage(2), playerAttackSheet.getImage(1), playerAttackSheet.getImage(0));
        animBuilder.setAnimationSwitchDelay(25);
        animBuilder.setOnce(true);
        attackRight = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setOnce(true);
        animBuilder.setAnimationSwitchDelay(400);
        animBuilder.setAnimationSprites(playerMoveSheet.getImage(2));
        jumpLeft = animBuilder.build();

        animBuilder = new Animation.Builder();
        animBuilder.setOnce(true);
        animBuilder.setAnimationSwitchDelay(400);
        animBuilder.setAnimationSprites(playerMoveSheet.getImage(7));
        jumpRight = animBuilder.build();

        currentAnimation = idleAnimationleft;
    }

    private void move() {

        boolean[] keysPressed = BasicGame.keysPressed;
        boolean mouseButtonPressed = BasicGame.leftMouseButtonPressed;

        if (currentAnimation == attackLeft || currentAnimation == attackRight) {
            if (currentAnimation.isDone()) isAttacking = false;
        } else isAttacking = false;


        boolean canMove = !collider.isColliding(CollisionManager.getColliders(SOLID), direction * (speed * 3));
        if (!canMove && !isOnLadder) {
            this.x += this.speed * (direction * - 1);
        }
        canMove = true;

        // Boolean for the walking animation
        boolean hasMoved = false;

        if (keysPressed[KeyEvent.VK_D] && !isOnLadder) {
            if (canMove) {
                if (this.x + collider.getWidth() + this.speed < SaxionApp.getWidth())
                    this.x += this.speed;
                if (currentAnimation.isDone() || !(currentAnimation == attackLeft || currentAnimation == attackRight)) currentAnimation = walkAnimationRight;
                hasMoved = true;
            }

            direction = 1;

        }

        if (keysPressed[KeyEvent.VK_A] && !isOnLadder) {
            if (canMove) {
                if (this.x - this.speed > 0)
                    this.x -= this.speed;
                if (currentAnimation.isDone() || !(currentAnimation == attackLeft || currentAnimation == attackRight)) currentAnimation = walkAnimationLeft;
                hasMoved = true;
            }
            direction = -1;
        }

        if (keysPressed[KeyEvent.VK_SHIFT] && (lastDash + dashCooldown < System.currentTimeMillis()) && canMove && !isDashing && !isOnLadder) {
            isDashing = true;
            currentDashForce = dashForce;
            lastDash = System.currentTimeMillis();
        }

        if (isDashing) {
            if (!(currentDashForce > 0)) {
                isDashing = false;
            } else {
                if (this.x + collider.getWidth() + (this.currentDashForce * direction) < SaxionApp.getWidth() && this.x + (this.currentDashForce * direction) > 0)
                    if (!collider.isColliding(CollisionManager.getColliders(SOLID), direction * (speed * 3)))
                        x += direction * currentDashForce;
                currentDashForce--;
            }
        }

        if (keysPressed[KeyEvent.VK_A] && keysPressed[KeyEvent.VK_D] && !isJumping && !isOnLadder && !isAttacking) {
            currentAnimation = idleAnimationleft;
        }

        Collider ladderInRange = null;
        // Check if there are ladders in range
        ArrayList<Collider> colliders = CollisionManager.getColliders(LADDER);
        if (colliders != null) {
            for (Collider c : colliders) {
                if (collider.distance(c) < 40 && collider.getY() >= c.getY()) {
                    SaxionApp.setTextDrawingColor(Color.BLACK);
                    SaxionApp.drawText("Press E to climb!", c.getX() - 100, c.getY(), 24);
                    ladderInRange = c;
                }
            }
        }

        // Check if the player is on a ladder, if so move and check if they have exceeded the ladder's height
        if (keysPressed[KeyEvent.VK_W] && isOnLadder) {
            this.y -= 2;

            if (this.y - this.collider.getHeight() < usedLadder.getY() - usedLadder.getHeight()) {
                isOnLadder = false;
            }
        }

        // Check if the player is in range of a ladder and if so clamp to the ladder
        if (keysPressed[KeyEvent.VK_E] && (ladderInRange != null)) {
            isOnLadder = true;
            usedLadder = ladderInRange;
        }


        // Change idle animations
        if (!hasMoved && direction == -1 && (currentAnimation.isDone() || !(currentAnimation == attackLeft || currentAnimation == attackRight)))
            currentAnimation = idleAnimationleft;
        if (!hasMoved && direction == 1 && (currentAnimation.isDone() || !(currentAnimation == attackLeft || currentAnimation == attackRight)))
            currentAnimation = idleAnimationRight;

        if (mouseButtonPressed && (lastAttack + attackCooldown < System.currentTimeMillis()) && !isAttacking) {
            attackRight.reset();
            attackLeft.reset();
            if (direction == 1) currentAnimation = attackRight;
            if (direction == -1) currentAnimation = attackLeft;

            // Player started the attack thus deal damage
            for (int i = 0; i < ForestLevelScene.getEnemies().size(); i++) {
                SpiderEnemy enemy = ForestLevelScene.getEnemies().get(i);
                if (enemy.collider.isColliding(collider, direction * 25, 0)) {
                    enemy.damage(damage);
                }
            }

            isAttacking = true;
            lastAttack = System.currentTimeMillis();
        }

        // Check if the player is on the ground
        isGrounded = collider.isColliding(CollisionManager.getColliders(SOLID), 0, yVelocity * -1);


        // Check if the player is allowed to jump
        if (keysPressed[KeyEvent.VK_SPACE] && !isJumping && isGrounded && !isOnLadder) {
            isJumping = true;
            this.yVelocity = jumpForce;
        }

        if (!isGrounded && isJumping) {
            if (direction == 1) currentAnimation = jumpRight;
            if (direction == -1) currentAnimation = jumpLeft;
        }

        // Check if the player player should be able to move according to the zVelocity
        if ((!isGrounded || yVelocity < 0) && !isOnLadder) this.y += yVelocity;

        if (!isGrounded || yVelocity < 0) {
            yVelocity += Settings.GRAVITY;
        } else {
            yVelocity = 0;
            isJumping = false;
        }

        collider.updateCoords(x, y);
    }

    private void draw() {
        currentAnimation.update();
        Image img = currentAnimation.currentFrame;
        img.setX(x);
        img.setY(y);
        SaxionApp.add(img);
    }

    public void update() {
        draw();
        move();
    }
}
