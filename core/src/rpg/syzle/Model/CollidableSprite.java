package rpg.syzle.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;

/**
 * Created by lucasdellabella on 12/30/16.
 */
public abstract class CollidableSprite extends Sprite {

    public CollidableSprite(Texture texture) {
        super(texture);
    }
    public abstract void onCollision(CollidableSprite collidableSprite);

    public boolean overlaps(CollidableSprite collidableSprite) {
        return Intersector.overlaps(this.getBoundingRectangle(), collidableSprite.getBoundingRectangle());
    }
}
