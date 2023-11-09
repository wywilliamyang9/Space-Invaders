import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;


public class TitleScreen {
    private Scene titleScene;

    TitleScreen(){ // constructor for TitleScreen
        // The Space Invaders Logo
        Image gameLogo = new Image("images/logo.png");
        ImageView gameLogoView = new ImageView(gameLogo);

        // Instructions on how to play and/ro quit the application
        Text instruction1 = new Text("Instructions");
        Text instruction2 = new Text("ENTER - Start Game");
        Text instruction3 = new Text("A or ◀, D or ▶ - Move ship left or right");
        Text instruction4 = new Text("SPACE - Fire!");
        Text instruction5 = new Text("Q - Quit Game");
        Text instruction6 = new Text("1 or 2 or 3 - Start Game at a specific level");

        // my name and student number
        Text creatorName = new Text("Implemented by William Yang(20788646) for  CS 349, University of Waterloo, S21");

        // setting custom font for the text
        instruction1.setFont(Font.font("Helvetica", 36));
        instruction2.setFont(Font.font("Helvetica", 16));
        instruction3.setFont(Font.font("Helvetica", 16));
        instruction4.setFont(Font.font("Helvetica", 16));
        instruction5.setFont(Font.font("Helvetica", 16));
        instruction6.setFont(Font.font("Helvetica", 16));
        creatorName.setFont(Font.font("Helvetica", 16));

        // VBox for instructions
        VBox instructions = new VBox(10);
        instructions.setAlignment(Pos.CENTER);
        instructions.getChildren().addAll(instruction1, instruction2, instruction3,
                instruction4, instruction5, instruction6);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(gameLogoView);
        BorderPane.setAlignment(gameLogoView, Pos.CENTER);
        borderPane.setBottom(creatorName);
        BorderPane.setAlignment(creatorName, Pos.CENTER);
        borderPane.setCenter(instructions);

        // create Scene for title page
        titleScene = new Scene(borderPane, 800, 600);
    }

    public Scene getScene(){
        return this.titleScene;
    }
}
