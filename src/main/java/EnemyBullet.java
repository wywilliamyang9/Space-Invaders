import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EnemyBullet {
    private ImageView enemyBullet;
    private int style;

    EnemyBullet(int type, double x, double y){
        style = type;
        if (type == 0) {
            Image image = new Image("images/bullet3.png", 20, 35, true, true);
            enemyBullet = new ImageView(image);
        } else if (type == 1 || type == 2) {
            Image image = new Image("images/bullet2.png", 20, 35, true, true);
            enemyBullet = new ImageView(image);
        } else if (type == 3 || type == 4) {
            Image image = new Image("images/bullet1.png", 20, 35, true, true);
            enemyBullet = new ImageView(image);
        }
        enemyBullet.setX(x);
        enemyBullet.setY(y);
    }

    public void moveBullet(double y){
        enemyBullet.setY(y);
    }

    public int getStyle(){ return this.style; }

    public ImageView getBullet(){ return this.enemyBullet; }
}
