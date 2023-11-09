import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class EndScreen {
    private Pane endScreen;

    EndScreen(int score, boolean win){
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER_RIGHT);

        // background for end screen
        Rectangle rectangle = new Rectangle(500, 300);
        rectangle.setFill(Color.WHITE);
        rectangle.setArcHeight(50.0d);
        rectangle.setArcWidth(50.0d);

        // Win Lose message
        Text winLose = new Text();
        if (win){
            winLose.setText("YOU WON!");
        } else {
            winLose.setText("GAME OVER!");
        }
        winLose.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));

        // Other Text
        Text scoreText = new Text("Final Score: " + score);
        Text instruction1 = new Text("ENTER - Start New Game");
        Text instruction2 = new Text("I - Back to Instructions");
        Text instruction3 = new Text("Q - Quit Game");
        Text instruction4 = new Text("1 or 2 or 3 - Start New Game at a specific level");

        scoreText.setFont(Font.font("Helvetica", 20));
        instruction1.setFont(Font.font("Helvetica", 20));
        instruction2.setFont(Font.font("Helvetica", 20));
        instruction3.setFont(Font.font("Helvetica", 20));
        instruction4.setFont(Font.font("Helvetica", 20));

        // VBox Formatting
        VBox vBox1 = new VBox(10);
        vBox1.getChildren().addAll(winLose, scoreText);
        vBox1.setAlignment(Pos.CENTER);
        VBox vBox2 = new VBox(10);
        vBox2.getChildren().addAll(instruction1, instruction2, instruction3, instruction4);
        vBox2.setAlignment(Pos.CENTER);
        VBox vBox3 = new VBox(30);
        vBox3.getChildren().addAll(vBox1, vBox2);
        vBox3.setAlignment(Pos.CENTER);

        // add everything to stackPane
        stackPane.getChildren().addAll(rectangle, vBox3);
        endScreen = new Pane(stackPane);
        stackPane.relocate(150, 175);
    }

    public Pane getEndScreen(){ return this.endScreen; }
}
