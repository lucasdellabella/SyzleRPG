package rpg.syzle.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by lucasdellabella on 12/29/16.
 * Any direction passed in should BE A DIRECTION and will be normalized.
 */
public class Bullet extends Sprite implements MyDrawable {

    private Vector2 direction;
    private float speed;

    public Bullet(Texture texture, Vector2 startPos, Vector2 direction, float speed) {
        super(texture);
        setBounds(startPos.x, startPos.y, 10, 36);
        setOriginCenter();
        rotate(direction.angle() - 90);
        this.direction = direction.nor();
        this.speed = speed;
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
