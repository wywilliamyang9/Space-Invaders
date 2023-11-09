import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

import java.util.ArrayList;


public class Enemy {
    private ArrayList<Group> enemies;
    private int movementDirection;
    private int numAliens = 50;
    private double speed;

    // for audio
    private int current = 1;
    private AudioClip move = new AudioClip(getClass().getResource("sounds/fastinvader1.wav").toExternalForm());

    // magic numbers
    private static final double ENEMY_SPEED1 = 0.5;
    private static final double ENEMY_SPEED2 = 1;
    private static final double ENEMY_SPEED3 = 2;
    private static final double ENEMY_VERTICAL_SPEED = 10;

    Enemy(int level){ // constructor for Enemy
        enemies = new ArrayList<>();

        // create grid of aliens
        for (int row = 1; row <= 5; row++){
            Group enemyRow = new Group();
            for (int column = 1; column <= 10; column++){
                if (row == 1){
                    enemyRow.getChildren().add(new Alien(3, row, column).getAlien());
                } else if (row == 2 || row == 3){
                    enemyRow.getChildren().add(new Alien(2, row, column).getAlien());
                } else {
                    enemyRow.getChildren().add(new Alien(1, row, column).getAlien());
                }
            }
            enemies.add(enemyRow);
        }

        // set starting movement speed based on level
        if (level == 1){
            speed = ENEMY_SPEED1;
        } else if (level == 2){
            speed = ENEMY_SPEED2;
        } else if (level == 3){
            speed = ENEMY_SPEED3;
        }

        // set default movement direction: right
        movementDirection = 1;
    }

    public void moveEnemy() throws Exception {
        for (Group enemyRow: enemies){ // makes sure that moving the aliens will not go out of bounds
            for (Node enemyNode: enemyRow.getChildren()){
                ImageView alien = (ImageView) enemyNode;
                double x = alien.getX();

                double destinationX = x + (speed * movementDirection);

                if (!((destinationX <= 800 - 50 && movementDirection == 1)
                        || (destinationX > 0 && movementDirection == -1))){
                    //change movement direction;
                    if (movementDirection == 1){
                        movementDirection = -1;
                    } else if (movementDirection == -1){
                        movementDirection = 1;
                    }

                    // move every thing down
                    for (Group enemyRow2: enemies) {
                        for (Node enemyNode2 : enemyRow2.getChildren()) {
                            ImageView alien2 = (ImageView) enemyNode2;

                            if (alien2.getY() > 600 - 45) { // check that non of the aliens have reached the bottom
                                throw new Exception("Game Over");
                            }

                            alien2.setY(alien2.getY() + ENEMY_VERTICAL_SPEED);
                        }
                    }

//                    speed += 0.1; // increase speed for each direction change

                    // play sound for row change
                    move.play();
                }
            }
        }


        for (Group enemyRow: enemies){ // move the aliens
            for (Node enemyNode: enemyRow.getChildren()){
                ImageView alien = (ImageView) enemyNode;
                double x = alien.getX();

                double destinationX = x + (speed * movementDirection);

                alien.setX(destinationX);
            }
        }
    }

    public void killEnemy(int row, int number) throws Exception{
        enemies.get(row).getChildren().remove(number);
        numAliens--;

        speed += 0.1; // increase speed for each kill

        if (numAliens == 0){
            throw new Exception("Game Over");
        }
    }

    public ArrayList<Group> getEnemy(){ return this.enemies; }

    public int getEnemiesLeft(){ return this.numAliens; }
}
