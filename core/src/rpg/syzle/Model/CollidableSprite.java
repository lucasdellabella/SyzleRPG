package rpg.syzle.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

/**
 * Struggling to figure out how to allow classes to implement collide for whichever types they want
 * Created by lucasdellabella on 12/30/16.
 */
public abstract class CollidableSprite extends Sprite {

    public CollidableSprite(Texture texture) {
        super(texture);
    }

    //public abstract void onCollision(CollidableSprite collidableSprite);
    //public abstract void collide(CollidableSprite collidableSprite);

    public Rectangle overlaps(CollidableSprite collidableSprite) {
        Rectangle intersection = new Rectangle(0, 0, 0, 0);
        Intersector.intersectRectangles(this.getBoundingRectangle(),
                collidableSprite.getBoundingRectangle(),
                intersection);
        return intersection;
    }
}
