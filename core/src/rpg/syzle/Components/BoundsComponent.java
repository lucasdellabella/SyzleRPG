package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Polygon;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by lucasdellabella on 1/2/17.
 */

public class BoundsComponent implements Component, Poolable {
    public Array<Polygon> hitboxes = new Array<>();

    /**
     * Add a hitbox to this bounds component
     * @param x the x position of the hitbox's bottom left corner relative to the entity's bottom
     *          left corner
     * @param y the y position of the hitbox's bottom left corner relative to the entity's bottom
     *          left corner
     * @param width the width of the hitbox
     * @param height the height of the hitbox
     */
    public void addHitbox(int x, int y, int width, int height) {
        Polygon newHitbox = new Polygon(new float[]
                        {x, y,
                        x + width, y,
                        x + width, y + height,
                        x, y + height});
        hitboxes.add(newHitbox);
    }

    public Array<Polygon> getHitboxes() {
        return hitboxes;
    }

    @Override
    public void reset() {
        hitboxes.clear();
    }
}
