import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
    private int x = 800/2 - 40;
    private int y = 600 - 40;
    private final int max = 800 - 80;
    private ImageView player;

    // magic number
    private static final int PLAYER_SPEED = 5;

    Player(){ // constructor for Player
        Image playerImage = new Image("images/player.png", 80, 40, true, true);
        player = new ImageView(playerImage);
        player.setX(x);
        player.setY(y);
    }

    public void movePlayer(int direction){
        int destinationX = x + (PLAYER_SPEED * direction);
        if ((destinationX <= max && direction > 0)
                || (destinationX >= 0 && direction < 0)){
            x = destinationX;
            player.setX(x);
        }
    }

    public int getPosition(){ return this.x + 40; }; // returns center of the ship

    public ImageView getPlayer(){ return this.player; };
}
