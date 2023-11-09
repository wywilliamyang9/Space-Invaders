import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class StatusBar {
    HBox statusBar;
    Text scoreTxt;
    Text livesTxt;
    Text levelTxt;

    StatusBar(int score, int lives, int level){
        scoreTxt = new Text("Score: " + score);
        livesTxt = new Text("Lives: " + lives);
        levelTxt = new Text("Level: " + level);

        // customize font
        scoreTxt.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        livesTxt.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        levelTxt.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));

        // change font colour
        scoreTxt.setFill(Color.WHITE);
        livesTxt.setFill(Color.WHITE);
        levelTxt.setFill(Color.WHITE);

        HBox livesLevels = new HBox(30, livesTxt, levelTxt);
        statusBar = new HBox(500, scoreTxt, livesLevels);
        statusBar.setPrefWidth(800);
        statusBar.setAlignment(Pos.TOP_CENTER);
    }

    public void updateScore(int score){
        scoreTxt.setText("Score: " + score);
    }

    public void updateLives(int lives){
        livesTxt.setText("Lives: " + lives);
    }

    public void updateLevel(int level){
        levelTxt.setText("Level: " + level);
    }

    public HBox getStatusBar(){ return this.statusBar; }
}
