import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class GameScreen {
    // for graphic
    private Group root;
    private Scene gameScene;
    private StatusBar statusBar;
    private AnimationTimer gameLoop;

    // for audio
    private AudioClip shootSound = new AudioClip(getClass().getResource("sounds/shoot.wav").toExternalForm());
    private AudioClip explosionSound = new AudioClip(getClass().getResource("sounds/explosion.wav").toExternalForm());
    private AudioClip invKilledSound = new AudioClip(getClass().getResource("sounds/invaderkilled.wav").toExternalForm());
    private AudioClip invShootSound1 = new AudioClip(getClass().getResource("sounds/fastinvader2.wav").toExternalForm());
    private AudioClip invShootSound2 = new AudioClip(getClass().getResource("sounds/fastinvader3.wav").toExternalForm());
    private AudioClip invShootSound3 = new AudioClip(getClass().getResource("sounds/fastinvader4.wav").toExternalForm());

    // Player
    private Player player;
    private int movementDirection = 0;
    private ArrayList<PlayerBullet> bullets;
    private long lastBullet = System.currentTimeMillis();

    // Enemy
    private Enemy enemies;
    private ArrayList<EnemyBullet> enemyBullets;
    private long lastEnemyBullet = System.currentTimeMillis();

    // information to be tracked
    private static boolean isOver = false;
    private int SCORE;
    private int LIVES;
    private int LEVEL;

    // magic numbers
    private static final double PLAYER_BULLET_SPEED = 6;
    private static final double ENEMY1_BULLET_SPEED = 4;
    private static final double ENEMY2_BULLET_SPEED = 5;
    private static final double ENEMY3_BULLET_SPEED = 6;

    GameScreen(){ // constructor for GameScreen
        root = new Group();
        gameScene = new Scene(root, 800, 600, Color.BLACK);
        root.requestFocus();
    }

    // start game at level with score and lives
    public void startGame(int score, int lives, int level){
        isOver = false;
        SCORE = score;
        LIVES = lives;
        LEVEL = level;

        // initialize status bar;
        statusBar = new StatusBar(score, lives, level);
        root.getChildren().add(statusBar.getStatusBar());

        // initialize player
        player = new Player();
        bullets = new ArrayList<>();
        root.getChildren().add(player.getPlayer());

        // initialize enemy
        enemies = new Enemy(level);
        enemyBullets = new ArrayList<>();
        for (Group enemyRow: enemies.getEnemy()){
            root.getChildren().add(enemyRow);
        }

        // handling user interactions during game play
        root.setOnKeyPressed(keyEvent -> {
            if (!isOver) { // stop tracking inputs when game is over
                // movement command inputted
                if (keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT) {
                    movementDirection = -1;
                } else if (keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT) {
                    movementDirection = 1;
                }

                // shoot command inputted
                if (keyEvent.getCode() == KeyCode.SPACE) {
                    // create PlayerBullet
                    if (System.currentTimeMillis() > lastBullet + 500) { // check that two shots per second
                        lastBullet = System.currentTimeMillis();
                        PlayerBullet bullet = new PlayerBullet(player.getPosition());
                        bullets.add(bullet);
                        root.getChildren().add(bullet.getBullet());
                        shootSound.play();
                    }
                }
            }
        });

        root.setOnKeyReleased(keyEvent -> {
            // stop movement
            if (keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT){
                movementDirection = 0;
            } else if (keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT){
                movementDirection = 0;
            }
        });

        // main game loop
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isOver) {
                    // handle movements
                    try {
                        enemies.moveEnemy();
                    } catch (Exception e) {
                        isOver = true;
                    }
                    checkEnemy();
                    player.movePlayer(movementDirection);

                    // handle bullets
                    playerShot();
                    enemyShot();

                    // increase level if all enemies destroyed
                    changeLevel();

                } else { // handle game over
                    boolean win = (enemies.getEnemiesLeft()  == 0);
                    EndScreen endScreen = new EndScreen(SCORE, win);
                    root.getChildren().clear();
                    root.getChildren().addAll(statusBar.getStatusBar(), endScreen.getEndScreen());
                    gameLoop.stop();
                }
            }
        };
        gameLoop.start();
    }

    private void checkEnemy(){
        // check that enemy has not hit the ship
        for (Group enemyRow: enemies.getEnemy()) {
            for (Node enemyNode : enemyRow.getChildren()) {
                if (player.getPlayer().intersects(enemyNode.getBoundsInParent())){
                    // destroy ship and respawn ship
                    root.getChildren().remove(player.getPlayer());
                    player = new Player();
                    root.getChildren().add(player.getPlayer());
                    explosionSound.play();

                    // decrease number of lives
                    LIVES--;
                    statusBar.updateLives(LIVES);
                    if (LIVES == 0){
                        isOver = true;
                    }

                    // update score
                    SCORE -= 10;
                    statusBar.updateScore(SCORE);
                }
            }
        }
    }

    private void playerShot(){
        // move bullets
        for (PlayerBullet bullet: bullets){
            double y = bullet.getBullet().getY();

            if (y - PLAYER_BULLET_SPEED < 0){ // remove bullet when leaving the screen
                bullets.remove(bullet);
                root.getChildren().remove(bullet.getBullet());
            } else {
                bullet.moveBullet(y - PLAYER_BULLET_SPEED);
            }
        }

        // check if the bullet hit any enemies
        if (enemies.getEnemiesLeft() != 0){
            int row = 0;
            for (Group enemyRow: enemies.getEnemy()) {
                int counter = 0;
                for (Node enemyNode : enemyRow.getChildren()) {
                    for (PlayerBullet bullet: bullets){
                        if (bullet.getBullet().intersects(enemyNode.getBoundsInParent())){
                            try{
                                // kill alien
                                enemies.killEnemy(row, counter);
                            } catch (Exception e){
                                if (LEVEL == 3){
                                    isOver = true;
                                }
                            }
                            // play sound
                            invKilledSound.play();

                            // remove bullet
                            bullets.remove(bullet);
                            root.getChildren().remove(bullet.getBullet());

                            // update score
                            if (row == 0){
                                SCORE += 30 * LEVEL;
                            } else if (row == 1 || row == 2){
                                SCORE += 20 * LEVEL;
                            } else if (row == 3 || row == 4){
                                SCORE += 10 * LEVEL;
                            }
                            statusBar.updateScore(SCORE);
                        }
                    }
                    counter++;
                }
                row++;
            }
        }
    }

    private void enemyShot(){
        // shoot enemy bullet
        if (System.currentTimeMillis() > lastEnemyBullet + 1000 + (250 / LEVEL)){ // faster in higher levels
            // select random enemy to shoot bullet
            lastEnemyBullet = System.currentTimeMillis();
            int randRow = ThreadLocalRandom.current().nextInt(0, enemies.getEnemy().size());
            int randEnemy = ThreadLocalRandom.current().nextInt(0, enemies.getEnemy().get(randRow).getChildren().size());

            double x = enemies.getEnemy().get(randRow).getChildren().get(randEnemy).getBoundsInParent().getCenterX();
            double y = enemies.getEnemy().get(randRow).getChildren().get(randEnemy).getBoundsInParent().getMaxY();

            // create EnemyBullet
            EnemyBullet enemyBullet = new EnemyBullet(randRow, x, y);
            enemyBullets.add(enemyBullet);
            root.getChildren().add(enemyBullet.getBullet());

            //play sound
            if (randRow == 0) {
                invShootSound1.play();
            } else if (randRow == 1 || randRow == 2) {
                invShootSound2.play();
            } else if (randRow == 3 || randRow == 4) {
                invShootSound1.play();
            }
        }

        // move enemyBullets
        for (EnemyBullet enemyBullet: enemyBullets){
            double y = enemyBullet.getBullet().getY();
            int type = enemyBullet.getStyle();
            double speed = 0;

            //play sound
            if (type == 0) {
                speed = ENEMY3_BULLET_SPEED;
            } else if (type == 1 || type == 2) {
                speed = ENEMY2_BULLET_SPEED;
            } else if (type == 3 || type == 4) {
                speed = ENEMY1_BULLET_SPEED;
            }

            if (y + speed > 600){ // remove bullet when leaving the screen
                enemyBullets.remove(enemyBullet);
                root.getChildren().remove(enemyBullet.getBullet());
            } else {
                enemyBullet.moveBullet(y + speed);
            }
        }

        // check if the enemyBullet hit the ship
        for (EnemyBullet enemyBullet: enemyBullets){
            if (enemyBullet.getBullet().intersects(player.getPlayer().getBoundsInParent())){
                // destroy ship and respawn ship
                root.getChildren().remove(player.getPlayer());
                player = new Player();
                root.getChildren().add(player.getPlayer());
                explosionSound.play();

                // remove bullet
                enemyBullets.remove(enemyBullet);
                root.getChildren().remove(enemyBullet.getBullet());

                // decrease number of lives
                LIVES--;
                statusBar.updateLives(LIVES);
                if (LIVES == 0){
                    isOver = true;
                }

                // update score
                SCORE -= 10 * LEVEL;
                statusBar.updateScore(SCORE);
            }
        }
    }

    private void changeLevel(){
        if (enemies.getEnemiesLeft() == 0) {
            LEVEL++;
            if (LEVEL > 3) {
                isOver = true;
                return;
            }
            statusBar.updateLevel(LEVEL);
            root.getChildren().clear();
            startGame(SCORE, LIVES, LEVEL);
        }
    }

    public boolean isGameOver() { return isOver; }

    public void clearScreen() {
        root.getChildren().clear();
    }

    public void quit(){
        gameLoop.stop();
        root.getChildren().clear();
    }

    public void restart(int level){
        root.getChildren().clear();
        startGame(0, 3, level);
    }

    public Scene getScene(){
        return this.gameScene;
    }
}
