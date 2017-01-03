package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by joe on 1/3/17.
 */

public class PlayerComponent implements Component, Poolable {
    public final int WIDTH = 32;
    public final int HEIGHT = 32;

    @Override
    public void reset() {
        // Do nothing (WIDTH and HEIGHT are constants)
    }
}
