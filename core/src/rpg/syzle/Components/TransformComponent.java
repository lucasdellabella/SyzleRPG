package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by lucasdellabella on 1/3/17.
 * --
 * Necessary for drawing things, and contains the position at which we draw something (not
 * everything in our game will need a bounds / be collidable - there will be some visual things
 * that want to be drawn but not take part in collisions
 */

public class TransformComponent implements Component, Poolable {
    public final Vector2 translate = new Vector2(0, 0);
    public final Vector2 scale = new Vector2(1.0f, 1.0f);
    public float rotation = 0.0f;

    @Override
    public void reset() {
        translate.set(0, 0);
        scale.set(1.0f, 1.0f);
        rotation = 0.0f;
    }
}
