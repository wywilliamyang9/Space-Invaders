import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerBullet {
    private ImageView bullet;

    PlayerBullet(int x){
        Image image = new Image("images/player_bullet.png", 10, 30, true, true);
        bullet = new ImageView(image);
        bullet.setX(x);
        bullet.setY(600 - 45);
    }

    public void moveBullet(double y){
        bullet.setY(y);
    }

    public ImageView getBullet(){ return this.bullet; }
}
