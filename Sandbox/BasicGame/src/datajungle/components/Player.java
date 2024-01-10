package datajungle.components;

import datajungle.*;
import datajungle.scenes.GameOverScene;
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

    int x;
    int y;


    // Player data
    Collider collider = new Collider(x, y, 44, 96, DAMAGE);
    Collider groundCheckCollider = new Collider(x + 20, y + 66, 10, 30, UTIL);

    boolean canMove;
    int speed = 2;
    long attackCooldown = 500; // Cooldown is in milliseconds
    long dashCooldown = 700; // Cooldown is in milliseconds
    long poisonDamageDelay = 1000;
    long bleedDamageDelay = 1000;
    int damage = 1;
    int jumpForce = -11;
    int dashForce = 20;
    int yVelocity = 0;
    int health = 10;

    int poisonCount = 0;
    int bleedCount = 0;

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
    long lastPoisoned = 0;
    long lastBled = 0;
    long slowStart;
    long slowedTime;


    int direction = -1;
    int currentDashForce = 0;
    Collider usedLadder = null;

    // modifiers
    boolean isGrounded = false;
    boolean isJumping = false;
    boolean isOnLadder = false;
    boolean isAttacking = false;
    boolean isDashing = false;
    boolean isPoisoned = false;
    boolean isSlowed = false;
    boolean isBleeding = false;


    Animation currentAnimation;

    Spritesheet playerMoveSheet;
    Spritesheet playerAttackSheet;

    public Player(String characterSheet, String characterAttackSheet, int x, int y) {
        this.x = x;
        this.y = y;
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

        if (slowStart + slowedTime < System.currentTimeMillis()) {
            isSlowed = false;
        }


        boolean canMove = !collider.isColliding(CollisionManager.getColliders(SOLID), direction);
        if (!canMove && !isOnLadder) {
            if (!collider.isColliding(CollisionManager.getColliders(SOLID), direction * -1))
                this.x += this.speed * (direction * -1);
            else if (collider.isColliding(CollisionManager.getColliders(SOLID), direction * -5) && isGrounded)
                canMove = true;
            else
                this.x += this.speed * (direction * -1);
        }



        // Boolean for the walking animation
        boolean hasMoved = false;
        int currentSpeed = this.speed;
        if (isSlowed) this.speed = speed / 2;

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

        this.speed = currentSpeed;

        if (keysPressed[KeyEvent.VK_SHIFT] && (lastDash + dashCooldown < System.currentTimeMillis()) && canMove && !isDashing && !isOnLadder) {
            isDashing = true;
            if (isSlowed) currentDashForce = dashForce / 2;
            else currentDashForce = dashForce;
            lastDash = System.currentTimeMillis();
        }

        if (isDashing && !isOnLadder) {
            if (!(currentDashForce > 0)) {
                isDashing = false;
            } else {
                if (this.x + collider.getWidth() + (this.currentDashForce * direction) < SaxionApp.getWidth() && this.x + (this.currentDashForce * direction) > 0)
                    if (!collider.isColliding(CollisionManager.getColliders(SOLID), direction * (speed * currentDashForce)))
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
            for (int i = 0; i < BasicGame.currentScene.getEnemies().size(); i++) {
                Enemy enemy = BasicGame.currentScene.getEnemies().get(i);
                if (enemy.collider.isColliding(collider, direction * 25, 0)) {
                    enemy.damage(damage);
                }
            }

            isAttacking = true;
            lastAttack = System.currentTimeMillis();
        }

        // Check if the player is on the ground
        isGrounded = groundCheckCollider.isColliding(CollisionManager.getColliders(SOLID), 0, yVelocity * -1);


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
        groundCheckCollider.updateCoords(x + 15, y + 66);
    }

    private void draw() {
        currentAnimation.update();
        Image img = currentAnimation.currentFrame;
        img.setX(x);
        img.setY(y);
        SaxionApp.add(img);
    }

    private void checkDamageModifiers() {
        // Poison check
        if (isPoisoned) {
            if (lastPoisoned + poisonDamageDelay < System.currentTimeMillis() && poisonCount < 5) {
                if (health > 1) {
                    health--; // start animatie ofzo
                }
                lastPoisoned = System.currentTimeMillis();
            } else if (poisonCount >= 5) {
                isPoisoned = false;
                poisonCount = 0;
            }
        }

        if (isBleeding) {
            if (lastBled + bleedDamageDelay < System.currentTimeMillis() && bleedCount < 5) {
                health--; // start animatie ofzo
                lastBled = System.currentTimeMillis();
            } else if (bleedCount >= 5) {
                isBleeding = false;
                bleedCount = 0;
            }
        }
    }

    public void applyPoison() {
        isPoisoned = true;
        poisonCount = 0;
        lastPoisoned = System.currentTimeMillis();
    }

    public void applyBleeding() {
        isBleeding = true;
        bleedCount = 0;
        lastBled = System.currentTimeMillis();
    }

    public void applySlowness() {
        isSlowed = true;
        slowStart = System.currentTimeMillis();
    }

    public void damage(int damage) {
        this.health -= damage;
        if (this.health < 0) BasicGame.changeScene(new GameOverScene());
    }

    public void update() {
        draw();
        checkDamageModifiers();
        move();
    }
}