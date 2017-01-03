package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by lucasdellabella on 1/3/17.
 */

public class TransformComponent implements Component, Poolable {
    public final Vector2 scale = new Vector2(1.0f, 1.0f);
    public float rotation = 0.0f;

    @Override
    public void reset() {
        scale.set(1.0f, 1.0f);
        rotation = 0.0f;
    }
}
