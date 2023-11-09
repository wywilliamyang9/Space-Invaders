import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Alien {
    private ImageView alien;

    Alien(int type, double row, double column){ // constructor for Player
        Image alienImage = new Image("images/enemy" + type + ".png", 45, 40, true, true);
        alien = new ImageView(alienImage);
        alien.setX(column * 50);
        alien.setY(row * 50 + 20);
    }

    public ImageView getAlien(){ return this.alien; }

}
