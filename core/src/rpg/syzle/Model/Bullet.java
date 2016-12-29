package rpg.syzle.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import static rpg.syzle.Constants.PLAYER_WIDTH;
import static rpg.syzle.Constants.PLAYER_HEIGHT;

/**
 * Created by lucasdellabella on 12/29/16.
 * Any direction passed in should BE A DIRECTION and will be normalized.
 */
public class Bullet extends Sprite implements MyDrawable {

    private Vector2 direction;
    private float speed;

    public Bullet(Texture texture, Vector2 startPos, Vector2 direction, float speed) {
        super(texture);
        rotate(direction.angle() - 90);
        this.direction = direction.nor();
        this.speed = speed;

        setBounds(startPos.x, startPos.y, getTexture().getWidth()/10f, getTexture().getHeight()/10f);
        correctBulletBounds();
        setOriginCenter();
    }

    // Should be player width and height, not tile width and happen.
    // Right now, however, they both happen to be the same
    private void correctBulletBounds() {
        setBounds(getX() - getWidth() / 2 + direction.x * PLAYER_WIDTH,
                getY() + getHeight() / 2 + direction.y * PLAYER_HEIGHT,
                getWidth(),
                getHeight());
    }

    public void move() {
        setBounds(getX() + direction.x * speed, getY() + direction.y * speed, 10, 36);
    }

    public Vector2 getDirection() {
        return direction;
    }

    public float getSpeed() {
        return speed;
    }
}
