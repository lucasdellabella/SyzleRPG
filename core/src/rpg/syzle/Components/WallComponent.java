package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by lucasdellabella on 1/3/17.
 */

public class WallComponent implements Component, Poolable {
    public boolean destructible = false;

    @Override
    public void reset() {
        destructible = false;
    }
}
