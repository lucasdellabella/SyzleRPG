package rpg.syzle.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by lucasdellabella on 1/3/17.
 */

public class BulletComponent implements Component, Poolable {
    public Entity owner;
    public int damage = 1;

    @Override
    public void reset() {
        owner = null;
    }
}
