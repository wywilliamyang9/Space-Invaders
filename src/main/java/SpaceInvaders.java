import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


public class SpaceInvaders extends Application{
    @Override
    public void start(Stage stage){
        stage.setTitle("Space Invaders");
        stage.setResizable(false);

        TitleScreen titleScreen = new TitleScreen();
        Scene titleScene = titleScreen.getScene();

        GameScreen gameScreen = new GameScreen();
        Scene gameScene = gameScreen.getScene();

        // level selector or start game
        titleScene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.DIGIT1){
                // initialize game at level 1
                gameScreen.startGame(0, 3,1);
                stage.setScene(gameScene);
            } else if (keyEvent.getCode() == KeyCode.DIGIT2){
                // initialize game at level 2
                gameScreen.startGame(0, 3, 2);
                stage.setScene(gameScene);
            } else if (keyEvent.getCode() == KeyCode.DIGIT3){
                // initialize game at level 3
                gameScreen.startGame(0, 3, 3);
                stage.setScene(gameScene);
            }
        });

        // quit or restart game
        gameScene.setOnKeyPressed(keyEvent -> { // quit game
            if (keyEvent.getCode() == KeyCode.Q){
                gameScreen.quit();
                stage.setScene(titleScene);
                stage.requestFocus();
            }

            if (gameScreen.isGameOver()) {
                if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.DIGIT1){
                    // restart game at level 1
                    gameScreen.restart(1);
                } else if (keyEvent.getCode() == KeyCode.DIGIT2){
                    // restart game at level 2
                    gameScreen.restart(2);
                } else if (keyEvent.getCode() == KeyCode.DIGIT3){
                    // restart game at level 3
                    gameScreen.restart(3);
                } else if (keyEvent.getCode() == KeyCode.I) { // play again
                    gameScreen.clearScreen();
                    stage.setScene(titleScene);
                    stage.requestFocus();
                } else if (keyEvent.getCode() == KeyCode.Q){
                    System.exit(0);
                }
            }
        });

        stage.setScene(titleScene);
        stage.show();
    }
}
